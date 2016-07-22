package com.pqsoft.splitAccount.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.DateUtil;

public class SplitAccountService {

	private String basePath = "spliteAccount.";
	
//	private String firstColumn="序号,申请单编号,区域,供应商,客户名称,客户编码,项目编号,起租确认日,租赁物类型,资金类型,应收金额,实收金额,付款方式,开户银行,核销银行,实收日期,备注";
//	private String firstName="ROWNUM,FUND_HEAD_ID,AREA_NAME,SUP_SHORTNAME,CUST_NAME,CUST_ID,PROJ_CODE,START_CONFIRM_DATE,PRODUCT_NAME,D_PAY_PROJECT,BEGINNING_MONEY,RECEIVE_MONEY,ITEM_METHOD,BANK_NAME,RECEIVE_BANK,ACCOUNT_DATE,FI_REMARK";
	private String firstColumn="序号,区域,供应商,客户名称,客户编码,项目编号,起租确认日期,租赁物名称,A1起租租金,A2保证金,A3第一期租金,A4保险费,A5担保费,A6手续费,A7留购价款,首期付款合计,DB保证金,管理服务费,计划付款日期,实收金额,付款方式,开户银行,核销银行,收款日期";
    private String firstName="ROWNUM,AREA_NAME,SUP_SHORTNAME,CUST_NAME,CUST_ID,PROJ_CODE,START_CONFIRM_DATE,LEASE_TYPE,QZZJ,BZJ,DYQZJ,BXF,DBF,SXF,LGJK,SQKHJ,DBBZJ,GLFWF,PLAN_DATE,TOTAL_MONEY,PAY_TYPE,CLIENT_BANK,FI_REALITY_BANK,FI_ACCOUNT_DATE";

	
	
	
//	private String rentColumn="序号,申请单编号,区域,供应商,客户名称,客户编码,项目编号,起租确认日,租赁物类型,租金类型,期次,应收金额,实收金额,付款方式,开户银行,核销银行,实收日期,备注";
//	private String rentName="ROWNUM,FUND_HEAD_ID,AREA_NAME,SUP_SHORTNAME,CUST_NAME,CUST_ID,PROJ_CODE,START_CONFIRM_DATE,PRODUCT_NAME,D_PAY_PROJECT,BEGINNING_NUM,BEGINNING_MONEY,RECEIVE_MONEY,ITEM_METHOD,BANK_NAME,RECEIVE_BANK,ACCOUNT_DATE,FI_REMARK";
	private String rentColumn="序号,供应商,客户名称,项目编号,租赁物类型,期次,租金,利息,本金,违约金,应收金额,实收金额,实收日期,付款方式,交易时间,开户银行,核销银行,备注,应收日期,租金类型";
	private String rentName="XULIE,SUP_SHORTNAME,CUST_NAME,PROJ_CODE,LEASE_TYPE,PERIOD,RENT,INTEREST,CORPUS,PENALTY,RECEIVE_MONEY,REALY_MONEY,FI_ACCOUNT_DATE,PAY_TYPE,FI_CHECK_DATE,CLIENT_BANK,FI_REALITY_BANK,FLAG,RECE_DATE,RENT_TYPE";
	
	
	private int COLSIZE=0;
	
	
	
	public Page getRentAccountDate(Map<String, Object> m) {
		Page page = new Page(m);
		//System.out.println(m.toString()+"..............................................");
		JSONArray jsonArray = JSONArray.fromObject(Dao.selectList(basePath+"getRentAccount",m));
		page.addDate(jsonArray, Dao.selectInt(basePath+"getRentAccountCount",m));
		//page.addParam(m);
		return page;

	}
	
	public Page getFirstAccountDate(Map<String, Object> m) {
		Page page = new Page(m);
		//System.out.println(m.toString()+"..............................................");
		JSONArray jsonArray = JSONArray.fromObject(Dao.selectList(basePath+"getFirstAccount",m));
		page.addDate(jsonArray, Dao.selectInt(basePath+"getFirstAccountCount",m));
		//page.addParam(m);
		return page;
	}

	public Excel exportRentAcountExcel(Map<String, Object> m) {
		//数据
		List<Map<String,Object>> dataList = Dao.selectList(basePath+"exportRentAcount",m);
			
		
		//封装excle
		Excel excel = new Excel();
		excel.setName("RentAccount"+DateUtil.getSysDate("yyyymmddss")+".xls");
		excel.addSheetName("sheet01");
		excel.addData(dataList);
		//表头	
		excel.addTitle(getcolumnNamesMap(rentColumn,rentName));

		return excel;
	}

	public Excel exportFirstAcountExcel(Map<String, Object> m) {
		//数据
		List<Map<String,Object>> dataList = Dao.selectList(basePath+"exportFirstAcount",m);
		
		//封装excle
		Excel excel = new Excel();
//		excel.setName("RentAccount"+DateUtil.getSysDate("yyyymmddss")+".xls");
		excel.addSheetName("sheet01");
		excel.addData(dataList);
		//表头
		excel.addTitle(getcolumnNamesMap(firstColumn,firstName));

		return excel;
	}
//对导出excle表头进行处理
	private LinkedHashMap<String, String> getcolumnNamesMap(
			String column, String name) {
		LinkedHashMap<String,String>  title=new LinkedHashMap<String,String>(); 
		String[] columns=column.split(",");
		String[]columnNames=name.split(",");
		for (int i = 0; i < columnNames.length; i++) {
			title.put(columnNames[i], columns[i]);
		}
		return title;
	}
//处理页面加载字段	
	private List<Map<String, Object>> getcolumnNamesList(String column) {
		List<Map<String, Object>>  columnList=new ArrayList<Map<String,Object>>()   ;
		String[] columns=column.split(",");
		COLSIZE=columns.length;
		for (int i = 0; i < COLSIZE; i++) {
			Map<String, Object> colMap = new HashMap<String, Object>();
			
			colMap.put("FLAG", columns[i]);
			columnList.add(colMap);
		}
//		System.out.println(columnList.toString());
		return columnList;
	}
//获取页面加载基本数据
	public void getBaseQueryDate(String name, VelocityContext context) {

		if("rent".equals(name)){
			context.put("columnList", getcolumnNamesList(rentColumn));
			context.put("sqlNameList",getcolumnNamesList(rentName) );
		}else if("first".equals(name)){
			context.put("columnList", getcolumnNamesList(firstColumn));
			context.put("sqlNameList",getcolumnNamesList(firstName) );
		}
		context.put("COLSIZE", COLSIZE);
		context.put("statusList", new DataDictionaryMemcached().get("期初表申请状态"));
	}

	

}
