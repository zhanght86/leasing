package com.pqsoft.base.grantCredit.service;

import java.util.HashMap;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class ClientCreditService {
	private String basePath = "clientCredit.";

	/**
	 * 查询授信管理
	 * 
	 * @param param
	 * @return
	 */
	public Page getCreditPage(Map<String, Object> param) {
		return PageUtil.getPage(param, basePath + "queryClientCreditList",
				basePath + "queryClientCreditCount");
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
		return Dao.insert(basePath + "doAddClientCredit", map);
	}
	
	/**
	 * 查询厂商信息
	 * @param company_id
	 * @return
	 * @author King 2015年6月5日
	 */
	public Map<String,Object> queryClientIfno(String client_id){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("CLIENT_ID", client_id);
		return Dao.selectOne(basePath+"queryClientIfno", map);
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
	
	/**
	 * 获取商务政策名称
	 * @author yangxue
	 * @date 2015-07-06 
	 * @param map
	 * @return
	 */
	public Object toFindScheme(Map<String,Object> map){
		System.out.println("-----"+map);
		return Dao.selectList(basePath + "toFindScheme", map);
	}
}
