package com.pqsoft.documentApp.action;

import java.util.Map;

import com.pqsoft.documentApp.service.DocProductService;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;

/**
 * 权证接口：设备表
 * @author Administrator
 */
public class DocProductAction extends Action{
	private final String pagePath = "documentApp/";
	private DocProductService service=new DocProductService();
	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = {"权证接口", ""})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply DocProductplanById(){
		  Map<String, Object> params = _getParameters();
		  Map<String, Object> m = service.queryDocProductById(params);
		  return new ReplyAjax(m);	
	}
	
	
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = {"权证接口", ""})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply addDocProductById(){
		  Map<String, Object> params = _getParameters();
		  int m = service.addDocProductById(params);
		  return new ReplyAjax(m);		   
	}

	
	
}
