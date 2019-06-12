package com.ukey.pojo;

import java.io.Serializable;

public class Report implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5111098158676828401L;
	private int rpid;
	private int uid;
	private int rid;
	private int reasonId;
	private String date;
	private int done;
	public int getRpid()
	{
		return rpid;
	}
	public void setRpid(int rpid)
	{
		this.rpid = rpid;
	}
	public int getUid()
	{
		return uid;
	}
	public void setUid(int uid)
	{
		this.uid = uid;
	}
	public int getRid()
	{
		return rid;
	}
	public void setRid(int rid)
	{
		this.rid = rid;
	}
	public int getReasonId()
	{
		return reasonId;
	}
	public void setReasonId(int reasonId)
	{
		this.reasonId = reasonId;
	}
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public int getDone()
	{
		return done;
	}
	public void setDone(int done)
	{
		this.done = done;
	}
	
	
}
