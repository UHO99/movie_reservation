package com.ureca.movie.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ureca.user.model.dto.Movie;
import com.ureca.user.util.DBUtil;

public class MovieDaoImp implements MovieDao
{
	private DBUtil dbutil = DBUtil.getInstance();

	@Override
	public void add(Movie mov) throws SQLException
	{
		Connection con = null;
		PreparedStatement stmt = null;

		try
		{
			con = dbutil.getConnection();
			String sql = "INSERT INTO movie(movie_id, movie_name, air_date, end_date, movie_time, hall_id) "
					+ "VALUES(?, ?, ?, ?, ?, ?)";
			stmt = con.prepareStatement(sql);

			int idx = 1;
			stmt.setInt(idx++, mov.getMovie_id());
			stmt.setString(idx++, mov.getMovie_name());
			stmt.setDate(idx++, mov.getAir_date());
			stmt.setDate(idx++, mov.getEnd_date() != null ? mov.getEnd_date() : mov.getAir_date());
			stmt.setInt(idx++, mov.getMovie_time() > 0 ? mov.getMovie_time() : 120);
			stmt.setInt(idx, mov.getHall_id());

			stmt.executeUpdate();
		}
		finally
		{
			dbutil.close(stmt, con);
		}
	}

	public void update(Movie mov) throws SQLException
	{
		Connection con = null;
		PreparedStatement stmt = null;

		try
		{
			con = dbutil.getConnection();
			String sql = "UPDATE movie SET movie_name=?, air_date=?, end_date=?, movie_time=?, hall_id=? WHERE movie_id=?";
			stmt = con.prepareStatement(sql);

			int idx = 1;
			stmt.setString(idx++, mov.getMovie_name());
			stmt.setDate(idx++, mov.getAir_date());
			stmt.setDate(idx++, mov.getEnd_date() != null ? mov.getEnd_date() : mov.getAir_date());
			stmt.setInt(idx++, mov.getMovie_time() > 0 ? mov.getMovie_time() : 120);
			stmt.setInt(idx++, mov.getHall_id());
			stmt.setInt(idx, mov.getMovie_id());

			stmt.executeUpdate();
		}
		finally
		{
			dbutil.close(stmt, con);
		}
	}

	public void remove(int id) throws SQLException
	{
		Connection con = null;
		PreparedStatement stmt = null;

		try
		{
			con = dbutil.getConnection();
			con.setAutoCommit(false); // 트랜잭션 시작

			// 1. hall 테이블에서 이 movie를 참조하는 movie_id를 NULL로
			stmt = con.prepareStatement("UPDATE hall SET movie_id = NULL WHERE movie_id = ?");
			stmt.setInt(1, id);
			stmt.executeUpdate();
			stmt.close();

			// 2. movie 행 삭제
			stmt = con.prepareStatement("DELETE FROM movie WHERE movie_id = ?");
			stmt.setInt(1, id);
			stmt.executeUpdate();

			con.commit();
		}
		catch (SQLException e)
		{
			if (con != null) con.rollback();
			throw e;
		}
		finally
		{
			if (con != null) con.setAutoCommit(true);
			dbutil.close(stmt, con);
		}
	}

	@Override
	public void close() throws SQLException
	{
	}

	@Override
	public Movie search(int id) throws SQLException
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try
		{
			con = dbutil.getConnection();
			String sql = "SELECT m.movie_id, m.movie_name, m.air_date, m.end_date, m.movie_time, m.hall_id, "
					+ "MIN(h.start_time) AS start_time FROM movie m "
					+ "LEFT JOIN hall h ON m.hall_id = h.hall_id AND h.movie_id = m.movie_id " + "WHERE m.movie_id=? "
					+ "GROUP BY m.movie_id, m.movie_name, m.air_date, m.end_date, m.movie_time, m.hall_id";
			stmt = con.prepareStatement(sql);

			stmt.setInt(1, id);

			rs = stmt.executeQuery();

			if (rs.next())
			{
				return mapRow(rs);
			}
		}
		finally
		{
			dbutil.close(rs, stmt, con);
		}

		return null;
	}

	@Override
	public List<Movie> searchAll() throws SQLException
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Movie> movs = new ArrayList<>(20);

		try
		{
			con = dbutil.getConnection();
			String sql = "SELECT m.movie_id, m.movie_name, m.air_date, m.end_date, m.movie_time, m.hall_id, "
					+ "MIN(h.start_time) AS start_time FROM movie m "
					+ "LEFT JOIN hall h ON m.hall_id = h.hall_id AND h.movie_id = m.movie_id "
					+ "GROUP BY m.movie_id, m.movie_name, m.air_date, m.end_date, m.movie_time, m.hall_id "
					+ "ORDER BY m.movie_id";
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next())
			{
				movs.add(mapRow(rs));
			}
		}
		finally
		{
			dbutil.close(rs, stmt, con);
		}

		return movs;
	}

	private Movie mapRow(ResultSet rs) throws SQLException
	{
		Movie mov = new Movie();
		mov.setMovie_id(rs.getInt("movie_id"));
		mov.setMovie_name(rs.getString("movie_name"));
		mov.setAir_date(rs.getDate("air_date"));
		try
		{
			mov.setEnd_date(rs.getDate("end_date"));
		}
		catch (SQLException ignored)
		{
			mov.setEnd_date(rs.getDate("air_date"));
		}
		try
		{
			mov.setMovie_time(rs.getInt("movie_time"));
		}
		catch (SQLException ignored)
		{
			mov.setMovie_time(120);
		}
		mov.setHall_id(rs.getInt("hall_id"));
		try
		{
			mov.setStart_time(rs.getTime("start_time"));
		}
		catch (SQLException ignored)
		{
		}
		return mov;
	}
}
