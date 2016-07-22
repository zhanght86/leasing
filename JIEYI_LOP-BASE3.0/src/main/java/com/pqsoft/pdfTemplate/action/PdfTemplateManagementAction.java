package com.pqsoft.pdfTemplate.action;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import com.pqsoft.baseScheme.service.BaseSchemeService;
import com.pqsoft.dataDictionary.action.DataDictionaryAction;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.pdfTemplate.service.PdfTemplateManagementService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.JsonUtil;
import com.pqsoft.util.StringUtils;

import net.sf.json.JSONObject;

public class PdfTemplateManagementAction extends Action {

	private Logger logger = Logger.getLogger(DataDictionaryAction.class);
	PdfTemplateManagementService service = new PdfTemplateManagementService();

	/**
	 * pdf模版管理列表页
	 * 
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-8-28 上午11:19:21
	 */
	@aPermission(name = { "参数配置", "模版管理" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	@Override
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		context.put("TPM_TYPE_LIST", new SysDictionaryMemcached().get("PDF模版类型"));
		context.put("TPM_CUSTOMER_TYPE_LIST", new DataDictionaryMemcached().get("客户类型"));

		context.put("TPM_PROJECT_TYPE_LIST", new BaseSchemeService().getFHFA_MANAGERSUBINFO(
				Security.getUser().getOrg().getPlatformId(), Security.getUser().getOrg()
				.getPlatform(), "行业类型"));
//		context.put("TPM_PROJECT_TYPE_LIST", new SysDictionaryMemcached().get("行业类型"));

		context.put("TPM_STATUS_LIST", new SysDictionaryMemcached().get("PDF模版使用状态"));
		context.put("TPM_TEXT_TYPE_LIST", new SysDictionaryMemcached().get("PDF模版文本类型"));
		context.put("TPM_SALES_WAY_LIST", new SysDictionaryMemcached().get("销售模式"));
		context.put("TPM_LEASE_WAY_LIST", new SysDictionaryMemcached().get("PDF模版对应租赁方式"));
		context.put("TPM_BUSINESS_PLATE_LIST", new SysDictionaryMemcached().get("业务类型"));// Modify
																		// By：
																		// YangJ
																		// 2014年5月6日11:33:52
		context.put("TPM_APPLY_AGENT_LIST", new SysDictionaryMemcached().get("PDF模版适用供应商"));
		context.put("TPM_BREACH_MONEY_LIST", new SysDictionaryMemcached().get("PDF模版是否涉及违约金"));
		context.put("TPM_SIGNATURE_NAME_LIST", new SysDictionaryMemcached().get("PDF模版签字方名称"));
		context.put("TPM_MANUFACTURERS_LIST", new SysDictionaryMemcached().get("PDF模版使用厂商"));
		context.put("TPM_USE_DEPARTMENT_LIST", new SysDictionaryMemcached().get("PDF模版使用部门"));
		context.put("TPM_SIGNED_OCCASION_LIST", new SysDictionaryMemcached().get("PDF模版签订场合"));
		context.put("TPM_FILE_TYPE_LIST", new SysDictionaryMemcached().get("PDF所属模版类型"));
		context.put("EDITOR_TPM_CUSTOMER_TYPE2", new SysDictionaryMemcached().get("模版对应的客户类型"));
		context.put("EDITOR_TPM_BUSINESS_PLATE2", new SysDictionaryMemcached().get("所属行业板块"));
		context.put("TPM_CODE_LIST", service.selectTpmCode());// 所有文本编号
		//add JZZL-151 gengchangbao 2016年4月12日 Start
		context.put("sealPathList", DataDictionaryMemcached.getList("签约主体"));
		//add JZZL-151 gengchangbao 2016年4月12日 end
		System.out.println("加载PDF模版界面！");
		return new ReplyHtml(VM.html("pdfTemplate/pdfTemplateMg.vm", context));
	}

	/**
	 * 管理页列表数据
	 * 
	 * @param 列表页查询条件
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-8-29 下午08:04:03
	 */
	@aPermission(name = { "参数配置", "模版管理" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toMgPdfTemplate() {
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
	 * 管理页展开层数据
	 * 
	 * @param TPM_TYPE
	 *            模版类型
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-8-29 下午08:04:03
	 */
	@aPermission(name = { "参数配置", "模版管理", "展开页", "明细" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toUnfoldDate() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("row"));
		param.remove("row");
		param.putAll(JsonUtil.toMap(json));
		param.put("FILE_TYPE", null);
		System.out.println(param);
		return new ReplyAjax(service.getUnfoldDate(param));
	}

	/**
	 * 添加新模版
	 * 
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-8-31 下午02:01:47
	 */
	@aPermission(name = { "参数配置", "模版管理", "添加" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doAddPdfTemplate() {
		Map<String, Object> param = _getParameters();
		System.out.println("合同模版参数ACTION:" + param);

		if (param.get("EDITOR_TPM_TYPE").equals("")) return new ReplyAjax(false, "模版类型不可为空");
		if (param.get("EDITOR_TPM_MUST_SELECT").equals("")) return new ReplyAjax(false, "是否必选 不可为空");
		if (param.get("EDITOR_TPM_CUSTOMER_TYPE1").equals("")) return new ReplyAjax(false, "模版对应的客户类型不可为空");
		if (param.get("EDITOR_TPM_BUSINESS_TYPE1").equals("")) return new ReplyAjax(false, "业务类型不可为空");
		if (param.get("EDITOR_TPM_BUSINESS_PLATE1").equals("")) return new ReplyAjax(false, "所属行业板块不可为空");
		if (param.get("TPM_SIGNATURE_LOGO").equals("")) return new ReplyAjax(false, "签章标识不可为空");
		if (param.get("TPM_SEAL_SERVICE").equals("")) return new ReplyAjax(false, "盖章处不可为空");
		//add JZZL-151 gengchangbao 2016年4月12日 Start
		Map<String, Object> sealInfoMap = null;
		String sealIndex = "0";
		String TPM_ID = Dao.getSequence("SEQ_PDFTEMPLATE_MAINTENANCE");
		param.put("TPM_ID", TPM_ID);
		for (String key : param.keySet()) {
			if (key.contains("SEAL_PATH_TBA_")){
				sealInfoMap = new HashMap<String, Object>();
				sealIndex = key.substring(14);
				sealInfoMap.put("TPM_ID", param.get("TPM_ID"));
				sealInfoMap.put("SEAL_PATH", param.get("SEAL_PATH_TBA_"+sealIndex));
				sealInfoMap.put("TPM_SEAL_SERVICE", param.get("TPM_SEAL_SERVICE2_TAB_"+sealIndex));
				service.addTpmSealInfo(sealInfoMap);
			}
		}
		//add JZZL-151 gengchangbao 2016年4月12日 end
		boolean flag = service.addPdfTemplate(param);
		if (flag) {
			return new ReplyAjax(param).addOp(new OpLog("添加模版", "添加", param.toString()));
		} else {
			return new ReplyAjax(false, "保存失败！");
		}
	}

	/**
	 * 上传PDF模版
	 * 
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-8-31 下午02:01:47
	 */
	@aPermission(name = { "参数配置", "模版管理", "上传" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply uploadPdfTemplate() {
		Map<String, Object> param = new HashMap<String, Object>();
		List<FileItem> fileList = _getFileItem();
		boolean flag = service.uploadPdfTemplate(param, fileList);
		logger.debug("param=" + param);
		if (flag) {
			// 在数据库中储存模版相关属性
			boolean flag1 = service.uploadPdfTemplate(param);
			if (flag1) {
				return new ReplyAjax(param).addOp(new OpLog("添加pdf模版", "添加", param.toString()));
			} else {
				return new ReplyAjax(false, "保存失败！");
			}
		}
		return new ReplyAjax(false, "保存失败！");
	}

	/**
	 * 修改模版页面
	 * 
	 * @param TPM_ID
	 *            模版id
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-9-5 下午02:20:44
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toUpdatePdfTemplate() {
		Map<String, Object> param = _getParameters();
		Map<String, Object> detailsMap = service.selectTemDetails(param);
		//add JZZL-151 gengchangbao 2016年4月12日 Start
		List<Map<String, Object>> tempList = (List<Map<String, Object>>) service.queryContractSealInfoList(param);
		detailsMap.put("fryzTemplate", tempList);
		//add JZZL-151 gengchangbao 2016年4月12日 End
		return new ReplyAjax(detailsMap);
	}

	/**
	 * 查询pdf模版详细信息
	 * 
	 * @param TPM_ID
	 *            模版id
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-9-5 下午02:20:44
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toShowPdfTemplate() {
		Map<String, Object> param = _getParameters();
		Map<String, Object> detailsMap = service.selectTemDetails(param);
		//add JZZL-151 gengchangbao 2016年4月12日 Start
		List<Map<String, Object>> tempList = (List<Map<String, Object>>) service.queryContractSealInfoList(param);
		detailsMap.put("fryzTemplate", tempList);
		//add JZZL-151 gengchangbao 2016年4月12日 End
		System.out.println("detailsMap" + detailsMap);
		return new ReplyAjax(detailsMap);
	}

	/**
	 * 修改模版
	 * 
	 * @param TPM_ID
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-9-5 下午04:01:26
	 */
	@aPermission(name = { "参数配置", "模版管理", "修改" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doUpdatePdfTemplate() {
		Map<String, Object> param = _getParameters();
		System.out.println("合同模版参数ACTION:" + param);
		if (param.get("EDITOR_TPM_TYPE2").equals("")) return new ReplyAjax(false, "模版类型不可为空");
		if (param.get("EDITOR_TPM_MUST_SELECT2").equals("")) return new ReplyAjax(false, "是否必选 不可为空");
		if (param.get("EDITOR_TPM_CUSTOMER_TYPE21").equals("")) return new ReplyAjax(false, "模版对应的客户类型不可为空");
		if (param.get("EDITOR_TPM_BUSINESS_TYPE21").equals("")) return new ReplyAjax(false, "业务类型不可为空");
		if (param.get("EDITOR_TPM_BUSINESS_PLATE21").equals("")) return new ReplyAjax(false, "所属行业板块不可为空");
		if (param.get("TPM_SIGNATURE_LOGO2").equals("")) return new ReplyAjax(false, "签章标识不可为空");
		if (param.get("TPM_SEAL_SERVICE2").equals("")) return new ReplyAjax(false, "盖章处不可为空");
		//add JZZL-151 gengchangbao 2016年4月12日 Start
		Map<String, Object> sealInfoMap = null;
		String sealIndex = "0";
		service.deleteTpmSealInfo(param);
		for (String key : param.keySet()) {
			if (key.contains("SEAL_PATH_TBA_")){
				sealInfoMap = new HashMap<String, Object>();
				sealIndex = key.substring(14);
				sealInfoMap.put("TPM_ID", param.get("TPM_ID"));
				sealInfoMap.put("SEAL_PATH", param.get("SEAL_PATH_TBA_"+sealIndex));
				sealInfoMap.put("TPM_SEAL_SERVICE", param.get("TPM_SEAL_SERVICE2_TAB_"+sealIndex));
				service.addTpmSealInfo(sealInfoMap);
			}
		}
		//add JZZL-151 gengchangbao 2016年4月12日 End
		boolean flag = service.updatePdfTemplate(param);
		if (flag) {
			return new ReplyAjax(param).addOp(new OpLog("修改模版", "修改", param.toString()));
		} else {
			return new ReplyAjax(false, "修改失败");
		}
	}

	/**
	 * 删除pdf模版
	 * 
	 * @param ID
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-9-3 下午11:28:37
	 */
	@aPermission(name = { "参数配置", "模版管理", "删除" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doDeletePdfTemplate() {
		Map<String, Object> param = _getParameters();
		boolean flag = service.deletePdfTemplate(param);
		if (flag) {
			return new ReplyAjax(param).addOp(new OpLog("删除pdf模版", "删除", param.toString()));
		} else {
			return new ReplyAjax(false, "删除失败");
		}
	}

	/**
	 * 下载pdf模版
	 * 
	 * @param TPM_ID
	 *            模版id
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-9-4 上午09:41:03
	 */
	@aPermission(name = { "参数配置", "模版管理", "展开页", "下载" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public ReplyFile downPdfTemplate() {
		Map<String, Object> param = _getParameters();
		Map<String, Object> map = service.selectPdfPath(param);
		// try {
		// DownloadFile.download(pdfPath, SkyEye.getResponse());
		// } catch (Exception e) {
		// throw new ActionException("PDF模版不存在",e);
		// }
		String pdfPath = map.get("PDF_PATH").toString();
		File file = new File(pdfPath);
		String fileName = file.getName();
		String type = pdfPath.substring(pdfPath.lastIndexOf("."));
		String Name = map.get("NAME") + "";
		boolean flag = Name.contains(".");
		if (flag) {
			fileName = null == map.get("NAME") ? "项目附件" : Name;
		} else fileName = null == map.get("NAME") ? "项目附件" + type : Name + type;
		return new ReplyFile(file, fileName);
	}

	/**
	 * 启用模版
	 * 
	 * @param TPM_ID
	 *            模版id
	 * @param TPM_TYPE
	 *            模版类型
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-9-4 上午11:38:44
	 */
	@aPermission(name = { "参数配置", "模版管理", "展开页", "启用" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doUpdateChangeStatus() {
		Map<String, Object> param = _getParameters();
		boolean flag = service.changeStatus(param);
		if (flag) {
			return new ReplyAjax(param);
		} else {
			return new ReplyAjax(flag, "启用失败").addOp(new OpLog("启用pdf模版", "启用", param.toString()));
		}
	}

	/**
	 * 修改上传的pdf模版
	 * 
	 * @param TPM_ID
	 *            模版id
	 * @param TPM_TYPE
	 *            模版类型
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-9-4 上午11:38:44
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toUpdatePdfFile() {
		Map<String, Object> param = _getParameters();
		param = service.selectPdfDetails(param);
		return new ReplyAjax(param);
	}

	/**
	 * 修改pdf模版
	 * 
	 * @param TPM_ID
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-9-5 下午04:01:26
	 */
	@aPermission(name = { "参数配置", "模版管理" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doUpdatePdfFile() {
		Map<String, Object> param = new HashMap<String, Object>();
		List<FileItem> fileList = _getFileItem();
		boolean flag = service.uploadPdfTemplate(param, fileList);
		logger.debug("param=" + param);
		if (flag) {
			// 在数据库中储存模版相关属性
			boolean flag1 = service.updatePdfFile(param);
			if (flag1) {
				return new ReplyAjax(param).addOp(new OpLog("修改pdf模版", "修改", param.toString()));
			} else {
				return new ReplyAjax(false, "保存失败");
			}
		}
		return new ReplyAjax(false, "保存失败！");
	}

}
