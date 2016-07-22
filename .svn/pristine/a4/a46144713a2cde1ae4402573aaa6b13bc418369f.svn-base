package com.pqsoft.bpm.cashDeposit;

import java.util.HashMap;
import java.util.Map;

import com.pqsoft.cashDeposit.service.CashDepositService;

public class CashDeposit {

	CashDepositService service = new CashDepositService();
	
	/**
	 * 保证金退款成功
	 * @author yx
	 * @date2015-03-02
	 * @param PAY_CODE 支付表编号
	 */
	public void yes(String PAY_CODE){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PAY_CODE", PAY_CODE);
		map.put("DEPOSIT_STATUS", "2");
		service.doUpRentStatus(map);
	}
	
	/**
	 * 保证金退款失败
	 * @author yx
	 * @date2015-03-02
	 * @param PAY_CODE 支付表编号
	 */
	public void no(String PAY_CODE){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PAY_CODE", PAY_CODE);
		map.put("DEPOSIT_STATUS", "3");
		service.doUpRentStatus(map);
	}
}
