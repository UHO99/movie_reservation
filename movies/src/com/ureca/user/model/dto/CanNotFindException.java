package com.ureca.user.model.dto;

public class CanNotFindException extends RuntimeException
{
	public CanNotFindException()
	{
	}

	public CanNotFindException(int id)
	{
		super(String.format("%d번에 해당하는 정보를 찾을 수 없습니다.", id));
	}
}
