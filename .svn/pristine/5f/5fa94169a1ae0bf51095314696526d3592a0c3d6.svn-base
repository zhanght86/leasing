package com.pqsoft.lendingLog.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.lendingLog.service.LendingLogService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class LendingLogAction extends Action {

	private LendingLogService service = new LendingLogService();
	
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "放款管理", "放款日志", "列表" })
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("lendingLog/lendingLog.vm", context));
	}
	
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "放款管理", "放款日志", "列表" })
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply getLendingLogPage(){
		Map<String,Object> param = _getParameters();
		return new ReplyAjaxPage(service.getPage(param));
	}

}
