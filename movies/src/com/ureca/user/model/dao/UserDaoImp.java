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
	//////////////////////// TODO 01. DBUtil 객체 생성하기  
	private DBUtil dbutil = DBUtil.getInstance();

	@Override
	public void add(User usr) throws SQLException
	{
		Connection con = null;
		PreparedStatement stmt = null;

		try
		{
			con = dbutil.getConnection();
			String sql = "INSERT INTO user(id, name, grade) VALUES(?, ?, ?)";
			stmt = con.prepareStatement(sql);

			int idx = 1;
			stmt.setInt(idx++, usr.getId());
			stmt.setString(idx++, usr.getName());
			stmt.setObject(idx++, usr.getGrade());

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
			String sql = "UPDATE user SET name=?, grade=? WHERE id=?";
			stmt = con.prepareStatement(sql);

			int idx = 1;
			stmt.setInt(idx++, usr.getId());
			stmt.setString(idx++, usr.getName());
			stmt.setObject(idx++, usr.getGrade());

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
			String sql = "DELETE FROM user WHERE id=?";
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
	public User search(int id) throws SQLException
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try
		{
			con = dbutil.getConnection();
			String sql = "SELECT id, name, grade, reservation_id FROM user WHERE id=?";
			stmt = con.prepareStatement(sql);

			stmt.setInt(1, id);

			rs = stmt.executeQuery();

			if (rs.next())
			{
				User usr = new User();
				usr.setId(rs.getInt("id"));
				usr.setName(rs.getString("name"));
				usr.setGrade(rs.getObject("grade"));
				usr.setReservation(rs.getInt("reservation_id"));

				return usr;
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
			//////////////////////// TODO 06. 사원 정보 전체 조회하기
			con = dbutil.getConnection();
			String sql = "SELECT id, name, grade, reservation_id FROM user";
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next())
			{
				User usr = new User();
				usr.setId(rs.getInt("id"));
				usr.setName(rs.getString("name"));
				usr.setGrade(rs.getObject("grade"));
				usr.setReservation(rs.getInt("reservation_id"));
				users.add(usr);
			}
		}
		finally
		{
			dbutil.close(rs, stmt, con);
		}

		return users;
	}
}
