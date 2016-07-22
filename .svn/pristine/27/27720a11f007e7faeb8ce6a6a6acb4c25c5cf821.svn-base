package com.pqsoft.positions.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class PositionsService {
	private final String xmlPath="positions.";
	public Page doPositionsData(Map<String, Object> m) {
		return PageUtil.getPage(m, xmlPath+"querypositionsData",xmlPath+"querypositions_count");
	}

	public Map cq_data(Map<String, Object> m){
		return Dao.selectOne(xmlPath+"querypositionsCQData",m);
	}
	
	public Map querypositionsMXData(Map<String, Object> m){
		return Dao.selectOne(xmlPath+"querypositionsMXData",m);
	}
}
