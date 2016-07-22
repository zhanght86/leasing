package com.pqsoft.pay.service;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jbpm.api.ProcessInstance;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.TaskService;
import com.pqsoft.buyBack.service.BuyBackService;
import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.entity.Excel;
import com.pqsoft.project.service.ProjectContractManagerService;
import com.pqsoft.project.service.projectService;
import com.pqsoft.pub.p2p.algorithm.Algorithm;
import com.pqsoft.pub.p2p.algorithm.model.CalculateItem;
import com.pqsoft.pub.p2p.algorithm.model.CarryType;
import com.pqsoft.pub.p2p.algorithm.model.DigitType;
import com.pqsoft.pub.p2p.algorithm.model.IsRatioCalculate;
import com.pqsoft.pub.p2p.algorithm.model.MeasureResult;
import com.pqsoft.skyeye.Log;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.filter.ResMime;
import com.pqsoft.skyeye.math.FinanceBean;
import com.pqsoft.skyeye.math.SkyEyeFinance;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.util.UtilDate;
import com.pqsoft.skyeye.util.UtilFinance;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.JsonUtil;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.PoiExcelUtil;
import com.pqsoft.util.StringUtils;
import com.pqsoft.util.Util;


public class PayTaskService {

	public Page queryPage(Map<String, Object> m) {
		// Page page = new Page(m);
		m.put("TYPE_", "还款计划状态");
		m.put("TYPE1_", "业务类型");
		BaseUtil.getDataAllAuth(m);
		m.put("ORD_ID", Security.getUser().getOrg().getId());
		return PageUtil.getPage(m, "PayTask.getAllPayTask",	"PayTask.getAllPayTask_count");
	}

	public int doPayListCodeIng(Map<String, Object> m) {
		return Dao.selectInt("PayTask.doPayListCodeIng", m);
	}

	public Page irrData(Map<String, Object> m) {
		Page page = new Page(m);
		JSONArray array = JSONArray.fromObject(Dao.selectList("getirrData", m));
		page.addDate(array, Dao.selectInt("getirrData_count", m));
		return page;
	}

	public Page queryPage_stuts(Map<String, Object> m) {
		// Page page = new Page(m);
		m.put("TYPE_", "还款计划状态");
		return PageUtil.getPage(m, "PayTask.getAllPayTask_stuts", "PayTask.getAllPayTask_count_stuts");
	}

	public Map<String, Object> queryPayByPayCode(Map<String, Object> m) {
		// Map map = (Map)Dao.selectOne("PayTask.queryPay", m);
		// String ID = map.get("ID")+"";
		// List<Map<String, String>> list = rowDenormaliser(m.get("ID") + "");
		List<Map<String, String>> list = Dao.selectList("PayTask.doShowRentPlanDetail", m);

		m.put("payDetail", JSONArray.fromObject(list));
		Map<String, Object> projectInfo = Dao.selectOne("PayTask.query_project_info", m.get("ID"));
		m.put("projectInfo", projectInfo);
		m.put("YEAR_INTEREST", projectInfo.get("YEAR_INTEREST"));
		return m;
	}

	public Map<String, Object> queryPayByPayCodeOld(Map<String, Object> m) {
		// Map<String,Object> map = Dao.selectOne("PayTask.queryPay", m);
		// String ID = map.get("ID")+"";
		List<Map<String, String>> list = rowDenormaliser(m.get("ID") + "");

		m.put("payDetail", JSONArray.fromObject(list));
		Map<String, Object> projectInfo = Dao.selectOne("PayTask.query_project_info", m.get("ID"));
		m.put("projectInfo", projectInfo);
		m.put("YEAR_INTEREST", projectInfo.get("YEAR_INTEREST"));
		return m;
	}

	public JSONObject calculateTest(Map<String, Object> m) throws Exception {
		Map<String, Object> map = Dao.selectOne("PayTask.queryPayByID", m);
		double last_money = 0;
		if (map.containsKey("LAST_MONEY") && StringUtils.isNotBlank(map.get("LAST_MONEY")))
			last_money = Double.parseDouble(map.get("LAST_MONEY") + "");
		int minRentList = Dao.selectInt("PayTask.queryMinRentListByID", m);
		// int minRentList = 1;
		map.put("minRentList", minRentList);
		List<Map<String, String>> list = rowDenormaliser(map.get("ID") + "");
		int changeIssue = Integer.parseInt(m.get("changeIssue") + "");// 开始变更期次
		int shortLengthenIssue = Integer.parseInt(m.get("shortLengthenIssue")+ "");// 要变更多少期
		double residualPrincipal = Double.valueOf(m.get("residualPrincipal")+ "");// 剩余本金
		double END_YEAR_INTEREST = m.get("END_YEAR_INTEREST") == null
				|| "".equals(m.get("END_YEAR_INTEREST"))
				? Double.parseDouble(m.get("YEAR_INTEREST") + "") : Double.parseDouble(m.get("END_YEAR_INTEREST") + "");// 变更的利率
		// END_YEAR_INTEREST = END_YEAR_INTEREST/100;
		String code = m.get("code") + "";
		if ("4".equals(code)) {// 如果code值为4的时候，表示为缩期
			shortLengthenIssue = -shortLengthenIssue;
		}
		// 变更的总期次为
		// double _leaseTerm = list.size() + shortLengthenIssue - changeIssue +
		// 1;
		int _leaseTerm = list.size() + shortLengthenIssue - changeIssue + 1	+ minRentList - 1;
		int _payCountOfYear = 12 / Integer.valueOf(map.get("LEASE_PERIOD") + "");
		// 拷贝变更期次前的数据
		String date = ""; // 日期参数
		List<Map<String, String>> ln = new ArrayList<Map<String, String>>();
		System.out.println("================" + changeIssue + ":" + list);
		for (int i = 1; i < changeIssue - minRentList + 1; i++) {
			ln.add(list.get(i - 1));
			date = list.get(i - 1).get("PAY_DATE");
		}
		// 如果date为空，有可能是从第一期开始变更
		if ("".equals(date)) {
			date = list.get(0).get("PAY_DATE");
			date = Util.getMonth(date, -1);
		}
		List<Map<String, String>> averagePrincipalInterest = null;

		// 起租日和还款日直接的利息
		double DateLXMoney = 0d;
		// 如果code值为8，则为不等额支付，不等额支付特殊处理
		// -------------------------------------
		double DISCOUNT_MONEY = Double.parseDouble(m.get("DISCOUNT_MONEY") == null
				|| "".equals(m.get("DISCOUNT_MONEY")) ? "0" : (m.get("DISCOUNT_MONEY") + ""));

		String CALCULATE_WAY = "PMT";
		if (StringUtils.isNotBlank(map.get("CALCULATE_TYPE"))) {
			CALCULATE_WAY = map.get("CALCULATE_TYPE") + "";
		}

		FinanceBean bean = new FinanceBean();
		if (StringUtils.isNotBlank(m.get("BEFORETHREEPERCENT")))
			bean.setBeforeThreePercent(Double.parseDouble(m.get("BEFORETHREEPERCENT") + ""));
		if (StringUtils.isNotBlank(m.get("ISNOPAYFORNEWYEAR")))
			bean.setNoPayForNewYear(Boolean.parseBoolean(m.get("ISNOPAYFORNEWYEAR") + ""));
		if (StringUtils.isNotBlank(m.get("ROUND_UP_TYPE")))
			bean.setROUND_UP_TYPE(Integer.parseInt(m.get("ROUND_UP_TYPE") + ""));
		if (StringUtils.isNotBlank(m.get("SCALE")))
			bean.setScale(Integer.parseInt(m.get("SCALE") + ""));
		if ("8".equals(code)) {
			JSONArray anomalyData = JSONArray.fromObject(m.get("EditRows"));// 不等额的期次数据
			averagePrincipalInterest = averagePrincipalInterest3(anomalyData,
					END_YEAR_INTEREST, _leaseTerm, residualPrincipal,
					_payCountOfYear, (m.get("PAY_WAY") + "").contains("期初")
							&& changeIssue == 1, date, changeIssue,
					DISCOUNT_MONEY, last_money, CALCULATE_WAY, bean);
			averagePrincipalInterest = manageLastIssue(averagePrincipalInterest, DateLXMoney);
		} else {
			averagePrincipalInterest = SkyEyeFinance.payTable(
					END_YEAR_INTEREST, _leaseTerm, residualPrincipal,
					last_money, 12 / _payCountOfYear, date, changeIssue,
					DISCOUNT_MONEY, (m.get("PAY_WAY") + "").contains("期初") ? 4
							: 1, CALCULATE_WAY, bean);

			// averagePrincipalInterest =
			// SkyEyeFinance.payTable(END_YEAR_INTEREST, _leaseTerm,
			// residualPrincipal, last_money,((m.get("PAY_WAY") +
			// "").contains("期初")
			// && changeIssue == 1)?1:0, 12/_payCountOfYear, date, changeIssue,
			// DISCOUNT_MONEY,(m.get("PAY_WAY") + "").contains("期初")?4:1);
			averagePrincipalInterest = manageLastIssue(
					averagePrincipalInterest, DateLXMoney);
		}
		// ----------------------------------
		// 不等额处理结束
		ln.addAll(averagePrincipalInterest);
		Map<String, Object> tm = new HashMap<String, Object>();
		tm.put("ln", ln);
		tm.put("END_YEAR_INTEREST", END_YEAR_INTEREST);
		return JSONObject.fromObject(tm);
	}

	public String calculateSave(Map<String, Object> m) {
		Map<String, Object> map = Dao.selectOne("PayTask.queryPay", m);
		System.out.println(m.toString());
		JSONArray rows = JSONArray.fromObject(m.get("myData"));
		// 插入支付表主表
		Dao.insert("PayTask.upgrade_payplan_change", m);
		System.out.println(m.toString());
		// 只有缩期或者展期的时候才会改变年率
		if ("4".equals(m.get("code_")) || "7".equals(m.get("code_"))) {
			Dao.update("PayTask.update_payplan_year_interest", m);
		}
		// 插入明细表
		List<Map<String, String>> list = getParsePayList(rows);
		for (Map<String, String> mapDate : list) {
			mapDate.put("PAY_ID", m.get("NEWID") + "");
			mapDate.put("ITEM_FLAG", "2");
			Dao.insert("PayTask.payplan_detail", mapDate);
		}

		// 插入明细的其他费用
		map.put("NEWID", m.get("NEWID") + "");
		Dao.insert("PayTask.payplan_detail_other", map);

		// 插入变更收取的其他费用
		Map<String, String> mo = new HashMap<String, String>();
		mo.put("新增收保险费",
				m.get("otherInsure") == null || "".equals(m.get("otherInsure")) ? ""
						: m.get("otherInsure") + "");
		mo.put("新增担保费",
				m.get("otherAssure") == null || "".equals(m.get("otherAssure")) ? ""
						: m.get("otherAssure") + "");
		mo.put("新增手续费",
				m.get("otherPoundage") == null
						|| "".equals(m.get("otherPoundage")) ? "" : m
						.get("otherPoundage") + "");
		insertPayplanDetailOther(mo, m.get("NEWID") + "");

		// // 修改财务明细表
		// insertPayplanBeginningOther(mo, m.get("PAYLIST_CODE") + "");

		// 修改支付表主表状态
		Map<String, String> temp = new HashMap<String, String>();
		temp.put("ID", m.get("NEWID") + "");
		if (m.get("status_") != null && (m.get("status_") + "").length() > 0) {
			temp.put("STATUS", m.get("status_") + "");
		} else {
			temp.put("STATUS", m.get("code_") + "");
		}
		Dao.update("PayTask.update_payplan", temp);

		// 变更保存参数
		temp = new HashMap<String, String>();
		temp.put("PAY_ID", m.get("NEWID") + "");
		temp.put("PARAM_NAME", "支付表变更");
		temp.put("PARAM_VALUE", m.get("PAYCHANGE_PARAMETER") + "");
		Dao.insert("PayTask.insert_paychange_parameter", temp);
		// // 删除财务表数据
		// Dao.delete("PayTask.deleteBeginning", m);
		// // 同步财务数据
		// Map<String, String> temp1 = new HashMap<String, String>();
		// // temp1.put("PAY_ID", ID + "");
		// temp1.put("PAYLIST_CODE", m.get("PAYLIST_CODE") + "");
		// temp1.put("PMT", "PMT租金");
		// temp1.put("ZJ", "租金");
		// temp1.put("SYBJ", "剩余本金");
		// temp1.put("D", "第");
		// temp1.put("QI", "期");
		// Dao.insert("PayTask.synchronizationBeginning", temp1);
		//
		// // 变更时插入fil_paychange_parameter(缩展期)
		// // 如果有多次变更的话先删除第一次变更数据
		// if ("4".equals(m.get("code_")) || "7".equals(m.get("code_"))
		// || "8".equals(m.get("code_"))) {
		//
		// Dao.delete("PayTask.del_fil_paychange_parameter", m);
		// // 删除后插入最新的数据
		// Dao.insert("PayTask.inert_fil_paychange_parameter", m);
		// }
		return forwardRepaymentJBPMNew(m);// 发起支付表变更流程，缩展期，不等额变更

	}

	public String forwardRepaymentJBPMNew(Map<String, Object> param) {
		List<Map<String, Object>> minPay = Dao.selectList(
				"PayTask.queryPayMinVersion_code", param);
		Object PAY_ID = (minPay == null || !(minPay.size() > 0)) ? param
				.get("NEWID") : minPay.get(0).get("ID");
		param.put("PAY_ID", PAY_ID);
		Map<String, Object> map = Dao.selectOne(
				"PayTask.forwardRepaymentShow1", param);
		System.out.println("--------------map=" + map);
		List<String> prcessList = JBPM.getDeploymentListByModelName("支付表变更",
				"", Security.getUser().getOrg().getPlatformId());
		if (prcessList != null && prcessList.size() > 0) {
			ProcessInstance processInstance = JBPM.startProcessInstanceById(
					prcessList.get(0) + "", map.get("PROJECT_ID") + "",
					map.get("CLIENT_ID") + "", map.get("PAYLIST_CODE") + "",
					map);
			param.put("JBPM_ID", processInstance.getId());
			// 保存欲归档文件
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("ID", map.get("ID"));
			temp.put("STATUS", param.get("code_"));
			Dao.update("PayTask.update_payplan", temp);

			String nextCode = new TaskService().getAssignee(processInstance
					.getId());
			return nextCode;
		} else {
			throw new ActionException("流程图不存在");
		}
	}

	public void calculateSave2(Map<String, Object> m) {
		// 测算保存后再次测算的需要删除第一次的记录（如果支付表里面有版本号小于0的就说明之前做过变更测算）
		// List<Object> listTemp =
		// Dao.selectList("PayTask.queryPayMinVersion_code",
		// m.get("PAYLIST_CODE") + "");
		// if (listTemp != null && listTemp.size() > 0) {
		// Map<String, Object> tt = new HashMap<String, Object>();
		// tt.put("PAYLIST_CODE", m.get("PAYLIST_CODE") + "");
		// changeDefeated(tt);// 调用失败方法删除之前的数据
		// }
		// JSONObject myData = JSONObject.fromObject(m.get("myData"));
		JSONArray rows = JSONArray.fromObject(m.get("myData"));
		// 插入支付表主表
		Dao.insert("PayTask.upgrade_payplan", m);
		// 只有缩期或者展期的时候才会改变年率
		if ("4".equals(m.get("code_")) || "7".equals(m.get("code_"))) {
			Dao.update("PayTask.update_payplan_year_interest", m);
		}
		// 插入明细表
		List<Map<String, String>> list = getParsePayList(rows);
		for (Map<String, String> map : list) {
			map.put("PAY_ID", m.get("NEWID") + "");
			map.put("ITEM_FLAG", "2");
			Dao.insert("PayTask.payplan_detail", map);
		}
		// 修改当期往下的利息为0(提前结清使用)
		Map<String, Object> mm = new HashMap<String, Object>();
		if (m.get("settleInfo") != null && !"".equals(m.get("settleInfo"))) {
			mm.put("ACCOUNT_DATE", JSONObject.fromObject(m.get("settleInfo"))
					.get("RENT_DATE"));
			mm.put("NEWID", m.get("NEWID"));
			Dao.update("BuyBack.detial_interest_zero", mm);
		}

		// 插入明细的其他费用
		Map<String, Object> map = Dao.selectOne("PayTask.queryPay", m);
		map.put("NEWID", m.get("NEWID") + "");
		Dao.insert("PayTask.payplan_detail_other", map);

		// 插入变更收取的其他费用
		Map<String, String> mo = new HashMap<String, String>();
		mo.put("新增收保险费",
				m.get("otherInsure") == null || "".equals(m.get("otherInsure")) ? ""
						: m.get("otherInsure") + "");
		mo.put("新增担保费",
				m.get("otherAssure") == null || "".equals(m.get("otherAssure")) ? ""
						: m.get("otherAssure") + "");
		mo.put("新增手续费",
				m.get("otherPoundage") == null
						|| "".equals(m.get("otherPoundage")) ? "" : m
						.get("otherPoundage") + "");
		insertPayplanDetailOther(mo, m.get("NEWID") + "");

		// 修改财务明细表
		insertPayplanBeginningOther(mo, m.get("PAYLIST_CODE") + "");

		// 修改支付表主表状态
		Map<String, String> temp = new HashMap<String, String>();
		temp.put("ID", map.get("ID") + "");
		if (m.get("status_") != null && (m.get("status_") + "").length() > 0) {
			temp.put("STATUS", m.get("status_") + "");
		} else {
			temp.put("STATUS", m.get("code_") + "");
		}
		Dao.update("PayTask.update_payplan", temp);

		// 提前还款时候保存一些变更参数code_=9
		if ("9".equals(m.get("code_")) && m.get("PAYCHANGE_PARAMETER") != null) {
			temp = new HashMap<String, String>();
			temp.put("PAY_ID", m.get("NEWID") + "");
			temp.put("PARAM_NAME", "提前还款参数");
			temp.put("PARAM_VALUE", m.get("PAYCHANGE_PARAMETER") + "");
			Dao.insert("PayTask.insert_paychange_parameter", temp);
		}
		// // 删除财务表数据
		// Dao.delete("PayTask.deleteBeginning", m);
		// // 同步财务数据
		// Map<String, String> temp1 = new HashMap<String, String>();
		// //temp1.put("PAY_ID", ID + "");
		// temp1.put("PAYLIST_CODE", m.get("PAYLIST_CODE") + "");
		// temp1.put("PMT", "PMT租金");
		// temp1.put("ZJ", "租金");
		// temp1.put("SYBJ", "剩余本金");
		// temp1.put("D", "第");
		// temp1.put("QI", "期");
		// Dao.insert("PayTask.synchronizationBeginning", temp1);

		// 变更时插入fil_paychange_parameter(缩展期)
		// 如果有多次变更的话先删除第一次变更数据
		if ("4".equals(m.get("code_")) || "7".equals(m.get("code_"))
				|| "8".equals(m.get("code_"))) {

			Dao.delete("PayTask.del_fil_paychange_parameter", m);
			// 删除后插入最新的数据
			Dao.insert("PayTask.inert_fil_paychange_parameter", m);
		}

	}

	public void changeDefeated(Map<String, Object> m) {
		Map<String, Object> selectOne = Dao.selectOne(
				"PayTask.queryPayMinVersion_code", m.get("PAYLIST_CODE") + "");
		String ID = selectOne.get("ID") + "";
		Dao.delete("PayTask.deletePay", ID);
		Dao.delete("PayTask.deletePayDetail", ID);// 删除未通过版本
		// 删除beginning里面变更插入的费用，费用名称以“新增”做标识
		Dao.delete("PayTask.deletePayBeginning", m.get("PAYLIST_CODE") + "");
		// 修改支付表的状态，审批不通过就把支付表状态改为正常
		Map<String, String> temp = new HashMap<String, String>();
		// Map<String,Object> map = Dao.selectOne("PayTask.queryPay", m);
		temp.put("STATUS", "0");
		Dao.update("PayTask.update_payplan", temp);
		// 删除财务表数据
		Dao.delete("PayTask.deleteBeginning", m);
		// 同步财务数据
		Map<String, String> temp1 = new HashMap<String, String>();
		temp1.put("PAY_ID", ID + "");
		temp1.put("PAYLIST_CODE", m.get("PAYLIST_CODE") + "");
		temp1.put("PMT", "PMT租金");
		temp1.put("ZJ", "租金");
		temp1.put("SYBJ", "剩余本金");
		temp1.put("D", "第");
		temp1.put("QI", "期");
		Dao.insert("PayTask.synchronizationBeginning", temp1);

	}

	public void changeSucceed(Map<String, Object> m) {
		Map<String, Object> status = Dao.selectOne(
				"PayTask.queryProject_status", m.get("PAYLIST_CODE") + "");
		Map<String, Object> selectOne = null;
		// 如果项目状态小于20及在立项审批流程中的时候正式版本可能重新刷一次
		// int projectStatus = 20;
		// if (status != null) {
		// projectStatus = status.get("STATUS") == null
		// || "".equals(status.get("STATUS")) ? 20 : Integer
		// .parseInt(status.get("STATUS") + "");
		// }
		// if (projectStatus < 20) {
		// selectOne = (Map) Dao.selectOne("PayTask.queryPayMinVersion_code2",
		// m.get("PAYLIST_CODE") + "");
		// } else {
		selectOne = Dao.selectOne("PayTask.queryPayMinVersion_code",
				m.get("PAYLIST_CODE") + "");
		// }
		// Map selectOne = (Map) Dao.selectOne("PayTask.queryPayBycode",
		// m.get("PAYLIST_CODE") + "");//二次通过修复
		Object ID = selectOne.get("ID");
		// Object PROJECT_ID = selectOne.get("PROJECT_ID");

		// 删除财务表数据
		Dao.delete("PayTask.deleteBeginning", m);
		// 同步财务数据
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("PAY_ID", ID);
		temp.put("PAYLIST_CODE", m.get("PAYLIST_CODE"));
		temp.put("PMT", "PMT租金");
		temp.put("ZJ", "租金");
		temp.put("SYBJ", "剩余本金");
		temp.put("D", "第");
		temp.put("QI", "期");
		Dao.insert("PayTask.synchronizationBeginning", temp);

		// temp.put("PROJECT_ID",PROJECT_ID);
		// if(!StringUtils.isBlank(PROJECT_ID)&&!StringUtils.isBlank(m.get("PAYLIST_CODE")))
		// Dao.update("PayTask.updateBeginning", temp);
		// 修改支付表版本号和支付表状态改为正常
		temp = new HashMap<String, Object>();
		temp.put("ID", ID);
		if ("66".equals(null == selectOne ? "0" : selectOne.get("STATUS")
				.toString())) {
			temp.put("STATUS", "6");
		} else {
			temp.put("STATUS", "0");
		}

		Object VERSION_CODE = selectOne.get("VERSION_CODE");
		Integer VERSION_CODE_FORMAT = VERSION_CODE == null ? 0 : Math
				.abs(Integer.valueOf(VERSION_CODE.toString()));
		temp.put("VERSION_CODE", Math.abs(VERSION_CODE_FORMAT));
		Dao.update("PayTask.update_payplan", temp);
		// 如果是提前结清变更流程，则改成功之后的状态为提前结清
		if ("6".equals(status.get("PLAN_STATUS"))) {
			temp.put("STATUS", "6");
			temp.remove("VERSION_CODE");
			Dao.update("PayTask.update_payplan", temp);
			// 修改项目状态为提前还款
			Dao.update("PayTask.updateProject_status", m.get("PAYLIST_CODE")
					+ "");
			temp.put("PAYLIST_CODE", m.get("PAYLIST_CODE") + "");
			Dao.update("PayTask.update_IS_END_STATUS2", temp);
		} else if ("5".equals(status.get("PLAN_STATUS"))) {// 如果是回购流程，则改成功之后的状态为回购
			temp.put("STATUS", "5");
			temp.remove("VERSION_CODE");
			Dao.update("PayTask.update_payplan", temp);
			temp.put("PAYLIST_CODE", m.get("PAYLIST_CODE"));
			Dao.update("PayTask.update_IS_END_STATUS", temp);
		} else {// 缩展期和不等额变更修改状态
			Dao.update("PayTask.update_fil_paychange_parameter_IS_END_STATUS",
					m);
		}
	}

	// 修改支付表的起租时间
	public void updatePayDate(Map<String, Object> m) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("ID", m.get("pay_id") + "");
		map.put("START_DATE", m.get("start_date") + "");
		// 更新支付表主表
		Dao.update("PayTask.update_payplan", map);
		// 更新明细表lease_period
		Map<String, Object> pay = Dao.selectOne("PayTask.queryPayByID",
				m.get("pay_id") + "");
		List<Map<String, String>> list = rowDenormaliser(m.get("pay_id") + "");
		String start_date = m.get("start_date") + "";
		// start_date = Util.getPayDateByFirstDate(m.get("start_date") + "",
		// pay.get("LEASE_PERIOD") + "");
		// ----------------------------------------------------
		start_date = UtilDate.getMonth(start_date,
				-2 * Integer.parseInt(pay.get("LEASE_PERIOD") + ""));
		// 如果为期末支付，则第一期支付时间往后延一期
		if ("1".equals(pay.get("PAY_WAY")) || "3".equals(pay.get("PAY_WAY"))) {
			start_date = Util.getMonth(start_date,
					Integer.parseInt(pay.get("LEASE_PERIOD") + ""));
		}
		for (int i = 0; i < list.size(); i++) {
			start_date = Util.getMonth(start_date,
					Integer.parseInt(pay.get("LEASE_PERIOD") + ""));
			Map<String, Object> tm = new HashMap<String, Object>();
			tm.put("PAY_DATE", start_date);
			tm.put("PAY_ID", m.get("pay_id"));
			tm.put("PERIOD_NUM", i + 1);
			Dao.update("PayTask.update_detailPayDate", tm);
		}

		// 同步财务数据
		Map<String, String> temp = new HashMap<String, String>();
		temp.put("PAY_ID", pay.get("ID") + "");
		temp.put("PAYLIST_CODE", pay.get("PAYLIST_CODE") + "");
		temp.put("PMT", "PMT租金");
		temp.put("ZJ", "租金");
		temp.put("SYBJ", "剩余本金");
		temp.put("D", "第");
		temp.put("QI", "期");
		// 删除财务表数据
		Dao.delete("PayTask.deleteBeginning", temp);
		Dao.insert("PayTask.synchronizationBeginning", temp);
	}

	// 查询组织好支付表明细发回json数据
	public JSONObject payDetailShow(Map<String, Object> param) {
		List<Map<String, String>> rowDenormaliser = rowDenormaliser(param
				.get("ID") + "");
		// 资产包
		if (param.containsKey("PROJECT_MODEL")
				&& "6".equals(param.get("PROJECT_MODEL"))) {
			String zkl = Dao.selectOne("PayTask.queryZKL", param);
			for (Map<String, String> map : rowDenormaliser) {
				double zcbxjl = Double.parseDouble(null == map.get("bj") ? "0"
						: map.get("bj")) * 100 / Double.parseDouble(zkl);
				zcbxjl = Math.round(zcbxjl * 100);
				map.put("zcbxjl", zcbxjl / 100 + "");
			}
		}
		JSONObject json = new JSONObject();
		json.put("data", JSONArray.fromObject(rowDenormaliser));
		return json;
	}

	// 查询组织好支付表明细发回json数据
	public JSONObject payDetailShowNew(Map<String, Object> param) {
		List<Map<String, String>> rowDenormaliser = rowDenormaliserNew(param
				.get("ID") + "");

		JSONObject json = new JSONObject();
		json.put("data", JSONArray.fromObject(rowDenormaliser));
		return json;
	}

	// 查询组织好支付表明细发回json数据
	public Page payDetailShow1(Map<String, Object> param) {
		Page page = new Page();
		List<Map<String, String>> rowDenormaliser = rowDenormaliser(param
				.get("ID") + "");
		page.addDate(JSONArray.fromObject(rowDenormaliser),
				rowDenormaliser.size());
		return page;
	}

	// 提前还款准备数据
	public Map<String, Object> forwardRepaymentShow(Map<String, Object> param) {
		if (param.get("PAY_ID") == null || "".equals(param.get("PAY_ID"))) {
			List<Map<String, Object>> minPay = Dao.selectList(
					"queryPayMinVersion_code3", param);
			param.put("PAY_ID", minPay.get(0).get("ID"));
		}
		Map<String, Object> map = Dao.selectOne(
				"PayTask.forwardRepaymentShow1", param);
		Map<String, Object> map2 = Dao.selectOne(
				"PayTask.forwardRepaymentShow2", param);
		List<Map<String, String>> list = rowDenormaliser(map.get("ID") + "");
		Map<String, Object> rmap = new HashMap<String, Object>();
		rmap.put("map", map);
		rmap.put("map2", JSONObject.fromObject(map2));
		rmap.put("list", JSONArray.fromObject(list));
		return rmap;
	}

	public String forwardRepaymentJBPM(Map<String, Object> param) {
		List<Map<String, Object>> minPay = Dao.selectList(
				"PayTask.queryPayMinVersion_code", param);
		Object PAY_ID = (minPay == null || !(minPay.size() > 0)) ? param
				.get("NEWID") : minPay.get(0).get("ID");
		param.put("PAY_ID", PAY_ID);
		Map<String, Object> map = Dao.selectOne(
				"PayTask.forwardRepaymentShow1", param) == null ? new HashMap<String, Object>()
				: (Map<String, Object>) Dao.selectOne(
				"PayTask.forwardRepaymentShow1", param);
		// map.put("SUPPLIER_ID",'')
		// Object SUPPLIER_ID = Dao.selectOne("PayTask.query_SUPPLIER_ID",
		// param);
		// map.put("SUPPLIER_ID", SUPPLIER_ID);
		map.put("PROJECT_ID", (minPay == null || !(minPay.size() > 0)) ? null
				: minPay.get(0).get("PROJECT_ID"));
		List<String> prcessList = JBPM.getDeploymentListByModelName("提前还租");
		if (prcessList != null && prcessList.size() > 0) {
			ProcessInstance processInstance = JBPM.startProcessInstanceById(
					prcessList.get(0) + "", map.get("PROJECT_ID") + "",
					map.get("CLIENT_ID") + "", map.get("PAYLIST_CODE") + "",
					map);
			param.put("JBPM_ID", processInstance.getId());
			// new
			// ProjectContractManagerService().doAddProjectContractList(param.get("JBPM_ID").toString());//
			// 保存欲归档文件
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("ID", map.get("ID"));
			temp.put("STATUS", param.get("code_"));
			Dao.update("PayTask.update_payplan", temp);

			// 分期流程后更新fil_paychange_parameter数据
			Dao.update("PayTask.update_fil_paychange_parameter", param);
			return "流程发起成功";
		} else {
			throw new ActionException("流程图不存在");
		}
	}

	public Map<String, Object> forwardRepaymentJBPMMessage(
			Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean flag = false;
		String info = "流程发起失败!";
		String data = "";
		List<Map<String, Object>> minPay = Dao.selectList(
				"PayTask.queryPayMinVersion_code", param);
		Object PAY_ID = (minPay == null || !(minPay.size() > 0)) ? param
				.get("NEWID") : minPay.get(0).get("ID");
		param.put("PAY_ID", PAY_ID);
		Map<String, Object> map = Dao.selectOne(
				"PayTask.forwardRepaymentShow1", param) == null ? new HashMap<String, Object>()
				: (Map<String, Object>) Dao.selectOne(
				"PayTask.forwardRepaymentShow1", param);
		// map.put("SUPPLIER_ID",'')
		// Object SUPPLIER_ID = Dao.selectOne("PayTask.query_SUPPLIER_ID",
		// param);
		// map.put("SUPPLIER_ID", SUPPLIER_ID);
		map.put("PROJECT_ID", (minPay == null || !(minPay.size() > 0)) ? null
				: minPay.get(0).get("PROJECT_ID"));
		List<String> prcessList = JBPM.getDeploymentListByModelName("提前还租");
		if (prcessList != null && prcessList.size() > 0) {
			ProcessInstance processInstance = JBPM.startProcessInstanceById(
					prcessList.get(0) + "", map.get("PROJECT_ID") + "",
					map.get("CLIENT_ID") + "", map.get("PAYLIST_CODE") + "",
					map);
			param.put("JBPM_ID", processInstance.getId());
			// new
			// ProjectContractManagerService().doAddProjectContractList(param.get("JBPM_ID").toString());//
			// 保存欲归档文件
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("ID", map.get("ID"));
			temp.put("STATUS", param.get("code_"));
			Dao.update("PayTask.update_payplan", temp);
			data = new TaskService().getAssignee(processInstance.getId());

			// 分期流程后更新fil_paychange_parameter数据
			Dao.update("PayTask.update_fil_paychange_parameter", param);
			info = "下一步操作人为: ";
			flag = true;
		} else {
			throw new ActionException("流程图不存在");
		}
		result.put("flag", flag);
		result.put("info", info);
		result.put("data", data);
		return result;
	}

	// ----------------------------------------计算报价的支付表开始
	// 报价测算
	public JSONObject quoteCalculateTest(Map<String, Object> m)
			throws Exception {
		double annualRate = Double.parseDouble(m.get("annualRate") + "");
		int _leaseTerm = Integer.parseInt(m.get("_leaseTerm") + "");
		double residualPrincipal = Double.parseDouble(m
				.get("residualPrincipal") + "");
		int _payCountOfYear = Integer.parseInt(m.get("_payCountOfYear") + "");
		double LAST_MONEY = 0d;
		if (m.containsKey("LAST_MONEY")
				&& StringUtils.isNotBlank(m.get("LAST_MONEY"))) {
			LAST_MONEY = Double.parseDouble(m.get("LAST_MONEY") + "");
		}
		String PAY_WAY = m.get("pay_way") + "";
		String date = m.get("date") + "";// 起租日
		double DateLXMoney = 0d;

		if (m.get("date1") == null || m.get("date1").equals("")) {
			date = null;
		} else {
			// 计算起租日和还款日中间利息差
			DateLXMoney = this.getDateLiXiMoney(date, m.get("date1") + "",
					residualPrincipal, annualRate, 12 / _payCountOfYear,
					PAY_WAY);

			String date1 = m.get("date1") + "";// 还款日
			date = date1;
		}
		DateLXMoney = Util.formatDouble2(DateLXMoney);
		m.put("DateLXMoney", DateLXMoney);

		List<Map<String, String>> averagePrincipalInterest = null;
		Map<String, Object> tm = new HashMap<String, Object>();
		double DISCOUNT_MONEY = Double
				.parseDouble(m.get("DISCOUNT_MONEY") == null
						|| "".equals(m.get("DISCOUNT_MONEY")) ? "0" : (m
						.get("DISCOUNT_MONEY") + ""));
		Map<String, Object> dataDictionaryMap = new HashMap<String, Object>();
		dataDictionaryMap.put("TYPE", "支付方式");
		dataDictionaryMap.put("CODE", PAY_WAY);
		dataDictionaryMap = DataDictionaryService
				.queryDataDictionaryByTypeAndCode(dataDictionaryMap);
		PAY_WAY = dataDictionaryMap.get("FLAG") + "";
		if (PAY_WAY.contains("期初") && date != null) {
			date = Util.getMonth(date, -12 / _payCountOfYear * 1);
		}
		String CALCULATE_WAY = "PMT";
		if (StringUtils.isNotBlank(m.get("CALCULATE_TYPE") + "")) {
			CALCULATE_WAY = m.get("CALCULATE_TYPE") + "";
		}

		FinanceBean bean = new FinanceBean();
		if (StringUtils.isNotBlank(m.get("BEFORETHREEPERCENT")))
			bean.setBeforeThreePercent(Double.parseDouble(m
					.get("BEFORETHREEPERCENT") + ""));
		else if (StringUtils.isNotBlank(m.get("BEFORETHREE_PERCENT")))
			bean.setBeforeThreePercent(Double.parseDouble(m
					.get("BEFORETHREE_PERCENT") + ""));

		if (StringUtils.isNotBlank(m.get("ISNOPAYFORNEWYEAR")))
			bean.setNoPayForNewYear(Boolean.parseBoolean(m
					.get("ISNOPAYFORNEWYEAR") + ""));
		if (StringUtils.isNotBlank(m.get("ROUND_UP_TYPE")))
			bean.setROUND_UP_TYPE(Integer.parseInt(m.get("ROUND_UP_TYPE") + ""));
		if (StringUtils.isNotBlank(m.get("SCALE")))
			bean.setScale(Integer.parseInt(m.get("SCALE") + ""));
		if (StringUtils.isNotBlank(m.get("AMOUNT") + ""))
			bean.setEqu_count(Integer.parseInt(m.get("AMOUNT") + ""));
		if (StringUtils.isNotBlank(m.get("BEFORETERM")))
			bean.setBeforeTerm(Integer.parseInt(m.get("BEFORETERM") + ""));
		if (StringUtils.isNotBlank(m.get("ISTAX")))
			bean.setIsTAX(m.get("ISTAX")+"");

		if(StringUtils.isNotBlank(m.get("POUNDAGE_PRICE")))
			bean.setFEES_PRICE(Double.parseDouble(m.get("POUNDAGE_PRICE")+""));
		else if(StringUtils.isNotBlank(m.get("FEES_PRICE")))
			bean.setFEES_PRICE(Double.parseDouble(m.get("FEES_PRICE")+""));

		if (StringUtils.isNotBlank(m.get("MANAGEMENT_FEETYPE")))
			bean.setMANAGEMENT_FEETYPE(m.get("MANAGEMENT_FEETYPE")+"");

		if (StringUtils.isNotBlank(m.get("MQGLF")))
			bean.setMQGLF(Double.parseDouble(m.get("MQGLF")+""));

		if (StringUtils.isNotBlank(m.get("LXTQSQ")))
			bean.setLXTQSQ(m.get("LXTQSQ")+"");
		if(StringUtils.isNotBlank(m.get("YEARDAYS")))
			bean.setYearDays(Integer.parseInt(m.get("YEARDAYS")+""));
		if(StringUtils.isNotBlank(m.get("SFCELX")))
			bean.setSFCELX(m.get("SFCELX")+"");
		if(StringUtils.isNotBlank(m.get("FIRSTDAYS")))
			bean.setFirstDays(Integer.parseInt(m.get("FIRSTDAYS")+""));

		if(StringUtils.isNotBlank(m.get("LASTDAYS")))
			bean.setLastDays(Integer.parseInt(m.get("LASTDAYS")+""));

		DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");

		if(StringUtils.isNotBlank(m.get("START_DATE")))
			bean.setStartDate(fmt.parse(m.get("START_DATE")+""));

		if(StringUtils.isNotBlank(m.get("REPAYMENT_DATE")))
			bean.setRepayDate(fmt.parse(m.get("REPAYMENT_DATE")+""));

		bean.setPayWay(PAY_WAY);

		if(StringUtils.isNotBlank(m.get("LEASE_PERIOD")))
			bean.setLeasePeriod(Integer.parseInt(m.get("LEASE_PERIOD")+""));

		if(StringUtils.isNotBlank(m.get("LEASE_TERM")))
			bean.setLeaseTerm(Integer.parseInt(m.get("LEASE_TERM")+""));

		bean.addRepayDate();
		System.out.println("-------------------1111111111111-----"+bean.getStartDate()+"--"+bean.getRepayDate()+"------bean.getFirstDays="+bean.getFirstDays()+"--------------bean.getLastDays()="+bean.getLastDays());
		if (PAY_WAY.contains("不等额") || "2".equals(PAY_WAY)
				|| "3".equals(PAY_WAY)) {
			// 如果是第一次进来m.get("EditRows")为空，就按照正常的计算
			// if (m.get("EditRows") == null || "".equals(m.get("EditRows"))
			// || "[]".equals(m.get("EditRows"))) {
			// averagePrincipalInterest = SkyEyeFinance.payTable(annualRate,
			// _leaseTerm, residualPrincipal, LAST_MONEY, 12/_payCountOfYear,
			// date, 1,
			// DISCOUNT_MONEY,PAY_WAY.contains("期初")?4:1,CALCULATE_WAY);
			// tm.put("code", 8);
			// } else {
			//
			// JSONArray anomalyData =
			// JSONArray.fromObject(m.get("EditRows"));// 不等额的期次数据
			// averagePrincipalInterest = averagePrincipalInterest3(
			// anomalyData, annualRate, _leaseTerm, residualPrincipal,
			// _payCountOfYear, PAY_WAY.contains("期初"), date, 1,
			// DISCOUNT_MONEY,LAST_MONEY,CALCULATE_WAY);
			// tm.put("code", 8);
			// }
			JSONArray anomalyData = JSONArray.fromObject(m.get("EditRows"));// 不等额的期次数据
			averagePrincipalInterest = SkyEyeFinance.payTable(annualRate,
					_leaseTerm, residualPrincipal, LAST_MONEY,
					12 / _payCountOfYear, date, 1, DISCOUNT_MONEY,
					PAY_WAY.contains("期初") ? 2 : 3, anomalyData, CALCULATE_WAY,
					bean);

			tm.put("code", 8);
			System.out.println("-------------------averagePrincipalInterest="
					+ averagePrincipalInterest);
		} else if (PAY_WAY.contains("等额本息") || "1".equals(PAY_WAY)
				|| "4".equals(PAY_WAY)) {
			if (date == null) {
				date = "";
			}
			averagePrincipalInterest = SkyEyeFinance.payTable(annualRate,
					_leaseTerm, residualPrincipal, LAST_MONEY,
					12 / _payCountOfYear, date, 1, DISCOUNT_MONEY,
					PAY_WAY.contains("期初") ? 4 : 1, CALCULATE_WAY, bean);

		} else if (PAY_WAY.contains("等额本金") || "5".equals(PAY_WAY)
				|| "6".equals(PAY_WAY)) {
			if (date == null) {
				date = "";
			}

			averagePrincipalInterest = SkyEyeFinance.payTable(annualRate,
					_leaseTerm, residualPrincipal, LAST_MONEY,
					12 / _payCountOfYear, date, 1, DISCOUNT_MONEY,
					PAY_WAY.contains("期初") ? 6 : 5, CALCULATE_WAY, bean);

		} else if (PAY_WAY.contains("平息") || "7".equals(PAY_WAY)) {
			if (date == null) {
				date = "";
			}

			averagePrincipalInterest = SkyEyeFinance.payTable(annualRate,
					_leaseTerm, residualPrincipal, LAST_MONEY,
					12 / _payCountOfYear, date, 1, DISCOUNT_MONEY, 7,
					CALCULATE_WAY, bean);

		}
		tm.put("ln", averagePrincipalInterest);
		tm.put("GDLX_PRICE", bean.getGDLX_PRICE());
		return JSONObject.fromObject(tm);
	}


	// 获取还款计划应收日期  add gengchangbao JZZL-189
	public JSONObject quoteCalculateTest_New(Map<String, Object> m)
			throws Exception {
		int _payCountOfYear = Integer.parseInt(m.get("_payCountOfYear") + "");
		String PAY_WAY = m.get("pay_way") + "";
		String date = m.get("date") + "";// 起租日

		if (m.get("date1") == null || m.get("date1").equals("")) {
			date = null;
		}


		List<Map<String, String>> averagePrincipalInterest = null;
		Map<String, Object> tm = new HashMap<String, Object>();
		Map<String, Object> dataDictionaryMap = new HashMap<String, Object>();
		dataDictionaryMap.put("TYPE", "支付方式");
		dataDictionaryMap.put("CODE", PAY_WAY);
		dataDictionaryMap = DataDictionaryService
				.queryDataDictionaryByTypeAndCode(dataDictionaryMap);
		PAY_WAY = dataDictionaryMap.get("FLAG") + "";
		if (PAY_WAY.contains("期初") && date != null) {
			date = Util.getMonth(date, -12 / _payCountOfYear * 1);
		}

		FinanceBean bean = new FinanceBean();
		if (StringUtils.isNotBlank(m.get("BEFORETHREEPERCENT")))
			bean.setBeforeThreePercent(Double.parseDouble(m
					.get("BEFORETHREEPERCENT") + ""));
		else if (StringUtils.isNotBlank(m.get("BEFORETHREE_PERCENT")))
			bean.setBeforeThreePercent(Double.parseDouble(m
					.get("BEFORETHREE_PERCENT") + ""));

		if (StringUtils.isNotBlank(m.get("ISNOPAYFORNEWYEAR")))
			bean.setNoPayForNewYear(Boolean.parseBoolean(m
					.get("ISNOPAYFORNEWYEAR") + ""));
		if (StringUtils.isNotBlank(m.get("ROUND_UP_TYPE")))
			bean.setROUND_UP_TYPE(Integer.parseInt(m.get("ROUND_UP_TYPE") + ""));
		if (StringUtils.isNotBlank(m.get("SCALE")))
			bean.setScale(Integer.parseInt(m.get("SCALE") + ""));
		if (StringUtils.isNotBlank(m.get("AMOUNT") + ""))
			bean.setEqu_count(Integer.parseInt(m.get("AMOUNT") + ""));
		if (StringUtils.isNotBlank(m.get("BEFORETERM")))
			bean.setBeforeTerm(Integer.parseInt(m.get("BEFORETERM") + ""));
		if (StringUtils.isNotBlank(m.get("ISTAX")))
			bean.setIsTAX(m.get("ISTAX")+"");

		if(StringUtils.isNotBlank(m.get("POUNDAGE_PRICE")))
			bean.setFEES_PRICE(Double.parseDouble(m.get("POUNDAGE_PRICE")+""));
		else if(StringUtils.isNotBlank(m.get("FEES_PRICE")))
			bean.setFEES_PRICE(Double.parseDouble(m.get("FEES_PRICE")+""));

		if (StringUtils.isNotBlank(m.get("MANAGEMENT_FEETYPE")))
			bean.setMANAGEMENT_FEETYPE(m.get("MANAGEMENT_FEETYPE")+"");

		if (StringUtils.isNotBlank(m.get("MQGLF")))
			bean.setMQGLF(Double.parseDouble(m.get("MQGLF")+""));

		if (StringUtils.isNotBlank(m.get("LXTQSQ")))
			bean.setLXTQSQ(m.get("LXTQSQ")+"");
		if(StringUtils.isNotBlank(m.get("YEARDAYS")))
			bean.setYearDays(Integer.parseInt(m.get("YEARDAYS")+""));
		if(StringUtils.isNotBlank(m.get("SFCELX")))
			bean.setSFCELX(m.get("SFCELX")+"");
		if(StringUtils.isNotBlank(m.get("FIRSTDAYS")))
			bean.setFirstDays(Integer.parseInt(m.get("FIRSTDAYS")+""));

		if(StringUtils.isNotBlank(m.get("LASTDAYS")))
			bean.setLastDays(Integer.parseInt(m.get("LASTDAYS")+""));

		DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");

		if(StringUtils.isNotBlank(m.get("START_DATE")))
			bean.setStartDate(fmt.parse(m.get("START_DATE")+""));

		if(StringUtils.isNotBlank(m.get("REPAYMENT_DATE")))
			bean.setRepayDate(fmt.parse(m.get("REPAYMENT_DATE")+""));

		bean.setPayWay(PAY_WAY);

		if(StringUtils.isNotBlank(m.get("LEASE_PERIOD")))
			bean.setLeasePeriod(Integer.parseInt(m.get("LEASE_PERIOD")+""));

		if(StringUtils.isNotBlank(m.get("LEASE_TERM")))
			bean.setLeaseTerm(Integer.parseInt(m.get("LEASE_TERM")+""));

		bean.addRepayDate();

		averagePrincipalInterest = new ArrayList<Map<String,String>>();
		int LEASE_TERM = bean.getLeaseTerm();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,String> payDateMap = null;
		for (int i = 0; i < LEASE_TERM; i++) {
			payDateMap = new HashMap<String, String>();
			payDateMap.put("PAY_DATE", df.format((Date) bean.getDataList().get(Integer.valueOf(i + 1))));
			averagePrincipalInterest.add(payDateMap);
		}
		tm.put("ZFSJ", averagePrincipalInterest);
		return JSONObject.fromObject(tm);
	}


//	/**
//	 * 利息提前收取
//	 *
//	 * @param param
//	 *            需要有LXTQSQ key值 当此值为2表示提前收取
//	 * @param list
//	 * @author:King 2014-3-31
//	 */
//	public void getLXTQSQ(Map<String, Object> param,
//			List<Map<String, String>> list) {
//		if (StringUtils.isNotBlank(param.get("LXTQSQ"))
//				&& "2".equals(param.get("LXTQSQ"))) {
//			double ratedouble = 0;
//			for (Map<String, String> map : list) {
//				double lx = Double.parseDouble(map.get("lx"));
//				ratedouble += lx;
//				map.put("lx", "0");
//				map.put("zj", map.get("bj"));
//			}
//			// 固定利息合计
//			ratedouble = Util.formatDouble2(ratedouble);
//			param.put("GDLX_PRICE", ratedouble);
//		}
//	}

	/**
	 * 处理管理费分期收取的情况
	 *
	 * @param param
	 *            需要SQFS key值 SQFS值为3表示分期收取 FEES_PRICE 手续费金额
	 * @param list
	 * @author:King 2014-3-31
	 */
	public void glfSQFS(Map<String, Object> param,
						List<Map<String, String>> list) {
		if (StringUtils.isNotBlank(param.get("SQFS"))
				&& "3".equals(param.get("SQFS"))) {
			int lease_term = list.size();
			double FEES_PRICE = 0;
			try {
				if (StringUtils.isNotBlank(param.get("FEES_PRICE")))
					FEES_PRICE = Double.parseDouble(param.get("FEES_PRICE")
							+ "");
			} catch (Exception e) {
			}
			FEES_PRICE = FEES_PRICE / lease_term;
			FEES_PRICE = Math.round(FEES_PRICE * 100) / 100;
			for (Map<String, String> map : list) {
				map.put("glf", FEES_PRICE + "");
				double zj = Double.parseDouble(map.get("zj")) + FEES_PRICE;
				map.put("zj", zj + "");
			}
		}
	}

	// 报价测算保存
	public static void quoteCalculateSave(Map<String, Object> m) {
		// JSONObject myData = JSONObject.fromObject(m.get("myData"));
		JSONArray rows = JSONArray.fromObject(m.get("rows"));
		// 插入支付表主表
		m.put("YEAR_INTEREST", m.get("annualRate"));
		m.put("TOPRIC_SUBFIRENT", m.get("residualPrincipal"));
		int _payCountOfYear = Integer.parseInt(m.get("_payCountOfYear") + "");
		m.put("LEASE_TERM", m.get("_leaseTerm"));
		m.put("LEASE_PERIOD", 12 / _payCountOfYear);
		m.put("PAY_WAY", m.get("pay_way"));
		m.put("START_DATE", m.get("date"));
		m.put("PAYLIST_CODE", m.get("project_code") + "-1");
		m.put("PROJECT_ID", m.get("project_id"));
		m.put("VERSION_CODE", -1);
		m.put("STATUS", "0");
		m.put("CREATE_PERSON", Security.getUser().getId());
		m.put("CHANGE_START_FLAG", "0");
		m.put("PAY_STATUS", "0");
		m.put("MANAGEMENT_FEETYPE", 2);
		m.put("CALCULATE_WAY", 1);
		m.put("UPDATE_STATUS", "0");
		try {
			Dao.insert("PayTask.insert_payplan", m);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 像支付表子表插入费用项
		// String PAY_ID = null;
		try {
			if (m != null && m.get("NEWID") != null
					&& !m.get("NEWID").equals("")) {
				m.put("PAY_ID", m.get("NEWID"));
				new projectService().createPayParam(m);
			}
		} catch (Exception e) {
			Log.error("插入其他费用错误" + e.getMessage());
		}
		// 插入明细表
		List<Map<String, String>> list = getParsePayList(rows);
		for (Map<String, String> map : list) {
			map.put("PAY_ID", m.get("NEWID") + "");
			map.put("ITEM_FLAG", "2");
			Dao.insert("PayTask.payplan_detail", map);
		}
		list.get(list.size() - 1);

		DateUtil.getPAYLIST_END_DATE(m.get("project_id") + "",
				list.get(list.size() - 1).get("PAY_DATE"), m.get("pay_way")
						+ "", 12 / _payCountOfYear);
	}

	// 报价测算保存
	public static void quoteCalculateSaveSCHEME(Map<String, Object> m) {
		// JSONObject myData = JSONObject.fromObject(m.get("myData"));
		JSONArray rows = JSONArray.fromObject(m.get("rows"));
		// 插入支付表主表

		// 像支付表子表插入费用项
		// String PAY_ID = null;
		// try {
		// if (m != null && m.get("SCHEME_ID_SEQ") != null &&
		// !m.get("SCHEME_ID_SEQ").equals("")) {
		// m.put("PAY_ID", m.get("SCHEME_ID_SEQ"));
		// new projectService().createPayParamScheme(m);
		// }
		// } catch (Exception e) {
		// Log.error("插入其他费用错误" + e.getMessage());
		// }
		// 插入明细表
		List<Map<String, String>> list = getParsePayList(rows);
		for (Map<String, String> map : list) {
			map.put("PAY_ID", m.get("SCHEME_ID_SEQ") + "");
			map.put("ITEM_FLAG", "2");
			Dao.insert("PayTask.payplan_detailSCHEME", map);
		}
		list.get(list.size() - 1);

	}

	/**
	 * 报价测算 更新或保存 pay_id 有值就是更新，否则就是新增
	 *
	 */
	public static void quoteCalculateSaveSCHEME2(Map<String, Object> params) {
		JSONArray rows = JSONArray.fromObject(params.get("rows"));
		// 插入明细表
		List<Map<String, String>> list = getParsePayList(rows);
		for (Map<String, String> map : list) {
			map.put("PAY_ID", params.get("SCHEME_ID_SEQ") + "");
			map.put("ITEM_FLAG", "2");
			Dao.insert("PayTask.payplan_detailSCHEME", map);
		}
		list.get(list.size() - 1);

	}

	// 根据合同id查询支付表id
	public int queryIdByProjectId(String ProjectId) {
		return Dao.selectInt("PayTask.queryIdByProjectId", ProjectId);
	}

	// ----------------------------------------计算报价的支付表结束

	// ----------------------------------------------------抽出来供调用的方法
	/*
	 * 计算的还款计划处理最后一期后返回 DateLXMoney:起租日和还款日之间的利息，放在第一期利息中
	 */
	public static List<Map<String, String>> manageLastIssue(
			List<Map<String, String>> list, double DateLXMoney) {
		if (list.size() <= 1)
			return list;
		if (DateLXMoney > 0d) {
			// 处理第一期利息和租金
			Map<String, String> mapMoney = list.get(0);
			double zjMoney = Double.parseDouble(mapMoney.get("zj"))
					+ DateLXMoney;
			zjMoney = Util.formatDouble2(zjMoney);
			double lxMoney = Double.parseDouble(mapMoney.get("lx"))
					+ DateLXMoney;
			lxMoney = Util.formatDouble2(lxMoney);
			mapMoney.put("zj", zjMoney + "");
			mapMoney.put("lx", lxMoney + "");
			// 处理第一期利息和租金
		}

		Map<String, String> map1 = list.get(list.size() - 2);
		Map<String, String> map = list.get(list.size() - 1);
		double bj = Util.formatDouble2(Double.parseDouble(map1.get("sybj")));
		double zj = Util.formatDouble2(Double.parseDouble(map.get("zj")));
		double lx = Util.formatDouble2(Util.subDouble(zj, bj));

		/****************************************************************/
		// 当最后一期利息为负数时，变换配平方式为 最后一期的利息=上一期的剩余本金*利率 最后一期的租金=本金+利息 King
		// 2014-07-26//
		if (lx < 0) {
			double rate = Util.div(
					Double.parseDouble(map1.get("lx") + ""),
					Double.parseDouble(list.get(list.size() - 3).get("sybj")
							+ ""), 100);
			lx = Util.formatDouble2(Util.mul(rate, bj));
			zj = Util.formatDouble2(Util.addDouble(lx, bj));
		}
		/****************************************************************/
		map.put("bj", bj + "");
		map.put("lx", lx + "");
		map.put("sybj", "0");
		map.put("zj", zj + "");

		/****************************** King 2014-9-18****格式化金额 避免科学技术法 ***************************************************/
		for (Map<String, String> map2 : list) {
			map2.put("zj",
					BigDecimal.valueOf(Double.parseDouble(map2.get("zj")))
							.toPlainString());
			map2.put("bj",
					BigDecimal.valueOf(Double.parseDouble(map2.get("bj")))
							.toPlainString());
			map2.put("lx",
					BigDecimal.valueOf(Double.parseDouble(map2.get("lx")))
							.toPlainString());
			map2.put("sybj",
					BigDecimal.valueOf(Double.parseDouble(map2.get("sybj")))
							.toPlainString());
		}
		/*************************************************************************************/
		return list;
	}

	// 插入其他费用Detail
	public static void insertPayplanDetailOther(Map<String, String> map,
												String PAY_ID) {
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map<String, String> tm = new HashMap<String, String>();
			Map.Entry m = (Map.Entry) it.next();
			if (m.getValue() != null && !"".equals(m.getValue())) {
				tm.put("ITEM_NAME", m.getKey() + "");
				tm.put("ITEM_MONEY", m.getValue() + "");
				tm.put("ITEM_FLAG", "4");
				tm.put("PAY_ID", PAY_ID);
				tm.put("FLAG_TYPE", "1");
				Dao.insert("PayTask.payplan_detail", tm);
			}
		}
	}

	// 插入其他费用beginning
	public static void insertPayplanBeginningOther(Map<String, String> map,
												   String PAYLIST_CODE) {
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map<String, String> tm = new HashMap<String, String>();
			Map.Entry m = (Map.Entry) it.next();
			if (m.getValue() != null && !"".equals(m.getValue())) {
				tm.put("BEGINNING_NAME", m.getKey() + "");
				tm.put("BEGINNING_MONEY", m.getValue() + "");
				// tm.put("BEGINNING_FLAG","0");
				tm.put("ITEM_FLAG", "4");
				tm.put("PAYLIST_CODE", PAYLIST_CODE);
				Dao.insert("PayTask.insert_r_beginning", tm);
			}
		}

		// Map selectOne = (Map)
		// Dao.selectOne("PayTask.queryPayMinVersion_code2", PAYLIST_CODE);
		Map selectOne = (Map) Dao.selectOne("PayTask.queryPayMinVersion_code3",
				PAYLIST_CODE);
		if (selectOne == null || selectOne.size() < 0)
			return;
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("PAYLIST_CODE", PAYLIST_CODE);
		temp.put("PROJECT_ID", selectOne.get("PROJECT_ID") + "");
		if (!StringUtils.isBlank(selectOne.get("PROJECT_ID"))
				&& !StringUtils.isBlank(PAYLIST_CODE))
			Dao.update("PayTask.updateBeginning", temp);
	}

	/* 按照期次分组后显示的数据再次转换为一个资金项目为一条数据 */
	public static List<Map<String, String>> getParsePayList(JSONArray list) {
		List<Map<String, String>> ln = new ArrayList<Map<String, String>>();
		LinkedHashMap<String, String> mn = null;
		for (int i = 0; i < list.size(); i++) {
			JSONObject map = (JSONObject) list.get(i);
			// 拿到每期期次
			String PERIOD_NUM = map.get("qc") + "";
			String PAY_DATE = map.get("PAY_DATE") + "";
			if (map.containsKey("zj")) {
				mn = new LinkedHashMap<String, String>();
				mn.put("ITEM_NAME", "租金");
				mn.put("ITEM_MONEY", map.get("zj") + "");
				mn.put("PERIOD_NUM", PERIOD_NUM);
				mn.put("PAY_DATE", PAY_DATE);
				if ("yes".equals(map.get("lock"))) {
					mn.put("LOCKED_FLAG", "1");
				}
				ln.add(mn);
			}

			if (map.containsKey("bj")) {
				mn = new LinkedHashMap<String, String>();
				mn.put("ITEM_NAME", "本金");
				mn.put("ITEM_MONEY", map.get("bj") + "");
				mn.put("PERIOD_NUM", PERIOD_NUM);
				mn.put("PAY_DATE", PAY_DATE);
				ln.add(mn);
			}
			if (map.containsKey("lx")) {
				mn = new LinkedHashMap<String, String>();
				mn.put("ITEM_NAME", "利息");
				mn.put("ITEM_MONEY", map.get("lx") + "");
				mn.put("PERIOD_NUM", PERIOD_NUM);
				mn.put("PAY_DATE", PAY_DATE);
				ln.add(mn);
			}
			if (map.containsKey("lxzzs")) {
				mn = new LinkedHashMap<String, String>();
				mn.put("ITEM_NAME", "利息增值税");
				mn.put("ITEM_MONEY", map.get("lxzzs") + "");
				mn.put("PERIOD_NUM", PERIOD_NUM);
				mn.put("PAY_DATE", PAY_DATE);
				ln.add(mn);
			}
			if (map.containsKey("sxf")) {
				mn = new LinkedHashMap<String, String>();
				mn.put("ITEM_NAME", "手续费");
				mn.put("ITEM_MONEY", map.get("sxf") + "");
				mn.put("PERIOD_NUM", PERIOD_NUM);
				mn.put("PAY_DATE", PAY_DATE);
				ln.add(mn);
			}
			if (map.containsKey("glf")) {
				mn = new LinkedHashMap<String, String>();
				mn.put("ITEM_NAME", "管理费");
				mn.put("ITEM_MONEY", map.get("glf") + "");
				mn.put("PERIOD_NUM", PERIOD_NUM);
				mn.put("PAY_DATE", PAY_DATE);
				ln.add(mn);
			}
			if (map.containsKey("sybj")) {
				mn = new LinkedHashMap<String, String>();
				mn.put("ITEM_NAME", "剩余本金");
				mn.put("ITEM_MONEY",
						map.get("sybj") == null
								|| "".equals(map.get("sybj") + "")
								|| "null".equals(map.get("sybj") + "") ? "0"
								: map.get("sybj") + "");
				mn.put("PERIOD_NUM", PERIOD_NUM);
				mn.put("PAY_DATE", PAY_DATE);
				ln.add(mn);
			}
			if (map.containsKey("PMTzj")) {
				mn = new LinkedHashMap<String, String>();
				mn.put("ITEM_NAME", "PMT租金");
				mn.put("ITEM_MONEY", map.get("PMTzj") + "");
				mn.put("PERIOD_NUM", PERIOD_NUM);
				mn.put("PAY_DATE", PAY_DATE);
				ln.add(mn);
			}
		}
		return ln;

	}

	/* 按照期次分组后显示的数据再次转换为一个资金项目为一条数据 */
	public static List<Map<String, String>> getParsePayListNew(JSONArray list) {
		List<Map<String, String>> ln = new ArrayList<>();
		LinkedHashMap<String, String> mn;
		for (Object aList : list) {
			JSONObject map = (JSONObject) aList;
			// 拿到每期期次
			String PERIOD_NUM = map.get("PERIOD_NUM") + "";
			String PAY_DATE = map.get("PAY_DATE") + "";
			if (map.containsKey("ZJ")) {
				mn = new LinkedHashMap<>();
				mn.put("ITEM_NAME", "租金");
				mn.put("ITEM_MONEY", map.get("ZJ") + "");
				mn.put("PERIOD_NUM", PERIOD_NUM);
				mn.put("PAY_DATE", PAY_DATE);
				if ("yes".equals(map.get("lock"))) {
					mn.put("LOCKED_FLAG", "1");
				}
				ln.add(mn);
			}
			if (map.containsKey("BJ")) {
				mn = new LinkedHashMap<String, String>();
				mn.put("ITEM_NAME", "本金");
				mn.put("ITEM_MONEY", map.get("BJ") + "");
				mn.put("PERIOD_NUM", PERIOD_NUM);
				mn.put("PAY_DATE", PAY_DATE);
				ln.add(mn);
			}
			if (map.containsKey("LX")) {
				mn = new LinkedHashMap<String, String>();
				mn.put("ITEM_NAME", "利息");
				mn.put("ITEM_MONEY", map.get("LX") + "");
				mn.put("PERIOD_NUM", PERIOD_NUM);
				mn.put("PAY_DATE", PAY_DATE);
				ln.add(mn);
			}
			if (map.containsKey("SXF")) {
				mn = new LinkedHashMap<String, String>();
				mn.put("ITEM_NAME", "手续费");
				mn.put("ITEM_MONEY", map.get("SXF") + "");
				mn.put("PERIOD_NUM", PERIOD_NUM);
				mn.put("PAY_DATE", PAY_DATE);
				ln.add(mn);
			}
			if (map.containsKey("GLF")) {
				mn = new LinkedHashMap<String, String>();
				mn.put("ITEM_NAME", "管理费");
				mn.put("ITEM_MONEY", map.get("GLF") + "");
				mn.put("PERIOD_NUM", PERIOD_NUM);
				mn.put("PAY_DATE", PAY_DATE);
				ln.add(mn);
			}
			if (map.containsKey("LXZZS")) {
				mn = new LinkedHashMap<String, String>();
				mn.put("ITEM_NAME", "利息增值税");
				mn.put("ITEM_MONEY", map.get("LXZZS") + "");
				mn.put("PERIOD_NUM", PERIOD_NUM);
				mn.put("PAY_DATE", PAY_DATE);
				ln.add(mn);
			}
			if (map.containsKey("SYBJ")) {
				mn = new LinkedHashMap<String, String>();
				mn.put("ITEM_NAME", "剩余本金");
				mn.put("ITEM_MONEY",
						map.get("SYBJ") == null
								|| "".equals(map.get("SYBJ") + "")
								|| "null".equals(map.get("SYBJ") + "") ? "0"
								: map.get("SYBJ") + "");
				mn.put("PERIOD_NUM", PERIOD_NUM);
				mn.put("PAY_DATE", PAY_DATE);
				ln.add(mn);
			}
			if (map.containsKey("PMTZJ")) {
				mn = new LinkedHashMap<>();
				mn.put("ITEM_NAME", "PMT租金");
				mn.put("ITEM_MONEY", map.get("PMTZJ") + "");
				mn.put("PERIOD_NUM", PERIOD_NUM);
				mn.put("PAY_DATE", PAY_DATE);
				ln.add(mn);
			}

			if (map.containsKey("PMTZJ")) {
				mn = new LinkedHashMap<String, String>();
				mn.put("ITEM_NAME", "PMT租金");
				mn.put("ITEM_MONEY", map.get("PMTZJ") + "");
				mn.put("PERIOD_NUM", PERIOD_NUM);
				mn.put("PAY_DATE", PAY_DATE);
				ln.add(mn);
			}
		}
		return ln;

	}

	// /**
	// * 等额本息测算数据列表
	// * @param annualRate 实际年利率
	// * @param _leaseTerm 租赁期数
	// * @param residualPrincipal 融资额(剩余本金)
	// * @param _payCountOfYear 租赁周期(每年支付期次)
	// // * @param whetherBeginningPeriod 是否期初(期初true/期末false)
	// * @param whetherBeginningPeriod 是否期初(期初 1 /期末 0)
	// * @param date 第一期还款日期
	// * @param changeIssue 变更开始期次
	// * @param DISCOUNT_MONEY 贴息金额
	// * @param fv 未来期望值(尾款金额)
	// * @return
	// * @author King 2015年3月2日
	// */
	// public static List<Map<String, String>> averagePrincipalInterest(
	// double annualRate, int _leaseTerm, double residualPrincipal,
	// int _payCountOfYear, int whetherBeginningPeriod, String date,
	// int changeIssue, double DISCOUNT_MONEY,double fv) {
	// List<Map<String, String>> lt = new ArrayList<Map<String, String>>();
	// // double residualPrincipal_ = residualPrincipal;
	// double zj = Finance.pmt(annualRate/_payCountOfYear, _leaseTerm,
	// -residualPrincipal, fv, whetherBeginningPeriod);
	// // double zj = FinancialAlgLib.pmt(residualPrincipal_, annualRate,
	// // _leaseTerm, _payCountOfYear, whetherBeginningPeriod);
	//
	// // -----贴息处理--------------------
	// if (DISCOUNT_MONEY > 0) {
	// if (zj * _leaseTerm - residualPrincipal < DISCOUNT_MONEY)
	// throw new ActionException("贴息金额过大");
	// double DISCOUNT_MONEYP = DISCOUNT_MONEY / _leaseTerm;
	// zj = zj - DISCOUNT_MONEYP;
	// double rate1 = (12 / _payCountOfYear)* Finance.rate(_leaseTerm, zj,
	// -residualPrincipal, 0,whetherBeginningPeriod);
	// annualRate = rate1;
	// }
	// zj = Util.formatDouble2(zj);
	// for (int i = 1; i <= _leaseTerm; i++) {
	// LinkedHashMap<String, String> mw = new LinkedHashMap<String, String>();
	// double lx = 0;
	// if (!(whetherBeginningPeriod==1 && changeIssue == 1 && i == 1)) {//
	// 如果为期初（并且开始期次为1）的时候第一期利息为0
	// lx = Util.div(Util.mul(residualPrincipal, annualRate),
	// _payCountOfYear, 2);
	// }
	// lx = Util.formatDouble2(lx);
	// double bj = Util.subDouble(zj, lx);
	// bj = Util.formatDouble2(bj);
	// double sybj = Util.subDouble(residualPrincipal, bj);
	// sybj = Util.formatDouble2(sybj);
	//
	// residualPrincipal = sybj;
	// if (date != null && !"".equals(date)) {
	// date = Util.getMonth(date, 12 / _payCountOfYear).toString();
	// }
	// mw.put("zj", zj + "");
	// mw.put("bj", bj + "");
	// mw.put("lx", lx + "");
	// mw.put("sybj", sybj + "");
	// mw.put("PMTzj", zj + "");
	// mw.put("qc", changeIssue + i - 1 + "");
	// mw.put("PAY_DATE", date + "");
	// lt.add(mw);
	// }
	// return lt;
	// }

	// /*
	// * 计算等额本金租金方法 annualRate 年利率 _leaseTerm 总期次 residualPrincipal 融资额（剩余本金）
	// * _payCountOfYear 每年支付次数 whetherBeginningPeriod 是否期初（期初true/期末false） date
	// * 第一期日期 changeIssue 从第几期开始
	// */
	// public static List<Map<String, String>> averagePrincipalInterest2(
	// double annualRate, double _leaseTerm, double residualPrincipal,
	// int _payCountOfYear, boolean whetherBeginningPeriod, String date,
	// int changeIssue, double DISCOUNT_MONEY) {
	// List<Map<String, String>> lt = new ArrayList<Map<String, String>>();
	// double residualPrincipal_ = residualPrincipal;
	// double bj = Util.formatDouble10(residualPrincipal_ / _leaseTerm);
	//
	// // TODO -----贴息处理--等额本金先删除掉贴息--------------------
	// // if (DISCOUNT_MONEY > 0) {
	// // // int _payCountOfYear =
	// // // Integer.parseInt(param.get("_payCountOfYear") + "");
	// // // double _leaseTerm = Double.parseDouble(param.get("_leaseTerm") +
	// // // "");
	// // if (zj * _leaseTerm - residualPrincipal < DISCOUNT_MONEY) throw new
	// // ActionException("贴息金额过大");
	// // double DISCOUNT_MONEYP = DISCOUNT_MONEY / _leaseTerm;
	// // // double residualPrincipal =
	// // // Double.parseDouble(param.get("residualPrincipal") + "");
	// // // double zj = Double.parseDouble(list.get(0).get("zj"));
	// // zj = zj - DISCOUNT_MONEYP;
	// // // String PAY_WAY = param.get("pay_way") + "";
	// // double rate1 = (12 / _payCountOfYear) * Finance.rate(_leaseTerm, zj,
	// // -residualPrincipal, 0, whetherBeginningPeriod ? 1 : 0);
	// // // double rate2 = rate1 / (12 / _payCountOfYear);
	// // annualRate = rate1;
	// // }
	// // --------------------------------
	// bj = Util.formatDouble2(bj);
	// for (int i = 1; i <= _leaseTerm; i++) {
	// LinkedHashMap<String, String> mw = new LinkedHashMap<String, String>();
	// // double bj = Finance.ppmt(annualRate/_payCountOfYear, i,
	// // (int)_leaseTerm, -residualPrincipal_);
	// // double lx = Util.subDouble(zj, bj);
	// // double sybj = Util.subDouble(residualPrincipal,bj);
	// double lx = 0;
	// if (!(whetherBeginningPeriod && changeIssue == 1 && i == 1)) {//
	// 如果为期初（并且开始期次为1）的时候第一期利息为0
	// // lx = residualPrincipal * annualRate / _payCountOfYear;
	// lx = Util.div(Util.mul(residualPrincipal, annualRate),
	// _payCountOfYear, 2);
	// }
	// lx = Util.formatDouble2(lx);
	// double zj = Util.addDouble(bj, lx);
	// zj = Util.formatDouble2(zj);
	// double sybj = Util.subDouble(residualPrincipal, bj);
	// sybj = Util.formatDouble2(sybj);
	//
	// residualPrincipal = sybj;
	// if (date != null && !"".equals(date)) {
	// date = Util.getMonth(date, 12 / _payCountOfYear).toString();
	// }
	// mw.put("zj", zj + "");
	// mw.put("bj", bj + "");
	// mw.put("lx", lx + "");
	// mw.put("sybj", sybj + "");
	// mw.put("PMTzj", zj + "");
	// mw.put("qc", changeIssue + i - 1 + "");
	// mw.put("PAY_DATE", date + "");
	// lt.add(mw);
	// }
	// return lt;
	// }

	/* 计算不等额支付的 */
	public List<Map<String, String>> averagePrincipalInterest3(
			JSONArray anomalyData, double annualRate, int _leaseTerm,
			double _topricSubfirent, int _payCountOfYear,
			boolean whetherBeginningPeriod, String date, int changeIssue,
			double tx, double last_money, String CALCULATE, FinanceBean bean)
			throws Exception {
		// 2.开始配平，内差法
		// 得到不等额的期次
		// JSONObject json = (JSONObject)anomalyData.get(0);
		// Map anomalyMap = JsonUtil.toMap(json);
		List<Map<String, String>> tmpList = SkyEyeFinance.payTable(annualRate,
				_leaseTerm, _topricSubfirent, last_money, 12 / _payCountOfYear,
				date, changeIssue, tx, whetherBeginningPeriod ? 4 : 1,
				CALCULATE, bean);

		boolean flag = true;
		int tmp = 0;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = changeIssue; i < _leaseTerm + changeIssue; i++) {
			flag = true;
			for (int j = 0; j < anomalyData.size(); j++) {
				JSONObject json = (JSONObject) anomalyData.get(j);
				Map anomalyMap = JsonUtil.toMap(json);
				if ((i + "").equals(anomalyMap.get("qc") + "")) {
					anomalyMap.put("lock", "yes");
					list.add(anomalyMap);
					tmp++;
					flag = false;
					break;
				}
			}
			if (flag) {
				Map<String, String> mn = tmpList.get(i - tmp - changeIssue);
				mn.put("qc", i + "");
				list.add(mn);
			}
		}
		boolean forceBreak = false;
		double y = 0, x, c = 0, jd = 0.0000001, side = -1;
		double minDeviation = 1;
		int changePeriodNum = 1;
		do {
			double sybj = _topricSubfirent, ownPriceSum = 0d;
			for (int i = 0; i < _leaseTerm + changePeriodNum - 1; i++) {
				Map<String, String> map_ = list.get(i);
				double lx = 0;
				/*
				 * if (!"yes".equals(map_.get("lock"))) { lx = sybj *
				 * (annualRate / _payCountOfYear); } else { lx =
				 * Double.parseDouble(map_.get("lx") + ""); } if
				 * (whetherBeginningPeriod && changeIssue == 1 && i == 0) lx =
				 * 0; double zj =
				 * Util.formatDouble10("yes".equals(map_.get("lock")) ?
				 * Double.parseDouble(map_.get("bj") + "") + lx :
				 * (Double.parseDouble(map_.get("zj")) + y)); double bj =
				 * Util.subDouble(zj, lx);
				 */
				lx = sybj * (annualRate / _payCountOfYear);
				if (whetherBeginningPeriod && changeIssue == 1 && i == 0) {
					lx = 0;
				}
				double zj = Util
						.formatDouble10("yes".equals(map_.get("lock")) ? Double
								.parseDouble(map_.get("zj") + "") : (Double
								.parseDouble(map_.get("zj")) + y));
				double bj = Util.subDouble(zj, lx);
				sybj = Util.subDouble(sybj, bj);
				map_.put("zj", zj + "");
				map_.put("bj", bj + "");
				map_.put("lx", lx + "");
				map_.put("sybj", sybj + "");
				map_.put("PMTzj", zj + "");
				ownPriceSum = Util.addDouble(ownPriceSum, bj);
			}

			// 计算误差
			x = Util.subDouble(_topricSubfirent, ownPriceSum);
			// 初始化内差值，当误差大于0时为增，否则为减
			if (side == -1)
				side = x > 0 ? 0.1 : -0.1;
			// 调整内差值，以及精度
			if (x * side > 0) {
				side = -side;
				if (jd == 10) {
					// 如果最小误差两次一样，退出计算
					if (minDeviation == Math.abs(x))
						break;
					// 保存最小误差
					if (minDeviation > Math.abs(x))
						minDeviation = Math.abs(x);
				} else {
					jd *= 10;
				}
			}
			// //计算误差
			// x = subDouble(_topricSubfirent,ownPriceSum);
			// //初始化内差值，当误差大于0时为增，否则为减
			// if(side==-1)side=x>0?0.1:-0.1;
			// //调整内差值，以及精度
			// if(x*side>0){side = -side;jd =jd==10?10:jd*10;}
			// //如果最小误差两次一样，退出计算
			// if(minDeviation==Math.abs(x))break;
			// //保存最小误差
			// if(minDeviation>Math.abs(x))minDeviation=Math.abs(x);
			// //重新设定配平参数
			y = -side / jd;
			// 运算100次，如果出现运算100次以后还不能配平的，把它增大即可。
		} while (!forceBreak && Math.abs(x) > 0.01 && c++ < 1000);

		/*
		 * 不等额计算完毕之后再进行租金取整计算(同时重新计算还款日【经过配平出来后还款日期错位，需要重新算】) 期次也不对，从新计算拍期次
		 */
		// date = Util.getMonth(date, 12 / _payCountOfYear).toString();
		String date1 = list.get(0).get("PAY_DATE");
		int qc = changeIssue;
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			if (!"yes".equals(map.get("lock"))) {
				double zj = Util
						.formatDouble2(Double.parseDouble(map.get("zj")));
				double lx = 0;
				if (!(whetherBeginningPeriod && changeIssue == 1 && i == 0)) {// 如果为期初（并且开始期次为1）的时候第一期利息为0
					lx = Util.div(Util.mul(_topricSubfirent, annualRate),
							_payCountOfYear, 2);
				}
				lx = Util.formatDouble2(lx);
				double bj = Util.subDouble(zj, lx);
				bj = Util.formatDouble2(bj);
				double sybj = Util.subDouble(_topricSubfirent, bj);
				sybj = Util.formatDouble2(sybj);
				_topricSubfirent = sybj;
				zj = Util.formatDouble2(zj);
				map.put("zj", zj + "");
				map.put("PMTzj", zj + "");
				map.put("bj", bj + "");
				map.put("lx", lx + "");
				map.put("sybj", sybj + "");
				map.put("PAY_DATE", date1);
				map.put("qc", qc + i + "");
				if (date1 != null && !date1.equals("") && !date1.equals("null")) {
					date1 = Util.getMonth(date1, 12 / _payCountOfYear)
							.toString();
				}

			} else {
				double lx = 0;
				if (!(whetherBeginningPeriod && changeIssue == 1 && i == 0)) {
					lx = Util.div(Util.mul(_topricSubfirent, annualRate),
							_payCountOfYear, 2);
				}
				lx = Util.formatDouble2(lx);
				double zj = Double.parseDouble(map.get("zj"));
				double bj = Util.subDouble(zj, lx);
				bj = Util.formatDouble2(bj);
				double sybj = Util.subDouble(_topricSubfirent, bj);
				sybj = Util.formatDouble2(sybj);
				_topricSubfirent = sybj;
				map.put("sybj", sybj + "");
				map.put("lx", lx + "");
				map.put("bj", bj + "");
				map.put("zj", zj + "");
				map.put("PAY_DATE", date1);
				map.put("qc", qc + i + "");
				if (date1 != null && !date1.equals("") && !date1.equals("null")) {
					date1 = Util.getMonth(date1, 12 / _payCountOfYear)
							.toString();
				}
			}
		}
		return list;
	}

	// 查询明细表按照期次把行转为列
	public static List<Map<String, String>> rowDenormaliser(String pay_id) {
		List<Map<String, Object>> list = Dao.selectList("PayTask.payDetail",
				pay_id);
		List<Map<String, String>> lw = new ArrayList<Map<String, String>>();
		if (list.isEmpty() || list.size() < 1)
			return lw;
		Map<String, String> mw = null;
		int num = Integer.parseInt(list.get(list.size() - 1).get("PERIOD_NUM")
				+ "");
		// 开始期次
		int t_num = Integer.parseInt(list.get(0).get("PERIOD_NUM") + "");
		for (int i = t_num; i <= num; i++) {
			mw = new HashMap<String, String>();
			Set set = null;
			for (Map<String, Object> m : list) {
				if ((i + "").equals(m.get("PERIOD_NUM") + "")
						&& "租金".equals(m.get("ITEM_NAME"))) {
					mw.put("zj", m.get("ITEM_MONEY") + "");
					set = new HashSet();
					set.add(m.get("PAY_DATE"));
				} else if ((i + "").equals(m.get("PERIOD_NUM") + "")
						&& "本金".equals(m.get("ITEM_NAME"))) {
					mw.put("bj", m.get("ITEM_MONEY") + "");
					set = new HashSet();
					set.add(m.get("PAY_DATE"));
				} else if ((i + "").equals(m.get("PERIOD_NUM") + "")
						&& "利息".equals(m.get("ITEM_NAME"))) {
					mw.put("lx", m.get("ITEM_MONEY") + "");
					set = new HashSet();
					set.add(m.get("PAY_DATE"));
				} else if ((i + "").equals(m.get("PERIOD_NUM") + "")
						&& "利息增值税".equals(m.get("ITEM_NAME"))) {
					mw.put("lxzzs", m.get("ITEM_MONEY") + "");
					set = new HashSet();
					set.add(m.get("PAY_DATE"));
				} else if ((i + "").equals(m.get("PERIOD_NUM") + "")
						&& "手续费".equals(m.get("ITEM_NAME"))) {
					mw.put("sxf", m.get("ITEM_MONEY") + "");
					set = new HashSet();
					set.add(m.get("PAY_DATE"));
				} else if ((i + "").equals(m.get("PERIOD_NUM") + "")
						&& "管理费".equals(m.get("ITEM_NAME"))) {
					mw.put("glf", m.get("ITEM_MONEY") + "");
					set = new HashSet();
					set.add(m.get("PAY_DATE"));
				} else if ((i + "").equals(m.get("PERIOD_NUM") + "")
						&& "剩余本金".equals(m.get("ITEM_NAME"))) {
					mw.put("sybj", m.get("ITEM_MONEY") + "");
					set = new HashSet();
					set.add(m.get("PAY_DATE"));
				} else if ((i + "").equals(m.get("PERIOD_NUM") + "")
						&& "PMT租金".equals(m.get("ITEM_NAME"))) {
					mw.put("PMTzj", m.get("ITEM_MONEY") + "");
					set = new HashSet();
					set.add(m.get("PAY_DATE"));
				}
			}
			Iterator iterator = set.iterator();
			mw.put("PAY_DATE", iterator.next() + "");
			mw.put("qc", i + "");
			lw.add(mw);
		}
		// 如果pmt租金为空的情况，将租金复制给pmt租金
		for (Map<String, String> mt : lw) {
			if (mt.get("PMTzj") == null || "".equals(mt.get("PMTzj"))) {
				mt.put("PMTzj", mt.get("zj"));
			}
		}
		return lw;
	}

	// 查询明细表按照期次把行转为列
	public static List<Map<String, String>> rowDenormaliserNew(String pay_id) {
		List<Map<String, Object>> list = Dao.selectList("PayTask.payDetailNew",
				pay_id);
		List<Map<String, String>> lw = new ArrayList<Map<String, String>>();
		if (list.isEmpty() || list.size() < 1)
			return lw;
		Map<String, String> mw = null;
		int num = Integer.parseInt(list.get(list.size() - 1).get("PERIOD_NUM")
				+ "");
		// 开始期次
		int t_num = Integer.parseInt(list.get(0).get("PERIOD_NUM") + "");
		for (int i = t_num; i <= num; i++) {
			mw = new HashMap<String, String>();
			Set set = null;
			for (Map<String, Object> m : list) {
				if ((i + "").equals(m.get("PERIOD_NUM") + "")
						&& "租金".equals(m.get("ITEM_NAME"))) {
					mw.put("zj", m.get("ITEM_MONEY") + "");
					set = new HashSet();
					set.add(m.get("PAY_DATE"));
				} else if ((i + "").equals(m.get("PERIOD_NUM") + "")
						&& "本金".equals(m.get("ITEM_NAME"))) {
					mw.put("bj", m.get("ITEM_MONEY") + "");
					set = new HashSet();
					set.add(m.get("PAY_DATE"));
				} else if ((i + "").equals(m.get("PERIOD_NUM") + "")
						&& "利息".equals(m.get("ITEM_NAME"))) {
					mw.put("lx", m.get("ITEM_MONEY") + "");
					set = new HashSet();
					set.add(m.get("PAY_DATE"));
				} else if ((i + "").equals(m.get("PERIOD_NUM") + "")
						&& "剩余本金".equals(m.get("ITEM_NAME"))) {
					mw.put("sybj", m.get("ITEM_MONEY") + "");
					set = new HashSet();
					set.add(m.get("PAY_DATE"));
				} else if ((i + "").equals(m.get("PERIOD_NUM") + "")
						&& "PMT租金".equals(m.get("ITEM_NAME"))) {
					mw.put("PMTzj", m.get("ITEM_MONEY") + "");
					set = new HashSet();
					set.add(m.get("PAY_DATE"));
				}
			}
			Iterator iterator = set.iterator();
			mw.put("PAY_DATE", iterator.next() + "");
			mw.put("qc", i + "");
			lw.add(mw);
		}
		// 如果pmt租金为空的情况，将租金复制给pmt租金
		for (Map<String, String> mt : lw) {
			if (mt.get("PMTzj") == null || "".equals(mt.get("PMTzj"))) {
				mt.put("PMTzj", mt.get("zj"));
			}
		}
		return lw;
	}

	/**
	 * 获取列转行的还款计划明细数据
	 *
	 * @param map
	 * @return
	 * @author:King 2013-9-27
	 */
	public List<Map<String, Object>> doShowRentPlanDetail(Map<String, Object> map) {
		List<Map<String, Object>> list = Dao.selectList("PayTask.doShowRentPlanDetail", map);
		if (list.isEmpty() || list.size() < 1) {
			return list;
		}
		return list;
	}

	public List<Map<String, Object>> doShowRentPlanDetailScheme(
			Map<String, Object> map) {
		List<Map<String, Object>> list = Dao.selectList(
				"PayTask.doShowRentPlanDetailScheme", map);
		if (list.isEmpty() || list.size() < 1) {
			return list;
		}
		return list;
	}

	/**
	 * 查看还款计划明细页面的头信息
	 *
	 * @param PAY_ID
	 * @return
	 * @author:King 2013-9-28
	 */
	public Map<String, Object> doShowRentHeadByPayId(String PAY_ID) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isBlank(PAY_ID))
			return map;
		map.put("ID", PAY_ID);
		map.put("_TYPE", "租赁周期");
		return Dao.selectOne("PayTask.queryRentHead", map);
	}

	public List<Map<String, Object>> queryPaymentMouldDetail(
			Map<String, Object> m) {
		List<Map<String, Object>> list = Dao.selectList(
				"PayTask.queryPaymentMouldDetailByType", m);
		return list;
	}

	public List<Map<String, Object>> queryPaymentByPaylistCode(
			Map<String, Object> m) {
		List<Map<String, Object>> list = Dao.selectList(
				"PayTask.queryPaymentByPaylistCode", m);
		return list;
	}

	/**
	 * 根据还款计划id查询明细中的其他费用
	 *
	 * @param ID
	 * @return
	 * @author:King 2013-9-28
	 */
	public List<Map<String, Object>> doShowRentDetailOtherFeeListByPayId(
			String ID) {
		if (StringUtils.isBlank(ID))
			return new ArrayList<>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_TYPE", "应收项目类型");
		map.put("PAY_ID", ID);
		return Dao.selectList("PayTask.doShowRentDetailOtherFeeListByPayId", map);
	}

	/**
	 * 根据项目id查询项目方案信息
	 *
	 * @param PROJECT_ID
	 * @return
	 * @author:King 2013-9-28
	 */
	public List<Map<String, Object>> doShowProjSchemeInfoByProjectId(
			String PROJECT_ID) {
		if (StringUtils.isBlank(PROJECT_ID))
			return new ArrayList<Map<String, Object>>();
		return Dao.selectList("PayTask.doShowProjSchemeInfoByProjectId",
				PROJECT_ID);
	}

	// /**
	// * 山重 根据起租确认日及还款计划编号计算付款日期
	// *
	// * @param START_DATE
	// * 起租日确认日
	// * @param PAYLIST_CODE
	// * 还款计划编号
	// * @author:King 2013-10-19
	// */
	// public void doUpdatePayDateByPaylistCode(String START_DATE,
	// String PAYLIST_CODE) {
	// String leaseBuyDate = null;
	// String insuranceDate = null;
	// String DBBailDateStr = null;
	// // 5. 更新数据库的起租确认日期
	// int day = Integer.parseInt(START_DATE.substring(START_DATE
	// .lastIndexOf("-") + 1));
	// if (day <= 5) {
	// leaseBuyDate = START_DATE.substring(0, START_DATE.lastIndexOf("-"))
	// + "-15";
	// } else if (day <= 15) {
	// leaseBuyDate = START_DATE.substring(0, START_DATE.lastIndexOf("-"))
	// + "-25";
	// } else if (day <= 25) {
	// leaseBuyDate = Util.getMonth(START_DATE, 1);
	// leaseBuyDate = leaseBuyDate.substring(0,
	// leaseBuyDate.lastIndexOf("-"))
	// + "-05";
	// } else {
	// leaseBuyDate = Util.getMonth(START_DATE, 1);
	// leaseBuyDate = leaseBuyDate.substring(0,
	// leaseBuyDate.lastIndexOf("-"))
	// + "-15";
	// }
	// // 保险日期
	// if (day <= 20) {
	// insuranceDate = START_DATE
	// .substring(0, START_DATE.lastIndexOf("-")) + "-25";
	// } else {
	// insuranceDate = Util.getMonth(START_DATE, 1);
	// insuranceDate = leaseBuyDate.substring(0,
	// leaseBuyDate.lastIndexOf("-"))
	// + "-25";
	// }
	// // DBBailDateStr
	// Map<String, Object> map = Dao.selectOne(
	// "PayTask.doShowMaxPayDateByPayCode", PAYLIST_CODE);
	// if (map != null && !map.isEmpty() && map.size() > 0) {
	// DBBailDateStr = StringUtils.nullToString(map.get("PAY_DATE"));
	// }
	// this.updatePayDateByPaylistCode(
	// PAYLIST_CODE.substring(0, PAYLIST_CODE.lastIndexOf("-")),
	// DBBailDateStr, START_DATE, 4);
	//
	// // 预计付款日 判断当前项目"+projectNo+"的供应商是否陕重汽或陕西通力
	// // 陕重汽ADMR-8586BY，陕西通力 ADMR-8NL5NS
	// // start cld 2012-0503 建机改为周三放款
	// // 设备款
	// // 下周三的日期
	// String SUPPLIERNAMES = Dao.selectOne(
	// "PayTask.doShowSupplierNamesByProjectCode",
	// PAYLIST_CODE.substring(0, PAYLIST_CODE.lastIndexOf("-")));
	// DataDictionaryMemcached DDMservice = new DataDictionaryMemcached();
	//
	// // add cld 01-27
	// List<Map> temp_sq = (List) new ArrayList<String>();
	// temp_sq = DDMservice.get("放款_陕汽");//
	//
	// List<String> sq = new ArrayList<String>();
	// for (Map t : temp_sq) {
	// sq.add((String) t.get("FLAG"));
	// }
	//
	// List<Map> temp_jj = (List) new ArrayList<String>();
	// temp_jj = DDMservice.get("放款_建机");//
	//
	// List<String> jj = new ArrayList<String>();
	// for (Map t : temp_jj) {
	// jj.add((String) t.get("FLAG"));
	// }
	// // if(SUPPLIERNAMES.contains("陕西通力")||SUPPLIERNAMES.contains("陕西重汽")){
	// if (sq.contains(SUPPLIERNAMES)) {
	// String next_wed = "";
	// next_wed = Util.NEXT_WEDNESDAY(START_DATE);
	// this.updatePayDateByPaylistCode(
	// PAYLIST_CODE.substring(0, PAYLIST_CODE.lastIndexOf("-")),
	// next_wed, START_DATE, 1);
	// // }else if(SUPPLIERNAMES.contains("建机")){
	// } else if (jj.contains(SUPPLIERNAMES)) {
	// String next_wed = "";
	// next_wed = Util.NEXT_WEDNESDAY_jj(START_DATE);
	// this.updatePayDateByPaylistCode(
	// PAYLIST_CODE.substring(0, PAYLIST_CODE.lastIndexOf("-")),
	// next_wed, START_DATE, 1);
	// } else {
	// throw new ActionException("修改放款日期异常，请联系管理员添加对应的放款政策信息");
	// //
	// this.updatePayDateByPaylistCode(PAYLIST_CODE.substring(0,PAYLIST_CODE.lastIndexOf("-")),
	// // leaseBuyDate,START_DATE, 1);
	// }
	// // 其他费用
	// this.updatePayDateByPaylistCode(
	// PAYLIST_CODE.substring(0, PAYLIST_CODE.lastIndexOf("-")),
	// leaseBuyDate, START_DATE, 4);
	// // 保险费
	// this.updatePayDateByPaylistCode(
	// PAYLIST_CODE.substring(0, PAYLIST_CODE.lastIndexOf("-")),
	// insuranceDate, START_DATE, 3);
	// // // 担保费 担保费支付日期和保险费支付日期一样
	// this.updatePayDateByPaylistCode(
	// PAYLIST_CODE.substring(0, PAYLIST_CODE.lastIndexOf("-")),
	// insuranceDate, START_DATE, 2);
	//
	// }

	/**
	 * 更新付款预计时间
	 *
	 * @param PROJECT_CODE
	 *            项目编号
	 * @param RESERVE_DATE
	 *            预付款日期
	 * @param ACCEPTANCE_DATE
	 *            验收日期
	 * @param PAY_TYPE
	 *            款项类型（1：设备款，2：担保费，3：保险费，4：其他费用）
	 * @author:King 2013-10-19
	 */
	public void updatePayDateByPaylistCode(String PROJECT_CODE,
										   String RESERVE_DATE, String ACCEPTANCE_DATE, int PAY_TYPE) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PROJECT_CODE", PROJECT_CODE);
		map.put("RESERVE_DATE", RESERVE_DATE);
		map.put("PAY_TYPE", PAY_TYPE);
		map.put("START_DATE", sdf.format(new Date()));
		map.put("ACCEPTANCE_DATE", ACCEPTANCE_DATE);
		Dao.update("PayTask.updatePayDateByPaylistCode", map);
	}

	/**
	 * 流程中确定起租确认日，同时更新付款的预付款日期
	 *
	 * @param jbpmId
	 * @author:King 2013-10-22
	 */
	public void updatePayDateByPayStartDateWithJbpm(String jbpmId) {
		Map<String, Object> param = JBPM.getVeriable(jbpmId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		param.putAll((Map<? extends String, ? extends Object>) Dao.selectOne(
				"PayTask.doShowPayDateByPayStartDateWithJbpm",
				param.get("PROJECT_ID") + ""));
		// this.doUpdatePayDateByPaylistCode(sdf.format(new Date()),
		// param.get("PAYLIST_CODE") + "");

		// 更新起租确认日
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PAYLIST_CODE", param.get("PAYLIST_CODE") + "");
		map.put("START_CONFIRM_DATE", sdf.format(new Date()));
		Dao.update("PayTask.updateRentHeadStartConfirmDateByPayCode", map);
		String PROJECT_ID = param.get("PROJECT_ID") + "";
		// 更新项目主表的起租确认日 King 2013-11-12
		Dao.update("project.updateSTART_CONFIRM_DATE", PROJECT_ID);

		DateUtil.getPAYLIST_END_DATE(PROJECT_ID, param.get("PAY_DATE") + "",
				param.get("PAY_WAY") + "",
				Integer.parseInt(param.get("LEASE_PERIOD") + ""));
	}

	public void cancelAfterVerification(Map<String, Object> map) {
		DecimalFormat df = new DecimalFormat(".00");// 保留两位小数处理

		// 首先删除已生成的冲红申请单
		if (map.get("FQ_HEAD") != null) {
			map.put("fund_id", map.get("FQ_HEAD"));
			Dao.delete("rentDk.delFundAccount", map);
			Dao.delete("rentDk.delFundDetail", map);
			Dao.delete("rentDk.delFundHead", map);

		}

		// 组织机构应该取缓存 后面在改
		map.put("USER_ID", Security.getUser().getId());
		Map<String, Object> ORG_MAP = Dao.selectOne("payment.orgListByUserId",
				map);

		if (ORG_MAP != null) {
			map.put("ORG_ID", ORG_MAP.get("ORG_ID"));
		}

		// 插入租金冲抵申请当
		String HEAD_ID = Dao.getSequence("SEQ_FUND_HEAD");
		Map<String, Object> fk_bill = new HashMap<String, Object>();
		fk_bill.put("HEAD_ID", HEAD_ID);// 用户编号
		fk_bill.put("USERCODE", Security.getUser().getCode());// 用户编号
		fk_bill.put("USERNAME", Security.getUser().getName());// 用户名称
		fk_bill.put("FI_PAY_DATE", map.get("FI_ACCOUNT_DATE"));// 付款时间
		fk_bill.put("FI_ACCOUNT_DATE", map.get("FI_ACCOUNT_DATE"));
		fk_bill.put("FI_PROJECT_NUM", 1);// 项目数量
		fk_bill.put("FI_STATUS", 2);
		// fk_bill.put("FI_FLAG", 9);//设备回购

		// 应收首期款(手动填写)
		double FI_PAY_MONEY = map.get("FI_REALITY_ACCOUNT") != null
				&& !"".equals(map.get("FI_REALITY_ACCOUNT").toString()) ? Double
				.parseDouble(map.get("FI_REALITY_ACCOUNT").toString()) : 0d;

		fk_bill.put("FI_PAY_MONEY", df.format(FI_PAY_MONEY));
		fk_bill.put("FI_REALITY_ACCOUNT", df.format(FI_PAY_MONEY));
		fk_bill.put("FI_PAY_MONEY", df.format(FI_PAY_MONEY));

		// 插入付款申请单。
		int a = Dao.insert("rentDk.doInsertFundHead", fk_bill);
		int b = 0;
		int c = 0;
		int e = 0;
		// 通过项目CODE查询出发票开具对象（select INVOICE_TARGET_TYPE, INVOICE_TARGET_ID from
		// fil_project_head h ）
		Map<String, Object> InvoiceMap = Dao.selectOne("rentDk.getFPDX", map);

		if (FI_PAY_MONEY > 0) {
			Map<String, Object> detail = new HashMap<String, Object>();
			detail.put("ITEM_FLAG", 4);
			detail.put("PAYLIST_CODE", map.get("PAYLIST_CODE"));
			List<Map<String, Object>> detailL = Dao.selectList(
					"rentDk.getRentData1", detail);
			if (detailL != null) {
				for (int i = 0; i < detailL.size(); i++) {

					Map<String, Object> d = detailL.get(i);

					d.put("D_STATUS", 1);
					String ID = Dao.getSequence("SEQ_FUND_DETAIL");
					d.put("D_FUND_ID", HEAD_ID);
					d.put("ID", ID);
					d.put("D_RECEIVE_DATE", map.get("FI_ACCOUNT_DATE"));
					d.put("REALITY_TIME", map.get("FI_ACCOUNT_DATE"));
					d.put("D_PROJECT_CODE", map.get("PRO_CODE"));
					d.put("D_STATUS", 2);

					d.put("D_CLIENT_NAME", map.get("D_CLIENT_NAME"));
					d.put("D_CLIENT_CODE", map.get("D_CLIENT_CODE"));
					d.put("D_PAYEE", Security.getUser().getName());
					d.put("D_TO_THE_PAYEE", map.get("D_CLIENT_CODE"));
					if (InvoiceMap != null) {
						d.put("INVOICE_TARGET_TYPE", 3);
						d.put("INVOICE_TARGET_ID", InvoiceMap.get("SUP_ID"));
					}

					// 插入应收明细
					b = Dao.insert("rentDk.doInsertAppDetail", d);

					// 插入实收明细
					d.put("FA_FUND_ID", HEAD_ID);
					d.put("FA_BEGING_ID", d.get("D_BEGING_ID"));
					d.put("FA_DETAIL_ID", ID);
					d.put("FA_ACCOINT_MONEY", d.get("D_PAY_MONEY"));
					d.put("FA_ACCOINT_MONEY", d.get("D_PAY_MONEY"));
					c = Dao.insert("fundNotEBank.doInsertAccount", d);

					// 更行应收起初表
					d.put("BEGINNING_PAID", d.get("D_PAY_MONEY"));
					d.put("BEGINNING_FLAG", 1);
					d.put("BEGINNING_ID", d.get("D_BEGING_ID"));
					fk_bill.put("FI_PAY_DATE", map.get("FI_ACCOUNT_DATE"));// 付款时间
					e = Dao.update("fundNotEBank.doUpdateBeginning", d);

					FI_PAY_MONEY -= Double.parseDouble(d.get("D_PAY_MONEY")
							.toString());
				}
			}
		}
	}

	public String calculateSaveJBPM(Map<String, Object> param) {
		List<Map<String, Object>> minPay = Dao.selectList(
				"queryPayMinVersion_code", param);
		param.put("PAY_ID", minPay.get(0).get("ID"));
		Map<String, Object> map = Dao.selectOne(
				"PayTask.forwardRepaymentShow1", param);// map.put("SUPPLIER_ID",'')
		Object SUPPLIER_ID = Dao.selectOne("PayTask.query_SUPPLIER_ID", param);
		map.put("SUPPLIER_ID", SUPPLIER_ID);
		map.put("PAY_ID", map.get("ID").toString());
		map.put("PROJECT_ID", minPay.get(0).get("PROJECT_ID") + "");
		List<String> prcessList = JBPM
				.getDeploymentListByModelName("Early settlement of approval");
		if (prcessList != null && prcessList.size() > 0) {
			System.out
					.println("=============================" + map.toString());
			ProcessInstance processInstance = JBPM.startProcessInstanceById(
					prcessList.get(0) + "", map.get("PROJECT_ID") + "",
					map.get("CLIENT_ID") + "", map.get("PAYLIST_CODE") + "",
					map);
			param.put("JBPM_ID", processInstance.getId());
			new ProjectContractManagerService().doAddProjectContractList(param
					.get("JBPM_ID").toString());// 保存欲归档文件
			Map<String, String> temp = new HashMap<String, String>();
			temp.put("ID", map.get("ID") + "");
			temp.put("STATUS", param.get("code_") + "");
			Dao.update("PayTask.update_payplan", temp);
			return "流程发起成功";
		} else {
			throw new ActionException("流程图不存在");
		}
	}

	public String calculateSaveJBPMNextCode(Map<String, Object> param) {
		List<Map<String, Object>> minPay = Dao.selectList("PayTask.queryPayMinVersion_code", param);
		param.put("PAY_ID", minPay.get(0).get("ID"));
		Map<String, Object> map = Dao.selectOne("PayTask.forwardRepaymentShow1", param);// map.put("SUPPLIER_ID",'')
		Object SUPPLIER_ID = Dao.selectOne("PayTask.query_SUPPLIER_ID", param);
		map.put("SUPPLIER_ID", SUPPLIER_ID);
		map.put("PAY_ID", map.get("ID").toString());
		map.put("PROJECT_ID", minPay.get(0).get("PROJECT_ID") + "");
		// List<String> prcessList =
		// JBPM.getDeploymentListByModelName("提前结清审批流程",minPay.get(0).get("PLATFORM_TYPE")
		// + "",Security.getUser().getOrg().getPlatformId());
		List<String> prcessList = JBPM.getDeploymentListByModelName("提前结清审批流程",	"", Security.getUser().getOrg().getPlatformId());
		if (prcessList != null && prcessList.size() > 0) {
			ProcessInstance processInstance =
					JBPM.startProcessInstanceById(prcessList.get(0) + "", map.get("PROJECT_ID") + "",
					map.get("CLIENT_ID") + "", map.get("PAYLIST_CODE") + "", map);
			param.put("JBPM_ID", processInstance.getId());
			// new
			// ProjectContractManagerService().doAddProjectContractList(param
			// .get("JBPM_ID").toString());// 保存欲归档文件
			return new TaskService().getAssignee(processInstance.getId());
		} else {
			throw new ActionException("流程图不存在");
		}
	}

	public void calculateParameter(Map<String, Object> param, String PARAM_NAME) {
		JSONObject settleInfo = JSONObject.fromObject(param.get("settleInfo"));
		param.put("OLD_PAY_ID", settleInfo.get("ID"));
		settleInfo.put("ID", param.get("NEWID"));
		Map<String, Object> map = new HashMap<>();
		map.put("PAY_ID", settleInfo.get("ID"));
		map.put("PARAM_NAME", PARAM_NAME);
		map.put("PARAM_VALUE", settleInfo.toString());
		// map.put("PROJECT_ID","");
		map.put("ACCOUNT_DATE",
				settleInfo.get("RENT_DATE") == null
						|| "".equals(settleInfo.get("RENT_DATE")) ? ""
						: settleInfo.get("RENT_DATE"));
		map.put("RENT_TOTAL",
				settleInfo.get("TOTAL_ZJ") == null
						|| "".equals(settleInfo.get("TOTAL_ZJ")) ? ""
						: settleInfo.get("TOTAL_ZJ"));
		map.put("RENT_PAID",
				settleInfo.get("RECEIVED_RENT") == null
						|| "".equals(settleInfo.get("RECEIVED_RENT")) ? ""
						: settleInfo.get("RECEIVED_RENT") == null);
		map.put("RENT_LEFT",
				settleInfo.get("REMAIN_RENT") == null
						|| "".equals(settleInfo.get("REMAIN_RENT")) ? ""
						: settleInfo.get("REMAIN_RENT") == null);
		map.put("PENALTY_AMT",
				settleInfo.get("PENALTY_RECE") == null
						|| "".equals(settleInfo.get("PENALTY_RECE")) ? ""
						: settleInfo.get("PENALTY_RECE") == null);
		map.put("PENALTY_AMT",
				settleInfo.get("PENALTY_RECE") == null
						|| "".equals(settleInfo.get("PENALTY_RECE")) ? ""
						: settleInfo.get("PENALTY_RECE") == null);
		map.put("PAYLIST_CODE",
				settleInfo.get("PAYLIST_CODE") == null
						|| "".equals(settleInfo.get("PAYLIST_CODE")) ? ""
						: settleInfo.get("PAYLIST_CODE") == null);
		// map.put("INTEREST_UNDO","");
		// map.put("CORPUS_UNDO","");
		// map.put("SUM_AMT_AHEAD","");
		map.put("CAUTION_DK",
				settleInfo.get("DEPOSIT") == null
						|| "".equals(settleInfo.get("DEPOSIT")) ? ""
						: settleInfo.get("DEPOSIT"));
		// 如果选择了保证金不冲抵，则插入0
		if ("1".equals(settleInfo.get("STATUS"))) {
			map.put("CAUTION_DK", 0);
		}
		map.put("IS_END_STATUS", "0");
		Dao.insert("PayTask.insert_paychange_parameter_all", map);

	}

	public void cancelSettle(Map<String, Object> map) {
		// 查询如果资金没有未核销的就说明已全部核销
		List<Map<String, Object>> is_verification_finish = Dao.selectList(
				"BuyBack.is_verification_finish", map);
		if (is_verification_finish == null || is_verification_finish.isEmpty()) {
			throw new ActionException("请勿重复操作核销");
		}
		// 查询结清保证金
		Map<String, Object> depositMap = Dao.selectOne("BuyBack.getCAUTION_DK",
				map);
		// 删除大于结算日的罚息
		Dao.delete("BuyBack.FI_ACCOUNT_DATE_FX", map);
		// 更新提前结清表数据
		update_fil_paychange_parameter(map);
		// 计算结清款违约金，并插入罚息表
		int tt = Dao.update("BuyBack.buy_faxi1", map);
		if (tt > 0) {// 如果有结清款违约金再插入违约金表
			Map<String, Object> faxi = Dao
					.selectOne("BuyBack.buy_faxi_id", map);
			if (faxi != null && !faxi.isEmpty()) {
				Dao.insert("BuyBack.insert_buy_faxi2", faxi);
			} else {
				throw new ActionException("违约金插入失败，请联系管理员");
			}
		}
		// 如果选择了保证金抵扣就冲抵保证金
		// 并且保证金金额大于0
		double DEPOSIT_ = 0;
		if (depositMap != null) {
			DEPOSIT_ = depositMap.get("CAUTION_DK") == null
					|| "".equals(depositMap.get("CAUTION_DK").toString()) ? 0
					: Double.parseDouble(depositMap.get("CAUTION_DK")
					.toString());
		}

		// 新增来款
		Map<String, Object> fiFound = new HashMap<String, Object>();

		// 来款帐户
		fiFound.put("FUND_COMECODE", null);
		// 来款人
		fiFound.put("FUND_COMENAME", map.get("NAME"));
		// 收款帐户
		fiFound.put("FUND_ACCEPT_CODE", map.get("FI_REALITY_BANK"));
		// 收款人
		fiFound.put("FUND_ACCEPT_NAME", Security.getUser().getOrg()
				.getPlatform());
		// 收到金额
		fiFound.put("FUND_RECEIVE_MONEY", map.get("FI_REALITY_ACCOUNT"));
		fiFound.put("FUND_ID", Dao.selectOne("fi.fund.getFundId"));
		fiFound.put("FUND_FUNDCODE", Dao.selectOne("fi.fund.getFundCode"));
		fiFound.put("FUND_NOTDECO_STATE", "0");
		fiFound.put("FUND_STATUS", "0");
		fiFound.put("FUND_ISCONGEAL", "0");
		fiFound.put("FUND_RED_STATUS", "0");
		fiFound.put("FUND_PRANT_ID", "0");
		fiFound.put("FUND_IMPORT_PERSON", Security.getUser().getName());
		fiFound.put("FUND_IMPORT_PERSON_ID", Security.getUser().getId());
		Dao.insert("fi.fund.add", fiFound);

		if (DEPOSIT_ > 0) {
			Map<String, Object> tm = new HashMap<String, Object>();
			tm.put("PAYCODEPARAM", map.get("PAYLIST_CODE"));
			double EXTRA_MONEY = map.get("FI_REALITY_ACCOUNT") != null
					&& !"".equals(map.get("FI_REALITY_ACCOUNT") + "") ? Double
					.parseDouble(map.get("FI_REALITY_ACCOUNT") + "") : 0d;
			tm.put("EXTRA_MONEY", EXTRA_MONEY);
			tm.put("FI_ACCOUNT_DATE_", map.get("FI_ACCOUNT_DATE"));
			tm.put("FI_REALITY_BANK_", map.get("FI_REALITY_BANK"));
			tm.put("FUND_ID", fiFound.get("FUND_ID"));
			Dao.selectOne("BuyBack.doConfirmRefundAppByProId", tm);
		} else {
			Map<String, Object> tm = new HashMap<String, Object>();
			tm.put("PAYCODEPARAM", map.get("PAYLIST_CODE"));
			double EXTRA_MONEY = map.get("FI_REALITY_ACCOUNT") != null
					&& !"".equals(map.get("FI_REALITY_ACCOUNT") + "") ? Double
					.parseDouble(map.get("FI_REALITY_ACCOUNT") + "") : 0d;
			tm.put("EXTRA_MONEY", EXTRA_MONEY);
			tm.put("FI_ACCOUNT_DATE_", map.get("FI_ACCOUNT_DATE"));
			tm.put("FI_REALITY_BANK_", map.get("FI_REALITY_BANK"));
			tm.put("FUND_ID",
					Integer.parseInt(fiFound.get("FUND_ID").toString()));
			Dao.selectOne("BuyBack.PRC_FUNDRED_NO_DEPOSIT", tm);
		}

		// 一下的核销改到存过里面操作
		/*
		 * DecimalFormat df = new DecimalFormat(".00");//保留两位小数处理
		 * //首先删除已生成的冲红申请单 if(map.get("FQ_HEAD")!=null){ map.put("fund_id",
		 * map.get("FQ_HEAD")); Dao.delete("rentDk.delFundAccount", map);
		 * Dao.delete("rentDk.delFundDetail", map);
		 * Dao.delete("rentDk.delFundHead", map); } // 组织机构应该取缓存 后面在改
		 * map.put("USER_ID", Security.getUser().getId()); Map<String,Object>
		 * ORG_MAP=Dao.selectOne("payment.orgListByUserId", map);
		 * if(ORG_MAP!=null){ map.put("ORG_ID", ORG_MAP.get("ORG_ID")); }
		 * //插入租金冲抵申请当 String HEAD_ID = Dao.getSequence("SEQ_FUND_HEAD");
		 * Map<String,Object> fk_bill = new HashMap<String,Object>();
		 * fk_bill.put("HEAD_ID", HEAD_ID);//用户编号 fk_bill.put("USERCODE",
		 * Security.getUser().getCode());//用户编号 fk_bill.put("USERNAME",
		 * Security.getUser().getName());//用户名称 fk_bill.put("FI_PAY_DATE",
		 * map.get("FI_ACCOUNT_DATE"));//付款时间 fk_bill.put("FI_ACCOUNT_DATE",
		 * map.get("FI_ACCOUNT_DATE")); fk_bill.put("FI_PROJECT_NUM", 1);//项目数量
		 * fk_bill.put("FI_STATUS", 2); //fk_bill.put("FI_FLAG", 9);//设备回购
		 * //应收首期款(手动填写) double FI_PAY_MONEY =
		 * map.get("FI_REALITY_ACCOUNT")!=null&&!"".equals(
		 * map.get("FI_REALITY_ACCOUNT"
		 * ).toString())?Double.parseDouble(map.get("FI_REALITY_ACCOUNT"
		 * ).toString()):0d; fk_bill.put("FI_PAY_MONEY",
		 * df.format(FI_PAY_MONEY)); fk_bill.put("FI_REALITY_ACCOUNT",
		 * df.format(FI_PAY_MONEY)); fk_bill.put("FI_PAY_MONEY",
		 * df.format(FI_PAY_MONEY)); //插入付款申请单。 int a =
		 * Dao.insert("rentDk.doInsertFundHead",fk_bill); int b = 0; int c = 0;
		 * int e = 0; //通过项目CODE查询出发票开具对象（select INVOICE_TARGET_TYPE,
		 * INVOICE_TARGET_ID from fil_project_head h ） Map<String,Object>
		 * InvoiceMap=Dao.selectOne("rentDk.getFPDX", map); if(FI_PAY_MONEY>0){
		 * Map<String,Object> detail = new HashMap<String,Object>();
		 * detail.put("ITEM_FLAG", 4); detail.put("PAYLIST_CODE",
		 * map.get("PAYLIST_CODE")); List<Map<String,Object>> detailL =
		 * Dao.selectList("rentDk.getRentData4", detail); if(detailL!=null){
		 * for(int i=0; i<detailL.size(); i++){ Map<String, Object> d =
		 * detailL.get(i); d.put("D_STATUS", 1); String ID =
		 * Dao.getSequence("SEQ_FUND_DETAIL"); d.put("D_FUND_ID", HEAD_ID);
		 * d.put("ID", ID); d.put("D_RECEIVE_DATE", map.get("FI_ACCOUNT_DATE"));
		 * d.put("D_PROJECT_CODE", map.get("PRO_CODE")); d.put("D_STATUS", 2);
		 * d.put("D_CLIENT_NAME", map.get("D_CLIENT_NAME"));
		 * d.put("D_CLIENT_CODE", map.get("D_CLIENT_CODE")); d.put("D_PAYEE",
		 * Security.getUser().getName()); d.put("D_TO_THE_PAYEE",
		 * map.get("D_CLIENT_CODE")); if(InvoiceMap != null){
		 * d.put("INVOICE_TARGET_TYPE", 3); d.put("INVOICE_TARGET_ID",
		 * InvoiceMap.get("SUP_ID")); } //插入应收明细 b =
		 * Dao.insert("rentDk.doInsertAppDetail",d); //插入实收明细
		 * d.put("FA_FUND_ID", HEAD_ID); d.put("FA_BEGING_ID",
		 * d.get("D_BEGING_ID")); d.put("FA_DETAIL_ID", ID);
		 * d.put("FA_ACCOINT_MONEY", d.get("D_PAY_MONEY"));
		 * d.put("FA_ACCOINT_MONEY", d.get("D_PAY_MONEY")); c =
		 * Dao.insert("fundNotEBank.doInsertAccount",d); //更行应收起初表
		 * d.put("BEGINNING_PAID", d.get("D_PAY_MONEY"));
		 * d.put("BEGINNING_FLAG", 1); d.put("BEGINNING_ID",
		 * d.get("D_BEGING_ID")); fk_bill.put("FI_PAY_DATE",
		 * map.get("FI_ACCOUNT_DATE"));//付款时间 d.put("REALITY_TIME",
		 * map.get("FI_ACCOUNT_DATE")); e =
		 * Dao.update("fundNotEBank.doUpdateBeginning",d); FI_PAY_MONEY -=
		 * Double.parseDouble(d.get("D_PAY_MONEY").toString()); } } }
		 */
	}

	public void exportRentIllustrateExcel(String proj_id, OutputStream os)
			throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("PROJECT_ID", proj_id);
		Map<String, Object> map = Dao.selectOne("PayTask.querCustInfo", param);
		List<Map<String, Object>> list = Dao.selectList(
				"PayTask.doShowRentList", param);
		SkyEye.getResponse().setCharacterEncoding("UTF-8");
		SkyEye.getResponse().setHeader(
				"Content-Disposition",
				new StringBuilder("attachment;filename=")
						.append(new String("起租说明书.xls".getBytes("GB2312"),
								"ISO-8859-1")).toString());
		SkyEye.getResponse().setContentType(ResMime.get("xls"));
		map.put("还款计划列表", list);
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String fontpath = SkyEye.getRealPath("WEB-INF/res/模板-A16起租说明书.xls");
		// modelData.put("还款计划列表",
		// this.getPersonalProjectDao().doQueryBySql(temp_sql_sb.toString()));
		PoiExcelUtil.excelTemplateExcle(new FileInputStream(fontpath), SkyEye
				.getResponse().getOutputStream(), map);

	}

	// 根据pro_id更新 区域 交货地点 验收日期
	public void updateProjectInfo(Map<String, Object> param) {
		if (param.get("UPDATE_DELIVER_DATE") != null
				&& !"".equals((param.get("UPDATE_DELIVER_DATE").toString()))) {
			Dao.update("project.updateProjectInfoByProId", param);
		}
	}

	// 更新结清表参数
	private void update_fil_paychange_parameter(Map<String, Object> param) {
		BuyBackService service1 = new BuyBackService();
		// param.put("PAYLIST_CODE", "SFPDJT130408-1");
		// param.put("TPM_ID", "77");
		// param.putAll(service1.selectTpmPath(param));// 查询模版path
		// param.put("FILE_PATH", "");
		// if (StringUtils.isBlank(param.get("FILE_PATH").toString())) { throw
		// new ActionException("没有找到指定的合同模板"); }
		param.putAll(service1.selectRepoData(param));// 模版中需要的业务数据
		{
			// 计算excel数据
			Double ZJ_ZJ = 0.00;// 租金总计
			Double YS_ZJ_ZE = 0.00;// 已收租金总额
			Double SY_ZJ_ZE = 0.00;// 剩余租金总额
			Double HJ = 0.00;// 合计
			Double START_ZJ = 0.00;// 起租租金
			Double SUM_ZJ = 0.00;// 租金总计
			Double DEPOSIT = 0.00;// 保证金
			Double LEAVE = 0.00;// 留购价
			Double RENT_RECE = 0.00;// 到期未付租金违约金
			Double QT_FY = 0.00;// 其他费用合计
			Double UNPAID_INTEREST = 0.00;// 未到期租金利息
			Double PENALTY_RECE = 0.00;// 逾期金额
			Double LEASE_TOPRIC = 0.00;// 租赁物总价值
			Double BEGINNING_PAID = 0.00;// 已还租金
			int WD_ZJ_QC = 0;// 未到租金期次
			int BEGINNING_NUM = 0;// 已还的最大期次

			if (param.containsKey("PENALTY_RECE")
					&& !param.get("PENALTY_RECE").equals("")) {
				PENALTY_RECE = Double.parseDouble(param.get("PENALTY_RECE")
						.toString());
				PENALTY_RECE = Util.formatDouble2(PENALTY_RECE);
			}
			param.put("PENALTY_RECE", PENALTY_RECE);// 逾期金额

			if (param.containsKey("LEASE_TOPRIC")
					&& !param.get("LEASE_TOPRIC").equals("")) {
				LEASE_TOPRIC = Double.parseDouble(param.get("LEASE_TOPRIC")
						.toString());
			}
			param.put("LEASE_TOPRIC", LEASE_TOPRIC);// 租赁物总价值

			if (param.containsKey("BEGINNING_PAID")
					&& !param.get("BEGINNING_PAID").equals("")) {
				BEGINNING_PAID = Double.parseDouble(param.get("BEGINNING_PAID")
						.toString());
			}
			param.put("BEGINNING_PAID", BEGINNING_PAID);// 已还租金

			if (param.containsKey("START_ZJ")
					&& !param.get("START_ZJ").equals("")) {
				START_ZJ = Double.parseDouble(param.get("START_ZJ").toString());
			}
			param.put("START_ZJ", START_ZJ);// 起租租金

			if (param.containsKey("SUM_ZJ") && !param.get("SUM_ZJ").equals("")) {
				SUM_ZJ = Double.parseDouble(param.get("SUM_ZJ").toString());
			}
			param.put("SUM_ZJ", SUM_ZJ);// 租金总计

			ZJ_ZJ = START_ZJ + SUM_ZJ;
			param.put("ZJ_ZJ", ZJ_ZJ);// 租金总计

			if (param.containsKey("DEPOSIT")
					&& !param.get("DEPOSIT").equals("")) {
				DEPOSIT = Double.parseDouble(param.get("DEPOSIT").toString());
			}
			param.put("DEPOSIT", DEPOSIT);// 保证金
			// 查询是否要做保证金抵扣
			boolean flag = false;
			Map<String, Object> dm = Dao.selectOne(
					"BuyBack.select_fil_paychange_parameter", param);
			if (dm != null && !dm.isEmpty()) {
				JSONObject PARAM_VALUE = JSONObject.fromObject(dm
						.get("PARAM_VALUE"));
				if ("0".equals(PARAM_VALUE.get("STATUS"))) {
					param.put("保证金冲抵", "+保证金冲抵" + DEPOSIT + "元");
					flag = true;
				}
			}

			if (param.containsKey("LEAVE") && !param.get("LEAVE").equals("")) {
				LEAVE = Double.parseDouble(param.get("LEAVE").toString());
			}
			param.put("LEAVE", LEAVE);// 留购价

			YS_ZJ_ZE = BEGINNING_PAID + START_ZJ;
			if (flag) {// 如果选了保证金冲抵，则已收要加上保证金冲抵金额
				YS_ZJ_ZE = YS_ZJ_ZE + DEPOSIT;
			}
			param.put("YS_ZJ_ZE", YS_ZJ_ZE);// 已收租金总额

			SY_ZJ_ZE = ZJ_ZJ - YS_ZJ_ZE;
			param.put("SY_ZJ_ZE", SY_ZJ_ZE);// 剩余租金总额

			if (param.containsKey("RENT_RECE")
					&& !param.get("RENT_RECE").equals("")) {
				RENT_RECE = Double.parseDouble(param.get("RENT_RECE")
						.toString());
			}
			param.put("RENT_RECE", RENT_RECE);// 到期未付租金违约金

			if (param.containsKey("QT_FY") && !param.get("QT_FY").equals("")) {
				QT_FY = Double.parseDouble(param.get("QT_FY").toString());
			}
			param.put("QT_FY", QT_FY);// 其他费用合计

			if (param.containsKey("UNPAID_INTEREST1")
					&& !param.get("UNPAID_INTEREST1").equals("")) {
				UNPAID_INTEREST = Double.parseDouble(param.get(
						"UNPAID_INTEREST1").toString());
			}
			param.put("UNPAID_INTEREST", UNPAID_INTEREST);// 未到期租金利息

			HJ = SY_ZJ_ZE + RENT_RECE + LEAVE + QT_FY - UNPAID_INTEREST;
			param.put("HJ", HJ);// 合计

			if (param.containsKey("BEGINNING_NUM")
					&& !param.get("BEGINNING_NUM").equals("")) {
				BEGINNING_NUM = Integer.parseInt(param.get("BEGINNING_NUM")
						.toString());// 已还的最大期次
			}
			WD_ZJ_QC = BEGINNING_NUM + 1;
			param.put("WD_ZJ_QC", WD_ZJ_QC);// 未到租金期次

			// -----------------------租赁期限
			param.put(
					"ZLQX",
					(param.get("LEASE_PERIOD") == null
							|| "".equals(param.get("LEASE_PERIOD")) ? 1
							: Integer.parseInt(param.get("LEASE_PERIOD") + ""))
							* (param.get("LEASE_TERM") == null
							|| "".equals(param.get("LEASE_TERM")) ? 1
							: Integer.parseInt(param.get("LEASE_TERM")
							+ "")) + "");
			param.put(
					"FILE_NAME",
					param.get("NAME") + "" + param.get("PRO_CODE")
							+ param.get("FILE_NAME"));

			// 更新提前结清表
			Map<String, Object> mm = new HashMap<String, Object>();
			mm.put("HIRE_BANK", param.get("FI_REALITY_BANK"));// 核销银行
			mm.put("RENT_TOTAL", param.get("ZJ_ZJ"));// 租金总计
			mm.put("RENT_PAID", BEGINNING_PAID + START_ZJ);// 已收租金包含起租租金
			mm.put("RENT_LEFT", ZJ_ZJ - BEGINNING_PAID - START_ZJ);// 剩余租金
			mm.put("PENALTY_AMT", param.get("PENALTY_RECE"));// 违约金
			mm.put("INTEREST_UNDO", param.get("UNPAID_INTEREST"));// 未到期租金利息
			mm.put("SUM_AMT_AHEAD", param.get("FI_REALITY_ACCOUNT"));// 提前结清款
			mm.put("OTHER_SHOULD_PAY", param.get("QT_FY"));// 其他应收款
			mm.put("CORPUS_UNDO", Dao.selectDouble("BuyBack.ws_bj", param));// 未收本金
			mm.put("PAYLIST_CODE", param.get("PAYLIST_CODE"));
			mm.put("REALITY_DATE", param.get("FI_ACCOUNT_DATE"));

			Dao.update("BuyBack.update_fil_paychange_parameter", mm);

		}

	}

	public JSONObject disconnectCalculate(Map<String, Object> param)
			throws Exception {
		param.put("ID", Dao.selectInt("PayTask.PAY_MAX_ID", param));
		JSONArray detailOld = this.payDetailShow(param).getJSONArray("data");// 查询出原还款计划
		// 查询出需要拆分的还款计划的剩余本金
		double surplusBJ = Dao.selectDouble("PayTask.surplusBJbyQC", param);
		double SPLIT_RATIO = Double.parseDouble(param.get("SPLIT_RATIO") + "");
		// 开始拆分期次
		int SPLIT_PERIOD = param.get("SPLIT_PERIOD") == null
				|| "".equals(param.get("SPLIT_PERIOD")) ? 0 : Integer
				.parseInt(param.get("SPLIT_PERIOD") + "");
		Map<String, Object> data = Dao.selectOne("disconnectCalculateData",
				param);
		// 测算
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("_leaseTerm", Integer.parseInt(data.get("LEASE_TERM") + "")
				- SPLIT_PERIOD + 1);
		temp.put("annualRate", data.get("ANNUALRATE"));
		temp.put("_payCountOfYear", "12");
		temp.put("CALCULATE_WAY", data.get("CALCULATE_WAY") + "");
		temp.put("date", Util.getMonth(data.get("PAY_DATE") + "", -1));
		temp.put("pay_way", "1");
		temp.put("residualPrincipal",
				Util.formatDouble2(Util.mul(surplusBJ, SPLIT_RATIO)));
		// 计算出拆出来的支付表(不适用pmt计算)
		// JSONObject new_plan2 = this.quoteCalculateTest(temp);
		// JSONArray data2 = new_plan2.getJSONArray("ln");
		// data2 = this.resetPlan(data2,SPLIT_PERIOD);

		// 计算剩下的那张支付表
		temp.put("residualPrincipal", Util.formatDouble2(surplusBJ
				- Util.mul(surplusBJ, SPLIT_RATIO)));
		JSONObject new_plan1 = this.quoteCalculateTest(temp);
		JSONArray data1 = new_plan1.getJSONArray("ln");
		data1 = this.resetPlan(data1, SPLIT_PERIOD);
		// 使用减法计算第二张还款计划
		JSONArray data2 = new JSONArray();
		double t_d = Util.formatDouble2(Util.mul(surplusBJ, SPLIT_RATIO));
		for (int i = 0; i < data1.size(); i++) {
			JSONObject json1 = JSONObject.fromObject(data1.get(i));
			JSONObject json = new JSONObject();
			for (int j = 0; j < detailOld.size(); j++) {
				JSONObject oldJson = JSONObject.fromObject(detailOld.get(j));
				if (json1.getString("qc").equals(oldJson.getString("qc"))) {
					json.put("qc", json1.get("qc"));
					json.put(
							"zj",
							Util.sub(oldJson.getDouble("zj"),
									json1.getDouble("zj")));
					json.put(
							"bj",
							Util.sub(oldJson.getDouble("bj"),
									json1.getDouble("bj")));
					json.put("lx", Util.sub(json.getDouble("zj"),
							json.getDouble("bj")));
					t_d = Util.sub(t_d, json.getDouble("bj"));
					json.put("sybj", t_d);
					json.put("PMTzj", json.get("zj"));
					json.put("PAY_DATE", json1.get("PAY_DATE"));
				}
			}
			data2.add(json);
		}
		// 将第一个支付表加上未拆分之前的数据
		JSONArray temp_ = new JSONArray();
		temp_.addAll(detailOld.subList(0, SPLIT_PERIOD - 1));
		temp_.addAll(data1);
		JSONObject json = new JSONObject();
		json.put("data1", temp_);
		json.put("data2", data2);
		json.put("TOPRIC_SUBFIRENT1", Util.formatDouble2(surplusBJ
				- Util.mul(surplusBJ, SPLIT_RATIO)));
		json.put("TOPRIC_SUBFIRENT2",
				Util.formatDouble2(Util.mul(surplusBJ, SPLIT_RATIO)));
		// 查询设备总额
		double lease_topric = Dao.selectDouble("PayTask.query_lease_topric",
				param);
		json.put(
				"LEASE_TOPRIC1",
				Util.formatDouble2(lease_topric
						- Util.mul(lease_topric, SPLIT_RATIO)));
		json.put("LEASE_TOPRIC2",
				Util.formatDouble2(Util.mul(lease_topric, SPLIT_RATIO)));
		return json;
	}

	public JSONArray resetPlan(JSONArray data, int num) {
		JSONArray r_data = new JSONArray();
		for (int i = 0; i < data.size(); i++) {
			JSONObject temp = JSONObject.fromObject(data.get(i));
			temp.put("qc", temp.getLong("qc") + num - 1);
			r_data.add(temp);
		}
		return r_data;
	}

	public void disconnectCalculateSave(Map<String, Object> m) {
		String PAYLIST_CODE = m.get("PAYLIST_CODE") + "";
		String NEW_CODE = PAYLIST_CODE.substring(0, PAYLIST_CODE.length() - 1)
				+ "2";
		// 插入拆分参数表
		Map<String, Object> split = new HashMap<String, Object>();
		split.put("NEW_CODE", NEW_CODE);
		split.put("PAYLIST_CODE", PAYLIST_CODE);
		split.put("IDS", m.get("ids"));
		split.put("EDSARRAY", m.get("edsArray"));
		split.put("SPLIT_PERIOD", m.get("SPLIT_PERIOD"));
		split.put("SPLIT_RATIO", m.get("SPLIT_RATIO"));
		split.put("LEASE_TOPRIC1", m.get("LEASE_TOPRIC1"));
		split.put("LEASE_TOPRIC2", m.get("LEASE_TOPRIC2"));
		split.put("PARAM_NAME", "还款计划拆分");
		split.put("PAY_ID", Dao.selectInt("PayTask.queryMaxId", PAYLIST_CODE));
		Dao.insert("PayTask.insert_fil_pay_split", split);
		// 插入第一张支付表
		JSONArray rows = JSONArray.fromObject(m.get("DATA1"));
		this.disconnectSave(m, rows, true, PAYLIST_CODE);
		// 插入第二张支付表
		rows = JSONArray.fromObject(m.get("DATA2"));
		this.disconnectSave(m, rows, false, NEW_CODE);

		// 跟新设备表的支付表id
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("PAYLIST_CODE", NEW_CODE);
		temp.put("edsArray", m.get("edsArray"));
		Dao.update("PayTask.update_equipment_paylist_code", temp);
		Map<String, Object> JBPMmap = Dao.selectOne("PayTask.queryJBPMmap",
				PAYLIST_CODE);
		// 发起流程
		List<String> prcessList = JBPM.getDeploymentListByModelName("还款计划拆分");
		if (prcessList != null && prcessList.size() > 0) {
			JBPM.startProcessInstanceById(prcessList.get(0) + "",
					JBPMmap.get("PROJECT_ID") + "", JBPMmap.get("CLIENT_ID")
							+ "", PAYLIST_CODE, JBPMmap);
		} else {
			throw new ActionException("流程图不存在");
		}
	}

	public Map<String, Object> disconnectCalculateSaveMsg(Map<String, Object> m) {
		Map<String, Object> result = new HashMap<String, Object>();
		String PAYLIST_CODE = m.get("PAYLIST_CODE") + "";
		String NEW_CODE = PAYLIST_CODE.substring(0, PAYLIST_CODE.length() - 1)
				+ "2";
		// 插入拆分参数表
		Map<String, Object> split = new HashMap<String, Object>();
		split.put("NEW_CODE", NEW_CODE);
		split.put("PAYLIST_CODE", PAYLIST_CODE);
		split.put("IDS", m.get("ids"));
		split.put("EDSARRAY", m.get("edsArray"));
		split.put("SPLIT_PERIOD", m.get("SPLIT_PERIOD"));
		split.put("SPLIT_RATIO", m.get("SPLIT_RATIO"));
		split.put("LEASE_TOPRIC1", m.get("LEASE_TOPRIC1"));
		split.put("LEASE_TOPRIC2", m.get("LEASE_TOPRIC2"));
		split.put("PARAM_NAME", "还款计划拆分");
		split.put("PAY_ID", Dao.selectInt("PayTask.queryMaxId", PAYLIST_CODE));
		Dao.insert("PayTask.insert_fil_pay_split", split);
		// 插入第一张支付表
		JSONArray rows = JSONArray.fromObject(m.get("DATA1"));
		this.disconnectSave(m, rows, true, PAYLIST_CODE);
		// 插入第二张支付表
		rows = JSONArray.fromObject(m.get("DATA2"));
		this.disconnectSave(m, rows, false, NEW_CODE);
		String msg = "下一步操作人为: ";
		boolean flag = true;
		// 跟新设备表的支付表id
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("PAYLIST_CODE", NEW_CODE);
		temp.put("edsArray", m.get("edsArray"));
		Dao.update("PayTask.update_equipment_paylist_code", temp);
		Map<String, Object> JBPMmap = Dao.selectOne("PayTask.queryJBPMmap",
				PAYLIST_CODE);
		// 发起流程
		List<String> prcessList = JBPM.getDeploymentListByModelName("还款计划拆分");
		if (prcessList != null && prcessList.size() > 0) {
			ProcessInstance processInstance = JBPM.startProcessInstanceById(
					prcessList.get(0) + "", JBPMmap.get("PROJECT_ID") + "",
					JBPMmap.get("CLIENT_ID") + "", PAYLIST_CODE, JBPMmap);
			String nextCode = new TaskService().getAssignee(processInstance
					.getId());
			result.put("flag", flag);
			result.put("msg", msg);
			result.put("data", nextCode);
			return result;
		} else {
			msg = "流程图不存在";
			flag = false;
			result.put("flag", flag);
			result.put("msg", msg);
			return result;
		}
	}

	public void disconnectSave(Map<String, Object> m, JSONArray rows,
							   boolean first, String PAYLIST_CODE) {
		Map<String, Object> temp = new HashMap<String, Object>();
		// 插入支付表主表
		Dao.insert("PayTask.upgrade_payplan", m);
		// 插入明细表
		List<Map<String, String>> list = getParsePayList(rows);
		for (Map<String, String> map : list) {
			map.put("PAY_ID", m.get("NEWID") + "");
			map.put("ITEM_FLAG", "2");
			Dao.insert("PayTask.payplan_detail", map);
		}
		// 插入明细的其他费用
		if (first) {
			Map<String, Object> map = Dao.selectOne("PayTask.queryPay", m);
			map.put("NEWID", m.get("NEWID") + "");
			Dao.insert("PayTask.payplan_detail_other", map);
			// 修改第一张支付表
			temp.put("ID", m.get("NEWID"));
			temp.put("TOPRIC_SUBFIRENT", m.get("TOPRIC_SUBFIRENT1"));
			temp.put("LEASE_TOPRIC", m.get("LEASE_TOPRIC1"));
			Dao.update("PayTask.update_payplan", temp);
		} else {
			// 修改第二张支付表
			temp = new HashMap<String, Object>();
			temp.put("ID", m.get("NEWID"));
			temp.put("VERSION_CODE", "-1");
			temp.put("TOPRIC_SUBFIRENT", m.get("TOPRIC_SUBFIRENT2"));
			temp.put("LEASE_TOPRIC", m.get("LEASE_TOPRIC2"));
			temp.put("PAYLIST_CODE", PAYLIST_CODE);
			Dao.update("PayTask.update_payplan", temp);
			// 第二张支付表同步beginning数据
			m.put("PAYLIST_CODE", PAYLIST_CODE);
		}
		// 删除财务表数据
		Dao.delete("PayTask.deleteBeginning", m);
		// 同步财务数据
		Map<String, String> temp1 = new HashMap<String, String>();
		// temp1.put("PAY_ID", ID + "");
		temp1.put("PAYLIST_CODE", m.get("PAYLIST_CODE") + "");
		temp1.put("PMT", "PMT租金");
		temp1.put("ZJ", "租金");
		temp1.put("SYBJ", "剩余本金");
		temp1.put("D", "第");
		temp1.put("QI", "期");
		Dao.insert("PayTask.synchronizationBeginning", temp1);
	}

	// // 更新丢失放款数据起租确认日期 预付日期
	// public void updateConfirmDateForLosePayData() {
	// List<Map<String, Object>> listDate = Dao.selectList(
	// "payment.selectSTART_CONFIRM_DATEForLosePayData", null);
	// for (int i = 0; i < listDate.size(); i++) {
	// Map<String, Object> mm = listDate.get(i);
	// System.out.println(mm.get("PAYLIST_CODE") + "" + "------------"
	// + mm.get("START_CONFIRM_DATE") + "");
	// doUpdatePayDateByPaylistCode(mm.get("START_CONFIRM_DATE") + "",
	// mm.get("PAYLIST_CODE") + "");
	// }
	// }

	public Excel exportData(Map<String, Object> params) {
		List<Map<String, Object>> list = Dao.selectList(
				"PayTask.getirrDataToExcel", params);
		Excel excel = new Excel();
		excel.addSheetName("sheet01");
		excel.addData(list);
		LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();

		title.put("SUP_SHORTNAME", "供应商");
		title.put("CLERK_NAME", "承租人");
		title.put("PRO_CODE", "项目编号");
		title.put("XIRR_", "IRR");
		excel.addTitle(title);
		return excel;
	}

	public int insertPaymentDetail(JSONObject obj, Map<String, Object> m) {
		List<Map<String, Object>> dataList = obj.getJSONArray("RECORD_LIST");
		String PAY_ID = obj.get("PAY_ID").toString();
		String USER_CODE = obj.get("USER_CODE").toString();
		// String PAYLIST_CODE = obj.get("PAYLIST_CODE").toString();
		for (Map<String, Object> map : dataList) {
			map.put("USER_CODE", USER_CODE);
			map.put("PAY_ID", PAY_ID);
			Dao.insert("payment.createPayMentDate", map);
		}
		return 1;
	}

	// 计算起租日和还款日中间的利息差
	/**
	 * date:起租日 date1：还款日 residualPrincipal:融资额 annualRate：年利率;LEASE_PERIOD租赁周期
	 */
	public double getDateLiXiMoney(String date, String date1,
								   double residualPrincipal, double annualRate, int LEASE_PERIOD,
								   String PAY_WAY) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("date", date);
		map.put("date1", date1);
		map.put("residualPrincipal", residualPrincipal);
		String monthrate = SkyEye.getConfig("MONTHRATE");
		if (StringUtils.isNotBlank(monthrate) && "ON".equals(monthrate)
				&& (PAY_WAY.contains("平息") || "7".equals(PAY_WAY))) {
			map.put("annualRate",
					UtilFinance.mul(annualRate,
							UtilFinance.div(12, LEASE_PERIOD, 2)));
		} else
			map.put("annualRate", annualRate);
		return Dao.selectDouble("PayTask.getDateLiXiMoneyNew", map);
	}

	public Object getPayLineBase(Map<String, Object> m) {
		return Dao.selectOne("PayTask.getPayLineBase", m);
	}

	public List<Map<String, Object>> getDetail(Map<String, Object> payT) {
		return Dao.selectList("PayTask.getDetail", payT);
	}

	public List<Map<String, Object>> findPayList1(Map<String, Object> map) {
		return Dao.selectList("PayTask.findPayList1", map);
	}

	/******** 租前息 **********/
	// 买卖合同还款约定
	public List<Map<String, Object>> findPayPayment(Map<String, Object> map) {
		return Dao.selectList("PayTask.findPayPayment", map);
	}

	/**
	 * @Title: queryPayCode 查询支付表信息-租前息
	 * @autor wuguowei_jing@163.com 2014-5-12 下午4:13:47
	 * @return
	 */
	public Map<String, Object> queryPayCode(Map<String, Object> m) {
		Map<String, Object> projectInfo = Dao.selectOne(
				"PayTask.query_project_info", m.get("ID"));
		double YEAR_INTEREST = Double.valueOf(projectInfo.get("YEAR_INTEREST")
				.toString());
		m.put("projectInfo", projectInfo);
		m.put("YEAR_INTEREST", YEAR_INTEREST);
		m.put("PAYLIST_CODE_ZQX", projectInfo.get("PAYLIST_CODE") + "_ZQX");
		m.put("TIAN_YEAR_INTEREST", Util.div(YEAR_INTEREST, 360, 6));
		return m;
	}

	/**
	 * @Title: rentBeforeSave 租前息数据保存
	 * @autor wuguowei_jing@163.com 2014-5-13 上午10:58:51
	 * @param m
	 */
	public void rentBeforeSave(Map<String, Object> m) {
		JSONArray rows = JSONArray.fromObject(m.get("myData"));
		// 插入租前息支付表主表
		Dao.insert("PayTask.rentbefore_payplan", m);
		// 插入明细表
		List<Map<String, String>> list = getParsePayList(rows);
		for (Map<String, String> map : list) {
			map.put("PAY_ID", m.get("NEWID") + "");
			map.put("ITEM_FLAG", "2");
			Dao.insert("PayTask.payplan_detail", map);
		}
		// 同步财务数据
		Map<String, String> temp1 = new HashMap<String, String>();
		temp1.put("PAYLIST_CODE", m.get("PAYLIST_CODE_ZQX") + "");
		temp1.put("PMT", "PMT租金");
		temp1.put("ZJ", "租金");
		temp1.put("SYBJ", "剩余本金");
		temp1.put("D", "第");
		temp1.put("QI", "期");
		Dao.insert("PayTask.synchronizationBeginning", temp1);

	}

	/**
	 * @Title: getAllPayRent 支付表变更
	 * @autor wuguowei_jing@163.com 2014-5-12 下午5:29:42
	 * @param m
	 */
	public Page getAllPayRentBefore(Map<String, Object> m) {
		Page page = new Page(m);
		String ORD_ID = BaseUtil.getSupOrgChildren();
		if (ORD_ID != null && !"".equals(ORD_ID) && !"null".equals(ORD_ID)) {
			m.put("ORD_ID", ORD_ID);
		}
		m.put("TYPE_", "还款计划状态");
		List<Map<String, Object>> list = Dao.selectList(
				"PayTask.getAllPayRentBefore", m);
		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array,
				Dao.selectInt("PayTask.getAllPayRentBefore_count", m));
		return page;
	}

	/**
	 * @Title: getAllPayRent 支付表变更
	 * @autor wuguowei_jing@163.com 2014-5-12 下午5:29:42
	 * @param m
	 */
	public Page getAllPayRent(Map<String, Object> m) {
		m.put("TYPE_", "还款计划状态");
		return PageUtil.getPage(m, "PayTask.getAllPayRent",
				"PayTask.getAllPayRent_count");
	}

	public double chuliOverDue(String PAYLIST_CODE, String ACCOUNT_DATE) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PAYLIST_CODE", PAYLIST_CODE);
		map.put("ACCOUNT_DATE", ACCOUNT_DATE);
		// 由于提前结清冻结后当前日期以后的期次不生成罚息，而之前的方法会生成发现，故将之后期初的罚息删除掉
		Dao.delete("PayTask.deleteJQByPERIOD", map);
		return Util.formatDouble0(Dao.selectDouble(
				"BuyBack.query_fi_overdue_ACCOUNT_DATE", map));
	}

	public void calculateSaveJQ(Map<String, Object> m) {

		// 获取之前的明细表
		Map<String, Object> mapOld = Dao.selectOne("PayTask.queryPayOldID", m);

		JSONArray rows = JSONArray.fromObject(m.get("myData"));

		// 插入明细表
		List<Map<String, String>> list = getParsePayListNew(rows);
//		System.out.println("--------------------------list=" + list);
		for (Map<String, String> map : list) {
			map.put("PAY_ID", m.get("NEWID") + "");
			map.put("ITEM_FLAG", "2");
			Dao.insert("PayTask.payplan_detail", map);
		}
		// 修改当期往下的利息为0(提前结清使用)，以及租金
		Map<String, Object> mm = new HashMap<>();
		if (m.get("settleInfo") != null && !"".equals(m.get("settleInfo"))) {
			mm.put("ACCOUNT_DATE", JSONObject.fromObject(m.get("settleInfo"))
					.get("RENT_DATE"));
			mm.put("NEWID", m.get("NEWID"));
			mm.put("LILV", Double.parseDouble(JSONObject.fromObject(m.get("settleInfo")).get("exemptInterest") + ""));
			Dao.update("BuyBack.detial_interest_zero", mm);
			// 修改租金
			Dao.update("BuyBack.detial_interest_ZJ", mm);
		}

		// 插入明细的其他费用

		mapOld.put("NEWID", m.get("NEWID") + "");
		Dao.insert("PayTask.payplan_detail_other", mapOld);

		// 插入变更收取的其他费用
		Map<String, String> mo = new HashMap<String, String>();

		mo.put("新增手续费",
				m.get("OTHER_MONEY") == null || "".equals(m.get("OTHER_MONEY")) ? ""
						: m.get("OTHER_MONEY") + "");
		mo.put("新增税金", m.get("taxes") == null || "".equals(m.get("taxes")) ? ""
				: m.get("taxes") + "");
		insertPayplanDetailOther(mo, m.get("NEWID") + "");

		// 修改支付表主表状态
		Map<String, String> temp = new HashMap<String, String>();
		temp.put("ID", m.get("NEWID") + "");
		temp.put("STATUS", m.get("code_") + "");
		Dao.update("PayTask.update_payplan", temp);

	}

	public Map<String, Object> doShowJQByPayId(String PAY_ID) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isBlank(PAY_ID))
			return map;
		map.put("PAY_ID", PAY_ID);
		map.put("PARAM_NAME", "提前结清");
		Map<String, Object> mapDate = Dao.selectOne("PayTask.doShowJQByPayId", map);
		return JSONObject.fromObject(mapDate.get("PARAM_VALUE"));
	}

	public Map<String, Object> doShowNormalByPayId(String PAY_ID) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isBlank(PAY_ID))
			return map;
		map.put("PAY_ID", PAY_ID);
		map.put("PARAM_NAME", "正常结清");
		Map<String, Object> mapDate = Dao.selectOne("PayTask.doShowJQByPayId",
				map);
		System.out.println("-------------mapDate=" + mapDate);
		JSONObject SCHEME_MAP = JSONObject.fromObject(mapDate
				.get("PARAM_VALUE"));
		return SCHEME_MAP;
	}

	public List<Map<String, Object>> queryJQDate(Map<String, Object> map) {
		List<Map<String, Object>> list = Dao.selectList("PayTask.queryJQDate",
				map);
		return list;
	}

	public List<Map<String, Object>> DIKOUFUND(Map<String, Object> map) {
		List<Map<String, Object>> list = Dao.selectList("PayTask.DIKOUFUND",
				map);
		return list;
	}

	public List<Map<String, Object>> listFundByCustID(Map<String, Object> map) {
		List<Map<String, Object>> list = Dao.selectList(
				"PayTask.listFundByCustID", map);
		return list;
	}

	public void calculateSaveNormalJQ(Map<String, Object> m) {

		// 获取之前的明细表
		Map<String, Object> mapOld = Dao.selectOne("PayTask.queryPayOldID", m);

		// 插入明细表（获取以前数据的明细）
		List<Map<String, String>> list = Dao.selectList(
				"BuyBack.getPayDetailByOldId", m);
		for (Map<String, String> map : list) {
			map.put("PAY_ID", m.get("NEWID") + "");
			map.put("ITEM_FLAG", "2");
			Dao.insert("PayTask.payplan_detail", map);
		}

		// 插入明细的其他费用
		mapOld.put("NEWID", m.get("NEWID") + "");
		Dao.insert("PayTask.payplan_detail_other", mapOld);

		// 修改支付表主表状态
		Map<String, String> temp = new HashMap<String, String>();
		temp.put("ID", m.get("NEWID") + "");
		temp.put("STATUS", m.get("code_") + "");
		Dao.update("PayTask.update_payplan", temp);

	}

	public String calculateSaveNormalJBPMNextCode(Map<String, Object> param) {
		List<Map<String, Object>> minPay = Dao.selectList(
				"PayTask.queryPayMinVersion_code", param);
		param.put("PAY_ID", minPay.get(0).get("ID"));
		Map<String, Object> map = Dao.selectOne(
				"PayTask.forwardRepaymentShow1", param);// map.put("SUPPLIER_ID",'')
		Object SUPPLIER_ID = Dao.selectOne("PayTask.query_SUPPLIER_ID", param);
		map.put("SUPPLIER_ID", SUPPLIER_ID);
		map.put("PAY_ID", map.get("ID").toString());
		map.put("PROJECT_ID", minPay.get(0).get("PROJECT_ID") + "");
		// List<String> prcessList =
		// JBPM.getDeploymentListByModelName("提前结清审批流程",minPay.get(0).get("PLATFORM_TYPE")
		// + "",Security.getUser().getOrg().getPlatformId());
		List<String> prcessList = JBPM.getDeploymentListByModelName("正常结清审批流程",
				"", Security.getUser().getOrg().getPlatformId());
		if (prcessList != null && prcessList.size() > 0) {
			ProcessInstance processInstance = JBPM.startProcessInstanceById(
					prcessList.get(0) + "", map.get("PROJECT_ID") + "",
					map.get("CLIENT_ID") + "", map.get("PAYLIST_CODE") + "",
					map);
			param.put("JBPM_ID", processInstance.getId());
			// new
			// ProjectContractManagerService().doAddProjectContractList(param
			// .get("JBPM_ID").toString());// 保存欲归档文件
			String nextCode = new TaskService().getAssignee(processInstance
					.getId());
			return nextCode;
		} else {
			throw new ActionException("流程图不存在");
		}
	}

	/**
	 * 根据支付表查询支付表对应的方案信息
	 *
	 * @param PAYLIST_CODE
	 * @return
	 * @author King 2015年7月30日
	 */
	public List<Map<String, Object>> queryPayScheme(String PAYLIST_CODE) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PAYLIST_CODE", PAYLIST_CODE);
		return Dao.selectList("PayTask.queryPayScheme", map);
	}

	/**
	 *
	 * @Title: algorithmP2PCalculate
	 *	@Description: P2P金融算法
	 * @author guoweiwu@jiezhongchina.com
	 * @param m
	 * @return
	 * @date 2016-4-14 上午9:26:17
	 */
	public JSONObject algorithmP2PCalculate(Map<String,Object> m){


		System.out.println("============================================================"+m);
		CalculateItem model = new CalculateItem();
		    /*年利率*/
		BigDecimal yearInterest = new BigDecimal(Double.valueOf(m.get("YEAR_INTEREST").toString())).divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP);
		    /*月综合费率*/
		BigDecimal monthComperensiveRate = yearInterest.divide(new BigDecimal(12), 6, BigDecimal.ROUND_HALF_UP);
		    /*内部利率*/
		BigDecimal nblv = new BigDecimal(Double.valueOf(m.get("SCHEME_SYL_BZ_VALUE").toString())).divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP);
		    /*融资额*/
		BigDecimal rze = new BigDecimal(Double.valueOf(m.get("TOTAL_FINACING").toString()));
		    /*成交价*/
		BigDecimal cj = new BigDecimal(Double.valueOf(m.get("LEASE_TOPRIC").toString()));
		    /*采购价*/
		BigDecimal cgj = new BigDecimal(Double.valueOf(m.get("CC_PRICE").toString()));
		    /*每期管理费*/
		BigDecimal mqglf = new BigDecimal(Double.valueOf(m.get("MQGLF").toString()));
		    /*合同租车费*/
		BigDecimal htzcf = new BigDecimal(Double.valueOf(m.get("HTZCF").toString()));
		    /*财客元素*/
		int ckys  =  null== m.get("CKYS")?1:Integer.valueOf(m.get("CKYS").toString()) ;

		if(cgj.doubleValue()==0){
			cgj = cj;
		}
		    /*进位方式*/
		int  rut = null == m.get("ROUND_UP_TYPE")?2:Integer.valueOf(m.get("ROUND_UP_TYPE").toString());
		    
		    /*保留位数*/
		int  scale = null == m.get("SCALE")?0:Integer.valueOf(m.get("SCALE").toString());
		    

		    /*2016年7月4日 09:21:29  吴国伟 固定值 5000*/
		//model.setAdditionalCosts(cj.subtract(cgj));
		    /*是否加5000 2016年7月11日 14:07:03*/
		if(1==ckys){//固定加价5000
			model.setAdditionalCosts(new BigDecimal("5000"));
		}else if(2==ckys){//计算得出
			model.setAdditionalCosts(cj.subtract(cgj));
		}else{
			model.setAdditionalCosts(new BigDecimal("0"));
		}

		System.out.println("固定值-2016年7月4日 09:21:11 计算读取值========================"+model.getAdditionalCosts());
		System.out.println("计算值========================"+cj.subtract(cgj));

		if(0==scale){
			model.setDigitType(DigitType.取整);
			if(1==rut){//直接进位 取整
				model.setCarryType(CarryType.直接进位);
			}else{//四舍五入
				model.setCarryType(CarryType.四舍五入);
			}
		}else{
			model.setDigitType(DigitType.两位小数);
			model.setCarryType(CarryType.四舍五入);
		}

		model.setD(Integer.valueOf(m.get("LEASE_TERM").toString()));

		model.setMonthComprehensiveRate(monthComperensiveRate);

		model.setA(rze);

		model.setC(yearInterest);

		model.setNblv(nblv);

		model.setIsRatioCalculate(IsRatioCalculate.yes);

		model.setMonthAdministration(mqglf);

		model.setClientAdministration(htzcf);

		Algorithm a = new Algorithm();

		Map<String, Object> calculateResult = a.getCalculateResult(model);
		System.out.println(calculateResult);
		List<MeasureResult> list = (List<MeasureResult>) calculateResult.get("list");
		List<Map<String,Object>> mlist =dataChangUtil(list);
		Map<String,Object> rm = new HashMap<String,Object>();
		rm.put("ln", mlist);
		rm.put("zhtje", calculateResult.get("zhtje"));
		rm.put("zzcfwf", calculateResult.get("zzcfwf"));
		rm.put("MYZJ", list.get(0).getZj());
		System.out.println(rm);
		return JSONObject.fromObject(rm);
	}
	/**
	 *
	 * @Title: dataChangUtil
	 *	@Description: 数据转换
	 * @author guoweiwu@jiezhongchina.com
	 * @param list
	 * @return
	 * @date 2016-4-14 上午10:45:43
	 */
	public List<Map<String,Object>> dataChangUtil(List<MeasureResult> list){
		List<Map<String,Object>> rlist = new ArrayList<Map<String,Object>>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,Object> rm;
		for(MeasureResult res:list){
			rm = new HashMap<String,Object>();
			rm.put("zj", res.getZj()+"");
			rm.put("bj", res.getBj()+"");
			rm.put("lx", res.getZcfwf()+"");
			rm.put("sybj", res.getSybj()+"");
			rm.put("PMTzj", res.getZj()+"");
			rm.put("qc", res.getQs()+"");
			rm.put("PAY_DATE", res.getZfrq()+"");
			rm.put("glf", res.getGlf()+"");
			rm.put("sxf", res.getJyfwf()+"");
			rm.put("lxzzs", "0.00");
			rlist.add(rm);

		}

		return rlist;
	}
}
