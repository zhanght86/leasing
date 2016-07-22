package com.pqsoft.yingsf.action;

import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.ReplyExcel;
import com.pqsoft.yingsf.service.YingsfService;

public class YingsfAction extends Action{
	YingsfService service = new YingsfService();
	@Override
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "放款管理", "应付管理","列表显示"})
	@aDev(code = "170043", email = "guozheng@pqsoft.cn", name = "guozheng")
	public Reply execute() {
		return new ReplyHtml(VM.html("yingsf/Yingsf.vm", null));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "放款管理", "应付管理","列表显示"})
	@aDev(code = "170043", email = "guozheng@pqsoft.cn", name = "guozheng")
	public Reply toPage() {
		Map<String, Object> map = _getParameters();
		return new ReplyAjaxPage(PageUtil.getPage(map, "yingsf.getPage", "yingsf.getPageCount"));
	}
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "放款管理", "应付管理","查看"})
	@aDev(code = "170043", email = "guozheng@pqsoft.cn", name = "guozheng")
	public Reply toInfo() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		List<Map<String, Object>> list = service.getList(map);
//		for (Map<String, Object> m : list) {
//			m.put("FI_REALITY_MONEY_F", UTIL.FORMAT.currency(m.get("FI_REALITY_MONEY")));
//			m.put("FI_PAY_MONEY", UTIL.FORMAT.currency(m.get("FI_PAY_MONEY")));
//			m.put("D_RECEIVE_DATE", UTIL.FORMAT.date(m.get("D_RECEIVE_DATE")));
//			m.put("FI_PAY_DATE_F", UTIL.FORMAT.date(m.get("FI_PAY_DATE")));
//			m.put("FI_ACCOUNT_DATE_F", UTIL.FORMAT.date(m.get("FI_ACCOUNT_DATE")));
//			m.put("FUND_ACCEPT_DATE_F", UTIL.FORMAT.date(m.get("FUND_ACCEPT_DATE")));//添加到帐日
//		}
		context.put("list", list);
		context.put("map", map);
		return new ReplyHtml(VM.html("yingsf/YingsfDetail.vm", context));
	}
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "放款管理", "应付管理","总计"})
	@aDev(code = "170043", email = "guozheng@pqsoft.cn", name = "guozheng")
	@SuppressWarnings("unchecked")
	public Reply findsum()
	{
		Map<String, Object> param = _getParameters();
		Map<String,Object> sumlist = service.findsum(param);
		return new ReplyJson(JSONArray.fromObject(sumlist));
	}
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "放款管理", "应付管理","导出"})
	@aDev(code = "170043", email = "guozheng@pqsoft.cn", name = "guozheng")
	public Reply exportExcel() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param.get("searchParams"));
		param.remove("searchParams");
		param.putAll(json);
		System.out.println("param-------"+param);
		ManageService mgService = new ManageService();
		Map supMap = (Map) mgService.getSupByUserId(Security.getUser().getId());
		if (supMap != null && supMap.get("SUP_ID") != null) {
			param.put("SUP_ID", supMap.get("SUP_ID"));
		}
		return new ReplyExcel(service.exportData(param), "workFlow"
				+ DateUtil.getSysDate() + Math.abs(new Random(10000).nextInt())
				+ ".xls");
	}
}
