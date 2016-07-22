package com.pqsoft.zcfl.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class AssetsReportTableService {
	/*****租赁物报表******@auth: fuyulong 2014年8月04日 *************************/
	private final String _NAMESPACE="leaseReport.";
	
	//租赁物统计,租赁物分析
	public Page queryPage(Map<String, Object> m) {
		return PageUtil.getPage(m, _NAMESPACE+"showLeaseWdyDate", _NAMESPACE+"showLeaseWdyDate_count");
	}
	
	//查询项目创建时间
	public Map queryDateByLease(){
		return (Map)Dao.selectOne(_NAMESPACE+"getDYDJTime");
	}
	
	/**
	 * 未抵押数据
	 * @param
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2014-8-4  下午12:18:32
	 */
	public List<Map<String,Object>> leaseList(Map<String,Object> m){
		return Dao.selectList( _NAMESPACE+"showLeaseWdyDate", m);
	}
	
}
