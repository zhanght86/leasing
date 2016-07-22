package com.pqsoft.zcfl.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class DataSourceService {
	String xmlPath = "zcfl.DS.";//xml路径
	
	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, xmlPath+"getPageList", xmlPath+"getPageCount");
	}
	

	public boolean doAddDS(Map<String, Object> param) {
		String ID = Dao.getSequence("SEQ_ZCFL_SQLTP");
		param.put("ID", ID);
		return Dao.delete(xmlPath + "addDS", param) > 0 ? true : false;
	}

	public boolean doDeleteDS(Map<String, Object> param) {
		return Dao.delete(xmlPath + "deleteDS", param) > 0 ? true : false;
	}
	
	public boolean doUpdateDS(Map<String, Object> param) {
		return Dao.delete(xmlPath + "updateDS", param) > 0 ? true : false;
	}
	
	public Map<String,Object> getDSData(Map<String, Object> param) {
		return Dao.selectOne(xmlPath + "getDSData", param);
	}
	
}
