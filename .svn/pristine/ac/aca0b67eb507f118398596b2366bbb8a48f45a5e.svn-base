package com.pqsoft.insurebxbc.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;

public class InsureBXBCDetailService {
	
	private String insurebasePath = "InsureChangeExhib.";
	
	public InsureBXBCDetailService() {
	}
	
	/**
	 *  得到明细的分页数据
	 */
	public Page getDetailPageData(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(insurebasePath + "selChargeExhibDetail",param));
		page.addDate(array, Dao.selectInt(insurebasePath + "selChargeExhibCountDetail", param));
		return page;
	}

}
