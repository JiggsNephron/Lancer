package com.zenfly.lancer;

import android.util.Log;

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
	private String phone;
	private int done;
	private int alarm;

	public Task(String name, int job, String deadline, int location, float wage, String phone, String email, int hours, int done, int alarm)
	{
		Log.v("Deadline = ", deadline);
		this.name = name;
		this.job = job;
		this.deadline = deadline;
		this.location = location;
		this.hourlyWage = wage;
		this.phone = phone;
		this.email = email;
		this.hoursWorked = hours;
		this.done = done;
		this.alarm = alarm;
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
<<<<<<< HEAD
	
	public int getAlarm()
	{
		return alarm;
	}
=======
>>>>>>> branch 'TeamStartsHere' of git@github.com:JiggsNephron/Lancer.git
}
