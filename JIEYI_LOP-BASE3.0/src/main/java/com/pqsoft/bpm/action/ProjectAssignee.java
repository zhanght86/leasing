package com.pqsoft.bpm.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.taskDictionary.service.TaskDictionaryService;

public class ProjectAssignee implements AssignmentHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public void assign(Assignable assignable, OpenExecution execution) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 节点名称
		String activityName = execution.getActivity().getName().toString();
		// 流程名称
		String processDefinitionId = execution.getProcessDefinitionId().toString();
		// 供应商Id
		String SUPPLIER_ID = execution.getVariable("SUPPLIER_ID") == null ? "" : execution.getVariable("SUPPLIER_ID").toString();
		
		map.put("NODE_NAME", activityName);
		map.put("TASK_ID", processDefinitionId);
		map.put("SUPPLIER_ID", SUPPLIER_ID);
//		StringBuffer sb = new StringBuffer();
//		sb.append("  SELECT JV.KEY_, JV.STRING_VALUE_                                                            "         )
//		.append(" 	FROM JBPM4_VARIABLE JV, JBPM4_EXECUTION JE                 "         )
//		.append("	WHERE JE.DBID_ = JV.EXECUTION_                             "         )
//	    .append("   AND ('"+execution.getId()+".' LIKE JE.ID_ || '.%' OR         "         )
//	    .append("   JE.ID_ || '.' LIKE '"+execution.getId()+".%')                "         );
//		List<Map<String,Object>> variable = Dao.execSelectSql(sb.toString());
		Map<String,Object> amap=new HashMap<String,Object>();
		amap.put("JBPMID", execution.getId()+".");
		List<Map<String,Object>> variable = Dao.selectList("bpm.procinst.queryJbpmAssgin", amap);;
		for(Map<String,Object> m: variable){
			map.put(m.get("KEY_").toString(), m.get("STRING_VALUE_"));
		}
		// SUPPLIER_ID = "sq052";
		TaskDictionaryService service = new TaskDictionaryService();
		// 根据流程名称、节点、供应商ID获取对应操作人
		String nextUser = service.getUserCodeByProcessDefinition(map);
		if (nextUser == null || nextUser == "" || "".equals(nextUser)) {
			throw new ActionException("下一节点没有配置流程操作人!");
		} else {
			assignable.setAssignee(nextUser);
		}
	}

}
