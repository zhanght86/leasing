package com.pqsoft.shouldPaid.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.shouldPaid.service.shouldPaidService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class shouldPaidAction extends Action{

	private shouldPaidService service=new shouldPaidService();
	private VelocityContext context=new VelocityContext();
	
	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@aPermission(name = { "资金管理", "应实收统计", "列表显示" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply shouldPaidMg()
	{
		Map<String, Object> param = _getParameters();
		param.putAll(service.shouldPaidHj());
		context.put("param", param);
		
		return new ReplyHtml(VM.html("shouldPaid/shouldPaidMg.vm", context));
	}
	
	@aPermission(name = { "资金管理", "应实收统计", "列表显示" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply shouldPaid_Mg_AJAX()
	{
		Map<String, Object> param = _getParameters();
		Page page = service.shouldPaid_Mg_AJAX(param);
		return new ReplyAjaxPage(page);
	}
}
