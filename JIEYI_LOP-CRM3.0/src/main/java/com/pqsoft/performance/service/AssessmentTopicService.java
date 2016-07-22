package com.pqsoft.performance.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class AssessmentTopicService {
	
	String xmlPath = "performance.T.";//xml路径
	
	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, xmlPath+"getPageList", xmlPath+"getPageCount");
	}
	
	public boolean doDeleteAT(Map<String, Object> param) {
		return Dao.delete(xmlPath + "deleteAT", param) > 0 ? true : false;
	}
	
	public boolean doAddAT(Map<String, Object> param) {
		String ID = Dao.getSequence("SEQ_T_ASSESSMENT_TOPIC");
		param.put("ID", ID);
		return Dao.delete(xmlPath + "addAT", param) > 0 ? true : false;
	}
	
	public boolean doUpdateAT(Map<String, Object> param) {
		return Dao.delete(xmlPath + "updateAT", param) > 0 ? true : false;
	}
	
	public Map<String,Object> getATData(Map<String, Object> param) {
		return Dao.selectOne(xmlPath + "getATData", param);
	}

}
