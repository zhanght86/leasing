package com.pqsoft.user.action;

import java.util.Map;

import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.user.service.AgentService;

public class AgentAction extends Action {

	// 代办
	@Override
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply execute() {

		return new ReplyHtml(VM.html("user/agent.vm", null));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply getPage() {
		Map<String, Object> param = _getParameters();
		param.put("USER_ID", Security.getUser().getId());
		return new ReplyAjaxPage(new AgentService().getPage(param));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply doAdd() {
		Map<String, Object> param = _getParameters();
		param.put("USER_ID", Security.getUser().getId());
		boolean flag = new AgentService().add(param);
		if (flag) {
			return new ReplyAjax();
		} else {
			return new ReplyAjax(flag, "添加失败");
		}
	}

}
