package com.pqsoft.blackcust.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.blackcust.service.BlackCustManageService;
import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.JsonUtil;

/**
 * 黑名单管理
 * @author caizhongyang
 *
 */
public class BlackCustManageAction extends Action {
	public VelocityContext context = new VelocityContext();
	private String path="blackcust/";
	private BlackCustManageService service=new BlackCustManageService();
	
	@Override
	@aPermission (name = {"业务管理","黑名单管理","列表显示"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply execute() {
		return new ReplyHtml(VM.html(path+"blackCustManage.vm",context));
	}
	@aPermission (name = {"业务管理","黑名单管理","列表显示"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply getBlackCustManageData() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		context.put("PContext", json);
		return new ReplyAjaxPage(service.getPage(param));
	}
	@aPermission (name = {"业务管理","黑名单管理","列表显示"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply getBlackCustManageTest() {
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"blackCustManageTest.vm",context));
	}
	
	@aPermission (name = {"业务管理","黑名单管理","添加"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply toAddBlackCustVm() {
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"addBlackCust.vm",context));
	}
	
	@aPermission (name = {"业务管理","黑名单管理","添加"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply addBlackCust() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("param"));
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		return new ReplyAjax(service.addBlackCust(param));
	}
	
	@aPermission (name = {"业务管理","黑名单管理","修改"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply updateBlackCust() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("param"));
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		return new ReplyAjax(service.updateBlackCust(param));
	}
	
	
	@aPermission (name = {"黑名单管理"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply queryBlackCustById() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		context.put("list", service.queryBlackCustById(param).get(0));
		return new ReplyHtml(VM.html(path+"blackCustView.vm",context));
	}
	
	@aPermission (name = {"黑名单管理"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply queryBlackCustById2() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		return new ReplyAjax(service.queryBlackCustById(param).get(0));
	}
	
	
	@aPermission (name = {"黑名单管理"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply toUpdateBlackCustVmById() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		context.put("list", service.queryBlackCustById(param).get(0));
		return new ReplyHtml(VM.html(path+"updateBlackCustView.vm",context));
	}
	
	@aPermission (name = {"黑名单管理"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply cancelBlackCust() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		return new ReplyAjax(service.cancelBlackCust(param));
	}
	
	
	/****/
	@aPermission (name = {"黑名单管理"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply startCacelContractProjectByJbpm(){
		Map<String,Object> param = _getParameters();
		List<String> list = JBPM.getDeploymentListByModelName("合同撤销审批",
				null, Security.getUser()
						.getOrg().getPlatformId());
		String pid = null;
		if (list.size() > 0) {
			pid = list.get(0);
		} else
			throw new ActionException("没有找到流程图");

		if (pid == null) {
			throw new ActionException("没有匹配到审批流程");
		}
		Map<String, Object> jmap = new HashMap<String, Object>();
		
		jmap.put("ID", param.get("ID").toString());
		String jbpmId = JBPM.startProcessInstanceById(pid,
				"", "","", jmap).getId();
		//map.put("STATUS", 1);
		context.put("MSG", "1");
		return new ReplyHtml(VM.html(path + "contractProjectManage.vm", context))
				.addOp(new OpLog("", "", param + ""));
	}
}