package com.pqsoft.cashBzjQmdk.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import net.sf.json.JSONArray;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.cashBzjQmdk.service.CashBzjQmdkService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.ReplyMobile;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class CashBzjQmdkAction extends Action {
	private String path = "cashDeposit/";
	private CashBzjQmdkService service = new CashBzjQmdkService();
	@Override
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末抵扣", "列表"})
	public Reply execute() {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
		List<Map<String,Object>> list = Dao.selectList("fi.fund.getBinkInfoF", map);
		VelocityContext context = new VelocityContext();
		context.put("list", list);
		return new ReplyHtml(VM.html(path+"to_Bzj_Qmdk.vm", context));
	}
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末抵扣", "列表"})
	public Reply getBzjQmdkData() {
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.getBzjQmdkData(map));
	}
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末抵扣", "保证金转来款"})
	public Reply convert_To_FiFund() {
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.getBzjQmdkData(map));
	}
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末抵扣", "保证金退回"})
	public Reply getRefundData() {
		Map<String,Object> map = _getParameters();
		Map<String,Object> map_tem = service.getCustNameBankInfo(map);
		map.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
		List<Map<String,Object>> list = Dao.selectList("fi.fund.getBinkInfo", map);
		map_tem.put("FA_NAME", Security.getUser().getOrg().getPlatform());//付款人
		return new ReplyAjax(true, map_tem);
	}
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末抵扣", "查询"})
	public Reply getDeposit_syInfo() {
		Map<String,Object> map = _getParameters();
		Map<String,Object> sy_money = Dao.selectOne("cashDepositK.getDeposit_syInfo", map);
		return new ReplyAjax(true, sy_money);
	}
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末抵扣", "保证金退回申请"})
	public Reply startApplayQMDK_TH(){
		//期末抵扣退回申请流程
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
	@aPermission(name = {"资金管理", "客户保证金期末抵扣", "保证金转来款"})
	public Reply startQMDK_ZLK(){
		//期末抵扣转来款
		Map<String,Object> map = _getParameters();
		System.out.println(map+"-----dds-------");
		//1生成一条来款记录插入fi_fund表
		//2生成一条记录到剩余保证金操作记录表
		//3更新还款计划表剩余保证金
		
		//1
		String FUND_ID_BZJ=Dao.selectOne("fi.fund.getFundId");
		String FUND_CODE_BZJ=Dao.selectOne("fi.fund.getFundCode");
		Map<String,Object> map_tem = new HashMap<String, Object>();
		map_tem.put("PAYLIST_CODE", map.get("PAYLIST_CODE"));
		map_tem.put("FUND_ACCEPT_DATE", map.get("CREATE_TIME"));//来款时间
		map_tem.put("FUND_COMENAME", map.get("CUST_NAME"));//来款人
		map_tem.put("FUND_RECEIVE_MONEY", map.get("DEPOSIT_SY"));//来款金额
		map_tem.put("FUND_SY_MONEY", map.get("DEPOSIT_SY"));//来款金额
		map_tem.put("FUND_ID", FUND_ID_BZJ);
		map_tem.put("FUND_FUNDCODE", FUND_CODE_BZJ);
		map_tem.put("FUND_NOTDECO_STATE", "4");
		map_tem.put("FUND_STATUS", "0");
		map_tem.put("FUND_ISCONGEAL", "0");
		map_tem.put("FUND_RED_STATUS", "0");
		map_tem.put("FUND_PRANT_ID", "0");
		map_tem.put("FUND_IMPORT_PERSON", Security.getUser().getName());
		map_tem.put("FUND_IMPORT_PERSON_ID", Security.getUser().getId());
		map_tem.put("FUND_CLIENT_ID", map.get("CUST_ID"));//认款人
		map_tem.put("FUND_CLIENT_NAME", map.get("CUST_NAME"));//认款人
		map_tem.put("FUND_PAY_CODE", map.get("PAYLIST_CODE"));//将要分解到那张支付表
		map_tem.put("FUND_TYPE", 2);
		map_tem.put("FUND_DOCKET", "期末剩余客户保证金转来款！");//来款摘要
		Dao.insert("fi.fund.add", map_tem);
		//2
		double refund_money = Double.parseDouble(map.get("DEPOSIT_SY").toString());
		double bzj_sy_money = Double.parseDouble(map.get("DEPOSIT_SY_TEM").toString());
		map.put("BEGIN_BZJ", map.get("DEPOSIT_SY_TEM"));
		map.put("SY_BZJ", bzj_sy_money-refund_money);
		map.put("FUND_ID", FUND_ID_BZJ);
		map.put("FUND_CODE", FUND_CODE_BZJ);
		map.put("REMARK", "期末剩余保证金转来款，转来款金额"+refund_money);
		map.put("OPERATOR_MAN", Security.getUser().getName());
		map.put("TYPE", "转来款");
		Dao.insert("bpm.bzj_qmcd.insertFi_Bzj_Record", map);
		//3
		map.put("REFUND_MONEY", refund_money);
		Dao.update("cashDepositK.update_Deposit_sy", map);
		return new ReplyAjax(true, "客户保证金转来款成功！！");
	}
	
}
