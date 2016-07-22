package com.pqsoft.bpm.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

import com.pqsoft.skyeye.api.Dao;

/**
 * 查找发起人
 * 
 * @author King 2013-10-26
 */
public class StartPersonCodeAssignee implements AssignmentHandler {

	/**
	 * @author:King 2013-10-26
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void assign(Assignable assignable, OpenExecution execution) throws Exception {
		String JBPMID = execution.getId();
		List<String> list = Dao.selectList("bpm.handler.queryStartPersonCode", JBPMID);
		if (!list.isEmpty() && list.size() > 0) {
			assignable.setAssignee(list.get(0));
		} else {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("JBPMID", JBPMID);
			String op = Dao.selectOne("bpm.handler.queryStartPersonCode2", param);
			// 当没有找到人员时，将任务转给admin超级管理员
			if (op == null || "".equals(op)) op = (String) execution.getVariable("CREATEUSERCODE");
			if (op == null || "".equals(op)) op = "admin";
			assignable.setAssignee(op);
		}
	}
}
