package com.pqsoft.documentApp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class DocProductService {
	private final String xmlPath = "dossierApp.product.";
	/**
	 * 权证接口查询方法（设备表）
	 * @param params
	 * @return
	 */
	public Map<String, Object> queryDocProductById(Map<String, Object> params) {
		System.out.println("params:"+params.get("ID"));
		params.put("PROJECT_ID", params.get("ID"));
		Map<String, Object> oo= new HashMap<String, Object>();
		List<String> type=Dao.selectList(xmlPath+"queryProductById", params);
		oo.put("type", type);
		return oo;
	}
	
	
	/**
	 * 权证接口添加方法（设备表）
	 * @param params
	 * @return
	 */
	public int addDocProductById(Map<String, Object> params) {
		params.put("PROJECT_ID", params.get("ID"));
		Map<String, Object> oo= new HashMap<String, Object>();
		List<Map<String, Object>> type=Dao.selectList(xmlPath+"queryProductById", params);
		for(Map<String, Object>  obj:type){
		   oo.put("ID", obj.get("ID"));
		   oo.put("PROJECT_ID", obj.get("ID"));
		   oo.putAll(obj);
		   oo.put("PRODUCT_PRICE", String.valueOf(obj.get("PRODUCT_PRICE")));
		}
		return Dao.insert(xmlPath+"insertDocProductByIds", oo);
	}


}
