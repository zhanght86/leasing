package com.pqsoft.bpm.refundLoan;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.refundLoan.service.RefundLoanService;
import com.pqsoft.skyeye.rbac.api.Security;

public class RefundLoan {
	private Map<String, Object> param = null;

	private void getVeriable(String jbpmId) {
		param = JBPM.getVeriable(jbpmId);
	}
	
	/**
	 * 放款通过
	 * refundProjectSHStatus
	 * @date 2013-11-12 下午09:28:29
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public void retufnLoanYes(String PROJECT_ID) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("PROJECT_ID", PROJECT_ID);
		param.put("PROJECT_STATUS", "5");
		RefundLoanService service = new RefundLoanService();
		service.updateProjectStatus(param);
	}
	
	/**
	 * 放款不通过 
	 * retufnLoanNo
	 * @date 2013-11-13 下午02:49:09
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void retufnLoanNo(String PROJECT_ID) {
		RefundLoanService service = new RefundLoanService();
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("PROJECT_ID", PROJECT_ID);
		param.put("PROJECT_STATUS", "6");
		param.put("CONFRIM_PERSON", Security.getUser().getName());
		param.put("CONFRIM_TIME", new Date());
		
		//获取融资机构当前授信余额、实际使用额度、放款金额
		Map<String,Object> loanMap = (Map<String,Object>)service.getCreditQuotaLoan(param);
		//获取项目当前授信余额、实际使用额度、放款金额
		Map<String, Object> balanceCreditMap = service.getBalanceCreditByPid(param);
		
		//实际使用额度+放款额度 = 融资机构现有的实际使用额度
		double pracctcal=Double.parseDouble(loanMap.get("PRACTICAL_LIMIT").toString())-Double.parseDouble(loanMap.get("LOAN_AMOUNT").toString());
		double balance=Double.parseDouble(loanMap.get("BALANCE").toString())+Double.parseDouble(loanMap.get("LOAN_AMOUNT").toString());
		//实际使用额度+放款额度 = 项目现有的实际使用额度
		double pracctcal1=Double.parseDouble(balanceCreditMap.get("PRACTICAL_LIMIT").toString())-Double.parseDouble(balanceCreditMap.get("LOAN_AMOUNT").toString());
		double balance1=Double.parseDouble(balanceCreditMap.get("BALANCE").toString())+Double.parseDouble(balanceCreditMap.get("LOAN_AMOUNT").toString());
		Map<String,Object> creditMap = new HashMap<String,Object>();
		creditMap.put("PRACTICAL_LIMIT", pracctcal);
		creditMap.put("PROJECT_ID", param.get("PROJECT_ID"));
		creditMap.put("PRACTICAL_LIMIT1", pracctcal1);
		creditMap.put("BALANCE", balance);
		creditMap.put("BALANCE1", balance1);
		creditMap.put("ID", loanMap.get("ID"));
		param.put("ID", loanMap.get("IDS"));
		try{
			//修改放款信息
			service.updateLoan(param);
			//变更项目状态
			service.updateProjectStatus(param);
			//确认资金到账后，需更改融资机构实际使用额度
			service.updateCreditQuota(creditMap);
			//确认资金到账后，需更改项目实际使用额度
			service.updateBalanceCreditByPid(creditMap);
			//确认资金到账后，需更改融资方案实际使用额度
			loanMap.put("PROJECT_ID", param.get("PROJECT_ID"));
			service.updateBailoutwayByPid(loanMap);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
