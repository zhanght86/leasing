//package com.pqsoft.refinanceInto.action;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Map;
//
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.entity.Excel;
//import com.pqsoft.refinanceInto.service.RefinanceIntoBillService;
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
//import com.pqsoft.util.ReplyExcel;
//import com.pqsoft.util.StringUtils;
//
//public class RefinanceIntoBillAction extends Action {
//    private String path = "refinanceInto/";
//    private RefinanceIntoBillService service = new RefinanceIntoBillService();
//    
//    @Override
//    @aAuth(type = aAuth.aAuthType.USER)
//    @aPermission(name ={"票据管理", "融资进项票管理","列表"})
//    @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")    
//    public Reply execute() {
//	VelocityContext context = new VelocityContext();
//	Map<String,Object> param = _getParameters();
//	context.put("param", param);
//	return new ReplyHtml(VM.html(path+"refIntoBillPage.vm", context));
//    }
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//    @aPermission(name ={"票据管理", "融资进项票管理","查询"})
//    @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东") 
//    public Reply getIntoBillPageData(){
//	Map<String,Object> param = _getParameters();
//	Page pageData  = service.getIntoBillPageData(param);
//	return new ReplyAjaxPage(pageData);
//    }
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//    @aPermission(name ={"票据管理", "融资进项票管理","添加进项票信息"})
//    @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东") 
//    public Reply addRefinceIntoBill(){
//	Map<String,Object> param = _getParameters();
//	int result = 0;
//	if(param.containsKey("INVOICE_ID") && param.get("INVOICE_ID") !=null && !"".equals(StringUtils.nullToString(param.get("INVOICE_ID")))){
//	    result = service.updateIntoBill(param);
//	}else{
//	    param.put("CREATE_USER", Security.getUser().getName());
//	    param.put("TYPE", "1");
//	    result = service.addIntoBill(param);
//	}
//	if(result > 0 ){
//	    return new ReplyAjax(true, "操作成功！");
//	}else{
//	    return new ReplyAjax(false, "操作失败！");
//	}
//    }
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//    @aPermission(name ={"票据管理", "融资进项票管理","水电费"})
//    @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东") 
//    public Reply getRefinceIntoBillPage(){
//	VelocityContext context = new VelocityContext();
//	Map<String,Object> param = _getParameters();
//	context.put("param", param);
//	return new ReplyHtml(VM.html(path+"", context));
//    }
//
//    @aAuth(type = aAuth.aAuthType.USER)
//    @aPermission(name ={"票据管理", "融资进项票管理","删除融资进项票"})
//    @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东") 
//    public Reply delInvoice(){
//	Map<String,Object> param = _getParameters();
//	int result = 0 ;
//	result = service.delIntoBill(param);
//	if(result > 0){
//	    return new ReplyAjax(true,"操作成功！");
//	}else{
//	    return new ReplyAjax(false,"操作失败！");
//	}
//    }
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//    @aPermission(name ={"票据管理", "融资进项票管理","发票认证"})
//    @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东") 
//    public Reply approveInvoice(){
//	Map<String,Object> param = _getParameters();
//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//	param.put("LEGA_TIME", sdf.format(new Date()));
//	param.put("LEGA_USER", Security.getUser().getName());
//	int result = service.updateIntoBill(param);
//	if(result > 0 ){
//	    return new ReplyAjax(true, "操作成功！");
//	}else{
//	    return new ReplyAjax(false, "操作失败！");
//	}
//    }
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//    @aPermission(name ={"票据管理", "融资进项票管理","导出发票信息"})
//    @aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东") 
//    public Reply exportExcelMess(){
//	Map<String,Object> param = _getParameters();
//	Excel excel = new Excel();
//	excel = service.getExcelInvoices(param);
//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String fileName = "租赁物发票登记表-("+sdf.format(new Date())+")"+Math.round(Math.random()*8999+1000)+".xls";
//	return new ReplyExcel(excel,fileName);
//    }
//}
