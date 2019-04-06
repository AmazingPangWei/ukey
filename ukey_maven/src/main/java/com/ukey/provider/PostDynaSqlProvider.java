package com.ukey.provider;

import org.apache.ibatis.jdbc.SQL;

import com.ukey.pojo.Post;

public class PostDynaSqlProvider
{
	public String insertPost(Post post) {
		return new SQL() {
			{
				INSERT_INTO("post");
				VALUES("class_id", "#{class_id}");
				VALUES("uid", "#{uid}");
				VALUES("title", "#{title}");
				VALUES("reading", "0");
			}
		}.toString();
	}	
}
