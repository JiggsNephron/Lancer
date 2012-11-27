package com.zenfly.lancer;

public class Item
{
	private int id;
	private String name;
	private float price;
	
	public Item(String name, float price)
	{
		this.name = name;
		this.price = price;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public float getPrice()
	{
		return price;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
}