package com.pqsoft.anchored.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;

/**
 * 
 * @author King 2013-11-10
 */
public class AnchoredManagerService {
	private String namespace = "anchored.";

	/**
	 * 查询挂靠公司列表
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-11-10
	 */
	public Page doShowAnchoredList(Map<String, Object> map) {
		return PageUtil.getPage(map, namespace + "showAnchoredMg", namespace + "showAnchoredMgCount");
	}

	/**
	 * 添加挂靠公司
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-11-10
	 */
	public int doAddAnchored(Map<String, Object> map) {
		return Dao.insert(namespace + "addAnchored", map);
	}

	/**
	 * 修改挂靠公司信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-11-10
	 */
	public int doUpdateAnchored(Map<String, Object> map) {
		return Dao.insert(namespace + "updateAnchored", map);
	}

	/**
	 * 删除挂靠公司信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-11-10
	 */
	public int doDeleteAncored(Map<String, Object> map) {
		return Dao.delete(namespace + "deleteAnchored", map);
	}
	
	/**
	 * 查询所有的挂靠公司信息
	 * @return
	 * @author:King 2013-11-10
	 */
	public List<Map<String,Object>> doShowAnchoredCompanyList(){
//		Map<String,Object> map=BaseUtil.getSup();
//		if(StringUtils.isBlank(map)){
//			map=new HashMap<String,Object>();
//		}
		return Dao.selectList(namespace+"doShowAnchoredCompanyList",null);
	}
	
	/**
	 * 判断挂靠公司是否存在
	 * 
	 * @param NAME
	 * @return
	 * @author:King 2013-11-10
	 */
	public int doShowAnchoredByName(String NAME,String SUP_ID) {
		if (StringUtils.isBlank(NAME)) {
			return 0;
		} else{
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("SUP_ID", SUP_ID);
			map.put("NAME", NAME);
			return Dao.selectInt(namespace + "doShowAnchoredByName", map);
		}
	}
	
	/**
	 * 校验挂靠公司是否被使用
	 * @param ID
	 * @return
	 * @author:King 2013-12-11
	 */
	public int isDelAnchoredCheck(String ID){
		return Dao.selectInt(namespace+"isDelAnchoredCheck", ID);
	}
	
	
	public Page doShowFLList(Map<String, Object> map) {
		return PageUtil.getPage(map, namespace + "showFLMg", namespace + "showFLCount");
	}
	
	
	/**
	 * 判断融资公司是否存在
	 * 
	 * @param NAME
	 * @return
	 * @author:King 2013-11-10
	 */
	public int doShowPLByName(String NAME) {
		if (StringUtils.isBlank(NAME)) {
			return 0;
		} else{
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("NAME", NAME);
			return Dao.selectInt(namespace + "doShowPLByName", map);
		}
	}
	
	public int doAddPL(Map<String, Object> map) {
		return Dao.insert(namespace + "addPL", map);
	}
	
	/**
	 * 修改融资公司信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-11-10
	 */
	public int doUpdatePL(Map<String, Object> map) {
		return Dao.insert(namespace + "updatePL", map);
	}
	
	/**
	 * 校验融资公司是否被使用
	 * @param ID
	 * @return
	 * @author:King 2013-12-11
	 */
	public int isDelPLCheck(String ID){
		return Dao.selectInt(namespace+"isDelFLCheck", ID);
	}
	
	/**
	 * 删除融资公司信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-11-10
	 */
	public int doDeletePL(Map<String, Object> map) {
		return Dao.delete(namespace + "deletePL", map);
	}
}
