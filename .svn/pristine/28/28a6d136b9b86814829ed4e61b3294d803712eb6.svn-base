package com.pqsoft.baopei.action;

import java.util.Map;

import com.pqsoft.baopei.service.BaopeiService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class BaopeiAction extends Action{
	BaopeiService service=new BaopeiService();
	@Override
	@aPermission(name = { "保险管理", "理赔查询", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170050", email = "guozheng@pqsoft.cn", name = "guozheng")
	public Reply execute() {
		return new ReplyHtml(VM.html("baopei/Baopei.vm", null));
	}
	
	@aPermission(name = { "保险管理", "理赔查询", "列表显示" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170050", email = "guozheng@pqsoft.cn", name = "guozheng")
	public Reply getinsureList() {
		Map<String, Object> param = _getParameters();
		Page page = service.getList(param);
		return new ReplyAjaxPage(page);
	}
	@aPermission(name = { "保险管理", "理赔查询", "理赔" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170050", email = "guozheng@pqsoft.cn", name = "guozheng")
	public Reply lipei()
	{
		Map<String,Object> param=_getParameters();
		Double INSU_PRICE=Double.valueOf(param.get("INSU_PRICE").toString());
		Double baofei=Double.valueOf(param.get("baofei").toString());//实际保费
		Double cha=baofei-INSU_PRICE;
		param.put("cha", cha);
		if(cha>0)
		{
			int i=service.updategua(param);
			if(i>0)
			{
			return new ReplyAjax(true, "您还拥有余额"+cha+"，已存到挂账！");
			}else
			{
				return new ReplyAjax(false, "对不起，由于未知原因，操作失败！");
			}
		}
		else
		{
			int i=service.updatelai(param);
			if(i>0)
			{
				return new ReplyAjax(true, "您的资金不足还差"+(-cha)+"已添加到您的应付账单！");
			}
			else
			{
				return new ReplyAjax(false, "对不起，由于未知原因，操作失败！");
			}
		}
	}
}
