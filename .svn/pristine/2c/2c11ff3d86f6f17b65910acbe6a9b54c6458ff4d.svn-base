package com.pqsoft.sms.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.sms.service.SmsService;

public class SmsAction extends Action{

	private String path = "sms/";
	private SmsService service= new SmsService();

	@Override
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"接口管理","短信","短信接口配置"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		context.put("mapC", service.getConfigDetail());
		return new ReplyHtml(VM.html(path+"config.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"接口管理","短信","发送"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply manualSendMessage() {
		VelocityContext context = new VelocityContext();
		context.put("param", _getParameters());
		return new ReplyHtml(VM.html(path+"manualSendMessage.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"接口管理","短信","发送"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doManualSendMessage() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.doManualSendMessage(param));
	}
	

	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"接口管理","短信","发送"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply sendMessage() {
		VelocityContext context = new VelocityContext();
		context.put("param", _getParameters());
		return new ReplyHtml(VM.html(path+"sendMessage.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"接口管理","短信","保存"})
	@aDev(code = "170026", email = "wanghl@pqsoft.com", name = "王海龙")
	public Reply saveSms(){
		Map<String,Object> param = _getParameters();
		int count = service.updateSms(param);
		if(count>0){
			return new ReplyAjax(true, "保存成功");
		}else{
			return new ReplyAjax(false, "保存失败");
		}
	}

}
