package com.pqsoft.base.interfaceTemplate.service;

import java.util.List;
import java.util.Map;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class InterfaceTemplateSqlService {
	
	String xmlPath = "interfaceTemplate.";//xml路径
	
	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, xmlPath+"getPageList", xmlPath+"getPageCount");
	}
	
	public boolean doAddSql(Map<String, Object> param) {
		String ID = Dao.getSequence("SEQ_INTERFACE_SQL");
		param.put("ID", ID);
		return Dao.insert(xmlPath + "doAddSql", param) > 0 ? true : false;
	}
	
	public boolean deleteSql(Map<String, Object> param){
		return Dao.delete( xmlPath + "deleteSql", param) >= 0 ? true : false;
	}
	
	public boolean checkName(Map<String, Object> param){
		List<Map<String,Object>> listMap = Dao.selectList(xmlPath + "checkName", param);
		if(listMap == null || listMap.size() == 0){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean doUpdateSql(Map<String, Object> param){
		return Dao.update( xmlPath + "doUpdateSql", param) >= 0 ? true : false;
	}
	
	/**
	 * 获得SQL配置的返回值 
	 * 返回值类型为字符串 String
	 * @param NAME SQL配置名称
	 * @param ID SQL 中所需参数
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2015-3-4  上午11:31:48
	 */
	public String getSqlValue(String NAME,String ID){
		String SQL = SqlConfigMemcached.getSQL(NAME);
		SQL = SQL.replace("#{ID}",ID).replace("#{id}",ID);
		return Dao.selectOne(xmlPath + "getSqlValueString",SQL);
	}
	
	/**
	 * 获得SQL配置的返回值  
	 * 返回值类型为列表 List
	 * @param NAME SQL配置名称
	 * @param ID SQL 中所需参数
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2015-3-4  上午11:31:48
	 */
	public List<Object> getSqlValues(String NAME,String ID){
		String SQL = SqlConfigMemcached.getSQL(NAME);
		SQL = SQL.replace("#{ID}",ID).replace("#{id}",ID);
		return Dao.selectList(xmlPath + "getSqlValueList",SQL);
	}
	
}
