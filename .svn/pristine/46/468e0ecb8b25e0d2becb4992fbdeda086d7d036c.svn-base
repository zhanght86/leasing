//package com.pqsoft.call.action;
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.call.service.CallPlayScreenService;
//import com.pqsoft.call.util.CallUtil;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.api.Security;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//
//public class CallPlayScreenAction extends Action {
//	
//	CallPlayScreenService service = new CallPlayScreenService();
//
//	/**
//	 * 弹屏页面
//	 * @author 付玉龙  fuyulong47@foxmail.com
//	 * @date 2013-11-24  上午06:14:11
//	 */
//	@aPermission(name = { "债权管理", "呼叫中心", "弹屏页面"})
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
//	@Override
//	public Reply execute() {
//		Map<String,Object> param = _getParameters();
//		VelocityContext context = new VelocityContext();
//		List<Map<String,Object>> proList = null;
//		//承租人信息
//		List<Map<String,Object>> custListMap = service.selectCustDate(param);
//		Map<String,Object> custMap = null;
//		if(custListMap != null && !custListMap.isEmpty() && custListMap.size() >= 0){
//			custMap = custListMap.get(0);
//		}
//		//项目信息
//		if(custMap != null && !custMap.isEmpty() && custMap.containsKey("ID")){
//			//proList = service.selectProDate(custMap);
//			proList = service.selectProDate(param);
//		}
//		context.put("proList", proList);
//		context.put("custMap", custMap);
//		return new ReplyHtml(VM.html("call/callPlayScreenMg.vm", context));
//	}
//	
//	/**
//	 * 还款记录
//	 * @author 付玉龙  fuyulong47@foxmail.com
//	 * @date 2013-11-24  上午06:27:15
//	 */
//	@aPermission(name = { "债权管理", "呼叫中心", "弹屏页面"})
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
//	public Reply repaymentHistory(){
//		VelocityContext context = new VelocityContext();
//		Map<String, Object> param = _getParameters();
//		List<Map<String,Object>> repaymentHistoryList = service.selectRepaymentHistoryDate(param);
//		context.put("repaymentHistoryList", repaymentHistoryList);
//		return new ReplyHtml(VM.html("call/repaymentHistory.vm", context));
//	}
//
//	/**
//	 * @author 付玉龙  fuyulong47@foxmail.com
//	 * @date 2013-11-24  上午06:14:11
//	 */
//	@aPermission(name = { "债权管理", "呼叫中心", "内嵌页面"})
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
//	public Reply callIndexMg() {
//		Map<String,Object> param = _getParameters();
//		//获取呼叫专员配置信息
//		param.put("CNO_NUMBER", param.get("cno"));
//		Map<String,Object> map = CallUtil.getCallInformationCommissioner(param);
//		Security.login(map.get("USER_CODE").toString());
//		VelocityContext context = new VelocityContext();
//		return new ReplyHtml(VM.html("call/callIndex.vm", context));
//	}
//	
//}
