package com.pqsoft.bpm.status;

import java.util.HashMap;
import java.util.Map;

import com.pqsoft.base.grantCredit.service.SupplierCreditService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.util.StringUtils;

public class SuppliersStatus {
	private String namespace = "bpm.suppliers.";

	/**
	 * 发起申请
	 * @param SUP_ID
	 * @author wanghl
	 * @datetime 2015年3月16日,下午5:50:22
	 */
	public void suppliersApprovaling(String SUP_ID){
		if(StringUtils.isNotBlank(SUP_ID)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("SUP_ID", SUP_ID);
			map.put("STATUS", 1);
			Dao.update(namespace+"updateSuppliersStatus",map);
		}else{
			new ActionException("申请中状态变更失败");
		}
	}
	
	/**
	 * 申请通过
	 * @param SUP_ID
	 * @author wanghl
	 * @datetime 2015年3月16日,下午5:50:22
	 */
	public void suppliersApprovalPass(String SUP_ID){
		if(StringUtils.isNotBlank(SUP_ID)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("SUP_ID", SUP_ID);
			map.put("STATUS", 2);
			Dao.update(namespace+"updateSuppliersStatus",map);
		}else{
			new ActionException("通过状态变更失败");
		}
	}
	
	/**
	 * 经销商授信审批通过
	 * @param CUGP_ID
	 * @author King 2015年4月8日
	 */
	public void supplierCreditPass(String CUGP_ID){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("CUGP_ID", CUGP_ID);
		map.put("STATUS", "0");
		Dao.update(namespace+"supplierCreditPass", map);
	}
	
	
	/**
	 * 经销商授信审批不通过
	 * @param CUGP_ID
	 * @author King 2015年6月5日
	 */
	public void supplierCreditNoPass(String CUGP_ID){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("CUGP_ID", CUGP_ID);
		Dao.delete(namespace+"supplierCreditNoPass", map);
	}
}
