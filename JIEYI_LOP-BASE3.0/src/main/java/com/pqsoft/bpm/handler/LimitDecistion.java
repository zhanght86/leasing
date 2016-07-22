package com.pqsoft.bpm.handler;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.rbac.api.Security;

/**
 * 判断用户额度
 * @author Administrator
 *
 */
public class LimitDecistion implements DecisionHandler{

	private static final long serialVersionUID = -8129544475289066708L;

	@Override
	public String decide(OpenExecution execution) {
		String PROJECT_ID = JBPM.getVeriable(execution.getId()).get("PROJECT_ID")+"";
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("ID", PROJECT_ID);
		Map<String, Object> project = Dao.selectOne("letterOpinion.getProject", map1);
		project.get("FINANCE_TOPRIC");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ID", Security.getUser().getId());
		
		Map<String, Object> param = Dao.selectOne("letterOpinion.getUser", map);
		if(param != null && project != null){
			if(param.get("USERED") != null && !param.get("USERED").equals("") &&
					project.get("FINANCE_TOPRIC") != null && !project.get("FINANCE_TOPRIC").toString().equals("")){
				if(Double.parseDouble(param.get("USERED").toString()) 
						> Double.parseDouble(project.get("FINANCE_TOPRIC").toString())){
					Dao.update("letterUpdate.updateLetterFun",PROJECT_ID);//from liuli
					return "是";
				}else{
					Map<String, Object> map2 = new HashMap<String, Object>();
					map2.put("PROJECT_ID", PROJECT_ID);
					Map<String, Object>  letterMap=  Dao.selectOne("letterOpinion.getLetterOpinion", map2);
					 if(letterMap!=null&&letterMap.get("FIRSTTRIAL_SPJL")!=null){
						 //jzzl-84 初审结论为 客户放弃的 返回是。 add by xingsumin
						if(letterMap.get("FIRSTTRIAL_SPJL").toString().equals("3")||letterMap.get("FIRSTTRIAL_SPJL").toString().equals("4")||letterMap.get("FIRSTTRIAL_SPJL").toString().equals("5")){
							Dao.update("letterUpdate.updateLetterFun",PROJECT_ID);//from liuli
							return "是";
						}
					}
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
