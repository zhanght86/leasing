package com.pqsoft.cashBzjQmth.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.cashBzjQmth.service.CashBzjQmthService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class CashBzjQmthAction extends Action{
	CashBzjQmthService service = new CashBzjQmthService();
	private String pagePath = "cashDeposit/";
	
	/*
	 * 跳转到期末退回页面
	 */
	@Override
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末退回", "列表"})
	public Reply execute() {
		return new ReplyHtml(VM.html(pagePath+"to_Bzj_Qmth.vm", null));
	}
	/*
	 * 跳获取期末退回列表
	 */
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末退回", "列表"})
	public Reply getBzj_Qmth_All(){
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.getBzj_Qmth_All(map));
	}
	/*
	 * 跳转到发起申请页面
	 */
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末退回", "申请页面"})
	public Reply startApplayPage(){
		Map<String,Object> map = _getParameters();
		Map<String,Object> map_tem = service.selectTHById(map);
		map.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
		Map<String,Object> map2 = Dao.selectOne("fi.fund.getBinkInfo", map);
		map_tem.put("FA_NAME", map2.get("FA_NAME"));
		map_tem.put("FA_BINK", map2.get("FA_BINK"));
		map_tem.put("FA_ACCOUNT", map2.get("FA_ACCOUNT"));
		List<Map<String,Object>> list = service.getFi_R_Beginning_ListById(map);
		VelocityContext context = new VelocityContext();
		context.put("map", map_tem);
		context.put("list", list);
		return new ReplyHtml(VM.html(pagePath+"show_Qmth_Applay.vm", context));
	}
	/*
	 * 发起期末退回申请
	 */
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末退回", "申请"})
	public Reply startApplay_QMTH(){
		Map<String,Object> map = _getParameters();
		map.put("REMARK_ID", Dao.selectInt("cashDepositK.searchApplayId", null));
		//插入数据到中间表
		Dao.insert("cashDepositK.insertApplay", map);
		//修改fi_rent_plan_head表中status状态
		map.put("STATUS_T", 25);
		Dao.update("cashDepositK.update_FRPH_StatusById", map);
		Map<String,Object> map_tem = new HashMap<String, Object>();
		map_tem.put("ID", map.get("REMARK_ID"));
		//发起流程
		List<String> list = JBPM.getDeploymentListByModelName("客户保证金期末退回申请","", Security.getUser().getOrg().getPlatformId());
		String jbpm_id =JBPM.startProcessInstanceById(list.get(0), "", "", "", map_tem).getId();
		String nextCode = new TaskService().getAssignee(jbpm_id);
		return new ReplyAjax(true, nextCode, "下一步操作人为: ");
	}
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末退回", "查询"})
	public Reply getDeposit_syInfo(){
		Map<String,Object> map = _getParameters();
		Map<String,Object> sy_money = Dao.selectOne("cashDepositK.getDeposit_syInfo", map);
		return new ReplyAjax(true, sy_money);
	}
	/*
	 * 检测是否已发起流程
	 */
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末退回", "查询"})
	public Reply checkApplay_QMTH(){
		Map<String,Object> map = _getParameters();
		//获取fi_rent_plan_head表中status状态
		int STATUS = service.checkApplay_QMTH(map);
		return new ReplyAjax(true, STATUS);
	}
	/*
	 * 流程中页面
	 */
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末退回", "审核页面"})
	public Reply showApplayPage(){
		Map<String,Object> map = _getParameters();
		Map<String,Object> map_tem = service.getBzj_Qmth_DataById(map);
		List<Map<String,Object>> list = service.getFRBListByRemarkId(map);
		VelocityContext context = new VelocityContext();
		context.put("map", map_tem);
		context.put("list", list);
		return new ReplyHtml(VM.html(pagePath+"show_Qmth_Process.vm", context));
	}
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末退回", "付款账户"})
	public Reply getBankInfo() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html(pagePath+"showBankInfo.vm", context));
	}
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末退回", "付款账户"})
	public Reply getBankData() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.getBankData(param));
	}
}
