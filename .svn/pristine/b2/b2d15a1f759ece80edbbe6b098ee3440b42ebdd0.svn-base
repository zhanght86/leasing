package com.pqsoft.bpm.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.velocity.VelocityContext;
import org.dom4j.DocumentException;
import org.jbpm.pvm.internal.repository.JBPMCACHE;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.JbpmPng;
import com.pqsoft.bpm.JbpmXml;
import com.pqsoft.bpm.service.DeploymentService;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.JsonUtil;

public class DeploymentAction extends Action {

	// 流程定义页面
	@aPermission(name = { "流程管理", "流程定义" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		SysDictionaryMemcached dd = new SysDictionaryMemcached();
		context.put("modules", dd.get("流程定义模块"));
		context.put("projectType", dd.get("业务类型"));
		context.put("platform", new DeploymentService().getFhfa());
		return new ReplyHtml(VM.html("bpm/deployment.vm", context));
	}

	// ajax分页
	@aPermission(name = { "流程管理", "流程定义" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply getPage() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(new DeploymentService().getPage(param));
	}

	// 流程所有版本
	@aPermission(name = { "流程管理", "流程定义" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply getAllVersionByPDKEY() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PDKEY", SkyEye.getRequest().getParameter("PDKEY"));
		DeploymentService service = new DeploymentService();
		JSONArray array = JSONArray.fromObject(service.getDeploymentAllVersion(map));
		return new ReplyJson(array);
	}

	// 上传新流程
	@aPermission(name = { "流程管理", "流程定义", "流程发布" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply doUploadDeployment() {
		try {
			List<FileItem> list = _getFileItem();
			for (FileItem fileItem : list) {
				String deploymentId = JBPM.deploy(fileItem.getInputStream());
				DeploymentService service = new DeploymentService();
				Map<String, Object> deployment = service.getJbpmDeploymen(deploymentId);
				// 更新自定义的流程定义表数据
				if (deployment != null && service.getDeploymenDiyCount(deployment) == 0) {
					service.addDeploymen(deployment);
				}
				// else { //如需自动启用最新流程可放开此段代码
				// service.updateDeploymenDiy(deployment);
				// }
			}
		} catch (Exception e) {
			throw new ActionException("发布出现异常，请检查文件", e);
		}
		try {
			SkyEye.getResponse().sendRedirect(SkyEye.getRequest().getContextPath() + "/bpm/Deployment.action");
		} catch (IOException e) {
			throw new ActionException("发布完成，跳转页面失败，请刷新", e);
		}
		return null;
	}

	@aPermission(name = { "流程管理", "流程定义", "流程删除" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply doDelDeployment() {
		String id = SkyEye.getRequest().getParameter("id");
		System.out.println(id);
		JBPM.deleteDeploymentCascade(id);
		return new ReplyAjax();
	}

	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	@aAuth(type = aAuthType.LOGIN)
	public Reply png() {
		String id = SkyEye.getRequest().getParameter("id");
		return new ReplyFile(new JbpmPng().get(id), id + ".png");
	}

	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	@aAuth(type = aAuthType.LOGIN)
	public Reply xml() {
		String id = SkyEye.getRequest().getParameter("id");
		return new ReplyFile(new JbpmXml().get(id), id + ".jpdl.xml");
	}

	/**
	 * 进入配置流程图页面 调用此方法必须传入参数:流程定义id
	 * 
	 * @param id
	 *            流程定义id ====> eg:资产包业务立项流程-1
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-8-29 上午09:32:45
	 */
	@aPermission(name = { "流程管理", "流程定义", "流程定义设置" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply toUpdateJbpmConfiguration() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		// param.put("id", "资产包业务立项流程-1");//测试用
		Map<String, Map<String, Object>> coordinatesMap = new HashMap<String, Map<String, Object>>();
		try {
			coordinatesMap = new DeploymentService().imgNodeCoordinates(param.get("id").toString());// 获取流程图各节点的坐标
		} catch (Exception e) {
			return new ReplyHtml("参数中没有流程定义id ！");
		}
		List<String> coordinatesMapKeyList = new ArrayList<String>();
		try {
			// 迭代coordinatesMap中Key的操作
			Iterator<String> it = coordinatesMap.keySet().iterator();
			while (it.hasNext()) {
				coordinatesMapKeyList.add(it.next());
			}
		} catch (Exception e) {
			throw new ActionException("没有找到流程定义id为" + param.get("id") + "流程图！", e);
		}
		context.put("coordinatesMapKeyList", coordinatesMapKeyList);
		context.put("coordinatesMap", coordinatesMap);
		context.put("JAVA_LIST", new SysDictionaryMemcached().get("JAVA节点流程配置"));
		context.put("id", param.get("id"));
		return new ReplyHtml(VM.html("bpm/jbpmConfigurationUpdate.vm", context));
	}

	/**
	 * 查询流程图节点详情 参数至少包含以下三个
	 * 
	 * @param PDID
	 *            流程定义id
	 * @param NAME
	 *            节点名称
	 * @param TYPE
	 *            节点类型
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-8-29 上午09:43:20
	 */
	@aPermission(name = { "流程管理", "流程定义", "流程定义设置" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doSelectJbpmConfigurationVM() {
		Map<String, Object> param = _getParameters();
		if (!param.isEmpty() && param.containsKey("TYPE") && !"".equals(param.get("TYPE"))) {
			Map<String, Object> detailMap = new DeploymentService().jbpmNodeDetails(param);
			if (detailMap != null && !detailMap.isEmpty()) {
				param.putAll(detailMap);
			}
			if ("task".equals(param.get("TYPE")) || "start".equals(param.get("TYPE")) || "join".equals(param.get("TYPE"))) { return new ReplyAjax(
					JSONObject.fromObject(param)); }
			return new ReplyAjax(false, "系统中暂时没有" + param.get("TYPE") + "类型的配置模版！");
		} else {
			return new ReplyAjax(false, "缺少流程图信息");
		}
	}

	/**
	 * 更新流程图节点详情 参数至少包含以下三个
	 * 
	 * @param PDID
	 *            流程定义id
	 * @param NAME
	 *            节点名称
	 * @param TYPE
	 *            节点类型
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-8-29 上午09:43:20
	 */
	@aPermission(name = { "流程管理", "流程定义", "流程定义设置" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doUpdateJbpmConfiguration() {
		Map<String, Object> param = _getParameters();
		param = JsonUtil.toMap(JSONObject.fromObject(param.get("param")));
		Map<String, Object> detailMap = new DeploymentService().jbpmNodeDetails(param);
		boolean flag = false;
		String msg = "";
		String log = "";
		{
			// 修改流程图XML文件
			// if (param.containsKey("ASSIGN_TYPE") &&
			// param.containsKey("ASSIGN_VALUE")) {
			// }
			try {
				new DeploymentService().updateXml(param);
			} catch (DocumentException e) {
				e.printStackTrace();
				throw new ActionException("修改流程图XML文件出错！");
			}
		}
		param.put("JAVA_NAME", param.get("FLAG"));
		if (detailMap != null && !detailMap.isEmpty()) {
			if (detailMap.containsKey("noConfig")) {
				log = "添加";
				flag = new DeploymentService().addJbpmNodeDetails(param);
			} else {
				log = "修改";
				flag = new DeploymentService().updateJbpmNodeDetails(param);
			}
		} else {
			log = "添加";
			flag = new DeploymentService().addJbpmNodeDetails(param);
		}
		if (flag) {
			JBPMCACHE.clean();
			msg = "保存成功！";
		} else {
			msg = "保存失败！";
		}
		return new ReplyAjax(flag, msg).addOp(new OpLog("配置流程图", log, param.toString()));
	}

	// 测试用
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply startTest() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("SUPPLIER_ID", "zy096");
		map.put("var1", "5");
		JBPM.startProcessInstanceById(SkyEye.getRequest().getParameter("PDID"), "1000", "1000", "paycode", map);
		return new ReplyAjax();
	}

	// 设置模块
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "流程管理", "流程定义", "流程定义设置" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply doSetModule() {
		DeploymentService service = new DeploymentService();
		Map<String, Object> param = _getParameters();
		if (service.getDeploymenDiyCount(param) > 0) {
			service.updateDeployment(param);
		} else {
			service.addDeploymen(param);
		}
		return new ReplyAjax();
	}

	// 设置启用
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "流程管理", "流程定义", "流程定义设置" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply doUsing() {
		DeploymentService service = new DeploymentService();
		Map<String, Object> param = _getParameters();
		if (service.getDeploymenDiyCount(param) > 0) {
			service.updateDeployment(param);
		} else {
			service.addDeploymen(param);
		}
		return new ReplyAjax();
	}

}
