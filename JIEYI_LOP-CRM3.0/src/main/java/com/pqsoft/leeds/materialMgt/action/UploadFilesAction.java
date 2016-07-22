package com.pqsoft.leeds.materialMgt.action;

import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class UploadFilesAction extends Action {

	@Override
	@aAuth(type=aAuth.aAuthType.LOGIN)
//	@aPermission(name={"参数配置", "资料管理"})
	@aDev(code="leeds", name="leeds", email="leedsjung@126.com")
	public Reply execute() {
		
		
		// TODO 
		return null;
	}
	
	
}
