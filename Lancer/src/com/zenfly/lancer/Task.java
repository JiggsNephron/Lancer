package com.zenfly.lancer;

public class Task
{
	private int id;
	private String name;
	private int job;
	private int location;
	private String deadline;
	private int hoursWorked;
	private int hourlyWage;

	public Task(String name, int job, String deadline, int location, int wage, int hours)
	{
		this.name = name;
		this.job = job;
		this.deadline = deadline;
		this.location = location;
		this.hourlyWage = wage;
		this.hoursWorked = hours;
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
	
	public int getWage()
	{
		return hourlyWage;
	}
	
	public int getHoursWorked()
	{
		return hoursWorked;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
}
