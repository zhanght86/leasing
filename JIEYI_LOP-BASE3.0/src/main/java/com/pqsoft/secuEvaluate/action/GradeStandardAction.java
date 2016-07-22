package com.pqsoft.secuEvaluate.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.secuEvaluate.service.GradeStandardService;
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

public class GradeStandardAction extends Action{
	@Override
	public Reply execute() {
		return null;
	}
	
	Map<String, Object> m = null;	
	private static GradeStandardService service = new GradeStandardService();
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
	
	
	
	@aPermission(name = { "参数配置","评分设置","评级标准", "列表" })
	@aDev(code = "170062", email = "wujd@163.com", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	public Reply getDataList() {
		this.getPageParameter();
		return new ReplyHtml(VM.html("SecuEvaluate/GradeStandardManger.vm", context));
	}
	
	@aPermission(name = { "参数配置","评分设置","评级标准", "列表" })
	@aDev(code = "170062", email = "wujd@163.com", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	public Reply pageAjax() {
		Map<String, Object> param = _getParameters();
		System.out.println(param);
		Page page = service.queryPage(param);
		return new ReplyAjaxPage(page);
	}
	
	@aPermission(name = { "参数配置","评分设置","评级标准", "添加" })
	@aDev(code = "170062", email = "wujd@163.com", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	public Reply toDataType() {
		return new ReplyHtml(VM.html("SecuEvaluate/GradeStandardAdd.vm", null));
	}
	
	@aPermission(name = { "参数配置","评分设置","评级标准", "添加" })
	@aDev(code = "170062", email = "wujd@163.com", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	public Reply addGradeStandard() {
		this.getPageParameter();
		JSONObject  object = JSONObject.fromObject(m.get("alldata"));
		object.put("USER_CODE", Security.getUser().getCode());
		int a = service.insertGradeStandard(object);
		boolean flag=false;
		if(a>0)
		{
			flag=true;
		}
		return new ReplyAjax(flag, null);
	}

	
	
	@aPermission(name = { "参数配置","评分设置","评级标准", "修改" })
	@aDev(code = "170062", email = "wujd@163.com", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	public Reply checkType(){
		this.getPageParameter();
		service.checkType(m);

		int count=(Integer)service.checkType(m);
		
		boolean flag=false;
		if(count>0)
		{
			flag=true;
		}
		return new ReplyAjax(flag, null);
	}	

	@aPermission(name = { "参数配置","评分设置","评级标准", "作废" })
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

	@aPermission(name = { "参数配置","评分设置","评级标准", "查看" })
	@aDev(code = "170062", email = "wujd@163.com", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	public Reply toDataTypeInfo() {
		this.getPageParameter();
		VelocityContext  context = new VelocityContext();
		System.out.println(m);
		context.put("param", m);
		context.put("type", service.getDataTypeInfo(m));
		return new ReplyHtml(VM.html("SecuEvaluate/GradeStandardUpdate.vm", context));
	}
	
	@aPermission(name = { "参数配置","评分设置","评级标准", "作废" })
	@aDev(code = "170062", email = "wujd@163.com", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	public Reply invalidDataType() {
		this.getPageParameter();
		int count= service.invalidGradeStandard(m);
		boolean flag=false;
		if(count>0)
		{
			flag=true;
		}
		return new ReplyAjax(flag, null);
	}	
	
	@aPermission(name = { "参数配置","评分设置","评级标准", "修改" })
	@aDev(code = "170062", email = "wujd@163.com", name = "吴剑东")
	@aAuth(type = aAuthType.USER)
	public Reply updateGradeStandard() {
		this.getPageParameter();
		JSONObject  object = JSONObject.fromObject(m.get("alldata"));
		object.put("USER_CODE", Security.getUser().getCode());
		int count = service.updateGradeStandard(object);
		boolean flag=false;
		if(count>0)
		{
			flag=true;
		}
		return new ReplyAjax(flag, null);
	}

	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置","评分设置","评级标准"})
	public Reply getTypesGradeStandard(){
		//页面参数
		Map<String,Object> m = _getParameters();
		GradeStandardService service = new GradeStandardService();
		m.put("list",service.getTypesGradeStandard(m));
		m.put("flag", true);
		return new ReplyAjax(m);
	}
}
