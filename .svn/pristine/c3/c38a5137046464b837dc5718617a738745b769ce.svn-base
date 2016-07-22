package com.pqsoft.vehicleControl.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class VehicleControlService {
	
	public Page getPageData(Map<String,Object> param){
		return PageUtil.getPage(param, "vehicleControl.getDataPage", "vehicleControl.getDataCount");
	}
	
	public int updateStatus(Map<String,Object> param){
		return Dao.update("vehicleControl.updateStatus",param);
	}
	
	public Map<String,Object> selectEquipmentById(Map<String,Object> param){
		return Dao.selectOne("vehicleControl.selectEquipmentById",param);
	}
	
	public int addControl(Map<String,Object> param){
		return Dao.insert("vehicleControl.addControl", param);
	}
	
	public int updateCtrlType(Map<String,Object> param){
		return Dao.update("vehicleControl.updateCtrlType",param);
	}

}
