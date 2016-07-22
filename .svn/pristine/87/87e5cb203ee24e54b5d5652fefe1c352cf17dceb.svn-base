package com.pqsoft.refund.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;

public class RefundProcess {
	private String basePath ="refundSflc.";
	
	public void toDebtProcess(String JBPM_ID){
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("JBPM_ID", JBPM_ID);
		param.put("RE_STATUS", "10");
		Dao.update(basePath+"upRefundByJbpmId", param);
	}
	
	public void backToPlanProcess(String JBPM_ID){
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("JBPM_ID", JBPM_ID);
		param.put("RE_STATUS", "0");
		Dao.update(basePath+"upRefundByJbpmId",param);
	}
	
	public void toFinalApproval(String JBPM_ID){
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("JBPM_ID", JBPM_ID);
		param.put("RE_STATUS", "1");
		Dao.update(basePath+"upRefundByJbpmId", param);
	}
	
	public void toHexiaoComplete(String JBPM_ID){
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("JBPM_ID", JBPM_ID);
		param.put("RE_STATUS", "1");
		List<Object> refundDan = Dao.selectList(basePath+"getRefundDanByJbpmId", param);
		if(refundDan.size() > 0){
			throw new ActionException("存在退款单未核销，不能提交！");
		}else{
			param.put("RE_STATUS", "2");
			Dao.update(basePath+"upRefundByJbpmId", param);
		}
	}
	

}
