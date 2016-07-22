package com.pqsoft.bpm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

import com.pqsoft.skyeye.api.Dao;

public class FindAreaPerson implements AssignmentHandler {

	/**
	 * @author:lishuo 2016年1月6日18:12:56
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @author:lishuo 2016年1月6日18:12:59
	 */
	@Override
	public void assign(Assignable assignable, OpenExecution execution) throws Exception {
		String JBPMID = execution.getId();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("JBPMID", JBPMID);
		//查询出财务的CODE
		List<Map<String,Object>> listCode = Dao.selectList("bpm.handler.findCode", map);
		if (!listCode.isEmpty() && listCode.size() > 0) {
			Map<String,Object> m = listCode.get(0);	// 当前发起人对象
			String CODE = (String)m.get("CODE");	// 当前对象名称
			if("".equals(CODE)||CODE==null) CODE="admin";
			assignable.setAssignee(CODE);
			}
		}
	}
