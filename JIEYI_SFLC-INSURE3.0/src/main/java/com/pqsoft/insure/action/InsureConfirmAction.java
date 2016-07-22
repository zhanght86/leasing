//package com.pqsoft.insure.action;
//
//import java.io.File;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.insure.service.InsureConfirmService;
//import com.pqsoft.insure.service.InsureService;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjax;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyFile;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.api.SkyEye;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//
//public class InsureConfirmAction extends Action {
//
//	@Override
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
//	@aPermission(name = { "保险管理", "保险监控", "保险审核" })
//	public Reply execute() {
//		Map<String, Object> map = new InsureService().queryProductAndCompany();
//		VelocityContext context = new VelocityContext();
//		context.put("data", map);
//		return new ReplyHtml(VM.html("insure/confirm.vm", context));
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
//	@aPermission(name = { "保险管理", "保险监控", "保险审核" })
//	public Reply getPageList() {
//		InsureConfirmService service = new InsureConfirmService();
//		Map<String, Object> param = _getParameters();
//		return new ReplyAjaxPage(service.getPageList(param));
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
//	@aPermission(name = { "保险管理", "保险监控", "保险审核" })
//	public Reply toSub() {
//		InsureConfirmService service = new InsureConfirmService();
//		String fileIds = SkyEye.getRequest().getParameter("ids");
//		if (fileIds == null) return null;
//		for (String id : fileIds.split("[,，;；]")) {
//			if (id == null || "".equals(id.trim())) continue;
//			service.toSub(id);
//		}
//		return new ReplyAjax();
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
//	@aPermission(name = { "保险管理", "保险监控", "保险审核" })
//	public Reply toSubNo() {
//		InsureConfirmService service = new InsureConfirmService();
//		String fileIds = SkyEye.getRequest().getParameter("ids");
//		if (fileIds == null) return null;
//		for (String id : fileIds.split("[,，;；]")) {
//			if (id == null || "".equals(id.trim())) continue;
//			service.toSubNo(id);
//		}
//		return new ReplyAjax();
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
//	@aPermission(name = { "保险管理", "保险监控", "保险审核" })
//	public Reply toSubAll() {
//		InsureConfirmService service = new InsureConfirmService();
//		Map<String, Object> param = _getParameters();
//		service.toSubAll(param);
//		return new ReplyAjax();
//	}
//
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
//	public Reply getFileLook() {
//		String id = SkyEye.getRequest().getParameter("id");
//		if (id == null) return null;
//		return new ReplyHtml("<img src='" + SkyEye.getRequest().getContextPath() + "/insure/InsureConfirm!getFile.action?id=" + id + "' />");
//	}
//
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
//	public Reply getFile() {
//		String id = SkyEye.getRequest().getParameter("id");
//		if (id == null) return null;
//		InsureConfirmService service = new InsureConfirmService();
//		Map<String, Object> fileMap = service.getFile(id);
//		if (fileMap == null) return null;
//		File file = new File((String) fileMap.get("FILE_PATH"));
//		if (!file.exists()) return null;
//		return new ReplyFile(file, (String) fileMap.get("FILE_NAME"));
//	}
//
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
//	public Reply delFile() {
//		String id = SkyEye.getRequest().getParameter("id");
//		if (id != null) {
//			InsureConfirmService service = new InsureConfirmService();
//			service.delFile(id);
//		}
//		return new ReplyAjax();
//	}
//
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
//	public Reply getFileList() {
//		String id = SkyEye.getRequest().getParameter("id");
//		if (id == null) return null;
//		InsureConfirmService service = new InsureConfirmService();
//		List<Map<String, Object>> list = service.getFileList(id);
//		VelocityContext context = new VelocityContext();
//		context.put("list", list);
//		return new ReplyHtml(VM.html("insure/insureFile.vm", context));
//	}
//
//}
