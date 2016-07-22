package com.pqsoft.insure.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class InsureConfirmService {

	public Page getPageList(Map<String, Object> param) {
		return PageUtil.getPage(param, "insure.confirm.getPageList", "insure.confirm.getPageCount");
	}

	public void toSub(String id) {
		if (id == null) return;
		Dao.update("insure.confirm.toSub", id);
	}

	public void toSubNo(String id) {
		if (id == null) return;
		Dao.update("insure.confirm.toSubNo", id);
	}

	public void toSubAll(Map<String, Object> param) {
		Dao.update("insure.confirm.toSubAll", param);
	}

	public Map<String, Object> getFile(String id) {
		return Dao.selectOne("insure.confirm.getFile", id);
	}

	public List<Map<String, Object>> getFileList(String id) {
		return Dao.selectList("insure.confirm.getFileByIRID", id);
	}

	public void delFile(String id) {
		Dao.delete("insure.confirm.delFile", id);
	}

}
