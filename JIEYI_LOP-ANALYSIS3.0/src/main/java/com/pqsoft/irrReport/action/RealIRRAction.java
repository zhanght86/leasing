package com.pqsoft.irrReport.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.irrReport.service.RealIRRService;
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
import com.pqsoft.util.UtilChart;

public class RealIRRAction extends Action {
	/*******实际收益率管理****@auth: king 2014年9月1日 *************************/

	private String path="irrReportTable/";
	private RealIRRService service=new RealIRRService();
	
	//页面自定义显示列的下拉列表所需，同时也是sql所需
	private String[] titlesName = new String[] { "客户名称","项目编号","项目名称","合同编号","业务类型",
			"行业类型","客户经理", "实际收益率"};
	private String[] sqlsName = new String[] { "CLIENT_NAME","PROJECT_CODE","PROJECT_NAME","LEASE_CODE","PLATFORM_NAME",
			"INDUSTRY_FICATION","CLECK_NAME","REAL_IRR"};
	
	//报表工具调用
	UtilChart utilChart=new UtilChart();
	
	/**
	 * 进入收益率菜单
	 */
	@Override
	@aAuth(type=aAuthType.USER)
	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
	@aPermission(name={"报表管理", "实际收益率统计"})
	public Reply execute() {
		VelocityContext context=new VelocityContext();
		return new ReplyHtml(VM.html(path+"MgRealIRR.vm", context));
	}
	
	/**
	 * 查询收益率列表
	 * @return
	 * @author King 2014年9月2日
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuthType.LOGIN)
	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
//	@aPermission(name={"项目管理", "实际收益率","列表"})
	public Reply doShowRealIRR(){
		Map<String,Object> param=_getParameters();
		param.putAll(JSONObject.fromObject(param.get("param")));
		return new ReplyAjaxPage(service.doShowRealIRRMg(param));
	}
	
	/**
	 * 查看收益率图标分析
	 * @return
	 * @author King 2014年9月2日
	 */
	@aAuth(type=aAuthType.LOGIN)
	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
//	@aPermission(name={"项目管理", "实际收益率","列表"})
	public Reply toIrrLineChart(){
		Map<String,Object> param=_getParameters();
		VelocityContext context=new VelocityContext();
		context.put("chartReport",utilChart.createLineChart("实际收益率分析",service.queryIRRXValue(param),service.queryIRRDataList(param)));
		return new ReplyHtml(VM.html(path+"graphFXRealIRR.vm", context)); 
	}
	
	/**
	 * 刷实际收益率
	 * 
	 * @author King 2014年9月2日
	 */
	@aAuth(type=aAuthType.LOGIN)
	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
//	@aPermission(name={"项目管理", "实际收益率","列表"})
	public void refreshIRRForProject(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		service.refreshIRRForProject(sdf.format(new Date()));
	}
	
	/**
	 * 查看成本核算
	 * @return
	 * @author King 2014年11月3日
	 */
	@aAuth(type=aAuthType.LOGIN)
	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
//	@aPermission(name={"项目管理", "实际收益率","列表"})
	public Reply toIrrLineData(){
		Map<String,Object> param=_getParameters();
		VelocityContext context=new VelocityContext();
		context.put("param", param);
		context.put("sumList", service.querycbgsSum(param));
		context.put("detailList", service.querycbgsDetail(param));
		return new ReplyHtml(VM.html(path+"Costing.vm", context));
	}
	
	/**
	 * 进入收益率平均值分析列表
	 * @return
	 * @author King 2014年11月21日
	 */
	@aAuth(type=aAuthType.USER)
	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
	@aPermission(name={"报表管理", "收益率分析","列表显示"})
	public Reply toChartFxIrrAvg(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("REPORT_DATE_list", service.queryDateList());
		context.put("REPORT_TYPE_list", new DataDictionaryMemcached().get("报表-收益率-统计类型"));
		param.put("REPORT_DATE", "4");
		context.put("chartReport", utilChart.to_chartLine(param, "报表-收益率-统计类型", "收益率(平均)分析", "realIRR.REPORT_X_MONTH,realIRR.IRR_REPORT_TYPE,realIRR.showGroupIrrFX","1"));
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"graphFXRealIRRAvg.vm", context));
	}
	
	/**
	 * 查看实际收益率分析图形列表
	 * @return
	 * @author King 2014年11月21日
	 */
	@aAuth(type=aAuthType.USER)
	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
	@aPermission(name={"报表管理", "收益率分析","列表显示"})
	public Reply toChartFxIrr(){
		String chart = null;
	    chart=new UtilChart().to_chartLine(JSONObject.fromObject(_getParameters().get("param")), "报表-收益率-统计类型", "收益率(平均)分析", "realIRR.REPORT_X_MONTH,realIRR.IRR_REPORT_TYPE,realIRR.showGroupIrrFX","1");
		return new ReplyAjax(true, chart,null);
	}
	
	/**
	 * 查看分析数据
	 * @return
	 * @author King 2014年11月21日
	 */
	@aAuth(type=aAuthType.LOGIN)
	@aDev(code="170053",email="jinfu@pqsoft.cn",name="King")
	@aPermission(name={"项目管理", "收益率分析","列表显示"})
	public Reply toChartFXIRRData() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("REPORT_DATE_list", service.queryDateList());
		context.put("REPORT_TYPE_list", new DataDictionaryMemcached().get("报表-收益率-统计类型"));
		Map<String,Object> json=new HashMap<String,Object>();
		json.putAll(param);
		/********************************控制自定义显示列********************/
		context.put("columnInit", utilChart.parseColumnInit("收益率分析", titlesName, sqlsName));
		/********************************************************************/
		return new ReplyHtml(VM.html(path + "graphFXIRRDATA.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doIRRDataShow() {
		Map<String, Object> param = _getParameters();
		param.put("USER_CODE", Security.getUser().getCode());
		JSONObject json = JSONObject.fromObject(param.get("searchParams"));
		param.putAll(json);

		//判断是否定制显示字段 如果配置则保存配置并使用 否则查询配置历史
		if(param.get("COLUMN_NAMES") != null){
			utilChart.delReportColumnByReportAndUser("收益率统计", Security.getUser().getCode());;
			utilChart.insertReportColumnByReportAndUser("收益率统计", Security.getUser().getCode(),param.get("COLUMN_NAMES"));
		}
		param.put("SQL_COLUMN", utilChart.getColumns(param.get("COLUMN_NAMES")+""));
		return new ReplyAjaxPage(service.queryIRRPage(param));
	}
	
	/**
	 * 查询应实收统计图表
	 * 
	 * @return
	 * @author King 2014年7月31日
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doChartIRRData() {
		String chart = null;
		Map<String, Object> param = _getParameters();
		JSONObject json=JSONObject.fromObject(param.get("param"));
		chart=new UtilChart().to_chartLine(json, "报表-收益率-统计类型", "收益率(平均)分析", "realIRR.REPORT_X_MONTH,realIRR.IRR_REPORT_TYPE,realIRR.showGroupIrrFX","2");
		return new ReplyAjax(true, chart,null);
	}
	
}
