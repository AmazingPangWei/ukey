package com.ukey.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.ukey.pojo.Association;

@Repository
public interface AssociationDao
{
	@Select("select aid,email,aname,date,description from association Order by date ASC limit 5")
	List<Association> listAssciation();
}
