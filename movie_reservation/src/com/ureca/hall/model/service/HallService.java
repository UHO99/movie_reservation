package com.ureca.hall.model.service;

import java.util.List;

import com.ureca.user.model.dto.Hall;

public interface HallService
{
	void add(Hall hall);

	void update(Hall hall);

	void remove(int hallId, int seatId);

	Hall search(int hallId, int seatId);

	List<Hall> searchByHallId(int hallId);

	List<Hall> searchByMovieAndHall(int movieId, int hallId);

	List<Hall> searchAll();
}
