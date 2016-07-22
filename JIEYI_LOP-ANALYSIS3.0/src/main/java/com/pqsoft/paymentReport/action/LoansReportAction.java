package com.pqsoft.paymentReport.action;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.paymentReport.service.LoansReportService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.UtilChart;

public class LoansReportAction extends Action {

	private final String path = "loanReport/";
	private final LoansReportService service = new LoansReportService();
	//页面自定义显示列的下拉列表所需，同时也是sql所需
	private String[] titlesName = new String[] { "合同号", "经销商", "开户行", "银行账号", "车辆开票金额", "裸车价", "增值税", "代收首付", "经销商保证金", "经销商贴息", "购置税", "验车费", "保险费", "其他", "放款金额" };
	private String[] sqlsName = new String[] { "LEASE_CODE","COMPANY_NAME", "OPEN_BANK", "OPEN_BANKACCOUNT", "INCOICE_MONEY", "PRODUCT_MONEY", "ZZH", "DS_FIRST_MONEY", "DB_BAIL_PERCENT", "DB_BAIL_TIEXI", "GOUZHISHUI", "CHECKED_MONEY", "INSURANCE_MONEY", "ORHTER_MONEY", "PAYMENT_MONEY" };
	//报表工具调用
    UtilChart utilChart=new UtilChart();
	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"报表管理", "放款报表","列表显示"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply execute() {
		Map<String,Object> param=_getParameters();
		VelocityContext context = new VelocityContext();
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("放款统计", titlesName, sqlsName));
		/********************************************************************/
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "toMgLoansReport.vm", context));
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = {"报表管理", "放款报表","列表显示"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgLoansReport(){
		Map<String,Object> map = _getParameters();
		JSONObject json = JSONObject.fromObject(map.get("searchParams"));
		map.remove("searchParams");
		map.putAll(json);
		return new ReplyAjaxPage(service.toMgLoansReport(map));
	}
}
