package com.pqsoft.base.task.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.task.service.TaskAllocationService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;


public class TaskAllocationAction extends Action{

	private String path = "base/task/";
	private TaskAllocationService service= new TaskAllocationService();
	
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置", "任务分配管理", "任务分配规则管理", "列表显示"})
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param =_getParameters();
		context.put("PContext", param);
		List taskname=(List)new SysDictionaryMemcached().get("任务名称");
		context.put("taskname", taskname);
		return new ReplyHtml(VM.html(path + "TaskAllocation.vm", context));
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"参数配置", "任务分配管理", "任务分配规则管理", "列表显示" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply pageData() {
		Map<String, Object> param = _getParameters();
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"参数配置","任务分配管理", "任务分配规则管理","添加" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply add() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> m = new HashMap<String, Object>();
//		List taskname=(List)new SysDictionaryMemcached().get("任务名称");
		List<Map<String,Object>> taskname=service.getTaskName(m);
		context.put("taskname", taskname);
		return new ReplyHtml(VM.html(path + "addTaskAllocation.vm", context));
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"参数配置", "任务分配管理", "任务分配规则管理", "添加" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply IsRepeat() {
		Map<String, Object> m = _getParameters();
		int result=service.IsRepeat(m);
		String msg = "";
		Boolean flag = true ;
		if(result==0){
			msg ="规则名称可以录入!";
			flag = true; 
			return new ReplyAjax(flag, msg);
		}else{
			msg ="规则名称重复!";
			flag = false; 
			return new ReplyAjax(flag, msg);
		}
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"参数配置", "任务分配管理", "任务分配规则管理", "添加" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply doAdd() {
		Map<String, Object> m = _getParameters();
		String msg = "";
		Boolean flag = true ;
		//规则名称数量
		int num=service.getnum(m);
		if(num==0)
		{
		int result=service.doAdd(m);
		if(result>0){
			msg ="保存成功!";
			flag = true; 
			return new ReplyAjax(flag, msg).addOp(new OpLog("任务分配规则管理", "添加", m.toString()));
		}else{
			msg ="保存失败!";
			flag = false; 
			return new ReplyAjax(flag, msg);
		}
		}else
		{
			msg ="重复的规则名称!";
			flag = false; 
			return new ReplyAjax(flag, msg);
		}
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"参数配置","任务分配管理", "任务分配规则管理","修改" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply upd() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> m = _getParameters();
		Map<String, Object> data =service.getOneTaskAllocation(m);
		context.put("data", data);
//		List taskname=(List)new SysDictionaryMemcached().get("任务名称");
		List<Map<String,Object>> taskname=service.getTaskName(m);
		context.put("taskname", taskname);
		return new ReplyHtml(VM.html(path + "updTaskAllocation.vm", context));
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"参数配置", "任务分配管理", "任务分配规则管理", "修改" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply doUpd() {
		Map<String, Object> m = _getParameters();
		String msg = "";
		Boolean flag = true ;
		int num=service.getnum(m);
		if(num==0)
		{
			int result=service.doUpd(m);
			if(result>0){
				msg ="保存成功!";
				flag = true; 
				return new ReplyAjax(flag, msg).addOp(new OpLog("任务分配规则管理", "修改", m.toString()));
			}else{
				msg ="保存失败!";
				flag = false; 
				return new ReplyAjax(flag, msg);
			}
		}else
		{
			msg ="重复的规则名称!";
			flag = false; 
			return new ReplyAjax(flag, msg);
		}
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"参数配置", "任务分配管理", "任务分配规则管理", "删除" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply del() {
		Map<String, Object> m = _getParameters();
		String msg = "";
		Boolean flag = true ;
		int i=service.getGroup(m);
		if(i>0)
		{
			msg ="删除失败,规则使用中";
			flag = false; 
			return new ReplyAjax(flag, msg);
		}else{
		int result=service.del(m);
		if(result>0){
			msg ="删除成功!";
			flag = true; 
			return new ReplyAjax(flag, msg).addOp(new OpLog("任务分配规则管理", "删除", m.toString()));
		}else{
			msg ="删除失败!";
			flag = false; 
			return new ReplyAjax(flag, msg);
		}
		}
	}
}
