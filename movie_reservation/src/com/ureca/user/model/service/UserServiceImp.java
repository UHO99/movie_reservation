package com.ureca.user.model.service;

import java.sql.SQLException;

import java.util.List;

import com.ureca.user.model.dao.UserDao;
import com.ureca.user.model.dto.CanNotFindException;
import com.ureca.user.model.dto.DuplicateException;
import com.ureca.user.model.dto.MovieException;
import com.ureca.user.model.dto.User;
import com.ureca.user.util.UserFactory;

public class UserServiceImp implements UserService
{
	private UserDao dao = UserFactory.getUser();

	@Override
	public void add(User usr)
	{
		try
		{
			int id = usr.getId();
			User find = dao.search(id);
			if (find != null)
			{
				throw new DuplicateException(id);
			}
			else
			{
				dao.add(usr);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MovieException("등록 중 오류 발생");
		}
	}

	public User search(int id)
	{
		try
		{
			User usr = dao.search(id);
			if (usr == null)
			{
				throw new CanNotFindException(id);
			}

			return usr;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MovieException("사원 정보 조회 중 오류 발생");
		}
	}

	public void update(User usr)
	{
		try
		{
			search(usr.getId());
			dao.update(usr);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MovieException("사원 정보 수정 중 오류 발생");
		}
	}

	public void remove(int id)
	{
		try
		{
			search(id);
			dao.remove(id);
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
