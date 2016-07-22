package com.pqsoft.project.service;

import java.util.HashMap;
import java.util.Map;
import com.pqsoft.skyeye.api.Dao;

public class EarningsService {
	
	private final String path="earnings.";
	
	/*
	 * 查询收益分析
	 */
	public Map<String, Object> selectEarnings(String CLIENT_ID){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CLIENT_ID", CLIENT_ID);
		return Dao.selectOne(path+"selectEarningsById", map);
	}
	
	/*
	 * 修改收益分析
	 */
	public int updateEarnings(Map<String, Object> map){
		return Dao.update(path+"updateEarning", map);
	}
	
	/*
	 * 增加收益分析
	 */
	public int addEarnings(Map<String, Object> map){
		return Dao.insert(path+"addEarnings", map);
	}
	
	/*
	 * 删除收益分析
	 */
	public int deleteEarning(String CLIENT_ID){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CLIENT_ID", CLIENT_ID);
		return Dao.delete(path+"deleteEarningsById", map);
	}
}
