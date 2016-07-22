package com.pqsoft.credit.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class CreditEvaluateService {
	
	String basePath = "credit.Evaluate.";
		public Map<String,Object> getCreditEval(Map<String,Object> m ){
			
			return Dao.selectOne(basePath+"getCreditEval", m);
					
		}
		public int saveCreditEval(Map<String,Object> m ){
			return Dao.insert(basePath+"saveCreditEval", m);
		}
		public int updateCreditEval(Map<String,Object> m ){
			return Dao.update(basePath+"updateCreditEval",m);
		}

}
