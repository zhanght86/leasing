package com.pqsoft.fundPlan.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class FundplanService {

	public List<Map<String,Object>> getproject(Map<String,Object> m)
	{
		return Dao.selectList("fundPlan.getproject",m);
	}
	public Map<String,Object>getAccountsReceivable(Map<String,Object> m)
	{
		return Dao.selectOne("fundPlan.getAccountsReceivable",m);
	}
	
	public Map<String,Object>getPayable(Map<String,Object> m)
	{
		return Dao.selectOne("fundPlan.getPayable",m);
	}
}
