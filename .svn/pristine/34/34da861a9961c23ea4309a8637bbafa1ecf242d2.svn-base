package com.pqsoft.base.company.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class CompanyInfoService {
	private String basePath = "CompanyInfo.";
	
	/**
	 * 添加厂商信息
	 * @param param
	 * @return
	 * @author wangjunjie
	 * @datetime 2015年2月28日,下午3:17:27
	 */
	public int addCompanyInfo(Map<String,Object> param) {
		return Dao.insert(basePath+"addCompanyInfo",param);
	}
	
	/**
	 * 查询一条厂商信息
	 * @param param
	 * @return
	 * @author wangjunjie
	 * @datetime 2015年2月28日,下午4:09:58
	 */
	public Map<String,Object> getOneCompanyInfo(Map<String,Object> param){
		 return Dao.selectOne(basePath+"getOneComInfo", param);
	}
	
	/**
	 * 有没有信息?
	 * @param param
	 * @return
	 * @author wangjunjie
	 * @datetime 2015年2月28日,下午9:07:56
	 */
	public int selectCount(Map<String,Object> param){
		return Dao.selectInt(basePath+"select_count", param);
	}
	
	/**
	 * 修改厂商信息
	 * @param param
	 * @return
	 * @author wangjunjie
	 * @datetime 2015年2月28日,下午9:16:46
	 */
	public int updateCompanyInfo(Map<String,Object> param){
		return Dao.update(basePath+"updateCompanyInfo",param);
	}
	
	public int delCompanyInfo(Map<String, Object> param){
		return Dao.delete(basePath+"delCompanyInfo", param);
	}
	
}
