package com.pqsoft.target.action;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.target.service.TargetService;

public class TargetAction extends Action {
	
	private String pagePath = "mgTargets/";
	private TargetService service = new TargetService();

	@Override
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"指标管理", "指标价格管理", "列表显示"})
	public Reply execute() {
		// TODO Auto-generated method stub
		VelocityContext context = new VelocityContext();
		context.put("sup", service.toViewSuppliers());
		return new ReplyHtml(VM.html(pagePath+"toMgTarget.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgTarget(){
		Map<String,Object> param = _getParameters();
		return new ReplyAjaxPage(service.toMgTarget(param));
	}
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"指标管理", "指标价格管理", "添加"})
	public Reply doAddTarget(){
		Map<String,Object> param = _getParameters();
		param.put("USERCODE",Security.getUser().getCode());
		int i = service.doAddTarget(param);//添加指标价格
		String msg = "";
		boolean flag = false;
		if(i>0){
			flag = true;
			msg = "指标价格添加成功";
		}else {
			msg = "指标价格添加失败";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("指标管理-指标价格管理","添加指标价格", msg));
	}
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"指标管理", "指标价格管理", "查看"})
	public Reply toViewTarget(){
		Map<String,Object> param = _getParameters();
		param.put("USERCODE",Security.getUser().getCode());	
		return new ReplyJson(JSONObject.fromObject(service.toViewTarget(param)));
	}
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"指标管理", "指标价格管理", "修改"})
	public Reply toUpTarget(){
		Map<String,Object> param = _getParameters();
		param.put("USERCODE",Security.getUser().getCode());	
		return new ReplyJson(JSONObject.fromObject(service.toViewTarget(param)));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply doUpTarget(){
		Map<String,Object> param = _getParameters();
		param.put("USERCODE",Security.getUser().getCode());
		int i = service.doUpTarget(param);//添加指标价格
		String msg = "";
		boolean flag = false;
		if(i>0){
			flag = true;
			msg = "指标价格修改成功";
		}else {
			msg = "指标价格修改不失败";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("指标管理-指标价格管理","修改指标价格", msg));
	}
	
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"指标管理", "指标价格管理", "删除"})
	public Reply doDelTarget(){
		Map<String,Object> param = _getParameters();
		param.put("USERCODE",Security.getUser().getCode());
		param.put("TARGET_SY",param.get("TARGET_TOTAL"));	
		int i = service.doDelTarget(param);//添加指标价格
		String msg = "";
		boolean flag = false;
		if(i>0){
			flag = true;
			msg = "指标价格删除成功";
		}else {
			msg = "指标价格删除失败";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("指标管理-指标价格管理","删除指标价格", msg));
	}
	
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"指标管理", "融资指标管理", "列表显示"})
	public Reply toMgFinancintProMain() {
		// TODO Auto-generated method stub
		return new ReplyHtml(VM.html(pagePath+"toMgFinancintPro.vm", null));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgFinancintPro(){
		Map<String,Object> param = _getParameters();
		return new ReplyAjaxPage(service.toMgFinancintPro(param));
	}
}
