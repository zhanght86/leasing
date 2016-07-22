package com.pqsoft.receipt.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.addTax.service.VatFirstPayService;
import com.pqsoft.businessTax.service.BusTaxFirstPayService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.entity.Excel;
import com.pqsoft.invoice.service.InvoiceHandleService;
import com.pqsoft.receipt.service.ReceiptService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.ReplyExcel;
import com.pqsoft.util.UtilChart;

public class ReceiptAction extends Action {
	private String path = "receipt/" ;
	private ReceiptService service = new ReceiptService();

	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	 private String[] titlesName=null;
	 private String[] sqlsName = null;
	    
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","首期款收据申请","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showReceiptFirtPayApplyPage(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("PLATFORMTYPEList", new SysDictionaryMemcached().get("业务类型"));
		context.put("param", param);
		
		//先加入固定的数据
		int GDNUM=12;
		
		//查询剩下的明细项
		List list=Dao.selectList("Receipt.sjkjColumnsAllTitel");
		
		titlesName=new String[GDNUM+list.size()];
		sqlsName=new String[GDNUM+list.size()];
		
		titlesName[0]="经销商";
		sqlsName[0]="SUP_SHORTNAME";
		
		titlesName[1]="客户名称";
		sqlsName[1]="CLIENT_NAME";
		
		titlesName[2]="融资租赁合同号";
		sqlsName[2]="LEASE_CODE";
		
		titlesName[3]="支付表号";
		sqlsName[3]="PAYLIST_CODE";
		
		titlesName[4]="业务类型";
		sqlsName[4]="PROJECT_MODEL";
		
		titlesName[5]="起租日期";
		sqlsName[5]="CONFIRM_DATE_START";
		
		titlesName[6]="应收日期";
		sqlsName[6]="BEGINNING_RECEIVE_DATA";
		
		titlesName[7]="是否上牌";
		sqlsName[7]="ON_CARD";
		
		titlesName[8]="款项合计";
		sqlsName[8]="TOTAL_MONEY";
		
		titlesName[9]="首期租金";
		sqlsName[9]="ACCRUE_MONEY";
		
		titlesName[10]="第一期租金";
		sqlsName[10]="FIRSTRENT_MONEY";
		
		titlesName[11]="租前利息";
		sqlsName[11]="STARTLX_MONEY";
		
		
		int DTNUM=0;
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			if(map !=null && map.get("FUND_TYPE") !=null && !map.get("FUND_TYPE").equals("") && map.get("SHORTNAME") !=null && !map.get("SHORTNAME").equals("")){
				titlesName[GDNUM+DTNUM]=map.get("FUND_TYPE").toString();
				sqlsName[GDNUM+DTNUM]=map.get("SHORTNAME").toString();
				DTNUM++;
			}
			
		}
		
		
		UtilChart utilChart=new UtilChart();
		context.put("columnInit", utilChart.parseColumnInit("收据开票页面字段", titlesName, sqlsName));
		
		return new ReplyHtml(VM.html(path+"receiptFirstPayApplyPage.vm", context));
	} 
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","首期款收据申请","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getReceiptFirstPayApplyData(){
		Map<String,Object> param = _getParameters();
		Page pageData = service.getReceiptFirstPayApplyPageData(param);
		return new ReplyAjaxPage(pageData);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","租金收据申请","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showReceiptRentApplyPage(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"receiptRentApplyPage.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","虚拟核销租金收据申请","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showReceiptRentVirtualApplyPage(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"receiptRentVirtualApplyPage.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","虚拟核销租金收据申请","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getReceiptRentVirtualApplyData(){
		Map<String,Object> param = _getParameters();
		Page pageData = service.getReceiptRentVirtualApplyPageData(param);
		return new ReplyAjaxPage(pageData);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","租金收据申请","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getReceiptRentApplyData(){
		Map<String,Object> param = _getParameters();
		Page pageData = service.getReceiptRentApplyPageData(param);
		return new ReplyAjaxPage(pageData);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","DB保证金收据申请","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showDbReceiptApplyPage(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("PLATFORMTYPEList", new SysDictionaryMemcached().get("业务类型"));
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"receiptDbApplyPage.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","DB保证金收据申请","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getDbReceiptApplyData(){
		Map<String,Object> param = _getParameters();
		Page pageData = service.getReceiptDbApplyPageData(param);
		return new ReplyAjaxPage(pageData);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","担保费收据申请","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showDbRReceiptApplyPage(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("PLATFORMTYPEList", new SysDictionaryMemcached().get("业务类型"));
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"receiptDBRApplyPage.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","担保费收据申请","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getDbRReceiptApplyData(){
		Map<String,Object> param = _getParameters();
		Page pageData = service.getReceiptDbRApplyPageData(param);
		return new ReplyAjaxPage(pageData);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","厂商保证金收据申请","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showCSReceiptApplyPage(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("PLATFORMTYPEList", new SysDictionaryMemcached().get("业务类型"));
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"receiptCSApplyPage.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","厂商保证金收据申请","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getCSReceiptApplyData(){
		Map<String,Object> param = _getParameters();
		Page pageData = service.getReceiptCSApplyPageData(param);
		return new ReplyAjaxPage(pageData);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","收据核销","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showReceiptHeXiaoPage(){
		Map<String,Object> param = _getParameters();
		VelocityContext  context = new VelocityContext();
		List<Object> invoiceTypes = (List<Object>)new DataDictionaryMemcached().get("发票_类型名称");
    	context.put("invoiceTypes", invoiceTypes);
    	context.put("PLATFORMTYPEList", new SysDictionaryMemcached().get("业务类型"));
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"receiptHeXiaoPage.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","收据核销","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getReceiptHeXiaoPageData(){
		Map<String,Object> param = _getParameters();
		Page pageData = service.getReceiptHeXiaoPageData(param);
		return new ReplyAjaxPage(pageData);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","收据核销","驳回"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply rejectReceipt(){
		Map<String,Object> param = _getParameters();
		int result = 0 ;
		VatFirstPayService vatService = new VatFirstPayService();
		List<Object> sonInvoices = vatService.getSonInvoice(param);
		if(sonInvoices.size()>0){
			for (Object obj : sonInvoices) {
				Map<String,Object> sonInvoice = (Map<String,Object>)obj;
				sonInvoice.put("reJectData", sonInvoice.get("ID").toString());
				result = vatService.delInvoiceDetail(sonInvoice);
				result = vatService.delInvoiceItem(sonInvoice);
				result = vatService.delInvoiceMain(sonInvoice);
			}
		}
		result = vatService.delInvoiceDetail(param);
		result = vatService.delInvoiceItem(param);
		result = vatService.delInvoiceMain(param);;
		if(result > 0 ){
			return new ReplyAjax(true,"驳回成功！");
		}else{
			return new ReplyAjax(false,"驳回失败！");
		}
	}
	
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","收据核销","全导出/导出选中项"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getReceiptExcelData(){
		Map<String,Object> param = _getParameters();
		Excel excel = new Excel();
		excel = service.getReceiptExcelData(param);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String fileName = "收据信息-("+sdf.format(new Date())+")"+Math.round(Math.random()*8999+1000)+".xls";
		return new ReplyExcel(excel,fileName);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","收据核销","上传回执"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply uploadReceiptMethod(){
		List<File> files = _getFile();
		int result = new BusTaxFirstPayService().uploadReceiptBusTax(files);
		if(result >0){
			return new ReplyAjax(true, "上传回执【"+result+"】条");
		}else{
			return new ReplyAjax(true, "上传回执失败，请检查文件格式！");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","DB保证金收据申请","申请全部/申请选中项"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply applyDbFeeMethod(){
		Map<String,Object> param = _getParameters();
		VatFirstPayService subService = new VatFirstPayService();
		JSONArray jsonArray = JSONArray.fromObject(param.get("sqlData").toString());
		List<Object> applyMess = new ArrayList<Object>();
		if(jsonArray.size() > 0){
			applyMess = jsonArray ;
		}else{
			param.put("INVOICE_TYPE", "收据");
			applyMess = service.getAllDbReceiptMess(param);
		}
		int result = 0 ;
		for (Object object : applyMess) {
			Map<String,Object> newParam = (Map<String, Object>)object;
			newParam.put("CREATOR", Security.getUser().getName());
			newParam.put("FUND_TYPE", "项目费用");
			newParam.put("FUND_NAME", "供应商保证金");
			//插入发票详细信息
			List<String> s = new ArrayList<String>();
			s.add("供应商保证金");
			newParam.put("D_STATUS", "0");
			newParam.put("BEGINNING_NAMES", s);
			newParam.put("INVOICE_TYPE", "收据");
			result += subService.addInvoice(newParam);
		}
		if(result >0){
			return new ReplyAjax(true,"申请成功！");
		}else{
			return new ReplyAjax(false,"申请失败！");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","担保费收据申请","申请全部/申请选中项"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply applyDBRFeeMethod(){
		Map<String,Object> param = _getParameters();
		VatFirstPayService subService = new VatFirstPayService();
		JSONArray jsonArray = JSONArray.fromObject(param.get("sqlData").toString());
		List<Object> applyMess = new ArrayList<Object>();
		if(jsonArray.size() > 0){
			applyMess = jsonArray ;
		}else{
			param.put("INVOICE_TYPE", "收据");
			applyMess = service.getAllDBRReceiptMess(param);
		}
		int result = 0 ;
		for (Object object : applyMess) {
			Map<String,Object> newParam = (Map<String, Object>)object;
			newParam.put("CREATOR", Security.getUser().getName());
			newParam.put("FUND_TYPE", "项目费用");
			newParam.put("FUND_NAME", "担保费");
			//插入发票详细信息
			List<String> s = new ArrayList<String>();
			s.add("担保费");
			newParam.put("D_STATUS", "0");
			newParam.put("BEGINNING_NAMES", s);
			newParam.put("INVOICE_TYPE", "收据");
			result += subService.addInvoice(newParam);
		}
		if(result >0){
			return new ReplyAjax(true,"申请成功！");
		}else{
			return new ReplyAjax(false,"申请失败！");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","厂商保证金收据申请","申请全部/申请选中项"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply applyCSFeeMethod(){
		Map<String,Object> param = _getParameters();
		VatFirstPayService subService = new VatFirstPayService();
		JSONArray jsonArray = JSONArray.fromObject(param.get("sqlData").toString());
		List<Object> applyMess = new ArrayList<Object>();
		if(jsonArray.size() > 0){
			applyMess = jsonArray ;
		}else{
			param.put("INVOICE_TYPE", "收据");
			applyMess = service.getAllCSReceiptMess(param);
		}
		int result = 0 ;
		for (Object object : applyMess) {
			Map<String,Object> newParam = (Map<String, Object>)object;
			newParam.put("CREATOR", Security.getUser().getName());
			newParam.put("FUND_TYPE", "项目费用");
			newParam.put("FUND_NAME", "厂商保证金");
			//插入发票详细信息
			List<String> s = new ArrayList<String>();
			s.add("厂商保证金");
			newParam.put("D_STATUS", "0");
			newParam.put("BEGINNING_NAMES", s);
			newParam.put("INVOICE_TYPE", "收据");
			result += subService.addInvoice(newParam);
		}
		if(result >0){
			return new ReplyAjax(true,"申请成功！");
		}else{
			return new ReplyAjax(false,"申请失败！");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","首期款收据申请","申请全部/申请选中项"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply applyReceiptFirstPayFeeMethod(){
		Map<String,Object> param = _getParameters();
		VatFirstPayService subService = new VatFirstPayService();
		JSONArray jsonArray = JSONArray.fromObject(param.get("sqlData").toString());
		List<Object> applyMess = new ArrayList<Object>();
		if(jsonArray.size() > 0 ){
			applyMess = jsonArray ;
		}else{
			param.put("INVOICE_TYPE", "收据");
			applyMess = service.getAllFirstReceiptMess(param);
		}
		int result = 0 ;
		for (Object object : applyMess) {
			Map<String,Object> newParam = (Map<String, Object>)object;
			newParam.put("CREATOR", Security.getUser().getName());
			newParam.put("FUND_TYPE", "首期付款");
			newParam.put("FUND_NAME", "首期付款");
			//插入发票详细信息
			List<String> s = new ArrayList<String>();
			
			
			s.add("首期租金");
			if(newParam.containsKey("PAY_WAY") && newParam.get("PAY_WAY") !=null && ("2".equals(newParam.get("PAY_WAY").toString()) ||
					"4".equals(newParam.get("PAY_WAY").toString()) || "6".equals(newParam.get("PAY_WAY").toString()))){
				s.add("本金");
				s.add("利息");
			}
			
//			s.add("客户保证金");
//			s.add("手续费");
//			s.add("留购价款");
//			s.add("管理费");
//			s.add("保险费");
//			s.add("GPS费用");
//			s.add("第一期租金");
//			s.add("租前利息");
			
			//查询费用名称
			List listStr=Dao.selectList("Receipt.sjkjColumnsAllTitel");
			for(int iii=0;iii<listStr.size();iii++){
				Map map=(Map)listStr.get(iii);
				if(map !=null && map.get("FUND_TYPE") !=null && !map.get("FUND_TYPE").equals("")){
					s.add(map.get("FUND_TYPE")+"");
				}
			}
			
			newParam.put("FIRST_TYPE", "0");
			newParam.put("BEGINNING_NAMES", s);
			newParam.put("INVOICE_TYPE", "收据");
			newParam.put("IF_RECEIPT", "yes");
			result += subService.addInvoice(newParam);
		}
		if(result >0){
			return new ReplyAjax(true,"申请成功！");
		}else{
			return new ReplyAjax(false,"申请失败！");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","租金收据申请","申请全部/申请选中项"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply applyReceiptRentMethod(){
		Map<String,Object> param = _getParameters();
		VatFirstPayService subService = new VatFirstPayService();
		JSONArray jsonArray = JSONArray.fromObject(param.get("sqlData").toString());
		List<Object> applyMess = new ArrayList<Object>();
		if(jsonArray.size() > 0 ){
			applyMess = jsonArray ;
		}else{
			param.put("INVOICE_TYPE", "收据");
			applyMess = service.getAllRentReceiptMess(param);
		}
		int result = 0 ;
		//插入发票详细信息
		List<String> s = new ArrayList<String>();
		for (Object object : applyMess) {
			s.clear();
			Map<String,Object> newParam = (Map<String, Object>)object;
			newParam.put("CREATOR", Security.getUser().getName());
			newParam.put("FUND_TYPE", "租金");
			newParam.put("FUND_NAME", "租金");
			s.add("本金");
			s.add("利息");
			s.add("手续费");
			s.add("管理费");
			s.add("利息增值税");
			
			String D_STATUS = "1";
			newParam.put("D_STATUS", D_STATUS);
			newParam.put("BEGINNING_NAMES", s);
			newParam.put("INVOICE_TYPE", "收据");
			newParam.put("IF_RECEIPT", "yes");
			result += subService.addInvoice(newParam);
		}
		if(result >0){
			return new ReplyAjax(true,"申请成功！");
		}else{
			return new ReplyAjax(false,"申请失败！");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","收据管理","虚拟核销租金收据申请","申请全部/申请选中项"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply applyReceiptRentVirtualMethod(){
		Map<String,Object> param = _getParameters();
		VatFirstPayService subService = new VatFirstPayService();
		JSONArray jsonArray = JSONArray.fromObject(param.get("sqlData").toString());
		List<Object> applyMess = new ArrayList<Object>();
		if(jsonArray.size() > 0 ){
			applyMess = jsonArray ;
		}else{
			param.put("INVOICE_TYPE", "收据");
			applyMess = service.getAllRentReceiptVirtualMess(param);
		}
		int result = 0 ;
		//插入发票详细信息
		List<String> s = new ArrayList<String>();
		for (Object object : applyMess) {
			s.clear();
			Map<String,Object> newParam = (Map<String, Object>)object;
			newParam.put("CREATOR", Security.getUser().getName());
			newParam.put("FUND_TYPE", "租金");
//			newParam.put("FUND_NAME", "虚拟核销_租金");
			newParam.put("INVOICE_TO_NAME", newParam.get("SUP_SHORTNAME")) ;
			System.out.println("----------------------ITEM_FLAG="+newParam.get("ITEM_FLAG"));
			if("违约金".equals(newParam.get("ITEM_FLAG"))){
				newParam.put("FUND_NAME", "虚拟核销_违约金");
			    s.add("违约金") ;
			}else{
				newParam.put("FUND_NAME", "虚拟核销_租金");
				s.add("本金");
				s.add("利息");
			}
						
			String D_STATUS = "1";
			newParam.put("D_STATUS", D_STATUS);
			newParam.put("BEGINNING_NAMES", s);
			newParam.put("INVOICE_TYPE", "收据");
			newParam.put("IF_RECEIPT", "yes");
			result += subService.addVisualInvoice(newParam);
		}
		if(result >0){
			return new ReplyAjax(true,"申请成功！");
		}else{
			return new ReplyAjax(false,"申请失败！");
		}
	}
	
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"票据管理","收据管理","收据核销","合并"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply mergerReceiptMethod(){
		InvoiceHandleService handleService = new InvoiceHandleService();
    	String msg = "合并"+handleService.mergeReceipt()+"条成功！";
		return new ReplyAjax(true , msg);
	}
	
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name ={"票据管理","收据管理","收据核销","拆分"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply splitReceiptMethod(){
//		InvoiceHandleService handleService = new InvoiceHandleService();
//    	String msg = "拆分"+handleService.splitInvoice()+"条成功！";
//		return new ReplyAjax(true, msg);
//	}
	public static void main(String[] args) {
		Timer t = new Timer() ;
		t.schedule(new MyTimeTest(), 10000);
	}	
}

class  MyTimeTest extends  TimerTask {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
