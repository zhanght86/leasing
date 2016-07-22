package com.pqsoft.gls.service;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;

public class GlsService {
	private String xmlPath = "gls.";//xml路径
	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, xmlPath+"getPageList", xmlPath+"getPageCount");
	}
	public boolean doAddGls(Map<String, Object> param) {
		String ID = Dao.getSequence("SEQ_GLS");
		param.put("ID", ID);
		int i = Dao.insert(xmlPath + "addGls", param);
		//JSONObject json = JSONObject.fromObject(param.get("param"));
		//Map<String, Object> p1 = new HashMap<String, Object>();
		//p1.put("GLS_ID", json.get("ID"));
		/////param.put("GLS_ID", ID);
		//Dao.insert(xmlPath + "addGls1", param);
		
		return true;
	}
	public boolean doAddGls1(Map<String, Object> param) {
		//String ID = Dao.getSequence("SEQ_GLS");
		
		//param.put("ID", ID);
		Dao.insert(xmlPath + "addGls1", param);
		//JSONObject json = JSONObject.fromObject(param.get("param"));
		//Map<String, Object> p1 = new HashMap<String, Object>();
		//p1.put("GLS_ID", json.get("ID"));
		/////param.put("GLS_ID", ID);
		//Dao.insert(xmlPath + "addGls1", param);
		return true;
	}
	public Map<String,Object> getMgGlsData(Map<String, Object> param) {
		return Dao.selectOne(xmlPath + "getGlsData", param);
	}
	public boolean doDeleteGls(Map<String, Object> param) {
		
		Dao.delete(xmlPath + "deleteGls3", param);
		Dao.delete(xmlPath + "deleteGls", param);
		Dao.delete(xmlPath + "deleteGls1", param);
		Dao.delete(xmlPath + "deleteGls2", param);
		return true;
	}
	public boolean doUpdateGls(Map<String, Object> param) {
		
		Dao.update(xmlPath + "updateGls", param);
		//Dao.update(xmlPath + "updateGls1", param);
		return true;
	}
	public Object getGlsData(Map<String, Object> param) {
		return Dao.selectOne(xmlPath + "getGlsData", param);
	}
	public Object getGlsData11(Map<String, Object> param) {
		return Dao.selectOne(xmlPath + "getGlsData11", param);
	}
	public Page getGlsData1(Map<String, Object> param) {
		
		return PageUtil.getPage(param, xmlPath+"getGlsData1",xmlPath+"getPageCount1");
	}

	public List getSysEmp(Map<String, Object> param) {
		return Dao.selectList(xmlPath + "getSysEmp", param);
	}
	
	public boolean addCreditReports(Map<String,Object> param){
		Dao.insert(xmlPath+"addCreditReports",param);
		//Dao.insert(xmlPath+"addCreditReports1",param);
		return true;
	}
	public Map<String,Object> findFileById(Map<String,Object> param){
		return Dao.selectOne(xmlPath+"findFileById", param);
	}
	public Object doInsertPro(Map<String, Object> param) {
		String ID =Dao.getSequence("SEQ_FIL_CUST_CLIENT");
		param.put("ID", ID);
		param.put("ORG_ID", Security.getUser().getOrg().getId());
		param.put("CLERK_CODE", Security.getUser().getCode());
		param.put("CLERK_NAME", Security.getUser().getName());
		Dao.insert(xmlPath+"doInsertPro", param);
		Dao.update(xmlPath+"updateCusId",param);
		param.put("TYPE", "LP");
		return param;
	}
	public int selectCountByCode(Map<String, Object> param) {
		return Dao.selectInt(xmlPath+"selectCountByCode", param);
	}
}
