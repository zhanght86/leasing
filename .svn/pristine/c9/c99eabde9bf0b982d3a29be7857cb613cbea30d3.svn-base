package com.pqsoft.achangeb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;

/**
 * A转B管理
 * 
 * @author King 2013-11-3
 */
public class AtoBService {

	private String namespace = "achangeb.";

	/**
	 * 查询a转b申请列表
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-11-3
	 */
	public Page doShowMgATBApply(Map<String, Object> map) {
		map.put("PMT", "租金");
		map.put("START_MONEY", "起租租金");
		map.put("SUP_ID", BaseUtil.getSupOrgChildren());
		return PageUtil.getPage(map, namespace + "doShowAchangeBApply", namespace + "doShowAchangeBApplyCount");
	}

	/**
	 * 查询供应商下客户信息
	 * @param map
	 * @return
	 * @author:King 2013-11-3
	 */
	public List<Map<String, Object>> doShowClientInfoList(Map<String, Object> map) {
		return Dao.selectList(namespace + "doShowClientInfo", map);
	}
	
	/**
	 * 插入A-B信息
	 * @param map
	 * @return
	 * @author:King 2013-11-3
	 */
	public int doAddAtoB(Map<String,Object>map){
		String AB_ID=Dao.getSequence("SEQ_FIL_PROJECT_ATOB");
		map.put("AB_ID", AB_ID);
		return Dao.insert(namespace+"doAddAtoB", map);
	}
	/**
	 * 修改A-B信息
	 * @param map
	 * @return
	 * @author:King 2013-11-3
	 */
	public int doUpdateAtoB(Map<String,Object>map){
		return Dao.insert(namespace+"doUpdateAtoB", map);
	}
	
	/**
	 * 已经做过的A-B项目查询列表
	 * @param map
	 * @return
	 * @author:King 2013-11-8
	 */
	public Page doShowAChangeB(Map<String,Object> map){
		map.put("PMT", "租金");
		map.put("START_MONEY", "起租租金");
		map.put("BZJ", "保证金");
		map.put("BXF", "保险费");
		map.put("DBF", "担保费");
		map.put("SXF", "手续费");
		map.put("LGJ", "留购价");
		map.put("SUP_ID", BaseUtil.getSupOrgChildren());
		return PageUtil.getPage(map, namespace+"doShowAChangeB", namespace+"doShowAChangeBCount");
	}
	
	/**
	 * 查询ATOB表中的B客户信息
	 * @param PROJECT_ID
	 * @return
	 * @author:King 2013-12-14
	 */
	public Map<String,Object> queryBclientInfo(String PROJECT_ID,String STATUS){
		Map<String,Object> map=new HashMap<String,Object>();
		if(StringUtils.isBlank(STATUS)){
			map.put("STATUS", "2");
		}else{
			map.put("STATUS", STATUS);
		}
		map.put("PROJECT_ID", PROJECT_ID);
		return Dao.selectOne(namespace+"queryBclientInfo", map);
	}
	
	/**
	 * 查询ABid
	 * @param PROJECT_ID
	 * @return
	 * @author:King 2013-12-14
	 */
	public String queryAB_ID(String PROJECT_ID){
		return Dao.selectOne(namespace+"queryAB_ID", PROJECT_ID);
	}
	
	/**
	 * 更新ab流程状态
	 * @param AB_ID
	 * @author:King 2013-12-14
	 */
	public void doUpdateABStatus(String AB_ID){
		Dao.update(namespace+"doUpdateABStatus", AB_ID);
	}
}
