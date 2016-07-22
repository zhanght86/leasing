package com.pqsoft.bpm.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.util.PageUtil;

public class SupOpConfService {

	public Page getPage(Map<String, Object> map) {
		return PageUtil.getPage(map, "bpm.supopconf.getPageList", "bpm.supopconf.getPageCount");
	}

	public Object getItem(Map<String, Object> param) {
		return Dao.selectList("bpm.supopconf.getItem", param);
	}

	public void addItem(Map<String, Object> param) {
		try {
			Dao.insert("bpm.supopconf.addItem", param);
		} catch (Exception e) {
			throw new ActionException(e.getMessage(), e);
		}
	}

	public void delItem(String id) {
		try {
			Dao.delete("bpm.supopconf.delItem", id);
		} catch (Exception e) {
			throw new ActionException(e.getMessage(), e);
		}
	}

	public Object roles() {
		return Dao.selectList("bpm.supopconf.roles");
	}

	public Object users() {
		return Dao.selectList("bpm.supopconf.roles");
	}

	public JSONArray users(Map<String, Object> param) {
		return JSONArray.fromObject(Dao.selectList("bpm.supopconf.users", param));
	}

}
