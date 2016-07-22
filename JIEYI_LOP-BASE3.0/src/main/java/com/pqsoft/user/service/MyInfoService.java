package com.pqsoft.user.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class MyInfoService {

	public int changePwd(Map<String, Object> param) {
		return Dao.update("MyInfo.changePwd", param);
	}

}
