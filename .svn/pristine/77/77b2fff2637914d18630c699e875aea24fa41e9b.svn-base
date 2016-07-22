package com.pqsoft.GPS.GPSbytx.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class GPSbytxService {
	public Page findAll(Map<String,Object> param)
	{
		Page page=new Page(param);
		param.put("TYPE_", "GPS使用状态");
		List<Object> list = Dao.selectList("GPSbytx.findAll", param);
		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array, Dao.selectInt("GPSbytx.findAllcount", param));
		return page;
	}

}
