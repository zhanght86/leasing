package com.pqsoft.zcfl.action;


import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.zcfl.service.AssetsInitialLevelService;

public class AssetsInitialLevelAction extends Action {
	@aPermission(name = { "资产分类管理","资产评级管理","同步数据"})
	@aDev(code = "170020", email = "leedsjung@163.com", name = "张路")
	@aAuth(type = aAuthType.USER)
	public Reply addAssetsInitialLevel() {
		AssetsInitialLevelService service = new AssetsInitialLevelService();
		boolean flag = true;
		try {
			service.geIinetialDataList();
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return new ReplyAjax(flag);
	}

	@Override
	public Reply execute() {
		// TODO 
		return null;
	}
	
}
