package com.pqsoft.taskDictionary.service;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.PageTemplate;
import com.pqsoft.util.ClobTransfer;

public class TaskDictionaryService {

	private static Logger logger = Logger.getLogger(TaskDictionaryService.class);
	public TaskDictionaryService(){}
	
	public Page queryPage(Map<String,Object> m)
	{
		Page page = new Page(m);
		List list=Dao.selectList("TaskDictionary.getAllTaskDictionary",m);
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject json = new JSONObject();
			Map map=(Map)list.get(i);
			json.put("TID", map.get("TID"));
			json.put("NAME", map.get("NAME"));
			json.put("USER_ID", map.get("USER_ID"));
			json.put("NUM", map.get("NUM"));
			json.put("CODE", map.get("CODE"));
			json.put("SUP_NAMES", map.get("SUP_NAMES"));
			json.put("TASK_NAMES", map.get("TASK_NAMES"));
			json.put("NODE_NAMES", map.get("NODE_NAMES"));
			array.add(json);
		}
		page.addDate(array, Dao.selectInt("TaskDictionary.getAllTaskDictionary_count",m));
		return page;
	}
	
	public Page getTaskDicInfo(Map<String,Object> m)
	{
		Page page = new Page(m);
		List list=Dao.selectList("TaskDictionary.getTaskDicInfo",m);
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject json = new JSONObject();
			Map map=(Map)list.get(i);
			json.put("ID", map.get("ID"));
			json.put("NAME", map.get("NAME"));
			json.put("TASK_ID", map.get("TASK_ID"));
			json.put("SUPPLIER_ID", new ClobTransfer().clobToString((Clob)map.get("SUPPLIER_ID")));
			json.put("USER_ID", map.get("USER_ID"));
			json.put("NODE_NAME", map.get("NODE_NAME"));
			json.put("SUP_NAME", new ClobTransfer().clobToString((Clob)map.get("SUP_NAME")));
			json.put("REMARK", map.get("REMARK"));
			array.add(json);
		}
		page.addDate(array, 0);
		return page;
	}
	
	
	public int insertTaskDic(Map<String,Object> m)  {
		return Dao.insert("TaskDictionary.insertTaskDictionary", m);
    }

	
	public String checkNoteAndSup(Map<String,Object> m) {
		return  (String)Dao.selectOne("TaskDictionary.checkNoteAndSup", m);
    }
	
	/**
	 * 删除 流程节点配置
	 * @param ID 配置信息ID  USER_ID 用户ID
	 * @author:  吴剑东
	 * @date:    2013-9-13 下午04:19:30
	 */
	public int deleteDictionary(Map<String,Object> m) {
		return Dao.delete("TaskDictionary.deleteTaskDic", m);
	}	
	
	public int deleteTaskDicById(Map<String,Object> m) {
		return Dao.delete("TaskDictionary.deleteTaskDicById", m);
	}	
	
	public List getUserListByType(Map<String,Object> param)  {
		//1:内部 2:供应商 3:厂商   
		//param.put("PERSON_TYPE", "0");
		return Dao.selectList("User.getUserListByType",param);
    }
	
	
	public List getTaskList(Map<String,Object> param)  {
		return Dao.selectList("TaskDictionary.getTaskListForTaskDic");
    }

	public List getSupplierList(Map<String,Object> param)  {
		return Dao.selectList("TaskDictionary.getSupplierListForTaskDic");
	}
	

	public List getOldTaskId()  {
		return Dao.selectList("TaskDictionary.getOldTaskId",null);
	}
	
	/**
	 * 根据流程名称、节点、供应商ID获取对应操作人
	 * @param TASK_ID 流程名称   jbpmId 
	 * @param NODE_NAME 节点名称
	 * @param SUPPLIER_ID 供应商Id
	 * @author:  吴剑东
	 * @date:    2013-9-13 下午04:19:30
	 */
	public String getUserCodeByProcessDefinition(Map<String,Object> m) {
		return (String)Dao.selectOne("TaskDictionary.getUserCodeByProcessDefinition", m);
	}

	public Integer doUpdateTaskID(Map<String, Object> m) {
		return Dao.update("TaskDictionary.updateTaskID", m);
	}

	public String querySuppByCode(String CODE) {
		return (String)Dao.selectOne("TaskDictionary.querySuppByCode", CODE);
	}

	public boolean checkSupName(Map<String, Object> m) {
		return Dao.selectInt("TaskDictionary.checkSupName", m)>0 ? true:false;
	}	
	
}
