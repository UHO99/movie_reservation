package com.ureca.movie.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ureca.movie.model.dao.MovieDao;
import com.ureca.user.model.dto.CanNotFindException;
import com.ureca.user.model.dto.DuplicateException;
import com.ureca.user.model.dto.Movie;
import com.ureca.user.model.dto.MovieException;
import com.ureca.user.util.MovieFactory;

public class MovieServiceImp implements MovieService
{
	private final MovieDao dao = MovieFactory.getMovie();

	@Override
	public void add(Movie mov)
	{
		try
		{
			if (dao.search(mov.getMovie_id()) != null)
			{
				throw new DuplicateException(mov.getMovie_id());
			}
			dao.add(mov);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MovieException("영화 등록 중 오류 발생");
		}
	}

	@Override
	public Movie search(int movieId)
	{
		try
		{
			Movie mov = dao.search(movieId);
			if (mov == null)
			{
				throw new CanNotFindException(movieId);
			}
			return mov;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MovieException("영화 조회 중 오류 발생");
		}
	}

	@Override
	public void update(Movie mov)
	{
		try
		{
			search(mov.getMovie_id());
			dao.update(mov);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MovieException("영화 수정 중 오류 발생");
		}
	}

	@Override
	public void remove(int movieId)
	{
		try
		{
			search(movieId);
			dao.remove(movieId);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MovieException("영화 삭제 중 오류 발생");
		}
	}

	@Override
	public List<Movie> searchAll()
	{
		try
		{
			return dao.searchAll();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MovieException("전체 영화 조회 중 오류 발생");
		}
	}
}
