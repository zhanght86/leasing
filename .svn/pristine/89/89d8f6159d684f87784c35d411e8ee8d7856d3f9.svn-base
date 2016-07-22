package com.pqsoft.base.task.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class TaskAllocationService {

	public Page getPageData(Map<String,Object> param){
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList("Task.Allocation.selectTaskAllocation",param));
		page.addDate(array, Dao.selectInt("Task.Allocation.selectTaskAllocationCount", param));
		return page;
	}
	
	public int IsRepeat(Map<String,Object> param)
	{
		return Dao.selectInt("Task.Allocation.IsRepeat", param);
	}
	public int doAdd(Map<String,Object> param)
	{
		return Dao.insert("Task.Allocation.doAdd",param);
	}
	public Map<String,Object> getOneTaskAllocation(Map<String,Object> param)
	{
		return Dao.selectOne("Task.Allocation.getOneTaskAllocation",param);
	}
	public int doUpd(Map<String,Object> param)
	{
		System.out.println(param+"=============");
		return Dao.insert("Task.Allocation.doupd",param);
	}
	public int del(Map<String,Object> param)
	{
		return Dao.delete("Task.Allocation.del",param);
	}
	public List<Map<String,Object>> getTaskName(Map<String,Object> param)
	{
		return Dao.selectList("Task.Allocation.getTaskName",param);
	}
	public int getGroup(Map<String,Object> param)
	{
		return Dao.selectInt("Task.Allocation.getGroup", param);
	}
	public int getnum(Map<String,Object> param)
	{
		return Dao.selectInt("Task.Allocation.getnum", param);
	}
}
