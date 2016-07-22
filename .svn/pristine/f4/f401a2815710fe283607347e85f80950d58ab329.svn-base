package com.pqsoft.approveComments.action;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.Code.service.CodeService;
import com.pqsoft.approveComments.service.ApproveCommentsService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.JsonUtil;

/**
 * 审批批复
 * @author caizhongyang
 *
 */
public class ApproveCommentsAction extends Action {
	public VelocityContext context = new VelocityContext();
	private String path="approveComments/";
	private ApproveCommentsService service=new ApproveCommentsService();
	
	@Override
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply execute() {
		Map<String, Object> param = _getParameters();
		context.put("param", service.queryProjectByProId(param).get(0));
		context.put("code1", CodeService.getCode("批复编号",null,null));
		return new ReplyHtml(VM.html(path+"approveCommentManage.vm",context));
	}
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply queryApproveCommentByProId() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		return new ReplyAjax(service.queryApproveCommentByProId(param));
	}
	
//	@aPermission (name = {})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply getApproveCommentManageTest() {
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"approveCommentManageTest.vm",context));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "caizhongyang", email = "caibinjava@163.com", name = "蔡忠洋")
	public Reply saveApproveComments() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		return new ReplyAjax(service.saveApproveComments(param));
	}
	
}