package com.pqsoft.addTax.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.addTax.service.VatFirstPayService;
import com.pqsoft.addTax.service.VatOtherFeeService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class VatOtherFeeAction extends Action {

	private String path = "addTax/";
	private VatOtherFeeService service = new VatOtherFeeService();
	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","管理服务费发票申请","列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showVatManagerFeeApplyPage(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html(path+"vatManagerApplyPage.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","管理服务费发票申请","查询"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getVatManagerFeePageData(){
		Map<String,Object> param = _getParameters();
		Page pageData = service.getVatManagerApplyPageData(param);
		return new ReplyAjaxPage(pageData);
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","留购价款发票申请","列表"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply showVatLeavePurApplyPage(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		param.put("param", param);
		context.put("param", context);
		return new ReplyHtml(VM.html(path+"vatLeavePurchaseApplyPage.vm", context));
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","留购价款发票申请","查询"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply getVatLeavePurApplyData(){
		Map<String,Object> param  = _getParameters();
		Page pageData = service.getVatLeavePurApplyPageData(param);
		return new ReplyAjaxPage(pageData);
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","留购价款发票申请","申请"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply applyLeaveFeeMethod(){
		Map<String,Object> param = _getParameters();
		VatFirstPayService vatService = new VatFirstPayService();
		JSONArray jsonArray = JSONArray.fromObject(param.get("sqlData").toString());
		int result = 0 ;
		List<Object> applyMess = new ArrayList<Object>();
		if(jsonArray.size()>0){
			applyMess = jsonArray;
		}else{
			List<String> listItem = new ArrayList<String>();
			listItem.add("留购价款");
			param.put("BEGINNING_NAMES", listItem);
			param.put("VAT_FLAG", "YES");
			param.put("LEAVING", "yes");
			applyMess = service.getAllOtherVatInvoiceMess(param);
		}
		for (Object object : applyMess) {
			Map<String,Object> newParam = (Map<String, Object>)object;
			newParam.put("CREATOR", Security.getUser().getName());
			newParam.put("FUND_TYPE", "项目结束");
			//插入发票详细信息
			List<String> s = new ArrayList<String>();
			s.add("留购价款");
			newParam.put("D_STATUS", "0");
			newParam.put("BEGINNING_NAMES", s);
			newParam.put("LEAVING", "yes");
			newParam.put("INVOICE_TYPE", "增值税发票");
			newParam.put("IF_INVOICE", "yes");
			result += vatService.addInvoice(newParam);
		}
		if(result >0){
			return new ReplyAjax(true,"申请成功！");
		}else{
			return new ReplyAjax(false,"申请失败！");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"票据管理","增值税发票管理","管理服务费发票申请","申请"})
	@aDev(code = "170016", email = "jinhondon@126.com", name = "靳洪东")
	public Reply applyManagerFeeMethod(){
		Map<String,Object> param = _getParameters();
		VatFirstPayService vatService = new VatFirstPayService();
		JSONArray jsonArray = JSONArray.fromObject(param.get("sqlData").toString());
		int result = 0 ;
		List<Object> applyMess = new ArrayList<Object>();
		if(jsonArray.size()>0){
			applyMess = jsonArray ;
		}else{
			List<String> listItem = new ArrayList<String>();
			listItem.add("管理费");
			param.put("BEGINNING_NAMES", listItem);
			param.put("VAT_FLAG", "YES");
			applyMess = service.getAllOtherVatInvoiceMess(param);
		}
		for (Object object : applyMess) {
			Map<String,Object> newParam = (Map<String, Object>)object;
			newParam.put("CREATOR", Security.getUser().getName());
			newParam.put("FUND_TYPE", "项目费用");
			//插入发票详细信息
			List<String> s = new ArrayList<String>();
			s.add("管理费");
			newParam.put("D_STATUS", "0");
			newParam.put("BEGINNING_NAMES", s);
			newParam.put("INVOICE_TYPE", "增值税发票");
			newParam.put("IF_INVOICE", "yes");
			result += vatService.addInvoice(newParam);
		}
		if(result >0){
			return new ReplyAjax(true,"申请成功！");
		}else{
			return new ReplyAjax(false,"申请失败！");
		}

	}
}
