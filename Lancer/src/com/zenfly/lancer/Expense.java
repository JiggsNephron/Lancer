package com.zenfly.lancer;

public class Expense
{
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
}
