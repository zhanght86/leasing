package com.pqsoft.leaseReport.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.sf.json.JSONObject;
import org.apache.velocity.VelocityContext;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.leaseReport.service.LeaseReportTableService;
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

/******* 租赁物抵押报表****@auth: fuyulong 2014年8月04日 *************************/
public class LeaseReportTableYjyAction extends Action {
	
	private final String _PATH = "leaseYjyReprotTable/";
	// **实现类服务
	LeaseReportTableService service = new LeaseReportTableService();

	//页面自定义显示列的下拉列表所需，同时也是sql所需
	private String[] titlesName = new String[] { "状态", "客户名称", "合同编号", "支付表编号", "抵押期限", "到期提醒", "已解押原因" };
	private String[] sqlsName = new String[] { "STATE", "CUST_NAME", "LEASE_CODE", "PAYLIST_CODE", "MORTGAGE_TERM_DATE","DQTX", "YY" };
	
	//报表工具调用
	UtilChart utilChart=new UtilChart();

	@SuppressWarnings("unchecked")
	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "租赁物抵押报表", "租赁物已解押统计" })
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-租赁物抵押统计-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-租赁物抵押-分析-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		Map<String,Object> param = _getParameters();
		param.put("DATA_TYPE1", "YJY");
		context.put("chartReport", utilChart.to_chartPie(param, "报表-租赁物抵押-分析-统计类型", "已解押统计", "leaseReport.showGroupLeaseType", "1"));
		return new ReplyHtml(VM.html(_PATH+"graphTJLeaseChart.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "租赁物抵押报表", "租赁物已解押分析" })
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply yjyFX() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-租赁物抵押分析-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-租赁物抵押-分析-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		Map json=new HashMap();
		json.putAll(param);
		json.put("REPORT_DATE", "4");//默认为年
		json.put("REPORT_TYPE", "1");//默认项目区域
		json.put("DATA_TYPE1", "YJY");
		context.put("chartReport", new UtilChart().to_chartLine(json, "报表-租赁物抵押-分析-统计类型", "已解押分析", "leaseReport.REPORT_X_MONTH,leaseReport.LEASE_REPORT_TYPE,leaseReport.showGroupLeaseFX","1"));
		return new ReplyHtml(VM.html(_PATH+"graphFXLeaseChart.vm", context));
	}

	/**
	 * 进入已解押图形报表页面
	 * @param
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2014-8-4  下午02:07:42
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	@aPermission(name = { "数据统计", "租赁物抵押报表", "已解押统计" })
	@SuppressWarnings("unchecked")
	public Reply toChartLease() {
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-租赁物抵押统计-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-租赁物抵押-分析-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("已解押统计", titlesName, sqlsName));
		/********************************************************************/
		
		return new ReplyHtml(VM.html(_PATH + "graphTJLease.vm", context));
	}
	
	/**
	 * 进入租赁物抵押图形报表页面
	 * @param
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2014-8-4  下午02:26:54
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	@aPermission(name = { "数据统计", "租赁物抵押报表", "已解押分析" })
	@SuppressWarnings("unchecked")
	public Reply toChartFXLease() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		List<Object> REPORT_DATE_list = (List<Object>) new DataDictionaryMemcached().get("报表-租赁物抵押分析-时间-区间");
		context.put("REPORT_DATE_list", REPORT_DATE_list);
		List<Object> REPORT_TYPE_list = (List<Object>) new DataDictionaryMemcached().get("报表-租赁物抵押-分析-统计类型");
		context.put("REPORT_TYPE_list", REPORT_TYPE_list);
		Map json=new HashMap();
		json.putAll(param);
		json.put("REPORT_DATE", "4");//默认为年
		json.put("REPORT_TYPE", "1");//默认项目区域
		json.put("DATA_TYPE1", "YJY");
		context.put("chartReport",new UtilChart().to_chartLine(json, "报表-租赁物抵押-分析-统计类型", "已解押分析", "leaseReport.REPORT_X_MONTH,leaseReport.LEASE_REPORT_TYPE,leaseReport.showGroupLeaseFX","1"));
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("已解押统计", titlesName, sqlsName));
		/********************************************************************/
		return new ReplyHtml(VM.html(_PATH + "graphFXLease.vm", context));
	}

	/**
	 * 查询已解押统计图表
	 * @param
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2014-8-4  下午03:20:16
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doChartLease() {
		String chart = null;
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("param"));
		if ("2".equals(json.get("REPORT_QZTX"))) {
			// 折线图
			// 判断时间节点为全部还是时间段
			// 如果为全部将其实日期和当前日期查出来
			// 如果为时间段，判断系统中有输入起始日期和终止日期
			if (json != null && json.get("REPORT_DATE") != null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("1")) {
				param.putAll(service.queryDateByLease());
			} else if (json != null && json.get("REPORT_DATE") != null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("6")) {
				Map<String, Object> dateMap = service.queryDateByLease();
				if (json.get("START_TIME") == null || json.get("START_TIME").equals("")) {
					json.put("START_TIME", dateMap.get("START_TIME"));
				}

				if (json.get("END_TIME") == null || json.get("END_TIME").equals("")) {
					json.put("END_TIME", dateMap.get("END_TIME"));
				}
			}
			json.put("DATA_TYPE1", "YJY");
			chart = new UtilChart().to_chartLine(json, "报表-租赁物抵押-分析-统计类型", "已解押分析", "leaseReport.REPORT_X_MONTH,leaseReport.LEASE_REPORT_TYPE,leaseReport.showGroupLeaseFX", "1");

		} else {
			// 饼状图
			JSONObject param1 = JSONObject.fromObject(_getParameters().get("param"));
			param1.put("DATA_TYPE1", "YJY");
			chart = utilChart.to_chartPie(param1, "报表-租赁物抵押-分析-统计类型", "已解押统计", "leaseReport.showGroupLeaseType", "1");
		}
		return new ReplyAjax(true, chart, null);
	}
	
	/**
	 * 查询已解押统计图表
	 * @param
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2014-8-4  下午03:20:16
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doChartLeaseData() {
		String chart = null;
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("param"));
		if ("2".equals(json.get("REPORT_QZTX"))) {
			// 折线图
			// 判断时间节点为全部还是时间段
			// 如果为全部将其实日期和当前日期查出来
			// 如果为时间段，判断系统中有输入起始日期和终止日期
			if (json != null && json.get("REPORT_DATE") != null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("1")) {
				param.putAll(service.queryDateByLease());
			} else if (json != null && json.get("REPORT_DATE") != null && !json.get("REPORT_DATE").equals("") && json.get("REPORT_DATE").equals("6")) {
				Map<String, Object> dateMap = service.queryDateByLease();
				if (json.get("START_TIME") == null || json.get("START_TIME").equals("")) {
					json.put("START_TIME", dateMap.get("START_TIME"));
				}
				
				if (json.get("END_TIME") == null || json.get("END_TIME").equals("")) {
					json.put("END_TIME", dateMap.get("END_TIME"));
				}
			}
			json.put("DATA_TYPE1", "YJY");
			chart = new UtilChart().to_chartLine(json, "报表-租赁物抵押-分析-统计类型", "已解押分析", "leaseReport.REPORT_X_MONTH,leaseReport.LEASE_REPORT_TYPE,leaseReport.showGroupLeaseFX", "2");
			
		} else {
			// 饼状图
			JSONObject param1 = JSONObject.fromObject(_getParameters().get("param"));
			param1.put("DATA_TYPE1", "YJY");
			chart = utilChart.to_chartPie(param1, "报表-租赁物抵押-分析-统计类型", "已解押统计", "leaseReport.showGroupLeaseType", "2");
		}
		return new ReplyAjax(true, chart, null);
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "数据统计", "超级表", "列表导出" })
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
		m.put("DATA_TYPE1", "YJY");
		new POIExcelUtil().expExcel_2007(m,"已解押统计"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx", "已解押统计", titleName,sql_columns, service.leaseList(m));
	
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doTableShow() {
		Map<String, Object> param = _getParameters();
		param.put("USER_CODE", Security.getUser().getCode());
		JSONObject json = JSONObject.fromObject(param.get("searchParams"));
		param.putAll(json);
		//判断是否定制显示字段 如果配置则保存配置并使用 否则查询配置历史
		if(param.get("COLUMN_NAMES") != null){
			utilChart.delReportColumnByReportAndUser("已解押统计", Security.getUser().getCode());;
			utilChart.insertReportColumnByReportAndUser("已解押统计", Security.getUser().getCode(),param.get("COLUMN_NAMES"));
		}
		param.put("SQL_COLUMN", utilChart.getColumns(param.get("COLUMN_NAMES")+""));
		param.put("DATA_TYPE1","YJY");
		return new ReplyAjaxPage(service.queryPage(param));
	}

}
