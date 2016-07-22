package com.pqsoft.leaseDate.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.PageUtil;

public class LeaseDateService {
	
	private String basePath = "leaseDate.";
	
	public Page queryPage(Map<String,Object> param){
		param.put("TYPE_", "还款计划状态");
		param.put("TYPE1_", "业务类型");
		BaseUtil.getDataAllAuth(param);
		
		return PageUtil.getPage(param, basePath+"getAllPayTask", basePath+"getAllPayTask_count");
	}
}
