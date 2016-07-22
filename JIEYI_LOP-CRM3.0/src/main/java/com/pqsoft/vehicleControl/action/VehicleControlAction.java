package com.pqsoft.vehicleControl.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
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
import com.pqsoft.vehicleControl.service.VehicleControlService;

public class VehicleControlAction extends Action {

	private VehicleControlService service = new VehicleControlService();
	
	@Override
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	@aPermission(name = {"接口管理", "车辆远程控制管理", "列表显示"})
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		
		return new ReplyHtml(VM.html("vehicleControl/vehicleControl.vm", context));
	}

	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	@aPermission(name = {"接口管理", "车辆远程控制管理", "列表显示"})
	public Reply getPageData(){
		Map<String, Object> param = _getParameters();
		
		return new ReplyAjaxPage(service.getPageData(param));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply startControlByJbpm(){
		Map<String,Object> param = _getParameters();
		
		List<String> list = JBPM.getDeploymentListByModelName("车辆远程控制审批",
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
		
		jmap.put("EQ_ID", param.get("ID").toString());
		String jbpmId = JBPM.startProcessInstanceById(pid,
				null, null,null, jmap).getId();
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("ID", param.get("ID"));
		map.put("CONTROL_STATUS", 1);
		map.put("OPERATERESULT", "");
		service.updateStatus(map);
		return new ReplyAjax(true, "流程发起成功");
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply toShowControlJbpm(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> param = _getParameters();
		param.put("ID", param.get("EQ_ID"));
		Map<String, Object> eqMap = service.selectEquipmentById(param);
		context.put("eqMap", eqMap);
		return new ReplyHtml(VM.html("vehicleControl/toShowControlJbpm.vm", context));
	}
	
	
}
