//package com.pqsoft.agreementBill.action;
//
//import java.util.Map;
//
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.agreementBill.service.AgreementBillService;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Page;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjax;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//
//public class AgreementBillAction extends Action {
//    private String path = "agreementBill/";
//    private AgreementBillService service = new AgreementBillService();
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//    @aPermission(name ={"票据管理","开票协议书管理","列表"})
//    @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//    @Override
//    public Reply execute() {
//	VelocityContext context = new VelocityContext();
//	Map<String,Object> param = _getParameters();
//	param.put("param", param);
//	return new ReplyHtml(VM.html(path+"agreementBillManager.vm", context));
//    }
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//    @aPermission(name ={"票据管理","开票协议书管理","查询"})
//    @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//    public Reply getAgreeBillPageData(){
//	Map<String,Object> param = _getParameters();
//	Page pageData = service.getAgreementBillPage(param);
//	return new ReplyAjaxPage(pageData);
//    }
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//    @aPermission(name ={"票据管理","开票协议书管理","添加"})
//    @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//    public Reply addAgreeBill(){
//	Map<String,Object> param = _getParameters(); 
//	int result = service.addAgreementBill(param);
//	if(result > 0 ){
//	    return new ReplyAjax(true, "操作成功！");
//	}else{
//	    return new ReplyAjax(true, "操作失败！");
//	}
//    }
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//    @aPermission(name ={"票据管理","开票协议书管理","更新"})
//    @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//    public Reply updateAgreeBill(){
//	Map<String,Object> param = _getParameters();
//	int result = service.updateBillMess(param);
//	if(result > 0){
//	    return new ReplyAjax(true,"操作成功！");
//	}else{
//	    return new ReplyAjax(false,"操作失败！");
//	}
//    }
//
//}
