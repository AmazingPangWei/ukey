package com.ukey.controller;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ukey.pojo.Reply;
import com.ukey.service.UkeyService;
import com.ukey.util.UUIDCreator;

import net.coobird.thumbnailator.Thumbnails;

@Controller
public class ReplyController
{
	@Autowired
	private UkeyService service;
	
	@RequestMapping("/reply/insertReply")
	@ResponseBody
	Map<String,Object> insertReply(@RequestBody Reply reply){
		Map<String, Object> map = new HashMap<>();
		if(service.insertReply(reply)) {
			map.put("result", true);
			return map;
		}
		else
		{
			map.put("result", false);
			return map;
		}
	}
	
	@RequestMapping("/reply/listReply")
	@ResponseBody
	Map<String,Object> listReply(@RequestBody Map<String,String> map){
		Map<String,Object> res = new HashMap<>();
		if(map.get("pid") != null && map.get("page")!=null) {
			Integer pid = Integer.valueOf(map.get("pid"));
			Integer page = Integer.valueOf(map.get("page"));
			int totalReply = service.totalReply(pid);
			List<Reply> listReply = service.listReply(pid, page);
			String title = service.getPostTile(pid);
			res.put("tatalReply", totalReply);
			res.put("title", title);
			res.put("listReply", listReply);
			return res;
		}
		else {
			res.put("result", null);
			return res;
		}
	}	
	
	@RequestMapping("/reply/upload")
	@ResponseBody
	Map<String,Object> upload(@RequestParam("file") List<MultipartFile> list , 
			HttpServletRequest request) throws Exception
	{
		List<String> urls = new ArrayList<>();
        Map<String, Object> map = new HashMap<>(2);
        System.out.println("开始处理图片...");
        //当传来的文件为Null时
        if(list == null) {
        	map.put("errno", 1);
			urls=null;
			map.put("data", urls);
			return map;
        }
        String returnUrl = request.getScheme() + "://" + "localhost" + ":" + "8080" + request.getContextPath() +"/upload/";//存储路径
        String path = request.getServletContext().getRealPath("/upload/"); //文件存储位置
        System.out.println("returnUrl:"+returnUrl);
        System.out.println("path:"+path);
        for (MultipartFile file : list) {
        	try
			{
				String fileName = UUIDCreator.getUUID();
		          //获得文件后缀名 
		        String suffixName=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
				String savePath = path + "\\" + fileName + suffixName;
				String url = returnUrl + fileName + suffixName;
				//小于10k，不压缩
				if (file.getSize() <= 1024 * 10)
				{
					System.out.println("不压缩");
					file.transferTo(new File(savePath));
				}
				//500k时，压缩75%
				else if (file.getSize() <= 1024 * 500)
				{
					InputStream in = file.getInputStream();
					Thumbnails.of(in).scale(0.25f).toFile(savePath);
				}
				//大于500k，压缩90%
				else if(file.getSize() <= 1024 * 1024 * 2)
				{
					InputStream in = file.getInputStream();
					Thumbnails.of(in).scale(0.1f).toFile(savePath);
				} 
				else
				{
					InputStream in = file.getInputStream();
					Thumbnails.of(in).scale(0.15f).toFile(savePath);
				}
				System.out.println(savePath);
				System.out.println(url);
				urls.add(url);
			}
			catch (Exception e)
			{
				map.put("errno", 1);
				urls=null;
				map.put("data", urls);
				e.printStackTrace();
				return map;
			}
        }
        
        map.put("data", urls);
        map.put("errno", 0);
        return map;
	}
	
}
