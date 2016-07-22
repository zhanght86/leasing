package com.pqsoft.PaymentTerm.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

/**
 * @author 吴剑东
 *
 */
public class PaymentMouldService {
	private final static String path="PaymentMould.";

	public Page toPaymentMouldManager(Map<String,Object> m) {
		Page page = new Page(m);
		page.addDate(JSONArray.fromObject(Dao.selectList(path + "toPaymentMouldManager", m)), Dao.selectInt(path + "toPaymentMouldManagerCount",m));
		return page;
	}

	public void add(Map map){
		Dao.insert(path+"addPaymentMould",map);
	}
	
	public boolean deleted(Map<String,Object> m) {
		return Dao.delete(path+"deletePaymentMouldById", m)>0?false:true;
	}
	
	public boolean deletedByName(Map<String,Object> m) {
		return Dao.delete(path+"deletePaymentMouldByName", m)>0?true:false;
	}
	
	public boolean checkMouldNameCount(Map<String,Object> m) {
		return Dao.selectInt(path+"checkMouldNameCount", m)>0?false:true;
	}
	
	public List<Map<String, Object>> queryPaymentMouldDetail(Map<String,Object> m)
	{
		List<Map<String, Object>> list = Dao.selectList(path+"queryPaymentMouldDetail", m);
		return list;
	}
	public List<Map<String, Object>> queryPaymentNormAll(Map<String,Object> m){
		return Dao.selectList(path+"queryPaymentNormAll", m);
	}
}
