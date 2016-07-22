package com.pqsoft.projectInvalid.action;
/**
 *   项目作废
 *   @author 韩晓龙
 */
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.jbpm.api.ProcessInstance;

import com.pqsoft.anchored.service.AnchoredManagerService;
import com.pqsoft.baseScheme.service.BaseSchemeService;
import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.bpm.status.ProjectStatus;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.pay.service.PayTaskService;
import com.pqsoft.project.service.ProjectContractManagerService;
import com.pqsoft.projectInvalid.service.Project_InvalidService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyMobile;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.util.UTIL;
import com.pqsoft.util.StringUtils;

public class ProjectInvalidAction extends Action {

	private String basePath = "projectInvalid/"; 
	private Project_InvalidService service = new Project_InvalidService();
	public VelocityContext context = new VelocityContext();
	
	/**
	 *  显示主页面
	 */
	@Override
	@aPermission(name = { "项目管理", "项目作废", "列表" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		//从数据字典取得 租赁物类型
		context.put("product_type", (List)new DataDictionaryMemcached().get("产品类型"));
		return new ReplyHtml(VM.html(basePath + "projectInvalidShow.vm", context));
	}
	
	/**
	 * 用于分页查询
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply pageData(){
		Map<String,Object> param = _getParameters();
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	
	/**
	 *  流程表单
	 *  参照金府立项流程表单
	 */
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply projectShowForJbpm() {
		Map<String, Object> map = _getParameters();
		// 项目信息
		Map projectMap = service.queryProjectById(map);
		map.putAll(projectMap);

		// 设备信息
		List eqlist = service.queryEqByProjectID(map);
		String SUPPLIER_ID = map.get("SUP_ID") + "";
		context.put("supAccountList", service.doShowSupAccount(SUPPLIER_ID));
		// 担保人信息
		List GuaranList = service.queryGuaranByProjectID(map);

		List<Map<String, Object>> listSechme = service.querySechmeByAllDate(map);

		// 还款计划
		// 查询支付表id在修改的时候使用
		PayTaskService pay = new PayTaskService();
		String PROJECT_ID = map.get("PROJECT_ID") + "";
		int PAY_ID = pay.queryIdByProjectId(PROJECT_ID);
		context.put("totalMap", service.doShowRentInfo(PAY_ID + ""));
		context.put("PAY_ID", PAY_ID);
		Map<String, Object> payMap = new HashMap<String, Object>();
		payMap.put("ID", PAY_ID);
		context.put("detailList", pay.doShowRentPlanDetail(payMap));
		String AFFILIATED_COMPANY = null;
		// 方案信息(商务政策)
		if (listSechme.size() <= 0 && map.get("POB_ID") != null) {
			BaseSchemeService schemeService = new BaseSchemeService();
			List<Map<String, Object>> SCHEMEDETAIL = schemeService.doSelectSchemeDetailByName(map.get("POB_ID") + "", "0", "1");
			for (Map<String, Object> mapItem : SCHEMEDETAIL) {
				if ("AFFILIATED_COMPANY".equals(mapItem.get("KEY_NAME_EN"))) {
					AFFILIATED_COMPANY = mapItem.get("VALUE_STR") + "";
					List lst = new AnchoredManagerService().doShowAnchoredCompanyList();
					if (lst.isEmpty() || lst.size() < 1) {
						mapItem.put("VALUE_STATUS", "1");
					} else {
						mapItem.put("LIST", lst);
					}
				} else {
					List lst = new DataDictionaryMemcached().get(mapItem.get("KEY_NAME_ZN") + "");
					mapItem.put("LIST", lst);
				}
			}
			context.put("SCHEMEDETAIL", SCHEMEDETAIL);
			context.put("viewType", "project_scheme_view1");

			if (map.get("POB_ID") != "" && !map.get("POB_ID").equals("-1")) {
				Map mapfee = new HashMap();
				BaseSchemeService baseSchService = new BaseSchemeService();
				mapfee.put("SCHEME_NAME", map.get("POB_ID"));
				map.put("feeCount", baseSchService.doSelectBaseSchemeFeeRateCount(mapfee));
			}
		} else {
			for (Map<String, Object> mapItem : listSechme) {
				// if (mapItem.containsKey("CALCULATE") &&
				// mapItem.get("CALCULATE") != null) {
				// continue;
				// } else {
				if ("AFFILIATED_COMPANY".equals(mapItem.get("KEY_NAME_EN"))) {
					AFFILIATED_COMPANY = mapItem.get("VALUE_STR") + "";
					List lst = new AnchoredManagerService().doShowAnchoredCompanyList();
					if (lst.isEmpty() || lst.size() < 1) {
						mapItem.put("VALUE_STATUS", "1");
					} else {
						mapItem.put("LIST", lst);
					}
				} else {
					List lst = new DataDictionaryMemcached().get(mapItem.get("KEY_NAME_ZN") + "");
					mapItem.put("LIST", lst);
				}
				// }
			}
			context.put("SCHEMEDETAIL", listSechme);

		}

		context.put("eqList", eqlist);
		context.put("GuaranList", GuaranList);
		context.put("param", map);

		// 判断当前流程节点名称
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = new TaskService().doShowTaskInfoByJbpmId(map);
		if (!list.isEmpty() && list.size() > 0) {
			String taskName = list.get(0).get("NAME_") + "";
			if (taskName.contains("呼叫中心")) {
				context.put("TASKNAME", "CALL");
			} else if (taskName.contains("签约")) {
				context.put("TASKNAME", "SIGN");
			} else if (taskName.contains("档案")) {
				context.put("TASKNAME", "DOSSIER");
			} else if (taskName.contains("起租申请")) {
				context.put("TASKNAME", "PAYSTART");
			} else if (taskName.contains("验收日")) {
				context.put("TASKNAME", "YSR");
			} else if (taskName.contains("反馈信审担当")) {
				context.put("TASKNAME", "ANSWER");
			} else if (taskName.contains("确认租赁物")) {
				context.put("TASKNAME", "EQUIPMENT");
			} else if (taskName.contains("首期付款验证")) {
				context.put("TASKNAME", "SFKYZ");
			} else if (taskName.contains("起租批准")) {
				context.put("TASKNAME", "QZPZ");
			} else if (taskName.contains("DB确认租赁物") || taskName.contains("DB上传起租要件")) {
				context.put("TASKNAME", "DBZLSC");
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			context.put("NOWDAY", sdf.format(new Date()));
			// 更新项目主表 当前节点名称
			new ProjectStatus().updateProjectJbpmTaskName(PROJECT_ID, taskName);
		}

		if (!StringUtils.isBlank(AFFILIATED_COMPANY) && !AFFILIATED_COMPANY.equals("null")) {
			try {
				// 挂靠公司信息
				context.put("AFFILIATEDFLAG", "1");
				context.put("AFFILIATED_COMPANY", service.doQueryAnchoredCompany(AFFILIATED_COMPANY));
			} catch (Exception e) {
			}
		}
		// 扣款类型
		String PROJECT_MODEL = map.get("PROJECT_MODEL") + "";
		List final_Type_List = new ArrayList();
		Map finalMap2 = new HashMap();
		finalMap2.put("FINAL_TYPE", null);
		finalMap2.put("FINAL_NAME", "--请选择--");
		final_Type_List.add(finalMap2);

		Map finalMap = new HashMap();
		finalMap.put("FINAL_TYPE", "0");
		finalMap.put("FINAL_NAME", "自有客户");
		final_Type_List.add(finalMap);

		Map finalMap1 = new HashMap();
		finalMap1.put("FINAL_TYPE", "1");
		if (PROJECT_MODEL.equals("6") || PROJECT_MODEL.equals("7") || PROJECT_MODEL.equals("8") || PROJECT_MODEL.equals("9"))
			finalMap1.put("FINAL_NAME", "担保人");
		else
			finalMap1.put("FINAL_NAME", "终端客户");
		final_Type_List.add(finalMap1);
		context.put("final_Type_List", final_Type_List);

		// 发票开具类型
		List<Map<String, Object>> invoice_target_type = new DataDictionaryService().queryDataDictionaryByScheme("发票_对象_类型");
		context.put("invoice_target_type", invoice_target_type);

		context.put("eqList", eqlist);
		context.put("GuaranList", GuaranList);
		context.put("param", map);
		context.put("FORMAT", UTIL.FORMAT);
		if (Security.isMobile()) {

			DecimalFormat df = new DecimalFormat(".##");
			// 租赁方案
			List<Map<String, Object>> zulinfanganList = new ArrayList<Map<String, Object>>();
			// 基本信息
			Map<String, Object> jiben = new HashMap<String, Object>();
			Map<String, Object> ljiben = new LinkedHashMap<String, Object>();
			ljiben.put("客户名称：", map.get("CUST_NAME") == null ? "" : map.get("CUST_NAME"));
			ljiben.put("客户类型：", map.get("CUST_TYPE_NAME") == null ? "" : map.get("CUST_TYPE_NAME"));
			ljiben.put("组织架构：", map.get("ORG_NAME") == null ? "" : map.get("ORG_NAME"));
			ljiben.put("区域：", map.get("AREA_NAME") == null ? "" : map.get("AREA_NAME"));
			ljiben.put("项目编号：", map.get("PRO_CODE") == null ? "" : map.get("PRO_CODE"));
			ljiben.put("项目名称：", map.get("PRO_NAME") == null ? "" : map.get("PRO_NAME"));
			ljiben.put("交货地点：", map.get("DELIVER_ADDRESS") == null ? "" : map.get("DELIVER_ADDRESS"));
			ljiben.put("验收日期：", map.get("DELIVER_DATE") == null ? "" : map.get("DELIVER_DATE"));
			ljiben.put("业务类型：", map.get("MODEL_NAME") == null ? "" : map.get("MODEL_NAME"));
			jiben.put("基本信息", ljiben);
			zulinfanganList.add(jiben);
			// 设备信息
			Map<String, Object> shebei = new HashMap<String, Object>();
			Map<String, Object> lshebei = new LinkedHashMap<String, Object>();
			double total = 0;
			for (int i = 0; i < eqlist.size(); i++) {
				Map<String, Object> map2 = (Map<String, Object>) eqlist.get(i);
				String str = "厂商编号:" + (map2.get("COMPANY_CODE") == null ? "\n" : map2.get("COMPANY_CODE") + "\n");
				str += "厂商:" + (map2.get("COMPANY_NAME") == null ? "\n" : map2.get("COMPANY_NAME") + "\n");
				str += "供应商:" + (map2.get("SUPPLIERS_NAME") == null ? "\n" : map2.get("SUPPLIERS_NAME") + "\n");
				str += "设备名称:" + (map2.get("PRODUCT_NAME") == null ? "\n" : map2.get("PRODUCT_NAME") + "\n");
				str += "车系:" + (map2.get("CATENA_NAME") == null ? "\n" : map2.get("CATENA_NAME") + "\n");
				str += "型号:" + (map2.get("SPEC_NAME") == null ? "\n" : map2.get("SPEC_NAME") + "\n");
				str += "出厂编号:" + (map2.get("WHOLE_ENGINE_CODE") == null ? "\n" : map2.get("WHOLE_ENGINE_CODE") + "\n");
				str += "出厂日期:" + (map2.get("CERTIFICATE_DATE") == null ? "\n" : map2.get("CERTIFICATE_DATE") + "\n");
				str += "发动机编号:" + (map2.get("ENGINE_TYPE") == null ? "\n" : map2.get("ENGINE_TYPE") + "\n");
				str += "车架号:" + (map2.get("CAR_SYMBOL") == null ? "\n" : map2.get("CAR_SYMBOL") + "\n");
				// str += "留购价(元):" + (map2.get("STAYBUY_PRICE") == null ? "\n"
				// : map2.get("STAYBUY_PRICE") + "\n");
				str += "指导价(元):" + (map2.get("UNIT_PRICE") == null ? "\n" : map2.get("UNIT_PRICE") + "\n");
				str += "数量:" + (map2.get("AMOUNT") == null ? "\n" : map2.get("AMOUNT") + "\n");
				str += "单位:" + (map2.get("UNITTEST") == null ? "\n" : map2.get("UNITTEST") + "\n");
				str += "小计:" + (map2.get("TOTAL") == null ? "\n" : map2.get("TOTAL") + "\n");
				total += Double.valueOf(map2.get("TOTAL") == null ? "0" : map2.get("TOTAL") + "");
				if (map.get("PROJECT_MODEL") != null && "1".equals(map.get("PROJECT_MODEL") + "")) {
					str += "产地:" + (map2.get("PRODUCT_ADDRESS") == null ? "\n" : map2.get("PRODUCT_ADDRESS") + "\n");
					str += "合格证书:" + (map2.get("CERTIFICATE_NUM") == null ? "\n" : map2.get("CERTIFICATE_NUM") + "\n");
					str += "限乘人数:" + (map2.get("LIMIT_NUM") == null ? "\n" : map2.get("LIMIT_NUM") + "\n");
					str += "机动车辆生产企业名称:" + (map2.get("COMPANY_FULLNAME") == null ? "\n" : map2.get("COMPANY_FULLNAME") + "\n");
					str += "进口证明书号:" + (map2.get("IMPORT_NUM") == null ? "\n" : map2.get("IMPORT_NUM") + "\n");
					str += "商检单号:" + (map2.get("INSPEC_NUM") == null ? "\n" : map2.get("INSPEC_NUM") + "\n");
					str += "吨位:" + (map2.get("TONNAGE") == null ? "\n" : map2.get("TONNAGE") + "\n");
					str += "实际车辆开票名称（车辆类型）:" + (map2.get("ACTUAL_PRODUCT_NAME") == null ? "\n" : map2.get("ACTUAL_PRODUCT_NAME") + "\n");
					str += "实际车辆开票型号（车厂牌型号）:" + (map2.get("ACTUAL_PRODUCT_TYPE") == null ? "\n" : map2.get("ACTUAL_PRODUCT_TYPE") + "\n");

				}
				lshebei.put((i + 1) + "", str);
			}
			lshebei.put("合计（小写：￥）:", total + "");
			shebei.put("设备信息", lshebei);
			zulinfanganList.add(shebei);
			// 方案信息
			Map<String, Object> fangan = new HashMap<String, Object>();
			Map<String, Object> lfangan = new LinkedHashMap<String, Object>();
			lfangan.put("设备总额：", df.format(Double.valueOf(map.get("LEASE_TOPRIC") == null ? "0" : map.get("LEASE_TOPRIC") + "")));
			lfangan.put("融资额：", df.format(Double.valueOf(map.get("FINANCE_TOPRIC") == null ? "0" : map.get("FINANCE_TOPRIC") + "")));
			String preiod = "";
			if (map.get("LEASE_PERIOD") != null && "1".equals(map.get("LEASE_PERIOD") + "")) {
				preiod = "月";
			} else if (map.get("LEASE_PERIOD") != null && "2".equals(map.get("LEASE_PERIOD") + "")) {
				preiod = "双月";
			} else if (map.get("LEASE_PERIOD") != null && "3".equals(map.get("LEASE_PERIOD") + "")) {
				preiod = "季";
			} else if (map.get("LEASE_PERIOD") != null && "6".equals(map.get("LEASE_PERIOD") + "")) {
				preiod = "半年";
			} else if (map.get("LEASE_PERIOD") != null && "12".equals(map.get("LEASE_PERIOD") + "")) {
				preiod = "年";
			}
			lfangan.put("期限：", map.get("LEASE_TERM") == null ? "" : map.get("LEASE_TERM") + preiod);
			lfangan.put("年利率：", map.get("YEAR_INTEREST") == null ? "" : map.get("YEAR_INTEREST") + "%");
			lfangan.put("手续费率：", df.format(Double.valueOf(map.get("FEES") == null ? "0" : map.get("FEES") + "")) + "%");
			lfangan.put("手续费：", df.format(Double.valueOf(map.get("FEES_PRICE") == null ? "0" : map.get("FEES_PRICE") + "")));
			lfangan.put("商务政策名称：", map.get("POB_ID") == null || "-1".equals(map.get("POB_ID") + "") ? "自定义" : map.get("POB_ID"));
			int i = 1;
			int ORNUMBER = 1;
			for (Map<String, Object> map2 : listSechme) {
				String KEY_NAME_EN = map2.get("KEY_NAME_EN") + "";
				if (!"COMPANY_ID".equals(KEY_NAME_EN) && !"SUPPLIER_ID".equals(KEY_NAME_EN) && !"PRODUCT_ID".equals(KEY_NAME_EN)) {
					if ("1".equals(map2.get("ORNUMBER") + "")) {
						if (ORNUMBER != 0 && i != 1) {
							i = (4 - i) * 2;
							ORNUMBER = 0;
							i = 1;
						}
						List<Map<String, Object>> LIST = (List<Map<String, Object>>) map2.get("LIST");
						String FLAG = "";
						if (LIST != null && LIST.size() > 0) {
							for (Map<String, Object> map3 : LIST) {
								if ((map3.get("CODE") + "").equals(map2.get("VALUE_STR") + "")) {
									FLAG = map3.get("FLAG") + "";
									break;
								}
							}
							if (map2.get("CALCULATE") != null) {
								FLAG += "%";
							}
						} else {
							FLAG = (map2.get("VALUE_STR") == null ? "0" : map2.get("VALUE_STR")) + "%";
						}
						lfangan.put(map2.get("KEY_NAME_ZN") + "：", FLAG);
					} else {
						List<Map<String, Object>> LIST = (List<Map<String, Object>>) map2.get("LIST");
						String FLAG = "";
						if (LIST != null && LIST.size() > 0) {
							for (Map<String, Object> map3 : LIST) {
								if ((map3.get("CODE") + "").equals(map2.get("VALUE_STR") + "")) {
									FLAG = map3.get("FLAG") + "";
									break;
								}
							}

						} else {
							FLAG = map2.get("VALUE_STR") == null ? "0" : map2.get("VALUE_STR") + "";
						}
						if (map2.get("CALCULATE") != null) {
							FLAG += "%";
						}
						lfangan.put(map2.get("KEY_NAME_ZN") + "：", FLAG);
					}
				}
			}
			Map<String, Object> totalMap = (Map<String, Object>) context.get("totalMap");
			lfangan.put("首期付款日期：", totalMap == null || totalMap.get("FIRSTPAYDATE") == null ? "" : totalMap.get("FIRSTPAYDATE"));
			lfangan.put("首期付款合计：", df.format(Double.valueOf(totalMap == null || totalMap.get("FIRSTPAYALL") == null ? "0.00" : totalMap.get("FIRSTPAYALL") + "")));
			lfangan.put("租金总额：", df.format(Double.valueOf(totalMap == null || totalMap.get("MONEYALL") == null ? "0.00" : totalMap.get("MONEYALL") + "")));
			String PLATFORM_TYPE = map.get("PLATFORM_TYPE") + "";
			if ("0".equals(PLATFORM_TYPE)) {
				PLATFORM_TYPE = "直接租赁业务";
			} else if ("1".equals(PLATFORM_TYPE)) {
				PLATFORM_TYPE = "标准型售后回租业务";
			} else if ("2".equals(PLATFORM_TYPE)) {
				PLATFORM_TYPE = "销售型售后回租业务";
			}
			lfangan.put("业务类型：", PLATFORM_TYPE);
			fangan.put("方案信息", lfangan);
			zulinfanganList.add(fangan);
			// 担保人
			Map<String, Object> danbao = new HashMap<String, Object>();
			Map<String, Object> ldanbao = new LinkedHashMap<String, Object>();
			if (GuaranList != null && GuaranList.size() > 0) {
				for (int j = 0; j < GuaranList.size(); j++) {
					Map<String, Object> map2 = (Map<String, Object>) GuaranList.get(j);
					String str = "类型:" + (map2.get("FLAG") == null ? "\n" : map2.get("FLAG") + "\n");
					str += "名称:" + (map2.get("GUARAN_NAME") == null ? "\n" : map2.get("GUARAN_NAME") + "\n");
					str += "身份证/组织机构:" + (map2.get("ORGANIZATION_CODE") == null ? "\n" : map2.get("ORGANIZATION_CODE") + "\n");
					str += "联系方式:" + (map2.get("GUARAN_PHONE") == null ? "\n" : map2.get("GUARAN_PHONE") + "\n");
					ldanbao.put((j + 1) + "", str);
				}
			}
			danbao.put("担保人", ldanbao);
			zulinfanganList.add(danbao);
			// 扣款账户信息
			Map<String, Object> koukuan = new HashMap<String, Object>();
			Map<String, Object> lkoukuan = new LinkedHashMap<String, Object>();
			String FINAL_TYPE = "";
			for (int j = 0; j < final_Type_List.size(); j++) {
				Map<String, Object> map2 = (Map<String, Object>) final_Type_List.get(j);
				if ((map2.get("FINAL_TYPE") + "").equals(map.get("FINAL_TYPE") + "")) {
					FINAL_TYPE = map2.get("FINAL_NAME") + "";
					break;
				}

			}
			lkoukuan.put("扣款类型：", FINAL_TYPE);
			lkoukuan.put("客户名称：", map.get("CUST_NAME1") == null ? "" : map.get("CUST_NAME1"));
			lkoukuan.put("扣款账户：", map.get("BANK_NAME") == null ? "" : (map.get("BANK_NAME") + "(" + (map.get("BANK_ACCOUNT") == null ? "" : map.get("BANK_ACCOUNT")) + ")"));
			koukuan.put("扣款账户信息", lkoukuan);
			zulinfanganList.add(koukuan);
			// 发票
			Map<String, Object> fapiao = new HashMap<String, Object>();
			Map<String, Object> lfapiao = new LinkedHashMap<String, Object>();
			String INVOICE_TARGET_TYPE = ((Map<String, Object>) invoice_target_type.get(0)).get("FLAG") + "";
			for (int j = 0; j < invoice_target_type.size(); j++) {
				Map<String, Object> map2 = (Map<String, Object>) invoice_target_type.get(j);
				if ((map2.get("CODE") + "").equals(map.get("INVOICE_TARGET_TYPE") + "")) {
					INVOICE_TARGET_TYPE = map2.get("FLAG") + "";
					break;
				}

			}
			lfapiao.put("发票类型：", INVOICE_TARGET_TYPE);
			fapiao.put("发票", lfapiao);
			zulinfanganList.add(fapiao);
			// 还款计划
			Map<String, Object> zujin = new HashMap<String, Object>();
			Map<String, Object> lzujin = new LinkedHashMap<String, Object>();
			List<Map<String, Object>> detailList = (List<Map<String, Object>>) context.get("detailList");
			double PMTZJ = 0;
			double ZJ = 0;
			double BJ = 0;
			double LX = 0;
			double SYBJ = 0;
			if (detailList != null && detailList.size() > 0) {
				for (int j = 0; j < detailList.size(); j++) {
					Map<String, Object> map2 = detailList.get(j);
					String str = "期次:" + (map2.get("PERIOD_NUM") == null ? "\n" : map2.get("PERIOD_NUM") + "\n");
					str += "支付时间:" + (map2.get("PAY_DATE") == null ? "\n" : map2.get("PAY_DATE") + "\n");
					str += "PMT租金:￥" + df.format(Double.valueOf(map2.get("PMTZJ") == null ? "0" : map2.get("PMTZJ") + "")) + "\n";
					PMTZJ += Double.valueOf(map2.get("PMTZJ") == null ? "0" : map2.get("PMTZJ") + "");
					str += "租金:￥" + df.format(Double.valueOf(map2.get("ZJ") == null ? "0" : map2.get("ZJ") + "")) + "\n";
					ZJ += Double.valueOf(map2.get("ZJ") == null ? "0" : map2.get("ZJ") + "");
					str += "本金:￥" + df.format(Double.valueOf(map2.get("BJ") == null ? "0" : map2.get("BJ") + "")) + "\n";
					BJ += Double.valueOf(map2.get("BJ") == null ? "0" : map2.get("BJ") + "");
					str += "利息:￥" + df.format(Double.valueOf(map2.get("LX") == null ? "0" : map2.get("LX") + "")) + "\n";
					LX += Double.valueOf(map2.get("LX") == null ? "0" : map2.get("LX") + "");
					str += "剩余本金:￥" + df.format(Double.valueOf(map2.get("SYBJ") == null ? "0" : map2.get("SYBJ") + "")) + "\n";
					SYBJ += Double.valueOf(map2.get("SYBJ") == null ? "0" : map2.get("SYBJ") + "");
					lzujin.put((j + 1) + "", str);
				}

			}
			lzujin.put("合计:", "PMT租金:￥" + PMTZJ + "\n租金:￥" + ZJ + "\n本金:￥" + BJ + "\n利息:￥" + LX);
			zujin.put("还款计划", lzujin);
			zulinfanganList.add(zujin);
			// 项目附件
			List<Map<String, Object>> FILELIST = new ProjectContractManagerService().doShowProjectContractList(map.get("PROJECT_ID") + "", "0", null, map.get("CLIENT_ID") + "", null, "立项");
			Map<String, Object> fujian = new HashMap<String, Object>();
			Map<String, Object> lfujian = new LinkedHashMap<String, Object>();
			for (int n = 0; n < FILELIST.size(); n++) {
				Map<String, Object> map2 = FILELIST.get(n);
				lfujian.put((n + 1) + " " + map2.get("NAME"), map2.get("PDF_PATH") + "");
			}
			fujian.put("项目附件", lfujian);
			zulinfanganList.add(fujian);

			// 承租人信息
			List<Map<String, Object>> chengzurenList = new ArrayList<Map<String, Object>>();
			{
				// 参数 CLIENT_ID="+ value + "&TYPE=" + type
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("CLIENT_ID", map.get("CUST_ID"));
				param.put("TYPE", map.get("CUST_TYPE"));
				Map<String, Object> context = new HashMap<String, Object>();
				context.put("param", param);
				com.pqsoft.customers.service.CustomersService service = new com.pqsoft.customers.service.CustomersService();
				context.put("custInfo", service.findCustDetail(param));// 客户详细信息
				context.put("custLinkInfo", service.findCustLinkInfo(param));// 客户关联信息
				context.put("type", service.findCustDataType());// 客户关联信息
				context.put("getProvince", service.getProvince());
				context.put("account_type", new DataDictionaryMemcached().get("开户行账号类型"));
				context.put("bank", Dao.selectList("customers.findBankDetail", param));
				if (param.get("TYPE").toString().equals("NP")) {// 自然人
					param.put("TYPE_LINK", 0);
					context.put("child", Dao.selectList("customers.findLinkPDetail", param));
					param.put("TYPE_LINK", 1);
					context.put("solialRe", Dao.selectList("customers.findLinkPDetail", param));
					context.put("relationToCust", service.relationToCust());
				}
				// 基本信息
				Map<String, Object> cjiben = new HashMap<String, Object>();
				Map<String, Object> lcjiben = new LinkedHashMap<String, Object>();
				Map<String, Object> type = (Map<String, Object>) context.get("type");
				Map<String, Object> custInfo = (Map<String, Object>) context.get("custInfo");
				if (param.get("TYPE").toString().equals("NP")) {// 自然人
					lcjiben.put("客户类型：", "个人");
					lcjiben.put("客户名称：", custInfo.get("NAME") == null ? "" : custInfo.get("NAME"));
					List<Map<String, Object>> id_typeL = (List<Map<String, Object>>) type.get("id_typeL");
					String ID_CARD_TYPE = "";
					for (Map<String, Object> map2 : id_typeL) {
						if ((map2.get("CODE") + "").equals(custInfo.get("ID_CARD_TYPE") + "")) {
							ID_CARD_TYPE = map2.get("FLAG") + "";
							break;
						}
					}
					lcjiben.put("证件类型：", ID_CARD_TYPE);
					lcjiben.put("证件号：", custInfo.get("ID_CARD_NO") == null ? "" : custInfo.get("ID_CARD_NO"));
					lcjiben.put("出生年月：", custInfo.get("BIRTHDAY") == null ? "" : custInfo.get("BIRTHDAY"));
					lcjiben.put("手机号码：", custInfo.get("TEL_PHONE") == null ? "" : custInfo.get("TEL_PHONE"));
					lcjiben.put("邮编：", custInfo.get("POST") == null ? "" : custInfo.get("POST"));
					lcjiben.put("家庭电话：", custInfo.get("PHONE") == null ? "" : custInfo.get("PHONE"));
					lcjiben.put("性别：", custInfo.get("SEX") == null || "0".equals(custInfo.get("SEX") + "") ? "男" : "女");
					List<Map<String, Object>> degree_edu = (List<Map<String, Object>>) type.get("degree_edu");
					String DEGREE_EDU = "";
					for (Map<String, Object> map2 : degree_edu) {
						if ((map2.get("CODE") + "").equals(custInfo.get("DEGREE_EDU") + "")) {
							DEGREE_EDU = map2.get("FLAG") + "";
							break;
						}
					}
					lcjiben.put("文化程度：", DEGREE_EDU);
					List<Map<String, Object>> marital_status = (List<Map<String, Object>>) type.get("marital_status");
					String IS_MARRY = "";
					for (Map<String, Object> map2 : marital_status) {
						if ((map2.get("CODE") + "").equals(custInfo.get("IS_MARRY") + "")) {
							IS_MARRY = map2.get("FLAG") + "";
							break;
						}
					}
					lcjiben.put("婚姻状况：", IS_MARRY);
					List<Map<String, Object>> nationL = (List<Map<String, Object>>) type.get("nationL");
					String NATION = "";
					for (Map<String, Object> map2 : nationL) {
						if ((map2.get("CODE") + "").equals(custInfo.get("NATION") + "")) {
							NATION = map2.get("FLAG") + "";
							break;
						}
					}
					lcjiben.put("民族：", NATION);
					List<Map<String, Object>> getProvince = (List<Map<String, Object>>) context.get("getProvince");
					String PROVINCE = "";
					for (Map<String, Object> map2 : getProvince) {
						if ((map2.get("AREA_ID") + "").equals(custInfo.get("PROVINCE") + "")) {
							PROVINCE = map2.get("NAME") + "";
							break;
						}
					}
					lcjiben.put("省：", PROVINCE);
					lcjiben.put("市：", custInfo.get("CITY_NAME") == null ? "" : custInfo.get("CITY_NAME"));
					lcjiben.put("子女：", custInfo.get("IS_CHILDRED") == null || "0".equals(custInfo.get("IS_CHILDRED") + "") ? "无" : "有");
					lcjiben.put("工作单位：", custInfo.get("WORK_UNIT") == null ? "" : custInfo.get("WORK_UNIT"));
					List<Map<String, Object>> com_typeL = (List<Map<String, Object>>) type.get("com_typeL");
					String COMPANY_PROPERTY = "";
					for (Map<String, Object> map2 : com_typeL) {
						if ((map2.get("CODE") + "").equals(custInfo.get("COMPANY_PROPERTY") + "")) {
							COMPANY_PROPERTY = map2.get("FLAG") + "";
							break;
						}
					}
					lcjiben.put("工作属性：", COMPANY_PROPERTY);
					lcjiben.put("入职时间：", custInfo.get("ENTRY_TIME") == null ? "" : custInfo.get("ENTRY_TIME"));
					List<Map<String, Object>> profession = (List<Map<String, Object>>) type.get("profession");
					// String POSITION = "";
					// for (Map<String, Object> map2 : profession) {
					// if ((map2.get("CODE") +
					// "").equals(custInfo.get("POSITION") + "")) {
					// POSITION = map2.get("FLAG") + "";
					// break;
					// }
					// }
					lcjiben.put("职务：", custInfo.get("POSITION") == null ? "" : custInfo.get("POSITION"));
					lcjiben.put("传真：", custInfo.get("FAX") == null ? "" : custInfo.get("FAX"));
					lcjiben.put("工作地址：", custInfo.get("COMPANY_ADDR") == null ? "" : custInfo.get("COMPANY_ADDR"));
					lcjiben.put("家庭地址：", custInfo.get("HOUSE_ADDRESS") == null ? "" : custInfo.get("HOUSE_ADDRESS"));
					lcjiben.put("户籍地址：", custInfo.get("HOUSR_RE_ADDRESS") == null ? "" : custInfo.get("HOUSR_RE_ADDRESS"));
					List<Map<String, Object>> seniority = (List<Map<String, Object>>) type.get("seniority");
					String TAX_QUALIFICATION = "";
					for (Map<String, Object> map2 : seniority) {
						if ((map2.get("CODE") + "").equals(custInfo.get("TAX_QUALIFICATION") + "")) {
							TAX_QUALIFICATION = map2.get("FLAG") + "";
							break;
						}
					}
					lcjiben.put("纳税人资质：", TAX_QUALIFICATION);
					if ("1Marriage".equals(custInfo.get("IS_MARRY") + "")) {
						Map<String, Object> custLinkInfo = (Map<String, Object>) context.get("custLinkInfo");
						Map<String, Object> spoustDet = (Map<String, Object>) custLinkInfo.get("spoustDet");
						if (spoustDet == null) {
							spoustDet = new HashMap<String, Object>();
						}
						lcjiben.put("配偶姓名：", spoustDet.get("NAME") == null ? "" : spoustDet.get("NAME"));
						lcjiben.put("配偶性别：", spoustDet.get("SEX") == null || "0".equals(spoustDet.get("SEX")) ? "男" : "女");
						lcjiben.put("配偶出生日期：", spoustDet.get("BIRTHDAY") == null ? "" : spoustDet.get("BIRTHDAY"));
						for (Map<String, Object> map2 : nationL) {
							if ((map2.get("CODE") + "").equals(spoustDet.get("NATION") + "")) {
								NATION = map2.get("FLAG") + "";
								break;
							}
						}
						lcjiben.put("配偶民族：", NATION);
						lcjiben.put("配偶身份证号码：", spoustDet.get("ID_CARD_NO") == null ? "" : spoustDet.get("ID_CARD_NO"));
						lcjiben.put("配偶手机：", spoustDet.get("TEL_PHONE") == null ? "" : spoustDet.get("TEL_PHONE"));
						lcjiben.put("配偶户籍所在地：", spoustDet.get("HOUSR_RE_ADDRESS") == null ? "" : spoustDet.get("HOUSR_RE_ADDRESS"));
						lcjiben.put("配偶工作单位：", spoustDet.get("WORK_UNIT") == null ? "" : spoustDet.get("WORK_UNIT"));
						for (Map<String, Object> map2 : com_typeL) {
							if ((map2.get("CODE") + "").equals(spoustDet.get("COMPANY_PROPERTY") + "")) {
								COMPANY_PROPERTY = map2.get("FLAG") + "";
								break;
							}
						}
						lcjiben.put("配偶单位属性：", COMPANY_PROPERTY);
						// for (Map<String, Object> map2 : profession) {
						// if ((map2.get("CODE") +
						// "").equals(spoustDet.get("POSITION") + "")) {
						// POSITION = map2.get("FLAG") + "";
						// break;
						// }
						// }
						lcjiben.put("配偶职务：", spoustDet.get("POSITION") == null ? "" : spoustDet.get("POSITION"));
						lcjiben.put("配偶单位电话：", spoustDet.get("WORK_PHONE") == null ? "" : spoustDet.get("WORK_PHONE"));
						lcjiben.put("配偶传真：", spoustDet.get("FAX") == null ? "" : spoustDet.get("FAX"));
						lcjiben.put("配偶单位地址：", spoustDet.get("COMPANY_ADDR") == null ? "" : spoustDet.get("COMPANY_ADDR"));
						for (Map<String, Object> map2 : degree_edu) {
							if ((map2.get("CODE") + "").equals(spoustDet.get("DEGREE_EDU") + "")) {
								DEGREE_EDU = map2.get("FLAG") + "";
								break;
							}
						}
						lcjiben.put("配偶文化程度：", DEGREE_EDU);
					}
				} else {
					lcjiben.put("客户类型：", "法人");
					lcjiben.put("企业名称：", custInfo.get("NAME") == null ? "" : custInfo.get("NAME"));
					List<Map<String, Object>> com_typeL = (List<Map<String, Object>>) type.get("com_typeL");
					String BUSINESS_TYPE = "";
					for (Map<String, Object> map2 : com_typeL) {
						if ((map2.get("CODE") + "").equals(custInfo.get("BUSINESS_TYPE") + "")) {
							BUSINESS_TYPE = map2.get("FLAG") + "";
							break;
						}
					}
					lcjiben.put("企业性质：", BUSINESS_TYPE);
					lcjiben.put("营业执照：", custInfo.get("CORP_BUSINESS_LICENSE") == null ? "" : custInfo.get("CORP_BUSINESS_LICENSE"));
					lcjiben.put("法人代表：", custInfo.get("LEGAL_PERSON") == null ? "" : custInfo.get("LEGAL_PERSON"));
					lcjiben.put("法人代表联系方式：", custInfo.get("LEGAL_PERSON_PHONE") == null ? "" : custInfo.get("LEGAL_PERSON_PHONE"));
					lcjiben.put("税务识别号：", custInfo.get("TAX_CODE") == null ? "" : custInfo.get("TAX_CODE"));
					lcjiben.put("组织机构代码证号：", custInfo.get("ORAGNIZATION_CODE") == null ? "" : custInfo.get("ORAGNIZATION_CODE"));
					lcjiben.put("成立日期：", custInfo.get("SETUP_DATE") == null ? "" : custInfo.get("SETUP_DATE"));
					lcjiben.put("有效期：", custInfo.get("PERIOD_OF_VALIDITY") == null ? "" : custInfo.get("PERIOD_OF_VALIDITY"));
					lcjiben.put("注册时间：", custInfo.get("REGISTE_DATE") == null ? "" : custInfo.get("REGISTE_DATE"));
					lcjiben.put("注册资本：", custInfo.get("REGISTE_CAPITAL") == null ? "" : custInfo.get("REGISTE_CAPITAL"));
					lcjiben.put("传真：", custInfo.get("FAX") == null ? "" : custInfo.get("FAX"));
					lcjiben.put("公司邮编：", custInfo.get("POST") == null ? "" : custInfo.get("POST"));
					lcjiben.put("员工人数：", custInfo.get("NUMBER_PER") == null ? "" : custInfo.get("NUMBER_PER"));
					lcjiben.put("注册资本：", custInfo.get("REGISTE_CAPITAL") == null ? "" : custInfo.get("REGISTE_CAPITAL"));
					List<Map<String, Object>> getProvince = (List<Map<String, Object>>) context.get("getProvince");
					String PROVINCE = "";
					for (Map<String, Object> map2 : getProvince) {
						if ((map2.get("AREA_ID") + "").equals(custInfo.get("PROVINCE") + "")) {
							PROVINCE = map2.get("NAME") + "";
							break;
						}
					}
					lcjiben.put("省：", PROVINCE);
					lcjiben.put("市：", custInfo.get("CITY_NAME") == null ? "" : custInfo.get("CITY_NAME"));
					List<Map<String, Object>> seniority = (List<Map<String, Object>>) type.get("seniority");
					String TAX_QUALIFICATION = "";
					for (Map<String, Object> map2 : seniority) {
						if ((map2.get("CODE") + "").equals(custInfo.get("TAX_QUALIFICATION") + "")) {
							TAX_QUALIFICATION = map2.get("FLAG") + "";
							break;
						}
					}
					lcjiben.put("纳税资质：", TAX_QUALIFICATION);
					List<Map<String, Object>> situation = (List<Map<String, Object>>) type.get("situation");
					String RATEPAYING = "";
					for (Map<String, Object> map2 : situation) {
						if ((map2.get("CODE") + "").equals(custInfo.get("RATEPAYING") + "")) {
							RATEPAYING = map2.get("FLAG") + "";
							break;
						}
					}
					lcjiben.put("纳税情况：", RATEPAYING);
					lcjiben.put("是否对外担保：", custInfo.get("IS_GUARANTEE") == null || "0".equals(custInfo.get("IS_GUARANTEE")) ? "无" : "有");
					lcjiben.put("主营业务：", custInfo.get("MAIN_BUSINESS") == null ? "" : custInfo.get("MAIN_BUSINESS"));
					lcjiben.put("注册地址：", custInfo.get("REGISTE_ADDRESS") == null ? "" : custInfo.get("REGISTE_ADDRESS"));
					lcjiben.put("公司办公地址：", custInfo.get("WORK_ADDRESS") == null ? "" : custInfo.get("WORK_ADDRESS"));

				}
				cjiben.put("基本信息", lcjiben);
				chengzurenList.add(cjiben);

				// 银行信息
				Map<String, Object> yinhang = new HashMap<String, Object>();
				Map<String, Object> lyinhang = new LinkedHashMap<String, Object>();
				List<Map<String, Object>> bank = (List<Map<String, Object>>) context.get("bank");
				for (int j = 0; j < bank.size(); j++) {
					Map<String, Object> map2 = bank.get(j);
					String str = "银行名称:" + (map2.get("BANK_NAME") == null ? "\n" : map2.get("BANK_NAME") + "\n");
					str += "银行帐号:" + (map2.get("BANK_ACCOUNT") == null ? "\n" : map2.get("BANK_ACCOUNT") + "\n");
					str += "银行地址:" + (map2.get("BANK_ADDRESS") == null ? "\n" : map2.get("BANK_ADDRESS") + "\n");
					str += "持卡人:" + (map2.get("BANK_CUSTNAME") == null ? "\n" : map2.get("BANK_CUSTNAME") + "\n");
					str += "账号类型:" + (map2.get("FLAG") == null ? "\n" : map2.get("FLAG") + "\n");
					str += "备注:" + (map2.get("REMARK") == null ? "\n" : map2.get("REMARK") + "\n");
					lyinhang.put((j + 1) + "", str);
				}
				yinhang.put("银行信息", lyinhang);
				chengzurenList.add(yinhang);
				if (param.get("TYPE").toString().equals("NP")) {// 自然人
					// 子女信息
					Map<String, Object> zinv = new HashMap<String, Object>();
					Map<String, Object> lzinv = new LinkedHashMap<String, Object>();
					List<Map<String, Object>> child = (List<Map<String, Object>>) context.get("child");
					for (int j = 0; j < child.size(); j++) {
						Map<String, Object> map2 = child.get(j);
						String str = "姓名:" + (map2.get("LINK_NAME") == null ? "\n" : map2.get("LINK_NAME") + "\n");
						str += "身份证:" + (map2.get("LINK_IDCARD") == null ? "\n" : map2.get("LINK_IDCARD") + "\n");
						str += "性别:" + (map2.get("LINK_SEX") == null || "0".equals(map2.get("LINK_SEX") + "") ? "男\n" : "女\n");
						str += "出生日期:" + (map2.get("LINK_BIRTHDAY") == null ? "\n" : map2.get("LINK_BIRTHDAY") + "\n");
						str += "工作单位:" + (map2.get("LINK_WORK_UNITS") == null ? "\n" : map2.get("LINK_WORK_UNITS") + "\n");
						str += "备注:" + (map2.get("REMARK") == null ? "\n" : map2.get("REMARK") + "\n");
						lzinv.put((j + 1) + "", str);
					}
					zinv.put("子女信息", lzinv);
					chengzurenList.add(zinv);
					// 社会关系
					Map<String, Object> shehui = new HashMap<String, Object>();
					Map<String, Object> lshehui = new LinkedHashMap<String, Object>();
					List<Map<String, Object>> solialRe = (List<Map<String, Object>>) context.get("solialRe");
					List<Map<String, Object>> relationToCust = (List<Map<String, Object>>) context.get("relationToCust");
					for (int j = 0; j < solialRe.size(); j++) {
						Map<String, Object> map2 = solialRe.get(j);
						String str = "联系人名称:" + (map2.get("LINK_NAME") == null ? "\n" : map2.get("LINK_NAME") + "\n");
						str += "身份证:" + (map2.get("LINK_IDCARD") == null ? "\n" : map2.get("LINK_IDCARD") + "\n");
						str += "性别:" + (map2.get("LINK_SEX") == null || "0".equals(map2.get("LINK_SEX") + "") ? "男\n" : "女\n");
						String LINK_RELATION2CUST = "";
						for (int m = 0; m < relationToCust.size(); m++) {
							Map<String, Object> map1 = relationToCust.get(m);
							String FLAG = map1.get("FLAG") + "";
							String CODE = Double.valueOf(map1.get("CODE") + "").intValue() + "";
							if (CODE.equals(map.get("LINK_RELATION2CUST") == null ? "" : Double.valueOf(map.get("LINK_RELATION2CUST") + "").intValue() + "")) {
								LINK_RELATION2CUST = FLAG;
								break;
							}
						}
						str += "与客户关系:" + LINK_RELATION2CUST + "\n";
						str += "联系人手机:" + (map2.get("LINK_MOBILE") == null ? "\n" : map2.get("LINK_MOBILE") + "\n");
						str += "联系人固话:" + (map2.get("LINK_PHONE") == null ? "\n" : map2.get("LINK_PHONE") + "\n");
						str += "工作单位:" + (map2.get("LINK_WORK_UNITS") == null ? "\n" : map2.get("LINK_WORK_UNITS") + "\n");
						str += "备注:" + (map2.get("REMARK") == null ? "\n" : map2.get("REMARK") + "\n");
						lshehui.put((j + 1) + "", str);
					}
					shehui.put("社会关系", lshehui);
					chengzurenList.add(shehui);
				}
			}
			Map<String, Object> zulinfanganMap = new HashMap<String, Object>();
			zulinfanganMap.put("租赁方案", zulinfanganList);
			Map<String, Object> chengzurenMap = new HashMap<String, Object>();
			chengzurenMap.put("承租人信息", chengzurenList);

			List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
			json.add(zulinfanganMap);
			json.add(chengzurenMap);

			return new ReplyMobile(json);

		} else
			context.put("modelList", new SysDictionaryMemcached().get("业务类型"));
		return new ReplyHtml(VM.html(basePath + "projectInvalidViewForJbpm_main_Credit.vm", context));
	}
	
	@aPermission(name = { "项目管理", "项目作废", "发起流程" })
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	@aAuth(type = aAuthType.LOGIN)
	public Reply projectInvalidJBPM() {
		Map<String, Object> param = _getParameters();
		//System.out.println("--------------------param="+param);
		//发起流程---------------------------------------------------------------
		List<String> list=JBPM.getDeploymentListByModelName("project_withdrawn");//project_withdrawn
		ProcessInstance processInstance =  JBPM.startProcessInstanceById(list.get(0), param.get("PROJECT_ID")+"", param.get("CLIENT_ID")+"", "", param);
		String nextCode = new TaskService().getAssignee(processInstance.getId());
		changeProStatus99();
		return new ReplyAjax(true,nextCode,"下一步操作人为: ").addOp(new OpLog("项目管理", "项目作废-作废申请", "申请流程发起"));
	}
	
	/**
	 *  修改项目状态   作废中 99
	 */
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	@aAuth(type = aAuthType.LOGIN)
	public void changeProStatus99() {
		Map<String, Object> param = _getParameters();
		System.out.println("param=======================" + param);
		param.put("STATUS", "99");//作废中 99
		service.updateProStatus(param);
	}
	
	/**
	 *  修改项目状态   已作废100
	 */
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	@aAuth(type = aAuthType.LOGIN)
	public void changeProStatus100() {
		Map<String, Object> param = _getParameters();
		System.out.println("param=======================" + param);
		param.put("STATUS", "100");//已作废100
		service.updateProStatus(param);
	}
	
	private void getVeriable(String jbpmId) {
		Map<String, Object> param = JBPM.getVeriable(jbpmId);
	}
}
