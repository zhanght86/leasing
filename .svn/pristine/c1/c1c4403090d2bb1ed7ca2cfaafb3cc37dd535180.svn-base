package com.pqsoft.server.action;

import java.util.Map;
import org.apache.velocity.VelocityContext;
import com.pqsoft.server.service.Id5Service;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;

public class Id5Action extends Action {

	Id5Service service = new Id5Service();
	
	@Override
	@aAuth(type=aAuth.aAuthType.ALL)
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		context.put("ZLGS", service.getZLGS());
		return new ReplyHtml(VM.html("base/server/id5.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.ALL)
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getResult(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.getResult(param));
	}

}
