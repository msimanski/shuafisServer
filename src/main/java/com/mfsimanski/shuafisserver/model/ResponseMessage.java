package com.mfsimanski.shuafisserver.model;

import java.util.Map;

public class ResponseMessage
{
	private Map<String, String> message;

	public ResponseMessage(Map<String, String> message)
	{
		this.message = message;
	}
	
	public ResponseMessage(String s) 
	{
		this.message.put("message", s);
	}

	public Map<String, String> getMessage()
	{
		return message;
	}

	public void setMessage(Map<String, String> message)
	{
		this.message = message;
	}

}
