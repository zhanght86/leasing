//package com.pqsoft.manufacturerApproval.action;
///**
// *  厂商审批查询
// *  @author 韩晓龙
// */
//import java.util.Map;
//
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.base.organization.service.ManageService;
//import com.pqsoft.manufacturerApproval.service.ManufacturerSearchService;
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
//
//public class ManufacturerSearchAction extends Action{
//	
//	private String basePath = "manufacturerApproval/"; 
//	private ManufacturerSearchService service = new ManufacturerSearchService();
//	private ManageService mgService = new ManageService();
//	
//	/**
//	 *  显示主页面
//	 */
//	@Override
//	@aPermission(name = { "厂商项目审批", "审批查询", "主页面"})
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply execute() {
//		VelocityContext context = new VelocityContext();
//		context.put("USERDEROLE", Security.getUser().getRoles().toString());
//		context.put("USERDENAME", Security.getUser().getName().toString());
//		return new ReplyHtml(VM.html(basePath + "manufacturerSearchShow.vm", context));
//	}
//	
//	/**
//	 * 用于分页查询
//	 */
//	@aPermission(name = { "厂商项目审批", "审批查询", "主页面分页查询"})
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply pageData(){
//		Map<String,Object> param = _getParameters();
//		//TODO 根据登陆账号判断关联的厂商 MANUFACTURER
//		//Map map = manageService.getCompanyByUserId(Security.getUser().getId().toString());//此方法有问题，需要实际的厂商编号测试
//		//param.put("MANUFACTURER", value);
//		//还可能对流程名称做限定
//		//param.put("LCNAME", value);
//		Map comMap = (Map) mgService.getCompanyByUserId(Security.getUser().getId());
//		if(comMap != null && comMap.get("COMPANY_ID") != null){
//			param.put("COMPANY_ID", comMap.get("COMPANY_ID"));
//		}
//		Page pagedata = service.getPageData(param);
//		return new ReplyAjaxPage(pagedata);
//	}
//	
//	/**
//	 *  重置审批  暂对企划担当和信审担当开放
//	 *  修改者 记录在 MODIFACTOR
//	 */
//	@aPermission(name = { "厂商项目审批", "审批查询", "重置审批" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply spReset() {
//		Map<String,Object> param = _getParameters();
//		param.put("MODIFACTOR", Security.getUser().getName());//当前操作人为修改者
//		service.spResetDetailByProjId(param);
//		return  new ReplyAjax(true,"");
//	}
//}