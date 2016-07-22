package com.pqsoft.performance.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class AssessmentConfigurService {
	
	String xmlPath = "performance.C.";//xml路径
	
	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, xmlPath+"getPageList", xmlPath+"getPageCount");
	}
	
	public List<Map<String,Object>> getDepartment(){
		return Dao.selectList(xmlPath + "getDepartment");
	}
	
	public List<Map<String,Object>> getPost(Map<String, Object> param){
		return Dao.selectList(xmlPath + "getPost",param);
	}
	
	public List<Map<String,Object>> getAssessmentTopic(){
		return Dao.selectList(xmlPath + "getAssessmentTopic");
	}
	
	public boolean doUpdateAC(Map<String,Object> param){
		return Dao.update(xmlPath + "doUpdateAC", param) > 0 ? true : false;
	}
	
	public boolean doDeleteAC(Map<String,Object> param){
		return Dao.delete(xmlPath + "doDeleteAC", param) > 0 && Dao.delete(xmlPath + "doDeleteACD", param) > 0 ? true : false;
	}
	
	public Map<String,Object> getAssessmentConfigur(Map<String, Object> param){
		Map<String,Object> assessmentConfigurMap = Dao.selectOne(xmlPath + "assessmentConfigur",param);//获取配置模版
		param.put("PARENT_ID",  assessmentConfigurMap.get("DEPARTMENT"));
		assessmentConfigurMap.put("postList", getPost(param));//查询部门下所有的岗位
		List<Map<String, Object>> assessmentTopicList = Dao.selectList(xmlPath + "assessmentTopicList",assessmentConfigurMap);//获取模版中所有的考核项ID
		assessmentConfigurMap.put("assessmentTopicList", assessmentTopicList);
		param.put("AC_ID", param.get("ID"));
		for (int i = 0; i < assessmentTopicList.size(); i++) {
			Map<String, Object> assessmentTopicIdMap = assessmentTopicList.get(i);//考核项ID
			param.put("AT_ID", assessmentTopicIdMap.get("AT_ID"));//考核项ID
			List<Map<String,Object>> assessmentTopicDetailList = Dao.selectList(xmlPath + "assessmentTopicDetailList",param);//考核项下所有的条款
			assessmentTopicIdMap.put("assessmentTopicDetailList", assessmentTopicDetailList);//考核项下所有的条款
		}
		return assessmentConfigurMap;
	}
	
	@SuppressWarnings("unchecked")
	public boolean doSave(JSONObject assessmentConfigurMap) {
		try{
			String  AC_ID = assessmentConfigurMap.get("AC_ID").toString();//考核标准配置主表ID--模版ID
			List<Map<String, Object>> assessmentTopicList = assessmentConfigurMap.getJSONArray("DATA_LIST");//所有考核项
			if(AC_ID != null && !AC_ID.equals("")){
				Dao.update( xmlPath + "updateAssessmentConfigur", assessmentConfigurMap);//修改考核标准配置主表
				assessmentConfigurMap.put("DOTYPE", false);
			}else{
				AC_ID = Dao.getSequence("SEQ_ASSESSMENT_CONFIGUR");//考核标准配置主表-主键ID 
				assessmentConfigurMap.put("AC_ID", AC_ID);
				Dao.insert(xmlPath + "addAssessmentConfigur",assessmentConfigurMap);
				assessmentConfigurMap.put("DOTYPE", true);
			}
			Dao.delete(xmlPath + "deleteAssessmentDetail",AC_ID);
			for(Map<String, Object> assessmentTopic : assessmentTopicList){//循环所有考核项
				assessmentTopic.put("AC_ID", AC_ID);//考核标准配置主表ID--模版ID
				List<Map<String,Object>> assessmentDetailList = (List<Map<String, Object>>) assessmentTopic.get("LIST");
				for (int i = 0; i < assessmentDetailList.size(); i++) {//条款区间-绩效金额
					Map<String,Object> assessmentDetail = assessmentDetailList.get(i);
					assessmentDetail.put("AT_ID", assessmentTopic.get("AT_ID"));
					assessmentDetail.put("AC_ID", AC_ID);
					Dao.insert( xmlPath + "addAssessmentDetail", assessmentDetail);//添加考核标准配置子表
				}
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			Dao.rollback();
			return false;
		}
    }

}
