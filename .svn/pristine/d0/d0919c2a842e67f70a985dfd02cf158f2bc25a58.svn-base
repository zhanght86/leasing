package com.pqsoft.badBebt.service;

import java.util.HashMap;
import java.util.Map;


import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class BadBebtService {
	private String path = "badBebt.";
	public Page getBadBebtList(Map<String,Object> map){
		return PageUtil.getPage(map, path+"getBadBebtList", path+"getBadBebtCount");
	}
	public int insertBadBebtData(Map<String,Object> map){
		return Dao.insert(path+"insertBadBebtData",map);
	}
	public Object getCUISRecord(Map<String, Object> map) {
		return Dao.selectList(path+"getCUISRecord", map);
	}
	public Object getSUSRecord(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return Dao.selectList(path+"getSUSRecord", map);
	}
	public Map<String,Object> getBadBebtInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return Dao.selectOne(path+"getBadBebtInfo", map);
	}
	public Page getBadBebtShowInfo(Map<String, Object> map) {
		return PageUtil.getPage(map, path+"getBadBebtShowList", path+"getBadBebtShowCount");
	}
}
