package com.zenfly.lancer;

public class Note 
{
	private int id;
	private int job;
	private int task;
	private String subject;
	private String body;
	
	public Note(int job, int task, String subject, String body)
	{
		this.job = job;
		this.task = task;
		this.subject = subject;
		this.body = body;
	}
	
	public int getJob()
	{
		return job;
	}
	
	public int getTask()
	{
		return task;
	}
	
	public String getSubject()
	{
		return subject;
	}
	
	public String getBody()
	{
		return body;
	}
}
