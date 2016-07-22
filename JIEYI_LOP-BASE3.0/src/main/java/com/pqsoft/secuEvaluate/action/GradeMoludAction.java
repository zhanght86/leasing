package com.pqsoft.secuEvaluate.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.secuEvaluate.service.GradeMoludService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class GradeMoludAction  extends Action {
	private GradeMoludService service=new GradeMoludService();
	private Map<String,Object> m=null;
	private VelocityContext context=null;
	private static final String ns="SecuEvaluate/";
	
	@Override
	public Reply execute() {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> getParameters(){
		context=new VelocityContext();
		m=new HashMap<String,Object>();
		Enumeration<?> en=SkyEye.getRequest().getParameterNames();
		while(en.hasMoreElements()){
			Object men=en.nextElement();
			if("json".equals(men.toString())){
				m.putAll(JSONObject.fromObject(SkyEye.getRequest().getParameter("json").trim()));
			}else{
				m.put(men.toString(),SkyEye.getRequest().getParameter(men.toString()).trim());
			}
		}
		m.put("USERNAME",Security.getUser().getName());
		m.put("USERCODE",Security.getUser().getCode());
		m.put("USERID",Security.getUser().getId());
		m.put("RELATED", SkyEye.getRequest().getParameter("RELATED"));
		m.putAll(_getParameters());
		context.put("param", m);
		return m;
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置","评分设置","等级模版","列表"})
	public Reply toManager(){
		this.getParameters();
		context.put("mainsList", new DataDictionaryMemcached().get("主体"));
		context.put("type", new DataDictionaryMemcached().get("行业类型"));
		context.put("custTypes", new DataDictionaryMemcached().get("承租人类型"));
		return new ReplyHtml(VM.html(ns+"GradeMoludManager.vm", context));
	}
	

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "资金管理", "首付款退款", "首付款退款-申请","数据"})
	public Reply toMgGradeMoludData(){
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(_getParameters().get("param"));
		param.remove("param");
		param.putAll(json);
		return new ReplyAjaxPage(service.toGradeMoludManager(param));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置","评分设置","等级模版","添加"})
	public Reply addUI(){
		this.getParameters();
		context.put("mainsList", new DataDictionaryMemcached().get("主体"));
		context.put("type", new DataDictionaryMemcached().get("行业类型"));
		context.put("custTypes", new DataDictionaryMemcached().get("承租人类型"));
		return new ReplyHtml(VM.html(ns+"GradeMoludAdd.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置","评分设置","等级模版","修改"})
	public Reply updateUI(){
		this.getParameters();
//		context.put("pqsoft", service.queryByid(m));
		context.put("pqsoft", service.queryTreeByid(m));
		context.put("self", service.querySelfByid(m));
		context.put("norm", service.queryMouldStandard(m));
		
		context.put("mainsList", new DataDictionaryMemcached().get("主体"));
		context.put("hytype", new DataDictionaryMemcached().get("行业类型"));
		context.put("custTypes", new DataDictionaryMemcached().get("承租人类型"));
		context.put("autoValue", new DataDictionaryMemcached().get("承租人自动等级参数"));
		return new ReplyHtml(VM.html(ns+"GradeMoludUpdate.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置","评分设置","等级模版","复制"})
	public Reply copyUI(){
		this.getParameters();
		context.put("pqsoft", service.queryByid(m));
		context.put("self", service.querySelfByid(m));
		context.put("mainsList", new DataDictionaryMemcached().get("主体"));
		context.put("type", new DataDictionaryMemcached().get("行业类型"));
		context.put("custTypes", new DataDictionaryMemcached().get("承租人类型"));
		context.put("autoValue", new DataDictionaryMemcached().get("承租人自动等级参数"));
		return new ReplyHtml(VM.html(ns+"GradeMoludCopy.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置","评分设置","等级模版","自动取值"})
	public Reply autoValueUI(){
		this.getParameters();
		context.put("pqsoft", service.queryByid(m));
		context.put("self", service.querySelfByid(m));
		context.put("mainsList", new DataDictionaryMemcached().get("主体"));
		context.put("type", new DataDictionaryMemcached().get("行业类型"));
		context.put("custTypes", new DataDictionaryMemcached().get("承租人类型"));
		context.put("autoValue", new DataDictionaryMemcached().get("承租人自动等级参数"));
		return new ReplyHtml(VM.html(ns+"GradeMoludAutoValue.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置","评分设置","等级模版","增加"})
	public Reply add(){
		this.getParameters();
		JSONObject m = JSONObject.fromObject(SkyEye.getRequest().getParameter("fromData"));
		JSONArray jsonArray = m.getJSONArray("aa");
		for(int i=0;i<jsonArray.size();i++){
			Map map = (JSONObject)jsonArray.get(i);

			if("1".equals(map.get("norm")+"")){
				service.saveMouldOfStandard(map);
			}else{
				service.add(map);
			}
		}
		return new ReplyAjax(m);
	}


	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置","评分设置","等级模版","修改"})
	public Reply update(){
		this.getParameters();
		m.put("ID", m.get("id"));
		service.deleted(m);
		JSONObject m = JSONObject.fromObject(SkyEye.getRequest().getParameter("fromData"));
		JSONArray jsonArray = m.getJSONArray("aa");
		for(int i=0;i<jsonArray.size();i++){
			Map map = (JSONObject)jsonArray.get(i);
			
			if("1".equals(map.get("norm")+"")){
				service.saveMouldOfStandard(map);
			}else{
				service.add(map);
			}
		}
		return new ReplyAjax(m);
	}

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置","评分设置","等级模版","查询"})
	public Reply query(){
		this.getParameters();
//		context.put("pqsoft", service.queryByid(m));
		context.put("pqsoft", service.queryTreeByid(m));
		context.put("self", service.querySelfByid(m));
		context.put("norm", service.queryMouldStandard(m));

		context.put("mainsList", new DataDictionaryMemcached().get("主体"));
		context.put("type", new DataDictionaryMemcached().get("行业类型"));
		context.put("custTypes", new DataDictionaryMemcached().get("承租人类型"));
		context.put("autoValue", new DataDictionaryMemcached().get("承租人自动等级参数"));
		return new ReplyHtml(VM.html(ns+"GradeMoludQuery.vm", context));
	}

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置","评分设置","等级模版","查询"})
	public Reply querybyid(){
		this.getParameters();
		
		//service.queryByid(m);
		return new ReplyAjax(null);
	}
	
	/*
	 * 如查询到有重复，把id加上1毫秒再次验证，知道不重复后返回加了多少毫秒
	 * 
	 */
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置","评分设置","等级模版","修改"})
	public Reply checkId(){
		this.getParameters();
		String num = service.checkId(m);
		m.put("NUM", num);
		return new ReplyAjax(m);
	}
	
	/*
	 * 根据parentid由ajax查询
	 */
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置","评分设置","等级模版","修改"})
	public Reply querybyparentid(){
		this.getParameters();
		List list = service.queryByParentid(m);
		String str ="";
		for(int i=0;i<list.size();i++){
			Map map= (Map)list.get(i);
			if(map.get("TITLE")!=null){
				str=str+"<tr><td id='"+map.get("ID")+"' calss='"+map.get("PARENTSID")+"'>"+map.get("TITLE")+"</td></tr>";
			}else{
				str=str+"<td id='"+map.get("ID")+"' calss='"+map.get("PARENTSID")+"'>" + map.get("CONTENT")+"</td>";
			}
		}
		m.put("STR", str);
		return new ReplyAjax(m);
	}

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置","评分设置","等级模版","删除"})
	public Reply deleted(){
		this.getParameters();
		service.deleted(m);
		return this.toManager();
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "参数配置","评分设置","等级模版","复制"})
	public Reply doCopy(){
		this.getParameters();
		service.doCopy(m);
		return this.toManager();
	}
	

}
