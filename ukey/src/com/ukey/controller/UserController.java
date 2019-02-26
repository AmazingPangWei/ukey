package com.ukey.controller;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ukey.pojo.User;
import com.ukey.service.UkeyService;
import com.ukey.util.Email;
import com.ukey.util.FileOperation;
import com.ukey.util.UUIDCreator;

import net.coobird.thumbnailator.Thumbnails;

@Controller
public class UserController
{
	@Autowired
	private UkeyService service;
	
	@RequestMapping("/user/ifuserexist")
	@ResponseBody
	Map<String,Boolean> ifUserExist(@RequestBody Map<String,String> map){
		System.out.println(map);
		Map<String,Boolean> res = new HashMap<String, Boolean>();
		if(service.ifUserExist(map.get("email"))) {
			res.put("result", true);
		}
		else {
			res.put("result", false);
		}
		return res;
	}
	
	//转到提示页面，显示注册成功
	@PostMapping("/user/signin")
	@ResponseBody
	Map<String,Boolean> addUser(@RequestBody User user) {
		System.out.println(user);
		Map<String,Boolean> map = new HashMap<>();
		boolean res = service.insertUser(user);
		map.put("result", res);
		System.out.println(map.toString());
		return map;
	}
	
	@PostMapping("/user/login")
	@ResponseBody
	Map<String,String> login(@RequestBody Map<String,String> param,HttpSession session) {
		String email = param.get("email");
		String password = param.get("password");
		Map<String,String> res = new HashMap<>();
		boolean t = service.ifUserExist(email);
		if(!t) {
			res.put("result", "noUser");
			return res;
		}
		ServletContext app = session.getServletContext();
		User user = service.login(email, password);
		//该用户是否可以登录
		if(user!=null) {
			//判断是否该用户已在线,map对应uid和在线情况
			Map<Integer,Boolean> map = (Map<Integer,Boolean>)app.getAttribute("userMap");
			if(map!=null)
				System.out.println(map.toString());
			if(map != null && map.get(user.getUid()) != null ) {
				//该用户已经在线，所以不能登录！
				res.put("result","online");
				return res;
			}
			//处理初始状况，可能 map为null
			if(map == null) {
				map = new HashMap<>();
			}
			//可以登录
			map.put(user.getUid(), true);
			session.setAttribute("user", user);
			app.setAttribute("userMap",map);
			res.put("result", "right");
		}
		//该用户密码错误
		else {
			res.put("result", "passwordError");
		}
		return res;
	}
	
	@RequestMapping("/user/logout")
	@ResponseBody
	Map<String,Boolean> logout(HttpSession session){
		User user = (User) session.getAttribute("user");
		Map<String,Boolean>  res = new HashMap<>();
		if(user != null) {
			//获得application,删除对应uid
			ServletContext app = session.getServletContext();
			Map<Integer, Boolean> userMap = (Map<Integer, Boolean>) app.getAttribute("userMap");
			userMap.remove(user.getUid());
			app.setAttribute("userMap", userMap);
			//对session进行处理
			session.removeAttribute("user");
			res.put("result", true);
			return res;
		}
		else {
			res.put("result", false);
			return res;
		}
	}
	
	//处理用户更改密码请求
	@PostMapping("/user/passwd")
	@ResponseBody
	Map<String,Boolean> passwd(@RequestBody Map<String,String> map,HttpSession session) {
		String email = map.get("email");
		Map<String, Boolean> result = new HashMap<>();
		try
		{
			String md5 = Email.sendEmail(email);
			session.setAttribute("email", email);
			session.setAttribute("md5", md5);
			System.out.println(email);
			System.out.println(md5);
			result.put("result", true);
			return result;
		}
		catch (Exception e)
		{
			result.put("result", false);
			session.removeAttribute("email");
			session.removeAttribute("md5");
			return result;
		}
	}
	
	//用户更改密码验证
	@GetMapping("/user/changePassword")
	String changePassword(@RequestParam String identificationCode,
			HttpSession session,RedirectAttributes redirectAttributes) {
		String md5 = (String)session.getAttribute("md5");
		System.out.println(md5);
		System.out.println(identificationCode);
		//若匹配，则可以更改密码，跳到修改密码的页面
		if(md5 != null && md5.equals(identificationCode)) {
			session.removeAttribute(md5);
			session.setAttribute("mark", true);
			return "redirect:change_password?code=2";
		}
		//不匹配，则跳到主页
		else {
			return "redirect: ../message?code=3";
		}
	}
	
	//由修改密码页面提出的修改密码请求
	@RequestMapping("/user/chpasswd")
	@ResponseBody
	Map<String,Object> passwordChange(@RequestBody Map<String,String> map,
			HttpSession session) {
		String newPassword = map.get("newPassword");
		Map<String,Object> result = new HashMap<>();
		boolean mark = (boolean)session.getAttribute("mark");
		String email = (String)session.getAttribute("email");
		if(mark) {
			service.updatePassword(email, newPassword);
			session.removeAttribute("mark");
			session.removeAttribute("email");
			result.put("result", true);
		}
		else {
			result.put("result", false);
		}
		return result;
	}
	
	@RequestMapping("/user/chpasswdByUser")
	@ResponseBody
	Map<String,Object> passwordChangeByUser(@RequestBody Map<String,String> map,
			HttpSession session) {
		System.out.println(map.toString());
		String oldPassword = map.get("oldPassword");
		String newPassword = map.get("newPassword");
		Map<String,Object> result = new HashMap<>();
		User user = (User)session.getAttribute("user");
		if(user!=null && oldPassword != null && !oldPassword.equals("")
				&& newPassword != null &&!newPassword.equals("") 
				&& oldPassword.equals(service.getPassword(user.getUid()))) {
			service.updatePassword(user.getEmail(),newPassword );
			this.logout(session);
			result.put("result", true);
		}
		else {
			result.put("result", false);
		}
		return result;
	}
	
	
	@RequestMapping("/user/updateUser")
	@ResponseBody
	Map<String,Boolean> updateUser(@RequestBody User user){
		System.out.println(user);
		Map<String,Boolean> map = new HashMap<>();
		if(user.getUid() == 0) {
			map.put("result", false);
			return map;
		}
		Boolean bool = service.updateUser(user);
		if(bool) {
			map.put("result", true);
		}
		else
			map.put("result", false);
		return map;
	}
	
	@RequestMapping("/user/updateFace")
	String upload(@RequestParam("file") MultipartFile file , 
			@RequestParam("uid") Integer uid,
			HttpServletRequest request) throws Exception
	{
        System.out.println("开始处理图片...");
        //当传来的文件为Null时
        if(file == null || uid == null) {
			return "../news?code=4";
        }
        String originName = service.getImageName(uid);
		String path = request.getServletContext().getRealPath("face/"); // 文件存储位置
		try
		{
			String fileName = UUIDCreator.getUUID();
			// 获得文件后缀名
			String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			String savePath = path + fileName + suffixName;
			//删除旧头像
			if(!originName.equals("default.jpg")) {
				String deletePath = path + originName;
				FileOperation.deleteFile(deletePath);
			}
			//加入新头像
			service.updateImagePath("/ukey/face/"+fileName+suffixName,uid);
			// 小于10k，不压缩
			if (file.getSize() <= 1024 * 10)
			{
				System.out.println("不压缩");
				file.transferTo(new File(savePath));
			}
			// 500k时，压缩50%
			else if (file.getSize() <= 1024 * 500)
			{
				InputStream in = file.getInputStream();
				Thumbnails.of(in).scale(0.5f).toFile(savePath);
			}
			// 大于500k，压缩75%
			else if (file.getSize() <= 1024 * 1024 * 2)
			{
				InputStream in = file.getInputStream();
				Thumbnails.of(in).scale(0.25f).toFile(savePath);
			}
			else
			{
				InputStream in = file.getInputStream();
				Thumbnails.of(in).scale(0.15f).toFile(savePath);
			}
		}
		catch (Exception e)
		{
			return "../news?code=4";
		}
        return "user/info?uid="+uid;
	}
	
	@RequestMapping("/user/userInfo")
	@ResponseBody
	User updateUser(@RequestBody Map<String,Integer> map) {
		if(map.get("uid") == null) {
			return new User();
		}
		int uid = map.get("uid");
		User user = service.findUserByUid(uid);
		return user;
	}
	
	@RequestMapping("/user/nowUser")
	@ResponseBody
	User nowUser(HttpSession session) {
		User user = (User)session.getAttribute("user");
		if(user == null)
			return new User();
		user.setPassword(null);
		return user;
	}
}
