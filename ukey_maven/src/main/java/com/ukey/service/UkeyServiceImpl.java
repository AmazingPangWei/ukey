package com.ukey.service;

import static com.ukey.util.UkeyConstant.pageNum;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ukey.dao.AssociationDao;
import com.ukey.dao.CommentDao;
import com.ukey.dao.PostDao;
import com.ukey.dao.ReplyDao;
import com.ukey.dao.ReportDao;
import com.ukey.dao.SchoolDao;
import com.ukey.dao.TopDao;
import com.ukey.dao.UserDao;
import com.ukey.pojo.Association;
import com.ukey.pojo.Comment;
import com.ukey.pojo.Post;
import com.ukey.pojo.Reply;
import com.ukey.pojo.Report;
import com.ukey.pojo.ReportReason;
import com.ukey.pojo.ReportResult;
import com.ukey.pojo.School;
import com.ukey.pojo.User;
@Service
public class UkeyServiceImpl implements UkeyService
{
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PostDao postDao;
	
	@Autowired
	private ReplyDao replyDao;
	
	@Autowired
	private SchoolDao schoolDao;
	
	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private TopDao topDao;
	
	@Autowired
	private AssociationDao assDao;
	
	@Autowired
	private ReportDao reportDao;
	
	@Autowired
	private CacheService cache;
	
	@Transactional(readOnly=true)
	@Override
	public Boolean ifUserExist(String email)
	{
		if(userDao.checkUserExist(email) > 0) {
			return true;
		}
		else 
		{
			return false;
		}			
	}
	
	@Override
	@Transactional
	public boolean insertUser(User user) {
		try
		{
			userDao.insertUser(user);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	@Transactional
	public void deletePost(int pid)
	{
		Post post = postDao.getPostByPid(pid);
		updateCache(post);
		List<Reply> replys = replyDao.listAllReply(pid);
		//删除对回复的评论
		for(Reply reply:replys) {
			int rid = reply.getRid();
			commentDao.deleteByRid(rid);
		}
		//删除评论
		replyDao.deleteReplyByPid(pid);
		//删除帖子
		postDao.deletePost(pid);
	}
	
	@Override
	@Transactional
	public void updatePassword(String email, String newPassword)
	{
		userDao.updatePassword(newPassword, email);
	}
	
	
	@Override
	@Transactional(readOnly=true)
	public String getPassword(int uid)
	{
		return userDao.getPassword(uid);
	}
	
	@Override
	@Transactional(readOnly=true)
	public User login(String email, String password)
	{
		User user = userDao.login(email, password);
		return user;
	}
	
	@Override
	@Transactional
	public Boolean updateUser(User user)
	{
		try
		{
			userDao.updateUser(user);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	@Override
	@Transactional(readOnly=true)
	public Map<String, Object> indexInit()
	{
		flushIndex();
		Map<String,Object> map = new HashMap<>();
		map.put("page1", cache.cacheResult("page1"));
		map.put("page2_1", cache.cacheResult("page2_1"));
		map.put("page2_2", cache.cacheResult("page2_2"));
		map.put("page3", cache.cacheResult("page3"));
		map.put("page4", null);
		System.out.println(map);
		return map;
	}
	
	@Override
	@Transactional
	public Boolean insertPost(Post post) {
		try
		{
			//自动增长，产生pid,方便后面的insertReply
			postDao.insertPost(post);
			//构造reply，使其成为本帖的第一楼
			Reply reply = new Reply();
			reply.setMessage(post.getMessage());
			reply.setPid(post.getPid());
			reply.setUid(post.getUid());
			replyDao.insertReply(reply);
			updateCache(post);//刷新缓存
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	@Override
	@Transactional(readOnly=true)
	public Map<String,Object> listPost(int classId, int page)
	{
		Map<String,Object> res = new HashMap<>();
		List<Post> top = null;
		//找出顶置贴
		if(page == 1) {
			top = topDao.listTop(classId);
		}
		res.put("tops", top);
		//每页6贴
		List<Post> list = postDao.listPost(classId, (page-1)*pageNum);
		res.put("posts", list);
		return res;
	}
	
	//某类帖子总的数量
	@Override
	@Transactional(readOnly=true)
	public int totalPost(int classId)
	{
		int totalPost = postDao.totalPost(classId);
		int totalTop = postDao.totalTop(classId);
		return totalPost - totalTop;
	}
	
	
	@Override
	@Transactional
	public Boolean insertReply(Reply reply)
	{
		try
		{
			replyDao.insertReply(reply);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<School> listSchool(){
		return schoolDao.listSchool();
	}
	
	//获得6条帖子的回复
	@Override
	@Transactional(readOnly=true)
	public List<Reply> listReply(int pid,int page){
		return replyDao.listReply(pid, (page-1)*pageNum);
	}
	
	@Override
	@Transactional(readOnly=true)
	public int totalReply(int pid) {
		return replyDao.totalReply(pid);
	}
	
	@Override
	@Transactional
	public boolean deleteReplyByReport(int rpid)
	{
		try
		{
			//得到将会被删掉的reply
			Reply reply = reportDao.selectReportedReply(rpid);
			//删掉这个reply从评论表中
			reportDao.deleteReplyByRpid(rpid);
			//将这个reply插入到trash中
			reportDao.insertTrash(reply);
			//处理完毕
			reportDao.handleReport(rpid);
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	}
	
	@Override
	@Transactional
	public boolean insertComment(Comment comment)
	{
		try
		{
			commentDao.insertComment(comment);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	@Override
	@Transactional
	public boolean insertTop(int pid)
	{
		try
		{
			topDao.insertTop(pid);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
		
	}
	
	@Override
	@Transactional
	public boolean deleteTop(int pid)
	{
		try
		{
			topDao.deleteTop(pid);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	@Override
	@Transactional
	public boolean updateImagePath(String url, int uid)
	{
		try
		{
			userDao.updateImagePath(url, uid);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	//获取头像名，以删除来源的头像
	@Override
	@Transactional(readOnly=true)
	public String getImageName(int uid)
	{
		String originPath = userDao.getImagePath(uid);
		return originPath.substring(originPath.lastIndexOf("/")+1);
	}
	
	@Override
	@Transactional(readOnly=true)
	public User findUserByUid(int uid)
	{
		User user = userDao.findUserByUid(uid);
		return user;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Post> searchPost(String words)
	{
		List<Post> posts = postDao.searchPost(words);
		return posts;
	}
	
	@Override
	@Transactional(readOnly=true)
	public String getPostTile(int pid)
	{
		String title = postDao.postTitle(pid);
		return title;
	}
	
	@Override
	@Transactional
	public void addReading(int pid)
	{
		cache.cacheRemove("page3");//page3为排名，更新一下排名
		postDao.addReading(pid);
	}
	
	
	@Override
	@Transactional(readOnly=true)
	public List<ReportReason> listReportReason()
	{
		List<ReportReason> res = reportDao.listReportReason();
		return res;
	}
	
	@Override
	@Transactional
	public boolean insertReport(Report report)
	{
		try
		{
			reportDao.insertReport(report);
			return true;
		}
		catch (Exception e){
			return false;
		}
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<ReportResult> listReport(int page)
	{
		List<ReportResult> res;
		try
		{
			res = reportDao.listReport((page - 1) * 20);
		}
		catch (Exception e)
		{
			return null;
		}
		return res;
	}
	
	@Override
	@Transactional(readOnly=true)
	public int totalReport()
	{
		return reportDao.totalReport();
	}
	
	@Override
	@Transactional
	public boolean handleReport(int rpid)
	{
		try
		{
			reportDao.handleReport(rpid);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Map<String, Object>> listUnhandleReport()
	{
		return reportDao.listUnhandleReport();
	}
	
	@Override
	@Transactional(readOnly=true)
	public void flushIndex()
	{
		if(cache.cacheResult("page1") == null) {
			List<Post> page1 = new LinkedList<>();
			page1.add(postDao.secondHandPost());
			page1.add(postDao.losePost());
			page1.add(postDao.partnerPost());
			page1.add(postDao.dynamicPost());
			page1.add(postDao.gamePost());
			cache.cachePut("page1", page1);
		}
		if(cache.cacheResult("page2_1") == null) {
			List<Post> page2_1 = postDao.listLost();
			cache.cachePut("page2_1", page2_1);
		}
		if(cache.cacheResult("page2_2") == null){
			List<Association> listAss = assDao.listAssciation();
			cache.cachePut("page2_2", listAss);
		}
		if(cache.cacheResult("page3") == null) {
			List<Map<String,String>> page3 = userDao.listRank();
			cache.cachePut("page3", page3);
		}
	}
	
	@Override
	public void updateCache(Post post)
	{
		if(post.getClass_id() == 2) {
			cache.cacheRemove("page2_1");
		}
		cache.cacheRemove("page1");
		cache.cacheRemove("page3");
	}
}
