package com.ukey.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ukey.pojo.Comment;
import com.ukey.service.UkeyService;

@Controller
public class CommentController
{
	@Autowired
	private UkeyService service;
	
	@RequestMapping("/comment/insertComment")
	@ResponseBody
	Map<String,Boolean> insertComment(@RequestBody Comment comment){
		Map<String,Boolean> map = new HashMap<>();
		boolean res = service.insertComment(comment);
		map.put("result", res);
		return map;
	}
	
}
