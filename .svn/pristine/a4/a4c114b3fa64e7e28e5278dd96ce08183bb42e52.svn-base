package com.pqsoft.rentReport.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class RentReportTableService {

	/*****起租报表******@auth: xgm 2014年8月4日 *************************/
	private final String _NAMESPACE="rentReport.";
	
	//起租统计,起租分析
	public Page queryPage(Map<String, Object> m) {
		return PageUtil.getPage(m, _NAMESPACE+"showProjectDate", _NAMESPACE+"showProjectDate_count");
	}
	
	//查询客户表符合条件的客户最小日期及当前日期
	public Map queryDateByProject(){
		return (Map)Dao.selectOne(_NAMESPACE+"PROJECTCREATE_TIME");
	}
	public List getm(Map json)
	{
		return Dao.selectList("rentReport.PROJECT_REPORT_TYPE",json);
	}
	/**
	 * 查询数据列表
	 * @param m
	 * @return
	 * @author King 2014年8月3日
	 */
	public List<Map<String,Object>> projectList(Map<String,Object> m){
		return Dao.selectList( _NAMESPACE+"showProjectDate", m);
	}
}
