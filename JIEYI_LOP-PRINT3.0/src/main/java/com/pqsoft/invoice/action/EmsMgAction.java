package com.pqsoft.invoice.action;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.invoice.service.EmsMgService;
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
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.CollectionUtil;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.ReplyExcel;

public class EmsMgAction extends Action {
	private EmsMgService service = new EmsMgService();
	@Override
	public Reply execute() {
		return null;
	}

	
	/**
	 * 
	 * @Title: toMgEmsPage 
	 * @Description: TODO(票据) 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "票据管理", "邮寄地址管理", "列表显示" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply toMgEmsPage(){
		VelocityContext context = new VelocityContext();
		DataDictionaryMemcached DDMservice = new DataDictionaryMemcached();
		context.put("dic_target_type", CollectionUtil.listInsertDefualtComboxInfo(DDMservice.get("发票_对象_类型")));
		return new ReplyHtml(VM.html("/invoice/ems/emsMg.vm", context));
	}
	/**
	 * 
	 * @Title: selectEmsMgPageData 
	 * @Description: TODO() 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"票据管理", "邮寄地址管理", "列表显示"})
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply selectEmsMgPageData(){
		Map<String,Object> param = this._getParameters();
		Page page = service.selectEmsMgPageData(param);
		return new ReplyAjaxPage(page);
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
	@aPermission(name = { "票据管理", "邮寄地址管理", "导出"})
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply doExportApplyExcel(){
		Map<String,Object> param = _getParameters();
		return new ReplyExcel(service.getExportApplyExcel(param),"EMSInfo"+DateUtil.getSysDate()+Math.abs(new Random(10000).nextInt())+".xls").addOp(new OpLog("票据管理", "邮寄地址管理", "导出"));
	}
	
	/**
	 * 
	 * @Title: doSelectEmsInfo 
	 * @Description: TODO() 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"票据管理", "邮寄地址管理", "列表显示"})
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply doSelectEmsInfo(){
		Map<String,Object> param = _getParameters();
		return new ReplyJson(JSONArray.fromObject(service.doSelectEmsInfo(param)));
	}
	
	/**
	 * 
	 * @Title: updateOrAddEmsInfo 
	 * @Description: TODO() 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"票据管理", "邮寄地址管理", "修改"})
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply updateOrAddEmsInfo(){
		Map<String,Object> param = _getParameters();
		
		return new ReplyAjax(true,"",service.updateOrAddEmsInfo(param));
	}
	
	/**
	 * 
	 * @Title: uploadInvoiceEmsInfo 
	 * @Description: TODO() 
	 * @return Reply 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "票据管理", "邮寄地址管理", "上传" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply uploadInvoiceEmsInfo(){
		List<File> files = _getFile();
		int result = service.uploadInvoiceEmsInfo(files);
		if(result > 0 ){
		   return new ReplyAjax(true, "成功上传【"+result+"】条");	
		}else{
		   return new ReplyAjax(true, "上传回执失败,请检查文件格式！");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "票据管理", "邮寄地址管理", "上传" })
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply synchronizationEms(){
		Map<String,Object> param = _getParameters() ;
		int a = service.synchronizationEms(param) ;
		
		boolean flag=false ;
		String info="同步失败!" ;
		if(a>0){
			flag=true;
			info="同步成功!" ;
		}
		
		return new ReplyAjax(flag, info) ;
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
