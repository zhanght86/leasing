package com.pqsoft.bpm.handler;

import java.util.List;
import java.util.Map;

import org.jbpm.api.ProcessInstance;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Dao;

public class ProInstHandler {

	/**
	 * 结束项目关联的所有流程
	 */
	public void endInsByProject(String jbpmId) {
		Map<String, Object> map = JBPM.getProInst(jbpmId);
		if (map == null) return;
		String projectId = (String) map.get("PROJECT_ID");
		if (projectId == null) return;
		map.put("jbpmId", jbpmId);
		List<String> list = Dao.selectList("bpm.procinst.getProInstByProjectNotJbpmId", map);
		for (String instanceId : list) {
			JBPM.endProcessInstance(instanceId, ProcessInstance.STATE_ENDED);
		}
	}

}
