package com.pqsoft.zcfl.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.zcfl.service.GradSortService;

public class CapitalClassificationAction extends Action {

	@aPermission(name = { "资产分类管理", "资产评级管理" ,"列表" })
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply Refer2() {
		Map<String, Object> param = _getParameters();
		GradSortService service = new GradSortService();
		List<Object> str = (List<Object>) service.list1(param);
		if (str == null) {
			str = (List<Object>) new ArrayList<Object>();
		}
		JSONArray jsonArray = JSONArray.fromObject(str);
		return new ReplyAjax(jsonArray);
	}

	@Override
	public Reply execute() {
		// TODO 
		return null;
	}
}
