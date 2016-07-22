package com.pqsoft.insurebxbc.service;
/**
 *  保险补差支付申请核销
 */
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class InsureBXBCReceiveAffirmService {
	
	private String basePath = "insurebxbcsqAffirm.";
	
	public InsureBXBCReceiveAffirmService() {
	}
	
	/**
	 *  得到分页数据
	 */
	public Page getPageData(Map<String, Object> param) {
		Page page = new Page(param);
		param.put("STATUS", "0");
		param.put("FUND_NAME", "保险补差收款");
		param.put("IS_SUB", "已提交");
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath + "queryAll",param));
		page.addDate(array, Dao.selectInt(basePath + "queryCount", param));
		return page;
	}
	
	/**
	 *   核销一条保险补差收款申请状态 FIL_INSURE_APPLY_INFO
	 */
	public int updateAffirmBXBCItem(Map<String,Object> param){
		param.put("STATUS", "1");//核销
		return Dao.update(basePath + "affirmBXBCItem",param);
	}
	
	/**
	 *   核销一条保险补差收款申请状态 INSUR_CHARGE_EXHIB
	 */
	public int updateAffirmBXBCDetail(Map<String,Object> param){
		param.put("STATUS", "1");//核销
		return Dao.update(basePath + "affirmBXBCDetail",param);
	}
	
	/**
	 *   驳回一条保险补差支付申请状态
	 */
	public int updateBackBXBCItem(Map<String,Object> param){
		param.put("STATUS", "0");
		param.put("IS_SUB", "已驳回");
		return Dao.update(basePath + "backBXBCItem",param);
	}
}
