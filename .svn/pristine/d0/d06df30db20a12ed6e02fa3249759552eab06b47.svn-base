package com.pqsoft.bpm.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;



/**
 * 返佣申请
 * @author wanghl
 * @datetime 2015年5月28日,下午5:36:27
 */
public class CommissionStatus {
	
	
	/**
	 * 审批通过状态
	 * @param APPLY_ID
	 * @author wanghl
	 * @datetime 2015年5月28日,下午6:13:40
	 */
	public void commissionPass(String APPLY_ID){
		if(StringUtils.isNotBlank(APPLY_ID)){
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("APPLY_ID", APPLY_ID);
			List<Map<String,Object>> loanList = Dao.selectList("bpm.commission.selectLoan", param);
			for(int i=0;i<loanList.size();i++){
				Map<String, Object> map = loanList.get(i);
				String loan_id = map.get("LOAN_ID")+"";
				param.put("LOAN_ID", loan_id);
				param.put("STATUS", "2");
				Dao.update("bpm.commission.updateCommission",param);
			}
		}else{
			new ActionException("通过状态变更失败");
		}
	}
	
	/**
	 * 审批不通过状态
	 * @param APPLY_ID
	 * @author wanghl
	 * @datetime 2015年5月28日,下午6:24:56
	 */
	public void commissionNoPass(String APPLY_ID){
		if(StringUtils.isNotBlank(APPLY_ID)){
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("APPLY_ID", APPLY_ID);
			List<Map<String,Object>> loanList = Dao.selectList("bpm.commission.selectLoan", param);
			for(int i=0;i<loanList.size();i++){
				Map<String, Object> map = loanList.get(i);
				String loan_id = map.get("LOAN_ID")+"";
				param.put("LOAN_ID", loan_id);
				param.put("STATUS", "3");
				Dao.update("bpm.commission.updateCommission",param);
			}
		}else{
			new ActionException("不通过状态变更失败");
		}
	}
}
