package com.pqsoft.paper.action;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.action.DataDictionaryAction;
import com.pqsoft.paper.service.PaperFilesManageService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.JsonUtil;
import com.pqsoft.util.StringUtils;

import net.sf.json.JSONObject;

public class PaperFilesManageAction extends Action {

	private Logger logger = Logger.getLogger(DataDictionaryAction.class);
	PaperFilesManageService service = new PaperFilesManageService();

	/**
	 * 资料管理列表页
	 * 
	 * @author 耿长宝
	 * @date 2013-8-28 上午11:19:21
	 */
	@aPermission(name = { "参数配置", "备份资料管理" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "gengchangbao", email = "changbaogeng@jiezhongchina.com", name = "耿长宝")
	@Override
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		System.out.println("==================资料管理=========================");
		return new ReplyHtml(VM.html("paper/paperFilesManage.vm", context));
	}
	
	/**
	 * 上传资料管理
	 * 
	 * @author 耿长宝
	 * @date 2013-8-28 上午11:19:21
	 */
	@aPermission(name = { "参数配置", "备份资料管理" , "列表" })
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "gengchangbao", email = "changbaogeng@jiezhongchina.com", name = "耿长宝")
	public Reply toPaperFilesShow() {
		Map<String, Object> param = _getParameters();
		if (param.containsKey("param")) {
			JSONObject json = JSONObject.fromObject(param.get("param"));
			param.remove("param");
			param.putAll(JsonUtil.toMap(json));
		}
		if (!param.containsKey("FILE_TYPE") || StringUtils.isBlank(param.get("FILE_TYPE"))) {
			param.put("FILE_TYPE", "0");
		}
		return new ReplyAjaxPage(service.getPage(param));

	}
	
	
	/**
	 * 上传资料管理
	 * 
	 * @author 耿长宝
	 * @date 2013-8-28 上午11:19:21
	 */
	@aPermission(name = { "参数配置", "备份资料管理" , "上传" })
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "gengchangbao", email = "changbaogeng@jiezhongchina.com", name = "耿长宝")
	public Reply uploadPaperFile() {
		Map<String, Object> param = new HashMap<String, Object>();
		List<FileItem> fileList = _getFileItem();
		boolean flag = service.uploadPaperFile(param, fileList);
		logger.debug("param=" + param);
		if (flag) {
			boolean flag1 = service.uploadPaperFile(param);
			if (flag1) {
				return new ReplyAjax(param).addOp(new OpLog("添加资料文件", "添加", param.toString()));
			} else {
				return new ReplyAjax(false, "保存失败！");
			}
		}
		return new ReplyAjax(false, "保存失败！");
	}
	
	
	/**
	 * 删除资料管理
	 * 
	 * @author 耿长宝
	 * @date 2013-8-28 上午11:19:21
	 */
	@aPermission(name = { "参数配置", "备份资料管理" , "删除" })
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "gengchangbao", email = "changbaogeng@jiezhongchina.com", name = "耿长宝")
	public Reply doDeletePaperFile() {
		Map<String, Object> param = _getParameters();
		//根据ID获取资料基本信息
		Map<String, Object> map = service.selectPaperFile(param);
		File file = new File(map.get("FILE_PATH").toString());
		if (file.isFile() && file.exists()) {  
			file.delete();  
		}
		boolean flag = service.deletePaperFile(param);
		if (flag) {
			return new ReplyAjax(param).addOp(new OpLog("删除pdf模版", "删除", param.toString()));
		} else {
			return new ReplyAjax(false, "删除失败");
		}
	}
	
	/**
	 * 下载资料文件
	 * 
	 * @author 耿长宝
	 * @date 2013-8-28 上午11:19:21
	 */
	@aPermission(name = { "参数配置", "备份资料管理" , "下载" })
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "gengchangbao", email = "changbaogeng@jiezhongchina.com", name = "耿长宝")
	public ReplyFile downPaperFile() {
		Map<String, Object> param = _getParameters();
		//根据ID获取资料基本信息
		Map<String, Object> map = service.selectPaperFile(param);
		String pdfPath = map.get("FILE_PATH").toString();
		File file = new File(pdfPath);
		String fileName = file.getName();
		return new ReplyFile(file, fileName);
	}
}
