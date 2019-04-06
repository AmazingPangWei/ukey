package com.ukey.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ukey.pojo.School;
import com.ukey.service.UkeyService;

@Controller
public class SchoolController
{	
	@Autowired
	private UkeyService service;
	
	@RequestMapping("/school/listSchool")
	@ResponseBody
	List<School> listSchool(){
		List<School> list = service.listSchool();
		return list;
	}
}
