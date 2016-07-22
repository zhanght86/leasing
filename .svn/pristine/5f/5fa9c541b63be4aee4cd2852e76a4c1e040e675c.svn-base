package com.pqsoft.notice.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class NoticeService {
	 private String basepath = "notice.";
	/**
	 * 分页显示
	 * @param param
	 * @return
	 */
	public Page getPageData(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basepath+"queryNoticeList",param));
		page.addDate(array, Dao.selectInt(basepath+"selectNoticeCount", param));
		return page;
	}
	
	public int saveNotice(Map<String,Object> param){
		return Dao.insert(basepath+"saveNotice", param);
	}
	
	/**
	 * 查询序列
	 * 
	 * @param m
	 */
	public int selectSeq() {
		int i = Integer.parseInt(((Map<String,Object>)Dao.selectOne(basepath+"selectSeq")).get("SEQ").toString());
		return i;
	}
	
	public Map<String,Object> selectImageById(Map<String,Object> param){
		return Dao.selectOne(basepath+"selectImageById", param);
	}
	
	public Map<String,Object> selectNoticeyId(Map<String,Object> param){
		return Dao.selectOne(basepath+"selectNoticeyId", param);
	}
	
	public int updateNoticeById(Map<String,Object> param){
		return Dao.update(basepath+"updateNoticeById", param);
	}
	
	public int delNoticeById(Map<String,Object> param){
		return Dao.delete(basepath+"delNoticeById", param);
	}
	
}
