package com.pqsoft.performance.action;

import java.util.Map;

import net.sf.json.JSONObject;

import com.pqsoft.performance.service.AssessmentTopicService;
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

public class AssessmentTopicAction extends Action {
	
	private String path = "performance/";
	private AssessmentTopicService service= new AssessmentTopicService();
	
	@Override
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"绩效管理","考核项管理","管理页"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply execute() {
		return new ReplyHtml(VM.html(path+"AssessmentTopicMg.vm", null));
	}
	
	@aPermission(name = {"绩效管理","考核项管理","管理页"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getMgAssessmentTopicData() {
		Map<String, Object> param = _getParameters();
			JSONObject json = JSONObject.fromObject(param);
			param.clear();
			param.putAll(JsonUtil.toMap(json));
		return new ReplyAjaxPage(service.getPage(param));
	}
	
	@aPermission(name = {"绩效管理","考核项管理","管理页"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getATData() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.getATData(param));
	}
	
	@aPermission(name = {"绩效管理","考核项管理","管理页","删除"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doDeleteAT() {
		Map<String, Object> param = _getParameters();
		boolean flag =  service.doDeleteAT(param);
		return new ReplyAjax(flag,param).addOp(new OpLog("考核项管理","删除",param.toString()));
	}
	
	@aPermission(name = {"绩效管理","考核项管理","管理页","添加"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doAddAT() {
		Map<String, Object> param = _getParameters();
		if(param.containsKey("param")){
			JSONObject json = JSONObject.fromObject(param.get("param"));
			param.remove("param");
			param.putAll(JsonUtil.toMap(json));
		}
		boolean flag =  service.doAddAT(param);
		return new ReplyAjax(flag,param).addOp(new OpLog("考核项管理","添加",param.toString()));
	}
	
	@aPermission(name = {"绩效管理","考核项管理","管理页","修改"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doUpdateAT() {
		Map<String, Object> param = _getParameters();
		if(param.containsKey("param")){
			JSONObject json = JSONObject.fromObject(param.get("param"));
			param.remove("param");
			param.putAll(JsonUtil.toMap(json));
		}
		boolean flag =  service.doUpdateAT(param);
		return new ReplyAjax(flag,param).addOp(new OpLog("考核项管理","修改",param.toString()));
	}

}
