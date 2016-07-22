package com.pqsoft.vehicleAlarm.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.vehicleAlarm.service.VehicleAlarmService;

public class VehicleAlarmAction extends Action {
	
	private VehicleAlarmService service = new VehicleAlarmService();
	private String basePath = "vehicleAlarm/";

	@Override
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	@aPermission(name = {"接口管理", "车辆报警管理", "列表显示"})
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		ArrayList<Object> ALARM_LEVEL = (ArrayList<Object>) new DataDictionaryMemcached().getList("报警级别");
		context.put("ALARM_LEVEL", ALARM_LEVEL);
		return new ReplyHtml(VM.html(basePath+"vehicleAlarm.vm", context)); 
	}
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	@aPermission(name = {"接口管理", "车辆报警管理", "列表显示"})
	public Reply getPage(){
		Map<String,Object> param = _getParameters();
		return new ReplyAjaxPage(service.getPage(param));
		
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply startAlarmByJbpm(){
		Map<String,Object> param = _getParameters();
		
		List<String> list = JBPM.getDeploymentListByModelName("报警关闭审批",
				null, Security.getUser()
						.getOrg().getPlatformId());
		String pid = null;
		if (list.size() > 0) {
			pid = list.get(0);
		} else
			throw new ActionException("没有找到流程图");

		if (pid == null) {
			throw new ActionException("没有匹配到审批流程");
		}
		Map<String, Object> jmap = new HashMap<String, Object>();
		
		jmap.put("ID", param.get("ID").toString());
		String jbpmId = JBPM.startProcessInstanceById(pid,
				null, null,null, jmap).getId();
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("ID", param.get("ID"));
		map.put("STATUS", 1);
		service.updateStauts(map);
		
		return new ReplyAjax(true, "流程发起成功");
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply toShowAlarmJbpm(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		Map<String, Object> alarmMap = service.selectAlarmById(param);
		context.put("alarmMap", alarmMap);
		return new ReplyHtml(VM.html(basePath+"toShowAlarmJbpm.vm", context));
	}

}
