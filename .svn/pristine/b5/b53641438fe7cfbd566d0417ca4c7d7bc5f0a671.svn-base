//package com.kernal.jbpm.service.impl;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.Reader;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.zip.ZipInputStream;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.jbpm.api.ExecutionService;
//import org.jbpm.api.HistoryService;
//import org.jbpm.api.ProcessDefinition;
//import org.jbpm.api.ProcessInstance;
//import org.jbpm.api.RepositoryService;
//import org.jbpm.api.TaskService;
//import org.jbpm.pvm.internal.history.model.HistoryTaskImpl;
//import org.jbpm.pvm.internal.task.TaskImpl;
//import org.jdom.Document;
//import org.jdom.Element;
//import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.PreparedStatementCallback;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import com.jbpm.utils.JBPMUtil;
//import com.kernal.dao.JdbcBaseDao;
//import com.kernal.jbpm.action.JbpmBaseAction;
//import com.kernal.jbpm.service.JbpmService;
//import com.kernal.springSecurity.PlatformUser;
//import com.kernal.utils.MD5Util;
//import com.kernal.utils.QueryUtil;
//import com.kernal.utils.StringUtil;
//import com.kernal.utils.UUIDUtil;
//import com.kernal.utils.XmlUtil;
//import java.io.StringReader;
//
//@SuppressWarnings("unchecked")
//public class JbpmServiceImpl implements JbpmService {
//	private final String JBPM_TASK_ADVISE = "currentTaskAdvise";
//	private JdbcBaseDao jbpmDao;
//
//	public void setJbpmDao(JdbcBaseDao jbpmDao) {
//		this.jbpmDao = jbpmDao;
//	}
//
//	public JdbcBaseDao getJbpmDao() {
//		return jbpmDao;
//	}
//
//	@Override
//	public String deployWorkflow(String zipFilePath) throws Exception {
//		String fileName = zipFilePath.substring(zipFilePath.lastIndexOf("\\") + 1, zipFilePath.lastIndexOf(".zip"));
//		RepositoryService repositoryService = JBPMUtil.getRepositoryService();
//		ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath));
//		String newDeployId = repositoryService.createDeployment().addResourcesFromZipInputStream(zis).deploy();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String deploymentTimestamp = sdf.format(new java.util.Date());
//		this.getJbpmDao().doUpdateBySql(
//				"insert into t_deploy(id,deployid,deploydesc,deploytime) values('" + UUIDUtil.getUUID() + "'," + newDeployId + ",'" + fileName
//						+ "','" + deploymentTimestamp + "')");
//		zis.close();
//		String sql = "select blob_value_ as 文件内容 from jbpm4_lob where name_ like '%.xml' and deployment_=" + newDeployId;
//		String xmlContent = JBPMUtil.queryBlob(this.getJbpmDao(), sql);
//		Document document = XmlUtil.readXML(xmlContent, true);
//		Element root = document.getRootElement();
//		List childrenElements = root.getChildren();
//		List sqls_list = new ArrayList();
//		for (int i = 0; i < childrenElements.size(); i++) {
//			StringBuffer insertSql_sb = new StringBuffer("insert into t_deployDetail(id,deployId,activityName,activityType,activityPosition) values(");
//			Element childElement = (Element) childrenElements.get(i);
//			if ((!"start".equals(childElement.getName().toLowerCase())) && (!"task".equals(childElement.getName().toLowerCase()))
//					&& ((childElement.getName().toLowerCase()).indexOf("end") == -1)) {
//				continue;
//			}
//			insertSql_sb.append("'" + UUIDUtil.getUUID() + "'").append("," + newDeployId)
//					.append(",'" + StringUtil.nullToString(childElement.getAttributeValue("name")) + "'").append(",'" + childElement.getName() + "'")
//					.append("," + (i + 1)).append(" )");
//			sqls_list.add(insertSql_sb.toString());
//		}
//		document = null;
//		XmlUtil.closeLocalResources();
//		String[] sqls = new String[sqls_list.size()];
//		sqls = (String[]) sqls_list.toArray(sqls);
//		this.getJbpmDao().doBatchInsert(sqls);
//		return null;
//	}
//
//	@Override
//	public void removeDeployedWorkflow(String deployId) throws Exception {
//		RepositoryService repositoryService = JBPMUtil.getRepositoryService();
//		repositoryService.deleteDeploymentCascade(deployId);
//		this.getJbpmDao().doUpdateBySql("delete from t_deploy where deployid=" + deployId);
//		this.getJbpmDao().doUpdateBySql("delete from t_deployDetail where deployid=" + deployId);
//		this.getJbpmDao().doUpdateBySql("delete from t_jbpm_hist_var where deployid=" + deployId);
//		this.getJbpmDao().doUpdateBySql("delete from t_last_task where deployid=" + deployId);
//	}
//
//	@Override
//	public String removeProcessInstance(String processInstanceId, String deleteAction, HttpServletRequest request) throws Exception {
//		Map<String, String> processInstanceMap = this.getHistoryProcessInstanceDataMap(processInstanceId);
//		String returnValue = ((JbpmBaseAction) Class.forName(deleteAction).newInstance()).deleteProcessInstance(request, processInstanceMap,
//				this.jbpmDao);
//		ExecutionService executionService = JBPMUtil.getExecutionService();
//		executionService.deleteProcessInstanceCascade(processInstanceId);
//		String deleteSql = "delete from  t_jbpm_hist_var where hprocessinstance_id='" + processInstanceId + "'";
//		this.getJbpmDao().doUpdateBySql(deleteSql);
//		return returnValue;
//	}
//
//	public List getWorkflowActivitiesSetting(String deployId) throws Exception {
//		StringBuffer sql_sb = new StringBuffer("");
//		sql_sb.append(" \n   select  ").append(" \n            id            as 记录编号, ").append(" \n            deployid      as 部署编号, ")
//				.append(" \n            activityType  as 节点类型, ").append(" \n            activityName  as 节点名称, ")
//				.append(" \n            initiatorType as 执行人类型, ").append(" \n            initiator     as 执行人, ")
//				.append(" \n            startAction   as 开始动作, ").append(" \n            saveAction    as 保存动作, ")
//				.append(" \n            rollbackAction    as 退回动作, ").append(" \n            endAction     as 结束动作, ")
//				.append(" \n            deleteAction  as 删除动作, ").append(" \n            formPath      as 应用表单, ")
//				.append(" \n            formTitle     as 应用表单标题, ").append(" \n            submitbuttons      as 提交按钮,")
//				.append(" \n            isneedadvise       as 是否填写意见,").append(" \n            submitroutesnodes       as 提交路由节点 ")
//				.append(" \n        from t_deploydetail ").append(" \n        where deployid=" + deployId)
//				.append(" \n   order by (case activityType when 'start' then '00' when 'end' then '99' else '1'||activityName end) asc ");
//		List activitiesList = this.getJbpmDao().doQueryBySql(sql_sb.toString());
//		return activitiesList;
//	}
//
//	@Override
//	public void saveChangeToActivity(Map<String, Object> object) throws Exception {
//		this.getJbpmDao().doUpdateExecuteByID("t_deploydetail", "id", StringUtil.nullToString(object.get("id")), object);
//	}
//
//	@Override
//	public String startDeployedProcess(HttpServletRequest request, String processDefinitionId, Map modelData) throws Exception {
//		RepositoryService repositoryService = JBPMUtil.getRepositoryService();
//		ExecutionService executionService = JBPMUtil.getExecutionService();
//
//		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId)
//				.uniqueResult();
//		String deployId = "";
//		try {
//			deployId = processDefinition.getDeploymentId();
//		} catch (Exception e) {
//			System.out.println("INFO 流程发起异常，流程定义：" + processDefinitionId);
//			e.printStackTrace();
//			throw new Exception("流程发起异常");
//		}
//
//		String returnFormPath = "";
//		List activitiesList = this.getWorkflowActivitiesSetting(deployId);
//		// 从前台作为参数传递的请求transition路径(优先级高于所有)
//		String requestInitiatorRoute = "";
//		String tempRequestInitiatorRoute = StringUtil.nullToString(modelData.get("requestInitiatorRoute"));
//		Map variablesMap = modelData;
//		PlatformUser user = (PlatformUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		variablesMap.put("requestInitiator", user.getUsername());
//		variablesMap.put("requestInitiatorName", user.getRealName());
//		variablesMap.put("processDeployId", deployId);
//		variablesMap.put("processDefinitionId", processDefinition.getId());
//		variablesMap.put("processDefinitionName", processDefinition.getName());
//		variablesMap.put("processInstanceState", "草稿");
//
//		String queryString = request.getQueryString();
//		int mathRandomIndex = queryString.indexOf("mathRandom=");
//		if (mathRandomIndex > 0) {
//			queryString = queryString.substring(0, mathRandomIndex);
//		}
//		String draftUUID = MD5Util.getstrs(queryString);
//		variablesMap.put("draftUUID", draftUUID);
//		// 瞬时map
//		Map<String, Object> transientMap = null;
//		{
//			// 流程草稿处理
//			{
//				String currentProcessInstanceId = "";
//				Map activityMap = (Map) activitiesList.get(1);
//				TaskService taskService = JBPMUtil.getTaskService();
//				/* String sql_query_processInstance =
//				 * "select 流程实例编号  from vi_hist_processinstance_info tProcessInstance where tProcessInstance.流程实例处理状态='草稿' and tProcessInstance.流程定义编号='"
//				 * +
//				 * processDefinitionId+"' and tProcessInstance.草稿唯一标识='"+draftUUID
//				 * +"'"; */
//				String sql_query_processInstance = "select hprocessinstance_id as 流程实例编号  from t_jbpm_hist_var tProcessInstance where  htask_dbid is null and tProcessInstance.processInstance_state='草稿' and htask_dbid is null and tProcessInstance.processdefinition_Id='"
//						+ processDefinitionId + "' and tProcessInstance.draftUUID='" + draftUUID + "'";
//				List infos = this.getJbpmDao().doQueryBySql(sql_query_processInstance);
//				if (infos.size() >= 1) {
//					Map queryProcessInstanceMapInfo = (Map) infos.get(0);
//					currentProcessInstanceId = StringUtil.nullToString(queryProcessInstanceMapInfo.get("流程实例编号"));
//					TaskImpl currentTask = (TaskImpl) taskService.createTaskQuery().processInstanceId(currentProcessInstanceId).uniqueResult();
//					transientMap = this.getTransientTaskMap(deployId, currentProcessInstanceId, currentTask.getId(), currentTask.getActivityName(),
//							activityMap, variablesMap, request.getSession().getServletContext().getServerInfo());
//					variablesMap.put("processInstanceId", currentProcessInstanceId);
//					variablesMap.putAll(transientMap);
//					// 流程实例中的变量
//					Map contextMapVariables = this.getHistoryProcessInstanceDataMap(currentProcessInstanceId);
//					contextMapVariables.putAll(variablesMap);
//					variablesMap.putAll(contextMapVariables);
//					return currentTask.getFormResourceName();
//				}
//			}
//		}
//		for (int i = 0; i < activitiesList.size(); i++) {
//			Map activityMap = (Map) activitiesList.get(i);
//			String activityType = StringUtil.nullToString(activityMap.get("节点类型"));
//			if ("start".equalsIgnoreCase(activityType)) {
//				String startAction = StringUtil.nullToString(activityMap.get("开始动作")).trim();
//				if (!"".equals(startAction)) {
//					JbpmBaseAction startEventAction = ((JbpmBaseAction) Class.forName(startAction).newInstance());
//					requestInitiatorRoute = StringUtil.empty2Other(startEventAction.appointRequestRoute(request, variablesMap, this.jbpmDao),
//							tempRequestInitiatorRoute);
//					startEventAction.execute(request, variablesMap, this.jbpmDao);
//				}
//				String endAction = StringUtil.nullToString(activityMap.get("结束动作")).trim();
//				if (!"".equals(endAction)) {
//					JbpmBaseAction endEventAction = ((JbpmBaseAction) Class.forName(endAction).newInstance());
//					endEventAction.execute(request, variablesMap, this.jbpmDao);
//				}
//			}
//			// 当流程开始时执行action
//		}
//		ProcessInstance pi = null;
//		if ("".equals(requestInitiatorRoute.trim())) {
//			pi = executionService.startProcessInstanceById(processDefinitionId);
//		} else {
//			pi = executionService.startProcessInstanceById(processDefinitionId, requestInitiatorRoute);
//		}
//		String processInstanceId = pi.getId();
//		variablesMap.put("processInstanceId", processInstanceId);
//		if (!pi.isEnded()) {
//			Set<String> activityNames = pi.findActiveActivityNames();
//			TaskService taskService = JBPMUtil.getTaskService();
//			for (String activityName_ : activityNames) {
//				TaskImpl task = (TaskImpl) taskService.createTaskQuery().processInstanceId(processInstanceId).activityName(activityName_)
//						.uniqueResult();
//				for (int i = 0; i < activitiesList.size(); i++) {
//					Map activityMap = (Map) activitiesList.get(i);
//					String activityType = StringUtil.nullToString(activityMap.get("节点类型"));
//					if ("task".equalsIgnoreCase(activityType)) {
//						String activityName = StringUtil.nullToString(activityMap.get("节点名称"));
//						if (activityName_.equals(activityName)) {
//							String initiator = StringUtil.nullToString(activityMap.get("执行人"));
//							String formPath = StringUtil.nullToString(activityMap.get("应用表单"));
//
//							initiator = user.getUsername();
//							String startAction_task = StringUtil.nullToString(activityMap.get("开始动作")).trim();
//							String actionInitiator = "";
//							String actionFormPath = "";
//							if (!"".equals(startAction_task)) {
//								JbpmBaseAction startEventAction = ((JbpmBaseAction) Class.forName(startAction_task).newInstance());
//								actionInitiator = startEventAction.appointExecuteActor(request, variablesMap, this.jbpmDao);
//								actionFormPath = startEventAction.appointFormPath(request, variablesMap, this.jbpmDao);
//								startEventAction.execute(request, variablesMap, this.jbpmDao);
//							}
//							initiator = StringUtil.empty2Other(actionInitiator, initiator);
//							formPath = StringUtil.empty2Other(actionFormPath, formPath);
//							if (!initiator.isEmpty()) {
//								task.setAssignee(initiator);
//								HistoryService historyService = JBPMUtil.getHistoryService();
//								HistoryTaskImpl historyTaskImpl = (HistoryTaskImpl) historyService.createHistoryTaskQuery().taskId(task.getId())
//										.uniqueResult();
//								historyTaskImpl.setAssignee(initiator);
//							}
//							if (!formPath.isEmpty()) {
//								task.setFormResourceName(formPath);
//							}
//							if (user.getUsername().equals(task.getAssignee())) {
//								returnFormPath = task.getFormResourceName();
//								transientMap = this.getTransientTaskMap(deployId, processInstanceId, task.getId(), task.getActivityName(),
//										activityMap, variablesMap, request.getSession().getServletContext().getServerInfo());
//								transientMap.put("firstActivityName", activityName);
//							}
//						}
//					}
//				}
//				// 实现保存流程实例dbid
//				long tempHTaskDBId = task.getDbid();
//				String tempProcessFormPath = task.getFormResourceName();
//				// 保存流程task历史数据
//				this.insertHistVarExec_info(this.getJbpmDao(), deployId, processInstanceId, tempHTaskDBId, tempProcessFormPath, variablesMap);
//				// 保存整个流程实例历史数据
//			}
//			executionService.setVariables(processInstanceId, new HashMap());
//			String hProcessInstanceId = processInstanceId;
//			long hTaskDBId = -1;
//			this.insertHistVarExec_info(this.getJbpmDao(), deployId, hProcessInstanceId, hTaskDBId, returnFormPath, variablesMap);
//			// 追加task附加信息
//			variablesMap.putAll(transientMap);
//		} else {
//			System.out.println("此流程没有可操作的人工节点，属于不规范流程");
//			return null;
//		}
//		return returnFormPath;
//	}
//
//	public Map<String, Object> getTransientTaskMap(String deployId, String processInstanceId, String taskId, String taskName,/* TaskImpl
//																															 * task
//																															 * , */
//			Map activityMap, Map variablesMap, String containerName) throws Exception {
//		Map<String, Object> transientMap = new HashMap<String, Object>();
//		// 追加task附加信息，但是变量不进行数据库存储
//		String currentInitiatorType = StringUtil.nullToString(activityMap.get("执行人类型"));
//		String submitRoutesNodes = StringUtil.nullToString(activityMap.get("提交路由节点"));
//		// String taskName = task.getActivityName();
//
//		transientMap.put("currentTaskId", taskId);
//		transientMap.put("currentDeployId", deployId);
//		transientMap.put("currentTaskName", taskName);
//		transientMap.put("currentProcessInstanceId", processInstanceId);
//		transientMap.put("currentTaskFormPath", StringUtil.nullToString(activityMap.get("应用表单")));
//		transientMap.put("currentTaskFormTitle", QueryUtil.getQueryStringByRequest(variablesMap, StringUtil.nullToString(activityMap.get("应用表单标题"))));
//		transientMap.put("currentTaskSubmitButtonsStr", StringUtil.nullToString(activityMap.get("提交按钮")).replaceAll("；", ";"));
//		transientMap.put("currentTaskIsNeedAdvise", StringUtil.nullToString(activityMap.get("是否填写意见")));
//		transientMap.put("currentTaskSaveAction", StringUtil.nullToString(activityMap.get("保存动作")));
//		transientMap.put("currentRollbackAction", StringUtil.nullToString(activityMap.get("退回动作")));
//		transientMap.put("currentTaskDeleteAction", StringUtil.nullToString(activityMap.get("删除动作")).trim());
//		transientMap.put("currentSubmitRoutesNodes", submitRoutesNodes);
//		transientMap.put("currentInitiatorType", currentInitiatorType);
//
//		// 回退list
//		Map<String, Map<String, String>> allWorkFlowNodesTransitions = JBPMUtil.getAllWorkFlowNodesTransitions(deployId, this.getJbpmDao(),
//				containerName);
//
//		Map<String, List<Map<String, String>>> next_routeDealMap = new HashMap<String, List<Map<String, String>>>();
//		Map<String, List<Map<String, String>>> back_routeDealMap = new HashMap<String, List<Map<String, String>>>();
//
//		Set<String> next_allWorkFlowNodesSet = new HashSet<String>();
//		Map<String, String> allWorkFlowNodesInfoMap = allWorkFlowNodesTransitions.get(taskName);
//
//		String[] submitRoutesArr = submitRoutesNodes.split(",");
//		for (int i_ = 0; i_ < submitRoutesArr.length; i_++) {
//			String nodeNameKey = submitRoutesArr[i_];
//			next_allWorkFlowNodesSet.add(nodeNameKey);
//		}
//		List nodesSettingList = this.getWorkflowActivitiesSetting(deployId);
//		Set<String> allRoutesSet = allWorkFlowNodesInfoMap.keySet();
//
//		Map<String, List<Map<String, String>>> allRouteDealMap = new LinkedHashMap<String, List<Map<String, String>>>();
//		{
//			for (int index = 0; index < nodesSettingList.size(); index++) {
//				Map nodeMap = (Map) nodesSettingList.get(index);
//				String nodeName = StringUtil.nullToString(nodeMap.get("节点名称"));
//				String routeName = nodeName;
//				if (!allRoutesSet.contains(nodeName))// 去除非task节点类型
//				{
//					continue;
//				} else {
//					List<Map<String, String>> workFlowDealersMapList = new ArrayList<Map<String, String>>();
//					String initiatorType = StringUtil.nullToString(nodeMap.get("执行人类型"));
//					// 发起人
//					if ("requestInitiator".equals(initiatorType)) {
//						String requestInitiator = StringUtil.nullToString(variablesMap.get("requestInitiator"));
//						String requestInitiatorName = StringUtil.nullToString(variablesMap.get("requestInitiatorName"));
//						JBPMUtil.getRequestInitiatorDealer(requestInitiator, requestInitiatorName, this.getJbpmDao(), workFlowDealersMapList);
//					} else if ("manager".equals(initiatorType))// 部门经理
//					{
//						String requestInitiator = StringUtil.nullToString(variablesMap.get("requestInitiator"));
//						JBPMUtil.getManagerDealer(requestInitiator, this.getJbpmDao(), workFlowDealersMapList);
//					} else if ("group".equals(initiatorType))// 群组
//					{
//						String initiator = StringUtil.nullToString(nodeMap.get("执行人"));
//						String groupName = initiator;
//						JBPMUtil.getAllDealersByGroup(workFlowDealersMapList, groupName, this.getJbpmDao());
//					} else if ("variable".equals(initiatorType)) {
//						String initiator = StringUtil.nullToString(nodeMap.get("执行人"));
//						JBPMUtil.getVariableDealer(initiator, variablesMap, this.getJbpmDao(), workFlowDealersMapList);
//					} else if ("sql".equals(initiatorType)) {
//						String initiator = StringUtil.nullToString(nodeMap.get("执行人"));
//						JBPMUtil.getSQLDealer(initiator, variablesMap, this.getJbpmDao(), workFlowDealersMapList);
//					}
//					String routeTransitionName = allWorkFlowNodesInfoMap.get(routeName);
//					String key = routeName + "," + routeTransitionName;
//					if (next_allWorkFlowNodesSet.contains(routeName)) {
//						next_routeDealMap.put(key, workFlowDealersMapList);
//					} else {
//						back_routeDealMap.put(key, workFlowDealersMapList);
//					}
//					allRouteDealMap.put(key, workFlowDealersMapList);
//				}
//			}
//		}
//		transientMap.put("allRouteDealMap", allRouteDealMap);
//		transientMap.put("next_routeDealMap", next_routeDealMap);
//		transientMap.put("back_routeDealMap", back_routeDealMap);
//
//		List processedHistoryLogInfoList = null;
//		StringBuffer querySql_sb = new StringBuffer("");
//		querySql_sb.append(" \n   select ttask.*,tusers.xm as 真实姓名  ").append(" \n   from( ")
//				.append(" \n     select *  from vi_hist_task_info t  where  t.流程实例编号='" + processInstanceId + "' order by t.节点结束时间  desc")
//				.append(" \n   )ttask left join( ").append(" \n       select * from jb_yhxx tu ")
//				.append(" \n   )tusers on ttask.节点执行人= tusers.login_name order by ttask.节点结束时间  desc");
//		processedHistoryLogInfoList = this.getJbpmDao().doQueryBySql(querySql_sb.toString());
//		transientMap.put("processedHistoryLogInfoList", processedHistoryLogInfoList);
//		return transientMap;
//	}
//
//	/**
//	 * @param deployId
//	 *            部署编号
//	 * @param hProcessInstanceId
//	 *            流程实例编号
//	 * @param hTaskDBId
//	 *            历史任务编号
//	 * @param processFormPath
//	 *            处理表单路径
//	 * @param variablesMap
//	 *            任务节点变量列表
//	 * @return 返回需要执行的批量sql历史数据信息
//	 */
//	@Override
//	public void insertHistVarExec_info(JdbcBaseDao jbpmDao, String deployId, String hProcessInstanceId, final long hTaskDBId, String processFormPath,
//			Map variablesMap) throws Exception {
//		final StringBuffer varNameListString_sb = new StringBuffer("");
//		final StringBuffer varValueListString_sb = new StringBuffer("");
//
//		Iterator iter = variablesMap.keySet().iterator();
//		String advise = StringUtil.nullToString(variablesMap.get(this.JBPM_TASK_ADVISE));
//
//		String currentTaskSubmitButtonText = StringUtil.empty2Other(StringUtil.nullToString(variablesMap.get("currentTaskSubmitButtonText")), "发起");
//
//		while (iter.hasNext()) {
//			String varName = StringUtil.nullToString(iter.next());
//			if (varName.isEmpty()) continue;
//			String varValue = StringUtil.nullToString(variablesMap.get(varName));
//			varNameListString_sb.append("@@_@@").append(varName);
//			varValueListString_sb.append("@@_@@").append(varValue);
//		}
//		boolean isSaveOperation = "保存".equals(currentTaskSubmitButtonText);
//		if (-1 == hTaskDBId) {
//			if (isSaveOperation) {
//				String sql = "select OPERATION_BUTTON_TEXT from t_jbpm_hist_var where htask_dbid is null and hprocessinstance_id='"
//						+ hProcessInstanceId + "'";
//				List l = jbpmDao.doQueryBySql(sql);
//				if (l.size() > 0) {
//					Map m = (Map) l.get(0);
//					currentTaskSubmitButtonText = StringUtil.nullToString(m.get("OPERATION_BUTTON_TEXT"));
//				}
//			}
//			jbpmDao.doUpdateBySql("delete from t_jbpm_hist_var where htask_dbid is null and hprocessinstance_id='" + hProcessInstanceId + "'");
//		} else {
//			if (isSaveOperation) {
//				String sql = "select OPERATION_BUTTON_TEXT from t_jbpm_hist_var where htask_dbid=" + hTaskDBId + " and hprocessinstance_id='"
//						+ hProcessInstanceId + "'";
//				List l = jbpmDao.doQueryBySql(sql);
//				if (l.size() > 0) {
//					Map m = (Map) l.get(0);
//					currentTaskSubmitButtonText = StringUtil.nullToString(m.get("OPERATION_BUTTON_TEXT"));
//				}
//			}
//			jbpmDao.doUpdateBySql("delete from t_jbpm_hist_var where htask_dbid=" + hTaskDBId + " and hprocessinstance_id='" + hProcessInstanceId
//					+ "'");
//		}
//		String proj_id = StringUtil.nullToString(variablesMap.get("ProjectNo"));
//		String cust_id = StringUtil.empty2Other(StringUtil.nullToString(variablesMap.get("CustomerNumber")), "-1");
//		String cust_type = StringUtil.nullToString(variablesMap.get("CustomerType"));
//		String draftUUID = StringUtil.nullToString(variablesMap.get("draftUUID"));
//		String processDefinitionId = StringUtil.nullToString(variablesMap.get("processDefinitionId"));
//		String processInstanceState = StringUtil.nullToString(variablesMap.get("processInstanceState"));
//		// 发起人
//		String requestInitiator = StringUtil.nullToString(variablesMap.get("requestInitiator"));
//		String requestInitiatorName = StringUtil.nullToString(variablesMap.get("requestInitiatorName"));
//
//		final PlatformUser user = (PlatformUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		final StringBuffer sql_sb = new StringBuffer(
//				" insert into t_jbpm_hist_var(id,deployId,hprocessinstance_id,htask_dbid,process_form_path,advise,var_name,var_value,actorRealName,delegateuser,delegaterealName,proj_id,cust_id,cust_type,OPERATION_BUTTON_TEXT,draftUUID,processInstance_State,processDefinition_Id,requestInitiator,requestInitiatorName) values(");
//		sql_sb.append("'" + UUIDUtil.getUUID() + "'").append("," + deployId).append(",'" + hProcessInstanceId + "'");
//		if (-1 != hTaskDBId) {
//			sql_sb.append("," + hTaskDBId);
//		} else {
//			sql_sb.append(",null");
//		}
//		if (!StringUtil.nullToString(processFormPath).isEmpty()) {
//			sql_sb.append(",'" + processFormPath + "'");
//		} else {
//			sql_sb.append(",null");
//		}
//		// 写入意见
//		if (!StringUtil.nullToString(advise).isEmpty()) {
//			sql_sb.append(",'" + advise + "'");
//		} else {
//			sql_sb.append(",null");
//		}
//		sql_sb.append(",?,?");
//		sql_sb.append(",'" + user.getRealName() + "'").append(",'" + user.getUsername() + "'").append(",'" + user.getRealName() + "'")
//				.append(",'" + proj_id + "'").append(",'" + cust_id + "'").append(",'" + cust_type + "'")
//				.append(",'" + currentTaskSubmitButtonText + "'").append(",'" + draftUUID + "'").append(",'" + processInstanceState + "'")
//				.append(",'" + processDefinitionId + "'").append(",'" + requestInitiator + "'").append(",'" + requestInitiatorName + "'").append(")");
//		jbpmDao.getJdbcTemplate().execute(sql_sb.toString(), new PreparedStatementCallback() {
//			@Override
//			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//				String varNameListString = varNameListString_sb.substring(5, varNameListString_sb.length());
//				String varValueListString = varValueListString_sb.substring(5, varValueListString_sb.length());
//				Reader clobReader_varNameListString = new StringReader(varNameListString); // 将
//																							// varNameListString转成流形式
//				Reader clobReader_varValueListString = new StringReader(varValueListString); // 将
//																								// text转成流形式
//				ps.setCharacterStream(1, clobReader_varNameListString, varNameListString.length());// 替换sql语句中的？
//				ps.setCharacterStream(2, clobReader_varValueListString, varValueListString.length());// 替换sql语句中的？
//				int num = ps.executeUpdate();// 执行SQL
//				try {
//					clobReader_varValueListString.close();
//					clobReader_varNameListString.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//					throw new SQLException("IO读写异常");
//				}
//				String saveObject = "实例";
//				if (-1 != hTaskDBId) {
//					saveObject = "节点";
//				}
//				if (num > 0) {
//					System.out.println("流程【" + saveObject + "】历史数据插入成功,执行人：【" + user.getRealName() + "】");
//				} else {
//					System.out.println("流程【" + saveObject + "】历史数据插入失败,执行人：【" + user.getRealName() + "】");
//				}
//				return null;
//			}
//		});
//	}
//
//	// @Override
//	public void getRequestFormDealedInfo(HttpServletRequest request, String deployId, String processInstanceId, String currentTaskId,
//			String currentTaskName, Map modelData) throws Exception {
//		List activitiesList = this.getWorkflowActivitiesSetting(deployId);
//		// 处理提交按钮
//		Map<String, Object> transientMap = null;
//		for (int i = 0; i < activitiesList.size(); i++) {
//			Map activityMap = (Map) activitiesList.get(i);
//			String activityName = StringUtil.nullToString(activityMap.get("节点名称"));
//			if (currentTaskName.equals(activityName)) {
//				transientMap = this.getTransientTaskMap(deployId, processInstanceId, currentTaskId, currentTaskName, activityMap, modelData, request
//						.getSession().getServletContext().getServerInfo());
//				break;
//			}
//		}
//		modelData.putAll(transientMap);
//	}
//
//	@Override
//	public String requestProcessTaskForm(HttpServletRequest request, String currentTaskId, Map modelData) throws Exception {
//		TaskService taskService = JBPMUtil.getTaskService();
//		TaskImpl task = (TaskImpl) taskService.getTask(currentTaskId);
//		ProcessInstance pi = task.getProcessInstance();
//		String currentTaskName = task.getActivityName();
//		modelData.put("currentTaskName", currentTaskName);
//		RepositoryService repositoryService = JBPMUtil.getRepositoryService();
//		String deployId = repositoryService.createProcessDefinitionQuery().processDefinitionId(pi.getProcessDefinitionId()).uniqueResult()
//				.getDeploymentId();
//		String formPath = task.getFormResourceName();
//		String processInstanceId = pi.getId();
//		// 流程实例中的变量
//		Map contextMapVariables = this.getHistoryProcessInstanceDataMap(processInstanceId);
//		contextMapVariables.putAll(modelData);
//		// request中的变量
//		modelData.putAll(contextMapVariables);
//		// List activitiesList = this.getWorkflowActivitiesSetting(deployId);
//		// //处理提交按钮
//		// Map<String,Object> transientMap = null;
//		// for(int i=0;i<activitiesList.size();i++)
//		// {
//		// Map activityMap = (Map)activitiesList.get(i);
//		// String activityName =
//		// StringUtil.nullToString(activityMap.get("节点名称"));
//		// if(activityName_.equals(activityName))
//		// {
//		// transientMap =
//		// this.getTransientTaskMap(deployId,processInstanceId,task.getId(),task.getActivityName(),
//		// activityMap,
//		// modelData,request.getSession().getServletContext().getServerInfo());
//		// break;
//		// }
//		// }
//		// modelData.putAll(transientMap);
//		this.getRequestFormDealedInfo(request, deployId, processInstanceId, currentTaskId, currentTaskName, modelData);
//		return formPath;
//	}
//
//	@Override
//	public String submitProcessedTaskForm(HttpServletRequest request, String currentTaskId, Map modelData) throws Exception {
//		String currentTaskSaveAction = StringUtil.nullToString(request.getParameter("currentTaskSaveAction"));
//		String currentTaskSubmitButtonText = StringUtil.nullToString(request.getParameter("currentTaskSubmitButtonText"));
//		String saveActionReturnResult = "";
//		RepositoryService repositoryService = JBPMUtil.getRepositoryService();
//		TaskService taskService = JBPMUtil.getTaskService();
//		ExecutionService executionService = JBPMUtil.getExecutionService();
//
//		String lastDeployId = "";
//		String lastTaskProcessInstanceId = "";
//		long lastTaskDbId = 0;
//		String lastTaskName = "";
//		String lastTaskActor = "";
//
//		TaskImpl task = (TaskImpl) taskService.getTask(currentTaskId);
//
//		ProcessInstance pi = task.getProcessInstance();
//		String processInstanceId = pi.getId();
//		String tempHProcessInstanceId = processInstanceId;
//
//		String currentActivityName = task.getActivityName();
//		String deployId = repositoryService.createProcessDefinitionQuery().processDefinitionId(pi.getProcessDefinitionId()).uniqueResult()
//				.getDeploymentId();
//		String requestInitiatorRoute = StringUtil.nullToString(modelData.get("requestInitiatorRoute"));
//
//		// 保存last task 信息
//		lastDeployId = deployId;
//		lastTaskProcessInstanceId = tempHProcessInstanceId;
//		lastTaskDbId = task.getDbid();
//		lastTaskName = task.getActivityName();
//		lastTaskActor = task.getAssignee();
//		// 流程实例中的变量
//		Map contextMapVariables = this.getHistoryProcessInstanceDataMap(tempHProcessInstanceId);
//		contextMapVariables.putAll(modelData);
//		// 需要传递到下一个task的map变量列表
//		modelData.putAll(contextMapVariables);
//		Map variablesMap = modelData;
//		boolean isSaveButton = false;
//
//		if ((!currentTaskSaveAction.trim().isEmpty()) && ("保存".equals(currentTaskSubmitButtonText))) {
//			isSaveButton = true;
//			saveActionReturnResult = ((JbpmBaseAction) Class.forName(currentTaskSaveAction).newInstance()).save(request, modelData, this.jbpmDao);
//		}
//
//		String tempProcessTaskFormPath = task.getFormResourceName();
//		String tempProcessInstancePath = tempProcessTaskFormPath;
//		// 回调action执行
//		if (!isSaveButton) {
//			List activitiesList = this.getWorkflowActivitiesSetting(deployId);
//			for (int i = 0; i < activitiesList.size(); i++) {
//				Map activityMap = (Map) activitiesList.get(i);
//				String activityType = StringUtil.nullToString(activityMap.get("节点类型"));
//				if ("task".equalsIgnoreCase(activityType)) {
//					String activityName = StringUtil.nullToString(activityMap.get("节点名称"));
//					if (currentActivityName.equals(activityName)) {
//						String endAction = StringUtil.nullToString(activityMap.get("结束动作")).trim();
//						if (!"".equals(endAction)) {
//							JbpmBaseAction endEventAction = ((JbpmBaseAction) Class.forName(endAction).newInstance());
//							// 取出action中定义的路由走向
//							requestInitiatorRoute = StringUtil.empty2Other(endEventAction.appointRequestRoute(request, variablesMap, this.jbpmDao),
//									requestInitiatorRoute);
//							// 处理事务逻辑
//							endEventAction.execute(request, variablesMap, this.jbpmDao);
//						}
//						// 下一步transition路由走向
//						if (requestInitiatorRoute.trim().isEmpty()) {
//							taskService.completeTask(currentTaskId);
//						} else {
//							taskService.completeTask(currentTaskId, requestInitiatorRoute);
//						}
//
//						variablesMap.put("processInstanceState", "待处理");
//						// 流程没有结束
//						if (!pi.isEnded()) {
//							{
//								Set<String> activityNames = pi.findActiveActivityNames();
//								for (String activityName_ : activityNames) {
//									{
//										TaskImpl task_inner = (TaskImpl) taskService.createTaskQuery().processInstanceId(processInstanceId)
//												.activityName(activityName_).uniqueResult();
//										for (int iii = 0; iii < activitiesList.size(); iii++) {
//											Map activityMap_inner = (Map) activitiesList.get(iii);
//											String activityType_inner = StringUtil.nullToString(activityMap_inner.get("节点类型"));
//											if ("task".equalsIgnoreCase(activityType_inner)) {
//												String activityName_inner = StringUtil.nullToString(activityMap_inner.get("节点名称"));
//												if (activityName_.equals(activityName_inner)) {
//													String formPath = StringUtil.nullToString(activityMap_inner.get("应用表单"));
//													// 获取应用配置的数值
//													formPath = QueryUtil.getQueryStringByRequest(variablesMap, formPath);
//													// 获取下次执行人
//													String initiator = StringUtil.nullToString(modelData.get("changeRequestInitiator"));
//													String startAction_task = StringUtil.nullToString(activityMap_inner.get("开始动作")).trim();
//													String actionInitiator = "";
//													String actionFormPath = "";
//													if (!"".equals(startAction_task)) {
//														// 执行开始动作的action
//														JbpmBaseAction startEventAction = ((JbpmBaseAction) Class.forName(startAction_task)
//																.newInstance());
//														actionInitiator = startEventAction.appointExecuteActor(request, variablesMap, this.jbpmDao);
//														actionFormPath = startEventAction.appointFormPath(request, variablesMap, this.jbpmDao);
//														startEventAction.execute(request, variablesMap, this.jbpmDao);
//													}
//													initiator = StringUtil.empty2Other(actionInitiator, initiator);
//													formPath = StringUtil.empty2Other(actionFormPath, formPath);
//													if (!initiator.isEmpty()) {
//														task_inner.setAssignee(initiator);
//														HistoryService historyService = JBPMUtil.getHistoryService();
//														HistoryTaskImpl historyTaskImpl = (HistoryTaskImpl) historyService.createHistoryTaskQuery()
//																.taskId(task_inner.getId()).uniqueResult();
//														historyTaskImpl.setAssignee(initiator);
//														// String
//														// update_sql_hist_assignee
//														// =
//														// "update jbpm4_hist_task set assignee_='"+initiator+"' where dbid_="+task_inner.getExecutionDbid();
//														// this.getJbpmDao().doUpdateBySql(update_sql_hist_assignee);
//													}
//													if (!formPath.isEmpty()) {
//														tempProcessInstancePath = formPath;
//														task_inner.setFormResourceName(formPath);
//													}
//												}
//											}
//										}
//										// 实现保存流程实例dbid
//										long tempHTaskDBId = task_inner.getDbid();
//										String tempProcessFormPath = task_inner.getFormResourceName();
//										// 保存流程task历史数据
//										this.insertHistVarExec_info(this.getJbpmDao(), deployId, processInstanceId, tempHTaskDBId,
//												tempProcessFormPath, variablesMap);
//										// 保存整个流程实例历史数据
//									}
//								}
//							}
//							executionService.setVariables(processInstanceId, new HashMap());
//						} else if (pi.isEnded()) {
//							variablesMap.put("processInstanceState", "已结束");
//							for (int ii = 0; ii < activitiesList.size(); ii++) {
//								Map activityMap_inner = (Map) activitiesList.get(ii);
//								String activityType_inner = StringUtil.nullToString(activityMap_inner.get("节点类型"));
//								if ("end".equalsIgnoreCase(activityType_inner)) {
//									String startAction_inner = StringUtil.nullToString(activityMap_inner.get("开始动作")).trim();
//									if (!"".equals(startAction_inner)) {
//										JbpmBaseAction startEventAction_inner = ((JbpmBaseAction) Class.forName(startAction_inner).newInstance());
//										startEventAction_inner.execute(request, variablesMap, this.jbpmDao);
//									}
//									String endAction_inner = StringUtil.nullToString(activityMap_inner.get("结束动作")).trim();
//									if (!"".equals(endAction_inner)) {
//										JbpmBaseAction endEventAction_inner = ((JbpmBaseAction) Class.forName(endAction_inner).newInstance());
//										endEventAction_inner.execute(request, variablesMap, this.jbpmDao);
//									}
//								}
//							}
//							StringBuffer insertLastTaskSql_sb = new StringBuffer("");
//							insertLastTaskSql_sb
//									.append("insert into t_last_task(id,deployid,hprocessinstance_id,htask_dbid,htask_name,htask_actor) values(")
//									.append("'" + UUIDUtil.getUUID() + "'").append("," + lastDeployId).append(",'" + lastTaskProcessInstanceId + "'")
//									.append("," + lastTaskDbId).append(",'" + lastTaskName + "'").append(",'" + lastTaskActor + "')");
//							this.getJbpmDao().doUpdateBySql(insertLastTaskSql_sb.toString());
//						}
//						break;
//					}
//				}
//			}
//		}
//		// 实现保存流程实例dbid
//		long tempHTaskDBId = task.getDbid();
//		// 保存流程task历史数据
//		this.insertHistVarExec_info(this.getJbpmDao(), deployId, tempHProcessInstanceId, tempHTaskDBId, tempProcessTaskFormPath, variablesMap);
//		// 保存整个流程实例历史数据
//		this.insertHistVarExec_info(this.getJbpmDao(), deployId, tempHProcessInstanceId, -1, tempProcessInstancePath, variablesMap);
//		return saveActionReturnResult;
//	}
//
//	@Override
//	public byte[] getProcessWorkflowPicture(String deployId, String imageResourceName) throws Exception {
//		RepositoryService repositoryService = JBPMUtil.getRepositoryService();
//		InputStream inputStream = repositoryService.getResourceAsStream(deployId, imageResourceName);
//		byte[] workflowPictureByteData = new byte[inputStream.available()];
//		inputStream.read(workflowPictureByteData);
//		return workflowPictureByteData;
//	}
//
//	@Override
//	public byte[] getProcessWorkflowPicture(String processInstanceId) throws Exception {
//		RepositoryService repositoryService = JBPMUtil.getRepositoryService();
//		ExecutionService executionService = JBPMUtil.getExecutionService();
//		ProcessInstance processInstance = executionService.findProcessInstanceById(processInstanceId);
//		String processDefinitionId = processInstance.getProcessDefinitionId();
//		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId)
//				.uniqueResult();
//		String deployId = processDefinition.getDeploymentId();
//		String imageResourceName = processDefinition.getImageResourceName();
//		byte[] workflowPictureByteData = this.getProcessWorkflowPicture(deployId, imageResourceName);
//		return workflowPictureByteData;
//	}
//
//	@Override
//	public Map<String, String> getHistoryTaskDataMap(String currentTaskId, String currentProcessInstanceId) throws Exception {
//		Map<String, String> m = new HashMap<String, String>();
//		String sql_query_task = "select  var_name,var_value  from t_jbpm_hist_var hTask where  hTask.hprocessinstance_id='"
//				+ currentProcessInstanceId + "' and hTask.htask_dbid='" + currentTaskId + "'";
//		Map queryTaskMapInfo = (Map) this.getJbpmDao().doQueryBySql(sql_query_task).get(0);
//		String taskSourceKeyStr = StringUtil.nullToString(queryTaskMapInfo.get("var_name"));
//		String taskSourceValueStr = StringUtil.nullToString(queryTaskMapInfo.get("var_value"));
//		String splitStr = "@@_@@";
//		Map<String, String> taskMap = StringUtil.getMapByString(taskSourceKeyStr, taskSourceValueStr, splitStr);
//		m.putAll(taskMap);
//		return m;
//	}
//
//	@Override
//	public Map<String, String> getHistoryProcessInstanceDataMap(String currentProcessInstanceId) throws Exception {
//		Map<String, String> m = new HashMap<String, String>();
//		String sql_query_processInstance = "select var_name,var_value from t_jbpm_hist_var tProcessInstance where tProcessInstance.htask_dbid is null and tProcessInstance.hprocessinstance_id='"
//				+ currentProcessInstanceId + "' ";
//		Map queryProcessInstanceMapInfo = (Map) this.getJbpmDao().doQueryBySql(sql_query_processInstance).get(0);
//		String processInstanceSourceKeyStr = StringUtil.nullToString(queryProcessInstanceMapInfo.get("var_name"));
//		String processInstanceSourceValueStr = StringUtil.nullToString(queryProcessInstanceMapInfo.get("var_value"));
//		String splitStr = "@@_@@";
//		Map<String, String> processInstanceMap = StringUtil.getMapByString(processInstanceSourceKeyStr, processInstanceSourceValueStr, splitStr);
//		m.putAll(processInstanceMap);
//		return m;
//	}
//
//	@Override
//	public void requestHistoryProcessedTaskForm(HttpServletRequest request, String deployId, String currentProcessInstanceId, String currentTaskId,
//			Map modelData) throws Exception {
//
//		String activityName_ = StringUtil.nullToString(modelData.get("currentActivityName"));
//		modelData.put("currentTaskName", activityName_);
//		Map<String, String> processInstanceMap = this.getHistoryProcessInstanceDataMap(currentProcessInstanceId);
//		Map<String, String> taskMap = this.getHistoryTaskDataMap(currentTaskId, currentProcessInstanceId);
//		this.doMergeTaskWithoutVariableByProcessInstance(taskMap, processInstanceMap);
//		modelData.putAll(taskMap);
//
//		this.getRequestFormDealedInfo(request, deployId, currentProcessInstanceId, currentTaskId, activityName_, modelData);
//		// 请求变量
//		/* String deployId = StringUtil.nullToString(modelData.get("deployId"));
//		 * List activitiesList = this.getWorkflowActivitiesSetting(deployId);
//		 * Map<String,Object> transientMap = new HashMap<String,Object>();
//		 * for(int i=0;i<activitiesList.size();i++) { Map activityMap =
//		 * (Map)activitiesList.get(i); String activityName =
//		 * StringUtil.nullToString(activityMap.get("节点名称"));
//		 * if(activityName_.equals(activityName)) { //追加task附加信息，但是变量不进行数据库存储
//		 * transientMap.put("currentTaskId",currentTaskId);
//		 * transientMap.put("currentTaskName",activityName);
//		 * transientMap.put("currentTaskFormPath",
//		 * StringUtil.nullToString(activityMap.get("应用表单")));
//		 * transientMap.put("currentTaskFormTitle"
//		 * ,QueryUtil.getQueryStringByRequest
//		 * (modelData,StringUtil.nullToString(activityMap.get("应用表单标题"))));
//		 * transientMap.put("currentTaskSubmitButtonsStr",
//		 * StringUtil.nullToString
//		 * (activityMap.get("提交按钮")).replaceAll("；",";"));
//		 * transientMap.put("currentTaskIsNeedAdvise",
//		 * StringUtil.nullToString(activityMap.get("是否填写意见"))); break; } }
//		 * modelData.putAll(transientMap); */
//	}
//
//	public void doMergeTaskWithoutVariableByProcessInstance(Map<String, String> taskMap, Map<String, String> processInstanceMap) {
//		Iterator iterProcessInstanceMap = processInstanceMap.keySet().iterator();
//		while (iterProcessInstanceMap.hasNext()) {
//			String key = iterProcessInstanceMap.next().toString();
//			if ((!taskMap.containsKey(key)) || ("processInstanceState".equals(key))) {
//				taskMap.put(key, processInstanceMap.get(key));
//			}
//		}
//	}
//
//	@Override
//	public void setDelegate(Map delegateInfo, boolean isCancel) throws Exception {
//		String grantUser = StringUtil.nullToString(delegateInfo.get("grantUser"));
//		this.getJbpmDao().doUpdateBySql("delete from t_jbpm_delegate where grantuser='" + grantUser + "'");
//		if (!isCancel) {
//			this.getJbpmDao().doInsertOrUpdate("t_jbpm_delegate", delegateInfo, "id");
//		}
//	}
//
//	@Override
//	public boolean isRepeatRequest(String definitionId, String proj_id) throws Exception {
//		boolean isRepeat = false;
//		String sql = " select * from vi_hist_processinstance_info where ";
//		sql += " vi_hist_processinstance_info.流程定义编号 ='" + definitionId + "' ";
//		sql += " and vi_hist_processinstance_info.项目编号='" + proj_id + "' ";
//		sql += " and vi_hist_processinstance_info.流程实例处理状态 ='待处理' ";
//		List l = this.jbpmDao.doQueryBySql(sql);
//		isRepeat = l.size() > 0;
//		return isRepeat;
//	}
//
//	@Override
//	public String rollbackProcessInstance(String processInstanceId, String deleteAction, HttpServletRequest request) throws Exception {
//		Map<String, String> processInstanceMap = this.getHistoryProcessInstanceDataMap(processInstanceId);
//		String returnValue = ((JbpmBaseAction) Class.forName(deleteAction).newInstance()).rollbackProcessInstance(request, processInstanceMap,
//				this.jbpmDao);
//		return returnValue;
//
//	}
//}
