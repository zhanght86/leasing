//package com.pqsoft.businessTax.action;
//
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import net.sf.json.JSONArray;
//
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.addTax.service.VatFirstPayService;
//import com.pqsoft.businessTax.service.BusTaxFirstPayService;
//import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
//import com.pqsoft.entity.Excel;
//import com.pqsoft.invoice.service.InvoiceHandleService;
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
//public class BusTaxFirstPayAction extends Action {
//    private String path = "businessTax/";
//    private BusTaxFirstPayService service = new  BusTaxFirstPayService();
//	
//    @Override
//	public Reply execute() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
//    @aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","营业税发票管理","首期款发票申请","列表"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply showBusFirstPayApplyPage(){
//		Map<String,Object> param = _getParameters();
//		VelocityContext context = new VelocityContext();
//		context.put("param", param);
//		return new ReplyHtml(VM.html(path+"busFirstPayApplyPage.vm", context));
//	}
//	
//    @aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","营业税发票管理","首期款发票申请","查询"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply getBusFirstPayApplyPageData(){
//		Map<String,Object> param = _getParameters();
//		Page pageData = service.getBusFirstPayApplyData(param);
//		return new ReplyAjaxPage(pageData);
//	}
//
//    @aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","营业税发票管理","营业税票据核销","列表"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//    public Reply showBusTaxHexiaoPage(){
//    	Map<String,Object> param = _getParameters();
//    	VelocityContext context = new VelocityContext();
//    	List<Object> invoiceTypes = (List<Object>)new DataDictionaryMemcached().get("发票_类型名称");
//    	context.put("invoiceTypes", invoiceTypes);
//    	context.put("param", param);
//    	return new ReplyHtml(VM.html(path+"busTaxHeXiaoPage.vm", context));
//    }
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","营业税发票管理","营业税票据核销","查询"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//    public Reply getBusTaxHeXiaoPageData(){
//    	Map<String,Object> param = _getParameters();
//    	Page pageData = service.getBusTaxHexiaoData(param);
//    	return new ReplyAjaxPage(pageData);
//    }
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","营业税发票管理","营业税发票核销","导出"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//    public Reply exportExportBusTaxMess(){
//    	Map<String,Object> param = _getParameters();
//		Excel excel = new Excel();
//		String type = param.containsKey("type") && param.get("type") !=null ? param.get("type").toString():"";
//		//校验发票金额是否超过最大上限
//		VatFirstPayService vatService = new VatFirstPayService();
//		int checkResult = 0 ;
//		Map<String,Object> dicParam = new HashMap<String, Object>();
//		dicParam.put("DIC_TYPE", "发票_上限定义");
//		dicParam.put("DIC_CODE", "2");
//		dicParam = vatService.getDicShortNameByTypeCode(dicParam);
//		if(dicParam !=null && dicParam.containsKey("LIMIT_NUM") && dicParam.get("LIMIT_NUM") !=null && !"".equals(dicParam.get("LIMIT_NUM").toString())){
//		    Map<String,Object> limitParam = new HashMap<String, Object>();
//		    limitParam.put("UP_LIMIT", dicParam.get("LIMIT_NUM").toString());
//		    limitParam.put("INVOICE_TYPE", "营业税发票");
//			checkResult = vatService.getUpLimitMess(limitParam);
//		}
//        if(checkResult > 0){
//        	return new ReplyAjax(false, "存在超出上限的票据，请进行拆分！");
//        }else{
//			excel = service.getExcelBusTaxData(param, type);
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		    String fileName = "销项票(营业税)-("+sdf.format(new Date())+")"+Math.round(Math.random()*8999+1000)+".xls";
//			return new ReplyExcel(excel,fileName);
//        }
//    }
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","营业税发票管理","营业税发票核销","上传回执"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//    public Reply uploadReceiptMethod(){
//    	List<File> files = _getFile();
//		int result = service.uploadReceiptBusTax(files);
//		if(result >0){
//			return new ReplyAjax(true, "上传回执【"+result+"】条");
//		}else{
//			return new ReplyAjax(true, "上传回执失败，请检查文件格式！");
//		}
//    }
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","营业税发票管理","营业税发票核销","驳回"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply rejectBusTax(){
//		Map<String,Object> param = _getParameters();
//		int result = 0 ;
//		VatFirstPayService vatService = new VatFirstPayService();
//		List<Object> sonInvoices = vatService.getSonInvoice(param);
//		if(sonInvoices.size()>0){
//			for (Object obj : sonInvoices) {
//				Map<String,Object> sonInvoice = (Map<String,Object>)obj;
//				sonInvoice.put("reJectData", sonInvoice.get("ID").toString());
//				result = vatService.delInvoiceDetail(sonInvoice);
//				result = vatService.delInvoiceItem(sonInvoice);
//				result = vatService.delInvoiceMain(sonInvoice);
//			}
//		}
//		result = vatService.delInvoiceDetail(param);
//		result = vatService.delInvoiceItem(param);
//		result = vatService.delInvoiceMain(param);
//		if(result > 0 ){
//			return new ReplyAjax(true,"驳回成功！");
//		}else{
//			return new ReplyAjax(false,"驳回失败！");
//		}
//	}
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","营业税发票管理","营业税发票核销","合并"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//    public Reply mergerBusTaxInvoice(){
//    	InvoiceHandleService handleService = new InvoiceHandleService();
//    	String msg = "合并"+handleService.mergeInvoiceByRentList()+"条成功！";
//    	return new ReplyAjax(true, msg);
//    }
//    
////    @aAuth(type = aAuth.aAuthType.USER)
////	@aPermission(name ={"票据管理","营业税发票管理","营业税发票核销","拆分"})
////	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
////    public Reply splitBusTaxInvoice(){
////    	InvoiceHandleService handleService = new InvoiceHandleService();
////    	String msg = "拆分"+handleService.splitInvoice()+"条成功！";
////    	return new ReplyAjax(true,msg);
////    }
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","营业税发票管理","合并开票申请","列表"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//    public Reply showBusTaxMergerApplyPage(){
//    	VelocityContext context = new VelocityContext();
//    	Map<String,Object> param = _getParameters();
//    	context.put("param", param);
//    	return new ReplyHtml(VM.html(path+"busTaxMergerApplyPage.vm", context));
//    }
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","营业税发票管理","合并开票申请","查询"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//    public Reply getBusTaxMergerApplyData(){
//    	Map<String,Object> param = _getParameters();
//    	Page pageData = service.getBusTaxMergerApplyData(param);
//    	return new ReplyAjaxPage(pageData);
//    }
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","营业税发票管理","合并开票申请","申请"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//    public Reply applyBusTaxMergerMethod(){
//    	Map<String,Object> param = _getParameters();
//    	VatFirstPayService inService = new VatFirstPayService();
//    	JSONArray jsonArray = JSONArray.fromObject(param.get("sqlData").toString());
//    	List<Object> applyMess = new ArrayList<Object>();
//    	if(jsonArray.size() > 0){
//    		applyMess = jsonArray ;
//    	}else{
//    		VatFirstPayService vatService = new VatFirstPayService();
//    		param.put("BUS_FLAG", "YES");
//    		applyMess = vatService.getAllMergerInvoiceMess(param);
//    	}
//		int result = 0 ;
//		for (Object object : applyMess) {
//			Map<String,Object> newParam = new HashMap<String,Object>();
//			newParam.putAll((Map<String, Object>)object);
//			newParam.put("CREATOR", Security.getUser().getName());
//			newParam.put("FUND_TYPE", "合并发票");
//			newParam.put("FUND_NAME", "合并发票");
//			newParam.put("D_STATUS", "0");
//			newParam.put("BEGINNING_NUM", StringUtils.nullToString(newParam.get("BEGINNING_NUM")));
//			newParam.put("TARGET_TYPE", newParam.containsKey("INVOICE_TARGET_TYPE") && newParam.get("INVOICE_TARGET_TYPE") != null ? newParam.get("INVOICE_TARGET_TYPE").toString():"");
//			newParam.put("TARGET_ID", newParam.containsKey("INVOICE_TARGET_ID") && newParam.get("INVOICE_TARGET_ID") != null ? newParam.get("INVOICE_TARGET_ID").toString():"");
//			newParam.put("INVOICE_TYPE", "营业税发票");
//			result += inService.addInvoiceMerger(newParam);
//		}
//    	if(result >0){
//    		return new ReplyAjax(true, "操作成功！");
//    	}else{
//    		return new ReplyAjax(false,"操作失败！");
//    	}
//    }
//    
//    @aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","营业税发票管理","首期款发票申请","申请"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//    public Reply applyBusFirstFeeMethod(){
//    	Map<String,Object> param = _getParameters();
//    	VatFirstPayService inService = new VatFirstPayService();
//    	JSONArray jsonArray = JSONArray.fromObject(param.get("sqlData").toString());
//		int result = 0 ;
//		for (Object object : jsonArray) {
//			Map<String,Object> newParam = (Map<String, Object>)object;
//			newParam.put("CREATOR", Security.getUser().getName());
//			newParam.put("FUND_TYPE", "首期付款");
//			newParam.put("FUND_NAME", "首期付款");
//			//插入发票详细信息
//			List<String> s = new ArrayList<String>();
//			s.add("手续费");
//			s.add("起租租金");
//			if(newParam.containsKey("PAY_WAY") && newParam.get("PAY_WAY") !=null && "2".equals(newParam.get("PAY_WAY").toString()) ||
//					"4".equals(newParam.get("PAY_WAY").toString()) || "6".equals(newParam.get("PAY_WAY").toString())){
//				s.add("本金");
//			}
//			newParam.put("FIRST_TYPE", "0");
//			newParam.put("BEGINNING_NAMES", s);
//			newParam.put("INVOICE_TYPE", "营业税发票");
//			newParam.put("IF_INVOICE", "yes");
//			result += inService.addInvoice(newParam);
//		}
//    	if(result >0){
//    		return new ReplyAjax(true, "操作成功！");
//    	}else{
//    		return new ReplyAjax(false,"操作失败！");
//    	}
//    }
//    
//}
