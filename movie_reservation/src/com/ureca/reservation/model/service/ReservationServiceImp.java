package com.ureca.reservation.model.service;

import java.util.List;

import com.ureca.reservation.model.dao.ReservationDao;
import com.ureca.user.model.dto.CanNotFindException;
import com.ureca.user.model.dto.DuplicateException;
import com.ureca.user.model.dto.Reservation;
import com.ureca.user.util.ReservationFactory;

public class ReservationServiceImp implements ReservationService
{
	private final ReservationDao dao = ReservationFactory.getReservation();

	@Override
	public void add(Reservation res)
	{
		boolean taken = res.getStart_datetime() != null && res.getHall_id() > 0
				? dao.isSeatTaken(res.getMovie_id(), res.getHall_id(), res.getStart_datetime(), res.getSeat_id())
				: dao.isSeatTaken(res.getMovie_id(), res.getSeat_id());
		if (taken)
		{
			throw new DuplicateException(res.getSeat_id());
		}

		dao.add(res);
	}

	@Override
	public void update(Reservation res)
	{
		Reservation existing = dao.search(res.getReservation_id());

		if (existing == null)
		{
			throw new CanNotFindException(res.getReservation_id());
		}
		if (existing.getSeat_id() != res.getSeat_id() && dao.isSeatTaken(res.getMovie_id(), res.getSeat_id()))
		{
			throw new DuplicateException(res.getSeat_id());
		}

		dao.update(res);
	}

	@Override
	public void remove(int reservationId)
	{
		if (dao.search(reservationId) == null)
		{
			throw new CanNotFindException(reservationId);
		}
		dao.remove(reservationId);
	}

	@Override
	public Reservation search(int reservationId)
	{
		Reservation res = dao.search(reservationId);
		if (res == null)
		{
			throw new CanNotFindException(reservationId);
		}
		return res;
	}

	@Override
	public boolean isSeatTaken(int movieId, int seatId)
	{
		return dao.isSeatTaken(movieId, seatId);
	}

	@Override
	public boolean isSeatTaken(int movieId, int hallId, java.sql.Timestamp startDatetime, int seatId)
	{
		return dao.isSeatTaken(movieId, hallId, startDatetime, seatId);
	}

	@Override
	public int nextReservationId()
	{
		return dao.nextReservationId();
	}

	@Override
	public List<Reservation> searchAll()
	{
		return dao.searchAll();
	}

	@Override
	public List<Reservation> searchByUserId(int userId)
	{
		return dao.searchByUserId(userId);
	}
}
