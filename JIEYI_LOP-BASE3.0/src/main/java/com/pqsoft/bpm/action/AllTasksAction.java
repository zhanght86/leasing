package com.pqsoft.bpm.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.service.AllTasksService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class AllTasksAction extends Action {

	private AllTasksService service = new AllTasksService();
	
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "权限管理", "流程任务人管理", "列表" })
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("bpm/allTask.vm", context));
	}
	
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "权限管理", "流程任务人管理", "列表" })
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply getPage(){
		Map<String, Object> param = _getParameters();
		//modify by lishuo 12-30-2015 Strat
		param.put("USER_CODE", Security.getUser().getCode());
		param.put("USER_ID", Security.getUser().getId());
		param.put("USER_ORG_ID", Security.getUser().getOrg().getId());
		//modify by lishuo 12-30-2015 End
		return new ReplyAjaxPage(service.getPage(param));
	}
	
	/**
	 * add by lishuo 12-30-2015
	 * 区分开团队任务和流程任务人管理
	 * @return
	 */
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "权限管理", "流程任务人管理", "列表" })
	@aDev(code = "23", email = "wanghl@pqsoft.cn", name = "李硕")
	public Reply getPage2(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.getPage(param));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply findOperator(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html("bpm/allTask_operator.vm", context));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply getOperatorPage(){
		Map<String,Object> param = _getParameters();
		return new ReplyAjaxPage(service.getOperatorPage(param));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply setOperator(){
		Map<String,Object> param = _getParameters();
		//设置任务操作人
		int count = service.updateOperator(param);
		Map<String, Object> secuUser = service.getSecuUser(param);
		secuUser.put("DBID", param.get("DBID"));
		//更新首页我的事宜
		service.updateTaskPortal(secuUser);
		if(count>0){
			return new ReplyAjax(true, "成功");
		}
		return new ReplyAjax(false, "失败");
	}
}
