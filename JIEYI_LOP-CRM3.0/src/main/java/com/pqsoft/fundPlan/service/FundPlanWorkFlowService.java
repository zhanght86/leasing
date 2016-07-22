package com.pqsoft.fundPlan.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class FundPlanWorkFlowService {
	private String basePath = "fundPlan.";

	public FundPlanWorkFlowService() {
	}

	/**
	 * 得到分页数据
	 */
	public Page getPageData(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath
				+ "queryAll", param));
		page.addDate(array, Dao.selectInt(basePath + "queryCount", param));
		return page;
	}
	
	public List getDetail(Map<String, Object> param) {
		return Dao.selectList(basePath	+ "queryDetail", param);
	}
	
	/**
	 *  项目作废查询用
	 */
	public List getDetailProjectInvalid(Map<String, Object> param) {
		return Dao.selectList(basePath	+ "queryDetailProjectInvalid", param);
	}
}
