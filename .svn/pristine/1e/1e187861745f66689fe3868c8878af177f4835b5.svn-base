package com.pqsoft.bpm.handler;

import java.util.Map;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

import com.pqsoft.base.grantCredit.service.SupplierCreditService;
import com.pqsoft.margin.action.MarginAction;
import com.pqsoft.util.StringUtils;

public class SupplierBZJDecistion implements DecisionHandler {

	/**
	 * 
	 * @author: King 2015年8月31日
	 */
	private static final long serialVersionUID = 1L;
	MarginAction marginAction = new MarginAction();
	
	/**
	 * 当是一次性缴纳入网保证金 刷入网保证金金额
	 * @param CUGP_ID
	 * @author King 2015年8月31日
	 */
	@Override
	public String decide(OpenExecution execution) {
		String CUGP_ID=execution.getVariable("CUGP_ID")+"";
		SupplierCreditService service=new SupplierCreditService();
		Map<String,Object> map=service.queryCreditInfoById(CUGP_ID);
		if(StringUtils.isNotBlank(map.get("RWBZJGZ"))&&"1".equals(map.get("RWBZJGZ"))){
//			double RWBZJJE=Double.parseDouble(map.get("RWBZJJE")+"");
			
			marginAction.insertMargin(map);
			return "一次性缴纳";
		}else
			return "分期缴纳";
	}

}
