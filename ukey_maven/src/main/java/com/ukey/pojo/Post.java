package com.ukey.pojo;

public class Post
{
	private int pid;
	private int class_id;
	private int uid;
	private String title;
	private int reading;
	private String date;
	private String message;//描述，通常是楼主
	private User user;//发帖人
	
	public int getPid()
	{
		return pid;
	}
	public void setPid(int pid)
	{
		this.pid = pid;
	}
	public int getClass_id()
	{
		return class_id;
	}
	public void setClass_id(int class_id)
	{
		this.class_id = class_id;
	}
	public int getUid()
	{
		return uid;
	}
	public void setUid(int uid)
	{
		this.uid = uid;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getMessage()
	{
		return message;
	}
	public void setMessage(String message)
	{
		this.message = message;
	}
	public int getReading()
	{
		return reading;
	}
	public void setReading(int reading)
	{
		this.reading = reading;
	}
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public User getUser()
	{
		return user;
	}
	public void setUser(User user)
	{
		this.user = user;
	}
	
}
