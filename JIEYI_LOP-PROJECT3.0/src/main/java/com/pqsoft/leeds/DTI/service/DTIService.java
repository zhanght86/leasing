package com.pqsoft.leeds.DTI.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class DTIService {
	private String path = "DTI.";
	
	public int addDTI(Map<String,Object> param){
		return Dao.insert(path+"addDTI",param);
	}
	
	public List<Map<String,Object>> findByProjectId(Map<String,Object> param){
		return Dao.selectList(path+"findByProjectId", param);
	}
	
	public int updateDTI(Map<String,Object> param){
		return Dao.update(path+"updateDTI",param);
	}
	
	public int updateDTInotCount(Map<String,Object> param){
		return Dao.update(path+"updateDTInotCount",param);
	}
	
	public int deleteDTI(Map<String,Object> param){
		return Dao.delete(path+"deleteDTInotCount",param);
	}
	
	//当前申请车辆月供金额
	public double findMonthRent(Map<String,Object> param){
		List<Double> list=Dao.selectList(path+"findMonthRent", param);
		double d=0;
		for (Double double1 : list) {
			d+=double1;
		}
		return d;
//		return Dao.selectDouble(path+"findMonthRent", param);
	}
	
	public double findZC_AMOUNT_1(Map<String,Object> param){
		return Dao.selectDouble(path+"findZC_AMOUNT_1", param);
	}
	
	public double findZC_AMOUNT_2(Map<String,Object> param){
		return Dao.selectDouble(path+"findZC_AMOUNT_2", param);
	}
	
	public double findZC_AMOUNT_3(Map<String,Object> param){
		return Dao.selectDouble(path+"findZC_AMOUNT_3", param);
	}
}
