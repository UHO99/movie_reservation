package com.ureca.user.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.ureca.user.model.dto.User;

public interface UserDao
{
	void add(User usr) throws SQLException;
	void update(User usr) throws SQLException;
	void remove(int id) throws SQLException;
	void close() throws SQLException;
	User search(int id) throws SQLException;
	List<User> searchAll() throws SQLException;
}
