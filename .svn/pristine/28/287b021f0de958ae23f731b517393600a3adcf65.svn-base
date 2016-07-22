package com.pqsoft.manufacturerApproval.service;
/**
 *  厂商审批查询
 *  @author 韩晓龙
 */
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class ManufacturerSearchService {
	private String basePath = "manufacturerSearch.";
	
	public ManufacturerSearchService() {
	}
	
	/**
	 *  得到分页数据
	 */
	public Page getPageData(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath + "queryAll",param));
		page.addDate(array, Dao.selectInt(basePath + "queryCount", param));
		return page;
	}
	
	/**
	 *  根据项目编号查询厂家审批意见
	 */
	public Object selectDetailByProjId(Map<String, Object> param) {
		return Dao.selectOne(basePath + "selectDetailByProjId",param);
	}
	/**
	 *  重置厂家审批意见
	 */
	public int spResetDetailByProjId(Map<String, Object> param) {
		return Dao.update(basePath + "spResetDetailByProjId",param);
	}

}
