package com.pqsoft.customers.action;

import java.util.Map;

import net.sf.json.JSONObject;

import com.pqsoft.customers.service.custFinanceService;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class custFinanceAction extends Action {

	custFinanceService cfService = new custFinanceService();
	
	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}

	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = {"客户管理", "客户资料管理", "法人财报查看" })
	public Reply toViewFinance(){
		Map<String,Object> map = _getParameters();
		return new ReplyAjax(JSONObject.fromObject(cfService.getFinance(map)));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = {"客户管理", "客户资料管理", "法人财报添加" })
	public Reply toAddFinance(){
		Map<String,Object> map = _getParameters();
		int i = cfService.doInsertFinance(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "法人财报添加成功";
		}else{
			msg = "法人财报添加失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("客户管理", "客户资料管理-法人财报添加", msg));
	}
	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aAuth(type = aAuthType.LOGIN)
//	@aPermission(name = {"客户管理", "客户资料管理", "法人财报修改" })
	public Reply toUpdateFinance(){
		Map<String,Object> map = _getParameters();
		int i = cfService.doUpdateFinance(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "法人财报修改成功";
		}else{
			msg = "法人财报修改失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("客户管理", "客户资料管理-法人财报修改", msg));
	}
}
