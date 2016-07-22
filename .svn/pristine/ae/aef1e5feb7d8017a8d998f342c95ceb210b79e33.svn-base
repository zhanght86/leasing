package com.pqsoft.base.grantCredit.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class CreditLogService {

	private final String xmlPath = "creditLog.";
	
	/**
	 * 添加授信日志
	 * @author yangxue  2015-07-07
	 * @param map
	 * @return
	 */
	public int doInsertCreditLog(Map<String,Object> map){		
		return Dao.insert(xmlPath+"addCreditLog", map);
	}
	
	/**
	 * 根据授信ID, 查看授信日志
	 * @param map
	 * @return
	 */
	public Object toFindCreditLog(Map<String,Object> map){
		return Dao.selectList(xmlPath+"toFindCreditLog", map);
	}
}
