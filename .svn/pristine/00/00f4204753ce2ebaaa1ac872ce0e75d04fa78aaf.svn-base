package com.pqsoft.bpm.handler;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Dao;

/**
 * 直租不上牌模式专用 判断第二个债权审批
 * 
 * @author Donzell 2014-1-14
 */
public class PilferDecistion2 implements DecisionHandler {
	private String namespace = "bpm.handler.";
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

		// 有盗抢险且非陕重汽  OLD
		//有盗抢险且非陕重汽或山重建机  UPDATE @author Donzell  20140114
		if (COMPANY_NAME.contains("山重建机")||(!COMPANY_NAME.contains("陕重汽") && PILFER != null && (PILFER.equals("有") || PILFER.equals("1")))) {
			return "有盗抢险且非陕重汽或山重建机";
		} else {
			return "非山重建机无盗抢险或陕重汽";
		}
	}

}
