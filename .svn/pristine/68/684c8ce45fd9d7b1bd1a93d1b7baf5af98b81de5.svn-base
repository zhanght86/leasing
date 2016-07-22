package com.pqsoft.documentApp.action;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.documentApp.service.ApplyDossierService;
import com.pqsoft.documentApp.service.RemindDossierService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class RemindDossierAction extends Action {

	private final String pagePath = "documentApp/";
	private RemindDossierService service = new RemindDossierService();

	@Override
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "归档提醒", "列表显示" })
	public Reply execute() {
		// TODO Auto-generated method stub
		VelocityContext context = new VelocityContext();
		// 数据字典查询项目业务类型
		context.put("YEWU", new SysDictionaryMemcached().get("业务类型"));
		return new ReplyHtml(VM.html(pagePath + "remind/toMgRemindDossier.vm",
				context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "归档提醒", "列表显示" })
	public Reply toRemindDossier() {
		Map<String, Object> param = _getParameters();// 获取页面参数
		// 查询列表数据
		JSONObject json = JSONObject.fromObject(param.get("searchParams"));
		param.remove("searchParams");
		param.putAll(json);
		Page page = service.toRemindDossier(param);
		return new ReplyAjaxPage(page);
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "归档提醒", "列表展开" })
	public Reply toShowRemindApp() {
		Map<String, Object> param = _getParameters();// 获取页面参数
		// 根据承租人, 合同号, 档案柜编号, 档案袋编号查看归档明细信息
		List<Map<String, Object>> listMap = service.toShowRemindApp(param);
		return new ReplyAjax(listMap);
	}

	// 未归档提醒中所用到的资料配置方法
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "yangxue")
	@aPermission(name = { "权证管理", "归档提醒", "列表展开" })
	public Reply toShowRecodeApp() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();// 获取页面参数
		ApplyDossierService service1 = new ApplyDossierService();
		context.put("params", param);
		context.put("remind", service.getRemindData(param));
		context.put("listMap", service1.toGetDocumentations(param));
		return new ReplyHtml(VM.html("datalist/recodeList.vm", context));
	}

	/**
	 * 资源分配中未归档文件
	 */
	public Reply toNotFileDossier() {
		Map<String, Object> param = _getParameters();// 获取页面参数
		// 查询列表数据
		List<Map<String, Object>> listMap = service.toNotFileDossier(param);
		return new ReplyAjax(listMap);
	}
}
