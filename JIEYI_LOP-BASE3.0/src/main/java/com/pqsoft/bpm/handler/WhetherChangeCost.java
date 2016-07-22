package com.pqsoft.bpm.handler;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

import com.pqsoft.skyeye.api.Dao;

/**
 * 租金变更是否存在新增其他费用
 */
public class WhetherChangeCost implements DecisionHandler {
	public String decide(OpenExecution execution) {
		String PAYLIST_CODE = execution.getVariable("PAYLIST_CODE") + "";
		Map<String,Object> param=new HashMap<String, Object>();
		//param.put("_TYPE", "PDF模版所属商务板块");
		param.put("PAYLIST_CODE", PAYLIST_CODE);
		int id = Dao.selectInt("PayTask.whetherChangeCost", param);
		String outline = null;
		if (id>0) {
			outline = "有新增其他费用";
		} else {
			outline = "无新增其他费用";
		}
		return outline;
	}

}
