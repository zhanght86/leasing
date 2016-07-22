package com.pqsoft.bpm.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.StringUtils;

public class RefundDeductDecistion implements DecisionHandler {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String basePath = "refundSflc.";
	
	@Override
	public String decide(OpenExecution execution) {
		String JBPM_ID = execution.getId();
		//通过流程id查询相关信息
		Boolean flag = true;
		Map<String,Object> newParam = new HashMap<String, Object>();
		newParam.put("JBPM_ID", JBPM_ID);
		List<Object> getApplyDan = Dao.selectList(basePath+"getRefundDanByJbpmId", newParam);
		for (Object obj : getApplyDan) {
			Map<String,Object> applyDan = (Map<String,Object>)obj;
			List<Object> getRefundDetail = Dao.selectList(basePath+"getRefundDetailByJbpmId", applyDan);
			int project_count = Integer.parseInt(StringUtils.nullToString(applyDan.get("RE_PROJECT_COUNT")));
			if(getRefundDetail.size() < project_count){
				flag = false;
				break;
			}
		}
		if(flag == false){
			return "通知企划";
		}else{
			return "副总审核";
		}
	}

}
