package com.pqsoft.targets.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.secu.service.AuthService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.util.UTIL;
import com.pqsoft.targets.service.TargetsBaseService;
import com.pqsoft.util.UtilChart;

public class TargetsBaseAction extends Action{

	public VelocityContext context = new VelocityContext();
	Map<String, Object> m = null;
	private TargetsBaseService service=new TargetsBaseService();
	
	//页面自定义显示列的下拉列表所需，同时也是sql所需
	private String[] titlesName = new String[] { "区域", "客户名称", "项目编号","合同编号","行业", "客户经理", "实际利润" };
	private String[] titlesNameDun = new String[] { "区域", "客户名称", "项目编号","合同编号","行业", "客户经理", "实际逾期率" };
	private String[] titlesNameSYL = new String[] { "区域", "客户名称", "项目编号","合同编号","行业", "客户经理", "实际收益率" };
	private String[] sqlsName = new String[] { "PROVINCE_NAME","CUST_NAME", "PRO_CODE", "LEASE_CODE","FICATION_NAME", "CLERK_NAME","MONEY_VALUE"};
	
	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@aPermission(name = { "参数配置", "利润指标配置", "配置" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply ProfitTargets() {
		m=_getParameters();
		List targetsList = (List) new DataDictionaryMemcached().get("利润指标");
		context.put("targetsList", targetsList);
		if(m==null){
			m=new HashMap<String, Object>();
		}
		m.put("INDICATORS_TYPE", 1);
		m.put("TYPE", 1);//默认为1
		m.put("param", m);
		
		//查询当前日期本年起始日期(默认为本年)
		Map dateMap=service.queryDateType("1");
		m.putAll(dateMap);
		
		//查看该指标该类型配置的报表显示时间段
		Map mapBase=service.queryCharBase(m);
		if(mapBase!=null){
			m.putAll(mapBase);
		}
		
		context.put("param", m);
		
		return new ReplyHtml(VM.html("targets/profitTargets.vm", context));
	}
	
	@aPermission(name = { "参数配置", "逾期率指标", "配置" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply dunRateTargets() {
		m=_getParameters();
		List targetsList = (List) new DataDictionaryMemcached().get("逾期率指标");
		context.put("targetsList", targetsList);
		if(m==null){
			m=new HashMap<String, Object>();
		}
		m.put("INDICATORS_TYPE", 2);
		m.put("TYPE", 1);//默认为1
		m.put("param", m);
		
		//查询当前日期本年起始日期(默认为本年)
		Map dateMap=service.queryDateType("1");
		m.putAll(dateMap);
		
		//查看该指标该类型配置的报表显示时间段
		Map mapBase=service.queryCharBase(m);
		if(mapBase!=null){
			m.putAll(mapBase);
		}
		
		context.put("param", m);
		
		return new ReplyHtml(VM.html("targets/profitTargets.vm", context));
	}
	
	@aPermission(name = { "参数配置", "收益率指标", "配置" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply yieldRateTargets() {
		m=_getParameters();
		List targetsList = (List) new DataDictionaryMemcached().get("收益率指标");
		context.put("targetsList", targetsList);
		if(m==null){
			m=new HashMap<String, Object>();
		}
		m.put("INDICATORS_TYPE", 3);
		m.put("TYPE", 1);//默认为1
		m.put("param", m);
		
		//查询当前日期本年起始日期(默认为本年)
		Map dateMap=service.queryDateType("1");
		m.putAll(dateMap);
		
		//查看该指标该类型配置的报表显示时间段
		Map mapBase=service.queryCharBase(m);
		if(mapBase!=null){
			m.putAll(mapBase);
		}
		
		context.put("param", m);
		
		return new ReplyHtml(VM.html("targets/profitTargets.vm", context));
	}
	
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply getTypeList() { 
		m=_getParameters();
		context.put("list", service.getTypeList(m));
		return new ReplyHtml(VM.html("targets/targetsType.vm", context));
	}
	
	
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply getTargetsDetail() {
		m=_getParameters();
		context.put("Format", UTIL.FORMAT);
		context.put("list", service.getTargetsDetail(m));
		return new ReplyHtml(VM.html("targets/targetsDetail.vm", context));
	}
	
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply queryDateType(){
		m=_getParameters();
		String TIMETYPE=m.get("TIMETYPE").toString();
		Map dateMap=service.queryDateType(TIMETYPE);
		return new ReplyJson(JSONObject.fromObject(dateMap));
	}
	
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply addTargetsInfo() {
		m=_getParameters();
		JSONObject json = new JSONObject();
		int i = 0;
		try {
			//先判断该起始时间段和终止时间段 是否存在 ，如果存在则不能保存
			int num=service.queryDateName(m);
			if(num>0){
				json.put("flag", false);
				json.put("errorMsg", "起始时间"+m.get("START_DATE")+"至结束时间"+m.get("END_DATE")+"已经设置过标准值，不能重复设置！");
			}
			else{
				i = service.addTargetsInfo(m);
				if (i > 0) {
					json.put("flag", true);
					json.put("errorMsg", "添加成功！");
				} else {
					json.put("flag", false);
					json.put("errorMsg", "添加失败！");
				}
			}
			
		} catch (Exception e) {
			json.put("flag", false);
			json.put("errorMsg", "添加失败！");
		}
		return new ReplyJson(json);
	}
	
	
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply deleteDetail() {
		m=_getParameters();
		JSONObject json = new JSONObject();
		int i = 0;
		try {
			i = service.deleteDetail(m);
		} catch (Exception e) {
			json.put("flag", false);
			json.put("errorMsg", "删除失败！");
		}
		if (i > 0) {
			json.put("flag", true);
			json.put("errorMsg", "删除成功！");
		} else {
			json.put("flag", false);
			json.put("errorMsg", "删除失败！");
		}
		return new ReplyJson(json);
	}

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply updateTargetsInfo() {
		m=_getParameters();
		JSONObject json = new JSONObject();
		int i = 0;
		try {
			int num=service.queryDateName(m);
			int number=service.queryDateNameMap(m);
			
			if(num==0 || (num==1 && number==1)){
				i = service.updateTargetsInfo(m);
				if (i > 0) {
					json.put("flag", true);
					json.put("errorMsg", "修改成功！");
				} else {
					json.put("flag", false);
					json.put("errorMsg", "修改失败！");
				}
			}else{
				json.put("flag", false);
				json.put("errorMsg", "起始时间"+m.get("START_DATE")+"至结束时间"+m.get("END_DATE")+"已经设置过标准值，不能重复设置！");
			}
			
			
		} catch (Exception e) {
			json.put("flag", false);
			json.put("errorMsg", "修改失败！");
		}
		return new ReplyJson(json);
	}
	
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply updateBaseTimeType() {
		m=_getParameters();
		JSONObject json = new JSONObject();
		int i = 0;
		try {
			i = service.updateBaseTimeType(m);
		} catch (Exception e) {
			json.put("flag", false);
			json.put("errorMsg", "修改失败！");
		}
		if (i > 0) {
			json.put("flag", true);
			json.put("errorMsg", "修改成功！");
		} else {
			json.put("flag", false);
			json.put("errorMsg", "修改失败！");
		}
		return new ReplyJson(json);
	}
	
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply queryBaseTimeType(){
		m=_getParameters();
		Map dateMap=service.queryCharBase(m);
		if(dateMap == null){
			dateMap=new HashMap();
			dateMap.put("BASE_TIMETYPE", 100);
		}
		return new ReplyJson(JSONObject.fromObject(dateMap));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "利润指标对比", "报表分析" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	public Reply queryCharProfit() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> m=_getParameters();
		List targetsList = (List) new DataDictionaryMemcached().get("利润指标");
		context.put("targetsList", targetsList);
		m.put("INDICATORS_TYPE", "1");
		//默认查区域
		m.put("TYPE", "1");
		//默认查本年
		m.put("TIMETYPE", "1");
		String TYPE=m.get("TYPE")+"";
		String TIMETYPE=m.get("TIMETYPE")+"";
		
		
		String MSG="";
		//查询该时间该指标类型在数据库有数据吗
		Map mapTimeType=service.queryBaseTimeTypeInfoIsF(m);
		if(mapTimeType !=null && mapTimeType.get("START_DATE")!=null && mapTimeType.get("END_DATE")!=null){
			String START_DATE=mapTimeType.get("START_DATE")+"";
			String END_DATE=mapTimeType.get("END_DATE")+"";
			UtilChart utilChart=new UtilChart();
			m.put("REPORT_TYPE",m.get("TIMETYPE")); 
			m.put("Y_UNIT", "元");
			context.put("chartReport",utilChart.to_chartBar(m, "利润指标", START_DATE+"到"+END_DATE+"利润指标对比(单位：元)", "targets.queryProfitX,targets.queryProfitTypeY,targets.queryProfitY", "1"));
		}else{
			Map mapDate=service.queryDateType(m.get("TIMETYPE")+"");
			String START_DATE=mapDate.get("START_DATE")+"";
			String END_DATE=mapDate.get("END_DATE")+"";
			for(int i=0;i<targetsList.size();i++){
				Map maptargets=(Map)targetsList.get(i);
				if(TYPE.equals(maptargets.get("CODE"))){
					MSG="没有满足从"+START_DATE+"到"+END_DATE+"的"+maptargets.get("FLAG")+"指标报表。请联系管理员配置！";
				}
			}
			m.put("MSG", MSG);
		}
		m.put("TABSHOW", "利润指标分析");
		context.put("param", m);
		
		return new ReplyHtml(VM.html("targets/targetsChars.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "逾期率指标对比", "报表分析" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	public Reply queryCharDunRate() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> m=_getParameters();
		List targetsList = (List) new DataDictionaryMemcached().get("逾期率指标");
		context.put("targetsList", targetsList);
		m.put("INDICATORS_TYPE", "2");
		//默认查区域
		m.put("TYPE", "1");
		//默认查本年
		m.put("TIMETYPE", "1");
		String TYPE=m.get("TYPE")+"";
		String TIMETYPE=m.get("TIMETYPE")+"";
		
		
		String MSG="";
		//查询该时间该指标类型在数据库有数据吗
		Map mapTimeType=service.queryBaseTimeTypeInfoIsF(m);
		if(mapTimeType !=null && mapTimeType.get("START_DATE")!=null && mapTimeType.get("END_DATE")!=null){
			String START_DATE=mapTimeType.get("START_DATE")+"";
			String END_DATE=mapTimeType.get("END_DATE")+"";
			UtilChart utilChart=new UtilChart();
			m.put("REPORT_TYPE",m.get("TIMETYPE")); 
			m.put("Y_UNIT", "%");
			context.put("chartReport",utilChart.to_chartBar(m, "逾期率指标", START_DATE+"到"+END_DATE+"逾期率指标对比(单位：%)", "targets.queryProfitX,targets.queryDunRateTypeY,targets.queryDunRateY", "1"));
		}else{
			Map mapDate=service.queryDateType(m.get("TIMETYPE")+"");
			String START_DATE=mapDate.get("START_DATE")+"";
			String END_DATE=mapDate.get("END_DATE")+"";
			for(int i=0;i<targetsList.size();i++){
				Map maptargets=(Map)targetsList.get(i);
				if(TYPE.equals(maptargets.get("CODE"))){
					MSG="没有满足从"+START_DATE+"到"+END_DATE+"的"+maptargets.get("FLAG")+"指标报表。请联系管理员配置！";
				}
			}
			m.put("MSG", MSG);
		}
		m.put("TABSHOW", "逾期率指标分析");
		context.put("param", m);
		
		return new ReplyHtml(VM.html("targets/targetsChars.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "报表管理", "收益率指标对比", "报表分析" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	public Reply queryCharYieldRate() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> m=_getParameters();
		List targetsList = (List) new DataDictionaryMemcached().get("收益率指标");
		context.put("targetsList", targetsList);
		m.put("INDICATORS_TYPE", "3");
		//默认查区域
		m.put("TYPE", "1");
		//默认查本年
		m.put("TIMETYPE", "1");
		String TYPE=m.get("TYPE")+"";
		String TIMETYPE=m.get("TIMETYPE")+"";
		
		
		String MSG="";
		//查询该时间该指标类型在数据库有数据吗
		Map mapTimeType=service.queryBaseTimeTypeInfoIsF(m);
		if(mapTimeType !=null && mapTimeType.get("START_DATE")!=null && mapTimeType.get("END_DATE")!=null){
			String START_DATE=mapTimeType.get("START_DATE")+"";
			String END_DATE=mapTimeType.get("END_DATE")+"";
			UtilChart utilChart=new UtilChart();
			m.put("REPORT_TYPE",m.get("TIMETYPE")); 
			m.put("Y_UNIT", "%");
			context.put("chartReport",utilChart.to_chartBar(m, "收益率指标", START_DATE+"到"+END_DATE+"收益率指标对比(单位：%)", "targets.queryProfitX,targets.queryYieldRateTypeY,targets.queryYieldRateY", "1"));
		}else{
			Map mapDate=service.queryDateType(m.get("TIMETYPE")+"");
			String START_DATE=mapDate.get("START_DATE")+"";
			String END_DATE=mapDate.get("END_DATE")+"";
			for(int i=0;i<targetsList.size();i++){
				Map maptargets=(Map)targetsList.get(i);
				if(TYPE.equals(maptargets.get("CODE"))){
					MSG="没有满足从"+START_DATE+"到"+END_DATE+"的"+maptargets.get("FLAG")+"指标报表。请联系管理员配置！";
				}
			}
			m.put("MSG", MSG);
		}
		m.put("TABSHOW", "收益率指标分析");
		context.put("param", m);
		
		return new ReplyHtml(VM.html("targets/targetsChars.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	public Reply doCharProfit() {
		
		VelocityContext context = new VelocityContext();
		Map<String,Object> param=_getParameters();
		JSONObject m=JSONObject.fromObject(param.get("param"));
		String INDICATORS_TYPE=m.get("INDICATORS_TYPE")+"";
		List targetsList =new ArrayList();
		if(INDICATORS_TYPE.equals("1")){
			targetsList = (List) new DataDictionaryMemcached().get("利润指标");
		}else if(INDICATORS_TYPE.equals("2")){
			targetsList = (List) new DataDictionaryMemcached().get("逾期率指标");
		}else if(INDICATORS_TYPE.equals("3")){
			targetsList = (List) new DataDictionaryMemcached().get("收益率指标");
		}
		context.put("targetsList", targetsList);
		
		String TYPE=m.get("TYPE")+"";
		String TIMETYPE=m.get("TIMETYPE")+"";
		
		//查询该时间该指标类型在数据库有数据吗
		Map mapTimeType=service.queryBaseTimeTypeInfoIsF(m);
		if(mapTimeType !=null && mapTimeType.get("START_DATE")!=null && mapTimeType.get("END_DATE")!=null){
			String chart = null;
			String START_DATE=mapTimeType.get("START_DATE")+"";
			String END_DATE=mapTimeType.get("END_DATE")+"";
			UtilChart utilChart=new UtilChart();
			if(INDICATORS_TYPE.equals("1")){
				m.put("Y_UNIT", "元");
				chart=utilChart.to_chartBar(m, "利润指标", START_DATE+"到"+END_DATE+"利润指标对比(单位：元)", "targets.queryProfitX,targets.queryProfitTypeY,targets.queryProfitY", "1");
			}else if(INDICATORS_TYPE.equals("2")){
				m.put("Y_UNIT", "%");
				chart=utilChart.to_chartBar(m, "逾期率指标", START_DATE+"到"+END_DATE+"逾期率指标对比(单位：%)", "targets.queryProfitX,targets.queryDunRateTypeY,targets.queryDunRateY", "1");
			}else if(INDICATORS_TYPE.equals("3")){
				m.put("Y_UNIT", "%");
				chart=utilChart.to_chartBar(m, "收益率指标", START_DATE+"到"+END_DATE+"收益率指标对比(单位：%)", "targets.queryProfitX,targets.queryYieldRateTypeY,targets.queryYieldRateY", "1");
			}
			
			return new ReplyAjax(true, chart,null);
		}else{
			String MSG="";
			Map mapDate=service.queryDateType(m.get("TIMETYPE")+"");
			String START_DATE=mapDate.get("START_DATE")+"";
			String END_DATE=mapDate.get("END_DATE")+"";
			for(int i=0;i<targetsList.size();i++){
				Map maptargets=(Map)targetsList.get(i);
				if(TYPE.equals(maptargets.get("CODE"))){
					MSG="没有满足从"+START_DATE+"到"+END_DATE+"的"+maptargets.get("FLAG")+"指标报表。请联系管理员配置！";
				}
			}
			return new ReplyAjax(false, null,MSG);
		}
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	public Reply toChartTargetsData() {
		Map<String, Object> param = _getParameters();
		String INDICATORS_TYPE=param.get("INDICATORS_TYPE")+"";
		VelocityContext context = new VelocityContext();
		List targetsList =new ArrayList();
		if(INDICATORS_TYPE.equals("1")){
			targetsList = (List) new DataDictionaryMemcached().get("利润指标");
		}else if(INDICATORS_TYPE.equals("2")){
			targetsList = (List) new DataDictionaryMemcached().get("逾期率指标");
		}else if(INDICATORS_TYPE.equals("3")){
			targetsList = (List) new DataDictionaryMemcached().get("收益率指标");
		}
		context.put("targetsList", targetsList);
		
		param.put("TIMETYPE", "1");//默认为本年
		param.put("TYPE", "1");//默认为区域
		context.put("param", param);
		/********************************控制自定义显示列********************/
		if(INDICATORS_TYPE.equals("1")){
			context.put("columnInit", new UtilChart().parseColumnInit("利润指标统计", titlesName, sqlsName));
		}else if(INDICATORS_TYPE.equals("2")){
			context.put("columnInit", new UtilChart().parseColumnInit("逾期率指标统计", titlesNameDun, sqlsName));
		}else if(INDICATORS_TYPE.equals("3")){
			context.put("columnInit", new UtilChart().parseColumnInit("收益率指标统计", titlesNameSYL, sqlsName));
		}
		/********************************************************************/
		return new ReplyHtml(VM.html("targets/graphTargetsData.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	public Reply doTableTargetsShow() {
		Map<String, Object> param = _getParameters();
		param.put("USER_CODE", Security.getUser().getCode());
		JSONObject json = JSONObject.fromObject(param.get("searchParams"));
		param.putAll(json);
		String INDICATORS_TYPE=param.get("INDICATORS_TYPE")+"";

		//判断是否定制显示字段 如果配置则保存配置并使用 否则查询配置历史
		if(param.get("COLUMN_NAMES") != null){
			if(INDICATORS_TYPE.equals("1")){
				new UtilChart().delReportColumnByReportAndUser("利润指标统计", Security.getUser().getCode());;
				new UtilChart().insertReportColumnByReportAndUser("利润指标统计", Security.getUser().getCode(),param.get("COLUMN_NAMES"));
			}else if(INDICATORS_TYPE.equals("2")){
				new UtilChart().delReportColumnByReportAndUser("逾期率指标统计", Security.getUser().getCode());;
				new UtilChart().insertReportColumnByReportAndUser("逾期率指标统计", Security.getUser().getCode(),param.get("COLUMN_NAMES"));
			}else if(INDICATORS_TYPE.equals("3")){
				new UtilChart().delReportColumnByReportAndUser("收益率指标统计", Security.getUser().getCode());;
				new UtilChart().insertReportColumnByReportAndUser("收益率指标统计", Security.getUser().getCode(),param.get("COLUMN_NAMES"));
			}
			
		}
		param.put("SQL_COLUMN", new UtilChart().getColumns(param.get("COLUMN_NAMES")+""));
		return new ReplyAjaxPage(service.queryPage(param));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	public Reply doChartTargetsData() {
		String chart = null;
		Map<String, Object> param = _getParameters();
		JSONObject json=JSONObject.fromObject(param.get("param"));
		
		String INDICATORS_TYPE=json.get("INDICATORS_TYPE")+"";
		
		Map mapDate=service.queryDateType(json.get("TIMETYPE")+"");
		String START_DATE=mapDate.get("START_DATE")+"";
		String END_DATE=mapDate.get("END_DATE")+"";
		if(INDICATORS_TYPE.equals("1")){
			chart=new UtilChart().to_chartBarTS(json, "利润指标", START_DATE+"到"+END_DATE+"利润指标对比(单位：元)", "targets.queryProfitX,targets.queryProfitTypeY,targets.queryProfitY", "2");
		}else if(INDICATORS_TYPE.equals("2")){
			chart=new UtilChart().to_chartBarTS(json, "逾期率指标", START_DATE+"到"+END_DATE+"逾期率指标对比(单位：%)", "targets.queryProfitX,targets.queryDunRateTypeY,targets.queryDunRateY", "2");
		}else if(INDICATORS_TYPE.equals("3")){
			chart=new UtilChart().to_chartBarTS(json, "收益率指标", START_DATE+"到"+END_DATE+"收益率指标对比(单位：%)", "targets.queryProfitX,targets.queryYieldRateTypeY,targets.queryYieldRateY", "2");
		}
		else{
			chart=new UtilChart().to_chartBarTS(json, "利润指标", START_DATE+"到"+END_DATE+"利润指标对比(单位：元)", "targets.queryProfitX,targets.queryProfitTypeY,targets.queryProfitY", "2");
		}
		
		return new ReplyAjax(true, chart,null);
	}
}
