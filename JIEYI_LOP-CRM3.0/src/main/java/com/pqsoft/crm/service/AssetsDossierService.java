package com.pqsoft.crm.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class AssetsDossierService {
	
	String basePath = "crm.AssetsDossier.";

    /**        ---------------------------------------------设备档案---------------------          **/
	public Page getEquipmentListPage(Map<String,Object> map){
		Page page = new Page(map);
		page.addDate(JSONArray.fromObject(Dao.selectList(basePath+"getEquipmentPageList", map)), 10);
		return page;
	}

	public int doDeleteEquipment(Map<String,Object> m) {
		return Dao.delete(basePath+"deleteEquipment", m);
	}
	public int saveEquipment(Map<String,Object> param){
		return Dao.insert(basePath+"saveEquipment",param);
	}

	public int updateEquipment(Map<String,Object> param){
		return Dao.update(basePath+"updateEquipment",param);
	}

    /**        ---------------------------------------------房产档案---------------------          **/
	
	
	public Page getHouseListPage(Map<String,Object> map) {
		Page page = new Page(map);
		page.addDate(JSONArray.fromObject(Dao.selectList(basePath+"getHousePageList", map)), 10);
		return page;
	}

	public int doDeleteHouse(Map<String,Object> m) {
		return Dao.delete(basePath+"deleteHouse", m);
	}
	public int saveHouse(Map<String,Object> param){
		return Dao.insert(basePath+"saveHouse",param);
	}
	public int updateHouse(Map<String,Object> param){
		return Dao.update(basePath+"updateHouse",param);
	}
	
	


    /**        ---------------------------------------------土地档案---------------------          **/
	
	
	public Page getEstateListPage(Map<String,Object> map){
		Page page = new Page(map);
		page.addDate(JSONArray.fromObject(Dao.selectList(basePath+"getEstatePageList", map)), 10);
		return page;
	}

	public int doDeleteEstate(Map<String,Object> m) {
		return Dao.delete(basePath+"deleteEstate", m);
	}
	public int saveEstate(Map<String,Object> param){
		return Dao.insert(basePath+"saveEstate",param);
	}
	public int updateEstate(Map<String,Object> param){
		return Dao.update(basePath+"updateEstate",param);
	}
	
	

    /**        ---------------------------------------------其他资产档案---------------------          **/
	
	
	public Page getOtherListPage(Map<String,Object> map){
		Page page = new Page(map);
		page.addDate(JSONArray.fromObject(Dao.selectList(basePath+"getOtherPageList", map)), 10);
		return page;
	}

	public int doDeleteOther(Map<String,Object> m) {
		return Dao.delete(basePath+"deleteOther", m);
	}
	public int saveOther(Map<String,Object> param){
		return Dao.insert(basePath+"saveOther",param);
	}
	public int updateOther(Map<String,Object> param){
		return Dao.update(basePath+"updateOther",param);
	}
	
}
