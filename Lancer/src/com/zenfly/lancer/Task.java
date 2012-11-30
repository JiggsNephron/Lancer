package com.zenfly.lancer;

public class Task
{
	private int id;
	private String name;
	private int job;
	private int location;
	private String deadline;
	private int hoursWorked;
	private float hourlyWage;
	private String email;
	private int phone;
	private int done;

	public Task(String name, int job, String deadline, int location, float wage, int hours, int done)
	{
		this.name = name;
		this.job = job;
		this.deadline = deadline;
		this.location = location;
		this.hourlyWage = wage;
		this.hoursWorked = hours;
		this.done = done;
	}
	
	public int getId()
	{
		return id;
	}
	
	public int getLocation()
	{
		return location;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getJob()
	{
		return job;
	}
	
	public String getDeadline()
	{
		return deadline;
	}
	
	public float getWage()
	{
		return hourlyWage;
	}
	
	public int getHoursWorked()
	{
		return hoursWorked;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public int getPhone()
	{
		return phone;
	}
	
	public int getDone()
	{
		return done;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
}
