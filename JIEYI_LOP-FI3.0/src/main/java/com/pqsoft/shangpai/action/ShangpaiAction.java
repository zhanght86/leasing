package com.pqsoft.shangpai.action;

import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.shangpai.service.ShangpaiService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class ShangpaiAction extends Action{

	public VelocityContext context=new VelocityContext();
	ShangpaiService service=new ShangpaiService();
	@Override
	@aPermission(name = { "车辆管理", "上牌管理", "列表显示" })
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	@aAuth(type = aAuthType.USER)
	public Reply execute() {
		context.put("param", _getParameters());
		return new ReplyHtml(VM.html("shangpai/Shangpai.vm", context));
	}
	@aPermission(name = { "车辆管理", "上牌管理", "列表显示" })
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply findshangpai()
	{
		Map<String, Object> param = _getParameters();
		Page page = service.findshangpai(param);
		return new ReplyAjaxPage(page);
	}
	@aPermission(name = { "车辆管理", "上牌管理", "添加" })
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	@aAuth(type = aAuthType.USER)
	public Reply saveshangpai()
	
	{
		Map<String, Object> param = _getParameters();
		int i=service.saveshangpai(param);
		if(i>0)
		{
			return new ReplyAjax(true, "添加成功！");
		}
		else
		{
			return new ReplyAjax(true, "添加失败！");
		}
	}
	@aPermission(name = { "车辆管理", "上牌管理", "查看" })
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	@aAuth(type = aAuthType.USER)
	public Reply findone()
	{
		Map<String, Object> param = _getParameters();
		context.put("looklist", service.findone(param));
		return new ReplyHtml(VM.html("shangpai/lookshangpai.vm", context));
	}
	@aPermission(name = { "车辆管理", "上牌管理", "修改" })
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	@aAuth(type = aAuthType.USER)
	public Reply findbyid()
	{
		Map<String, Object> param = _getParameters();
		context.put("looklist", service.findone(param));
		return new ReplyHtml(VM.html("shangpai/updateshangpai.vm", context));
	}
	@aPermission(name = { "车辆管理", "上牌管理", "修改" })
	@aDev(code = "170051", email = "guozheng@163.com", name = "guozheng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply updateshangpai()
	{
		Map<String, Object> param = _getParameters();
		int i=service.updateshangpai(param);
		if(i>0)
		{
			return new ReplyAjax(true, "修改成功！");
		}
		else
		{
			return new ReplyAjax(true, "修改失败！");
		}
	}

}
