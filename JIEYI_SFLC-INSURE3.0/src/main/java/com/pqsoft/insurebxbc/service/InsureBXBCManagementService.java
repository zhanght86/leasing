package com.pqsoft.insurebxbc.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class InsureBXBCManagementService {
	
	private String basePath = "InsureChangeExhib.";
	
	public InsureBXBCManagementService() {
	}
	
	/**
	 *  得到分页数据
	 */
	public Page getPageData(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath + "getProPage",param));
		page.addDate(array, Dao.selectInt(basePath + "getProPageCount", param));
		return page;
	}
	
	/**
	 *  向INSUR_CHARGE_EXHIB添加记录
	 */
	public int insertInsurChargeExhib(Map<String, Object> param) {
		return Dao.insert(basePath + "addChargeExhib", param);
	}
	
	/**
	 *  更新INSUR_CHARGE_EXHIB记录
	 */
	public int updateInsurChargeExhib(Map<String, Object> param) {
		return Dao.update(basePath + "upChargeExhib", param);
	}
	
	/**
	 *  更新INSUR_CHARGE_EXHIB记录  修改状态
	 */
	public int upChargeExhibAdd(Map<String, Object> param) {
		return Dao.update(basePath + "upChargeExhibAdd", param);
	}
	
	/**
	 *  更新INSUR_CHARGE_EXHIB记录  修改状态
	 */
	public int upChargeExhibById(Map<String, Object> param) {
		return Dao.update(basePath + "upChargeExhibById", param);
	}
	
	/**
	 *  向FIL_INSURE_APPLY_INFO添加记录 返回序列 即ID号
	 */
	public int addApplyInfo(Map<String, Object> param) {
		return Dao.insert(basePath + "addApplyInfo", param);
	}
	
}
