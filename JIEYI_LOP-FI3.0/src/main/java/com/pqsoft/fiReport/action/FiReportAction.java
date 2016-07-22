package com.pqsoft.fiReport.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.fiReport.service.FiReportService;
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

public class FiReportAction extends Action{
	
	private VelocityContext context = new VelocityContext();
	private FiReportService service=new FiReportService();

	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "资金管理", "财务业务系统表格", "列表显示" })
	public Reply fiFinaReportMain() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html("fiReport/fiFinaReportMain.vm", context));
	}
	
	@aPermission(name = { "资金管理", "财务业务系统表格", "列表显示" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply getFiStartManager() {
		Map<String, Object> m =_getParameters();
		context.put("PContext", m);
		return new ReplyHtml(VM.html("fiReport/fi_Start_Manager.vm",context));
	}

	@aPermission(name = { "资金管理", "财务业务系统表格", "列表显示" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply getFiStartPageAjax() {
		Map<String, Object> m =_getParameters();
		Page page = service.getFiStartPageAjax(m);
		return new ReplyAjaxPage(page);
	}
}
