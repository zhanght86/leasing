//package com.pqsoft.complement.action;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.apache.log4j.Logger;
//import org.apache.velocity.VelocityContext;
//import org.jbpm.api.ProcessInstance;
//
//import com.pqsoft.anchored.service.AnchoredManagerService;
//import com.pqsoft.baseScheme.service.BaseSchemeService;
//import com.pqsoft.bpm.JBPM;
//import com.pqsoft.bpm.service.TaskService;
//import com.pqsoft.bpm.status.ProjectStatus;
//import com.pqsoft.complement.service.ComplementService;
//import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
//import com.pqsoft.dataDictionary.service.DataDictionaryService;
//import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
//import com.pqsoft.pay.service.PayTaskService;
//import com.pqsoft.pigeonhole.service.PigeonholeService;
//import com.pqsoft.project.service.ProjectContractManagerService;
//import com.pqsoft.project.service.projectService;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Page;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjax;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.api.ReplyJson;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.exception.ActionException;
//import com.pqsoft.skyeye.rbac.OpLog;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//import com.pqsoft.skyeye.util.UTIL;
//import com.pqsoft.util.ReplyExcel;
//import com.pqsoft.util.StringUtils;
//
//public class ComplementAction extends Action{
//
//	private final Logger logger = Logger.getLogger(this.getClass());
//	public VelocityContext context = new VelocityContext();
//	Map<String, Object> m = null;
//	ComplementService service = new ComplementService();
//	
//	@Override
//	@aPermission(name = { "项目管理","资料补齐", "列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply execute() {
//		service.complementStartDateData("100853");
//		return null;
//	}
//	
//	@aPermission(name = { "项目管理","资料补齐", "列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply getDataList() {
//		_getParameters();
//		List<Object> list = (List) new DataDictionaryMemcached().get("资料补齐状态");
//		context.put("list", list);
//		context.put("PContext", m);
//		//租赁物名称
//		context.put("toGetProduct", service.toGetProduct(m));//获取租赁物类型
//		return new ReplyHtml(VM.html("complement/complement_Manger.vm", context));
//	}
//
//	@aPermission(name = { "项目管理","资料补齐", "列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply pageAjax() {
//		Map<String, Object> param = _getParameters();
//		Page page = service.queryPage(param);
//		return new ReplyAjaxPage(page);
//	}
//	
//	@aPermission(name = { "项目管理","资料补齐", "列表" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply uploadMessage() {
//		Map map=_getParameters();
//		return new ReplyExcel(service.uploadMessage(map), "资料补齐导出").addOp(new OpLog("项目管理", "资料补齐导出", "导出错误"));
//	}
//	
//	@aPermission(name = { "项目管理","资料补齐", "延期" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply updateEndById() {
//		Map<String, Object> param = _getParameters();
//		int result=service.updateEndById(param);
//		JSONObject json = new JSONObject();
//		if(result>0){
//			json.put("flag", true);
//		}
//		return new ReplyJson(json);
//	}
//	
//	@aPermission(name = { "项目管理","资料补齐", "转DB保证金" })
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aAuth(type = aAuthType.USER)
//	public Reply sendDB(){
//		Map<String, Object> param = _getParameters();
//		String PROJECT_CODE=param.get("PROJECT_CODE").toString();
//		String PROJECT_ID=param.get("PROJECT_ID").toString();
//		int result=service.sendDB(PROJECT_CODE,PROJECT_ID);
//		JSONObject json = new JSONObject();
//		if(result>0){
//			json.put("flag", true);
//		}
//		return new ReplyJson(json);
//	}
//	
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aPermission(name = { "项目管理","资料补齐", "补齐申请" })
//	public Reply complementC() {
//		m=_getParameters();
//		//通过穿过来的数据查询出第一张支付表
//		String payList_code=service.getOnePayList_code(m);
//		m.put("PAYLIST_CODE", payList_code);
//		context.put("BASEINFO", m);
//		return new ReplyHtml(VM.html("complement/complement_C.vm", context));
//	}
//	
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aPermission(name = { "项目管理","资料补齐", "补齐申请" })
//	public Reply doShowRetentionDataZL() {
//		Map<String, Object> param = _getParameters();
//		String LC_TYPE = "";
//		if(param.get("LC_TYPE").equals("LG")){
//			LC_TYPE = "留购";
//		}else if(param.get("LC_TYPE").equals("ZCHG")){
//			LC_TYPE = "正常回购";
//		}else if(param.get("LC_TYPE").equals("FQHG")){
//			LC_TYPE = "分期回购";
//		}else if(param.get("LC_TYPE").equals("ZJBG")){
//			LC_TYPE = "租金变更";
//		}else if(param.get("LC_TYPE").equals("ZLBQ")){
//			LC_TYPE = "资料补齐";
//		}else if(param.get("LC_TYPE").equals("ZLBG")){
//			LC_TYPE = "资料变更";
//		}
//		ProjectContractManagerService pcservice = new ProjectContractManagerService();
//		VelocityContext context = new VelocityContext();
//		param.putAll(pcservice.doShowClientTypeByProjectId(param.get("PROJECT_ID").toString()));
//		param.put("CLIENT_ID", param.get("CLIENTID"));
//		context.put("param", param);
//		context.put("BASEINFO", param);
//		context.put("FILELIST", pcservice.doShowDossierFileConfigFromDataDictionary(param.get("CLIENT_TYPE").toString(), LC_TYPE, null));
//		return new ReplyHtml(VM.html("complement/projectContractManagerZLBQ.vm", context));
//
//	}
//	
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aPermission(name = { "项目管理","资料补齐", "补齐申请","保存-发起流程" })
//	public Reply complementSave() {
//		m=_getParameters();
//		String APPLY_ID = service.complementSave(m);
//		List<String> list=JBPM.getDeploymentListByModelName("INFORMATION_PADDED");
//		Map<String,Object> map=new HashMap<String,Object>();
//		map.put("DOSSIER_APPLY_ID", APPLY_ID);
//		map.put("SUPPLIER_ID", m.get("SUPPLIER_ID"));
//		if(list.size()>0){
//			JBPM.startProcessInstanceById(list.get(0), m.get("PROJECT_ID")+"", m.get("CLIENT_ID")+"", m.get("PAYLIST_CODE")+"", map);
//			return new ReplyAjax(true).addOp(new OpLog("资料补齐-补齐申请", "提交保存", "申请ID为" + APPLY_ID));
//		}else{
//			throw new ActionException("资料补齐申请审核失败！");
//		}
//	}
//	
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aPermission(name = { "项目管理","资料补齐", "补齐申请","保存-发起流程" })
//	public Reply doAddCheckedContractData() {
//		String APPLY_ID="";
//		Map<String, Object> param = _getParameters();
//		PigeonholeService pService = new PigeonholeService();
//		pService.doDelPigeonholeApplyInfo(param);
//		Map<String, Object> m = new HashMap<String, Object>();
//		try {
//			APPLY_ID = pService.doAddPigeonholeApplyInfo(param);
//		} catch (Exception e) {
//			throw new ActionException("归档申请失败", e);
//		}
//		ProjectContractManagerService pcservice = new ProjectContractManagerService();
//		if (param.containsKey("FILEINFO")) {
//			List<Map<String, Object>> list = JSONArray.fromObject(param.get("FILEINFO"));
//			int i = 0;
//			pcservice.doDelContractByProjectId(list.get(0));
//			String LC_TYPE = "";
//			for (Map<String, Object> map : list) {
//				 if(param.get("LC_TYPE").equals("ZLBQ")){
//					LC_TYPE = "资料补齐";
//				}else if(param.get("LC_TYPE").equals("ZLBG")){
//					LC_TYPE = "资料变更";
//				}
//				map.put("TPM_BUSINESS_PLATE", LC_TYPE);
//				i += pcservice.doAddCheckedContract(map);
//			}
//			service.complementSave1(param);
//			List<String> listJBPM=JBPM.getDeploymentListByModelName("INFORMATION_PADDED");
//			Map<String,Object> map=new HashMap<String,Object>();
//			map.put("DOSSIER_APPLY_ID", APPLY_ID);
//			map.put("TASK_NAME", "资料补齐");
//			map.put("SUPPLIER_ID", param.get("SUPPLIER_ID"));
//			map.put("PROJECT_ID", param.get("PROJECT_ID"));
//			if(list.size()>0){
//				ProcessInstance processInstance = JBPM.startProcessInstanceById(listJBPM.get(0), param.get("PROJECT_ID")+"", param.get("CLIENT_ID")+"", param.get("PAYLIST_CODE")+"", map);
//				String nextCode = new TaskService().getAssignee(processInstance.getId());
//				return new ReplyAjax(true,nextCode,"下一步操作人为: ").addOp(new OpLog("资料补齐-补齐申请", "提交保存", "申请ID为" + APPLY_ID));
//			}else{
//				throw new ActionException("资料补齐申请审核失败！");
//			}
//		} else {
//			return new ReplyAjax(false, "没有索引到需要的数据");
//		}
//	}
//	
//	
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	public Reply toShowDossierApp(){
//		Map<String, Object> param = _getParameters();
//		VelocityContext context = new VelocityContext();
//		context.put("param", param);
//		String DOSSIER_APPLY_ID = param.get("DOSSIER_APPLY_ID") + "";
//		Map<String, Object> map = service.doShowPigeonholeApplyInfo(DOSSIER_APPLY_ID);
//		List<Map<String, Object>> list = JSONArray.fromObject(map.get("FILEINFO"));
//		if (list.get(0).containsKey("PAYLIST_CODE")) {
//			map.put("PAYLIST_CODE", list.get(0).get("PAYLIST_CODE"));
//		}
//		context.put("BASEINFO", map);
//		context.put("FILELIST", list);
//		return new ReplyHtml(VM.html("complement/showComplementApply.vm", context));
//	}
//	
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	public Reply toUpLoadDossierInfo(){
//		Map<String, Object> param = _getParameters();
//		VelocityContext context=new VelocityContext();
//		List<Map<String,Object>> list=service.doShowDossierStorageApplyList(param);
//		context.put("FILELIST", list);
//		context.put("baseInfo", list.get(0));
//		return new ReplyHtml(VM.html("complement/upLoadDossierInfo.vm", context));
//	}
//	
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aPermission(name = { "项目管理","资料补齐", "变更" })
//	public Reply complementU() {
//		Map<String, Object> param = _getParameters();
//		VelocityContext context = new VelocityContext();
//		List<Map<String, Object>> FILELIST = service.doShowDossierBorrowApplyList(param);
//		context.put("FILELIST", FILELIST);
//			
//		//通过穿过来的数据查询出第一张支付表
//		String payList_code=service.getOnePayList_code(param);
//		param.put("PAYLIST_CODE", payList_code);
//		context.put("BASEINFO", param);
//		
//		projectService service1 = new projectService();
//		// 项目信息
//		Map projectMap = service1.queryProjectById(param);
//
//		// 设备信息
//		List eqlist = service1.queryEqByProjectID(param);
//		
//		// 单位
//		List unitList = (List) new DataDictionaryMemcached().get("设备单位");
//		context.put("unitList", unitList);
//		
//		context.put("param", projectMap);
//		context.put("eqList", eqlist);
//		
//		return new ReplyHtml(VM.html("complement/complement_U.vm", context));
//	}
//	
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aPermission(name = { "项目管理","资料补齐", "变更" })
//	public Reply doShowRetentionDataZLBG() {
//		Map<String, Object> param = _getParameters();
//		String LC_TYPE = "";
//		if(param.get("LC_TYPE").equals("LG")){
//			LC_TYPE = "留购";
//		}else if(param.get("LC_TYPE").equals("ZCHG")){
//			LC_TYPE = "正常回购";
//		}else if(param.get("LC_TYPE").equals("FQHG")){
//			LC_TYPE = "分期回购";
//		}else if(param.get("LC_TYPE").equals("ZJBG")){
//			LC_TYPE = "租金变更";
//		}else if(param.get("LC_TYPE").equals("ZLBQ")){
//			LC_TYPE = "资料补齐";
//		}else if(param.get("LC_TYPE").equals("ZLBG")){
//			LC_TYPE = "资料变更";
//			
//			projectService proservice=new projectService();
//			// 项目信息
//			Map projectMap = proservice.queryProjectById(param);
//			param.putAll(projectMap);
//
//			// 设备信息
//			List eqlist = proservice.queryEqByProjectID(param);
//			String SUPPLIER_ID=param.get("SUP_ID")+"";
//			context.put("supAccountList", proservice.doShowSupAccount(SUPPLIER_ID));
//			// 担保人信息
//			List GuaranList = proservice.queryGuaranByProjectID(param);
//
//			List<Map<String, Object>> listSechme = proservice.querySechmeByAllDate(param);
//
//			// 还款计划
//			// 查询支付表id在修改的时候使用
//			PayTaskService pay = new PayTaskService();
//			String PROJECT_ID = param.get("PROJECT_ID") + "";
//			int PAY_ID = pay.queryIdByProjectId(PROJECT_ID);
//			context.put("totalMap", proservice.doShowRentInfo(PAY_ID + ""));
//			context.put("PAY_ID", PAY_ID);
//			Map<String, Object> payMap = new HashMap<String, Object>();
//			payMap.put("ID", PAY_ID);
//			context.put("detailList", pay.doShowRentPlanDetail(payMap));
//			// 方案信息(商务政策)
//			if (listSechme.size() <= 0 && param.get("POB_ID") != null) {
//				BaseSchemeService schemeService = new BaseSchemeService();
//				List<Map<String, Object>> SCHEMEDETAIL = schemeService.doSelectSchemeDetailByName(param.get("POB_ID") + "", "0", "1");
//				for (Map<String, Object> mapItem : SCHEMEDETAIL) {
//					if ("AFFILIATED_COMPANY".equals(mapItem.get("KEY_NAME_EN"))) {
//						List lst = new AnchoredManagerService().doShowAnchoredCompanyList();
//						if (lst.isEmpty() || lst.size() < 1) {
//							mapItem.put("VALUE_STATUS", "1");
//						} else {
//							mapItem.put("LIST", lst);
//						}
//					} else {
//						List lst = new DataDictionaryMemcached().get(mapItem.get("KEY_NAME_ZN") + "");
//						mapItem.put("LIST", lst);
//					}
//				}
//				context.put("SCHEMEDETAIL", SCHEMEDETAIL);
//
//				if (param.get("POB_ID") != "" && !param.get("POB_ID").equals("-1")) {
//					Map mapfee = new HashMap();
//					BaseSchemeService baseSchService = new BaseSchemeService();
//					mapfee.put("SCHEME_NAME", param.get("POB_ID"));
//					param.put("feeCount", baseSchService.doSelectBaseSchemeFeeRateCount(mapfee));
//				}
//			} else {
//				for (Map<String, Object> mapItem : listSechme) {
//					if ("AFFILIATED_COMPANY".equals(mapItem.get("KEY_NAME_EN"))) {
//						List lst = new AnchoredManagerService().doShowAnchoredCompanyList();
//						if (lst.isEmpty() || lst.size() < 1) {
//							mapItem.put("VALUE_STATUS", "1");
//						} else {
//							mapItem.put("LIST", lst);
//						}
//					} else {
//						List lst = new DataDictionaryMemcached().get(mapItem.get("KEY_NAME_ZN") + "");
//						mapItem.put("LIST", lst);
//					}
//				}
//				context.put("SCHEMEDETAIL", listSechme);
//
//			}
//
//			// 判断当前流程节点名称
//			context.put("TASKNAME", "SIGN");
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//			// 扣款类型
//			List final_Type_List = new ArrayList();
//			Map finalMap2 = new HashMap();
//			finalMap2.put("FINAL_TYPE", null);
//			finalMap2.put("FINAL_NAME", "--请选择--");
//			final_Type_List.add(finalMap2);
//
//			Map finalMap = new HashMap();
//			finalMap.put("FINAL_TYPE", "0");
//			finalMap.put("FINAL_NAME", "自有客户");
//			final_Type_List.add(finalMap);
//
//			Map finalMap1 = new HashMap();
//			finalMap1.put("FINAL_TYPE", "1");
//			finalMap1.put("FINAL_NAME", "终端客户");
//			final_Type_List.add(finalMap1);
//			context.put("final_Type_List", final_Type_List);
//
//			// 发票开具类型
//			List<Map<String, Object>> invoice_target_type = new DataDictionaryService().queryDataDictionaryByScheme("发票_对象_类型");
//			context.put("invoice_target_type", invoice_target_type);
//
//			context.put("eqList", eqlist);
//			context.put("GuaranList", GuaranList);
//			context.put("FORMAT", UTIL.FORMAT);
//			context.put("modelList", new SysDictionaryMemcached().get("业务类型"));
//			context.put("param", param);
//			context.put("bankList", new DataDictionaryMemcached().get("开户行所属总行"));
//		}
//		ProjectContractManagerService pcservice = new ProjectContractManagerService();
//		param.putAll(pcservice.doShowClientTypeByProjectId(param.get("PROJECT_ID").toString()));
//		param.put("CLIENT_ID", param.get("CLIENTID"));
//		context.put("param", param);
//		context.put("BASEINFO", param);
//		context.put("FILELIST", pcservice.doShowDossierFileConfigFromDataDictionary(param.get("CLIENT_TYPE").toString(), LC_TYPE, null));
//		if(param.get("LC_TYPE").equals("ZLBG")){
//			context.put("ZLBG", "ZLBG");
//			return new ReplyHtml(VM.html("complement/projectContractManagerZLBG_main.vm", context));
//		}else{
//			return new ReplyHtml(VM.html("complement/projectContractManagerZLBG.vm", context));
//		}
//	}
//	
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aPermission(name = { "项目管理","资料补齐", "变更","保存-发起流程" })
//	public Reply doAddBorrowApp() {
//		Map<String, Object> param = _getParameters();
//		//修改变更流程SUPPLIER_ID
//		service.updateStatus(param);
//		List<String> list=JBPM.getDeploymentListByModelName("DATA_CHANGES");
//		Map<String,Object> map=new HashMap<String,Object>();
//		map.put("PARAM", param);
//		ProcessInstance processinstance = JBPM.startProcessInstanceById(list.get(0), param.get("PROJECT_ID")+"", param.get("CLIENT_ID")+"", "", param);
//		String nextCode = new TaskService().getAssignee(processinstance.getId());
//		return new ReplyAjax(true,nextCode,"下一步操作人为: ").addOp(new OpLog("资料补齐-变更申请", "提交流程", "申请ID为" + param.get("PROJECT_ID")));
//	}
//	
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	@aPermission(name = { "项目管理","资料补齐", "变更","保存-发起流程" })
//	public Reply doAddCheckedBGContractData() {
//		String APPLY_ID="";
//		Map<String, Object> param = _getParameters();
//		PigeonholeService pService = new PigeonholeService();
//		pService.doDelPigeonholeApplyInfo(param);
//		Map<String, Object> m = new HashMap<String, Object>();
//		try {
//			APPLY_ID = pService.doAddPigeonholeApplyInfo(param);
//		} catch (Exception e) {
//			throw new ActionException("归档申请失败", e);
//		}
//		ProjectContractManagerService pcservice = new ProjectContractManagerService();
//		if (param.containsKey("FILEINFO")) {
//			List<Map<String, Object>> list = JSONArray.fromObject(param.get("FILEINFO"));
//			int i = 0;
//			if(!list.isEmpty()&&list.size()>0){
//				pcservice.doDelContractByProjectId(list.get(0));
//				String LC_TYPE = "";
//				for (Map<String, Object> map : list) {
//					 if(param.get("LC_TYPE").equals("ZLBQ")){
//						LC_TYPE = "资料补齐";
//					}else if(param.get("LC_TYPE").equals("ZLBG")){
//						LC_TYPE = "资料变更";
//					}
//					map.put("TPM_BUSINESS_PLATE", LC_TYPE);
//					i += pcservice.doAddCheckedContract(map);
//				}
//			}
//			service.updateStatus(param);
//			List<String> listJBPM=JBPM.getDeploymentListByModelName("DATA_CHANGES");
//			Map<String,Object> map=new HashMap<String,Object>();
//			map.put("DOSSIER_APPLY_ID", APPLY_ID);
//			map.put("TASK_NAME", "资料变更");
//			map.put("SUPPLIER_ID", param.get("SUPPLIER_ID"));
//			map.put("PROJECT_ID", param.get("PROJECT_ID"));
//			map.put("PROJECTCHANGEID", param.get("PROJECTCHANGEID"));
//			if(listJBPM.size()>0){
//				ProcessInstance processInstance =  JBPM.startProcessInstanceById(listJBPM.get(0), param.get("PROJECT_ID")+"", param.get("CLIENT_ID")+"", param.get("PAYLIST_CODE")+"", map);
//				String nextCode = new TaskService().getAssignee(processInstance.getId());
//				return new ReplyAjax(true,nextCode,"下一步操作人为: ").addOp(new OpLog("资料补齐-变更申请", "提交保存", "申请ID为" + APPLY_ID));
//			}else{
//				throw new ActionException("资料补齐申请审核失败！");
//			}
//		} else {
//			return new ReplyAjax(false, "没有索引到需要的数据");
//		}
//	}
//	
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
//	public Reply complementV() {
//		
//		Map<String, Object> param = _getParameters();
//		
//		JSONArray DOSSIERINFOLIST = JSONArray.fromObject(param.get("DOSSIERINFO"));
//		JSONArray EQINFOLIST = JSONArray.fromObject(param.get("EQINFO"));
//		Map map=new HashMap();
//		map.put("CUST_NAME", param.get("CUST_NAME"));
//		map.put("PRO_CODE", param.get("PROJECT_CODE"));
//		map.put("PROJECT_MODEL", param.get("PROJECT_MODEL"));
//		map.put("RESTOREPURPOSE", param.get("RESTOREPURPOSE"));
//		context.put("eqList", EQINFOLIST);
//		context.put("FILELIST", DOSSIERINFOLIST);
//		context.put("BASEINFO", map);
//		return new ReplyHtml(VM.html("complement/complement_V.vm", context));
//	}
//	
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	public Reply doAddProjectChangeApply(){
//		Map<String,Object> param=_getParameters();
//		String PROJECTCHANGEID=service.doAddProjectChangeApply(param);
//		return new ReplyAjax(true, PROJECTCHANGEID, "成功").addOp(new OpLog("项目变更", "添加资料变更", PROJECTCHANGEID));
//	}
//	
//	/**
//	 * 修改项目资料
//	 * @return
//	 * @author:King 2013-12-19
//	 */
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
//	public Reply doUpdateProjectChangeApply(){
//		Map<String,Object> param=_getParameters();
//		service.doUpdateProjectChangeApply(param);
//		return new ReplyAjax(true, "成功").addOp(new OpLog("项目变更", "添加资料变更", param.get("PROJECTCHANGEID")+""));
//	}
//	
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
////	@aPermission(name = { "流程任务", "档案入柜", "查看" })
//	public Reply toShowComplementDossierStorage(){
//		Map<String, Object> param = _getParameters();
//		VelocityContext context=new VelocityContext();
//		String PROJECTCHANGEID=param.get("PROJECTCHANGEID")+"";
//		List<Map<String,Object>> equlist=new ArrayList<Map<String,Object>>();
//		if(!StringUtils.isBlank(param.get("PROJECTCHANGEID"))){
//			Map<String,Object> projMap=new HashMap<String,Object>();
//			projMap=service.getProjectChange(param);
//			equlist=JSONArray.fromObject(projMap.get("EQUIPMENT"));
//			//资料变更需要调用的项目变更信息
//			context.put("projMap",projMap);
//		}
//		
//		projectService proservice=new projectService();
//		// 项目信息
//		Map projectMap = proservice.queryProjectById(param);
//		param.putAll(projectMap);
//
//		// 设备信息
//		List<Map<String,Object>> eqlist = proservice.queryEqByProjectID(param);
//		if(!equlist.isEmpty()&&equlist.size()>0){
//			for (Map<String,Object> object : eqlist) {
//				for (Map<String,Object> em : equlist) {
//					String EQ_ID=(em.get("EQ_ID")+"").trim();
//					String ID=(object.get("ID")+"").trim();
//					if(EQ_ID.equals(ID)){
//						object.putAll(em);
//						break;
//					}
//				}
//			}
//		}
//		String SUPPLIER_ID=param.get("SUP_ID")+"";
//		context.put("supAccountList", proservice.doShowSupAccount(SUPPLIER_ID));
//		// 担保人信息
//		List GuaranList = proservice.queryGuaranByProjectID(param);
//
//		List<Map<String, Object>> listSechme = proservice.querySechmeByAllDate(param);
//
//		// 还款计划
//		// 查询支付表id在修改的时候使用
//		PayTaskService pay = new PayTaskService();
//		String PROJECT_ID = param.get("PROJECT_ID") + "";
//		int PAY_ID = pay.queryIdByProjectId(PROJECT_ID);
//		context.put("totalMap", proservice.doShowRentInfo(PAY_ID + ""));
//		context.put("PAY_ID", PAY_ID);
//		Map<String, Object> payMap = new HashMap<String, Object>();
//		payMap.put("ID", PAY_ID);
//		context.put("detailList", pay.doShowRentPlanDetail(payMap));
//		// 方案信息(商务政策)
//		if (listSechme.size() <= 0 && param.get("POB_ID") != null) {
//			BaseSchemeService schemeService = new BaseSchemeService();
//			List<Map<String, Object>> SCHEMEDETAIL = schemeService.doSelectSchemeDetailByName(param.get("POB_ID") + "", "0", "1");
//			for (Map<String, Object> mapItem : SCHEMEDETAIL) {
//				if ("AFFILIATED_COMPANY".equals(mapItem.get("KEY_NAME_EN"))) {
//					List lst = new AnchoredManagerService().doShowAnchoredCompanyList();
//					if (lst.isEmpty() || lst.size() < 1) {
//						mapItem.put("VALUE_STATUS", "1");
//					} else {
//						mapItem.put("LIST", lst);
//					}
//				} else {
//					List lst = new DataDictionaryMemcached().get(mapItem.get("KEY_NAME_ZN") + "");
//					mapItem.put("LIST", lst);
//				}
//			}
//			context.put("SCHEMEDETAIL", SCHEMEDETAIL);
//
//			if (param.get("POB_ID") != "" && !param.get("POB_ID").equals("-1")) {
//				Map mapfee = new HashMap();
//				BaseSchemeService baseSchService = new BaseSchemeService();
//				mapfee.put("SCHEME_NAME", param.get("POB_ID"));
//				param.put("feeCount", baseSchService.doSelectBaseSchemeFeeRateCount(mapfee));
//			}
//		} else {
//			for (Map<String, Object> mapItem : listSechme) {
//				if ("AFFILIATED_COMPANY".equals(mapItem.get("KEY_NAME_EN"))) {
//					List lst = new AnchoredManagerService().doShowAnchoredCompanyList();
//					if (lst.isEmpty() || lst.size() < 1) {
//						mapItem.put("VALUE_STATUS", "1");
//					} else {
//						mapItem.put("LIST", lst);
//					}
//				} else {
//					List lst = new DataDictionaryMemcached().get(mapItem.get("KEY_NAME_ZN") + "");
//					mapItem.put("LIST", lst);
//				}
//			}
//			context.put("SCHEMEDETAIL", listSechme);
//
//		}
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		
//		// 扣款类型
//		List final_Type_List = new ArrayList();
//		Map finalMap2 = new HashMap();
//		finalMap2.put("FINAL_TYPE", null);
//		finalMap2.put("FINAL_NAME", "--请选择--");
//		final_Type_List.add(finalMap2);
//
//		Map finalMap = new HashMap();
//		finalMap.put("FINAL_TYPE", "0");
//		finalMap.put("FINAL_NAME", "自有客户");
//		final_Type_List.add(finalMap);
//
//		Map finalMap1 = new HashMap();
//		finalMap1.put("FINAL_TYPE", "1");
//		finalMap1.put("FINAL_NAME", "终端客户");
//		final_Type_List.add(finalMap1);
//		context.put("final_Type_List", final_Type_List);
//		context.put("bankList", new DataDictionaryMemcached().get("开户行所属总行"));
//		// 发票开具类型
//		List<Map<String, Object>> invoice_target_type = new DataDictionaryService().queryDataDictionaryByScheme("发票_对象_类型");
//		context.put("invoice_target_type", invoice_target_type);
//		// 判断当前流程节点名称
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		list = new TaskService().doShowTaskInfoByJbpmId(param);
//		if (!list.isEmpty() && list.size() > 0) {
//			String taskName = list.get(0).get("NAME_") + "";
//			if (taskName.contains("企划部部长")) {
//				context.put("TASKNAME", "QHBBZ");
//			} else if (taskName.contains("保险变更")) {
//				context.put("TASKNAME", "BXBG");
//			} else if (taskName.contains("档案")) {
//				context.put("TASKNAME", "DOSSIER");
//			} else if (taskName.contains("通知担当")) {
//				context.put("TASKNAME", "TZDD");
//			} else if (taskName.contains("担当申请")) {
//				context.put("TASKNAME", "DDSQ");
//			}
//			context.put("NOWDAY", sdf.format(new Date()));
//			// 更新项目主表 当前节点名称
//			new ProjectStatus().updateProjectJbpmTaskName(PROJECT_ID, taskName);
//		}
//		context.put("eqList", eqlist);
//		context.put("GuaranList", GuaranList);
//		context.put("FORMAT", UTIL.FORMAT);
//		context.put("modelList", new SysDictionaryMemcached().get("业务类型"));
//		context.put("param", param);
//		context.put("ZLBG", "ZLBG");
//		return new ReplyHtml(VM.html("complement/complementShowProject_main.vm", context));
//	}
//}
