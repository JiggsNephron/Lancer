package com.example.lancer;

public class Expense
{
	private String job;
	private String task;
	private String item;
	private int quantity;
	
	public Expense(String job, String task, String item, int quantity)
	{
		this.job = job;
		this.task = task;
		this.item = item;
		this.quantity = quantity;
	}
	
	public String getJob()
	{
		return job;
	}
	
	public String getTask()
	{
		return task;
	}
	
	public String getItem()
	{
		return item;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
}
