//package com.pqsoft.insure.action;
//
//import java.util.Map;
//
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.base.organization.service.ManageService;
//import com.pqsoft.insure.service.InsureService;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.api.Security;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//import com.pqsoft.util.PageUtil;
//
//public class InsureMonitorAction extends Action {
//
//	@Override
//	public Reply execute() {
//		return null;
//	}
//
//	@aPermission(name = { "保险管理", "保险监控", "全程保险明细" })
//	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
//	@aAuth(type = aAuthType.USER)
//	public Reply toAll() {
//		VelocityContext context = new VelocityContext();
//		Map map = new InsureService().queryProductAndCompany();
//		context.put("data", map);
//		return new ReplyHtml(VM.html("insure/InsureMonitorAll.vm", context));
//	}
//
//	@aPermission(name = { "保险管理", "保险监控", "全程保险明细" })
//	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
//	@aAuth(type = aAuthType.USER)
//	public Reply toAllPage() {
//		Map<String, Object> map = _getParameters();
//		// 数据权限 判断供应商登录只能看到当前供应商数据
//		ManageService mgService = new ManageService();
//		Map supMap = (Map) mgService.getSupByUserId(Security.getUser().getId());
//		if(supMap != null && supMap.get("SUP_ID") != null){
//			map.put("SUP_TYPE", supMap.get("SUP_ID"));
//		}
//		return new ReplyAjaxPage(PageUtil.getPage(map, "InsureMonitor.getAllPage", "InsureMonitor.getAllPageCount"));
//	}
//
//	@aPermission(name = { "保险管理", "保险监控", "半程保险明细" })
//	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
//	@aAuth(type = aAuthType.USER)
//	public Reply toHalf() {
//		VelocityContext context = new VelocityContext();
//		Map map = new InsureService().queryProductAndCompany();
//		context.put("data", map);
//		return new ReplyHtml(VM.html("insure/InsureMonitorHalf.vm", context));
//	}
//
//	@aPermission(name = { "保险管理", "保险监控", "半程保险明细" })
//	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
//	@aAuth(type = aAuthType.USER)
//	public Reply toHalfPage() {
//		Map<String, Object> map = _getParameters();
//		// 数据权限 判断供应商登录只能看到当前供应商数据
//		ManageService mgService = new ManageService();
//		Map supMap = (Map) mgService.getSupByUserId(Security.getUser().getId());
//		if(supMap != null && supMap.get("SUP_ID") != null){
//			map.put("SUP_TYPE", supMap.get("SUP_ID"));
//		}
//		return new ReplyAjaxPage(PageUtil.getPage(map, "InsureMonitor.getHalfPage", "InsureMonitor.getHalfPageCount"));
//	}
//
//}
