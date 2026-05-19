package com.ureca.hall.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ureca.user.model.dto.Hall;
import com.ureca.user.util.DBUtil;

public class HallDaoImp implements HallDao
{
	private final DBUtil dbutil = DBUtil.getInstance();

	@Override
	public void add(Hall hall) throws SQLException
	{
		Connection con = null;
		PreparedStatement stmt = null;
		try
		{
			con = dbutil.getConnection();
			String sql = "INSERT INTO hall(hall_id, seat_id, seat_count, movie_time, start_time, movie_id, today_show_cnt) "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?)";
			stmt = con.prepareStatement(sql);
			int idx = 1;
			stmt.setInt(idx++, hall.getHallId());
			stmt.setInt(idx++, hall.getSeatId());
			stmt.setInt(idx++, hall.getSeatCount());
			stmt.setInt(idx++, hall.getMovieTime());
			stmt.setTime(idx++, hall.getStartTime());
			if (hall.getMovieId() == null)
			{
				stmt.setNull(idx++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setInt(idx++, hall.getMovieId());
			}
			stmt.setInt(idx, hall.getTodayShowCnt());
			stmt.executeUpdate();
		}
		finally
		{
			dbutil.close(stmt, con);
		}
	}

	@Override
	public void update(Hall hall) throws SQLException
	{
		Connection con = null;
		PreparedStatement stmt = null;
		try
		{
			con = dbutil.getConnection();
			String sql = "UPDATE hall SET seat_count=?, movie_time=?, start_time=?, movie_id=?, today_show_cnt=? "
					+ "WHERE hall_id=? AND seat_id=?";
			stmt = con.prepareStatement(sql);
			int idx = 1;
			stmt.setInt(idx++, hall.getSeatCount());
			stmt.setInt(idx++, hall.getMovieTime());
			stmt.setTime(idx++, hall.getStartTime());
			if (hall.getMovieId() == null)
			{
				stmt.setNull(idx++, java.sql.Types.INTEGER);
			}
			else
			{
				stmt.setInt(idx++, hall.getMovieId());
			}
			stmt.setInt(idx++, hall.getTodayShowCnt());
			stmt.setInt(idx++, hall.getHallId());
			stmt.setInt(idx, hall.getSeatId());
			stmt.executeUpdate();
		}
		finally
		{
			dbutil.close(stmt, con);
		}
	}

	@Override
	public void remove(int hallId, int seatId) throws SQLException
	{
		Connection con = null;
		PreparedStatement stmt = null;
		try
		{
			con = dbutil.getConnection();
			stmt = con.prepareStatement("DELETE FROM hall WHERE hall_id=? AND seat_id=?");
			stmt.setInt(1, hallId);
			stmt.setInt(2, seatId);
			stmt.executeUpdate();
		}
		finally
		{
			dbutil.close(stmt, con);
		}
	}

	@Override
	public Hall search(int hallId, int seatId) throws SQLException
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			con = dbutil.getConnection();
			stmt = con.prepareStatement(
					"SELECT hall_id, seat_id, seat_count, movie_time, start_time, movie_id, today_show_cnt "
							+ "FROM hall WHERE hall_id=? AND seat_id=?");
			stmt.setInt(1, hallId);
			stmt.setInt(2, seatId);
			rs = stmt.executeQuery();
			if (rs.next())
			{
				return mapRow(rs);
			}
			return null;
		}
		finally
		{
			dbutil.close(rs, stmt, con);
		}
	}

	@Override
	public List<Hall> searchByHallId(int hallId) throws SQLException
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Hall> list = new ArrayList<>();
		try
		{
			con = dbutil.getConnection();
			stmt = con.prepareStatement(
					"SELECT hall_id, seat_id, seat_count, movie_time, start_time, movie_id, today_show_cnt "
							+ "FROM hall WHERE hall_id=? ORDER BY seat_id");
			stmt.setInt(1, hallId);
			rs = stmt.executeQuery();
			while (rs.next())
			{
				list.add(mapRow(rs));
			}
			return list;
		}
		finally
		{
			dbutil.close(rs, stmt, con);
		}
	}

	@Override
	public List<Hall> searchByMovieAndHall(int movieId, int hallId) throws SQLException
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Hall> list = new ArrayList<>();
		try
		{
			con = dbutil.getConnection();
			stmt = con.prepareStatement(
					"SELECT hall_id, seat_id, seat_count, movie_time, start_time, movie_id, today_show_cnt "
							+ "FROM hall WHERE hall_id=? AND movie_id=? ORDER BY start_time, seat_id");
			stmt.setInt(1, hallId);
			stmt.setInt(2, movieId);
			rs = stmt.executeQuery();
			while (rs.next())
			{
				list.add(mapRow(rs));
			}
			return list;
		}
		finally
		{
			dbutil.close(rs, stmt, con);
		}
	}

	@Override
	public List<Hall> searchAll() throws SQLException
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Hall> list = new ArrayList<>();
		try
		{
			con = dbutil.getConnection();
			rs = con.prepareStatement(
					"SELECT hall_id, seat_id, seat_count, movie_time, start_time, movie_id, today_show_cnt "
							+ "FROM hall ORDER BY hall_id, seat_id")
					.executeQuery();
			while (rs.next())
			{
				list.add(mapRow(rs));
			}
			return list;
		}
		finally
		{
			dbutil.close(rs, stmt, con);
		}
	}

	private Hall mapRow(ResultSet rs) throws SQLException
	{
		Object movieObj = rs.getObject("movie_id");
		Integer movieId = movieObj == null ? null : rs.getInt("movie_id");
		return new Hall(rs.getInt("hall_id"), rs.getInt("seat_id"), rs.getInt("seat_count"), rs.getInt("movie_time"),
				rs.getTime("start_time"), movieId, rs.getInt("today_show_cnt"));
	}
}
