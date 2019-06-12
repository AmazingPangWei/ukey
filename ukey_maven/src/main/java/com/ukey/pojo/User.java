package com.ukey.pojo;

import java.io.Serializable;

public class User implements Serializable
{

	private static final long serialVersionUID = 3689927668037128716L;
	private int uid;
	private String email;
	private String password;
	private String uname;
	private String schoolid;
	private int typeid;
	private String image_path;
	private String description;
	private String birthday;
	private String sex;
	private String register_date;
	@Override
	public String toString()
	{
		return "User [uid=" + uid + ", email=" + email + ", password=" + password + ", uname=" + uname + ", schoolid="
				+ schoolid + ", typeid=" + typeid + ", image_path=" + image_path + ", description=" + description
				+ ", birthday=" + birthday + ", sex=" + sex + "]";
	}
	public int getUid()
	{
		return uid;
	}
	public void setUid(int uid)
	{
		this.uid = uid;
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
	public String getUname()
	{
		return uname;
	}
	public void setUname(String uname)
	{
		this.uname = uname;
	}
	public String getSchoolid()
	{
		return schoolid;
	}
	public void setSchoolid(String schoolid)
	{
		this.schoolid = schoolid;
	}
	public int getTypeid()
	{
		return typeid;
	}
	public void setTypeid(int typeid)
	{
		this.typeid = typeid;
	}
	public String getImage_path()
	{
		return image_path;
	}
	public void setImage_path(String image_path)
	{
		this.image_path = image_path;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getBirthday()
	{
		return birthday;
	}
	public void setBirthday(String birthday)
	{
		this.birthday = birthday;
	}
	public String getSex()
	{
		return sex;
	}
	public void setSex(String sex)
	{
		this.sex = sex;
	}
	public String getRegister_date()
	{
		return register_date;
	}
	public void setRegister_date(String register_date)
	{
		this.register_date = register_date;
	}
	
	
}
