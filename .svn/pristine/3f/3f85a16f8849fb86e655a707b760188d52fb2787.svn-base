package com.pqsoft.base.grantCredit.service;

import java.util.HashMap;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class CompanyCreditService {
	private String basePath = "companyCredit.";

	/**
	 * 查询授信管理
	 * 
	 * @param param
	 * @return
	 */
	public Page getCreditPage(Map<String, Object> param) {
		return PageUtil.getPage(param, basePath + "queryComCreditList",
				basePath + "queryComCreditCount");
	}

	/**
	 * 添加经销商授信
	 * 
	 * @param map
	 * @return
	 * @author King 2015年3月19日
	 */
	public int doAddCompanyCredit(Map<String, Object> map) {
		map.put("CUGP_ID", Dao.getSequence("SEQ_CUST_GRANTPLAN"));
		return Dao.insert(basePath + "doAddCompanyCredit", map);
	}
	
	/**
	 * 查询厂商信息
	 * @param company_id
	 * @return
	 * @author King 2015年6月5日
	 */
	public Map<String,Object> queryCompanyIfno(String company_id){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("COMPANY_ID", company_id);
		return Dao.selectOne(basePath+"queryCompanyIfno", map);
	}
	
	/**
	 * 根据授信id查询授信数据
	 * 
	 * @param ID
	 * @return
	 * @author King 2015年3月19日
	 */
	public Map<String, Object> queryCreditInfoById(String ID) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CUGP_ID", ID);
		return Dao.selectOne("SupplierCredit.queryCreditInfoById", map);
	}
	public Page searchClient(Map<String, Object> map){
		return PageUtil.getPage(map, basePath + "searchClientList",
				basePath + "searchClientCount");
	}
	public Page getBZJ_Info(Map<String,Object> map){
		return PageUtil.getPage(map, basePath + "searchBZJ_InfoList",
				basePath + "searchBZJ_InfoCount");
	}
	public int insertBZJ_Info(Map<String,Object> map){
		return Dao.insert(basePath+"insertBZJ_Info", map);
	}
	public Map<String,Object> selectDetailData(Map<String,Object> map){
		return Dao.selectOne(basePath+"selectDetailData", map);
	}
	
}
