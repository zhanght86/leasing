package com.pqsoft.bpm.status;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;

/**
 * 大项目子流程评审状态
 * @author King 2014-2-28
 */
public class ProjectSubStatus {
	private Map<String, Object> param = null;
	private String namespace = "bpm.status.";

	private void getVeriable(String jbpmId) {
		param = JBPM.getVeriable(jbpmId);
	}
	
	/**
	 * 评审通过
	 * @param jbpmId
	 * @author:King 2014-2-28
	 */
	public void subStatusPass(String jbpmId) {
		this.getVeriable(jbpmId);
		String project_id = null;
		if (param.containsKey("PROJECT_ID")) {
			project_id = param.get("PROJECT_ID") + "";
		}
		if (StringUtils.isNotEmpty(project_id)) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("PROJECT_ID", project_id);
			m.put("SUB_JBPM_STATUS", "0");
			Dao.update(namespace + "updateProjectSubStatus", m);
		} else {
			throw new ActionException("评审状态变更失败");
		}
	}
	/**
	 * 评审不通过
	 * @param jbpmId
	 * @author:King 2014-2-28
	 */
	public void subStatusNoPass(String jbpmId) {
		this.getVeriable(jbpmId);
		String project_id = null;
		if (param.containsKey("PROJECT_ID")) {
			project_id = param.get("PROJECT_ID") + "";
		}
		if (StringUtils.isNotEmpty(project_id)) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("PROJECT_ID", project_id);
			m.put("SUB_JBPM_STATUS", "1");
			Dao.update(namespace + "updateProjectSubStatus", m);
		} else {
			throw new ActionException("评审状态变更失败");
		}
	}
	
	/**
	 * 验证项目评审会状态
	 * @param jbpmId
	 * @author:King 2014-2-28
	 */
	public void queryProjectSubStatus(String jbpmId){
		this.getVeriable(jbpmId);
		String project_id = null;
		if (param.containsKey("PROJECT_ID")) {
			project_id = param.get("PROJECT_ID") + "";
		}
		if (StringUtils.isNotEmpty(project_id)) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("PROJECT_ID", project_id);
			String SUB_JBPM_STATUS=Dao.selectOne(namespace + "queryProjectSubStatus", m);
			if(StringUtils.isBlank(SUB_JBPM_STATUS)){
				throw new ActionException("评审会评审未结束，项目不能向下进行");
			}else if(SUB_JBPM_STATUS=="1"){
				throw new ActionException("评审不通过，项目不能向下进行");
			}
		} else {
			throw new ActionException("未找到评审项目");
		}
	}
}
