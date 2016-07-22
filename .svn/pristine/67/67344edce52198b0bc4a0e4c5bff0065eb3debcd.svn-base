package com.pqsoft.GPS.GPSInstall.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;


public class GPSManageService {

	public Page queryPage(Map<String, Object> param) {
		Page page = new Page(param);
		param.put("TYPE_", "GPS使用状态");
		List<Object> list = Dao.selectList("GPSInstall.queryGPSInfo", param);
		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array, Dao.selectInt("GPSInstall.queryGPSInfo_count", param));
		return page;
	}

	public boolean toInstallGPS(Map<String, Object> param) {
//		ApiGPS gps=new ApiGPS();
//		gps.GPSManager(param.get("GPS_CODE").toString(), param.get("GPS_STATUS").toString());
		return Dao.insert("GPSInstall.installGPS", param)>=1;
	}

	public List<Object> findGPSList() {
		return Dao.selectList("GPSInstall.queryListForGPS");
	}

//	public boolean toChangeGPS(Map<String, Object> param) {
//		return Dao.insert("GPSInstall.changeGPS", param)>=1;
//	}

	public void removeGPS(String id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ID", id);
		Dao.update("GPSInstall.removeGPS", param);
	}
	
	/**
	 * GPS命令查询列表
	 * @param param
	 * @return
	 * @author xcp 2014-11-18
	 *//*
	public Page queryGPSCommandPage(Map<String, Object> param) {
		Page page = new Page(param);
		List<Object> list = Dao.selectList("GPSInstall.queryGPSCommand", param);
		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array, Dao.selectInt("GPSInstall.queryGPSCommand_count", param));
		return page;
	}*/
	public int savehistory(Map<String,Object> param)
	{
		return Dao.insert("GPSInstall.savehistory", param);
	}
	public Page findhistory(Map<String,Object> param)
	{
		Page page = new Page(param);
		List<Object> list = Dao.selectList("GPSInstall.findhistory", param);
		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array, Dao.selectInt("GPSInstall.findhistorycount", param));
		return page;
	}
	@SuppressWarnings("unchecked")
	public List findhistorylist(Map<String,Object> param)
	{
		return Dao.selectList("GPSInstall.findhistory", param);
	}
	@SuppressWarnings("unchecked")
	public Map findming(Map<String,Object> param)
	{
		return Dao.selectOne("GPSInstall.findming", param);
	}
	public int findfa(Map<String,Object> param)
	{
		Integer count = Dao.selectOne("GPSInstall.findfa",param);
		return count == null ? 0 : count ;
	}
	public int savedistance(Map<String,Object> param)
	{
		return Dao.insert("GPSInstall.savedistance", param);
	}
	public int gpsbaoyang(Map<String,Object> param)
	{
		Integer count = Dao.selectOne("GPSInstall.gpsbaoyang", param);
		return count == null ? 0 : count ;
	}
	@SuppressWarnings("unchecked")
	public Map findgpsdis(Map<String,Object> param)
	{
		return Dao.selectOne("GPSInstall.findgpsdis", param);
	}
}
