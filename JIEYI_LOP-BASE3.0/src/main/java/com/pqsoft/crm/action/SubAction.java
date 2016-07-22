package com.pqsoft.crm.action;

import org.apache.velocity.VelocityContext;

import com.pqsoft.crm.service.SubService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;

public class SubAction extends Action {

	@Override
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		SubService service = new SubService();
		context.put("NAME", "张三");
		// context.put("nodes", service.data());
		context.put("data", service.data(SkyEye.getRequest().getParameter("id")));
		return new ReplyHtml(VM.html("crm/subrel.vm", context));
	}

}
