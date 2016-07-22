package com.pqsoft.fi.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.fi.payin.service.FundComService;
import com.pqsoft.fi.service.FundTreeService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

// 资金树
public class FundTreeAction extends Action {

	@Override
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "资金管理", "资金树" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply execute() {
		FundComService service = new FundComService();
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("data", service.getData(param));
		context.put("tree", new FundTreeService().getTree(param));
		return new ReplyHtml(VM.html("fi/fund_tree.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "资金管理", "资金树" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply getDetail() {
		FundTreeService service = new FundTreeService();
		Map<String, Object> param = _getParameters();
		return new ReplyJson(service.getDetail(param));
	}

}
