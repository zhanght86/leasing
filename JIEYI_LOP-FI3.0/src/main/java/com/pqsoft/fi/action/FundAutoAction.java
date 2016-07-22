package com.pqsoft.fi.action;

import com.pqsoft.fi.service.FundAutoService;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

// 资金树
public class FundAutoAction extends Action {

	@Override
	@aAuth(type = aAuthType.ALL)
	@aPermission(name = { "资金管理", "资金树" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply execute() {
		FundAutoService service = new FundAutoService();
		boolean flag=service.auto(_getParameters());
		return new ReplyAjax(flag);
	}
	
	@aAuth(type = aAuthType.ALL)
	@aPermission(name = { "资金管理", "资金树" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply executeSQK() {
		FundAutoService service = new FundAutoService();
		service.autoSQK(_getParameters());
		return null;
	}

}
