package com.pqsoft.base.task.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.task.service.TaskClaimService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
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
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.ReplyExcel;

public class TaskClaimAction extends Action{

	private String path = "base/task/";
	private TaskClaimService service= new TaskClaimService();
	@Override
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"权限管理","任务分配","列表页"})
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param =_getParameters();
		param.put("CREATION_MAN", Security.getUser().getId());
		context.put("PContext", param);
		return new ReplyHtml(VM.html(path + "TaskClaim.vm", context));
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"权限管理","任务分配","查询" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply pageData() {
		Map<String, Object> param = _getParameters();
		BaseUtil.getDataAllAuth(param);
		Map<String, Object> m=service.getTask(Security.getUser().getId());
		param.putAll(m);
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"权限管理","任务认领","列表页"})
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply execute1() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param =_getParameters();
		context.put("PContext", param);
		//客户来源
		List<Object> CUST_SOURCE = (List)new DataDictionaryMemcached().get("客户来源");
		context.put("scaleSourceTypes", CUST_SOURCE);
		return new ReplyHtml(VM.html(path + "TaskClaim1.vm", context));
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"权限管理","任务认领","查询" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply pageData1() {
		Map<String, Object> param = _getParameters();
		Map<String, Object> m=service.getTaskOther(Security.getUser().getId(),Security.getUser().getOrg().getId());
		param.putAll(m);
		Page pagedata = service.getPageData1(param);
		return new ReplyAjaxPage(pagedata);
	}
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = {"权限管理","任务分配","认领任务" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply Claim() {
		Map<String, Object> m = _getParameters();
		String msg = "";
		Boolean flag = true ;
		m.put("STATE", 2);
		m.put("CLAIM_ID", Security.getUser().getId());
		m.put("CLAIM_MAN", Security.getUser().getName());
		m.put("ORGID", Security.getUser().getOrg().getId());
		List<Map<String,Object>> list=service.getNoClaim(m);
		if(list.size()>0)
		{
			
			Map<String, Object> map=(Map<String, Object>) list.get(0);
			map.put("CLAIM_ID", Security.getUser().getId());
			int num=service.getTaskNum(map);
			if(num>=Integer.parseInt(map.get("NUM").toString()))
			{
				msg ="认领任务失败,已到可领取上限!";
				flag = false; 
				return new ReplyAjax(flag, msg);
			}else{
				BaseUtil.getDataAllAuth(map);
				List<Map<String,Object>> all=service.getNoClaimAll(map);
				if(all.size()>0)
				{
					m.put("ID", all.get(0).get("ID"));
					int i=service.Claim(m);
					if(i>0){
						service.updFunction(m);
						msg ="认领任务成功!";
						flag = true; 
						return new ReplyAjax(flag, msg).addOp(new OpLog("任务分配", "认领任务", m.toString()));
					}else{
						msg ="认领任务失败!";
						flag = false; 
						return new ReplyAjax(flag, msg);
					}
				}else
				{
					msg ="认领任务失败,没有任务!";
					flag = false; 
					return new ReplyAjax(flag, msg);
				}
			}
		}else{
			msg ="认领任务失败,没有任务!";
			flag = false; 
			return new ReplyAjax(flag, msg);
		}
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = {"权限管理","任务分配","分配任务" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
	public Reply Allot () {
		Map<String, Object> m = _getParameters();
		String msg = "";
		Boolean flag = true ;
//		m.put("STATE", 1);
//		int i=service.Claim(m);
		int i=service.updTaskMan(m);
		if(i>0){
//			service.updFunction(m);
			msg ="分配任务成功!";
			flag = true; 
			return new ReplyAjax(flag, msg).addOp(new OpLog("任务分配", "分配任务", m.toString()));
		}else{
			msg ="分配任务失败!";
			flag = false; 
			return new ReplyAjax(flag, msg);
		}
	}
	
	
	//expTaskClaimExcel
	/**
	 * 已办任务
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "180003", email = "zhengsc@pqsoft.cn", name = "zhengshangcheng")
	public Reply expTaskClaimExcel() {
		List<String> list = new ArrayList<String>();
		list.add("已办任务");
		List<LinkedHashMap<String, String>> titles = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
		title.put("STATE", "任务状态");
		title.put("TASK_CREATETIME", "流程创建时间");
		title.put("DISTRIBUTION_DATE", "分配/认领时间");
		title.put("NAME", "任务名称");
		title.put("CLAIM_MAN", "任务人");
		title.put("CREATEMAN_NAME", "任务创建人");
		title.put("PRO_CODE", "进件编号");
		title.put("CUST_NAME", "客户姓名");
		title.put("SHOP_NAME", "门店名称");
		title.put("JCREATE_TIME", "提件时间");
		titles.add(title);
		List<List<Map<String, Object>>> data = new ArrayList<List<Map<String, Object>>>();
		Map<String, Object> param = _getParameters();
		Map<String, Object> m=service.getTaskOther(Security.getUser().getId(),Security.getUser().getOrg().getId());
		param.putAll(m);
		List<Map<String, Object>> l = Dao.selectList("Task.Claim.expList", param);
		data.add(l);
		Excel excel = new Excel("已办任务", list, titles, data);
		return new ReplyExcel(excel, "已办任务.xls");
	}
	
	
//	@aAuth(type=aAuth.aAuthType.USER)
//	@aPermission(name ={"权限管理","任务分配","获取分配人员"})
//	@aDev(code = "XQ0013", email = "xugm@pqsoft.com", name = "徐广明")
//	public Reply getUsers() {
//		VelocityContext context = new VelocityContext();
//		Map<String, Object> param =_getParameters();
//		param.put("USERID", Security.getUser().getId());
//		List<Map<String,Object>> list = service.getUsers(param);
//		context.put("list", list);
//		context.put("param", param);
//		return new ReplyHtml(VM.html(path + "getTaskClaimUser.vm", context));
//	}
}
