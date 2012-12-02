package com.zenfly.lancer;

public class Expense
{
	int id;
	private int job;
	private int task;
	private int item;
	private int quantity;
	
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

	public void setId(int id)
	{
		this.id = id;
	}
	
	public void setJob(int job)
	{
		this.job = job;
	}
	
	public void setTask(int task)
	{
		this.task = task;
	}
	
	public void setItem(int item)
	{
		this.item = item;
	}
	
	public void setQuantity(int quant)
	{
		this.quantity = quant;
	}
}
