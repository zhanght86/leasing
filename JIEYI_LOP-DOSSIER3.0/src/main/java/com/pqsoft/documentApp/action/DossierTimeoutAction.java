package com.pqsoft.documentApp.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.documentApp.service.DossierTimeoutService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class DossierTimeoutAction extends Action{
	private String basePath = "documentApp/dossierTimeout/";
	private DossierTimeoutService service = new DossierTimeoutService();

	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	@aPermission(name = { "权证管理", "归档超时", "列表显示"})
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html(basePath+"toMgDossierTimeout.vm", context));
	}
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	@aPermission(name = { "权证管理", "归档超时", "列表显示"})
	public Reply pageData(){
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.getPageData(map));
	}

}
