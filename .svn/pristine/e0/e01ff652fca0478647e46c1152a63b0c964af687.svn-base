package com.pqsoft.zcfl.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class TitleStandardService {
	
	String xmlPath = "zcfl.titleStandard.";//xml路径
	
	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, xmlPath+"getPageList", xmlPath+"getPageCount");
	}
	
	public boolean doAddTS(Map<String, Object> param) {
		String ID = Dao.getSequence("SEQ_ZCFL_CLASSIFY_TP");
		param.put("ID", ID);
		return Dao.delete(xmlPath + "addTS", param) > 0 ? true : false;
	}
	
	public Map<String,Object> getTSData(Map<String, Object> param) {
		return Dao.selectOne(xmlPath + "getTSData", param);
	}
	
	public Map<String,Object> getTSDataFroShow(Map<String, Object> param) {
		return Dao.selectOne(xmlPath + "getTSDataFroShow", param);
	}
	
	public boolean doUpdateTS(Map<String, Object> param) {
		return Dao.delete(xmlPath + "updateTS", param) > 0 ? true : false;
	}
	
	public List<Map<String,Object>> getAllT(Map<String, Object> param){
		List<Map<String,Object>> listT =  Dao.selectList(xmlPath + "getAllT", param);
		if(listT != null && listT.size() > 0){
			for (int i = 0; i < listT.size(); i++) {
				Map<String, Object> mapT = listT.get(i);
				mapT.put("listXX", Dao.selectList(xmlPath + "getXX", mapT));		
			}
		}
		return listT;
	}
	
	public List<Map<String,Object>> getAllDs(){
		return Dao.selectList(xmlPath + "getAllDs");
	}
	
	@SuppressWarnings("unchecked")
	public boolean doSaveT(JSONObject tParam) {
		try{
			String  TS_ID = tParam.get("TS_ID").toString();//题目模版ID
			{//删除题目模版下的所有题目和题目选项
				List<Map<String,Object>> tListD = Dao.selectList( xmlPath + "selectTlist", TS_ID);
				for (Map<String, Object> tMapD : tListD) {
					Dao.delete( xmlPath + "deleteXx", tMapD);
				}
				Dao.delete( xmlPath + "deleteT", TS_ID);
			}
			{
				List<Map<String, Object>> tList = tParam.getJSONArray("DATA_LIST");//所有题目
				for(Map<String, Object> tMap : tList){//循环所有题目
					String T_ID = Dao.getSequence("SEQ_ZCFL_EVALUATE_SUBJECT");//题目ID
					tMap.put("ID", T_ID);
					tMap.put("TS_ID", TS_ID);
					Dao.insert(xmlPath + "addT", tMap);//添加题目
					List<Map<String,Object>> xxList = (List<Map<String, Object>>) tMap.get("LIST");//题目下的选项
					for (int i = 0; i < xxList.size(); i++) {
						Map<String,Object> xxMap = xxList.get(i);
						xxMap.put("T_ID", T_ID);
						Dao.insert( xmlPath + "addXx", xxMap);//添加题目下的选项
					}
				}
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			Dao.rollback();
			return false;
		}
    }
	
	public List<Map<String,Object>> getAllC(Map<String, Object> param){
		List<Map<String,Object>> listC =  Dao.selectList(xmlPath + "getAllC", param);
		if(listC != null && listC.size() > 0){
			for (int i = 0; i < listC.size(); i++) {
				Map<String, Object> mapC = listC.get(i);
				mapC.put("listCSJBFW", Dao.selectList(xmlPath + "getCSJBFW", mapC));
			}
		}
		return listC;
	}
	
	@SuppressWarnings("unchecked")
	public boolean doSaveC(JSONObject cParam) {
		try{
			String  TS_ID = cParam.get("TS_ID").toString();//题目模版ID
			{//删除题目模版下的所有标准和初始级别范围&调整规则
				List<Map<String,Object>> cListD = Dao.selectList( xmlPath + "selectClist", TS_ID);
				for (Map<String, Object> cMapD : cListD) {
					Dao.delete( xmlPath + "deleteCSJBFW", cMapD);
				}
				Dao.delete( xmlPath + "deleteC", TS_ID);
			}
			{
				List<Map<String, Object>> cList = cParam.getJSONArray("DATA_LIST");//所有标准项
				for(Map<String, Object> cMap : cList){//循环所有标准
					String C_ID = Dao.getSequence("SEQ_ZCFL_CLASSIFY_TP_RE");//标准ID
					cMap.put("ID", C_ID);
					cMap.put("TS_ID", TS_ID);
					Dao.insert(xmlPath + "addC", cMap);//添加标准
					List<Map<String,Object>> csjbfwList = (List<Map<String, Object>>) cMap.get("LIST");//标准下初始级别范围&调整规则
					for (int i = 0; i < csjbfwList.size(); i++) {
						Map<String,Object> csjbfwMap = csjbfwList.get(i);
						csjbfwMap.put("C_ID", C_ID);
						Dao.insert( xmlPath + "addCSJBFW", csjbfwMap);//添加标准下初始级别范围&调整规则
					}
				}
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			Dao.rollback();
			return false;
		}
    }
	
	public boolean doDeleteTC(Map<String, Object> param){
		try {
			String TS_ID = param.get("TS_ID").toString();//题目标准模版ID
			//删除题目
			List<Map<String,Object>> tListD = Dao.selectList( xmlPath + "selectTlist", TS_ID);
			for (Map<String, Object> tMapD : tListD) {
				Dao.delete( xmlPath + "deleteXx", tMapD);
			}
			Dao.delete( xmlPath + "deleteT", TS_ID);
			//删除标准
			List<Map<String,Object>> cListD = Dao.selectList( xmlPath + "selectClist", TS_ID);
			for (Map<String, Object> cMapD : cListD) {
				Dao.delete( xmlPath + "deleteCSJBFW", cMapD);
			}
			Dao.delete( xmlPath + "deleteC", TS_ID);
			//删除题目标准模版
			Dao.delete( xmlPath + "deleteTS", TS_ID);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
