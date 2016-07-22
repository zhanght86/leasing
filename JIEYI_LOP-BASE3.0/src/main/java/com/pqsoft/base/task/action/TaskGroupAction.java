package com.pqsoft.base.task.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.task.service.TaskGroupService;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;


public class TaskGroupAction extends Action{
	private String path = "base/task/";
	private TaskGroupService service= new TaskGroupService();
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"参数配置","任务分配管理","任务组管理","列表显示"})
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param =_getParameters();
		context.put("PContext", param);
		return new ReplyHtml(VM.html(path + "TaskGroup.vm", context));
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"参数配置","任务分配管理","任务组管理","查询" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply pageData() {
		Map<String, Object> param = _getParameters();
		param.put("CREATION_MAN", Security.getUser().getOrg().getId());
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"参数配置","任务分配管理","任务组管理","添加[按钮]" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply add() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> m = new HashMap<String, Object>();
		List taskname=(List)new SysDictionaryMemcached().get("任务名称");
//		List<Map<String,Object>> taskname=service.getTaskName();
		context.put("taskname", taskname);
		Map<String, Object> param =_getParameters();
		String ORG_ID="";
		if(Security.getUser().getOrg()==null)
		{
			ORG_ID="0";
		}else
		{
			ORG_ID=Security.getUser().getOrg().getId();
		}
		List personnel=service.getNextAllUser(ORG_ID);
		context.put("personnel", personnel);
		return new ReplyHtml(VM.html(path + "addTaskGroup.vm", context));
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"参数配置","任务分配管理","任务组管理","查询规则验证" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply selTaskAllication() {
		Map<String, Object> param =_getParameters();
		List<Map<String,Object>> list=service.getTaskAllication(param);
		String msg = "";
		Boolean flag = true ;
		if(list.size()==0)
		{
			msg ="所选任务名未配置规则，请先配置规则!";
			flag = false; 
			return new ReplyAjax(flag, msg);
		}else
		{
			flag = true; 
			return new ReplyAjax(flag, msg);
		}
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"参数配置","任务分配管理","任务组管理","查询规则" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply getTaskAllication() {
		Map<String, Object> param =_getParameters();
		VelocityContext context = new VelocityContext();
		List<Map<String,Object>> list=service.getTaskAllication(param);
		context.put("list", list);
		return new ReplyAjax(true, VM.html(path + "getTaskA.vm", context), "查询成功");
	}
	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"参数配置","任务分配管理","任务组管理","添加保存" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply doAdd() {
		Map<String, Object> param =_getParameters();
		Map<String, Object> m =new HashMap<String, Object>();
		param.put("CREATION_ID", Security.getUser().getOrg().getId());
		JSONArray json=JSONArray.fromObject(param.get("SECU"));
		String ID=service.doAdd(param);
		int j=0;
		for(int i=0;i<json.size();i++)
		{
			m=(Map<String, Object>) json.get(i);
			m.put("GROUP_ID", ID);
			j=service.doAdd1(m)+j;
		}
		String msg = "";
		Boolean flag = true ;
		if(json.size()==j)
		{
			msg ="保存成功";
			flag = true; 
			return new ReplyAjax(flag, msg).addOp(new OpLog("任务组管理", "添加", m.toString()));
		}else
		{
			msg ="保存失败";
			flag = false; 
			return new ReplyAjax(flag, msg);
		}
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"参数配置","任务分配管理","任务组管理","修改[按钮]" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply upd() {
		VelocityContext context = new VelocityContext();
		List taskname=(List)new SysDictionaryMemcached().get("任务名称");
//		List<Map<String,Object>> taskname=service.getTaskName();
		context.put("taskname", taskname);
		Map<String, Object> param =_getParameters();
		String ORG_ID="";
		if(Security.getUser().getOrg()==null)
		{
			ORG_ID="0";
		}else
		{
			ORG_ID=Security.getUser().getOrg().getId();
		}
		List<Map<String,Object>> list=service.getPersonnel(param);
		Map<String,Object> map=service.getConfigure(param);
		context.put("list", list);
		context.put("map", map);
		context.put("num", list.size());
		List<Map<String,Object>> personnel=service.getNextAllUser(ORG_ID);
		context.put("personnel", personnel);
		return new ReplyHtml(VM.html(path + "updTaskGroup.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"参数配置","任务分配管理","任务组管理","修改保存" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply doupd() {
		Map<String, Object> param =_getParameters();
		Map<String, Object> m =new HashMap<String, Object>();
		JSONArray json=JSONArray.fromObject(param.get("SECU"));
		param.put("CREATION_ID", Security.getUser().getOrg().getId());
//		service.del(param);
		service.delPersonnel(param);
		service.doupd(param);
		String ID=param.get("ID").toString();
		int j=0;
		for(int i=0;i<json.size();i++)
		{
			m=(Map<String, Object>) json.get(i);
			m.put("GROUP_ID", ID);
			j=service.doAdd1(m)+j;
		}
		String msg = "";
		Boolean flag = true ;
		if(json.size()==j)
		{
			msg ="保存成功";
			service.updtype(param);
			flag = true; 
			return new ReplyAjax(flag, msg).addOp(new OpLog("任务组管理", "添加", m.toString()));
		}else
		{
			msg ="保存失败";
			flag = false; 
			return new ReplyAjax(flag, msg);
		}
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"参数配置","任务分配管理","任务组管理","删除" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply del() {
		Map<String, Object> m = _getParameters();
		int result=service.del(m);
		service.delPersonnel(m);
		String msg = "";
		Boolean flag = true ;
		if(result>0){
			msg ="删除成功!";
			flag = true; 
			return new ReplyAjax(flag, msg).addOp(new OpLog("任务组管理", "删除", m.toString()));
		}else{
			msg ="删除失败!";
			flag = false; 
			return new ReplyAjax(flag, msg);
		}
	}
}
