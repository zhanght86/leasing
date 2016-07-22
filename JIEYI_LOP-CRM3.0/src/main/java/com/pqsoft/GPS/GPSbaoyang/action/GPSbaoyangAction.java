package com.pqsoft.GPS.GPSbaoyang.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.GPS.GPSbaoyang.service.GPSbaoyangService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class GPSbaoyangAction extends Action{
	GPSbaoyangService service=new GPSbaoyangService();
	@Override
	@aAuth(type = aAuthType.USER)
	@aPermission(name = {"资产管理", "GPS设备保养管理", "列表"})
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply execute() {
		VelocityContext context=new VelocityContext();
		return new ReplyHtml(VM.html("GPS/GPSbaoyang/GPSby.vm", context));
	}
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = {"资产管理", "GPS设备保养管理", "列表"})
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply findAll()
	{
		Map<String, Object> param = _getParameters();
		Page page = service.findAll(param);
		return new ReplyAjaxPage(page);
	}
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = {"资产管理", "GPS管理", "设备保养"})
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply findbyopen()
	{
		Map<String,Object> param=_getParameters();
		List list=service.findbyopen(param);
		return new ReplyJson(JSONArray.fromObject(list));
	}
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = {"资产管理", "GPS设备保养管理", "确认保养"})
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	public Reply saveby()
	{
		Map<String,Object> param=_getParameters();
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String hehe = dateFormat.format( now ); 
		param.put("BAO_DATE", hehe);
		Double DISTANCE=Double.valueOf(param.get("DISTANCE").toString());
		Double END_DISTANCE=Double.valueOf(param.get("END_DISTANCE").toString());
		int i=service.saveby(param);
		if(i>0)
		{
			service.updateby(param);
			if(END_DISTANCE-DISTANCE<=5)
			{
				return new ReplyAjax(true, "您好，您的设备保养成功!请进入gps管理中重新设定保养里程，谢谢合作!");
			}
			else
			{
				return new ReplyAjax(true, "您好，您还有"+(END_DISTANCE-DISTANCE)+"米需要进行保养");
			}
		}
		else
		{
			return new ReplyAjax(false, "操作失败!");
		}
	}
}