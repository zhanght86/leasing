package com.pqsoft.capitalPool.supplierPool.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class EqPayBalancePoolService {

	private String basePath = "EqPayBalancePool.";
	
	/**
	 * 设备付款余款池--可用余款
	 * @param param
	 * @return
	 */
	public Page getBalancePageData(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getPoolBalanceList",param));
		page.addDate(array, Dao.selectInt(basePath+"getPoolBalanceCount", param));
		return page;
	}
	
	/**
	 * 设备付款余款池--退款信息
	 * @param param
	 * @return
	 */
    public Page getRefundPageData(Map<String,Object> param){
    	param.put("DIC_RE_STATUS","退款单状态");
    	Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getPoolRefundList",param));
		page.addDate(array, Dao.selectInt(basePath+"getPoolRefundCount", param));
		return page;
    }
    
    /**
	 * 设备付款余款池--冲抵信息
	 * @param param
	 * @return
	 */
    public Page getOffsetPageData(Map<String,Object> param){
    	param.put("DIC_FI_STATUS", "资金池冲抵状态");
    	Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getPoolOffsetList",param));
		page.addDate(array, Dao.selectInt(basePath+"getPoolOffsetCount", param));
		return page;
    }
    
    public List<Object> getEqPayPoolList(Map<String,Object> param ){
    	param.put("DB_STATUS", "资金池状态");
    	return Dao.selectList(basePath+"getEqPayPoolList", param);
    }
}
