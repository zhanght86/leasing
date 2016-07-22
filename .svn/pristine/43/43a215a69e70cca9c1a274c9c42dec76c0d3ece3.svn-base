package com.pqsoft.addTax.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.InvoiceUtil;

public class VatOtherFeeService {
	
	private String basePath = "OtherInvoice.";
	
	/**
	 * 增值税服务管理费票据申请
	 * @param param
	 * @return
	 */
	public Page getVatManagerApplyPageData(Map<String,Object> param){
		List<String> listItem = new ArrayList<String>();
		listItem.add("管理费");
		param.put("BEGINNING_NAMES", listItem);
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getVatOtherApplyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getVatOtherApplyCount", param));
		return page ; 
	}
	
	/**
	 * 增值税留购价款票据申请
	 * @param param
	 * @return
	 */
	public Page getVatLeavePurApplyPageData(Map<String,Object> param ){
		List<String> listItem = new ArrayList<String>();
		listItem.add("留购价款");
		param.put("BEGINNING_NAMES", listItem);
		param.put("LEAVING", "yes");
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getVatOtherApplyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getVatOtherApplyCount", param));
		return page ; 
	}
 
	public List<Object> getAllOtherVatInvoiceMess(Map<String,Object> param){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		param.put("INVOICE_TYPE", "增值税发票");
		return Dao.selectList(basePath+"getAllOtherInvoiceMess", param);
	} 

}
