package com.pqsoft.crm.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class TrackService {

	public void addConfig(Map<String, Object> param) {
		param.put("ID", Dao.getSequence("SEQ_FIL_TRACK_CONFIG"));
		Dao.insert("crm.track.addConfig", param);
	}

	public void upConfig(Map<String, Object> param) {
		Dao.update("crm.track.upConfig", param);
	}

	public List<Map<String, Object>> getList() {
		return Dao.selectList("crm.track.getList");
	}

	public Map<String, Object> get(String id) {
		return Dao.selectOne("crm.track.get", id);
	}

	public void del(String id) {
		Dao.delete("crm.track.del", id);
	}

	public void addTrackBat(Map<String, Object> param) {
		param.put("ID", Dao.getSequence("SEQ_FIL_TRACK"));
		param.put("STATUS", "");
		Dao.insert("crm.track.addTrack", param);
	}
	
	public void addTrack(Map<String, Object> param) {
		param.put("ID", Dao.getSequence("SEQ_FIL_TRACK"));
		Dao.insert("crm.track.addTrack", param);
	}

	public Map<String, Object> getInfo(String id) {
		return Dao.selectOne("crm.track.getInfo", id);
	}

	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, "crm.track.getPageList", "crm.track.getPageCount");
	}

	public void upTrack(Map<String, Object> param) {
		Dao.update("crm.track.upTrack", param);
	}

}
