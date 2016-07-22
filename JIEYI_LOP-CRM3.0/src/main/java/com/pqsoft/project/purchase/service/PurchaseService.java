package com.pqsoft.project.purchase.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.PageUtil;

public class PurchaseService {

	public static String formatDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public Page queryPage(Map<String, Object> m) {
		return PageUtil.getPage(m, "purchase.getAllSuitProject", "purchase.getAllSuitProject_count");
	}
	
	public Page querySearchPage(Map<String, Object> m) {
		return PageUtil.getPage(m, "purchase.getSearchAllSuitProject", "purchase.getSearchAllSuitProject_count");
	}

	public List queryContentByPROJID(Map m) {
		return Dao.selectList("purchase.queryContentByPROJID", m);
	}
	
	public Map<String,Object> selectFileData(Map<String,Object> param){
		return Dao.selectOne("purchase.selectFileData",param);
	}
	
	public Map queryProjectById(Map map) {
		map.put("tags1", "业务类型");
		map.put("tags3", "客户类型");
		return Dao.selectOne("purchase.queryProjectById", map);
	}
	
	// 根据合同id查询支付表id
	public int queryIdByProjectId(String ProjectId) {
		return Dao.selectInt("purchase.queryIdByProjectId", ProjectId);
	}
	
	// 查询该项目所选择的设备
	public List queryEqByProjectID(Map param) {
		param.put("tags", "设备单位");
		return Dao.selectList("project.queryEqByProjectID", param);
	}
	
	// 查询项目所有的担保人
	public List queryGuaranByProjectID(Map param) {
		param.put("tags", "客户类型");
		return Dao.selectList("project.queryGuaranByProjectID", param);
	}
	
	// 查询该项目的方案
	public List<Map<String, Object>> querySechmeByProjectID(Map param) {
		return Dao.selectList("project.querySechmeByProjectID", param);
	}
	
	// 查询该项目的方案
	public List<Map<String, Object>> querySechmeByAllDate(Map param) {
		return Dao.selectList("project.querySechmeByAllDate", param);
	}
	
	public Excel exportData(Map<String, Object> params) {
		//数据
		List<Map<String,Object>> dataList = Dao.selectList("purchase.getAllSuitProjectExcel",params);
		//表头
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>(); 
		title.put("COMPANY_NAME", "厂商");
		title.put("SUPPLIER", "供应商");
		title.put("PRO_CODE", "项目编号");
		title.put("CLIENTNAME", "客户名称");
		title.put("PRODUCT_NAME", "租赁物类型");
		title.put("ENGINE_TYPE", "机型");
		title.put("TYPE_NAME", "机号");
		title.put("AMOUNT", "台量");
		title.put("END_DATE", "租赁到期日");
		title.put("PROSTATUS", "结束方式");
		//封装excle
		Excel excel = new Excel();
		excel.setName("项目留购申请"+DateUtil.getSysDate("yyyymmddss")+".xls");
		excel.addSheetName("sheet01");
		excel.addData(dataList);
		excel.addTitle(title);
		return excel;
	}
	
	public Excel exportSearchData(Map<String, Object> params) {
		//数据
		List<Map<String,Object>> dataList = Dao.selectList("purchase.getSearchAllSuitProjectExcel",params);
		//表头
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
		title.put("COMPANY_NAME", "厂商");
		title.put("SUPPLIER", "供应商");
		title.put("AREANAME", "区域");
		title.put("PRO_CODE", "项目编号");
		title.put("CLIENTNAME", "客户名称");
		title.put("PRODUCT_NAME", "租赁物类型");
		title.put("AMOUNT", "台量");
		title.put("END_DATE", "租赁到期日");
		title.put("PURCHASE_APP_DATE", "留购日期");
		title.put("PROSTATUS", "结束方式");
		//封装excle
		Excel excel = new Excel();
		excel.setName("项目留购查询"+DateUtil.getSysDate("yyyymmddss")+".xls");
		excel.addSheetName("sheet01");
		excel.addData(dataList);
		excel.addTitle(title);
		return excel;
	}
	
	//插入FIL_PROJECT_END_PURCHASE留购表数据
	public int insertProjectPurchase(Map<String,Object> param){
		return Dao.insert("purchase.insertProjectPurchase",param);
	}
	
	//留购查询
	public List<Map<String, Object>> queryPurchaseContentPage(Map m) {
		return Dao.selectList("purchase.getAllSuitProject", m);
	}

}
