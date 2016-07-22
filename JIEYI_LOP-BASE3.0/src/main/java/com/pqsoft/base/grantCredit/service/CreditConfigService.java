package com.pqsoft.base.grantCredit.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class CreditConfigService {
	private String basePath = "Credit.";
	
	public Page getConfigPage(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getConfigMain",param));
		page.addDate(array, Dao.selectInt(basePath+"getConfigMainCount", param));
		return page;
	}
	
	/**
	 * 获取授信配置列表
	 * @param param
	 * @return
	 */
	public List<Object> getConfigList(Map<String,Object> param){
		return Dao.selectList(basePath+"getConfigByComid", param);
	}
	
	/**
	 * 获取单条授信配置信息
	 * @param param
	 * @return
	 */
	public Map<String,Object> getOneConfig(Map<String,Object> param) {
		return Dao.selectOne(basePath+"getOneConfig",param);
	}
	
	/**
	 * 添加授信配置信息
	 * @param param
	 * @return
	 */
	public int addConfigMess(Map<String,Object> param ){
		return Dao.insert(basePath+"addConfig", param);
	}
	
	/**
	 * 更新授信配置信息
	 * @param param
	 * @return
	 */
	public int updateConfig(Map<String,Object> param){
		return Dao.update(basePath+"updateConfig", param);
	}
	
	/**
	 * 删除配置规则信息
	 * @param param
	 * @return
	 */
	public int delOneConifg(Map<String,Object> param) {
		return Dao.delete(basePath+"delOneConfig", param);
	}
	
	/**
	 * 查询租赁物授信设置字段
	 * @return
	 */
	public List<Object> getProperty(){
		return Dao.selectList(basePath+"getConfigParamProPerty");
	}
	
	/**
	 * 查询租赁物设置字段的属性值
	 * @param param
	 * @return
	 */
	public List<Object> getProPertyValueByPty(Map<String,Object> param){
		return Dao.selectList(basePath+"getConfigProtertyValue", param);
	}
	
	/**
	 * 查询单条授信配置字段属性信息
	 * @param param
	 * @return
	 */
	public Map<String,Object> getOneConfigParam(Map<String,Object> param){
		return Dao.selectOne(basePath+"getOneConfigParam", param);
	}

	/**
	 * 检查单个厂商的授信规则是否已添加
	 * @param param
	 * @return
	 */
	public List<Object> checkConfigRepeat(Map<String,Object> param){
		return Dao.selectList(basePath+"checkConfigRepeat", param);
	}
	
	public List<Object> getProCatenaByPNamecomId(Map<String,Object> param){
		return Dao.selectList(basePath+"getProCatenaByComId", param);
	}
	
	public List<Object> getProTypeByComId(Map<String,Object> param){
		return Dao.selectList(basePath+"getProTypeByComId", param);
	}
	
	public int saveLimitRate(Map<String,Object> param){
		return Dao.update(basePath+"updateLimitRate", param);
	}
}
