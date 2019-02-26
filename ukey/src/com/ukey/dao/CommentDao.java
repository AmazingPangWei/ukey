package com.ukey.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import com.ukey.pojo.Comment;

@Repository
public interface CommentDao
{
	@Select("select * from comment_reply where rid = #{rid}")
	@Results({
		@Result(id=true,column="cid",property="cid"),
		@Result(column="rid",property="rid"),
		@Result(column="c_uid",property="c_uid"),
		@Result(column="message",property="message"),
		@Result(column="date",property="date"),
		@Result(column="c_uid",property="user",
		one=@One(select="com.ukey.dao.UserDao.findUserByUid",fetchType=FetchType.EAGER))
	})
	List<Comment> selectByRid(int rid);
	
	@Insert("insert into comment_reply(rid,c_uid,message) values(#{rid},#{c_uid},#{message})")
	void insertComment(Comment comment);
	
	@Delete("delete from comment_reply where rid = #{rid}")
	void deleteByRid(int rid);
}
