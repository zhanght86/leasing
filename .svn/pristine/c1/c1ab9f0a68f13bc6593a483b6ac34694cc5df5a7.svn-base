package com.pqsoft.leaseApplication.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.suppliers.service.SuppliersService;
import com.pqsoft.baseScheme.service.BaseSchemeService;
import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.crm.service.splitPaymentService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.leaseApplication.service.LeaseApplicationService;
import com.pqsoft.pay.service.PayTaskService;
import com.pqsoft.payment.service.paymentService;
import com.pqsoft.project.service.projectService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.util.UTIL;
import com.pqsoft.util.DateSiteUtil;
import com.pqsoft.util.MyNumberTool;
import com.pqsoft.util.StringUtils;
import com.pqsoft.util.Util;

public class LeaseApplicationAction extends Action {

	private String basePath = "leaseApplication/";
	LeaseApplicationService service = new LeaseApplicationService();

	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	}

	@aPermission(name = { "合同管理", "起租日管理", "拆分" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply queryEQList() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();

		// 项目信息
		Map projectMap = service.queryProjectById(map);
		context.put("param", projectMap);

		// 设备分组信息
		context.put("rowList", service.queryEqCountByProjectID(map));

		// 设备信息
		List eqlist = service.queryEqListByProjectId(map);
		context.put("eqList", eqlist);

		// 还款日
		List rePayMentList = new DataDictionaryService()
				.queryDataDictionaryByScheme("还款日");
		context.put("rePayMentList", rePayMentList);

		return new ReplyHtml(VM.html(basePath + "queryEqListApp.vm", context));
	}

	@aPermission(name = { "项目管理", "项目一览", "核心要件", "起租申请" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply queryEQListProject() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		projectService pjservice = new projectService();
		try {
			map.put("MANAGER_ID", Security.getUser().getOrg().getPlatformId());
			map.put("MANAGER_NAME", Security.getUser().getOrg().getPlatform());
		} catch (Exception e) {
			throw new ActionException("您不输入公司平台内部人员");
		}
		if (StringUtils.isNotBlank(Security.getUser().getOrg().getSuppId())) {
			// 经销商登陆立项
			Map<String, Object> mapSup = new HashMap<String, Object>();
			mapSup.put("SUP_ID", Security.getUser().getOrg().getSuppId());// 通过经销商查询出默认的省市
			mapSup.put("SUP_TYPE", "1");// 经销商标示
			Map<String, Object> mapAreaSup = pjservice.queryAreaSupMap(mapSup);
			context.put("mapAreaSup", mapAreaSup);

			// 通过省查询市
			List<Object> cityMap = pjservice.queryManagerArea(Security
					.getUser().getOrg().getPlatformId(),
					mapAreaSup.get("PROV_ID") + "", 3);
			context.put("cityList", cityMap);

			Map<String, Object> mm = new HashMap<String, Object>();
			mm.put("SUP_ID", Security.getUser().getOrg().getSuppId());
			mm.put("SUP_NAME", Security.getUser().getOrg().getSuppName());
			List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
			lst.add(mm);
			context.put("suppliersList", lst);
		} else if (StringUtils
				.isNotBlank(Security.getUser().getOrg().getSpId())) {
			// SP登陆立项
			Map<String, Object> mapSup = new HashMap<String, Object>();
			mapSup.put("SUP_ID", Security.getUser().getOrg().getSpId());// 通过SP查询出默认的省市
			mapSup.put("SUP_TYPE", "2");// SP标示
			Map<String, Object> mapAreaSup = pjservice.queryAreaSupMap(mapSup);
			context.put("mapAreaSup", mapAreaSup);

			// 通过省查询市
			List<Object> cityMap = pjservice.queryManagerArea(Security
					.getUser().getOrg().getPlatformId(),
					mapAreaSup.get("PROV_ID") + "", 3);
			context.put("cityList", cityMap);

			map.put("SPID", Security.getUser().getOrg().getSpId());
			SuppliersService suppService = new SuppliersService();
			context.put("suppliersList", suppService.querySuppByCom(map));
		} else {
			// 通过平台查经销商
			SuppliersService suppService = new SuppliersService();
			context.put("suppliersList", suppService.querySuppByCom(map));
		}
		// 项目信息
		Map<String, Object> projectMap = service.queryProjectById(map);
		// 方案信息
		List<Map<String, Object>> mlist = service.getScheme(map);
		Map<String, Object> m = mlist.get(0);
		context.put("schemeBase", m);
		/************************ 查询商务政策对应的利率规则 **********************************************/
		BaseSchemeService bsService = new BaseSchemeService();
		context.put("dicTag", Util.DICTAG);
		context.put("MyNumberTool", new MyNumberTool());
		context.put(
				"SCHEMEDETAIL",
				bsService.getSchemeClob(null, m.get("PROJECT_ID") + "",
						m.get("SCHEME_ROW_NUM") + ""));
		context.put(
				"schemeBase",
				bsService.getSchemeBaseInfoByProjectId(
						m.get("PROJECT_ID") + "", null,
						m.get("SCHEME_ROW_NUM") + "").get(0));
		/************************ 查询商务政策对应的利率规则 King 2014-08-21 **********************************************/
		BaseSchemeService baseSchService = new BaseSchemeService();
		Map<String, Object> maprate = new HashMap<String, Object>();
		maprate.put("SCHEME_CODE", m.get("SCHEME_ID"));
		// 年利率
		m.put("rateList", baseSchService.doSelectBaseSchemeYearRate(maprate));
		// 手续费
		m.put("feeList", baseSchService.doSelectBaseSchemeFeeRate(maprate));
		/**********************************************************************/
		Map<String, Object> baseMap = pjservice.queryInfoByEqBase(m);
		PayTaskService pay = new PayTaskService();
		int PAY_ID = pjservice.queryIdByProjectIdRowNum(m);
		Map<String, Object> payMap = new HashMap<String, Object>();
		payMap.put("ID", PAY_ID);
		baseMap.put("ID", PAY_ID);
		List<Map<String, Object>> listDetail = pay
				.doShowRentPlanDetailScheme(payMap);
		baseMap.put("ONEMONEY", (listDetail.get(0)).get("ZJ") + "");
		context.put("detailList", listDetail);
		context.put("FORMAT", UTIL.FORMAT);
		context.put("ywlx", new SysDictionaryMemcached().get("业务类型"));
		context.put("baseMap", baseMap);
		m.putAll(projectMap);
		m.put("LEASE_TOPRIC_SY", map.get("LEASE_TOPRIC_SY"));
		context.put("param", m);
		System.out.println("---------------baseMap=" + baseMap);
		context.put("ZLZQ", new SysDictionaryMemcached().get("租赁周期"));
		context.put("FYLX", new DataDictionaryMemcached().get("其他费用类型"));
		/************************ end **********************************************/
		// // 单位
		List unitList = (List) new DataDictionaryMemcached().get("设备单位");
		context.put("unitList", unitList);
		// 设备分组信息
		List<Map<String, Object>> rowlist = service
				.queryEqCountByProjectID(map);
		context.put("rowList", rowlist);

		// 设备信息
		map.put("SCHEME_ID", projectMap.get("SCHEME_ID"));
		List<Map<String, Object>> eqlistall = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> companyList = new ArrayList<Map<String, Object>>();// 厂商
		List<Map<String, Object>> thingList = new ArrayList<Map<String, Object>>();// 品牌
		List<Map<String, Object>> productList = new ArrayList<Map<String, Object>>();// 车系
		List<Map<String, Object>> specList = new ArrayList<Map<String, Object>>();// 车型
		Map<String, Object> eqmap = new HashMap<String, Object>();
		List<Map<String, Object>> eqlist = service.queryEqListByProjectId(map);
		for (int i = 0; i < eqlist.size(); i++) {
			eqmap = eqlist.get(i);
			companyList = service.companyList(eqmap);
			thingList = service.thingList(eqmap);
			productList = service.productList(eqmap);
			specList = service.specList(eqmap);
			eqmap.put("companyList", companyList);
			eqmap.put("thingList", thingList);
			eqmap.put("productList", productList);
			eqmap.put("specList", specList);
			eqlistall.add(eqmap);
		}
		context.put("eqList", eqlistall);

		// 还款日
		List rePayMentList = new DataDictionaryService()
				.queryDataDictionaryByScheme("还款日");
		context.put("rePayMentList", rePayMentList);

		return new ReplyHtml(VM.html(basePath + "queryEqListApp.vm", context));
	}

	//
	// @aPermission(name = { "项目管理", "项目一览", "核心要件","支付表","起租" })
	// @aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	// @aAuth(type = aAuthType.USER)
	// public Reply queryEQListProjectLH()
	// {
	// VelocityContext context=new VelocityContext();
	// LeaseApplicationService LAservice=new LeaseApplicationService();
	// projectService service=new projectService();
	// Map<String, Object> map = _getParameters();
	// // 项目信息
	// Map projectMap = service.queryProjectById(map);
	// map.putAll(projectMap);
	//
	// if("8".equals(projectMap.get("PLATFORM_TYPE")+""))
	// {
	// List LHSQFSLIST=new
	// DataDictionaryService().queryDataDictionaryByScheme("联合收取方式");
	// context.put("LHSQFSLIST", LHSQFSLIST);
	// //查询联合租赁信息
	// List list=service.getFLByProjectId(map);
	// context.put("FL_LIST", list);
	//
	// context.put("FLINFO_LIST", service.getFLInfo());
	// }
	//
	// // 设备分组信息
	// List rowlist = service.queryEqCountByProjectID(map);
	// context.put("rowList", rowlist);
	// // 单位
	// List unitList = (List) new DataDictionaryMemcached().get("设备单位");
	// context.put("unitList", unitList);
	//
	//
	// // 设备信息（商务政策）
	// List eqlist = LAservice.queryEqListByProjectId(map);
	// context.put("eqList", eqlist);
	//
	// context.put("param", map);
	// String PLATFORM_TYPE = map.get("PLATFORM_TYPE") + "";
	//
	// // 扣款类型
	// String PROJECT_MODEL = map.get("PROJECT_MODEL") + "";
	// List final_Type_List = new ArrayList();
	// Map finalMap2 = new HashMap();
	// finalMap2.put("FINAL_TYPE", null);
	// finalMap2.put("FINAL_NAME", "");
	// final_Type_List.add(finalMap2);
	//
	// Map finalMap = new HashMap();
	// finalMap.put("FINAL_TYPE", "0");
	// finalMap.put("FINAL_NAME", "自有客户");
	// final_Type_List.add(finalMap);
	//
	// Map finalMap1 = new HashMap();
	// finalMap1.put("FINAL_TYPE", "1");
	//
	// finalMap1.put("FINAL_NAME", "终端客户");
	// final_Type_List.add(finalMap1);
	// context.put("final_Type_List", final_Type_List);
	//
	// // 发票开具类型
	// List<Map<String, Object>> invoice_target_type = new
	// DataDictionaryService()
	// .queryDataDictionaryByScheme("发票_对象_类型");
	// context.put("invoice_target_type", invoice_target_type);
	// // 租赁周期
	// context.put("LEASE_TERM", new SysDictionaryMemcached().get("租赁周期"));
	//
	// //先查询有没有生成支付表，如果有生成，则不让其修改出资金额
	// int num=service.getPayByProjectID(map);
	// context.put("num", num);
	// return new ReplyHtml(VM.html(basePath+"project_scheme_show.vm",
	// context));
	// }

	@aPermission(name = { "合同管理", "起租日管理", "拆分" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply queryEQListByPay() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();

		// 项目信息
		Map projectMap = service.queryProjectById(map);
		context.put("param", projectMap);

		// 列出所有支付表信息
		context.put("PAYLIST", service.queryPayListByEq(map));

		// 还款日
		List rePayMentList = new DataDictionaryService()
				.queryDataDictionaryByScheme("还款日");
		context.put("rePayMentList", rePayMentList);

		return new ReplyHtml(VM.html(basePath + "queryPayListApp.vm", context));
	}

	@aPermission(name = { "合同管理", "起租日管理", "拆分" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply qzAppListLH() {
		VelocityContext context = new VelocityContext();
		LeaseApplicationService LAservice = new LeaseApplicationService();
		projectService service = new projectService();
		Map<String, Object> map = _getParameters();
		// 项目信息
		Map projectMap = service.queryProjectById(map);
		map.putAll(projectMap);

		if ("8".equals(projectMap.get("PLATFORM_TYPE") + "")) {
			List LHSQFSLIST = new DataDictionaryService()
					.queryDataDictionaryByScheme("联合收取方式");
			context.put("LHSQFSLIST", LHSQFSLIST);
			// 查询联合租赁信息
			List list = service.getFLByProjectId(map);
			context.put("FL_LIST", list);

			context.put("FLINFO_LIST", service.getFLInfo());
		}

		// 设备分组信息
		List rowlist = service.queryEqCountByProjectID(map);
		context.put("rowList", rowlist);
		// 单位
		List unitList = (List) new DataDictionaryMemcached().get("设备单位");
		context.put("unitList", unitList);

		// 设备信息（商务政策）
		List eqlist = LAservice.queryEqListByProjectId(map);
		context.put("eqList", eqlist);

		context.put("param", map);
		String PLATFORM_TYPE = map.get("PLATFORM_TYPE") + "";

		// 扣款类型
		String PROJECT_MODEL = map.get("PROJECT_MODEL") + "";
		List final_Type_List = new ArrayList();
		Map finalMap2 = new HashMap();
		finalMap2.put("FINAL_TYPE", null);
		finalMap2.put("FINAL_NAME", "");
		final_Type_List.add(finalMap2);

		Map finalMap = new HashMap();
		finalMap.put("FINAL_TYPE", "0");
		finalMap.put("FINAL_NAME", "自有客户");
		final_Type_List.add(finalMap);

		Map finalMap1 = new HashMap();
		finalMap1.put("FINAL_TYPE", "1");

		finalMap1.put("FINAL_NAME", "终端客户");
		final_Type_List.add(finalMap1);
		context.put("final_Type_List", final_Type_List);

		// 发票开具类型
		List<Map<String, Object>> invoice_target_type = new DataDictionaryService()
				.queryDataDictionaryByScheme("发票_对象_类型");
		context.put("invoice_target_type", invoice_target_type);
		// 租赁周期
		context.put("LEASE_TERM", new SysDictionaryMemcached().get("租赁周期"));

		// 先查询有没有生成支付表，如果有生成，则不让其修改出资金额
		int num = service.getPayByProjectID(map);
		context.put("num", num);

		// 还款日
		List rePayMentList = new DataDictionaryService()
				.queryDataDictionaryByScheme("还款日");
		context.put("rePayMentList", rePayMentList);

		return new ReplyHtml(VM.html(basePath + "project_scheme_show.vm",
				context));
	}

	// @aPermission(name = { "项目管理", "项目一览", "核心要件","支付表","新建" })
	// @aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	// @aAuth(type = aAuthType.USER)
	// public Reply queryEQCrditList()
	// {
	// //大项目就一个方案
	// Map<String, Object> map = _getParameters();
	// VelocityContext context=new VelocityContext();
	//
	// // 项目信息
	// Map projectMap = service.queryProjectById(map);
	// context.put("param", projectMap);
	//
	// //设备分组信息
	// List rowlist = service.queryEqCountByProjectIDDXM(map);
	// context.put("rowList", rowlist);
	//
	// // 设备信息
	// List eqlist = service.queryEqListByProjectIdDXM(map);
	// context.put("eqList", eqlist);
	//
	// return new ReplyHtml(VM.html(basePath+"queryEqListDXMApp.vm", context));
	// }

	@aPermission(name = { "合同管理", "起租日管理", "测试" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply calculation() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		// //处理两个日期
		// Map da=Dao.selectOne("PayTask.queryNextDate", map);
		// map.put("REPAYMENT_DATE_CHANGE", da.get("REPAYMENT_DATE_CHANGE"));
		// 页面中所用信息
		context = service.schemeUtil(context, map);
		return new ReplyHtml(VM.html(basePath + "calculationStartApp.vm",
				context));
	}

	// @aPermission(name = { "项目管理", "项目一览", "核心要件","支付表","测算" })
	// @aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	// @aAuth(type = aAuthType.LOGIN)
	// public Reply calculationDXM(){
	// Map<String, Object> map = _getParameters();
	// VelocityContext context=new VelocityContext();
	// //页面中所用信息
	// context=service.schemeUtilDXM(context, map);
	// return new ReplyHtml(VM.html(basePath+"calculationStartDXMApp.vm",
	// context));
	// }

	@aPermission(name = { "合同管理", "起租日管理", "测试" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.USER)
	public Reply PAYVIEW_DATE() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		// //页面中所用信息
		// //处理两个日期
		// Map da=Dao.selectOne("PayTask.queryNextDate", map);
		// map.put("REPAYMENT_DATE_CHANGE", da.get("REPAYMENT_DATE_CHANGE"));
		context = service.rentPlanUtilData(context, map);
		return new ReplyHtml(VM.html(basePath + "calculation_DXM_VIEW.vm",
				context));
	}

	@aPermission(name = { "项目管理", "起租申请", "发起申请流程" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply startJbpmQzSq() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		String PLATFORM_TYPE = "1";// 业务类型----默认直租
		if (param != null && param.get("PLATFORM_TYPE") != null
				&& !param.get("PLATFORM_TYPE").equals("")) {
			PLATFORM_TYPE = param.get("PLATFORM_TYPE").toString();
		}
		Map JBPMMAP = new HashMap();
		JBPMMAP.put("EQ_IDS", param.get("EQ_IDS"));
		// 修改设备状态为流程申请中
		String PAY_ID = service.updateEqStatus(param);
		JBPMMAP.put("PAY_ID", PAY_ID);
		// List<String>
		// list=JBPM.getDeploymentListByModelName("Lease_application");
		List<String> list = JBPM.getDeploymentListByModelName("起租申请流程",
				param.get("PLATFORM_TYPE") + "", Security.getUser().getOrg()
						.getPlatformId());
		String jbpm_id = JBPM.startProcessInstanceById(list.get(0),
				param.get("PROJECT_ID") + "", param.get("CUST_ID") + "", "",
				JBPMMAP).getId();
		String nextCode = new TaskService().getAssignee(jbpm_id);
		context.put("JBPM_ID", jbpm_id);
		context.put("nextCode", nextCode);
		return new ReplyHtml(VM.html(basePath + "jbpmSuccess.vm", context));
	}

	@aPermission(name = { "项目管理", "起租申请", "发起申请流程" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply startJbpmQzSqLH() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		String PLATFORM_TYPE = "1";// 业务类型----默认直租

		Map JBPMMAP = new HashMap();
		// 查询设备ID拼接起来放进流程
		service.queryEqIdsByProjectIDRow(param);
		JBPMMAP.put("EQ_IDS", param.get("EQ_IDS"));
		// 修改设备状态为流程申请中
		String PAY_ID = service.updateEqStatusLH(param);
		JBPMMAP.put("PAY_ID", PAY_ID);
		// List<String>
		// list=JBPM.getDeploymentListByModelName("Lease_application");
		List<String> list = JBPM.getDeploymentListByModelName("起租申请流程",
				param.get("PLATFORM_TYPE") + "", Security.getUser().getOrg()
						.getPlatformId());
		String jbpm_id = JBPM.startProcessInstanceById(list.get(0),
				param.get("PROJECT_ID") + "", param.get("CUST_ID") + "", "",
				JBPMMAP).getId();
		String nextCode = new TaskService().getAssignee(jbpm_id);
		context.put("JBPM_ID", jbpm_id);
		context.put("nextCode", nextCode);
		return new ReplyHtml(VM.html(basePath + "jbpmSuccess.vm", context));
	}

	@aPermission(name = { "项目管理", "起租申请", "发起申请流程" })
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply startCreditSq() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		String PLATFORM_TYPE = "1";// 业务类型----默认直租
		if (param != null && param.get("PLATFORM_TYPE") != null
				&& !param.get("PLATFORM_TYPE").equals("")) {
			PLATFORM_TYPE = param.get("PLATFORM_TYPE").toString();
		}

		// 生成支付表
		String PAY_ID = service.updateEqStatusCredit(param);

		context.put("PRO_CODE", param.get("PRO_CODE"));

		return new ReplyHtml(VM.html("project/project_Info_Main.vm", context));
	}

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply leaseApplication_view_JBPM() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		// 页面中所用信息
		context = service.rentPlanUtil(context, map);
		return new ReplyHtml(VM.html(basePath + "calculation_App_JBPM_VIEW.vm",
				context));
	}

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply leaseApplication_CreateSendNotice() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();

		// 首付款情况
		context = service.fundInfo(map, context);

		// 查询发货信息
		Map contract = service.queryContractInfo(map);
		context.put("contract", contract);
		return new ReplyHtml(VM.html(basePath + "DeliveryProductaddpage.vm",
				context));
	}

	@aDev(code = "170051", email = "bingyyf@foxmai.com", name = "杨杰")
	@aAuth(type = aAuthType.LOGIN)
	public Reply productFundpage() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();

		// 首付款情况
		context = service.fundInfo(map, context);

		// 查询发货信息
		Map contract = service.queryContractInfoByFund(map);
		context.put("contract", contract);
		return new ReplyHtml(VM.html(basePath + "ProductFundpage.vm", context));
	}

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply uploadReceiptFile() {
		Map<String, Object> map = _getParameters();
		Map mapPath = this._getParametersIO(null, null, null);
		if (mapPath != null && mapPath.get("_FILE_NAME") != null
				&& !mapPath.get("_FILE_NAME").equals("")) {
			map.put("FILE_NAME", mapPath.get("_FILE_NAME"));
			map.put("FILE_PATH", mapPath.get("file"));
			service.uploadReceiptFile(map);
		}
		return new ReplyAjax(map.get("FILE_PATH"));
	}

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply uploadProjectFile() {
		Map<String, Object> map = _getParameters();
		Map mapPath = this._getParametersIO(null, null, null);
		if (mapPath != null && mapPath.get("_FILE_NAME") != null
				&& !mapPath.get("_FILE_NAME").equals("")) {
			map.put("FILE_NAME", mapPath.get("_FILE_NAME"));
			map.put("FILE_PATH", mapPath.get("file"));
			service.uploadProjectFile(map);
		}
		return new ReplyAjax(map.get("FILE_PATH"));
	}

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply downLoadRecordFile() {
		Map map = _getParameters();
		return new ReplyFile(new File(map.get("file_url") + ""),
				map.get("file_name") + "");
	}

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doAddDeliveryProduct() {
		Map<String, Object> map = _getParameters();
		service.doAddDeliveryProduct(map);
		VelocityContext context = new VelocityContext();

		// 首付款情况
		context = service.fundInfo(map, context);

		// 查询发货信息
		Map contract = service.queryContractInfo(map);
		context.put("contract", contract);

		return new ReplyHtml(VM.html(basePath + "DeliveryProductaddpage.vm",
				context));
	}

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doViewDeliveryProduct() {
		Map<String, Object> map = _getParameters();

		VelocityContext context = new VelocityContext();

		// 首付款情况
		context = service.fundInfo(map, context);

		// 查询发货信息
		Map contract = service.queryContractInfo(map);
		context.put("contract", contract);

		return new ReplyHtml(VM.html(basePath + "DeliveryProductViewpage.vm",
				context));
	}

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply DeliveryProductUploadpage() {
		Map<String, Object> map = _getParameters();

		VelocityContext context = new VelocityContext();

		// 首付款情况
		context = service.fundInfo(map, context);

		// 查询发货信息
		Map contract = service.queryContractInfo(map);
		context.put("contract", contract);
		return new ReplyHtml(VM.html(basePath + "DeliveryProductUploadpage.vm",
				context));
	}

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply downLoadSupFile() {
		Map<String, Object> param = _getParameters();
		// path是指欲下载的文件的路径。
		String filePath = param.get("FILE_URL").toString();
		File file = new File(filePath);
		return new ReplyFile(file, null);
	}

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public void downReceiptFile() {
		Map<String, Object> m = _getParameters();
		// 修改导出状态
		service.updateSendStatus(m);
		service.download(m.get("filPath").toString(), SkyEye.getResponse());
	}

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Object addReceiptProduct() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		map.put("tags", "设备单位");
		List eqlist = Dao.selectList(
				"leaseApplication.queryEqByProjectIDByPayID", map);
		context.put("eqList", eqlist);

		// 查询信息
		Map BaseMap = service.queryReceiptMap(map);
		context.put("BaseMap", BaseMap);
		context.put("param", map);
		return new ReplyHtml(VM.html(basePath + "ReceiptProductaddpage.vm",
				context));
	}

	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply doAddReceipt() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		service.doAddReceipt(map);
		map.put("tags", "设备单位");
		List eqlist = Dao.selectList(
				"leaseApplication.queryEqByProjectIDByPayID", map);
		context.put("eqList", eqlist);

		// 查询信息
		Map BaseMap = service.queryReceiptMap(map);
		context.put("BaseMap", BaseMap);
		return new ReplyHtml(VM.html(basePath + "ReceiptProductaddpage.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "合同管理", "起租日管理", "列表显示" })
	public Reply toStartProject() {
		// 获取页面参数
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		// 判断是否为供应商，供应商（厂商）则不能修改金额提交
		// Map SUP_MAP=BaseUtil.getSup();
		// if(SUP_MAP!=null){
		// context.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		// }
		//
		// Map COM_MAP=BaseUtil.getCom();
		// if(COM_MAP!=null){
		// context.put("COMPANY_ID",COM_MAP.get("COMPANY_ID"));
		// }
		context.put("PLATFORM_LIST", new SysDictionaryMemcached().get("业务类型"));
		return new ReplyHtml(VM.html(basePath + "StartProjectManger.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "合同管理", "起租日管理", "列表显示" })
	public Reply toAjaxData() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.toAjaxData(param));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "合同管理", "起租日管理", "列表显示" })
	public Reply getLeasePayList() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.getLeasePayList(param));
	}

	// @aAuth(type=aAuth.aAuthType.USER)
	// @aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	// @aPermission(name = { "项目管理", "项目一览", "核心要件","支付表","查看"})
	// public Reply leaseApplication_viewProject()
	// {
	// Map<String, Object> map = _getParameters();
	// VelocityContext context=new VelocityContext();
	// //页面中所用信息
	// context=service.rentPlanUtil(context, map);
	// return new ReplyHtml(VM.html(basePath+"calculation_App_JBPM_VIEW.vm",
	// context));
	// }

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "合同管理", "起租日管理", "列表显示" })
	public Reply leaseApplication_view() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		// 页面中所用信息
		context = service.rentPlanUtil(context, map);

		context.put("dicTag", Util.DICTAG);

		return new ReplyHtml(VM.html(basePath + "calculation_App_JBPM_VIEW.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "项目管理", "发货管理", "列表" })
	public Reply toDeliverManager() {
		// 获取页面参数
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html(basePath + "toDeliverManager.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "项目管理", "发货管理", "列表" })
	public Reply toDeliverAjaxData() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjaxPage(service.toDeliverAjaxData(param));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "项目管理", "发货管理", "导出发货通知单" })
	public Reply downReceiptFileMsg() {
		Map<String, Object> m = _getParameters();
		boolean flag = service.downloadFile(m.get("filPath").toString(),
				SkyEye.getResponse());
		String msg = "";
		if (!flag) {
			msg = "发货通知单文件不存在";
		}
		return new ReplyAjax(flag, msg);
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "项目管理", "发货管理", "查看发货申请单" })
	public Reply doViewDeliveryProductMsg() {
		Map<String, Object> map = _getParameters();

		VelocityContext context = new VelocityContext();

		// 首付款情况
		context = service.fundInfo(map, context);

		// 查询发货信息
		Map contract = service.queryContractInfo(map);
		context.put("contract", contract);

		return new ReplyHtml(VM.html(basePath + "DeliveryProductViewpage.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "项目管理", "发货管理", "查看发货验收单" })
	public Reply receiptProductMsg() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		map.put("tags", "设备单位");
		List eqlist = Dao.selectList(
				"leaseApplication.queryEqByProjectIDByPayID", map);
		context.put("eqList", eqlist);

		// 查询信息
		Map BaseMap = service.queryReceiptMap(map);
		context.put("BaseMap", BaseMap);
		return new ReplyHtml(VM.html(basePath + "ReceiptProductViewpage.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	@aPermission(name = { "合同管理", "起租日管理", "生成支付表" })
	public Reply saveStartDate() {
		VelocityContext context = new VelocityContext();

		Map<String, Object> map = _getParameters();
		int num = service.saveStartDate(map);
		String PAY_ID = map.get("PAY_ID") + "";
		// if(num>0){
		// return new ReplyAjax(true);
		// }
		// else{
		// return new ReplyAjax(false);
		// }

		Map JBPMMAP = new HashMap();
		// 修改设备状态为流程申请中
		JBPMMAP.put("PAY_ID", PAY_ID);
		// List<String> list =
		// JBPM.getDeploymentListByModelName("起租申请流程",map.get("PLATFORM_TYPE") +
		// "", Security.getUser().getOrg().getPlatformId());
		List<String> list = JBPM.getDeploymentListByModelName("起租申请流程", "",
				Security.getUser().getOrg().getPlatformId());
		String jbpm_id = JBPM.startProcessInstanceById(list.get(0),
				map.get("PROJECT_ID") + "", map.get("CUST_ID") + "", "",
				JBPMMAP).getId();
		String nextCode = new TaskService().getAssignee(jbpm_id);
		context.put("JBPM_ID", jbpm_id);
		context.put("nextCode", nextCode);
		return new ReplyAjax(true, nextCode, "下一个操作人为: ");

	}

	@aPermission(name = { "合同管理", "起租日管理", "测试" })
	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply calculationLH() {
		Map<String, Object> m = _getParameters();
		VelocityContext context = new VelocityContext();

		if (m.get("ChangeViewData") != null
				&& !m.get("ChangeViewData").equals("")) {
			JSONObject map = JSONObject.fromObject(m.get("ChangeViewData"));
			// 处理两个日期
			Map da = Dao.selectOne("PayTask.queryNextDate", map);
			map.put("REPAYMENT_DATE_CHANGE", da.get("REPAYMENT_DATE_CHANGE"));
			// 页面中所用信息
			// 先查询该数据是否生成支付表，生成则不修改，负责修改，保存出资比例
			service.updateLHCZ(map);
			context = service.schemeUtilLH(context, map);

			List LHSQFSLIST = new DataDictionaryService()
					.queryDataDictionaryByScheme("联合收取方式");
			context.put("LHSQFSLIST", LHSQFSLIST);

			// 查询联合租赁信息
			List list = new projectService().getFLByProjectId(map);
			context.put("FL_LIST", list);
		}
		return new ReplyHtml(VM.html(basePath + "calculationStartAppLH.vm",
				context));
	}

	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply toSchemeInfoLH() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> m = _getParameters();
		context = service.toSchemeInfoLH(context, m);
		return new ReplyHtml(VM.html(basePath + "calculationStartSchemLH.vm",
				context));
	}

	@aDev(code = "170051", email = "qijl@pqsoft.cn", name = "齐姜龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply calculationViewLH() {
		Map<String, Object> m = _getParameters();
		VelocityContext context = new VelocityContext();

		// 通过支付表ID查询出项目Id，方案识别号，起租日期，还租日期等信息
		Map mapInfo = service.queryBaseInfoByPayId(m);

		context = service.schemeUtilLH(context, mapInfo);

		List LHSQFSLIST = new DataDictionaryService()
				.queryDataDictionaryByScheme("联合收取方式");
		context.put("LHSQFSLIST", LHSQFSLIST);

		// 查询联合租赁信息
		List list = new projectService().getFLByProjectId(mapInfo);
		context.put("FL_LIST", list);

		return new ReplyHtml(VM.html(basePath + "calculationStartAppLHView.vm",
				context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170051", email = "qijianglong1013@163.com", name = "齐姜龙")
	public Reply toUpdateStartDay() {
		// String PAYLIST_CODE="RZZLHT201500430-1";
		// String START_DATE="2014-05-28";
		Map<String, Object> param = _getParameters();
		String PAYLIST_CODE = (String) param.get("PAYLIST_CODE");
		String START_DATE = (String) param.get("START_DATE");

		Map<String, Object> mapPay = Dao.selectOne(
				"bpm.status.getDateMapByPaylistCode", PAYLIST_CODE);
		// paymentService paymentSer=new paymentService();
		try {
			// 先查询该项目中金融产品配置的是否差额利息（SFCELX）,如果没有配置或者停用状态或者配置的为Y则计算否则不计算
			Map<String, Object> ISCELXMap = Dao.selectOne(
					"PayTask.querySchemeSFCELXByPay", mapPay);
			if (StringUtils.isNotBlank(ISCELXMap)&&ISCELXMap != null && ISCELXMap.get("VALUE_STR") != null
					&& ISCELXMap.get("VALUE_STR").equals("3")) {

				DateSiteUtil.setDateDataByPaylistCode(PAYLIST_CODE, START_DATE,
						null, true);// 参数PAYLIST_CODE,START_DATE为起租日,3固定为放款日期类型标示

			} else {
				// 根据关键日期管理查询出还款日和起租日
				DateSiteUtil.setDateDataByPaylistCode(PAYLIST_CODE, START_DATE,
						null, false);// 参数PAYLIST_CODE,START_DATE为起租日,3固定为放款日期类型标示

				Map<String, Object> mapbase = Dao.selectOne(
						"PayTask.getSchemeBaseInfoByProjectIdINT", mapPay);

				// 查询起租日和还款日
				Map<String, Object> dataMap = Dao.selectOne(
						"PayTask.queryPayDataByPayId", mapPay);			
				
				if (mapbase != null) {
					mapbase.put("SCHEME_ID", mapbase.get("ID"));
					List<Map<String, Object>> clobList = Dao.selectList(
							"leaseApplication.queryfil_scheme_clobForCs",
							mapbase);
					for (Map<String, Object> map2 : clobList) {
						mapbase.put(map2.get("KEY_NAME_EN") + "",
								map2.get("VALUE_STR"));
					}

					try {
						mapbase.put("AMOUNT", Dao.selectInt(
								"PayTask.queryAmountCount", mapPay));
					} catch (Exception e) {
					}

					mapbase.put("annualRate", mapbase.get("YEAR_INTEREST"));
					mapbase.put("_leaseTerm", mapbase.get("LEASE_TERM"));
					mapbase.put("residualPrincipal",
							mapbase.get("FINANCE_TOPRIC"));
					mapbase.put("_payCountOfYear",
							mapbase.get("PAYCOUNTOFYEAR"));
					mapbase.put("pay_way", mapbase.get("PAY_WAY"));

					mapbase.put("LXTQSQ", mapbase.get("LXTQSQ"));
					mapbase.put("DISCOUNT_MONEY", mapbase.get("DISCOUNT_MONEY"));
					mapbase.put("DISCOUNT_TYPE", mapbase.get("DISCOUNT_TYPE"));
					mapbase.put("CALCULATE_TYPE", mapbase.get("CALCULATE"));

					mapbase.put("START_DATE", dataMap.get("START_DATE"));
					mapbase.put("REPAYMENT_DATE", dataMap.get("REPAYMENT_DATE"));

					JSONObject page = null;
					try {
						Class<?> cla = Class
								.forName("com.pqsoft.pay.service.PayTaskService");
						page = (JSONObject) cla.getMethod("quoteCalculateTest",
								Map.class).invoke(cla.newInstance(), mapbase);
					} catch (Exception e) {
						throw new ActionException(e.getMessage(),e);
					}
					List<Map<String, String>> irrList = (List<Map<String, String>>) page
							.get("ln");

					double ZJHJ = 0.00;// 租金合计
					for (int hh = 0; hh < irrList.size(); hh++) {
						// "zj":"11919.33","PMTzj":"12308.22","bj":"11141.55","lx":"777.78","sybj":"128858.45","qc":"1","PAY_DATE":"2015-09-15","glf":"0.0","sxf":"0","lxzzs":"0.00"
						Map mapIrr = irrList.get(hh);
						if (mapIrr != null) {
							mapIrr.put("PAY_ID", mapPay.get("PAY_ID"));

							ZJHJ = ZJHJ
									+ Double.parseDouble((mapIrr.get("zj") != null && mapIrr
											.get("zj") != "") ? mapIrr
											.get("zj") + "" : "0");
							// 更新租金
							mapIrr.put("ITEM_NAME", "租金");
							mapIrr.put(
									"ITEM_MONEY",
									(mapIrr.get("zj") != null && mapIrr
											.get("zj") != "") ? mapIrr
											.get("zj") : "0");
							Dao.update("PayTask.updatePayDetailItemInfo",
									mapIrr);
							// 更新PMT租金
							mapIrr.put("ITEM_NAME", "PMT租金");
							mapIrr.put(
									"ITEM_MONEY",
									(mapIrr.get("PMTzj") != null && mapIrr
											.get("PMTzj") != "") ? mapIrr
											.get("PMTzj") : "0");
							Dao.update("PayTask.updatePayDetailItemInfo",
									mapIrr);
							// 更新本金
							mapIrr.put("ITEM_NAME", "本金");
							mapIrr.put(
									"ITEM_MONEY",
									(mapIrr.get("bj") != null && mapIrr
											.get("bj") != "") ? mapIrr
											.get("bj") : "0");
							Dao.update("PayTask.updatePayDetailItemInfo",
									mapIrr);
							// 更新利息
							mapIrr.put("ITEM_NAME", "利息");
							mapIrr.put(
									"ITEM_MONEY",
									(mapIrr.get("lx") != null && mapIrr
											.get("lx") != "") ? mapIrr
											.get("lx") : "0");
							Dao.update("PayTask.updatePayDetailItemInfo",
									mapIrr);
							// 更新剩余本金
							mapIrr.put("ITEM_NAME", "剩余本金");
							mapIrr.put(
									"ITEM_MONEY",
									(mapIrr.get("sybj") != null && mapIrr
											.get("sybj") != "") ? mapIrr
											.get("sybj") : "0");
							Dao.update("PayTask.updatePayDetailItemInfo",
									mapIrr);
							// 更新管理费
							mapIrr.put("ITEM_NAME", "管理费");
							mapIrr.put(
									"ITEM_MONEY",
									(mapIrr.get("glf") != null && mapIrr
											.get("glf") != "") ? mapIrr
											.get("glf") : "0");
							Dao.update("PayTask.updatePayDetailItemInfo",
									mapIrr);
							// 更新手续费
							mapIrr.put("ITEM_NAME", "手续费");
							mapIrr.put(
									"ITEM_MONEY",
									(mapIrr.get("sxf") != null && mapIrr
											.get("sxf") != "") ? mapIrr
											.get("sxf") : "0");
							Dao.update("PayTask.updatePayDetailItemInfo",
									mapIrr);
							// 更新利息增值税
							mapIrr.put("ITEM_NAME", "利息增值税");
							mapIrr.put(
									"ITEM_MONEY",
									(mapIrr.get("lxzzs") != null && mapIrr
											.get("lxzzs") != "") ? mapIrr
											.get("lxzzs") : "0");
							Dao.update("PayTask.updatePayDetailItemInfo",
									mapIrr);
						}

					}

					// 更新还款计划主表租金合计
					Map pmap = new HashMap();
					pmap.put("ID", mapPay.get("PAY_ID"));
					pmap.put("ZJHJ", ZJHJ);
					Dao.update("PayTask.updatePayHeadMoneyAll", pmap);

					System.out.println("------------------------dataMap="
							+ dataMap);
					// 同步应收期初表数据
					// 同步财务数据
					Map<String, String> temp = new HashMap<String, String>();
					temp.put("PAY_ID", mapPay.get("PAY_ID") + "");
					temp.put("PAYLIST_CODE", dataMap.get("PAYLIST_CODE") + "");
					temp.put("PMT", "PMT租金");
					temp.put("ZJ", "租金");
					temp.put("SYBJ", "剩余本金");
					temp.put("D", "第");
					temp.put("QI", "期");
					// 删除财务表数据
					Dao.delete("PayTask.deleteBeginning", temp);
					Dao.insert("PayTask.synchronizationBeginning", temp);

					// 同步中间表
					// 刷单条逾期数据
					Dao.update("PayTask.doDunDateByPaylist_code", temp);
					Dao.update("PayTask.doPRC_BE_QJL_PAY_CODE", temp);

				}
			}

		} catch (Exception e) {
			throw new ActionException(e.getMessage(),e);
		}

		return new ReplyAjax(true, "成功");
	}
}
