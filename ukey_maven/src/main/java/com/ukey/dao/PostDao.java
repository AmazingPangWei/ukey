package com.ukey.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import com.ukey.pojo.Post;
import com.ukey.provider.PostDynaSqlProvider;
@Repository
public interface PostDao
{
	//返回1条最新动态消息
	@Select("select * from post where class_id = 5 ORDER BY date DESC LIMIT 1 ")
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
	Post secondHandPost();
	
	@Select("select * from post where class_id = 2 ORDER BY date DESC LIMIT 1 ")
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
	Post losePost();
	
	@Select("select * from post where class_id = 3 ORDER BY date DESC LIMIT 1 ")
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
	Post partnerPost();
	
	@Select("select * from post where class_id = 1 ORDER BY date DESC LIMIT 1 ")
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
	Post dynamicPost();
	
	@Select("select * from post where class_id = 7 ORDER BY date DESC LIMIT 1 ")
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
	Post gamePost();
	
	@Select("select * from post where class_id = 2 ORDER BY date DESC LIMIT 8 ")
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
	List<Post> listLost();
	
		
	@InsertProvider(type=PostDynaSqlProvider.class , method="insertPost")
	@Options(useGeneratedKeys=true, keyProperty="pid")
	void insertPost(Post post);

	//有一对一和一对多的关系
	@Select("select post.pid as pid ,class_id,uid,title, reading,post.date as date"
			+ " from post where class_id = #{class_id}"
			+ " and  post.pid not in (select pid from top)"
			+ "ORDER BY date DESC limit #{page},6")                                 
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
	List<Post> listPost(@Param("class_id") int classId,@Param("page") int page);
	
	@Select("select count(*) from post where class_id = #{class_id} ")
	Integer totalPost(int class_id);
	
	@Select("select count(*) from post,top where class_id = #{class_id} and post.pid = top.pid")
	Integer totalTop(int class_id);
	
	@Update("update post set reading = reading + 1 where pid = #{pid}")
	void addReading(int pid);
	
	@Delete("delete from post where pid = #{pid}")
	void deletePost(int pid);
	
	@Select("select * from post where title like '%${words}%' ORDER BY date DESC")
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
	List<Post> searchPost(@Param("words") String words);
	
	@Select("select title from post where pid = #{pid}")
	String postTitle(int pid);
}
