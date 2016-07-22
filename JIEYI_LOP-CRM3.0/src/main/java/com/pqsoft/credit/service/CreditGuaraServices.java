package com.pqsoft.credit.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class CreditGuaraServices {
	private final String xmlPath = "creditGuara.";	
	
	
	/**
	 * 
	* @Title: getGurarntorData
	* @autor wuguowei_jing@163.com 2014-3-28 下午11:22:25
	* @Description: TODO 获取担保人列表信息
	* @param m
	* @return    
	* List<Map<String,Object>>    
	* @throws
	 */
	public List<Map<String,Object>> getGurarntorData(Map<String,Object> m){
		
		return Dao.selectList(xmlPath+"getGurarntorData", m);
	}
	/**
	 * 
	* @Title: getGurarntorDataOne
	* @autor wuguowei_jing@163.com 2014-3-29 下午2:54:25
	* @Description: TODO 查询单条
	* @param m
	* @return    
	* Map<String,Object>    
	* @throws
	 */
	public Map<String,Object> getGurarntorDataOne(Map<String,Object> m){
		return Dao.selectOne(xmlPath+"getGurarntorDataOne", m);
	}
	public Map<String,Object> getGurarntorLxr(Map<String,Object> m,String INFOR_TYPE){
		m.put("INFOR_TYPE", INFOR_TYPE);
		return Dao.selectOne(xmlPath+"getGurarntorLxr", m);
	}
	/**
	 * 
	* @Title: getSysDicData
	* @autor wuguowei_jing@163.com 2014-3-28 下午5:38:13
	* @Description: TODO 获取字典值
	* @param TYPE
	* @return    
	* List<Map<String,Object>>    
	* @throws
	 */
	public List<Map<String,Object>> getSysDicData(String TYPE){
		return Dao.selectList(xmlPath+"getSysDicData", TYPE);
	}
	/**
	 * 
	* @Title: doSaveGuarantor
	* @autor wuguowei_jing@163.com 2014-3-28 下午8:42:35
	* @Description: TODO 添加 修改 担保人信息
	* @param m
	* @return    
	* int    
	* @throws
	 */
	public int doSaveGuarantor(Map<String,Object> m){
		return Dao.insert(xmlPath+"doSaveGuarantor",m);
	}
	public int doUpdateGuarantor(Map<String,Object> m){
		return Dao.update(xmlPath+"doUpdateGuarantor",m);
	}
	
	
	public int doGuarantorLxr(Map<String,Object> m){//添加担保人法定代表人
		m.put("INFOR_TYPE", 1);
		return Dao.insert(xmlPath+"doGuarantorLxr",m);
	}
	public int updateGuarantorLxr(Map<String,Object> m){//修改担保人法定代表人
		return Dao.update(xmlPath+"updateGuarantorLxr",m);
	}
	public int doGuarantorLxr_2(Map<String,Object> m){//添加担保人联系人
		m.put("INFOR_TYPE", 3);
		return Dao.insert(xmlPath+"doGuarantorLxr_2",m);
	}
	
	public int updateGuarantorLxr_2(Map<String,Object> m){//修改担保人联系人
		return Dao.insert(xmlPath+"updateGuarantorLxr_2",m);
	}
	public int doGuarantorLxr_4(Map<String,Object> m){//添加配偶
		m.put("INFOR_TYPE", 4);
		return Dao.insert(xmlPath+"doGuarantorLxr_4",m);
	}
	public int updateGuarantorLxr_4(Map<String,Object> m){//修改配偶
		return Dao.insert(xmlPath+"updateGuarantorLxr_4",m);
	}
}
