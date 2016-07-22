package com.pqsoft.insure.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.insure.util.InsureStatus;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class InsureInfoService {

	public Page getPageList(Map<String, Object> param) {
		Page page = new Page(param);
		List<Map<String, Object>> list = Dao.selectList("insure.info.getPageList", param);
		for (Map<String, Object> map : list) {
			map.put("STATUS", InsureStatus.getStatus((String) map.get("STATUS")));
		}
		page.addDate(JSONArray.fromObject(list), Dao.selectInt("insure.info.getPageCount", param));
		return page;
	}

	public List<Map<String, Object>> getList(Map<String, Object> param) {
		List<Map<String, Object>> list = Dao.selectList("insure.info.getList", param);
		for (Map<String, Object> map : list) {
			map.put("STATUS", InsureStatus.getStatus((String) map.get("STATUS")));
		}
		return Dao.selectList("insure.info.getList", param);
	}

	public List<Map<String, Object>> getListForids(String ids) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (ids == null) return list;
		for (String id : ids.split("[,;，；]")) {
			if (id == null || "".equals(id.trim())) continue;
			Map<String, Object> m = Dao.selectOne("insure.info.getItem", id);
			m.put("STATUS", InsureStatus.getStatus((String) m.get("STATUS")));
			list.add(m);
		}
		return list;
	}
}
