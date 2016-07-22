package com.pqsoft.leeds.sign_in.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class SignInService {
	private static final String PATH = "signIn.";

	public Page queryMainPage(Map<String, Object> params) {
		return PageUtil.getPage(params, PATH+"mainPage", PATH+"mainPageCount");
	}
	
	public List<Map<String, Object>> queryRecords(Map<String, Object> params) {
		return Dao.selectList(PATH+"queryRecords", params);
	}

	public void updateRecord(Map<String, Object> params) {
		Dao.update(PATH+"updateRecord", params);
	}

	public String insertRecord(Map<String, Object> params) {
		String id = Dao.getSequence("SEQ_SIGNIN_USER");
		params.put("ID", id);
		Dao.insert(PATH+"insertRecord", params);
		return id;
	}

	public int delRecord(Map<String, Object> params) {
		return Dao.delete(PATH+"delRecord", params);
	}
}
