package com.pqsoft.base.grantCredit.action;

import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.grantCredit.service.CompanyRefundService;
import com.pqsoft.base.grantCredit.service.SupplierRefundService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class CompanyRefundAction extends Action {
	private String path = "base/grantCredit/";
	private CompanyRefundService service = new CompanyRefundService();
	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "授信管理", "经销商授信管理", "列表" })
	@aDev(code = "170016", email = "1226781445@qq.com", name = "肖云飞")
	public Reply showRefund(){
		Map<String,Object> map = _getParameters();
		System.out.println(map+"--zz------dd-----ss-----");
		Map<String,Object> map_param = service.showRefundById(map);
		System.out.println(map_param+"-----------");
		map.put("TYPE", 1);
		map.put("STATUS", 2);
		map.put("T_HEAD_ID", map_param.get("T_HEAD_ID"));
		List<Map<String,Object>> list = service.selectRefundList(map);
		VelocityContext context = new VelocityContext();
		context.put("list", list);
		context.put("map", map_param);
		return new ReplyHtml(VM.html(path + "companyRefundShow.vm", context));
	}
}
