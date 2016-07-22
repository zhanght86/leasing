package com.pqsoft.receipt.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.InvoiceUtil;

public class ReceiptService {
	private String basePath = "Receipt.";
	
	public Page getReceiptDbApplyPageData(Map<String,Object> param ){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		param.put("INVOICE_TYPE", "收据");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getDbReceiptApplyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getDbReceiptApplyCount", param));
		return page;
	}
	
	public Page getReceiptDbRApplyPageData(Map<String,Object> param ){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		param.put("INVOICE_TYPE", "收据");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getDBRReceiptApplyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getDBRReceiptApplyCount", param));
		return page;
	}
	
	public Page getReceiptCSApplyPageData(Map<String,Object> param ){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		param.put("INVOICE_TYPE", "收据");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getCSReceiptApplyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getCSReceiptApplyCount", param));
		return page;
	}
	
	public Page getReceiptFirstPayApplyPageData(Map<String,Object> param ){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		param.put("INVOICE_TYPE", "收据");
		
		//
		param.put("COLUMNSITEM", Dao.selectOne(basePath+"sjkpColumnsMaxMoney"));
		param.put("COLUMNSITEMTOTALMONEY", Dao.selectOne(basePath+"sjkpColumnsTotalMoney"));
		
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getReceiptFirstpayApplyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getReceiptFirstPayApplyCount", param));
		return page;
	}
	
	public Page getReceiptRentVirtualApplyPageData(Map<String,Object> param){
		param.put("INVOICE_TYPE", "收据");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getReceiptRentVirtualApplyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getReceiptRentVirtualApplyCount", param));
		return page;
	}
	
	public Page getReceiptRentApplyPageData(Map<String,Object> param){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		param.put("INVOICE_TYPE", "收据");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getReceiptRentApplyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getReceiptRentApplyCount", param));
		return page;
	}
	
	public Page getReceiptHeXiaoPageData(Map<String,Object> param ){
		param.put("INVOICE_TYPE", "收据");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getReceiptHeXiaoList",param));
		page.addDate(array, Dao.selectInt(basePath+"getReceiptHeXiaoCount", param));
		return page;
	}
	
	public Page getReceiptSearchPageData(Map<String,Object> param ){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getReceiptSearchList",param));
		page.addDate(array, Dao.selectInt(basePath+"getReceiptSearchCount", param));
		return page;
	}
	
	public Excel getReceiptExcelData(Map<String,Object> param ){
		List<Map<String,Object>>  pageList = new ArrayList<Map<String,Object>>();
		param.put("INVOICE_TYPE", "收据");
	    pageList = Dao.selectList("BusTaxInvoice.getBusTaxExportData", param);
	    for (Map<String, Object> map : pageList) {
			Dao.update("BusTaxInvoice.updateExportMemo", map);
		}
	    //表头
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
		title.put("REMARK", "备注");
		title.put("SUP_SHORTNAME", "供应商");
		title.put("FUND_NUM", "租金编号");
		title.put("INVOICE_TO_NAME", "开票对象");
		title.put("ITEM_NAME1", "项目1名称");
		title.put("ITEM_FACT_AMT1", "项目1单价");
		title.put("ITEM_NUM1", "项目1数量");
		title.put("ITEM_NAME2", "项目2名称");
		title.put("ITEM_FACT_AMT2", "项目2单价");
		title.put("ITEM_NUM2", "项目2数量");
		title.put("ITEM_NAME3", "项目3名称");
		title.put("ITEM_FACT_AMT3", "项目3单价");
		title.put("ITEM_NUM3", "项目3数量");
		title.put("ITEM_NAME4", "项目4名称");
		title.put("ITEM_FACT_AMT4", "项目4单价");
		title.put("ITEM_NUM4", "项目4数量");
		
		title.put("ITEM_NAME5", "项目5名称");
		title.put("ITEM_FACT_AMT5", "项目5单价");
		title.put("ITEM_NUM5", "项目5数量");
		
		title.put("ITEM_NAME6", "项目6名称");
		title.put("ITEM_FACT_AMT6", "项目6单价");
		title.put("ITEM_NUM6", "项目6数量");
		
		title.put("ITEM_NAME7", "项目7名称");
		title.put("ITEM_FACT_AMT7", "项目7单价");
		title.put("ITEM_NUM7", "项目7数量");
		
		title.put("ITEM_NAME8", "项目8名称");
		title.put("ITEM_FACT_AMT8", "项目8单价");
		title.put("ITEM_NUM8", "项目8数量");
		
		title.put("ITEM_NAME9", "项目9名称");
		title.put("ITEM_FACT_AMT9", "项目9单价");
		title.put("ITEM_NUM9", "项目9数量");
		//封装excel
		Excel excel = new Excel();
		excel.addData(pageList);
		excel.addTitle(title);
		excel.hasRownum();
		excel.defaultFormat(false);
		return excel;
	}

	public List<Object> getAllDbReceiptMess(Map<String,Object> param){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		return Dao.selectList(basePath+"getAllDbReceiptMess", param);
	}
	
	public List<Object> getAllDBRReceiptMess(Map<String,Object> param){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		return Dao.selectList(basePath+"getAllDBRReceiptMess", param);
	}
	
	public List<Object> getAllCSReceiptMess(Map<String,Object> param){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		return Dao.selectList(basePath+"getAllCSReceiptMess", param);
	}
	
	public List<Object> getAllFirstReceiptMess(Map<String,Object> param){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		
		param.put("COLUMNSITEM", Dao.selectOne(basePath+"sjkpColumnsMaxMoney"));
		param.put("COLUMNSITEMTOTALMONEY", Dao.selectOne(basePath+"sjkpColumnsTotalMoney"));
		
		return Dao.selectList(basePath+"getAllFirstReceiptMess", param);
	}
	
	public List<Object> getAllRentReceiptMess(Map<String,Object> param){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		return Dao.selectList(basePath+"getAllRentReceiptMess", param);
	}
	
	public List<Object> getAllRentReceiptVirtualMess(Map<String,Object> param){
		return Dao.selectList(basePath+"getAllRentReceiptVirtualMess", param);
	}
	/*
	public int uploadReceiptMess(List<File> files){
		int count = 0;
		File file = files.get(0);
		List<List<String>> sheet  = Excel.read2Map(file).get(0);
		if(sheet != null && sheet.size() >0 ){
			for(int i = 1; i < sheet.size() ; i++){
				List<String> row = sheet.get(i);
				//通过项目编号查询项目相关信息
				Map<String,Object> m = new HashMap<String, Object>();
				String INVOICE_CODE = row.get(0).toString();
				if(row.size()>0 &&  !"".equals(INVOICE_CODE)){
					m.put("INVOICE_CODE", INVOICE_CODE);
					m.put("INVOICE_DATE", row.get(1).toString());
					m.put("CURR_ALL_INVOICE_AMT", Security.getUser().getName());
					count += Dao.update(basePath+"updateCheckByBillCode",m);
				}
			}
		}
		return count;
	}*/
}
