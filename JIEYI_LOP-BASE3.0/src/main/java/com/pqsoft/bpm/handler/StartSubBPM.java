package com.pqsoft.bpm.handler;

import java.util.List;
import java.util.Map;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;

/**
 * 触发子流程
 * 
 * @author King 2013-9-22
 */
public class StartSubBPM {

	/**
	 * 发起大项目评审子流程
	 * 
	 * @param jbpmId
	 * @author:King 2013-9-22
	 */
	public void startCompanyConfirm(String jbpmId) {
		Map<String, Object> map = JBPM.getVeriable(jbpmId);
		List<String> list = JBPM.getDeploymentListByModelName("评审会");
		String pid = null;
		if (list.size() > 0)
			pid = list.get(0);
		else
			throw new ActionException("没有找到流程图");
		String PROJECT_ID=map.get("PROJECT_ID").toString();
		String CLIENT_ID=Dao.selectOne("bpm.handler.queryClientIdByProjectId", map);
		JBPM.startProcessInstanceById(pid, PROJECT_ID, CLIENT_ID, null, map);
	}
}
