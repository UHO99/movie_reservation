package com.ureca.user.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ureca.user.model.dto.User;
import com.ureca.user.util.DBUtil;

public class UserDaoImp implements UserDao
{
	private DBUtil dbutil = DBUtil.getInstance();

	@Override
	public void add(User usr) throws SQLException
	{
		Connection con = null;
		PreparedStatement stmt = null;

		try
		{
			con = dbutil.getConnection();
			String sql = "INSERT INTO user(user_id, user_name, grade) VALUES(?, ?, ?)";
			stmt = con.prepareStatement(sql);

			int idx = 1;
			stmt.setInt(idx++, usr.getId());
			stmt.setString(idx++, usr.getName());
			stmt.setString(idx++, usr.isAdmin() ? "admin" : String.valueOf(usr.getGrade()));

			stmt.executeUpdate();
		}
		finally
		{
			dbutil.close(stmt, con);
		}
	}

	public void update(User usr) throws SQLException
	{
		Connection con = null;
		PreparedStatement stmt = null;

		try
		{
			con = dbutil.getConnection();
			String sql = "UPDATE user SET user_name=?, grade=? WHERE user_id=?";
			stmt = con.prepareStatement(sql);

			int idx = 1;
			stmt.setString(idx++, usr.getName());
			stmt.setString(idx++, usr.isAdmin() ? "admin" : String.valueOf(usr.getGrade()));
			stmt.setInt(idx++, usr.getId());

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
			String sql = "DELETE FROM user WHERE user_id=?";
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
	}

	@Override
	public User search(int id) throws SQLException
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try
		{
			con = dbutil.getConnection();
			String sql = "SELECT user_id, user_name, grade FROM user WHERE user_id=?";
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
	public List<User> searchAll() throws SQLException
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<User> users = new ArrayList<>(20);

		try
		{
			con = dbutil.getConnection();
			String sql = "SELECT user_id, user_name, grade FROM user";
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next())
			{
				users.add(mapRow(rs));
			}
		}
		finally
		{
			dbutil.close(rs, stmt, con);
		}

		return users;
	}

	private User mapRow(ResultSet rs) throws SQLException
	{
		User usr = new User();
		usr.setId(rs.getInt("user_id"));
		usr.setName(rs.getString("user_name"));
		applyGrade(usr, rs.getString("grade"));
		return usr;
	}

	private void applyGrade(User usr, String grade)
	{
		if (grade == null || grade.isEmpty())
		{
			usr.setGrade('U');
		}
		else if ("admin".equalsIgnoreCase(grade))
		{
			usr.setGrade('a');
		}
		else
		{
			usr.setGrade(grade.charAt(0));
		}
	}
}
