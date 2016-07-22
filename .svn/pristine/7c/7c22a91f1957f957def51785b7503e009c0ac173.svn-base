package com.pqsoft.wx.bs.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.velocity.VelocityContext;
import com.pqsoft.base.task.service.TaskClaimService;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.crm.service.CustomerService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.leeds.cust_info_input.service.CustInfoInputService;
import com.pqsoft.leeds.utils.StringUtil;
import com.pqsoft.returnVisit.service.ReturnVisitService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.wx.base.WeixinBaseAction;
import com.pqsoft.wx.base.tools.WeixinTools;

import net.sf.json.JSONObject;
/**
 * 捷众家访
 * @author liangds
 *
 */
public class HomeVisitAction extends WeixinBaseAction{
	private TaskClaimService service= new TaskClaimService();
	private ReturnVisitService rservice = new ReturnVisitService();
	private CustomerService cservice = new CustomerService();
	
	@Override
	@aDev(code = "62", email = "liangds@163.com", name = "liangds")
	@aAuth(type = aAuthType.ALL)
	public Reply execute() {
		return null;
	}
	/**
	 * 家访任务列表 待办
	 * @return
	 */
	@aDev(code = "62", email = "liangds@163.com", name = "liangds")
	@aAuth(type = aAuthType.ALL)
	public Reply visitList() {
		VelocityContext context = new VelocityContext();
		if(getWeixinLoginInfo().length()>0){
			return errorMsg(getWeixinLoginInfo(),context);
		}
		if(WeixinTools.checkRole("城市副理")){
			Map<String, Object> param =new HashMap<String, Object>();
			param.put("USERCODE", Security.getUser().getCode());
			param.put("UNDERLING", "no");
			param.put("page", "1");
			param.put("rows", "10");
			context.put("page", new TaskService().getPage(param));
			context.put("state", getAgentId());
		}
		return new ReplyHtml(VM.html("wx/visit/processList.vm",context));
	}

	/**
	 * 家访任务列表 已办
	 * @return
	 */
	@aDev(code = "62", email = "liangds@163.com", name = "liangds")
	@aAuth(type = aAuthType.ALL)
	public Reply closeList() {
		VelocityContext context = new VelocityContext();
		if(getWeixinLoginInfo().length()>0){
			return errorMsg(getWeixinLoginInfo(),context);
		}
		if(WeixinTools.checkRole("城市副理")){
			Map<String, Object> param =new HashMap<String, Object>();
			param.put("page", "1");
			param.put("rows", "10");
			Map<String, Object> m=service.getTaskOther(Security.getUser().getId(),Security.getUser().getOrg().getId());
			param.putAll(m);
			context.put("page", service.getPageData1(param));
			context.put("state", getAgentId());
		}
		return new ReplyHtml(VM.html("wx/visit/closeList.vm",context));
	}
	
	/**
	 * 家访任务详细页面
	 * @return
	 */
	@aDev(code = "62", email = "wp@163.com", name = "wp")
	@aAuth(type = aAuthType.ALL)
	public Reply visitDetail() {
		VelocityContext context = new VelocityContext();
		if(getWeixinLoginInfo().length()>0){
			return errorMsg(getWeixinLoginInfo(),context);
		}
		Map<String, Object> param = _getParameters();
		System.out.println(param);
		
		TaskService service = new TaskService();
		//流程状态
		String statusFlag = SkyEye.getRequest().getParameter("status");
		//将statusFlag放到session里面
		SkyEye.getRequest().getSession().setAttribute("statusFlag", statusFlag);
		// 任务ID
		String taskId = SkyEye.getRequest().getParameter("taskId");
		// 流程ID
		String jbpmId = service.getJbpmIdBytaskId(taskId);
		
		String  thisUrl = "";
		try {
			// 获取流程配置
			Map<String, Object> config = service.getConfig(taskId);
			if (config == null) throw new ActionException("该节点未做任何配置，请联系管理员");
			thisUrl = service.getContentUrlNotEncode(jbpmId, config.get("ACTIVITYNAME_") + "", (String) config.get("FORM"));
			
			service.setRead(jbpmId);// 设置已读
			context.put("taskId", taskId);
			//审核页面用到
			@SuppressWarnings("rawtypes")
			List list = new ArrayList();
			if (config == null || config.size() == 0) {
				throw new ActionException("任务已处理,请刷新任务列表页面", null);
			} else {
				// 获取所有可提交下一节点
				list = service.getNextTransition((String) config.get("PROCDEFID_"), (String) config.get("ACTIVITYNAME_"), jbpmId);
			}
			context.put("flowNodes", list);
			//审核页面用到
		} catch (ActionException e) {
			throw e;
		} catch (Exception e) {
			throw new ActionException("解析下一步操作节点失败", e);
		}
		//家访用到的参数。
		String proId = "";
		String task_name ="";
		
		String [] urls = thisUrl.split("&");
		for (String para :urls){
			if (para.indexOf("PROJECT_ID") != -1)
				proId = para.substring(para.indexOf("=") + 1,para.length());
			if (para.indexOf("TASK_NAME") != -1)
				task_name = para.substring(para.indexOf("=") + 1,para.length());
		}


		//承租人
		List<Map<String, Object>> recs = Dao.execSelectSql(""
				+ "SELECT A.CLIENT_ID, A.GUARANTEE, A.JOINT_TENANT, "
				+ "A.JOINT_TENANT_ID,case when A.JOINT_TENANT=2 then 'NP' when A.JOINT_TENANT=3 then 'LP' end JOINT_TENANT_TYPE, "
				+ " A.PLATFORM_TYPE, B.NAME, B.TYPE, "
				+ " C.SCHEME_ROW_NUM, C.SCHEME_CODE SCHEME_ID "
				+ " FROM FIL_PROJECT_HEAD A, FIL_CUST_CLIENT B, FIL_PROJECT_SCHEME C  "
				+ " WHERE A.ID='"+proId+"' AND A.CLIENT_ID=B.ID"
						+ " AND A.ID=C.PROJECT_ID(+)");
		Map<String, Object> rec = recs.get(0);
		String CLIENT_ID = rec.get("CLIENT_ID").toString();
//				String TYPE = rec.get("TYPE").toString();
		String proCode = new CustInfoInputService().getProCode(proId);
		String hasGuarantee = rec.get("GUARANTEE") == null ? "1" : rec.get("GUARANTEE").toString();
		String hasJt = rec.get("JOINT_TENANT") == null ? "1" : rec.get("JOINT_TENANT").toString();
		
//		"PROJECT_ID="+proId+"&task_name="+params.get("TASK_NAME").toString()+"&task=jf&CLIENT_ID="+CLIENT_ID);
		//查询尽职调查
		Map<String, Object> param1 = new HashMap<String, Object>();
		param1.put("PROJECT_ID", proId);
		param1.put("task_name", task_name);
		param1.put("task", "jf");
		param1.put("CLIENT_ID", CLIENT_ID);
		
		System.out.println("--jiafang weixin--param:"+param1);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

		param1.put("ZX_SURVEY", Security.getUser().getName());// 调查人员
		param1.put("ZX_SURVEY_TIME", df.format(new Date()));///调查日期
		param1.put("P_TIME", df.format(new Date()));//调查时间
		
		List<Map<String, Object>> visits = rservice.toViewVisit1Ask(param1);//查看家访信息,多次家访记录
		List<Map<String, Object>> pros = rservice.toGetProjectInfoAsk(param1);//查看项目信息	
		
		context.put("title1", "尽职调查");
		context.put("param1", param1);
		context.put("visits", visits);
		context.put("pros", pros);
		context.put("addr", new DataDictionaryMemcached().get("资信访问地点"));
		//查询尽职调查
		
		//承租人尽职调查报告
		//+ "PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=承租人家访&task=jf&taskStatue=1&JFBG=JFBG");
		Map<String, Object> param2 = new HashMap<String, Object>();
		param2.put("PRO_CODE", proCode);
		param2.put("PROJECT_ID", proId);
		param2.put("PHASE", "承租人家访");
		param2.put("task", "jf");
		param2.put("taskStatue", "1");
		param2.put("JFBG", "JFBG");
		
		System.out.println("================yxweixin=========="+param2);
		context.put("EARNINGS", Security.hasPermission(new String[]{"业务管理", "客户管理", "收益分析"}));
		String phase = StringUtil.isBlank("承租人家访")?"立项": "承租人家访";
		param2.put("PHASE", phase);
		param2.put("parentId", WeixinTools.getFileParentId(proId, phase));
		
		List<Map<String, Object>> mts = cservice.getDocAndFiles(proId, phase);
		context.put("mts", mts);
		context.put("param2", param2);
		context.put("title2", "承租人尽职调查报告");
		//承租人尽职调查报告
		
		//共同承租人尽职调查报告
		if (!hasJt.trim().equals("1")) {
			//+ "PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=共同承租人家访&task=jf&taskStatue=1&JFBG=JFBG");
			Map<String, Object> param3 = new HashMap<String, Object>();
			param3.put("PRO_CODE", proCode);
			param3.put("PROJECT_ID", proId);
			param3.put("PHASE", "共同承租人家访");
			param3.put("task", "jf");
			param3.put("taskStatue", "1");
			param3.put("JFBG", "JFBG");
			
			System.out.println("================yxweixin=========="+param3);
//			context.put("EARNINGS", Security.hasPermission(new String[]{"业务管理", "客户管理", "收益分析"}));
			String phase3 = StringUtil.isBlank("共同承租人家访")?"立项": "共同承租人家访";
			param3.put("PHASE", phase3);
			param3.put("parentId", WeixinTools.getFileParentId(proId, phase3));
			
			List<Map<String, Object>> mts3 = cservice.getDocAndFiles(proId, phase3);
			context.put("mts3", mts3);
			context.put("param3", param3);
			context.put("title3", "共同承租人尽职调查报告");
		}
		//共同承租人尽职调查报告
		
		// 担保人
		//担保人尽职调查报告
		if (!hasGuarantee.trim().equals("1")) {
			//+ "PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=担保人家访&task=jf&taskStatue=1&JFBG=JFBG");
			Map<String, Object> param4 = new HashMap<String, Object>();
			param4.put("PRO_CODE", proCode);
			param4.put("PROJECT_ID", proId);
			param4.put("PHASE", "担保人家访");
			param4.put("task", "jf");
			param4.put("taskStatue", "1");
			param4.put("JFBG", "JFBG");
			
			System.out.println("================yxweixin=========="+param4);
//			context.put("EARNINGS", Security.hasPermission(new String[]{"业务管理", "客户管理", "收益分析"}));
			String phase4 = StringUtil.isBlank("担保人家访")?"立项": "担保人家访";
			param4.put("PHASE", phase4);
			param4.put("parentId", WeixinTools.getFileParentId(proId, phase4));
			
			List<Map<String, Object>> mts4 = cservice.getDocAndFiles(proId, phase4);
			context.put("mts4", mts4);
			context.put("param4", param4);
			context.put("title4", "担保人尽职调查报告");
		}
		//担保人尽职调查报告
		
		//weixin签名
		WeixinTools.getSign(WeixinTools.getUrl(SkyEye.getRequest()),getAgentId(),context);
		context.put("state", getAgentId());
		return new ReplyHtml(VM.html("wx/visit/info.vm",context));
	}
	
	/**
	 * 家访任务详细页面
	 * @return
	 */
	@aDev(code = "62", email = "liangds@163.com", name = "liangds")
	@aAuth(type = aAuthType.ALL)
	public Reply visitView() {
		VelocityContext context = new VelocityContext();
		if(getWeixinLoginInfo().length()>0){
			return errorMsg(getWeixinLoginInfo(),context);
		}
		
		Map<String, Object> param = _getParameters();
		System.out.println(param);
		context.put("state", getAgentId());
		return new ReplyHtml(VM.html("wx/visit/info.vm",context));
	}
	
	/**
	 * 上传附件
	 * @return
	 */
	@aDev(code = "62", email = "liangds@163.com", name = "liangds")
	@aAuth(type = aAuthType.ALL)
	public Reply uploadAttach() {
		VelocityContext context = new VelocityContext();
		if(getWeixinLoginInfo().length()>0){
			return errorMsg(getWeixinLoginInfo(),context);
		}
		JSONObject json = new JSONObject();
		Map<String,Object> result=WeixinTools.downImage(_getParameters(),getAgentId());
		
		json.put("ok", result.get("flag"));
		json.put("error", result.get("error"));
		json.put("wxImgId", result.get("wxImgId"));
		
		return new ReplyJson(json);
	}
	
}
