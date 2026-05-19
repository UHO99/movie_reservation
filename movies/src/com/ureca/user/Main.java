package com.ureca.user;

import com.ureca.user.model.service.UserService;
import com.ureca.user.model.service.UserServiceImp;
import com.ureca.user.view.MovieUI;

public class Main
{
	public static void main(String[] args)
	{
		UserService service = new UserServiceImp();
		MovieUI mainView = new MovieUI();
		mainView.setModel(service);
	}
}
