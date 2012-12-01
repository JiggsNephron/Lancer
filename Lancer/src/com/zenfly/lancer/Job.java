package com.zenfly.lancer;

public class Job 
{

	private int id;
	private String client;
	
	public Job(String client)
	{
		this.client = client;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getClient()
	{
		return client;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public void setClient(String client)
	{
		this.client = client;
	}
	
	public boolean equals(Object obj)
	{
		Job job = (Job) obj;
		if(this.id == job.id) return true;
		return false;
	}
}
