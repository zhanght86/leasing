package com.pqsoft.invoiceForRed.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class InvoiceForRedService {
    private String basePath = "InvoiceForRed.";
	public Page getForRedPage(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getForRedApplyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getForRedApplyCount", param));
		return page ; 
	}
	
	public int updateInvoiceStatus(Map<String,Object> param){
		return Dao.update(basePath+"updateInvoiceStatus", param);
	}
	
	public Map<String,Object> getInvoiceMess(Map<String,Object> param){
		return Dao.selectOne(basePath+"updateInvoiceStatus", param);
	}
	
	public void updateInvoiceDetailStatus(Map<String,Object> param){
		
		Dao.insert(basePath+"insertIntoInvoiceDetailsCHZF", param);
		Dao.delete(basePath+"deleteInvoiceDetails", param);
	}
	
	
}
