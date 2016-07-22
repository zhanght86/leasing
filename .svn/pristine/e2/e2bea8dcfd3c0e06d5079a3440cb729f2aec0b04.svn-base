package com.pqsoft.documentApp.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class APPDossierConfigService {
	private String namespage = "appDossierConfig.";

	/**
	 * 查询档案柜编号规则
	 * 
	 * @param map
	 * @return
	 * @author:yangxue 2015-07-10
	 */
	public Page doShowDossierConfigForPage(Map<String, Object> map) {
		return PageUtil.getPage(map, namespage + "doShowDossierConfigForPage", namespage + "doShowDossierConfigForPageCount");
	}

	/**
	 * 插叙档案柜编号列表
	 * 
	 * @param map
	 * @return
	 * @author:yangxue 2015-07-10
	 */
	public List<Map<String, Object>> doShowDossierConfigList(Map<String, Object> map) {
		return Dao.selectList(namespage + "doShowDossierConfigForPage", map);
	}

	/**
	 * 查询档案柜数量
	 * 
	 * @param map
	 * @return
	 * @author:yangxue 2015-07-10
	 */
	public int doShowDossierConfigCount(Map<String, Object> map) {
		return Dao.selectInt(namespage + "doShowDossierConfigForPageCount", map);
	}
	/**
	 * 添加保存档案编码规则
	 * @param map
	 * @return
	 * @author:yangxue 2015-07-10
	 */
	public int doAddDossierConfig(Map<String,Object> map){
		return Dao.insert(namespage+"doAddDossierConfig", map);
	}
	/**
	 * 修改档案编码规则保存
	 * @param map
	 * @return
	 * @author:yangxue 2015-07-10
	 */
	public int doUpdateDossierConfig(Map<String,Object> map){
		return Dao.insert(namespage+"doUpdateDossierConfig", map);
	}
	
	/**
	 * 删除档案柜编码规则
	 * @param map
	 * @return
	 * @author:yangxue 2015-07-10
	 */
	public int doDelDossierConfig(Map<String,Object> map){
		return Dao.delete(namespage+"doDelDossierConfig", map);
	}
}
