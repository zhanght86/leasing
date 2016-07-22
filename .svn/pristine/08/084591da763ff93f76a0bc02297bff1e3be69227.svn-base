//package com.pqsoft.insure.action;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.fileupload.FileItem;
//import org.apache.velocity.VelocityContext;
//import org.springframework.util.FileCopyUtils;
//
//import com.pqsoft.insure.service.InsureConfirmService;
//import com.pqsoft.insure.service.InsureRecordService;
//import com.pqsoft.insure.service.InsureService;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjax;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.api.SkyEye;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.api.Security;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//import com.pqsoft.util.BaseUtil;
//
//public class InsureRecordAction extends Action {
//
//	@Override
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
//	@aPermission(name = { "保险管理", "保险监控", "保险录入" })
//	public Reply execute() {
//		// InsureRecordService service = new InsureRecordService();
//		Map<String, Object> map = new InsureService().queryProductAndCompany();
//		VelocityContext context = new VelocityContext();
//		context.put("data", map);
//		return new ReplyHtml(VM.html("insure/record.vm", context));
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
//	@aPermission(name = { "保险管理", "保险监控", "保险录入" })
//	public Reply getPageList() {
//		InsureRecordService service = new InsureRecordService();
//		Map<String, Object> param = _getParameters();
//		Map<String, Object> sup = BaseUtil.getSup();
//		if (sup != null) {
//			param.put("SUPP_NAME", sup.get("SUP_SHORTNAME"));
//		}
//		return new ReplyAjaxPage(service.getPageList(param));
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
//	@aPermission(name = { "保险管理", "保险监控", "保险录入" })
//	public Reply saveInsuer() {
//		Map<String, Object> param = _getParameters();
//		param.put("CREATE_CODE", Security.getUser().getCode());
//		InsureRecordService service = new InsureRecordService();
//		service.saveInsuer(param);
//		List<FileItem> list = _getFileItem();
//		String filedirpath = SkyEye.getConfig("file.path");
//		filedirpath = filedirpath == null ? "/pqsoft/file" : filedirpath.toString();
//		File dir = new File(filedirpath);
//		{
//			dir.mkdirs();
//		}
//		service.delFile(param);
//		File file = null;
//		for (FileItem fileItem : list) {
//			if (!fileItem.isFormField()) {
//				try {
//					Map<String, Object> fileMap = new HashMap<String, Object>();
//					fileMap.put("IR_ID", param.get("ID"));
//					String fileName = _getFileName(fileItem.getName());
//					if (fileName == null || "".equals(fileName)) continue;
//					fileMap.put("FILE_NAME", fileName);
//					String path = filedirpath + File.separator + System.currentTimeMillis() + fileName;
//					fileMap.put("FILE_PATH", path);
//					file = new File(path);
//					FileCopyUtils.copy(FileCopyUtils.copyToByteArray(fileItem.getInputStream()), file);
//					if ("FILE1".equals(fileItem.getFieldName())) {
//						fileMap.put("FILE_TYPE", "1");
//					} else {
//						fileMap.put("FILE_TYPE", "2");
//					}
//					service.addFile(fileMap);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return new ReplyAjax();
//	}
//
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
//	@aPermission(name = { "保险管理", "保险监控", "保险录入" })
//	public Reply toSub() {
//		InsureRecordService service = new InsureRecordService();
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
//	@aPermission(name = { "保险管理", "保险监控", "保险录入" })
//	public Reply toSubAll() {
//		InsureRecordService service = new InsureRecordService();
//		Map<String, Object> param = _getParameters();
//		service.toSubAll(param);
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
//		context.put("OP", "UP");
//		return new ReplyHtml(VM.html("insure/insureFile.vm", context));
//	}
//
//}
