package com.ukey.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ukey.pojo.Post;
import com.ukey.pojo.User;
import com.ukey.service.UkeyService;

@Controller
public class PostController
{
	@Autowired
	private UkeyService service;
	
	@RequestMapping("/post/insertPost")
	@ResponseBody
	Map<String,Object> insertPost(@RequestBody Post post)
	{
		Map<String, Object> map = new HashMap<>();
		if(service.insertPost(post)) {
			map.put("result", true);
			return map;
		}
		else {
			map.put("result", false);
			return map;
		}
	}

	//列出某一页的帖子，一页6贴，并返回总页数
	@RequestMapping("/post/listPost")
	@ResponseBody
	Map<String,Object> listPost(@RequestBody Map<String,String> map){
		if(map.get("page") != null && map.get("class_id") != null)
		{
			Integer page = Integer.valueOf(map.get("page"));
			Integer classId = Integer.valueOf(map.get("class_id"));
			Integer total = service.totalPost(classId);
			Map<String,Object> result =  service.listPost(classId, page);
			result.put("total", total);
			return result;
		}
		else{
			Map<String,Object> result = new HashMap<>();
			result.put("result", null);
			return result;
		}
	}
	
	//删除某一条帖子
	@RequestMapping("/post/deletePost")
	@ResponseBody
	Map<String,String> deletePost(@RequestBody Map<String,String> map,HttpSession session){
		Map<String,String> res = new HashMap<>();
		if(map.get("pid") == null) {
			res.put("result", "NoParameter");
			return res;
		}
		User user = (User)session.getAttribute("user");
		if(user == null) {
			res.put("result", "NoUser");
			return res;
		}
		//无此权限
		else if(user.getTypeid() == 1){
			res.put("result", "NoAuthority");
			return res;
		}
		int pid = Integer.valueOf(map.get("pid"));
		service.deletePost(pid);
		res.put("result", "success");
		return res;
	}
	
	//增加一条顶置
	@RequestMapping("/post/insertTop")
	@ResponseBody
	Map<String,String> insertTop(@RequestBody Map<String,Integer> map,HttpSession session){
		Map<String,String> res = new HashMap<>();
		if(map.get("pid")!=null) {
			User user = (User)session.getAttribute("user");
			if(user.getTypeid() == 1) {
				res.put("result", "NoAuthority");
				return res;
			}
			int pid = map.get("pid");
			if(service.insertTop(pid)) {
				res.put("result", "success");
				return res;
			}
			else {
				res.put("result","error");
				return res;
			}
		}
		else {
			res.put("result", "error");
			return res;
		}
	}
	
	//删除一条顶置
	@RequestMapping("/post/deleteTop")
	@ResponseBody
	Map<String,String> deleteTop(@RequestBody Map<String,Integer> map,HttpSession session){
		Map<String,String> res = new HashMap<>();
		if(map.get("pid")!=null) {
			User user = (User)session.getAttribute("user");
			if(user == null || user.getTypeid() == 1) {
				res.put("result", "NoAuthority");
				return res;
			}
			int pid = map.get("pid");
			if(service.deleteTop(pid)) {
				res.put("result", "success");
				return res;
			}
			else {
				res.put("result","error");
				return res;
			}
		}
		else {
			res.put("result", "error");
			return res;
		}
	}
	
	@RequestMapping("/post/searchPost")
	@ResponseBody
	List<Post> searchPost(@RequestBody Map<String,String> map){
		if(map.get("words") == null) {
			List<Post> list = new LinkedList<>();
			return list;
		}
		if(map.get("words").equals("")) {
			List<Post> list = new LinkedList<>();
			return list;
		}
		System.out.println(map);
		String words = map.get("words");
		List<Post> posts = service.searchPost(words);
		return posts;
	}
	
	@RequestMapping("/post/addReading")
	@ResponseBody
	void addReading(@RequestBody Map<String,Integer> map) {
		int pid = map.get("pid");
		service.addReading(pid);
	}
}
