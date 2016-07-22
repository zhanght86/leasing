package com.pqsoft.fundPlan.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class FinanceReportService {
	
	private final String xmlPath = "financereport.";

	/**
	 * 报表-财务报表-财务基本信息统计
	 * toMgFinanceBaseReport
	 * @date 2015年5月8日
	 * @author yangxue
	 * @return 返回值类型   Page 
	 * @parameter
	 */
	public Page toMgFinanceBaseReport(Map<String,Object> map){
		return PageUtil.getPage(map, xmlPath+"toMgFinanceBaseReport", xmlPath+"toMgFinanceBaseReportC");
	}
	
	/**
	 * 报表-财务报表-财务基本信息统计-导出
	 * toImportExcel
	 * @date 2015年5月15日
	 * @author yangxue
	 * @return 返回值类型   Object 
	 * @parameter
	 */
	public Object toImportExcel(Map<String,Object> map){
		return Dao.selectList(xmlPath+"toImportExcel", map);
	}
}
