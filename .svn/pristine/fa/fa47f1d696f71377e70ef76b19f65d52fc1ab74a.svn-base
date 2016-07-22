package com.pqsoft.litigation_case.action;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.litigation_case.service.litigationCaseService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class litigationCaseAction extends Action{
	
	public VelocityContext context = new VelocityContext();
	litigationCaseService service=new litigationCaseService();
	
	@Override
	public Reply execute() {
		return null;
	}
	
	@aPermission(name = { "资产管理", "诉讼管理", "列表显示" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply litigation_case_OP_Msg() {
		context.put("param", _getParameters());
		return new ReplyHtml(VM.html("litigation_case/litigation_case_OP_MAG.vm", context)).addOp(new OpLog("资产管理", "诉讼管理", "诉讼查询错误"));
	}

	@aPermission(name = { "资产管理", "诉讼管理", "列表显示" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply litigation_case_OP_AJAX() {
		Map<String, Object> param = _getParameters();
		Page page = service.query_project_litigation_MG(param);
		return new ReplyAjaxPage(page);
	}
	
	@aPermission(name = { "资产管理", "诉讼管理", "诉讼查询", "添加案例" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply queryCreateFormAjax() {
		Map map = _getParameters();
		if (map == null) {
			map = new HashMap();
		}
		Map mapDate=service.queryCreateFormAjax(map);
		return new ReplyAjax(JSONArray.fromObject(mapDate)).addOp(new OpLog("资产管理", "诉讼管理", "诉讼查询错误"));
	}
	
	@aPermission(name = { "资产管理", "诉讼管理", "诉讼查询", "添加案例" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply createLitigation() {
		Map<String, Object> param = _getParameters();
		int num=service.createLitigation(param);
		boolean flag=false;
		if(num>0)
		{
			flag=true;
		}
		return new ReplyAjax(flag,null,null).addOp(new OpLog("资产管理", "诉讼管理", "添加案例-保存"));
	}
	
	@aPermission(name = { "资产管理", "诉讼管理", "案件管理","列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply litigation_Self_Mg(){
		VelocityContext context = new VelocityContext();
		context.put("statusList", new DataDictionaryMemcached().get("诉讼状态"));
		context.put("resultList", new DataDictionaryMemcached().get("诉讼结果"));
		return new ReplyHtml(VM.html("litigation_case/litigation_Self_MG.vm", context));
	}

	@aPermission(name = { "资产管理", "诉讼管理", "案件管理","列表" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply litigation_Self_Mg_AJAX() {
		Map<String, Object> m = _getParameters();
		
		if (m.containsKey("param")) {
			m.putAll(JSONObject.fromObject(m.get("param")));
		}
		if(m.get("CASE_TYPE")==null)
		{
			m.put("CASE_TYPE", 0);
		}
		Page page = service.litigation_Self_Mg(m);
		return new ReplyAjaxPage(page);
	}
	
	@aPermission(name = { "资产管理", "诉讼管理", "案件管理","过程记录" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply litiCreateForm(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> m = _getParameters();
		context.put("param", m);
		context.put("statusList", new DataDictionaryMemcached().get("诉讼状态"));
		context.put("resultList", new DataDictionaryMemcached().get("诉讼结果"));
		return new ReplyHtml(VM.html("litigation_case/litiCreateForm.vm", context));
	}
	
	@aPermission(name = { "资产管理", "诉讼管理", "案件管理","过程记录" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply saveLitiCreateApp() {
		// TODO King 确定文件存放位置
		Map<String, Object> param = _getParametersIO(null, null, null);
		String id = service.saveLitiCreateApp(param);
		return new ReplyAjax(true);
	}
	
	@aPermission(name = { "资产管理", "诉讼管理", "案件管理","明细查看" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply doShowDetailList(){
		Map<String,Object> param=_getParameters();
		VelocityContext context=new VelocityContext();
		context.put("litiList", service.doShowDetailList(param));
		return new ReplyHtml(VM.html("litigation_case/litiList.vm", context));
	}
	
	@aPermission(name = { "资产管理", "诉讼管理", "案件管理","明细查看" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply litiSelfView(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> m = _getParameters();
		m=service.queryDetailView(m);
		context.put("param", m);
		return new ReplyHtml(VM.html("litigation_case/litiSelfView.vm", context));
	}
	
	@aPermission(name = { "资产管理", "诉讼管理", "案件管理","添加社会案例" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply createLiteSHCase(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> m = _getParameters();
		context.put("param", m);
		context.put("resultList", new DataDictionaryMemcached().get("诉讼结果"));
		return new ReplyHtml(VM.html("litigation_case/liteCreateSHForm.vm", context));
	}
	
	@aPermission(name = { "资产管理", "诉讼管理", "案件管理","添加社会案例" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply saveLitiCreateS() {
		// TODO King 确定文件存放位置
		Map<String, Object> param = _getParametersIO(null, null, null);
		String id = service.saveLitiCreateS(param);
		return new ReplyAjax(true);
	}
	
	@aPermission(name = { "资产管理", "诉讼管理", "案件管理","修改社会案例"})
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply saveLitiUpdateS() {
		// TODO King 确定文件存放位置
		Map<String, Object> param = _getParametersIO(null, null, null);
		String id = service.saveLitiUpdateS(param);
		return new ReplyAjax(true);
	}
	
	@aPermission(name = { "资产管理", "诉讼管理", "案件管理","看看社会案例" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply queryViewS(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> m = _getParameters();
		m=service.queryViewS(m);
		context.put("param", m);
		return new ReplyHtml(VM.html("litigation_case/litiSHView.vm", context));
	}
	
	@aPermission(name = { "资产管理", "诉讼管理", "案件管理","修改社会案例" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply queryUpdateS(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> m = _getParameters();
		m=service.queryViewS(m);
		context.put("param", m);
		context.put("resultList", new DataDictionaryMemcached().get("诉讼结果"));
		return new ReplyHtml(VM.html("litigation_case/litiSHUpdate.vm", context));
	}
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170051",email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "资产管理", "诉讼管理", "案例下载"})
	public Reply downLoadRecordFile() {
		Map map = _getParameters();
		return new ReplyFile(new File(map.get("file_url") + ""), map.get("file_name") + "").addOp(new OpLog("资产管理", "诉讼管理-案例下载", "下载错误"));
	}

	@aPermission(name = { "资产管理", "诉讼管理", "案件管理", "删除社会案例" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	public Reply deleteThis() {
		Map<String, Object> param = _getParameters();
		boolean flag = service.deleteThis(param);
		if(flag){
			return new ReplyAjax(param).addOp(new OpLog("删除", "删除", param.toString()));
		}else{
			return new ReplyAjax(flag,"删除失败");
		}
	}
	
}