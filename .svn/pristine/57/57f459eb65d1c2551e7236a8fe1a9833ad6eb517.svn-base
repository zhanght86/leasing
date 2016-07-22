package com.pqsoft.credit.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;

public class CreditServices {
	private final String xmlPath = "credit.";	
	
	/**
	 * 
	* @Title: queryCreditManager
	* @autor wuguowei_jing@163.com 2014-3-29 上午10:35:34
	* @Description: TODO 资信列表查询
	* @param m
	* @return    
	* List<Map<String,Object>>    
	* @throws
	 */
	public Page queryCreditManager(Map<String,Object> m){
		String order = "desc" ;
		if(StringUtils.isNotBlank(m.get("CREATE_TIME1"))||StringUtils.isNotBlank(m.get("CREATE_TIME2")))
			order = "asc" ;
		m.put("ORDER", order) ;
		return PageUtil.getPage(m, xmlPath+"queryCreditManager", xmlPath+"queryCreditManagerCount");
	}
	public Map<String,Object> getCreditInfo(Map<String,Object> m){
		System.out.println("----------------------------m="+m);
		return Dao.selectOne(xmlPath+"getCreditInfo",m);
	}
	
	public static void main(String[] args) {
		System.out.println(StringUtils.isNotBlank(null));
		System.out.println(StringUtils.isNotBlank(""));
	}
}
