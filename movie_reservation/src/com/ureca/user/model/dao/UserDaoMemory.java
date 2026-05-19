package com.ureca.user.model.dao;

import java.util.ArrayList;
import java.util.List;

import com.ureca.user.model.dto.User;

public class UserDaoMemory implements UserDao
{
	private ArrayList<User> users;

	public UserDaoMemory()
	{
		users = new ArrayList<>(10);
		users.add(new User(9999, "admin", 'a'));
	}

	public ArrayList<User> getUsers()
	{
		return users;
	}

	public void setUsers(ArrayList<User> users)
	{
		this.users = users;
	}

	private int findIndex(int id)
	{
		if (id > 0)
		{
			for (int i = 0, size = users.size(); i < size; i++)
			{
				if (id == users.get(i).getId())
				{
					return i;
				}
			}
		}
		return -1;
	}

	@Override
	public void add(User usr)
	{
		users.add(usr);
	}

	@Override
	public void update(User usr)
	{
		int index = findIndex(usr.getId());
		users.set(index, usr);
	}

	@Override
	public void remove(int id)
	{
		users.remove(findIndex(id));
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
			return users.get(index);
		}
		else
		{
			return null;
		}
	}

	@Override
	public List<User> searchAll()
	{
		return users.subList(0, users.size());
	}
}
