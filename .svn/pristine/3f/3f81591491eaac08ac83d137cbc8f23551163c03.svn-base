package com.pqsoft.base.task.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;

public class TaskGroupService {

	public Page getPageData(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList("Task.Group.selectTaskGroup",param));
		page.addDate(array, Dao.selectInt("Task.Group.selectTaskGroupCount", param));
		return page;
	}
	
	public List<Map<String,Object>> getNextAllUser(String ORG_ID)
	{
		return Dao.selectList("Task.Group.getNextAllUser",ORG_ID);
	}
	
	public List<Map<String,Object>> getTaskAllication(Map<String,Object> param)
	{
		return Dao.selectList("Task.Group.getTaskAllication", param);
	}
	
	public String doAdd(Map<String,Object> param)
	{
		String id=Dao.getSequence("SEQ_T_SYS_TASK_GROUP");
		param.put("ID", id);
		Dao.insert("Task.Group.doAdd",param);
		return id;
	}
	public int doAdd1(Map<String,Object> param)
	{
		return Dao.insert("Task.Group.doAdd1",param);
	}
	public int doupd(Map<String,Object> param)
	{
		param.put("CREATION_ID", Security.getUser().getOrg().getId());
		return Dao.update("Task.Group.doupd",param);
	}
	public int del(Map<String,Object> param)
	{
		return Dao.delete("Task.Group.del",param);
	}
	public int delPersonnel(Map<String,Object> param)
	{
		return Dao.delete("Task.Group.delPersonnel",param);
	}
	public List<Map<String,Object>> getPersonnel(Map<String,Object> param)
	{
		return Dao.selectList("Task.Group.getPersonnel", param);
	}
	public Map<String,Object> getConfigure(Map<String,Object> param)
	{
		return Dao.selectOne("Task.Group.getConfigure", param);
	}
	public List<Map<String,Object>> getTaskName()
	{
		return Dao.selectList("Task.Group.getTaskName");
	}
	
	public int updtype(Map<String,Object> param){
		return Dao.update("Task.Group.updtype",param);
	}
}
