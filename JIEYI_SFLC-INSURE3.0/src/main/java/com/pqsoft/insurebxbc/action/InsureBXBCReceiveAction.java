//package com.pqsoft.insurebxbc.action;
///**
// *   保险补差收取申请
// */
//import java.util.Map;
//
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.insure.service.InsureService;
//import com.pqsoft.insurebxbc.service.InsureBXBCManagementService;
//import com.pqsoft.insurebxbc.service.InsureBXBCReceiveService;
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
//import com.pqsoft.skyeye.rbac.api.aPermission;
//import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
//
//public class InsureBXBCReceiveAction extends Action{
//	
//	private String basePath = "insure_bxbc/"; 
//	private InsureBXBCReceiveService bxbcReceiveService = new InsureBXBCReceiveService();
//	private InsureService insureService = new InsureService();
//	private InsureBXBCManagementService bxbcService = new InsureBXBCManagementService();
//	
//	/**
//	 *  显示主页面
//	 */
//	@Override
//	@aPermission(name = { "保险管理", "保险补差管理", "补差收取申请"})
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply execute() {
//		return new ReplyHtml(VM.html(basePath + "insureBXBCReceive.vm", null));
//	}
//	
//	/**
//	 * 用于分页查询
//	 */
//	@aPermission(name = { "保险管理", "保险补差管理", "补差收取申请分页查询"})
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply pageData(){
//		Map<String,Object> param = _getParameters();
//		Page pagedata = bxbcReceiveService.getPageData(param);
//		return new ReplyAjaxPage(pagedata);
//	}
//	
//	/**
//	 *  跳转新增页面
//	 */
//	@aPermission(name = { "保险管理", "保险补差管理", "补差收取申请新增"})
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply toShowAddApply() {
//		VelocityContext context = new VelocityContext();
//		//利用韩建的方法取得所有厂商 和 租赁物类型
//		context.put("ProductAndCompany", insureService.queryProductAndCompany());
//		return new ReplyHtml(VM.html(basePath + "insureBXBCReceiveAdd.vm", context));
//	}
//	
//	/**
//	 * 用于删除补差支付申请
//	 */
//	@aPermission(name = { "保险管理", "保险公司管理", "补差收取申请删除" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply deleteBCSQApply() {
//		Map<String,Object> param = _getParameters();
//		//清空状态 根据ID号
//		bxbcService.upChargeExhibById(param);//APPLY_ID = ID
//		//1 删除 FIL_INSURE_APPLY_INFO 表中的数据
//		bxbcReceiveService.deleteBCSQApply(param);
//		return  new ReplyAjax(true,"");
//	}
//	
//	/**
//	 * 用于提交补差支付申请
//	 */
//	@aPermission(name = { "保险管理", "保险公司管理", "补差收取申请提交" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply commitBCSQApply() {
//		Map<String,Object> param = _getParameters();
//		bxbcReceiveService.updateBCSQApply(param);
//		return  new ReplyAjax(true,"");
//	}
//}
