package com.pqsoft.base.channel.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.BaseUtil;

public class AssureAssetsManagerService {
	private String basePath = "AssureAssets.";
	
	public Page	getAssetsPageData(Map<String,Object> param ){
		Page page = new Page(param);
//    	Map<String,Object> SUP_MAP = BaseUtil.getSup();
//		if(SUP_MAP!=null){
//			param.put("SUP_ID",SUP_MAP.get("SUP_ID"));
//		}
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getAssureAssetsList",param));
		page.addDate(array, Dao.selectInt(basePath+"getAssureAssetsCount", param));
		return page;
	}
	
	public List<Object> getDropListData(Map<String,Object> param){
		return Dao.selectList(basePath+"getDropListData", param);
	}

}
