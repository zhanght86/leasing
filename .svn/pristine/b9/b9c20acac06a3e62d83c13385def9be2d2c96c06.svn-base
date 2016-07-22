package com.pqsoft.insureSettlement.service;
/**
 *  工具Service
 *  @author hanxl
 */
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class InsureSettlementUtilService {
	
private String basePath = "insureSettlementUtil.";
	
	public InsureSettlementUtilService() {
	}
	
	/**
	 *  得到供应商
	 */
	public Object getSuppliers() {
		return Dao.selectList(basePath + "suppliers");
	}
	/**
	 *  得到一个供应商下的所有客户
	 */
	public JSONArray getClients(Map<String, Object> param) {
		return JSONArray.fromObject(Dao.selectList(basePath + "clients", param));
	}
	
	/**
	 *  得到一个客户下的所有项目编号
	 */
	public JSONArray getProjects(Map<String, Object> param) {
		return JSONArray.fromObject(Dao.selectList(basePath + "projIds", param));
	}

}
