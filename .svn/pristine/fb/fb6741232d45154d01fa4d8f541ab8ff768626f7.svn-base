package com.pqsoft.badBebt.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.badBebt.service.BadBebtService;
import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class BadBebtManagementAction extends Action{
	private BadBebtService service = new BadBebtService();
	@Override
	public Reply execute() {
		return null;
	}
	@aPermission(name = { "催收管理", "坏账申请", "列表显示" })
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aAuth(type = aAuthType.USER)
	public Reply badBebtShow() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("badBebtManagement/badBebtShow.vm", context)).addOp(new OpLog("资产管理", "诉讼管理", "诉讼查询错误"));
	}
	@aPermission(name = { "催收管理", "坏账申请", "列表显示" })
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aAuth(type = aAuthType.USER)
	public Reply getBadBebtList() {
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.getBadBebtList(map));
	}
	
	@aPermission(name = { "催收管理", "坏账申请", "申请页面" })
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aAuth(type = aAuthType.USER)
	public Reply badBebtApplication() {
		Map<String,Object> map = _getParameters();
		JSONArray jsonArray = JSONArray.fromObject(map.get("params"));
		VelocityContext context = new VelocityContext();
		Map<String,Object> map_tem = (Map<String, Object>) jsonArray.get(0);
		map_tem.put("DEMO", 1);
		context.put("map", map_tem);
		context.put("overDue_Type", new DataDictionaryMemcached().get("催收类型"));
		context.put("NotRepaymentReason", new DataDictionaryMemcached().get("逾期原因"));
		context.put("PressResults", new DataDictionaryMemcached().get("催收反馈"));
		context.put("CollectRecords",service.getCUISRecord(map_tem));//催收记录
		context.put("LITIGATION_CASE",service.getSUSRecord(map_tem));//诉讼记录
		return new ReplyHtml(VM.html("badBebtManagement/badBebtApplication.vm", context));
	}
	/*
	 * 添加一条坏账数据进入坏账表
	 */
	@aPermission(name = { "催收管理", "坏账申请", "添加" })
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aAuth(type = aAuthType.USER)
	public Reply insertBadBebtData() {
		Map<String,Object> param = _getParameters();
		Map<String,Object> map = (Map<String, Object>)(JSONArray.fromObject(param.get("param")).get(0));
		map.put("ID", Dao.selectInt("badBebt.selectBadBebtId", null));
		map.put("STATUS", 1);
		int i = service.insertBadBebtData(map);
		if(i == 1){
			Dao.update("badBebt.updatePayListCode", map);
		}
		//发起流程
		Map JBPMMAP=new HashMap();
		JBPMMAP.put("ID", map.get("ID").toString());
		System.out.println(Security.getUser().getOrg().getPlatformId()+"---JPBM");
		List<String> list = JBPM.getDeploymentListByModelName("BadDebt","", Security.getUser().getOrg().getPlatformId());
		String jbpm_id =JBPM.startProcessInstanceById(list.get(0), "", "", "", JBPMMAP).getId();
		String nextCode = new TaskService().getAssignee(jbpm_id);
		return new ReplyAjax(true, nextCode, "下一步操作人为: ");
	}
	/*
	 * 发起流程时，显示页面
	 */
	@aPermission(name = { "催收管理", "坏账申请", "流程页面" })
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aAuth(type = aAuthType.USER)
	public Reply showBadBebtInfo() {
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		Map<String,Object> map2 = service.getBadBebtInfo(map);
		map2.put("DEMO", 2);
		context.put("map", map2);
		context.put("overDue_Type", new DataDictionaryMemcached().get("催收类型"));
		context.put("NotRepaymentReason", new DataDictionaryMemcached().get("逾期原因"));
		context.put("PressResults", new DataDictionaryMemcached().get("催收反馈"));
		context.put("CollectRecords",service.getCUISRecord(map2));//催收记录
		context.put("LITIGATION_CASE",service.getSUSRecord(map2));//诉讼记录
		return new ReplyHtml(VM.html("badBebtManagement/badBebtApplication.vm", context));
	}
	/*
	 * 显示所有的坏账信息
	 */
	@aPermission(name = { "催收管理", "坏账查询", "列表" })
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aAuth(type = aAuthType.USER)
	public Reply badBebtShowInfo() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("badBebtManagement/badBebtShowInfo.vm", context));
	}
	/*
	 * 显示所有的坏账信息
	 */
	@aPermission(name = { "催收管理", "坏账查询", "列表" })
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aAuth(type = aAuthType.USER)
	public Reply getBadBebtShowInfo() {
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.getBadBebtShowInfo(map));
	}
}
