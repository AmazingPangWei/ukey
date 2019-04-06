package com.ukey.listener;

import java.util.Map;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.ukey.pojo.User;

public class SessionListener implements HttpSessionListener
{
	@Override
	public void sessionCreated(HttpSessionEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event)
	{

		User user = (User) event.getSession().getAttribute("user");
		if (user != null)
		{
			Map<Integer, Boolean> userMap = (Map<Integer, Boolean>) event.getSession().getServletContext()
					.getAttribute("userMap");
			userMap.remove(user.getUid());
			event.getSession().getServletContext().setAttribute("userMap", userMap);
		}

	}
}
