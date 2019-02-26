package com.ukey.provider;

import org.apache.ibatis.jdbc.SQL;

import com.ukey.pojo.Reply;

public class ReplyDynaSqlProvider
{
	public String insertReply(Reply reply) {
		return new SQL() {
			{
				INSERT_INTO("reply");
				VALUES("uid", "#{uid}");
				VALUES("pid", "#{pid}");
				VALUES("message", "#{message}");
			}
		}.toString();
	}
}
