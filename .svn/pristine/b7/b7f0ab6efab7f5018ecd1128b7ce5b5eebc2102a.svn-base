package com.pqsoft.Funds_Whole.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.Funds_Whole.service.Funds_WholeService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.util.UTIL;

public class Funds_WholeAction extends Action {

	private final String pagePath="fundsWhole/";
	@Override
	public Reply execute() {
		return null;
	}

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "融资管理", "资金统筹","列表显示"})
	public Reply queryFundsWholeManage(){
		Map<String,Object> param = _getParameters();//获取页面参数
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(pagePath+"ManageFunds_Whole.vm", context));
	}
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "融资管理", "资金统筹","列表显示"})
	public Reply toMgFundsWholeData(){
		Map<String, Object> param = _getParameters();
		Funds_WholeService service = new Funds_WholeService();
		return new ReplyAjaxPage(service.pageTemplate(param));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "融资管理", "资金统筹","统筹测算"})
	public Reply doFundsWhole(){
		Map<String, Object> m = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param",m);
		return new ReplyHtml(VM.html(pagePath+"detailFunds_Whole.vm", context));
	}
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "融资管理", "资金统筹","统筹测算"})
	public Reply queryFundsWholeProjectDate(){
		Map<String, Object> m = _getParameters();
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
		System.out.println(m);
		Funds_WholeService service = new Funds_WholeService();
		return new ReplyAjaxPage(service.queryFundsWholeProjectList(m));
	}
	
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "融资管理", "资金统筹","统筹测算"})
	public Reply queryFundsWholeInfo(){
		//页面参数
		Map<String,Object> m = _getParameters();

		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
		
		Funds_WholeService service = new Funds_WholeService();
		Map<String,Object> data = service.queryFundsWholeInfo(m);
		m.putAll(data);
		System.out.println(m);

		m.put("flag", false);
		return new ReplyAjax(m);
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "融资管理", "资金统筹","删除"})
	public Reply deleteFundsWhole(){
		//页面参数
		Map<String,Object> m = _getParameters();

		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
		
		Funds_WholeService service = new Funds_WholeService();
		boolean flag = service.deleteFundsWhole(m);
		m.put("flag", flag);
		return new ReplyAjax(m);
	}
	

	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "融资管理", "资金统筹","统筹清单"})
	public Reply saveFundsWholeInfo(){
		//页面参数
		Map<String,Object> m = _getParameters();

		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
		System.out.println(m);
		Funds_WholeService service = new Funds_WholeService();
		boolean flag = service.saveFundsWholeInfo(m);
		m.put("flag", flag);
		return new ReplyAjax(m);
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "融资管理", "资金统筹","统筹"})
	public Reply saveFundsWhole(){
		Map<String,Object> m = _getParameters();
		Funds_WholeService service = new Funds_WholeService();
		String ID=(String)service.saveFundsWhole(m);
		String[] CODE=SkyEye.getRequest().getParameterValues("CODE");
		String[] USE_DATE=SkyEye.getRequest().getParameterValues("USE_DATE");
		String[] USE_MONEY=SkyEye.getRequest().getParameterValues("USE_MONEY");
		if(CODE!=null){
			for (int i = 0; i < CODE.length; i++) {
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("RUFUND_ID", ID);
				map.put("CODE",CODE[i] );
				map.put("USE_DATE",USE_DATE[i] );
				map.put("USE_MONEY",USE_MONEY[i] );
				service.saveFundsWholeList(map);
			}
		}
		return this.queryFundsWholeManage();
	}
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	@aPermission(name = { "融资管理", "资金统筹","列表显示"})
	public Reply getFundsWholeList(){
		Map<String,Object> m = _getParameters();//获取页面参数
		VelocityContext context = new VelocityContext();
		Funds_WholeService service = new Funds_WholeService();
		Map<String,Object> FundsWhole=service.getFundsWhole(m);
		List<Map<String,Object>> FundsWholeList= service.getFundsWholeList(m);
		context.put("FundsWhole",FundsWhole );
		context.put("FundsWholeList",FundsWholeList );
		context.put("paramMap", m);
		context.put("Format", UTIL.FORMAT);
		
		return new ReplyHtml(VM.html(pagePath+"ShowFunds_Whole.vm", context));
	}
}

