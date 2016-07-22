package com.pqsoft.bpm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class RemarksService {

	private final String mapper = "bpm.remarks.";
	
	/**
	 * 添加备注信息
	 * 
	 * @return boolean
	 */
	public boolean addRemarks(String jbpmId,String opName,String opCode,String remarks) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("JBPM_ID", jbpmId);
		param.put("OP_NAME", opName);
		param.put("OP_CODE", opCode);
		param.put("REMARKS", remarks);
		return 1 == Dao.insert(mapper + "addRemarks", param);
	}
	
	/**
	 * 根据jbpmId 查询备注信息
	 * 
	 * @param jbpmId
	 * @return
	 */
	public List<Map<String, Object>> getRemarks(String jbpmId) {
		String[] strs = jbpmId.split("[.]");
		if (strs.length > 2) {
			jbpmId = strs[0] + "." + strs[1];
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("JBPM_ID", jbpmId);
		return Dao.selectList(mapper + "getRemarks", param);
	}
	
}
