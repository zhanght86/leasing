package com.pqsoft.secuEvaluate.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.secuEvaluate.service.EvaluateDictionaryService;
import com.pqsoft.secuEvaluate.service.SecuEvaluateService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class EvaluateDictionaryAction extends Action{
	@Override
	public Reply execute() {
		return null;
	}
	
	Map<String, Object> m = null;	
	private static Logger logger = Logger.getLogger(EvaluateDictionaryAction.class);
	private static EvaluateDictionaryService service = new EvaluateDictionaryService();
	public VelocityContext context=new VelocityContext();
	
	
	@aDev(code = "170062", email = "wujd@163.com", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	private void getPageParameter(){
		m = new HashMap<String, Object>();
		Enumeration<?> en = SkyEye.getRequest().getParameterNames();
		while (en.hasMoreElements()) {
			Object enN = en.nextElement();			
			String para = SkyEye.getRequest().getParameter(enN.toString()).trim();
			m.put(enN.toString(), para);
		}
		m.put("USERNAME",Security.getUser().getName());
		m.put("USERCODE",Security.getUser().getCode());
	}
	
	
	
	@aPermission(name = { "参数配置","评分设置", "定量打分项", "列表" })
	@aDev(code = "170062", email = "wujd@163.com", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	public Reply getDataList() {
		this.getPageParameter();
		return new ReplyHtml(VM.html("SecuEvaluate/EvaluateDicitonaryManger.vm", context));
	}
	
	@aPermission(name = { "参数配置","评分设置", "定量打分项", "列表" })
	@aDev(code = "170062", email = "wujd@163.com", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	public Reply pageAjax() {
		Map<String, Object> param = _getParameters();
		System.out.println(param);
		Page page = service.queryPage(param);
		return new ReplyAjaxPage(page);
	}
	
	@aPermission(name = { "参数配置","评分设置", "定量打分项", "添加" })
	@aDev(code = "170062", email = "wujd@163.com", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	public Reply toDataType() {
		VelocityContext context=new VelocityContext();
		//context.put("type", new DataDictionaryMemcached().get("行业类型-打分"));
		SecuEvaluateService iservice = new SecuEvaluateService();
		context.put("type",iservice.toFindIndustryMemcache());
		context.put("custTypes", new DataDictionaryMemcached().get("客户类型")); //Add By YangJ 2014-6-4 上午10:24:28
		return new ReplyHtml(VM.html("SecuEvaluate/EvaluateDicitonaryAdd.vm", context));
	}
	
	@aPermission(name = { "参数配置","评分设置", "定量打分项", "添加" })
	@aDev(code = "170062", email = "wujd@163.com", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	public Reply addEvaluateDictionary() {
		this.getPageParameter();
		JSONObject  object = JSONObject.fromObject(m.get("alldata"));
		System.out.println("alldata:::::::::"+m.get("alldata"));
		object.put("USER_CODE", Security.getUser().getCode());
		System.out.println("object:::::::::"+object);
		int a = service.insertEvaluateDictionary(object);
		
		boolean flag=false;
		if(a>0)
		{
			flag=true;
		}
		return new ReplyAjax(flag, null);
	}

	
	
	@aPermission(name = {"参数配置","评分设置", "定量打分项", "修改" })
	@aDev(code = "170062", email = "wujd@163.com", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	public Reply checkType(){
		this.getPageParameter();
		int count=(Integer)service.checkType(m);
		
		boolean flag=false;
		String msg="";
		if(count>0)
		{	msg = "该类型已存在,请重新输入!" ;
			flag=true;
		}
		return new ReplyAjax(flag, msg);
	}	

	@aPermission(name = { "参数配置","评分设置", "定量打分项", "作废" })
	@aDev(code = "170062", email = "wujd@163.com", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	public Reply deleteDictionary(){
		this.getPageParameter();
		int count=(Integer)service.deleteDictionary(m);
		boolean flag=false;
		if(count>0)
		{
			flag=true;
		}
		return new ReplyAjax(flag, null);
	}	
	
	@aPermission(name = { "参数配置","评分设置", "定量打分项", "作废" })
	@aDev(code = "170062", email = "wujd@163.com", name = "杨杰") //Add By YangJ 2014-6-3 下午03:31:18
	@aAuth(type = aAuthType.USER)
	public Reply delDictionary(){
		this.getPageParameter();
		int count;
		count = (Integer) service.delDictionary(m);
		boolean flag=false;
		if(count>0)
		{
			flag=true;
		}
		return new ReplyAjax(flag, null);
	}	

	@aPermission(name = { "参数配置","评分设置", "定量打分项", "查看" })
	@aDev(code = "170062", email = "wujd@163.com", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	public Reply toDataTypeInfo() {
		this.getPageParameter();
		VelocityContext  context = new VelocityContext();
		System.out.println(m);
		context.put("param", m);
		context.put("type", service.getDataTypeInfo(m));
		SecuEvaluateService iservice = new SecuEvaluateService();
		context.put("tradetype",iservice.toFindIndustryMemcache());
		//context.put("tradetype", new DataDictionaryMemcached().get("行业类型-打分"));
		return new ReplyHtml(VM.html("SecuEvaluate/EvaluateDicitonaryUpdate.vm", context));
	}
	
	@aPermission(name = { "参数配置","评分设置", "定量打分项", "作废" })
	@aDev(code = "170062", email = "wujd@163.com", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	public Reply invalidDataType() {
		this.getPageParameter();
		int count= service.invalidEvaluateDictionary(m);
		boolean flag=false;
		if(count>0)
		{
			flag=true;
		}
		return new ReplyAjax(flag, null);
	}	
	
	@aPermission(name = { "参数配置","评分设置", "定量打分项", "修改" })
	@aDev(code = "170062", email = "wujd@163.com", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	public Reply updateEvaluateDictionary() {
		System.out.println("alldata--->m:"+m);
		this.getPageParameter();
		System.out.println("alldata--->m:"+m);
		JSONObject  object = JSONObject.fromObject(m.get("alldata"));
		object.put("USER_CODE", Security.getUser().getCode());
		int count = service.updateEvaluateDictionary(object);
		boolean flag=false;
		if(count>0)
		{
			flag=true;
		}
		return new ReplyAjax(flag, null);
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置","评分设置", "定量打分项"})
	public Reply getTypesEvaluateDictionary(){
		//页面参数
		Map<String,Object> m = _getParameters();
		EvaluateDictionaryService service = new EvaluateDictionaryService();
		m.put("list",service.getTypesEvaluateDictionary(m));
		m.put("flag", true);
		return new ReplyAjax(m);
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置","评分设置", "定量打分项"})
	public Reply getTypesGradeMolud(){
		//页面参数
		Map<String,Object> m = _getParameters();
		EvaluateDictionaryService service = new EvaluateDictionaryService();
		m.put("list",service.getTypesGradeMolud(m));
		m.put("flag", true);
		return new ReplyAjax(m);
	}
}
