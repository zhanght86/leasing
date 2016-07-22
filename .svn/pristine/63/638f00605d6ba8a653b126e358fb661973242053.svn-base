//package com.pqsoft.vehicleInvoice.action;
//
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//import net.sf.json.JSONArray;
//
//import org.apache.activemq.util.ByteArrayInputStream;
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.entity.Excel;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Dao;
//import com.pqsoft.skyeye.api.Page;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjax;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyFile;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.api.SkyEye;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.exception.ActionException;
//import com.pqsoft.skyeye.rbac.api.Security;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//import com.pqsoft.util.BaseUtil;
//import com.pqsoft.util.Invoice;
//import com.pqsoft.util.InvoiceUtil;
//import com.pqsoft.util.ReplyExcel;
//import com.pqsoft.util.StringUtils;
//import com.pqsoft.vehicleInvoice.service.VehicleInvoiceService;
//
//public class VehicleInvoiceAction extends Action {
//
//	private String path = "vehicleInvoice/";
//	private VehicleInvoiceService service = new  VehicleInvoiceService();
//	
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aPermission(name ={"票据管理","机动车票据管理"," "})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	@Override
//	public Reply execute() {
//		return null;
//	}
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","机动车票据管理","机动车票据申请","列表"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply showVehApplyPage(){
//		VelocityContext context = new VelocityContext();
//		Map<String,Object> param = _getParameters();
//		param.put("param", param);
//		return new ReplyHtml(VM.html(path+"vehInvoiceApplyPage.vm", context));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","机动车票据管理","机动车票据申请","查询"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply getVehApplyPagedata(){
//		Map<String,Object> param = _getParameters();
//		Page pagedata = service.getApplyPageData(param);
//		return new ReplyAjaxPage(pagedata);
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","机动车票据管理","机动车票据申请","导出excel"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply exportExcelInvoiceMess(){
//		Map<String,Object> param = _getParameters();
//		Excel excel = new Excel();
//		excel = service.exportInvoiceExcel(param,"apply");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//	    String fileName = "机动车票-("+sdf.format(new Date())+")"+Math.abs(new Random(10000).nextInt())+".xls";
//		return new ReplyExcel(excel,fileName);
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","机动车票据管理","机动车票据申请","申请页导出XML"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply exportInvoiceXML(){
//		Map<String,Object> param = _getParameters();
//        InputStream stream = null;
//		String fileName ="";
//		Boolean flag = true;
//		String msg = "";
//		try {
//			String XMLstr = service.exportInvoiceXML(param);
//			if("0".equals(XMLstr)){
//				return new ReplyAjax(flag, "无数据导出成功！");
//			}else{
//				stream = new ByteArrayInputStream(XMLstr.getBytes("gb2312"));
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				fileName = "Vehicle-"+sdf.format(new Date())+")"+Math.abs(new Random(10000).nextInt())+".xml";
//				return new ReplyFile(stream,fileName);
//			}
//		}catch (NullPointerException e){
//			logger.error(e, e);
//			Dao.rollback();
//			flag = false;
//			msg += e.getMessage()+"导出失败！";
//			return new ReplyAjax(flag, msg);
//			
//		} catch (Exception e) {
//			logger.error(e, e);
//			Dao.rollback();
//			flag = false;
//			msg = "导出出错！";
//			return new ReplyAjax(flag, msg);
//		}
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","机动车票据管理","机动车票据核销","列表"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply showVerificationPage(){
//		VelocityContext context = new VelocityContext();
//		Map<String,Object> param = _getParameters();
//		param.put("param", param);
//		return new ReplyHtml(VM.html(path+"verificInvoicePage.vm", context));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","机动车票据管理","机动车票据核销","查询"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply invoiceFalseReasion(){
//		Map<String,Object> param = _getParameters();
//		String msg = service.invoiceFalseReasion(param);
//		return new ReplyAjax(true,StringUtils.nullToOther(msg, "无此项目，请输入完整项目编号！"));
//	}
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","机动车票据管理","机动车票据核销","查询"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply getVerificationPageData(){
//		Map<String,Object> param = _getParameters();
//		Page pagedata = service.getVerPageData(param);
//		return new ReplyAjaxPage(pagedata);
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","机动车票据管理","机动车票据核销","导出excel"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply exportVerificExcel(){
//		Map<String,Object> param = _getParameters();
//		Excel excel = new Excel();
//		excel = service.exportInvoiceExcel(param,"verific");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//	    String fileName = "机动车票-("+sdf.format(new Date())+")"+Math.abs(new Random(10000).nextInt())+".xls";
//		return new ReplyExcel(excel,fileName);
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","机动车票据管理","机动车票据核销","上传发票回执"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply verificUploadMethod(){
//		Map<String,Object> fileMess = service.uploadFileForOne(SkyEye.getRequest());
//		StringBuffer xml = new StringBuffer();
//		String realPath = fileMess.get("FILE_PATH").toString();
//		int count = 0 ;
//		try {
//			BufferedReader rd = new BufferedReader(new InputStreamReader(new FileInputStream(realPath),"gb2312"));
//			String tempStr = "";
//			while((tempStr = rd.readLine()) != null && tempStr.length()>0){
//				xml.append(tempStr);
//			}
//			System.out.println("======"+xml);
//			List<Invoice> invoices  = InvoiceUtil.parseXml(xml.toString());
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			
//			for(Invoice invoice : invoices){
//				Map<String,Object> iv = new HashMap<String, Object>();
//				iv.put("CLSBDH", invoice.getClsbdh().toString());
//				iv.put("INVOICE_DATE", invoice.getInvoice_date().toString());
//				iv.put("INVOICE_ID", invoice.getInvoice_id().toString());
//				iv.put("ZZSSE", invoice.getZzsse().toString());
//				iv.put("MODIFIER", Security.getUser().getName().toString());
//				iv.put("MODIFY_DATE", sdf.format(new Date()));
//				int result = service.updateVehicleMess(iv);
//				count += result ;
//			}
//		} catch (Exception e) {
//			throw new ActionException("解析出错！");
//		}
//		 return new ReplyAjax(count);
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","机动车票据管理","机动车票据查询","列表"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply showVehSearhPage(){
//		VelocityContext context = new VelocityContext();
//		Map<String,Object> param = _getParameters();
//		Map<String,Object> SUP_MAP = BaseUtil.getSup();
//		context.put("SUP_MAP", SUP_MAP);
//		context.put("param",param);
//		return new ReplyHtml(VM.html(path+"vehicleSearchPage.vm", context));
//	}
//	
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","机动车票据管理","机动车票据查询","查询"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply getVehSearch(){
//		Map<String,Object> param = _getParameters();
//		Page pagedata = service.getVehSearchPageData(param);
//		return new ReplyAjaxPage(pagedata);
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","机动车票据管理","机动车票据查询","导出excel"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply exportSearchExcel(){
//		Map<String,Object> param = _getParameters();
//		Excel excel = new Excel();
//		excel = service.exportInvoiceExcel(param,"search");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//	    String fileName = "机动车票-("+sdf.format(new Date())+")"+Math.abs(new Random(10000).nextInt())+".xls";
//		return new ReplyExcel(excel,fileName);
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","机动车票据管理","机动车票据查询","机动车票作废"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply vehInvoiceInvalid(){
//		Map<String,Object> param = _getParameters();
//		//执行作废
//		int result  = 0 ;
//		JSONArray json = JSONArray.fromObject(param.get("sqlData").toString());
//		for (Object obj : json) {
//			Map<String,Object> veh_id = (Map<String,Object>)obj;
//			veh_id.put("INVOICE_STATUS", "3");
//			result = service.upVehInvoiceStatus(veh_id);
//		}
//		if(result >0 ){
//		    return new ReplyAjax(true,"操作失败！");
//		}else{
//			return new ReplyAjax(false,"操作成功！");
//		}
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","机动车票据管理","机动车票据查询","机动车票冲红"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply vehInvoiceForRed(){
//		Map<String,Object> param = _getParameters();
//		//冲红操作
//		param.put("INVOICE_STATUS", "2");
//		int result = service.upVehInvoiceStatus(param);
//		if(result > 0 ){
//			return new ReplyAjax(true, "操作成功！");
//		}else{
//			return new ReplyAjax(false, "操作失败！");
//		}
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"票据管理","机动车票据管理","机动车票据核销","驳回"})
//	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
//	public Reply reJectVehInvoice(){
//		Map<String,Object> param = _getParameters();
//		int result = service.delInvoiceMess(param);
//		if(result >0 ){
//			return new ReplyAjax(true,"驳回成功！");
//		}else{
//			return new ReplyAjax(false,"驳回操作失败！");
//		}
//	}
//}
