package com.pqsoft.capitalPool.companyPool.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;


public class ComDepositPoolService {
	private String basePath = "ComDepositPool.";
	
	public Page getCompDepositPageData(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getComDepositPoolList",param));
		page.addDate(array, Dao.selectInt(basePath+"getComDepositPoolCount", param));
		return page;
	}

}
