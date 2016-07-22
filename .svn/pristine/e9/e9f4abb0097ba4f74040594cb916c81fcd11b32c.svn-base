package com.pqsoft.projectFeeReport.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class ProjectFeeReportTableService {
	/*****项目报表******@auth: king 2014年7月30日 *************************/
	private final String _NAMESPACE="projectFeeReport.";
	
	//项目统计,项目分析
	public Page queryPage(Map<String, Object> m) {
		return PageUtil.getPage(m, _NAMESPACE+"showProjectFeeDate", _NAMESPACE+"showProjectFeeDate_count");
	}
	
	//查询客户表符合条件的客户最小日期及当前日期
	public Map queryDateByProjectFee(){
		return (Map)Dao.selectOne(_NAMESPACE+"PROJECTFEECREATE_TIME");
	}
	
	/**
	 * 查询数据列表
	 * @param m
	 * @return
	 * @author King 2014年8月3日
	 */
	public List<Map<String,Object>> projectFeeList(Map<String,Object> m){
		return Dao.selectList( _NAMESPACE+"showProjectFeeDate", m);
	}
}
