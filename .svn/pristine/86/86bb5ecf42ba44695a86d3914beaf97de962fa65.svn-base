package com.pqsoft.renterpool.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.jbpm.api.ProcessInstance;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.fundNotEBank.service.FundNotEBankService;
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
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.ReplyExcel;

public class CashDepositAction extends Action {

	private final String pagePath="renterpool/cashDeposit/";
	CashDepositService service = new CashDepositService();
	
	/***********************************************************************************************************
	 ************************                                                           ************************
	 *                                         承租人保证金池-可用余款池
	 ************************                                                           ************************
	 **********************************************************************************************************/
	
	@Override
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金池管理", "客户保证金池管理", "主页面"})
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> map = _getParameters();
		context.put("param",map);
		return new ReplyHtml(VM.html(pagePath+"toMgCashDepositMain.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金池管理", "客户保证金池管理", "可用余款池","列表"})
	public Reply toMgCashDeposit() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> map = _getParameters();
		context.put("param",map);
		context.put("zj_status", new DataDictionaryMemcached().get("资金池状态"));
		return new ReplyHtml(VM.html(pagePath+"toMgCashDeposit.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aPermission(name = {"资金池管理", "客户保证金池管理", "主页面"})
	public Reply toMgCashDepositNew() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> map = _getParameters();
		context.put("param",map);
		context.put("zj_status", new DataDictionaryMemcached().get("资金池状态"));
		context.put("POUNDAGE_WAYLIST", new DataDictionaryMemcached().get("保证金处理方式"));
		return new ReplyHtml(VM.html(pagePath+"toMgCashDeposit_New.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgCashDepositData(){
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.toMgRefund(map));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aPermission(name = {"资金池管理", "客户保证金池管理", "主页面"})
	public Reply toMgCashDepositData_New(){
		Map<String,Object> map = _getParameters();
		if(map.get("STATUS")==null || map.get("STATUS").equals("")){
			map.put("STATUS","0");
		}
		return new ReplyAjaxPage(service.toMgRefundNew(map));
	}
	
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "资金池管理", "客户保证金池管理", "主页面" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply queryDetailByPoolId() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.queryDetailByPoolId(param));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金池管理", "客户保证金池管理", "可用余款池","退款申请"})
	public Reply getSuppliers(){
		//获取供应商数据
		Map<String,Object> supp = _getParameters();
		
		return new ReplyAjax(JSONObject.fromObject(service.getSuppliers(supp)));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aPermission(name = {"资金池管理", "客户保证金池管理","退款申请"})
	public Reply getCust_Bank_Info(){
		Map<String,Object> map = _getParameters();
		
		int num=service.doPayListCodeIng(map);
		if(num>0){
			return new ReplyAjax(false,"正在核销中或者虚拟核销未退款，不能发起提前结清");
		}
		else{
			boolean flag=service.IsDunByPayListCOde(map);
			if(!flag){
				return new ReplyAjax(false,"逾期，请先消除逾期数据后在进行操作。");
			}
			else{
				return new ReplyAjax(true,JSONObject.fromObject(service.queryBankInfo(map)),null);
			}
		}
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aPermission(name = {"资金池管理", "客户保证金池管理","转来款"})
	public Reply getCust_Change_Info(){
		Map<String,Object> map = _getParameters();
		
		int num=service.doPayListCodeIng(map);
		if(num>0){
			return new ReplyAjax(false,"正在核销中或者虚拟核销未退款，不能发起提前结清");
		}
		else{
			boolean flag=service.IsDunByPayListCOde(map);
			if(!flag){
				return new ReplyAjax(false,"逾期，请先消除逾期数据后在进行操作。");
			}
			else{
				return new ReplyAjax(true,null);
			}
		}
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金池管理", "客户保证金池管理", "可用余款池","退款申请"})
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply refundCDApply(){
		Map<String,Object> param = _getParameters();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//保存退款申请单
		Map<String,Object> refundDan = new HashMap<String, Object>();
		String re_code = Security.getUser().getName()+"-"+param.get("PAY_TIME").toString();
		String re_id = service.getRefundDanSeq();
		refundDan.put("RE_ID", re_id);
		refundDan.put("RE_CODE", re_code);
		refundDan.put("RE_DATE", param.get("PAY_TIME").toString());
		refundDan.put("RE_MONEY", param.get("REFUND_MONEY").toString());
		refundDan.put("RE_PROJECT_COUNT", param.get("PROJECT_COUNT").toString());
		refundDan.put("RE_TYPE", "2");
		refundDan.put("RE_APPLY_NAME", Security.getUser().getName());
		refundDan.put("RE_APPLY_TIME", sdf.format(new Date()));
		refundDan.put("RE_PAYEE_TYPE", param.get("RE_PAYEE_TYPE").toString());
		//refundDan.put("CUST_ID", param.get("CUST_ID").toString());
		refundDan.put("RE_STATUS", "0");
		refundDan.put("RE_PAYEE_UNIT", param.get("RE_PAYEE_UNIT").toString());
		refundDan.put("RE_PAYEE_ACCOUNT", param.get("RE_PAYEE_ACCOUNT").toString());
		refundDan.put("RE_PAYEE_BANK", param.get("RE_PAYEE_BANK").toString());
		refundDan.put("RE_PAYEE_BANK_ADDR", param.get("RE_PAYEE_BANK_ADDR").toString());
		int result = service.addRefundCUST(refundDan);
		//将退款单号回更到承租人保证及池中， 将状态变更为已退款的状态
		param.put("STATUS", "2");
		param.put("RE_ID", re_id);		
		String str= param.get("POOL_ID_ITEMS")+"";
		String []st=str.split(",");
		int cust = 0;
		//List<Map<String,Object>> lst=new ArrayList<Map<String,Object>>();
		for (int i = 0; i < st.length; i++) {
			if(StringUtils.isNotEmpty(st[i])){
//				Map<String,Object> ma=new HashMap<String,Object>();
				param.put("POOL_ID", st[i]);
				cust = service.updateCUSTRefundId(param);;
			}
		}
		
		//param.put("POOL_ID", param.get("POOL_ID_ITEMS").toString());	
		
		
		if(result > 0 && cust > 0){
			return new ReplyAjax(true, "操作成功!").addOp(new OpLog("资金管理","承租人保证金池管理","操作成功"));
		}else{
		    return new ReplyAjax(false,"操作失败!").addOp(new OpLog("资金管理","承租人保证金池管理","操作失败")); 
		}
	}
	
	
	/***********************************************************************************************************
	 ************************                                                           ************************
	 *                                         承租人保证金池-退款明细
	 ************************                                                           ************************
	 **********************************************************************************************************/
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金池管理", "客户保证金池管理", "承租人保证金[退款]-列表"})
	public Reply toMgCDRefund() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> map = _getParameters();
		context.put("param",map);
		context.put("tkzt", new DataDictionaryMemcached().get("退款单状态"));
		return new ReplyHtml(VM.html(pagePath+"toMgCDRefund.vm", context));
	}

	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgCDRefundData(){
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.toMgCDRefund(map));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金池管理", "客户保证金池管理", "承租人保证金[退款]-下拉列表"})
	public Reply getRefundDetailData(){
		Map<String,Object> map = _getParameters();
		return new ReplyAjax(service.getRefundDetailData(map));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name ={"资金池管理", "客户保证金池管理", "承租人保证金[退款]-发起退款流程"})
	public Reply startRefundApp(){
		Map<String,Object> param = _getParameters();
		String msg = "" ;
		Boolean flag = true; 
		int result = 0;
		//发起退款评审流程
		List<String> prcessList = JBPM.getDeploymentListByModelName("承租人保证金池退款审核流程");
	
		if(prcessList.size() > 0){
			Map<String,Object> pool = (Map<String, Object>) service.getPoolData(param);	
			Map<String, Object> jmap = new HashMap<String, Object>();
    		jmap.put("RE_ID", param.get("RE_ID")+"");
    		jmap.put("POOL_ID",pool.get("POOL_ID")+"");
    		jmap.put("PRO_ID", pool.get("PROJECT_ID")+"");
			String jbpm_id = JBPM.startProcessInstanceById(String.valueOf(prcessList.get(0)),pool.get("PROJECT_ID").toString(), pool.get("OWNER_ID").toString(),"",jmap).getId(); 
			msg += jbpm_id+"已发起！";
    		param.put("JBPM_ID", jbpm_id);
    		//变更退款单状态
    		param.put("RE_STATUS", "1");
    		ResidualMoneyService rmService = new ResidualMoneyService();
    		result = rmService.updateRefundMess(param);
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
	public Reply toFindDetail() {
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();		
		context.put("toRefundData",service.toRefundData(param));
		context.put("getRefundDetailData", service.getRefundDetailData(param));
		return new ReplyHtml(VM.html(pagePath+"toFindDetail.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toUpdateDetail(){
		Map<String,Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("toRefundData",service.toRefundData(param));
		context.put("getRefundDetailData", service.getRefundDetailData(param));
		context.put("getRefundData",service.getRefundData(param));
		return new ReplyHtml(VM.html(pagePath+"toUpdateDetail.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply doUpdateDetail(){
		Map<String,Object> param = _getParameters();
		param.put("USERCOE", Security.getUser().getCode());
		param.put("USERNAME", Security.getUser().getName());
		int i = service.doUpdateRefund(param);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "退款单修改成功";
		}else{
			flag = false;
			msg = "退款单修改失败";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("资金管理", "承租人保证金池退款",msg));
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name ={"资金池管理", "客户保证金池管理", "承租人保证金[退款]-撤销"})
	public Reply delRefundDan(){
		Map<String,Object> param = _getParameters();
		param.put("STATUS2", "2");
		String POOL_ID  = param.get("POOL_ID") != null ? param.get("POOL_ID").toString():"0";
		Map<String,Object> reFund = new HashMap<String, Object>();
		reFund.put("POOL_ID", POOL_ID);
		reFund.put("RE_ID", param.get("RE_ID"));
		reFund.put("STATUS", "1");
		ResidualMoneyService rmService = new ResidualMoneyService();
		service.updateCUSTRefundId(reFund);
	    int result = rmService.delRefundMess(param);
	    if(result >0){
		    return new ReplyAjax(true,"操作成功!");
	    }else{
	    	return new ReplyAjax(false,"操作失败!");
	    }
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金池管理", "客户保证金池管理", "承租人保证金[退款]-导出"})
	public Reply excelDetail() {
		Map<String, Object> map =  _getParameters();
		
		return new ReplyExcel(service.excelDetail(map),"承租人保证金池管理"+DateUtil.getSysDate()+Math.abs(new Random(10000).nextInt())+".xls");
	}
	/***********************************************************************************************************
	 ************************                                                           ************************
	 *                                         承租人保证金池-抵扣明细                                                                                                                 *
	 ************************                                                           ************************
	 **********************************************************************************************************/
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金池管理", "客户保证金池管理", "冲抵明细-列表"})
	public Reply toMgCDResetFund() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> map = _getParameters();
		context.put("param",map);
		return new ReplyHtml(VM.html(pagePath+"toMgCDResetFund.vm", context));
	}

	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgCDResetFundData(){
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.toMgCDdetail(map));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金池管理", "客户保证金池管理", "冲抵明细-下拉选"})
	public Reply getCDDetainlByFundId(){
		Map<String,Object> map = _getParameters();
		return new ReplyAjax(service.getCDDetainlByFundId(map));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金池管理", "客户保证金池管理", "冲抵明细-导出"})
	public Reply excelCDDetail() {
		Map<String, Object> map =  _getParameters();
		
		return new ReplyExcel(service.excelCDDetail(map),"承租人保证金池管理"+DateUtil.getSysDate()+Math.abs(new Random(10000).nextInt())+".xls");
	}
	/***********************************************************************************************************
	 ************************                                                           ************************
	 *                                         承租人保证金池-结清抵扣申请                                                                                                       *
	 ************************                                                           ************************
	 **********************************************************************************************************/
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金池管理", "客户保证金池管理", "结清抵扣申请","列表"})
	public Reply toMgJQDKData() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> map = _getParameters();
		context.put("param",map);
		context.put("checkedStatus", new DataDictionaryMemcached().get("核销状态"));
		return new ReplyHtml(VM.html(pagePath+"toMgJQDKData.vm", context));
	}

	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgJQDKDataMana(){
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.toMgJQDKData(map));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金池管理", "客户保证金池管理", "结清抵扣申请","申请"})
	public Reply toMgDeductionBZJ(){
		Map<String,Object> map = _getParameters();
		map.put("USERNAME", Security.getUser().getName());//用戶名稱
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		context.put("paymentType", new DataDictionaryMemcached().get("付款方式"));
		FundNotEBankService Fservice = new FundNotEBankService();
		context.put("toGetCompany", Fservice.toGetCompany(map));//获取厂商		
		context.put("toGetProduct", Fservice.toGetProduct(map));//获取租赁物类型
		return new ReplyHtml(VM.html(pagePath+"toMgDeductionBZJ.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金池管理", "客户保证金池管理", "结清抵扣申请","申请"})
	public Reply toMgDeductionBZJData(){
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.toMgDeductionBZJ(map));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金池管理", "客户保证金池管理", "结清抵扣申请-判断支付表锁住状态"})
	public Reply checkedLockTypeRent(){
		Map<String,Object> map = _getParameters();
		String lockType = service.checkedLockTypeRent(map);
		boolean flag = false;
		String msg = "";
		if(!"".equals(lockType)){
			flag = false;
			msg = lockType;
		}else {
			flag = true;
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("资金管理", "结清抵扣申请-判断支付表锁住状态",msg+"已经被锁住"));
	}
	
		
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金池管理", "客户保证金池管理", "结清抵扣申请","申请"})
	public Reply doInsertFundHead(){
		Map<String,Object> map = _getParameters();
		map.put("USERNAME", Security.getUser().getName());//用戶名稱
		map.put("USERCODE", Security.getUser().getCode());//用戶编号
		int i = service.doInsertFundHead(map);
		boolean flag = false;
		String msg = "";
		if(i>0){
			flag = true;
			msg = "添加保证及抵扣租金成功";
		}else {
			msg = "添加保证及抵扣租金失败";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("资金管理", "承租人保证金池-结清抵扣申请",msg));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金池管理", "客户保证金池管理", "结清抵扣申请","核销"})
	public Reply doSubmitAppJQDK() {
		Map<String,Object> map = _getParameters();//获取页面参数
		
		map.put("USERCODE",Security.getUser().getCode());//用户编号
		map.put("USERNAME",Security.getUser().getName());//用户名
		
		//则核销冲红保证金
		service.doHXBzj(map);
		
		//核销申请的结清明细
		int i = service.doCheckedFund(map);
		
		boolean flag = false;
		String msg = "";
		if(i>0){
		    String overPayCode = service.getDetailDataByFundId(map);
			flag = true;
			msg = overPayCode==null?"承租人保证金池-结清抵扣申请提交成功！":"还有项目("+ overPayCode +")没有核销完成， 请到租金扣划或承租人保证金结清抵扣中继续申请核销";
		}else {
			flag = false;
			msg = "承租人保证金池-结清抵扣申请提交失败！";
		}				
		return new ReplyAjax(flag, msg).addOp(new OpLog("资金管理", "承租人保证金池-结清抵扣申请", msg));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金池管理", "客户保证金池管理", "结清抵扣申请","下拉选"})
	public Reply getJQDKDetail() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		
		context.put("param", map);
		
		FundNotEBankService ebankService = new FundNotEBankService();
		Map<String,Object> jqdkM = (Map<String, Object>) ebankService.toGetFundData(map);//查看申请单信息
		
		//判断是付款方式，以确定是使用哪种资金池
		List<Map<String,Object>> listTage=new ArrayList<Map<String,Object>>();
		//
		List<Map<String,Object>> poolList=new ArrayList<Map<String,Object>>();
		
		String OWNER_ID=null;
		//当付款方式为7 ：保证金结清抵扣-承租人时， 用户可以使用承租人余款池， 承租人保证金池， 待处理资金池， 保险理赔池
		if(jqdkM!=null && jqdkM.get("FI_FLAG")!=null && !jqdkM.get("FI_FLAG").equals("") && jqdkM.get("FI_FLAG").toString().equals("7")){
			listTage = ebankService.queryCustByFundID(map);
			if(listTage.size()>0)
			{
				Map MapTage=(Map)listTage.get(0);
				OWNER_ID=(MapTage.get("FI_TAGE_ID")!=null && !MapTage.get("FI_TAGE_ID").equals(""))?MapTage.get("FI_TAGE_ID").toString():"";
			}
			
			
			//虚拟数据
			Map map1=new HashMap();
			map1.put("OWNER_ID", OWNER_ID);
			map1.put("FUND_ID", map.get("FUND_ID"));
			map1.put("POOLNAME", "保证金池");
			map1.put("POOLID", "4");
			double userMoney1=ebankService.queryPoolMoneyDe(map1);
			map1.put("POOLUSERMONEY", ebankService.queryPoolMoney(map1)+userMoney1);
			map1.put("dichong_money", userMoney1);
			poolList.add(map1);
			
			Map map2=new HashMap();
			map2.put("OWNER_ID", OWNER_ID);
			map2.put("FUND_ID", map.get("FUND_ID"));
			map2.put("POOLNAME", "承租人资金池");
			map2.put("POOLID", "5");
			double userMoney2=ebankService.queryPoolMoneyDe(map2);
			map2.put("POOLUSERMONEY", ebankService.queryPoolMoney(map2)+userMoney2);
			map2.put("dichong_money", userMoney2);
			poolList.add(map2);
			
			Map map3=new HashMap();
			map3.put("OWNER_ID", OWNER_ID);
			map3.put("FUND_ID", map.get("FUND_ID"));
			map3.put("POOLNAME", "保险理赔资金池");
			map3.put("POOLID", "6");
			double userMoney3=ebankService.queryPoolMoneyDe(map3);
			map3.put("POOLUSERMONEY", ebankService.queryPoolMoney(map3)+userMoney3);
			map3.put("dichong_money", userMoney3);
			poolList.add(map3);
			
			Map map4=new HashMap();
			map4.put("OWNER_ID", OWNER_ID);
			map4.put("FUND_ID", map.get("FUND_ID"));
			map4.put("POOLNAME", "待处理资金池");
			map4.put("POOLID", "7");
			double userMoney4=ebankService.queryPoolMoneyDe(map4);
			map4.put("POOLUSERMONEY", ebankService.queryPoolMoney(map4)+userMoney4);
			map4.put("dichong_money", userMoney4);
			poolList.add(map4);
		}
		
		else if(jqdkM!=null && jqdkM.get("FI_FLAG")!=null && !jqdkM.get("FI_FLAG").equals("") && jqdkM.get("FI_FLAG").toString().equals("8"))
		{
			listTage=ebankService.querySuppByFundID(map);
			if(listTage.size()>0)
			{
				Map MapTage=(Map)listTage.get(0);
				OWNER_ID=(MapTage.get("FI_TAGE_ID")!=null && !MapTage.get("FI_TAGE_ID").equals(""))?MapTage.get("FI_TAGE_ID").toString():"";
			}
			
			//只需要DB保证金池，供应商电汇资金池，设备付款资金池，待处理资金池
			//虚拟数据
			Map map1=new HashMap();
			map1.put("OWNER_ID", OWNER_ID);
			map1.put("FUND_ID", map.get("FUND_ID"));
			map1.put("POOLNAME", "DB保证金池");
			map1.put("POOLID", "1");
			double userMoney1=ebankService.queryPoolMoneyDe(map1);
			map1.put("POOLUSERMONEY", ebankService.queryPoolMoney(map1)+userMoney1);
			map1.put("dichong_money", userMoney1);
			poolList.add(map1);
			
			Map map2=new HashMap();
			map2.put("OWNER_ID", OWNER_ID);
			map2.put("FUND_ID", map.get("FUND_ID"));
			map2.put("POOLNAME", "代理垫汇资金池");
			map2.put("POOLID", "2");
			double userMoney2=ebankService.queryPoolMoneyDe(map2);
			map2.put("POOLUSERMONEY", ebankService.queryPoolMoney(map2)+userMoney2);
			map2.put("dichong_money", userMoney2);
			poolList.add(map2);
			
			Map map3=new HashMap();
			map3.put("OWNER_ID", OWNER_ID);
			map3.put("FUND_ID", map.get("FUND_ID"));
			map3.put("POOLNAME", "设备付款资金池");
			map3.put("POOLID", "3");
			double userMoney3=ebankService.queryPoolMoneyDe(map3);
			map3.put("POOLUSERMONEY", ebankService.queryPoolMoney(map3)+userMoney3);
			map3.put("dichong_money", userMoney3);
			poolList.add(map3);
			
			Map map4=new HashMap();
			map4.put("OWNER_ID", OWNER_ID);
			map4.put("FUND_ID", map.get("FUND_ID"));
			map4.put("POOLNAME", "待处理资金池");
			map4.put("POOLID", "7");
			double userMoney4=ebankService.queryPoolMoneyDe(map4);
			map4.put("POOLUSERMONEY", ebankService.queryPoolMoney(map4)+userMoney4);
			map4.put("dichong_money", userMoney4);
			poolList.add(map4);
		}
		
		context.put("jqdkM", jqdkM);
		
		context.put("listTage", listTage);
		
		context.put("poolList", poolList);
		
		return new ReplyHtml(VM.html(pagePath+"getJQDKDetail.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	//@aPermission(name = {"资金管理", "承租人保证金池管理", "结清抵扣申请-下拉选"})
	public Reply getCheckedDetailData(){
		Map<String,Object> param = _getParameters();
		CashDepositService service = new CashDepositService();
		return new ReplyAjaxPage(service.getJQDKDetail(param));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金池管理", "客户保证金池管理", "结清抵扣申请","保存"})
	public Reply doCommitHXSheet(){
		Map<String,Object> paramMap = _getParameters();
		paramMap.put("USERCODE",Security.getUser().getCode());//用户编号
		paramMap.put("USERNAME",Security.getUser().getName());//用户名
		
		int i =0;
		boolean flag = false;
		String msg = "";
		i = service.doUpdateFHead(paramMap);//更新申请单数据
		if(i>0){
			i = service.doSaveAccount(paramMap);//添加实收明细
			//service.dunCommit();
			if(i>0){
				flag = true;
				msg = "实收明细插入成功";
			}else{
				flag = false;
				msg = "实收明细插入失败";
			}
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("资金管理", "承租人保证金池-结清抵扣申请-保存",msg));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金池管理", "客户保证金池管理", "结清抵扣申请","核销"})
	public Reply doCheckedBZJ(){
		//核销时如果调用资金池中的数据时， 直接核销
		
		Map<String,Object> param = _getParameters();
		param.put("USERCODE",Security.getUser().getCode());//用户编号
		param.put("USERNAME",Security.getUser().getName());//用户名
		service.doUpdateFHead(param);//更新申请单数据
		int i = service.doCheckedBZJ(param);
		boolean flag = false;
		String msg = "";
		if(i>0){
			i = service.doHXBzj_1(param);
			if(i>0){
				flag = true;
				msg = "承租人保证金池-结清抵扣申请提交成功！";
			}else {
				flag = false;
				msg = "承租人保证金池-结清抵扣申请提交失败！";
			}
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("资金管理", "承租人保证金池-结清抵扣申请", msg));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金池管理", "客户保证金池管理", "结清抵扣","作废"})
	public Reply doCancellation() {
		Map<String,Object> paramMap = _getParameters();
		paramMap.put("USERCODE",Security.getUser().getCode());//用户编号
		int i = 0;
		boolean flag = false;
		String msg = "";
		i = service.doCancellation(paramMap);//作废
		if (i > 0) {
			flag = true;
			msg = "承租人保证金池-结清抵扣申请作废成功！";
		} else {
			flag = false;
			msg = "承租人保证金池-结清抵扣申请作废成功！";
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("资金管理", "承租人保证金池-结清抵扣申请", msg));
	}
	
	
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"资金池管理", "客户保证金池管理","结清抵扣","列表"})
	public Reply toMgDeductionBZJShow(){
		Map<String,Object> map = _getParameters();
		map.put("USERNAME", Security.getUser().getName());//用戶名稱
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		context.put("paymentType", new DataDictionaryMemcached().get("付款方式"));
		FundNotEBankService Fservice = new FundNotEBankService();
		context.put("toGetCompany", Fservice.toGetCompany(map));//获取厂商		
		context.put("toGetProduct", Fservice.toGetProduct(map));//获取租赁物类型
		return new ReplyHtml(VM.html(pagePath+"toMgDeductionBZJShow.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	public Reply toMgDeductionBZJShowData(){
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.toMgDeductionBZJ(map));
	}
	
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name = {"资金池管理", "客户保证金池管理", "发起流程", "转平均冲抵流程"})
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply changePJCDJBPM() {
		Map<String,Object> map = _getParameters();
		
		int num=service.doPayListCodeIng(map);
		if(num>0){
			return new ReplyAjax(false,"正在核销中或者虚拟核销未退款，不能发起提前结清");
		}
		else{
			boolean flag=service.IsDunByPayListCOde(map);
			if(!flag){
				return new ReplyAjax(false,"逾期中，不能发起提前结清");
			}
			else{
				List<String> prcessList = JBPM.getDeploymentListByModelName("转平均冲抵审批流程","","");
				if (prcessList != null && prcessList.size() > 0) {
					//冻结支付表
					service.updatePayInfo(map);
					ProcessInstance processInstance = JBPM.startProcessInstanceById(
							prcessList.get(0) + "", map.get("PROJECT_ID") + "",
							map.get("CLIENT_ID") + "", map.get("PAYLIST_CODE") + "",
							map);
					String nextCode = new TaskService().getAssignee(processInstance.getId());
					return new ReplyAjax(true,nextCode,"下一步操作人为: ");
				} else {
					throw new ActionException("流程图不存在");
				}
				
			}
		}
		
	}
	
	//平均冲抵附加页面
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply changePJCDPage(){
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		//基础信息
		Map baseInfo=service.queryBaseInfoByPoolID(map);
		context.put("baseInfo", baseInfo);
		
		context.put("POUNDAGE_WAYLIST", new DataDictionaryMemcached().get("保证金处理方式"));
		//未核销租金抵扣数据
		List dataList=service.queryPJDKList(baseInfo);
		context.put("dataList", dataList);
		System.out.println("-----------------------dataList="+dataList);
		return new ReplyHtml(VM.html(pagePath+"changePJCDPage.vm", context));
	}
	
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name = {"资金池管理", "客户保证金池管理", "发起流程", "转期末冲抵流程"})
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply changeQMCDJBPM(){
		Map<String,Object> map = _getParameters();
		
		int num=service.doPayListCodeIng(map);
		if(num>0){
			return new ReplyAjax(false,"正在核销中或者虚拟核销未退款，不能发起转期末冲抵流程");
		}
		else{
			boolean flag=service.IsDunByPayListCOde(map);
			if(!flag){
				return new ReplyAjax(false,"逾期，请先消除逾期数据后在进行操作。");
			}
			else{
				List<String> prcessList = JBPM.getDeploymentListByModelName("转期末冲抵审批流程","","");
				if (prcessList != null && prcessList.size() > 0) {
					//冻结支付表
					service.updatePayInfo(map);
					ProcessInstance processInstance = JBPM.startProcessInstanceById(
							prcessList.get(0) + "", map.get("PROJECT_ID") + "",
							map.get("CLIENT_ID") + "", map.get("PAYLIST_CODE") + "",
							map);
					String nextCode = new TaskService().getAssignee(processInstance.getId());
					return new ReplyAjax(true,nextCode,"下一步操作人为: ");
				} else {
					throw new ActionException("流程图不存在");
				}
				
			}
		}
	}
	
	//期末冲抵附加页面
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply changeQMCDPage(){
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		//基础信息
		Map baseInfo=service.queryBaseInfoByPoolID(map);
		context.put("baseInfo", baseInfo);
		
		context.put("POUNDAGE_WAYLIST", new DataDictionaryMemcached().get("保证金处理方式"));
		
		//未核销租金抵扣数据
		List dataList=service.queryQMDKList(baseInfo);
		context.put("dataList", dataList);
		
		return new ReplyHtml(VM.html(pagePath+"changeQMCDPage.vm", context));
	}
	
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金池管理", "客户保证金池管理","退款申请"})
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply refundBackSave(){
		Map<String,Object> param = _getParameters();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//保存退款申请单
		Map<String,Object> refundDan = new HashMap<String, Object>();
		String re_code = Security.getUser().getName()+"-"+sdf.format(new Date());
		String re_id = service.getRefundDanSeq();
		refundDan.put("RE_MONEY", param.get("RE_MONEY"));
		refundDan.put("RE_DATE", param.get("RE_DATE"));
		refundDan.put("RE_ID", re_id);
		refundDan.put("RE_CODE", re_code);
		refundDan.put("RE_TYPE", "2");
		refundDan.put("RE_APPLY_NAME", Security.getUser().getName());
		refundDan.put("RE_APPLY_TIME", sdf.format(new Date()));
		refundDan.put("RE_PAYEE_TYPE", param.get("RE_PAYEE_TYPE"));
		String RE_PAYEE_TYPE=param.get("RE_PAYEE_TYPE").toString();
		if(RE_PAYEE_TYPE.equals("2")){//承租人
			refundDan.put("CUST_ID",param.get("CUST_ID"));
			refundDan.put("RE_PAYEE_NAME",param.get("CUST_NAME_D"));
		}
		else{
			refundDan.put("SUP_ID",param.get("SUP_ID"));
			refundDan.put("RE_PAYEE_NAME",param.get("SUPP_NAME_D"));
		}
		refundDan.put("RE_STATUS", "1");
		refundDan.put("RE_PAYEE_UNIT", param.get("RE_PAYEE_UNIT").toString());
		refundDan.put("RE_PAYEE_ACCOUNT", param.get("RE_PAYEE_ACCOUNT").toString());
		refundDan.put("RE_PAYEE_BANK", param.get("RE_PAYEE_BANK").toString());
		refundDan.put("RE_PAYEE_BANK_ADDR", param.get("RE_PAYEE_BANK_ADDR").toString());
		refundDan.put("RE_PROJECT_COUNT", 1);
		int result = service.addRefundCUST(refundDan);
		//将退款单号回更到承租人保证及池中， 将状态变更为已退款的状态
		
		List<String> prcessList = JBPM.getDeploymentListByModelName("客户保证金退款审批流程","","");
		if (prcessList != null && prcessList.size() > 0) {
			//冻结支付表
			param.put("STATUS", "2");
			param.put("RE_ID", re_id);
			
			int cust = service.updateCUSTRefundIdNew(param);
			
			ProcessInstance processInstance = JBPM.startProcessInstanceById(
					prcessList.get(0) + "", param.get("PROJECT_ID") + "",
					param.get("CUST_ID") + "", param.get("PAYLIST_CODE") + "",
					param);
			String nextCode = new TaskService().getAssignee(processInstance.getId());
			return new ReplyAjax(true,nextCode,"下一步操作人为: "+nextCode);
		} else {
			throw new ActionException("流程图不存在");
		}
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金池管理", "客户保证金池管理","转来款"})
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply changeMoneyCome(){
		Map<String,Object> map = _getParameters();
		int num = service.changeMoneyCome(map);
		if(num>0){
			return new ReplyAjax(true,"转来款成功！");
		}else{
			return new ReplyAjax(true,"转来款失败！");
		}
	}
	
	//客户保证金退款附加页面
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply changeTKPage(){
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		//基础信息
		Map baseInfo=service.queryBaseInfoByPoolIDBackMoney(map);
		context.put("baseInfo", baseInfo);
		
		return new ReplyHtml(VM.html(pagePath+"changeTKPage.vm", context));
	}
	
/**
 * -------------------------------------DB保证金池管理----------------------
 * -------------------------------------DB保证金池管理----------------------
 * */
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aPermission(name = {"资金池管理","供应商保证金池管理","主页面"})
	public Reply toMgCashDBDepositNew() {
		VelocityContext context = new VelocityContext();
		Map<String,Object> map = _getParameters();
		context.put("param",map);
		context.put("zj_status", new DataDictionaryMemcached().get("资金池状态"));
		return new ReplyHtml(VM.html(pagePath+"toMgCashDBDeposit.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aPermission(name = {"资金池管理", "供应商保证金池管理", "主页面"})
	public Reply toMgCashDBDepositData(){
		Map<String,Object> map = _getParameters();
		
		if(map.get("STATUS")==null || map.get("STATUS").equals("")){
			map.put("STATUS","0");
		}
		
		return new ReplyAjaxPage(service.toMgRefundNew_DB(map));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aPermission(name = {"资金池管理", "供应商保证金池管理","退款申请"})
	public Reply getSupp_Bank_Info(){
		Map<String,Object> map = _getParameters();
		
		int num=service.doPayListCodeIng(map);
		if(num>0){
			return new ReplyAjax(false,"正在核销中或者虚拟核销未退款，不能发起提前结清");
		}
		else{
			boolean flag=service.IsDunByPayListCOde(map);
			if(!flag){
				return new ReplyAjax(false,"逾期，请先消除逾期数据后在进行操作。");
			}
			else{
				return new ReplyAjax(true,JSONObject.fromObject(service.queryBankInfo(map)),null);
			}
		}
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金池管理", "供应商保证金池管理","退款申请"})
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply refundDBBackSave(){
		Map<String,Object> param = _getParameters();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//保存退款申请单
		Map<String,Object> refundDan = new HashMap<String, Object>();
		String re_code = Security.getUser().getName()+"-"+sdf.format(new Date());
		String re_id = service.getRefundDanSeq();
		refundDan.put("RE_MONEY", param.get("RE_MONEY"));
		refundDan.put("RE_DATE", param.get("RE_DATE"));
		refundDan.put("RE_ID", re_id);
		refundDan.put("RE_CODE", re_code);
		refundDan.put("RE_TYPE", "1");
		refundDan.put("RE_APPLY_NAME", Security.getUser().getName());
		refundDan.put("RE_APPLY_TIME", sdf.format(new Date()));
		refundDan.put("RE_PAYEE_TYPE", "1");
		refundDan.put("SUP_ID",param.get("SUP_ID"));
		refundDan.put("CUST_ID",param.get("SUP_ID"));
		refundDan.put("RE_PAYEE_NAME",param.get("SUPP_NAME_D"));
		refundDan.put("RE_STATUS", "1");
		refundDan.put("RE_PAYEE_UNIT", param.get("RE_PAYEE_UNIT").toString());
		refundDan.put("RE_PAYEE_ACCOUNT", param.get("RE_PAYEE_ACCOUNT").toString());
		refundDan.put("RE_PAYEE_BANK", param.get("RE_PAYEE_BANK").toString());
		refundDan.put("RE_PAYEE_BANK_ADDR", param.get("RE_PAYEE_BANK_ADDR").toString());
		refundDan.put("RE_PROJECT_COUNT", 1);
		int result = service.addRefundCUST(refundDan);
		//将退款单号回更到承租人保证及池中， 将状态变更为已退款的状态
		
		List<String> prcessList = JBPM.getDeploymentListByModelName("供应商保证金退款审批流程","","");
		if (prcessList != null && prcessList.size() > 0) {
			//冻结支付表
			param.put("STATUS", "2");
			param.put("RE_ID", re_id);
			
			int cust = service.updateCUSTRefundIdNew(param);
			
			ProcessInstance processInstance = JBPM.startProcessInstanceById(
					prcessList.get(0) + "", param.get("PROJECT_ID") + "",
					param.get("CUST_ID") + "", param.get("PAYLIST_CODE") + "",
					param);
			String nextCode = new TaskService().getAssignee(processInstance.getId());
			return new ReplyAjax(true,nextCode,"下一步操作人为: "+nextCode);
		} else {
			throw new ActionException("流程图不存在");
		}
	}
	
	//供应商保证金退款附加页面
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply changeDBTKPage(){
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		//基础信息
		Map baseInfo=service.queryDBByPoolIDBackMoney(map);
		context.put("baseInfo", baseInfo);
		
		return new ReplyHtml(VM.html(pagePath+"changeDBTKPage.vm", context));
	}
	
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aPermission(name = {"资金池管理", "供应商保证金池管理","转来款"})
	public Reply getSupp_Change_Info(){
		Map<String,Object> map = _getParameters();
		
		int num=service.doPayListCodeIng(map);
		if(num>0){
			return new ReplyAjax(false,"正在核销中或者虚拟核销未退款，不能发起提前结清");
		}
		else{
			boolean flag=service.IsDunByPayListCOde(map);
			if(!flag){
				return new ReplyAjax(false,"逾期，请先消除逾期数据后在进行操作。");
			}
			else{
				return new ReplyAjax(true,null);
			}
		}
	}
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name ={"资金池管理", "供应商保证金池管理","转来款"})
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	public Reply changeDBMoneyCome(){
		Map<String,Object> map = _getParameters();
		int num = service.changeDBMoneyCome(map);
		if(num>0){
			return new ReplyAjax(true,"转来款成功！");
		}else{
			return new ReplyAjax(true,"转来款失败！");
		}
	}
}
