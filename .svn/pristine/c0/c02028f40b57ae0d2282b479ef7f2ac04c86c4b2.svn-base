package com.pqsoft.bpm.handler;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

import com.pqsoft.base.channel.ChannelTaskAction;
import com.pqsoft.base.channel.service.CreditAmountManagerService;
import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.StringUtils;

public class assureThingTypeDecistion implements DecisionHandler {
	private String basePath = "CreditAmount." ;
	/**
	 * @author:jinhongdong 2014-01-17
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public String decide(OpenExecution execution) {
		String APPLY_ID = JBPM.getVeriable(execution.getId()).get("APPLY_ID")+"";
		//通过申请单查询对应的申请单信息
		Map<String,Object> newParam = new HashMap<String, Object>();
		newParam.put("APPLY_ID", APPLY_ID);
		String nextStation = "";
		Map<String,Object> applyDan = Dao.selectOne(basePath+"getCreditApplyDan", newParam); 
		if(applyDan.containsKey("ASSURE_TYPE") && applyDan.get("ASSURE_TYPE") !=null && "1".equals(applyDan.get("ASSURE_TYPE").toString())){
			//将金额写入核销功能
			ChannelTaskAction channel = new ChannelTaskAction();
			channel.addAccounts(execution.getId());
			if(applyDan.containsKey("ASSURE_PAYMENT_MODE") && applyDan.get("ASSURE_PAYMENT_MODE") !=null && "1".equals(applyDan.get("ASSURE_PAYMENT_MODE").toString())){
				//更新保证额度
		        CreditAmountManagerService creditService = new CreditAmountManagerService();
		        newParam.put("ASSURE_AMOUNT", StringUtils.nullToString(applyDan.get("APPLY_AMOUNT")));
		        newParam.put("CREDIT_ID", StringUtils.nullToString(applyDan.get("CREDIT_ID")));
		        creditService.updateCreditAmount(newParam);
			}
			nextStation = "财务核销" ;
		}else{
			ChannelTaskAction channelTask = new ChannelTaskAction();
			channelTask.updateAssureAmount(execution.getId());
			nextStation = "结束" ;
		}
		return nextStation;
	}

}
