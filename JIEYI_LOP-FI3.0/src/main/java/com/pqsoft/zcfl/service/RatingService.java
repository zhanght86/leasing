package com.pqsoft.zcfl.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class RatingService {
	String xmlPath = "zcfl.Rate.";//xml路径
	
	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, xmlPath+"getPageList", xmlPath+"getPageCount");
	}
	
	public List<Map<String,Object>> getRatingConfig(Map<String, Object> param){
		return Dao.selectList( xmlPath + "getRatingConfig", param);
	}
	
	public boolean saveRatingConfig(Map<String, Object> param){
		return Dao.insert( xmlPath + "saveRatingConfig", param) > 0 ? true : false;
	}
	
	public boolean updateRatingConfig(Map<String, Object> param){
		return Dao.update( xmlPath + "updateRatingConfig", param) > 0 ? true : false;
	}
	
	public boolean deleteRatingConfig(Map<String, Object> param){
		return Dao.delete( xmlPath + "deleteRatingConfig", param) >= 0 && Dao.delete( xmlPath + "deleteRating", param) >= 0 ? true : false;
	}
	
	public List<Map<String,Object>> getTitleData(Map<String, Object> param){
		return Dao.selectList( xmlPath + "getTitleData", param);
	}
	
	public List<Map<String,Object>> getConfigTitleData(Map<String, Object> param){
		return Dao.selectList( xmlPath + "getConfigTitleData", param);
	}
	
	public List<Map<String,Object>> getConfigTitleForName(Map<String, Object> param){
		return Dao.selectList( xmlPath + "getConfigTitleData", param);
	}
	
	public boolean saveConfigTItle(Map<String, Object> param){
		String CONFIG_ID = param.get("CONFIG_ID").toString();
		String titleIds = param.get("TITLE_IDS").toString();
		String[] TITLE_IDS = null;
		if(!titleIds.equals("")){
			TITLE_IDS = (titleIds).split(",");
		}
		int i = Dao.delete(xmlPath + "deleteRatingConfig", CONFIG_ID);
		boolean success = true;
		if (i >= 0) {
			if(TITLE_IDS != null){
				for (int SERIATION = 0 ; SERIATION < TITLE_IDS.length; SERIATION++) {
					String TITLE_ID = TITLE_IDS[SERIATION];
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("TITLE_ID", TITLE_ID);
					map.put("CONFIG_ID", CONFIG_ID);
					map.put("SERIATION", SERIATION);
					int j = Dao.insert(xmlPath + "addConfigTItle", map);
					if (j < 0) {
						success = false;
						Dao.rollback();
						break;
					}
				}
			}
		} else {
			success = false;
		}
		return success;
	}
	
}
