package com.pqsoft.project.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class ProjectInvalidService {

	public Page getProjectPage(Map<String, Object> m) {
		m.put("tags1", "业务类型");
		m.put("tags2", "项目状态位");
		m.put("tags3", "客户类型");
		return PageUtil.getPage(m, "projectInvalid.getAllProject", "projectInvalid.getAllProject_count");
	}

}
