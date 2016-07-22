package com.pqsoft.margin.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

/** 
 * 经销商入网保证金
 * @author 郑商城
 * @date 2015-08-27
 */
public class MarginService {
	
	/**
	 * 获取最高入网保证金金额
	 * @return
	 */
	public List<Map<String, Object>> getMaxMargin(){
		return Dao.selectList("margin.getMaxMargin");
	}

	/**
	 * 入网保证金列表
	 * @param map
	 * @return
	 */
	public Page showMargin(Map<String,Object> map){
		
		Page page = new Page(map) ;
		List<Map<String, Object>> list = Dao.selectList("margin.showMarginPage", map);
		JSONArray array = JSONArray.fromObject(list);
		int count = Dao.selectInt("margin.showMarginCount", map);
		page.addDate(array, count);
		return page;
	}
	
	/**
	 * 入网保证金明细
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> showMarginDetail(Map<String, Object> map){
		
		return Dao.selectList("margin.showMarginDetail", map);
	}
	
	/**
	 * 入网保证金数据插入
	 * @param map
	 * @return
	 */
	public int insertMargin(Map<String, Object> map){
		return Dao.insert("margin.insertMargin", map);
	}
	
	/**
	 * 入网保证金明细数据插入
	 * @param map
	 * @return
	 */
	public int insertMarginDetail(Map<String, Object> map){
		return Dao.insert("margin.insertMarginDetail", map);
	}
	
	/**
	 * 查询一条经销商入网保证金数据
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> showOneMargin(Map<String, Object> map){
		return Dao.selectList("margin.showOneMargin", map);
	}
	
	/**
	 * 修改经销商入网保证金
	 * @param map
	 * @return
	 */
	public int updateMargin(Map<String, Object> map){
		return Dao.update("margin.updateMargin", map);
	}
	
	/**
	 * 修改经销商入网保证金明细数据
	 * @param map
	 * @return
	 */
	public int updateMarginDetail(Map<String, Object> map){
		return Dao.update("margin.updateMarginDetail", map);
	}
	
	/**
	 * 保证金退款
	 * @param map
	 * @return
	 */
	public int updateMarginTK(Map<String, Object> map){
		return Dao.update("margin.updateMarginTK", map);
	}
}
