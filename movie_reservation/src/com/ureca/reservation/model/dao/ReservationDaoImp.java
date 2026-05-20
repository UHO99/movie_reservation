package com.ureca.reservation.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ureca.user.model.dto.Reservation;
import com.ureca.user.util.DBUtil;

public class ReservationDaoImp implements ReservationDao
{
	private final DBUtil dbutil = DBUtil.getInstance();

	private static final String SELECT_COLS = "reservation_id, seat_id, user_id, movie_id, hall_id, start_datetime";

	@Override
	public void add(Reservation res)
	{
		Connection con = null;
		PreparedStatement stmt = null;
		try
		{
			con = dbutil.getConnection();
			String sql = "INSERT INTO reservation(reservation_id, seat_id, user_id, movie_id, hall_id, start_datetime) "
					+ "VALUES(?, ?, ?, ?, ?, ?)";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, res.getReservation_id());
			stmt.setInt(2, res.getSeat_id());
			stmt.setInt(3, res.getUser_id());
			stmt.setInt(4, res.getMovie_id());
			stmt.setInt(5, res.getHall_id());
			stmt.setTimestamp(6, res.getStart_datetime());
			stmt.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new RuntimeException("예약 등록 중 오류 발생", e);
		}
		finally
		{
			dbutil.close(stmt, con);
		}
	}

	@Override
	public void update(Reservation res)
	{
		Connection con = null;
		PreparedStatement stmt = null;
		try
		{
			con = dbutil.getConnection();
			String sql = "UPDATE reservation SET seat_id=?, user_id=?, movie_id=?, hall_id=?, start_datetime=? "
					+ "WHERE reservation_id=?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, res.getSeat_id());
			stmt.setInt(2, res.getUser_id());
			stmt.setInt(3, res.getMovie_id());
			stmt.setInt(4, res.getHall_id());
			stmt.setTimestamp(5, res.getStart_datetime());
			stmt.setInt(6, res.getReservation_id());
			stmt.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new RuntimeException("예약 수정 중 오류 발생", e);
		}
		finally
		{
			dbutil.close(stmt, con);
		}
	}

	@Override
	public void remove(int reservationId)
	{
		Connection con = null;
		PreparedStatement stmt = null;
		try
		{
			con = dbutil.getConnection();
			stmt = con.prepareStatement("DELETE FROM reservation WHERE reservation_id=?");
			stmt.setInt(1, reservationId);
			stmt.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new RuntimeException("예약 삭제 중 오류 발생", e);
		}
		finally
		{
			dbutil.close(stmt, con);
		}
	}

	@Override
	public Reservation search(int reservationId)
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			con = dbutil.getConnection();
			stmt = con.prepareStatement("SELECT " + SELECT_COLS + " FROM reservation WHERE reservation_id=?");
			stmt.setInt(1, reservationId);
			rs = stmt.executeQuery();
			if (rs.next())
			{
				return mapRow(rs);
			}
			return null;
		}
		catch (SQLException e)
		{
			throw new RuntimeException("예약 조회 중 오류 발생", e);
		}
		finally
		{
			dbutil.close(rs, stmt, con);
		}
	}

	@Override
	public List<Reservation> searchAll()
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Reservation> list = new ArrayList<>();
		try
		{
			con = dbutil.getConnection();
			rs = con.prepareStatement("SELECT " + SELECT_COLS + " FROM reservation ORDER BY reservation_id")
					.executeQuery();
			while (rs.next())
			{
				list.add(mapRow(rs));
			}
			return list;
		}
		catch (SQLException e)
		{
			throw new RuntimeException("예약 전체 조회 중 오류 발생", e);
		}
		finally
		{
			dbutil.close(rs, stmt, con);
		}
	}

	@Override
	public List<Reservation> searchByMovie(int movieId)
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Reservation> list = new ArrayList<>();
		try
		{
			con = dbutil.getConnection();
			stmt = con.prepareStatement("SELECT " + SELECT_COLS + " FROM reservation WHERE movie_id=?");
			stmt.setInt(1, movieId);
			rs = stmt.executeQuery();
			while (rs.next())
			{
				list.add(mapRow(rs));
			}
			return list;
		}
		catch (SQLException e)
		{
			throw new RuntimeException("영화별 예약 조회 중 오류 발생", e);
		}
		finally
		{
			dbutil.close(rs, stmt, con);
		}
	}

	@Override
	public List<Reservation> searchByUserId(int userId)
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Reservation> list = new ArrayList<>();
		try
		{
			con = dbutil.getConnection();
			stmt = con.prepareStatement(
					"SELECT " + SELECT_COLS + " FROM reservation WHERE user_id=? ORDER BY reservation_id");
			stmt.setInt(1, userId);
			rs = stmt.executeQuery();
			while (rs.next())
			{
				list.add(mapRow(rs));
			}
			return list;
		}
		catch (SQLException e)
		{
			throw new RuntimeException("회원별 예약 조회 중 오류 발생", e);
		}
		finally
		{
			dbutil.close(rs, stmt, con);
		}
	}

	@Override
	public boolean isSeatTaken(int movieId, int seatId)
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			con = dbutil.getConnection();
			stmt = con.prepareStatement("SELECT 1 FROM reservation WHERE movie_id=? AND seat_id=? LIMIT 1");
			stmt.setInt(1, movieId);
			stmt.setInt(2, seatId);
			rs = stmt.executeQuery();
			return rs.next();
		}
		catch (SQLException e)
		{
			// 구 스키마 sit_id 폴백
			try
			{
				dbutil.close(rs, stmt, con);
				con = dbutil.getConnection();
				stmt = con.prepareStatement("SELECT 1 FROM reservation WHERE movie_id=? AND sit_id=? LIMIT 1");
				stmt.setInt(1, movieId);
				stmt.setInt(2, seatId);
				rs = stmt.executeQuery();
				return rs.next();
			}
			catch (SQLException e2)
			{
				throw new RuntimeException("좌석 조회 중 오류 발생", e2);
			}
		}
		finally
		{
			dbutil.close(rs, stmt, con);
		}
	}

	@Override
	public boolean isSeatTaken(int movieId, int hallId, java.sql.Timestamp startDatetime, int seatId)
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			con = dbutil.getConnection();
			stmt = con.prepareStatement(
					"SELECT 1 FROM reservation WHERE movie_id=? AND hall_id=? AND start_datetime=? AND seat_id=? LIMIT 1");
			stmt.setInt(1, movieId);
			stmt.setInt(2, hallId);
			stmt.setTimestamp(3, startDatetime);
			stmt.setInt(4, seatId);
			rs = stmt.executeQuery();
			return rs.next();
		}
		catch (SQLException e)
		{
			return isSeatTaken(movieId, seatId);
		}
		finally
		{
			dbutil.close(rs, stmt, con);
		}
	}

	@Override
	public int nextReservationId()
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			con = dbutil.getConnection();
			rs = con.prepareStatement("SELECT COALESCE(MAX(reservation_id), 0) + 1 FROM reservation").executeQuery();

			if (rs.next())
			{
				return rs.getInt(1);
			}
			return 1;
		}
		catch (SQLException e)
		{
			throw new RuntimeException("예약번호 생성 중 오류 발생", e);
		}
		finally
		{
			dbutil.close(rs, stmt, con);
		}
	}

	private Reservation mapRow(ResultSet rs) throws SQLException
	{
		int hallId = 0;
		java.sql.Timestamp start = null;

		try
		{
			hallId = rs.getInt("hall_id");
		}
		catch (SQLException ignored)
		{
		}
		try
		{
			start = rs.getTimestamp("start_datetime");
		}
		catch (SQLException ignored)
		{
		}
		int seatId;
		try
		{
			seatId = rs.getInt("seat_id");
		}
		catch (SQLException e)
		{
			seatId = rs.getInt("sit_id");
		}
		return new Reservation(rs.getInt("reservation_id"), seatId, rs.getInt("movie_id"), rs.getInt("user_id"), hallId,
				start);
	}
}
