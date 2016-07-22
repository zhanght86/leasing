package com.pqsoft.secu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.pqsoft.skyeye.api.AbstractCache;
import com.pqsoft.skyeye.api.Dao;

public class AuthMemcached  extends AbstractCache<ArrayList>{
	@Override
	public ArrayList update(String id) {
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("ROLE_ID", id);
		return (ArrayList)Dao.selectList("auth.getRolePerList",param);
	}
}
