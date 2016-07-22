package com.pqsoft.documentApp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class DocProjectService {
	private final String xmlPath = "dossierApp.project.";

	/**
	 * 权证接口查询方法（项目表）
	 * @param params
	 * @return
	 */
	public Map<String, Object> queryDocProjectById(Map<String, Object> params) {
		System.out.println("params:"+params.get("ID"));
		params.put("ID", params.get("ID"));
		Map<String, Object> oo= new HashMap<String, Object>();
		List<String> heads=Dao.selectList(xmlPath+"queryheadClientById", params);
		oo.put("heads", heads);	
		return oo;
	}

	/**
	 * 权证接口添加方法（项目表）
	 * @param params
	 * @return
	 */
	public int addDocProjectById(Map<String, Object> params) {
		params.put("ID", params.get("ID"));
		List<Map<String, Object>> heads=Dao.selectList(xmlPath+"queryheadClientById", params);
		Map<String, Object> oo = new HashMap<String, Object>();
		for(Map<String, Object>  obj:heads){
			oo.put("ID", params.get("ID"));
			oo.putAll(obj);
//			oo.put("PROJECT_ID", obj.get("PROJECT_ID"));
//			oo.put("CLIENT_CODE", obj.get("CLIENT_CODE"));
//			oo.put("CLIENT_NAME", obj.get("CLIENT_NAME"));
//			oo.put("CLIENT_TYPE", obj.get("CLIENT_TYPE"));
//			oo.put("MARRIAGE_TYPE", obj.get("MARRIAGE_TYPE"));
//			oo.put("CLERK_CODE", obj.get("CLERK_CODE"));
//			oo.put("INDUSTRY_TYPE", obj.get("INDUSTRY_TYPE"));
//			oo.put("PLATFORM_TYPE", obj.get("PLATFORM_TYPE"));
//			oo.put("AREA_NAME", obj.get("CITY_NAME"));
//			oo.put("PROJECT_STATUS", obj.get("STATUS"));
//			//oo.put("PROJECT_PROPERTY", obj.get(""));
//			oo.put("PROJECT_CODE", obj.get("PRO_CODE"));
//			oo.put("PROJECT_NAME", obj.get("PRO_NAME"));
			oo.put("LEASE_ALL_PRICE", String.valueOf(obj.get("LEASE_ALL_PRICE")));
		}		
		return Dao.insert(xmlPath+"insertDocProjectByIds", oo);
	}

}
