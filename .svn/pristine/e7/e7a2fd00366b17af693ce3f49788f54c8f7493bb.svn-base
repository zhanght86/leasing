package com.pqsoft.target.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.BaseUtil;

public class TargetCompanyService {

	private String basePath = "targetCompany.";
	
	public Page getPageData(Map<String,Object> param){
		BaseUtil.getDataAllAuth(param);
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"toMgTargetCompany",param));
		page.addDate(array, Dao.selectInt(basePath+"toMgTargetCompanyC", param));
		return page;
	}
}
