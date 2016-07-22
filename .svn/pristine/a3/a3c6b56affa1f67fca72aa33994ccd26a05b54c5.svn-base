package com.pqsoft.bpm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;

public class OverdueExemptJbpm {

	// TODO 免息通过
	public void agree(String EXEMPT_ID) {
//		String EXEMPT_ID = JBPM.getVeriable(jbpmId).get("EXEMPT_ID").toString();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("ID", EXEMPT_ID);
		m.put("STATUS", "pass");
//		if (Dao.selectInt("fi.overdue.getExemptOpCount", m) > 0) throw new ActionException("部分违约金已进入核销，所以无法进行免除操作，请驳回重新申请");
		
		Map<String, Object> param = Dao.selectOne("fi.overdue.getExempt", m);
		Dao.update("fi.overdue.exemptPass", param);
		Dao.update("fi.overdue.upExemptOverdue", m);
		List<Map<String, Object>> dues = Dao.selectList("fi.overdue.getExemptItemDue", m);
		for (Map<String, Object> map : dues) {
			Dao.delete("fi.overdue.deleteByPayLJ", map);
			Dao.selectOne("fi.overdue.upDueOne", map);
			
			map.put("PAYLIST_CODE", map.get("PAY_CODE"));
			map.put("BEGINNING_NUM", map.get("PERIOD"));
			Dao.update("rentWriteNew.doPRC_BE_QJL_ONE",map);
		}
		
		
		if(Dao.selectInt("fi.overdue.getErrorCount", param)>0)throw new ActionException("减免金额过大，请驳回确认是否存在重复申请！");
	}

	// TODO 免息不通过
	public void disagree(String EXEMPT_ID) {
//		String EXEMPT_ID = JBPM.getVeriable(jbpmId).get("EXEMPT_ID").toString();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("ID", EXEMPT_ID);
		m.put("STATUS", "nopass");
		Dao.update("fi.overdue.exemptNoPass", m);
		Dao.update("fi.overdue.upExemptOverdue", m);
		
		List<Map<String, Object>> dues = Dao.selectList("fi.overdue.getExemptItemDue", m);
		for (Map<String, Object> map : dues) {
			map.put("PAYLIST_CODE", map.get("PAY_CODE"));
			map.put("BEGINNING_NUM", map.get("PERIOD"));
			Dao.update("rentWriteNew.doPRC_BE_QJL_ONE",map);
		}
	}

}
