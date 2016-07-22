package com.pqsoft.base.grantCredit.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.Code.service.CodeService;
import com.pqsoft.base.grantCredit.service.ClientCreditService;
import com.pqsoft.base.grantCredit.service.CreditLogService;
import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class ClientCreditManagerAction extends Action {
	private String path = "base/grantCredit/";
	private ClientCreditService creditService = new ClientCreditService();

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "授信管理", "客户授信管理", "列表" })
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply execute() {
		return new ReplyHtml(VM.html(path + "clientCreditManager.vm", null));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	// @aPermission(name ={"授信管理","经销商授信管理","列表"})
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply pageData() {
		Map<String, Object> param = _getParameters();
		Page pagedata = creditService.getCreditPage(param);
		return new ReplyAjaxPage(pagedata);
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "授信管理", "客户授信管理", "添加授信" })
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Reply toAddClientCredit() {
		VelocityContext context = new VelocityContext();
		context.put("param",
				creditService.queryClientIfno(_getParameters().get("CLIENT_ID")+""));
		context.put("CUGP_CODE", CodeService.getCode("经销商授信协议编号", null, null));
		context.put("MSG", "-1");
		return new ReplyHtml(VM.html(path + "addClientCredit.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name={"授信管理","经销商授信管理","添加授信"})
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Object doAddClientCredit() {
		Map<String, Object> param = _getParametersIO(null, null, null);
		param.put("CREATE_ID", Security.getUser().getId());
		param.put("TYPE", "1");
		param.put("STATUS", "2");
		int i = creditService.doAddCompanyCredit(param);
		VelocityContext context = new VelocityContext();
		if (i >= 1) {
			List<String> list = JBPM.getDeploymentListByModelName("客户授信审批",
					"", "");
			String pid = null;
			if (list.size() > 0) {
				pid = list.get(0);
			} else
				throw new ActionException("没有找到流程图");

			if (pid == null) {
				throw new ActionException("没有匹配到审批流程");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("CUGP_ID", param.get("CUGP_ID") + "");
			map.put("CLIENT_ID", param.get("CUST_ID") + "");
			context.put("CUGP_CODE", param.get("CUGP_CODE") + "");
			String jbpmid = JBPM.startProcessInstanceById(pid, "", param.get("CUST_ID") + "", "", map)
					.getId();
			context.put("RST", jbpmid);
			
			context.put("param", creditService.queryClientIfno(param.get("CUST_ID") + ""));
			
		}
		param.put("MEMO", "客户授信添加");
		CreditLogService logService = new CreditLogService();
		logService.doInsertCreditLog(param);
		context.put("map", param);
		context.put("MSG", "1");
		return new ReplyHtml(VM.html(path + "addCompanyCredit.vm", context))
				.addOp(new OpLog("授信管理", "添加授信", param + ""));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name={"授信管理","经销商授信管理","经销商授信流程查看页面"})
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Reply toShowClientCreditForJbpm() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		param = creditService.queryCreditInfoById(param.get("CUGP_ID") + "");
		context.put("param", param);
		param.put("CLIENT_ID", param.get("CUST_ID"));
		context.put("sup", creditService.queryClientIfno(param.get("CUST_ID") + ""));
		return new ReplyHtml(VM.html(path + "showClientCreditForJbpm.vm", context));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name={"授信管理","经销商授信管理","添加授信"})
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Reply toShowClientCredit() {
		Map<String, Object> param = _getParameters();
		CreditLogService logService = new CreditLogService();
		VelocityContext context = new VelocityContext();
		param = creditService.queryCreditInfoById(param.get("CUGP_ID") + "");
		context.put("param", param);
		param.put("SUP_ID", param.get("CUST_ID"));
		context.put("sup", creditService.queryClientIfno(param.get("CUST_ID") + ""));
		context.put("log", logService.toFindCreditLog(param));
		return new ReplyHtml(VM.html(path + "showClientCredit.vm",
				context));
	}
	
	
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name={"授信管理",  "客户授信管理","修改授信"})
	@aDev(code = "170039", name = "yangxue", email = "yangxue@pqsoft.cn")
	public Reply toUpClientCredit(){
		Map<String, Object> param = _getParameters();
		CreditLogService logService = new CreditLogService();
		VelocityContext context = new VelocityContext();
		param = creditService.queryCreditInfoById(param.get("CUGP_ID") + "");
		context.put("param", param);
		param.put("SUP_ID", param.get("CUST_ID"));
		context.put("sup", creditService.queryClientIfno(param.get("CUST_ID") + ""));
		context.put("log", logService.toFindCreditLog(param));
		return new ReplyHtml(VM.html(path + "updateClientCredit.vm",
				context));
	}
	
	
	@aAuth(type = aAuthType.LOGIN)
	// @aPermission(name={"授信管理",  "客户授信管理","获取商务政策"})
	@aDev(code = "170039", name = "yangxue", email = "yangxue@pqsoft.cn")
	public Reply toFindScheme(){
		Map<String, Object> param = _getParameters();
		List<Map<String,Object>> list= (List<Map<String, Object>>) creditService.toFindScheme(param);
		VelocityContext  context=new VelocityContext();
		context.put("list", list);
		return  new ReplyHtml(VM.html(path + "toShowScheme.vm",context));
	}
}
