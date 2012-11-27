package com.zenfly.lancer;

public class Expense
{
	int id;
	private int job;
	private int task;
	private int item;
	private int quantity;
	private int ID;
	
	public Expense(int job, int task, int item, int quantity)
	{
		this.job = job;
		this.task = task;
		this.item = item;
		this.quantity = quantity;
	}
	
	public int getId()
	{
		return id;
	}
	
	
	public int getId()
	{
		return ID;
	}
	
	public int getJob()
	{
		return job;
	}
	
	public int getTask()
	{
		return task;
	}
	
	public int getItem()
	{
		return item;
	}
	
	public int getQuantity()
	{
		return quantity;
	}

	public void setId(int parseInt)
	{
		this.id = id;
	}
}
