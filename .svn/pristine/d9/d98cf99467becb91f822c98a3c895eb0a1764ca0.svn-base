package com.pqsoft.GPS.GPSds.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class GPSdsService {

	public Page findAll(Map<String, Object> param) {
		Page page = new Page(param);
		param.put("TYPE_", "GPS使用状态");
		List<Object> list = Dao.selectList("GPSds.findAll", param);
		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array, Dao.selectInt("GPSds.findAllcount", param));
		return page;
	}
	@SuppressWarnings("unchecked")
	public List findDing(Map<String,Object> param)
	{
		return Dao.selectList("GPSds.findDing", param);
	}
	
	@SuppressWarnings("unchecked")
	public List findMany(Map<String,Object> param)
	{
		return Dao.selectList("GPSds.findMany",param);
	}
	public List<Map<String,Object>> finddetail(Map<String, Object> map) {
		return Dao.selectList("GPSds.getGpsEqDetial",map);
	}
	@SuppressWarnings("unchecked")
	public List findshu(Map<String, Object> param) {
		return Dao.selectList("GPSds.getfindshu", param);
	}
	public List<?> getEpOverdueDetail(Map<String, Object> map) {
		return Dao.selectList("GPSds.getEpOverdueDetail",map);
	}
	public List<?> getEquipDetail(Map<String, Object> param) {
		return  Dao.selectList("GPSds.getEquipDetail",param);
	}
	public Page findAllshu(Map<String, Object> param) {
		Page page = new Page(param);
		param.put("TYPE_", "GPS使用状态");
		List<Object> list = Dao.selectList("GPSds.findAllshu", param);
		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array, Dao.selectInt("GPSds.findAllcountshu", param));
		return page;
	}
}
