package com.pqsoft.bpm.handler;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

import com.pqsoft.base.grantCredit.service.SupplierCreditService;

public class CreditAssureDecistion implements DecisionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String decide(OpenExecution execution) {
		String APPLY_ID = execution.getVariable("APPLY_ID") + "";
		SupplierCreditService service = new SupplierCreditService();
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("APPLY_ID", APPLY_ID);
		Map<String,Object> applyDan = service.getApplyDan(param);
		String turnPath = "";
		if(applyDan.containsKey("") && applyDan.containsKey("")){
			turnPath = "有保证金额度/有房产抵押额度";
		}else if(applyDan.containsKey("")){
			turnPath = "有保证金额度";
		}else if(applyDan.containsKey("")){
			turnPath = "有房产抵押额度";
		}
		return turnPath;
	}

}
