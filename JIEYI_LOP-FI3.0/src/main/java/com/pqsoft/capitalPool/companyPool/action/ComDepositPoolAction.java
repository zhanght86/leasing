package com.pqsoft.capitalPool.companyPool.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.capitalPool.companyPool.service.ComDepositPoolService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class ComDepositPoolAction extends Action {
    private String path = "capitalPool/companyPool/";
    private ComDepositPoolService service = new ComDepositPoolService();
	
    @aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","厂商保证金池","列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	@Override
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"CompanyPoolManager.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","厂商保证金池","查询"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getComDepositPageData(){
		Map<String,Object> param = _getParameters();
        Page pageData = service.getCompDepositPageData(param);
        return new ReplyAjaxPage(pageData);
	}
	
}
