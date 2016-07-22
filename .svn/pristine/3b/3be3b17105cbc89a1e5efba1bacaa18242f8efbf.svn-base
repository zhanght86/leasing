package com.pqsoft.crm.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.overdueUrge.service.OverdueUrgeService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class OverdueUrgeAction extends Action {
	OverdueUrgeService service = new OverdueUrgeService();

	@Override
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资产管理", "函件催收", "列表显示" })
	@aDev(code = "170021", email = "zhuxu@pqsoft.cn", name = "zhuxu")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html("overdueUrge/urge_letter.vm", context));
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资产管理", "函件催收", "列表显示" })
	@aDev(code = "170043", email = "zhuxu@pqsoft.cn", name = "zhuxu")
	public Reply getUrgeLettrtData() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(new OverdueUrgeService()
				.getUrgeLettrtData(param));
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资产管理", "函件催收", "详细" })
	@aDev(code = "170043", email = "zhuxu@pqsoft.cn", name = "zhuxu")
	public Reply showUrgeLettrtData() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		context.put("map", service.getShowUrgeLettrtData(param));
		context.put("PAY_REASON", DataDictionaryMemcached.getList("拖欠还款原因"));
		return new ReplyHtml(VM
				.html("overdueUrge/show_urge_letter.vm", context));
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资产管理", "函件催收", "处理" })
	@aDev(code = "170043", email = "zhuxu@pqsoft.cn", name = "zhuxu")
	public Reply toAddUrgeLetter() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		context.put("map", service.getShowUrgeLettrtData(param));
		context.put("PAY_REASON", DataDictionaryMemcached.getList("拖欠还款原因"));
		return new ReplyHtml(VM.html("overdueUrge/add_urge_letter.vm", context));
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资产管理", "函件催收", "处理" })
	@aDev(code = "170043", email = "zhuxu@pqsoft.cn", name = "zhuxu")
	public Reply addUrgeLetter() {
		Map<String, Object> param = _getParameters();
		int i = service.addUrgeLetter(param);
		return new ReplyAjax(i == 1, null);
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资产管理", "电话催收", "列表显示" })
	@aDev(code = "170021", email = "zhuxu@pqsoft.cn", name = "zhuxu")
	public Reply urgePhone() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html("overdueUrge/urge_phone.vm", context));
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资产管理", "电话催收", "列表显示" })
	@aDev(code = "170043", email = "zhuxu@pqsoft.cn", name = "zhuxu")
	public Reply getUrgePhoneData() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(new OverdueUrgeService()
				.getUrgePhoneData(param));
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资产管理", "电话催收", "详细" })
	@aDev(code = "170043", email = "zhuxu@pqsoft.cn", name = "zhuxu")
	public Reply showUrgePhoneData() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		context.put("map", service.getShowUrgePhoneData(param));
		context.put("PAY_REASON", DataDictionaryMemcached.getList("拖欠还款原因"));
		return new ReplyHtml(VM.html("overdueUrge/show_urge_phone.vm", context));
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资产管理", "电话催收", "处理" })
	@aDev(code = "170043", email = "zhuxu@pqsoft.cn", name = "zhuxu")
	public Reply toAddUrgePhone() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		context.put("map", service.getShowUrgePhoneData(param));
		context.put("PAY_REASON", DataDictionaryMemcached.getList("拖欠还款原因"));
		return new ReplyHtml(VM.html("overdueUrge/add_urge_phone.vm", context));
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资产管理", "电话催收", "处理" })
	@aDev(code = "170043", email = "zhuxu@pqsoft.cn", name = "zhuxu")
	public Reply addUrgePhone() {
		Map<String, Object> param = _getParameters();
		int i = service.addUrgePhone(param);
		return new ReplyAjax(i == 1, null);
	}

	// ----------------------------------------------------------------------------------

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资产管理", "法务催收", "列表显示" })
	@aDev(code = "170021", email = "zhuxu@pqsoft.cn", name = "zhuxu")
	public Reply urgeLawyer() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html("overdueUrge/urge_lawyer.vm", context));
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资产管理", "法务催收", "列表显示" })
	@aDev(code = "170043", email = "zhuxu@pqsoft.cn", name = "zhuxu")
	public Reply getUrgeLawyerData() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(new OverdueUrgeService()
				.getUrgeLawyerData(param));
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资产管理", "法务催收", "详细" })
	@aDev(code = "170043", email = "zhuxu@pqsoft.cn", name = "zhuxu")
	public Reply showUrgeLawyerData() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		context.put("map", service.getShowUrgeLawyerData(param));
		context.put("PAY_REASON", DataDictionaryMemcached.getList("拖欠还款原因"));
		context.put("PHASELIST", DataDictionaryMemcached.getList("诉讼阶段"));
		return new ReplyHtml(VM
				.html("overdueUrge/show_urge_lawyer.vm", context));
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资产管理", "电话催收", "处理" })
	@aDev(code = "170043", email = "zhuxu@pqsoft.cn", name = "zhuxu")
	public Reply toAddUrgeLawyer() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		context.put("map", service.getShowUrgeLawyerData(param));
		context.put("PAY_REASON", DataDictionaryMemcached.getList("拖欠还款原因"));
		context.put("PHASELIST", DataDictionaryMemcached.getList("诉讼阶段"));
		return new ReplyHtml(VM.html("overdueUrge/add_urge_lawyer.vm", context));
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资产管理", "电话催收", "处理" })
	@aDev(code = "170043", email = "zhuxu@pqsoft.cn", name = "zhuxu")
	public Reply addUrgeLawyer() {
		Map<String, Object> param = _getParameters();
		int i = service.addUrgeLawyer(param);
		return new ReplyAjax(i == 1, null);
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资产管理", "电话催收", "日志" })
	@aDev(code = "170043", email = "zhuxu@pqsoft.cn", name = "zhuxu")
	public Reply showUrgeLawyerLog() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		List urgeLog = service.showUrgeLawyerLog(param);
		context.put("urgeLog", urgeLog);
		return new ReplyHtml(VM.html("overdueUrge/urge_log.vm", context));
		/*return new ReplyJson(JSONArray.fromObject(service
				.showUrgeLawyerLog(param)));*/
	}

	// ----------------------------------------------------------------------------------

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资产管理", "法务催收", "列表显示" })
	@aDev(code = "170021", email = "zhuxu@pqsoft.cn", name = "zhuxu")
	public Reply urgeForce() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html("overdueUrge/urge_force.vm", context));
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资产管理", "法务催收", "列表显示" })
	@aDev(code = "170043", email = "zhuxu@pqsoft.cn", name = "zhuxu")
	public Reply getUrgeForceData() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(new OverdueUrgeService()
				.getUrgeForceData(param));
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资产管理", "法务催收", "详细" })
	@aDev(code = "170043", email = "zhuxu@pqsoft.cn", name = "zhuxu")
	public Reply showUrgeForceData() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		context.put("map", service.getShowUrgeForceData(param));
		context.put("PAY_REASON", DataDictionaryMemcached.getList("拖欠还款原因"));
		context.put("PHASELIST", DataDictionaryMemcached.getList("收车状态"));
		return new ReplyHtml(VM.html("overdueUrge/show_urge_force.vm", context));
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资产管理", "电话催收", "处理" })
	@aDev(code = "170043", email = "zhuxu@pqsoft.cn", name = "zhuxu")
	public Reply toAddUrgeForce() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		context.put("map", service.getShowUrgeForceData(param));
		context.put("PAY_REASON", DataDictionaryMemcached.getList("拖欠还款原因"));
		context.put("PHASELIST", DataDictionaryMemcached.getList("收车状态"));
		return new ReplyHtml(VM.html("overdueUrge/add_urge_force.vm", context));
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资产管理", "电话催收", "处理" })
	@aDev(code = "170043", email = "zhuxu@pqsoft.cn", name = "zhuxu")
	public Reply addUrgeForce() {
		Map<String, Object> param = _getParameters();
		int i = service.addUrgeForce(param);
		return new ReplyAjax(i == 1, null);
	}

	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资产管理", "逾期催收", "列表显示" })
	@aDev(code = "170021", email = "zhuxu@pqsoft.cn", name = "zhuxu")
	public Reply allotTask() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		ArrayList typelist = new DataDictionaryMemcached().get("催收任务分配类型");
		context.put("typelist", typelist);
		context.put("param", param);
		return new ReplyHtml(VM.html("crm/overdue-allotTask.vm", context));
	}

}
