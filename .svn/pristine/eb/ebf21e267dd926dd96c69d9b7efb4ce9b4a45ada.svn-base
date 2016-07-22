package com.pqsoft.invoice.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.invoice.service.InvoiceService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class InvoiceMgAction extends Action{
	private InvoiceService service = new InvoiceService();
	Map<String,Object> m = new HashMap<String,Object>();
	
	@Override
	public Reply execute() {
		return null;
	}
	/**
	 * 
	 * @Title: toMgInvoiceConfigPage 
	 * @Description: (开具规则定义) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "票据管理", "开具规则定义", "列表显示" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply toMgInvoiceConfigPage(){
		VelocityContext context = new VelocityContext();
		service.alterRuler();
		Map<String, Object> datas = service.getColumns();
		context.put("fields", datas.get("fields"));
		context.put("rowss", datas.get("rowss"));
		return new ReplyHtml(VM.html("/invoice/invoiceConfigMg.vm", context));
	}
	
	/**
	 * 
	 * @Title: doSelectConfigPageData 
	 * @Description: TODO() 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "票据管理", "开具规则定义", "配置页面数据(必选)" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply doSelectConfigPageData(){
		Map<String,Object> param = _getParameters();
		Page pageData = service.selectConfigPageData(param);
		return new ReplyAjaxPage(pageData);
	}
	
	/**
	 * 
	 * @Title: doSelectRulerValues 
	 * @Description: TODO() 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "票据管理", "开具规则定义", "配置页面(必选)"})
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply doSelectRulerValues(){
		Map<String,Object> param = _getParameters();
		
		JSONArray rulerValues = service.selectRulerValues(param);
		return new ReplyJson(rulerValues);
	}
	
	/**
	 * 
	 * @Title: doSelectRulerValues 
	 * @Description: TODO() 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "票据管理", "开具规则定义", "配置页面(必选)"})
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply doSelectRulerValuesSite(){
		Map<String,Object> param = _getParameters();
		
		JSONArray rulerValues = service.doSelectRulerValuesSite(param);
		return new ReplyJson(rulerValues);
	}
	

	/**
	 * 
	 * @Title: doUpdateRulerHead 
	 * @Description: TODO() 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "票据管理", "开具规则定义", "配置页面(必选)"})
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply doUpdateRulerHead(){
		Map<String,Object> param = _getParameters();
		if(service.doUpdateRulerHead(param)){
			return new ReplyAjax(true);
		}	
		return new ReplyAjax(false);
	}
	/**
	 * 
	 * @Title: doDeleteRulerHead 
	 * @Description: TODO() 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "票据管理", "开具规则定义", "配置页面(必选)"})
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply doDeleteRulerHead(){
		Map<String,Object> param = _getParameters();
		if(service.doDeleteRulerHead(param)){
			return new ReplyAjax(true);
		}	
		return new ReplyAjax(false);
	}
	/**
	 * 
	 * @Title: doSelectConfigDetailData 
	 * @Description: TODO() 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "票据管理", "开具规则定义", "配置页面(必选)"})
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply doSelectConfigDetailData(){
		Map<String,Object> param = _getParameters();
		Page pageData = service.selectConfigDetailData(param);
		return new ReplyAjaxPage(pageData);
	}
	
	/**
	 * 
	 * @Title: toInvoiceConfigDetailMg 
	 * @Description: () 
	 * @return Reply 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "票据管理", "开具规则定义", "配置页面(必选)" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply toInvoiceConfigDetailMg(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		context.put("id", param.get("id"));
		return new ReplyHtml(VM.html("/invoice/invoiceConfigUpdate.vm", context));
	}
	/**
	 * 
	 * @Title: doUpdateConfigDetail 
	 * @Description: TODO() 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "票据管理", "开具规则定义", "配置页面(必选)"})
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply doUpdateConfigDetail(){
		Map<String,Object> param = _getParameters();
		if(service.doUpdateConfigDetail(param)){
			return new ReplyAjax(true);
		}	
		return new ReplyAjax(false);
	}
	/**
	 * 
	 * @Title: doRefreshConfigFinal 
	 * @Description: TODO() 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "票据管理", "开具规则定义", "配置页面(必选)"})
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply doRefreshConfigFinal(){
		Map<String,Object> param = _getParameters();
		if(service.doRefreshConfigFinal(param)){
			return new ReplyAjax(true);
		}	
		return new ReplyAjax(false);
	}
	
	/**
	 * 
	 * @Title: checkInvoice 
	 * @Description: (无法开具原因查询) 
	 * @return Reply 
	 * @throws 
	 */
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "票据管理", "增值税发票", "无法开具原因查询" })
//	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
//	public Reply checkInvoice(){
//		service.checkInvoiceMess();
//		return new ReplyHtml(VM.html("invoice/invoiceFalseReasion.vm", null));
//	}
	
	/**
	 * 
	 * @Title: checkInvoiceData 
	 * @Description: (无法开具原因查询(数据)) 
	 * @return Reply 
	 * @throws 
	 */
//	@SuppressWarnings({ "unchecked", "static-access" })
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name = { "票据管理", "增值税发票", "无法开具原因查询(数据)"})
//	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
//	public Reply checkInvoiceData(){
//		Map<String,Object> param = this._getParameters();
//		System.out.println(param);
//		return new ReplyAjaxPage(service.checkInvoiceData(param));
//	}
	
	@SuppressWarnings("unchecked")
	public static Map _getParameters()
	{
		Map m = new HashMap();
		Object enN;
		String para;
		for(Enumeration en = SkyEye.getRequest().getParameterNames(); en.hasMoreElements(); m.put(enN.toString(), para.replace("--请选择--", "").replace("--\\u8bf7\\u9009\\u62e9--", "")))
		{
		    enN = en.nextElement();
		    para = SkyEye.getRequest().getParameter(enN.toString()).trim();
		}
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
		return m;
	}
}
