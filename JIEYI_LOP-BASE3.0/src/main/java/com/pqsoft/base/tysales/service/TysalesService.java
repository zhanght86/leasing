package com.pqsoft.base.tysales.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class TysalesService {
	
	private String basePath = "Tysales.";
	
	/**
	 * 三年的销售情况列表
	 * @param param
	 * @return
	 * @author taoym
	 * @datetime 2015年8月31日,下午8:40:40
	 */
	public Page getTysalesDetail(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"findTysalesDetail",param)); 
		page.addDate(array, Dao.selectInt(basePath+"findTysalesCount", param));
		return page;
	}
	
	/**
	 * 添加三年的销售情况
	 * @param map
	 * @return
	 * @author taoym
	 * @datetime 2015年8月31日,下午9:40:13
	 */
	public int doInsertTysales(Map<String,Object> map){
		return Dao.insert(basePath+"doInsertTysales", map);
	}
	
	
	/**
	 * 查找一条三年的销售情况
	 * @param param
	 * @return
	 * @author taoym
	 * @datetime 2015年8月31日,下午10:55:56
	 */
	public Map<String,Object> getTysales(Map<String,Object> param){
		return Dao.selectOne(basePath+"getTysales",param);
	}
	
	/**
	 * 修改三年的销售情况
	 * @param param
	 * @return
	 * @author taoym
	 * @datetime 2015年8月31日,下午9:38:55
	 */
	public int doUpdateTysales(Map<String,Object> param){
		return Dao.update(basePath+"doUpdateTysales",param);
	}
	
	/**
	 * 删除三年的销售情况
	 * @param param
	 * @return
	 * @author taoym
	 * @datetime 2015年8月31日,下午10:22:43
	 */
	public int delTysales(Map<String,Object> param){
		return Dao.update(basePath+"delTysales",param);
	}

}
