package com.pqsoft.insureSettlement.service;
/**
 *  款项上传
 *  @author hanxl
 */
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class InsureSettlementUploadService {
	
private String basePath = "insureSettlementUpload.";
	
	public InsureSettlementUploadService() {
	}
	
	/**
	 *  得到分页数据
	 */
	public Page getPageData(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath + "queryAll",param));
		page.addDate(array, Dao.selectInt(basePath + "queryCount", param));
		return page;
	}
	
	/**
	 *   插入一条保险理赔款来款信息
	 */
	public int insertInsureSettlementFee(Map<String,Object> param){
		return Dao.insert(basePath + "addinsureSettlementFee",param);
	}

}
