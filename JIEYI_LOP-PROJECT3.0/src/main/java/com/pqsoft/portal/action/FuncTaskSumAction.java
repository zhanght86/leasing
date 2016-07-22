package com.pqsoft.portal.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.portal.service.FuncTaskSumService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.BaseUtil;

public class FuncTaskSumAction extends Action {

	@Override
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "lichao")
	public Reply execute() {
		FuncTaskSumService service = new FuncTaskSumService();
		Map<String, Object> param = _getParameters();
		BaseUtil.getDataAllAuth(param);
		VelocityContext context = new VelocityContext();
		System.out.println("查询功能任务数量");
		context.put("projectCount", service.getProjectCount(param));
		System.out.println("查询功能任务数量");
		context.put("startCount", service.getStartCount(param));
		System.out.println("查询功能任务数量");
		context.put("fundCount", service.getFundCount(param));
		System.out.println("查询功能任务数量");
		context.put("paymentCount", service.getPaymentCount(param));
		System.out.println("查询功能任务数量");
		context.put("remindCount", service.getRemindCount(param));
		System.out.println("查询功能任务数量");
		context.put("overdueCount", service.getOverdueCount(param));
		System.out.println("查询功能任务数量");
		return new ReplyHtml(VM.html("portal/fts.vm", context));
	}

}
