package com.pqsoft.insureSettlement.action;

/**
 *  保险理赔 款项查看
 *  @author hanxl
 *  涉及表 FI_INSUREPAID_FEE_UPLOAD
 *  涉及表 FI_DEPOSIT_POOL
 */
import java.util.Map;

import com.pqsoft.insureSettlement.service.InsureSettlementExamineService;
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

public class InsureSettlementExamineAction extends Action {

	private String basePath = "insureSettlement/";
	private InsureSettlementExamineService service = new InsureSettlementExamineService();

	@Override
//	@aPermission(name = { "保险管理", "保险理赔", "款项查询主页面" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply execute() {
//		return new ReplyHtml(VM.html(basePath + "insureSettlementExamineShow.vm", null));
		return null;
	}
	/**
	 * 用于分页查询
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply pageData(){
		Map<String,Object> param = _getParameters();
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
}
