package com.ureca.user.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ureca.reservation.model.service.ReservationService;
import com.ureca.user.model.dto.Movie;
import com.ureca.user.model.dto.Reservation;
import com.ureca.user.model.dto.User;

public class ReservationDialog extends JDialog
{
	private static final int ROWS = 5;
	private static final int COLS = 8;
	private static final int TOTAL_SEATS = ROWS * COLS;

	private final MessageDialog dialog;
	private final ReservationService reservationService;
	private final Movie movie;
	private final User currentUser;
	private final Runnable onReserved;

	private JPanel confirmPan;
	private JLabel confirmL;
	private JButton yesBt;
	private JButton noBt;
	private int pendingSeat;

	public ReservationDialog(java.awt.Window owner, MessageDialog dialog, ReservationService reservationService,
			Movie movie, User currentUser, Runnable onReserved)
	{
		super(owner, "좌석 예약 - " + movie.getMovie_name(), ModalityType.APPLICATION_MODAL);
		this.dialog = dialog;
		this.reservationService = reservationService;
		this.movie = movie;
		this.currentUser = currentUser;
		this.onReserved = onReserved;

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new BorderLayout(8, 8));

		JLabel infoL = new JLabel(String.format("영화: %s | 회원: %s (ID %d)", movie.getMovie_name(),
				currentUser.getName(), currentUser.getId()), JLabel.CENTER);
		add(infoL, BorderLayout.NORTH);
		add(buildSeatPanel(), BorderLayout.CENTER);
		add(buildConfirmPanel(), BorderLayout.SOUTH);

		pack();
		setSize(Math.max(getWidth(), 520), Math.max(getHeight(), 380));
		setLocationRelativeTo(owner);
	}

	private JPanel buildSeatPanel()
	{
		JPanel wrapper = new JPanel(new BorderLayout(5, 5));
		wrapper.add(new JLabel("좌석을 선택하세요", JLabel.CENTER), BorderLayout.NORTH);

		JPanel seatPan = new JPanel(new GridLayout(ROWS, COLS, 4, 4));
		for (int sitId = 1; sitId <= TOTAL_SEATS; sitId++)
		{
			JButton seatBt = new JButton(String.valueOf(sitId));
			final int seat = sitId;
			if (reservationService.isSeatTaken(movie.getMovie_id(), seat))
			{
				seatBt.setEnabled(false);
				seatBt.setText(seat + "(예약)");
			}
			else
			{
				seatBt.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						showConfirm(seat);
					}
				});
			}
			seatPan.add(seatBt);
		}
		wrapper.add(seatPan, BorderLayout.CENTER);
		return wrapper;
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
		confirmL.setText(seat + "번 좌석을 예약하시겠습니까?");
		confirmPan.setVisible(true);
		confirmPan.revalidate();
		repaint();
	}

	private void hideConfirm()
	{
		confirmPan.setVisible(false);
		pendingSeat = 0;
	}

	private void reserveSeat()
	{
		try
		{
			int reservationId = reservationService.nextReservationId();
			Reservation res = new Reservation(reservationId, pendingSeat, movie.getMovie_id(), currentUser.getId());
			reservationService.add(res);
			dialog.show("예약이 완료되었습니다. (예약번호: " + reservationId + ")");
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
