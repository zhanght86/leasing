//package com.pqsoft.splitAccount.action;
//
//
//import java.util.Map;
//import java.util.Random;
//
//import org.apache.velocity.VelocityContext;
//
//import net.sf.json.JSONObject;
//
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//import com.pqsoft.splitAccount.service.SplitAccountService;
//import com.pqsoft.util.DateUtil;
//import com.pqsoft.util.ReplyExcel;
//import com.pqsoft.util.SecuUtil;
//
//public class SplitAccountAction extends Action {
//
//	SplitAccountService service=new SplitAccountService();
//	Map<String,Object> m = null;
//	
//	
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "资金管理", "分账管理"})
//	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
//	@Override
//	public Reply execute() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "资金管理", "分账管理", "租金分账","列表" })
//	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
//	public Reply  rentAccount(){
//		VelocityContext context = new VelocityContext();
//		service.getBaseQueryDate("rent", context);
//		return new ReplyHtml(VM.html("splitAccount/rentSplitAccount.vm", context));
//	}
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "资金管理", "分账管理", "首付款分账","列表" })
//	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
//	public Reply  firstFundAccount(){
//		VelocityContext context = new VelocityContext();
//		service.getBaseQueryDate("first", context);
//		return new ReplyHtml(VM.html("splitAccount/firstSplitAccount.vm", context));
//	}
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "资金管理", "分账管理", "租金分账","数据查询(必选)"  })
//	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
//	public Reply  getRentAccountDate(){
//		m = _getParameters();
//		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
//		m.remove("searchParams");
//		m.putAll(json);
//		return new ReplyAjaxPage(service.getRentAccountDate(m));
//	}
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "资金管理", "分账管理", "首付款分账","数据查询(必选)" })
//	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
//	public Reply  getFirstAccountDate(){
//		m = _getParameters();
//		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
//		m.remove("searchParams");
//		m.putAll(json);
//		return new ReplyAjaxPage(service.getFirstAccountDate(m));
//	}
//	/**
//	 * 
//	 * @Title: exportExcel 
//	 * @Description: (申请导出) 
//	 * @return 
//	 * @return Reply 
//	 * @throws 
//	 */
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "资金管理", "分账管理", "租金分账", "Excle导出" })
//	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
//	public Reply exportFirstAcountExcel(){
//		m = _getParameters();
////		m.put("sqlData", m.get("sqlData").toString());
//		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
//		m.remove("searchParams");
//		m.putAll(json);
//		//加入操作人信息
//		SecuUtil.putUserInfo(m);
//		return new ReplyExcel(service.exportFirstAcountExcel(m),"FirstAccount"+DateUtil.getSysDate()+Math.abs(new Random(10000).nextInt())+".xls");
//	}
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "资金管理", "分账管理", "租金分账", "Excle导出" })
//	@aDev(code = "6195", email = "yuq@strongflc.com", name = "于奇")
//	public Reply exportRentAcountExcel(){
//		m = _getParameters();
////		m.put("sqlData", m.get("sqlData").toString());
//		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
//		m.remove("searchParams");
//		m.putAll(json);
//		//加入操作人信息
//		SecuUtil.putUserInfo(m);
//		return new ReplyExcel(service.exportRentAcountExcel(m),"RentAccount"+DateUtil.getSysDate()+Math.abs(new Random(10000).nextInt())+".xls");
//	}
//
//}
