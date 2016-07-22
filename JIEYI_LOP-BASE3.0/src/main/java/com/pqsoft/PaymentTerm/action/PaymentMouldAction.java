package com.pqsoft.PaymentTerm.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.PaymentTerm.service.PaymentMouldService;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
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
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class PaymentMouldAction  extends Action {
	private PaymentMouldService service=new PaymentMouldService();
	private Map<String,Object> m=null;
	private VelocityContext context=null;
	private static final String ns="PaymentTerm/";
	
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
	@aPermission(name = { "放款管理", "放款模版配置","列表显示"})
	public Reply toManager(){
		this.getParameters();
		context.put("typeList", new SysDictionaryMemcached().get("业务类型"));
		context.put("normList", service.queryPaymentNormAll(m));
		return new ReplyHtml(VM.html(ns+"PaymentMouldManager.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "放款管理", "放款模版配置","列表显示"})
	public Reply toMgPaymentMouldData(){
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(_getParameters().get("param"));
		param.remove("param");
		param.putAll(json);
		return new ReplyAjaxPage(service.toPaymentMouldManager(param));
	}
	
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "qijianglong")
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "放款管理", "放款模版配置","列表显示"})
	public Reply toPaymentMouldDetail()
	{
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.queryPaymentMouldDetail(param));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "放款管理", "放款模版配置","添加放款模版"})
	public Reply addUI(){
		this.getParameters();
		context.put("typeList", new SysDictionaryMemcached().get("业务类型"));
		return new ReplyHtml(VM.html(ns+"PaymentMouldAdd.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "放款管理", "放款模版配置","修改"})
	public Reply updateUI(){
		this.getParameters();

		context.put("typeList", new SysDictionaryMemcached().get("业务类型"));
		return new ReplyHtml(VM.html(ns+"PaymentMouldUpdate.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "放款管理", "放款模版配置","添加放款模版"})
	public Reply add(){
		this.getParameters();
		String arr = m.get("TORM_IDS")+"";
		String[] TORM_IDS = arr.split("-");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("NAME", m.get("ADD_NAME"));
		map.put("CODE", m.get("ADD_CODE"));
		for(int i=0;i<TORM_IDS.length;i++){
			map.put("NORM_ID", TORM_IDS[i]);
			service.add(map);
		}
		return new ReplyAjax(m);
	}


	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "放款管理", "放款模版配置","修改"})
	public Reply update(){
		this.getParameters();
		String arr = m.get("UP_TORM_IDS")+"";
		String[] TORM_IDS = arr.split("-");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("NAME", m.get("UP_NAME"));
		map.put("CODE", m.get("UP_CODE"));
		service.deletedByName(map);
		for(int i=0;i<TORM_IDS.length;i++){
			map.put("NORM_ID", TORM_IDS[i]);
			service.add(map);
		}
		return new ReplyAjax(m);
	}

	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "放款管理", "放款模版配置","查询"})
	public Reply querybyid(){
		this.getParameters();
		
		//service.queryByid(m);
		return new ReplyAjax(null);
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "放款管理", "放款模版配置"})
	public Reply checkMouldNameCount(){
		this.getParameters();
		return new ReplyAjax(service.checkMouldNameCount(m),m);
	}

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "放款管理", "放款模版配置","删除"})
	public Reply deleted(){
		this.getParameters();
		return new ReplyAjax(service.deleted(m),m);
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "放款管理", "放款模版配置","删除"})
	public Reply deletedByName(){
		this.getParameters();
		return new ReplyAjax(service.deletedByName(m),m);
	}
}
