package com.ukey.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.ukey.pojo.School;
@Repository
public interface SchoolDao
{
	@Select("select * from school")
	List<School> listSchool();
}
