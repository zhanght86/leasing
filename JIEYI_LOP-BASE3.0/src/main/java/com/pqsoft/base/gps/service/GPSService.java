package com.pqsoft.base.gps.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;

public class GPSService {
	
	private String basePath = "gps.";

	public Page getGPSMgPageData(Map<String, Object> param) {
		return PageUtil.getPage(param, basePath+"getGPSMgPageData", basePath+"getGPSMgPageDataCount");
	}

	public Map<String,Object> getGPSInfo(Map<String, Object> param) {
		return Dao.selectOne(basePath+"getGPSInfo", param);
	}

	public Map<String,String> getWorkLine(Map<String, Object> param) {
		Map<String,String> res = new HashMap<String,String>();
		List<Map<String,Object>> workLineList = Dao.selectList(basePath+"getWorkLine", param);
		for(Map<String,Object> m : workLineList){
			res.put(StringUtils.nullToOther(m.get("LASTED_TIME"),"0"), m.get("WORK_HOURS").toString());
		}
		return res;
	}

}
