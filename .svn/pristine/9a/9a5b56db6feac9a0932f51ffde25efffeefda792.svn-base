package com.pqsoft.base.productmMobile.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class ProductMobileService {
	private String basePath = "Product.";
	
	public List<Object> getProBig(Map<String,Object> param){
		return Dao.selectList(basePath+"getAllProduct",param);
	}
	
	public Map<String,Object> getOneProBig(Map<String,Object> param){
		return Dao.selectOne(basePath+"getOneProduct",param);
	}
	
	public int addProBig(Map<String,Object> param){
		return Dao.insert(basePath+"addProductBig",param);
	}
	
	public int updateProBig(Map<String,Object> param){
		return Dao.update(basePath+"updateProductBig", param);
	}
	
	public List<Object> getProCatenaByProId(Map<String,Object> param){
		return Dao.selectList(basePath+"getProCatenaByProId", param);
	}
	
	public Map<String,Object> getOneProCatena(Map<String,Object> param){
		return Dao.selectOne(basePath+"getOneProCatena", param);
	}
	
	public int addProCatena(Map<String,Object> param ){
		return Dao.insert(basePath+"addProCatena",param);
	}
	
	public int updateProCatena(Map<String,Object> param) {
		return Dao.update(basePath+"updateProCatena", param);
	}
	
	public List<Object> getProTypeList(Map<String,Object> param){
		return Dao.selectList(basePath+"getProductTypeList", param);
	}
	
	public Map<String,Object> getOneProType(Map<String,Object> param){
		return Dao.selectOne(basePath+"getOneProType", param);
	}
	
	public int addProType (Map<String,Object> param){
		return Dao.insert(basePath+"addProType", param);
	}
	
	public int updateProType(Map<String,Object> param){
		return Dao.update(basePath+"updateProType", param);
	}

	public int delProType(Map<String,Object> param){
		return Dao.delete(basePath+"delProType", param);
	}
	
	public int updateProTypeSo(Map<String,Object> param){
		return Dao.update(basePath+"updateProTypeSo", param);
	}
	public Map getslidzzid(Map<String,Object> param)
	{
		return Dao.selectOne(basePath+"getslidzzid",param);
	}
	public int delProCatena(Map<String,Object> param){
		return Dao.delete(basePath+"delProCatena", param);
	}
	
	public int delProduct(Map<String,Object> param){
		return Dao.delete(basePath+"delProduct", param);
	}
}
