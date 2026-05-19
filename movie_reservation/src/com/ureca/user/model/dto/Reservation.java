package com.ureca.user.model.dto;

import java.sql.Timestamp;

public class Reservation extends User
{
	private int reservation_id;
	private int seat_id;
	private int movie_id;
	private int user_id;
	private int hall_id;
	private Timestamp start_datetime;

	public Reservation(int reservation_id, int seat_id, int movie_id)
	{
		this(reservation_id, seat_id, movie_id, 0, 0, null);
	}

	public Reservation(int reservation_id, int seat_id, int movie_id, int user_id)
	{
		this(reservation_id, seat_id, movie_id, user_id, 0, null);
	}

	public Reservation(int reservation_id, int seat_id, int movie_id, int user_id, int hall_id,
			Timestamp start_datetime)
	{
		super();
		this.reservation_id = reservation_id;
		this.seat_id = seat_id;
		this.movie_id = movie_id;
		this.user_id = user_id;
		this.hall_id = hall_id;
		this.start_datetime = start_datetime;
	}

	public int getReservation_id()
	{
		return reservation_id;
	}

	public void setReservation_id(int reservation_id)
	{
		this.reservation_id = reservation_id;
	}

	public int getSeat_id()
	{
		return seat_id;
	}

	public void setSeat_id(int seat_id)
	{
		this.seat_id = seat_id;
	}

	/** 하위 호환 */
	public int getSit_id()
	{
		return seat_id;
	}

	public void setSit_id(int seat_id)
	{
		this.seat_id = seat_id;
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

	public int getHall_id()
	{
		return hall_id;
	}

	public void setHall_id(int hall_id)
	{
		this.hall_id = hall_id;
	}

	public Timestamp getStart_datetime()
	{
		return start_datetime;
	}

	public void setStart_datetime(Timestamp start_datetime)
	{
		this.start_datetime = start_datetime;
	}

	@Override
	public String toString()
	{
		return "Reservation [reservation_id=" + reservation_id + ", seat_id=" + seat_id + ", movie_id=" + movie_id
				+ ", user_id=" + user_id + ", hall_id=" + hall_id + ", start_datetime=" + start_datetime + "]";
	}
}
