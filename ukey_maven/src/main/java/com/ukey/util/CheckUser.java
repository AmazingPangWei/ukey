package com.ukey.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.ukey.pojo.User;

public class CheckUser
{
	public static Map<String,Object> checkAuthority(HttpSession session){
		Map<String,Object> res = new HashMap<>();
		User user = (User)session.getAttribute("user");
		if(user == null) {
			res.put("result", "NoUser");
			return res;
		}
		//无此权限
		else if(user.getTypeid() == 1){
			res.put("result", "NoAuthority");
			return res;
		}
		res.put("result", "ok");
		return res;
	}
}
