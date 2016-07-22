//package com.pqsoft.businessTax.action;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import net.sf.json.JSONArray;
//
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.addTax.service.VatFirstPayService;
//import com.pqsoft.businessTax.service.BusTaxOtherFeeService;
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
//
//
//public class BusTaxOtherFeeAction extends Action {
//	private String path = "businessTax/";
//	private BusTaxOtherFeeService service = new BusTaxOtherFeeService();
//
//	@Override
//	public Reply execute() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","营业税发票管理","留购价款开票申请","列表"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply showBusTaxLeaveApplyPage(){
//		VelocityContext context = new VelocityContext();
//		Map<String,Object> param = _getParameters();
//		context.put("param", param);
//		return new ReplyHtml(VM.html(path+"busTaxLeavePurchaseApplyPage.vm", context));
//	}
//	
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","营业税发票管理","留购价款开票申请","查询"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply getBusTaxLeaveApplyData(){
//		Map<String,Object> param = _getParameters();
//		Page pageData = service.getBusTaxLeavePurApplyPageData(param);
//		return new ReplyAjaxPage(pageData);
//	}
//
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","营业税发票管理","留购价款开票申请","申请"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply applyBusTaxLeaveMethod(){
//		Map<String,Object> param = _getParameters();
//		VatFirstPayService busInvoice = new VatFirstPayService();
//		JSONArray jsonArray = JSONArray.fromObject(param.get("sqlData").toString());
//		int result = 0 ;
//		List<Object> applyMess = new ArrayList<Object>();
//		if(jsonArray.size()>0){
//			applyMess = jsonArray;
//		}else{
//			List<String> listItem = new ArrayList<String>();
//			listItem.add("留购价款");
//			param.put("BEGINNING_NAMES", listItem);
//			param.put("LEAVING", "yes");
//			param.put("BUS_FLAG", "YES");
//			applyMess = service.getAllOtherBusInvoiceMess(param);
//		}
//		for (Object object : applyMess) {
//			Map<String,Object> newParam = (Map<String, Object>)object;
//			newParam.put("CREATOR", Security.getUser().getName());
//			newParam.put("FUND_TYPE", "项目结束");
//			//插入发票详细信息
//			List<String> s = new ArrayList<String>();
//			s.add("留购价款");
//			newParam.put("D_STATUS", "0");
//			newParam.put("BEGINNING_NAMES", s);
//			newParam.put("INVOICE_TYPE", "营业税发票");
//			newParam.put("LEAVING", "yes");
//			newParam.put("IF_INVOICE", "yes");
//			result += busInvoice.addInvoice(newParam);
//		}
//		if(result >0){
//			return new ReplyAjax(true,"申请成功！");
//		}else{
//			return new ReplyAjax(false,"申请失败！");
//		}
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","营业税发票管理","管理服务费开票申请","列表"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply showBusTaxManagerPage(){
//		VelocityContext context = new VelocityContext();
//		Map<String,Object> param = _getParameters();
//		context.put("param", param);
//		return new ReplyHtml(VM.html(path+"busTaxManagerApplyPage.vm",context));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","营业税发票管理","管理服务费开票申请","查询"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply getBusTaxManagerApplyData(){
//		Map<String,Object> param = _getParameters();
//		Page pageData = service.getBusTaxManagerApplyPageData(param);
//		return new ReplyAjaxPage(pageData);
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","营业税发票管理","管理服务费开票申请","申请"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply applyBusTaxManagerFeeMethod(){
//		Map<String,Object> param = _getParameters();
//		VatFirstPayService busInvoice = new VatFirstPayService();
//		JSONArray jsonArray = JSONArray.fromObject(param.get("sqlData").toString());
//		int result = 0 ;
//		List<Object> applyMess = new ArrayList<Object>();
//		if(jsonArray.size()>0){
//			applyMess = jsonArray;
//		}else{
//			List<String> listItem = new ArrayList<String>();
//			listItem.add("管理服务费");
//			param.put("BEGINNING_NAMES", listItem);
//			param.put("BUS_FLAG", "YES");
//			applyMess = service.getAllOtherBusInvoiceMess(param);
//		}
//		for (Object object : applyMess) {
//			Map<String,Object> newParam = (Map<String, Object>)object;
//			newParam.put("CREATOR", Security.getUser().getName());
//			newParam.put("FUND_TYPE", "项目费用");
//			//插入发票详细信息
//			List<String> s = new ArrayList<String>();
//			s.add("管理服务费");
//			newParam.put("D_STATUS", "0");
//			newParam.put("BEGINNING_NAMES", s);
//			newParam.put("INVOICE_TYPE", "营业税发票");
//			newParam.put("IF_INVOICE", "yes");
//			result += busInvoice.addInvoice(newParam);
//		}
//		if(result >0){
//			return new ReplyAjax(true,"申请成功！");
//		}else{
//			return new ReplyAjax(false,"申请失败！");
//		}
//		
//	}
//}
