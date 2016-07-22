package com.pqsoft.base.organization.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;


public class ManageService {

	private final String mapperPath = "orgazization.";

	/**
	 * 根据组织架构ID获取子节点信息 <br>
	 * I_ORGPARENTID 组织架构ID <br>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getOrganizations(String ORG_PARENT_ID) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ORG_PARENT_ID", ORG_PARENT_ID);
		return Dao.selectList(mapperPath + "getOrganizations", map);
	}

	public List<Map<String, Object>> getRegional(Map<String,Object> m)
	{
		return Dao.selectList(mapperPath + "getRegional",m);
	}
	@SuppressWarnings("unchecked")
	public Map<String, Object> getInfoById(String orgId) {
		return (Map<String, Object>) Dao.selectOne(mapperPath + "getInfoById", orgId);
	}

	/**
	 * 查询与岗位关联的角色
	 * 
	 * @param orgId
	 * @return
	 */
	public Object getHaveRole(String orgId) {
		return Dao.selectList(mapperPath + "getHaveRole", orgId);
	}

	/**
	 * 查询不与岗位关联的角色
	 * 
	 * @param orgId
	 * @return
	 */
	public Object getNotHaveRole(String orgId, String roleContent) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ORG_ID", orgId);
		param.put("roleContent", roleContent);
		return Dao.selectList(mapperPath + "getNotHaveRole", param);
	}

	public void updateOrgRole(String orgId, String roleId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ROLE_ID", roleId);
		param.put("ORG_ID", orgId);
		Dao.delete(mapperPath + "updateOrgRole", param);
	}

	public Object getHaveUser(String orgId) {
		return Dao.selectList(mapperPath + "getHaveUser", orgId);
	}

	public Object getNotHaveUser(String orgId, String userContent) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ORG_ID", orgId);
		param.put("userContent", userContent);
		return Dao.selectList(mapperPath + "getNotHaveUser", param);
	}

	public void removeOrgUser(String orgId, String userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("USER_ID", userId);
		param.put("ORG_ID", orgId);
		Dao.delete(mapperPath + "removeOrgUser", param);
	}

	public void addOrgUser(String orgId, String userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("USER_ID", userId);
		param.put("ORG_ID", orgId);
		if (Dao.selectInt(mapperPath + "getOrgUserCount", param) > 0) { return; }
		Dao.delete(mapperPath + "addOrgUser", param);
	}

	public Object Jurisdiction() {
		return Dao.insert(mapperPath + "jurisdictionInsert", null);
	}

	/**
	 * 级联删除组织架构
	 * @param id 组织架构ID
	 *
	 */
	public void delCase(String id) {
		List<?> list = Dao.selectList(mapperPath + "getOrgIdByParentId", id);
		Dao.delete(mapperPath+"delFhFA_manager",id);
		for (Object o : list) {
			delCase((String) o);
		}
		Dao.delete(mapperPath + "delUserOrgByOrgId", id);
		Dao.delete(mapperPath + "delOrgByOrgId", id);
	}
	
	//根据userid 查询对应供应商信息
	public Map<String,Object> getSupByUserId(String userId){
		return null;
	}
	
	/**
	 * 根据用户ID查询厂商数据
	 * 参考供应商
	 * {@code=com.pqsoft.base.organization.service.ManageService.getSupByUserId}
	 * @param userId
	 * @return
	 */
	public Map<String,Object> getCompanyByUserId(String userId){
		return null;
	}
	
	//根据组织org_id 对应的部门岗位 role_id 查询人员信息
	public Map<String,Object> getUserMess(String org_id,String role_id){
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("ORG_ID", org_id);
		param.put("ROLE_ID", role_id);
	    return Dao.selectOne(mapperPath+"getRoleByOrgId", param);
	}

	
	/**
	 * 获取所有子公司组织结构信息
	 * @author xingsumin 2015年12月28日14:32:10
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getSonOrganizations() {
		Map<String, Object> map = new HashMap<String, Object>();
		return Dao.selectList(mapperPath + "getSonOrganizations", map);
	}

	/**
	 * 根据用户CODE获取当前用户的上上级ORG对象
	 * @param code
	 * @return
	 */
	public Map<String, Object> getUserPPOrgByUserCode(Map<String, Object> code) {
		return Dao.selectOne(mapperPath + "getUserPPOrgByUserCode", code);
	}

	/**
	 * 根据用户CODE获取当前用户的上级ORG对象
	 * @param code
	 * @return
	 */
	public Map<String, Object> getUserParentOrgByUserCode(Map<String, Object> code) {
		return Dao.selectOne(mapperPath + "getUserParentOrgByUserCode", code);
	}
}
