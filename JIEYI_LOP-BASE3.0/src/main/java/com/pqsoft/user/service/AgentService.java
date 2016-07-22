package com.pqsoft.user.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class AgentService {

	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, "user.agent.getPageList", "user.agent.getPageCount");
	}

	public boolean add(Map<String, Object> param) {
		return 1 == Dao.insert("user.agent.add", param);
	}

}
