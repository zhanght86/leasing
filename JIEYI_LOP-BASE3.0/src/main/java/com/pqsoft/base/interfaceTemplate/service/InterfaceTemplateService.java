package com.pqsoft.base.interfaceTemplate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.JsonUtil;
import com.pqsoft.util.PageUtil;

public class InterfaceTemplateService {

	String xmlPath = "interfaceTemplate.";//xml路径
	
	public Page getPageJd(Map<String, Object> param) {
		return PageUtil.getPage(param, xmlPath+"getPageJdList", xmlPath+"getPageJdCount");
	}
	
	public Page getPageDs(Map<String, Object> param) {
		return PageUtil.getPage(param, xmlPath+"getPageDsList", xmlPath+"getPageDsCount");
	}
	
	public List<Object> getDx(){
		return Dao.selectList(xmlPath+"getDx");
	}
	
	public List<Object> getJavaConfig(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("TYPE", "JAVA节点流程配置");
		map.put("FLAG_INTNA", "com.pqsoft.base.interfaceTemplate.service.RunInterfaceTemplate");
		map.put("SHORTNAME", "sendJbpmMessage");
		return Dao.selectList(xmlPath+"getJavaConfig",map);
	}
	
	public List<Map<String,Object>> getNodeNameList(Map<String,Object> param){
		param.put("TYPE", "JAVA节点流程配置");
		param.put("FLAG_INTNA", "com.pqsoft.base.interfaceTemplate.service.RunInterfaceTemplate");
		param.put("SHORTNAME", "sendJbpmMessage");
		return Dao.selectList(xmlPath+"getNodeNameList",param);
	}
	
	public List<Map<String,Object>> getJdUpdateData(Map<String,Object> param){
		return Dao.selectList(xmlPath+"getJdUpdateData",param);
	}
	
	public boolean deleteJd(Map<String, Object> param){
		return Dao.delete( xmlPath + "deleteJd", param) >= 0 ? true : false;
	}
	
	public boolean deleteDs(Map<String, Object> param){
		return Dao.delete( xmlPath + "deleteDs", param) >= 0 ? true : false;
	}
	
	public boolean doUpdateDs(Map<String, Object> param){
		if(!param.containsKey("WXQ")){
			param.put("WXQ", "2");
		}
		if(!param.containsKey("WXF")){
			param.put("WXF", "2");
		}
		if(!param.containsKey("SMS")){
			param.put("SMS", "2");
		}
		if(!param.containsKey("EMAIL")){
			param.put("EMAIL", "2");
		}
		return Dao.update( xmlPath + "doUpdateDs", param) >= 0 ? true : false;
	}
	
	public boolean checkName(Map<String, Object> param){
		List<Map<String,Object>> listMap = Dao.selectList(xmlPath + "checkNameDs", param);
		if(listMap == null || listMap.size() == 0){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean doAddDs(Map<String, Object> param) {
		String ID = Dao.getSequence("SEQ_INTERFACE_TEMPLATE");
		param.put("ID", ID);
		return Dao.insert(xmlPath + "doAddDs", param) > 0 ? true : false;
	}
	
	@SuppressWarnings("unchecked")
	public boolean doAddJd(JSONObject saveData) {
		Map<String,Object> dataMap = JsonUtil.toMap(saveData);
		try {
			List<Map<String, Object>> dxList = saveData.getJSONArray("dxList");
			if(dataMap.containsKey("OLD_NODE_NAME") && !dataMap.get("OLD_NODE_NAME").equals("")){
				Dao.delete(xmlPath + "deleteJdForUpdate", dataMap);
			}
			for (Map<String, Object> dxMap : dxList) {
				dxMap.putAll(dataMap);
				dxMap.put("ID",Dao.getSequence("SEQ_INTERFACE_TEMPLATE"));
				Dao.insert(xmlPath + "addJd", dxMap);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			Dao.rollback();
			return false;
		}
	}

}
