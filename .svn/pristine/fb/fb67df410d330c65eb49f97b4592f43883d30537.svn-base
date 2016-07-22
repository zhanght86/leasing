package com.pqsoft.vehicleLocation.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.vehicleLocation.service.VehicleLocationService;

public class VehicleLocationAction extends Action {

	private VehicleLocationService service = new VehicleLocationService();
	
	@Override
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	@aPermission(name = {"接口管理", "车辆位置定位", "列表显示"})
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("vehicleLocation/toMgVehicleLocation.vm", context));
	}

	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	@aPermission(name = {"接口管理", "车辆位置定位", "列表显示"})
	public Reply getPageData(){
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.getPageData(param));
	}
	
	
}
