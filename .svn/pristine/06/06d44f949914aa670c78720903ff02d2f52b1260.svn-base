package com.pqsoft.GPS.GPSbaojing.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class GPSbaojingService {
	public Page findwenti(Map<String,Object> param)
	{
		Page page = new Page(param);
		param.put("TYPE_", "GPS使用状态");
		List<Object> list = Dao.selectList("GPSbaojing.findwenti", param);
		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array, Dao.selectInt("GPSbaojing.findwenticount", param));
		return page;
	}
	public Page findAll(Map<String,Object> param)
	{
		Page page = new Page(param);
		param.put("TYPE_", "GPS使用状态");
		List<Object> list = Dao.selectList("GPSbaojing.findAll", param);
		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array, Dao.selectInt("GPSbaojing.findAllcount", param));
		return page;
	}
}
