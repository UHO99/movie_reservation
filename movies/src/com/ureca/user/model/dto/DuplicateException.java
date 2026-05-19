package com.ureca.user.model.dto;

public class DuplicateException extends RuntimeException
{
	public DuplicateException()
	{
	}

	public DuplicateException(String empno)
	{
		super(String.format("%s번은 이미 등록된 번호입니다.", empno));
	}
}
