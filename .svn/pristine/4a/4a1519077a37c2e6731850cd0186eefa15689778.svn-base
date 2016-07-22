package com.pqsoft.businessTax.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.InvoiceUtil;

public class BusTaxOtherFeeService {
	private String basePath = "OtherInvoice.";
	
	public Page getBusTaxManagerApplyPageData(Map<String,Object> param){
		List<String> listItem = new ArrayList<String>();
		listItem.add("管理费");
		param.put("BEGINNING_NAMES", listItem);
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getBusTaxOtherApplyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getBusTaxOtherApplyCount", param));
		return page ; 
	}

	public Page getBusTaxLeavePurApplyPageData(Map<String,Object> param){
		List<String> listItem = new ArrayList<String>();
		listItem.add("留购价款");
		param.put("BEGINNING_NAMES", listItem);
		param.put("LEAVING", "yes");
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getBusTaxOtherApplyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getBusTaxOtherApplyCount", param));
		return page ; 
	}
	
	public List<Object> getAllOtherBusInvoiceMess(Map<String,Object> param){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		param.put("INVOICE_TYPE","营业税发票");
		return Dao.selectList(basePath+"getAllOtherInvoiceMess", param);
	}
}
