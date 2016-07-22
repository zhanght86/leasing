package com.pqsoft.util;

import java.util.Map;

import com.pqsoft.skyeye.rbac.api.Security;

public class SecuUtil {

	public static void putUserInfo(Map<String,Object> params){
		params.put("USERNAME",Security.getUser() == null ? "未登录" : Security.getUser().getName());
		params.put("USERCODE",Security.getUser() == null ? "-1" : Security.getUser().getCode());
		params.put("USERID",Security.getUser() == null ? "-1" : Security.getUser().getId());
	}
	
}
