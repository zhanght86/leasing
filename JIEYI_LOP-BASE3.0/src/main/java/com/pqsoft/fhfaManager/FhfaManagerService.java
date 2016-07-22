package com.pqsoft.fhfaManager;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class FhfaManagerService {
	
	private final String path="fhfaManager." ;
	
	//通过主键ID查询融资租赁公司信息的管理
	public Map<String,Object> selectFHFAManagerById(Long id){
		
		return Dao.selectOne(path + "selectFHFAManagerById", id) ;
	}
}
