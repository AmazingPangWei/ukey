package com.ukey.pojo;

public class Association
{
	private Integer aid;
	private String email;
	private String password;
	private String aname;
	private String date;
	private String description;
	public Integer getAid()
	{
		return aid;
	}
	public void setAid(Integer aid)
	{
		this.aid = aid;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getAname()
	{
		return aname;
	}
	public void setAname(String aname)
	{
		this.aname = aname;
	}
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	@Override
	public String toString()
	{
		return "Association [aid=" + aid + ", email=" + email + ", password=" + password + ", aname=" + aname
				+ ", date=" + date + ", description=" + description + "]";
	}
}
