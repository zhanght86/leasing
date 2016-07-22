package com.pqsoft.bpm.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.StringUtils;

public class RefundTypeDecistion implements DecisionHandler {
    private String basePath = "refundSflc.";
	 
	@Override
	public String decide(OpenExecution execution) {
        String JBPM_ID = execution.getId();
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("JBPM_ID", JBPM_ID);
        List<Object> applyDanMess = Dao.selectList(basePath+"getRefundDanByJbpmId", param);
        Map<String,Object> oneApply = (Map<String,Object>)applyDanMess.get(0);
        if("1".equals(StringUtils.nullToString(oneApply.get("RE_TYPE")))){
        	return "债权审核";
        }else{
		    return "副总审核";
        }
	}

}
