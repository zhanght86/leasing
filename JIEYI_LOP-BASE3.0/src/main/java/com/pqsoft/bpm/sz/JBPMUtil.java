//package com.jbpm.utils;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.io.UnsupportedEncodingException;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.jbpm.api.ExecutionService;
//import org.jbpm.api.HistoryService;
//import org.jbpm.api.IdentityService;
//import org.jbpm.api.ManagementService;
//import org.jbpm.api.ProcessEngine;
//import org.jbpm.api.RepositoryService;
//import org.jbpm.api.TaskService;
//import org.jdom.Document;
//import org.jdom.Element;
//import org.jdom.Namespace;
//import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.PreparedStatementCallback;
//
//import com.kernal.dao.JdbcBaseDao;
//import com.kernal.jbpm.service.JbpmService;
//import com.kernal.utils.QueryUtil;
//import com.kernal.utils.StringUtil;
//import com.kernal.utils.WebUtil;
//import com.kernal.utils.XmlUtil;
//
//@SuppressWarnings("unchecked")
//public class JBPMUtil {
//	private static ProcessEngine processEngine = null;
//	private static RepositoryService repositoryService = null;
//	private static ExecutionService executionService = null;
//	private static TaskService taskService = null;
//	private static ManagementService managementService = null;
//	private static HistoryService historyService = null;
//	private static IdentityService identityService = null;
//	static {
//		processEngine = (ProcessEngine) WebUtil.getApplicationContext()
//				.getBean("processEngine");
//		repositoryService = processEngine.getRepositoryService();
//		executionService = processEngine.getExecutionService();
//		taskService = processEngine.getTaskService();
//		managementService = processEngine.getManagementService();
//		historyService = processEngine.getHistoryService();
//		identityService = processEngine.getIdentityService();
//	}
//
//	public static ProcessEngine getProcessEngine() {
//		return processEngine;
//	}
//
//	public static void setProcessEngine(ProcessEngine processEngine) {
//		JBPMUtil.processEngine = processEngine;
//	}
//
//	public static RepositoryService getRepositoryService() {
//		return repositoryService;
//	}
//
//	public static void setRepositoryService(RepositoryService repositoryService) {
//		JBPMUtil.repositoryService = repositoryService;
//	}
//
//	public static ExecutionService getExecutionService() {
//		return executionService;
//	}
//
//	public static void setExecutionService(ExecutionService executionService) {
//		JBPMUtil.executionService = executionService;
//	}
//
//	public static TaskService getTaskService() {
//		return taskService;
//	}
//
//	public static void setTaskService(TaskService taskService) {
//		JBPMUtil.taskService = taskService;
//	}
//
//	public static ManagementService getManagementService() {
//		return managementService;
//	}
//
//	public static void setManagementService(ManagementService managementService) {
//		JBPMUtil.managementService = managementService;
//	}
//
//	public static HistoryService getHistoryService() {
//		return historyService;
//	}
//
//	public static void setHistoryService(HistoryService historyService) {
//		JBPMUtil.historyService = historyService;
//	}
//
//	public static IdentityService getIdentityService() {
//		return identityService;
//	}
//
//	public static void setIdentityService(IdentityService identityService) {
//		JBPMUtil.identityService = identityService;
//	}
//
//	public static void updateProcessInstanceHistoryData(
//			String currentProcessInstanceId, Map<String, Object> updateModelData)
//			throws Exception {
//		// 获取流程历史
//		JbpmService jbpmService = ((JbpmService) WebUtil
//				.getApplicationContext().getBean("jbpmService"));
//		Map contextMapVariables = jbpmService
//				.getHistoryProcessInstanceDataMap(currentProcessInstanceId);
//		// 获取本地历史
//		JdbcBaseDao baseDao = (JdbcBaseDao) WebUtil.getApplicationContext()
//				.getBean("jdbcDao");
//		String sql_query_processInstance = "select * from vi_hist_processinstance_info tProcessInstance where  tProcessInstance.流程实例编号='"
//				+ currentProcessInstanceId + "' ";
//		Map queryProcessInstanceMapInfo = (Map) baseDao.doQueryBySql(
//				sql_query_processInstance).get(0);
//		String processInstanceSourceKeyStr = StringUtil
//				.nullToString(queryProcessInstanceMapInfo.get("变量名列表"));
//		String processInstanceSourceValueStr = StringUtil
//				.nullToString(queryProcessInstanceMapInfo.get("变量值列表"));
//		String splitStr = "@@_@@";
//		Map<String, String> processInstanceMap = StringUtil.getMapByString(
//				processInstanceSourceKeyStr, processInstanceSourceValueStr,
//				splitStr);
//		// 更新流程历史数据
//		for (String updateKey : updateModelData.keySet()) {
//			Object updateValue = updateModelData.get(updateKey);
//			// 更新流程历史
//			contextMapVariables.put(updateKey, updateValue);
//			// 更新本地历史
//			processInstanceMap.put(updateKey,
//					StringUtil.nullToString(updateValue));
//		}
//		// 更新流程历史数据
//		// 更新本地N+1条流程实例历史数据（扩展历史数据t_jbpm_hist_var）
//		String query_sql = "select deployid as 部署编号 from t_jbpm_hist_var where htask_dbid is null and hprocessinstance_id='"
//				+ currentProcessInstanceId + "'";
//		String deployId = "-1";
//		List l = baseDao.doQueryBySql(query_sql);
//		if (l.size() >= 1) {
//			deployId = StringUtil.nullToString(((Map) l.get(0)).get("部署编号"));
//		}
//		baseDao.doDeleteBySql("delete from t_jbpm_hist_var where htask_dbid is null and hprocessinstance_id='"
//				+ currentProcessInstanceId + "'");
//
//		jbpmService.insertHistVarExec_info(baseDao, deployId,
//				currentProcessInstanceId, -1, null, processInstanceMap);
//	}
//
//	public static void getManagerInfo(String requestInitiator,
//			JdbcBaseDao conn, Map<String, String> workFlowDealersMap)
//			throws Exception {
//		StringBuffer sb_queryManagerSql = new StringBuffer("");
//		// 查询发起人的部门经理
//		sb_queryManagerSql
//				.append(" \n   select jb_yhxx.login_name as 登录名,jb_yhxx.real_name as 真实姓名 from( ")
//				.append(" \n      select jb_gsbm.bmjl from( ")
//				.append(" \n          select bm,login_name from jb_yhxx  where upper(login_name) =upper('"
//						+ requestInitiator + "') ")
//				.append(" \n       )jb_yhxx inner join( ")
//				.append(" \n          select t.id,t.bmjl from jb_gsbm t ")
//				.append(" \n       )jb_gsbm on jb_yhxx.bm = jb_gsbm.id   ")
//				.append(" \n   )tt inner join( ")
//				.append(" \n       select id,login_name,jb_yhxx.xm as real_name from jb_yhxx ")
//				.append(" \n   )jb_yhxx on  jb_yhxx.id = tt.bmjl ");
//		List l = conn.doQueryBySql(sb_queryManagerSql.toString());
//		if (l.size() > 0) {
//			Map m = (Map) l.get(0);
//			String manageruser = StringUtil.nullToString(m.get("登录名"));
//			String managerrealname = StringUtil.nullToString(m.get("真实姓名"));
//			workFlowDealersMap.put("login_name", manageruser);
//			workFlowDealersMap.put("real_name", managerrealname);
//			// 获得代理用户
//			getDelegateInfo(manageruser, conn, workFlowDealersMap);
//		}
//	}
//
//	public static void getDelegateInfo(String nextDealUser, JdbcBaseDao conn,
//			Map<String, String> workFlowDealersMap) throws Exception {
//		String currentDate = StringUtil.getSystemDate();
//		String queryDelegateSql = "select t.delegateuser as 代理登陆名,t.delegaterealname 代理真实身份 from t_jbpm_delegate t where grantuser ='"
//				+ nextDealUser
//				+ "' and t.start_date <='"
//				+ currentDate
//				+ "' and t.end_date>='" + currentDate + "'";
//		List l = conn.doQueryBySql(queryDelegateSql);
//		if (l.size() > 0) {
//			Map m = (Map) l.get(0);
//			String delegateuser = StringUtil.nullToString(m.get("代理登陆名"));
//			String delegaterealname = StringUtil.nullToString(m.get("代理真实身份"));
//			workFlowDealersMap.put("delegate_login_name", delegateuser);
//			workFlowDealersMap.put("delegate_real_name", delegaterealname);
//		}
//	}
//
//	public static void getRequestInitiatorDealer(String requestInitiator,
//			String requestInitiatorName, JdbcBaseDao conn,
//			List<Map<String, String>> workFlowDealersMapList) throws Exception {
//		Map<String, String> workFlowDealersMap = new HashMap<String, String>();
//		workFlowDealersMap.put("login_name", requestInitiator);
//		workFlowDealersMap.put("real_name", requestInitiatorName);
//		getDelegateInfo(requestInitiator, conn, workFlowDealersMap);
//		workFlowDealersMapList.add(workFlowDealersMap);
//	}
//
//	public static String getUserRealName(String login_name, JdbcBaseDao conn)
//			throws Exception {
//		String sql = "select xm from jb_yhxx where login_name='" + login_name
//				+ "'";
//		Map map = (Map) conn.doQueryBySql(sql).get(0);
//		String real_name = StringUtil.nullToString(map.get(map.keySet()
//				.iterator().next()));
//		return real_name;
//	}
//
//	public static void getVariableDealer(String variable, Map variablesMap,
//			JdbcBaseDao conn, List<Map<String, String>> workFlowDealersMapList)
//			throws Exception {
//		Map<String, String> workFlowDealersMap = new HashMap<String, String>();
//		String login_name = QueryUtil.getQueryStringByRequest(variablesMap,
//				variable);
//		workFlowDealersMap.put("login_name", login_name);
//		String real_name = JBPMUtil.getUserRealName(login_name, conn);
//		workFlowDealersMap.put("real_name", real_name);
//		JBPMUtil.getDelegateInfo(login_name, conn, workFlowDealersMap);
//		workFlowDealersMapList.add(workFlowDealersMap);
//	}
//
//	public static void getSQLDealer(String sql, Map variablesMap,
//			JdbcBaseDao conn, List<Map<String, String>> workFlowDealersMapList)
//			throws Exception {
//		sql = QueryUtil.getQueryStringByRequest(variablesMap, sql);
//		List l = conn.doQueryBySql(sql);
//		for (int index = 0; index < l.size(); index++) {
//			Map<String, String> workFlowDealersMap = new HashMap<String, String>();
//			Map map = (Map) l.get(index);
//			String login_name = StringUtil.nullToString(map.get(map.keySet()
//					.iterator().next()));
//			workFlowDealersMap.put("login_name", login_name);
//			String real_name = JBPMUtil.getUserRealName(login_name, conn);
//			workFlowDealersMap.put("real_name", real_name);
//			JBPMUtil.getDelegateInfo(login_name, conn, workFlowDealersMap);
//			workFlowDealersMapList.add(workFlowDealersMap);
//		}
//	}
//
//	public static void getManagerDealer(String requestInitiator,
//			JdbcBaseDao conn, List<Map<String, String>> workFlowDealersMapList)
//			throws Exception {
//		Map<String, String> workFlowDealersMap = new HashMap<String, String>();
//		JBPMUtil.getManagerInfo(requestInitiator, conn, workFlowDealersMap);
//		workFlowDealersMapList.add(workFlowDealersMap);
//	}
//
//	public static void getAllDealersByGroup(
//			List<Map<String, String>> workFlowDealersMapList, String groupName,
//			JdbcBaseDao conn) throws Exception {
//		String sql = "select jb_yhxx.login_name as 登录名,jb_yhxx.xm as 真实身份  from(select * from t_role where label like '"
//				+ groupName
//				+ "')t_role inner join(select * from t_user_role)t_user_role on t_user_role.role_id = t_role.id inner join(select * from jb_yhxx)jb_yhxx on  jb_yhxx.id = t_user_role.user_id ";
//		List l = conn.doQueryBySql(sql);
//		int len = l.size();
//		if (len > 0) {
//			for (int i = 0; i < len; i++) {
//				Map<String, String> workFlowDealersMap = new HashMap<String, String>();
//				Map m = (Map) l.get(i);
//				String login_name = StringUtil.nullToString(m.get("登录名"));
//				String real_name = StringUtil.nullToString(m.get("真实身份"));
//				workFlowDealersMap.put("login_name", login_name);
//				workFlowDealersMap.put("real_name", real_name);
//				JBPMUtil.getDelegateInfo(login_name, conn, workFlowDealersMap);
//				workFlowDealersMapList.add(workFlowDealersMap);
//			}
//		}
//	}
//
//	// 获取blob中的xml文件内容
//	public static String queryBlob(JdbcBaseDao jbpmDao, final String sql)
//			throws Exception {
//		return jbpmDao.getJdbcTemplate().execute(sql,
//				new PreparedStatementCallback<String>() {
//					@Override
//					public String doInPreparedStatement(PreparedStatement ps)
//							throws SQLException, DataAccessException {
//						ResultSet rs = null;
//						StringBuffer sb = new StringBuffer("");
//						rs = ps.executeQuery(sql);
//						Reader read = null;
//						InputStream is = null;
//						try {
//							if (rs.next()) {
//								java.sql.Blob blob = rs.getBlob(1);
//								is = blob.getBinaryStream();
//								read = new InputStreamReader(is, "UTF-8");
//								char[] charbuf = new char[4096];
//								for (int i = read.read(charbuf); i > 0; i = read
//										.read(charbuf)) {
//									sb.append(charbuf, 0, i);
//								}
//							}
//							read.close();
//							is.close();
//						} catch (UnsupportedEncodingException e) {
//							e.printStackTrace();
//							throw new SQLException(
//									"UnsupportedEncodingException");
//						} catch (IOException e) {
//							e.printStackTrace();
//							throw new SQLException("IOException");
//						}
//						return sb.toString();
//					}
//				});
//	}
//
//	public static Map<String, Map<String, String>> getAllWorkFlowNodesTransitions(
//			String deployId, JdbcBaseDao jbpmDao, String containerName)
//			throws Exception {
//		Map<String, Map<String, String>> allWorkFlowNodesTransitions = new HashMap<String, Map<String, String>>();
//		String sql = "select blob_value_ as 文件内容 from jbpm4_lob where name_ like '%.xml' and deployment_="
//				+ deployId;
//		String xmlContent = JBPMUtil.queryBlob(jbpmDao, sql);
//		Document document = XmlUtil.readXML(xmlContent, true);
//		Element root = document.getRootElement();
//		Namespace ns = root.getNamespace();
//		List taskElements = root.getChildren("task", ns);
//
//		for (int i = 0; i < taskElements.size(); i++) {
//			Element taskElement = (Element) taskElements.get(i);
//			String nodeName = StringUtil.nullToString(taskElement
//					.getAttributeValue("name"));
//			Map<String, String> m = new HashMap<String, String>();
//			List transitionElements = taskElement.getChildren("transition", ns);
//			for (int j = 0; j < transitionElements.size(); j++) {
//				Element transitionElement = (Element) transitionElements.get(j);
//				String transitionTo = StringUtil.nullToString(transitionElement
//						.getAttributeValue("to"));
//				String transitionName = StringUtil
//						.nullToString(transitionElement
//								.getAttributeValue("name"));
//				m.put(transitionTo, transitionName);
//			}
//			allWorkFlowNodesTransitions.put(nodeName, m);
//		}
//		document = null;
//		XmlUtil.closeLocalResources();
//		return allWorkFlowNodesTransitions;
//	}
//}
