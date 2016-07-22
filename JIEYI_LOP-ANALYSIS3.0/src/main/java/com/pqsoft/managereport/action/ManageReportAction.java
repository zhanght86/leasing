package com.pqsoft.managereport.action;

import java.util.Map;

import net.sf.json.JSONObject;

import com.pqsoft.managereport.service.ManageReportService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class ManageReportAction extends Action {

	private final String path = "managereport/";
	private final ManageReportService  service = new ManageReportService();
	
	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "区域销售报表", "列表显示" })
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "yangxue")
	public Reply execute() {
		return new ReplyHtml(VM.html(path+"toMgManageReport.vm", null));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "报表管理", "区域销售报表", "列表显示" })
	//@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "yangxue")
	public Reply toMgManageReport(){
		Map<String,Object> map = _getParameters();
		JSONObject json = JSONObject.fromObject(map.get("searchParams"));
		map.remove("searchParams");
		map.putAll(json);
		return new ReplyAjaxPage(service.toMgManageReport(map)); 
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "产品分析报表", "列表显示" })
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "yangxue")
	public Reply toMgproductAnalyse(){
		return new ReplyHtml(VM.html(path+"toMgproductAnalyse.vm", null));
	}
	
	public Reply toMgproductAnalysePage(){
		Map<String,Object> map = _getParameters();
		JSONObject json = JSONObject.fromObject(map.get("searchParams"));
		map.remove("searchParams");
		map.putAll(json);
		return new ReplyAjaxPage(service.toMgproductAnalyse(map)); 
	}
}
