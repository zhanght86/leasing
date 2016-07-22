package com.pqsoft.bpm.status;

import java.util.HashMap;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class BadBebtManageStatus {
	private String namespace = "badBebt.";
	/**
	 * 坏账审批通过
	 * @param CUGP_ID
	 */
	public void badBebtManagePass(String ID){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("ID", ID);
		map.put("STATUS",'2');
		System.out.println(map+"----1-----");
		Dao.update(namespace+"badBebtManagePassOrNot", map);
	}
	/**
	 * 坏账审批不通过
	 * @param CUGP_ID
	 */
	public void badBebtManageNoPass(String ID){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("ID", ID);
		map.put("STATUS", "3");
		System.out.println(map+"------2-----");
		Dao.update(namespace+"badBebtManagePassOrNot", map);
	}
}
