package com.pqsoft.secu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.pqsoft.skyeye.api.AbstractCache;
import com.pqsoft.skyeye.api.Dao;

public class MenuMemcached  extends AbstractCache<ArrayList>{
	@Override
	public ArrayList<menuEntity> update(String id) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("MENU_ID", id);
		menuEntity entity = (menuEntity) Dao.selectOne("SecMenu.getUpdateMenu", map);
		ArrayList<menuEntity> list=new ArrayList<menuEntity>();
		list.add(entity);
		return list;
	}
}
