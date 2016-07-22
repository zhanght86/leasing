package com.pqsoft.positions.action;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.company.service.CompanyService;
import com.pqsoft.positions.service.PositionsService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class PositionsAction  extends Action{

	private final String pagePath="positions/";
	private final PositionsService service = new PositionsService();
	
	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aPermission(name = { "业务管理", "头寸管理","列表页"})
	public Reply doPositionsManger(){
		Map<String, Object> m = _getParameters();
		VelocityContext context = new VelocityContext();
		
		//长期应收
		Map cqMap=service.cq_data(m);
		if(cqMap !=null){
			m.putAll(cqMap);
		}
		
		//根据条件查询明细
		Map mxMap=service.querypositionsMXData(m);
		if(mxMap !=null){
			m.putAll(mxMap);
		}
		
		context.put("param",m);
		return new ReplyHtml(VM.html(pagePath+"doPositionsManger.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aPermission(name = { "业务管理", "头寸管理","列表页"})
	public Reply doPositionsData(){
		Map<String, Object> m = _getParameters();
		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
		m.remove("searchParams");
		m.putAll(json);
		System.out.println(m);
		
		return new ReplyAjaxPage(service.doPositionsData(m));
	}

	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply doParamInfo(){
		Map<String, Object> m = _getParameters();
		System.out.println("-------------------yunxinglema???----------");
		return new ReplyAjax(service.querypositionsMXData(m));
	}
}
