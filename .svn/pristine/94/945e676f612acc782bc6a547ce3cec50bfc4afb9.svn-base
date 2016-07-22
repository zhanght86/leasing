package com.pqsoft.payReport.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.payReport.service.PayReportTableService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.POIExcelUtil;
import com.pqsoft.util.UtilChart;

/******* 支付表报表****@auth: leeds 2014年8月4日 *************************/
public class PayReportTableAction extends Action {
	
	private final String _PATH = "payReportTable/";
	// **实现类服务
	PayReportTableService service = new PayReportTableService();

	//页面自定义显示列的下拉列表所需，同时也是sql所需
	private String[] titlesName = new String[] { "状态", "合同编号", "支付表编号", "版本号",
			"客户经理", "客户名称", "创建时间", "起租日期", "租赁物名称", "租赁物型号", "厂商", 
			"整机编号/车架号", "数量", "租赁期限", "租赁到期日", "租赁物总价", "租金总额", "融资额",
			"约定利率", "现行利率", "首期租金", "首期租金比例", "第一期租金", "保证金", "保险费",
			"手续费", "管理费", "留购价", "首付款合计", "其他费用合计"};
	private String[] sqlsName = new String[] { "STATUS", "LEASE_CODE", "PAYLIST_CODE", "VERSION_CODE",
			"CLERK_NAME", "CLIENT_NAME", "CREATE_DATE", "START_DATE","PRODUCT_NAME", "SPEC_NAME", "COMPANY_NAME", 
			"WHOLE_ENGINE_CODE", "AMOUNT", "LEASE_TERM", "END_DATE","LEASE_TOPRIC", "MONEY_ALL", "TOPRIC_SUBFIRENT",
			"YD_INTEREST", "YEAR_INTEREST", "FIRSTRENT_VALUE", "FIRSTRENT_RATE","FIRST_RENTMONEY", "DEPOSIT_VALUE", "INSURANCE_VALUE",
			"POUNDAGE_PRICE", "POUNDAGE_VALUE", "STAYBUY_PRICE", "FIRST_MONEY_ALL","OTHER_SUM"};
	
	//报表工具调用
	UtilChart utilChart=new UtilChart();

	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "支付表报表", "正常支付表统计" })
	@aDev(code = "170020", email = "zhanglu@pqsoft.cn", name = "张路")
	public Reply execute() {
		return new ReplyHtml(VM.html(_PATH+"graphPay.vm", null));
	}

	/**
	 * 进入新增支付表-饼状图页面
	 * 
	 * @return
	 * @author leeds 2014年8月4日
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170020", email = "zhanglu@pqsoft.cn", name = "张路")
	@aPermission(name = { "报表管理", "支付表报表", "新增支付表统计" })
	@SuppressWarnings("unchecked")
	public Reply toNewPayChart() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-支付表-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-支付表-新增支付表-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		Map params = new HashMap();
		params.put("REPORT_TYPE", 3);
		context.put("chartReport", utilChart.to_chartPie(params, "报表-支付表-新增支付表-统计类型", "新增支付表", "payReport.showGroupNewPay","1"));
		return new ReplyHtml(VM.html(_PATH + "graphNewPayChart.vm", context));
	}
	
	/**
	 * 进入新增支付表-数据明细页面
	 * 
	 * @return
	 * @author leeds 2014年8月4日
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170020", email = "zhanglu@pqsoft.cn", name = "张路")
	@aPermission(name = { "报表管理", "支付表报表", "新增支付表统计" })
	@SuppressWarnings("unchecked")
	public Reply toNewPayData() {
		VelocityContext context = new VelocityContext();
		Map param = _getParameters();
		context.put("param", param);
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-支付表-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-支付表-新增支付表-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);

		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("支付表统计", titlesName, sqlsName));
		/********************************************************************/
		
		return new ReplyHtml(VM.html(_PATH + "graphNewPayData.vm", context));
	}
	/**
	 * 进入正常支付表-饼状图页面
	 * 
	 * @return
	 * @author leeds 2014年8月4日
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170020", email = "zhanglu@pqsoft.cn", name = "张路")
	@aPermission(name = {"报表管理", "支付表报表", "正常支付表统计" })
	@SuppressWarnings("unchecked")
	public Reply toNormalPayChart() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-支付表-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-支付表-正常-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		Map params = new HashMap();
		params.put("REPORT_TYPE", 3);
		context.put("chartReport", utilChart.to_chartPie(params, "报表-支付表-正常-统计类型", "正常支付表", "payReport.showGroupNormalPay","1"));
		return new ReplyHtml(VM.html(_PATH + "graphNormalPayChart.vm", context));
	}
	
	/**
	 * 进入正常支付表-数据明细页面
	 * 
	 * @return
	 * @author leeds 2014年8月4日
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170020", email = "zhanglu@pqsoft.cn", name = "张路")
	@aPermission(name = { "数据统计", "支付表报表", "正常支付表统计" })
	@SuppressWarnings("unchecked")
	public Reply toNormalPayData() {
		VelocityContext context = new VelocityContext();
		Map param = _getParameters();
		context.put("param", param);
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-支付表-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-支付表-正常-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);

		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("支付表统计", titlesName, sqlsName));
		/********************************************************************/
		
		return new ReplyHtml(VM.html(_PATH + "graphNormalPayData.vm", context));
	}
	/**
	 * 进入变更支付表-饼状图页面
	 * 
	 * @return
	 * @author leeds 2014年8月4日
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170020", email = "zhanglu@pqsoft.cn", name = "张路")
	@aPermission(name = { "报表管理", "支付表报表", "变更支付表统计" })
	@SuppressWarnings("unchecked")
	public Reply toChangePayChart() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-支付表-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-支付表-变更-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		Map params = new HashMap();
		params.put("REPORT_TYPE", 3);
		context.put("chartReport", utilChart.to_chartPie(params, "报表-支付表-变更-统计类型", "变更支付表", "payReport.showGroupChangePay","1"));
		return new ReplyHtml(VM.html(_PATH + "graphChangePayChart.vm", context));
	}
	
	/**
	 * 进入变更支付表-数据明细页面
	 * 
	 * @return
	 * @author leeds 2014年8月4日
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170020", email = "zhanglu@pqsoft.cn", name = "张路")
	@aPermission(name = { "报表管理", "支付表报表", "变更支付表统计" })
	@SuppressWarnings("unchecked")
	public Reply toChangePayData() {
		VelocityContext context = new VelocityContext();
		Map param = _getParameters();
		context.put("param", param);
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-支付表-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-支付表-变更-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);

		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("支付表统计", titlesName, sqlsName));
		/********************************************************************/
		
		return new ReplyHtml(VM.html(_PATH + "graphChangePayData.vm", context));
	}
	/**
	 * 
	 * 进入新增支付表分析-折线图页面
	 * 
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "支付表报表", "新增支付表分析" })
	@aDev(code = "170020", email = "zhanglu@pqsoft.cn", name = "张路")
	public Reply toNewPayChartFX() {
		Map<String,Object> json=_getParameters();
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-支付表-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-支付表-新增支付表-分析");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		json.put("REPORT_DATE", "4");//默认为本年
		json.put("REPORT_TYPE", "3");//默认为客户经理
		//折线图
		//判断时间节点为全部还是时间段
		//如果为全部将其实日期和当前日期查出来
		//如果为时间段，判断系统中有输入起始日期和终止日期
		if(json!=null && json.get("REPORT_DATE")!=null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("1")){
			json.putAll(service.queryDateByPay());
		}
		else if(json!=null && json.get("REPORT_DATE")!=null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("6")){
			Map dateMap=service.queryDateByPay();
			if(json.get("START_TIME")==null || json.get("START_TIME").equals("")){
				json.put("START_TIME", dateMap.get("START_TIME"));
			}
			
			if(json.get("END_TIME")==null || json.get("END_TIME").equals("")){
				json.put("END_TIME", dateMap.get("END_TIME"));
			}
		}
		String chart=new UtilChart().to_chartLine(json, "报表-支付表-新增支付表-分析", "新增支付表分析", "payReport.REPORT_X_MONTH,payReport.NEW_PAY_TYPE,payReport.showGroupNewPayFX","1");
		context.put("chartReport", chart);
		return new ReplyHtml(VM.html(_PATH+"graphFXNewChart.vm", context));
	}
	
	/**
	 * 进入新增支付表-折线图详细页面
	 * 
	 * @return
	 * @author leeds 2014年8月1日
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170020", email = "zhanglu@pqsoft.cn", name = "张路")
	@aPermission(name = { "报表管理", "支付表报表", "新增支付表分析" })
	@SuppressWarnings("unchecked")
	public Reply toNewPayDataFX() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-支付表-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-支付表-新增支付表-分析");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		Map json=new HashMap();
		json.putAll(param);
		json.put("REPORT_DATE", "4");//默认为本年
		json.put("REPORT_TYPE", "3");//默认为客户经理
		context.put("chartReport",new UtilChart().to_chartLine(json, "报表-支付表-新增支付表-分析", "新增支付表分析", "payReport.REPORT_X_MONTH,payReport.NEW_PAY_TYPE,payReport.showGroupNewPayFX","1"));
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("客户统计", titlesName, sqlsName));
		/********************************************************************/
		return new ReplyHtml(VM.html(_PATH + "graphFXNewData.vm", context));
	}
	
	/**
	 * 
	 * 进入变更支付表分析-折线图页面
	 * 
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "支付表报表", "变更支付表分析" })
	@aDev(code = "170020", email = "zhanglu@pqsoft.cn", name = "张路")
	public Reply toChangePayChartFX() {
		Map<String,Object> json=_getParameters();
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-支付表-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-支付表-变更-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		json.put("REPORT_DATE", "4");//默认为本年
		json.put("REPORT_TYPE", "3");//默认为客户经理
		//折线图
		//判断时间节点为全部还是时间段
		//如果为全部将其实日期和当前日期查出来
		//如果为时间段，判断系统中有输入起始日期和终止日期
		if(json!=null && json.get("REPORT_DATE")!=null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("1")){
			json.putAll(service.queryDateByPay());
		}
		else if(json!=null && json.get("REPORT_DATE")!=null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("6")){
			Map dateMap=service.queryDateByPay();
			if(json.get("START_TIME")==null || json.get("START_TIME").equals("")){
				json.put("START_TIME", dateMap.get("START_TIME"));
			}
			
			if(json.get("END_TIME")==null || json.get("END_TIME").equals("")){
				json.put("END_TIME", dateMap.get("END_TIME"));
			}
		}
		String chart=new UtilChart().to_chartLine(json, "报表-支付表-变更-统计类型", "变更支付表分析", "payReport.REPORT_X_MONTH,payReport.CHANGE_PAY_TYPE,payReport.showGroupChangePayFX","1");
		context.put("chartReport", chart);
		return new ReplyHtml(VM.html(_PATH+"graphFXChangeChart.vm", context));
	}
	
	/**
	 * 进入变更支付表-折线图详细页面
	 * 
	 * @return
	 * @author leeds 2014年8月1日
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170020", email = "zhanglu@pqsoft.cn", name = "张路")
	@aPermission(name = { "报表管理", "支付表报表", "变更支付表分析" })
	@SuppressWarnings("unchecked")
	public Reply toChangePayDataFX() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-支付表-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-支付表-变更-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		Map json=new HashMap();
		json.putAll(param);
		json.put("REPORT_DATE", "4");//默认为本年
		json.put("REPORT_TYPE", "3");//默认为客户经理
		context.put("chartReport",new UtilChart().to_chartLine(json, "报表-支付表-变更-统计类型", "变更支付表分析", "payReport.REPORT_X_MONTH,payReport.CHANGE_PAY_TYPE,payReport.showGroupChangePayFX","1"));
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("客户统计", titlesName, sqlsName));
		/********************************************************************/
		return new ReplyHtml(VM.html(_PATH + "graphFXChangeData.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170020", email = "zhanglu@pqsoft.cn", name = "张路")
	public Reply doTableShow() {
		Map<String, Object> param = _getParameters();
		param.put("USER_CODE", Security.getUser().getCode());
		JSONObject json = JSONObject.fromObject(param.get("searchParams"));
		param.putAll(json);

		//判断是否定制显示字段 如果配置则保存配置并使用 否则查询配置历史
		if(param.get("COLUMN_NAMES") != null){
			utilChart.delReportColumnByReportAndUser("支付表统计", Security.getUser().getCode());;
			utilChart.insertReportColumnByReportAndUser("支付表统计", Security.getUser().getCode(),param.get("COLUMN_NAMES"));
		}
		param.put("SQL_COLUMN", utilChart.getColumns(param.get("COLUMN_NAMES")+""));
		return new ReplyAjaxPage(service.queryPage(param));
	}

	/**
	 * 查询支付表统计图表
	 * 
	 * @return
	 * @author leeds 2014年8月4日
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170020", email = "zhanglu@pqsoft.cn", name = "张路")
	public Reply doChartPay() {
		String chart = null;
		Map<String, Object> param = _getParameters();
		JSONObject json=JSONObject.fromObject(param.get("param"));
		if("2".equals(json.get("REPORT_QZTX"))){
			//折线图
			//判断时间节点为全部还是时间段
			//如果为全部将其实日期和当前日期查出来
			//如果为时间段，判断系统中有输入起始日期和终止日期
			if(json!=null && json.get("REPORT_DATE")!=null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("1")){
				json.putAll(service.queryDateByPay());
			}
			else if(json!=null && json.get("REPORT_DATE")!=null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("6")){
				Map dateMap=service.queryDateByPay();
				if(json.get("START_TIME")==null || json.get("START_TIME").equals("")){
					json.put("START_TIME", dateMap.get("START_TIME"));
				}
				
				if(json.get("END_TIME")==null || json.get("END_TIME").equals("")){
					json.put("END_TIME", dateMap.get("END_TIME"));
				}
			}
			if("NEW".equals(json.get("PAGE_TYPE"))) {
				chart=new UtilChart().to_chartLine(json, "报表-支付表-新增支付表-分析", "新增支付表", "payReport.REPORT_X_MONTH,payReport.NEW_PAY_TYPE,payReport.showGroupNewPayFX","1");
			} else {
				chart=new UtilChart().to_chartLine(json, "报表-支付表-变更-统计类型", "变更", "payReport.REPORT_X_MONTH,payReport.CHANGE_PAY_TYPE,payReport.showGroupChangePayFX","1");
			}
		}else{
			//饼状图
			if("NEW".equals(json.get("PAGE_TYPE"))) {
				chart=utilChart.to_chartPie(JSONObject.fromObject( _getParameters().get("param")), "报表-支付表-新增支付表-统计类型", "新增支付表", "payReport.showGroupNewPay","1");
			} else if("NORMAL".equals(json.get("PAGE_TYPE"))) {
				chart=utilChart.to_chartPie(JSONObject.fromObject( _getParameters().get("param")), "报表-支付表-正常-统计类型", "正常", "payReport.showGroupNormalPay","1");
			} else {
				chart=utilChart.to_chartPie(JSONObject.fromObject( _getParameters().get("param")), "报表-支付表-变更-统计类型", "变更", "payReport.showGroupChangePay","1");
			}
		}
		return new ReplyAjax(true, chart,null);
	}
	
	/**
	 * 查询支付表统计图表
	 * 
	 * @return
	 * @author leeds 2014年8月4日
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170020", email = "zhanglu@pqsoft.cn", name = "张路")
	public Reply doChartPayData() {
		String chart = null;
		Map<String, Object> param = _getParameters();
		JSONObject json=JSONObject.fromObject(param.get("param"));
		if("2".equals(json.get("REPORT_QZTX"))){
			//折线图
			//判断时间节点为全部还是时间段
			//如果为全部将其实日期和当前日期查出来
			//如果为时间段，判断系统中有输入起始日期和终止日期
			if(json!=null && json.get("REPORT_DATE")!=null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("1")){
				json.putAll(service.queryDateByPay());
			}
			else if(json!=null && json.get("REPORT_DATE")!=null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("6")){
				Map dateMap=service.queryDateByPay();
				if(json.get("START_TIME")==null || json.get("START_TIME").equals("")){
					json.put("START_TIME", dateMap.get("START_TIME"));
				}
				
				if(json.get("END_TIME")==null || json.get("END_TIME").equals("")){
					json.put("END_TIME", dateMap.get("END_TIME"));
				}
			}
			if("NEW".equals(json.get("PAGE_TYPE"))) {
				chart=new UtilChart().to_chartLine(json, "报表-支付表-新增支付表-分析", "新增支付表", "payReport.REPORT_X_MONTH,payReport.NEW_PAY_TYPE,payReport.showGroupNewPayFX","2");
			} else {
				chart=new UtilChart().to_chartLine(json, "报表-支付表-变更-统计类型", "变更", "payReport.REPORT_X_MONTH,payReport.CHANGE_PAY_TYPE,payReport.showGroupChangePayFX","2");
			}
		}else{
			//饼状图
			if("NEW".equals(json.get("PAGE_TYPE"))) {
				chart=utilChart.to_chartPie(JSONObject.fromObject( _getParameters().get("param")), "报表-支付表-新增支付表-统计类型", "新增支付表", "payReport.showGroupNewPay","2");
			} else if("NORMAL".equals(json.get("PAGE_TYPE"))) {
				chart=utilChart.to_chartPie(JSONObject.fromObject( _getParameters().get("param")), "报表-支付表-正常-统计类型", "正常", "payReport.showGroupNormalPay","2");
			} else {
				chart=utilChart.to_chartPie(JSONObject.fromObject( _getParameters().get("param")), "报表-支付表-变更-统计类型", "变更", "payReport.showGroupChangePay","2");
			}
		}
		return new ReplyAjax(true, chart,null);
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "数据统计", "支付表报表", "列表导出" })
	@aDev(code = "170020", email = "zhanglu@pqsoft.cn", name = "张路")
	public ReplyAjax exportExcel(){
		Map<String,Object> m = _getParameters();
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
		//查询导出字段
		String[] titleName = null;
		String[] sql_columns = null;
		
		m.put("USER_CODE", Security.getUser().getCode());
		//判断是否全部导出
		m.put("SQL_COLUMN",utilChart.getColumns(m.get("SQL_COLUMN")+""));
		
		if(m.get("SQL_COLUMN") == null || m.get("SQL_COLUMN") == ""|| m.get("SQL_COLUMN") == "*"){
			titleName = titlesName;
			sql_columns=sqlsName;
		}else{
			String[] arr = m.get("SQL_COLUMN").toString().split(",");
			titleName = new String[arr.length];
			sql_columns=arr;
			//数据排序
			for (int i = 0; i < arr.length; i++) {
				for(int j=0;j<sqlsName.length;j++){
					System.out.println("aaaa="+arr[i]);
					if(arr[i]==sqlsName[j]){
						//titleName匹配
						titleName[i]=titlesName[j];
					}
				}
			}
		}
		new POIExcelUtil().expExcel_2007(m,"支付表统计"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx", "支付表统计", titleName,sql_columns, service.payList(m));
	
		return null;
	}

}
