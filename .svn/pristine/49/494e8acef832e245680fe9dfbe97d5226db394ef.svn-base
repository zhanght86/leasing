//package com.pqsoft.call.action;
///**
// *   催收台账
// *   @author 韩晓龙
// */
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//import net.sf.json.JSONObject;
//
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.base.organization.service.ManageService;
//import com.pqsoft.call.service.UrgeBookService;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Page;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjax;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.api.Security;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//import com.pqsoft.util.DateUtil;
//import com.pqsoft.util.ReplyExcel;
//
//public class UrgeBookAction extends Action {
//	
//	private String basePath = "call/"; 
//	private UrgeBookService service = new UrgeBookService();
//	
//	/**
//	 * 用于得到催收台账页面
//	 */
//	@Override
//	@aPermission(name = { "债权管理", "呼叫中心", "催收台账" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply execute() {
//		VelocityContext context=new VelocityContext();
//		return new ReplyHtml(VM.html(basePath + "urgeBookShow.vm", context));
//	}
//	/**
//	 * 用于得到呼叫中心-催收记录页面
//	 */
//	@aPermission(name = { "债权管理", "呼叫中心", "呼叫中心-催收记录" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply toShowUrgeRecord() {
//		Map<String,Object> param = _getParameters();
//		//1. 取得	逾期原因	客户反馈	催收日志	备注
//		Map map = (Map)service.selectRecordById(param);
//		VelocityContext context = new VelocityContext();
//		context.put("Record", map);//将map直接给context
//		//2. 取得	留言内容
//		List list = service.selectMessageById(param);
//		context.put("Message", list);//将map直接给context
//		return new ReplyHtml(VM.html(basePath + "urgeRecord.vm", context));
//	}
//	/**
//	 * 用于分页查询
//	 */
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply pageData(){
//		Map<String,Object> param = _getParameters();
//		ManageService mgService = new ManageService();
//		Map supMap = (Map) mgService.getSupByUserId(Security.getUser().getId());
//		if(supMap != null && supMap.get("SUP_ID") != null){
//			param.put("SUP_TYPE", supMap.get("SUP_ID"));
//		}
//		Page pagedata = service.getPageData(param);
//		return new ReplyAjaxPage(pagedata);
//	}
//	/**
//	 *  添加催收信息
//	 */
//	@aPermission(name = { "债权管理", "呼叫中心", "催收在线添加" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply doAddMessage() {
//		Map<String,Object> param = _getParameters();
//		param.put("M_USER", Security.getUser().getName());
//		service.insertMessage(param);
//		return  new ReplyAjax(true,"");
//	}
//	
//	/**
//	 *  删除催收信息
//	 */
//	@aPermission(name = { "债权管理", "呼叫中心", "催收在线删除" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply doDeleteMessage() {
//		Map<String,Object> param = _getParameters();
//		service.deleteMessageById(param);
//		return  new ReplyAjax(true,"");
//	}
//	
//	/**
//	 *  导出催收记录 TODO
//	 */
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply exportExcel(){
//		Map<String,Object> param = _getParameters();
//		JSONObject json = JSONObject.fromObject(param.get("searchParams"));
//		param.remove("searchParams");
//		param.putAll(json);
//		//数据权限
//		ManageService mgService = new ManageService();
//		Map supMap = (Map) mgService.getSupByUserId(Security.getUser().getId());
//		if(supMap != null && supMap.get("SUP_ID") != null){
//			param.put("SUP_TYPE", supMap.get("SUP_ID"));
//		}
//		return new ReplyExcel(service.exportData(param),"urgeBook"+DateUtil.getSysDate()+Math.abs(new Random(10000).nextInt())+".xls");
//	}
//}
