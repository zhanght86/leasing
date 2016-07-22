package com.pqsoft.performance.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class AssessmentResultService {
	
	String xmlPath = "performance.R.";//xml路径
	
	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, xmlPath+"getPageList", xmlPath+"getPageCount");
	}
	
	public Map<String,Object> getResult(Map<String, Object> param) {
		return Dao.selectOne(xmlPath + "getResult", param);
	}
	
	public List<Map<String,Object>> getResultDetail(Map<String, Object> param) {
		return Dao.selectList(xmlPath + "getResultDetail", param);
	}

}
