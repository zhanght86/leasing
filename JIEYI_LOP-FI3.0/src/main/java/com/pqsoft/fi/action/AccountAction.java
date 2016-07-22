package com.pqsoft.fi.action;

import java.util.Map;

import com.pqsoft.fi.service.AccountService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class AccountAction extends Action {

	@Override
	@aPermission(name = { "资金管理", "账户管理" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply execute() {
		return new ReplyHtml(VM.html("fi/account.vm", null));
	}

	@aPermission(name = { "资金管理", "账户管理" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply getPage() {
		Map<String, Object> param = _getParameters();
		param.put("USERCODE", Security.getUser().getCode());
		return new ReplyAjaxPage(new AccountService().getPage(param));
	}
}
