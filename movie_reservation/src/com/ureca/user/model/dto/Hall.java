package com.ureca.user.model.dto;

import java.sql.Time;

public class Hall
{
	private int hallId;
	private int seatId;
	private int seatCount;
	private int movieTime;
	private Time startTime;
	private Integer movieId;
	private int todayShowCnt;

	public Hall()
	{
	}

	public Hall(int hallId, int seatId, int seatCount, int movieTime, Time startTime, Integer movieId, int todayShowCnt)
	{
		this.hallId = hallId;
		this.seatId = seatId;
		this.seatCount = seatCount;
		this.movieTime = movieTime;
		this.startTime = startTime;
		this.movieId = movieId;
		this.todayShowCnt = todayShowCnt;
	}

	public int getHallId()
	{
		return hallId;
	}

	public void setHallId(int hallId)
	{
		this.hallId = hallId;
	}

	public int getSeatId()
	{
		return seatId;
	}

	public void setSeatId(int seatId)
	{
		this.seatId = seatId;
	}

	public int getSeatCount()
	{
		return seatCount;
	}

	public void setSeatCount(int seatCount)
	{
		this.seatCount = seatCount;
	}

	public int getMovieTime()
	{
		return movieTime;
	}

	public void setMovieTime(int movieTime)
	{
		this.movieTime = movieTime;
	}

	public Time getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Time startTime)
	{
		this.startTime = startTime;
	}

	public Integer getMovieId()
	{
		return movieId;
	}

	public void setMovieId(Integer movieId)
	{
		this.movieId = movieId;
	}

	public int getTodayShowCnt()
	{
		return todayShowCnt;
	}

	public void setTodayShowCnt(int todayShowCnt)
	{
		this.todayShowCnt = todayShowCnt;
	}
}
