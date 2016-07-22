package com.pqsoft.performance.action;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.performance.service.AssessmentConfigurService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.JsonUtil;

public class AssessmentConfigurAction extends Action {
	
	private String path = "performance/";
	private AssessmentConfigurService service= new AssessmentConfigurService();
	
	@Override
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"绩效管理","考核标准配置管理","管理页"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		context.put("POST_LEVEL_LIST", DataDictionaryMemcached.getList("岗位级别"));
		return new ReplyHtml(VM.html(path+"AssessmentConfigurMg.vm", context));
	}
	
	@aPermission(name = {"绩效管理","考核标准配置管理","管理页"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getMgAssessmentConfigurData() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		param.put("TYPE", "岗位级别");
		return new ReplyAjaxPage(service.getPage(param));
	}
	
	
	@aPermission(name = {"绩效管理","考核标准配置管理","管理页"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getPost() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.getPost(param));
	}

	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"绩效管理","考核标准配置管理","管理页","添加and修改"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toAssessmentConfigurAddAndUpdate() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("ASSESSMENT_TOPIC_LIST", service.getAssessmentTopic());//考核项
		context.put("DEPARTMENT_LIST", service.getDepartment());//部门
		context.put("POST_LEVEL_LIST", DataDictionaryMemcached.getList("岗位级别"));
		if(param.containsKey("ID") && !param.get("ID").toString().equals("")){
			context.put("assessmentConfigurMap",service.getAssessmentConfigur(param));
		}
		return new ReplyHtml(VM.html(path+"AssessmentConfigurAddAndUpdate.vm", context));
	}
	
	@aPermission(name = {"绩效管理","考核标准配置管理","管理页","添加and修改"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doSaveAssessmentConfigur() {
		Map<String, Object> param = _getParameters();
		JSONObject  assessmentConfigurMap = JSONObject.fromObject(param.get("alldata"));
		boolean flag = service.doSave(assessmentConfigurMap);
		return new ReplyAjax(flag,assessmentConfigurMap).addOp(new OpLog("考核标准配置管理","添加and修改",param.toString()));
	}
	
	@aPermission(name = {"绩效管理","考核标准配置管理","管理页","添加and修改"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doUpdateAC() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.doUpdateAC(param)).addOp(new OpLog("考核标准配置管理","修改启用状态",param.toString()));
	}
	
	@aPermission(name = {"绩效管理","考核标准配置管理","管理页"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toAssessmentConfigurShow() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("ASSESSMENT_TOPIC_LIST", service.getAssessmentTopic());//考核项
		context.put("DEPARTMENT_LIST", service.getDepartment());//部门
		context.put("POST_LEVEL_LIST", DataDictionaryMemcached.getList("岗位级别"));
		if(param.containsKey("ID") && !param.get("ID").toString().equals("")){
			context.put("assessmentConfigurMap",service.getAssessmentConfigur(param));
		}
		return new ReplyHtml(VM.html(path+"AssessmentConfigurShow.vm", context));
	}
	
	@aPermission(name = {"绩效管理","考核查看","管理页","删除"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doDeleteAC() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.doDeleteAC(param)).addOp(new OpLog("考核标准配置管理","修改启用状态",param.toString()));
	}

}
