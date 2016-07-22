package com.pqsoft.bpm.status;

import java.util.Map;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Dao;

/**
 * 项目主表更新
 * @author Donzell 2013-12-9
 */
public class PurcharseUpdateForJbpm {
	
	/**
	 * 更新项目状态和DB保证金解冻
	 * @param jbpmId
	 * @author Donzell 2013-12-9
	 */
	public void updateCreditRatifyDate(String jbpmId){
		Map<String,Object> map=JBPM.getVeriable(jbpmId);
		String PROJECT_ID = map.get("PROJECT_ID") + "";
		Dao.update("purchase.updateProjectStatus", map);
	}
	
}
