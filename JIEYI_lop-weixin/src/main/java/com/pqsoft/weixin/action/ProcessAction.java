package com.pqsoft.weixin.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import com.pqsoft.bpm.service.MemoService;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.PageUtil;

/**
 * 流程审批类
 * @author LUYANFENG @ 2015年6月30日
 */
public class ProcessAction extends AbstractWeiXinResponseAction{
	
	private TaskService taskServ = new TaskService();

	
	@aDev(code = "170025", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply mytask(){
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("weixin/process/mytask.vm", context));
	}
	
	@aDev(code = "170025", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply mytaskHTML(){
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("weixin/process/mytaskHTML.ejs.vm", context));
	}
	
	/**
	 * 下一页 json数据
	 * page
	 * rows
	 * 
	 * @return
	 */
	@aDev(code = "170025", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply nextPageJson() {
		//TODO 
		Map<String, Object> param = _getParameters();
		param.put("USERCODE", Security.getUser().getCode());
		Page page = this.taskServ.getPage(param);
		JSONObject json = new JSONObject();
		json.put("page", page);
		return new ReplyJson(json);
	}
	
	/**
	 * 查看所有任务处理意见
	 * @return
	 * @author : LUYANFENG @ 2015年7月6日
	 * @from com.pqsoft.bpm.action.TaskAction.toShowTaskMemo()
	 */
	@aPermission(name = { "待办事宜", "待办事宜", "查询" })
	@aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply toShowTaskMemo() {
		try {
			VelocityContext context = new VelocityContext();
			Map<String, Object> parameters = _getParameters();
			context.put("param", parameters);
			// 流程ID
			String taskId = SkyEye.getRequest().getParameter("taskId");
			String jbpmId = taskServ.getJbpmIdBytaskId(taskId);
			// 查询当前任务信息
			List<Map<String, Object>> memos = new MemoService().getMemos(taskServ.getShortName(jbpmId));
//			context.put("jbpm", taskServ.getJbpm(taskServ.getShortName(jbpmId)));
//			context.put("memoDemos", DataDictionaryMemcached.getList("流程审批意见"));
//			context.put("progress", taskServ.progress(memos));
			// 查询意见
//			context.put("memos", taskServ.reverse(memos));
			context.put("memos", memos);
			context.put("flag", BaseUtil.isClerk());
			context.put("taskId", taskId);
			// 流程ID
			// 获取流程配置
			Map<String, Object> config = taskServ.getConfig(taskId);
			List list = new ArrayList();
			if (config == null || config.size() == 0) {
				throw new ActionException("任务已处理,请刷新任务列表页面", null);
			} else {
				// 获取所有可提交下一节点
				list = taskServ.getNextTransition((String) config.get("PROCDEFID_"), (String) config.get("ACTIVITYNAME_"), jbpmId);
			}
			// ------ 查询当前任务信息

			// 流程表单url及参数
			context.put("url", taskServ.getContentUrl(jbpmId, config.get("ACTIVITYNAME_") + "", (String) config.get("FORM")));
			// 所有可提交下一节点
			context.put("flowNodes", list);
			context.put("actUsers", taskServ.getTaskByJbpmId(taskServ.getShortName(jbpmId)));
			context.put("currentUser", Security.getUser());
			return new ReplyHtml(VM.html("weixin/process/toShowTaskMemo.vm", context));
		} catch (ActionException e) {
			throw e;
		} catch (Exception e) {
			throw new ActionException("解析下一步操作节点失败", e);
		}
	}
}
