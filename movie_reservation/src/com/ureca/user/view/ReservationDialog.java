package com.ureca.user.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ureca.reservation.model.service.ReservationService;
import com.ureca.user.model.dto.Hall;
import com.ureca.user.model.dto.Movie;
import com.ureca.user.model.dto.Reservation;
import com.ureca.user.model.dto.User;

/**
 * 예약 흐름: 관+영화 회차(hall) 조회 → 회차 선택 → 해당 회차 좌석(reservation) 조회/선택
 */
public class ReservationDialog extends JDialog
{
	private static final int DEFAULT_SEATS = 40;
	private static final int SEAT_COLS = 10;

	private final MessageDialog dialog;
	private final ReservationService reservationService;
	private final Movie movie;
	private final User currentUser;
	private final Runnable onReserved;
	private final List<Hall> showtimes;

	private final JPanel centerPan;
	private JPanel confirmPan;
	private JLabel confirmL;
	private JButton yesBt;
	private JButton noBt;

	private Hall selectedShow;
	private int pendingSeat;

	public ReservationDialog(java.awt.Window owner, MessageDialog dialog, ReservationService reservationService,
			Movie movie, User currentUser, List<Hall> showtimes, Runnable onReserved)
	{
		super(owner, "좌석 예약 - " + movie.getMovie_name(), ModalityType.APPLICATION_MODAL);
		this.dialog = dialog;
		this.reservationService = reservationService;
		this.movie = movie;
		this.currentUser = currentUser;
		this.showtimes = showtimes;
		this.onReserved = onReserved;

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new BorderLayout(8, 8));

		centerPan = new JPanel(new BorderLayout());
		add(buildHeaderPanel(), BorderLayout.NORTH);
		add(centerPan, BorderLayout.CENTER);
		add(buildConfirmPanel(), BorderLayout.SOUTH);

		if (showtimes == null || showtimes.isEmpty())
		{
			showSeatPanel(fallbackShow());
		}
		else if (showtimes.size() == 1)
		{
			selectShow(showtimes.get(0));
		}
		else
		{
			showShowtimePanel();
		}

		pack();
		setSize(Math.max(getWidth(), 600), Math.max(getHeight(), 480));
		setLocationRelativeTo(owner);
	}

	private Hall fallbackShow()
	{
		return new Hall(movie.getHall_id(), 1, DEFAULT_SEATS, movie.getMovie_time() > 0 ? movie.getMovie_time() : 120,
				movie.getStart_time(), movie.getMovie_id(), 1);
	}

	private JPanel buildHeaderPanel()
	{
		JPanel panel = new JPanel(new GridLayout(0, 1, 2, 2));
		panel.add(new JLabel(String.format("%d관 | 영화: %s | 회원: %s (ID %d)", movie.getHall_id(), movie.getMovie_name(),
				currentUser.getName(), currentUser.getId()), JLabel.CENTER));
		if (selectedShow != null)
		{
			panel.add(new JLabel(String.format("선택 회차: %d회차 %s | 좌석 %d석 | 상영 %d분", selectedShow.getSeatId(),
					selectedShow.getStartTime() != null ? selectedShow.getStartTime().toString() : "-",
					selectedShow.getSeatCount(), selectedShow.getMovieTime()), JLabel.CENTER));
		}
		else if (showtimes != null && !showtimes.isEmpty())
		{
			panel.add(new JLabel("상영 회차를 선택한 뒤 좌석을 고르세요.", JLabel.CENTER));
		}
		return panel;
	}

	private void showShowtimePanel()
	{
		selectedShow = null;
		centerPan.removeAll();
		JPanel wrapper = new JPanel(new BorderLayout(5, 5));
		wrapper.add(new JLabel("상영 회차 선택 (hall: 관·회차·시작시간·좌석수)", JLabel.CENTER), BorderLayout.NORTH);

		JPanel btnPan = new JPanel(new GridLayout(0, 2, 6, 6));
		for (final Hall show : showtimes)
		{
			String label = String.format("%d회차 %s | %d석", show.getSeatId(),
					show.getStartTime() != null ? show.getStartTime().toString() : "-", show.getSeatCount());
			JButton bt = new JButton(label);
			bt.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					selectShow(show);
				}
			});
			btnPan.add(bt);
		}
		wrapper.add(btnPan, BorderLayout.CENTER);
		centerPan.add(wrapper, BorderLayout.CENTER);
		centerPan.revalidate();
		repaint();
	}

	private void selectShow(Hall show)
	{
		selectedShow = show;
		showSeatPanel(show);
	}

	private void showSeatPanel(Hall show)
	{
		centerPan.removeAll();
		int seatCount = show.getSeatCount() > 0 ? show.getSeatCount() : DEFAULT_SEATS;
		int rows = (int) Math.ceil((double) seatCount / SEAT_COLS);
		Timestamp startAt = resolveStartDatetime(show);

		JPanel wrapper = new JPanel(new BorderLayout(5, 5));
		wrapper.add(
				new JLabel(
						String.format("%d관 %d회차 %s — 좌석 1~%d (회색: 이미 예약됨)", show.getHallId(), show.getSeatId(),
								show.getStartTime() != null ? show.getStartTime().toString() : "-", seatCount),
						JLabel.CENTER),
				BorderLayout.NORTH);

		JPanel seatPan = new JPanel(new GridLayout(rows, SEAT_COLS, 4, 4));
		for (int seat = 1; seat <= seatCount; seat++)
		{
			JButton seatBt = new JButton(String.valueOf(seat));
			final int seatNum = seat;
			boolean taken = reservationService.isSeatTaken(movie.getMovie_id(), show.getHallId(), startAt, seatNum);
			if (taken)
			{
				seatBt.setEnabled(false);
				seatBt.setBackground(Color.LIGHT_GRAY);
				seatBt.setText(seat + "(예약)");
			}
			else
			{
				seatBt.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						showConfirm(seatNum);
					}
				});
			}
			seatPan.add(seatBt);
		}

		if (showtimes != null && showtimes.size() > 1)
		{
			JButton backBt = new JButton("← 회차 다시 선택");
			backBt.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					hideConfirm();
					showShowtimePanel();
				}
			});
			wrapper.add(backBt, BorderLayout.SOUTH);
		}

		wrapper.add(seatPan, BorderLayout.CENTER);
		centerPan.add(wrapper, BorderLayout.CENTER);
		centerPan.revalidate();
		repaint();
		pack();
	}

	private JPanel buildConfirmPanel()
	{
		confirmPan = new JPanel(new BorderLayout(5, 5));
		confirmL = new JLabel("", JLabel.CENTER);
		JPanel btnPan = new JPanel();
		yesBt = new JButton("YES");
		noBt = new JButton("NO");
		btnPan.add(yesBt);
		btnPan.add(noBt);
		confirmPan.add(confirmL, BorderLayout.CENTER);
		confirmPan.add(btnPan, BorderLayout.SOUTH);
		confirmPan.setVisible(false);

		yesBt.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				reserveSeat();
			}
		});
		noBt.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				hideConfirm();
			}
		});
		return confirmPan;
	}

	private void showConfirm(int seat)
	{
		pendingSeat = seat;
		confirmL.setText(String.format("%d회차 %s — %d번 좌석을 예약하시겠습니까?", selectedShow.getSeatId(),
				selectedShow.getStartTime() != null ? selectedShow.getStartTime().toString() : "-", seat));
		confirmPan.setVisible(true);
		confirmPan.revalidate();
		repaint();
	}

	private void hideConfirm()
	{
		confirmPan.setVisible(false);
		pendingSeat = 0;
	}

	private Timestamp resolveStartDatetime(Hall show)
	{
		if (show.getStartTime() != null)
		{
			return Timestamp.valueOf(movie.getAir_date().toString() + " " + show.getStartTime().toString());
		}
		return Timestamp.valueOf(movie.getAir_date().toString() + " 10:00:00");
	}

	private void reserveSeat()
	{
		try
		{
			if (selectedShow == null)
			{
				dialog.show("상영 회차를 먼저 선택해 주세요.");
				return;
			}
			Timestamp startAt = resolveStartDatetime(selectedShow);
			int reservationId = reservationService.nextReservationId();
			Reservation res = new Reservation(reservationId, pendingSeat, movie.getMovie_id(), currentUser.getId(),
					selectedShow.getHallId(), startAt);
			reservationService.add(res);
			dialog.show(String.format("예약 완료\n예약번호: %d | %s | %d관 %d회차 %s | 좌석 %d번", reservationId,
					movie.getMovie_name(), selectedShow.getHallId(), selectedShow.getSeatId(),
					selectedShow.getStartTime() != null ? selectedShow.getStartTime().toString() : "-", pendingSeat));
			hideConfirm();
			dispose();
			onReserved.run();
		}
		catch (Exception err)
		{
			dialog.show(err.getMessage());
		}
	}
}
