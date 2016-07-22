package com.pqsoft.base.user.service;

import java.util.HashMap;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;


public class ManageService {

	private final String mapperPath = "User.";

	/**
	 * 根据用户ID获取组织架构信息
	 * 
	 * @param parameter
	 * @return
	 * @author 靳洪东
	 */
	public Object getOrgByUser(String parameter) {
		return Dao.selectList(mapperPath + "getOrgByUserId", parameter);
	}

	/**
	 * 更新组织架构
	 * 
	 * @param userId
	 * @param parameterValues
	 * @author 靳洪东
	 */
	public void updateOrg(String userId, String[] parameterValues) {
		Dao.delete(mapperPath + "removeOrgUser", userId);// 删除用户关联的所有组织架构
		if (parameterValues != null) {
			for (String orgId : parameterValues) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("USER_ID", userId);
				param.put("ORG_ID", orgId);
				Dao.insert(mapperPath + "addOrgUser", param);// 添加用户组织架构
			}
		}
	}

}
