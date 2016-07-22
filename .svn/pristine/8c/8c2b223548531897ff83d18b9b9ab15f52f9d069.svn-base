package com.pqsoft.bpm.handler;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Dao;

/**
 * 是否需要补征信
 * @author Administrator
 *
 */
public class YtjtgDecistion2 implements DecisionHandler{

	private static final long serialVersionUID = 1L;

	@Override
	public String decide(OpenExecution execution) {
		String PROJECT_ID = JBPM.getVeriable(execution.getId()).get("PROJECT_ID")+"";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("PROJECT_ID", PROJECT_ID);
		Map<String, Object> map = Dao.selectOne("letterOpinion.getLetterOpinion", param);
		if(map != null){
			if(map.get("ENDTRIAL_YTJTG") != null && !map.get("ENDTRIAL_YTJTG").equals("")){
				Map<String, Object> param1 = new HashMap<String, Object>();
				param1.put("CODE", map.get("ENDTRIAL_YTJTG"));
				Map<String, Object> map1 = Dao.selectOne("letterOpinion.getDictionary", param1);
				if(map1.get("FLAG").toString().indexOf("征信") > -1){
					return "是";
				}else{
					return "否";
				}
			}else{
				return "否";
			}
		}else{
			return "否";
		}
	}

}
