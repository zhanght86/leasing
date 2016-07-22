package com.pqsoft.white.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class WhiteListManageService {

	private String xmlPath = "White.";
	
	/**
	 * 白名单数据列表查询
	 * 
	 * @param param
	 * @return
	 */
	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, xmlPath + "getWhitePageList", xmlPath + "getWhitePageCount");
	}
	
	
	/**
	 * 导出查询到的数据
	 * @return
	 */
	public List<Map<String, Object>> getexportExcelAll(Map<String,Object> param) {
		return Dao.selectList(xmlPath + "getexportExcelAll", param);
	}
	
	/**
	 * 导出选中的查询数据
	 * @return
	 */
	public List<Map<String, Object>> getexportExcelCheck(Map<String,Object> param) {
		
		return Dao.selectList(xmlPath + "getexportExcelCheck", param);
	}
}
