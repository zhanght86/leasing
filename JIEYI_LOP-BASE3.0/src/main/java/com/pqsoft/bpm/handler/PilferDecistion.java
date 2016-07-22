package com.pqsoft.bpm.handler;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Dao;

/**
 * 有无盗抢险及是否是陕重汽
 * 
 * @author King 2013-9-22
 */
public class PilferDecistion implements DecisionHandler {
	private String namespace = "bpm.handler.";
	/**
	 * @author:King 2013-9-22
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String decide(OpenExecution execution) {
		// 项目id
//		String PROJECT_ID = execution.getVariable("PROJECT_ID") + "";
		String PROJECT_ID = JBPM.getVeriable(execution.getId()).get("PROJECT_ID")+"";
//		System.out.println("king="+execution.getVariables());
		String TYPE="PDF模版所属商务板块";
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("PROJECT_ID", PROJECT_ID);
		param.put("_TYPE", TYPE);
		Map<String, Object> map = Dao.selectOne(namespace + "queryProSchemeByProId", param);
		// 有无盗抢险
		String PILFER = null;
		if (map.containsKey("PILFER")) {
			PILFER = map.get("PILFER") + "";
		}
		String COMPANY_NAME = map.get("COMPANY_NAME") + "";

		// 有盗抢险且非陕重汽
		if ((!COMPANY_NAME.contains("陕重汽")&& PILFER != null && (PILFER.equals("有") || PILFER.equals("1")))||COMPANY_NAME.contains("社会产品")) {
			return "有盗抢险且非陕重汽";
		} else {
			return "无盗抢险或陕重汽";
		}
	}

}
