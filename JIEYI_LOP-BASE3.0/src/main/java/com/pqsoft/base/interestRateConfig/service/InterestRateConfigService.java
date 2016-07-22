package com.pqsoft.base.interestRateConfig.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

/**
 *   利率配置
 *   @author 韩晓龙
 */
/*
 *   T_SYS_RATE
 *	  ID               NUMBER(6),
 *	  ADJUST_TIME      DATE,
 *	  SIX_MONTHS       FLOAT,
 *	  ONE_YEAR         FLOAT,
 *	  ONE_THREE_YEARS  FLOAT,
 *   THREE_FIVE_YEARS FLOAT,
 *   OVER_FIVE_YEARS  FLOAT,
 *	  REMARK           VARCHAR2(400)
 */
public class InterestRateConfigService{
	private String basePath = "InterestRateConfig.";
	
	public InterestRateConfigService() {
	}
	
	//得到分页数据
	public Page getPageData(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList("InterestRateConfig.queryAll",param));
		page.addDate(array, Dao.selectInt(basePath + "queryCount", param));
		return page;
	}
	
	//更新利率配置
	public int updateInterestRate(Map<String,Object> param){
		return Dao.update(basePath + "updateInterestRate", param);
	}
	
	//插入新的利率配置
	public int insertInterestRate(Map<String,Object> param){
		return Dao.insert(basePath+"addInterestRate",param);
	}
	
	//删除选中的利率配置
	public int deleteInterestRate(Map<String,Object> param){
		return Dao.delete(basePath+"deleteInterestRate",param);
	}

}
