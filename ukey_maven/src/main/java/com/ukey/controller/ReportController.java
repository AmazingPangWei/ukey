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

import com.ukey.pojo.Report;
import com.ukey.pojo.ReportReason;
import com.ukey.pojo.ReportResult;
import com.ukey.service.UkeyService;
import com.ukey.util.CheckUser;
@Controller
public class ReportController
{
	@Autowired
	private UkeyService service;
	
	@RequestMapping("/report/listReportReason")
	@ResponseBody
	List<ReportReason> listReportReason(){
		List<ReportReason> list = service.listReportReason();
		return list;
	}
	
	@RequestMapping("/report/insertReport")
	@ResponseBody
	Map<String,Boolean> insertReport(@RequestBody Report report){
		boolean res = service.insertReport(report);
		Map<String, Boolean> map = new HashMap<String,Boolean>();
		map.put("result", res);
		return map;
	}
	
	@RequestMapping("/report/listReport")
	@ResponseBody
	Map<String,Object> listReport(@RequestBody Map<String,Integer> map){
		int page = map.get("page");
		System.out.println(page);
		List<ReportResult> listReport = service.listReport(page);
		int total = service.totalReport();
		Map<String,Object> res = new HashMap<>();
		res.put("reports", listReport);
		res.put("total", total);
		return res;
	}
	
	@RequestMapping("/report/handleReport")
	@ResponseBody
	Map<String,Object> handleReport(@RequestBody Map<String,Integer> map){
		int rpid = map.get("rpid");
		boolean res = service.deleteReplyByReport(rpid);
		Map<String,Object> result = new HashMap<>();
		result.put("result", res);
		return result;
	}
	
	@RequestMapping("/report/ignoreReport")
	@ResponseBody
	Map<String,Object> ignoreReport(@RequestBody Map<String,Integer> map){
		int rpid = map.get("rpid");
		boolean res = service.handleReport(rpid);
		Map<String,Object> result = new HashMap<>();
		result.put("result", res);
		return result;
	}
	
	@RequestMapping("/report/listUnhandleReport")
	@ResponseBody
	List<Map<String, Object>> listUnhandleReport(HttpSession session){
		List<Map<String, Object>> list = new LinkedList<>();
		Map<String,Object> res = CheckUser.checkAuthority(session);
		if(!(res.get("result").equals("ok"))) {
			list.add(res);
			return list;
		}
		list = service.listUnhandleReport();
		return list;
	}
}
