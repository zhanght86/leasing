package com.pqsoft.performance.action;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.performance.service.AssessmentResultService;
import com.pqsoft.performance.service.RefreshAssessmentDataJobs;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.JsonUtil;

public class AssessmentResultAction extends Action {
	
	private String path = "performance/";
	private AssessmentResultService service= new AssessmentResultService();
	
	@Override
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"绩效管理","考核查看","管理页"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply execute() {
		new RefreshAssessmentDataJobs().refreshAssessmentData();//刷数据
		return new ReplyHtml(VM.html(path+"AssessmentResultMg.vm", null));
	}
	
	@aPermission(name = {"绩效管理","考核查看","管理页"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getMgAssessmentResultData() {
		Map<String, Object> param = _getParameters();
			JSONObject json = JSONObject.fromObject(param);
			param.clear();
			param.putAll(JsonUtil.toMap(json));
		return new ReplyAjaxPage(service.getPage(param));
	}
	
	@aPermission(name = {"绩效管理","考核查看","管理页"})
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getARDetailData() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("RESULT", service.getResult(param));
		context.put("RESULT_DETAIL", service.getResultDetail(param));
		return new ReplyHtml(VM.html(path+"AssessmentResultDetail.vm", context));
	}
	
}
