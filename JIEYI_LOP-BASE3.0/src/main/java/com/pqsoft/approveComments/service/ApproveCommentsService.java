package com.pqsoft.approveComments.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.asm.commons.TryCatchBlockSorter;

import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

/**
 * @author caizhongyang
 *
 */
public class ApproveCommentsService {

	private String xmlPath = "approveComments.";
	/*
	 * 增加 
	 */
	public boolean saveApproveComments(Map<String, Object> param) {
		boolean flag=true;
		int i=0;
		if("update".equals(param.get("type").toString())){
			i=Dao.update(xmlPath+"updateApproveComments", param);
		}else{
			i=Dao.insert(xmlPath+"saveApproveComments", param);
		}
		if(i<0){
			flag=false;
		}
		return flag;
	}

	/*
	 * 查看 
	 */
	public List<Map<String,Object>> queryApproveCommentByProId(Map<String, Object> param) {
		List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
		list=Dao.selectList(xmlPath+"queryApproveCommentByProId",param);
		return list;
	}
	
	public List<Map<String,Object>> queryProjectByProId(Map<String, Object> param){
		List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
		list=Dao.selectList(xmlPath+"queryProjectByProId",param);
        return list;
	}
	
	public boolean updateBlackCust(Map<String, Object> param) {
		boolean flag=true;
		int i=Dao.update(xmlPath+"updateBlackCust", param);
		if(i<0){
			flag=false;
		}
		return flag;
	}
}
