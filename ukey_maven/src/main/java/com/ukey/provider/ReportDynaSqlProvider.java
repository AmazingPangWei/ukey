package com.ukey.provider;

import org.apache.ibatis.jdbc.SQL;

import com.ukey.pojo.Reply;
import com.ukey.pojo.Report;

public class ReportDynaSqlProvider
{
	public String insertReport(Report report) {
		return new SQL() {
			{
				INSERT_INTO("report_post");
				VALUES("uid", "#{uid}");
				VALUES("rid", "#{rid}");
				VALUES("reasonId", "#{reasonId}");
			}
		}.toString();
	}
	
	public String insertTrash(Reply reply) {
		return new SQL() {
			{
				INSERT_INTO("trash");
				VALUES("rid", "#{rid}");
				VALUES("uid", "#{uid}");
				VALUES("pid", "#{pid}");
				VALUES("message", "#{message}");
			}
		}.toString();
	}
}
