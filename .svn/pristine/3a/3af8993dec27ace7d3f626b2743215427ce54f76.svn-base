//package com.pqsoft.call.action;
//
//import java.util.Map;
//import java.util.Random;
//
//import net.sf.json.JSONObject;
//
//import com.pqsoft.call.service.LeaseVerifyService;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//import com.pqsoft.util.DateUtil;
//import com.pqsoft.util.ReplyExcel;
//import com.pqsoft.util.SecuUtil;
//
//public class LeaseVerifyAction extends Action {
//
//	private LeaseVerifyService service = new LeaseVerifyService();
//	Map<String,Object> m = null;
//	
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "债权管理", "呼叫中心", "租赁物信息验证","列表" })
//	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
//	@Override
//	public Reply execute() {
//
//		return new ReplyHtml(VM.html("call/leaseVerify.vm", null));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "债权管理", "呼叫中心", "承租人信息验证(自然人)","列表" })
//	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
//	public Reply getIdentity(){
//		
//		return new ReplyHtml(VM.html("call/IdentityVerify.vm", null));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "债权管理", "呼叫中心", "承租人信息验证(法人)","列表" })
//	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
//	public Reply getIdentityLp(){
//		
//		return new ReplyHtml(VM.html("call/IdentityLpVerify.vm", null));
//	}
//	
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "债权管理", "呼叫中心", "租赁物信息验证","查询(必选)" })
//	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
//	public Reply getPage(){
//		m = _getParameters();
//		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
//		m.remove("searchParams");
//		m.putAll(json);
//		return new ReplyAjaxPage(service.getLeasingData(m));
//	}
//	
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "债权管理", "呼叫中心", "承租人信息验证(自然人)","查询(必选)"  })
//	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
//	public Reply getPageIdentity(){
//		m = _getParameters();
//		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
//		m.remove("searchParams");
//		m.putAll(json);
//		return new ReplyAjaxPage(service.getIdentityData(m));
//	}
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "债权管理", "呼叫中心", "承租人信息验证(法人)","查询(必选)"  })
//	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
//	public Reply getPageIdentityLP(){
//		m = _getParameters();
//		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
//		m.remove("searchParams");
//		m.putAll(json);
//		return new ReplyAjaxPage(service.getIdentityLpData(m));
//	}
//	
//	/**
//	 * 
//	 * @Title: exportExcel 
//	 * @Description: (Excle导出) 
//	 * @return 
//	 * @return Reply 
//	 * @throws 
//	 */
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "债权管理", "呼叫中心", "租赁物信息验证", "Excle导出" })
//	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
//	public Reply exportExcel(){
//		m = _getParameters();
////		m.put("sqlData", m.get("sqlData").toString());
//		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
//		m.remove("searchParams");
//		m.putAll(json);
//		//加入操作人信息
//		SecuUtil.putUserInfo(m);
//		return new ReplyExcel(service.exportData(m),"LeaseVerify"+DateUtil.getSysDate()+Math.abs(new Random(10000).nextInt())+".xls");
//	}
//	
//	
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "债权管理", "呼叫中心", "承租人信息验证(自然人)", "Excle导出" })
//	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
//	public Reply exportExcelIdentity(){
//		m = _getParameters();
////		m.put("sqlData", m.get("sqlData").toString());
//		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
//		m.remove("searchParams");
//		m.putAll(json);
//		//加入操作人信息
//		SecuUtil.putUserInfo(m);
//		return new ReplyExcel(service.exportDataIdentity(m),"IdentityVerify"+DateUtil.getSysDate()+Math.abs(new Random(10000).nextInt())+".xls");
//	}
//
//	
//	
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "债权管理", "呼叫中心","承租人信息验证(法人)", "Excle导出" })
//	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
//	public Reply exportExcelIdentityLp(){
//		m = _getParameters();
////		m.put("sqlData", m.get("sqlData").toString());
//		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
//		m.remove("searchParams");
//		m.putAll(json);
//		//加入操作人信息
//		SecuUtil.putUserInfo(m);
//		return new ReplyExcel(service.exportDataIdentityLp(m),"IdentityLpVerify"+DateUtil.getSysDate()+Math.abs(new Random(10000).nextInt())+".xls");
//	}
//
//	
//	
//
//}
