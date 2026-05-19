package com.ureca.user.model.dto;

public class CanNotFindException extends RuntimeException
{
	public CanNotFindException()
	{
	}

	public CanNotFindException(String empno)
	{
		super(String.format("%s번에 해당하는 정보를 찾을 수 없습니다.", empno));
	}
}
