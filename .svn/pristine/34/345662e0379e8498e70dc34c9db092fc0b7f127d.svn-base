package com.pqsoft.credit.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class CreditCustomerService {
	
	private final String xmlPath = "credit.customers.";	
	
	
	/**
	 * findCustDetail 客户详细信息
	 * @date 2013-8-31 下午05:36:53
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object findCustDetail(Map<String,Object> map) {
		Map<String, Object> m = Dao.selectOne(xmlPath+"findCustDetail", map);
		if(m!=null){
			Dao.getClobTypeInfo2(m,"IDCARD_PHOTO");
		}
		return m;
	}
	
	/**
	 * doUpdateCust 修改客户信息
	 * @date 2013-9-3 上午11:33:31
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doUpdateCust(Map<String,Object> map) {
		return Dao.update(xmlPath+"updateCust", map);
	}
	/**
	 * doUpdateCust 修改客户信息-同步到原客户信息
	 * @date 2013-9-3 上午11:33:31
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int updateNewCust(Map<String,Object> map) {
		return Dao.update(xmlPath+"updateNewCust", map);
	}
	
	
}
