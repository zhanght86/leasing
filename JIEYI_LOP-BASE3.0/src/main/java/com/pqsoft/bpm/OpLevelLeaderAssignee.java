package com.pqsoft.bpm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;

public class OpLevelLeaderAssignee implements AssignmentHandler {

	private static final long serialVersionUID = 42763487263847632L;

	public String POSTYPE = "BMJL";// 默认找部门经理

	@Override
	public void assign(Assignable assignable, OpenExecution execution) throws Exception {
		String JBPMID = execution.getId();
		String op = null;
		List<String> list = Dao.selectList("bpm.handler.queryStartPersonCode", JBPMID);
		for (String str : list) {
			if (str != null && !"".equals(str)) {
				op = str;
				break;
			}
		}
		Map<String, Object> param = new HashMap<String, Object>();
		if (op == null || "".equals(op)) {
			param.put("JBPMID", JBPMID);
			op = Dao.selectOne("bpm.handler.queryStartPersonCode2", param);
		}
		if (op == null || "".equals(op)) op = (String) execution.getVariable("CREATEUSERCODE");
		if (op == null || "".equals(op)) throw new ActionException("未找到流程的发起人!");
		param.put("USERCODE", op);
		param.put("POSTYPE", POSTYPE);
		List<String> org = Dao.selectList("bpm.handler.getOrgId", param);
		for (String string : org) {
			if (org == null) continue;
			param.put("ORG_ID", string);
			op = Dao.selectOne("bpm.handler.getOpPos", param);
			if (op != null && !"".equals(op)) {
				assignable.setAssignee(op);
				return;
			}
		}
		throw new ActionException("未找到下个节点对应审批人!");
	}

}
