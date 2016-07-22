package com.pqsoft.proChange.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

/**
 * 方案变更申请
 * @author zhengshangcheng
 *
 */
public class ProChangeService {

	//修改项目方案变更原因及备注
	public int updatePro(Map<String, Object> param){
		return Dao.update("proChange.updatePro", param);
	}
	
	//修改设备信息
	public int updateEquipment(Map<String, Object> param){
		return Dao.update("proChange.updateEquipment", param);
	}
	
	//修改期数
	public int updateScheme(Map<String, Object> param){
		return Dao.update("proChange.updateScheme", param);
	}
	
	public Map<String, Object> getPro(Map<String, Object> param){
		return Dao.selectOne("proChange.getPro", param);
	}
	
	//方案变更申请   申请
	public int changeSchemeStatus(Map<String, Object> param){
		return Dao.update("proChange.changeSchemeStatus",param);
	}
	
	public Map<String, Object> getSchemeStatus(Map<String, Object> param){
		return Dao.selectOne("proChange.getSchemeStatus", param);
	}
	
	//方案变更申请   信审同意
	public void changeSchemeStatus3(String project_id){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("PROJECT_ID", project_id);
		param.put("FABGSQ_STATUS", "3");
		Dao.selectOne("proChange.changeSchemeStatus3", param);
	}
	
	//方案变更申请   信审拒绝
	public void changeSchemeStatus4(String project_id){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("PROJECT_ID", project_id);
		param.put("FABGSQ_STATUS", "4");
		Dao.selectOne("proChange.changeSchemeStatus4", param);
	}
	//将页面中所有内容保存在fil_project_scheme_change中---by tianhui
	public int saveUpdateProject(Map<String, Object> param){
		List<Map<String, Object>> list = Dao.selectList("proChange.selUpdateProject",param);
		if(null != list && list.size() > 0){
			Dao.delete("proChange.delUpdateProject",param);	
		}
		return Dao.insert("proChange.saveUpdateProject", param);
	} 
	
}
