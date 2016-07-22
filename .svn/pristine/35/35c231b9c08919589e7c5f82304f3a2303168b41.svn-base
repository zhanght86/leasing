package com.pqsoft.call.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.DateUtil;


public class LeaseVerifyService {

	String basePath = "leaseVerify.";
	
	public Page getLeasingData(Map<String, Object> params) {
		Page page = new Page(params);
		//System.out.println(params.toString()+"..............................................");
		JSONArray jsonArray = JSONArray.fromObject(Dao.selectList(basePath+"getAllEquipCheckInfo",params));
		page.addDate(jsonArray, Dao.selectInt(basePath+"getAllEquipCheckInfoCount",params));
		//page.addParam(params);
		return page;
	}

	
	/**
	 * 导出签约中间表
	 * @Title: exportData 
	 * @param params
	 * @return 
	 * @return Excel 
	 * @throws 
	 */
	public Excel exportData(Map<String, Object> params) {
		//数据
		List<Map<String,Object>> dataList = Dao.selectList(basePath+"exportEquipCheckInfo",params);
		
		//表头		
		String columnString="项目编号,供应商,租赁物类型,是否正确,台量,是否正确,租赁物购买价款,是否正确,起租比例,融资期限,是否正确,每期租金,合同签署,是否交车,交车时间,是否通过";
		String columnName="PROJ_ID,DLD,EQUIP_TYPE,ISTRUE1,EQUIP_AMOUNT,ISTRUE2,EQUIP_AMT,ISTRUE3,QZ_RATIO,LEASE_TERM,ISTRUE4,RENT,ISTRUE5,ISTRUE6,JC_DATE,ISAGREE";
		String[] columns=columnString.split(",");
		String[]columnNames=columnName.split(",");
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
		for (int i = 0; i < columnNames.length; i++) {
			title.put(columnNames[i], columns[i]);
		}
		//封装excle		
		Excel excel = new Excel();
		excel.addTitle(title);
		excel.setName("LeaseVerify"+DateUtil.getSysDate("yyyymmddss")+".xls");
		excel.addSheetName("sheet01");
		excel.addData(dataList);
		

		return excel;
	}


	public Page getIdentityData(Map<String, Object> params) {
		Page page = new Page(params);
		//System.out.println(params.toString()+"..............................................");
		JSONArray jsonArray = JSONArray.fromObject(Dao.selectList(basePath+"getIdentityInfo",params));
		page.addDate(jsonArray, Dao.selectInt(basePath+"getIdentityInfoCount",params));
		//page.addParam(params);
		return page;
	}


	public Page getIdentityLpData(Map<String, Object> params) {
		Page page = new Page(params);
		//System.out.println(params.toString()+"..............................................");
		JSONArray jsonArray = JSONArray.fromObject(Dao.selectList(basePath+"getIdentityLpInfo",params));
		page.addDate(jsonArray, Dao.selectInt(basePath+"getIdentityLpInfoCount",params));
		//page.addParam(params);
		return page;
	}


	public Excel exportDataIdentity(Map<String, Object> params) {
		//数据
		List<Map<String,Object>> dataList = Dao.selectList(basePath+"exportIdentityInfo",params);
		//表头		
		String columnString="项目编号,客户名称,联系方式,是否正确,是否本人接听,身份证号,是否正确,户籍所在地,是否正确,婚姻状况,配偶姓名,是否正确,备注,是否通过";
		String columnName="PROJ_ID,CUST_NAME,PHONE,ISTRUE1,ISTRUE2,CARD_NO,ISTRUE3,RESIDENCE,ISTRUE4,ISMARRI,SPOUSE_NAME,ISTRUE5,REMARK,ISAGREE";
		String[] columns=columnString.split(",");
		String[]columnNames=columnName.split(",");
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
		for (int i = 0; i < columnNames.length; i++) {
			title.put(columnNames[i], columns[i]);
		}

		//封装excle
		Excel excel = new Excel();
		excel.addTitle(title);
		excel.setName("IdentityVerify"+DateUtil.getSysDate("yyyymmddss")+".xls");
		excel.addSheetName("sheet01");
		excel.addData(dataList);
		//excel.addTitle(title);

		return excel;
	}


	public Excel exportDataIdentityLp(Map<String, Object> params) {
		//数据
		List<Map<String,Object>> dataList = Dao.selectList(basePath+"exportIdentityLpInfo",params);
		
		//表头		
		String columnString="项目编号,公司名称,联系方式,是否正确,是否本人接听,注册资金万元,是否正确,公司属性,是否正确,法人代表,是否正确,备注,是否通过";
		String columnName="PROJ_ID,CUST_NAME,PHONE,ISTRUE1,ISTRUE2,REG_AMT,ISTRUE3,CUST_PEOPERTY,ISTRUE4,LEGAL_REPRESENT,ISTRUE5,REMARK,ISAGREE";
		String[] columns=columnString.split(",");
		String[]columnNames=columnName.split(",");
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
		for (int i = 0; i < columnNames.length; i++) {
			title.put(columnNames[i], columns[i]);
		}

		//封装excle
		Excel excel = new Excel();
		excel.setName("IdentityLpVerify"+DateUtil.getSysDate("yyyymmddss")+".xls");
		excel.addSheetName("sheet01");
		excel.addData(dataList);
		excel.addTitle(title);

		return excel;
	}

}
