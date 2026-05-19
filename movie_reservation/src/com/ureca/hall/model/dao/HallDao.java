package com.ureca.hall.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.ureca.user.model.dto.Hall;

public interface HallDao
{
	void add(Hall hall) throws SQLException;

	void update(Hall hall) throws SQLException;

	void remove(int hallId, int seatId) throws SQLException;

	Hall search(int hallId, int seatId) throws SQLException;

	List<Hall> searchByHallId(int hallId) throws SQLException;

	List<Hall> searchByMovieAndHall(int movieId, int hallId) throws SQLException;

	List<Hall> searchAll() throws SQLException;
}
