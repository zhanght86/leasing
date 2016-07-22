package com.pqsoft.zcfl.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class ClassifyTaskService {

	private final String mapper = "zcfl.classifytask.";

	public Page pageTemplate(Map<String, Object> param) {
		return PageUtil.getPage(param, mapper + "list", mapper + "listCount");
	}

}
