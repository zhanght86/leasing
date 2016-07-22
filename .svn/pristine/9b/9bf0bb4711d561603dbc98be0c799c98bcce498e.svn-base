package com.pqsoft.base.suppliersInfo.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class SuppliersInfoService {
	private String basePath = "SuppliersInfo.";
	
	/**
	 * 添加供应商信息
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年2月28日,下午3:17:27
	 */
	public int addSupplierInfo(Map<String,Object> param) {
		return Dao.insert(basePath+"addSupplierInfo",param);
	}
	
	/**
	 * 查询一条供应商信息
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年2月28日,下午4:09:58
	 */
	public Map<String,Object> getOneSupplierInfo(Map<String,Object> param){
		 return Dao.selectOne(basePath+"getOneSupp", param);
	}
	
	/**
	 * 有没有副表信息?
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年2月28日,下午9:07:56
	 */
	public int selectCount(Map<String,Object> param){
		return Dao.selectInt(basePath+"select_count", param);
	}
	
	/**
	 * 修改供应商信息
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年2月28日,下午9:16:46
	 */
	public int updateSupplierInfo(Map<String,Object> param){
		return Dao.update(basePath+"updateSupplierInfo",param);
	}
}