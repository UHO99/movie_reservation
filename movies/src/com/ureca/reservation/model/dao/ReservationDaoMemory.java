package com.ureca.reservation.model.dao;

import java.util.ArrayList;
import java.util.List;

import com.ureca.user.model.dto.Reservation;

public class ReservationDaoMemory implements ReservationDao
{
	private final ArrayList<Reservation> reservations;

	public ReservationDaoMemory()
	{
		reservations = new ArrayList<>(20);
	}

	private int findIndex(int reservationId)
	{
		for (int i = 0, size = reservations.size(); i < size; i++)
		{
			if (reservationId == reservations.get(i).getReservation_id())
			{
				return i;
			}
		}
		return -1;
	}

	@Override
	public void add(Reservation res)
	{
		reservations.add(res);
	}

	@Override
	public void update(Reservation res)
	{
		int index = findIndex(res.getReservation_id());
		if (index > -1)
		{
			reservations.set(index, res);
		}
	}

	@Override
	public void remove(int reservationId)
	{
		int index = findIndex(reservationId);
		if (index > -1)
		{
			reservations.remove(index);
		}
	}

	@Override
	public Reservation search(int reservationId)
	{
		int index = findIndex(reservationId);
		return index > -1 ? reservations.get(index) : null;
	}

	@Override
	public List<Reservation> searchAll()
	{
		return new ArrayList<>(reservations);
	}

	@Override
	public List<Reservation> searchByMovie(int movieId)
	{
		List<Reservation> result = new ArrayList<>();
		for (Reservation res : reservations)
		{
			if (res.getMovie_id() == movieId)
			{
				result.add(res);
			}
		}
		return result;
	}

	@Override
	public boolean isSeatTaken(int movieId, int sitId)
	{
		for (Reservation res : reservations)
		{
			if (res.getMovie_id() == movieId && res.getSit_id() == sitId)
			{
				return true;
			}
		}
		return false;
	}

	public int nextReservationId()
	{
		int max = 0;
		for (Reservation res : reservations)
		{
			if (res.getReservation_id() > max)
			{
				max = res.getReservation_id();
			}
		}
		return max + 1;
	}
}
