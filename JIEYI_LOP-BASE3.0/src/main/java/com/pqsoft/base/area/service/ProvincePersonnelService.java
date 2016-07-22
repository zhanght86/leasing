package com.pqsoft.base.area.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class ProvincePersonnelService {

	private String basePath = "ProvincePersonnel.";
	public List<Object> getAllProvincePersonnel(Map<String,Object> m) {
		return Dao.selectList(basePath + "getAllProvincePersonnel",m);
	}
	
	public List<Object> getselectpeople(Map<String,Object> m)
	{
		return Dao.selectList(basePath+"getselectpeople",m);
	}
	
	public List<Object> getselectAllpeople(Map<String,Object> m)
	{
		return Dao.selectList(basePath+"getselectAllpeople",m);
	}
	
	public int insProvincePeople(Map<String,Object> m)
	{
		return Dao.insert(basePath+"insProvincePeople",m);
	}
	public int delProvincePeople(Map<String,Object> m)
	{
		return Dao.delete(basePath+"delProvincePeople",m);
	}
}
