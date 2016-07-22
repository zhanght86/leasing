package com.pqsoft.taskDictionary.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.pqsoft.skyeye.api.AbstractCache;
import com.pqsoft.skyeye.api.Dao;

public class TaskDictionaryMemcached extends AbstractCache<ArrayList>{

	/**
	* @Description: TODO 数据字典Memcached
	* @author 齐姜龙
	* @param m
	* @return
	* @throws Exception 
	* @date 2013-8-5
	 */
	@Override
	public ArrayList update(String type) {
		ArrayList list=new ArrayList();
		//list逻辑
		Map map=new HashMap();
		map.put("TYPE", type);
		list=(ArrayList)Dao.selectList("TaskDictionary.getDataTypeInfoByStatus", map);
		return list;
	}
	
	
}
