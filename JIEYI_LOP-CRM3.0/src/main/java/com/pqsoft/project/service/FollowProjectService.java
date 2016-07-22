package com.pqsoft.project.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class FollowProjectService {

	private String basePath = "followproject.";
	public List getFollowProject(Map m)
	{
		return Dao.selectList(basePath+"getfollowproject", m);
	}
	public List getSpendProject(Map m)
	{
		return Dao.selectList(basePath+"getSpendProject",m);
	}
	public Map addfollow(Map m)
	{
		String ID=Dao.getSequence("SEQ_FIL_FOLLOW_PROJECT");
		m.put("ID", ID);
		int i=Dao.insert(basePath+"addfollow",m);
		m.put("i", i);
		return m;
	}
	public Map addspend(Map m)
	{
		String ID=Dao.getSequence("SEQ_FIL_SPEND_PROJECT");
		m.put("ID", ID);
		int i=Dao.insert(basePath+"addspend",m);
		m.put("i", i);
		return m;
	}
	
	public int delfollow(Map m)
	{
		return Dao.delete(basePath+"delfollow",m);
	}
	public int delspend(Map m)
	{
		return Dao.delete(basePath+"delspend",m);
	}
	
	public void queryProjectBaseInfo(Map map)
	{
		Map mapInfo=Dao.selectOne(basePath+"queryProjectBaseInfo", map);
		map.putAll(mapInfo);
	}
	
}
