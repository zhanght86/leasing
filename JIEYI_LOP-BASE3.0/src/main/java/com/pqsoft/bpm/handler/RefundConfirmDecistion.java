package com.pqsoft.bpm.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

import com.pqsoft.skyeye.api.Dao;

public class RefundConfirmDecistion implements DecisionHandler {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String basePath = "refundSflc.";
	 
	@Override
	public String decide(OpenExecution execution) {
		String JBPM_ID = execution.getId();
		Map<String,Object> newParam = new HashMap<String, Object>();
		newParam.put("JBPM_ID", JBPM_ID);
		List<Object> getApplyDan = Dao.selectList(basePath+"getRefundDanByJbpmId", newParam);
		if(getApplyDan.size()>0){
			return "副总审核";
		}else{
		    return "结束";
		}
	}
}
