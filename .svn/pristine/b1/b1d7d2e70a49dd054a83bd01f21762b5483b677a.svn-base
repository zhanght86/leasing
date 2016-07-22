package com.pqsoft.GPS.GPSbytx.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.GPS.GPSbytx.service.GPSbytxService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class GPSbytxAction extends Action{
	GPSbytxService service=new GPSbytxService();
	@Override
	@aAuth(type = aAuthType.USER)
	@aPermission(name = {"资产管理", "GPS设备保养提醒", "列表"})
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply execute() {
		VelocityContext context=new VelocityContext();
		return new ReplyHtml(VM.html("GPS/GPSbytx/GPSbytx.vm", context));
	}
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = {"资产管理", "GPS设备保养提醒", "列表"})
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply findAll()
	{
		Map<String,Object> param=_getParameters();
		Page page=service.findAll(param);
		return new ReplyAjaxPage(page);
	}

}
