package com.pqsoft.documentApp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class DocRentplanService {
	private final String xmlPath = "dossierApp.rentplan.";
	
	/**
	 * 权证接口查询方法（合同表）
	 * @param params
	 * @return
	 */
	public Map<String, Object> queryDocRentplanById(Map<String, Object> params) {
		System.out.println("params:"+params.get("ID"));
		params.put("ID", params.get("ID"));
		params.put("PROJECT_ID", params.get("ID"));
		Map<String, Object> rr= new HashMap<String, Object>();
		List<String> fives=Dao.selectList(xmlPath+"queryRentplanById", params);
		rr.put("fives", fives);
		return rr;
	}
	
	/**
	 * 权证接口添加方法（合同表）
	 * @param params
	 * @return
	 */
	public int addDocRentplanById(Map<String, Object> params) {
		params.put("ID", params.get("ID"));
		params.put("PROJECT_ID", params.get("ID"));
		Map<String, Object> rr= new HashMap<String, Object>();
		List<Map<String, Object>> fives=Dao.selectList(xmlPath+"queryRentplanById", params);		
	    int i = 0;
		for(Map<String,Object> obj:fives){	 
	    	rr.put("ID",  params.get("ID"));
	    	rr.put("PROJECT_ID",  params.get("ID"));
	    	rr.put("PAY_CODE", obj.get("PAYLIST_CODE"));
	    	rr.put("LEASE_CODE", obj.get("LEASE_CODE"));
	    	rr.put("PAYMENT_STATUS", obj.get("O_STATUS"));
	    	rr.put("START_DATE", obj.get("START_DATE"));
	    	rr.put("END_DATE", obj.get("PAY_DATE"));
	    	rr.put("EQU_COUNT", obj.get("S_AMOUNT"));
	    	rr.put("PAY_STATUS", obj.get("B_STATUS"));
	    	rr.put("PAY_WAY", obj.get("PAY_WAY"));
	    	rr.put("PAYMENT_DATE", obj.get("REALITY_DATE"));
	    	rr.put("TOPRIC_SUBFIRENT", obj.get("FINANCE_TOPRIC"));
	    	rr.put("LEASE_PERIOD", obj.get("LEASE_PERIOD"));
	    	rr.put("LEASE_TERM", obj.get("LEASE_TERM"));
	    	rr.put("FIRSTRENT_VALUE", obj.get("FIRSTRENT_RATE"));
	    	rr.put("DEPOSIT_RATE", obj.get("DEPOSIT_RATE"));
	    	rr.put("LEASE_TOPRIC", obj.get("LEASE_TOPRIC"));
	    	rr.put("FIRSTRENT_PRICE", obj.get("FIRSTRENT_VALUE"));
	    	rr.put("DEPOSIT_PRICE", obj.get("DEPOSIT_VALUE"));
	    	rr.put("FC_STATUS", null);
	    	rr.put("CREATE_DATE", null);
	    	i = Dao.insert(xmlPath+"insertDocRentplanByIds", rr);
	    }				
		return i;
	}
}
