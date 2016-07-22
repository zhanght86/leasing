//package com.pqsoft.lmrm.action;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.apache.commons.fileupload.FileItem;
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.lmrm.service.LeaseMortgageService;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Page;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjax;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.api.SkyEye;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.api.Security;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
//import com.pqsoft.util.DownloadFile;
//import com.pqsoft.util.JsonUtil;
//import com.pqsoft.util.StringUtils;
//
//public class LeaseMortgageAction extends Action {
//	
//	String vmPath = "lmrm/";
//	LeaseMortgageService service = new LeaseMortgageService();
//
//	//@aPermission(name = { "资产管理", "抵押管理", "主页面" })
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "zhanglu", email = "leedsjung@126.com", name = "张路")
//	@Override
//	public Reply execute() {
//		return new ReplyHtml(VM.html(vmPath + "leaseMortgageMg.vm", null));
//	}
//	
//	//@aPermission(name = { "资产管理", "抵押管理", "主页面" })
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "zhanglu", email = "leedsjung@126.com", name = "张路")
//	public Reply toMgLeaseMortgageData(){
//		Map<String, Object> param = _getParameters();
////		param.put("TASK_MAN", Security.getUser().getId());
//		if(param.containsKey("param")){
//			JSONObject json = JSONObject.fromObject(param.get("param"));
//			param.remove("param");
//			param.putAll(JsonUtil.toMap(json));
//		}
//		return new ReplyAjaxPage(service.getPage(param));
//	}
//	
//	//@aPermission(name = { "资产管理", "抵押管理", "添加抵押" })
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "zhanglu", email = "leedsjung@126.com", name = "张路")
//	public Reply saveMort(){
//		VelocityContext context = new VelocityContext();
//		List<FileItem> fileList = _getFileItem();
//		Map<String,Object> map = (Map<String, Object>) service.uploadFileForOne(fileList) ;
//		map.put("MORT_FILE_PATH", map.get("FILE_PATH"));
//		map.put("FPE_ID", map.get("EQU_ID"));
//		if(StringUtils.isNotBlank(map.get("ID"))) {
//			service.updateMort(map);
//		} else {
//			map.put("STATE", 0);
//			service.addMort(map);
//		}
//		return new ReplyAjax();
//	}
//	
//	//@aPermission(name = { "资产管理", "抵押管理", "查看抵押" })
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "zhanglu", email = "leedsjung@126.com", name = "张路")
//	public Reply showMort(){
//		Map<String, Object> param = _getParameters();
//		Map mort = service.getMort(param);
//		return new ReplyAjax(mort);
//	}
//	
//	//@aPermission(name = { "资产管理", "抵押管理", "修改抵押" })
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "zhanglu", email = "leedsjung@126.com", name = "张路")
//	public Reply updateMort(){
//		Map param = _getParameters();
//		List<FileItem> fileList = _getFileItem();
//		Map<String,Object> map = (Map<String, Object>) service.uploadFileForOne(fileList) ;
//		param.putAll(map);
//		param.put("MORT_FILE_PATH", map.get("FILE_PATH"));
//		
//		return new ReplyAjax(service.updateMort(param));
//	}
//	
//	//@aPermission(name = { "资产管理", "抵押管理", "下载" })
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "zhanglu", email = "leedsjung@126.com", name = "张路")
//	public Reply downloadFile(){
//		Map<String, Object> param = _getParameters();
//		DownloadFile.download(param.get("filePath").toString(), SkyEye.getResponse());
//		return null;
//	}
//	
//	//@aPermission(name = { "资产管理", "抵押管理", "解押" })
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "zhanglu", email = "leedsjung@126.com", name = "张路")
//	public Reply saveDemort(){
//		Map param = _getParameters();
//		List<FileItem> fileList = _getFileItem();
//		Map<String,Object> map = (Map<String, Object>) service.uploadFileForOne(fileList) ;
//		param.putAll(map);
//		param.put("DEMORT_FILE_PATH", map.get("FILE_PATH"));
//		
//		service.updateDeMort(param);
//		return new ReplyAjax();
//	}
//	
//	//@aPermission(name = { "资产管理", "抵押管理", "加载任务人" })
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "zhanglu", email = "leedsjung@126.com", name = "张路")
//	public Reply loadTaskMans(){
//		Map param = new HashMap();
//		param.put("ORG_ID", Security.getUser().getOrg().getId());
//		List<Map> taskMans = service.getTaskMans(param);
//		Page page = new Page();
//		page.addDate(JSONArray.fromObject(taskMans), taskMans.size());
//		return new ReplyAjaxPage(page);
//	}
//	
//	//@aPermission(name = { "资产管理", "抵押管理", "指派任务" })
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "zhanglu", email = "leedsjung@126.com", name = "张路")
//	public Reply assignTaskMan(){
//		Map param = _getParameters();
//		JSONObject row = JSONObject.fromObject(param.get("row"));
//		param.putAll(row);
//		if(StringUtils.isNotBlank(param.get("ID"))) {
//			service.updateMort(param);
//		} else {
//			param.put("FPE_ID", param.get("EQU_ID"));
//			param.put("STATE", 2);
//			service.addMort(param);
//		}
//		return new ReplyAjax();
//	}
//}
