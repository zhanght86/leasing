package com.pqsoft.customers.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.StringUtils;

/**
 * 财务放款申请
 * @date 2016年1月5日 15:46:01
 * @author YClimb
 *
 */
public class ActualCostService {

	/**
	 * 申请费用保存/实际放款数据提交
	 * @param param
	 * @return
	 */
	public int updateActualCost(Map<String, Object> param) {
		
		String type = param.get("TYPE") + "";
		
		String[] SIGN = param.get("SIGN").toString().split(",");			// 页面选择的费用款项ID，以,号分隔
		String[] SIGNVALUE = param.get("SIGNVALUE").toString().split(",");	// 页面选择的费用款项值，以,号分隔
		String[] SIGNTYPE = null;
		String[] APPLYMONEY = null;
		
		// fee表示申请费用保存、loan表示实际放款数据保存
		if(StringUtils.isNotBlank(type)){
			if(type.equals("fee")){
				SIGNTYPE = param.get("SIGNTYPE").toString().split(",");		// 页面选择的申请对象值，以,号分隔
			}else if(type.equals("loan")){
				APPLYMONEY = param.get("APPLYMONEY").toString().split(",");	// 页面选择的请款金额值，以,号分隔
			}
		}
		
		int result = 0;
		
		for (int i = 0; i < SIGN.length; i++) {
			
			param.put("TYPE_ID", SIGN[i]);									// 费用类型
			
			// 申请费用保存
			if(StringUtils.isNotBlank(type)){
				if(type.equals("fee")){
					
					param.put("APPLY_MONEY", SIGNVALUE[i]);					// 请款金额
					param.put("APPLY_TYPE", SIGNTYPE[i]);					// 申请对象(总部、客户自付)
					param.put("APPLY_USER", Security.getUser().getName());	// 申请操作人
					
					// 判断当前条记录是否在数据库中已存在，如果存在，则执行修改操作，否则新增
					List<Map<String, Object>> list = Dao.selectList("paymentApply.findXmmxlistByCP", param);
					
					if(null != list && list.size() > 0){
						
						result += Dao.update("project.updateActualCost", param);	// 根据PROJECT_ID和TYPE_ID更新FIL_PROJECT_FEEDETAIL表数据
					}else{
						
						if(StringUtils.isBlank(SIGNVALUE[i]) || "0".equals(SIGNVALUE[i])){
							result += 1;
						}else{
							result += Dao.update("project.addActualCost", param);	// 根据PROJECT_ID和TYPE_ID更新FIL_PROJECT_FEEDETAIL表数据
						}
					}
					
				}else if(type.equals("loan")){
					
					// 实际放款数据提交
					param.put("ACTUAL_MONEY", SIGNVALUE[i]);						// 实际发生金额
					param.put("BALANCE", (Double.parseDouble(APPLYMONEY[i])-Double.parseDouble(SIGNVALUE[i])) + "");	// 差额
					param.put("IS_LOAN", "1");										// 是否放款
					param.put("LOAN_TIME", "sysdate");								// 放款时间
					
					result += Dao.update("project.updateActualCost", param);		// 根据PROJECT_ID和TYPE_ID更新FIL_PROJECT_FEEDETAIL表数据
				}
			}
			
		}
		
		return result;
	}

	/**
	 * 登记实际费用
	 * @param param
	 * @return
	 */
	public int regActualCost(Map<String, Object> param) {

		String[] DEALIS = param.get("DEALIS").toString().split(",");			// 页面选择的差额是否处理，以,号分隔
		String[] DEALUSER = param.get("DEALUSER").toString().split(",");		// 页面选择的处理人，以,号分隔
		String[] DEALREMARK = param.get("DEALREMARK").toString().split(",");	// 页面选择的备注，以,号分隔
		String[] APPLYTYPE = param.get("APPLYTYPE").toString().split(",");		// 页面选择的差额对象，以,号分隔
		
		int result = 0;
		
		for (int i = 0; i < DEALUSER.length; i++) {
			
			param.put("BALANCE_IS_DEAL", DEALIS[i]);							// 差额是否处理(0未处理，1已处理)
			param.put("BALANCE_DEAL_USER", DEALUSER[i]);						// 差额处理人
			param.put("BALANCE_DEAL_REMARK", DEALREMARK[i]);					// 差额处理备注
			param.put("APPLY_TYPE", APPLYTYPE[i]);								// 申请对象(总部、客户自付)
			
			
			param.put("REG_TIME", "sysdate");									// 登记时间
			param.put("REG_USER", Security.getUser().getName());				// 登记人
			
			result += Dao.update("project.regActualCost", param);				// 根据PROJECT_ID和APPLY_TYPE更新FIL_PROJECT_FEEDETAIL表数据
		}
		
		return result;
	}

}
