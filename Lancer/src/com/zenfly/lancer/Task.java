package com.zenfly.lancer;

public class Task
{
	private int id;
	private String name;
	private int job;
	private int location;
	private String deadline;
	private long minutesWorked;
	private float hourlyWage;
	private String email;
	private String phone;
	private int done;
	private int alarm;
	private int started;

	public Task(String name, int job, String deadline, int location, float wage, String phone, String email, long mins, int done, int alarm, int started)
	{
		this.name = name;
		this.job = job;
		this.deadline = deadline;
		this.location = location;
		this.hourlyWage = wage;
		this.phone = phone;
		this.email = email;
		this.minutesWorked = mins;
		this.done = done;
		this.alarm = alarm;
		this.started = started;
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
	
	public long getMinutesWorked()
	{
		return minutesWorked;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getPhone()
	{
		return phone;
	}
	
	public int getDone()
	{
		return done;
	}
	
	public int hasAlarm()
	{
		return alarm;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public void setAlarm(int alarm)
	{
		this.alarm = alarm;
	}
	
	public int getAlarm()
	{
		return alarm;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setJob(int job)
	{
		this.job = job;
	}
	
	public void setLocation(int location)
	{
		this.location = location;
	}
	
	public void setDeadline(String deadline)
	{
		this.deadline = deadline;
	}
	
	public void setWage(float wage)
	{
		this.hourlyWage = wage;
	}
	
	public void setMinutes(long minutes)
	{
		this.minutesWorked = minutes;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	
	public void setDone(int done)
	{
		this.done = done;
	}
	
	public int getStarted()
	{
		return this.started;
	}
}
