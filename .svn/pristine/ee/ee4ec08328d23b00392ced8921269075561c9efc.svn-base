package com.pqsoft.bpm.action;

import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jsp.weixin.ParamesAPI.util.WeixinJSUtil;
import net.sf.json.JSONArray;

//import org.apache.tools.ant.types.resources.comparators.Date;
import org.apache.velocity.VelocityContext;

import com.pqsoft.action.SysAction;
import com.pqsoft.base.interfaceTemplate.service.RunInterfaceTemplate;
import com.pqsoft.base.task.service.TaskClaimService;
import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.DeploymentService;
import com.pqsoft.bpm.service.MemoService;
import com.pqsoft.bpm.service.RemarksService;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
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
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.User;
import com.pqsoft.skyeye.rbac.api.User.Org;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.ReplyExcel;

public class TaskAction extends Action {

	@Override
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "待办事宜", "待办事宜", "查询" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply execute() {// 待办事宜列表
		VelocityContext context = new VelocityContext();
		TaskService service = new TaskService();
		context.put("entrust", service.getEntrustOne(Security.getUser().getCode()));
		context.put("code", Security.getUser().getCode());
		//客户来源
		List<Object> CUST_SOURCE = (List)new DataDictionaryMemcached().get("客户来源");
		context.put("scaleSourceTypes", CUST_SOURCE);
		context.put("colorJson", JSONArray.fromObject(new DataDictionaryMemcached().get("颜色编码库")));
		
		return new ReplyHtml(VM.html("bpm/task.vm", context));
	}
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "待办事宜", "待办事宜", "查询" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply getUsers() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param =_getParameters();
		context.put("param", param);
		return new ReplyHtml(VM.html("base/task/getTaskClaimUser.vm", context));
	}
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply getOperatorPage(){
		Map<String,Object> param = _getParameters();
		TaskClaimService service= new TaskClaimService();
		param.put("ORG_ID", Security.getUser().getOrg().getId());
		return new ReplyAjaxPage(service.getUsers(param));
	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "待办事宜", "待办事宜", "查询" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply revokeTaskMan(){
		Map<String, Object> m = _getParameters();
		TaskClaimService service= new TaskClaimService();
		String msg = "";
		Boolean flag = true ;
//			m.put("STATE", 1);
//			int i=service.Claim(m);
		int i=service.updTaskMan(m);
		if(i>0){
//				service.updFunction(m);
			msg ="撤销任务成功!";
			flag = true; 
			return new ReplyAjax(flag, msg);
		}else{
			msg ="撤销任务失败!";
			flag = false; 
			return new ReplyAjax(flag, msg);
		}
	}
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "待办事宜", "待办事宜", "查询" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply choose() {// 待办事宜列表
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("bpm/taskChoose.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "待办事宜", "待办事宜", "查询" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply getEntrust() {
		VelocityContext context = new VelocityContext();
		TaskService service = new TaskService();
		context.put("entrust", service.getEntrustOne(Security.getUser().getCode()));
		return new ReplyHtml(VM.html("bpm/taskEntrust.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply portal() {// 主页 我的事宜小窗
		VelocityContext context = new VelocityContext();
		TaskService service = new TaskService();
		Map<String, Object> param = _getParameters();
		param.put("USERCODE", Security.getUser().getCode());
		param.put("SECU_ID", Security.getUser().getId());
		if (!Security.getUser().getCode().equals("god")) {
			param.put("ORG_ID", Security.getUser().getOrg().getId());
		} else {
			param.put("ORG_ID", "");
		}
		List<Map<String, Object>> list = service.portal(param);
		context.put("list", list);
		context.put("paymentCount", list.size());
		return new ReplyHtml(VM.html("bpm/taskPortal.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply portal_db() {
		VelocityContext context = new VelocityContext();
		TaskService service = new TaskService();
		Map<String, Object> param = _getParameters();
		param.put("USERCODE", Security.getUser().getCode());
		context.put("list", service.portal_db(param));
		return new ReplyHtml(VM.html("bpm/taskPortal_db.vm", context));
	}

	// ajax分页
	@aPermission(name = { "待办事宜", "待办事宜", "查询" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply getPage() {
		if (Security.isWeixin()) {// 微信展示
			Map<String, Object> param = _getParameters();
			param.put("USERCODE", Security.getUser().getCode());
			VelocityContext context = new VelocityContext();
			context.put("wxcfg", WeixinJSUtil.sign("http://server.pqsoft.cn/wx/bpm/Task!getPage.action"));
			context.put("page", new TaskService().getPage(param));
			return new ReplyHtml(VM.html("bpm/wx_task_list.vm", context));
		}
		Map<String, Object> param = _getParameters();
		param.put("USERCODE", Security.getUser().getCode());
		param.put("UNDERLING", "no");

		return new ReplyAjaxPage(new TaskService().getPage(param));
	}
	
	// ajax分页
	@aPermission(name = { "待办事宜", "待办事宜", "查询" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply getChoosePage() {
		Map<String, Object> param = _getParameters();
		param.put("USERCODE", Security.getUser().getCode());
		return new ReplyAjaxPage(new TaskService().getChoosePage(param));
	}
	
	@aPermission(name = { "待办事宜", "待办事宜", "查询" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply doChoose() {
		Map<String, Object> param = _getParameters();
		param.put("USERCODE", Security.getUser().getCode());
		new TaskService().doChoose(param);
		return new ReplyAjax();
	}

	// ajax分页
	@aPermission(name = { "待办事宜", "待办事宜", "查询" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply getPageEntrust() {
		Map<String, Object> param = _getParameters();
		param.put("USERCODE", Security.getUser().getCode());
		return new ReplyAjaxPage(new TaskService().getPageEntrust(param));
	}

	/**
	 * 历史任务
	 * 
	 * @return
	 */
	@aPermission(name = { "待办事宜", "待办事宜", "查询" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply toOldTask() {
		return new ReplyHtml(VM.html("bpm/taskOld.vm", null));
	}

	/**
	 * 历史任务
	 * 
	 * @return
	 */
	@aPermission(name = { "待办事宜", "待办事宜", "查询" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply getOldPage() {
		Map<String, Object> param = _getParameters();
		param.put("USERCODE", Security.getUser().getCode());
		return new ReplyAjaxPage(new TaskService().getOldPage(param));
	}

	/**
	 * 根据jbpmId 查看流程处理页面
	 * 
	 * @Function: com.pqsoft.bpm.action.TaskAction.toShow
	 * @return 流程处理页面
	 * @author: 吴剑东
	 * @date: 2013-9-6 上午10:43:18
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "待办事宜", "待办事宜", "处理" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply toShow() {
		TaskService service = new TaskService();
		VelocityContext context = new VelocityContext();
		//流程状态
		String statusFlag = SkyEye.getRequest().getParameter("status");
		//将statusFlag放到session里面
		SkyEye.getRequest().getSession().setAttribute("statusFlag", statusFlag);
		// 任务ID
		String taskId = SkyEye.getRequest().getParameter("taskId");
		// 流程ID
		String jbpmId = service.getJbpmIdBytaskId(taskId);
		if (Security.isWeixin()) {
			List<Map<String, Object>> memos = new MemoService().getMemos(service.getShortName(jbpmId));
			context.put("jbpm", service.getJbpm(service.getShortName(jbpmId)));
			context.put("progress", service.progress(memos));
			context.put("paramFlag", statusFlag);//流程审批状态
			// 查询意见
			context.put("memos", service.reverse(memos));
			return new ReplyHtml(VM.html("bpm/wx_task_op.vm", context));
		}
		try {
			if(jbpmId == null) {
				try {
					new TaskClaimService().updClaimProcedure(taskId, "1");
					Dao.commit();
				} catch (Exception e) {
					e.printStackTrace();
				}
				throw new ActionException("任务已经处理");
			}
			// 获取流程配置
			Map<String, Object> config = service.getConfig(taskId);
			if (config == null) throw new ActionException("该节点未做任何配置，请联系管理员");
			// List list = new ArrayList();
			// if (config == null || config.size() == 0) {
			// throw new ActionException("任务已处理,请刷新任务列表页面", null);
			// } else {
			// // 获取所有可提交下一节点
			// list = service.getNextTransition((String)
			// config.get("PROCDEFID_"), (String) config.get("ACTIVITYNAME_"),
			// jbpmId);
			// }
			// ------ 查询当前任务信息

			// 流程表单url及参数
			context.put("url", service.getContentUrl(jbpmId, config.get("ACTIVITYNAME_") + "", (String) config.get("FORM")));
			// 所有可提交下一节点
			// context.put("flowNodes", list);
			
			/**
			 * quanxian
			 */
			context.put("aututhority", "待办事宜");
			context.put("jbpmId", jbpmId);
			service.setRead(jbpmId);// 设置已读
			context.put("taskId", taskId);
			context.put("pro_code", SkyEye.getRequest().getParameter("pro_code"));
			context.put("delete_op", JBPM.getConfigDelOpByProId(JBPM.getDeploymentNameByProcessId(jbpmId), JBPM.getTaskNameByTaskId(taskId)));
		} catch (ActionException e) {
			throw e;
		} catch (Exception e) {
			throw new ActionException("解析下一步操作节点失败", e);
		}
		// --节点驳回操作
		System.out.println("URL--------------> " + context.get("url"));
		return new ReplyHtml(VM.html("bpm/taskShow.vm", context));
	}

	/**
	 * 根据jbpmId 查看流程历史
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "待办事宜", "待办事宜", "查询" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply toShowOld() {
		VelocityContext context = new VelocityContext();
		try {
			MemoService memoService = new MemoService();
			// 任务ID
			String memoId = SkyEye.getRequest().getParameter("MEMO_ID");
			String jbpmId = SkyEye.getRequest().getParameter("JBPM_ID");
			if (memoId != null) {
				// 流程ID
				Map<String, Object> memo = memoService.getMemo(memoId);
				if (memo == null) throw new ActionException("等待流程审批！");
				jbpmId = (String) memo.get("JBPM_ID");

				// SHOW_FLAG 流程历史查看页面不显示操作按钮
				context.put("url", memo.get("URL") != null ? (memo.get("URL").toString() + "&SHOW_FLAG=1") : memo.get("URL"));
			}
			// 获取流程配置
			// Map<String, Object> config = service.getConfigByMemoId(memoId);
			// service.actOp((String) config.get("INIT_OP"),
			// service.getShortName(jbpmId));
			// if (config == null || config.size() == 0) { throw new
			// ActionException("任务已处理,请刷新任务列表页面", null); }

			// ------ 查询当前任务信息

			// 流程表单url及参数
			// 所有可提交下一节点
			context.put("jbpmId", jbpmId);
			context.put("SHOW_FLAG", "1");
			context.put("jbpmIdUrl", URLEncoder.encode(jbpmId, "UTF-8"));
		} catch (Exception e) {
			throw new ActionException(e.getMessage(), e);

		}
		System.out.println("URL--------------> " + context.get("url"));
		// --节点驳回操作
		return new ReplyHtml(VM.html("bpm/taskShowOld.vm", context));
	}
	
	/**
	 * 根据角色权限限定打开的画面TAB 
	 * 		add gengcb 20160120 JZZL-64  start
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "申请管理", "项目编号链接", "查询" })
	@aDev(code = "gcb", email = "changbaogeng@jiezhongchina.com", name = "耿长宝")
	public Reply tabRightsShow() {
		
		MemoService memoService = new MemoService();
		VelocityContext context = new VelocityContext();
		
		Org o = Security.getUser().getOrg();
		String id = o.getId();
		
		String projectId = SkyEye.getRequest().getParameter("PROJECT_ID");
		String jbpmId = "";
		try {
			String url="";
			
			if (id != null) {
				// 画面TAB
				Map<String, Object> memo = memoService.getTabRights(id);
				if (memo == null) throw new ActionException("角色权限tab未设置");
				url = memo.get("TAB_RIGHTS") != null ? (memo.get("TAB_RIGHTS").toString() + "&SHOW_FLAG=1") : "";
			}
			
			if ("".equals(url)){
				context.put("url", null);
			} else {
				url = url + "&PROJECT_ID="+projectId;
				context.put("url", url);
			}
			
			context.put("PROJECT_ID", projectId);
			
			// add gengchangbao 2016/2/1  JZZL-93 START
			Map<String, Object> jbpmIdMap = memoService.getJbpmIdByProjectId(projectId);
			if (jbpmIdMap != null && jbpmIdMap.get("JBPM_ID") !=null && !"".equals(jbpmIdMap.get("JBPM_ID"))) {
				jbpmId = jbpmIdMap.get("JBPM_ID").toString();
			}
			if (jbpmId != null && !"".equals(jbpmId)) {
				context.put("jbpmId", jbpmId);
				context.put("SHOW_FLAG", "1");
				context.put("jbpmIdUrl", URLEncoder.encode(jbpmId, "UTF-8"));
			}
			// add gengchangbao 2016/2/1  JZZL-93 END
		} catch (Exception e) {
			
		}
		// --节点驳回操作
		System.out.println("URL--------------> " + context.get("url"));
		return new ReplyHtml(VM.html("bpm/projectShow.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aPermission(name = { "待办事宜", "待办事宜", "查询" })
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply doCyOver() {
		TaskService service = new TaskService();
		service.cyOver(SkyEye.getRequest().getParameter("TASK_ID"));
		return new ReplyAjax();
	}

	/**
	 * 查看所有任务处理意见
	 * 
	 * @Function: com.pqsoft.bpm.action.TaskAction.toShowTaskMemo
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-9-6 上午10:47:06
	 */
	@aPermission(name = { "待办事宜", "待办事宜", "查询" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply toShowTaskMemo() {
		String statusFlag = SkyEye.getRequest().getParameter("status");
		try {
			TaskService service = new TaskService();
			VelocityContext context = new VelocityContext();
			// 流程ID
			String taskId = SkyEye.getRequest().getParameter("taskId");
			String jbpmId = service.getJbpmIdBytaskId(taskId);
			// 查询当前任务信息
			List<Map<String, Object>> memos = new MemoService().getMemos(service.getShortName(jbpmId));
			context.put("jbpm", service.getJbpm(service.getShortName(jbpmId)));
			context.put("memoDemos", DataDictionaryMemcached.getList("流程审批意见"));
			context.put("progress", service.progress(memos));
			List<Map<String, Object>> ms=service.reverse(memos);
		 for(int i=0;i<ms.size();i++){
			   Map<String, Object> m= ms.get(i);
			   Object obj= m.get("FILE_PATH");
			   if(obj==null){
		           continue;
				   }
				   String fps=obj.toString();
				   String fp[] = fps.split(","); 
				   List<String> filepathList=new ArrayList<String>();
				  for(int k=0;k<fp.length;k++){
					   filepathList.add(fp[k]);
				   }
				   m.put("FILE_PATH", filepathList);
			   }
			 
			 //信审意见中的外部备注映射到审批意见的外部意见
			 String pro_code = SkyEye.getRequest().getParameter("pro_code");
			 Map<String, Object> mapTa = new TaskService().getLetter(pro_code);
			 context.put("mapLetter", mapTa);
			 
			// 查询意见
			context.put("memos",ms );
			context.put("flag", BaseUtil.isClerk() || !"1".equals(Security.getUser().getPersonType()));
			context.put("user", Security.getUser());
			context.put("taskId", taskId);
			// 流程ID
			// 获取流程配置
			Map<String, Object> config = service.getConfig(taskId);
			List list = new ArrayList();
			if (config == null || config.size() == 0) {
				throw new ActionException("任务已处理,请刷新任务列表页面", null);
			} else {
				// 获取所有可提交下一节点
				list = service.getNextTransition((String) config.get("PROCDEFID_"), (String) config.get("ACTIVITYNAME_"), jbpmId);
			}
			// ------ 查询当前任务信息

			// 流程表单url及参数
			context.put("url", service.getContentUrl(jbpmId, config.get("ACTIVITYNAME_") + "", (String) config.get("FORM")));
			// 所有可提交下一节点
			context.put("flowNodes", list);
			context.put("actUsers", service.getTaskByJbpmId(service.getShortName(jbpmId)));
			List<Map<String, Object>> notgo = new SysDictionaryMemcached().get("拒件理由");
			context.put("notgo", notgo);
			//add by lishuo start
			String USER_NAME =Security.getUser().getRoles().get(0);
			if("客服".equals(USER_NAME)){
				context.put("USER_NAME", USER_NAME);
			}else if("客服主管".equals(USER_NAME)){
				context.put("USER_NAME", USER_NAME);
			}else if("城市副理".equals(USER_NAME)){
				context.put("USER_NAME", USER_NAME);
			}
			//add by lishuo end
			return new ReplyHtml(VM.html("bpm/taskMemoShow.vm", context));
		} catch (ActionException e) {
			throw e;
		} catch (Exception e) {
			throw new ActionException("解析下一步操作节点失败", e);
		}
	}

	/**
	 * 查看所有任务处理意见
	 * 
	 * @Function: com.pqsoft.bpm.action.TaskAction.toShowTaskMemo
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-9-6 上午10:47:06
	 */
	@aPermission(name = { "待办事宜", "待办事宜", "查询" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply toShowTaskOldMemo() {
		TaskService service = new TaskService();
		VelocityContext context = new VelocityContext();
		// 流程ID
		String jbpmId = SkyEye.getRequest().getParameter("jbpmId");
		List<Map<String, Object>> memos = new MemoService().getMemos(service.getShortName(jbpmId));
		context.put("jbpm", service.getJbpm(service.getShortName(jbpmId)));
		context.put("progress", memos.subList(memos.size() > 3 ? memos.size() - 3 : 0, memos.size()));
		context.put("memos", service.reverse(memos));
		context.put("flag", BaseUtil.isClerk() || !"1".equals(Security.getUser().getPersonType()));
		
		// add gengchangbao 2016/2/1 JZZL-93 Start
		//添加标记，讲没有提交审批意见表单
		String readOnlyFlag = SkyEye.getRequest().getParameter("readOnlyFlag");
		if (readOnlyFlag != null && !"".equals(readOnlyFlag)) {
			context.put("readOnlyFlag", readOnlyFlag);
		}
		// add gengchangbao 2016/2/1 JZZL-93 End
		
		context.put("user", Security.getUser());
		context.put("actUsers", service.getTaskByJbpmId(service.getShortName(jbpmId)));
		return new ReplyHtml(VM.html("bpm/taskOldMemoShow.vm", context));
	}

	// 任务流程图跟踪
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	@aPermission(name = { "待办事宜", "待办事宜", "流程跟踪查询" })
	public Reply toTrack() {
		VelocityContext context = new VelocityContext();
		String JBPM_ID = SkyEye.getRequest().getParameter("JBPM_ID");
		TaskService service = new TaskService();
		Map<String, Object> data = service.taskTrack(JBPM_ID);
		context.put("data", data);
		Map<String, Map<String, Object>> coordinatesMap = new HashMap<String, Map<String, Object>>();
		try {
			coordinatesMap = new DeploymentService().imgNodeCoordinates(data.get("PDID").toString());// 获取流程图各节点的坐标
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
			return new ReplyHtml("没有找到" + JBPM_ID + "对应的流程图！");
		}
		context.put("coordinatesMapKeyList", coordinatesMapKeyList);
		context.put("coordinatesMap", coordinatesMap);
		context.put("actUsers", service.getTaskByJbpmId(service.getShortName(JBPM_ID)));
		return new ReplyHtml(VM.html("bpm/taskTrack.vm", context));
	}

	// 保存或更新备注
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	@aPermission(name = { "待办事宜", "待办事宜", "处理" })
	public Reply doSetMemo() {
		boolean flag = false;
		try {
			Map<String, Object> param = _getParameters();
			String taskId = (String) param.get("taskId");
			String memo = (String) param.get("MEMO");
			String WMEMO = (String) param.get("WMEMO");
			if (taskId == null) { return new ReplyAjax(false, "无任务ID"); }
			// 记录备注
			MemoService service = new MemoService();
			User user = Security.getUser();
			// 判断备注是否存在
			if (service.getMemoCount(taskId) > 0) {
				flag = service.upMemo(taskId, user.getName(), user.getCode(), memo, null, null, "BROWSER", WMEMO);
			} else {
				TaskService taskService = new TaskService();
				Map<String, Object> task = taskService.getTask(taskId);
				service.addMemo(taskId, (String) task.get("EXECUTION_ID_"), (String) task.get("NAME_"), null);
				flag = service.upMemo(taskId, user.getName(), user.getCode(), memo, null, null, "BROWSER", WMEMO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ReplyAjax(flag, "");
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "待办事宜", "待办事宜", "处理" })
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public Reply getCoordinatesMapKeyByName() {
		Map<String, Object> param = _getParameters();
		Map<String, Map<String, Object>> coordinatesMap = new HashMap<String, Map<String, Object>>();
		List<Object> coordinatesMapKeyList = new ArrayList<Object>();
		try {
			coordinatesMap = new DeploymentService().imgNodeCoordinates(param.get("taskId").toString());// 获取流程图各节点的坐标

			// 迭代coordinatesMap中Key的操作
			Iterator<String> it = coordinatesMap.keySet().iterator();
			while (it.hasNext()) {
				Map<String, Object> nodeMap = new HashMap<String, Object>();
				nodeMap.put("NAME", it.next());
				coordinatesMapKeyList.add(nodeMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ReplyJson(JSONArray.fromObject(coordinatesMapKeyList));
	}

	/**
	 * 执行流程通过操作
	 * 
	 * @Function: com.pqsoft.bpm.action.TaskAction.doNext
	 * @return
	 * @author: 吴剑东
	 * @date: 2013-9-6 上午10:50:39
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	@aPermission(name = { "待办事宜", "待办事宜", "处理" })
	public Reply doNext() {
		//点击提交或者结束进入下一环节
		boolean flag = false;
		try {
			Map<String, Object> param = _getParameters();
			TaskService service = new TaskService();
			MemoService memoService = new MemoService();
			String taskId = param.get("TASK_ID").toString();
			
			Map<String, Object> config = service.getConfig(taskId);
			if (config == null) throw new ActionException("该节点未做任何配置，请联系管理员");
			//Map<String, Object> memo = memoService.getMemoByTaskId(taskId);
			String jbpmId = service.getJbpmIdBytaskId(taskId);
			jbpmId = service.getShortName(jbpmId);
			{
				// TODO taskId
				new TaskClaimService().updClaimProcedure(taskId, "1");
			}

			// ------------ TODO 进入下一节点
			String tranName_ = param.get("tranName_") == null ? "" : param.get("tranName_").toString();
			String opType = param.get("nextName_") == null ? "" : param.get("nextName_").toString();
			if (opType != "") opType = "提交至" + opType;
			// ------------ 记录备注 操作类型
			User user = Security.getUser();
			Map<String, Object> task = service.getTask(taskId);
			String url = service.getContentUrl(jbpmId, task.get("NAME_") + "", (String) config.get("FORM"));
			String memo_text = "";
			if (param.containsKey("MEMO") && !param.get("MEMO").equals("")) {
				memo_text = param.get("MEMO").toString();
			}
			String wmemo_text = "";
			if (param.containsKey("WMEMO") && !param.get("WMEMO").equals("")) {
				wmemo_text = param.get("WMEMO").toString();
			}
			//查询是否有该任务，如果数据库表中没有该任务，则添加；已有任务则更新
			Map<String,Object> taskSubmit=memoService.getMemosByTaskId(taskId);
			
			//如果已有任务，更新
			if(taskSubmit!=null && taskSubmit.size()>0){
				System.out.println("wmemo_text........."+wmemo_text);
				 flag = memoService.updateMemoSubmit(taskId, user.getName(), user.getCode(), memo_text, opType, url, "BROWSER", wmemo_text,tranName_);
			}else{
				memoService.addMemo(taskId, (String) task.get("EXECUTION_ID_"), (String) task.get("NAME_"), url, user.getName(), user.getCode(),
						memo_text, wmemo_text,tranName_);
			}
			
			/* if (memo == null) {
					memoService.addMemo(taskId, (String) task.get("EXECUTION_ID_"), (String) task.get("NAME_"), url, user.getName(), user.getCode(),
							memo_text, wmemo_text,tranName_);
			 }*/
			// flag = memoService.upMemo(taskId, user.getName(), user.getCode(), memo_text, opType, url, "BROWSER", wmemo_text,tranName_);
			service.addCy(taskId, (String) config.get("CY"));
			SkyEye.getRequest().setAttribute("_TEMP_OP", (String) param.get("nextOpCode"));
			if (tranName_ == null || "".equals(tranName_)) {
				JBPM.completeTask(taskId);
			} else {
				JBPM.completeTask(taskId, tranName_);
			}
			// ------------ 执行后续操作
			// ------------ TODO 设置新任务操作人 需考虑会签情况，用流程图默认功能实现。
			flag = true;
			//拒件原因
			service.updnotGoMemo(param);
			// 发邮件
			String taskName="";
			List<Map<String, Object>> list = service.getTaskByJbpmId(jbpmId);
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				map.put("LAST_NODE", task.get("NAME_"));
				Dao.update("bpm.task.updLastNode",map);
				Map<String, Object> claim = new HashMap<String, Object>();
				// TYPE:流程1数据2; TASK_NAME:任务名称;DATA_ID:数据id;DATA_NAME:描述;
				claim.put("TYPE", "1");
				taskName=map.get("ACTIVITY_NAME_").toString();
				claim.put("TASK_NAME", map.get("ACTIVITY_NAME_"));
				claim.put("DATA_ID", map.get("DBID_"));
				claim.put("USER_CODE", map.get("CODE"));
				claim.put("DATA_NAME", jbpmId);
				claim.put("PROJECT_ID", map.get("PROJECT_ID"));
				claim.put("URL", "bpm/Task!toShow.action?taskId=" + map.get("DBID_"));
				Map<String,Object> Create=Dao.selectOne("Task.Claim.getCreateMan",jbpmId);
				claim.put("CREATEMAN_NAME", Create.get("OP_NAME"));
				claim.put("CREATEMAN_CODE", Create.get("OP_CODE"));
				claim.put("TASK_CREATETIME", Create.get("START_DATE"));
				try {
					new TaskClaimService().addClaim(claim);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			List<Map<String, Object>> list1 = service.getTaskByJbpmId(jbpmId);
			for(int j=0;j<list1.size();j++){
				try {
					Map<String, Object> map = list1.get(j);
					map.put("JBPM_ID", jbpmId);
					if (map != null && map.get("CODE") != null && !map.get("CODE").equals("")) {
						SysAction.setMsg(map.get("CODE")+"", "通知：收到新任务--" + jbpmId);
						String TYPE = "";
						// 邮件通知
						if ("yyyy".equals(map.get("EMAIL"))) {
//							service.sendMail(map.get("CODE").toString(), jbpmId);
							TYPE = "EMAIL"; 
							new RunInterfaceTemplate().sendJbpmMessage("task节点", map, TYPE);
						}
						// 微信通知
//						if ("yyyy".equals(map.get("WX"))) {
////							WeixinService.sendTextMsg(map.get("CODE").toString(), "通知：收到新任务--" + jbpmId, "app5");
//							TYPE = "WX"; 
//							new RunInterfaceTemplate().sendJbpmMessage("task节点", map, TYPE);
//						}
						if ("yyyy".equals(map.get("SMS"))) {
							// 通知短信
//							map.get("MOBILE");// 手机号
							TYPE = "SMS"; 
							new RunInterfaceTemplate().sendJbpmMessage("task节点", map, TYPE);
						}
					}
				} catch (Exception e) {
					logger.error(e, e);
				}
			}
			System.out.println("-----------------发邮件完成---------");
			String assignee = service.getAssigneeJobName(jbpmId);
			
			/**有条件通过，加HAVE_COND_PASS字段 begin**/
			if(tranName_.indexOf("有条件通过")>-1){
				Dao.update("bpm.memo.updatejbpm4HistProcinst",jbpmId);
			}
			/**有条件通过，加HAVE_COND_PASS字段 end**/
			if (assignee == null) return new ReplyAjax(flag, "提交成功！该任务已进入下一节点："+taskName+"");
			String Reject=service.getIsReject(jbpmId);
			if(Reject != null && Reject.contains("驳回"))
			{
				return new ReplyAjax(flag, "该任务已驳回金融专员："+ assignee+"");
			}else if(Reject != null && Reject.contains("家访任务分配"))
			{
				return new ReplyAjax(flag, "发起家访成功！该任务已进入下一节点："+ assignee+"");
			}
			return new ReplyAjax(flag, "提交成功！该任务已进入下一节点：" + assignee+"");
		} catch (ActionException e) {
			throw e;
		} catch (Exception e) {
			if (e.getCause() != null) { throw new ActionException("任务处理失败：" + e.getCause().getMessage(), e); }
			throw new ActionException("任务处理失败：" + e.getMessage(), e);
		}
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	@aPermission(name = { "待办事宜", "待办事宜", "委托操作人" })
	public Reply doAddEntrust() {
		Map<String, Object> param = _getParameters();
		param.put("SRC_CODE", Security.getUser().getCode());
		TaskService service = new TaskService();
		service.addEntrust(param);
		return new ReplyAjax().addOp(new OpLog("待办事宜", "委托", "将个人任务委托给" + param.get("NEW_CODE")));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	@aPermission(name = { "待办事宜", "待办事宜", "撤销委托操作人" })
	public Reply doCancelEntrust() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("SRC_CODE", Security.getUser().getCode());
		TaskService service = new TaskService();
		service.cancelEntrust(param);
		return new ReplyAjax().addOp(new OpLog("待办事宜", "委托", "撤销个人所有任务委托"));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply getUser() {
		TaskService service = new TaskService();
		return new ReplyJson(JSONArray.fromObject(service.getUser()));
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply expMyTaskExcel() {
		List<String> list = new ArrayList<String>();
		Map<String, Object> param = _getParameters();
		param.put("USER_ID", Security.getUser().getId());
		param.put("ORG_ID", Security.getUser().getOrg().getId());
		param.put("USERCODE", Security.getUser().getCode());
		if("1".equals(param.get("type").toString())){
			param.put("UNDERLING", "no");
			list.add("待办事宜");
		}else if("2".equals(param.get("type").toString())){
			param.put("UNDERLING", "yes");
			list.add("下属任务");
		}
		List<LinkedHashMap<String, String>> titles = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
		title.put("TASKNAME", "任务人");
		title.put("ACTIVITYNAME", "当前任务");
		title.put("LAST_NODE", "上一任务");
		title.put("STATUS_NEW", "状态");
		title.put("CUST_NAME", "客户姓名");
		title.put("CUST_ID_INFO", "客户身份证号");
		title.put("SCHEME_NAME", "产品方案");
		title.put("LEASE_TERM", "期限");
		title.put("FINANCE_TOPRIC", "融资金额");
		title.put("CREATETIME", "任务到达时间");
		title.put("START_TIME", "进件时间");
		title.put("FGS", "分公司");
		title.put("SHOP_NAME", "门店");
		title.put("CLERK_NAME", "提件人");
		title.put("SALE_NAME", "销售人员");
		titles.add(title);
		
		List<List<Map<String, Object>>> data = new ArrayList<List<Map<String, Object>>>();
		
		List<Map<String, Object>> l = Dao.selectList("bpm.task.expList", param);
		for(int i=0;i<l.size();i++){
			Map<String, Object> param1 = new HashMap<String, Object>();
			if(l.get(i).get("SHOP_NAME") != null && !"".equals(l.get(i).get("SHOP_NAME").toString())){
				param1.put("SHOPNAME", l.get(i).get("SHOP_NAME"));
				l.get(i).put("FGS", ((Map<String, Object>)Dao.selectOne("bpm.task.getFGS", param1)).get("NAME"));
			}
		}
		data.add(l);
		if("1".equals(param.get("type").toString())){
			Excel excel = new Excel("待办事宜", list, titles, data);
			return new ReplyExcel(excel, "任务.xls");
		}else if("2".equals(param.get("type").toString())){
			Excel excel = new Excel("下属任务", list, titles, data);
			return new ReplyExcel(excel, "任务.xls");
		}
		return new ReplyExcel(null, "");
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply doUploadFile() {
		boolean flag = false;
	   //更新该操作人的操作记录中的上传附件信息，首先根据taskid查询有没有该操作记录
		File file = _getFileOneInPackage("jbpmMemo");
		Map<String, Object> param = _getParameters();
		param.put("FILE_PATH", file.getPath());
		String taskId = (String) param.get("taskId");
		if (taskId == null) { return new ReplyAjax(false, "无任务ID"); }
		// 记录备注
		MemoService service = new MemoService();
		User user = Security.getUser();
		Date serverDate=new Date();
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String serverTime= time.format(serverDate);
		//DateSiteUtil.getDate("");
		try {
			//查询是否有该任务，
			Map<String,Object> taskmap=new HashMap<String, Object>();
			taskmap=service.getMemosByTaskId(taskId);
			String filepath=null;
			//如果有该任务，更新上传文件;如果没有添加新任务
			if(taskmap!=null && taskmap.size()>0){
				if(taskmap.get("FILE_PATH")!=null){
					filepath=taskmap.get("FILE_PATH").toString();
					filepath=filepath.concat(",").concat("上传时间：").concat(serverTime).concat("    附件地址:").concat(file.getPath());
					System.out.println("filepath......"+filepath);
				}
				flag=service.updateMemo(taskId,filepath);
				
			}else{
				// 判断备注是否存在
				TaskService taskService = new TaskService();
				Map<String, Object> task = taskService.getTask(taskId);
				String jbpmId = (String) task.get("EXECUTION_ID_");
				String taskName = (String) task.get("NAME_");
				String fpath="上传时间：".concat(serverTime).concat("    附件地址:").concat(file.getPath());
				flag = service.addMemo(taskId, jbpmId, user.getName(),user.getCode(),  taskName, fpath, "BROWSER");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return new ReplyAjax(flag, "");
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply doDownFile() {
		String filepath = (String) _getParameters().get("filepath");	
		int index=filepath.indexOf("附件地址:");
		filepath=filepath.substring(index+5);
		File file = new File(filepath);
		return new ReplyFile(file, file.getName());
	}
	
	/**
	 * 进入下属任务
	 * @return
	 */
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "待办事宜", "下属任务", "进入下属任务" })
	@aDev(code = "18003", email = "zhengsc@pqsoft.cn", name = "郑商城")
	public Reply toUnderlingTask(){
		VelocityContext context = new VelocityContext();
		context.put("colorJson", JSONArray.fromObject(new DataDictionaryMemcached().get("颜色编码库")));
		//客户来源
		List<Object> CUST_SOURCE = (List)new DataDictionaryMemcached().get("客户来源");
		context.put("scaleSourceTypes", CUST_SOURCE);
		return new ReplyHtml(VM.html("bpm/underlingTask.vm", context));
	}
	
	/**
	 * 下属任务
	 * @return
	 */
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "待办事宜", "下属任务", "下属任务" })
	@aDev(code = "18003", email = "zhengsc@pqsoft.cn", name = "郑商城")
	public Reply underlingTaskPage(){
		Map<String, Object> param = _getParameters();
		param.put("USERCODE", Security.getUser().getCode());
		param.put("UNDERLING", "yes");
		return new ReplyAjaxPage(new TaskService().getUnderlingTaskPage(param));
	}
	
	/**
	 * 进入我的任务-团队任务
	 * @return
	 */
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "待办事宜", "团队任务", "进入团队任务" })
	@aDev(code = "10000", email = "panyi@jiezhongchina.com", name = "弋攀")
	public Reply toTeamTask(){
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("bpm/allTeamTask.vm", context));
	}
	
	/**
	 * 查看所有备注信息
	 * 
	 * @Function: com.pqsoft.bpm.action.TaskAction.toShowTaskRemarks
	 * @return
	 * @author: 邢素敏
	 * @date: 2015年12月11日12:02:14
	 */
	@aPermission(name = { "待办事宜", "待办事宜", "查询" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170062", email = "suminxing@jiezhongchina.com", name = "邢素敏")
	
	public Reply toShowTaskRemarks() {
			TaskService service = new TaskService();
			VelocityContext context = new VelocityContext();
			String taskId = SkyEye.getRequest().getParameter("taskId");
			String jbpmId ="";
			if(null==taskId){
				jbpmId=SkyEye.getRequest().getParameter("jbpmId");
			}else{
				 jbpmId = service.getJbpmIdBytaskId(taskId);
			}
			
			List<Map<String, Object>> remarks = new RemarksService().getRemarks(service.getShortName(jbpmId));
			context.put("jbpm", service.getJbpm(service.getShortName(jbpmId)));
			//context.put("progress", memos.subList(memos.size() > 3 ? memos.size() - 3 : 0, memos.size()));
			context.put("remarks", service.reverse(remarks));
			context.put("user", Security.getUser());
			context.put("taskId", taskId);
			context.put("jbpmId", jbpmId);
			context.put("actUsers", service.getTaskByJbpmId(service.getShortName(jbpmId)));
			return new ReplyHtml(VM.html("bpm/taskRemarkShow.vm", context));
	}
		/**
		 * 添加备注信息
		 * 
		 * @Function: com.pqsoft.bpm.action.TaskAction.addRemarks
		 * @return
		 * @author: 邢素敏
		 * @date: 2015年12月11日12:02:14
		 */
		@aAuth(type = aAuthType.LOGIN)
		@aPermission(name = { "待办事宜", "待办事宜", "处理" })
		@aDev(code = "170062", email = "suminxing@jiezhongchina.com", name = "邢素敏")
		public Reply addRemarks() {
			boolean flag = false;
			try {
				Map<String, Object> param = _getParameters();
				TaskService service = new TaskService();
				String jbpmId = (String) param.get("jbpmId");
				String remarks = (String) param.get("remarks");
				// 记录备注
				RemarksService reService = new RemarksService();
				User user = Security.getUser();
				flag=reService.addRemarks(jbpmId, user.getName(), user.getCode(), remarks);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ReplyAjax(flag);
		}
}
