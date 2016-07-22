package com.pqsoft.businessTax.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.InvoiceUtil;

public class BusTaxRentService {
	private String basePath = "BusTaxInvoice.";
	
	/**
	 * 营业税租金开票申请数据
	 * @param param
	 * @return
	 */
	public Page getBusRentApplyData(Map<String,Object> param){
		param.put("SQL_STR", InvoiceUtil.getJoinStr());
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getBusRentApplyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getBusRentApplyCount", param));
		return page ;
	}
}
