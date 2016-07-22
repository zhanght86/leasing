package com.pqsoft.base.channel.service;

import java.util.List;
import java.util.Map;

import org.omg.CORBA.INTERNAL;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class AssureCreditService {
	private String basePath = "CreditAmount.";
	
	public Page getAssureCreditPageData(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getAssureCreditList",param));
		page.addDate(array, Dao.selectInt(basePath+"getAssureCreditCount", param));
		return page;
	}

	public List<Object> getAmountApplyDan(Map<String,Object> param){
		return Dao.selectList(basePath+"getCreditApplyDan", param);
	}
	
	public int addAssureApplyDan(Map<String,Object> param){
	     String APPLY_ID = Dao.getSequence("SEQ_T_CREDIT_CHANNEL_APPLY");
	     param.put("APPLY_ID", APPLY_ID);
	     Dao.insert(basePath+"addCreditApplyDan", param);
		return Integer.parseInt(APPLY_ID);
	} 
	
	public int upAssureApplyDan(Map<String,Object> param){
		return Dao.update(basePath+"updateCreditApplyDan", param);
	}
	
	public Map<String,Object> getOneCreditMess(Map<String,Object> param){
		return Dao.selectOne(basePath+"getOneCreditBySupId", param);
	}
	
	public Map<String,Object> getOneApplyDan(Map<String,Object> param){
		return Dao.selectOne(basePath+"getOneApplyDanById", param);
	}
	
	public Map<String,Object> getOneSupCredit(Map<String,Object> param ){
		return Dao.selectOne(basePath+"getCreditMess", param);
	}
	
	public Map<String,Object> getWriteOffMess(Map<String,Object> param){
		return Dao.selectOne(basePath+"getWriteOffMess", param);
	}
}
