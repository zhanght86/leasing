package com.pqsoft.fi.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class AccountService {

	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, "fi.account.getPageList", "fi.account.getPageCount");
	}

}
