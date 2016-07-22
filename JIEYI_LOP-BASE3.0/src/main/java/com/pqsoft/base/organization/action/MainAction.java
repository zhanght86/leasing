package com.pqsoft.base.organization.action;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class MainAction extends Action {
	private static final String basePath = "base/organization/";

	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理","组织架构"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		ManageService service = new ManageService();
		context.put("data", service.getOrganizations("0"));
		return new ReplyHtml(VM.html(basePath + "manage.vm", context));
	}

}
