package com.ukey.service;

import java.util.List;
import java.util.Map;

import com.ukey.pojo.Comment;
import com.ukey.pojo.Post;
import com.ukey.pojo.Reply;
import com.ukey.pojo.Report;
import com.ukey.pojo.ReportReason;
import com.ukey.pojo.ReportResult;
import com.ukey.pojo.School;
import com.ukey.pojo.User;

public interface UkeyService
{
	public Boolean ifUserExist(String email);
	
	public boolean insertUser(User user);
	
	public Boolean updateUser(User user);
	
	public void updatePassword(String email,String newPassword);
	
	public String getPassword(int uid);
	
	public User login(String email,String password);
	
	public Map<String,Object> indexInit();
	
	public Boolean insertPost(Post post);
	
	public void deletePost(int pid);
	
	public Map<String,Object> listPost(int classId,int page);
	
	public Boolean insertReply(Reply reply);
	
	public List<School> listSchool();
	
	public int totalPost(int classId);
	
	public int totalReply(int pid);
	
	public List<Reply> listReply(int pid,int page);
	
	public boolean deleteReplyByReport(int rpid);
	
	public boolean insertComment(Comment comment);
	
	public boolean insertTop(int pid);
	
	public boolean deleteTop(int pid);
	
	public String getImageName(int uid);
	
	public boolean updateImagePath(String url,int uid);
	
	public User findUserByUid(int uid);
	
	public List<Post> searchPost(String words);
	
	public String getPostTile(int pid);
	
	public void addReading(int pid);
	
	public List<ReportReason> listReportReason();
	
	public boolean insertReport(Report report);
	
	public List<ReportResult> listReport(int page);
	
	public int totalReport();
	
	public boolean handleReport(int rpid);
	
	public List<Map<String,Object>> listUnhandleReport();
	
	public void flushIndex();
	
	public void updateCache(Post post);
}
