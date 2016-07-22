package com.pqsoft.pendingpool.action;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.pendingpool.service.PendingPoolService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class PendingPoolAction extends Action {

	private final String pagePath = "pendingpool/";
	private final PendingPoolService service = new PendingPoolService();
	
	@Override
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "待处理资金池","列表"})
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> map = _getParameters();
		context.put("param", map);
		context.put("type", new DataDictionaryMemcached().get("付款方式"));
		return new ReplyHtml(VM.html(pagePath+"toMgPendingPool.vm",context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "待处理资金池","列表"})
	public Reply toMgPendingPool(){
		Map<String,Object> map = _getParameters();
		JSONObject obj = JSONObject.fromObject(map.get("param"));
		map.remove("param");
		map.putAll(obj);
		return new ReplyAjaxPage(service.toMgPendingPool(map));
	}

	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "待处理资金池","添加"})
	public Reply doInsertPending(){
		Map<String,Object> map = _getParameters();
		map.put("USERCODE", Security.getUser().getCode());//获取用户编号
		int i = service.doInsertPending(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "来款添加成功";
		}else{
			msg = "来款添加失败";
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("资金管理","待处理资金池",msg));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "待处理资金池","修改(数据查询)"})
	public Reply toShowPending(){
		Map<String,Object> map = _getParameters();
		Map<String,Object> data = (Map<String, Object>) service.toShowPending(map);
		return new ReplyAjax(JSONObject.fromObject(data));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "待处理资金池","修改(操作)"})
	public Reply doUpdatePending(){
		Map<String,Object> map = _getParameters();
		map.put("USERCODE", Security.getUser().getCode());//获取用户编号
		int i = service.doUpdatePending(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "来款修改成功";
		}else {
			msg = "来款修改失败";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("资金管理","待处理资金池",msg));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "待处理资金池","作废"})
	public Reply doZFfunds(){
		Map<String,Object> map = _getParameters();//获取页面参数
		map.put("USERCODE", Security.getUser().getCode());//获取用户编号
		int i = service.doZFfunds(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "来款资金作废成功";
		}else{
			msg = "来款资金作废失败";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("资金管理","待处理资金池",msg));		
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "待处理资金池","选择归类集对象名称"})
	public Reply toChosePer(){
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		context.put("list", service.toChosePer(map));
		return new ReplyHtml(VM.html(pagePath+"toChosePerson.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "资金管理", "待处理资金池","选择归类集对象名称(确认)"})
	public Reply doAllocationPer(){
		Map<String,Object> map = _getParameters();
		map.put("USERCODE", Security.getUser().getCode());//获取用户编号
		int i = service.doInsertPerPool(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "认款成功";
		}else {
			msg = "认款失败";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("资金管理","待处理资金池",msg));
	}
}
