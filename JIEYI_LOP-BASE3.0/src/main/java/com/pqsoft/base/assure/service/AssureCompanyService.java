package com.pqsoft.base.assure.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class AssureCompanyService {
	private String basePath = "AssureCompany.";

	public Page getPageData(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getCompanyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getCompanyCount", param));
		return page;
	}
	
	public int addCompany(Map<String,Object> param){
		return Dao.insert(basePath+"addCompany",param);
	}
	
	public int updateCompany(Map<String,Object> param){
		return Dao.update(basePath+"updateCompany", param);
	}
	
	public int delCompany(Map<String,Object> param){
		return Dao.update(basePath+"deleteCompany", param);
	}
	
	public List<Object> getAllArea(){
		return Dao.selectList(basePath+"selectArea");
	}
	
	public List<Object> getAreaDownByParentId(Map<String,Object> param){
		return Dao.selectList(basePath+"selectProvOrCity", param);
	}

}
