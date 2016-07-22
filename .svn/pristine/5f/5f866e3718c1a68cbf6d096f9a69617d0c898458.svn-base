package com.pqsoft.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

/**
 * 数据字典工具类
 * @author King 2014-3-28
 */
public class DataDictionaryUtil {
	
	/**
	 * 获取数据字典 某个code对应的中文
	 * @param TYPE
	 * @param CODE
	 * @return
	 * @author:King 2014-3-28
	 */
	public Object getFLAG(String TYPE,Object CODE){
		Map<String,Object> map=new HashMap<String,Object>();
		if("总保证金比例".equals(TYPE)){
			System.out.println("");
		}
		if(StringUtils.isBlank(TYPE))
			return "无参数";
		if(StringUtils.isBlank(CODE))
			return CODE;
		map.put("_TYPE", TYPE);
		map.put("_CODE", CODE.toString());
		Object obj=Dao.selectOne("DateDictionary.getFLAG", map);
		if(StringUtils.isBlank(obj))
			return CODE;
		return obj;
	}
	
	/**
	 * 获取数据字典 某个code对应的中文
	 * @param TYPE
	 * @param CODE
	 * @return
	 * @author:King 2014-3-28
	 */
	public Object getFLAGProportion(String TYPE,String CODE,String MONEYCF1,String MONEYTOTAL1){
		Map<String,Object> map=new HashMap<String,Object>();
		if(StringUtils.isBlank(TYPE))
			return "无参数";
		if(StringUtils.isBlank(CODE)){
			return CODE;
		}
		double CODE_MONEY=Double.parseDouble(CODE);
		double MONEYCF=Double.parseDouble(MONEYCF1);
		double MONEYTOTAL=Double.parseDouble(MONEYTOTAL1);
		return (CODE_MONEY * MONEYCF / MONEYTOTAL);
	}
	
	/**
	 * 查询商务政策下拉列表 商务政策用
	 * @param TYPE
	 * @return
	 * @author:King 2014-3-28
	 */
	public List<Map<String,Object>> getCodeFLAGList(String TYPE){
		if(StringUtils.isBlank(TYPE))
			return new ArrayList<Map<String,Object>>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("_TYPE", TYPE);
		return Dao.selectList("DateDictionary.getCodeFLAGList", map);
	}
	/**
	 * 查询数据字典下拉表
	 * @param TYPE
	 * @return
	 * @author:King 2014-3-30
	 */
	public List<Map<String,Object>> getDataList(String TYPE,String SCHEME_CODE){
		if(StringUtils.isBlank(TYPE))
			return new ArrayList<Map<String,Object>>();
		if (StringUtils.isBlank(SCHEME_CODE))
			return new ArrayList<Map<String, Object>>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("_TYPE", TYPE);
		map.put("SCHEME_CODE", SCHEME_CODE);
		return Dao.selectList("DateDictionary.getDataList", map);
	}
	
	/**
	 * 获取两个数字相加
	 * @param TYPE
	 * @param CODE
	 * @return
	 * @author:King 2014-3-28
	 */
	public Double getMoneyAdd(double MONEY1,double MONEY2){
		return MONEY1+MONEY2;
	} 
	
	
	/**
	 * 获取数据字典 某个code对应的中文
	 * @param TYPE
	 * @param CODE
	 * @return
	 * @author:King 2014-3-28
	 */
	public Object getSiteFLAG(String TYPE,String CODE){
		Map<String,Object> map=new HashMap<String,Object>();
		if(StringUtils.isBlank(TYPE))
			return "无参数";
		if(StringUtils.isBlank(CODE))
			return CODE;
		map.put("_TYPE", TYPE);
		map.put("_CODE", CODE);
		Object obj=Dao.selectOne("DateDictionary.getSiteFLAG", map);
		if(StringUtils.isBlank(obj))
			return CODE;
		return obj;
	}
}
