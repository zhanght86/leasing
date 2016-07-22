package com.pqsoft.bpm.handler;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Dao;

/**
 * 根据项目id剥离出山重建机
 * 
 * @author Donzell 2014-1-14
 */
public class BusinessSectorDecision3 implements DecisionHandler {
	private String namespace = "bpm.handler.";
	/**
	 * @author:Donzell 2014-3-1`
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String decide(OpenExecution execution) {
		// 项目id
		String PROJECT_ID = JBPM.getVeriable(execution.getId()).get("PROJECT_ID")+"";
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("_TYPE", "PDF模版所属商务板块");
		param.put("PROJECT_ID", PROJECT_ID);
		Map<String, Object> map = Dao.selectOne(namespace + "queryProSchemeByProId", param);
		
		String YINGYE_STATUS = map.get("YINGYE_STATUS")+"";
		String outline = null;
		if (YINGYE_STATUS.contains("1")) {
			outline = "营业审核";
		} else {
			outline = "其他";
		}
		return outline;
	}

}
