package com.ureca.user.model.dto;

import java.sql.Date;
import java.sql.Time;

public class Movie extends User
{
	private int movie_id;
	private Date air_date;
	private Date end_date;
	private int movie_time;
	private String movie_name;
	private int hall_id;
	private Time start_time;

	public Movie()
	{
	}

	public Movie(int movie_id, Date air_date, String movie_name, int hall_id)
	{
		this(movie_id, air_date, air_date, 120, movie_name, hall_id, null);
	}

	public Movie(int movie_id, Date air_date, Date end_date, int movie_time, String movie_name, int hall_id,
			Time start_time)
	{
		super();
		this.movie_id = movie_id;
		this.air_date = air_date;
		this.end_date = end_date;
		this.movie_time = movie_time;
		this.movie_name = movie_name;
		this.hall_id = hall_id;
		this.start_time = start_time;
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

	public Date getEnd_date()
	{
		return end_date;
	}

	public void setEnd_date(Date end_date)
	{
		this.end_date = end_date;
	}

	public int getMovie_time()
	{
		return movie_time;
	}

	public void setMovie_time(int movie_time)
	{
		this.movie_time = movie_time;
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

	public Time getStart_time()
	{
		return start_time;
	}

	public void setStart_time(Time start_time)
	{
		this.start_time = start_time;
	}

	@Override
	public String toString()
	{
		return "Movie [movie_id=" + movie_id + ", air_date=" + air_date + ", end_date=" + end_date + ", movie_time="
				+ movie_time + ", movie_name=" + movie_name + ", hall_id=" + hall_id + ", start_time=" + start_time
				+ "]";
	}
}
