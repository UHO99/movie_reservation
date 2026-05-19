package com.ureca.movie.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.ureca.user.model.dto.Movie;
import com.ureca.user.model.dto.User;

public interface MovieDao
{
	void add(Movie mov) throws SQLException;
	void update(Movie mov) throws SQLException;
	void remove(int id) throws SQLException;
	void close() throws SQLException;
	Movie search(int id) throws SQLException;
	List<Movie> searchAll() throws SQLException;
}
