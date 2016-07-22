package com.pqsoft.yingsf.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.DateUtil;

public class YingsfService {
	public List<Map<String, Object>> getList(Map<String, Object> param) {
		return Dao.selectList("yingsf.getPaymentList", param);
	}
	@SuppressWarnings("unchecked")
	public Map findsum(Map<String, Object> param)
	{
		return  Dao.selectOne("yingsf.findsum", param);
	}
	public Excel exportData(Map<String, Object> params) {
		// 数据
		List<Map<String, Object>> dataList = Dao.selectList("yingsf.exportExcel", params);
		// 表头
		String columnString = "客户编号,客户名称,项目,还款计划,客户经理,放款日期,应付金额,实付金额";
		String columnName = "CUST_ID,CLIENT_NAME,PRO_NAME,PAYLIST_CODE,CLERK_NAME,BEGGIN_DATE,PAYMENT_MONEY,LAST_MONEY";
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
