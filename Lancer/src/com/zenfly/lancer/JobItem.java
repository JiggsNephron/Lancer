package com.zenfly.lancer;

public class JobItem 
{
	private int image;
	private String name;//image id
	private String location;
	private String date;

	
	public JobItem(String name, String location, String date)//constructor
	{
		super();
		this.name = name;  //this refers to this course object although it has no name yet it cant be referenced.
		this.location = location;
		this.date = date;

	}

	public int getImage()//gets the image id
	{
		return image;
	}
	
	public String getText(String temp)
	{
		if(temp.equals("date"))
		{
			return date;
		}
		else if(temp.equals("location"))
		{
			return location;
		}
		else if(temp == "date")
		{
			return date;
		}
		
		else
		{
			name = null;
			return name + " caused an error";
		}
	}	
}
