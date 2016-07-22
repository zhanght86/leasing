package com.pqsoft.bpm.handler;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Dao;

/**
 * 拒绝
 * @author Administrator
 *
 */
public class RefuseDecistion2 implements DecisionHandler{

	private static final long serialVersionUID = 1L;

	@Override
	public String decide(OpenExecution execution) {
		
		String PROJECT_ID = JBPM.getVeriable(execution.getId()).get("PROJECT_ID")+"";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PROJECT_ID", PROJECT_ID);
		Map<String, Object> map1 = Dao.selectOne("letterOpinion.getLetterOpinion", map);
		if(map1 != null){
			if(map1.get("ENDTRIAL_SPJL") != null || map1.get("ENDTRIAL_SPJL").equals("")){
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("CODE", map1.get("ENDTRIAL_SPJL"));
				Map<String, Object> map3 = Dao.selectOne("letterOpinion.getDictionaryNew", map2);
				if(map3 != null){
					if(map3.get("FLAG").toString().indexOf("通过") > -1 ||
							map3.get("FLAG").toString().indexOf("有条件通过") > -1){
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
		}else{
			return "否";
		}
	}

}
