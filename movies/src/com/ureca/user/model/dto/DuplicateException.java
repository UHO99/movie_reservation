package com.ureca.user.model.dto;

public class DuplicateException extends RuntimeException
{
	public DuplicateException()
	{
	}

	public DuplicateException(int id)
	{
		super(String.format("%d번은 이미 등록된 번호입니다.", id));
	}
}
