package com.ukey.pojo;

import java.io.Serializable;

public class School implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6014997045894691610L;
	private int schoolid;
	private String sname;

	public int getSchoolid()
	{
		return schoolid;
	}
	public void setSchoolid(int schoolid)
	{
		this.schoolid = schoolid;
	}
	public String getSname()
	{
		return sname;
	}
	public void setSname(String sname)
	{
		this.sname = sname;
	}
}
