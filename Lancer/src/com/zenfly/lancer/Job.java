package com.zenfly.lancer;

public class Job 
{

	private int id;
	private String client;
	private String location;
	
	public Job(String client, String location)
	{
		this.client = client;
		this.location = location;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getClient()
	{
		return client;
	}
	
	public String getLocation()
	{
		return location;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public void setClient(String client)
	{
		this.client = client;
	}
	
	public void setLocation(String location)
	{
		this.location = location;
	}
}
