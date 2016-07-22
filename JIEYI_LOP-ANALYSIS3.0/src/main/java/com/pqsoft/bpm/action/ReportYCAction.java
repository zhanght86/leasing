package com.pqsoft.bpm.action;

import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.service.ReportService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.POIExcelUtil;
import com.pqsoft.util.UtilChart;

public class ReportYCAction extends Action {

	private final String _PATH = "bpm/";
	// **实现类服务
	ReportService service = new ReportService();

	// 页面自定义显示列的下拉列表所需，同时也是sql所需
	private String[] titlesName = new String[] { "省份", "城市", "客户名称", "证件号码", "客户状态", "客户等级", "联系方式", "客户经理", "创建时间", "户籍/家庭地址", "注册/办公地址" };
	private String[] sqlsName = new String[] { "PROVINCE_NAME", "CITY_NAME", "CUST_NAME", "CUST_CODE", "CUST_STATUS", "CUST_LEVER", "CUST_PHONE",
			"CLERK_NAME", "CREATE_TIME", "NPCOMP", "LPCOMP" };

	// 报表工具调用
	UtilChart utilChart = new UtilChart();

	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "流程统计", "异常流程统计" })
	@aDev(code = "170043", email = "lichaohn@163.com", name = "lc")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-客户统计-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-流程-统计状态");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		Map<String, Object> param = _getParameters();
		param.put("types", ReportService.type);
		param.put("tks", ReportService.tk);
		param.put("HSYCYC", service.getYchs());
		context.put("chartReport", utilChart.to_chartPie(param, "报表-流程-统计状态", "流程统计", "bpm.report.getTj", "1"));
		return new ReplyHtml(VM.html(_PATH + "report_yc_chart.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "报表管理", "流程统计", "异常流程统计" })
	@aDev(code = "170043", email = "lichaohn@163.com", name = "lc")
	public Reply t1() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-客户统计-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-流程-统计状态");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		// context.put("chartReport",utilChart.to_chart(param, "报表-客户-统计类型",
		// "客户统计", "clientReport.showGroupClientType","1"));
		/******************************** 控制自定义显示列 ********************/
		context.put("columnInit", utilChart.parseColumnInit("流程统计", ReportService.titlesZH, ReportService.titlesEN));
		/********************************************************************/

		return new ReplyHtml(VM.html(_PATH + "report_yc.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doTableShow() {
		Map<String, Object> param = _getParameters();
		param.put("USER_CODE", Security.getUser().getCode());
		param.put("HSYCYC", service.getYchs());
		JSONObject json = JSONObject.fromObject(param.get("searchParams"));
		param.putAll(json);

		// 判断是否定制显示字段 如果配置则保存配置并使用 否则查询配置历史
		if (param.get("COLUMN_NAMES") != null) {
			utilChart.delReportColumnByReportAndUser("客户统计", Security.getUser().getCode());;
			utilChart.insertReportColumnByReportAndUser("客户统计", Security.getUser().getCode(), param.get("COLUMN_NAMES"));
		}
		param.put("SQL_COLUMN", utilChart.getColumns(param.get("COLUMN_NAMES") + ""));
		return new ReplyAjaxPage(new ReportService().report(param));
	}

	/**
	 * 查询客户统计图表
	 * 
	 * @return
	 * @author King 2014年7月31日
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doChartClient() {
		String chart = null;
		Map<String, Object> param = _getParameters();
		param = JSONObject.fromObject(param.get("param"));
		param.put("types", ReportService.type);
		param.put("tks", ReportService.tk);
		param.put("HSYCYC", service.getYchs());
		chart = utilChart.to_chartPie(param, "报表-流程-统计状态", "流程统计", "bpm.report.getTj", "1");
		return new ReplyAjax(true, chart, null);
	}

	/**
	 * 查询客户统计图表
	 * 
	 * @return
	 * @author King 2014年7月31日
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doChartClientData() {
		String chart = null;
		Map<String, Object> param = _getParameters();
		param = JSONObject.fromObject(param.get("param"));
		param.put("types", ReportService.type);
		param.put("tks", ReportService.tk);
		param.put("HSYCYC", service.getYchs());
		chart = utilChart.to_chartPie(param, "报表-流程-统计状态", "流程统计", "bpm.report.getTj", "2");
		return new ReplyAjax(true, chart, null);
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "数据统计", "超级表", "列表导出" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public ReplyAjax exportExcel() {
		Map<String, Object> m = _getParameters();
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
		// 查询导出字段
		String[] titleName = null;
		String[] sql_columns = null;

		m.put("USER_CODE", Security.getUser().getCode());
		// 判断是否全部导出
		m.put("SQL_COLUMN", utilChart.getColumns(m.get("SQL_COLUMN") + ""));

		if (m.get("SQL_COLUMN") == null || m.get("SQL_COLUMN") == "" || m.get("SQL_COLUMN") == "*") {
			titleName = titlesName;
			sql_columns = sqlsName;
		} else {
			String[] arr = m.get("SQL_COLUMN").toString().split(",");
			titleName = new String[arr.length];
			sql_columns = arr;
			// 数据排序
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < sqlsName.length; j++) {
					System.out.println("aaaa=" + arr[i]);
					if (arr[i] == sqlsName[j]) {
						// titleName匹配
						titleName[i] = titlesName[j];
					}
				}
			}
		}
		new POIExcelUtil().expExcel_2007(m, "流程统计" + "_" + DateUtil.getSysDate() + "_" + Math.abs(new Random(100).nextInt()) + ".xlsx", "流程统计",
				titleName, sql_columns, service.dataList(m));

		return null;
	}
}
