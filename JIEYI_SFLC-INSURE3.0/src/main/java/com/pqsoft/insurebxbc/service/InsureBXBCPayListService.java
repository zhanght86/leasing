package com.pqsoft.insurebxbc.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class InsureBXBCPayListService {
	
	private String basePath = "insurebxbczfList.";
	
	public InsureBXBCPayListService() {
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
}
