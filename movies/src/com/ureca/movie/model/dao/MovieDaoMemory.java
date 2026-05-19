package com.ureca.movie.model.dao;

import java.util.ArrayList;
import java.util.List;

import com.ureca.user.model.dto.Movie;
import com.ureca.user.model.dto.User;

public class MovieDaoMemory implements MovieDao
{
	private ArrayList<Movie> movs;

	public MovieDaoMemory()
	{
		movs = new ArrayList<>(10);
	}

	public ArrayList<Movie> getUsers()
	{
		return movs;
	}

	public void setMovie(ArrayList<Movie> movs)
	{
		this.movs = movs;
	}

	private int findIndex(int id)
	{
		if (id > 0)
		{
			for (int i = 0, size = movs.size(); i < size; i++)
			{
				if (id == movs.get(i).getMovie_id())
				{
					return i;
				}
			}
		}
		return -1;
	}

	@Override
	public void add(Movie mov)
	{
		movs.add(mov);
	}

	@Override
	public void update(Movie mov)
	{
		int index = findIndex(mov.getMovie_id());
		movs.set(index, mov);
	}

	@Override
	public void remove(int id)
	{
		movs.remove(findIndex(id));
	}

	@Override
	public void close()
	{
		System.exit(0); // jvm을 정상 종료
	}

	@Override
	public User search(int id)
	{
		int index = findIndex(id);
		if (index > -1)
		{
			return movs.get(index);
		}
		else
		{
			return null;
		}
	}

	@Override
	public List<Movie> searchAll()
	{
		return movs.subList(0, movs.size());
	}
}
