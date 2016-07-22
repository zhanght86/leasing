package com.pqsoft.eqInvoices.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class EqInvoiceLegerService {
	private String basePath = "EqInvoice.";
	
	public Page getEqLegerPageData(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getEqInvoiceLegerList",param));
		page.addDate(array, Dao.selectInt(basePath+"getEqInvoiceLegerCount", param));
		return page;
	}
	
	
	public Page getDunLegerPageData(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getDunInvoiceLegerList",param));
		page.addDate(array, Dao.selectInt(basePath+"getDunInvoiceLegerCount", param));
		return page;
	}
	
	/**
	 * 导出租赁物发票台账信息
	 * @param param
	 * @return
	 */
	public Excel exportEqInvoiceLedger(Map<String,Object> param){
		List<Map<String,Object>>  pageList = Dao.selectList(basePath+"getEqInvoiceLeger", param);
		//更新导出标识
		for(int i=0;i<pageList.size();i++){
			Map map=(Map)pageList.get(i);
			Dao.update(basePath+"updateExportFlag",map);
		}
		//表头
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
		title.put("EQINVOICE_LEGER_NO", "序号");
		title.put("PLATFORM_TYPE", "业务类型");
		title.put("LEASE_CODE", "合同编号");
		title.put("PAYLIST_CODE", "还款计划编号");
		title.put("TYPE_NAME", "型号");
		title.put("ENGINE_TYPE", "发动机编号");
		title.put("SUP_SHORTNAME", "供应商");
		title.put("CUST_NAME", "客户名称");
		title.put("LEASE_TOPRIC", "租赁物购买价款");
		title.put("INVOICE_CODE", "发票号");
		title.put("INVOICE_DATE", "发票日期");
		title.put("INVOICE_AMOUNT", "发票含税金额");
		title.put("INVOICE_TAX", "税额");
		title.put("CREATE_TIME", "录入日期");
		title.put("REALITY_DATE", "放款日期");
		
		
		
		//封装excel
		Excel excel = new Excel();
		excel.addData(pageList);
		excel.addTitle(title);
		excel.setHeadTitles("发票登记表");
		excel.setHeadDate(true);
		return excel;
	}
	
	/**
	 * 导出租赁物发票台账信息
	 * @param param
	 * @return
	 */
	public Excel exportDunInvoiceLedger(Map<String,Object> param){
		List<Map<String,Object>>  pageList = Dao.selectList(basePath+"getDunInvoiceLeger", param);
		//更新导出标识
		for(int i=0;i<pageList.size();i++){
			Map map=(Map)pageList.get(i);
			Dao.update(basePath+"updateExportFlagDun",map);
		}
		//表头
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
		title.put("EQINVOICE_LEGER_NO", "序号");
		title.put("SUP_SHORTNAME", "供应商");
		title.put("CUST_NAME", "客户名称");
		title.put("PLATFORM_TYPE", "业务类型");
		title.put("LEASE_CODE", "合同编号");
		title.put("PAYLIST_CODE", "还款计划编号");
		title.put("TAI_NUM", "期次");
		title.put("INVOICE_CODE", "发票号");
		title.put("INVOICE_DATE", "发票日期");
		title.put("INVOICE_AMOUNT", "发票含税金额");
		title.put("INVOICE_TAX", "税额");
		title.put("CREATE_TIME", "录入日期");
		
		//封装excel
		Excel excel = new Excel();
		excel.addData(pageList);
		excel.addTitle(title);
		excel.setHeadTitles("发票登记表");
		excel.setHeadDate(true);
		return excel;
	}
}

