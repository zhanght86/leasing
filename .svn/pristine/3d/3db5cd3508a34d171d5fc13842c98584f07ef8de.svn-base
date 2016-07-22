package com.pqsoft.projectBudget.action;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.projectBudget.service.ProjectBudgetService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.RX_PaymentInternalReturnLine;
import com.pqsoft.util.Util;

public class ProjectBudgetAction extends Action {
	/*********** @auth: king 2014年8月14日 项目测算跟进 *************************/

	private String path = "projectBudget/";

	private ProjectBudgetService service = new ProjectBudgetService();

	@Override
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	@aPermission(name = { "项目管理", "项目跟踪测算", "列表显示" })
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html(path + "projectBudgetManager.vm", context));
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	// @aPermission(name={"项目管理", "项目跟踪测算", "列表显示"})
	public Reply doMgProjectBudget() {
		Map<String, Object> param = _getParameters();
		param.putAll(JSONObject.fromObject(param.get("param")));
		return new ReplyAjaxPage(service.doMgProjectBudget(param));
	}

	/**
	 * 进入测算界面
	 * 
	 * @return
	 * @author King 2014年8月18日
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply toShowEstimates() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		try {
			param.putAll(service.queryShowEstimates(param.get("PROJECT_ID"),
					param.get("PROJECT_SCHEME_ID")));
		} catch (Exception e) {
			throw new ActionException("没有索引到指定方案信息");
		}
		JSONArray list = JSONArray.fromObject(param.get("SCHEME_CLOB"));
		param.remove("SCHEME_CLOB");
		context.put("param", param);
		context.put("dicTag", Util.DICTAG);
		context.put("SCHEMELIST", list);
		return new ReplyHtml(VM.html(path + "showEstimates.vm", context));
	}

	/**
	 * 测算收益率
	 * 
	 * @return
	 * @author King 2014年8月18日
	 */
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply doCalculateIRR() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		JSONObject json = JSONObject.fromObject(param.get("param"));
		try {
			List<RX_PaymentInternalReturnLine> list = service
					.RX_RentEstimates(json);
			context.put("list", list);
			context.put("listParam", list.get(Integer.valueOf(json.get(
					"LEASE_TERM").toString()) + 1));
		} catch (Exception e) {
			throw new ActionException("测算失败");
		}
		return new ReplyAjax(true, VM.html(path + "estimatesIRR.vm", context),
				"");
	}
}
