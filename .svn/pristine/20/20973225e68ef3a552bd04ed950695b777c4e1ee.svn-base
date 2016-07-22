package com.pqsoft.fundPlan.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.fundPlan.service.FundPlanWorkFlowService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class FundPlanWorkFlowAction extends Action {
	
	private String basePath = "fundPlan/"; 
	private FundPlanWorkFlowService service = new FundPlanWorkFlowService();
	
	@Override
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply execute() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("DETAIL", service.getDetail(param));
		return new ReplyHtml(VM.html(basePath + "fundPlanOnlyReadShow.vm", context));
	}
	
	/**
	 * 用于分页查询
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply pageData(){
		Map<String,Object> param = _getParameters();
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	
	/**
	 * 用于项目作废查询
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply projInvalidPage() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("DETAIL", service.getDetailProjectInvalid(param));
		return new ReplyHtml(VM.html(basePath + "fundPlanOnlyReadProjectInvalidShow.vm", context));
	}
	
}
