package com.pqsoft.bpm.status;

import java.util.HashMap;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class ClientCreditStatus {
	
	private String namespace = "clientCredit.";
	/**
	 * 客户授信审批通过
	 * @param CUGP_ID
	 * @author King 2015年4月8日
	 */
	public void clientCreditPass(String CUGP_ID){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("CUGP_ID", CUGP_ID);
		map.put("STATUS", "0");
		System.out.println(map);
		Dao.update(namespace+"clientCreditPass", map);
	}
	
	/**
	 * 客户授信审批不通过
	 * @param CUGP_ID
	 * @author King 2015年6月5日
	 */
	public void clientCreditNoPass(String CUGP_ID){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("CUGP_ID", CUGP_ID);
		Dao.delete(namespace+"clientCreditNoPass", map);
	}
}
