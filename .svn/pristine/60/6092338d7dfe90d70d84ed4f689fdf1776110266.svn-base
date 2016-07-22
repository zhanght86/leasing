package com.pqsoft.systemManagement.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.entity.Tree;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.rbac.api.Security;

/**
 * @ClassName: MenuService
 * @author 程龙达
 * @date 2013-8-19 下午04:23:50
 */

public class MenuService {

	public List<Tree> getMenus(Map<String, Object> params) {
		String id = (params.get("id") == null || params.get("id").toString().equals("")) ? "0" : params.get("id").toString();
		params.put("menu_id", id);

		params.put("user_id", params.get("USERID"));
		if (params.get("USERCODE") != null && !params.get("USERCODE").equals("god")) {
			params.put("ORG_ID", Security.getUser().getOrg().getId());
			if (params.get("ORG_ID") != null && !"".equals(params.get("ORG_ID"))) { return Dao.selectList("menu.getMenusOther", params); }
			return null;
		} else {
			return Dao.selectList("menu.getMenus", params);
		}

	}

}
