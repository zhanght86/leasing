package com.pqsoft.vouchInvoices.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class VouchService {
	
	private String basePath = "vouchInvoice.";
	
	public VouchService() {
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
	
	/**
	 *   修改一条担保费条目信息
	 */
	public int updateVouchItem(Map<String,Object> param){
		return Dao.update(basePath + "updateVouchItem",param);
	}
}
