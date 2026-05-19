package com.ureca.movie.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ureca.user.model.dto.Movie;
import com.ureca.user.model.dto.User;
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
			String sql = "INSERT INTO movie(movie_id, air_date, movie_name, hall_id) VALUES(?, ?, ?, ?)";
			stmt = con.prepareStatement(sql);

			int idx = 1;
			stmt.setInt(idx++, mov.getMovie_id());
			stmt.setDate(idx++, mov.getAir_date());
			stmt.setString(idx++, mov.getMovie_name());
			stmt.setInt(idx++, mov.getHall_id());

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
			String sql = "UPDATE movie SET movie_name=?, air_date=? WHERE movie_id=?";
			stmt = con.prepareStatement(sql);

			int idx = 1;
			stmt.setInt(idx++, mov.getMovie_id());
			stmt.setString(idx++, mov.getMovie_name());
			stmt.setDate(idx++, mov.getAir_date());

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
			String sql = "DELETE FROM movie WHERE movie_id=?";
			stmt = con.prepareStatement(sql);

			stmt.setInt(1, id);

			stmt.executeUpdate();
		}
		finally
		{
			dbutil.close(stmt, con);
		}

	}

	@Override
	public void close() throws SQLException
	{
		// TODO Auto-generated method stub

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
			String sql = "SELECT movie_id, movie_name, air_date, hall_id FROM movie WHERE movie_id=?";
			stmt = con.prepareStatement(sql);

			stmt.setInt(1, id);

			rs = stmt.executeQuery();

			if (rs.next())
			{
				Movie mov = new Movie();
				mov.setMovie_id(rs.getInt("movie_id"));
				mov.setMovie_name(rs.getString("movie_name"));
				mov.setAir_date(rs.getDate("air_date"));
				mov.setHall_id(rs.getInt("hall_id"));

				return mov;
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
			String sql = "SELECT movie_id, movie_name, air_date, hall_id FROM movie";
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next())
			{
				Movie mov = new Movie();
				mov.setMovie_id(rs.getInt("movie_id"));
				mov.setMovie_name(rs.getString("movie_name"));
				mov.setAir_date(rs.getDate("air_date"));
				mov.setHall_id(rs.getInt("hall_id"));

				movs.add(mov);
			}
		}
		finally
		{
			dbutil.close(rs, stmt, con);
		}

		return movs;
	}
}
