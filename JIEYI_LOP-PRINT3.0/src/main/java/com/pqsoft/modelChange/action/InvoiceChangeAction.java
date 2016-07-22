//package com.pqsoft.modelChange.action;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Map;
//
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.modelChange.service.InvoiceChangeService;
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
//public class InvoiceChangeAction extends Action {
//	private String path = "modelChange/"; 
//	private InvoiceChangeService service = new InvoiceChangeService();
//
//	@Override
//	public Reply execute() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
//	/**
//	 * 票据方式变更页面
//	 * @return
//	 */
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","票据开具变更","申请","列表"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply showInvoiceChangeApplyPage(){
//		VelocityContext context = new VelocityContext();
//		Map<String,Object> param = _getParameters();
//		context.put("param", param);
//		return new ReplyHtml(VM.html(path+"invoiceChangeApplyPage.vm", context));
//	}
//	
//	/**
//	 * 票据信息查询
//	 * @return
//	 */
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","票据开具变更","申请","查询"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply getChangeApplyPageData(){
//		Map<String,Object> param = _getParameters();
//		Page pageData = service.getInvoiceChangeApplyData(param);
//		return new ReplyAjaxPage(pageData);
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","票据开具变更","核销","列表"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply showChangeHeXiaoPage(){
//		VelocityContext context = new VelocityContext();
//		Map<String,Object> param = _getParameters();
//		context.put("param", param);
//		return new ReplyHtml(VM.html(path+"invoiceChangeHeXiaoPage.vm", context));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","票据开具变更","核销","查询"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply getChangeHeXiaoPageData(){
//		Map<String,Object> param = _getParameters();
//		Page pageData = service.getChangeHeXiaoPageData(param);
//		return new ReplyAjaxPage(pageData);
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","票据开具变更","查询","列表"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply showSearchModelChangePage(){
//		Map<String,Object> param = _getParameters();
//		VelocityContext context = new VelocityContext();
//		context.put("param", param);
//		return new ReplyHtml(VM.html(path+"invoiceChangeSearchPage.vm", context));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","票据开具变更","查询","查询[按钮]"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply getSearchModelChangeData(){
//		Map<String,Object> param = _getParameters();
//		Page pageData = service.getChangePageSearchData(param);
//		return new ReplyAjaxPage(pageData);
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","票据开具变更","核销","通过"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply crossApplyMethod(){
//		Map<String,Object> param = _getParameters();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		param.put("HEXIAO_USER_ID", Security.getUser().getName());
//		param.put("HEXIAO_DATE", sdf.format(new Date()));
//		int result = service.crossApplyUpdateStatus(param);
//		if(result >0 ){
//			service.updateProjectInvoiceStyle(param);
//			return new ReplyAjax(true, "操作成功！");
//		}else{
//			return new ReplyAjax(false, "操作失败！");
//		}
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","票据开具变更","核销","驳回"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply reJectApplyMethod(){
//		Map<String,Object> param = _getParameters();
//		int result = service.reJectDeleteApply(param);
//		if(result >0){
//			return new ReplyAjax(true,"操作成功！");
//		}else{
//			return new ReplyAjax(false,"操作失败！");
//		}
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","票据开具变更","申请","提交"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply changeApplyMethod(){
//		Map<String,Object> param = _getParameters();
//		Map<String,Object> applyMess = service.getBaseApplyMess(param);
//		param.put("APPLY_USER_ID", Security.getUser().getName());
//		if(param.containsKey("INVOICE_METHOD") && param.get("INVOICE_METHOD") !=null && "合并开具".equals(param.get("INVOICE_METHOD").toString().trim())){
//			param.put("FROM_TYPE", "合并开具");
//			param.put("TO_TYPE", "按月开具");
//		}else{
//			param.put("FROM_TYPE", "按月开具");
//			param.put("TO_TYPE", "合并开具");
//		}
//		param.put("STATUS", "0");
//		applyMess.putAll(param);
//		int result = service.addChangeApply(applyMess);
//		if(result > 0 ){
//			return new ReplyAjax(true,"操作成功！");
//		}else{
//			return new ReplyAjax(false,"操作失败！");
//			
//		}
//	}
//
//}
