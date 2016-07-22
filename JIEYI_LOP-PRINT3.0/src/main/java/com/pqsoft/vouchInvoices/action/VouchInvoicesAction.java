package com.pqsoft.vouchInvoices.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.vouchInvoices.service.VouchService;


public class VouchInvoicesAction extends Action {

    private String path = "vouchInvoices/";
    private VouchService service = new VouchService();
	
	@aPermission(name ={"资金管理", "付款管理", "担保费支付担保费发票录入"})
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		context.put("param", context);
		return new ReplyHtml(VM.html(path+"vouchInvoiceManager.vm", context));
	}
	
	/**
	 * 用于分页查询
	 */
	@aPermission(name = { "资金管理", "付款管理", "担保费支付担保费发票分页查询"})
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply pageData(){
		Map<String,Object> param = _getParameters();
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	
	/**
	 * 用于担保费条目信息
	 */
	@aPermission(name = { "资金管理", "付款管理", "担保费支付担保费信息新增/修改" })
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply updateVouchItem() {
		Map<String,Object> param = _getParameters();
		service.updateVouchItem(param);
		return  new ReplyAjax(true,"");
	}
	
}
