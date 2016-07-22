package com.pqsoft.baopei.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class BaopeiService {

	public Page getList(Map<String, Object> m) {
		return PageUtil.getPage(m, "baopei.getAll", "baopei.getAll_count");
	}
	public int updategua(Map<String,Object> param)
	{
		return Dao.update("baopei.updategua",param);
	}
	public int updatelai(Map<String,Object> param)
	{
		return Dao.update("baopei.updatelai", param);
	}
}
