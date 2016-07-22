package com.pqsoft.capitalPool.supplierPool.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class SupPadSinkPoolService {

	private String basePath = "SupPadSinkPool.";
	
	/**
	 * 供应商垫汇余款可用额度
	 * @param param
	 * @return
	 */
	public Page getPageDataCanUse(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getBalanceList",param));
		page.addDate(array, Dao.selectInt(basePath+"getBalanceCount", param));
		return page;
	}
	
	
	/**
	 * 供应商垫汇余款退款信息
	 * @param param
	 * @return
	 */
	public Page getRefundPageData(Map<String,Object> param){
		param.put("DIC_RE_STATUS","退款单状态");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getRefundList",param));
		page.addDate(array, Dao.selectInt(basePath+"getRefundCount", param));
		return page;
	}
	
	/**
	 * 供应商垫汇余款池数据详细列表
	 * @param param
	 * @return
	 */
	public List<Object> getPadSinkList(Map<String,Object> param){
		param.put("DB_STATUS", "资金池状态");
		return Dao.selectList(basePath+"getPadSinkList", param);
	} 
	
	/**
	 * 供应商垫汇余款池冲抵信息
	 * @param param
	 * @return
	 */
	public Page getOffsetPageData(Map<String,Object> param){
		param.put("DIC_FI_STATUS", "资金池冲抵状态");
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getOffsetList",param));
		page.addDate(array, Dao.selectInt(basePath+"getOffsetCount", param));
		return page;
	}
	
}
