package com.pqsoft.addTax.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.addTax.service.VatFirstPayService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.entity.Excel;
import com.pqsoft.invoice.service.InvoiceHandleService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
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
import com.pqsoft.util.StringUtils;
import com.pqsoft.util.UtilChart;

public class VatFirstPayAction extends Action{

	private String path = "addTax/";
	private VatFirstPayService service = new VatFirstPayService();
	
	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","首期款发票申请","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showFirstPayApplyPage(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("PLATFORMTYPEList", new SysDictionaryMemcached().get("业务类型"));
		context.put("param", param);
		
		
		//先加入固定的数据
		int GDNUM=13;
		
		//查询剩下的明细项
		List list=Dao.selectList("VatInvoice.aykjColumnsAllTitel");
		
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
		
		titlesName[8]="税率";
		sqlsName[8]="TAX_RATE";
		
		titlesName[9]="款项合计";
		sqlsName[9]="TOTAL_MONEY";
		
		titlesName[10]="首期租金";
		sqlsName[10]="ACCRUE_MONEY";
		
		titlesName[11]="第一期租金";
		sqlsName[11]="FIRSTRENT_MONEY";
		
		titlesName[12]="租前利息";
		sqlsName[12]="STARTLX_MONEY";
		
		
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
		context.put("columnInit", utilChart.parseColumnInit("按月开票页面字段", titlesName, sqlsName));
		
		return new ReplyHtml(VM.html(path+"vatFirstPayApplyPage.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","首期款发票申请","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply  firstPayApplyPageData(){
		Map<String,Object> param = _getParameters();
		Page pageData = service.getFirstPayApplyPageData(param);
		return new ReplyAjaxPage(pageData);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","首期款发票申请","申请全部/申请选中项"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply applyVatFirstFeeMethod(){
		Map<String,Object> param = _getParameters();
		JSONArray jsonArray = JSONArray.fromObject(param.get("sqlData").toString());
		int result = 0 ;
		List<Object> applyMess = new ArrayList<Object>();
		if(jsonArray.size()>0){
			applyMess = jsonArray ;
		}else{
			param.put("VAT_FLAG", "YES");
			param.put("INVOICE_TYPE", "增值税发票");
			applyMess = service.getAllFirstInviceMess(param);
		}
		for (Object object : applyMess) {
			Map<String,Object> newParam = (Map<String, Object>)object;
			newParam.put("CREATOR", Security.getUser().getName());
			newParam.put("FUND_TYPE", "首期付款");
			newParam.put("FUND_NAME", "首期付款");
			//插入发票详细信息
			List<String> s = new ArrayList<String>();
			
			s.add("首期租金");
//			s.add("租前利息");
			if(newParam.containsKey("PAY_WAY") && newParam.get("PAY_WAY") !=null && ("2".equals(newParam.get("PAY_WAY").toString()) ||
					"4".equals(newParam.get("PAY_WAY").toString()) || "6".equals(newParam.get("PAY_WAY").toString()))){
				s.add("本金");
				s.add("利息");
			}
			
//			s.add("手续费");
//			s.add("留购价款");
//			s.add("管理费");
//			s.add("保险费");
//			s.add("GPS费用");
//			s.add("第一期租金");
//			s.add("租前利息");
			
			//查询费用名称
			List listStr=Dao.selectList("VatInvoice.aykjColumnsAllTitel");
			for(int iii=0;iii<listStr.size();iii++){
				Map map=(Map)listStr.get(iii);
				if(map !=null && map.get("FUND_TYPE") !=null && !map.get("FUND_TYPE").equals("")){
					s.add(map.get("FUND_TYPE")+"");
				}
			}
			
			newParam.put("FIRST_TYPE", "0");
			newParam.put("BEGINNING_NAMES", s);
			newParam.put("INVOICE_TYPE", "增值税发票");
			newParam.put("IF_INVOICE", "yes");
			result += service.addInvoice(newParam);
		}
		if(result >0){
			return new ReplyAjax(true,"申请成功！");
		}else{
			return new ReplyAjax(false,"申请失败！");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","增值税发票核销","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showVatHeXiaoPage(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		List<Object> invoiceTypes = (List<Object>)new DataDictionaryMemcached().get("发票_类型名称");
    	context.put("invoiceTypes", invoiceTypes);
		//进行发票的拆分/合并
//		InvoiceHandleService handleService = new InvoiceHandleService();
//		handleService.handleInvoice();
//		handleService.splitInvoice();
		context.put("param", param);
		context.put("PLATFORMTYPEList", new SysDictionaryMemcached().get("业务类型"));
		return new ReplyHtml(VM.html(path+"vatHeXiaoPage.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","增值税发票核销","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply vatHeXiaoPageData(){
		Map<String,Object> param = _getParameters();
		Page pageData = service.getVatHeXiaoPageData(param);
		return new ReplyAjaxPage(pageData);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","增值税发票核销","全导出/导出选中项"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getVatHeXiaoExcelData(){
		Map<String,Object> param = _getParameters();
		Excel excel = new Excel();
		String type = param.containsKey("type") && param.get("TYPE") != null ? param.get("type").toString() :"";
		//校验发票金额是否超过最大上限
		int checkResult = 0 ;
		Map<String,Object> dicParam = new HashMap<String, Object>();
		dicParam.put("DIC_TYPE", "发票_上限定义");
		dicParam.put("DIC_CODE", "4");
		dicParam.putAll(service.getDicShortNameByTypeCode(dicParam));
		if(dicParam !=null && dicParam.containsKey("LIMIT_NUM") && dicParam.get("LIMIT_NUM") !=null && !"".equals(dicParam.get("LIMIT_NUM").toString())){
			Map<String,Object> limitParam = new HashMap<String, Object>();
		    limitParam.putAll(param);
		    limitParam.put("UP_LIMIT", dicParam.get("LIMIT_NUM").toString());
		    limitParam.put("INVOICE_TYPE", "增值税发票");
			checkResult = service.getUpLimitMess(limitParam);
			
		}
		
		
		
        if(checkResult > 0){
        	return new ReplyAjax(false, "存在超出上限的票据，请进行拆分！");
        }else{
        	excel = service.getVatExcelExportData(param,type );
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	String fileName = "销项票(增值税)-("+sdf.format(new Date())+")"+Math.round(Math.random()*8999+1000)+".xls";
        	return new ReplyExcel(excel,fileName);
        }
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","增值税发票核销","上传回执"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply uploadReceiptMethod(){
		List<File> files = _getFile();
		int result = service.uploadReceiptVat(files);
		if(result > 0 ){
		   return new ReplyAjax(true, "成功上传回执【"+result+"】条");	
		}else{
		   return new ReplyAjax(true, "发票上传回执失败,请检查文件格式！");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","增值税发票核销","驳回"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply rejectVat(){
		Map<String,Object> param = _getParameters();
		int result = 0 ;
		List<Object> sonInvoices = service.getSonInvoice(param);
		if(sonInvoices.size()>0){
			for (Object obj : sonInvoices) {
				Map<String,Object> sonInvoice = (Map<String,Object>)obj;
				sonInvoice.put("reJectData", sonInvoice.get("ID").toString());
				result = service.delInvoiceDetail(sonInvoice);
				result = service.delInvoiceItem(sonInvoice);
				result = service.delInvoiceMain(sonInvoice);
			}
		}
		result = service.delInvoiceDetail(param);
		result = service.delInvoiceItem(param);
		result = service.delInvoiceMain(param);
		if(result > 0 ){
			return new ReplyAjax(true,"驳回成功！");
		}else{
			return new ReplyAjax(false,"驳回失败！");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","增值税发票查询"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showVatSearchPage(){
	    Map<String,Object> param  = _getParameters();
	    VelocityContext context = new VelocityContext();
	    context.put("param", param);
	    return new ReplyHtml(VM.html(path+"vatSearchPage.vm", context));
	}   
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","增值税发票查询"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getVatSearchPageData(){
		Map<String,Object> param = _getParameters();
		Page pageData = service.getVatSearchPageData(param) ;
		return new ReplyAjaxPage(pageData);
	}
	
	 private String[] titlesName=null;
	 private String[] sqlsName = null;
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","合并开票申请","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showMergerInvoiceApplyPage(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("param", param);
		context.put("PLATFORMTYPEList", new SysDictionaryMemcached().get("业务类型"));
		
		
		//先加入固定的数据
		int GDNUM=17;
		
		//查询剩下的明细项
		List list=Dao.selectList("VatInvoice.hbkjColumnsAllTitel");
		
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
		
		titlesName[5]="税率";
		sqlsName[5]="TAX_RATE";
		
		titlesName[6]="租赁期次";
		sqlsName[6]="LEASE_TERM";
		
		titlesName[7]="合并开票期次";
		sqlsName[7]="RENT_LIST";
		
		titlesName[8]="合并租金总额";
		sqlsName[8]="RENT_PRICE_TOTAL";
		
		titlesName[9]="合并本金总额";
		sqlsName[9]="CAPITAL_PRICE_TOTAL";
		
		titlesName[10]="合并利息总额";
		sqlsName[10]="INTEREST_PRICE_TOTAL";
		
		titlesName[11]="合并管理费总额";
		sqlsName[11]="GLF_PERIOD_PRICE_TOTAL";
		
		titlesName[12]="合并手续费总额";
		sqlsName[12]="SXF_PERIOD_PRICE_TOTAL";
		
		titlesName[13]="合并利息增值税总额";
		sqlsName[13]="LXZZS_PRICE_TOTAL";
		
		titlesName[14]="违约金总额";
		sqlsName[14]="OVER_PRICE_TOTAL";
		
		titlesName[15]="是否上牌";
		sqlsName[15]="ON_CARD";
		
		titlesName[16]="首期租金";
		sqlsName[16]="ACCRUE_RENT_PRICE";
		
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
		context.put("columnInit", utilChart.parseColumnInit("合并开票页面字段", titlesName, sqlsName));
		
		return new ReplyHtml(VM.html(path+"vatMergerApplyPage.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","合并开票申请","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getVatMergerInvoiceApplyData(){
		Map<String,Object> param = _getParameters();
		Page pageData = service.getVatMergerInvoiceApplyData(param);
		return new ReplyAjaxPage(pageData);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","合并开票申请","申请"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply applyVatMergerMethod(){
		Map<String,Object> param = _getParameters();
		JSONArray jsonArray = JSONArray.fromObject(param.get("sqlData").toString());
		List<Object> applyMess = new ArrayList<Object>();
		if(jsonArray.size() >0){
			applyMess = jsonArray ;
		}else{
			param.put("VAT_FLAG", "YES");
			param.put("INVOICE_TYPE", "增值税发票");
			applyMess = service.getAllMergerInvoiceMess(param);
		}
		int result = 0 ;
		for (Object object : applyMess) {
			Map<String,Object> newParam = new HashMap<String,Object>();
			newParam.putAll((Map<String, Object>)object);
			newParam.put("CREATOR", Security.getUser().getName());
			newParam.put("FUND_TYPE", "合并发票");
			newParam.put("FUND_NAME", "合并发票");
			newParam.put("BEGINNING_NUM", StringUtils.nullToString(newParam.get("BEGINNING_NUM")));
			newParam.put("D_STATUS", "0");
//			newParam.put("TARGET_TYPE", newParam.containsKey("INVOICE_TARGET_TYPE") && newParam.get("INVOICE_TARGET_TYPE") != null ? newParam.get("INVOICE_TARGET_TYPE").toString():"");
//			newParam.put("TARGET_ID", newParam.containsKey("INVOICE_TARGET_ID") && newParam.get("INVOICE_TARGET_ID") != null ? newParam.get("INVOICE_TARGET_ID").toString():"");
			newParam.put("INVOICE_TYPE", "增值税发票");
			result += service.addInvoiceMerger(newParam);
		}
		if(result >0){
			return new ReplyAjax(true,"申请成功！");
		}else{
			return new ReplyAjax(false,"申请失败！");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","增值税发票核销","列表显示"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getDropInvoiceList(){
		Map<String,Object> param = _getParameters();
		List<Object> invoiceList = service.getInvoiceList(param);	
		return new ReplyJson(JSONArray.fromObject(invoiceList));
	}
	
 
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","增值税发票核销","合并"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply mergerInvoice(){
		InvoiceHandleService handleService = new InvoiceHandleService();
		Boolean flag = true;
		Map<String,Object> param = _getParameters();
		String msg = "合并"+handleService.handleInvoice(param)+"条成功！";
//		try {
//			handleService.handleInvoice();
//		} catch (Exception e) {
//			 flag = false;
//			 msg = e.getMessage();
//		}
		return new ReplyAjax(flag,msg);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","增值税发票核销","拆分"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply splitInvoice(){
		InvoiceHandleService handleService = new InvoiceHandleService();
		Boolean flag = true;
		Map<String,Object> param = _getParameters();
		String msg = "拆分"+handleService.splitInvoice(param)+"条成功！";
//		try {
//			;
//		} catch (Exception e) {
//			flag = false;
//			msg = "拆分失败";
//			throw new RunTimeException();
//		}
		return new ReplyAjax(flag,msg);
	}
	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name ={"票据管理","营业税发票管理","营业税发票核销","合并发票"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply splitInvoice1(){
		InvoiceHandleService handleService = new InvoiceHandleService();
		Boolean flag = true;
		String msg = "合并"+handleService.mergeInvoiceByRentList()+"条成功！";
//		try {
//			;
//		} catch (Exception e) {
//			flag = false;
//			msg = "拆分失败";
//			throw new RunTimeException();
//		}
		return new ReplyAjax(flag,msg);
	}
	
}
