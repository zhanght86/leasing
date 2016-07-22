package com.pqsoft.bpm.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;

public class AllTasksService {

	
	public Page getPage(Map<String,Object> param){
		return PageUtil.getPage(param, "bpm.allTask.getPageList", "bpm.allTask.getPageCount");
	}
	
	public List<Map<String,Object>> findOperator(){
		return Dao.selectList("bpm.allTask.findOperator");
	}
	
	public Page getOperatorPage(Map<String,Object> param){
		// 如果SID非空，则调用我的任务-团队任务-设置的方法
		if(StringUtils.isNotBlank(param.get("SID"))){
			return PageUtil.getPage(param, "bpm.allTask.getOperatorPageTeam", "bpm.allTask.getOperatorCountTeam");
		}else{
			return PageUtil.getPage(param, "bpm.allTask.getOperatorPage", "bpm.allTask.getOperatorCount");
		}
	}
	
	public int updateOperator(Map<String,Object> param){
		return Dao.update("bpm.allTask.updateOperator", param);
	}
	/**
	 * 查询操作人信息
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年7月24日,下午3:30:57
	 */
	public Map<String,Object> getSecuUser(Map<String,Object> param){
		return Dao.selectOne("bpm.allTask.getSecuUser", param);
	}
	
	/**
	 * 更新首页我的事宜
	 * @param param
	 * @return
	 * @author wanghl
	 * @datetime 2015年7月24日,下午3:33:39
	 */
	public int updateTaskPortal(Map<String,Object> param){
		return Dao.update("bpm.allTask.updateTaskPortal", param);
	}
}
