package com.pqsoft.bpm.handler;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Dao;

/**
 * 根据项目id判断商务板块，并确定最后流程走向
 * 
 * @author King 2013-9-22
 */
public class BusinessSectorDecision implements DecisionHandler {
	private String namespace = "bpm.handler.";
	/**
	 * @author:King 2013-9-22
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String decide(OpenExecution execution) {
		// 项目id
//		String PROJECT_ID = execution.getVariable("PROJECT_ID") + "";
//		System.out.println("king="+PROJECT_ID+"lll"+execution.getVariables());
		String PROJECT_ID = JBPM.getVeriable(execution.getId()).get("PROJECT_ID")+"";
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("_TYPE", "PDF模版所属商务板块");
		param.put("PROJECT_ID", PROJECT_ID);
		Map<String, Object> map = Dao.selectOne(namespace + "queryProSchemeByProId", param);
		// 此处需要根据项目id查询商务板块
		String businessSector = map.get("BUSINESS_SECTOR") + "";
		String outline = null;
		if (businessSector.contains("商用车")) {
			outline = "商用车板块";
		} else {
			outline = "建机板块";
		}
		return outline;
	}

}
