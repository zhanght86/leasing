package com.pqsoft.base.grantCredit.action;

import java.util.HashMap;
import java.util.Map;

import com.pqsoft.base.grantCredit.service.SupplierCreditService;
import com.pqsoft.bpm.JBPM;

public class CreditApply {

    /**
     * 评审通过的方法
     * @param param
     */
	public void creditAppPass(String jbpm_id){
		Map<String,Object> param = JBPM.getVeriable(jbpm_id);
		SupplierCreditService creditService = new SupplierCreditService();
		Map<String,Object> applyMess = creditService.getApplyDan(param);
		if(param.containsKey("APPLY_TYPE") && param.get("APPLY_TYPE")!=null && !"".equals(param.get("APPLY_TYPE").toString())){
			Map<String,Object> newParam = new  HashMap<String, Object>();
			newParam.put("APPLY_ID", param.get("APPLY_ID").toString());
			newParam.put("CREDIT_ID", applyMess.get("CREDIT_ID").toString());
            if("0".equals(param.get("APPLY_TYPE").toString())){
            	//基础额度申请 (将基础额度、担保人额度回更到授信并将授信变为已授信)
            	newParam.put("CREDIT_STATUS", "1");
            	newParam.put("CREDIT_MONEY", applyMess.get("CREDIT_MONEY").toString());
            	newParam.put("GUARANT_MONEY", applyMess.get("GUARANT_MONEY").toString());
            	newParam.put("COACH_LIMIT", applyMess.get("COACH_LIMIT").toString());
            	newParam.put("COACH_REMIND_MONEY", applyMess.get("COACH_REMIND_MONEY").toString());
            	newParam.put("POSITIVE_REMIND_MONEY", applyMess.get("POSITIVE_REMIND_MONEY").toString());
            	creditService.updateCreditMess(newParam);
            }else if("1".equals(param.get("APPLY_TYPE").toString())){
            	//转正申请(将供应商的状态改为"正式期")
            	newParam.put("STATUS", "1");
            	newParam.put("SUP_ID", applyMess.get("SUP_ID").toString());
            	creditService.updateSupStatus(newParam);
            }else if("2".equals(param.get("APPLY_TYPE").toString())){
            	//基础额度变更申请(将调整的基础额度和担保人额度回更到授信)
            	newParam.put("CREDIT_MONEY", applyMess.get("CREDIT_MONEY").toString());
            	newParam.put("GUARANT_MONEY", applyMess.get("GUARANT_MONEY").toString());
            	creditService.updateCreditMess(newParam);
            	
            }else if("3".equals(param.get("APPLY_TYPE").toString())){
            	//特别调整额度(将特别调整额度回更到授信)
            	newParam.put("SPECIAL_ADJUST_MONEY", applyMess.get("APPLY_MONEY").toString());
            	newParam.put("LAST_SPECIAL_LIMIT", applyMess.get("ADJUST_LIMIT").toString());
            	creditService.updateCreditMess(newParam);
            	
            }else if("4".equals(param.get("APPLY_TYPE").toString())){
            	//临时担保授信(将临时担保额度回更到授信)
            	newParam.put("TEMP_ASSURE_MONEY", applyMess.get("APPLY_MONEY").toString());
            	newParam.put("LAST_ASSURE_LIMIT", applyMess.get("ADJUST_LIMIT").toString());
            	creditService.updateCreditMess(newParam);
            	
            }else if("5".equals(param.get("APPLY_TYPE").toString())){
            	//一单一议授信(将申请的额度回更到授信)
            	newParam.put("SINGLE_MEET_MONEY", applyMess.get("APPLY_MONEY").toString());
            	creditService.updateCreditMess(newParam); 	
            }
            //变更申请单状态
            newParam.put("STATUS", "1");
            creditService.updateApplyDan(newParam);
		}
	}
	
	/**
	 * 评审不通过方法
	 * @param param
	 */
	public void creditAppNotPass(String jbpm_id){
		SupplierCreditService creditService = new SupplierCreditService();
		Map<String,Object> param = JBPM.getVeriable(jbpm_id);
		Map<String,Object> newParam = new  HashMap<String, Object>();
		//变更申请单状态
        newParam.put("STATUS", "0");
        newParam.put("APPLY_ID", param.get("APPLY_ID").toString());
        creditService.updateApplyDan(newParam);
	}
}
