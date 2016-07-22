package com.pqsoft.projectInvalid.service;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class Project_InvalidService {
	
	private String basePath = "projectInvalid.";
	
	public Project_InvalidService() {
	}
	
	/**
	 *  得到分页数据
	 */
	public Page getPageData(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath + "queryAll",param));
		page.addDate(array, Dao.selectInt(basePath + "queryCount", param));
		return page;
	}
	////////////////////////////////////////项目信息一览////////////////////////////////////////////
	public Map queryProjectById(Map map) {
		map.put("tags1", "业务类型");
		map.put("tags3", "客户类型");
		map.put("tags4", "业务类型");
		Map<String, Object> m = Dao.selectOne(basePath + "queryProjectById", map);
		if(m!=null){
			Clob clob = (Clob) m.get("IDCARD_PHOTO");
			if(clob!=null){
				try {
					m.put("IDCARD_PHOTO", clob.getSubString(1, (int) clob.length()));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return m;
	}
	// 查询该项目所选择的设备
	public List queryEqByProjectID(Map param) {
		param.put("tags", "设备单位");
		return Dao.selectList(basePath + "queryEqByProjectID", param);
	}
	/**
	 * 根据供应商ID查询供应商账户信息
	 */
	public List<Map<String,Object>> doShowSupAccount(String SUPPLIER_ID){
		return Dao.selectList(basePath + "doShowSupAccount", SUPPLIER_ID);
	}
	// 查询项目所有的担保人
	public List queryGuaranByProjectID(Map param) {
		param.put("tags", "客户类型");
		return Dao.selectList(basePath + "queryGuaranByProjectID", param);
	}
	// 查询该项目的方案
	public List<Map<String, Object>> querySechmeByAllDate(Map param) {
		return Dao.selectList(basePath + "querySechmeByAllDate", param);
	}
	/**
	 * 查询首期款合计，首期款付款日期及租金合计
	 */
	public Map<String, Object> doShowRentInfo(String PAY_ID) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PAY_ID", PAY_ID);
		map.put("ITEM_NAME", "租金");
		return Dao.selectOne(basePath + "doShowRentInfo", map);
	}
	/**
	 * 查询挂靠公司
	 */
	public Map<String,Object> doQueryAnchoredCompany(String T_ANCHORED_COMPANY_ID){
		return Dao.selectOne(basePath + "doQueryAnchoredCompany", T_ANCHORED_COMPANY_ID);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 *  修改项目状态
	 */
	public int updateProStatus(Map<String,Object> param){
		return Dao.update(basePath + "updateProStatus",param);
	}
}
