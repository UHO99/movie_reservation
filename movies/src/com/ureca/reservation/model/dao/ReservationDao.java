package com.ureca.reservation.model.dao;

import java.util.List;

import com.ureca.user.model.dto.Reservation;

public interface ReservationDao
{
	void add(Reservation res);

	void update(Reservation res);

	void remove(int reservationId);

	Reservation search(int reservationId);

	List<Reservation> searchAll();

	List<Reservation> searchByMovie(int movieId);

	boolean isSeatTaken(int movieId, int sitId);
}
