package com.pqsoft.consider.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import com.pqsoft.skyeye.api.Dao;

public class ConsiderService {
	
	private final String path="consider.";
	/*
	 * 查询基本信息
	 */
	public Map<String,Object> selectInfoById(String PROJECT_ID){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PROJECT_ID", PROJECT_ID);
		return Dao.selectOne(path+"selectInfoById", map);
		
	}
	/*
	 * 查询基本信息
	 */
	public Map<String,Object> selectFeedBackById(String PROJECT_ID){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PROJECT_ID", PROJECT_ID);
		return Dao.selectOne(path+"selectFeedBackById", map);
	}
	/*
	 * 修改意见表信息
	 */
	public int feedBackSave(Map<String, Object> map){
		return Dao.update(path+"updateFeedBackById", map);
	}
	/*
	 * 删除意见表信息
	 */
	public int deleteFeedBack(Map<String, Object> map){
		return Dao.delete(path+"deleteFeekBackById", map);
	}
	
	public Map<String,Object> valueById(String PROJECT_ID){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PROJECT_ID", PROJECT_ID);
		return Dao.selectOne(path+"valueById", map);
	}
	/*
	 * 查询申请书其他信息
	 */
	public Map<String,Object> selectAppOtherById(String PROJECT_ID){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PROJECT_ID", PROJECT_ID);
		return Dao.selectOne(path+"selectAppOtherById", map);
	}
	/*
	 * 查询申请书信息
	 */
	public Map<String, Object> selectAppById(String PROJECT_ID){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PROJECT_ID", PROJECT_ID);
		return Dao.selectOne(path+"selectAppById", map);
	}
	/*
	 * 修改申请书信息
	 */
	public int appSave(Map<String, Object> map){
		return Dao.update(path+"updateAppById", map);
	}
	/*
	 * 删除申请书信息
	 */
	public int deleteApp(Map<String, Object> map){
		return Dao.delete(path+"deleteAppById", map);
	}

	/*
	 * 查询其他费用总和
	 */
	public Map<String, Object> selectOtherFee(String PROJECT_ID){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PROJECT_ID", PROJECT_ID);
		return Dao.selectOne(path+"selectOtherFee", map);
	}
	
	/*
	 * 查询租赁投放
	 */
	public Map<String, Object> selPlaceById(Map<String, Object> map){
		return Dao.selectOne(path+"selPlaceById", map);
	}
	/*
	 * 查询租赁投放审查审批表 
	 */
	public Map<String, Object> selectPlaceReview(Map<String, Object> map){
		return Dao.selectOne(path+"selectPlaceReview", map);
	}
	/*
	 * 增加租赁投放审查审批表 
	 */
	public int addPlaceReview(Map<String, Object> map){
		return Dao.insert(path+"addPlaceReview", map);
	}
	/*
	 * 修改租赁投放审查审批表 
	 */
	public int updatePlaceReview(Map<String, Object> map){
		return Dao.update(path+"updatePlaceReview", map);
	}
}
