package com.ukey.pojo;

import java.io.Serializable;

public class ReportReason implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4956790546575534968L;
	private int reasonId;
	private String reason;
	public int getReasonId()
	{
		return reasonId;
	}
	public void setReasonId(int reasonId)
	{
		this.reasonId = reasonId;
	}
	public String getReason()
	{
		return reason;
	}
	public void setReason(String reason)
	{
		this.reason = reason;
	}
	
}
