package com.pqsoft.bpm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class ReportService {
	public static List<String> type = null;
	public static List<String> tk = null;
	public static List<String> titlesZH = new ArrayList<String>();// 中文表头
	public static List<String> titlesEN = new ArrayList<String>();// 英文表头
	static {
		type = Dao.selectList("bpm.report.getType");// 中文类型(不包含项目编号，申请人申请时间等非循环性列)
		tk = new ArrayList<String>();// 标识
		for (int i = 0; i < type.size(); i++) {
			tk.add("A" + i);
		}
		titlesZH.add("项目编号");
		titlesEN.add("PRO_CODE");
		titlesZH.add("业务类型");
		titlesEN.add("PLATFORM_NAME");
		titlesZH.add("申请人");
		titlesEN.add("OP_NAME");
		titlesZH.add("流程编号");
		titlesEN.add("ID_");
		// titlesZH.add("分支机构");
		// titlesEN.add("SUB_COMPANY_NAME");
		titlesZH.add("申请时间");
		titlesEN.add("CREATE_DATE");
		titlesZH.add("总耗时(小时)");// 小时,会有多位小数
		titlesEN.add("ALLHS");
		titlesZH.add("状态");// 非一次通过 一次通过
		titlesEN.add("ALLCF");
		for (int i = 0; i < type.size(); i++) {
			titlesZH.add(type.get(i) + "[操作人]");
			titlesEN.add("A" + i + "N");
			titlesZH.add(type.get(i) + "[耗时(小时)]");
			titlesEN.add("A" + i + "HS");
			titlesZH.add(type.get(i) + "[次数]");
			titlesEN.add("A" + i + "C");
		}
	}

	/**
	 * 参数：REPORT_DATE，START_TIME，END_TIME（标准模式）
	 * 参数：
	 * 
	 * @param param
	 * @return
	 */
	public Page report(Map<String, Object> param) {
		param.put("types", type);
		param.put("tks", tk);
		return PageUtil.getPage(param, "bpm.report.getReport", "bpm.report.getReportCount");
	}

	public List<Map<String, Object>> dataList(Map<String, Object> param) {
		return Dao.selectList("bpm.report.getReport", param);
	}

	public String getYchs() {
		String re = "2";
		List<Map<String, Object>> list = (List<Map<String, Object>>) DataDictionaryMemcached.getList("流程设置");
		for (Map<String, Object> map : list) {
			if (map.get("FLAG").equals("异常流程耗时")) {
				re = (String) map.get("CODE");
			}
		}
		return re;
	}
}
