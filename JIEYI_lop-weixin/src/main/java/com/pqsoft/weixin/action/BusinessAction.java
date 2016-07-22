package com.pqsoft.weixin.action;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jsp.weixin.ParamesAPI.util.WeixinJSUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import com.pqsoft.Code.service.CodeService;
import com.pqsoft.bpm.service.MemoService;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.customers.service.CustomersService;
import com.pqsoft.leeds.utils.StringUtil;
import com.pqsoft.project.service.projectService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.MyNumberTool;
import com.pqsoft.util.PageUtil;
import com.pqsoft.weixin.service.BzManageService;
import com.pqsoft.weixin.utils.AppUtils.Phase;

/**
 * 业务申请crud
 * 
 * @author LUYANFENG @ 2015年2月28日
 */
public class BusinessAction extends AbstractWeiXinResponseAction {

	private BzManageService bzService = new BzManageService();

	
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "lichao")
	@aAuth(type=aAuthType.ALL)
	public Reply listHTML(){
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("weixin/business/listHTML.ejs.vm", context ));
	}
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "lichao")
	@aAuth(type=aAuthType.ALL)
	public Reply sqListHTML(){
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("weixin/business/sqListHTML.ejs.vm", context ));
	}
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "lichao")
	@aAuth(type=aAuthType.ALL)
	public Reply bcListHTML(){
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("weixin/business/bcListHTML.ejs.vm", context ));
	}
	
	/**
	 * 下一页 json数据
	 * page
	 * rows
	 * 
	 * @return
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "lichao")
	@aAuth(type = aAuthType.LOGIN)
	public Reply nextPageJson() {
		Map<String, Object> m = _getParameters();
		Map<String, Object> param = new HashMap<String, Object>();
		param.putAll(m);
		if (StringUtils.isNotBlank((String) m.get("page"))) {
			param.put("page", m.get("page"));
		}
		if (StringUtils.isNotBlank((String) m.get("rows"))) {
			param.put("rows", m.get("rows"));
		}
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			param.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}

		Map COM_MAP = BaseUtil.getCom();
		if (COM_MAP != null) {
			param.put("COMPANY_ID", COM_MAP.get("COMPANY_ID"));
		}
		
		//  fil_project_head status = 27 是不通过
		if (StringUtils.isBlank((String) m.get("t")) || m.get("t").equals("sq")) { // 申请
			param.put("STATUS", "0");
		} else if (m.get("t").equals("cx")) { // 查询
			param.put("STATUS2", "case2");
		} else if (m.get("t").equals("bc")) { // 补充
			param.put("DICT_TYPE", "微信补充资料");
		}
		param.put("CLERK_ID", Security.getUser().getId());
		param.put("dict_type", "微信补充资料");
		param.put("tsdd_type", "业务类型");
		param.put("tsdd1_type", "项目状态位");
		param.put("tsdd2_type", "客户类型");
		Page page = PageUtil.getPage(param, "weixin_view.select_business_list", "weixin_view.getAllProject_count");

		JSONObject json = new JSONObject();
		json.put("page", page);
		return new ReplyJson(json);
	}

	/**
	 * 查询我的业务列表
	 * 
	 * @return
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "lichao")
	@aAuth(type = aAuthType.LOGIN)
	public Reply list() {
		VelocityContext vm = new VelocityContext();
		vm.put("t", "cx");
		vm.put("wxcfg", WeixinJSUtil.sign(this.WX_check_URI));
		return new ReplyHtml(VM.html("weixin/business/list.vm", vm));
	}
	/**
	 * 用户及租赁物信息
	 * way 访问类型
	 * @deprecated
	 * @see detail_tab()
	 */
	/*@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "lichao")
	@aAuth(type = aAuthType.LOGIN)
	public Reply detail() {
		VelocityContext vc = new VelocityContext();
		Map<String, Object> param = _getParameters();	// ID
		Object TASK_ID = param.get("TASK_ID");
		vc.put("TASK_ID", TASK_ID);
		String way = (String) param.get("way");
		vc.put("way", way);
		param.remove("way");
		Map<String, Object> selectOne = Dao.selectOne("weixin_view.select_business_detail1", param);
		if (StringUtils.isBlank((String) param.get("ID"))) { throw new ActionException("无法访问"); }
		vc.put("is_owner", Security.getUser().getId().equals(selectOne.get("CREATE_ID")));
//		vc.put("pro_status", selectOne.get("STATUS"));
		vc.put("ID", param.get("ID"));

		vc.put("result", selectOne);
		vc.put("MyNumberTool", new MyNumberTool());
		
		vc.put("wxcfg", WeixinJSUtil.sign(this.WX_check_URI));
		return new ReplyHtml(VM.html("weixin/business/detail1.vm", vc));
	}*/

	/**
	 * 用户上传的相片列表
	 * @deprecated
	 * @see detail_tab()
	 *//*
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "lichao")
	@aAuth(type = aAuthType.LOGIN)
	public Reply detail2() {

		VelocityContext vc = new VelocityContext();
		Map<String, Object> param = _getParameters();
		Object TASK_ID = param.get("TASK_ID");
		vc.put("TASK_ID", TASK_ID);

		Phase PHASE = StringUtil.isBlank((String) param.get("PHASE") ) ? Phase.立项 : Phase.valueOf(param.get("PHASE").toString());
		String proId = (String) param.get("ID"); // PROJECT_ID
		if (StringUtils.isBlank(proId)) { throw new ActionException("无法访问"); }
		
		Map<String, Object> selectOne = Dao.selectOne("weixin_view.select_business_detail1", param);
		vc.put("is_owner", Security.getUser().getId().equals(selectOne.get("CREATE_ID")));
		if (selectOne != null) vc.put("is_canWrite", (selectOne.get("LNAME").toString().contains("补充资料") || "0".equals(selectOne.get("STATUS").toString())));

		String way = (String) param.get("way");
		param.remove("way");
		vc.put("way", way);

		List<Map<String, Object>> mts = bzService.getFile(vc,PHASE , proId, "");
		vc.put("mts", mts);
		JSONArray fromObject = JSONArray.fromObject(mts);
		vc.put("mtsJson", fromObject);

		vc.put("wxcfg", WeixinJSUtil.sign(this.WX_check_URI));
		return new ReplyHtml(VM.html("weixin/business/detail2.vm", vc));
	}
	*/
	
	/**
	 * 
	 * <pre>
	 * TODO
	 * <pre>
	 * @return
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type = aAuthType.LOGIN)
	public Reply detail_tab() {
		TaskService taskServ = new TaskService();
		VelocityContext vc = new VelocityContext();
		
		Map<String, Object> param = _getParameters();	
		vc.put("param", param);
		
		String way = (String) param.get("way");
		String taskId = (String) param.get("TASK_ID");
		String PROJECT_ID = (String) param.get("ID");
		
		vc.put("way", way);
		if (StringUtils.isBlank(PROJECT_ID)) { throw new ActionException("无法访问"); }
		
		
		Map<String,Object> qrymap = new HashMap<String,Object>();
		qrymap.put("ID", param.get("ID"));
		Map<String, Object> selectOne = Dao.selectOne("weixin_view.select_business_detail1", qrymap);
		if (StringUtils.isBlank((String) param.get("ID"))) { throw new ActionException("无法访问"); }
		
		String FK_ID = (String) param.get("DETAIL_ID");
		Phase PHASE = Phase.立项 ;
		if("fk_sq".equals(way) || "fk_bc".equals(way)){
			PHASE = Phase.放款前;
		}else if("fk_hdbc".equals(way)){
			PHASE = Phase.放款后;
		}else if("fk_cx".equals(way)){
			Object FFPH_STATUS = param.get("FFPH_STATUS");
			if("45".contains(FFPH_STATUS.toString())){
				PHASE = Phase.放款后;
			}else{
				PHASE = Phase.放款前;
			}
		}
		
		
		if(PHASE == Phase.放款前 || PHASE == Phase.放款后 ){
			Map<String,Object> qryMap = new HashMap<String,Object>();
			qryMap.put("PROJECT_ID", PROJECT_ID);
			List<Map<String, Object>> equipInfos = Dao.selectList("weixin_view.selectEquipInfo", qryMap);
			vc.put("eqiupInfo", equipInfos != null  && !equipInfos.isEmpty()? equipInfos.get(0) : null);
		}
		
		
		vc.put("is_owner", Security.getUser().getId().equals(selectOne.get("CREATE_ID")));
		if (selectOne != null) vc.put("is_canWrite", (selectOne.get("LNAME").toString().contains("补充资料") || "0".equals(selectOne.get("STATUS").toString())) || "20".equals(selectOne.get("STATUS").toString()) );
		vc.put("ID", param.get("ID"));
		vc.put("result", selectOne);
		vc.put("MyNumberTool", new MyNumberTool());

		List<Map<String, Object>> mts = bzService.getFile(vc,PHASE , PROJECT_ID, FK_ID);
		JSONArray fromObject = JSONArray.fromObject(mts);
		vc.put("mtsJson", fromObject);
		
		if(StringUtils.isBlank(taskId)){
			Map<String,Object> qryMap = new HashMap<String,Object>();
			qryMap.put("PROJECT_ID",PROJECT_ID);
			Map<String,Object> jbpmMap = Dao.selectOne("weixin_view.getJBPMByProjectID", qryMap );
			if(jbpmMap != null && !jbpmMap.isEmpty()){
				taskId = ""+jbpmMap.get("HTASK_");
			}
		}
		
		if(StringUtils.isNotBlank(taskId)){
			// 获取流程配置
			String jbpmId = taskServ.getJbpmIdBytaskId(taskId);
			Map<String, Object> config = taskServ.getConfig(taskId);
			List list = new ArrayList();
			if (config == null || config.size() == 0) {
			} else {
				try {
					// 获取所有可提交下一节点
					list = taskServ.getNextTransition((String) config.get("PROCDEFID_"), (String) config.get("ACTIVITYNAME_"), jbpmId);
					// 流程表单url及参数
					vc.put("url", taskServ.getContentUrl(jbpmId, config.get("ACTIVITYNAME_") + "", (String) config.get("FORM")));
					vc.put("flowNodes", list);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		param.put("TASK_ID", taskId);
		vc.put("TASK_ID", taskId);
		vc.put("wxcfg", WeixinJSUtil.sign(this.WX_check_URI));
		return new ReplyHtml(VM.html("weixin/business/detail_tab.vm", vc));
	}
	
	/**
	 * 追加担保人个人、企业
	 * GUARANTEE : 1 无
	 * 
	 * @return
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "lichao")
	@aAuth(type = aAuthType.LOGIN)
	public Reply appendDBRData() {
		JSONObject json = new JSONObject();
		json.put("ok", false);
		Map<String, Object> _getParameters = _getParameters();
		String GUARANTEE = (String) _getParameters.get("GUARANTEE");
		String PROJECT_ID = (String) _getParameters.get("PROJECT_ID");
		String FLAG = (String) _getParameters.get("FLAG");
		
		
		this.bzService.checkProjectMan(PROJECT_ID, 0,20);
		
		
		if (StringUtils.isBlank(GUARANTEE) || ("1".equals(GUARANTEE ) && StringUtils.isBlank(FLAG)) ) {
			json.put("ok", false);
			return new ReplyJson(json);
		}
		if (StringUtils.isBlank(PROJECT_ID)) {
			json.put("ok", false);
			return new ReplyJson(json);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("GUARANTEE", GUARANTEE);
		map.put("ID", PROJECT_ID);
		map.put("PROJECT_ID", PROJECT_ID);
		projectService service = new projectService();
		Map<String, Object> projectMap = service.queryProjectById(map);
		
		Object old_guarantee = projectMap.get("GUARANTEE");
		if( old_guarantee != null && GUARANTEE.equals(old_guarantee.toString())){
			json.put("ok", false);
			json.put("info", "请不要重复操作此功能！！！");
			return new ReplyJson(json);
		}
		
		Clob clob = (Clob) projectMap.get("SCHEME_CLOB");
		String str = null;
		try {
			str = clob.getSubString(1, (int) clob.length());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JSONArray SCHEME_CLOB = JSONArray.fromObject(str);
		for (Iterator<Object> iterator = SCHEME_CLOB.iterator(); iterator.hasNext();) {
			Map<String,Object> mClob = (Map<String,Object>) iterator.next();
			if ("有无担保人".equals(mClob.get("KEY_NAME_ZN").toString())) {
				if ("2".equals(GUARANTEE)) {
					mClob.put("VALUE_STR", "2");
				} else if ("3".equals(GUARANTEE)) {
					mClob.put("VALUE_STR", "3");
				} else {
					mClob.put("VALUE_STR", "1");
				}
				break;
			}
		}
		Map<String, Object> scheme = new HashMap<String, Object>();
		scheme.put("SCHEME_CLOB", SCHEME_CLOB.toString());
		scheme.put("PROJECT_ID", PROJECT_ID);
		Dao.update("weixin_view.updScheme", scheme);

		

		int update = Dao.update("weixin_view.appendDBRData", map); // 更新
		if (GUARANTEE.equals("2"))// 担保人为自然人
		{
			CustomersService cService = new CustomersService();
			String ID = Dao.getSequence("SEQ_FIL_CUST_CLIENT");
			Map<String, Object> custMap = new HashMap<String, Object>();
			custMap.put("ID", ID);
			custMap.put("USERID", Security.getUser().getId());
			custMap.put("USERCODE", Security.getUser().getCode());
			custMap.put("USERNAME", Security.getUser().getName());
			String CUST_ID = CodeService.getCode("客户编号", ID, null).toString();
			custMap.put("CUST_ID", CUST_ID);
			custMap.put("CREDIT_ID", map.get("ID"));
			custMap.put("TYPE", "NP");
			// 临时客户状态
			custMap.put("CUST_STATUS", "0");
			cService.doAddCust(custMap);
		} else if (GUARANTEE.equals("3")) {
			CustomersService cService = new CustomersService();
			String ID = Dao.getSequence("SEQ_FIL_CUST_CLIENT");
			Map<String, Object> custMap = new HashMap<String, Object>();
			custMap.put("ID", ID);
			custMap.put("USERID", Security.getUser().getId());
			custMap.put("USERCODE", Security.getUser().getCode());
			custMap.put("USERNAME", Security.getUser().getName());
			String CUST_ID = CodeService.getCode("客户编号", ID, null).toString();
			custMap.put("CUST_ID", CUST_ID);
			custMap.put("CREDIT_ID", map.get("ID"));
			custMap.put("TYPE", "LP");
			// 临时客户状态
			custMap.put("CUST_STATUS", "0");
			cService.doAddCust(custMap);
		} else if (GUARANTEE.equals("1")) {
			Map<String, Object> dbr = Dao.selectOne("weixin_view.getDbr", map);
			Dao.delete("weixin_view.delDbr1", map);
			if (dbr != null && !dbr.isEmpty()) Dao.delete("weixin_view.delClient", dbr);
			
			Map<String,Object> pms = new HashMap<String,Object>();
			pms.put("TPM_CUSTOMER_TYPE", FLAG);
			pms.put("PROJECT_ID", PROJECT_ID);
			Dao.delete("weixin_view.delImages", pms);
		}
		json.put("ok", update == 1);
		return new ReplyJson(json);
	}

	/**
	 * 追加共同承租人个人、企业
	 * 
	 * @return
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "lichao")
	@aAuth(type = aAuthType.LOGIN)
	public Reply appendGTCZRData() {
		JSONObject json = new JSONObject();
		json.put("ok", false);
		
		Map<String, Object> params = _getParameters();
		String JOINT_TENANT = SkyEye.getRequest().getParameter("JOINT_TENANT");
		String PROJECT_ID = SkyEye.getRequest().getParameter("PROJECT_ID");
		String FLAG = (String) params.get("FLAG");
		
		this.bzService.checkProjectMan(PROJECT_ID, 0,20);
		if (StringUtils.isBlank(JOINT_TENANT) || ("1".equals(JOINT_TENANT ) && StringUtils.isBlank(FLAG))) {
			json.put("ok", false);
			return new ReplyJson(json);
		}
		if (StringUtils.isBlank(PROJECT_ID)) {
			json.put("ok", false);
			return new ReplyJson(json);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("JOINT_TENANT", JOINT_TENANT);
		map.put("ID", PROJECT_ID);
		map.put("PROJECT_ID", PROJECT_ID);
		projectService service = new projectService();
		Map<String, Object> projectMap = service.queryProjectById(map);
		
		Object old_joint_tenant = projectMap.get("JOINT_TENANT");
		if( old_joint_tenant != null && JOINT_TENANT.equals(old_joint_tenant+"")){
			json.put("ok", false);
			json.put("info", "请不要重复操作此功能！！！");
			return new ReplyJson(json);
		}
		
		Clob clob = (Clob) projectMap.get("SCHEME_CLOB");
		String str = null;
		try {
			str = clob.getSubString(1, (int) clob.length());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JSONArray SCHEME_CLOB = JSONArray.fromObject(str);
		for (Iterator<Object> iterator = SCHEME_CLOB.iterator(); iterator.hasNext();) {
			Map<String,Object> mClob = (Map<String,Object>) iterator.next();
			if ("JOINT_TENANT".equals(mClob.get("KEY_NAME_EN").toString())) {
				if ("2".equals(JOINT_TENANT)) {
					mClob.put("VALUE_STR", "2");
				} else if ("3".equals(JOINT_TENANT)) {
					mClob.put("VALUE_STR", "3");
				} else {
					mClob.put("VALUE_STR", "1");
				}
				break;
			}
		}
		Map<String, Object> scheme = new HashMap<String, Object>();
		scheme.put("SCHEME_CLOB", SCHEME_CLOB.toString());
		scheme.put("PROJECT_ID", PROJECT_ID);
		Dao.update("weixin_view.updScheme", scheme);
		
		if (JOINT_TENANT.equals("2")) {// 自然人
			CustomersService cService = new CustomersService();
			String ID = Dao.getSequence("SEQ_FIL_CUST_CLIENT");
			Map<String, Object> custMap = new HashMap<String, Object>();
			custMap.put("ID", ID);
			custMap.put("USERID", Security.getUser().getId());
			custMap.put("USERCODE", Security.getUser().getCode());
			custMap.put("USERNAME", Security.getUser().getName());
			String CUST_ID = CodeService.getCode("客户编号", ID, null).toString();
			custMap.put("CUST_ID", CUST_ID);
			custMap.put("TYPE", "NP");
			// 临时客户状态
			custMap.put("CUST_STATUS", "0");
			cService.doAddCust(custMap);
			map.put("JOINT_TENANT_ID", ID);
		} else if (JOINT_TENANT.equals("3")) {						// 法人
			CustomersService cService = new CustomersService();
			String ID = Dao.getSequence("SEQ_FIL_CUST_CLIENT");
			Map<String, Object> custMap = new HashMap<String, Object>();
			custMap.put("ID", ID);
			custMap.put("USERID", Security.getUser().getId());
			custMap.put("USERCODE", Security.getUser().getCode());
			custMap.put("USERNAME", Security.getUser().getName());
			String CUST_ID = CodeService.getCode("客户编号", ID, null).toString();
			custMap.put("CUST_ID", CUST_ID);
			custMap.put("TYPE", "LP");
			// 临时客户状态
			custMap.put("CUST_STATUS", "0");
			cService.doAddCust(custMap);
			map.put("JOINT_TENANT_ID", ID);
		} else if (JOINT_TENANT.equals("1")) {
			Map<String, Object> CoTenant = Dao.selectOne("weixin_view.getCoTenant", map);
			Dao.delete("weixin_view.delClient", CoTenant);
			// 删除上传的资料
			Map<String,Object> pms = new HashMap<String,Object>();
			pms.put("TPM_CUSTOMER_TYPE", FLAG);
			pms.put("PROJECT_ID", PROJECT_ID);
			Dao.delete("weixin_view.delImages", pms);
			
		}
		int update = Dao.update("weixin_view.appendGTCZRData", map);
		json.put("ok", update == 1);
		return new ReplyJson(json);
	}

	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "lichao")
	@aAuth(type = aAuthType.LOGIN)
	public Reply detail3() {
		VelocityContext vc = new VelocityContext();
		Map<String, Object> param = _getParameters();
		vc.put("param", param);
		String project_id = (String) param.get("ID");
		String way = (String) param.get("way");
		vc.put("way", way);
		vc.put("ID",  project_id );

		if (StringUtils.isBlank( project_id)) { throw new ActionException("无法访问"); }
		
//		param.remove("way");

		TaskService service = new TaskService();
		String jbpmId = "";
		
		if(  way != null && way.startsWith("fk_")){
			projectService proSservice = new projectService();
			List<Map<String, Object>> doShowProjectJbpmList = proSservice.doShowProjectJbpmList(project_id);
			if(doShowProjectJbpmList != null && !doShowProjectJbpmList.isEmpty()){
				Map<String, Object> showjbpm = doShowProjectJbpmList.get(doShowProjectJbpmList.size()-1);
				String memoId = showjbpm.get("MEMEID").toString();
				Map<String, Object> memo = new MemoService().getMemo(memoId);
				if (memo == null) throw new ActionException("等待流程审批！");
				jbpmId = (String) memo.get("JBPM_ID");
			}
			
		}else{
			// 流程ID
			Map<String, Object> projectMap = Dao.selectOne("weixin_view.select_business_detail1", param); //ID
			if ( projectMap != null && !projectMap.isEmpty() ) {
				jbpmId = (String) projectMap.get("JBPM_ID");
			}
		}
		
		if(StringUtils.isBlank(jbpmId)){
			throw new ActionException("无法访问：数据异常");
		}
		List<Map<String, Object>> memos = new MemoService().getMemos(service.getShortName(jbpmId));
		Map<String, Object> jbpmMap = (Map<String, Object>) service.getJbpm(service.getShortName(jbpmId));
		if(jbpmMap != null && !jbpmMap.isEmpty()){
			String ended = jbpmMap.get("STATE_").toString();
			if(ended.equalsIgnoreCase("ended")){
				Map<String,Object> qrymap = new HashMap<String,Object>();
				qrymap.put("ID", project_id);
				Map<String,Object> selectOne = Dao.selectOne("weixin_view.select_business_detail1", qrymap);
				if(selectOne == null || selectOne.isEmpty()){
					throw new ActionException("无法访问：数据异常");
				}
				vc.put("PRO_STATUS", selectOne.get("STATUS").toString());
			}
		}
		vc.put("jbpm", jbpmMap);
		
		
		vc.put("progress", memos );
		vc.put("memos", service.reverse(memos));
		vc.put("actUsers", service.getTaskByJbpmId(service.getShortName(jbpmId)));
			
			
		vc.put("wxcfg", WeixinJSUtil.sign(this.WX_check_URI));
		return new ReplyHtml(VM.html("weixin/business/detail3.vm", vc));
	}

	/**
	 * 业务申请
	 * 
	 * @return
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "lichao")
	@aAuth(type = aAuthType.LOGIN)
	public Reply sqList() {
		VelocityContext vm = new VelocityContext();
		vm.put("wxcfg", WeixinJSUtil.sign(this.WX_check_URI));
		return new ReplyHtml(VM.html("weixin/business/sqList.vm", vm));
	}

	/**
	 * 补充业务资料
	 * 
	 * @return
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "lichao")
	@aAuth(type = aAuthType.LOGIN)
	public Reply bcList() {
		VelocityContext vm = new VelocityContext();
		vm.put("wxcfg", WeixinJSUtil.sign(this.WX_check_URI));
		return new ReplyHtml(VM.html("weixin/business/bcList.vm", vm));
	}

	/**
	 * 删除一条方案
	 * 
	 * @return
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "lichao")
	@aAuth(type = aAuthType.LOGIN)
	public Reply delBusiness() {
		JSONObject result = new JSONObject();
		result.put("ok", false);
		try {
			String id = (String) _getParameters().get("ID");

			this.bzService.checkProjectMan(id);

			projectService service = new projectService();
			service.delProject(id);
			result.put("ok", true);
		} catch (Exception e1) {
			Dao.rollback();
			e1.printStackTrace();
			result.put("ok", false);
			result.put("error", "服务错误");
		}
		return new ReplyJson(result);
	}

	/**
	 * 删除上传的不合适相片
	 */
	@aDev(code = "170043", email = "luyf@pqsoft.cn", name = "lichao")
	@aAuth(type = aAuthType.LOGIN)
	public Reply delMedia_img() {
		Map<String, Object> m = _getParameters();
		String project_id = (String) m.get("PROJECT_ID");
		this.bzService.checkProjectMan(project_id);

		int delete = Dao.delete("weixin_view.delMedia_img", m);  // FILE_ID
		JSONObject result = new JSONObject();
		result.put("ok", delete == 1);
		return new ReplyJson(result);
	}
	
	/**
	 * 微信通知处理导向的地方
	 * @author LUYANFENG @ 2015年4月21日
	 */
	@aDev(code = "170025", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply gotoWhere(){
		 Map<String, Object> params = _getParameters();
		 String PRO_CODE = (String) params.get("PRO_CODE");
		 String ID = (String) params.get("ID");
		/* if(StringUtils.isBlank(PRO_CODE)){
			 throw new ActionException("参数丢失");
		 }*/
		 
//		 Map<String, Object> selectOne = Dao.selectOne("weixin_view.select_business_detail1", params);
		//TODO 测试
		return this.list();
		
	}
	
	@aDev(code = "170025", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.ALL)
	public Reply test(){
		VelocityContext vc = new VelocityContext();
		return new ReplyHtml(VM.html("weixin/测试/资料上传.vm", vc));
	}
	
	
	
	/**
	 * 查询文件
	 * @author LUYANFENG @ 2015年4月30日
	 */
	@aDev(code = "170025", email = "luyf@pqsoft.cn", name = "luyanfeng")
	@aAuth(type=aAuthType.LOGIN)
	public Reply findFiles(){
		JSONObject json = new JSONObject();
		Map<String, Object> _getParameters = _getParameters();
		String ID = (String) _getParameters.get("ID");
		List<Map<String,Object>> files = this.bzService.getFiles(ID);
		json.put("files", files);
		json.put("ID", ID);
		return new ReplyJson(json );
	}

}
