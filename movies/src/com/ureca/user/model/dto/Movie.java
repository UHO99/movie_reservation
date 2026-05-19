package com.ureca.user.model.dto;

import java.sql.Date;

public class Movie extends User
{
	private int movie_id;
	private Date air_date;
	private String movie_name;
	private int hall_id;

	public Movie()
	{

	}

	public Movie(int movie_id, Date air_date, String movie_name, int hall_id)
	{
		super();
		this.movie_id = movie_id;
		this.air_date = air_date;
		this.movie_name = movie_name;
		this.hall_id = hall_id;
	}

	public int getMovie_id()
	{
		return movie_id;
	}

	public void setMovie_id(int movie_id)
	{
		this.movie_id = movie_id;
	}

	public Date getAir_date()
	{
		return air_date;
	}

	public void setAir_date(Date air_date)
	{
		this.air_date = air_date;
	}

	public String getMovie_name()
	{
		return movie_name;
	}

	public void setMovie_name(String movie_name)
	{
		this.movie_name = movie_name;
	}

	public int getHall_id()
	{
		return hall_id;
	}

	public void setHall_id(int hall_id)
	{
		this.hall_id = hall_id;
	}

	@Override
	public String toString()
	{
		return "Movie [movie_id=" + movie_id + ", air_date=" + air_date + ", movie_name=" + movie_name + ", hall_id="
				+ hall_id + "]";
	}
}
