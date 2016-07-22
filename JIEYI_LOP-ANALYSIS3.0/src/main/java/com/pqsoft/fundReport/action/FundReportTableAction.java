package com.pqsoft.fundReport.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.fundReport.service.FundReportTableService;
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
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.POIExcelUtil;
import com.pqsoft.util.UtilChart;

/******* 应实收报表****@auth: king 2014年7月30日 *************************/
public class FundReportTableAction extends Action {
	
	private final String _PATH = "fundReportTable/";
	// **实现类服务
	FundReportTableService service = new FundReportTableService();

	//页面自定义显示列的下拉列表所需，同时也是sql所需
	private String[] titlesName = new String[] { "客户名称","客户等级","客户类型","区域","业务类型",
			"客户经理","支付表编号", "租金","本金","利息","违约金","首付款","保证金","手续费","保险费","其他费用"};
	private String[] sqlsName = new String[] { "CUST_NAME","CUST_LEVER","TYPE_NAME","AREA_NAME","PLATFORM_NAME",
			"CLERK_NAME","PAYLIST_CODE", "ZUJIN","BENJIN","LIXI","WEIYUEJIN","SQK","BZJ","SXF","BXF","QTFY"};
	
	//报表工具调用
	UtilChart utilChart=new UtilChart();


	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"报表管理", "应实收报表", "应收统计"})
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-客户统计-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-应实收-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		context.put("chartReport", utilChart.to_chartPie(_getParameters(), "报表-应实收-统计类型", "应收统计", "fundReport.showGroupFundType","1"));
		context.put("param", param);
		return new ReplyHtml(VM.html(_PATH+"graphTJFundChart.vm", context));
	}

	/**
	 * 进入应收图形报表页面
	 * 
	 * @return
	 * @author King 2014年7月30日
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "报表管理", "应实收报表", "客户统计" })
	@SuppressWarnings("unchecked")
	public Reply toChartFund() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-客户统计-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-应实收-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("应收统计", titlesName, sqlsName));
		/********************************************************************/
		
		return new ReplyHtml(VM.html(_PATH + "graphTJFund.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doTableShow() {
		Map<String, Object> param = _getParameters();
		param.put("USER_CODE", Security.getUser().getCode());
		JSONObject json = JSONObject.fromObject(param.get("searchParams"));
		param.putAll(json);

		//判断是否定制显示字段 如果配置则保存配置并使用 否则查询配置历史
		if(param.get("COLUMN_NAMES") != null){
			utilChart.delReportColumnByReportAndUser("应收统计", Security.getUser().getCode());;
			utilChart.insertReportColumnByReportAndUser("应收统计", Security.getUser().getCode(),param.get("COLUMN_NAMES"));
		}
		param.put("SQL_COLUMN", utilChart.getColumns(param.get("COLUMN_NAMES")+""));
		return new ReplyAjaxPage(service.queryPage(param));
	}
	
	/**
	 * 查询应实收统计图表
	 * 
	 * @return
	 * @author King 2014年7月31日
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doChartFund() {
		String chart = null;
		Map<String, Object> param = _getParameters();
		JSONObject json=JSONObject.fromObject(param.get("param"));
		if("2".equals(json.get("REPORT_QZTX"))){
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
			 chart=new UtilChart().to_chartLine(json, "报表-应实收-统计类型", "应收分析", "fundReport.REPORT_X_MONTH,fundReport.FUND_REPORT_TYPE,fundReport.showGroupFundFX","1");
			
		}else{
			//饼状图
			chart=utilChart.to_chartPie(JSONObject.fromObject( _getParameters().get("param")), "报表-应实收-统计类型", "应收统计", "fundReport.showGroupFundType","1");
		}
		return new ReplyAjax(true, chart,null);
	}
	/**
	 * 查询应实收统计图表
	 * 
	 * @return
	 * @author King 2014年7月31日
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doChartFundData() {
		String chart = null;
		Map<String, Object> param = _getParameters();
		JSONObject json=JSONObject.fromObject(param.get("param"));
		if("2".equals(json.get("REPORT_QZTX"))){
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
			chart=new UtilChart().to_chartLine(json, "报表-应实收-统计类型", "应收分析", "fundReport.REPORT_X_MONTH,fundReport.FUND_REPORT_TYPE,fundReport.showGroupFundFX","2");
			
		}else{
			//饼状图
			chart=utilChart.to_chartPie(JSONObject.fromObject( _getParameters().get("param")), "报表-应实收-统计类型", "应收统计", "fundReport.showGroupFundType","2");
		}
		return new ReplyAjax(true, chart,null);
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "应实收报表", "应收分析" })
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply toChartFXFund() {
		Map<String, Object> json = _getParameters();
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-客户分析-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-应实收-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		String chart = null;
		//折线图
		//判断时间节点为全部还是时间段
		//如果为全部将其实日期和当前日期查出来
		//如果为时间段，判断系统中有输入起始日期和终止日期
		if(json!=null && json.get("REPORT_DATE")!=null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("1")){
			json.putAll(service.queryDateByCust());
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
		json.put("REPORT_DATE", "4");//默认为本年
		json.put("REPORT_TYPE", "1");//默认为新增客户
		chart=new UtilChart().to_chartLine(json, "报表-应实收-统计类型", "应收分析", "fundReport.REPORT_X_MONTH,fundReport.FUND_REPORT_TYPE,fundReport.showGroupFundFX","1");
		context.put("chartReport",chart);
		return new ReplyHtml(VM.html(_PATH+"graphFXFundChart.vm", context));
	}

	
	/**
	 * 进入应收图形报表页面
	 * 
	 * @return
	 * @author King 2014年8月1日
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "报表管理", "应实收报表", "应收分析" })
	@SuppressWarnings("unchecked")
	public Reply toChartFXFundData() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-客户分析-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-应实收-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		Map json=new HashMap();
		json.putAll(param);
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("应收分析", titlesName, sqlsName));
		/********************************************************************/
		return new ReplyHtml(VM.html(_PATH + "graphFXFund.vm", context));
	}
	
	/**
	 * 进入实收图形报表页面
	 * 
	 * @return
	 * @author King 2014年7月30日
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = {"报表管理", "应实收报表", "实收统计"})
	@SuppressWarnings("unchecked")
	public Reply toChartFundSS() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-客户统计-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-应实收-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		context.put("chartReport",utilChart.to_chartPie( _getParameters(), "报表-应实收-统计类型", "实收统计", "fundReport.showGroupFundSSType","1"));
		context.put("param", param);
		return new ReplyHtml(VM.html(_PATH + "graphTJFundSSChart.vm", context));
	}
	/**
	 * 进入实收图形报表页面数据
	 * 
	 * @return
	 * @author King 2014年7月30日
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@SuppressWarnings("unchecked")
	public Reply toChartFundSSData() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-客户统计-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-应实收-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("实收统计", titlesName, sqlsName));
		/********************************************************************/
		
		return new ReplyHtml(VM.html(_PATH + "graphTJFundSS.vm", context));
	}
	
	/**
	 * 实收统计
	 * @return
	 * @author King 2014年8月4日
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doTableSSShow() {
		Map<String, Object> param = _getParameters();
		param.put("USER_CODE", Security.getUser().getCode());
		JSONObject json = JSONObject.fromObject(param.get("searchParams"));
		param.putAll(json);
		
		//判断是否定制显示字段 如果配置则保存配置并使用 否则查询配置历史
		if(param.get("COLUMN_NAMES") != null){
			utilChart.delReportColumnByReportAndUser("实收统计", Security.getUser().getCode());;
			utilChart.insertReportColumnByReportAndUser("实收统计", Security.getUser().getCode(),param.get("COLUMN_NAMES"));
		}
		param.put("SQL_COLUMN", utilChart.getColumns(param.get("COLUMN_NAMES")+""));
		return new ReplyAjaxPage(service.queryPage(param));
	}
	
	/**
	 * 查询实收统计图表
	 * 
	 * @return
	 * @author King 2014年7月31日
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doChartFundSS() {
		String chart = null;
		Map<String, Object> param = _getParameters();
		JSONObject json=JSONObject.fromObject(param.get("param"));
		if("2".equals(json.get("REPORT_QZTX"))){
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
			 chart=new UtilChart().to_chartLine(json, "报表-应实收-统计类型", "实收分析", "fundReport.REPORT_X_MONTH,fundReport.FUNDSS_REPORT_TYPE,fundReport.showGroupFundSSFX","1");
			
		}else{
			//饼状图
			chart=utilChart.to_chartPie(JSONObject.fromObject( _getParameters().get("param")), "报表-应实收-统计类型", "实收统计", "fundReport.showGroupFundSSType","1");
		}
		return new ReplyAjax(true, chart,null);
	}
	
	/**
	 * 查询实收统计图表
	 * 
	 * @return
	 * @author King 2014年7月31日
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doChartFundSSData() {
		String chart = null;
		Map<String, Object> param = _getParameters();
		JSONObject json=JSONObject.fromObject(param.get("param"));
		if("2".equals(json.get("REPORT_QZTX"))){
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
			chart=new UtilChart().to_chartLine(json, "报表-应实收-统计类型", "实收分析", "fundReport.REPORT_X_MONTH,fundReport.FUNDSS_REPORT_TYPE,fundReport.showGroupFundSSFX","2");
			
		}else{
			//饼状图
			chart=utilChart.to_chartPie(JSONObject.fromObject( _getParameters().get("param")), "报表-应实收-统计类型", "实收统计", "fundReport.showGroupFundSSType","2");
		}
		return new ReplyAjax(true, chart,null);
	}
	
	/**
	 * 进入实收图形报表页面
	 * 
	 * @return
	 * @author King 2014年8月1日
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "报表管理", "应实收报表", "实收分析" })
	@SuppressWarnings("unchecked")
	public Reply toChartFXFundSS() {
		Map<String, Object> json = _getParameters();
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-客户分析-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-应实收-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		String chart = null;
		//折线图
		//判断时间节点为全部还是时间段
		//如果为全部将其实日期和当前日期查出来
		//如果为时间段，判断系统中有输入起始日期和终止日期
		if(json!=null && json.get("REPORT_DATE")!=null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("1")){
			json.putAll(service.queryDateByCust());
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
		json.put("REPORT_DATE", "4");//默认为本年
		json.put("REPORT_TYPE", "1");//默认为新增客户
		chart=new UtilChart().to_chartLine(json, "报表-应实收-统计类型", "实收分析", "fundReport.REPORT_X_MONTH,fundReport.FUNDSS_REPORT_TYPE,fundReport.showGroupFundSSFX","1");
		context.put("chartReport",chart);
		return new ReplyHtml(VM.html(_PATH + "graphFXFundSSChart.vm", context));
	}
	/**
	 * 进入实收图形报表页面
	 * 
	 * @return
	 * @author King 2014年8月1日
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "报表管理", "应实收报表", "实收分析" })
	@SuppressWarnings("unchecked")
	public Reply toChartFXFundSSData() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-客户分析-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-应实收-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("实收分析", titlesName, sqlsName));
		/********************************************************************/
		return new ReplyHtml(VM.html(_PATH + "graphFXFundSS.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "报表管理", "超级表", "列表导出" })
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
		new POIExcelUtil().expExcel_2007(m,"应实收统计"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx", "应收统计", titleName,sql_columns, service.fundList(m));
		return null;
	}
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "报表管理", "超级表", "列表导出" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public ReplyAjax exportSSExcel(){
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
		new POIExcelUtil().expExcel_2007(m,"应实收统计"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx", "实收收统计", titleName,sql_columns, service.fundList(m));
		return null;
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "应实收报表", "应收未收统计" })
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply toChartTJFundWS() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-客户统计-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-应实收-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		context.put("chartReport", utilChart.to_chartPie(_getParameters(), "报表-应实收-统计类型", "应收未收统计", "fundReport.showGroupFundWSType","1"));
		return new ReplyHtml(VM.html(_PATH+"graphTJFundWSChart.vm", context));
	}

	/**
	 * 查询应实收未收统计图表
	 * 
	 * @return
	 * @author King 2014年7月31日
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doChartFundWS() {
		String chart = null;
		Map<String, Object> param = _getParameters();
		JSONObject json=JSONObject.fromObject(param.get("param"));
		if("2".equals(json.get("REPORT_QZTX"))){
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
			 chart=new UtilChart().to_chartLine(json, "报表-应实收-统计类型", "应收未收分析", "fundReport.REPORT_X_MONTH,fundReport.FUNDWS_REPORT_TYPE,fundReport.showGroupFundWSFX","1");
			
		}else{
			//饼状图
			chart=utilChart.to_chartPie(JSONObject.fromObject( _getParameters().get("param")), "报表-应实收-统计类型", "应收未收统计", "fundReport.showGroupFundWSType","1");
		}
		return new ReplyAjax(true, chart,null);
	}
	
	/**
	 * 进入应收未收报表管理页面
	 * 
	 * @return
	 * @author King 2014年7月30日
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "报表管理", "应实收报表", "客户统计" })
	@SuppressWarnings("unchecked")
	public Reply toChartFundWS() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-客户统计-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-应实收-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("应收未收统计", titlesName, sqlsName));
		/********************************************************************/
		
		return new ReplyHtml(VM.html(_PATH + "graphTJFundWS.vm", context));
	}
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doFundWSDataShow() {
		Map<String, Object> param = _getParameters();
		param.put("USER_CODE", Security.getUser().getCode());
		JSONObject json = JSONObject.fromObject(param.get("searchParams"));
		param.putAll(json);

		//判断是否定制显示字段 如果配置则保存配置并使用 否则查询配置历史
		if(param.get("COLUMN_NAMES") != null){
			utilChart.delReportColumnByReportAndUser("应收未收统计", Security.getUser().getCode());;
			utilChart.insertReportColumnByReportAndUser("应收未收统计", Security.getUser().getCode(),param.get("COLUMN_NAMES"));
		}
		param.put("SQL_COLUMN", utilChart.getColumns(param.get("COLUMN_NAMES")+""));
		return new ReplyAjaxPage(service.queryWSPage(param));
	}
	/**
	 * 查询应实收统计图表
	 * 
	 * @return
	 * @author King 2014年7月31日
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doChartFundWSData() {
		String chart = null;
		Map<String, Object> param = _getParameters();
		JSONObject json=JSONObject.fromObject(param.get("param"));
		if("2".equals(json.get("REPORT_QZTX"))){
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
			chart=new UtilChart().to_chartLine(json, "报表-应实收-统计类型", "应收未收分析", "fundReport.REPORT_X_MONTH,fundReport.FUNDWS_REPORT_TYPE,fundReport.showGroupFundWSFX","2");
			
		}else{
			//饼状图
			chart=utilChart.to_chartPie(JSONObject.fromObject( _getParameters().get("param")), "报表-应实收-统计类型", "应收未收统计", "fundReport.showGroupFundWSType","2");
		}
		return new ReplyAjax(true, chart,null);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "应实收报表", "应收分析" })
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply toChartFXFundWS() {
		Map<String, Object> json = _getParameters();
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-客户分析-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-应实收-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		String chart = null;
		//折线图
		//判断时间节点为全部还是时间段
		//如果为全部将其实日期和当前日期查出来
		//如果为时间段，判断系统中有输入起始日期和终止日期
		if(json!=null && json.get("REPORT_DATE")!=null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("1")){
			json.putAll(service.queryDateByCust());
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
		json.put("REPORT_DATE", "4");//默认为本年
		json.put("REPORT_TYPE", "1");//默认为新增客户
		chart=new UtilChart().to_chartLine(json, "报表-应实收-统计类型", "应收未收分析", "fundReport.REPORT_X_MONTH,fundReport.FUNDWS_REPORT_TYPE,fundReport.showGroupFundWSFX","1");
		context.put("chartReport",chart);
		return new ReplyHtml(VM.html(_PATH+"graphFXFundWSChart.vm", context));
	}
	/**
	 * 进入应收未收图形报表页面
	 * 
	 * @return
	 * @author King 2014年8月1日
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	@aPermission(name = { "报表管理", "应实收报表", "应收分析" })
	@SuppressWarnings("unchecked")
	public Reply toChartFXFundWSData() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-客户分析-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-应实收-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		Map json=new HashMap();
		json.putAll(param);
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("应收未收分析", titlesName, sqlsName));
		/********************************************************************/
		return new ReplyHtml(VM.html(_PATH + "graphFXFundWS.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name = { "报表管理", "超级表", "列表导出" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public ReplyAjax exportWSExcel(){
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
		new POIExcelUtil().expExcel_2007(m,"应实未收收统计"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx", "应收未收统计", titleName,sql_columns, service.fundWSList(m));
		return null;
	}
}
