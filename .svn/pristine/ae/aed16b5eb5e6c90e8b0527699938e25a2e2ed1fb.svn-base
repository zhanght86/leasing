package com.pqsoft.fundReport.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class FundReportTableService {
	/*****客户报表******@auth: king 2014年7月30日 *************************/
	private final String _NAMESPACE="fundReport.";
	
	//客户统计,客户分析
	public Page queryPage(Map<String, Object> m) {
		return PageUtil.getPage(m, _NAMESPACE+"showFundList", _NAMESPACE+"showFundList_count");
	}
	
	/**
	 * 实收统计
	 * @param m
	 * @return
	 * @author King 2014年8月4日
	 */
	public Page querySSPage(Map<String, Object> m) {
		return PageUtil.getPage(m, _NAMESPACE+"showFundSSList", _NAMESPACE+"showFundSSList_count");
	}
	
	public Page queryWSPage(Map<String,Object> m){
		return PageUtil.getPage(m, _NAMESPACE+"showFundWSList", _NAMESPACE+"showFundWSList_count");
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
	public List<Map<String,Object>> fundList(Map<String,Object> m){
		return Dao.selectList( _NAMESPACE+"showFundList", m);
	}
	/**
	 * 查询应收未收数据列表
	 * @param m
	 * @return
	 * @author King 2014年8月3日
	 */
	public List<Map<String,Object>> fundWSList(Map<String,Object> m){
		return Dao.selectList( _NAMESPACE+"showFundWSList", m);
	}
	/**
	 * 查询实收数据列表
	 * @param m
	 * @return
	 * @author King 2014年8月3日
	 */
	public List<Map<String,Object>> fundSSList(Map<String,Object> m){
		return Dao.selectList( _NAMESPACE+"showFundSSList", m);
	}
	
}
