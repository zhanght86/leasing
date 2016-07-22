package com.pqsoft.payment.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.payment.service.payMentFLService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class payMentFLAction  extends Action{

	public VelocityContext context=new VelocityContext();
	payMentFLService service=new payMentFLService();
	
	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@aPermission(name = { "放款管理", "代收付款", "申请" ,"列表显示" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply payFL_Mg()
	{
		Map map=this._getParameters();
		return new ReplyHtml(VM.html("payment/FLPayMent/fl_payMent_MS.vm", context));
	}
	
	@aPermission(name = { "放款管理", "代收付款", "申请" ,"列表显示" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply pay_FL_Mg_AJAX()
	{
		Map<String, Object> param = _getParameters();
		Page page = service.pay_FL_Eq_Mg(param);
		return new ReplyAjaxPage(page);
	}
	
	@aPermission(name = { "放款管理", "代收付款", "审核" ,"列表显示" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply payFL_COM_Mg()
	{
		Map map=this._getParameters();
		return new ReplyHtml(VM.html("payment/FLPayMent/fl_payMent_COM_MS.vm", context));
	}
	
	@aPermission(name = { "放款管理", "代收付款", "审核" ,"列表显示" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply pay_FL_COM_Mg_AJAX()
	{
		Map<String, Object> param = _getParameters();
		Page page = service.pay_FL_COM_Eq_Mg(param);
		return new ReplyAjaxPage(page);
	}
	
	@aPermission(name = { "放款管理", "代收付款", "放款记录" ,"列表显示" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply payFL_JL_Mg()
	{
		Map map=this._getParameters();
		return new ReplyHtml(VM.html("payment/FLPayMent/fl_payMent_JL_MS.vm", context));
	}
	
	@aPermission(name = { "放款管理", "代收付款", "放款记录" ,"列表显示" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply pay_FL_JL_Mg_AJAX()
	{
		Map<String, Object> param = _getParameters();
		Page page = service.pay_FL_JL_Mg(param);
		return new ReplyAjaxPage(page);
	}
	
	@aPermission(name = { "放款管理", "代收付款", "申请" ,"修改" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply pay_FL_Update()
	{
		Map map=this._getParameters();
		int num=service.updatePayMentFL(map);
		boolean flag=false;
		if(num>0)
		{
			flag=true;
		}
		return new ReplyAjax(flag,null,null).addOp(new OpLog("放款管理", "代收付款", "修改"));
	}
	
	@aPermission(name = { "放款管理", "代收付款", "申请" ,"提交" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply fl_PayHead_Status()
	{
		Map<String, Object> map = _getParameters();
		map.put("STATUS", 1);
		service.fl_PayHead_Status(map);
		return this.payFL_Mg();
	}
	
	
	@aPermission(name = { "放款管理", "代收付款", "审核" ,"驳回" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply fl_PayHead_StatusBack()
	{
		Map<String, Object> map = _getParameters();
		map.put("STATUS", 3);
		service.fl_PayHead_Status(map);
		return this.payFL_COM_Mg();
	}
	
	@aPermission(name = { "放款管理", "代收付款", "审核" ,"通过" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply fl_PayHead_StatusPass()
	{
		Map<String, Object> map = _getParameters();
		map.put("STATUS", 2);
		map.put("MONDFY_ID", Security.getUser().getId());
		service.fl_PayHead_Status(map);
		return this.payFL_COM_Mg();
	}
}
