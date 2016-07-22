package com.pqsoft.lmrm.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class DmvService {
String xmlPath = "lmrm.DMV.";//xml路径
	
	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, xmlPath+"getPageList", xmlPath+"getPageCount");
	}
	
	public List<Map<String,Object>> selectAreaList(){
		return Dao.selectList(xmlPath+"selectAreaList");
	}
	
	public boolean addDmv(Map<String, Object> param){
		param.put("ADD_ID", Dao.getSequence("SEQ_FIL_DMV"));
		return Dao.insert(xmlPath+"addDmv",param) > 0;
	}
	
	public boolean deleteDmv(Map<String, Object> param){
		return Dao.insert(xmlPath+"deleteDmv",param) > 0;
	}
	
	public boolean updateDmv(Map<String, Object> param){
		return Dao.update(xmlPath+"updateDmv",param) > 0;
	}
	
}
