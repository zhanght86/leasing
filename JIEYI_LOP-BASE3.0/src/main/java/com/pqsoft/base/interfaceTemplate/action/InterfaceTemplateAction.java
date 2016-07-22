package com.pqsoft.base.interfaceTemplate.action;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.velocity.VelocityContext;
import com.pqsoft.base.interfaceTemplate.service.InterfaceTemplateService;
import com.pqsoft.base.interfaceTemplate.service.RunInterfaceTemplate;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.JsonUtil;

public class InterfaceTemplateAction extends Action {

	private String path = "interfaceTemplate/";
	private InterfaceTemplateService service = new InterfaceTemplateService();

	@Override
	@aPermission(name = { "参数配置", "推送模版设置" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		context.put("nodeList", service.getJavaConfig());
		context.put("sqlList", service.getDx());
		return new ReplyHtml(VM.html(path + "interfaceTemplate.vm", context));
	}

	@aPermission(name = { "参数配置", "推送模版设置" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getJdData() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		return new ReplyAjaxPage(service.getPageJd(param));
	}

	@aPermission(name = { "参数配置", "推送模版设置" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getDsData() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		return new ReplyAjaxPage(service.getPageDs(param));
	}

	@aPermission(name = { "参数配置", "推送模版设置" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply deleteJd() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.deleteJd(param));
	}

	@aPermission(name = { "参数配置", "推送模版设置" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply deleteDs() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.deleteDs(param));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "推送模版设置" })
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doUpdateDs() {
		Map<String, Object> param = _getParameters();
		if (param.containsKey("param")) {
			JSONObject json = JSONObject.fromObject(param.get("param"));
			param.remove("param");
			param.putAll(JsonUtil.toMap(json));
		}
		boolean flag = service.doUpdateDs(param);
		return new ReplyAjax(flag, param).addOp(new OpLog("接口模版管理", "修改", param.toString()));
	}

	@aPermission(name = { "参数配置", "推送模版设置" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply checkNameAdd() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.checkName(param), null);
	}

	@aPermission(name = { "参数配置", "推送模版设置" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply checkNameUpdate() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.checkName(param), null);
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "推送模版设置" })
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doAddDs() {
		Map<String, Object> param = _getParameters();
		if (param.containsKey("param")) {
			JSONObject json = JSONObject.fromObject(param.get("param"));
			param.remove("param");
			param.putAll(JsonUtil.toMap(json));
		}
		boolean flag = service.doAddDs(param);
		return new ReplyAjax(flag, param).addOp(new OpLog("接口模版管理", "定时批量", "添加", param.toString()));
	}

	@aPermission(name = { "参数配置", "推送模版设置" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getJdList() {
		Page page = new Page();
		JSONArray jdList = JSONArray.fromObject(service.getJavaConfig());
		page.addDate(jdList, jdList.size());
		return new ReplyAjaxPage(page);
	}
	
	@aPermission(name = { "参数配置", "推送模版设置" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getNodeNameList() {
		Map<String,Object> param = _getParameters();
		List<Map<String,Object>> getNodeNameList = service.getNodeNameList(param);
		return new ReplyAjax(getNodeNameList);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "推送模版设置" })
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doAddJd() {
		Map<String,Object> param = _getParameters();
		JSONObject  saveData = JSONObject.fromObject(param.get("alldata"));
		boolean flag = service.doAddJd(saveData);
		return new ReplyAjax(flag, saveData).addOp(new OpLog("参数配置,推送模版设置,定时批量", "添加", param.toString()));
	}
	
	@aPermission(name = { "参数配置", "推送模版设置" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getJdUpdateData() {
		Map<String,Object> param = _getParameters();
		List<Map<String,Object>> getJdUpdateData = service.getJdUpdateData(param);
		return new ReplyAjax(getJdUpdateData);
	}
	
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply CESHI() {
		Map<String,Object> param = _getParameters();
//		param.put("NAME", "测试");
//		param.put("ID", "1123581321");
		new RunInterfaceTemplate().sendJbpmMessage(param.get("NAME").toString(),param.get("ID").toString());
		return new ReplyAjax();
	}
	
}
