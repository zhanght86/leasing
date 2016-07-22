package com.pqsoft.base.policyPublish.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

/**
 *   政策发布
 *   @author 韩晓龙
 */

public class PolicyPublishService{
	private String basePath = "PolicyPublish.";
	
	public PolicyPublishService() {
	}
	
	//得到分页数据
	public Page getPageData(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath + "queryAll",param));
		page.addDate(array, Dao.selectInt(basePath + "queryCount", param));
		return page;
	}
	
	//更新政策
	public int updatePolicy(Map<String,Object> param){
		return Dao.update(basePath + "updatePolicy", param);
	}
	
	//插入新的政策
	public int insertPolicy(Map<String,Object> param){
		return Dao.insert(basePath+"addPolicy",param);
	}
	
	//删除选中的政策
	public int deletePolicy(Map<String,Object> param){
		return Dao.delete(basePath+"deletePolicy",param);
	}
	
	/**
	 * 删除选中政策的附件
	 */
	public int deletePolicyFile(Map<String,Object> param){
		return Dao.delete(basePath+"deletePolicyFile",param);
	}
	
	/**
	 * 删除中间表
	 */
	public int deletePolicyMidTab(Map<String,Object> param){
		return Dao.delete(basePath+"deletePolicyMidTab",param);
	}
	/**
	 * 上传附件
	 */
	public int insertPolicyFile(Map<String,Object> param){
		return Dao.insert(basePath+"insertPolicyFile",param);
	}
	/**
	 * 选择可视用户组
	 */
	public int insertMidPolicyTable(Map<String,Object> param){
		return Dao.insert(basePath+"insertMidPolicyTable",param);
	}
	
	/**删除可视用户组
	 */
	public int deleteMidPolicyTable(Map<String,Object> param){
		return Dao.delete(basePath+"deleteMidPolicyTable",param);
	}
	
	/**查询可视用户组
	 */
	public List selectMidPolicyTable(Map<String,Object> param){
		return Dao.selectList(basePath+"selectMidPolicyTable",param);
	}
	
	/**查询一个政策下的所有附件
	 */
	public List selectPolicyFile(Map<String,Object> param){
		return Dao.selectList(basePath+"selectPolicyFile",param);
	}
	
	/**根据id查询一个附件
	 */
	public Object selectPolicyFileById(Map<String,Object> param){
		return Dao.selectOne(basePath+"selectPolicyFileById",param);
	}
	
	/**根据id删除一个附件
	 */
	public int deletePolicyFileById(Map<String,Object> param){
		return Dao.delete(basePath+"deletePolicyFileById",param);
	}

	/**
	 *  得到所有子节点
	 */
	public List getAllOrg(Map<String,Object> param) {
		return Dao.selectList(basePath+"getAllOrg",param);
	}
	
	/**
	 * 根据组织架构ID获取子节点信息 <br>
	 * I_ORGPARENTID 组织架构ID <br>
	 * 
	 */
	public List<Map<String, Object>> getOrganizations(String ORG_PARENT_ID,String POLICY_ID) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ORG_PARENT_ID", ORG_PARENT_ID);
		map.put("POLICY_ID", POLICY_ID);
		return Dao.selectList(basePath + "getOrganizations", map);
	}
	/**
	 *  根节点
	 */
	public List<Map<String, Object>> getOrganizationsroot(String ORG_PARENT_ID,String POLICY_ID) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ORG_PARENT_ID", ORG_PARENT_ID);
		map.put("POLICY_ID", POLICY_ID);
		return Dao.selectList(basePath + "getOrganizationsroot", map);
	}
}
