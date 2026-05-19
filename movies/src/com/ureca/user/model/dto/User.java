package com.ureca.user.model.dto;

import java.io.Serializable;
import java.util.Objects;

public class User implements Cloneable, Comparable<User>, Serializable
{
	private int id;
	private String name;
	private char grade;
	private int reservation_id;

	public User()
	{
	}

	public User(int id, String name, char grade, int reservation_id)
	{
		super();
		this.id = id;
		this.name = name;
		this.grade = grade;
		this.reservation_id = reservation_id;
	}

	@Override
	public int compareTo(User o)
	{
		//		return o.getEmpno().compareTo(empno);		//내림 차순
		return name.compareTo(o.name); //오름 차순
	}

	/**
	 * clone() 
	 *  - 객체의 내용이 똑같은 객체를 생성해서 리턴 
	 *  - Object 클래스에서 protected로 선언했기 때문에 함수를 사용하기 위해서는 반드시 Override 해야 한다. 
	 *  - Cloneable 인터페이스를 구현해야 한다. 
	 *   ==> Cloneable 인터페이스를 구현하지 않으면 CloneNotSupportedException이 발생한다. 
	 */
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	/**
	 * equals(Object o)
	 * - 객체의 내용을 비교하는 함수 
	 * - 반드시 Override 해야 객체 내용을 비교할 수 있다.  
	 */
	public int getId()
	{
		return this.id;
	}

	/**
	 * hashCode 
	 *  - 객체의 참조 값을 리턴하는 기능 
	 *  - 필요시 Override한다. 
	 *    ==> hashCode() 함수를 Override 해도 객체의 실제 hashcode는 변하지 않는다. 
	 */
	//	@Override
	//	public int hashCode() {
	//		return Objects.hash(empno, name, salary);
	//	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof User)
		{ // instanceof가  null 검사도 한다. 
			User usr = (User) obj;
			if (Objects.equals(this.id, usr.id))
			{
				return true;
			}
		}
		return false;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getGrade()
	{
		return grade;
	}

	public void setGrade(Object object)
	{
		this.grade = (char) object;
	}

	public void setReservation(int reservation_id)
	{
		this.reservation_id = reservation_id;
	}

	public int getReservation()
	{
		return reservation_id;
	}

	/**
	 * finalize()
	 *  - 객체가 가비지 컬렉터에 의해 메모리에서 해제될 때 호출되는 함수 
	 */
	protected void finalize() throws Throwable
	{
		System.out.println(this.hashCode() + " finalize........");
	}

	/**
	 * toString()
	 * - 객체의 내용을 문자열로 리턴 
	 * - System.out으로 객체를 출력시  toString()를 호출해서 출력한다. 
	 * - 객체를 String 객체에 + 연산을 하면 toString()를 호출해서 문자열을 연결한다. 
	 */
	public String toString()
	{
		return "id=" + id + ", name=" + name + ", grade=" + grade;
	}

	void notVirtualInvoke()
	{
		System.out.println("Employee의 notVirtualInvoke()");
	}
}
