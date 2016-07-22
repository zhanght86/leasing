package com.pqsoft.leeds.talkSkill.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.ctc.wstx.util.StringUtil;
import com.pqsoft.leeds.utils.VelocityUtil;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class CallVerifyService {
	private static final String PATH = "talkSkill.";
	private TalkSkillService tsService = new TalkSkillService();
	public List<Map<String, Object>> loadTpls(Map<String, Object> params) {
		//根据PROJECT_ID，查询模板列表
		List<Map<String, Object>> records = Dao.selectList(PATH+"loadTpls", params);
		for(Map<String, Object> record : records) {
			tsService.getMergeRecord(record, params);
		}
		return records;
	}

	public void updateConsist(Map<String, Object> params) {
		params.put("MEMO", params.get("TSC_MEMO"));
		Dao.update(PATH+"updateConsist", params);
	}

	public String saveConsist(Map<String, Object> params) {
		String id = Dao.getSequence("SEQ_TALK_SKILL_CONSIST");
		params.put("TSC_ID", id);
		params.put("MEMO", params.get("TSC_MEMO"));
		Dao.insert(PATH+"saveConsist", params);
		return id;
	}
	
}
