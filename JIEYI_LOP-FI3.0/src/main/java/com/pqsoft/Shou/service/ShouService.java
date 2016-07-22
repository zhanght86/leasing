package com.pqsoft.Shou.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.DateUtil;

public class ShouService {

	public List<Map<String, Object>> getList(Map<String, Object> param) {
		return Dao.selectList("shou.getPaymentList", param);
	}
	@SuppressWarnings("unchecked")
	public Map findsum(Map<String, Object> param)
	{
		return  Dao.selectOne("shou.findsum", param);
	}
	public Excel exportData(Map<String, Object> params) {
		// 数据
		List<Map<String, Object>> dataList = Dao.selectList("shou.exportExcel", params);
		// 表头
		String columnString = "客户编号,客户名称,项目,还款计划,客户经理,应收款名称,期次,应收时间,应收金额,实收金额";
		String columnName = "CUST_ID,CLIENT_NAME,PRO_NAME,PAYLIST_CODE,CLERK_NAME,BEGINNING_NAME,BEGINNING_NUM,BEGINNING_RECEIVE_DATA,BEGINNING_MONEY,BEGINNING_PAID";
		String[] columns = columnString.split(",");
		String[] columnNames = columnName.split(",");
		LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
		for (int i = 0; i < columnNames.length; i++) {
			title.put(columnNames[i], columns[i]);
		}
		// 封装excel
		Excel excel = new Excel();
		excel.addTitle(title);
		excel.setName("workFlow" + DateUtil.getSysDate("yyyymmddss") + ".xls");
		excel.addSheetName("sheet01");
		excel.addData(dataList);

		return excel;
	}

}
