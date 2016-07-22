package com.pqsoft.call.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class CallPlayScreenService {
	
	String xmlPath = "callPersonnelConfiguration.";
	
	public List<Map<String,Object>> selectCustDate(Map<String,Object> param){
		return Dao.selectList(xmlPath + "selectCustDate", param);
	}
	
	public List<Map<String,Object>> selectProDate(Map<String,Object> param){
		return Dao.selectList(xmlPath + "selectProDate", param);
	}
	
	public List<Map<String,Object>> selectRepaymentHistoryDate(Map<String,Object> param){
		return Dao.selectList(xmlPath + "selectRepaymentHistoryDate", param);
	}

}
