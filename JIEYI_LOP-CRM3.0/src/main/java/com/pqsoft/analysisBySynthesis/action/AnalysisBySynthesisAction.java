package com.pqsoft.analysisBySynthesis.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class AnalysisBySynthesisAction extends Action {

	private final String pagePath = "analysisBySynthesis/";
	
	
	@Override
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = {"客户管理", "综合分析标签页", "综合分析总页面" })
	public Reply execute() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param",param);
		return new ReplyHtml(VM.html(pagePath+"toMgAnaBySynthesisMain.vm", context));
	}
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = {"客户管理", "综合分析标签页", "综合分析总页面-查看" })
	public Reply query() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param",param);
		return new ReplyHtml(VM.html(pagePath+"queryMgAnaBySynthesisMain.vm", context));
	}
	
	
}
