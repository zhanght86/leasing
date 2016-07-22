package com.pqsoft.refundLoan.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.refundLoan.service.RefundLoanService;
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

public class RefundLoanAction extends Action {

	RefundLoanService loanService = new RefundLoanService();
	protected static final Logger logger = Logger.getLogger(RefundLoanAction.class);
	
	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资借款管理","列表显示"})
	public Reply toMgLoan() {
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html("refundLoan/loanManager.vm", context));
	}

	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资借款管理","列表显示"})
	public Reply toMgLoanData() {
		Map<String,Object> map = _getParameters();
		JSONObject json = JSONObject.fromObject(map.get("param"));
		map.remove("param");
		map.putAll(json);
		return new ReplyAjaxPage(loanService.toMgLoan(map));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资借款管理","银行通过"})
	public Reply importLoanInfo(){
		int j=0;
		Map<String,Object> m = _getParameters();
		m.put("PROJECT_STATUS", "2");
		boolean flag = false;
		try{			
			j = loanService.updateProjectStatus(m);//更改项目状态
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(j>0){
				flag = true;
			}
			else{
				flag = false;
			}
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("融资管理","融资借款管理","银行通过"));
	}	
	
	/***********************************************************************************************************************************
	 **********                                           以下为融资借款流程内操作                           　　　　　　　　　　　　　　　                   **********
	 ****************                                                                                                   ****************
	 ***********************************************************************************************************************************/
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资借款管理","发起付款流程"})
	//发起项目评审流程
	public Reply appForPayment(){
		Map<String,Object> map = _getParameters();
		String msg = "";
		boolean flag = true;
		//发起银行审批流程
		List<String> prcessList = JBPM.getDeploymentListByModelName("融资放款审批");
		String code=null;
		if(prcessList.size() > 0){
			Map<String, Object> jmap = new HashMap<String, Object>();
			String pro_id = map.get("PROJECT_ID")!=null&&!"".equals(map.get("PROJECT_ID"))?map.get("PROJECT_ID").toString():"";
			jmap.put("PROJECT_ID",pro_id);
			String jbpm_id = JBPM.startProcessInstanceById(String.valueOf(prcessList.get(0)),"", "","",jmap).getId(); 
			msg += jbpm_id+"已发起！";
			code=new TaskService().getAssignee(jbpm_id);
			//变更项目状态
			map.put("PROJECT_STATUS", "4");
			loanService.updateProjectStatus(map);
		}else{
			flag = false;
			msg = "未找到流程";
			throw new ActionException("未找到流程");
		}
		return new ReplyAjax(flag,"下一节点操作人为:"+code,"流程发起成功！").addOp(new OpLog("融资管理", "融资借款管理",msg));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = {"融资管理", "融资借款管理","发起付款流程"})
	////添加放款信息页面：费用合计，预计支付费用时间，扣款账户
	public Reply getLoanEntryPage(){
		//获取去页面参数
		Map<String,Object> m = _getParameters();
		VelocityContext context = new VelocityContext();
		Map<String,Object> paytype = loanService.getPaytypeFromFhfa(m);
		String  REPAY_TYPE= paytype.get("REPAY_TYPE").toString();
		paytype.put("REPAY_TYPE",REPAY_TYPE.substring(0,REPAY_TYPE.length()-1 ));
		logger.info(paytype.toString());
		paytype.put("type", "再融资放款类型");
		logger.info(paytype.toString());
		if(paytype.get("REPAY_TYPE")!=null){
			context.put("repayMap", loanService.getPaytypeName(paytype));
		}
		context.put("project", loanService.getProject(m));
		context.put("bankAccount", loanService.getBankAccount(m));
		context.put("data", m);
		return new ReplyHtml(VM.html("refundLoan/loanAdd.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资借款管理","发起付款流程"})
	//添加放款信息操作
	public Reply doLoanPayment(){
		int i=0;
		Map<String,Object> map = _getParameters();
		boolean flag = false;
		//添加放款信息：费用合计，预计支付费用时间，扣款账户
		i = loanService.addLoan(map);		
		if(i>0){
			flag = true;
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("融资管理","融资借款管理","添加付款借款明细"));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资借款管理","发起付款流程"})
	//查看预计放款信息
	public Reply toShowLoan(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> m = _getParameters();
		Map<String,Object> paytype = (Map)loanService.getLoanByID(m);//获取项目信息
		context.put("loanmap", paytype);
		context.put("project", loanService.getProject(m));//项目信息
		return new ReplyHtml(VM.html("refundLoan/toShowLoan.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资借款管理","发起付款流程"})
	//更新放款实际时间页面
	public Reply getUpdateLoanPage(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> m = _getParameters();
		Map<String,Object> paytype = (Map)loanService.getLoanByID(m);//获取项目信息
		context.put("loanmap", paytype);
		context.put("project", loanService.getProject(m));//项目信息
		return new ReplyHtml(VM.html("refundLoan/loanUpdate.vm", context));
	}
	
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资借款管理","发起付款流程"})
	////更新放款实际时间操作
	public Reply updateLoan(){
		Map<String,Object> m = _getParameters();
		int i = loanService.updateLoan(m);//修改放款信息
	    boolean flag = false;	    
		if(i>0){
			flag = true;
		}		
		return new ReplyAjax(flag, null).addOp(new OpLog("融资借款管理","修改放款信息","提交银行"));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资借款管理","发起付款流程"})
	//融资担当确认到账及放款页面
	public Reply enterIntoFinance(){
		Map<String,Object> m = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("project", loanService.getProject(m));//项目金额
		context.put("loanmap", loanService.getLoanByID(m));//放款信息
		context.put("bankAccount", loanService.getBankAccount(m));//放款信息
		context.put("data", m);
		return new ReplyHtml(VM.html("refundLoan/enterIntoFinance.vm", context));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资借款管理","发起付款流程"})
	//融资担当确认到账及放款操作
	public Reply confrimLoan(){
		int i=0,j=0,k=0,n=0,n2=0;
		Map<String,Object> m = _getParameters();
		//m.put("PROJECT_STATUS", "6");
		m.put("CONFRIM_PERSON", Security.getUser().getName());
		m.put("CONFRIM_TIME", new Date());
		//获取融资机构当前授信余额、实际使用额度、放款金额
		Map<String,Object> loanMap = (Map<String,Object>)loanService.getCreditQuotaLoan(m);
		//获取项目当前授信余额、实际使用额度、放款金额
		Map<String, Object> balanceCreditMap = loanService.getBalanceCreditByPid(m);
		
		//实际使用额度+放款额度 = 融资机构现有的实际使用额度
		double pracctcal=Double.parseDouble(loanMap.get("PRACTICAL_LIMIT").toString())+Double.parseDouble(loanMap.get("LOAN_AMOUNT").toString());
		double balance=Double.parseDouble(loanMap.get("BALANCE").toString())-Double.parseDouble(loanMap.get("LOAN_AMOUNT").toString());
		//实际使用额度+放款额度 = 项目现有的实际使用额度
		double pracctcal1=Double.parseDouble(balanceCreditMap.get("PRACTICAL_LIMIT").toString())+Double.parseDouble(balanceCreditMap.get("LOAN_AMOUNT").toString());
		double balance1=Double.parseDouble(balanceCreditMap.get("BALANCE").toString())-Double.parseDouble(balanceCreditMap.get("LOAN_AMOUNT").toString());
		Map<String,Object> creditMap = new HashMap<String,Object>();
		creditMap.put("PRACTICAL_LIMIT", pracctcal);
		creditMap.put("PROJECT_ID", m.get("PROJECT_ID"));
		creditMap.put("PRACTICAL_LIMIT1", pracctcal1);
		creditMap.put("BALANCE", balance);
		creditMap.put("BALANCE1", balance1);
		creditMap.put("ID", loanMap.get("ID"));
		m.put("ID", loanMap.get("IDS"));
		boolean flag = false;  //判断审核结果
		try{
			//修改放款信息
			i = loanService.updateLoan(m);
			System.out.println(i);
			//确认资金到账后，需更改融资机构实际使用额度
			k = loanService.updateCreditQuota(creditMap);
			//确认资金到账后，需更改项目实际使用额度
			n = loanService.updateBalanceCreditByPid(creditMap);
			//确认资金到账后，需更改融资方案实际使用额度
			loanMap.put("PROJECT_ID", m.get("PROJECT_ID"));
			n2 = loanService.updateBailoutwayByPid(loanMap);
			// 作废该项目支付表其他相关项目
			loanService.invalid(m);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(i>0&&k>0&&n>0&&n2>0){
			flag = true;
			return new ReplyAjax(flag, null).addOp(new OpLog("融资借款管理","审核放款信息","放款审核"));
		}else{
			return new ReplyAjax(flag, null).addOp(new OpLog("融资借款管理","审核放款信息","放款审核"));
		}
	}	
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资借款管理","查看"})
	public Reply queryLoanDetail(){
		VelocityContext context = new VelocityContext();
		Map<String,Object> m = _getParameters();
		Map<String,Object> paytype = (Map)loanService.queryLoanDetail(m);
		if(paytype ==null){
			paytype=new HashMap<String,Object>();
		}
		paytype.put("type", "再融资放款类型");
		if(paytype.get("REPAY_TYPE")!=null){
			String  REPAY_TYPE= paytype.get("REPAY_TYPE").toString();
			paytype.put("REPAY_TYPE",REPAY_TYPE.substring(0,REPAY_TYPE.length()-1 ));
			context.put("repayMap", loanService.getPaytypeName(paytype));
			System.out.println(loanService.getPaytypeName(paytype));
		}
		context.put("loanmap", paytype);
		return new ReplyHtml(VM.html("refundLoan/queryLoanDetail.vm", context));
	}
	
	/***********************************************************************************************************************************
	 **********                                           以下为银行不通过信息                            　　　　　　　　　　　　　　　                            **********
	 ****************                                                                                                   ****************
	 ***********************************************************************************************************************************/
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资借款管理","添加失败原因"})
	public Reply addLoanFailure(){
		int i = 0,j = 0;
		Map<String, Object> m = _getParameters();
		m.put("PROJECT_STATUS", "3");
		System.out.println(m);
		i = loanService.addLoanFailureResaon(m);
		System.out.println(i+"   "+m);
		j = loanService.updateProjectStatus(m);//更改项目状态
		System.out.println(i+"   "+j);
		boolean flag = false;
		if(i>0&&j>0){
			flag = true;
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("融资借款管理","添加失败原因","提交银行"));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "融资管理", "融资借款管理","查看失败原因"})
	public Reply queryLoanFailure(){
		Map<String,Object> reasonMap = _getParameters();
		reasonMap = (Map)loanService.getLoanByID(reasonMap);
		return new ReplyAjax(JSONObject.fromObject(reasonMap));
	}
	
	
//	@SuppressWarnings("unchecked")
//	@aAuth(type=aAuth.aAuthType.USER)
//	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = { "融资管理", "融资借款管理","审核"})
//	public Reply getAuditLoanPage(){
//		VelocityContext context = new VelocityContext();
//		Map<String,Object> m = _getParameters();
//		Map<String,Object> paytype = (Map)loanService.queryLoanDetail(m);
//		String  REPAY_TYPE= paytype.get("REPAY_TYPE").toString();
//		paytype.put("REPAY_TYPE",REPAY_TYPE.substring(0,REPAY_TYPE.length()-1 ));
//		paytype.put("type", "再融资放款类型");
//		if(paytype.get("REPAY_TYPE")!=null){
//			context.put("repayMap", loanService.getPaytypeName(paytype));
//			System.out.println(loanService.getPaytypeName(paytype));
//		}
//		context.put("loanmap", loanService.queryLoanDetail(m));
//		return new ReplyHtml(VM.html("refundLoan/loanAudit.vm", context));
//	}
}
