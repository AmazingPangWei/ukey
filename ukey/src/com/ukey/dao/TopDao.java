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

import com.ukey.pojo.Post;

@Repository
public interface TopDao
{
	@Insert("insert into top(pid) values(#{pid})")
	void insertTop(int pid);
	
	@Delete("delete from top where pid = #{pid}")
	void deleteTop(int pid);
	
	@Select("select post.pid as pid ,class_id,uid,title, reading,post.date as date from top,"
			+ "post where post.class_id = #{class_id} and post.pid = top.pid limit 3")
	@Results({
		@Result(id=true,column="pid",property="pid"),
		@Result(column="class_id",property="class_id"),
		@Result(column="uid",property="uid"),
		@Result(column="title",property="title"),
		@Result(column="reading",property="reading"),
		@Result(column="date",property="date"),
		@Result(column="pid" ,property="message",
		one=@One(select="com.ukey.dao.ReplyDao.firstReplyMessage",fetchType=FetchType.EAGER)
		),
		@Result(column="uid",property="user",
		one=@One(select="com.ukey.dao.UserDao.findUserByUid",fetchType=FetchType.EAGER)
		)
	})
	List<Post> listTop(int class_id);
}
