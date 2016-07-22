package com.pqsoft.bpm.handler;

import java.util.List;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

import com.pqsoft.skyeye.api.Dao;

public class FindAreaPerson implements AssignmentHandler {

	/**
	 * @author:lishuo 2016年1月11日10:29:20
	 */
	private static final long serialVersionUID = 1L;
	private static String op =null;
	/**
	 * @author:lishuo 2016年1月11日10:29:26
	 */
	@Override
	public void assign(Assignable assignable, OpenExecution execution) throws Exception {
		String PROVINCE_NAME =(String) execution.getVariable("PROVINCE_NAME");
		List<String> list = Dao.selectList("bpm.handler.queryContract_Man", PROVINCE_NAME);
			if(!list.isEmpty() && list.size() > 0){
				//若查询的CODE存在则给指定合同负责人，否则指定给合同审核主管
				op =list.get(0);
				assignable.setAssignee(op);
			}else {
				op = "htshzg";
				assignable.setAssignee(op);
			}
		}
}