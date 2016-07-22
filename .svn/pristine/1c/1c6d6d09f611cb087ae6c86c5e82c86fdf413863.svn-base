package com.pqsoft.fundPlan.action;

import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.fundPlan.service.FinanceReportService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.UtilChart;

public class FinanceReportAction extends Action {

	
	private final String path = "financeReport/";
	private final FinanceReportService service = new FinanceReportService();
	//页面自定义显示列的下拉列表所需，同时也是sql所需
	private String[] titlesName = new String[] { "项目编号", "合同编号", "客户名称", "经销商名称", "汽车品牌", "型号", "车款", "客户经理", "融资额", "每期还款额", "租金总额", "起租日期", "结束日期", "贷款期限", "已还款期数", "剩余还款期限", "已还款金额", "未还款金额", "融资额余额", "逾期期数", "逾期金额" };
	private String[] sqlsName = new String[] { "PRO_CODE","LEASE_CODE", "CUST_NAME", "SUPPLIERS_NAME", "PRODUCT_NAME", "SPEC_NAME", "TOTAL_PRICE", "CLERK_NAME", "TOPRIC_SUBFIRENT", "PJ_MONEY", "ZJ_TOTAL_MONEY", "START_DATE", "END_DATA", "LEASE_TERM", "YH_PERIOD", "WH_PERIOD", "YH_ZJ_MONEY", "WH_ZJ_MONEY", "WH_RZE_MONEY", "DUE_PERIOD", "WH_DUE_MONEY" };
	//报表工具调用
    UtilChart utilChart=new UtilChart();
	
	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "财务报表", "财务基本信息表", "列表显示" })
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "yangxue")
	public Reply execute() {
		Map<String,Object> param=_getParameters();
		VelocityContext context = new VelocityContext();
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("财务基本信息", titlesName, sqlsName));
		/********************************************************************/
		context.put("param", param);
		return new ReplyHtml(VM.html(path + "toMgFinanceBaseReport.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = { "报表管理", "财务报表", "财务基本信息表", "列表显示" })
	@aDev(code = "170039", email = "yangxue@pqsoft.com", name = "yangxue")
	public Reply toMgFinanceBaseReport() {
		Map<String,Object> map = _getParameters();
		JSONObject json = JSONObject.fromObject(map.get("searchParams"));
		map.remove("searchParams");
		map.putAll(json);
		return new ReplyAjaxPage(service.toMgFinanceBaseReport(map));
	}

	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = {"报表管理", "财务报表", "财务基本信息表", "导出"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toImportFinance(){
		Map<String,Object> map = _getParameters();
		JSONObject json = JSONObject.fromObject(map.get("searchParams"));
		map.remove("searchParams");
		map.putAll(json);
		List<Map<String,Object>> dataList = (List<Map<String, Object>>)service.toImportExcel(map); 
		new com.pqsoft.util.ImportExcelPoi().expExcel_2007(map, "特批处理总报表"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx","特批处理总报表",titlesName,sqlsName,dataList);
		return null;
	}
}
