package com.pqsoft.fi.action;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.velocity.VelocityContext;

import com.pqsoft.fi.service.StatementService;
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
import com.pqsoft.skyeye.util.UTIL;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.ReplyExcel;

public class StatementAction extends Action {

	@Override
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合同管理", "客户对账单","列表显示"})
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply execute() {
		// VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("fi/statement.vm", null));
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合同管理", "客户对账单","列表显示" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply toPage() {
		Map<String, Object> map = _getParameters();
		return new ReplyAjaxPage(PageUtil.getPage(map, "fi.statement.getPage", "fi.statement.getPageCount"));
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合同管理", "客户对账单","查看" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply toInfo() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		StatementService service = new StatementService();
		List<Map<String, Object>> list = service.getList(map);
		for (Map<String, Object> m : list) {
			m.put("D_RECEIVE_MONEY_F", UTIL.FORMAT.currency(m.get("D_RECEIVE_MONEY")));
			m.put("D_PAY_MONEY", UTIL.FORMAT.currency(m.get("D_PAY_MONEY")));
			m.put("D_RECEIVE_DATE", UTIL.FORMAT.date(m.get("D_RECEIVE_DATE")));
			m.put("FI_PAY_DATE_F", UTIL.FORMAT.date(m.get("FI_PAY_DATE")));
			m.put("FI_ACCOUNT_DATE_F", UTIL.FORMAT.date(m.get("FI_ACCOUNT_DATE")));
			m.put("FUND_ACCEPT_DATE_F", UTIL.FORMAT.date(m.get("FUND_ACCEPT_DATE")));//添加到帐日
		}
		context.put("list", list);
		context.put("map", map);
		return new ReplyHtml(VM.html("fi/statementDetail.vm", context));
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合同管理", "客户对账单","列表显示"})
	@aDev(code = "170043", email = "1226781445@qq.com", name = "肖云飞")
	//跳转到客户对账页面 
	public Reply toPageNew(){
		// VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("fi/statementNew.vm", null));
	}
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合同管理", "客户对账单","列表显示" })
	@aDev(code = "170043", email = "1226781445@qq.com", name = "肖云飞")
	//查询客户信息
	public Reply toPage2() {
		Map<String, Object> map = _getParameters();
		return new ReplyAjaxPage(PageUtil.getPage(map, "fi.statement.getPageNew", "fi.statement.getPageCountNew"));
	}
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合同管理", "客户对账单","查看" })
	@aDev(code = "170043", email = "1226781445@qq.com", name = "肖云飞")
	//跳转到当前用户还款信息
	public Reply toInfoNew() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("map", map);
		return new ReplyHtml(VM.html("fi/statementFund.vm", context));
	}
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合同管理", "客户对账单","查看" })
	@aDev(code = "170043", email = "1226781445@qq.com", name = "肖云飞")
	//展示当前用户还款信息
	public Reply toPageFund() {
		Map<String, Object> map = _getParameters();
		return new ReplyAjaxPage(PageUtil.getPage(map, "fi.statement.getPageFundNew", "fi.statement.getPageCountFundNew"));
	}
	
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合同管理", "客户对账单","查看" })
	@aDev(code = "170043", email = "1226781445@qq.com", name = "肖云飞")
	//查看明细项
	public Reply searchDetail() {
		StatementService service = new StatementService();
		Map<String, Object> map = _getParameters();
		return new ReplyAjax(service.getFundDetail(map));
	}
	
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "合同管理", "客户对账单","查看" })
	@aDev(code = "170043", email = "1226781445@qq.com", name = "肖云飞")
	//导出excel
	public Reply exportExcelFund() {
		StatementService service = new StatementService();
		Map<String, Object> map = _getParameters();
		return new ReplyExcel(service.exportData(map), "workFlow"
				+ DateUtil.getSysDate() + Math.abs(new Random(10000).nextInt())
				+ ".xls");
	}
	
	
}
