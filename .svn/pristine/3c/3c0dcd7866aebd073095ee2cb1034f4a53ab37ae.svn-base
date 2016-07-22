package com.pqsoft.documentApp.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class RemindDossierService {
	private final String xmlPath = "dossierApp.remind.";

	public Page toRemindDossier(Map<String, Object> m) {
		//return Dao.selectList(xmlPath+"toRemindDossier", map);
		m.put("type","业务类型");
		m.put("type1","事业部");
		m.put("type2","客户类型");
		return PageUtil.getPage(m, "dossierApp.remind.toRemindDossier", "dossierApp.remind.toRemindDossierCount");
	}

	public List<Map<String, Object>> toShowRemindApp(Map<String, Object> map) {
		map.put("YJ", "原件");
		map.put("FYJ", "复印件");
		return Dao.selectList(xmlPath+"toShowRemindApp", map);
	}

	public List<Map<String, Object>> toNotFileDossier(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return Dao.selectList(xmlPath+"toShowRemindApp", map);
	}

	public List<Map<String, Object>> getRemindData(Map<String, Object> map){
		return Dao.selectList(xmlPath+"getRemindData", map);
	}

}
