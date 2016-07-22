package com.pqsoft.api.datalist.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class DataListService {
	private String xmlPath = "datalist.";//xml路径
	
	public Page getPage(Map<String, Object> param) {
		param.put("TYPE", "客户类型");
		param.put("TYPE1", "权证类别");
		param.put("TYPE2", "权证级别");
		return PageUtil.getPage(param, xmlPath+"getPageList", xmlPath+"getPageCount");
	}
	public boolean doAddDataList(Map<String, Object> param) {
		return Dao.delete(xmlPath + "addDataList", param) > 0 ? true : false;
	}
	public Map<String,Object> getMgDataListData(Map<String, Object> param) {
		return Dao.selectOne(xmlPath + "getDataListData", param);
	}
	public boolean doDeleteDataList(Map<String, Object> param) {
		return Dao.delete(xmlPath + "deleteDataList", param) > 0 ? true : false;
	}
	public boolean doUpdateDataList(Map<String, Object> param) {
		return Dao.delete(xmlPath + "updateDataList", param) > 0 ? true : false;
	}
	public Object getDataListData(Map<String, Object> param) {
		return Dao.selectOne(xmlPath + "getDataListData", param);
	}
	
	public int selectCountByName(Map<String, Object> param) {
		//Map<String, Object> map = new HashMap<String, Object>();
		//map.put("WARRANT_NAME", WARRANT_NAME);
		return Dao.selectInt(xmlPath+"selectCountByName", param);
	}
	
	public List<Map<String,Object>> getData(Map<String,Object> param){
		param.put("TYPE", "客户类型");
		param.put("TYPE1", "权证类别");
		param.put("TYPE2", "权证级别");
		return Dao.selectList(xmlPath +"getData", param);
	}
	public Page getPage1(Map<String, Object> param) {
		param.put("TYPE1", "权证类别");
		param.put("TYPE2", "权证级别");
		return PageUtil.getPage(param, xmlPath+"getPageList1", xmlPath+"getPageCount1");
	}

}


