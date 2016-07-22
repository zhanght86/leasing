package com.pqsoft.base.suppliersInfo.action;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.base.suppliersInfo.service.FinancialStatisticsService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class FinancialStatisticsAction extends Action {

	private final String pagePath = "base/suppMainRelation/financialStatistics/";
	private FinancialStatisticsService service = new FinancialStatisticsService();

	@Override
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
//	@aPermission(name = { "合作机构", "渠道管理", "修改", "法人财务报表" })
	public Reply execute() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		return new ReplyHtml(VM.html(pagePath + "toEditFinanceMain.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doEditFinancil() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		map.put("USERCODE", Security.getUser().getCode());// 登陆用户编号
		context.put("SORT", map.get("SORT"));
		context.put("param", map);
		if (map.get("SORT") != null && !"".equals(map.get("SORT"))) {
			if (map.get("SORT").equals("1")) {
				service.queryDebtByClientid(map, context);
				logger.debug("资产负债表");
				return new ReplyHtml(VM.html(pagePath + "toEditFinance_debt.vm", context));
			} else if (map.get("SORT").equals("2")) {
				service.queryProfitByID(map, context);
				logger.debug("损益表");
				return new ReplyHtml(VM.html(pagePath + "toEditfinnace_Profit.vm", context));
			} else if (map.get("SORT").equals("3")) {
				logger.debug("现金流量表");
				service.queryCashById(map, context);
				return new ReplyHtml(VM.html(pagePath + "toEditfinnace_Cash.vm", context));
			} else if (map.get("SORT").equals("4")) {
				logger.debug("财务指标分析");
				service.testCalculate(map, context, true);
				return new ReplyHtml(VM.html(pagePath + "toEditfinnace_BankStatistics.vm", context));
			}
		} else {// 默认的查询当前用户的所有任务
			logger.debug("默认的查询当前用户的所有任务");
		}
		return new ReplyHtml(VM.html("", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
//	@aPermission(name = { "合作机构", "渠道管理", "修改", "法人财务报表" })
	public Reply doShowFinancil() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		// System.out.println("----------------map=" + map);
		map.put("USERCODE", Security.getUser().getCode());// 登陆用户编号
		context.put("SORT", map.get("SORT"));
		context.put("param", map);
		if (map.get("SORT") != null && !"".equals(map.get("SORT"))) {
			if (map.get("SORT").equals("1")) {
				service.queryDebtByClientid(map, context);
				logger.debug("资产负债表");
				return new ReplyHtml(VM.html(pagePath + "toShowFinance_debt.vm", context));
			} else if (map.get("SORT").equals("2")) {
				service.queryProfitByID(map, context);
				logger.debug("损益表");
				return new ReplyHtml(VM.html(pagePath + "toShowfinnace_Profit.vm", context));

			} else if (map.get("SORT").equals("3")) {
				logger.debug("现金流量表");
				service.queryCashById(map, context);
				return new ReplyHtml(VM.html(pagePath + "toShowfinnace_Cash.vm", context));
			} else if (map.get("SORT").equals("4")) {
				logger.debug("财务指标分析");
				service.queryFinanceAnalyzeById(map, context);
				return new ReplyHtml(VM.html(pagePath + "toShowfinnace_BankStatistics.vm", context));
			}
		} else {// 默认的查询当前用户的所有任务
			logger.debug("默认的查询当前用户的所有任务");
		}
		return new ReplyHtml(VM.html("", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doInsertDebt() {
		boolean flag = true;
		Map<String, Object> map = _getParameters();
		try {
			int num = service.findFinanceManageById(map);
			if (num < 1) {
				service.insertFinanceManage(map);
			} else {
				service.updateFinanceManageById(map);
			}
			int cashCount = service.selectCountForCashByManagerId(map);
			int profitdisCount = service.selectCountForProfitByManagerId(map);
			if (cashCount > 0) {
				service.updateCashInDateByManagerId(map);
			}
			if (profitdisCount > 0) {
				service.updateProfitInDateByManagerId(map);
			}

			flag = service.insertDebt(map);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
			logger.error(e.getMessage());
			flag = false;
		}
		return new ReplyAjax(flag).addOp(new OpLog("渠道管理", "添加", "资产负债[保存]"));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "", email = "yuanye@pqsoft.cn", name = "陈原野")
	public Reply doEditDebt() {
		boolean flag = true;
		Map<String, Object> map = _getParameters();
		map.put("CREATE_NAME", Security.getUser().getName());
		map.put("CREATE_CODE", Security.getUser().getCode());
		try {
			service.deleteDebt(map);
			service.updateFinanceManageById(map);
			int cashCount = service.selectCountForCashByManagerId(map);
			int profitdisCount = service.selectCountForProfitByManagerId(map);
			if (cashCount > 0) {
				service.updateCashInDateByManagerId(map);
			}
			if (profitdisCount > 0) {
				service.updateProfitInDateByManagerId(map);
			}

			flag = service.insertDebt(map);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
			logger.error(e.getMessage());
			flag = false;
		}
		return new ReplyAjax(flag).addOp(new OpLog("渠道管理", "修改", "资产负债[修改]"));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doInsertProfitDistri() {

		Map<String, Object> map = _getParameters();
		int num = service.findFinanceManageById(map);
		if (num < 1) {
			service.insertFinanceManage(map);
		} else {
			service.updateFinanceManageById(map);
		}
		int cashCount = service.selectCountForCashByManagerId(map);
		int debtCount = service.selectCountForDebtByManagerId(map);
		if (cashCount > 0) {
			service.updateCashInDateByManagerId(map);
		}
		if (debtCount > 0) {
			service.updateDebtInDateByManagerId(map);
		}
		int returnValue = service.insertProfitDistri(map);
		String result = "";
		if (returnValue > 0) {
			result = "保存成功";
			return new ReplyAjax(result).addOp(new OpLog("渠道管理", "添加", "损益表[保存]"));
		} else {
			result = "保存失败";
			return new ReplyAjax(result).addOp(new OpLog("渠道管理", "添加", "损益表[保存]"));
		}
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doEditProfitDistri() {

		Map<String, Object> map = _getParameters();
		service.deleteProfitDistri(map);
		service.updateFinanceManageById(map);
		int cashCount = service.selectCountForCashByManagerId(map);
		int debtCount = service.selectCountForDebtByManagerId(map);
		if (cashCount > 0) {
			service.updateCashInDateByManagerId(map);
		}
		if (debtCount > 0) {
			service.updateDebtInDateByManagerId(map);
		}
		int returnValue = service.insertProfitDistri(map);
		String result = "";
		if (returnValue > 0) {
			result = "保存成功";
			return new ReplyAjax(result).addOp(new OpLog("渠道管理", "添加", "损益表[保存]"));
		} else {
			result = "保存失败";
			return new ReplyAjax(result).addOp(new OpLog("渠道管理", "添加", "损益表[保存]"));
		}
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doInsertCash() {
		Map<String, Object> map = _getParameters();
		map.put("OPERATOR_ID", Security.getUser().getId());// 登陆用户编号
		map.put("OPERATOR_NAME", Security.getUser().getName());// 登陆用户姓名
		map.put("OPERATOR_CODE", Security.getUser().getCode());// 登陆用户工号
		map.put("CREATE_NAME", Security.getUser().getName());
		map.put("CREATE_CODE", Security.getUser().getCode());
		int num = service.findFinanceManageById(map);
		if (num < 1) {
			service.insertFinanceManage(map);
		} else {
			service.updateFinanceManageById(map);
		}

		int profitCount = service.selectCountForProfitByManagerId(map);
		int debtCount = service.selectCountForDebtByManagerId(map);
		if (profitCount > 0) {
			service.updateProfitInDateByManagerId(map);
		}
		if (debtCount > 0) {
			service.updateDebtInDateByManagerId(map);
		}

		int returnValue = service.insertCash(map);
		String result = "";
		if (returnValue > 0) {
			result = "保存成功";
			return new ReplyAjax(result).addOp(new OpLog("渠道管理", "添加", "现金流量[保存]"));
		} else {
			result = "保存失败";
			return new ReplyAjax(result).addOp(new OpLog("渠道管理", "添加", "现金流量[保存]"));
		}
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doEditCash() {
		Map<String, Object> map = _getParameters();
		map.put("CREATE_NAME", Security.getUser().getName());
		map.put("CREATE_CODE", Security.getUser().getCode());
		service.deleteCashByID(map);

		service.updateFinanceManageById(map);
		int profitCount = service.selectCountForProfitByManagerId(map);
		int debtCount = service.selectCountForDebtByManagerId(map);
		if (profitCount > 0) {
			service.updateProfitInDateByManagerId(map);
		}
		if (debtCount > 0) {
			service.updateDebtInDateByManagerId(map);
		}
		int returnValue = service.insertCash(map);
		String result = "";
		if (returnValue > 0) {
			result = "保存成功";
			return new ReplyAjax(result).addOp(new OpLog("渠道管理", "添加", "现金流量[保存]"));
		} else {
			result = "保存失败";
			return new ReplyAjax(result).addOp(new OpLog("渠道管理", "添加", "现金流量[保存]"));
		}
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply testFinancialIndex() {
		Map<String, Object> map = _getParameters();
		// 盈利能力
		List<Map<String, Object>> profitability = (List<Map<String, Object>>) service.profitability(map);
		map.put("profitability", profitability);

		// 偿债能力
		List<Map<String, Object>> debtAbility = (List<Map<String, Object>>) service.debtAbility(map);
		map.put("debtAbility", debtAbility);

		// EBITDA
		List<Map<String, Object>> EBITDA = (List<Map<String, Object>>) service.statisticsEBITDA(map);
		map.put("EBITDA", EBITDA);

		// 经营现金流分析
		List<Map<String, Object>> jyxjFlowAnalyze = (List<Map<String, Object>>) service.jyxjFlowAnalyze(map);
		map.put("jyxjFlowAnalyze", jyxjFlowAnalyze);

		// 销售收入增长率
		List<Map<String, Object>> salesGrowthRate = (List<Map<String, Object>>) service.salesGrowthRate(map);
		map.put("salesGrowthRate", salesGrowthRate);

		// 总资产增长率
		List<Map<String, Object>> AssetsGrowthRate = (List<Map<String, Object>>) service.AssetsGrowthRate(map);
		map.put("AssetsGrowthRate", AssetsGrowthRate);

		// 运营能力
		List<Map<String, Object>> servicePower = (List<Map<String, Object>>) service.servicePower(map);
		map.put("servicePower", servicePower);

		return new ReplyJson(JSONObject.fromObject(map)).addOp(new OpLog("渠道管理", "添加", "财务指标测算"));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doInsertIndex() {
		Map<String, Object> map = _getParameters();
		map.put("CREATE_NAME", Security.getUser().getName());
		map.put("CREATE_ID", Security.getUser().getId());
		map.put("OPERATOR_ID", Security.getUser().getId());// 登陆用户编号
		map.put("OPERATOR_NAME", Security.getUser().getName());// 登陆用户姓名
		map.put("OPERATOR_CODE", Security.getUser().getCode());// 登陆用户工号
		map.put("CREATE_NAME", Security.getUser().getName());
		map.put("CREATE_CODE", Security.getUser().getCode());
		int num = service.findFinanceManageById(map);
		if (num < 1) {
			service.insertFinanceManage(map);
		} else {
			service.updateFinanceManageById(map);
		}

		/**
		 * 财务指标 时间不可修改
		 * int profitCount = service.selectCountForProfitByManagerId(map);
		 * int debtCount = service.selectCountForDebtByManagerId(map);
		 * int cashCount = service.selectCountForCashByManagerId(map);
		 * if (profitCount > 0) {
		 * service.updateProfitInDateByManagerId(map);
		 * }
		 * if (debtCount > 0) {
		 * service.updateDebtInDateByManagerId(map);
		 * }
		 * if (cashCount > 0) {
		 * service.updateCashInDateByManagerId(map);
		 * }
		 */
		int returnValue = service.insertIndex(map);
		String result = "";
		if (returnValue > 0) {
			result = "保存成功";
			return new ReplyAjax(result).addOp(new OpLog("渠道管理", "添加", "财务指标[保存]"));
		} else {
			result = "保存失败";
			return new ReplyAjax(result).addOp(new OpLog("渠道管理", "添加", "财务指标[保存]"));
		}
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply doEditIndex() {
		Map<String, Object> map = _getParameters();
		map.put("CREATE_NAME", Security.getUser().getName());
		map.put("CREATE_ID", Security.getUser().getId());

		// /如果记录已存在则先删除再保存
		service.deleteFinanceAnalyzeRecord(map);

		service.updateFinanceManageById(map);

		/**
		 * 财务指标时间不可修改
		 * int profitCount = service.selectCountForProfitByManagerId(map);
		 * int debtCount = service.selectCountForDebtByManagerId(map);
		 * int cashCount = service.selectCountForCashByManagerId(map);
		 * if (profitCount > 0) {
		 * service.updateProfitInDateByManagerId(map);
		 * }
		 * if (debtCount > 0) {
		 * service.updateDebtInDateByManagerId(map);
		 * }
		 * if (cashCount > 0) {
		 * service.updateCashInDateByManagerId(map);
		 * }
		 */

		int returnValue = service.insertIndex(map);
		String result = "";
		if (returnValue > 0) {
			result = "保存成功";
			return new ReplyAjax(result).addOp(new OpLog("渠道管理", "添加", "财务指标[保存]"));
		} else {
			result = "保存失败";
			return new ReplyAjax(result).addOp(new OpLog("渠道管理", "添加", "财务指标[保存]"));
		}
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply toMgAnalazy() {
		Map<String, Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("toMgAnalazy", service.toMgAnalazy(map));
		return new ReplyHtml(VM.html(pagePath + "toMgAnalazy.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
//	@aPermission(name = { "合作机构", "渠道管理", "修改", "财务报表" })
	public Reply manageFinancial() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		context.put("param", map);
		context.put("financialList", service.manageFinancial(map));
		return new ReplyHtml(VM.html(pagePath + "manageFinance.vm", context));

	}
	
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "180006", email = "tao_yan_min@163.com", name = "陶言民")
//	@aPermission(name = { "合作机构", "渠道管理", "修改", "财务报表" })
	public Reply manageFinancialDetail() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		context.put("param", map);
		context.put("financialList", service.manageFinancial(map));
		return new ReplyHtml(VM.html(pagePath + "manageFinanceDetail.vm", context));

	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply manageFinancialProject() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		context.put("param", map);
		context.put("financialList", service.manageFinancial(map));
		return new ReplyHtml(VM.html(pagePath + "manageFinance.vm", context));

	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
//	@aPermission(name = { "合作机构", "渠道管理", "查看", "财务报表" })
	public Reply manageFinancialView() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		context.put("param", map);
		context.put("financialList", service.manageFinancial(map));
		return new ReplyHtml(VM.html(pagePath + "manageFinanceView.vm", context));

	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	// @aPermission(name = { "项目管理", "项目一览","查看"})
	public Reply manageFinancialViewProject() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		context.put("param", map);
		context.put("financialList", service.manageFinancial(map));
		return new ReplyHtml(VM.html(pagePath + "manageFinanceView.vm", context));

	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	// @aPermission(name = { "财务报表管理" })
	public Reply toAddManagePage() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		String seq = service.getFinancialSeq();
		param.put("MANAGEID", seq);
		context.put("param", param);
		return new ReplyHtml(VM.html(pagePath + "toAddFinanceMain.vm", context));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	// @aPermission(name = { "财务报表管理[添加]" })
	public Reply toAddFinancial() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		System.out.println("=====================map=" + map);
		context.put("param", map);
		String sort = (String) map.get("SORT");
		service.testCalculate(map, context, false);
		if (sort != null && !sort.equals("")) {
			if (sort.equals("1")) {
				// 资产负债表
				return new ReplyHtml(VM.html(pagePath + "toAddFinance_debt.vm", context));
			} else if (sort.equals("2")) {
				return new ReplyHtml(VM.html(pagePath + "toAddfinnace_Profit.vm", context));
			} else if (sort.equals("3")) {
				return new ReplyHtml(VM.html(pagePath + "toAddfinnace_Cash.vm", context));
			} else if (sort.equals("4")) {

			return new ReplyHtml(VM.html(pagePath + "toAddfinnace_BankStatistics.vm", context)); }
		}
		return new ReplyHtml(VM.html("", context));

	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	// @aPermission(name = { "财务报表管理[删除]" })
	public Reply deleteFinancilManage() {
		boolean flag = false;
		String msg = "";
		VelocityContext context = new VelocityContext();
		Map<String, Object> map = _getParameters();
		map.put("CLIENT_ID", map.get("CUST_ID"));
		map.put("PEOPLE_ID", map.get("CUST_ID"));
		service.deleteDebt(map);
		service.deleteProfitDistri(map);
		service.deleteCashByID(map);
		service.deleteFinanceAnalyzeRecord(map);
		int a = service.deleteFinancilManage(map);
		if (a > 0) {
			flag = true;
			msg = "删除成功";
		} else {
			flag = false;
			msg = "删除失败";
		}
		return new ReplyAjax(flag, msg);

	}
}
