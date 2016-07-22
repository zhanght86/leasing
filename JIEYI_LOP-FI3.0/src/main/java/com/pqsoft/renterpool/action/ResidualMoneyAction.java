package com.pqsoft.renterpool.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.renterpool.service.CashDepositService;
import com.pqsoft.renterpool.service.ResidualMoneyService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class ResidualMoneyAction extends Action{

	private final String pagePath="renterpool/residualMoney/";
	ResidualMoneyService service = new ResidualMoneyService();
	
	
	/***********************************************************************************************************
	 ************************                                                           ************************
	 *                                         承租人可用余款池
	 ************************                                                           ************************
	 **********************************************************************************************************/
	
	@Override
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金管理", "承租人余款池管理", "主页面"})
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> map = _getParameters();
		context.put("param",map);
		return new ReplyHtml(VM.html(pagePath+"toMgResidualMoneyMain.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金管理", "承租人余款池管理", "可用余款池-列表"})
	public Reply toMgResidualMoney() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> map = _getParameters();
		context.put("param",map);
		return new ReplyHtml(VM.html(pagePath+"toMgResidualMoney.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgResidualMoneyData(){
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.toMgResidualMoney(map));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金管理", "承租人余款池管理", "可用余款池-下拉选"})
	public Reply getDetailList(){
		Map<String,Object> map = _getParameters();
		return new ReplyAjax(service.getDetailList(map));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金管理", "承租人余款池管理", "可用余款池-冻结"})
	public Reply doUnfreezeMethod(){
		Map<String,Object> map = _getParameters();
		map.put("STATUS", 0);
		int i = service.doChangeFreezeStatus(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "余款金额冻结成功！";
		}else {
			msg = "余款金额冻结失败！";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("资金管理","承租人余款池管理",msg));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金管理", "承租人余款池管理", "可用余款池-解冻"})
	public Reply doFreezeMethod(){
		Map<String,Object> map = _getParameters();
		map.put("STATUS", 1);
		int i = service.doChangeFreezeStatus(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "余款金额解冻成功！";
		}else {
			msg = "余款金额解冻失败！";
		}
		return new ReplyAjax(flag,msg).addOp(new OpLog("资金管理","承租人余款池管理",msg));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	//@aPermission(name ={"资金管理","承租人余款池管理","可用余款池-退款单"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply getOpenBank(){
		Map<String,Object> param = _getParameters();
		return new ReplyAjax(JSONArray.fromObject(service.getOpenBank(param)));
	}
 	
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name ={"资金管理","承租人余款池管理","可用余款池-退款单"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply refundApply(){
		Map<String,Object> param = _getParameters();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//保存退款申请单
		Map<String,Object> refundDan = new HashMap<String, Object>();
		String re_code = param.get("CUST_NAME").toString()+"-"+param.get("PAY_TIME").toString();
		String re_id = service.getRefundDanSeq();
		refundDan.put("RE_ID", re_id);
		refundDan.put("RE_CODE", re_code);
		refundDan.put("RE_DATE", param.get("PAY_TIME").toString());
		refundDan.put("RE_MONEY", param.get("REFUND_MONEY").toString());
		refundDan.put("RE_PROJECT_COUNT", param.get("PROJECT_COUNT").toString());
		
		refundDan.put("RE_PAYEE_UNIT", param.get("RE_PAYEE_UNIT").toString());
		refundDan.put("RE_PAYEE_ACCOUNT", param.get("RE_PAYEE_ACCOUNT").toString());
		refundDan.put("RE_PAYEE_BANK", param.get("RE_PAYEE_BANK").toString());
		refundDan.put("RE_PAYEE_BANK_ADDR", param.get("RE_PAYEE_BANK_ADDR").toString());
		
		refundDan.put("RE_TYPE", "5");
		refundDan.put("RE_APPLY_NAME", Security.getUser().getName());
		refundDan.put("RE_APPLY_TIME", sdf.format(new Date()));
		refundDan.put("RE_PAYEE_TYPE", "2");
		refundDan.put("CUST_ID", param.get("CUST_ID").toString());
		refundDan.put("RE_STATUS", "0");
		int result = service.addRefundCUST(refundDan);
		//将退款单号回更到承租人余款池中， 将状态变更为已退款的状态
		param.put("STATUS", "2");
		param.put("RE_ID", re_id);
		param.put("POOL_ID", param.get("POOL_ID_ITEMS").toString());
		int cust = service.updateCUSTRefundId(param);
		
		if(result > 0 && cust > 0){
			return new ReplyAjax(true, "操作成功!").addOp(new OpLog("资金管理","承租人余款池管理","操作成功"));
		}else{
		    return new ReplyAjax(false,"操作失败!").addOp(new OpLog("资金管理","承租人余款池管理","操作失败")); 
		}
	}
	
	/***********************************************************************************************************
	 ************************                                                           ************************
	 *                                         承租人退款明细
	 ************************                                                           ************************
	 **********************************************************************************************************/
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金管理", "承租人余款池管理", "退款明细-列表"})
	public Reply toMgRefund() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> map = _getParameters();
		context.put("param",map);
		return new ReplyHtml(VM.html(pagePath+"toMgRefund.vm", context));
	}

	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgRefundData(){
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.toMgRefund(map));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name ={"资金管理","承租人余款池管理","退款明细-发起退款流程"})
	public Reply startRefundApp(){
		Map<String,Object> param = _getParameters();
		String msg = "" ;
		Boolean flag = true; 
		int result = 0;
		//发起退款评审流程
		List<String> prcessList = JBPM.getDeploymentListByModelName("承租人余款池退款审核流程");
		if(prcessList.size() > 0){
			CashDepositService CDservice = new CashDepositService();
			Map<String,Object> pool = (Map<String, Object>) CDservice.getPoolData(param);	
			Map<String, Object> jmap = new HashMap<String, Object>();
			jmap.put("RE_ID", param.get("RE_ID")+"");
    		jmap.put("POOL_ID",pool.get("POOL_ID")+"");
    		jmap.put("PRO_ID", pool.get("PROJECT_ID")+"");
    		
			String jbpm_id = JBPM.startProcessInstanceById(String.valueOf(prcessList.get(0)),pool.get("PROJECT_ID").toString(), pool.get("OWNER_ID").toString(),"",jmap).getId(); 
			msg += jbpm_id+"已发起！";
    		param.put("JBPM_ID", jbpm_id);
    		//变更退款单状态
    		param.put("RE_STATUS", "1");
    		result = service.updateRefundMess(param);
		}else{
			flag = false;
			msg = "未找到流程";
			throw new ActionException("未找到流程");
		}
		if(result >0){
			flag = true;
			msg +="操作成功!";
		}else{
			flag = false;
			msg +="操作失败!";
		}
		return new ReplyAjax(flag,msg);
	}	
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toFindRentDetail() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		CashDepositService CDserivce = new CashDepositService();		
		context.put("toRefundData",CDserivce.toRefundData(param));
		//context.put("getRefundDetailData", CDserivce.getRefundDetailData(param));
		return new ReplyHtml(VM.html(pagePath+"toFindRentDetail.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toUpdateRentDetail(){//财务核销
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		CashDepositService CDserivce = new CashDepositService();
		context.put("param", param);
		context.put("toRefundData",CDserivce.toRefundData(param));
		//context.put("getRefundDetailData", CDserivce.getRefundDetailData(param));
		context.put("getRefundData",CDserivce.getRefundData(param));
		return new ReplyHtml(VM.html(pagePath+"toUpdateRentDetail.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply doUpdateRentDetail(){
		Map<String,Object> param = _getParameters();
		param.put("USERCOE", Security.getUser().getCode());
		param.put("USERNAME", Security.getUser().getName());
		CashDepositService CDserivce = new CashDepositService();
		int i = CDserivce.doUpdateRefund(param);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "退款单修改成功";
		}else{
			flag = false;
			msg = "退款单修改失败";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("资金管理", "承租人余款池退款",msg));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toFindRentDetail1() {//财务总监查看
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		CashDepositService CDserivce = new CashDepositService();		
		context.put("toRefundData",CDserivce.toRefundData(param));
		//context.put("getRefundDetailData", CDserivce.getRefundDetailData(param));
		return new ReplyHtml(VM.html(pagePath+"toFindRentDetail1.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name ={"资金管理","承租人余款池管理","退款明细-撤销"})
	public Reply delRefundDan(){
		Map<String,Object> param = _getParameters();
	
		param.put("STATUS2", "2");
		List<Object> custBails = service.getCUSTBailList(param);
		String POOL_ID = "";
		for (Object custBail : custBails) {
			Map<String,Object> bail = (Map<String,Object>)custBail;
			POOL_ID +=  bail.get("POOL_ID").toString() + ",";
		}
		POOL_ID = POOL_ID.substring(0, POOL_ID.indexOf(","));
		Map<String,Object> reFund = new HashMap<String, Object>();
		reFund.put("POOL_ID", POOL_ID);
		reFund.put("RE_ID", param.get("RE_ID"));
		reFund.put("STATUS", "0");
		service.updateCUSTRefundId(reFund);
	    int result = service.delRefundMess(param);
	    if(result >0){
		    return new ReplyAjax(true,"操作成功!");
	    }else{
	    	return new ReplyAjax(false,"操作失败!");
	    }
	}
	/***********************************************************************************************************
	 ************************                                                           ************************
	 *                                         承租人冲抵明细
	 ************************                                                           ************************
	 **********************************************************************************************************/
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金管理", "承租人余款池管理", "冲抵明细-列表"})
	public Reply toMgResetFund() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> map = _getParameters();
		context.put("param",map);
		return new ReplyHtml(VM.html(pagePath+"toMgResetFund.vm", context));
	}

	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgResetFundData(){
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.toMgCDdetail(map));
	}
}
