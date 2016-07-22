package com.pqsoft.insure.service;
/**
 *  险种管理
 *  @author 韩晓龙
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class InsureTypeManagementService {
	
	private String basePath = "insureTypeDetail.";
	
	public InsureTypeManagementService() {
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
	 *  查询全部险种信息
	 */
	public List getAll() {
		return Dao.selectList(basePath + "selectAll");
	}
	
	
	/** 
	 *  插入一条险种记录
	 */ 
	public int insertInsureType(Map<String, Object> param) {
		return Dao.insert(basePath+"addInsureType",param);
	}
	
	/**
	 *  修改一条险种记录
	 */
	public int updateInsureType(Map<String, Object> param) {
		return Dao.update(basePath+"updateInsureType",param);
	}
	
	/**
	 *  修改一条险种记录
	 */
	public int deleteInsureType(Map<String, Object> param) {
		return Dao.delete(basePath+"deleteInsureType",param);
	}
}
