package com.ukey.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FromController
{
	@RequestMapping(value="/{formName}")
	 public String loginForm(@PathVariable String formName){
		// 动态跳转页面
		System.out.println(formName);
		return formName;
	}
	
	@RequestMapping(value="/user/{formName}")
	 public String loginForm2(@PathVariable String formName){
		// 动态跳转页面
		return "user/"+formName;
	}
		
	@RequestMapping(value="/post/{formName}")
	 public String loginForm3(@PathVariable String formName){
		// 动态跳转页面
		return "post/"+formName;
	}
	
	@RequestMapping(value="/reply/{formName}")
	 public String loginForm4(@PathVariable String formName){
		// 动态跳转页面
		return "reply/"+formName;
	}
	
	@RequestMapping(value="/report/{formName}")
	 public String loginForm5(@PathVariable String formName){
		// 动态跳转页面
		return "report/"+formName;
	}
}
