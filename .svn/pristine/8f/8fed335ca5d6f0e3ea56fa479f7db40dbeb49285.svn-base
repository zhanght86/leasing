package com.pqsoft.bpm.status;

import java.util.HashMap;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.util.StringUtils;

public class CompanyRefundStatus {
	private String namespace = "bpm.suppliers.";

	/**
	 * 经销商授信审批通过
	 * @param CUGP_ID
	 * @author King 2015年4月8日
	 */
	public void companyRefundPass(String ID){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("ID", ID);
		map.put("STATUS", "2");
		Dao.update(namespace+"companyRefundPass", map);
		Map<String,Object> map_temp = Dao.selectOne("companyCredit.selectDate_Company", map);
		map.put("COMEMONEY",map_temp.get("COMEMONEY"));
		map.put("T_HEAD_ID",map_temp.get("ID"));
		map.put("COMPANY_MONEY_BZJ",map_temp.get("COMPANY_MONEY_BZJ"));
		map.put("COMPANY_MONEY_BZJ_YE",map_temp.get("COMPANY_MONEY_BZJ_YE"));
		System.out.println(map+"-----map----");
		//更新t_sys_company_bzj_head表 保证金总计、保证金余额、使用率
		Dao.update("companyCredit.update_t_bzj_Head_Refund", map);
		
	}
	
	/**
	 * 经销商授信审批不通过
	 * @param CUGP_ID
	 * @author King 2015年6月5日
	 */
	public void companyRefundNoPass(String ID){
		/*Map<String,Object> map=new HashMap<String, Object>();
		map.put("ID", ID);
		Dao.delete(namespace+"companyRefundNoPass", map);*/
	}
}
