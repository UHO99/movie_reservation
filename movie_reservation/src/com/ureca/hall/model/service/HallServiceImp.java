package com.ureca.hall.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ureca.hall.model.dao.HallDao;
import com.ureca.hall.util.HallScheduleValidator;
import com.ureca.user.model.dto.CanNotFindException;
import com.ureca.user.model.dto.DuplicateException;
import com.ureca.user.model.dto.Hall;
import com.ureca.user.model.dto.MovieException;
import com.ureca.user.util.HallFactory;

public class HallServiceImp implements HallService
{
	private final HallDao dao = HallFactory.getHall();

	@Override
	public void add(Hall hall)
	{
		try
		{
			if (dao.search(hall.getHallId(), hall.getSeatId()) != null)
			{
				throw new DuplicateException(hall.getHallId());
			}

			HallScheduleValidator.assertNoOverlap(hall, dao.searchByHallId(hall.getHallId()));

			dao.add(hall);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MovieException("상영관 등록 중 오류 발생");
		}
	}

	@Override
	public void update(Hall hall)
	{
		try
		{
			if (dao.search(hall.getHallId(), hall.getSeatId()) == null)
			{
				throw new CanNotFindException(hall.getHallId());
			}
			HallScheduleValidator.assertNoOverlap(hall, dao.searchByHallId(hall.getHallId()));
			dao.update(hall);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MovieException("상영관 수정 중 오류 발생");
		}
	}

	@Override
	public void remove(int hallId, int seatId)
	{
		try
		{
			if (dao.search(hallId, seatId) == null)
			{
				throw new CanNotFindException(hallId);
			}
			dao.remove(hallId, seatId);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MovieException("상영관 삭제 중 오류 발생");
		}
	}

	@Override
	public Hall search(int hallId, int seatId)
	{
		try
		{
			Hall hall = dao.search(hallId, seatId);
			if (hall == null)
			{
				throw new CanNotFindException(hallId);
			}
			return hall;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MovieException("상영관 조회 중 오류 발생");
		}
	}

	@Override
	public List<Hall> searchByHallId(int hallId)
	{
		try
		{
			return dao.searchByHallId(hallId);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MovieException("상영관 목록 조회 중 오류 발생");
		}
	}

	@Override
	public List<Hall> searchByMovieAndHall(int movieId, int hallId)
	{
		try
		{
			return dao.searchByMovieAndHall(movieId, hallId);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MovieException("상영 회차 조회 중 오류 발생");
		}
	}

	@Override
	public List<Hall> searchAll()
	{
		try
		{
			return dao.searchAll();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MovieException("상영관 전체 조회 중 오류 발생");
		}
	}
}
