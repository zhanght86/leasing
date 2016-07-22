package com.pqsoft.bpm.handler;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Dao;

/**
 * 判断是否是陕汽A模式 或者庞大
 * @author King 2013-9-22
 */
public class ShouFuKuanHouDecision implements DecisionHandler {
	private String namespace = "bpm.handler.";
	/**
	 * @author:King 2013-9-22
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String decide(OpenExecution execution) {
		// 项目id
//		String PROJECT_ID=execution.getVariable("PROJECT_ID")+"";
		String PROJECT_ID = JBPM.getVeriable(execution.getId()).get("PROJECT_ID")+"";
		String TYPE="PDF模版所属商务板块";
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("PROJECT_ID", PROJECT_ID);
		param.put("_TYPE", TYPE);
		Map<String, Object> map = Dao.selectOne(namespace + "queryProSchemeByProId", param);
		String SUPPLIER_NAME= map.get("SUP_SHORTNAME")+"";
		if(SUPPLIER_NAME.contains("庞大集团")){
			return "庞大";
		}else{
			return "非庞大";
		}
	}

}
