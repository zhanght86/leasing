package com.pqsoft.bpm.action;

import java.util.Map;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class JBPMUtillAction extends Action {

	
	/**
	 * 
	 * <p>Title: execute</p> 
	 * <p>Description: TODO()</p> 
	 * @return 
	 * @see com.pqsoft.skyeye.api.Action#execute() 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name = { "流程", "删除", "删除" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	@Override
	public Reply execute() {
		Map<String, Object> map = this._getParameters();
		String jbpmid = (String) map.get("jbpmid");
		JBPM.deleteProcessInstanceCascade(jbpmid);
		
		return new ReplyAjax("已删除流程【"+jbpmid+"】");
	}

}
