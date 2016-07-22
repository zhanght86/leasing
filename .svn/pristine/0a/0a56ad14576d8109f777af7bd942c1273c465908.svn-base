package com.pqsoft.bpm.renterpool;

import java.util.Map;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.renterpool.service.CashDepositService;
import com.pqsoft.skyeye.rbac.api.Security;
public class RenterPool {

	private Map<String, Object> param = null;

	private void getVeriable(String jbpmId) {
		param = JBPM.getVeriable(jbpmId);
	}
	
	/**
	 * 退款通过
	 * doUpateRefundStatus
	 * @date 2013-11-10 下午08:48:42
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public void doUpateRefundStatus(String jbpmId){
		this.getVeriable(jbpmId);
		param.put("USERCOE", Security.getUser().getCode());
		param.put("USERNAME", Security.getUser().getName());
		CashDepositService service = new CashDepositService();
		param.put("RE_STATUS", 2);
		
		service.doUpateRefundStatus(param);
	}
}
