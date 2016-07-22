package com.pqsoft.bpm.status;

import java.util.Map;

import com.pqsoft.bpm.JBPM;

/**
 * 判断档案文件是否已经提交申请
 * @author King 2013-10-27
 */
public class DossierApplyStatus {
	/**
	 * 判断文件是否已经提交申请
	 * @param jbpmId
	 * @author:King 2013-10-27
	 */
	public void isDossierApply(String jbpmId){
		Map<String,Object> param=JBPM.getVeriable(jbpmId);
//		if(StringUtils.isBlank(param.get("DOSSIER_APPLY_ID"))){
//			throw new ActionException("请先选择项目所需合同文件信息");
//		}
	}
}
