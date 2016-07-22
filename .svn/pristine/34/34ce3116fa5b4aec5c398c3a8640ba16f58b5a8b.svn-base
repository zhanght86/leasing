package com.pqsoft.pdfTemplate.action;

import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import com.pqsoft.dataDictionary.action.DataDictionaryAction;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.pdfTemplate.service.PdfTemplateDataConfigurationService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.JsonUtil;

public class PdfTemplateDataConfigurationAction extends Action {

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(DataDictionaryAction.class);
	PdfTemplateDataConfigurationService service = new PdfTemplateDataConfigurationService();

	/**
	 * 模板关联管理列表页
	 * 
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-8-28 上午11:19:21
	 */
	@SuppressWarnings("unchecked")
	@aPermission(name = { "参数配置", "模板关联", "列表" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	@Override
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		DataDictionaryMemcached DDMservice = new DataDictionaryMemcached();
		// context.put("TPM_CUSTOMER_TYPE_LIST", (List)DDMservice.get("客户类型"));
		// context.put("TPM_STATUS_LIST", (List)DDMservice.get("PDF模版使用状态"));
		// context.put("TPM_BUSINESS_PLATE_LIST",
		// (List)DDMservice.get("PDF模版所属商务板块"));
		// context.put("PDF_FILE_LIST", service.selectPdfFile());//所有PDF模版信息
		context.put("PDF_LIST", service.selectPdfMain());// 所有PDF模版信息
		context.put("PDF_FORMLABEL_LIST", service.selectPdfFormLabel());// 所有表单域SQL信息
		return new ReplyHtml(VM.html("pdfTemplate/pdfTemplateDataConfigurationMg.vm", context));
	}

	/**
	 * 模板关联列表数据
	 * 
	 * @param 列表页查询条件
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-8-29 下午08:04:03
	 */
	@aPermission(name = { "参数配置", "模板关联", "列表" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toMgPdfTemplateDataConfiguration() {
		Map<String, Object> param = _getParameters();
		System.out.println("查询参数：：：" + param);
		if (param.containsKey("param")) {
			System.out.println("查询参数：：：" + param);
			JSONObject json = JSONObject.fromObject(param.get("param"));
			param.remove("param");
			param.putAll(JsonUtil.toMap(json));
		}
		return new ReplyAjaxPage(service.getPage(param));

	}

	/**
	 * 管理页展开层数据
	 * 
	 * @param TPM_ID
	 *            模版ID
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-8-29 下午08:04:03
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toUnfoldDate() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("row"));
		param.remove("row");
		param.putAll(JsonUtil.toMap(json));
		return new ReplyAjax(service.getUnfoldDate(param));
	}

	/**
	 * 查询表单域执行顺序
	 * 
	 * @param TPF_ID
	 *            pdf模版ID
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-9-17 下午08:20:55
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply selectExeOrder() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.selectExeOrder(param));
	}

	/**
	 * 添加模板关联
	 * 
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-9-9 下午02:31:45
	 */
	@aPermission(name = { "参数配置", "模板关联", "添加" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doAddPdfTemplateDataConfiguration() {
		Map<String, Object> param = _getParameters();
		boolean flag = service.addPdfTemplateDataConfiguration(param);
		if (flag) {
			param.put("ID", param.get("EDITOR_TPM_BUSINESS_PLATE"));
			return new ReplyAjax(param).addOp(new OpLog("模板关联", "添加保存", param.toString()));
		} else {
			return new ReplyAjax(flag, "保存失败");
		}
	}

	/**
	 * 查看模板关联
	 * 
	 * @paramID PDF模版表单域ID
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-9-9 下午03:38:34
	 */
	@aPermission(name = { "参数配置", "模板关联", "查看" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toShowDataConfiguration() {
		Map<String, Object> param = _getParameters();
		Map<String, Object> detailsMap = service.toShowDataConfiguration(param);
		return new ReplyAjax(detailsMap);
	}

	/**
	 * 模板关联
	 * 
	 * @param ID
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-9-3 下午11:28:37
	 */
	@aPermission(name = { "参数配置", "模板关联", "删除" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doDeletePdfTemplateDataConfiguration() {
		Map<String, Object> param = _getParameters();
		boolean flag = service.doDeletePdfTemplateDataConfiguration(param);
		if (flag) {
			return new ReplyAjax(param).addOp(new OpLog("模板关联", "删除", param.toString()));
		} else {
			return new ReplyAjax(false, "删除失败");
		}
	}

	/**
	 * 执行修改模板关联
	 * 
	 * @paramID PDF模版表单域ID
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-9-9 下午03:09:54
	 */
	@aPermission(name = { "参数配置", "模板关联", "修改" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doUpdatePdfTemplateDataConfiguration() {
		Map<String, Object> param = _getParameters();
		boolean flag = service.doUpdatePdfTemplateDataConfiguration(param);
		if (flag) {
			return new ReplyAjax(param).addOp(new OpLog("修改模板关联", "修改保存", param.toString()));
		} else {
			return new ReplyAjax(false, "修改失败");
		}
	}

	@aPermission(name = { "参数配置", "模板关联", "删除" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply deletePZ() {
		Map<String, Object> param = _getParameters();
		int i = service.deletePZ(param);
		if (i >= 1) {
			return new ReplyAjax().addOp(new OpLog("模板关联", "删除", "删除id为" + param));
		} else {
			return new ReplyAjax(false, "删除成功数量为" + i);
		}
	}
}
