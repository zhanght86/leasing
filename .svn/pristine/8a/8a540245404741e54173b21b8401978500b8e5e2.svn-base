package com.pqsoft.zcfl.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class GradSortService {
	
	public Page list(Map<String, Object> param) {
		int pageCurr = 1;
		int pageCount = 5;
		if (!(param.get("PAGE_CURR") == null || "".equals(param
				.get("PAGE_CURR").toString().trim()))) {
			pageCurr = Integer.parseInt(param.get("PAGE_CURR").toString());
		}
		if (!(param.get("PAGE_COUNT") == null || "".equals(param.get(
				"PAGE_COUNT").toString().trim()))) {
			pageCount = Integer.parseInt(param.get("PAGE_COUNT").toString());
			
		}
		param.put("PAGE_BEGIN", pageCount * (pageCurr - 1) + 1);
		
		param.put("PAGE_END", pageCount * pageCurr);
		
		Page page = PageUtil.getPage(param, "gradesort.list", "gradesort.list_count");
		return page;
	}
	
	public int AddGradeSort(Map map){
		System.out.println("map="+map);
		return Dao.insert("gradesort.AddAssetLevel", map);
	}
	
    public boolean AddGradeSortModel(Map map){
		String str = (String) map.get("CTP_IDS");
		// 删除 
		Dao.delete("gradesort.delGradeSortModel", map);
		//添加
		if (str != null) {
			String[] CTP_IDS = str.split("[,;]");
			for (int i = 0; i < CTP_IDS.length; i++) {
				map.put("CTP_ID", CTP_IDS[i]);
				map.put("SERIATION", i+1);
				Dao.insert("gradesort.AddGradeSortModel", map);
			}
		}
		return true;
	}
    
    
	public Object select(Map<String, Object> map) {
		
		return Dao.selectList(
				"gradesort.select", map);
	}
	
	   
	public Map<String,Object> moddle(Map<String, Object> param) {
		return (Map<String, Object>) Dao.selectOne(
				"gradesort.moddle", param);
	}
	
	public List<Object> middle(Map<String, Object> param) {
		return Dao.selectList(
				"gradesort.middle", param);
	}
	public List<Object> list1(Map<String, Object> param) {
		return Dao.selectList(
				"gradesort.list1", param);
	}
}
