package com.pqsoft.bailoutCondition.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;

public class BailoutConditionService {
	private final String xmlPath="bailoutCondition.";
	
	/**
	 * 分页查询融资条件方式管理数据
	 * toMgBailout
	 * @date 2013-10-15 上午11:16:01
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Page toMgBailout(Map<String,Object> map){
		map.put("TYPE_", "再融资方式");
		return PageUtil.getPage(map, xmlPath+"toMgBailout", xmlPath+"toMgBailoutCount");
	}
	
	/**
	 * 获取条件信息
	 * querCondition
	 * @date 2013-10-15 下午03:01:31
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public List<Map<String,Object>> querCondition(Map<String,Object> map){
		map.put("ORG_ID", Security.getUser().getOrg().getId());
		return Dao.selectList(xmlPath+"querCondition", map);
	}
	
	/**
	 * 获取融资机构
	 * querFhfa
	 * @date 2013-10-15 下午03:01:07
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public List<Map<String,Object>> querFhfa(Map<String,Object> map){
		return Dao.selectList(xmlPath+"querFhfa", map);
	}
	
	/**
	 * 获取融资方式
	 * querBalloutWayService
	 * @date 2013-10-15 下午03:18:28
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public List<Map<String,Object>> querBalloutWayService(Map<String,Object> map){
		map.put("TSDDTYPE", "再融资方式");
		return Dao.selectList(xmlPath+"querBalloutWay", map);
		
	}
	
	
	/**
	 * 查询所有符合条件信息
	 * getAllConditionService
	 * @date 2013-10-15 上午11:17:08
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object getAllConditionService(){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("ORG_ID", Security.getUser().getOrg().getId());
		return Dao.selectList("condition.selectAllCondition",map);
	}
	
	public Object querBailoutCondition(String id){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("TRBCID", id);
		return Dao.selectOne(xmlPath+"querBailoutCondition", map);
	}
	
	/**
	 * 插入融资条件方式
	 * doInsetBailoutCondition
	 * @date 2013-10-15 下午12:26:34
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doInsetBailoutCondition(Map<String,Object> map){
		List<Map<String,Object>> list = Dao.selectList(xmlPath+"selquerBailoutCondition",map);
		if(list.size()>0){
			return 0;
		}else{//bailoutcondition
			return Dao.insert(xmlPath+"INSERT_BAILOUT_CONDITION", map);
		}
	}
	
	/**
	 * 修改融资条件方式
	 * doReviiseBailoutConditionService
	 * @date 2013-10-15 下午01:27:27
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doReviseBailoutCondition(Map<String,Object> map){
		System.out.println("======================uuuu"+map);
		return Dao.update(xmlPath+"UPDATE_BAILOUT_CONDITION", map);
		
	}
}
