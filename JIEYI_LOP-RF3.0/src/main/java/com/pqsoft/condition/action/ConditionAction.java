package com.pqsoft.condition.action;

import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.condition.service.ConditionService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class ConditionAction extends Action {

	private final String pagePath="condition/";
	@Override
	public Reply execute() {
		return null;
	}

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资条件管理","列表显示"})
	public Reply toMgCondition(){
		Map<String,Object> paramMap = _getParameters();//获取页面参数
		VelocityContext context = new VelocityContext();
		context.put("paramMap", paramMap);
		return new ReplyHtml(VM.html(pagePath+"toMgCondition.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = { "融资管理", "融资条件管理","列表"})
	public Reply toMgConditionData(){
		Map<String, Object> param = _getParameters();
		ConditionService service = new ConditionService();
		return new ReplyAjaxPage(service.toMgConditionData(param));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资条件管理","添加"})
	@SuppressWarnings("unchecked")
	public Reply getInsetBailoutCondition(){
		VelocityContext context = new VelocityContext();
		ConditionService service = new ConditionService();
		List dictionarylist = (List) service.queryDataDictionary();
		context.put("dictionarylist",dictionarylist);
		return new ReplyHtml(VM.html(pagePath+"ConditionAdd.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = { "融资管理", "融资条件管理","添加"})
	public Reply doAjaxInsetBailoutCondition(){
		Map<String,Object> map = _getParameters();
		map.put("USERCODE",Security.getUser().getCode());//用户编号
		ConditionService service = new ConditionService();
		int i = 0;
		boolean flag = false;
		i= service.insetConditionService(map);
		if(i>0){
			flag = true;
		}else {
			flag = false;
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("融资方式管理","添加", map.get("USERCODE").toString()));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资条件管理","取自数据字典"})
	public Reply insertDataDictionary(){
		Map<String,Object> map = _getParameters();
		map.put("USERCODE",Security.getUser().getCode());//用户编号
		ConditionService service = new ConditionService();
		int i = 0;
		boolean flag = false;
		try{
		i= service.insertDataDictionary();
		if(i>=0){
			flag = true;
		}else {
			flag = false;
		}
		}catch(Exception e){
			throw new ActionException("失败"+e.getMessage());
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("融资方式管理","取自数据字典", map.get("USERCODE").toString()));
	}
}
