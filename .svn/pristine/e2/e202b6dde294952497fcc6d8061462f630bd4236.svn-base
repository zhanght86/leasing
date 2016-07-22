package com.pqsoft.fundPlan.action;

import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.fundPlan.service.FundplanService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class FundplanAction extends Action{

	FundplanService service = new FundplanService();
	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "资金计划"})
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("fundplan/fundplan.vm", context));
	}
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply getFund() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param=_getParameters();
		System.out.println(param+"==============");
		List<Map<String,Object>> list=service.getproject(param);
		//应收
		Map M1=service.getAccountsReceivable(param);
		//应付
		Map M2=service.getPayable(param);
		context.put("list", list);
		context.put("YS", M1);
		context.put("YF", M2);
		return new ReplyHtml(VM.html("fundplan/getFundplan.vm", context));
	}

}
