package com.pqsoft.insurebxbc.action;
/**
 *   保险补差支付核销
 */
import java.util.Map;

import com.pqsoft.insurebxbc.service.InsureBXBCPayAffirmService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class InsureBXBCPayAffirmAction extends Action{
	
	private String basePath = "insure_bxbc/"; 
	private InsureBXBCPayAffirmService bxbcAffirmService = new InsureBXBCPayAffirmService();
	
	/**
	 *  显示主页面
	 */
	@Override
//	@aPermission(name = { "保险管理", "保险补差管理", "补差支付核销"})
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply execute() {
		//return new ReplyHtml(VM.html(basePath + "insureBXBCPayAffirm.vm", null));
		return  null;
	}
	
	/**
	 * 用于分页查询
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply pageData(){
		Map<String,Object> param = _getParameters();
		Page pagedata = bxbcAffirmService.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	
	/**
	 * 用于审核保险补差支付申请
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply doUpdateBXBCItem() {
		Map<String,Object> param = _getParameters();
		//核销FIL_INSURE_APPLY_INFO
		param.put("HIRE_DATE", param.get("FK_DATE").toString());//放款日期
		param.put("HIRER", Security.getUser().getName());//核销人
		bxbcAffirmService.updateAffirmBXBCItem(param);
		//核销INSUR_CHARGE_EXHIB
		bxbcAffirmService.updateAffirmBXBCDetail(param);
		return  new ReplyAjax(true,"");
	}
	
	/**
	 * 用于驳回保险补差支付申请
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply doBackBXBCItem() {
		Map<String,Object> param = _getParameters();
		bxbcAffirmService.updateBackBXBCItem(param);
		return  new ReplyAjax(true,"");
	}
}
