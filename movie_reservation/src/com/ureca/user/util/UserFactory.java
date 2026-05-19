package com.ureca.user.util;

import com.ureca.user.model.dao.UserDao;
import com.ureca.user.model.dao.UserDaoImp;

public class UserFactory
{
	private static final UserDao dao = new UserDaoImp();

	public static UserDao getUser()
	{
		return dao;
	}
}
