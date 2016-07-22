package com.pqsoft.customers.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class custFinanceService {
 
	private final String xmlPath = "custFinance.";
	
	/**
	 * 查看财报
	 * getFinance
	 * @date 2013-11-20 下午10:44:22
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getFinance(Map<String,Object> map){
		return Dao.selectOne(xmlPath+"getCustFinance", map);
	} 
	
	/**
	 * 添加法人财报
	 * doInsertFinance
	 * @date 2013-11-20 下午10:46:30
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doInsertFinance(Map<String,Object> map){
		return Dao.insert(xmlPath+"doInsertCaiBao", map);
	}
	
	/**
	 * 修改法人财报
	 * doUpdateFinance
	 * @date 2013-11-20 下午10:47:18
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doUpdateFinance(Map<String,Object> map){
		return Dao.update(xmlPath+"doUpdateCaiBao",map);
	}
}
