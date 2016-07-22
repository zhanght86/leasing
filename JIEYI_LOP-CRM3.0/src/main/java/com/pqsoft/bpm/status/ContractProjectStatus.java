package com.pqsoft.bpm.status;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;

/**
 * 合同撤销
 * @author caizhognyang
 */
public class ContractProjectStatus {

	/**
	 * 审批通过状态
	 * @param ID
	 */
	public void contractProjectPass(String ID){
		if(StringUtils.isNotBlank(ID)){
			    Map<String,Object> param = new HashMap<String,Object>();
			    param.put("ID", ID);
			    param.put("STATUS", 100);
				param.put("CACELTYPE", 0);
				Dao.update("contractProjectManage.updateProjectCacelType",param);
		}else{
			throw new ActionException("通过状态变更失败");
		}
	}
	
	/**
	 * 审批不通过
	 * @param ID
	 */
	public void contractProjectNoPass(String ID){
		if(StringUtils.isNotBlank(ID)){
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("ID", ID);
			param.put("STATUS", 20);
			param.put("CACELTYPE",2);
			Dao.update("contractProjectManage.updateProjectCacelType",param);
		}else{
			throw new ActionException("不通过状态变更失败");
		}
	}
}
