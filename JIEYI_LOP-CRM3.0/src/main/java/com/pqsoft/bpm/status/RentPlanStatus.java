package com.pqsoft.bpm.status;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.pay.service.PayTaskService;
import com.pqsoft.skyeye.api.Dao;

/**
 * 
 * @author King 2013-9-25
 */
public class RentPlanStatus {
	private Map<String, Object> param = null;
	private String namespace = "PayTask.";

	private void getVeriable(String jbpmId) {
		param = JBPM.getVeriable(jbpmId);
	}

	/**
	 * 变更还款计划版本为原值的绝对值
	 * 
	 * @param JbpmId
	 * @author:King 2013-9-23
	 */
	public void synchronizationBeginning(String JbpmId) {
		this.getVeriable(JbpmId);
		String PROJECT_ID = null;
		if (param.containsKey("PROJECT_ID")) {
			PROJECT_ID = param.get("PROJECT_ID") + "";
		}
		if (StringUtils.isNotEmpty(PROJECT_ID)) {
			param = Dao.selectOne(namespace + "forwardRepaymentShow1", param);
			new PayTaskService().changeSucceed(param);
		}
	}
}
