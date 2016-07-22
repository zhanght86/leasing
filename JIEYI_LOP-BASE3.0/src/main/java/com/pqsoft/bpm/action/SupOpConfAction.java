//package com.pqsoft.bpm.action;
//
//import java.util.Map;
//
//import org.apache.velocity.VelocityContext;
//
//import net.sf.json.JSONArray;
//
//import com.pqsoft.bpm.service.SupOpConfService;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjax;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.api.ReplyJson;
//import com.pqsoft.skyeye.api.SkyEye;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
//
//public class SupOpConfAction extends Action {
//
//	@Override
//	@aAuth(type = aAuthType.USER)
//	@aPermission(name = { "系统管理", "流程节点人员配置" })
//	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
//	public Reply execute() {
//		VelocityContext context = new VelocityContext();
//		SupOpConfService service = new SupOpConfService();
//		context.put("roles", service.roles());
//		return new ReplyHtml(VM.html("bpm/supopconf.vm", context));
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aPermission(name = { "系统管理", "流程节点人员配置" })
//	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
//	public Reply users() {
//		Map<String, Object> param = _getParameters();
//		SupOpConfService service = new SupOpConfService();
//		return new ReplyJson(service.users(param));
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aPermission(name = { "系统管理", "流程节点人员配置" })
//	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
//	public Reply getPage() {
//		Map<String, Object> param = _getParameters();
//		SupOpConfService service = new SupOpConfService();
//		return new ReplyAjaxPage(service.getPage(param));
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aPermission(name = { "系统管理", "流程节点人员配置" })
//	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
//	public Reply getItem() {
//		Map<String, Object> param = _getParameters();
//		SupOpConfService service = new SupOpConfService();
//		JSONArray array = JSONArray.fromObject(service.getItem(param));
//		return new ReplyJson(array);
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aPermission(name = { "系统管理", "流程节点人员配置" })
//	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
//	public Reply doSetOp() {
//		Map<String, Object> param = _getParameters();
//		SupOpConfService service = new SupOpConfService();
//		service.addItem(param);
//		return new ReplyAjax();
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aPermission(name = { "系统管理", "流程节点人员配置" })
//	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
//	public Reply doDelOp() {
//		SupOpConfService service = new SupOpConfService();
//		service.delItem(SkyEye.getRequest().getParameter("id"));
//		return new ReplyAjax();
//	}
//
//}
