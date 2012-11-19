package com.zenfly.lancer;

public class Task
{
	private int id;
	private String name;
	private int job;
	private int location;
	private String deadline;
	
	/*public Task(String name, int job)
	{
		this.name = name;
		this.job = job;
	}
	
	public Task(String name, int job, String info, int choice)
	{
		this.name = name;
		this.job = job;
		if(choice == 0) this.location = info;
		else this.deadline = info;
	}*/
	
	public Task(String name, int job, String deadline, int location)
	{
		this.name = name;
		this.job = job;
		this.deadline = deadline;
		this.location = location;
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
}
