package com.pqsoft.financeConvert.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class FinanceModifyService {

	public Page getSuperTableData(Map<String, Object> params) {
		Page page = new Page(params);//FundEbank
		JSONArray jsonArray = JSONArray.fromObject(Dao.selectList("finanaceModify.getSuperdate",params));
		page.addDate(jsonArray, Dao.selectInt("finanaceModify.getSuperdateCount",params));
		
		return page;
	}

	public Page getClientTableData(Map<String, Object> params) {
		Page page = new Page(params);//FundEbank
		JSONArray jsonArray = JSONArray.fromObject(Dao.selectList("finanaceModify.getClientdate",params));
		page.addDate(jsonArray, Dao.selectInt("finanaceModify.getClientdateCount",params));
		
		return page;
	}

	public int updateSuper(Map<String, Object> param) {
		int i =Dao.update("finanaceModify.updateSuperDate", param); 
		return i;
	}

	public int updateClient(Map<String, Object> param) {
		int i =Dao.update("finanaceModify.updateClientDate", param); 
		return i;
	}

	public Excel getExplorExcleClient(Map<String, Object> m) {
		//数据
		List<Map<String,Object>> dataList = Dao.selectList("finanaceModify.exportClientdate",m);
		//表头		
		String columnString="客户编号,客户名称,财务接口编号(社会),财务接口编号(分期)";
		String columnName="ID,NAME,PROVE_ID,PROVE_ID_SPLIT";
		String[] columns=columnString.split(",");
		String[]columnNames=columnName.split(",");
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
		for (int i = 0; i < columnNames.length; i++) {
			title.put(columnNames[i], columns[i]);
		}
		//封装excle
		Excel excel = new Excel();
		excel.addSheetName("sheet01");
		excel.addData(dataList);
		excel.addTitle(title);

		return excel;
	}

	public Excel getExplorExcleSuper(Map<String, Object> m) {
		//数据
		List<Map<String,Object>> dataList = Dao.selectList("finanaceModify.exportSuperdate",m);
		//表头		
		String columnString="供应商编号,供应商名称,财务接口编号,财务接口编号(分期)";
		String columnName="ID,NAME,PROVE_ID,PROVE_ID_SPLIT";
		String[] columns=columnString.split(",");
		String[]columnNames=columnName.split(",");
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
		for (int i = 0; i < columnNames.length; i++) {
			title.put(columnNames[i], columns[i]);
		}
		//封装excle
		Excel excel = new Excel();
		excel.addSheetName("sheet01");
		excel.addData(dataList);
		excel.addTitle(title);

		return excel;
	}

}
