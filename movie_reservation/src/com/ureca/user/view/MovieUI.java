package com.ureca.user.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.ureca.hall.model.service.HallService;
import com.ureca.movie.model.service.MovieService;
import com.ureca.reservation.model.service.ReservationService;
import com.ureca.user.model.dto.Hall;
import com.ureca.user.model.dto.Movie;
import com.ureca.user.model.dto.Reservation;
import com.ureca.user.model.dto.User;
import com.ureca.user.model.service.UserService;

public class MovieUI
{
	private final UserService userService;
	private final MovieService movieService;
	private final ReservationService reservationService;
	private final HallService hallService;

	private JFrame main;
	private MessageDialog dialog;

	private JTextField userIdTf;
	private JTextField userNameTf;
	private JTextField userGradeTf;
	private JButton userInsertBt;
	private JButton userUpdateBt;
	private JButton userDeleteBt;
	private JButton userFindBt;

	private JTextField movieIdTf;
	private JTextField movieNameTf;
	private JTextField airDateTf;
	private JTextField endDateTf;
	private JTextField movieTimeTf;
	private JTextField hallIdTf;
	private JButton movieInsertBt;
	private JButton movieUpdateBt;
	private JButton movieDeleteBt;
	private JButton movieFindBt;

	private JTextField hallHallIdTf;
	private JTextField hallSeatIdTf;
	private JTextField hallSeatCountTf;
	private JTextField hallMovieTimeTf;
	private JTextField hallStartTimeTf;
	private JTextField hallMovieIdTf;
	private JTextField hallTodayShowTf;
	private JButton hallInsertBt;
	private JButton hallUpdateBt;
	private JButton hallDeleteBt;

	private JTextField resIdTf;
	private JTextField resSeatTf;
	private JTextField resUserIdTf;
	private JButton resFindByUserBt;
	private JButton resUpdateBt;
	private JButton resDeleteBt;

	private JTable userTable;
	private DefaultTableModel userModel;
	private final String[] userHeader = { "회원번호", "이름", "등급" };

	private JTable movieTable;
	private DefaultTableModel movieModel;
	private final String[] movieHeader = { "영화번호", "영화명", "상영관", "첫 회차", "상영(분)" };

	private JTable reservationTable;
	private DefaultTableModel reservationModel;
	private final String[] reservationHeader = { "예약번호", "회원", "영화명", "관", "좌석", "상영일시" };

	private User currentUser;

	public MovieUI(UserService userService, MovieService movieService, ReservationService reservationService,
			HallService hallService)
	{
		this.userService = userService;
		this.movieService = movieService;
		this.reservationService = reservationService;
		this.hallService = hallService;
		buildUi();
		showUserList();
		showMovieList();
		clearReservationList();
		updatePrivilegeControls();
	}

	private void buildUi()
	{
		main = new JFrame("영화 예약 시스템");
		dialog = new MessageDialog(main);

		userIdTf = new JTextField();
		userNameTf = new JTextField();
		userGradeTf = new JTextField();
		userInsertBt = new JButton("등록");
		userUpdateBt = new JButton("수정");
		userDeleteBt = new JButton("삭제");
		userFindBt = new JButton("검색");
		resFindByUserBt = new JButton("예약조회");

		movieIdTf = new JTextField();
		movieNameTf = new JTextField();
		airDateTf = new JTextField();
		endDateTf = new JTextField();
		movieTimeTf = new JTextField();
		hallIdTf = new JTextField();
		movieInsertBt = new JButton("등록");
		movieUpdateBt = new JButton("수정");
		movieDeleteBt = new JButton("삭제");
		movieFindBt = new JButton("검색");

		hallHallIdTf = new JTextField();
		hallSeatIdTf = new JTextField();
		hallSeatCountTf = new JTextField();
		hallMovieTimeTf = new JTextField();
		hallStartTimeTf = new JTextField();
		hallMovieIdTf = new JTextField();
		hallTodayShowTf = new JTextField();
		hallInsertBt = new JButton("상영관 등록");
		hallUpdateBt = new JButton("상영관 수정");
		hallDeleteBt = new JButton("상영관 삭제");

		resIdTf = new JTextField();
		resSeatTf = new JTextField();
		resUserIdTf = new JTextField();
		resUpdateBt = new JButton("예약 수정");
		resDeleteBt = new JButton("예약 삭제");

		userModel = new DefaultTableModel(userHeader, 0);
		userTable = new JTable(userModel);

		movieModel = new DefaultTableModel(movieHeader, 0);
		movieTable = new JTable(movieModel);
		movieTable.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() >= 1)
				{
					openReservationDialog();
				}
			}
		});

		ActionListener userHandler = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					Object src = e.getSource();
					if (src == userInsertBt)
					{
						insertUser();
					}
					else if (src == userUpdateBt)
					{
						updateUser();
					}
					else if (src == userDeleteBt)
					{
						deleteUser();
					}
					else if (src == userFindBt)
					{
						findUser();
					}
					else if (src == resFindByUserBt)
					{
						findReservationsByUser();
					}
				}
				catch (NumberFormatException err)
				{
					dialog.show("회원 번호와 등급을 올바르게 입력해 주세요.");
				}
				catch (Exception err)
				{
					dialog.show(err.getMessage());
				}
			}
		};

		ActionListener movieHandler = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					Object src = e.getSource();
					if (src == movieInsertBt)
					{
						insertMovie();
					}
					else if (src == movieUpdateBt)
					{
						updateMovie();
					}
					else if (src == movieDeleteBt)
					{
						deleteMovie();
					}
					else if (src == movieFindBt)
					{
						findMovie();
					}
				}
				catch (NumberFormatException err)
				{
					dialog.show("영화 정보를 숫자 형식에 맞게 입력해 주세요.");
				}
				catch (Exception err)
				{
					dialog.show(err.getMessage());
				}
			}
		};

		ActionListener hallHandler = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					requireAdmin();
					Object src = e.getSource();
					if (src == hallInsertBt)
					{
						insertHall();
					}
					else if (src == hallUpdateBt)
					{
						updateHall();
					}
					else if (src == hallDeleteBt)
					{
						deleteHall();
					}
				}
				catch (NumberFormatException err)
				{
					dialog.show("상영관 정보를 숫자/시간 형식에 맞게 입력해 주세요.");
				}
				catch (Exception err)
				{
					dialog.show(err.getMessage());
				}
			}
		};

		ActionListener resHandler = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					Object src = e.getSource();
					if (src == resUpdateBt)
					{
						updateReservation();
					}
					else if (src == resDeleteBt)
					{
						deleteReservation();
					}
				}
				catch (NumberFormatException err)
				{
					dialog.show("예약 정보를 숫자 형식에 맞게 입력해 주세요.");
				}
				catch (Exception err)
				{
					dialog.show(err.getMessage());
				}
			}
		};

		userInsertBt.addActionListener(userHandler);
		userUpdateBt.addActionListener(userHandler);
		userDeleteBt.addActionListener(userHandler);
		userFindBt.addActionListener(userHandler);
		movieInsertBt.addActionListener(movieHandler);
		movieUpdateBt.addActionListener(movieHandler);
		movieDeleteBt.addActionListener(movieHandler);
		movieFindBt.addActionListener(movieHandler);

		hallInsertBt.addActionListener(hallHandler);
		hallUpdateBt.addActionListener(hallHandler);
		hallDeleteBt.addActionListener(hallHandler);

		resUpdateBt.addActionListener(resHandler);
		resDeleteBt.addActionListener(resHandler);

		reservationModel = new DefaultTableModel(reservationHeader, 0);
		reservationTable = new JTable(reservationModel);

		JPanel left = new JPanel(new BorderLayout(5, 5));
		left.add(buildUserPanel(), BorderLayout.NORTH);
		left.add(buildMoviePanel(), BorderLayout.CENTER);
		left.add(buildHallPanel(), BorderLayout.SOUTH);

		JPanel movieListPan = new JPanel(new BorderLayout(5, 5));
		movieListPan.add(new JLabel("영화 목록 (클릭: 예약)", JLabel.CENTER), BorderLayout.NORTH);
		movieListPan.add(new JScrollPane(movieTable), BorderLayout.CENTER);

		JPanel reservationListPan = new JPanel(new BorderLayout(5, 5));
		reservationListPan.setPreferredSize(new Dimension(0, 260));
		reservationListPan.add(buildReservationPanel(), BorderLayout.NORTH);
		reservationListPan.add(new JScrollPane(reservationTable), BorderLayout.CENTER);

		JPanel right = new JPanel(new BorderLayout(5, 5));
		right.add(movieListPan, BorderLayout.CENTER);
		right.add(reservationListPan, BorderLayout.SOUTH);

		main.setLayout(new GridLayout(1, 2, 8, 8));
		main.add(left);
		main.add(right);
		main.setSize(1200, 760);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setLocationRelativeTo(null);
		main.setVisible(true);
	}

	private JPanel buildUserPanel()
	{
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setPreferredSize(new Dimension(0, 220));
		panel.add(new JLabel("회원 관리", JLabel.CENTER), BorderLayout.NORTH);

		JPanel form = new JPanel(new GridLayout(5, 1, 4, 4));
		form.add(row("회원번호", userIdTf));
		form.add(row("이      름", userNameTf));
		form.add(row("등      급", userGradeTf));

		JPanel buttonPan = new JPanel(new GridLayout(1, 5, 4, 4));
		buttonPan.add(userInsertBt);
		buttonPan.add(userUpdateBt);
		buttonPan.add(userDeleteBt);
		buttonPan.add(userFindBt);
		buttonPan.add(resFindByUserBt);
		form.add(buttonPan);

		JPanel listPan = new JPanel(new BorderLayout(5, 5));
		listPan.add(new JLabel("회원 목록", JLabel.CENTER), BorderLayout.NORTH);
		listPan.add(new JScrollPane(userTable), BorderLayout.CENTER);

		panel.add(form, BorderLayout.NORTH);
		panel.add(listPan, BorderLayout.CENTER);
		return panel;
	}

	private JPanel buildMoviePanel()
	{
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.add(new JLabel("영화 관리 (관리자)", JLabel.CENTER), BorderLayout.NORTH);

		JPanel form = new JPanel(new GridLayout(8, 1, 4, 4));
		form.add(row("영화번호", movieIdTf));
		form.add(row("영화명", movieNameTf));
		form.add(row("상영시작일", airDateTf));
		form.add(row("상영종료일", endDateTf));
		form.add(row("상영(분)", movieTimeTf));
		form.add(row("상영관", hallIdTf));

		JPanel buttonPan = new JPanel(new GridLayout(1, 4, 4, 4));
		buttonPan.add(movieInsertBt);
		buttonPan.add(movieUpdateBt);
		buttonPan.add(movieDeleteBt);
		buttonPan.add(movieFindBt);
		form.add(buttonPan);

		panel.add(form, BorderLayout.CENTER);
		return panel;
	}

	private JPanel buildHallPanel()
	{
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setPreferredSize(new Dimension(0, 200));
		panel.add(new JLabel("상영관 관리 (관리자·회원 검색 후)", JLabel.CENTER), BorderLayout.NORTH);

		JPanel form = new JPanel(new GridLayout(8, 1, 3, 3));
		form.add(row("관 번호", hallHallIdTf));
		form.add(row("좌석구역ID", hallSeatIdTf));
		form.add(row("좌석 수", hallSeatCountTf));
		form.add(row("시작시간", hallStartTimeTf));
		form.add(row("상영(분)", hallMovieTimeTf));
		form.add(row("영화ID", hallMovieIdTf));
		form.add(row("금일상영수", hallTodayShowTf));

		JPanel buttonPan = new JPanel(new GridLayout(1, 3, 4, 4));
		buttonPan.add(hallInsertBt);
		buttonPan.add(hallUpdateBt);
		buttonPan.add(hallDeleteBt);
		form.add(buttonPan);

		panel.add(form, BorderLayout.CENTER);
		return panel;
	}

	private JPanel buildReservationPanel()
	{
		JPanel panel = new JPanel(new GridLayout(3, 1, 4, 4));
		panel.add(new JLabel("예약 목록 (회원번호만 입력 후 「예약조회」)", JLabel.CENTER));
		panel.add(row("예약번호 / 좌석 (수정·삭제)", combineFields(resIdTf, resSeatTf)));
		JPanel btnPan = new JPanel(new GridLayout(1, 2, 4, 4));
		btnPan.add(resUpdateBt);
		btnPan.add(resDeleteBt);
		panel.add(btnPan);
		return panel;
	}

	private JPanel combineFields(JTextField... fields)
	{
		JPanel pan = new JPanel(new GridLayout(1, fields.length, 4, 4));
		for (JTextField field : fields)
		{
			pan.add(field);
		}
		return pan;
	}

	private JPanel row(String label, java.awt.Component field)
	{
		JPanel pan = new JPanel(new GridLayout(1, 2, 4, 4));
		pan.add(new JLabel(label));
		pan.add(field);
		return pan;
	}

	private void insertUser()
	{
		User user = readUserFromForm();
		userService.add(user);
		showUserList();
		dialog.show("회원이 등록되었습니다.");
		clearUserForm();
	}

	private void updateUser()
	{
		User user = readUserFromForm();
		if (currentUser != null && !currentUser.isAdmin())
		{
			User existing = userService.search(user.getId());
			user = new User(user.getId(), user.getName(), existing.getGrade());
		}
		userService.update(user);
		showUserList();
		dialog.show("회원 정보가 수정되었습니다.");
		if (currentUser != null && currentUser.getId() == user.getId())
		{
			currentUser = user;
			updatePrivilegeControls();
		}
		clearUserForm();
	}

	private void deleteUser()
	{
		int id = Integer.parseInt(userIdTf.getText().trim());
		userService.remove(id);
		showUserList();
		dialog.show("회원이 삭제되었습니다.");
		if (currentUser != null && currentUser.getId() == id)
		{
			currentUser = null;
			updatePrivilegeControls();
		}
		clearUserForm();
	}

	private void findUser()
	{
		int id = resolveLookupUserId();
		currentUser = userService.search(id);
		userNameTf.setText(currentUser.getName());
		userGradeTf.setText(currentUser.getGradeLabel());
		resUserIdTf.setText(String.valueOf(id));
		updatePrivilegeControls();
		findReservationsByUserId(id, false);
		dialog.show("회원 검색 완료: " + currentUser.getName() + (currentUser.isAdmin() ? " (관리자)" : " (일반)"));
	}

	private int resolveLookupUserId()
	{
		String idText = userIdTf.getText().trim();
		if (idText.isEmpty())
		{
			idText = resUserIdTf.getText().trim();
		}
		if (idText.isEmpty())
		{
			throw new NumberFormatException("회원 번호");
		}
		return Integer.parseInt(idText);
	}

	private void findReservationsByUser()
	{
		findReservationsByUserId(resolveLookupUserId(), true);
	}

	private void findReservationsByUserId(int userId, boolean showDialog)
	{
		User user = userService.search(userId);
		currentUser = user;
		resUserIdTf.setText(String.valueOf(userId));
		userNameTf.setText(user.getName());
		userGradeTf.setText(user.getGradeLabel());
		updatePrivilegeControls();
		showReservationList(userId);
		if (showDialog)
		{
			List<Reservation> list = reservationService.searchByUserId(userId);
			dialog.show(String.format("회원 %d (%s) 예약 %d건", userId, user.getName(), list.size()));
		}
	}

	private User readUserFromForm()
	{
		int id = Integer.parseInt(userIdTf.getText().trim());
		String name = userNameTf.getText().trim();
		char grade = parseGrade(userGradeTf.getText().trim());
		return new User(id, name, grade);
	}

	private char parseGrade(String gradeText)
	{
		if (gradeText.equalsIgnoreCase("admin"))
		{
			return 'a';
		}
		return gradeText.isEmpty() ? 'U' : gradeText.charAt(0);
	}

	private void insertMovie()
	{
		requireAdmin();
		movieService.add(readMovieFromForm());
		showMovieList();
		clearMovieForm();
		dialog.show("영화가 등록되었습니다.");
	}

	private void updateMovie()
	{
		requireAdmin();
		movieService.update(readMovieFromForm());
		showMovieList();
		clearMovieForm();
		dialog.show("영화 정보가 수정되었습니다.");
	}

	private void deleteMovie()
	{
		requireAdmin();
		int movieId = Integer.parseInt(movieIdTf.getText().trim());
		movieService.remove(movieId);
		showMovieList();
		clearMovieForm();
		dialog.show("영화가 삭제되었습니다.");
	}

	private void findMovie()
	{
		int movieId = Integer.parseInt(movieIdTf.getText().trim());
		Movie movie = movieService.search(movieId);
		fillMovieForm(movie);
	}

	private Movie readMovieFromForm()
	{
		int movieId = Integer.parseInt(movieIdTf.getText().trim());
		String movieName = movieNameTf.getText().trim();
		Date airDate = Date.valueOf(airDateTf.getText().trim());
		Date endDate = endDateTf.getText().trim().isEmpty() ? airDate : Date.valueOf(endDateTf.getText().trim());
		int movieTime = movieTimeTf.getText().trim().isEmpty() ? 120 : Integer.parseInt(movieTimeTf.getText().trim());
		int hallId = Integer.parseInt(hallIdTf.getText().trim());
		return new Movie(movieId, airDate, endDate, movieTime, movieName, hallId, null);
	}

	private void fillMovieForm(Movie movie)
	{
		movieIdTf.setText(String.valueOf(movie.getMovie_id()));
		movieNameTf.setText(movie.getMovie_name());
		airDateTf.setText(movie.getAir_date().toString());
		endDateTf
				.setText(movie.getEnd_date() != null ? movie.getEnd_date().toString() : movie.getAir_date().toString());
		movieTimeTf.setText(String.valueOf(movie.getMovie_time() > 0 ? movie.getMovie_time() : 120));
		hallIdTf.setText(String.valueOf(movie.getHall_id()));
	}

	private Hall readHallFromForm()
	{
		int hallId = Integer.parseInt(hallHallIdTf.getText().trim());
		int seatId = Integer.parseInt(hallSeatIdTf.getText().trim());
		int seatCount = Integer.parseInt(hallSeatCountTf.getText().trim());
		int movieTime = Integer.parseInt(hallMovieTimeTf.getText().trim());
		Time startTime = Time.valueOf(hallStartTimeTf.getText().trim());
		Integer movieId = hallMovieIdTf.getText().trim().isEmpty() ? null
				: Integer.valueOf(hallMovieIdTf.getText().trim());
		int todayShow = hallTodayShowTf.getText().trim().isEmpty() ? 0
				: Integer.parseInt(hallTodayShowTf.getText().trim());
		return new Hall(hallId, seatId, seatCount, movieTime, startTime, movieId, todayShow);
	}

	private void insertHall()
	{
		hallService.add(readHallFromForm());
		dialog.show("상영관이 등록되었습니다.");
		clearHallForm();
	}

	private void updateHall()
	{
		hallService.update(readHallFromForm());
		dialog.show("상영관 정보가 수정되었습니다.");
		clearHallForm();
	}

	private void deleteHall()
	{
		int hallId = Integer.parseInt(hallHallIdTf.getText().trim());
		int seatId = Integer.parseInt(hallSeatIdTf.getText().trim());
		hallService.remove(hallId, seatId);
		dialog.show("상영관 정보가 삭제되었습니다.");
		clearHallForm();
	}

	private void updateReservation()
	{
		int resId = Integer.parseInt(resIdTf.getText().trim());
		int seatId = Integer.parseInt(resSeatTf.getText().trim());
		int userId = resolveLookupUserId();
		Reservation existing = reservationService.search(resId);
		Reservation updated = new Reservation(resId, seatId, existing.getMovie_id(), userId, existing.getHall_id(),
				existing.getStart_datetime());
		reservationService.update(updated);
		showReservationList(userId);
		dialog.show("예약이 수정되었습니다.");
	}

	private void deleteReservation()
	{
		int resId = Integer.parseInt(resIdTf.getText().trim());
		int userId = currentUser != null ? currentUser.getId() : resolveLookupUserId();
		reservationService.remove(resId);
		showReservationList(userId);
		dialog.show("예약이 삭제되었습니다.");
	}

	private void requireAdmin()
	{
		if (currentUser == null || !currentUser.isAdmin())
		{
			throw new IllegalStateException("관리자만 수행할 수 있습니다. 먼저 관리자(9999)로 회원 검색하세요.");
		}
	}

	private void updatePrivilegeControls()
	{
		boolean admin = currentUser != null && currentUser.isAdmin();
		movieInsertBt.setEnabled(admin);
		movieUpdateBt.setEnabled(admin);
		movieDeleteBt.setEnabled(admin);
		movieFindBt.setEnabled(true);

		hallInsertBt.setEnabled(admin);
		hallUpdateBt.setEnabled(admin);
		hallDeleteBt.setEnabled(admin);

		userGradeTf.setEnabled(admin);
	}

	private void showUserList()
	{
		List<User> list = userService.searchAll();
		String[][] data = new String[list.size()][3];
		for (int i = 0; i < list.size(); i++)
		{
			User user = list.get(i);
			data[i][0] = String.valueOf(user.getId());
			data[i][1] = user.getName();
			data[i][2] = user.getGradeLabel();
		}
		userModel.setDataVector(data, userHeader);
	}

	private void showMovieList()
	{
		List<Movie> list = movieService.searchAll();
		String[][] data = new String[list.size()][5];
		for (int i = 0; i < list.size(); i++)
		{
			Movie mov = list.get(i);
			data[i][0] = String.valueOf(mov.getMovie_id());
			data[i][1] = mov.getMovie_name();
			data[i][2] = String.valueOf(mov.getHall_id()) + "관";
			data[i][3] = mov.getStart_time() != null ? mov.getStart_time().toString() : "-";
			data[i][4] = String.valueOf(mov.getMovie_time() > 0 ? mov.getMovie_time() : 120);
		}
		movieModel.setDataVector(data, movieHeader);
	}

	private void clearReservationList()
	{
		reservationModel.setDataVector(new String[0][0], reservationHeader);
	}

	private void showReservationList(int userId)
	{
		List<Reservation> list = reservationService.searchByUserId(userId);
		String[][] data = new String[list.size()][6];
		for (int i = 0; i < list.size(); i++)
		{
			Reservation res = list.get(i);
			data[i][0] = String.valueOf(res.getReservation_id());
			data[i][1] = String.valueOf(res.getUser_id());
			data[i][2] = resolveMovieName(res.getMovie_id());
			data[i][3] = String.valueOf(res.getHall_id()) + "관";
			data[i][4] = String.valueOf(res.getSeat_id());
			data[i][5] = res.getStart_datetime() != null ? res.getStart_datetime().toString() : "-";
		}
		reservationModel.setDataVector(data, reservationHeader);
	}

	private String resolveMovieName(int movieId)
	{
		try
		{
			return movieService.search(movieId).getMovie_name();
		}
		catch (Exception e)
		{
			return "-";
		}
	}

	private void openReservationDialog()
	{
		if (currentUser == null)
		{
			try
			{
				findReservationsByUserId(resolveLookupUserId(), false);
			}
			catch (NumberFormatException e)
			{
				dialog.show("예약하려면 회원 번호를 입력한 뒤 「검색」 또는 「예약조회」를 눌러 주세요.");
				return;
			}
		}
		int row = movieTable.getSelectedRow();
		if (row < 0)
		{
			return;
		}
		int movieId = Integer.parseInt((String) movieModel.getValueAt(row, 0));
		Movie movie = movieService.search(movieId);
		List<Hall> halls = hallService.searchByMovieAndHall(movie.getMovie_id(), movie.getHall_id());
		new ReservationDialog(main, dialog, reservationService, movie, currentUser, halls, new Runnable()
		{
			@Override
			public void run()
			{
				showMovieList();
				showReservationList(currentUser.getId());
			}
		}).setVisible(true);
	}

	private void clearUserForm()
	{
		userIdTf.setText("");
		userNameTf.setText("");
		userGradeTf.setText("");
	}

	private void clearMovieForm()
	{
		movieIdTf.setText("");
		movieNameTf.setText("");
		airDateTf.setText("");
		endDateTf.setText("");
		movieTimeTf.setText("");
		hallIdTf.setText("");
	}

	private void clearHallForm()
	{
		hallHallIdTf.setText("");
		hallSeatIdTf.setText("");
		hallSeatCountTf.setText("");
		hallMovieTimeTf.setText("");
		hallStartTimeTf.setText("");
		hallMovieIdTf.setText("");
		hallTodayShowTf.setText("");
	}
}
