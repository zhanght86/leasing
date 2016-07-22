package com.pqsoft.litigation_case.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;

public class litigationCaseService {

	public Page query_project_litigation_MG(Map<String, Object> m) {
		return PageUtil.getPage(m, "litigation_case.litigation_MSG", "litigation_case.litigation_MSG_count");
	}
	
	public Map queryCreateFormAjax(Map map){
		return Dao.selectOne("litigation_case.queryCreateFormAjax", map);
	}
	
	public int createLitigation(Map map){
		return Dao.insert("litigation_case.createLitigation", map);
	}
	
	public Page litigation_Self_Mg(Map<String, Object> m) {
		return PageUtil.getPage(m, "litigation_case.litigation_Self_Mg", "litigation_case.litigation_Self_Mg_count");
	}
	
	public String saveLitiCreateApp(Map<String, Object> map) {
		String id = Dao.getSequence("SEQ_FIL_PROJECT_LICASE_DTAIL");
		map.put("ID", id);
		map.put("USER_NAME", Security.getUser().getName());
		int i = Dao.insert("litigation_case.insertDetail", map);
		
		//修改主表信息
		Dao.update("litigation_case.updateLiteHeadById", map);
		if(map!=null && map.get("D_STATAUS")!=null && !map.get("D_STATAUS").equals(""))
		{
			double D_STATAUS=Double.parseDouble(map.get("D_STATAUS").toString());
			if(D_STATAUS==1)//诉讼结束
			{
				//如果诉讼结束 修改还款计划和项目表未正常状态
				Dao.update("litigation_case.updatePlanById", map);
			}
		}
		if (i > 0)
			return id;
		else
			return "";
	}
	
	public String saveLitiCreateS(Map<String, Object> map) {
		String id = Dao.getSequence("SEQ_FIL_LITIGATION_CASE");
		map.put("ID", id);
		map.put("USER_NAME", Security.getUser().getName());
		Dao.insert("litigation_case.createLitigationS", map);
		Dao.insert("litigation_case.insertDetailS", map);
		return id;
	}
	
	public List doShowDetailList(Map map){
		return Dao.selectList("litigation_case.doShowDetailList", map);
	}
	
	public Map queryDetailView(Map map){
		return Dao.selectOne("litigation_case.queryDetailView", map);
	}
	
	public Map queryViewS(Map map){
		return Dao.selectOne("litigation_case.queryViewS",map);
	}
	
	public String saveLitiUpdateS(Map<String, Object> map){
		String ID=map.get("ID").toString();
		map.put("USER_NAME", Security.getUser().getName());
		Dao.insert("litigation_case.updateLitigationS", map);
		Dao.insert("litigation_case.updateDetailS", map);
		return ID;
	}
	
	public boolean deleteThis(Map<String, Object> map){
		return Dao.delete("litigation_case.deleteThis",map) > 0 ? true : false;
	}
}
