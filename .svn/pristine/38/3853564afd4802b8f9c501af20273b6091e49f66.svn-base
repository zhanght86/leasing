package com.pqsoft.refund.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.BaseUtil;

public class RefundBailApplyService {
	private String basePath = "refundSflc.";
	
	public Page getRefundPageData(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getRefundBailList",param));
		page.addDate(array, Dao.selectInt(basePath+"getRefundBailCount", param));
		return page;
	}
	
	public List<Object> getCanRefundBailList(Map<String,Object> param ){
		return Dao.selectList(basePath+"getCanRefundBailList", param);
	}
	
	public Page getRefundDetailPageData(Map<String,Object> param){
		Page page = new Page(param);
		Map<String,Object> SUP_MAP = BaseUtil.getSup();
		if(SUP_MAP!=null){
			param.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getSearchDetailList",param));
		page.addDate(array, Dao.selectInt(basePath+"getSearchDetailCount", param));
		return page;
	}
	
	public List<Map<String,Object>> getRefundList(Map<String,Object> param){
		//退款主表
		List<Map<String,Object>> listMess =  Dao.selectList(basePath+"getExportDebtMess", param);
		for (Map<String, Object> map : listMess) {
			String company_name = map.containsKey("COMPANY_NAME") && map.get("COMPANY_NAME") !=null ? map.get("COMPANY_NAME").toString() :"";
			String sup_shortname = map.containsKey("SUP_SHORTNAME") && map.get("SUP_SHORTNAME")!=null ? map.get("SUP_SHORTNAME").toString():"";
			if(!"".equals(company_name) && "".equals(sup_shortname)){
				map.put("COMPANY_NAME", company_name+" 汇总");
			}
			if("".equals(company_name) && "".equals(sup_shortname)){
				map.put("COMPANY_NAME", "总计");
			}
		}
		return listMess;
	}

	public Map<String,Object> getOneRefundDan(Map<String,Object> param ){
		return Dao.selectOne(basePath+"getOneRefundDan", param);
	} 
	
	public List<Object> getRefundDetail(Map<String,Object> param){
		return Dao.selectList(basePath+"getRefundDetailReport", param);
	}
}
