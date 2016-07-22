package com.pqsoft.shangpai.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class ShangpaiService {
	public Page findshangpai(Map<String,Object> m)
	{
		return PageUtil.getPage(m, "shangpai.findshangpai", "shangpai.findshangpai_count");
	}
	public int saveshangpai(Map<String,Object> param)
	{
		return Dao.insert("shangpai.saveshangpai", param);
	}
	@SuppressWarnings("unchecked")
	public Map findone(Map<String,Object> param)
	{
		return Dao.selectOne("shangpai.findone", param);
	}
	public int updateshangpai(Map<String,Object> param)
	{
		return Dao.update("shangpai.updateshangpai", param);
	}
}
