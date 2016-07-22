package com.pqsoft.bpm.handler;

import java.util.List;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;

public class RefundFuZongAssignee implements AssignmentHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void assign(Assignable assign, OpenExecution execution) throws Exception {
		String JBPM_ID = execution.getId();
		String op = null;
		List<String> list = Dao.selectList("bpm.handler.getFuZongCode", JBPM_ID);
		for (String str : list) {
			if (str != null && !"".equals(str)) {
				op = str;
				break;
			}
		}
		if (op == null || "".equals(op)) throw new ActionException("未找到流程的发起人!");
		assign.setAssignee(op);
	}

}
