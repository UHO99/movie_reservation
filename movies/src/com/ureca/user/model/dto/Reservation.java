package com.ureca.user.model.dto;

public class Reservation extends User
{
	private int reservation_id;
	private int sit_id;
	private int movie_id;
	private int user_id;

	public Reservation(int reservation_id, int sit_id, int movie_id)
	{
		super();
		this.reservation_id = reservation_id;
		this.sit_id = sit_id;
		this.movie_id = movie_id;
	}

	public int getReservation_id()
	{
		return reservation_id;
	}

	public void setReservation_id(int reservation_id)
	{
		this.reservation_id = reservation_id;
	}

	public int getSit_id()
	{
		return sit_id;
	}

	public void setSit_id(int sit_id)
	{
		this.sit_id = sit_id;
	}

	public int getMovie_id()
	{
		return movie_id;
	}

	public void setMovie_id(int movie_id)
	{
		this.movie_id = movie_id;
	}

	public int getUser_id()
	{
		return user_id;
	}

	public void setUser_id(int user_id)
	{
		this.user_id = user_id;
	}

	@Override
	public String toString()
	{
		return "Reservation [reservation_id=" + reservation_id + ", sit_id=" + sit_id + ", movie_id=" + movie_id
				+ ", user_id=" + user_id + "]";
	}
}
