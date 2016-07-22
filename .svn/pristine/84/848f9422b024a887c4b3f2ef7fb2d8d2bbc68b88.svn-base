package com.pqsoft.payReport.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class PayReportTableService {
	/*****支付表报表******@auth: leeds 2014年8月4日 *************************/
	private final String _NAMESPACE="payReport.";
	
	//支付表统计,支付表分析
	public Page queryPage(Map<String, Object> m) {
		return PageUtil.getPage(m, _NAMESPACE+"showPayDate", _NAMESPACE+"showPayDate_count");
	}
	
	//--------------查询支付表符合条件的客户最小日期及当前日期
	public Map queryDateByPay(){
		return (Map)Dao.selectOne(_NAMESPACE+"PAYCREATE_TIME");
	}
	
	/**
	 * 查询数据列表
	 * @param m
	 * @return
	 * @author leeds 2014年8月4日
	 */
	public List<Map<String,Object>> payList(Map<String,Object> m){
		return Dao.selectList( _NAMESPACE+"showPayDate", m);
	}
	
}
