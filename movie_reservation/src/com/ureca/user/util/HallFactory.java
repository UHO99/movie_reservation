package com.ureca.user.util;

import com.ureca.hall.model.dao.HallDao;
import com.ureca.hall.model.dao.HallDaoImp;

public class HallFactory
{
	private static final HallDao dao = new HallDaoImp();

	public static HallDao getHall()
	{
		return dao;
	}
}
