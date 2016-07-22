package com.pqsoft.insurebxbc.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;

public class InsureBXBCReceiveService {
	
	private String basePath = "insurebxbcsq.";
	private String insurebasePath = "InsureChangeExhib.";
	
	public InsureBXBCReceiveService() {
	}
	
	/**
	 *  得到分页数据
	 */
	public Page getPageData(Map<String, Object> param) {
		Page page = new Page(param);
		param.put("STATUS", "1");
		param.put("IS_SUB", "已提交");
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath + "queryAll",param));
		page.addDate(array, Dao.selectInt(basePath + "queryCount", param));
		return page;
	}
	
	/**
	 *  得到明细的分页数据
	 */
	public Page getDetailPageData(Map<String, Object> param) {
		Page page = new Page(param);
		param.put("FUNDS_NAME", "保险补差收款");
		JSONArray array = JSONArray.fromObject(Dao.selectList(insurebasePath + "selChargeExhibDetail",param));
		page.addDate(array, Dao.selectInt(insurebasePath + "selChargeExhibCountDetail", param));
		return page;
	}
	
	/**
	 *  得到可以添加收取的分页数据
	 */
	public Page getAddPageData(Map<String, Object> param) {
		Page page = new Page(param);
		param.put("FUNDS_NAME", "保险补差收款");
		JSONArray array = JSONArray.fromObject(Dao.selectList(insurebasePath + "selChargeExhib",param));
		page.addDate(array, Dao.selectInt(insurebasePath + "selChargeExhibCount", param));
		return page;
	}
//////////////////////////////////////////////////////	
	/**
	 *   删除一条保险补差支付申请
	 */
	public int deleteBCSQApply(Map<String, Object> param) {
		return Dao.delete(basePath + "deleteBCSQApply",param);
	}
	
	/**
	 *   修改一条保险补差支付申请
	 *   update FIL_APPLY_INFO set is_sub='已提交',status='未核销'
	 */
	public int updateBCSQApply(Map<String,Object> param){
		param.put("IS_SUB", "已提交");
		param.put("STATUS", "0");
		return Dao.update(basePath + "commitBCSQApply",param);
	}

}
