package com.pqsoft.custTour.service;
/**
 *  保险公司管理
 *  @author 韩晓龙
 */
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class CustTourService {
	
	private String basePath = "custTour.";
	
	public CustTourService() {
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
/////////////////////////////////////设备巡视部分开始///////////////////////////////////////////////////////////			
	/**
	 *   插入一条设备巡视记录
	 */
	public int insertEquipTour(Map<String,Object> param){
		return Dao.insert(basePath + "addEquipTour",param);
	}
	
	/**
	 *  得到分页数据
	 */
	public Page getPageDataEquip(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath + "queryEquipAll",param));
		page.addDate(array, Dao.selectInt(basePath + "queryEquipCount", param));
		return page;
	}
	
	/**
	 *   删除一条设备巡视记录
	 */
	public int deleteEquipTour(Map<String, Object> param) {
		return Dao.delete(basePath + "deleteEquipTour",param);
	}
/////////////////////////////////////设备巡视部分结束///////////////////////////////////////////////////////////			
/////////////////////////////////////交往巡视部分开始///////////////////////////////////////////////////////////			
	/**
	 *   插入一条交往巡视记录
	 */
	public int insertContactTour(Map<String,Object> param){
		return Dao.insert(basePath + "addContactTour",param);
	}
	
	/**
	 *  得到分页数据
	 */
	public Page getPageDataContact(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath + "queryContactAll",param));
		page.addDate(array, Dao.selectInt(basePath + "queryContactCount", param));
		return page;
	}
	
	/**
	 *   删除一条交往巡视记录
	 */
	public int deleteContactTour(Map<String, Object> param) {
		return Dao.delete(basePath + "deleteContactTour",param);
	}
/////////////////////////////////////交往巡视部分结束///////////////////////////////////////////////////////////			
/////////////////////////////////////理赔巡视部分开始///////////////////////////////////////////////////////////			
	/**
	 *   插入一条理赔巡视记录
	 */
	public int insertClaimsTour(Map<String,Object> param){
		return Dao.insert(basePath + "addClaimTour",param);
	}
	
	/**
	 *  得到分页数据
	 */
	public Page getPageDataClaims(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath + "queryClaimAll",param));
		page.addDate(array, Dao.selectInt(basePath + "queryClaimCount", param));
		return page;
	}
	
	/**
	 *   删除一条理赔巡视记录
	 */
	public int deleteClaimsTour(Map<String, Object> param) {
		return Dao.delete(basePath + "deleteClaimTour",param);
	}
/////////////////////////////////////理赔巡视部分结束///////////////////////////////////////////////////////////			
}
