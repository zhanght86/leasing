package com.pqsoft.retentionRefund.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.retentionRefund.service.RetentionRefundService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class RetentionRefundAction extends Action {

	//读取页面路径
	private final String pagePath="retentionRefund/";
	
	@Override
	public Reply execute() {
		return null;
	}
	
	/**************************************************************留购价退款申请-管理页面**************************************************************************/
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "留购价退款", "留购价退款-申请","列表"})
	public Reply toMgRetention() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html(pagePath+"retentionRefundMg.vm", context));
	}
	

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "留购价退款", "留购价退款-申请","列表"})
	public Reply toMgRetentionData(){
		Map<String, Object> param = _getParameters();
		RetentionRefundService service = new RetentionRefundService();
		return new ReplyAjaxPage(service.findRetentionData(param));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "留购价退款", "留购价退款-申请","申请"})
	public Reply toAppRetention() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		RetentionRefundService service = new RetentionRefundService();
		String USERCODE = Security.getUser().getCode();
		context.put("org", service.getOrgData(USERCODE));//获取登录用户所属组织架构
		return new ReplyHtml(VM.html(pagePath+"retentionRefundApp.vm", context));
	}
	

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "留购价退款", "留购价退款-申请","申请"})
	public Reply toAppRetentionData(){
		Map<String, Object> param = _getParameters();
		RetentionRefundService service = new RetentionRefundService();
		return new ReplyAjaxPage(service.getRetentionApp(param));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "留购价退款", "留购价退款-申请","申请"})
	public Reply doSaveApp(){
		Map<String,Object> paramMap=_getParameters();
		paramMap.put("USERCODE",Security.getUser().getCode());//用户编号
		paramMap.put("USERNAME",Security.getUser().getName());//用户名
		RetentionRefundService service = new RetentionRefundService();
		int i=service.doSaveRetention(paramMap);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag=true;
			msg = "申请成功";
		}else{
			flag=false;
			msg = "申请失败";
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("留购价退款-申请","保存", msg));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "留购价退款", "留购价退款-申请","提交"})
	public Reply doCommitApp(){
		Map<String,Object> paramMap=_getParameters();
		paramMap.put("USERCODE",Security.getUser().getCode());//用户编号
		paramMap.put("USERNAME",Security.getUser().getName());//用户名
		RetentionRefundService service = new RetentionRefundService();
		int i=service.doCommitApp(paramMap);
		boolean flag = false;
		if(i>0){
			flag=true;
		}else{
			flag=false;
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("留购价退款-申请","提交", paramMap.get("USERCODE").toString()));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "留购价退款", "留购价退款-申请","删除"})
	public Reply doDelApp(){
		Map<String,Object> paramMap=_getParameters();
		paramMap.put("USERCODE",Security.getUser().getCode());//用户编号
		paramMap.put("USERNAME",Security.getUser().getName());//用户名
		RetentionRefundService service = new RetentionRefundService();
		int i=service.doDelApp(paramMap);
		boolean flag = false;
		if(i>0){
			flag=true;
		}else{
			flag=false;
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("留购价退款-申请","提交", paramMap.get("USERCODE").toString()));
	}
	/**************************************************************留购价退款审核-管理页面**************************************************************************/
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "留购价退款", "留购价退款-审核","列表"})
	public Reply toMgRetentionChedked() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html(pagePath+"retentionRefundChecked.vm", context));
	}
	

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "留购价退款", "留购价退款-审核","列表"})
	public Reply toMgRetentionChedkedData(){
		Map<String, Object> param = _getParameters();
		RetentionRefundService service = new RetentionRefundService();
		return new ReplyAjaxPage(service.findRetentionChedkData(param));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "留购价退款", "留购价退款-审核","审核"})
	public Reply doCheckedRetention(){
		Map<String,Object> paramMap=_getParameters();
		paramMap.put("USERCODE",Security.getUser().getCode());//用户编号
		paramMap.put("USERNAME",Security.getUser().getName());//用户名
		RetentionRefundService service = new RetentionRefundService();
		int i=service.doCheckedRetention(paramMap);
		boolean flag = false;
		if(i>0){
			flag=true;
		}else{
			flag=false;
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("留购价退款-审核","审核", paramMap.get("USERCODE").toString()));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "留购价退款", "留购价退款-审核","驳回"})
	public Reply doCancelRetention(){
		Map<String,Object> paramMap=_getParameters();
		paramMap.put("USERCODE",Security.getUser().getCode());//用户编号
		paramMap.put("USERNAME",Security.getUser().getName());//用户名
		RetentionRefundService service = new RetentionRefundService();
		int i=service.doCancelRetention(paramMap);
		boolean flag = false;
		if(i>0){
			flag=true;
		}else{
			flag=false;
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("留购价退款-审核","驳回", paramMap.get("USERCODE").toString()));
	}
	/**************************************************************留购价退款-申请/审核明细数据**************************************************************************/
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "留购价退款", "留购价退款-申请","列表"})
	public Reply findDetailApp(){
		Map<String, Object> param = _getParameters();
		RetentionRefundService service = new RetentionRefundService();
		return new ReplyAjax(service.findDetailApp(param));
	}
}
