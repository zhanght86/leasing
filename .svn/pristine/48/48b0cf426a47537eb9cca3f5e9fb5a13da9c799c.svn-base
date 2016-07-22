package com.pqsoft.bpm.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;

/**
 * 查找省份人员
 * 
 *
 */
public class Fundprovincepersonnel implements AssignmentHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String ROLENAME = null;//角色名称

	@Override
	public void assign(Assignable assignable, OpenExecution execution) throws Exception {
		if(ROLENAME==null){
			throw new ActionException("流程图未配置角色名称");
		}
		String PROJECT_ID = execution.getVariable("PROJECT_ID").toString();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("PROJECT_ID", PROJECT_ID);
		map.put("ROLENAME", ROLENAME);
		String op = Dao.selectOne("bpm.handler.queryProjectProvince",map);
		// 当没有找到人员时，将任务转给admin超级管理员
		if (op == null || "".equals(op)) op = "admin";
		assignable.setAssignee(op);
	}
}
