package com.pqsoft.base.assure.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.assure.service.AssureCompanyService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class AssureCompanyAction extends Action {
	private AssureCompanyService compservice = new AssureCompanyService();
	private String path = "base/assure/";

	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "合作机构", "担保公司管理", "列表显示" })
	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("PContext", param);
		context.put("allArea", compservice.getAllArea());
		return new ReplyHtml(VM.html(path + "assureCompanyManager.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
	public Reply pageData() {
		Map<String, Object> param = _getParameters();
		Page pagedata = compservice.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
	public Reply getAreaMess() {
		Map<String, Object> param = _getParameters();
		Map<String, Object> resultMess = new HashMap<String, Object>();
		List<Object> listArea = compservice.getAreaDownByParentId(param);
		resultMess.put("provs", listArea);
		return new ReplyJson(JSONObject.fromObject(resultMess));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "合作机构", "担保公司管理", "修改" })
	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
	public Reply saveCompany() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		param.put("USERNAME", Security.getUser().getName());
		int result = compservice.updateCompany(param);
		if (result > 0) {
			flag = true;
			msg = "保存成功！";
		} else {
			flag = false;
			msg = "保存失败！";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("担保公司管理", "修改", param.toString()));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "合作机构", "担保公司管理", "添加" })
	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
	public Reply addCompany() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		param.put("USERNAME", Security.getUser().getName());
		int result = compservice.addCompany(param);
		if (result > 0) {
			flag = true;
			msg = "添加成功！";
		} else {
			flag = false;
			msg = "添加失败！";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("担保公司管理", "新增", param.toString()));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "合作机构", "担保公司管理", "删除" })
	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
	public Reply delCompany() {
		Map<String, Object> param = _getParameters();
		Boolean flag = true;
		String msg = "";
		int result = compservice.delCompany(param);
		if (result > 0) {
			flag = true;
			msg = "删除成功！";
		} else {
			flag = false;
			msg = "删除失败！";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("担保公司管理", "删除", param.toString()));
	}

}
