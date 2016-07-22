package com.pqsoft.targets.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class TargetsBaseService {
	private final String baseSpace="targets.";

	public List getTypeList(Map<String,Object> map){
		String TYPE=map.get("TYPE")+"";
		if(TYPE.equals("1")){//区域
			return Dao.selectList(baseSpace+"queryAreaList",map);
		}else if(TYPE.equals("2")){//客户经理
			return Dao.selectList(baseSpace+"queryKHJLList",map);
		}else if(TYPE.equals("3")){//行业
			return Dao.selectList(baseSpace+"queryHYList",map);
		}
		return null;
	}
	
	public List getTargetsDetail(Map<String,Object> map){
		return Dao.selectList(baseSpace+"getTargetsDetail",map);
	}
	
	public Map<String,Object> queryDateType(String TIMETYPE){
		if(TIMETYPE.equals("1")){//本年
			return Dao.selectOne(baseSpace+"queryYear");
		}else if(TIMETYPE.equals("2")){//上年
			return Dao.selectOne(baseSpace+"queryYearLast");
		}else if(TIMETYPE.equals("3")){//本季度
			return Dao.selectOne(baseSpace+"queryQuarter");
		}else if(TIMETYPE.equals("4")){//上季度
			return Dao.selectOne(baseSpace+"queryQuarterLast");
		}else if(TIMETYPE.equals("5")){//本月
			return Dao.selectOne(baseSpace+"queryMonth");
		}else if(TIMETYPE.equals("6")){//上月
			return Dao.selectOne(baseSpace+"queryMonthLast");
		}else{//本年
			return Dao.selectOne(baseSpace+"queryYear");
		}
		
	}
	
	public int queryDateName(Map<String, Object> param) {
		return Dao.selectInt(baseSpace+"queryDateName", param);
	}
	
	public int queryDateNameMap(Map<String, Object> param){
		return Dao.selectInt(baseSpace+"queryDateNameMap", param);
	}
	
	public int addTargetsInfo(Map<String, Object> param) {
		return Dao.insert(baseSpace+"addTargetsInfo", param);
	}
	
	public int deleteDetail(Map<String, Object> param) {
		return Dao.delete(baseSpace+"deleteDetail", param);
	}
	
	public int updateTargetsInfo(Map<String, Object> param) {
		return Dao.update(baseSpace+"updateTargetsInfo", param);
	}
	
	public Map queryCharBase(Map<String, Object> param) {
		return Dao.selectOne(baseSpace+"queryCharBase", param);
	}
	
	public int updateBaseTimeType(Map<String, Object> param) {
		//先判断有没有，如果没有新增
		Map baseMap=Dao.selectOne(baseSpace+"queryCharBase",param);
		if(baseMap ==null){
			return Dao.insert(baseSpace+"insertBaseTimeType", param);
		}
		else{
			return Dao.update(baseSpace+"updateBaseTimeType", param);
		}
		
	}
	
	public Map queryBaseInfoIsF(Map<String, Object> param){
		return Dao.selectOne(baseSpace+"queryCharBase",param);
	}
	
	public Map queryBaseTimeTypeInfoIsF(Map<String, Object> param){
		return Dao.selectOne(baseSpace+"queryBaseTimeTypeInfoIsF",param);
	}
	
	
	public Page queryPage(Map<String, Object> m) {
		String INDICATORS_TYPE=m.get("INDICATORS_TYPE")+"";
		if(INDICATORS_TYPE.equals("1")){
			return PageUtil.getPage(m, baseSpace+"showProfitDate", baseSpace+"showProfitDate_count");
		}else if(INDICATORS_TYPE.equals("2")){
			return PageUtil.getPage(m, baseSpace+"showDunRateDate", baseSpace+"showDunRateDate_count");
		}else if(INDICATORS_TYPE.equals("3")){
			return PageUtil.getPage(m, baseSpace+"showYieldRateDate", baseSpace+"showYieldRateDate_count");
		}
		return PageUtil.getPage(m, baseSpace+"showProfitDate", baseSpace+"showProfitDate_count");
	}
}
