package com.pqsoft.cashBzjPjcd.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class CashBzjPjcdService {
	private String xmlPath = "cashDepositK.";
	public Page getPjcdData(Map<String, Object> map) {
		return PageUtil.getPage(map, xmlPath+"getPjcdDataList", xmlPath+"getPjcdDataCount");
	}
	public Map<String, Object> getPjcdDataById(Map<String, Object> map) {
		return Dao.selectOne(xmlPath+"getPjcdDataById", map);
	}
	public List<Map<String, Object>> getPjcd_Fi_R_Beginning(Map<String, Object> mapTem) {
		// TODO Auto-generated method stub
		return Dao.selectList(xmlPath+"getPjcd_Fi_R_Beginning", mapTem);
	}
	public int checkApplay_Pjcd(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return Dao.selectInt(xmlPath+"checkApplay_Status", map);
	}
	public Map<String, Object> getPjcdById(Map<String, Object> map) {
		return Dao.selectOne(xmlPath+"getPjcdById", map);
	}
	public Map<String, Object> getProcessDataByRemark_Id(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return Dao.selectOne(xmlPath+"getProcessDataByRemark_Id", map);
	}
	public List<Map<String,Object>> getProcessList(Map<String, Object> mapTem) {
		// TODO Auto-generated method stub
		return Dao.selectList(xmlPath+"getPjcd_Fi_R_Beginning", mapTem);
	}

}
