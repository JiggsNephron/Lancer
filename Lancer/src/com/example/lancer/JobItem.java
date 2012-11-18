package com.example.lancer;

public class JobItem 
{
	private int image;
	private String text;//image id
	private String text2;
	private String text3;

	
	public JobItem(String text, String text2, String text3)//constructor
	{
		super();
		this.text = text;  //this refers to this course object although it has no name yet it cant be referenced.
		this.text2 = text2;
		this.text3 = text3;

	}

	public int getImage()//gets the image id
	{
		return image;
	}
	
	public String getText(String temp)
	{
		if(temp.equals("1"))
		{
			return text;
		}
		else if(temp.equals("2"))
		{
			return text2;
		}
		else if(temp == "3")
		{
			return text3;
		}
		
		else
		{
			text = null;
			return text;
		}
	}	
}
