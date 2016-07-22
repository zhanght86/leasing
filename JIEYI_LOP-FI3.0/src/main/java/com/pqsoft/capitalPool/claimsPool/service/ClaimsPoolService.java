package com.pqsoft.capitalPool.claimsPool.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.PageUtil;

public class ClaimsPoolService {
	private final String xmlPath = "ClaimsPool.";
	
	public Page getClaimsPool_Page(Map<String, Object> m) {
		if (m != null && m.get("SUP_NAME") != null
				&& m.get("SUP_NAME").equals("超级管理员")) {
			m.remove("SUP_NAME");
		}
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			m.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}
		
		return PageUtil.getPage(m, xmlPath+"getClaimsPoolList",xmlPath+"getClaimsPoolListCount");

	}
	
	public Page getClaimsPool_Detail_Page(Map<String, Object> m) {
		if (m != null && m.get("SUP_NAME") != null
				&& m.get("SUP_NAME").equals("超级管理员")) {
			m.remove("SUP_NAME");
		}
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			m.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}
		
		return PageUtil.getPage(m, xmlPath+"getClaimsPool_Detail_List",xmlPath+"getClaimsPool_Detail_ListCount");

	}
	
	
	public Page getClaimsHead_Page(Map<String, Object> m) {
		if (m != null && m.get("SUP_NAME") != null
				&& m.get("SUP_NAME").equals("超级管理员")) {
			m.remove("SUP_NAME");
		}
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			m.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}
		
		return PageUtil.getPage(m, xmlPath+"getClaimsHeadList",xmlPath+"getClaimsHeadCount");

	}
	
	public Page claimsPool_back_PageAjax(Map<String, Object> m) {
		if (m != null && m.get("SUP_NAME") != null
				&& m.get("SUP_NAME").equals("超级管理员")) {
			m.remove("SUP_NAME");
		}
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			m.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}
		
		return PageUtil.getPage(m, xmlPath+"getClaims_Back_List",xmlPath+"getClaims_Back_Count");

	}
	
	public Page claimsPool_JBPM_VIEW(Map<String, Object> m){
		return PageUtil.getPage(m, xmlPath+"getClaims_JBPM_List",xmlPath+"getClaims_JBPM_Count");
	}

	public int updateclaims(Map map){
		return Dao.update(xmlPath+"updateclaims",map);
	}
	
	public void claims_C_Submit(Map map){
		String HEAD_ID=Dao.getSequence("SEQ_FIL_CLAIMS_HEAD");
		map.put("HEAD_ID", HEAD_ID);
		int i=Dao.insert(xmlPath+"insertClaims_head",map);
		if(i>0)
		{
			Dao.update(xmlPath+"updateClaimsById",map);
		}
	}
	
	public List getClaimsPoolDetail(Map map){
		return Dao.selectList(xmlPath+"getClaimsPoolDetail", map);
	}
	
	public void claimsPool_Remove(Map map){
		int i=Dao.delete(xmlPath+"Claims_head_Remove",map);
		if(i>0){
			Dao.update(xmlPath+"updateClaimsByHeadId",map);
		}
	}
	
	public void claimsPool_BOHUI(Map map){
		Dao.update(xmlPath+"updateClaimsBoHuiByHeadId",map);
	}
	
	public void claimsPool_Confirm(Map map){
		Dao.update(xmlPath+"updateClaimsConfirmByHeadId",map);
		//池子退款成功
		Dao.update(xmlPath+"updatePoolByHeadId",map);
	}
	
	public void JBPM_ClaimsPool(Map map){
		String JBPM_ID=Dao.getSequence("SEQ_FIL_CLAIMS_HEAD_JBPM");
		List<String> jbpmList=JBPM.getDeploymentListByModelName("claims");
		if(!jbpmList.isEmpty()&&jbpmList.size()>0){
			map.put("JBPM_ID", JBPM_ID);
			Dao.update(xmlPath+"updateClaimsHeadByID",map);
			Map<String,Object> jm=new HashMap<String,Object>();
			jm.put("CLAIMS_JBPM_ID", JBPM_ID);
			JBPM.startProcessInstanceById(jbpmList.get(0), null, null, null, jm);
		}
	}
	
	public void jbpmByClaimsPassStatue(String JBPM_ID)
	{
		String CLAIMS_JBPM_ID=JBPM.getVeriable(JBPM_ID).get("CLAIMS_JBPM_ID").toString();

		Dao.update(xmlPath+"updateClaimsHeadByJbpmID",CLAIMS_JBPM_ID);
	}
	
	
	public Page claimsPool_HeadView_VIEW(Map<String, Object> m){
		return PageUtil.getPage(m, xmlPath+"getClaims_HeadView_List",xmlPath+"getClaims_HeadView_Count");
	}
}
