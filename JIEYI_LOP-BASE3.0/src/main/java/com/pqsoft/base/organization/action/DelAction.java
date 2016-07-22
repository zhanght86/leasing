package com.pqsoft.base.organization.action;

import java.util.Map;

import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class DelAction extends Action {

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "组织架构" })
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply doDelCase() {
		Boolean flag = true;
		String msg = "";
		try {
			ManageService mservice = new ManageService();
			Map<String, Object> map = mservice.getInfoById(SkyEye.getRequest().getParameter("ID"));
			if (map.get("TYPE").toString().equals("11") || map.get("TYPE").toString().equals("12")) {
				flag = false;
				msg = "组织架构为平台，不能删除";
				return new ReplyAjax(flag, msg);
			}
			mservice.delCase(SkyEye.getRequest().getParameter("ID"));
			flag = true;
			return new ReplyAjax(flag, msg).addOp(new OpLog("组织架构", "删除", map.toString()));
		} catch (Exception e) {
			flag = false;
			msg = "删除失败";
			return new ReplyAjax(flag, msg);
		}
	}

	@Override
	public Reply execute() {
		return null;
	}

}
