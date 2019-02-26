package com.ukey.provider;

import org.apache.ibatis.jdbc.SQL;

import com.ukey.pojo.User;

public class UserDynaSqlProvider
{
	//插入新用户
	//默认头像设置
	public String insertUser(User user) {
		return new SQL() {
			{
				INSERT_INTO("user");
				VALUES("email", "#{email}");
				VALUES("password", "#{password}");
				VALUES("typeid", "1");
				VALUES("image_path", "\'/ukey/face/default.jpg\'");
				VALUES("uname", "\'不设置昵称的小懒虫\'");
			}
		}.toString();
	}
	
	public String updateUser(User user) {
		return new SQL() {
			{
				UPDATE("user");
				if(user.getBirthday() != null && !user.getBirthday().equals("")) {
					SET("birthday = #{birthday}");
				}
				
				if(user.getDescription() != null && !user.getDescription().equals("")) {
					SET("description = #{description}");
				}
				
				if(user.getUname() != null && !user.getUname().equals("")) {
					SET("uname = #{uname}");
				}
				
				if(user.getSchoolid() != null && !user.getSchoolid().equals("")) {
					SET("schoolid = #{schoolid}");
				}
				
				if(user.getSex() != null && !user.getSex().equals("")) {
					SET("sex = #{sex}");
				}
				
				WHERE("uid = #{uid}");
			}
		}.toString();
	}
	
	
}
