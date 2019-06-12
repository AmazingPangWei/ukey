package com.ukey.pojo;

import java.io.Serializable;
import java.util.List;

public class Reply implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 739246743455079383L;
	private int rid;
	private int uid;
	private int pid;
	private String message;
	private String date;
	private User user; //回复人
	private List<Comment> comments;
	
	public int getRid()
	{
		return rid;
	}
	public void setRid(int rid)
	{
		this.rid = rid;
	}
	public int getUid()
	{
		return uid;
	}
	public void setUid(int uid)
	{
		this.uid = uid;
	}
	public int getPid()
	{
		return pid;
	}
	public void setPid(int pid)
	{
		this.pid = pid;
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
	public List<Comment> getComments()
	{
		return comments;
	}
	public void setComments(List<Comment> comments)
	{
		this.comments = comments;
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
		return "Reply [rid=" + rid + ", uid=" + uid + ", pid=" + pid + ", message=" + message + ", date=" + date
				+ ", comments=" + comments + "]";
	}
	
	
}
