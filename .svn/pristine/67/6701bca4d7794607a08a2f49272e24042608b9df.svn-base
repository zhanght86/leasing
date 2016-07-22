package com.pqsoft.infoexport.service;
/**
 *  管理信息系统模板导出
 *  @author 韩晓龙
 */
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.DateUtil;

public class InfoExportService {
	private String basePath = "infoexport.";
	
	public InfoExportService() {
	}
	
	/**
	 *  得到分页数据
	 */
	public Page getPageData(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath + "queryAll",param));
		page.addDate(array, Dao.selectInt(basePath + "queryCount", param));
		return page;
	}
	
	/**
	 *   根据条件查询项目明细
	 */
	public List<Map<String, Object>> selectProjectsInfo(Map<String, Object> param) {
		return Dao.selectList(basePath + "selectProjectsInfo",param);
	}
	
}
