package com.ureca.movie.model.service;

import java.util.List;

import com.ureca.user.model.dto.User;

public interface MovieService
{
	void add(User emp);
	void update(User emp);
	void remove(String empno);
	void close();
	User search(String empno);
	List<User> searchAll();
}
