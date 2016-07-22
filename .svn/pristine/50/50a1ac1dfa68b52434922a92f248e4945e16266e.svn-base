package com.pqsoft.GPS.GPSbaoyang.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class GPSbaoyangService {
	public Page findAll(Map<String, Object> param) {
		Page page = new Page(param);
		param.put("TYPE_", "GPS使用状态");
		List<Object> list = Dao.selectList("GPSbaoyang.findAll", param);
		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array, Dao.selectInt("GPSbaoyang.findAllcount", param));
		return page;
	}
	@SuppressWarnings("unchecked")
	public List findbyopen(Map<String, Object> param) {
		return Dao.selectList("GPSbaoyang.findbyopen", param);
	}
	public int saveby(Map<String,Object> param)
	{
		return Dao.insert("GPSbaoyang.saveby", param);
	}
	public int updateby(Map<String,Object> param)
	{
		return Dao.update("GPSbaoyang.updateby", param);
	}
}
