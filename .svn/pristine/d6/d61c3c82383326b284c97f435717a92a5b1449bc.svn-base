package com.pqsoft.checkIdCard.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.util.PageUtil;

public class CheckIdCardService {
	
	String xmlPath = "checkIdCard.";//xml路径

	public Map<String, Object> getIdCardMsg(String ID_CARD_NO) {
		return Dao.selectOne(xmlPath + "getIdCardMsg", ID_CARD_NO);
	}

	public void addCheckCardIdSuccess(Map<String, Object> param) {
		Dao.insert(xmlPath + "addCheckCardIdSuccess", param);
	}

	public void addCardIdLog(Map<String, Object> param) {
		Dao.insert(xmlPath + "addCardIdLog", param);
	}
	
	public Page getCICPage(Map<String, Object> param) {
		return PageUtil.getPage(param, xmlPath+"getCICPageList", xmlPath+"getCICPageCount");
	}
	
	public Page getCICLogPage(Map<String, Object> param) {
		return PageUtil.getPage(param, xmlPath+"getCICLogPageList", xmlPath+"getCICLogPageCount");
	}
	
	public Page getCallInterfaceData(Map<String, Object> param) {
		return PageUtil.getPage(param, xmlPath+"getCIFPageList", xmlPath+"getCIFPageCount");
	}
	
	public String getIdCariState(Map<String, Object> param){
//		String res = Dao.selectOne(xmlPath+"getIdCariState", param);
		String res = Dao.selectOne(xmlPath+"getIdCariStateNew", param);
		if(res != null && !res.equals("")){
			if(res.equals("yrz")){
				return param.get("ID_CARD_NO")+"    	<a href='javascript:void(0);'><img border=\"0\" title='已认证' src='" + SkyEye.getRequest().getContextPath() + "/img/Verify_go.png'/></a>";
			}else if(res.equals("wrz")){
				if(param.containsKey("NAME")){
					return param.get("ID_CARD_NO")+"    	<a href=\"javascript:void(0);\" ><img border=\"0\" onclick=\"checkIdCard(this,'"+param.get("NAME")+"','"+param.get("ID_CARD_NO")+"')\" title=\"未认证\" src=\"" + SkyEye.getRequest().getContextPath() + "/img/Verify.png\"/></a>";
				}else{
					return param.get("ID_CARD_NO")+"    	<a href=\"javascript:void(0);\" onclick=\"alert('无法认证【无证件名称】！')\"><img border=\"0\" title='未认证' src='" + SkyEye.getRequest().getContextPath() + "/img/Verify.png'/></a>";
				}
			}else if(res.equals("rzwtg")){
				return param.get("ID_CARD_NO")+"    	<a href='javascript:void(0);'><img border=\"0\" title='认证未通过' src='" + SkyEye.getRequest().getContextPath() + "/img/Verify_NO.png'/></a>";
			}else{
				return param.get("ID_CARD_NO").toString();
			}
		}else{
			return param.get("ID_CARD_NO").toString();
		}
	}

}
