package com.pqsoft.credit.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class CreditGuarantorService {
	private final String xmlPath = "creditGuarantor.";	
	
	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, xmlPath+"getPageList", xmlPath+"getPageCount");
	}
	
	public List<Map<String,Object>> getGuarantorList(Map<String,Object> param){
		return Dao.selectList(xmlPath + "getGuarantorList", param);
	}
	
}
