package com.pqsoft.modelChange.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.BaseUtil;

public class InvoiceChangeService {
	private String basePath = "InvoiceChange.";
	
	/**
	 * 发票开具方式变更申请数据
	 * @param param
	 * @return
	 */
	public Page getInvoiceChangeApplyData(Map<String,Object> param){
		Map<String,Object> SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			param.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getChangeApplyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getChangeApplyCount", param));
		return page ; 
	}
	
	/**
	 * 发票开具方式变更核销数据
	 * @param param
	 * @return
	 */
	public Page getChangeHeXiaoPageData(Map<String,Object> param ){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getChangeHeXiaoList",param));
		page.addDate(array, Dao.selectInt(basePath+"getChangeHeXiaoCount", param));
		return page ; 
	}
	
	/**
	 * 发票开具方式变更查询历史
	 * @param param
	 * @return
	 */
	public Page getChangePageSearchData(Map<String,Object> param) {
		Page page = new Page(param);
		Map<String,Object> SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			param.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getChangeSearchList",param));
		page.addDate(array, Dao.selectInt(basePath+"getChangeSearchCount", param));
		return page ; 
	}
	
	/**
	 * 通过变更申请
	 * @param param
	 * @return
	 */
	public int crossApplyUpdateStatus(Map<String,Object> param ){
		return Dao.update(basePath+"updateChangeApplyMess", param);
	}
	
	public int updateProjectInvoiceStyle(Map<String,Object> param ){
		return Dao.update(basePath+"updateProjectInvoiceStyle",param);
	}
	
 
	/**
	 * 驳回变更申请
	 * @param param
	 * @return
	 */
	public int reJectDeleteApply(Map<String,Object> param){
		return Dao.delete(basePath+"delChangeApply", param);
	}

	/**
	 * 发票变更申请操作
	 * @param param
	 * @return
	 */
	public int addChangeApply(Map<String,Object> param ){
		return Dao.insert(basePath+"addChangeApply", param);
	}
	
	public Map<String,Object> getBaseApplyMess(Map<String,Object> param ){
		return Dao.selectOne(basePath+"getBaseApplyMess", param);
	}
}
