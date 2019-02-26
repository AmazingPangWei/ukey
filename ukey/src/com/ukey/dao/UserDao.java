package com.ukey.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import com.ukey.pojo.User;
import com.ukey.provider.UserDynaSqlProvider;

@Repository
public interface UserDao
{
	@InsertProvider(type=UserDynaSqlProvider.class,method = "insertUser")
	void insertUser(User user);
	
	@UpdateProvider(type=UserDynaSqlProvider.class,method = "updateUser")
	void updateUser(User user);
	
	@Update("update user set password=#{password} where email = #{email}")
	void updatePassword(@Param("password") String password,@Param("email") String email);
	
	@Select("select password from user where uid = #{uid}")
	String getPassword(int uid);

	@Select("select * from user where email = #{email} and password = #{password}")
	User login(@Param("email") String email,@Param("password") String password);
	
	@Select("select count(uid) from user where email=#{email}")
	Integer checkUserExist(String email);
	
	@Select("select uid,email,uname,schoolid,typeid,image_path,register_date,description,birthday,sex"
			+ " from user where uid = #{uid}")
	User findUserByUid(int uid);
	
	@Update("update user set image_path = #{url} where uid = #{uid}")
	void updateImagePath(@Param("url") String url,@Param("uid") int uid);
	
	@Select("select image_path from user where uid = #{uid}")
	String getImagePath(int uid);
	
	@Select("select uname,user.uid uid,num,reading " + 
			"from user , (" + 
			"SELECT uid,COUNT(*) as num , sum(reading) as reading FROM `post` GROUP BY uid LIMIT 10) t " + 
			"where user.uid = t.uid " + 
			"ORDER BY num DESC ")
	List<Map<String,String>> listRank();
}
