package com.pqsoft.invoice.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.invoice.service.InvoiceSearchService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.CollectionUtil;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.ReplyExcel;
import com.pqsoft.util.StringUtils;

/**
 * 
 * @ClassName: InvoiceSearchAction 
 * @Description: TODO(票据查询) 
 * @author 程龙达
 * @date 2013-11-5 下午07:01:28 
 *
 */
public class InvoiceSearchAction extends Action {
	
	private Map<String,Object> param = new HashMap<String,Object>();
	
	private InvoiceSearchService service = new InvoiceSearchService();
	
	@Override
	public Reply execute() {
		return null;
	}
	
	/**
	 * 
	 * @Title: toInvoiceSearchPage 
	 * @Description: TODO("票据管理", "发票查询", "发票查询界面" ) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "票据管理", "票据全查询", "列表" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply toInvoiceSearchPage(){
		VelocityContext context = new VelocityContext();
		DataDictionaryMemcached DDMservice = new DataDictionaryMemcached();
		context.put("PLATFORMTYPEList", new SysDictionaryMemcached().get("业务类型"));
		context.put("dic_invoice_typename", CollectionUtil.listInsertDefualtComboxInfo(DDMservice.get("发票_类型名称")));
		return new ReplyHtml(VM.html("/invoice/search/invoiceSearch.vm",context));
	}
	
	/**
	 * 
	 * @Title: toInvoiceSearchPage 
	 * @Description: TODO("发票管理", "发票查询", "发票查询界面" ) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "票据管理", "票据全查询", "列表" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply getInvoiceSearchPageData(){
		param = _getParameters();
		return new ReplyAjaxPage(service.getInvoiceSearchPageData(param));
	}
	
	/**
	 * 
	 * @Title: getInvoiceSearchFactById 
	 * @Description: TODO("发票管理", "发票查询", "发票查询界面" ) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "票据管理", "票据全查询", "列表" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply getInvoiceSearchFactById(){
		param = _getParameters();
		String itemid = StringUtils.nullToString(param.get("ITEMID").toString());
		JSONArray ar = new JSONArray();
		if(!itemid.equals("") && !itemid.equals("undefined")){
			ar = JSONArray.fromObject(service.getInvoiceSearchFactById(param));
		}
		return new ReplyJson(ar);
	}
	
	/**
	 * 
	 * @Title: getInvoiceSearchItemById 
	 * @Description: TODO("发票管理", "发票查询", "发票查询界面" ) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "票据管理", "票据全查询", "列表" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply getInvoiceSearchItemById(){
		param = _getParameters();
		String itemid = StringUtils.nullToString(param.get("ITEMID").toString());
		JSONArray ar = new JSONArray();
		if(!itemid.equals("") && !itemid.equals("undefined")){
			ar = JSONArray.fromObject(service.getInvoiceSearchItemById(param));
		}
		return new ReplyJson(ar);
	}
	
	/**
	 * 
	 * @Title: getInvoiceSearchDetailPageData 
	 * @Description: TODO("发票管理", "发票查询", "发票查询界面" ) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "票据管理", "票据全查询", "列表" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply getInvoiceSearchDetailPageData(){
		param = _getParameters();
		String itemid = StringUtils.nullToString(param.get("ITEMID").toString());
		return new ReplyAjaxPage(service.getInvoiceSearchDetailPageData(param));
	}
	
	/**
	 * 
	 * @Title: doExportApplyExcel 
	 * @Description: TODO() 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "票据管理", "票据全查询", "全导出/导出选中项"})
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply doExportApplyExcel(){
		Map<String,Object> param = _getParameters();
		return new ReplyExcel(service.getExportApplyExcel(param),"Invoice_"+DateUtil.getSysDate()+Math.abs(new Random(10000).nextInt())+".xls").addOp(new OpLog("发票管理", "查询", "发票查询界面"));
	}
	
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
