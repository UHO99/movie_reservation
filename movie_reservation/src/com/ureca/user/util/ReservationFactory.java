package com.ureca.user.util;

import com.ureca.reservation.model.dao.ReservationDao;
import com.ureca.reservation.model.dao.ReservationDaoImp;

public class ReservationFactory
{
	private static final ReservationDao dao = new ReservationDaoImp();

	public static ReservationDao getReservation()
	{
		return dao;
	}
}
