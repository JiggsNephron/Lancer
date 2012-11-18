package com.zenfly.lancer;

public class Task
{
	private int id;
	private String name;
	private String job;
	private String location;
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
	
	public Task(String name, String job, String deadline, String location)
	{
		this.name = name;
		this.job = job;
		this.deadline = deadline;
		this.location = location;
	}
	
	public String getLocation()
	{
		return location;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getJob()
	{
		return job;
	}
	
	public String getDeadline()
	{
		return deadline;
	}
}
