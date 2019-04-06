package com.ukey.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ukey.service.UkeyService;

@Controller
public class IndexController
{
	@Autowired
	private UkeyService service;
	//初始化index页面
	@RequestMapping("/indexInit")
	@ResponseBody
	Map<String,Object> indexInit(){
		Map<String,Object> map = new HashMap<>();
		map = service.indexInit();
		return map;
	}
}
