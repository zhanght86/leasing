package com.pqsoft.bpm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.pqsoft.bpm.JbpmXml;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class DeploymentService {

	public Page getPage(Map<String, Object> map) {
		map.put("TYPE1", "流程定义模块");
		map.put("TYPE2", "业务类型");
		return PageUtil.getPage(map, "bpm.deployment.getPageList", "bpm.deployment.getPageCount");
	}

	//
	// @SuppressWarnings("unchecked")
	// public Map<String, Object> getDeploymen(String deploymentId) {
	// return (Map<String, Object>) Dao.selectOne(
	// "bpm.deployment.getDeploymen", deploymentId);
	// }

	public int getDeploymenDiyCount(Map<String, Object> deployment) {
		return Dao.selectInt("bpm.deployment.getDeploymenDiyCount", deployment);
	}

	public int addDeploymen(Map<String, Object> deployment) {
		return Dao.update("bpm.deployment.addDeploymen", deployment);
	}

	public int updateDeployment(Map<String, Object> param) {
		return Dao.update("bpm.deployment.updateDeployment", param);
	}

	/**
	 * 获取流程图节点详情
	 * 
	 * @param PDID
	 *            流程定义id
	 * @param NAME
	 *            节点名称
	 * @param TYPE
	 *            节点类型
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-8-29 上午09:52:30
	 */
	public Map<String, Object> jbpmNodeDetails(Map<String, Object> param) {
		Map<String, Object> re = null;
		try {
			re = Dao.selectOne("bpm.deployment.jbpmNodeDetails", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (re == null) {
				re = new HashMap<String, Object>();
				re.put("noConfig", "noConfig");
			}
			List<Element> taskElements = new SAXReader().read(new JbpmXml().getFile((String) param.get("PDID"))).getRootElement().elements();
			for (Element element : taskElements) {
				String nodeName = element.attributeValue("name");
				if (nodeName == null) continue;
				if (nodeName.equals(param.get("NAME"))) {
					String ASSIGN_VALUE = null;
					//任务节点
					if ("task".equals(element.getName())) {
						ASSIGN_VALUE = element.attributeValue("assignee");
						if (ASSIGN_VALUE != null && !"".equals(ASSIGN_VALUE)) {
							re.put("ASSIGN_VALUE", ASSIGN_VALUE);
							re.put("ASSIGN_TYPE", "ASSIGN_CODE");
							break;
						}
						ASSIGN_VALUE = element.attributeValue("candidate-groups");
						if (ASSIGN_VALUE != null && !"".equals(ASSIGN_VALUE)) {
							re.put("ASSIGN_VALUE", ASSIGN_VALUE);
							re.put("ASSIGN_TYPE", "ASSIGN_ORG");
							break;
						}
						if (element.element("assignment-handler") != null) {
							Element handler = element.element("assignment-handler");
							if ("com.pqsoft.bpm.Sup2OpAssignee".equals(handler.attributeValue("class"))) {
								ASSIGN_VALUE = handler.element("field").element("string").attributeValue("value");
								if (ASSIGN_VALUE != null && !"".equals(ASSIGN_VALUE)) {
									re.put("ASSIGN_VALUE", ASSIGN_VALUE);
									re.put("ASSIGN_TYPE", "SUP_OP");
									break;
								}
							}
						}
						if (element.element("assignment-handler") != null) {
							ASSIGN_VALUE = element.element("assignment-handler").attributeValue("class");
							if (ASSIGN_VALUE != null && !"".equals(ASSIGN_VALUE)) {
								re.put("ASSIGN_VALUE", ASSIGN_VALUE);
								re.put("ASSIGN_TYPE", "ASSIGN_HANDLER");
								break;
							}
						}
						break;
					}
					// 判断节点
					if ("decision".equals(element.getName())) {
						Element element2 = element.element("handler");
						if (element2 != null) {
							ASSIGN_VALUE = element2.attributeValue("class");
						}
						if (ASSIGN_VALUE != null && !"".equals(ASSIGN_VALUE)) {
							re.put("ASSIGN_VALUE", ASSIGN_VALUE);
							re.put("ASSIGN_TYPE", "ASSIGN_ORG");
							break;
						}
						break;
					}
					// join节点
					if ("join".equals(element.getName())) {
						ASSIGN_VALUE = element.attributeValue("multiplicity");
						if (ASSIGN_VALUE != null && !"".equals(ASSIGN_VALUE)) {
							re.put("ASSIGN_VALUE", ASSIGN_VALUE);
							re.put("ASSIGN_TYPE", "ASSIGN_ORG");
							break;
						}
						break;
					}
					// java节点
					if ("java".equals(element.getName())) {
						ASSIGN_VALUE = element.attributeValue("class");
						if (ASSIGN_VALUE != null && !"".equals(ASSIGN_VALUE)) {
							re.put("ASSIGN_VALUE_CLASS", ASSIGN_VALUE);
						}
						ASSIGN_VALUE = element.attributeValue("method");
						if (ASSIGN_VALUE != null && !"".equals(ASSIGN_VALUE)) {
							re.put("ASSIGN_VALUE_METHOD", ASSIGN_VALUE);
						}
						ASSIGN_VALUE = element.element("arg").element("ref").attributeValue("object");
						if (ASSIGN_VALUE != null && !"".equals(ASSIGN_VALUE)) {
							re.put("ASSIGN_VALUE_PARAM", ASSIGN_VALUE);
						}
						break;
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	/**
	 * 更新流程图节点详情
	 * 
	 * @param PDID
	 *            流程定义id
	 * @param NAME
	 *            节点名称
	 * @param TYPE
	 *            节点类型
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-8-29 上午09:52:30
	 */
	public boolean updateJbpmNodeDetails(Map<String, Object> param) {
		return Dao.update("bpm.deployment.updateJbpmNodeDetails", param) >= 1;
	}

	/**
	 * 添加流程图节点详情
	 * 
	 * @param PDID
	 *            流程定义id
	 * @param NAME
	 *            节点名称
	 * @param TYPE
	 *            节点类型
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-8-29 上午09:52:30
	 */
	public boolean addJbpmNodeDetails(Map<String, Object> param) {
		return Dao.insert("bpm.deployment.addJbpmNodeDetails", param) >= 1;
	}

	/**
	 * 获取流程图各节点坐标
	 * 
	 * @param id
	 *            流程定义id
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-8-29 上午09:51:26
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, Object>> imgNodeCoordinates(String id) {
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("id", id);
		// map = (Map<String, Object>) Dao.selectOne("bpm.deployment.getXml",
		// id);
		try {
			// BufferedInputStream is = new BufferedInputStream(((Blob)
			// map.get("BLOB")).getBinaryStream());
			Document doc = new SAXReader().read(new JbpmXml().getFile(id));
			Element root = doc.getRootElement();
			List<Element> lst = root.elements();
			Map<String, Map<String, Object>> re = new HashMap<String, Map<String, Object>>();
			for (Element ele : lst) {
				String zb = ele.attributeValue("g");
				String name = ele.attributeValue("name");
				String type = ele.getName();// 节点标签类型
				if (StringUtils.isNotEmpty(zb) && StringUtils.isNotEmpty(name)) {
					Map<String, Object> m = new HashMap<String, Object>();
					String[] zbs = zb.split("[,]");
					String nw = zbs[2];
					String nh = zbs[3];
					String nl = zbs[0];
					String nt = zbs[1];
					m.put("name", name);// 节点名称
					m.put("width", nw);
					m.put("height", nh);
					m.put("nw", Integer.parseInt(nw) + Integer.parseInt(nl));// 节点框的宽度
					m.put("nh", Integer.parseInt(nh) + Integer.parseInt(nt));// 节点框的高度
					m.put("nl", nl);// 框距左边距离
					m.put("nt", nt);// 框距上边距离
					m.put("id", id);// 流程定义id
					m.put("type", type);// 节点标签类型
					re.put(name, m);
				}
			}
			return re;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object getDeploymentAllVersion(Map<String, Object> map) {
		return Dao.selectList("bpm.deployment.getDeploymentAllVersion", map);
	}

	public Map<String, Object> getJbpmDeploymen(String deploymentId) {
		return Dao.selectOne("getJbpmDeploymen", deploymentId);
	}

	/**
	 * 修改流程图XML文件
	 * 
	 * @param PDID
	 *            流程定义id
	 * @param ASSIGN_TYPE
	 *            操作方式：ASSIGN_CODE==>操作人、ASSIGN_ORG==>操作组、ASSIGN_HANDLER==>
	 *            特殊指定处理
	 * @param ASSIGN_VALUE
	 *            操作类型对应的值
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-9-12 下午05:39:56
	 */
	@SuppressWarnings("unchecked")
	public void updateXml(Map<String, Object> param) throws DocumentException {
		Document doc = new SAXReader().read(new JbpmXml().getFile(param.get("PDID").toString()));
		// List<Element> nodes =
		// doc.selectNodes("/*[name()='process']/*[name()='task'][@name='"+param.get("NAME")+"']");
		Element root = doc.getRootElement();
		List<Element> nodes = root.elements();
		for (Element ele : nodes) {
			String nodeName = ele.attributeValue("name");
			if (nodeName == null || !nodeName.equals(param.get("NAME"))) continue;
			boolean flag = false;
			if ("task".equals(ele.getName())) {
				delete(ele);
				if ("ASSIGN_CODE".equals(param.get("ASSIGN_TYPE"))) {
					ele.addAttribute("assignee", (String) param.get("ASSIGN_VALUE"));// 添加操作人
				} else if ("ASSIGN_ORG".equals(param.get("ASSIGN_TYPE"))) {
					ele.addAttribute("candidate-groups", (String) param.get("ASSIGN_VALUE"));// 添加操作组
					Element handler = ele.addElement("assignment-handler");
					handler.addAttribute("class", "com.pqsoft.bpm.GroupAssignee");// 供应商对应人（角色）
				} else if ("ASSIGN_HANDLER".equals(param.get("ASSIGN_TYPE"))) {
					ele.addElement("assignment-handler").addAttribute("class", param.get("ASSIGN_VALUE").toString());// 添加特殊指定处理
				} else if ("SUP_OP".equals(param.get("ASSIGN_TYPE"))) {
					Element handler = ele.addElement("assignment-handler");
					handler.addAttribute("class", "com.pqsoft.bpm.Sup2OpAssignee");// 供应商对应人（角色）
					Element field = handler.addElement("field");
					field.addAttribute("name", "roleName");
					field.addElement("string").addAttribute("value", param.get("ASSIGN_VALUE").toString());
				}
				flag = true;
			}
			// 判断节点
			if ("decision".equals(ele.getName())) {
				if (ele.attribute("expr") != null) ele.remove(ele.attribute("expr"));
				for (Object element : ele.elements("handler")) {
					ele.remove((Element) element);
				}
				ele.addElement("handler").addAttribute("class", param.get("ASSIGN_VALUE_DECISION").toString());// 添加特殊指定处理
				flag = true;
			}
			// join节点
			if ("join".equals(ele.getName())) {
				ele.addAttribute("multiplicity", (String) param.get("ASSIGN_VALUE_JOIN"));
				flag = true;
			}
			// java节点
			if ("java".equals(ele.getName())) {
				if (ele.attribute("class") != null) ele.remove(ele.attribute("class"));
				if (ele.attribute("method") != null) ele.remove(ele.attribute("method"));
				ele.remove(ele.element("field"));
				ele.remove(ele.element("arg"));
//				List<Element> es = ele.elements("arg");
//				for (int i = 0; i < es.size(); i++) {
//					Element e = es.get(i);
//					ele.remove(e);
//				}
				param.put("TYPEDD","JAVA节点流程配置");
				Map<String,Object> ddMap = Dao.selectOne("bpm.deployment.getDD", param);
				ele.addAttribute("class", ddMap.get("FLAG_INTNA").toString());
				ele.addAttribute("method", ddMap.get("SHORTNAME").toString());
				ele.addElement("field").addAttribute("name", "JBPM_JAVA_NAME").addElement("string").addAttribute("value", ddMap.get("FLAG").toString());
				ele.addElement("arg").addElement("ref").addAttribute("object", ddMap.get("REMARK").toString());
				flag = true;
			}
			if (flag) {
				byte[] xml = doc.asXML().getBytes();
				param.put("xml", xml);
				Map<String, Object> m = Dao.selectOne("bpm.deployment.getLob", param);
				// JBPM.updateXml(m.get("DEPLOYMENT_").toString(),
				// m.get("NAME_").toString(), new ByteArrayInputStream(xml));
				// blob数据量大的 不可嵌套做update 可能是11g的问题。
				param.put("DBID_", m.get("DBID_"));
				Dao.update("bpm.deployment.updateXml1", param);
				// Dao.update("bpm.deployment.updateXml", param);
				// Dao.update("bpm.deployment.testxml", param);
				new JbpmXml().clean(param.get("PDID").toString());// 清理xml缓存文件
			}
			break;
		}
	}

	/**
	 * 删除所有操作方式
	 * 
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-9-12 下午05:43:52
	 */
	@SuppressWarnings("unchecked")
	public void delete(Element ele) {
		ele.addAttribute("assignee", "assignee");// 添加操作人
		ele.addAttribute("candidate-groups", "candidate-groups");// 添加操作组
		ele.addElement("assignment-handler").addAttribute("class", "assignment-handler");// 添加特殊指定处理
		ele.remove(ele.attribute("assignee"));// 删除操作人
		ele.remove(ele.attribute("candidate-groups"));// 删除操作组
		{// 删除特殊指定处理
			List<Element> nodes2 = ele.elements();
			for (Element e : nodes2) {
				if (e.getName().equals("assignment-handler")) {
					ele.remove(e);
				}
			}
		}
	}

	public List<Map<String, Object>> getFhfa() {
		return Dao.selectList("bpm.deployment.getFhfa");
	}
}
