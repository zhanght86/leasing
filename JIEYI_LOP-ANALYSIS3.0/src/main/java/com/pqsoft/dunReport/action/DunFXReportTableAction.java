package com.pqsoft.dunReport.action;

import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dunReport.service.DunReportTableService;
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

public class DunFXReportTableAction  extends Action{
	private final String _PATH = "dunReportTable/";
	// **实现类服务
	DunReportTableService service = new DunReportTableService();

	//页面自定义显示列的下拉列表所需，同时也是sql所需
	private String[] titlesName = new String[] { 
			"客户名称",
			"客户类型",
			"省份",
			"城市",
			"厂商",
			"代理商",
			"合同编号",
			"行业类型",
			"客户经理",
			"业务类型",
			"支付表状态",
			"支付表编号",
			"起租日期",
			"结束日期",
			"连续逾期天数",
			"开始逾期期数",
			"总逾期期数",
			"逾期租金",
			"逾期率"
			 };
	private String[] sqlsName = new String[] { "CUST_NAME", 
			"CUST_TYPE_NAME", 
			"PROVINCE_NAME", 
			"CITY_NAME", 
			"COMPANY_NAME", 
			"SUP_NAME", 
			"LEASE_CODE", 
			"FICATION_NAME", 
			"CLERK_NAME", 
			"PLATFORM_NAME", 
			"STATUS_NAME", 
			"PAYLIST_CODE", 
			"START_DATE", 
			"END_DATE", 
			"PENALTY_DAY", 
			"PERIOD",
			"ALLPERIOD",
			"YQZJ", 
			"YQL"
			};
	
	//报表工具调用
	UtilChart utilChart=new UtilChart();


	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "逾期报表", "逾期率分析" })
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-逾期-逾期率-统计类型");
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-逾期分析-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		Map<String,Object> param=_getParameters();
		param.put("REPORT_DATE", "4");
		//折线图
		context.put("chartReport",utilChart.to_chartLine(param, "报表-逾期-逾期率-统计类型", "逾期率分析", "dunReport.REPORT_X_MONTH,dunReport.DUNFX_REPORT_TYPE,dunReport.showGroupDunSDFX","1"));
		return new ReplyHtml(VM.html(_PATH+"graphDunRateFXChar.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "逾期报表", "逾期金额分析" })
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply execute_MONEY() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-逾期-逾期率-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-逾期分析-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		Map<String,Object> param=_getParameters();
		param.put("REPORT_DATE", "4");
		context.put("chartReport",utilChart.to_chartLine(param, "报表-逾期-逾期率-统计类型", "逾期金额分析", "dunReport.REPORT_X_MONTH,dunReport.DUNFX_REPORT_TYPE,dunReport.showGroupDunMoneyFX","1"));
		return new ReplyHtml(VM.html(_PATH+"graphDunMoneyFXChar.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "逾期报表", "逾期期次分析" })
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply execute_PERIOD() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-逾期-逾期率-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-逾期分析-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_GROUP_list = (List<Object>) new DataDictionaryMemcached().get("报表-逾期-逾期期数-分类");
		context.put("REPORT_GROUP_list", REPORT_GROUP_list);
		Map<String,Object> param=_getParameters();
		param.put("REPORT_DATE", "4");
		context.put("chartReport",utilChart.to_chartLine(param, "报表-逾期-逾期率-统计类型", "逾期期次分析", "dunReport.REPORT_X_MONTH,dunReport.DUNFX_REPORT_TYPE,dunReport.showGroupDunTermFX","1"));
		
		return new ReplyHtml(VM.html(_PATH+"graphDunFXPeriodChar.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "逾期报表", "逾期天数分析" })
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply execute_DAY() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-逾期-逾期率-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-逾期分析-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_GROUP_list = (List<Object>) new DataDictionaryMemcached().get("报表-逾期-逾期天数-分类");
		context.put("REPORT_GROUP_list", REPORT_GROUP_list);
		Map<String,Object> param=_getParameters();
		param.put("REPORT_DATE", "4");
		context.put("chartReport",utilChart.to_chartLine(param, "报表-逾期-逾期率-统计类型", "逾期天数分析", "dunReport.REPORT_X_MONTH,dunReport.DUNFX_REPORT_TYPE,dunReport.showGroupDunDayFX","1"));
		
		return new ReplyHtml(VM.html(_PATH+"graphDunFXDayChar.vm", context));
	}
	
	
	/**
	 * 进入新增客户图形报表页面
	 * 
	 * @return
	 * @author King 2014年7月30日
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "报表管理", "逾期报表", "逾期率报表" })
	@SuppressWarnings("unchecked")
	public Reply toChartNewDunRate() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-逾期-逾期率-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-逾期分析-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("逾期率统计", titlesName, sqlsName));
		/********************************************************************/
		
		return new ReplyHtml(VM.html(_PATH + "graphFXYQL.vm", context));
	}
	
	/**
	 * 进入新增客户图形报表页面
	 * 
	 * @return
	 * @author King 2014年7月30日
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "报表管理", "逾期报表", "逾期金额报表" })
	@SuppressWarnings("unchecked")
	public Reply toChartNewDunMoney() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-逾期-逾期率-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-逾期分析-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("逾期金额分析", titlesName, sqlsName));
		/********************************************************************/
		
		return new ReplyHtml(VM.html(_PATH + "graphFXYQJE.vm", context));
	}
	
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "报表管理", "逾期报表", "逾期期次报表" })
	@SuppressWarnings("unchecked")
	public Reply toChartNewDunPeriod() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-逾期-逾期率-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-逾期分析-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_GROUP_list = (List<Object>) new DataDictionaryMemcached().get("报表-逾期-逾期期数-分类");
		context.put("REPORT_GROUP_list", REPORT_GROUP_list);

		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("逾期期次分析", titlesName, sqlsName));
		/********************************************************************/
		
		return new ReplyHtml(VM.html(_PATH + "graphFXYQQC.vm", context));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "报表管理", "逾期报表", "逾期天数统计" })
	@SuppressWarnings("unchecked")
	public Reply toChartNewDunDay() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-逾期-逾期率-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-逾期分析-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_GROUP_list = (List<Object>) new DataDictionaryMemcached().get("报表-逾期-逾期天数-分类");
		context.put("REPORT_GROUP_list", REPORT_GROUP_list);

		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("逾期天数统计", titlesName, sqlsName));
		/********************************************************************/
		
		return new ReplyHtml(VM.html(_PATH + "graphFXYQTS.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doTableShowRate() {
		Map<String, Object> param = _getParameters();
		param.put("USER_CODE", Security.getUser().getCode());
		JSONObject json = JSONObject.fromObject(param.get("searchParams"));
		param.putAll(json);
		
		//判断是否定制显示字段 如果配置则保存配置并使用 否则查询配置历史
		if("1".equals(param.get("REPORT_DUN_TYPE"))){
			if(param.get("COLUMN_NAMES") != null){
				utilChart.delReportColumnByReportAndUser("逾期率分析", Security.getUser().getCode());;
				utilChart.insertReportColumnByReportAndUser("逾期率分析", Security.getUser().getCode(),param.get("COLUMN_NAMES"));
			}
		}
		else if("2".equals(param.get("REPORT_DUN_TYPE"))){
			if(param.get("COLUMN_NAMES") != null){
				utilChart.delReportColumnByReportAndUser("逾期金额分析", Security.getUser().getCode());;
				utilChart.insertReportColumnByReportAndUser("逾期金额分析", Security.getUser().getCode(),param.get("COLUMN_NAMES"));
			}
		}
		else if("3".equals(param.get("REPORT_DUN_TYPE"))){
			if(param.get("COLUMN_NAMES") != null){
				utilChart.delReportColumnByReportAndUser("逾期期次分析", Security.getUser().getCode());;
				utilChart.insertReportColumnByReportAndUser("逾期期次分析", Security.getUser().getCode(),param.get("COLUMN_NAMES"));
			}
		}
		else if("4".equals(param.get("REPORT_DUN_TYPE"))){
			if(param.get("COLUMN_NAMES") != null){
				utilChart.delReportColumnByReportAndUser("逾期天数分析", Security.getUser().getCode());;
				utilChart.insertReportColumnByReportAndUser("逾期天数分析", Security.getUser().getCode(),param.get("COLUMN_NAMES"));
			}
		}
		
		param.put("SQL_COLUMN", utilChart.getColumns(param.get("COLUMN_NAMES")+""));
		return new ReplyAjaxPage(service.queryDunFXRatePage(param));
	}
	

	/**
	 * 查询逾期分析图表
	 * 
	 * @return
	 * @author King 2014年7月31日
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doChartDun() {
		String chart = null;
		Map<String, Object> param = _getParameters();
		JSONObject json=JSONObject.fromObject(param.get("param"));
		if("11".equals(json.get("REPORT_DUN_TYPE"))){
			//折线图
			//判断时间节点为全部还是时间段
			//如果为全部将其实日期和当前日期查出来
			//如果为时间段，判断系统中有输入起始日期和终止日期
			if(json!=null && json.get("REPORT_DATE")!=null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("1")){
				param.putAll(service.queryDateByCust());
			}
			else if(json!=null && json.get("REPORT_DATE")!=null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("6")){
				Map dateMap=service.queryDateByCust();
				if(json.get("START_TIME")==null || json.get("START_TIME").equals("")){
					json.put("START_TIME", dateMap.get("START_TIME"));
				}
				
				if(json.get("END_TIME")==null || json.get("END_TIME").equals("")){
					json.put("END_TIME", dateMap.get("END_TIME"));
				}
			}
			 chart=new UtilChart().to_chartLine(json, "报表-客户-分析-统计类型", "客户分析", "clientReport.REPORT_X_MONTH,clientReport.CUST_REPORT_TYPE,clientReport.showGroupClientFX","1");
			
		}else if("13".equals(json.get("REPORT_DUN_TYPE"))){
			//饼状图
			chart=utilChart.to_chartPie(JSONObject.fromObject( _getParameters().get("param")), "报表-客户-统计类型", "客户统计", "clientReport.showGroupClientType","1");
		}
		else if("1".equals(json.get("REPORT_DUN_TYPE"))){
			//散点图
			chart=utilChart.to_chartLine(JSONObject.fromObject( _getParameters().get("param")), "报表-逾期-逾期率-统计类型", "逾期率分析", "dunReport.REPORT_X_MONTH,dunReport.DUNFX_REPORT_TYPE,dunReport.showGroupDunSDFX","1");
		}
		else if("2".equals(json.get("REPORT_DUN_TYPE"))){
			//折线图
			chart=utilChart.to_chartLine(JSONObject.fromObject( _getParameters().get("param")), "报表-逾期-逾期率-统计类型", "逾期金额分析", "dunReport.REPORT_X_MONTH,dunReport.DUNFX_REPORT_TYPE,dunReport.showGroupDunMoneyFX","1");
		}
		else if("3".equals(json.get("REPORT_DUN_TYPE"))){
			chart=utilChart.to_chartLine(JSONObject.fromObject( _getParameters().get("param")), "报表-逾期-逾期率-统计类型", "逾期期次分析", "dunReport.REPORT_X_MONTH,dunReport.DUNFX_REPORT_TYPE,dunReport.showGroupDunTermFX","1");
		}
		else if("4".equals(json.get("REPORT_DUN_TYPE"))){
			chart=utilChart.to_chartLine(JSONObject.fromObject( _getParameters().get("param")), "报表-逾期-逾期率-统计类型", "逾期天数分析", "dunReport.REPORT_X_MONTH,dunReport.DUNFX_REPORT_TYPE,dunReport.showGroupDunDayFX","1");
		}
		return new ReplyAjax(true, chart,null);
	}
	
	/**
	 * 查询逾期统计图表
	 * 
	 * @return
	 * @author King 2014年7月31日
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doChartDunDATA() {
		String chart = null;
		Map<String, Object> param = _getParameters();
		JSONObject json=JSONObject.fromObject(param.get("param"));
		if("11".equals(json.get("REPORT_DUN_TYPE"))){
			//折线图
			//判断时间节点为全部还是时间段
			//如果为全部将其实日期和当前日期查出来
			//如果为时间段，判断系统中有输入起始日期和终止日期
			if(json!=null && json.get("REPORT_DATE")!=null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("1")){
				param.putAll(service.queryDateByCust());
			}
			else if(json!=null && json.get("REPORT_DATE")!=null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("6")){
				Map dateMap=service.queryDateByCust();
				if(json.get("START_TIME")==null || json.get("START_TIME").equals("")){
					json.put("START_TIME", dateMap.get("START_TIME"));
				}
				
				if(json.get("END_TIME")==null || json.get("END_TIME").equals("")){
					json.put("END_TIME", dateMap.get("END_TIME"));
				}
			}
			 chart=new UtilChart().to_chartLine(json, "报表-客户-分析-统计类型", "客户分析", "clientReport.REPORT_X_MONTH,clientReport.CUST_REPORT_TYPE,clientReport.showGroupClientFX","2");
			
		}else if("13".equals(json.get("REPORT_DUN_TYPE"))){
			//饼状图
			chart=utilChart.to_chartPie(JSONObject.fromObject( _getParameters().get("param")), "报表-客户-统计类型", "客户统计", "clientReport.showGroupClientType","2");
		}
		else if("1".equals(json.get("REPORT_DUN_TYPE"))){
			chart=utilChart.to_chartLine(JSONObject.fromObject( _getParameters().get("param")), "报表-逾期-逾期率-统计类型", "逾期率分析", "dunReport.REPORT_X_MONTH,dunReport.DUNFX_REPORT_TYPE,dunReport.showGroupDunSDFX","2");
		}
		else if("2".equals(json.get("REPORT_DUN_TYPE"))){
			chart=utilChart.to_chartLine(JSONObject.fromObject( _getParameters().get("param")), "报表-逾期-逾期率-统计类型", "逾期金额分析", "dunReport.REPORT_X_MONTH,dunReport.DUNFX_REPORT_TYPE,dunReport.showGroupDunMoneyFX","2");
		}
		else if("3".equals(json.get("REPORT_DUN_TYPE"))){
			chart=utilChart.to_chartLine(JSONObject.fromObject( _getParameters().get("param")), "报表-逾期-逾期率-统计类型", "逾期期次分析", "dunReport.REPORT_X_MONTH,dunReport.DUNFX_REPORT_TYPE,dunReport.showGroupDunTermFX","2");
		}
		else if("4".equals(json.get("REPORT_DUN_TYPE"))){
			chart=utilChart.to_chartLine(JSONObject.fromObject( _getParameters().get("param")), "报表-逾期-逾期率-统计类型", "逾期天数分析", "dunReport.REPORT_X_MONTH,dunReport.DUNFX_REPORT_TYPE,dunReport.showGroupDunDayFX","2");
		}
		return new ReplyAjax(true, chart,null);
	}

	
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "报表管理", "逾期报表", "列表导出" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
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
		new POIExcelUtil().expExcel_2007(m,"逾期统计"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx", "逾期统计", titleName,sql_columns, service.clientList(m));
	
		return null;
	}
	
	/**
	 * 手动刷数据
	 * @return
	 * @author King 2014年8月8日
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply overdue_(){
		service.addFiOverDueMonth_();
		return null;
	}
	
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "逾期报表", "项目逾期率" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply queryRateProject() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		
		return new ReplyHtml(VM.html(_PATH+"projectRate.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "报表管理", "逾期报表", "项目逾期率" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply queryRateProjectData() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		
		return new ReplyAjaxPage(service.queryRateProjectData(param));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "逾期报表", "项目逾期率分析" })
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply queryRateProjectChar() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-逾期分析-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		Map<String,Object> param=_getParameters();
		param.put("REPORT_DATE", "4");
		context.put("param", param);
		//折线图
		String TYPE=param.get("TYPE").toString();
		if(TYPE.equals("1")){
			context.put("chartReport",utilChart.queryProjectChar(param, "项目逾期率分析", "dunReport.REPORT_X_MONTH,dunReport.DUNFX_REPORT_TYPE,dunReport.showGroupDunSDFX"));
		}
		else{
			context.put("chartReport",utilChart.queryProjectChar(param, "项目逾期金额分析", "dunReport.REPORT_X_MONTH,dunReport.DUNFX_REPORT_TYPE,dunReport.showGroupDunMoneyFX"));
		}
		
		return new ReplyHtml(VM.html(_PATH+"queryRateProjectChar.vm", context));
	}
	
	
	/**
	 * 查询逾期分析图表
	 * 
	 * @return
	 * @author King 2014年7月31日
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doChartDunProject() {
		String chart = null;
		Map<String, Object> param = _getParameters();
		JSONObject json=JSONObject.fromObject(param.get("param"));
		if("2".equals(json.get("REPORT_DUN_TYPE"))){
			String TYPE=json.get("TYPE").toString();
			if(TYPE.equals("1")){
				chart=utilChart.queryProjectChar(JSONObject.fromObject( _getParameters().get("param")), "项目逾期率分析", "dunReport.REPORT_X_MONTH,dunReport.DUNFX_REPORT_TYPE,dunReport.showGroupDunSDFX");
			}
			else{
				chart=utilChart.queryProjectChar(JSONObject.fromObject( _getParameters().get("param")), "项目逾期金额分析", "dunReport.REPORT_X_MONTH,dunReport.DUNFX_REPORT_TYPE,dunReport.showGroupDunMoneyFX");
			}
		}
		return new ReplyAjax(true, chart,null);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "统计比较", "报表分析" })
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply queryCharFxBJ() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-统计比较-类别维度");
		List<Object> REPORT_TYPE_list= (List<Object>) new DataDictionaryMemcached().get("报表-统计比较-统计类型");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		Map<String,Object> param=_getParameters();
		param.put("REPORT_DATE", "4");
		param.put("REPORT_TYPE", "1");
		context.put("param", param);
		//柱形图
		List<String> listx=service.listX(param);
		List<Map<String, Object>> listY=service.listY(listx,param);
		//
		String REPORT_DATE=param.get("REPORT_DATE").toString();
		String char_name="统计比较分析-";
		String Y_UNIT="";
		if(REPORT_DATE.equals("1")){//利润
			Y_UNIT="元";
			char_name=char_name+"利润(元)";
		}else if(REPORT_DATE.equals("2")){//收益率
			Y_UNIT="%";
			char_name=char_name+"收益率(%)";
		}else if(REPORT_DATE.equals("3")){//逾期率
			Y_UNIT="%";
			char_name=char_name+"逾期率(%)";
		}else if(REPORT_DATE.equals("4")){//逾期金额
			Y_UNIT="元";
			char_name=char_name+"逾期金额(元)";
		}
		context.put("chartReport",utilChart.createBarChart(Y_UNIT,char_name, listx,listY));
		return new ReplyHtml(VM.html(_PATH+"graphTJFXChar.vm", context));
	}
	
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doCharFxBJ() {
		String chart = null;
		Map<String, Object> param = _getParameters();
		JSONObject json=JSONObject.fromObject(param.get("param"));
		//柱形图
		List<String> listx=service.listX(json);
		List<Map<String, Object>> listY=service.listY(listx,json);
		
		String REPORT_DATE=json.get("REPORT_DATE").toString();
		String char_name="统计比较分析-";
		String Y_UNIT="";
		if(REPORT_DATE.equals("1")){//利润
			Y_UNIT="元";
			char_name=char_name+"利润(元)";
		}else if(REPORT_DATE.equals("2")){//收益率
			Y_UNIT="%";
			char_name=char_name+"收益率(%)";
		}else if(REPORT_DATE.equals("3")){//逾期率
			Y_UNIT="%";
			char_name=char_name+"逾期率(%)";
		}else if(REPORT_DATE.equals("4")){//逾期金额
			Y_UNIT="元";
			char_name=char_name+"逾期金额(元)";
		}
		chart=new UtilChart().createBarChart(Y_UNIT,char_name, listx,listY);
		
		return new ReplyAjax(true, chart,null);
	}
}
