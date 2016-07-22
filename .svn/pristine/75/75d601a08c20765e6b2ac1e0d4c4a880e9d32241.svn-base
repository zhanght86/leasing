package com.pqsoft.payment.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.payment.service.LoanSupervisionService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.StringUtils;

public class LoanSupervisionAction extends Action {

	@Override
	@aPermission(name = { "放款管理", "放款后督" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "xgm")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("payment/loanSupervision.vm", context));
	}
	@aPermission(name = { "放款管理", "放款后督" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "xgm")
	public Reply getPage() {
		Map<String, Object> param = _getParameters();
		if (StringUtils.isNotBlank(Security.getUser().getOrg().getSuppId())) {
			param.put("SUPPID", Security.getUser().getOrg().getSuppId());
		}
		if (StringUtils.isNotBlank(Security.getUser().getOrg().getSpId())) {
			param.put("SUPPID", Security.getUser().getOrg().getSpId());
		}
		BaseUtil.getDataAllAuth(param);
		param.put("USERCODE", Security.getUser().getCode());
		return new ReplyAjaxPage(new LoanSupervisionService().getPage(param));
	}
	
}
