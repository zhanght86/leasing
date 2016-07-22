package com.pqsoft.bpm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.ProcessInstance;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.util.StringUtils;

public class JBPMService {

	private TaskService tService = new com.pqsoft.bpm.service.TaskService();
	private String basePath = "task.service.";

	/**
	 * @Title: doStartProcess
	 * @Description: (流程发起)
	 * @return Reply
	 * @author 程龙达
	 * @throws
	 */
	public Reply doStartProcess(Map<String, Object> map) {
		Map<String, Object> param = map;
		// 发起流程
		String MODEL_NAME = StringUtils.nullToOther(param.get("MODEL_NAME"), "");
		if ("".equals(MODEL_NAME)) { throw new ActionException("未找到流程模块名称：" + MODEL_NAME); }
		List<String> prcessList = JBPM.getDeploymentListByModelName(MODEL_NAME);
		if (prcessList.size() > 0) {
			// 流程定义最大版本名称
			String jbpmExId = String.valueOf(prcessList.get(0));
			String msg = "";
			Map<String,Object> data = new HashMap<String,Object>() ;
			try {
				// 发起流程
				ProcessInstance jbpmIn = JBPM.startProcessInstanceById(jbpmExId, StringUtils.nullToString(param.get("projectId")),
						StringUtils.nullToString(param.get("clientId")), StringUtils.nullToString(param.get("payCode")), param);
				 String nextCode = new TaskService().getAssignee(jbpmIn.getId());
				 data.put("jbpmId", jbpmIn.getId());
				 data.put("nextCode", nextCode) ;
				msg = tService.getMytaskId(jbpmIn.getId());
				data.put("msg", "下一步操作人为: ") ;
				// 执行初始action
				List<Map<String, Object>> config = this.getConfigByParam(jbpmIn.getProcessDefinitionId(), "TYPE", "start");
				String INIT_OP = config.get(0) != null ? StringUtils.nullToString(((Map<String, Object>) config.get(0)).get("INIT_OP")) : "";

				// 打开流程处理界面
				// SkyEye.getRequest().getRequestDispatcher("/bpm/Task!toShow.action?taskId="+tService.getMytaskId(jbpmIn.getId())).forward(SkyEye.getRequest(),SkyEye.getResponse());
			} catch (Exception e) {
				throw new ActionException("流程发起异常，请联系管理员");
			}
			return new ReplyAjax(true, data, msg);
		} else {
			throw new ActionException("未找到流程：" + MODEL_NAME);
		}

	}

	public List<Map<String, Object>> getConfigByParam(String pdid, String key, String value) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("PDID", pdid);
		param.put("KEY", key);
		param.put("VALUE", value);
		List<Map<String, Object>> config = (List) Dao.selectList(basePath + "getConfigByPDID", param);
		return config;
	}
}
