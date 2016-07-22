package com.pqsoft.lendingLog.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class LendingLogService {

	private String basePath = "lendingLog.";
	
	public Page getPage(Map<String,Object> param){
		return PageUtil.getPage(param, basePath+"getLendingLogPage", basePath+"getLendingLogCount");
	}
}
