package com.pqsoft.invoice.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.StringUtils;

public class InvoiceSearchService {
	private String nameSpace = "invoiceSearch.";
		
	public Page getInvoiceSearchPageData(
			Map<String, Object> param) {
		Page page = new Page(param);
		Map<String,Object> SUP_MAP = BaseUtil.getSup();
		if(SUP_MAP!=null){
			param.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		page.addDate(JSONArray.fromObject(Dao.selectList(nameSpace + "getInvoiceSearchPageData",param)), Dao.selectInt(nameSpace + "getInvoiceSearchPageDataCount", param));
		return page;
	}

	public List<Object> getInvoiceSearchFactById(Map<String, Object> param) {
		return Dao.selectList(nameSpace + "getInvoiceSearchFactById", param);
	}
	
	public List<Object> getInvoiceSearchItemById(Map<String, Object> param) {
		return Dao.selectList(nameSpace + "getInvoiceSearchItemById", param);
	}
	public Page getInvoiceSearchDetailPageData(Map<String, Object> param) {
		Page page = new Page(param);
		page.addDate(JSONArray.fromObject(Dao.selectList(nameSpace + "getInvoiceSearchDetailPageData",param)), Dao.selectInt(nameSpace + "getInvoiceSearchDetailCount", param));
		return page;
	}

	public Excel getExportApplyExcel(Map<String, Object> param) {
		param.put("SQL_TARGET_FLAG", "发票_对象_类型");
		//数据
		List<Map<String,Object>> dataList = Dao.selectList(nameSpace + "getInvoiceSearchExcelData",param);
		//表头
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
		title.put("ID", "唯一标识");
		title.put("SUP_SHORTNAME", "供应商名称");
		title.put("SUBCOMPANY", "厂商");
		title.put("PROJ_CODE", "项目编号");
		title.put("CUST_NAME", "客户名称");
		title.put("INVOICE_TYPE", "发票/收据");
		title.put("FUND_TYPE", "发票类型");
		title.put("FUND_NAME", "项目");
		title.put("INVOICE_CODE", "发票号");
		title.put("INVOICE_DATE", "发票日期");
		title.put("INVOICE_AMT", "金额");
		title.put("RENT_LIST", "期次");
//		M.ID,B.SUP_SHORTNAME,B.SUBCOMPANY,M.PROJ_CODE,B.CUST_NAME,M.INVOICE_TYPE,B.STATUS_NAME,B.CUST_TYPE,
//		M.FUND_TYPE,M.FUND_NAME,M.INVOICE_AMT,M.INVOICE_CODE,M.INVOICE_DATE,M.ALL_INVOICE_NUM,M.RENT_LIST
//		FROM FI_SALEITEM_INVOICE M 
		//封装excel
		Excel excel = new Excel();
//		excel.setName("CardCheck"+DateUtil.getSysDate("yyyymmddss")+".xls");
//		excel.addSheetName("sheet01");
		excel.addData(dataList);
		excel.addTitle(title);
//		excel.hasTitle(true);
		return excel;
	}
}
