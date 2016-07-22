package com.pqsoft.credit.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class CreditFundSupService {
	
	String basePath = "credit.fundSup.";
		public Map<String,Object> getFundSup(Map<String,Object> m ){
			
			return Dao.selectOne(basePath+"getFundSup", m);
					
		}
		public int saveFundSup(Map<String,Object> m ){
			return Dao.insert(basePath+"saveFundSup", m);
		}
		public int updateFundSup(Map<String,Object> m ){
			return Dao.update(basePath+"updateFundSup",m);
		}

}
