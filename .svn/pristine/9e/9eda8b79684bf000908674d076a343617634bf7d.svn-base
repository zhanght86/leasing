package com.pqsoft.crm.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class CreditDossierService {
	
	String basePath = "crm.CreditDossier.";

    /**        ---------------------------------------------债务档案---------------------          **/
	public Page getDebtListPage(Map<String,Object> map){
		Page page = new Page(map);
		page.addDate(JSONArray.fromObject(Dao.selectList(basePath+"getDebtPageList", map)), 10);
		return page;
	}

	public int doDeleteDebt(Map<String,Object> m) {
		return Dao.delete(basePath+"deleteDebt", m);
	}
	public int saveDebt(Map<String,Object> param){
		return Dao.insert(basePath+"saveDebt",param);
	}
	public int updateDebt(Map<String,Object> param){
		return Dao.update(basePath+"updateDebt",param);
	}
	

    /**        ---------------------------------------------信誉档案---------------------          **/

	
	public Page getReputationListPage(Map<String,Object> map){
		Page page = new Page(map);
		page.addDate(JSONArray.fromObject(Dao.selectList(basePath+"getReputationPageList", map)), 10);
		return page;
	}

	public int doDeleteReputation(Map<String,Object> m) {
		return Dao.delete(basePath+"deleteReputation", m);
	}
	public int saveReputation(Map<String,Object> param){
		return Dao.insert(basePath+"saveReputation",param);
	}

	public int updateReputation(Map<String,Object> param){
		return Dao.insert(basePath+"updateReputation",param);
	}
	


    /**        ---------------------------------------------法院档案---------------------          **/
	
	
	public Page getCourtListPage(Map<String,Object> map){
		Page page = new Page(map);
		page.addDate(JSONArray.fromObject(Dao.selectList(basePath+"getCourtPageList", map)), 10);
		return page;
	}

	
	public int doDeleteCourt(Map<String,Object> m) {
		return Dao.delete(basePath+"deleteCourt", m);
	}
	public int doDeleteCircles(Map<String,Object> m) {
		return Dao.delete(basePath+"doDeleteCircles", m);
	}
	public int doDeleteTax(Map<String,Object> m) {
		return Dao.delete(basePath+"doDeleteTax", m);
	}
	public int saveCourt(Map<String,Object> param){
		return Dao.insert(basePath+"saveCourt",param);
	}

	public int saveCircles(Map<String,Object> param){
		return Dao.insert(basePath+"saveCircles",param);
	}
	public int saveTax(Map<String,Object> param){
		return Dao.insert(basePath+"saveTax",param);
	}
	public int updateCourt(Map<String,Object> param){
		return Dao.update(basePath+"updateCourt",param);
	}
	
	public int updateCircles(Map<String,Object> param){
		return Dao.update(basePath+"updateCircles",param);
	}
	public int updateTax(Map<String,Object> param){
		return Dao.update(basePath+"updateTax",param);
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
		return Dao.insert(basePath+"updateOther",param);
	}
}
