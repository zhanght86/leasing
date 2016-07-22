package com.pqsoft.bpm.status;

import java.util.HashMap;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class CompanyCreditStatus {
	
	private String namespace = "companyCredit.";
	/**
	 * 厂商授信审批通过
	 * @param CUGP_ID
	 * @author King 2015年4月8日
	 */
	public void companyCreditPass(String CUGP_ID){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("CUGP_ID", CUGP_ID);
		map.put("STATUS", "0");
		Dao.update(namespace+"companyCreditPass", map);
	}
	
	/**
	 * 厂商授信审批不通过
	 * @param CUGP_ID
	 * @author King 2015年6月5日
	 */
	public void companyCreditNoPass(String CUGP_ID){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("CUGP_ID", CUGP_ID);
		Dao.delete(namespace+"companyCreditNoPass", map);
	}
}
