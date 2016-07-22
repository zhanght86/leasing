package com.pqsoft.sms.action;


import org.apache.velocity.VelocityContext;

import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.sms.service.NewSmsService;

public class NewSmsAction extends Action{
	private String path="sms/";
	private NewSmsService newSmsService=new NewSmsService() ;
	
	@Override
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"接口管理","短信","短信模板管理"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "王华英")
	public Reply execute() {
		//访问地址：http://localhost:8080/jieyi-project/sms/NewSms!execute.action
		VelocityContext context =new VelocityContext();
		context.put("param", _getParameters());
		return new ReplyHtml(VM.html(path+"newSms.vm", context));
	}

	
	@aAuth(type=aAuth.aAuthType.ALL)
	@aPermission(name ={"接口管理","短信","查询模板信息"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "王华英")
	public Reply querynewtemplate(){
		//访问地址：http://localhost:8080/jieyi-project/sms/NewSms!querynewtemplate.action
		return new ReplyAjaxPage(newSmsService.getAllTemplateDetail(_getParameters()));
	}
	
	/**
	 * job1
	 * @return
	 */
	@aAuth(type=aAuth.aAuthType.ALL)
	@aPermission(name ={"接口管理","短信","短信接口配置"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "王华英")
	public Reply FestivalInfo(){
		//访问地址：http://localhost:8080/jieyi-project/sms/NewSms!FestivalInfo.action
		int result= newSmsService.getFestivalInfo();
		boolean flag=false;
		String msg;
		if(result>0){
			flag=true;
			msg="添加成功!";
		}
		else{
			flag=true;
			msg="添加失败!";
		}
		return new ReplyAjax(flag, null, msg).addOp(new OpLog("短信平台", "模板管理", "添加模板", "添加错误"));	
	}
	
	/**
	 * job2
	 * @return
	 */
	@aAuth(type=aAuth.aAuthType.ALL)
	@aPermission(name ={"接口管理","短信","短信接口配置"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "王华英")
	public Reply AllSMSToSend() {
		//访问地址：http://localhost:8080/jieyi-project/sms/NewSms!AllSMSToSend.action
		boolean flag=newSmsService.getAllSMSToSend();
		String msg;
		if(flag=true){
			flag=true;
			msg="短信发送成功!";
		}
		else{
			flag=true;
			msg="短信发送失败!";
		}
		return new ReplyAjax(flag, null, msg).addOp(new OpLog("短信平台", "模板管理", "短信发送", "短信发送失败"));
	}
		
	/**
	 * job3
	 * @return
	 */
	@aAuth(type=aAuth.aAuthType.ALL)
	@aPermission(name ={"接口管理","短信","短信接口配置"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "王华英")
	public Reply AllSMSToSendByHttp() {
		//访问地址：http://localhost:8080/jieyi-project/sms/NewSms!AllSMSToSendByHttp.action
		boolean flag=newSmsService.getAllSMSToSendByHttp();
		String msg;
		if(flag=true){
			flag=true;
			msg="短信发送成功!";
		}
		else{
			flag=true;
			msg="短信发送失败!";
		}
		return new ReplyAjax(flag, null, msg).addOp(new OpLog("短信平台", "模板管理", "短信发送", "短信发送失败"));
	}
	
	@aAuth(type=aAuth.aAuthType.ALL)
	@aPermission(name ={"接口管理","短信","添加模板详情"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "王华英")
	public Reply addnewtemplate(){
		//访问地址：http://localhost:8080/jieyi-project/sms/NewSms!addnewtemplate.action
		int result= newSmsService.addTemplateDetail(_getParameters());
		boolean flag=false;
		String msg;
		if(result>0){
			flag=true;
			msg="添加成功!";
		}
		else{
			flag=true;
			msg="添加失败!";
		}
		return new ReplyAjax(flag, null, msg).addOp(new OpLog("短信平台", "模板管理", "添加模板", "添加错误"));
	}
	
	@aAuth(type=aAuth.aAuthType.ALL)
	@aPermission(name ={"接口管理","短信","修改模板详情"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "王华英")
	public Reply updatenewtemplate(){
		int result=newSmsService.updateTemplateDetail(_getParameters());
		boolean flag=false;
		String msg;
		if(result>0){
			flag=true;
			msg="修改成功!";
		}
		else{
			flag=true;
			msg="修改失败!";
		}
		return new ReplyAjax(flag, null, msg).addOp(new OpLog("短信平台", "模板管理", "修改模板", "修改错误"));
	}
}
