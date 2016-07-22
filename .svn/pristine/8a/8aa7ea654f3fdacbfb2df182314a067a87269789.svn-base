package com.pqsoft.cashDeposit.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.SystemMail;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.cashDeposit.service.CashDepositService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class CashDepositAction extends Action {

	CashDepositService service = new CashDepositService();
	private String pagePath = "cashDeposit/";
	
	@Override
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金管理", "客户保证金管理", "列表"})
	public Reply execute() {
		// TODO Auto-generated method stub
		return new ReplyHtml(VM.html(pagePath+"toMgCashDeposit.vm", null));
	}

	@aAuth(type = aAuthType.LOGIN)	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgCashDeposit() {
		Map<String, Object> param = _getParameters();//获取页面参数
		return new ReplyAjaxPage(service.toMgCashDeposit(param));
	}
	
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金管理", "客户保证金管理", "退款"})
	public Reply toAddReturnApp(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> map = _getParameters();//获取页面参数
	    context.put("param", map);
	    context.put("client", service.toFindProjectScheme(map));//获取客户信息
	    context.put("bzj",service.toFindScheme(map));//获取保证金数据
	    return new ReplyHtml(VM.html(pagePath+"toAddReturnApp.vm", context));
	}
	
	@SuppressWarnings("deprecation")
	@aAuth(type = aAuthType.LOGIN)	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply doSaveReturn(){//保存退款申请并发起流程
		Map<String,Object> param = _getParameters();
		//退款后剩余保证金金额
		double sy_bzj = Double.parseDouble(param.get("bzjze").toString())-Double.parseDouble(param.get("PAYMENT_MONEY").toString());
		param.put("DEPOSIT_SY", sy_bzj);
		param.put("DEPOSIT_STATUS", 1);//支付表保证金退款申请状态， 1 已申请
		service.doUpRentStatus(param);//修改支付表状态和剩余保证金金额
		String rf_id = Dao.getSequence("SEQ_FI_RETURN_FUND");//获取退款申请id
		param.put("USERCODE", Security.getUser().getCode());//获取用户编号
		param.put("rf_id", rf_id);
		service.doSaveReturn(param);//保存付款申请
		String msg = "";
		boolean flag = false;
		//发起付款保证金付款申请流程
		List<String> list = JBPM.getDeploymentListByModelName("客户保证金退款审批流程");
		if(list.size()>0){
			//Map<String,Object> jbpm = new HashMap<String,Object>();
			String jbpmId =  JBPM.startProcessInstanceById(list.get(0), param.get("PROJECT_ID")+"", "", param.get("PAYLIST_CODE")+"", param).getId();
			param.put("jbpmid", jbpmId);
			service.doUpReturnApp(param);//修改付款申请中流程编号
			flag = true;
			msg="客户保证金退款成功!流程编号为："+jbpmId;
		}else {
			msg="客户保证金退款失败!";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("资金管理-客户保证金管理","保证金退款", msg));
	}
	
	@aAuth(type = aAuthType.LOGIN)	
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toViewReturnApp(){
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		if(map.get("PAY_CODE")!=null){
			map.put("PAYLIST_CODE", map.get("PAY_CODE"));			
		}
		context.put("returnApp", service.toViewReturnApp(map));
		context.put("bzj",service.toFindScheme(map));//获取保证金数据
		return new ReplyHtml(VM.html(pagePath+"toViewReturnApp.vm", context));
	}
	/*
	 * 跳转客户保证金期末冲抵页面
	 */
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末冲抵", "列表"})
	public Reply to_Bzj_Qmcd(){
		return new ReplyHtml(VM.html(pagePath+"to_Bzj_Qmcd.vm", null));
	}
	/*
	 * 查询客户保证进期末冲抵数据
	 */
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末冲抵", "列表"})
	public Reply search_BzjCD_All(){
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.search_BzjCD_All(map));
	}
	
	/*
	 * 批量申请做期末冲抵
	 */
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末冲抵", "申请"})
	public Reply startApplay(){
		Map<String,Object> map = _getParameters();
		String[] attr = map.get("param").toString().split(",");
		int REMARK_ID = Dao.selectInt("cashDepositK.searchApplayId", null);
		for(int i=0;i<attr.length;i++){
			String PAYLIST_CODE = attr[i];
			Map<String,Object> map_tem = Dao.selectOne("cashDepositK.getIdByPaylist_Code",PAYLIST_CODE);
			map_tem.put("REMARK_ID", REMARK_ID);
			System.out.println(map_tem+"11111111");
			if((0+"").equals(map_tem.get("STATUS").toString())){
				map_tem.put("STATUS", "0");
			}
			Dao.insert("cashDepositK.insertApplay", map_tem);
		}
		//修改fi_rent_plan_head表中status状态
		Dao.update("cashDepositK.update_FRPH_Status", map);

		Map JBPMMAP=new HashMap();
		JBPMMAP.put("ID", REMARK_ID+"");
		List<String> list = JBPM.getDeploymentListByModelName("客户保证金期末冲抵申请","", Security.getUser().getOrg().getPlatformId());
		String jbpm_id =JBPM.startProcessInstanceById(list.get(0), "", "", "", JBPMMAP).getId();
		String nextCode = new TaskService().getAssignee(jbpm_id);
		return new ReplyAjax(true, nextCode, "下一步操作人为: ");
	}
	/*
	 * 跳转到批量申请页面
	 */
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末冲抵", "审核页面"})
	public Reply applayPage(){
		Map<String,Object> map = _getParameters();
		List<Map<String,Object>> list = Dao.selectList("cashDepositK.selectRentPlanHeadId", map);
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<list.size();i++){
			Map<String,Object> map_tem = list.get(i);
			if(i==list.size()-1){
				sb.append(map_tem.get("RENT_PLAN_HEAD_ID"));
			}else{
				sb.append(map_tem.get("RENT_PLAN_HEAD_ID")).append(",");
			}
		}
		VelocityContext context = new VelocityContext();
		context.put("ids", sb.toString());
		return new ReplyHtml(VM.html(pagePath+"to_showBzjApplayPage.vm", context));
	}
	/*
	 * 跳转到批量申请页面
	 */
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末冲抵", "审核页面"})
	public Reply getApplayPageData(){
		Map<String,Object> map = _getParameters();
		String attr =  (String) map.get("ids");
		StringBuffer sb = new StringBuffer();
		map.put("IDS2", attr);
		return new ReplyJson(JSONArray.fromObject(service.getApplayPageData(map)));
	}
	/*
	 * 显示保证金余额列表
	 */
	
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末冲抵", "剩余保证金明细"})
	public Reply showBzjYuE(){
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("PAYLIST_CODE", map.get("PAYLIST_CODE").toString());
		return new ReplyHtml(VM.html(pagePath+"to_showBzjYuE.vm", context));
	}
	/*
	 * 获取保证金余额数据
	 */
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末冲抵", "剩余保证金明细"})
	public Reply getBzjSYRecord(){
		Map<String,Object> map = _getParameters();
		System.out.println(map.get("PAYLIST_CODE")+"----");
		List<Map<String,Object>> list = service.getBzjSYRecord(map);
		return new ReplyJson(JSONArray.fromObject(list));
	}
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末冲抵", "剩余保证金明细"})
	public Reply getBzjDetailData(){
		Map<String,Object> map = _getParameters();
		return new ReplyAjax(service.getBzjDetailData(map));
	}
	
	@aAuth(type = aAuthType.USER)	
	@aDev(code = "170067", email = "xiaoyf@pqsoft.cn", name = "肖云飞")
	@aPermission(name = {"资金管理", "客户保证金期末冲抵", "查询"})
	public Reply getDataByPaylist_Code(){
		Map<String,Object> map = _getParameters();
		Map<String,Object> map_tem = new HashMap<String, Object>();
		map_tem.put("array", map.get("array"));
		List<Map<String,Object>> list = service.getDataByPaylist_Code(map_tem);
		return new ReplyAjax(true, JSONArray.fromObject(list));
	}
	
}

