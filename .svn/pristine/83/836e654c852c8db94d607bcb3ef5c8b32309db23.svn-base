package com.pqsoft.projectReport.action;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.projectReport.service.ProjectReportTableService;
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
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.POIExcelUtil;
import com.pqsoft.util.UtilChart;

public class ProjectReportTableAction  extends Action{

	private final String _PATH = "projectReportTable/";
	// **实现类服务
	ProjectReportTableService service = new ProjectReportTableService();

	//页面自定义显示列的下拉列表所需，同时也是sql所需
	private String[] titlesName = new String[] { "省份", "城市", "供应商名称", "项目编号", "客户名称", "业务类型",
			"申请时间", "批准日期", "批准状态", "签约日期", "合同编号", "客户经理", "合同总额" };
	private String[] sqlsName = new String[] { "PROVINCE_NAME", "CITY_NAME","SUPPLIERS_NAME", "PRO_CODE", "CUST_NAME", "PLATFORM_NAME"
			, "CREATE_TIME", "MEEING_TIME", "MEEING_NAME", "SIGNED_DATE","LEASE_CODE", "CLERK_NAME","TOTAL_PRICE" };
	
	//报表工具调用
	UtilChart utilChart=new UtilChart();


	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "项目统计", "项目统计报表" })
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-项目统计-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-项目-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		context.put("chartReport",utilChart.to_chartPie(_getParameters(), "报表-项目-统计类型", "项目统计", "projectReport.showGroupProjectType","1"));
		
		return new ReplyHtml(VM.html(_PATH+"graphTJProjectChart.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "项目统计", "项目分析报表" })
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply execute_FX() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-项目分析-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-项目-分析-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		
		
		Map json=_getParameters();
		//折线图
		json.put("REPORT_TYPE", "5");
		json.put("REPORT_DATE", "4");
		json.putAll(service.queryDateByProject());
		
		context.put("chartReport",new UtilChart().to_chartLine(json, "报表-项目-分析-统计类型", "项目分析", "projectReport.REPORT_X_MONTH,projectReport.PROJECT_REPORT_TYPE,projectReport.showGroupProjectFX","1"));
		
		return new ReplyHtml(VM.html(_PATH+"graphFXProjectChart.vm", context));
	}

	/**
	 * 进入新增客户图形报表页面
	 * 
	 * @return
	 * @author King 2014年7月30日
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "报表管理", "项目统计", "项目统计报表" })
	@SuppressWarnings("unchecked")
	public Reply toChartNewProject() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-项目统计-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-项目-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
//		context.put("chartReport",utilChart.to_chart(param, "报表-客户-统计类型", "客户统计", "clientReport.showGroupClientType","1"));

		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("项目统计", titlesName, sqlsName));
		/********************************************************************/
		
		return new ReplyHtml(VM.html(_PATH + "graphTJProject.vm", context));
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
			utilChart.delReportColumnByReportAndUser("项目统计", Security.getUser().getCode());;
			utilChart.insertReportColumnByReportAndUser("项目统计", Security.getUser().getCode(),param.get("COLUMN_NAMES"));
		}
		param.put("SQL_COLUMN", utilChart.getColumns(param.get("COLUMN_NAMES")+""));
		return new ReplyAjaxPage(service.queryPage(param));
	}

	/**
	 * 查询客户统计图表
	 * 
	 * @return
	 * @author King 2014年7月31日
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doChartProject() {
		String chart = null;
		Map<String, Object> param = _getParameters();
		JSONObject json=JSONObject.fromObject(param.get("param"));
		if("2".equals(json.get("REPORT_QZTX"))){
			//折线图
			//判断时间节点为全部还是时间段
			//如果为全部将其实日期和当前日期查出来
			//如果为时间段，判断系统中有输入起始日期和终止日期
			if(json!=null && json.get("REPORT_DATE")!=null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("1")){
				param.putAll(service.queryDateByProject());
			}
			else if(json!=null && json.get("REPORT_DATE")!=null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("6")){
				Map dateMap=service.queryDateByProject();
				if(json.get("START_TIME")==null || json.get("START_TIME").equals("")){
					json.put("START_TIME", dateMap.get("START_TIME"));
				}
				
				if(json.get("END_TIME")==null || json.get("END_TIME").equals("")){
					json.put("END_TIME", dateMap.get("END_TIME"));
				}
			}
			 chart=new UtilChart().to_chartLine(json, "报表-项目-分析-统计类型", "项目分析", "projectReport.REPORT_X_MONTH,projectReport.PROJECT_REPORT_TYPE,projectReport.showGroupProjectFX","1");
			
		}else{
			//饼状图
			chart=utilChart.to_chartPie(JSONObject.fromObject( _getParameters().get("param")), "报表-项目-统计类型", "项目统计", "projectReport.showGroupProjectType","1");
		}
		return new ReplyAjax(true, chart,null);
	}
	
	/**
	 * 查询客户统计图表
	 * 
	 * @return
	 * @author King 2014年7月31日
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doChartProjectDATA() {
		String chart = null;
		Map<String, Object> param = _getParameters();
		JSONObject json=JSONObject.fromObject(param.get("param"));
		if("2".equals(json.get("REPORT_QZTX"))){
			//折线图
			//判断时间节点为全部还是时间段
			//如果为全部将其实日期和当前日期查出来
			//如果为时间段，判断系统中有输入起始日期和终止日期
			if(json!=null && json.get("REPORT_DATE")!=null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("1")){
				param.putAll(service.queryDateByProject());
			}
			else if(json!=null && json.get("REPORT_DATE")!=null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("6")){
				Map dateMap=service.queryDateByProject();
				if(json.get("START_TIME")==null || json.get("START_TIME").equals("")){
					json.put("START_TIME", dateMap.get("START_TIME"));
				}
				
				if(json.get("END_TIME")==null || json.get("END_TIME").equals("")){
					json.put("END_TIME", dateMap.get("END_TIME"));
				}
			}
			 chart=new UtilChart().to_chartLine(json, "报表-项目-分析-统计类型", "项目分析", "projectReport.REPORT_X_MONTH,projectReport.PROJECT_REPORT_TYPE,projectReport.showGroupProjectFX","2");
			
		}else{
			//饼状图
			chart=utilChart.to_chartPie(JSONObject.fromObject( _getParameters().get("param")), "报表-项目-统计类型", "项目统计", "projectReport.showGroupProjectType","2");
		}
		return new ReplyAjax(true, chart,null);
	}

	/**
	 * 进入新增客户图形报表页面
	 * 
	 * @return
	 * @author King 2014年8月1日
	 */
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "报表管理", "项目报表", "项目分析报表" })
	@SuppressWarnings("unchecked")
	public Reply toChartFXProject() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-项目分析-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-项目-分析-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		Map json=new HashMap();
		json.putAll(param);
		json.put("REPORT_DATE", "4");//默认为本年
		json.put("REPORT_TYPE", "5");//默认为新增项目
		context.put("chartReport",new UtilChart().to_chartLine(json, "报表-项目-分析-统计类型", "项目分析", "projectReport.REPORT_X_MONTH,projectReport.PROJECT_REPORT_TYPE,projectReport.showGroupProjectFX","1"));
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("项目统计", titlesName, sqlsName));
		/********************************************************************/
		return new ReplyHtml(VM.html(_PATH + "graphFXProject.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "数据统计", "项目报表", "列表导出" })
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
		new POIExcelUtil().expExcel_2007(m,"项目统计"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx", "项目统计", titleName,sql_columns, service.projectList(m));
	
		return null;
	}

}
