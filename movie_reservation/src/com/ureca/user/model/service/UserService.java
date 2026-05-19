package com.ureca.user.model.service;

import java.util.List;

import com.ureca.user.model.dto.User;

public interface UserService
{
	void add(User usr);
	void update(User usr);
	void remove(int id);
	void close();
	User search(int id);
	List<User> searchAll();
}
