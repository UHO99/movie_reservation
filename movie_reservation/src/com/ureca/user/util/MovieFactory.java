package com.ureca.user.util;

import com.ureca.movie.model.dao.MovieDao;
import com.ureca.movie.model.dao.MovieDaoImp;

public class MovieFactory
{
	private static final MovieDao dao = new MovieDaoImp();

	public static MovieDao getMovie()
	{
		return dao;
	}
}
