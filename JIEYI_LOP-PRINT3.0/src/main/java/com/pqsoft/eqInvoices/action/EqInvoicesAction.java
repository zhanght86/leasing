package com.pqsoft.eqInvoices.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.entity.Excel;
import com.pqsoft.eqInvoices.service.EqInvoicesService;
import com.pqsoft.payment.service.paymentService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.ReplyExcel;


public class EqInvoicesAction extends Action {

    private String path = "eqInvoices/";
    private EqInvoicesService service = new EqInvoicesService();
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "租赁物发票录入", "列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("param", context);
		context.put("INVOICEAMOUNT", service.getInvoiceAmount(param)) ;
		context.put("PLATFORMTYPEList", new SysDictionaryMemcached().get("业务类型"));
		return new ReplyHtml(VM.html(path+"eqInvoiceManager.vm", context));
	}
	

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "租赁物发票录入","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "武燕飞")
	public Reply getInvoiceAmount(){
		Map<String,Object> param = _getParameters();
		Map<String,Object> invoices = service.getInvoiceAmount(param);
		return new ReplyAjax(JSONObject.fromObject(invoices));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "租赁物发票录入","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getPageData(){
		Map<String,Object> param = _getParameters();
		Page pageData = service.getPageData(param);
		return new ReplyAjaxPage(pageData);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "租赁物发票录入","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getInvoiceData(){
		Map<String,Object> param = _getParameters();
		List<Object> invoices = service.getEqInvoiceList(param);
		return new ReplyJson(JSONArray.fromObject(invoices));
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "租赁物发票录入","新建发票"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply addManualInvoice(){
		Map<String,Object> param = _getParameters();
		param.put("CREATE_USER", Security.getUser().getName().toString());
		int result = service.addEqInvoice(param);
//		service.updatePaymentEqInvoice(param);//更新放款表发票信息
		Boolean flag = true;
		String msg = "";
		if(result > 0 ){
			flag = true;
			msg = "操作成功！";
		}else{
			flag = false ; 
			msg = "操作失败！";
		}
		return new ReplyAjax(flag , msg);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "租赁物发票录入","修改"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply updateEqInvoice(){
		Map<String,Object> param = _getParameters();
		Boolean flag= true;
		String msg = "";
		int result = service.updateEqInvoice(param);
//		service.updatePaymentEqInvoice(param);//修改放款发票
		if(result >0){
			flag = true;
			msg = "操作成功！";
				
		}else{
			flag = true;
			msg = "操作失败！";
		}
		return new ReplyAjax(flag , msg);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "租赁物发票录入","认证通过/认证不通过"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply approveInvoice(){
		Map<String,Object> param = _getParameters();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		param.put("LEGA_TIME", sdf.format(new Date()));
		param.put("LEGA_USER", Security.getUser().getName());
		int result = service.updateEqInvoice(param);
		//项目放款回更
		if(param.containsKey("LEGA_STATUS") && param.get("LEGA_STATUS") !=null && "1".equals(param.get("LEGA_STATUS").toString())){
			paymentService payService = new paymentService();
			Map<String,Object> InvoiceMess = service.getInvoiceDetail(param);
			Map<String,Object> newParam = new HashMap<String, Object>();
			newParam.put("INVOICE_NUM", InvoiceMess.containsKey("INVOICE_CODE") && InvoiceMess.get("INVOICE_CODE")!=null ? InvoiceMess.get("INVOICE_CODE").toString():"");
			newParam.put("PROJECT_CODE", InvoiceMess.containsKey("PRO_CODE") && InvoiceMess.get("PRO_CODE")!=null ? InvoiceMess.get("PRO_CODE").toString():"");
			newParam.put("PAY_TYPE", 1);
			newParam.put("INVOICE_DATE", InvoiceMess.containsKey("INVOICE_DATE") && InvoiceMess.get("INVOICE_DATE")!=null ? InvoiceMess.get("INVOICE_DATE").toString():"");
			payService.updatePMByDate(newParam);
		}
		String msg ="";
		Boolean flag = true;
		if(result >0){
			msg ="操作成功！";
			flag = true ;
		}else{
			msg = "操作失败！";
			flag = false;
		}
		return new ReplyAjax(flag,msg);
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "租赁物发票录入","发票认证"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply addInportExcel(){
		List<File> files = _getFile();
		int result = service.uploadEqInvoiceExcel(files);
		if(result >0){
			return new ReplyAjax(true, "上传发票【"+result+"】条！");
		}else{
		    return new ReplyAjax(false,"无发票上传成功！");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "租赁物发票录入","上传发票"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply checkEQInvoiceExcel(){
		List<File> files = _getFile();
		int result = service.uploadCheckInvoiceMess(files);
		if(result> 0 ){
			return new ReplyAjax(true, "验证发票【"+result+"】条！");
		}else{
			return new ReplyAjax(false,"无验证数据！");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "租赁物发票录入","删除"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply delInvoice(){
		Map<String,Object> param = _getParameters();
		int result = service.delEqInvoice(param);
		Boolean flag = true;
		String msg = "";
		if(result > 0){
			flag = true ;
			msg = "操作成功！";
		}else{
			flag = false;
			msg = "操作失败！";
		}
		return new ReplyAjax(flag , msg);
	} 
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "租赁物发票录入","查看设备发票信息"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showEqInvoice(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		Map<String,Object> eqInvoiceMess = service.getOneEqInvoice(param);
		context.put("eqInvoice", eqInvoiceMess);
		return new ReplyHtml(VM.html(path+"", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "租赁物发票录入","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getProEqMess(){
		Map<String,Object> param = _getParameters();
		List<Object> proEqList = service.getProEqMess(param);
		return new ReplyJson(JSONArray.fromObject(proEqList));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "租赁物发票录入","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getProEqDetail(){
		Map<String,Object> param = _getParameters();
		Map<String,Object> eqDetail = service.getOneEqMess(param);
		return new ReplyJson(JSONObject.fromObject(eqDetail));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "租赁物发票录入","修改"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getInvoiceDetail(){
		Map<String,Object> param = _getParameters();
		Map<String,Object> invoiceDetail = service.getInvoiceDetail(param);
		List<Object> data = service.getProEqMess(invoiceDetail);
		invoiceDetail.put("data", data);
		return new ReplyJson(JSONObject.fromObject(invoiceDetail));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "租赁物发票录入","导出EXCEL"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply exportInvoiceMess(){
		Map<String,Object> param = _getParameters();
		Excel excel = new Excel();
		excel = service.getExcelInvoices(param);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String fileName = "租赁物发票登记表-("+sdf.format(new Date())+")"+Math.round(Math.random()*8999+1000)+".xls";
		return new ReplyExcel(excel,fileName);
	}
	
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "代收违约金发票录入", "列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getPageDun() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("param", context);
		context.put("INVOICEAMOUNT", service.getInvoiceDun(param)) ;
		context.put("PLATFORMTYPEList", new SysDictionaryMemcached().get("业务类型"));
		return new ReplyHtml(VM.html(path+"dunInvoiceManager.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "代收违约金发票录入","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getPageDataDun(){
		Map<String,Object> param = _getParameters();
		Page pageData = service.getPageDataDun(param);
		return new ReplyAjaxPage(pageData);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "代收违约金发票录入","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "武燕飞")
	public Reply getInvoiceDun(){
		Map<String,Object> param = _getParameters();
		Map<String,Object> invoices = service.getInvoiceDun(param);
		return new ReplyAjax(JSONObject.fromObject(invoices));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "代收违约金发票录入","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getProDunMess(){
		Map<String,Object> param = _getParameters();
		List<Object> proDunList = service.getProDunMess(param);
		return new ReplyJson(JSONArray.fromObject(proDunList));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "代收违约金发票录入","新建发票"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply addDunManualInvoice(){
		Map<String,Object> param = _getParameters();
		param.put("CREATE_USER", Security.getUser().getName().toString());
		int result = service.addDunInvoice(param);
//		service.updatePaymentEqInvoice(param);//更新放款表发票信息
		Boolean flag = true;
		String msg = "";
		if(result > 0 ){
			flag = true;
			msg = "操作成功！";
		}else{
			flag = false ; 
			msg = "操作失败！";
		}
		return new ReplyAjax(flag , msg);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "代收违约金发票录入","修改"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getInvoiceDetailDun(){
		Map<String,Object> param = _getParameters();
		Map<String,Object> invoiceDetail = service.getInvoiceDetail(param);
		return new ReplyJson(JSONObject.fromObject(invoiceDetail));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "代收违约金发票录入","修改"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply updateDunInvoice(){
		Map<String,Object> param = _getParameters();
		Boolean flag= true;
		String msg = "";
		int result = service.updateDunInvoice(param);
		if(result >0){
			flag = true;
			msg = "操作成功！";
				
		}else{
			flag = true;
			msg = "操作失败！";
		}
		return new ReplyAjax(flag , msg);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "代收违约金发票录入","认证通过/认证不通过"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply approveDunInvoice(){
		Map<String,Object> param = _getParameters();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		param.put("LEGA_TIME", sdf.format(new Date()));
		param.put("LEGA_USER", Security.getUser().getName());
		int result = service.updateDunHGInvoice(param);
		
		String msg ="";
		Boolean flag = true;
		if(result >0){
			msg ="操作成功！";
			flag = true ;
		}else{
			msg = "操作失败！";
			flag = false;
		}
		return new ReplyAjax(flag,msg);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "代收违约金发票录入","删除"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply delDunInvoice(){
		Map<String,Object> param = _getParameters();
		int result = service.delDunInvoice(param);
		Boolean flag = true;
		String msg = "";
		if(result > 0){
			flag = true ;
			msg = "操作成功！";
		}else{
			flag = false;
			msg = "操作失败！";
		}
		return new ReplyAjax(flag , msg);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "代收违约金发票录入","发票认证"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply addDunInportExcel(){
		List<File> files = _getFile();
		int result = service.uploadDunInvoiceExcel(files);
		if(result >0){
			return new ReplyAjax(true, "上传发票【"+result+"】条！");
		}else{
		    return new ReplyAjax(false,"无发票上传成功！");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "代收违约金发票录入","上传发票"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply checkDunInvoiceExcel(){
		List<File> files = _getFile();
		int result = service.uploadDunCheckInvoiceMess(files);
		if(result> 0 ){
			return new ReplyAjax(true, "验证发票【"+result+"】条！");
		}else{
			return new ReplyAjax(false,"无验证数据！");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理", "代收违约金发票录入","导出EXCEL"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply exportInvoiceDunMess(){
		Map<String,Object> param = _getParameters();
		Excel excel = new Excel();
		excel = service.getExcelInvoicesDun(param);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String fileName = "代收违约金发票登记表-("+sdf.format(new Date())+")"+Math.round(Math.random()*8999+1000)+".xls";
		return new ReplyExcel(excel,fileName);
	}
}
