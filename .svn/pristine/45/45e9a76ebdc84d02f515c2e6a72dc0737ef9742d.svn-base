package com.pqsoft.bpm.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.StringUtils;

public class StartAreaCodeAssignee implements AssignmentHandler {

	public void assign(Assignable assignable, OpenExecution execution) throws Exception {
		String JBPMID = execution.getId();
		String RegionalManager=Dao.selectOne("bpm.handler.queryStartAreaCode",JBPMID);
		if(StringUtils.isBlank(RegionalManager)){
			RegionalManager="admin";
		}
		assignable.setAssignee(RegionalManager);
	}
}
