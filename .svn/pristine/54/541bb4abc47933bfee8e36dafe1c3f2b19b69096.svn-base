package com.pqsoft.base.sp.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class SpMainRelationService {
	private String basePath = "SpMainRelation.";
	
	/**
	 * 从业经历列表
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年3月1日,下午3:40:40
	 */
	public Page getWorkExpDetail(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"findWorkExpDetail",param)); 
		page.addDate(array, Dao.selectInt(basePath+"findWorkExpCount", param));
		return page;
	}
	
	/**
	 * 添加从业经历
	 * @param map
	 * @return
	 * @author wanghl
	 * @datetime 2015年3月1日,下午3:40:13
	 */
	public int doInsertWoekExp(Map<String,Object> map){
		return Dao.insert(basePath+"doInsertWoekExp", map);
	}
	
	/**
	 * 查找一条就业经历
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年3月1日,下午8:55:56
	 */
	public Map<String,Object> getExperience(Map<String,Object> param){
		return Dao.selectOne(basePath+"getExperience",param);
	}
	
	/**
	 * 修改就业经历
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年3月1日,下午9:38:55
	 */
	public int doUpdateExperence(Map<String,Object> param){
		return Dao.update(basePath+"doUpdateExperence",param);
	}
	
	/**
	 * 删除从业经历
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年3月1日,下午10:22:43
	 */
	public int delWoekExp(Map<String,Object> param){
		return Dao.update(basePath+"delWoekExp",param);
	}
}
