package com.pqsoft.bpm.service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.pqsoft.util.HttpUtil;
import com.pqsoft.util.JsonUtil;
import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.pqsoft.bpm.JbpmXml;
import com.pqsoft.leeds.utils.StringUtil;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.PageUtil;

public class TaskService {

	public List<Map<String, Object>> reverse(List<Map<String, Object>> src) {
		List<Map<String, Object>> memore = new ArrayList<Map<String, Object>>(src);
		Collections.reverse(memore);
		return memore;
	}

	public List<Map<String, Object>> progress(List<Map<String, Object>> memos) {
		List<Map<String, Object>> progress = new ArrayList<Map<String, Object>>();
		int i = 0;
		for (Map<String, Object> map : memos) {
			if (i == 4) {
				break;
			}
			if (StringUtils.isEmpty((String) map.get("FILE_PATH"))) {
				progress.add(map);
				i++;
			}
		}
		return progress;
	}

	@SuppressWarnings("unchecked")
	public Page getPage(Map<String, Object> map) {
		map.put("USER_ID", Security.getUser().getId());
		map.put("ORG_ID", Security.getUser().getOrg().getId());

		BaseUtil.getDataAllAuth(map);
		Page page = new Page(map);
		List<Map<String, Object>> selectList = Dao.selectList("bpm.task.getPageList", map);
		for (int i = 0; i < selectList.size(); i++) {
			Map<String, Object> param1 = new HashMap<String, Object>();
			if (selectList.get(i).get("SHOP_NAME") != null
					&& !"".equals(selectList.get(i).get("SHOP_NAME").toString())) {
				param1.put("SHOPNAME", selectList.get(i).get("SHOP_NAME"));
				// add by lishuo 04-18-2016 Start
				Map<String, Object> result = Dao.selectOne("bpm.task.getFGS", param1);
				if ("".equals(result) || null == result) {
					selectList.get(i).put("FGS", param1.get("SHOPNAME"));// 若查询分公司没有值则取门店名
				} else {
					selectList.get(i).put("FGS",
							((Map<String, Object>) Dao.selectOne("bpm.task.getFGS", param1)).get("NAME"));
				}
				// add by lishuo 04-18-2016 End
			}
		}
		page.addDate(JSONArray.fromObject(selectList), Dao.selectInt("bpm.task.getPageCount".trim(), map));

		// return PageUtil.getPage(param, "bpm.task.getPageList",
		// "bpm.task.getPageCount");
		return page;
	}

	public Page getChoosePage(Map<String, Object> param) {
		return PageUtil.getPage(param, "bpm.task.getChoosePageList", "bpm.task.getChoosePageCount");
	}

	public void doChoose(Map<String, Object> param) {
		Dao.update("bpm.task.choose", param);
	}

	public Page getPageEntrust(Map<String, Object> param) {
		return PageUtil.getPage(param, "bpm.task.getPageListEntrust", "bpm.task.getPageCountEntrust");
	}

	/**
	 * 获取当前节点配置信息
	 * 
	 * @param taskId
	 */
	public Map<String, Object> getConfig(String taskId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", taskId);
		return Dao.selectOne("bpm.task.getConfig", map);
	}

	public Map<String, Object> getConfig(String deployId, String taskId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", taskId);
		return Dao.selectOne("bpm.task.getConfig", map);
	}

	/**
	 * 查询task明细
	 * 
	 * @param taskId
	 * @return
	 */
	public Map<String, Object> getTask(String taskId) {
		return Dao.selectOne("bpm.task.getTask", taskId);
	}

	/**
	 * 查询JbpmId
	 * 
	 * @param taskId
	 * @return JbpmId
	 */
	public String getJbpmIdBytaskId(String taskId) {
		return Dao.selectOne("bpm.task.queryJbpmIdBytaskId", taskId);
	}

	public List getNextTransition(String deployId, String containerName) throws Exception {
		return getNextTransition(deployId, containerName, null);
	}

	/**
	 * 指定流程图，指定task节点的下一步操作
	 * 
	 * @param deployId
	 * @param containerName
	 * @param jbpmId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getNextTransition(String deployId, String containerName, String jbpmId) throws Exception {
		List list = new ArrayList();
		Element root = new SAXReader().read(new JbpmXml().getFile(deployId)).getRootElement();
		List<Element> taskElements = root.elements("task");
		// List<Element> allElements = root.elements();
		for (Element taskElement : taskElements) {
			String nodeName = taskElement.attributeValue("name");
			if (nodeName == null || !nodeName.equals(containerName))
				continue;
			List<Element> transitionElements = taskElement.elements("transition");
			for (Element transitionElement : transitionElements) {
				Map<String, Object> m = new HashMap<String, Object>();
				// transitionName 流程线 名称
				// transitionTo 下一步节点名称
				String transitionTo = transitionElement.attributeValue("to");
				for (Element element : taskElements) {
					String nName = element.attributeValue("name");
					if (nName == null || !nName.equals(transitionTo))
						continue;
					String groups = element.attributeValue("candidate-groups");
					String assignee = element.attributeValue("assignee");
					m.put("assignee", assignee);
					if (assignee != null && !"".equals(assignee))
						break;
					if (groups != null && !"".equals(groups)) {
						List groupsList = new ArrayList();
						for (String g : groups.split("[,;，；]")) {
							if (g != null && !"".equals(g))
								groupsList.addAll((Dao.selectList("bpm.task.getUserCodeByOrgId", g)));
						}
						m.put("groups", groupsList);
						break;
					}
					Map<String, Object> sql = getConfigByXml(deployId, nName);
					if (sql == null) {
						throw new ActionException("流程图[" + deployId + "]节点[" + nName + "]未配置，请提交系统管理员");
					}
					if ("ASSIGN_SQL".equals(sql.get("ASSIGN_TYPE"))) {
						if (sql.get("ASSIGN_VALUE") != null) {
							m.put("groups", Dao.execSelectSql(
									sql.get("ASSIGN_VALUE").toString().replaceAll("#\\{ID\\}", "'" + jbpmId + "'")));
						}
					}
				}
				m.put("key", transitionElement.attributeValue("name"));
				m.put("value", transitionTo);
				list.add(m);
			}
		}
		return list;
	}

	private Map<String, Object> getConfigByXml(String deployId, String nName) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("NAME", nName);
		param.put("PDID", deployId);
		return Dao.selectOne("bpm.task.getConfigByXml", param);
	}

	/**
	 * 指定流程图，所有task节点的下一步操作
	 * 
	 * @param deployId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Map<String, String>> getAllNextTransition(String deployId) throws Exception {
		Map<String, Map<String, String>> allWorkFlowNodesTransitions = new HashMap<String, Map<String, String>>();
		List<Element> taskElements = new SAXReader().read(new JbpmXml().getFile(deployId)).getRootElement()
				.elements("task");
		for (Element taskElement : taskElements) {
			String nodeName = taskElement.attributeValue("name");
			Map<String, String> m = new HashMap<String, String>();
			List<Element> transitionElements = taskElement.elements("transition");
			for (Element transitionElement : transitionElements) {
				String transitionTo = transitionElement.attributeValue("to");
				String transitionName = transitionElement.attributeValue("name");
				m.put(transitionTo, transitionName);
			}
			allWorkFlowNodesTransitions.put(nodeName, m);
		}
		return allWorkFlowNodesTransitions;
	}

	// 流程编号截断
	public String getShortName(String jbpmId) {
		String[] strs = jbpmId.split("[.]");
		if (strs.length > 2) {
			jbpmId = strs[0] + "." + strs[1];
		}
		return jbpmId;
	}

	public Map<String, Object> taskTrack(String jbpmId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("JBPM_ID", jbpmId);
		List<Map<String, Object>> memoModels = (List<Map<String, Object>>) new MemoService().getMemos(jbpmId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<?> node = Dao.selectList("bpm.task.getTaskPersionByJbpm", param);
		resultMap.put("nowNode", node);
		resultMap.put("id", getShortName(jbpmId));
		Map<String, Object> deployPropMap = Dao.selectOne("bpm.task.getDeployIdByJbpmId", param);
		resultMap.put("PDID", deployPropMap.get("PROCDEFID_"));
		resultMap.put("memoModels", memoModels);
		return resultMap;
	}

	/**
	 * 获取流程携带业务连接
	 * 
	 * @param jbpmId
	 * @param taskName
	 *            当前节点名称
	 * @param url
	 *            流程表单Action地址
	 * @return
	 * @author <a href="mailto:lichaohn@163.com">lichao</a> 2012-4-27
	 */
	@SuppressWarnings("unchecked")
	public String getContentUrl(String jbpmId, String taskName, String url) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("JBPM_ID", getShortName(jbpmId));
		if (url != null) {
			if (url.startsWith("/"))
				url = url.substring(1, url.length());
			if (url.indexOf("?") == -1)
				url = url + "?";
			List<?> variables = getMaxVeriableByJbpmId(jbpmId);
			String data = null;
			try {
				data = "&JBPM_ID=" + URLEncoder.encode(jbpmId, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				throw new ActionException("解析表单失败", e1);
			}
			for (Object object : variables) {
				Map<String, Object> m = (Map<String, Object>) object;
				try {
					data += "&" + m.get("KEY_") + "=" + URLEncoder
							.encode(m.get("STRING_VALUE_") == null ? "" : m.get("STRING_VALUE_").toString(), "UTF8");
				} catch (UnsupportedEncodingException e) {
					throw new ActionException("解析表单失败", e);
				}
			}
			if (StringUtils.isBlank(taskName)) {

			} else {
				try {
					data += "&TASK_NAME=" + URLEncoder.encode(taskName, "UTF8");
				} catch (Exception e) {
					throw new ActionException("解析表单失败", e);
				}
			}
			map.put("CONTENT_URL", url + data);
			// Dao.update("bpm.task.updateContentUrl", map);
			return url + data;
		} else {
			// url = Dao.selectOne("bpm.task.getContentUrl", map);
			// return url;
		}
		return null;
	}

	/**
	 * add by lishuo 01-15-2016 合格证录入第二部保存校验
	 * 
	 * @param project_id
	 * @param type
	 * @throws Exception
	 */
	public void JudgeProcedureOK2(String project_id, String type) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("PROJECT_ID", project_id);
		List<Map<String, Object>> list = Dao.selectList("buyCertificate.QueryGPSInfo", param);
		List<Map<String, Object>> list3 = Dao.selectList("buyCertificate.QueryInvoiceByLeaseCode", param);
		if (list3.size() > 0)
			param.put("LEASE_CODE", list.get(0).get("LEASE_CODE"));
		List<Map<String, Object>> list2 = Dao.selectList("buyCertificate.QueryInvoice", param);
		if (list2.size() > 0) {
			if (list.get(0).get("BUYCAR_INVOICE_URL") == null || list.get(0).get("BUYCAR_INVOICE_URL").equals("")) {
				throw new Exception("购置税发票必须上传!");
			}
			if (list.get(0).get("CAR_INSURANCE_URL") == null || list.get(0).get("CAR_INSURANCE_URL").equals("")) {
				throw new Exception("交强险发票必须上传!");
			}
		}
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(0).get("GPS_COMPANY_TYPE") == null || list.get(0).get("GPS_COMPANY_TYPE").equals("")) {
					if (list.get(0).get("GPS_COMPANY") == null || list.get(0).get("GPS_COMPANY").equals("")) {
						throw new Exception("GPS厂商不能为空!");
					}
					if (list.get(0).get("GPS_TYPE") == null || list.get(0).get("GPS_TYPE").equals("")) {
						throw new Exception("GPS类型不能为空!");
					}
					if (list.get(0).get("GPS_ID") == null || list.get(0).get("GPS_ID").equals("")) {
						throw new Exception("GPS_ID不能为空!");
					}
				}
			}
		}
	}

	/**
	 * 获取流程携带业务连接./.去掉encode
	 * 
	 * @param jbpmId
	 * @param taskName
	 *            当前节点名称
	 * @param url
	 *            流程表单Action地址
	 * @return
	 * @author wp 2015-11-25
	 */
	@SuppressWarnings("unchecked")
	public String getContentUrlNotEncode(String jbpmId, String taskName, String url) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("JBPM_ID", getShortName(jbpmId));
		if (url != null) {
			if (url.startsWith("/"))
				url = url.substring(1, url.length());
			if (url.indexOf("?") == -1)
				url = url + "?";
			List<?> variables = getMaxVeriableByJbpmId(jbpmId);
			String data = null;

			data = "&JBPM_ID=" + jbpmId;

			for (Object object : variables) {
				Map<String, Object> m = (Map<String, Object>) object;
				data += "&" + m.get("KEY_") + "="
						+ (m.get("STRING_VALUE_") == null ? "" : m.get("STRING_VALUE_").toString());
			}
			if (StringUtils.isBlank(taskName)) {

			} else {
				data += "&TASK_NAME=" + taskName;
			}
			map.put("CONTENT_URL", url + data);
			// Dao.update("bpm.task.updateContentUrl", map);
			return url + data;
		} else {
			// url = Dao.selectOne("bpm.task.getContentUrl", map);
			// return url;
		}
		return null;
	}

	/**
	 * 根据流程ID和Key获取参数
	 * 
	 * @Function: com.pqsoft.bpm.service.TaskService.getMaxVeriableByJbpmId
	 * @param jbpm_id
	 *            流程ID
	 * @return 流程需要参数
	 * @author: 吴剑东
	 * @date: 2013-9-5 下午12:05:55
	 */
	public static List<?> getMaxVeriableByJbpmId(String jbpm_id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("JBPM_ID", jbpm_id);
		return Dao.selectList("bpm.task.getMaxVeriableByJbpmId", param);
	}

	/**
	 * 根据流程id等查询当前任务信息
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-9-23
	 */
	public List<Map<String, Object>> doShowTaskInfoByJbpmId(Map<String, Object> map) {
		return Dao.selectList("bpm.task.queryTaskInfoByJbpmId", map);
	}

	public List<Map<String, Object>> getTaskByJbpmId(String jbpmId) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("JBPM_ID", jbpmId);
		return Dao.selectList("bpm.task.getTaskByJbpmId", m);
	}

	public void setAssignee(String jbpmId, String code) {
		if (code == null || "".equals(code) || jbpmId == null || "".equals(jbpmId))
			return;
		getShortName(jbpmId);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("JBPM_ID", jbpmId);
		param.put("CODE", code);
		Dao.update("bpm.task.setAssigneeByJbpmId", param);
	}

	public void addEntrust(Map<String, Object> param) {
		Dao.update("bpm.task.cancelEntrust", param);
		Dao.insert("bpm.task.addEntrust", param);
	}

	public void cancelEntrust(Map<String, Object> param) {
		Dao.update("bpm.task.cancelEntrust", param);
	}

	public Page getOldPage(Map<String, Object> param) {
		return PageUtil.getPage(param, "bpm.task.getOldPageList", "bpm.task.getOldPageCount");
	}

	public Map<String, Object> getEntrustOne(String code) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("SRC_CODE", code);
		return Dao.selectOne("bpm.task.getEntrustOne", m);
	}

	public Object getUser() {
		return Dao.selectList("getUser");
	}

	public Map<String, Object> getConfigByMemoId(String memoId) {
		return Dao.selectOne("bpm.task.getConfigByMemoId", memoId);
	}

	public Object getJbpm(String jbpmId) {
		return Dao.selectOne("bpm.task.getJbpm", jbpmId);
	}

	public List<Map<String, Object>> portal(Map<String, Object> param) {
		return Dao.selectList("bpm.task.portal", param);
		// return Dao.selectList("bpm.task.portal1",param);
	}

	public Object portal_db(Map<String, Object> param) {
		return Dao.selectList("bpm.task.portal_db", param);
	}

	public String getAssignee(String jbpmId) {
		Map<String, Object> m = new HashMap<>();
		// 权限加上下一个操作人
		m.put("JBPM_ID", getShortName(jbpmId));
		List<String> list = Dao.selectList("bpm.task.getAssignee", m);
		if (list == null || list.size() == 0)
			return null;
		return ArrayUtils.toString(list);
	}

	public String getAssigneeJobName(String jbpmId) {
		Map<String, Object> m = new HashMap<String, Object>();
		// 权限加上下一个操作人
		m.put("JBPM_ID", getShortName(jbpmId));
		List<String> list = Dao.selectList("bpm.task.getAssigneeJobName", m);
		if (list == null || list.size() == 0)
			return null;
		return ArrayUtils.toString(list);
	}

	public String getIsReject(String jbpmId) {
		Map<String, Object> m = new HashMap<String, Object>();
		// 权限加上下一个操作人
		m.put("JBPM_ID", getShortName(jbpmId));
		return Dao.selectOne("bpm.task.getIsReject", m);
	}

	public String getMytaskId(String jbpmId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("JBPM_ID", jbpmId);
		param.put("CODE", Security.getUser().getCode());
		return Dao.selectOne("bpm.task.getMytaskId", param);
	}

	public void addCy(String taskId, String config) {
		if (config == null)
			return;
		if ("".equals(config.trim()))
			return;
		String userCode = null;
		if (Dao.selectInt("bpm.task.getUserCountByCode", config) > 0)
			userCode = config;
		if (userCode == null) {
			List<Map<String, Object>> m = Dao.execSelectSql(config);
			if (m.size() > 0) {
				userCode = (String) m.get(0).get("CODE");
			}
		}
		if (userCode == null || "".equals(userCode))
			return;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("TASK_ID", taskId);
		param.put("USERCODE", userCode);
		param.put("STATUS", "new");
		Dao.insert("bpm.task.addJbpmCirculate", param);
	}

	public void cyOver(String taskId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("TASK_ID", taskId);
		param.put("STATUS", "end");
		Dao.update("bpm.task.upJbpmCirculate", param);
	}

	public void sendSms(String jbpmId) {
		// 获取当前允许发短信的任务信息

	}

	/**
	 * 设置已读状态
	 * 
	 * @param jbpmId
	 */
	public void setRead(String jbpmId) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("JBPM_ID", jbpmId);
		Dao.update("bpm.task.upProcinst", m);
	}

	public void upFile(Map<String, Object> m) {

	}

	/**
	 * 执行流程节点配置业务逻辑
	 * 
	 * @Function: com.pqsoft.bpm.service.TaskService.actOp
	 * @param acts
	 *            流程节点对应类名方法名 例:TaskService!actOp;TaskService!getJbpmIdBytaskId
	 *            多个方法用;分割 类名与方法名用!分割
	 * @param jbpmId
	 *            流程Id
	 * @throws Exception
	 * @author: 吴剑东
	 * @throws Exception
	 * @date: 2013-9-6 上午09:31:54
	 */
	public void actOp(String acts, String jbpmId) throws Exception {
		if (acts != null && acts != "") {
			// 获取需要操作的类方法 多个方法用;分割
			for (String actOp : acts.split("[，；,;]")) {
				if (actOp != null || actOp != "") {
					// 类名与方法名用!分割
					String[] act = actOp.split("[!！]");

					Class<?> cla = Class.forName(act[0]);
					// 反射调用 执行类方法
					Method method = cla.getMethod(act[1], String.class);
					method.invoke(cla.newInstance(), jbpmId);
				}
			}
		}
	}

	public void sendMail(String code, String jbpmId) throws Exception {
		if (code == null)
			throw new Exception("无员工工号");
		if (jbpmId == null)
			throw new Exception("无流程ID");
		jbpmId = getShortName(jbpmId);
		Map<String, Object> userInfo = Dao.selectOne("bpm.task.getUserInfo", code);
		Map<String, Object> info = (Map<String, Object>) Dao.selectOne("bpm.task.getMailInfoByJbpmId", jbpmId);
		if (userInfo == null)
			throw new Exception("无员工信息");
		VelocityContext context = new VelocityContext();
		context.put("date", info.get("SENDDATE"));
		context.put("jbpmId", jbpmId);
		context.put("code", code);
		context.put("name", userInfo.get("NAME"));
		context.put("info", info);

		String html = VM.html("bpm/task_Mail.vm", context);

		SystemMail.sendMail((String) userInfo.get("EMAIL"), "新任务提醒", html);
	}

	public void updnotGoMemo(Map<String, Object> map) {
		if (map.containsKey("NOGOMEMO")) {
			Dao.update("bpm.task.updnotGoMemo", map);
		}
	}

	// 下属任务
	@SuppressWarnings("unchecked")
	public Page getUnderlingTaskPage(Map<String, Object> param) {
		param.put("USER_ID", Security.getUser().getId());
		param.put("ORG_ID", Security.getUser().getOrg().getId());

		BaseUtil.getDataAllAuth(param);
		Page page = new Page(param);
		List<Map<String, Object>> selectList = Dao.selectList("bpm.task.getPageList", param);
		Map<String, Object> fgsMap = null;
		for (int i = 0; i < selectList.size(); i++) {
			Map<String, Object> param1 = new HashMap<String, Object>();
			if (selectList.get(i).get("SHOP_NAME") != null
					&& !"".equals(selectList.get(i).get("SHOP_NAME").toString())) {
				param1.put("SHOPNAME", selectList.get(i).get("SHOP_NAME"));
				fgsMap = (Map<String, Object>) Dao.selectOne("bpm.task.getFGS", param1);
				if (fgsMap != null && fgsMap.get("NAME") != null) {
					selectList.get(i).put("FGS",
							((Map<String, Object>) Dao.selectOne("bpm.task.getFGS", param1)).get("NAME"));
				} else {
					selectList.get(i).put("FGS", "");
				}

			}
		}
		page.addDate(JSONArray.fromObject(selectList), Dao.selectInt("bpm.task.getPageCount".trim(), param));
		return page;
	}

	// 改变项目状态
	public int updateProStatus(String id, String status_new) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ID", id);
		param.put("STATUS_NEW", status_new);
		return Dao.update("bpm.task.updateProStatus", param);
	}

	// 审批意见外部意见与信审建议外部备注相同
	public Map<String, Object> getLetter(String pro_code) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("PRO_CODE", pro_code);
		return Dao.selectOne("bpm.task.getLetter", param);
	}

	/**
	 * 手续必填判断
	 * 
	 * @author xingsumin 2016年1月17日11:01:37
	 * 
	 */
	public void JudgeProcedureOK(String PROJECT_ID) {
		if (StringUtils.isNotBlank(PROJECT_ID)) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("PROJECT_ID", PROJECT_ID);
			// 购置税 交强险信息
			List<Map<String, Object>> certificates = Dao.selectList("buyCertificate.getInvoceAndInsuranceByProjectId",
					m);
			// GPS信息
			List<Map<String, Object>> gpsList = Dao.selectList("buyCertificate.getGpsListByProjectId", m);
			if (null != certificates && certificates.size() > 0) {
				Map<String, Object> certificate = certificates.get(0);
				if (null != certificate) {
					if (StringUtil.isBlank(certificate.get("BUYCAR_INVOICE_NAME"))
							&& "1".equals(certificate.get("CARNUM_TYPE"))) {
						throw new ActionException("请录入购置税信息!");
					}
					if (StringUtil.isBlank(certificate.get("CAR_INSURANCE_NAME"))
							|| StringUtil.isBlank(certificate.get("CAR_INSURANCE_URL"))) {
						throw new ActionException("请录入交强险图片信息!");
					}
					// add by lishuo 01-22-2016 Start
					if (StringUtil.isBlank(certificate.get("PROMISE_BOOK_NAME"))
							&& "0".equals(certificate.get("CARNUM_TYPE"))) {
						throw new ActionException("请录入承诺书图片信息!");
					}
					if (null == certificate.get("CARNUM_TYPE") || "请选择".equals(certificate.get("CARNUM_TYPE"))) {
						throw new ActionException("请录入牌照类型!");
					}
					if (null == certificate.get("CARNUM")) {
						throw new ActionException("请录入车牌号!");
					}
					// add by lishuo 01-22-2016 End
				} else {
					throw new ActionException("车辆合格证资料录入不全!");
				}

			} else {
				throw new ActionException("车辆合格证资料录入不全!");
			}
			if (null == gpsList || gpsList.size() < 1) {
				throw new ActionException("车辆GPS资料录入不全!");
			}
		} else {
			throw new ActionException("流程处理失败");
		}
	}

	/**
	 * 交车必填判断 *@author xingsumin 2016年1月17日11:03:37
	 */
	public void JudgeJiaoche(String PROJECT_ID) {
		if (StringUtils.isNotBlank(PROJECT_ID)) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("PROJECT_ID", PROJECT_ID);
			List<Map<String, Object>> receipts = Dao.selectList("buyCertificate.getEquimentReceiptByProjectId", m);
			if (null != receipts && receipts.size() > 0) {
				Map<String, Object> r = receipts.get(0);
				if (null != r) {
					if (StringUtil.isBlank(r.get("RECEIVE_DATE")) && StringUtil.isBlank(r.get("FILE_URL"))) {
						throw new ActionException("签收单信息未录入！");
					}
					if (StringUtil.isBlank(r.get("RECEIVE_DATE"))) {
						throw new ActionException("请录入签收日期！");
					}
					if (StringUtil.isBlank(r.get("FILE_URL"))) {
						throw new ActionException("请上传签收单！");
					}
				} else {
					throw new ActionException("签收单信息未录入！");
				}

			} else {
				throw new ActionException("签收单信息未录入！");
			}
		} else {
			throw new ActionException("签收单信息获取失败！");
		}

	}

	/**
	 * 核对and核销
	 * 
	 * @param PROJECT_ID
	 */
	public void JudgeFirstPay(String PROJECT_ID) {
		if (StringUtils.isNotBlank(PROJECT_ID)) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("PROJECT_ID", PROJECT_ID);

			// 0.1判断是否有车架号

			List<Object> selectList = Dao.selectList("checkAndHire.isCarSymbol", m);
			Map mapCount = (Map) selectList.get(0);
			int carSymbol = Integer.parseInt(String.valueOf(mapCount.get("CAR_SYMBOL")));
			if (carSymbol > 0) {

			} else {
				throw new ActionException("没有车架号！");
			}
			// 0.2判断是否有起租日
			String startDate = "";
			List<Object> receiveDateList = Dao.selectList("checkAndHire.isReceiveDate", m);
			if (receiveDateList.size() > 0) {
				Object receiveDateObj = receiveDateList.get(0);
				if (receiveDateObj != null && receiveDateObj != "") {
					Map receiveDateMap = (Map) receiveDateObj;
					Object rObj = receiveDateMap.get("RECEIVE_DATE");
					if(rObj!=null && rObj!=""){
					   startDate = String.valueOf(rObj);
					   m.put("startDate", startDate);
					   System.out.println(startDate+"---");
					}else{
						throw new ActionException("没有签收时间！");
					}
				}else{
					throw new ActionException("没有签收时间！");
				}
			} else {
				throw new ActionException("没有签收时间！");
			}
			// 第一步判断首期款是否核对

			// 1.通过合同号得到自己建立表的数据sum
			double sf = 0;
			List<Object> shiFu = Dao.selectList("checkAndHire.getAlreadyPaid", m);
			if (shiFu.size() > 0) {
				Object shiFuObj = shiFu.get(0);
				if (shiFuObj != null && shiFuObj != "") {
					Map mm = (Map) shiFuObj;
					if (mm.containsKey("MONEY")) {
						Object shifu = mm.get("MONEY");
						if (shifu == null || shifu == "") {
							sf = 0;
						} else {
							sf = Double.parseDouble(shifu == null || shifu == "" ? "0" : shifu.toString());
						}
					}
				}
			}
			// 2.通过合同号得到长宝给我的一个首期+租金的数据
			double yf = 0;
			List<Object> yingFu = Dao.selectList("checkAndHire.getAmountPayable", m);
			if (yingFu.size() > 0) {
				Object yingFuObj = yingFu.get(0);
				if (yingFuObj != null && yingFuObj != "") {
					Map mm = (Map) yingFuObj;
					if (mm.containsKey("FIRSTPAYALL")) {
						Object yingfu = mm.get("FIRSTPAYALL");
						if (yingfu == null || yingfu == "") {
							yf = 0;
						} else {
							yf = Double.parseDouble(yingfu == null || yingfu == "" ? "0" : yingfu.toString());
						}
					}
				}
			}
			// 3.进行比较
			if (sf >= yf) {
				// 第二步进行核销

				// 判断是否0首付
				List<Object> isZeroSFList = Dao.selectList("checkAndHire.isZeroSF", m);
				if (isZeroSFList.size() > 0) {
					Object isZeroSF = isZeroSFList.get(0);

					if (isZeroSF != null && isZeroSF != "") {
						Map isZeroSFM = (Map) isZeroSF;
						if (isZeroSFM.containsKey("YSMONEY")) {
							Object zero = isZeroSFM.get("YSMONEY");
							if (zero == null || zero == "") {
								System.out.println("首付为空");
							} else {
								String shoufu = (zero == null || zero == "" ? "0" : String.valueOf(zero));
								int shouFu = Integer.parseInt(shoufu);
								if (shouFu > 0) {
									// 核首付
									int update = Dao.update("checkAndHire.updateBegSF", m);
									if (update > 0) {

									} else {
										Dao.rollback();
										throw new ActionException("核首付不成功,但是实收>=应收！");
									}
								}
							}
						}
					}
				}

				// 1.得到预付租金 几期
				List<Object> monthNo = Dao.selectList("checkAndHire.getMonth", m);
				int month = 0;
				if (monthNo.size() > 0) {
					Object object = monthNo.get(0);
					if (object != null && object != "") {
						Map mm = (Map) object;
						if (mm.containsKey("MONTH")) {
							Object mongthno = mm.get("MONTH");
							if (mongthno == null || mongthno == "") {
								month = 0;
							} else {
								month = Integer
										.parseInt(mongthno == null || mongthno == "" ? "0" : mongthno.toString());
							}
						}
					}
				}
				if (month > 0) {
					// 2.更新beginning的状态 1 1
					m.put("MONTH", month);
					Dao.update("checkAndHire.updateBeg", m);
				}
				Dao.commit();
			} else {
				Dao.rollback();
				throw new ActionException("首付不足！");
			}

		} else {
			throw new ActionException("签收单信息获取失败！");
		}
	}

	/**
	 * 提车申请
	 *
	 * @author 钟林俊
	 * @param PROJECT_ID 项目id
	 */
	public void CarPurchaseRequest(String PROJECT_ID) {
		if (Strings.isNullOrEmpty(PROJECT_ID)) {
			throw new ActionException("项目ID为空！");
		}

		// 查找采购请求数据
		List<Map<String, Object>> purchaseRequests = Dao.selectList("CarPurchaseMapper.selectRequestParams", PROJECT_ID);

		if (CollectionUtils.isEmpty(purchaseRequests)) {
			throw new ActionException("没有符合项目ID：" + PROJECT_ID + " 的数据！");
		}

		// 查找门店信息
		Map<String, Object> store = Dao.selectOne("CarPurchaseMapper.selectOrgan", PROJECT_ID);

		// 请求数据临时容器
		MultiValueMap tempParams = new MultiValueMap();
		// 请求数据容器
		MultiValueMap requestParams = new MultiValueMap();

		// 将门店信息合并到临时容器
		for (Map<String, Object> map : purchaseRequests) {
			tempParams.putAll(map);
			if (!MapUtils.isEmpty(store)) {
				tempParams.put("ORGAN_CODE", store.get("ORGAN_CODE"));
				tempParams.put("ORGAN_NAME", store.get("ORGAN_NAME"));
			}
		}

		String key;
		// 参数名转换 大写下划线转小驼峰
		for (Object o : tempParams.keySet()) {
			key = (String) o;
			// noinspection unchecked
			requestParams.putAll(com.pqsoft.util.StringUtils.underlineToCamel(key, false),
					(List<String>) tempParams.get(key));
		}

		// 读取接口地址的配置文件
		ConfigService configService;
		try {
			configService = new ConfigService();
		} catch (ConfigurationException e) {
			throw new ActionException("车辆采购接口配置文件读取失败！");
		}

		// 发送请求并获取结果
		@SuppressWarnings("unchecked")
		String resp = HttpUtil.httpPost(configService.get("host") + configService.get("carPurchaseUrl"), requestParams);
		if (Strings.isNullOrEmpty(resp)) {
			throw new ActionException("提车申请响应为空！");
		}

		Map<String, Object> respMap = JsonUtil.toMap(JsonUtil.toJson(resp));
		String status = String.valueOf(respMap.get("status"));
		if ("error".equals(status) || "null".equals(status)) {
			// logger.error("提车申请失败: " + respMap.get("errorMessage"));
			throw new ActionException("提车申请失败: " + respMap.get("errorMessage"));
		}
	}

	/**
	 * 定金清零 *@author gengchangbao 2016年5月31日16:39:27
	 */

	public void ClearDeposit(String PROJECT_ID){
		if (Strings.isNullOrEmpty(PROJECT_ID)) {
			throw new ActionException("项目ID为空！");
		}
		Dao.update("project.clearDeposit", PROJECT_ID);
	}
	
	/**
	 * 资料上传必填项验证
	 * JZZL-209
	 * @param PROJECT_ID
	 *            项目id
	 */
	public void JudgeContractEDoc(String PROJECT_ID) {
		if (Strings.isNullOrEmpty(PROJECT_ID)) {
			throw new ActionException("项目ID为空！");
		}
		List<Map<String, Object>> resultFileList = Dao.selectList("datatemplet.uploadFileForCheck", PROJECT_ID);
		if (resultFileList != null && resultFileList.size()>0) {
			Map<String, Object> fileInfo = null;
			for (int i = 0; i < resultFileList.size(); i++) {
				fileInfo = resultFileList.get(i);
				if (fileInfo != null) {
					Dao.getClobTypeInfo2(fileInfo, "PATHS");
					if (fileInfo.get("PATHS") == null || "".equals(fileInfo.get("PATHS"))) {
						throw new ActionException("请上传合同审核资料必须项！");
					}
				}

			}
		}else {
			throw new ActionException("请上传合同审核资料必须项！");

		}
		//add gengchangbao JZZL-245 2016年7月7日12:51:39 Start
		//验证项目是否有代扣银行卡（如果有银行卡但是没有代扣或者代扣的银行卡被删除，给该客户的第一个银行卡做默认代扣处理）
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("PROJECT_ID", PROJECT_ID);
		Map<String,Object> bankInfo = Dao.selectOne("customers.queryProAndOpeBankId",param);
		if (bankInfo != null && (bankInfo.get("BANK_ID") == null || "".equals(bankInfo.get("BANK_ID").toString()))
				&& bankInfo.get("C_BANK_ID") != null && !"".equals(bankInfo.get("C_BANK_ID").toString())) {
			String BANK_ID = "";
			BANK_ID = bankInfo.get("C_BANK_ID").toString();
			param.put("BANK_ID", BANK_ID);
			Dao.update("customers.updateProjectBankId",param);
		}
		//add gengchangbao JZZL-245 2016年7月7日12:51:39 End
		List<Map<String, Object>> resultBankList = Dao.selectList("project.checkBankInfo", PROJECT_ID);
		if (!(null !=resultBankList &&  resultBankList.size() > 0)) {
			throw new ActionException("请录入银行卡（代扣卡）信息！");
		}
	}
}
