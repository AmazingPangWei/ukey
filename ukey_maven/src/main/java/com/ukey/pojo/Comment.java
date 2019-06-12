package com.ukey.pojo;

import java.io.Serializable;

public class Comment implements Serializable
{
	
	private static final long serialVersionUID = 1680979144203485237L;
	private int cid;
	private int rid;
	private int c_uid;
	private String message;
	private String date;
	private User user;//回复人
	public int getCid()
	{
		return cid;
	}
	public void setCid(int cid)
	{
		this.cid = cid;
	}
	public int getRid()
	{
		return rid;
	}
	public void setRid(int rid)
	{
		this.rid = rid;
	}
	public int getC_uid()
	{
		return c_uid;
	}
	public void setC_uid(int c_uid)
	{
		this.c_uid = c_uid;
	}
	public String getMessage()
	{
		return message;
	}
	public void setMessage(String message)
	{
		this.message = message;
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
	@Override
	public String toString()
	{
		return "Comment [cid=" + cid + ", rid=" + rid + ", c_uid=" + c_uid + ", message=" + message + ", date=" + date
				+ "]";
	}
}
