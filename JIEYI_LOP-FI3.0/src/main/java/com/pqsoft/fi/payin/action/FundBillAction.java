package com.pqsoft.fi.payin.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.fi.payin.service.FundBillService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class FundBillAction extends Action {

	@Override
	@aPermission(name = { "资金管理", "挂账管理","列表" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("fi/payin/fund_bill.vm", context));
	}
	@aPermission(name = { "资金管理", "挂账管理","列表查询" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "徐广明")
	public Reply getPage() {
		Map<String, Object> param = _getParameters();
		//FUND_STATUS状态为99时为挂账
		return new ReplyAjaxPage(new FundBillService().getPage(param));
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "资金管理", "挂账管理","转挂账" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply TransferredCredit() {
		Map<String, Object> m = _getParameters();
		int result=new FundBillService().TransferredCredit(m);
		String msg = "";
		Boolean flag = true ;
		if(result>0){
			msg ="转挂账成功!";
			flag = true; 
			return new ReplyAjax(flag, msg);
		}else{
			msg ="转挂账失败!";
			flag = false; 
			return new ReplyAjax(flag, msg);
		}
	}

}
