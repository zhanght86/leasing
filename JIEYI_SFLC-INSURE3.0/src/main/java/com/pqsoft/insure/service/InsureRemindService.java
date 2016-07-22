package com.pqsoft.insure.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class InsureRemindService {

	public Page getPageList(Map<String, Object> param) {
		return PageUtil.getPage(param, "insure.remind.getPageList", "insure.remind.getPageCount");
	}

	public void toSup(String id) {
		if (id == null) return;
		Dao.update("insure.remind.toSup", id);
	}

	public void toSupAll(Map<String, Object> param) {
		Dao.update("insure.remind.toSupAll", param);
	}

	public List<Map<String, Object>> getList(Map<String, Object> param) {
		return Dao.selectList("insure.remind.getList", param);
	}

	public List<Map<String, Object>> getListForids(String ids) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (ids == null) return list;
		for (String id : ids.split("[,;，；]")) {
			if (id == null || "".equals(id.trim())) continue;
			Map<String, Object> m = Dao.selectOne("insure.remind.getItem", id);
			list.add(m);
		}
		return list;
	}

}
