//package com.pqsoft.insurebxbc.action;
///**
// *   保险补差管理
// */
//import java.util.Iterator;
//import java.util.Map;
//
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.insure.service.InsureService;
//import com.pqsoft.insurebxbc.service.InsureBXBCManagementService;
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
//public class InsureBXBCManagementAction extends Action{
//	
//	private String basePath = "insure_bxbc/"; 
//	private InsureBXBCManagementService bxbcService = new InsureBXBCManagementService();
//	private InsureService insureService = new InsureService();
//	
//	/**
//	 *  显示主页面
//	 */
//	@Override
//	@aPermission(name = { "保险管理", "保险补差管理", "申请保险补差"})
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply execute() {
//		VelocityContext context = new VelocityContext();
//		//利用韩建的方法取得所有厂商 和 租赁物类型
//		context.put("ProductAndCompany", insureService.queryProductAndCompany());
//		return new ReplyHtml(VM.html(basePath + "insureBXBCApplyShow.vm", context));
//	}
//	/**
//	 * 用于分页查询
//	 */
//	@aPermission(name = { "保险管理", "保险补差管理", "主页面分页查询"})
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply pageData(){
//		Map<String,Object> param = _getParameters();
//		Page pagedata = bxbcService.getPageData(param);
//		return new ReplyAjaxPage(pagedata);
//	}
//	
//	/**
//	 *  用于确认保险补差 
//	 *  INSUR_CHARGE_EXHIB
//	 */
//	@aPermission(name = { "保险管理", "保险补差管理", "确认"})
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
//	public Reply doAddInsurChargeExhib() {
//		String[] ids = null;//项目id
//		String[] prices  = null;//金额
//		String[] sqtime  = null;//补差收取时间
//		String[] zftime  = null;//补差支付时间
//		Map<String,Object> param = _getParameters();
//		param.put("CREATOR", Security.getUser().getName());//当前操作人为创建人
//		ids = getNotNullValue(param, "ID_DATA").split(",");
//		prices = getNotNullValue(param, "BXBCJE_DATA").split(",");
//		sqtime = getNotNullValue(param, "SK_DATE_DATA").split(",");
//		zftime = getNotNullValue(param, "ZF_DATE_DATA").split(",");
//		int num = ids.length;
//		for (int i = 0; i < num; i++) {
//			//1
//			param.put("PROJECT_ID", ids[i]);
//			param.put("PLAN_MONEY", prices[i]);
//			param.put("PLAN_DATE", sqtime[i]);
//			param.put("STATUS", "0");
//			param.put("FUNDS_TYPE", "收款");
//			param.put("FUNDS_NAME", "保险补差收款");
//			param.put("PAYER", "客户本人");
//			param.put("PAYEE", "山重融资租赁有限公司");//收款人
//			bxbcService.insertInsurChargeExhib(param);//先插入收取记录
//			//2
//			param.put("PLAN_DATE", zftime[i]);
//			param.put("FUNDS_TYPE", "付款");
//			param.put("FUNDS_NAME", "保险补差付款");
//			param.put("PAYER", "山重融资租赁有限公司");
//			param.put("PAYEE", "天安保险北京分公司");//收款人
//			bxbcService.insertInsurChargeExhib(param);//再插入支付记录
//		}
//		return  new ReplyAjax(true,"");
//	}
//	
//	/**
//	 *  解决无值 返回 空字符串的问题
//	 */
//	private String getNotNullValue(Map map,String key){
//		 return map.containsKey(key) ? map.get(key).toString() : "";
//	}
//}
