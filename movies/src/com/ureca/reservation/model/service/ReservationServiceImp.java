package com.ureca.reservation.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ureca.user.model.dao.EmployeeDao;
import com.ureca.user.model.dto.CanNotFindException;
import com.ureca.user.model.dto.DuplicateException;
import com.ureca.user.model.dto.MovieException;
import com.ureca.user.model.dto.User;
import com.ureca.user.util.MovieFactory;

public class ReservationServiceImp implements ReservationService
{
	private MovieDao dao = MovieFactory.getEmployee();

	@Override
	public void add(User emp)
	{
		try
		{
			String empno = emp.getEmpno();
			User find = dao.search(empno);
			if (find != null)
			{
				throw new DuplicateException(empno);
			}
			else
			{
				dao.add(emp);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MovieException("등록 중 오류 발생");
		}
	}

	public User search(String empno)
	{
		try
		{
			User emp = dao.search(empno);
			if (emp == null)
			{
				throw new CanNotFindException(empno);
			}

			return emp;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MovieException("사원 정보 조회 중 오류 발생");
		}
	}

	public void update(User emp)
	{
		try
		{
			search(emp.getEmpno());
			dao.update(emp);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MovieException("사원 정보 수정 중 오류 발생");
		}
	}

	public void remove(String empno)
	{
		try
		{
			search(empno);
			dao.remove(empno);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MovieException("사원 정보 삭제 중 오류 발생");
		}
	}

	public void close()
	{
		System.exit(0);
	}

	public List<User> searchAll()
	{
		try
		{
			return dao.searchAll();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MovieException("전체 사원 정보 조회 중 오류 발생");
		}
	}
}
