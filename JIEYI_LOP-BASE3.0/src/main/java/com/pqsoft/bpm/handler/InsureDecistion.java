package com.pqsoft.bpm.handler;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Dao;

/**
 * 有无保险判断
 * 
 * @author King 2013-9-22
 */
public class InsureDecistion implements DecisionHandler {
	private String namespace = "bpm.handler.";
	/**
	 * @author:King 2013-9-22
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String decide(OpenExecution execution) {
		// 项目id
//		String PROJECT_ID = JBPM.getVeriable(execution.getId()).get("PROJECT_ID")+"";
//		String TYPE="PDF模版所属商务板块";
//		Map<String,Object> param=new HashMap<String,Object>();
//		param.put("PROJECT_ID", PROJECT_ID);
//		param.put("_TYPE", TYPE);
//		Map<String, Object> map = Dao.selectOne(namespace + "queryProSchemeByProId", param);
//		// 有无保险
//		double INSURANCE_PERCENT = 0;
//		if (map.containsKey("INSURANCE_PERCENT"))
//			INSURANCE_PERCENT = Double.parseDouble(map.get("INSURANCE_PERCENT") + "");
//		if (INSURANCE_PERCENT > 0) {
//			return "有保险";
//		} else {
			return "无保险";
//		}
	}

}
