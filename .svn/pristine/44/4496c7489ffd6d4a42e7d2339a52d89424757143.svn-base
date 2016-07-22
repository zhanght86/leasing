package com.pqsoft.action;

import java.io.IOException;

import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.cache.Cache;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.AnnoAnalysis;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

/**
 * 刷新底层权限
 * 
 * @author King 2013-8-30
 */
public class SetupAction extends Action {

	@Override
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", name = "King", email = "jinfu@pqsoft.cn")
	public Reply execute() {
		try {
			AnnoAnalysis.exec();
			Cache.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
