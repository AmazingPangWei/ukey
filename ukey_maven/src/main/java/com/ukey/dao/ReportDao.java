package com.ukey.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.ukey.pojo.Reply;
import com.ukey.pojo.Report;
import com.ukey.pojo.ReportReason;
import com.ukey.pojo.ReportResult;
import com.ukey.provider.ReportDynaSqlProvider;

@Repository
public interface ReportDao
{
	@Select("select * from report_reason")
	List<ReportReason> listReportReason();

	@InsertProvider(type=ReportDynaSqlProvider.class,method="insertReport")
	void insertReport(Report report);
	
	@Select("SELECT uname,date,reason FROM report_reason,USER,(" + 
			"SELECT uid,temp.date,reasonId FROM trash,(" + 
			"SELECT rid,date,reasonId FROM report_post WHERE done=1) AS temp WHERE trash.rid=temp.rid) AS temp2 WHERE report_reason.reasonId=temp2.reasonId AND USER.uid=temp2.uid ORDER BY date DESC LIMIT #{startIndex},20")
	List<ReportResult> listReport(int startIndex);
	
	@Select("select count(*) from trash")
	int totalReport();
	
	@Delete("delete from reply where rid = (select rid from report_post where rpid = #{rpid})")
	void deleteReplyByRpid(int rpid);
	
	@InsertProvider(type=ReportDynaSqlProvider.class,method="insertTrash")
	void insertTrash(Reply reply);
	
	@Update("update report_post set done = 1 where rpid = #{rpid}")
	void handleReport(int rpid);
	
	@Select("select * from reply where rid in (select rid from report_post where rpid = #{rpid})")
	Reply selectReportedReply(int rpid);
	
	@Select("SELECT rpid,uname,reason,message,temp.date AS date FROM reply,user,(SELECT rpid,rid,date,reason FROM report_post,report_reason WHERE done=0 AND report_post.reasonId=report_reason.reasonId ) AS temp WHERE temp.rid=reply.rid and reply.uid = user.uid")
	List<Map<String,Object>> listUnhandleReport();
}
