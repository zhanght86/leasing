package com.pqsoft.bpm;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

import com.pqsoft.skyeye.api.SkyEye;

public class GroupAssignee implements AssignmentHandler {

	private static final long serialVersionUID = -6114174557628498066L;
	public String roleName;

	@Override
	public void assign(Assignable assignable, OpenExecution execution) {
		String code = (String) SkyEye.getRequest().getAttribute("_TEMP_OP");
		if (code == null) throw new RuntimeException("无法获取对应操作人");
		assignable.setAssignee(code);
	}

}
