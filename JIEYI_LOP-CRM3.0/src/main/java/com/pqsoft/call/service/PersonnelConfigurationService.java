package com.pqsoft.call.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class PersonnelConfigurationService {
	
	String xmlPath = "callPersonnelConfiguration.";//xml路径
	
	/**
	 * 管理页列表分页
	 * @param
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-10-8  上午11:15:23
	 */
	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, xmlPath+"getPageList", xmlPath+"getPageCount");
	}
	
	/**
	 * 添加人员配置
	 * @param
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-10-8  上午11:15:23
	 */
	public boolean addPersonnelConfiguration(Map<String, Object> param) {
		return Dao.insert(xmlPath+"addPersonnelConfiguration", param) > 0;
	}
	
	/**
	 * 删除人员配置
	 * @param
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-10-8  上午11:15:23
	 */
	public boolean deletePersonnelConfiguration(Map<String, Object> param) {
		return Dao.delete(xmlPath+"deletePersonnelConfiguration", param) > 0;
	}
	
	/**
	 * 修改人员配置
	 * @param
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-10-8  上午11:15:23
	 */
	public boolean updatePersonnelConfiguration(Map<String, Object> param) {
		return Dao.update(xmlPath+"updatePersonnelConfiguration", param) > 0;
	}
	
	/**
	 * 人员配置详情
	 * @param
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-10-8  上午11:15:23
	 */
	public Map<String, Object> selectPersonnelConfigurationDetails(Map<String, Object> param) {
		return Dao.selectOne(xmlPath+"selectPersonnelConfigurationDetails", param);
	}

}
