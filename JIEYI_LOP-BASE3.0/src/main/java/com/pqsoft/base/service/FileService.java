package com.pqsoft.base.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class FileService {

	public Map<String, Object> findFileByID(String fileId, Map<String, Object> param) {
		Map<String,Object> m = (Map<String,Object>)Dao.selectOne("file.findFileByID",param);
		return m;
	}
	
}
