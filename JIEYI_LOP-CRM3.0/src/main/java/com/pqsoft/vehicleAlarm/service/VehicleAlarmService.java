package com.pqsoft.vehicleAlarm.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class VehicleAlarmService {

	
	public Page getPage(Map<String,Object> param){
		return PageUtil.getPage(param, "vehicleAlarm.getDataPage", "vehicleAlarm.getDataCount");
	}
	
	public int updateStauts(Map<String,Object> param){
		return Dao.update("vehicleAlarm.updateStatus",param);
	}
	
	public Map<String,Object> selectAlarmById(Map<String,Object> param){
		return Dao.selectOne("vehicleAlarm.selectAlarmById",param);
	}
}
