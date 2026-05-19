package com.ureca.reservation.model.service;

import java.sql.Timestamp;
import java.util.List;

import com.ureca.user.model.dto.Reservation;

public interface ReservationService
{
	void add(Reservation res);

	void update(Reservation res);

	void remove(int reservationId);

	Reservation search(int reservationId);

	boolean isSeatTaken(int movieId, int seatId);

	boolean isSeatTaken(int movieId, int hallId, Timestamp startDatetime, int seatId);

	int nextReservationId();

	List<Reservation> searchAll();

	List<Reservation> searchByUserId(int userId);
}
