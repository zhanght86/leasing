//package com.pqsoft.project.purchase.action;
//
//import java.util.ArrayList;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.apache.log4j.Logger;
//import org.apache.velocity.VelocityContext;
//import org.jbpm.api.ProcessInstance;
//
//import com.pqsoft.baseScheme.service.BaseSchemeService;
//import com.pqsoft.bpm.JBPM;
//import com.pqsoft.buyBack.action.ExcelTest;
//import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
//import com.pqsoft.dataDictionary.service.DataDictionaryService;
//import com.pqsoft.project.purchase.service.PurchaseService;
//import com.pqsoft.project.service.ProjectContractManagerService;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Page;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjax;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.api.ReplyJson;
//import com.pqsoft.skyeye.api.ReplyMobile;
//import com.pqsoft.skyeye.api.SkyEye;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.exception.ActionException;
//import com.pqsoft.skyeye.rbac.OpLog;
//import com.pqsoft.skyeye.rbac.api.Security;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//import com.pqsoft.util.DateUtil;
//import com.pqsoft.util.ReplyExcel;
//import com.pqsoft.util.SecuUtil;
//
//public class PurchaseAction extends Action {
//	private final Logger logger = Logger.getLogger(this.getClass());
//	public VelocityContext context = new VelocityContext();
//	Map<String, Object> m = null;
//	PurchaseService service = new PurchaseService();
//
//	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
//	@aAuth(type = aAuthType.ALL)
//	private void getPageParameter() {
//		m = new HashMap<String, Object>();
//		Enumeration<?> en = SkyEye.getRequest().getParameterNames();
//		while (en.hasMoreElements()) {
//			Object enN = en.nextElement();
//			String para = SkyEye.getRequest().getParameter(enN.toString()).trim();
//			m.put(enN.toString(), para);
//		}
//		m.put("USERNAME", Security.getUser().getName());
//		m.put("USERCODE", Security.getUser().getCode());
//		m.put("USERID", Security.getUser().getId());
//	}
//
//	@Override
//	public Reply execute() {
//		return null;
//	}
//
//	@aPermission(name = { "项目管理", "项目留购", "留购申请" })
//	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
//	@aAuth(type = aAuthType.USER)
//	public Reply getDataList() {
//		this.getPageParameter();
//		context.put("PContext", m);
//		return new ReplyHtml(VM.html("project/purchase/purchase_Manager.vm", context));
//	}
//
//	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
//	@aAuth(type = aAuthType.LOGIN)
//	public Reply pageAjax() {
//		Map<String, Object> param = _getParameters();
//		Page page = service.queryPage(param);
//		return new ReplyAjaxPage(page);
//	}
//	
//	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
//	@aAuth(type = aAuthType.LOGIN)
//	public Reply queryContentByPROJID() {
//		this.getPageParameter();
//		List list = new ArrayList();
//		if (m != null && m.get("PRODUCT_ID") != null) {
//			list = service.queryContentByPROJID(m);
//		}
//		return new ReplyJson(JSONArray.fromObject(list)).addOp(new OpLog("项目管理", "项目留购", "项目编号查询"));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"项目管理","项目留购","留购申请","填写留购表单"})
//	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
//	public Reply applyPurchasePage(){
//		Map<String, Object> map = _getParameters();
//
//		// 项目信息
//		Map projectMap = service.queryProjectById(map);
//		map.putAll(projectMap);
//
//		// 设备信息
//		List eqlist = service.queryEqByProjectID(map);
//
//		// 担保人信息
//		List GuaranList = service.queryGuaranByProjectID(map);
//
//		List<Map<String, Object>> listSechme = service.querySechmeByProjectID(map);
//
//		// 还款计划
//		// 查询支付表id在修改的时候使用
//		int PAY_ID = service.queryIdByProjectId(map.get("PROJECT_ID") + "");
//		context.put("PAY_ID", PAY_ID);
//		// 方案信息(商务政策)
//		if (listSechme.size() <= 0 && map.get("POB_ID") != null) {
//			List<Map<String, Object>> list = new DataDictionaryService().queryDataDictionaryByScheme("政策元素");
//			for (Map<String, Object> mapItem : list) {
//				List lst = new DataDictionaryMemcached().get(mapItem.get("FLAG") + "");
//				mapItem.put("LIST", lst);
//			}
//			context.put("DATALIST", list);
//			Map schemeMap = new HashMap();
//			schemeMap.put("SCHEME_NAME", map.get("POB_ID"));
//			schemeMap.put("STATUS", 0);
//			BaseSchemeService schemeService = new BaseSchemeService();
//			context.put("SCHEMEDETAIL", schemeService.doSelectSchemeDetailByName(schemeMap));
//			context.put("viewType", "project_scheme_view1");
//
//			if (map.get("POB_ID") != "" && !map.get("POB_ID").equals("-1")) {
//				Map mapfee = new HashMap();
//				BaseSchemeService baseSchService = new BaseSchemeService();
//				mapfee.put("SCHEME_NAME", map.get("POB_ID"));
//				map.put("feeCount", baseSchService.doSelectBaseSchemeFeeRateCount(mapfee));
//			}
//		} else {
//			List<Map<String, Object>> list = service.querySechmeByAllDate(map);
//			for (Map<String, Object> mapItem : list) {
//				List lst = new DataDictionaryMemcached().get(mapItem.get("FLAG") + "");
//				mapItem.put("LIST", lst);
//			}
//			context.put("DATALIST", list);
//			context.put("SCHEMEDETAIL", listSechme);
//		}
//
//		context.put("eqList", eqlist);
//		context.put("GuaranList", GuaranList);
//		context.put("param", map);
//		if (Security.isMobile()) {
//			return new ReplyMobile(context);
//		} else{
//			return new ReplyHtml(VM.html("project/purchase/purchaseProjectView.vm", context));
//		}
//	}
//	
//	/**
//	 * 适用留购流程
//	 * 生成所有权转移证书
//	 */
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
//	public Reply ExcelFile() {
//		Map<String, Object> param = _getParameters();
//		param.putAll(service.selectFileData(param));
//		Double LEAVE = 0.00;// 留购价
//		
//		if (param.containsKey("LEAVE") && !param.get("LEAVE").equals("")) {
//			LEAVE = Double.parseDouble(param.get("LEAVE").toString());
//		}
//		param.put("LEAVE", LEAVE);// 留购价
//		param.put("FILE_NAME", param.get("SUPPLIER_NAMES")+""+param.get("PRO_CODE")+param.get("FILE_NAME"));
//		return new ExcelTest(param);
//	}
//	
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
//	public Reply exportExcel(){
//		m = _getParameters();
//		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
//		m.remove("searchParams");
//		m.putAll(json);
//		//加入操作人信息
//		SecuUtil.putUserInfo(m);
//		return new ReplyExcel(service.exportData(m),"项目留购申请"+DateUtil.getSysDate()+Math.abs(new Random(10000).nextInt())+".xls");
//	}
//	
//	@SuppressWarnings("unchecked")
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
//	public Reply exportSearchExcel(){
//		m = _getParameters();
//		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
//		m.remove("searchParams");
//		m.putAll(json);
//		//加入操作人信息
//		SecuUtil.putUserInfo(m);
//		return new ReplyExcel(service.exportSearchData(m),"项目留购查询"+DateUtil.getSysDate()+Math.abs(new Random(10000).nextInt())+".xls");
//	}
//	
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aPermission(name ={"项目管理","项目留购","留购申请","发起流程(按钮)"})
//	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
//	public Reply appPurchaseWF(){
//		Map<String, Object> map = _getParameters();
//
//		JSONArray jsonArray = JSONArray.fromObject(map.get("param"));
//		//发起流程 需要确定是否需要展示多个流程图供选择
//		List<String> list = JBPM.getDeploymentListByModelName("留购");
//		String pid = null;
//		if (list.size() > 0)
//			pid = list.get(0);
//		else
//			throw new ActionException("没有找到流程图");
//
//		Map<String, Object> jmap = new HashMap<String, Object>();
//		jmap.put("PROJECT_ID", map.get("PROJECT_ID"));
//		jmap.put("SUPPLIER_ID", map.get("SUP_ID"));
//		jmap.put("PRO_CODE", map.get("PRO_CODE"));
//		ProcessInstance instance = JBPM.startProcessInstanceById(pid, map.get("PROJECT_ID").toString(), map.get("CLIENT_ID").toString(), null, jmap);
//		//插入FIL_PROJECT_END_PURCHASE留购表数据
//		Map<String, Object> mapData = new HashMap<String, Object>();
//		mapData.put("PROJECT_ID", map.get("PROJECT_ID"));
//		mapData.put("PRO_CODE", map.get("PRO_CODE").toString());
//		mapData.put("SUP_ID", map.get("SUP_ID").toString());
//		mapData.put("REMARK", map.get("REMARK").toString());
//		mapData.put("USERNAME", Security.getUser().getName());
//		service.insertProjectPurchase(mapData);
//		{
//			//付玉龙
//			String JBPM_ID = instance.getId();//流程id
//			new ProjectContractManagerService().doAddProjectContractList(JBPM_ID);//保存欲归档文件
//		}
//		return new ReplyAjax(true).addOp(new OpLog("项目管理", "项目留购", "发起流程(按钮)：" + map.get("PRO_CODE")));
//	}
//	
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
//	public Reply purchaseShow() {
//		Map<String, Object> map = _getParameters();
//
//		// 项目信息
//		Map projectMap = service.queryProjectById(map);
//		map.putAll(projectMap);
//
//		// 设备信息
//		List eqlist = service.queryEqByProjectID(map);
//		// 担保人信息
//		List GuaranList = service.queryGuaranByProjectID(map);
//		List<Map<String, Object>> listSechme = service.querySechmeByProjectID(map);
//		// 还款计划
//		// 查询支付表id在修改的时候使用
//		int PAY_ID = service.queryIdByProjectId(map.get("PROJECT_ID") + "");
//		context.put("PAY_ID", PAY_ID);
//		// 方案信息(商务政策)
//		if (listSechme.size() <= 0 && map.get("POB_ID") != null) {
//			List<Map<String, Object>> list = new DataDictionaryService().queryDataDictionaryByScheme("政策元素");
//			for (Map<String, Object> mapItem : list) {
//				List lst = new DataDictionaryMemcached().get(mapItem.get("FLAG") + "");
//				mapItem.put("LIST", lst);
//			}
//			context.put("DATALIST", list);
//			Map schemeMap = new HashMap();
//			schemeMap.put("SCHEME_NAME", map.get("POB_ID"));
//			schemeMap.put("STATUS", 0);
//			BaseSchemeService schemeService = new BaseSchemeService();
//			context.put("SCHEMEDETAIL", schemeService.doSelectSchemeDetailByName(schemeMap));
//			context.put("viewType", "project_scheme_view1");
//
//			if (map.get("POB_ID") != "" && !map.get("POB_ID").equals("-1")) {
//				Map mapfee = new HashMap();
//				BaseSchemeService baseSchService = new BaseSchemeService();
//				mapfee.put("SCHEME_NAME", map.get("POB_ID"));
//				map.put("feeCount", baseSchService.doSelectBaseSchemeFeeRateCount(mapfee));
//			}
//		} else {
//			List<Map<String, Object>> list = service.querySechmeByAllDate(map);
//			for (Map<String, Object> mapItem : list) {
//				List lst = new DataDictionaryMemcached().get(mapItem.get("FLAG") + "");
//				mapItem.put("LIST", lst);
//			}
//			context.put("DATALIST", list);
//			context.put("SCHEMEDETAIL", listSechme);
//		}
//
//		context.put("eqList", eqlist);
//		context.put("GuaranList", GuaranList);
//		context.put("param", map);
//		if (Security.isMobile()) {
//			return new ReplyMobile(context);
//		} else{
//			return new ReplyHtml(VM.html("project/purchase/purchaseProjectViewUpload.vm", context));
//		}
//	}
//	
//	/**
//	 * 
//	 * @param
//	 * @author 付玉龙  fuyulong47@foxmail.com
//	 * @date 2013-12-3  下午03:16:44
//	 */
//	@aAuth(type = aAuthType.LOGIN)
//	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
//	public Reply purchaseShowZJL() {
//		Map<String, Object> map = _getParameters();
//		
//		// 项目信息
//		Map projectMap = service.queryProjectById(map);
//		map.putAll(projectMap);
//		
//		// 设备信息
//		List eqlist = service.queryEqByProjectID(map);
//		// 担保人信息
//		List GuaranList = service.queryGuaranByProjectID(map);
//		List<Map<String, Object>> listSechme = service.querySechmeByProjectID(map);
//		// 还款计划
//		// 查询支付表id在修改的时候使用
//		int PAY_ID = service.queryIdByProjectId(map.get("PROJECT_ID") + "");
//		context.put("PAY_ID", PAY_ID);
//		// 方案信息(商务政策)
//		if (listSechme.size() <= 0 && map.get("POB_ID") != null) {
//			List<Map<String, Object>> list = new DataDictionaryService().queryDataDictionaryByScheme("政策元素");
//			for (Map<String, Object> mapItem : list) {
//				List lst = new DataDictionaryMemcached().get(mapItem.get("FLAG") + "");
//				mapItem.put("LIST", lst);
//			}
//			context.put("DATALIST", list);
//			Map schemeMap = new HashMap();
//			schemeMap.put("SCHEME_NAME", map.get("POB_ID"));
//			schemeMap.put("STATUS", 0);
//			BaseSchemeService schemeService = new BaseSchemeService();
//			context.put("SCHEMEDETAIL", schemeService.doSelectSchemeDetailByName(schemeMap));
//			context.put("viewType", "project_scheme_view1");
//			
//			if (map.get("POB_ID") != "" && !map.get("POB_ID").equals("-1")) {
//				Map mapfee = new HashMap();
//				BaseSchemeService baseSchService = new BaseSchemeService();
//				mapfee.put("SCHEME_NAME", map.get("POB_ID"));
//				map.put("feeCount", baseSchService.doSelectBaseSchemeFeeRateCount(mapfee));
//			}
//		} else {
//			List<Map<String, Object>> list = service.querySechmeByAllDate(map);
//			for (Map<String, Object> mapItem : list) {
//				List lst = new DataDictionaryMemcached().get(mapItem.get("FLAG") + "");
//				mapItem.put("LIST", lst);
//			}
//			context.put("DATALIST", list);
//			context.put("SCHEMEDETAIL", listSechme);
//		}
//		
//		context.put("eqList", eqlist);
//		context.put("GuaranList", GuaranList);
//		context.put("param", map);
//		if (Security.isMobile()) {
//			return new ReplyMobile(context);
//		} else{
//			return new ReplyHtml(VM.html("project/purchase/purchaseProjectViewUploadZJL.vm", context));
//		}
//	}
//	
//	@aAuth(type = aAuth.aAuthType.LOGIN)
//	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
//	public Reply purchaseShowReadonly() {
//		Map<String, Object> map = _getParameters();
//
//		// 项目信息
//		Map projectMap = service.queryProjectById(map);
//		map.putAll(projectMap);
//
//		// 设备信息
//		List eqlist = service.queryEqByProjectID(map);
//		// 担保人信息
//		List GuaranList = service.queryGuaranByProjectID(map);
//		List<Map<String, Object>> listSechme = service.querySechmeByProjectID(map);
//		// 还款计划
//		// 查询支付表id在修改的时候使用
//		int PAY_ID = service.queryIdByProjectId(map.get("PROJECT_ID") + "");
//		context.put("PAY_ID", PAY_ID);
//		// 方案信息(商务政策)
//		if (listSechme.size() <= 0 && map.get("POB_ID") != null) {
//			List<Map<String, Object>> list = new DataDictionaryService().queryDataDictionaryByScheme("政策元素");
//			for (Map<String, Object> mapItem : list) {
//				List lst = new DataDictionaryMemcached().get(mapItem.get("FLAG") + "");
//				mapItem.put("LIST", lst);
//			}
//			context.put("DATALIST", list);
//			Map schemeMap = new HashMap();
//			schemeMap.put("SCHEME_NAME", map.get("POB_ID"));
//			schemeMap.put("STATUS", 0);
//			BaseSchemeService schemeService = new BaseSchemeService();
//			context.put("SCHEMEDETAIL", schemeService.doSelectSchemeDetailByName(schemeMap));
//			context.put("viewType", "project_scheme_view1");
//
//			if (map.get("POB_ID") != "" && !map.get("POB_ID").equals("-1")) {
//				Map mapfee = new HashMap();
//				BaseSchemeService baseSchService = new BaseSchemeService();
//				mapfee.put("SCHEME_NAME", map.get("POB_ID"));
//				map.put("feeCount", baseSchService.doSelectBaseSchemeFeeRateCount(mapfee));
//			}
//		} else {
//			List<Map<String, Object>> list = service.querySechmeByAllDate(map);
//			for (Map<String, Object> mapItem : list) {
//				List lst = new DataDictionaryMemcached().get(mapItem.get("FLAG") + "");
//				mapItem.put("LIST", lst);
//			}
//			context.put("DATALIST", list);
//			context.put("SCHEMEDETAIL", listSechme);
//		}
//
//		context.put("eqList", eqlist);
//		context.put("GuaranList", GuaranList);
//		context.put("param", map);
//		if (Security.isMobile()) {
//			return new ReplyMobile(context);
//		} else{
//			return new ReplyHtml(VM.html("project/purchase/purchaseProjectViewReadonly.vm", context));
//		}
//	}
//	
//	@aPermission(name ={"项目管理","项目留购","留购查询"})
//	@aAuth(type = aAuth.aAuthType.USER)
//	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
//	public Reply purchaseManagedSearch() {
//		this.getPageParameter();
//		context.put("PContext", m);
//		return new ReplyHtml(VM.html("project/purchase/purchase_Managed_Search.vm", context));
//	}
//	
//	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
//	@aAuth(type = aAuthType.LOGIN)
//	public Reply pageSearchAjax() {
//		Map<String, Object> param = _getParameters();
//		Page page = service.querySearchPage(param);
//		return new ReplyAjaxPage(page);
//	}
//	
//}
////package com.pqsoft.project.purchase.action;
////
////import java.util.ArrayList;
////import java.util.Enumeration;
////import java.util.HashMap;
////import java.util.List;
////import java.util.Map;
////import java.util.Random;
////
////import net.sf.json.JSONArray;
////import net.sf.json.JSONObject;
////
////import org.apache.log4j.Logger;
////import org.apache.velocity.VelocityContext;
////import org.jbpm.api.ProcessInstance;
////
////import com.pqsoft.baseScheme.service.BaseSchemeService;
////import com.pqsoft.bpm.JBPM;
////import com.pqsoft.buyBack.action.ExcelTest;
////import com.pqsoft.buyBack.service.BuyBackService;
////import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
////import com.pqsoft.dataDictionary.service.DataDictionaryService;
////import com.pqsoft.pay.service.PayTaskService;
////import com.pqsoft.project.purchase.service.PurchaseService;
////import com.pqsoft.project.service.ProjectContractManagerService;
////import com.pqsoft.skyeye.VM;
////import com.pqsoft.skyeye.api.Action;
////import com.pqsoft.skyeye.api.Dao;
////import com.pqsoft.skyeye.api.Page;
////import com.pqsoft.skyeye.api.Reply;
////import com.pqsoft.skyeye.api.ReplyAjax;
////import com.pqsoft.skyeye.api.ReplyAjaxPage;
////import com.pqsoft.skyeye.api.ReplyHtml;
////import com.pqsoft.skyeye.api.ReplyJson;
////import com.pqsoft.skyeye.api.ReplyMobile;
////import com.pqsoft.skyeye.api.SkyEye;
////import com.pqsoft.skyeye.dev.aDev;
////import com.pqsoft.skyeye.exception.ActionException;
////import com.pqsoft.skyeye.rbac.OpLog;
////import com.pqsoft.skyeye.rbac.api.Security;
////import com.pqsoft.skyeye.rbac.api.aAuth;
////import com.pqsoft.skyeye.rbac.api.aPermission;
////import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
////import com.pqsoft.util.DateUtil;
////import com.pqsoft.util.ReplyExcel;
////import com.pqsoft.util.SecuUtil;
////import com.pqsoft.util.Util;
////
////public class PurchaseAction extends Action {
////	private final Logger logger = Logger.getLogger(this.getClass());
////	public VelocityContext context = new VelocityContext();
////	Map<String, Object> m = null;
////	PurchaseService service = new PurchaseService();
////
////	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
////	@aAuth(type = aAuthType.ALL)
////	private void getPageParameter() {
////		m = new HashMap<String, Object>();
////		Enumeration<?> en = SkyEye.getRequest().getParameterNames();
////		while (en.hasMoreElements()) {
////			Object enN = en.nextElement();
////			String para = SkyEye.getRequest().getParameter(enN.toString()).trim();
////			m.put(enN.toString(), para);
////		}
////		m.put("USERNAME", Security.getUser().getName());
////		m.put("USERCODE", Security.getUser().getCode());
////		m.put("USERID", Security.getUser().getId());
////	}
////
////	@Override
////	public Reply execute() {
////		return null;
////	}
////
////	@aPermission(name = { "项目管理", "项目留购", "留购申请" })
////	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
////	@aAuth(type = aAuthType.USER)
////	public Reply getDataList() {
////		this.getPageParameter();
////		context.put("PContext", m);
////		return new ReplyHtml(VM.html("project/purchase/purchase_Manager.vm", context));
////	}
////
////	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
////	@aAuth(type = aAuthType.LOGIN)
////	public Reply pageAjax() {
////		Map<String, Object> param = _getParameters();
////		Page page = service.queryPage(param);
////		return new ReplyAjaxPage(page);
////	}
////	
////	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
////	@aAuth(type = aAuthType.LOGIN)
////	public Reply queryContentByPROJID() {
////		this.getPageParameter();
////		List list = new ArrayList();
////		if (m != null && m.get("PRODUCT_ID") != null) {
////			list = service.queryContentByPROJID(m);
////		}
////		return new ReplyJson(JSONArray.fromObject(list)).addOp(new OpLog("项目管理", "项目留购", "项目编号查询"));
////	}
////	
////	@aAuth(type = aAuth.aAuthType.USER)
////	@aPermission(name ={"项目管理","项目留购","留购申请","填写留购表单"})
////	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
////	public Reply applyPurchasePage(){
////		Map<String, Object> map = _getParameters();
////
////		// 项目信息
////		Map projectMap = service.queryProjectById(map);
////		map.putAll(projectMap);
////
////		// 设备信息
////		List eqlist = service.queryEqByProjectID(map);
////
////		// 担保人信息
////		List GuaranList = service.queryGuaranByProjectID(map);
////
////		List<Map<String, Object>> listSechme = service.querySechmeByProjectID(map);
////
////		// 还款计划
////		// 查询支付表id在修改的时候使用
////		int PAY_ID = service.queryIdByProjectId(map.get("PROJECT_ID") + "");
////		context.put("PAY_ID", PAY_ID);
////		// 方案信息(商务政策)
////		if (listSechme.size() <= 0 && map.get("POB_ID") != null) {
////			List<Map<String, Object>> list = new DataDictionaryService().queryDataDictionaryByScheme("政策元素");
////			for (Map<String, Object> mapItem : list) {
////				List lst = new DataDictionaryMemcached().get(mapItem.get("FLAG") + "");
////				mapItem.put("LIST", lst);
////			}
////			context.put("DATALIST", list);
////			Map schemeMap = new HashMap();
////			schemeMap.put("SCHEME_NAME", map.get("POB_ID"));
////			schemeMap.put("STATUS", 0);
////			BaseSchemeService schemeService = new BaseSchemeService();
////			context.put("SCHEMEDETAIL", schemeService.doSelectSchemeDetailByName(schemeMap));
////			context.put("viewType", "project_scheme_view1");
////
////			if (map.get("POB_ID") != "" && !map.get("POB_ID").equals("-1")) {
////				Map mapfee = new HashMap();
////				BaseSchemeService baseSchService = new BaseSchemeService();
////				mapfee.put("SCHEME_NAME", map.get("POB_ID"));
////				map.put("feeCount", baseSchService.doSelectBaseSchemeFeeRateCount(mapfee));
////			}
////		} else {
////			List<Map<String, Object>> list = service.querySechmeByAllDate(map);
////			for (Map<String, Object> mapItem : list) {
////				List lst = new DataDictionaryMemcached().get(mapItem.get("FLAG") + "");
////				mapItem.put("LIST", lst);
////			}
////			context.put("DATALIST", list);
////			context.put("SCHEMEDETAIL", listSechme);
////		}
////
////		context.put("eqList", eqlist);
////		context.put("GuaranList", GuaranList);
////		context.put("param", map);
////		if (Security.isMobile()) {
////			return new ReplyMobile(context);
////		} else{
////			return new ReplyHtml(VM.html("project/purchase/purchaseProjectView.vm", context));
////		}
////	}
////	
////	/**
////	 * 适用留购流程
////	 * 生成所有权转移证书
////	 */
////	@SuppressWarnings("unchecked")
////	@aAuth(type = aAuthType.LOGIN)
////	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
////	public Reply ExcelFile() {
////		Map<String, Object> param = _getParameters();
////		param.putAll(service.selectFileData(param));
////		Double LEAVE = 0.00;// 留购价
////		
////		if (param.containsKey("LEAVE") && !param.get("LEAVE").equals("")) {
////			LEAVE = Double.parseDouble(param.get("LEAVE").toString());
////		}
////		param.put("LEAVE", LEAVE);// 留购价
////		param.put("FILE_NAME", param.get("SUPPLIER_NAMES")+""+param.get("PRO_CODE")+param.get("FILE_NAME"));
////		return new ExcelTest(param);
////	}
////	
////	@SuppressWarnings("unchecked")
////	@aAuth(type = aAuth.aAuthType.LOGIN)
////	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
////	public Reply exportExcel(){
////		m = _getParameters();
////		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
////		m.remove("searchParams");
////		m.putAll(json);
////		//加入操作人信息
////		SecuUtil.putUserInfo(m);
////		return new ReplyExcel(service.exportData(m),"项目留购申请"+DateUtil.getSysDate()+Math.abs(new Random(10000).nextInt())+".xls");
////	}
////	
////	@SuppressWarnings("unchecked")
////	@aAuth(type = aAuth.aAuthType.LOGIN)
////	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
////	public Reply exportSearchExcel(){
////		m = _getParameters();
////		JSONObject json = JSONObject.fromObject(m.get("searchParams"));
////		m.remove("searchParams");
////		m.putAll(json);
////		//加入操作人信息
////		SecuUtil.putUserInfo(m);
////		return new ReplyExcel(service.exportSearchData(m),"项目留购查询"+DateUtil.getSysDate()+Math.abs(new Random(10000).nextInt())+".xls");
////	}
////	
////	@aAuth(type = aAuth.aAuthType.USER)
////	@aPermission(name ={"项目管理","项目留购","留购申请","发起流程(按钮)"})
////	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
////	public Reply appPurchaseWF(){
////		Map<String, Object> map = _getParameters();
////
////		JSONArray jsonArray = JSONArray.fromObject(map.get("param"));
////		//发起流程 需要确定是否需要展示多个流程图供选择
////		List<String> list = JBPM.getDeploymentListByModelName("留购");
////		String pid = null;
////		if (list.size() > 0)
////			pid = list.get(0);
////		else
////			throw new ActionException("没有找到流程图");
////
////		Map<String, Object> jmap = new HashMap<String, Object>();
////		jmap.put("PROJECT_ID", map.get("PROJECT_ID"));
////		jmap.put("SUPPLIER_ID", map.get("SUP_ID"));
////		jmap.put("PRO_CODE", map.get("PRO_CODE"));
////		ProcessInstance instance = JBPM.startProcessInstanceById(pid, map.get("PROJECT_ID").toString(), map.get("CLIENT_ID").toString(), null, jmap);
////		//插入FIL_PROJECT_END_PURCHASE留购表数据
////		Map<String, Object> mapData = new HashMap<String, Object>();
////		mapData.put("PROJECT_ID", map.get("PROJECT_ID"));
////		mapData.put("PRO_CODE", map.get("PRO_CODE").toString());
////		mapData.put("SUP_ID", map.get("SUP_ID").toString());
////		mapData.put("REMARK", map.get("REMARK").toString());
////		mapData.put("USERNAME", Security.getUser().getName());
////		service.insertProjectPurchase(mapData);
////		{
////			//付玉龙
////			String JBPM_ID = instance.getId();//流程id
////			new ProjectContractManagerService().doAddProjectContractList(JBPM_ID);//保存欲归档文件
////		}
////		return new ReplyAjax(true).addOp(new OpLog("项目管理", "项目留购", "发起流程(按钮)：" + map.get("PRO_CODE")));
////	}
////	
////	@aAuth(type = aAuth.aAuthType.LOGIN)
////	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
////	public Reply purchaseShow() {
////		Map<String, Object> map = _getParameters();
////
////		// 项目信息
////		Map projectMap = service.queryProjectById(map);
////		map.putAll(projectMap);
////
////		// 设备信息
////		List eqlist = service.queryEqByProjectID(map);
////		// 担保人信息
////		List GuaranList = service.queryGuaranByProjectID(map);
////		List<Map<String, Object>> listSechme = service.querySechmeByProjectID(map);
////		// 还款计划
////		// 查询支付表id在修改的时候使用
////		int PAY_ID = service.queryIdByProjectId(map.get("PROJECT_ID") + "");
////		context.put("PAY_ID", PAY_ID);
////		// 方案信息(商务政策)
////		if (listSechme.size() <= 0 && map.get("POB_ID") != null) {
////			List<Map<String, Object>> list = new DataDictionaryService().queryDataDictionaryByScheme("政策元素");
////			for (Map<String, Object> mapItem : list) {
////				List lst = new DataDictionaryMemcached().get(mapItem.get("FLAG") + "");
////				mapItem.put("LIST", lst);
////			}
////			context.put("DATALIST", list);
////			Map schemeMap = new HashMap();
////			schemeMap.put("SCHEME_NAME", map.get("POB_ID"));
////			schemeMap.put("STATUS", 0);
////			BaseSchemeService schemeService = new BaseSchemeService();
////			context.put("SCHEMEDETAIL", schemeService.doSelectSchemeDetailByName(schemeMap));
////			context.put("viewType", "project_scheme_view1");
////
////			if (map.get("POB_ID") != "" && !map.get("POB_ID").equals("-1")) {
////				Map mapfee = new HashMap();
////				BaseSchemeService baseSchService = new BaseSchemeService();
////				mapfee.put("SCHEME_NAME", map.get("POB_ID"));
////				map.put("feeCount", baseSchService.doSelectBaseSchemeFeeRateCount(mapfee));
////			}
////		} else {
////			List<Map<String, Object>> list = service.querySechmeByAllDate(map);
////			for (Map<String, Object> mapItem : list) {
////				List lst = new DataDictionaryMemcached().get(mapItem.get("FLAG") + "");
////				mapItem.put("LIST", lst);
////			}
////			context.put("DATALIST", list);
////			context.put("SCHEMEDETAIL", listSechme);
////		}
////
////		context.put("eqList", eqlist);
////		context.put("GuaranList", GuaranList);
////		context.put("param", map);
////		if (Security.isMobile()) {
////			return new ReplyMobile(context);
////		} else{
////			return new ReplyHtml(VM.html("project/purchase/purchaseProjectViewUpload.vm", context));
////		}
////	}
////	
////	/**
////	 * 
////	 * @param
////	 * @author 付玉龙  fuyulong47@foxmail.com
////	 * @date 2013-12-3  下午03:16:44
////	 */
////	@aAuth(type = aAuthType.LOGIN)
////	@aDev(code = "fuyulong", email = "fuyulong47@foxmail.com", name = "付玉龙")
////	public Reply purchaseShowZJL() {
////		Map<String, Object> map = _getParameters();
////		
////		// 项目信息
////		Map projectMap = service.queryProjectById(map);
////		map.putAll(projectMap);
////		
////		// 设备信息
////		List eqlist = service.queryEqByProjectID(map);
////		// 担保人信息
////		List GuaranList = service.queryGuaranByProjectID(map);
////		List<Map<String, Object>> listSechme = service.querySechmeByProjectID(map);
////		// 还款计划
////		// 查询支付表id在修改的时候使用
////		int PAY_ID = service.queryIdByProjectId(map.get("PROJECT_ID") + "");
////		context.put("PAY_ID", PAY_ID);
////		// 方案信息(商务政策)
////		if (listSechme.size() <= 0 && map.get("POB_ID") != null) {
////			List<Map<String, Object>> list = new DataDictionaryService().queryDataDictionaryByScheme("政策元素");
////			for (Map<String, Object> mapItem : list) {
////				List lst = new DataDictionaryMemcached().get(mapItem.get("FLAG") + "");
////				mapItem.put("LIST", lst);
////			}
////			context.put("DATALIST", list);
////			Map schemeMap = new HashMap();
////			schemeMap.put("SCHEME_NAME", map.get("POB_ID"));
////			schemeMap.put("STATUS", 0);
////			BaseSchemeService schemeService = new BaseSchemeService();
////			context.put("SCHEMEDETAIL", schemeService.doSelectSchemeDetailByName(schemeMap));
////			context.put("viewType", "project_scheme_view1");
////			
////			if (map.get("POB_ID") != "" && !map.get("POB_ID").equals("-1")) {
////				Map mapfee = new HashMap();
////				BaseSchemeService baseSchService = new BaseSchemeService();
////				mapfee.put("SCHEME_NAME", map.get("POB_ID"));
////				map.put("feeCount", baseSchService.doSelectBaseSchemeFeeRateCount(mapfee));
////			}
////		} else {
////			List<Map<String, Object>> list = service.querySechmeByAllDate(map);
////			for (Map<String, Object> mapItem : list) {
////				List lst = new DataDictionaryMemcached().get(mapItem.get("FLAG") + "");
////				mapItem.put("LIST", lst);
////			}
////			context.put("DATALIST", list);
////			context.put("SCHEMEDETAIL", listSechme);
////		}
////		
////		context.put("eqList", eqlist);
////		context.put("GuaranList", GuaranList);
////		context.put("param", map);
////		if (Security.isMobile()) {
////			return new ReplyMobile(context);
////		} else{
////			return new ReplyHtml(VM.html("project/purchase/purchaseProjectViewUploadZJL.vm", context));
////		}
////	}
////	
////	@aAuth(type = aAuth.aAuthType.LOGIN)
////	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
////	public Reply purchaseShowReadonly() {
////		Map<String, Object> map = _getParameters();
////
////		// 项目信息
////		Map projectMap = service.queryProjectById(map);
////		map.putAll(projectMap);
////
////		// 设备信息
////		List eqlist = service.queryEqByProjectID(map);
////		// 担保人信息
////		List GuaranList = service.queryGuaranByProjectID(map);
////		List<Map<String, Object>> listSechme = service.querySechmeByProjectID(map);
////		// 还款计划
////		// 查询支付表id在修改的时候使用
////		int PAY_ID = service.queryIdByProjectId(map.get("PROJECT_ID") + "");
////		context.put("PAY_ID", PAY_ID);
////		// 方案信息(商务政策)
////		if (listSechme.size() <= 0 && map.get("POB_ID") != null) {
////			List<Map<String, Object>> list = new DataDictionaryService().queryDataDictionaryByScheme("政策元素");
////			for (Map<String, Object> mapItem : list) {
////				List lst = new DataDictionaryMemcached().get(mapItem.get("FLAG") + "");
////				mapItem.put("LIST", lst);
////			}
////			context.put("DATALIST", list);
////			Map schemeMap = new HashMap();
////			schemeMap.put("SCHEME_NAME", map.get("POB_ID"));
////			schemeMap.put("STATUS", 0);
////			BaseSchemeService schemeService = new BaseSchemeService();
////			context.put("SCHEMEDETAIL", schemeService.doSelectSchemeDetailByName(schemeMap));
////			context.put("viewType", "project_scheme_view1");
////
////			if (map.get("POB_ID") != "" && !map.get("POB_ID").equals("-1")) {
////				Map mapfee = new HashMap();
////				BaseSchemeService baseSchService = new BaseSchemeService();
////				mapfee.put("SCHEME_NAME", map.get("POB_ID"));
////				map.put("feeCount", baseSchService.doSelectBaseSchemeFeeRateCount(mapfee));
////			}
////		} else {
////			List<Map<String, Object>> list = service.querySechmeByAllDate(map);
////			for (Map<String, Object> mapItem : list) {
////				List lst = new DataDictionaryMemcached().get(mapItem.get("FLAG") + "");
////				mapItem.put("LIST", lst);
////			}
////			context.put("DATALIST", list);
////			context.put("SCHEMEDETAIL", listSechme);
////		}
////
////		context.put("eqList", eqlist);
////		context.put("GuaranList", GuaranList);
////		context.put("param", map);
////		if (Security.isMobile()) {
////			return new ReplyMobile(context);
////		} else{
////			return new ReplyHtml(VM.html("project/purchase/purchaseProjectViewReadonly.vm", context));
////		}
////	}
////	
////	@aPermission(name ={"项目管理","项目留购","留购查询"})
////	@aAuth(type = aAuth.aAuthType.USER)
////	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
////	public Reply purchaseManagedSearch() {
////		this.getPageParameter();
////		context.put("PContext", m);
////		return new ReplyHtml(VM.html("project/purchase/purchase_Managed_Search.vm", context));
////	}
////	
////	@aDev(code = "6192", email = "fudg@strongflc.com", name = "Donzell")
////	@aAuth(type = aAuthType.LOGIN)
////	public Reply pageSearchAjax() {
////		Map<String, Object> param = _getParameters();
////		Page page = service.querySearchPage(param);
////		return new ReplyAjaxPage(page);
////	}
////	
////}
