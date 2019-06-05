package com.ukey.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import com.ukey.pojo.Reply;
import com.ukey.provider.ReplyDynaSqlProvider;

@Repository
public interface ReplyDao
{
	@InsertProvider(type= ReplyDynaSqlProvider.class, method="insertReply")
	void insertReply(Reply reply);
	
	@Select("select * from reply where pid = #{pid} ORDER BY date ASC limit #{page},6")
	@Results({
		@Result(id=true,column="rid",property="rid"),
		@Result(column="uid",property="uid"),
		@Result(column="pid",property="pid"),
		@Result(column="message",property="message"),
		@Result(column="date",property="date"),
		@Result(column="uid",property="user",
		one=@One(select="com.ukey.dao.UserDao.findUserByUid",fetchType=FetchType.EAGER)),
		@Result(column="rid",property="comments",
		many=@Many(
				select="com.ukey.dao.CommentDao.selectByRid",
				fetchType=FetchType.EAGER))
	})
	List<Reply> listReply(@Param("pid") int pid, @Param("page") int page);
	
	//用户删帖，Lazy加载
	@Select("select * from reply where pid = #{pid}")
	@Results({
		@Result(id=true,column="rid",property="rid"),
		@Result(column="uid",property="uid"),
		@Result(column="pid",property="pid"),
		@Result(column="message",property="message"),
		@Result(column="date",property="date"),
		@Result(column="rid",property="comments",
		many=@Many(
				select="com.ukey.dao.CommentDao.selectByRid",
				fetchType=FetchType.LAZY))
	})
	List<Reply> listAllReply(int pid);
	
	@Select("select message from reply where pid = #{pid} ORDER BY date ASC limit 1")
	String firstReplyMessage(int pid);
	
	@Select("select count(*) from reply where pid=#{pid}")
	Integer totalReply(int pid);
	
	@Delete("delete from reply where pid = #{pid}")
	void deleteReplyByPid(int pid);
	
	@Delete("delete from reply where rid = #{rid}")
	void deleteReplyByRid(int rid);
	
	@Select("select * from reply where rid = #{rid}")
	Reply selectReplyByRid(int rid);
}
