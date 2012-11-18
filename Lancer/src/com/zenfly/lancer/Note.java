package com.zenfly.lancer;

public class Note 
{
	private int id;
	private String job;
	private String task;
	private String subject;
	private String body;
	
	public Note(String job, String task, String subject, String body)
	{
		this.job = job;
		this.task = task;
		this.subject = subject;
		this.body = body;
	}
	
	public String getJob()
	{
		return job;
	}
	
	public String getTask()
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
