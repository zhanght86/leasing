package com.pqsoft.mortgage.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class MortgageService {

	private final String xmlPath="mortgage.";
	
	public Object toFindCompany(){
		return Dao.selectOne(xmlPath+"toFindCompany");
	}
	
	/**
	 * 分页显示抵押数据
	 * @author: yx 
	 * @date: 2015-3-4
	 * @return_type:Page
	 * @param map
	 * @return
	 */
	public Page toMgMortgage(Map<String,Object> map){
		return PageUtil.getPage(map, xmlPath+"toMgMortgage", xmlPath+"toMgMortgageC");
	}
	
	/**
	 * 待抵押管理页面数据
	 * @author: yx 
	 * @date: 2015-5-6
	 * @return_type:Page
	 * @param map
	 * @return
	 */
	public Page toMgMortgageDDY(Map<String,Object> map){
		return PageUtil.getPage(map, xmlPath+"toMgMortgageDDY", xmlPath+"toMgMortgageDDYC");
	}
	
	/**
	 * 解抵押管理页面数据
	 * @author: yx 
	 * @date: 2015-5-6
	 * @return_type:Page
	 * @param map
	 * @return
	 */
	public Page toMgMortgageJY(Map<String,Object> map){
		return PageUtil.getPage(map, xmlPath+"toMgMortgageJY", xmlPath+"toMgMortgageJYC");
	}
	
	/**
	 * 付款流程中调用， 选择抵押设备数据
	 * @author: yx 
	 * @date: 2015-3-4
	 * @return_type:Object
	 * @param map
	 * @return
	 */
	public Object toMgMortgageEqu(Map<String,Object> map){
		return Dao.selectList(xmlPath+"toMgMortgageEqu", map);
	}
	
	/**
	 * 查看抵押数据
	 * @author: yx 
	 * @date: 2015-3-4
	 * @return_type:Object
	 * @param map
	 * @return
	 */
	public Object toViewMortgage(Map<String,Object> map){
		return Dao.selectOne(xmlPath+"toViewMortgage", map);
	}
	
	/**
	 * 添加抵押数据
	 * @author: yx 
	 * @date: 2015-3-4
	 * @return_type:int
	 * @param map
	 * @return
	 */
	public int doAddMortgage(Map<String,Object> map){
		return Dao.insert(xmlPath+"doAddMortgage", map);
	}
	
	/**
	 * 修改抵押数据
	 * @author: yx 
	 * @date: 2015-3-4
	 * @return_type:int
	 * @param map
	 * @return
	 */
	public int doUpMortgage(Map<String,Object> map){
		return Dao.update(xmlPath+"doUpMortgage", map);
	}
}
