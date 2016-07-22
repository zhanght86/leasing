package com.pqsoft.bpm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;

public class Sup2OpAssignee implements AssignmentHandler {

	private static final long serialVersionUID = 1L;

	public String roleName;

	@Override
	public void assign(Assignable assignable, OpenExecution execution) {
		if (roleName == null) throw new ActionException("未设置对应角色");
		String SUPPLIER_ID = execution.getVariable("SUPPLIER_ID") == null ? "" : execution.getVariable("SUPPLIER_ID").toString();
		if (SUPPLIER_ID == null) throw new ActionException("该流程无对应供应商");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ROLE_NAME", roleName.trim());
		map.put("SUP_ID", SUPPLIER_ID.trim());
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
			if(m.get("KEY_")!=null&& m.get("KEY_").equals("SUPPLIER_ID")){
				SUPPLIER_ID = m.get("STRING_VALUE_").toString();
			}
		}
		map.put("SUP_ID", SUPPLIER_ID.trim());
		String code = Dao.selectOne("bpm.handler.getSupOpAssignee",map);
		if (code == null) throw new ActionException("未设置下一审批节点对应的审批人员");
		assignable.setAssignee(code);
	}

}
