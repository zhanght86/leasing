package com.pqsoft.documentApp.action;

import java.util.Map;

import com.pqsoft.documentApp.service.DocProductService;
import com.pqsoft.documentApp.service.DocProjectService;
import com.pqsoft.documentApp.service.DocRentplanService;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;

/**
 * 权证接口：项目表数据注备
 * @author Administrator
 *
 */
public class DocProjectAction extends Action{
	
	private final String pagePath = "documentApp/";
	private DocProjectService service=new DocProjectService();

	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = {"权证接口", ""})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")	
   public Reply queryDocProjectById(){
	   Map<String, Object> params = _getParameters();
	   Map<String, Object> m = service.queryDocProjectById(params);
	   return new ReplyAjax(m);
   }
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = {"权证接口", ""})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")	
   public Reply addDocProjectById(){
	   Map<String, Object> params = _getParameters();
	   int m = service.addDocProjectById(params);
	   return new ReplyAjax(m);
   }
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name = {"权证接口", ""})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply addDocProjectAll(){//插入项目， 合同， 设备数据
		  Map<String, Object> params = _getParameters();
		  DocRentplanService rent = new DocRentplanService();
		  DocProductService product=new DocProductService();
		  int m = service.addDocProjectById(params);//插入项目
		  int n = 0;
		  boolean flag = false;
		  String msg = "";
		  if(m>0){
			  //插入合同
			  int k = rent.addDocRentplanById(params);
			  if(k>0){//插入项目
				 n =  product.addDocProductById(params);
			  }
		  }
		  
		  if(n>0){
			  flag = true;
			  msg = "数据接收成功 ";
		  }else {
			  msg = "数据接收失败";
		  }
		  return new ReplyAjax(flag, msg);		   
	}
}
