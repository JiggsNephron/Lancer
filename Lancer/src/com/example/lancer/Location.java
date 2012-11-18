package com.example.lancer;

public class Location 
{
	private int id;
	private String location;
	private String add1;
	private String add2;
	private String add3;
	
	public Location(String location)
	{
		this.location = location;
	}
	
	public Location(String location, String add1)
	{
		this.location = location;
		this.add1 = add1;
	}
	
	public Location(String location, String add1, String add2)
	{
		this.location = location;
		this.add1 = add1;
		this.add2 = add2;
	}
	
	public Location(String location, String add1, String add2, String add3)
	{
		this.location = location;
		this.add1 = add1;
		this.add2 = add2;
		this.add3 = add3;
	}
	
	public String getLocation()
	{
		return location;
	}
	
	public String getAdd1()
	{
		return add1;
	}
	
	public String getAdd2()
	{
		return add2;
	}
	
	public String getAdd3()
	{
		return add3;
	}
	
	public void setLocation(String location)
	{
		this.location = location;
	}
	
	public void setAdd1(String add1)
	{
		this.add1 = add1;
	}
	
	public void setAdd2(String add2)
	{
		this.add2 = add2;
	}
	
	public void setAdd3(String add3)
	{
		this.add3 = add3;
	}
}
