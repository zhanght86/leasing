package com.pqsoft.documentApp.action;

import java.util.Map;

import com.pqsoft.documentApp.service.DocRentplanService;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;

/**
 * 权证接口：合同表
 * @author Administrator
 */
public class DocRentplanAction extends Action{
	private final String pagePath = "documentApp/";
	private DocRentplanService service=new DocRentplanService();

	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = {"权证接口", ""})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply queryDocRentplanById(){
		  Map<String, Object> params = _getParameters();
		  Map<String, Object> m = service.queryDocRentplanById(params);
		  return new ReplyAjax(m);	
	}
	
	
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = {"权证接口", ""})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply addDocRentplanById(){
		  Map<String, Object> params = _getParameters();
		  int m = service.addDocRentplanById(params);
		  return new ReplyAjax(m);		   
	}
	
}
