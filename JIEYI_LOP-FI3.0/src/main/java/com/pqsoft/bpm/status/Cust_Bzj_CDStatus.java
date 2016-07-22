package com.pqsoft.bpm.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.rentWrite.service.rentWriteNewService;
import com.pqsoft.skyeye.api.Dao;

public class Cust_Bzj_CDStatus {
	private String namespace = "bpm.bzj_qmcd.";
	
	public void cust_bzj_cd_Pass(String ID){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("ID", ID);
		List<Map<String,Object>> list = Dao.selectList("cashDepositK.selectRentPlanHeadId", map);
		for(int i=0;i<list.size();i++){
			Map<String,Object> map_tem = list.get(i);
			Dao.update(namespace+"updatePassById", map_tem);
			new rentWriteNewService().qmcdByPayListCode(map_tem.get("PAYLIST_CODE").toString(),map_tem.get("TODAY").toString());
		}
	}
	public void cust_bzj_cd_NoPass(String ID){
		//修改fil_rent_plan_head表 status 状态
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("ID", ID);
		List<Map<String,Object>> list = Dao.selectList("cashDepositK.selectRentPlanHeadId", map);
		for(int i=0;i<list.size();i++){
			Map<String,Object> map_tem = list.get(i);
			Dao.update(namespace+"updatePassById", map_tem);
		}
	}
}
