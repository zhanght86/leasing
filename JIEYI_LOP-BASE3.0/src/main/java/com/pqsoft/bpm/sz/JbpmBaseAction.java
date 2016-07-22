//package com.kernal.jbpm.action;
//
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import com.kernal.dao.JdbcBaseDao;
//
//public class JbpmBaseAction {
//
//	/**
//	 * 指定该流程节点有谁去处理(startAction中触发)
//	 * 
//	 * @param variablesMap
//	 * @return
//	 */
//	public String appointExecuteActor(HttpServletRequest request,
//			Map<String, Object> variablesMap, JdbcBaseDao jdbcDao)
//			throws Exception {
//		return null;
//	}
//
//	/**
//	 * 指定该流程节点的处理表单路径(startAction中触发)
//	 * 
//	 * @param variablesMap
//	 * @return
//	 */
//	public String appointFormPath(HttpServletRequest request,
//			Map<String, Object> variablesMap, JdbcBaseDao jdbcDao)
//			throws Exception {
//		return null;
//	}
//
//	/**
//	 * 指派流程的走向（task的endAction方法中触发,start特殊在startAction中触发）
//	 * 
//	 * @param variablesMap
//	 * @return
//	 */
//	public String appointRequestRoute(HttpServletRequest request,
//			Map<String, Object> variablesMap, JdbcBaseDao jdbcDao)
//			throws Exception {
//		return null;
//	}
//
//	/**
//	 * 写入业务逻辑操作(startAction和endAction均触发)
//	 * 
//	 * @param variablesMap
//	 */
//	public void execute(HttpServletRequest request,
//			Map<String, Object> variablesMap, JdbcBaseDao jdbcDao)
//			throws Exception {
//		return;
//	}
//
//	/**
//	 * 保存操作(saveAction触发)
//	 * 
//	 * @param variablesMap
//	 */
//	public String save(HttpServletRequest request,
//			Map<String, Object> variablesMap, JdbcBaseDao jdbcDao)
//			throws Exception {
//		return null;
//	}
//
//	/**
//	 * 丢弃流程实例(删除操作触发)
//	 * 
//	 * @param processInsanceDataMap
//	 */
//	public String deleteProcessInstance(HttpServletRequest request,
//			Map<String, String> processInsanceDataMap, JdbcBaseDao jdbcDao)
//			throws Exception {
//		return null;
//	}
//
//	/**
//	 * 退回流程实例(退回操作触发)
//	 * 
//	 * @param processInsanceDataMap
//	 */
//	public String rollbackProcessInstance(HttpServletRequest request,
//			Map<String, String> processInsanceDataMap, JdbcBaseDao jdbcDao)
//			throws Exception {
//		return null;
//	}
//
//}
