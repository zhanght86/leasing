package com.pqsoft.clientReport.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class ClientReportTableService {
	/*****客户报表******@auth: king 2014年7月30日 *************************/
	private final String _NAMESPACE="clientReport.";
	
	//客户统计,客户分析
	public Page queryPage(Map<String, Object> m) {
		return PageUtil.getPage(m, _NAMESPACE+"showClientDate", _NAMESPACE+"showClientDate_count");
	}
	
	//查询客户表符合条件的客户最小日期及当前日期
	public Map queryDateByCust(){
		return (Map)Dao.selectOne(_NAMESPACE+"CUSTCREATE_TIME");
	}
	
	/**
	 * 查询数据列表
	 * @param m
	 * @return
	 * @author King 2014年8月3日
	 */
	public List<Map<String,Object>> clientList(Map<String,Object> m){
		return Dao.selectList( _NAMESPACE+"showClientDate", m);
	}
	
}
