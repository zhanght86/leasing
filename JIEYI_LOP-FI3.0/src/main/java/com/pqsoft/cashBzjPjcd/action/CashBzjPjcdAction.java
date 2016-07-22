package com.pqsoft.cashBzjPjcd.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.cashBzjPjcd.service.CashBzjPjcdService;
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
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class CashBzjPjcdAction extends Action{
	private CashBzjPjcdService service = new CashBzjPjcdService();
	private String pagePath = "cashDeposit/";
	@Override
	@aAuth(type = aAuthType.LOGIN)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	public Reply execute() {
		return new ReplyHtml(VM.html(pagePath+"to_Bzj_Pjcd.vm", null));
	}
	/*
	 * 获取可以做平均冲抵的数据
	 */
	@aAuth(type = aAuthType.LOGIN)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	public Reply getPjcdData(){
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.getPjcdData(map));
	}
	/*
	 * 跳转到流程申请页面
	 */
	@aAuth(type = aAuthType.LOGIN)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	public Reply startApplay_PjcdPage(){
		Map<String,Object> map = _getParameters();
		System.out.println(map.get("ID")+"-----------");
		Map<String,Object> map_tem = service.getPjcdDataById(map);
		System.out.println(map_tem+"------------------tem");
		List<Map<String,Object>> list = service.getPjcd_Fi_R_Beginning(map_tem);
		System.out.println(list+"------------------list");
		VelocityContext context = new VelocityContext();
		context.put("map", map_tem);
		context.put("list", list);
		return new ReplyHtml(VM.html(pagePath+"show_Pjcd_Applay.vm", context));
	}
	/*
	 * 校验是否已发起流程
	 */
	@aAuth(type = aAuthType.LOGIN)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	public Reply checkApplay_Pjcd(){
		Map<String,Object> map = _getParameters();
		//获取fi_rent_plan_head表中status状态
		int STATUS = service.checkApplay_Pjcd(map);
		return new ReplyAjax(true, STATUS);
	}
	/*
	 * 发起流程
	 */
	@aAuth(type = aAuthType.LOGIN)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	public Reply startApplay_Pjcd(){
		Map<String,Object> map = _getParameters();
		System.out.println(map+"------------------map");
		//通过id查询 剩余保证金、还款计划表状态、还款计划编号
		Map<String,Object> map_tem = service.getPjcdById(map);
		System.out.println(map+"------------------map");
		map_tem.put("REMARK_ID", Dao.selectInt("cashDepositK.searchApplayId", null));
		if((0+"").equals(map_tem.get("STATUS").toString())){
			map_tem.put("STATUS", "0");
		}
		map_tem.put("GOBACK_TIME", map.get("GOBACK_TIME"));
		//插入fi_bzj_qmcd_apply表数据
		Dao.insert("cashDepositK.insertApplay", map_tem);
		map_tem.put("STATUS_T", 26);
		Dao.update("cashDepositK.update_FRPH_StatusById", map_tem);
		
		Map<String,Object> map_temT = new HashMap<String, Object>();
		map_temT.put("ID", map_tem.get("REMARK_ID"));
		//发起流程
		List<String> list = JBPM.getDeploymentListByModelName("客户保证金平均冲抵申请","", Security.getUser().getOrg().getPlatformId());
		String jbpm_id =JBPM.startProcessInstanceById(list.get(0), "", "", "", map_temT).getId();
		String nextCode = new TaskService().getAssignee(jbpm_id);
		return new ReplyAjax(true, nextCode, "下一步操作人为: ");
	}
	/*
	 * 发起流程
	 */
	@aAuth(type = aAuthType.LOGIN)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	public Reply showProcessPage(){
		Map<String,Object> map = _getParameters();
		System.out.println(map+"------------------map");
		Map<String,Object> map_tem = service.getProcessDataByRemark_Id(map);
		List<Map<String,Object>> list = service.getProcessList(map_tem);
		VelocityContext context = new VelocityContext();
		context.put("map", map_tem);
		context.put("list", list);
		return new ReplyHtml(VM.html(pagePath+"show_Pjcd_ProcessPage.vm", context));
	}
	
	
}
