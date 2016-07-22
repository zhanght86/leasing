package com.pqsoft.agreementBill.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class AgreementBillService {
    private String basePath = "agreementBill.";
    
    public Page getAgreementBillPage(Map<String,Object> param){
	Page page = new Page(param);
	JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getAgreeMentBillList",param));
	page.addDate(array, Dao.selectInt(basePath+"getAgreeMentBillCount", param));
	return page;
    }
    
    public int addAgreementBill(Map<String,Object> param){
	return Dao.insert(basePath+"addAgreeBill", param);
    }
    
    public int updateBillMess(Map<String,Object> param){
	return Dao.update(basePath+"updateAgreeBill", param);
    }
 
}
