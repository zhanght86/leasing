//package com.pqsoft.call.action;
//
//import java.util.Map;
//
//import com.pqsoft.call.util.CallUtil;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjax;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//
//public class CallOutAction extends Action {
//
//
//	/**
//	 * 外呼电话
//	 * @author 付玉龙  fuyulong47@foxmail.com
//	 * @date 2013-10-8  上午11:01:39
//	 */
//	@aPermission(name = { "债权管理", "呼叫中心", "外呼"})
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
//	@Override
//	public Reply execute() {
//		Map<String, Object> param = _getParameters();
//		String result = CallUtil.callOut(param.get("phone").toString());
//		return new ReplyAjax(result);
//	}
//	
//}
