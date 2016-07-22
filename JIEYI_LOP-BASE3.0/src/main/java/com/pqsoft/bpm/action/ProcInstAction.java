package com.pqsoft.bpm.action;

import java.util.Map;

import org.jbpm.api.ProcessInstance;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

/**
 * 流程实例管理类
 */
public class ProcInstAction extends Action {

	@Override
	public Reply execute() {
		return null;
	}

//	@aAuth(type = aAuth.aAuthType.USER)
//	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
//	@aPermission(name = { "流程中心", "流程实例管理", "结束流程" })
//	public Reply doEndProcessInstance() throws Exception {
//		Map<String, Object> m = _getParameters();
//		String instanceId = m.get("id") == null ? "" : m.get("id").toString();
//		JBPM.endProcessInstance(instanceId, ProcessInstance.STATE_ENDED);
//		return new ReplyAjax();
//	}

}
