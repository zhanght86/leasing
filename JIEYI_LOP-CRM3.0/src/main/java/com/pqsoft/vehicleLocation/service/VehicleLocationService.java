package com.pqsoft.vehicleLocation.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class VehicleLocationService {
	
	public Page getPageData(Map<String,Object> param){
		return PageUtil.getPage(param, "vehicleLocation.getDataPage", "vehicleLocation.getDataCount");
	}
	
	
}
