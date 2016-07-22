package com.pqsoft.leeds.idCard.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.BaseUtil;

public class IdCardSrvice {

	private String path = "idCard.";
	
	public List<Map<String,Object>> getIdCard(Map<String,Object> param){
		return Dao.selectList(path+"getIdCard",param);
	}
	
	public Page getIdCardPage(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(path+"getIdCardPage",param));
		page.addDate(array, Dao.selectInt(path+"getIdCardCount", param));
		return page;
	}
	
	public Map<String,Object> getIdCardById(Map<String,Object> param){
		return Dao.selectOne(path+"getIdCardById",param);
	}
	
	public List<Map<String,Object>> getIdCardNew(Map<String,Object> param){
		return Dao.selectList(path+"getIdCardNew",param);
	}
	
	public Page getIdCardPageNew(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(path+"getIdCardPageNew",param));
		page.addDate(array, Dao.selectInt(path+"getIdCardCountNew", param));
		return page;
	}
}
