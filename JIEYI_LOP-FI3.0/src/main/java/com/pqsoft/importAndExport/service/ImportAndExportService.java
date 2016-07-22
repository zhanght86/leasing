package com.pqsoft.importAndExport.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class ImportAndExportService {
	private String path = "importAndExport.";
	
	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, path+"getPageList", path+"getPageCount");
	}
	
	public Page importPage(Map<String,Object> param){
		return PageUtil.getPage(param, path+"importPageList", path+"importPageCount");
	}
	
	public List<Map<String,Object>> getData(Map<String,Object> param){
		return Dao.selectList(path+"getData", param);
	}
	
	public List<Map<String,Object>> getZJE(){
		return Dao.selectList(path+ "getZJE");
	}
	
	public int updateFund(Map<String,Object> param){
		return Dao.update(path+ "updateFund",param);
	}
	
}
