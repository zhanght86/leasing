package com.pqsoft.insure.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class InsureRecordService {

	public Page getPageList(Map<String, Object> param) {
		return PageUtil.getPage(param, "insure.record.getPageList", "insure.record.getPageCount");
	}

	public void saveInsuer(Map<String, Object> param) {
		Dao.update("insure.record.saveInsuer", param);
	}

	public void toSub(String id) {
		if (id == null) return;
		Dao.update("insure.record.toSub", id);
	}

	public void toSubAll(Map<String, Object> param) {
		Dao.update("insure.record.toSubAll", param);
	}

	public void addFile(Map<String, Object> fileMap) {
		Dao.insert("insure.record.addFile", fileMap);
	}

	public void delFile(Map<String, Object> param) {
		Dao.delete("insure.record.delFile", param);
	}
}
