package com.pqsoft.secuEvaluate.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.StringUtils;

/**
 * @author 吴剑东
 * 
 */
@SuppressWarnings("unchecked")
public class NormScoreService {
	Map<String, Object> map = new HashMap<String, Object>();
	Map<String, Object> cmap = new HashMap<String, Object>();
	private final static String path = "NormScore.";

	/**
	 * 初始化主体信息
	 * 
	 * @date 2014-4-3 上午09:25:16
	 * @author 作者 吴剑东
	 */
	public NormScoreService(Map<String, Object> m) {
		this.map.putAll(m);
		// 初始化主体信息

		// 查询客户表信息
		Map<String, Object> map_0 = new HashMap<String, Object>();
		map_0 = Dao.selectOne(path + "getClientInfoByID", map);
		if (map_0 == null || map.size() < 0)
			return;

		// 查询财务报表管理表
		Map<String, Object> map_1 = new HashMap<String, Object>();
		map.put("CLIENT_ID", map_0.get("ID"));
		cmap.putAll(map_0);
		map_1 = Dao.selectOne(path + "getFCFMInfoByClientId", map);
		if (map_1 == null || map_1.size() < 0)
			return;
		map.put("IDENTIFIERS", map_1.get("ID"));
		cmap.putAll(map_1);

		// 查询财务分析表信息
		List<Map<String, Object>> map_analyze = new ArrayList<Map<String, Object>>();
		map_analyze = Dao.selectList(path + "getFCFAInfoByIdentifiers", map);
		cmap.put("map_analyze", map_analyze);

		// 查询负债表信息
		List<Map<String, Object>> map_debt = new ArrayList<Map<String, Object>>();
		map_debt = Dao.selectList(path + "getFCFDInfoByIdentifiers", map);
		cmap.put("map_debt", map_debt);

		// 查询现金流量表信息
		List<Map<String, Object>> map_cash = new ArrayList<Map<String, Object>>();
		map_cash = Dao.selectList(path + "getFCFCInfoByIdentifiers", map);
		cmap.put("map_cash", map_cash);

		// 查询利润及利润分配表信息
		List<Map<String, Object>> map_profitdis = new ArrayList<Map<String, Object>>();
		map_profitdis = Dao.selectList(path + "getFCFPInfoByIdentifiers", map);
		cmap.put("map_profitdis", map_profitdis);

	}

	/**
	 * 根据定量打分项名称,获取定量打分项分值
	 * 
	 * @date 2014-4-3 上午09:25:16
	 * @author 作者 吴剑东
	 */
	public Object getNormScore(Map<String, Object> normMap) {
		String score = "";
		Integer describe = Integer.parseInt(normMap.get("DESCRIBE").toString());
		if ("性别".equals(normMap.get("NORM_NAME") + "")) {
			if (cmap.get("SEX") != null && !"".equals(cmap.get("SEX") + "")) {
				map.put("D_CODE", cmap.get("SEX"));
				map.put("D_TYPE", "性别");
				score = queryScoreByCode();
			}
		} else if ("婚姻状况".equals(normMap.get("NORM_NAME") + "")) {
			if (cmap.get("IS_MARRY") != null
					&& !"".equals(cmap.get("IS_MARRY") + "")) {
				map.put("D_CODE", cmap.get("IS_MARRY"));
				map.put("D_TYPE", "婚姻状况");
				score = queryScoreByCode();
			}
		} else if ("文化程度".equals(normMap.get("NORM_NAME") + "")) {
			if (cmap.get("DEGREE_EDU") != null
					&& !"".equals(cmap.get("DEGREE_EDU") + "")) {
				map.put("D_CODE", cmap.get("DEGREE_EDU"));
				map.put("D_TYPE", "文化程度");
				score = queryScoreByCode();
			}
		} else if ("年龄".equals(normMap.get("NORM_NAME") + "")) {
			if (cmap.get("NIANLING") != null
					&& !"".equals(cmap.get("NIANLING") + "")) {
				normMap.put("D_CODE", cmap.get("NIANLING"));
				normMap.put("D_TYPE", "年龄");
				score = queryScoreIntervalByCode(normMap);
			}
		} else if ("企业性质".equals(normMap.get("NORM_NAME") + "")) {
			if (cmap.get("BUSINESS_TYPE") != null
					&& !"".equals(cmap.get("BUSINESS_TYPE") + "")) {
				map.put("D_CODE", cmap.get("BUSINESS_TYPE"));
				map.put("D_TYPE", "公司性质");
				score = queryScoreByCode();
			}
		}else if ("纳税情况".equals(normMap.get("NORM_NAME") + "")) {
			if (cmap.get("RATEPAYING") != null
					&& !"".equals(cmap.get("RATEPAYING") + "")) {
				map.put("D_CODE", cmap.get("RATEPAYING"));
				map.put("D_TYPE", "纳税情况");
				score = queryScoreByCode();
			}
		}else if ("客户来源".equals(normMap.get("NORM_NAME") + "")) {
			if (cmap.get("SCALE_SOURCE") != null
					&& !"".equals(cmap.get("SCALE_SOURCE") + "")) {
				map.put("D_CODE", cmap.get("SCALE_SOURCE"));
				map.put("D_TYPE", "客户来源");
				score = queryScoreByCode();
			}
		}else if ("是否担保".equals(normMap.get("NORM_NAME") + "")) {
			if (cmap.get("IS_GUARANTEE") != null
					&& !"".equals(cmap.get("IS_GUARANTEE") + "")) {
				map.put("D_CODE", cmap.get("IS_GUARANTEE"));
				map.put("D_TYPE", "是否担保");
				score = queryScoreByCode();
			}
		} else if ("纳税资质".equals(normMap.get("NORM_NAME") + "")) {
			if (cmap.get("TAX_QUALIFICATION") != null
					&& !"".equals(cmap.get("TAX_QUALIFICATION") + "")) {
				map.put("D_CODE", cmap.get("TAX_QUALIFICATION"));
				map.put("D_TYPE", "纳税资质");
				score = queryScoreByCode();
			}
		} else if ("职工人数".equals(normMap.get("NORM_NAME") + "")) {
			if (cmap.get("NUMBER_PER") != null
					&& !"".equals(cmap.get("NUMBER_PER") + "")) {
				normMap.put("D_CODE", cmap.get("NUMBER_PER") != null ? cmap
						.get("NUMBER_PER") : 0);
				score = queryScoreIntervalByCode(normMap);
			}
		} else if ("注册资本".equals(normMap.get("NORM_NAME") + "")) {
			if (cmap.get("REGISTE_CAPITAL") != null
					&& !"".equals(cmap.get("REGISTE_CAPITAL") + "")) {
				map.put("D_CODE", cmap.get("REGISTE_CAPITAL") != null ? cmap
						.get("REGISTE_CAPITAL") : 0);
				score = queryScoreIntervalByCode(normMap);
			}
		} else if ("净资产".equals(normMap.get("NORM_NAME") + "")) {
			// 总资产—流动负债—长期负债 单位（万元）
			List<Map<String, Object>> l = (List<Map<String, Object>>) cmap
					.get("map_debt");
			if (l != null && l.size() > 0) {
				// 取自[资产负债表]表字段

				normMap.put("D_CODE",
						l.get(l.size() - 1).get("OWER_TOTAL") != null ? l.get(
								l.size() - 1).get("OWER_TOTAL") : 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("销售收入".equals(normMap.get("NORM_NAME") + "")) {
			// 单位（万元) 取自[损益表] 表子段
			List<Map<String, Object>> l = (List<Map<String, Object>>) cmap
					.get("map_profitdis");
			if (l != null && l.size() > 0) {
				normMap.put("D_CODE", sub(l.get(l.size() - 1)
						.get("MAIN_INCOME"), l.get(l.size() - 1).get(
						"MAIN_COST")));
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}

			}
		} else if ("税后利润".equals(normMap.get("NORM_NAME") + "")) {
			// 单位（万元） 取自[损益表]表子段
			List<Map<String, Object>> l = (List<Map<String, Object>>) cmap
					.get("map_profitdis");
			if (l != null && l.size() > 0) {
				normMap.put("D_CODE",
						l.get(l.size() - 1).get("INCOME_TAX") != null ? l.get(
								l.size() - 1).get("INCOME_TAX") : 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("固定资产净值".equals(normMap.get("NORM_NAME") + "")) {
			// 单位（万元） 取自[资产负债表]表子段
			List<Map<String, Object>> l = (List<Map<String, Object>>) cmap
					.get("map_debt");
			if (l != null && l.size() > 0) {
				normMap.put("D_CODE",
						l.get(l.size() - 1).get("FIXED_NET") != null ? l.get(
								l.size() - 1).get("FIXED_NET") : 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("净资产与年末贷款余额比率".equals(normMap.get("NORM_NAME") + "")) {
			// 净资产/年末贷款余额*１００％ 百分比
			List<Map<String, Object>> l = (List<Map<String, Object>>) cmap
					.get("map_debt");
			if (l != null && l.size() > 0) {
				// 净资产 取自[负债表]所有者权益合计字段
				Object owerTotal = l.get(l.size() - 1).get("OWER_TOTAL");

				// 年末贷款余额
				Object longLoan = l.get(l.size() - 1).get("LONG_LOAN");

				normMap.put("D_CODE", div(owerTotal, longLoan));
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("资本固定化比率".equals(normMap.get("NORM_NAME") + "")) {

			List<Map<String, Object>> l = (List<Map<String, Object>>) cmap
					.get("map_debt");
			// （资产总额－流动资产总额）／所有者权益*１００％
			if (l != null && l.size() > 0) {
				// 资产总额 取自[资产负债表] 表字段
				Object NET_TOTAL = l.get(l.size() - 1).get("NET_TOTAL");

				// 流动资产总额 取自[资产负债表] 表字段
				Object FLOW_ASSETS_TOTAL = l.get(l.size() - 1).get(
						"FLOW_ASSETS_TOTAL");

				// 所有者权益 取自[资产负债表] 表字段
				Object OWER_TOTAL = l.get(l.size() - 1).get("OWER_TOTAL");

				normMap.put("D_CODE", div(sub(NET_TOTAL, FLOW_ASSETS_TOTAL),
						OWER_TOTAL));
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("流动比率".equals(normMap.get("NORM_NAME") + "")) {
			// 流动比率=流动资产/流动负债 取自[资信财报-财务分析]表字段 百分比
			List<Map<String, Object>> l = (List<Map<String, Object>>) cmap
					.get("map_analyze");
			if (l != null && l.size() > 0) {

				normMap.put("D_CODE",
						l.get(l.size() - 1).get("FLOW_RATE") != null ? l.get(
								l.size() - 1).get("FLOW_RATE") : 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("速动比率".equals(normMap.get("NORM_NAME") + "")) {
			// 速动比率 速冻比率=（流动资产-存货）/流动负债 QUICK_FREEZE_RATE 取自[资信财报-财务分析]表字段 百分比
			List<Map<String, Object>> l = (List<Map<String, Object>>) cmap
					.get("map_analyze");
			if (l != null && l.size() > 0) {
				normMap.put("D_CODE", l.get(l.size() - 1).get(
						"QUICK_FREEZE_RATE") != null ? l.get(l.size() - 1).get(
						"QUICK_FREEZE_RATE") : 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}

		} else if ("现金比率".equals(normMap.get("NORM_NAME") + "")) {
			// （现金等价物＋短期投资＋应收票据）／流动负债合计 百分比
			List<Map<String, Object>> lDept = (List<Map<String, Object>>) cmap
					.get("map_dept");
			List<Map<String, Object>> lCash = (List<Map<String, Object>>) cmap
					.get("map_cash");
			if (lDept != null && lDept.size() > 0 && lCash != null
					&& lCash.size() > 0) {

				// 现金等价物 取自[损益表] 字段
				Object finalCashEquivalents = lCash.get(lCash.size() - 1).get(
						"FINAL_CASH_EQUIVALENTS");

				// 短期投资 取自[资产负债表] 表字段
				Object shortInvestment = lDept.get(lDept.size() - 1).get(
						"SHORT_INVESTMENT");

				// 应收票据 取自[资产负债表] 表字段
				Object accountsBills = lDept.get(lDept.size() - 1).get(
						"ACCOUNTS_BILLS");

				// 流动负债合计 取自[资产负债表] 表字段
				Object flowDabtTotal = lDept.get(lDept.size() - 1).get(
						"FLOW_DABT_TOTAL");

				normMap.put("D_CODE", div(add(add(finalCashEquivalents,
						shortInvestment), accountsBills), flowDabtTotal));
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("主营业务收入现金率".equals(normMap.get("NORM_NAME") + "")) {
			// ［销售收入－（期末应收帐款－期初应收帐款）］／销售收入*１００％ 百分比

			List<Map<String, Object>> lDept = (List<Map<String, Object>>) cmap
					.get("map_dept");
			List<Map<String, Object>> lProfitdis = (List<Map<String, Object>>) cmap
					.get("map_profitdis");
			if (lDept != null && lDept.size() > 0 && lProfitdis != null
					&& lProfitdis.size() > 0) {

				// 销售收入
				Double salesRevenue = sub(lProfitdis.get(lProfitdis.size() - 1)
						.get("MAIN_INCOME"), lProfitdis.get(
						lProfitdis.size() - 1).get("MAIN_COST"));
				// 期末应收账款
				Object accountsSectionQM = lDept.size() >= 2 ? lDept.get(
						lDept.size() - 2).get("ACCOUNTS_SECTION") : 0d;

				// 期初应收账款
				Object accountsSectionQC = lDept.get(lDept.size() - 1).get(
						"ACCOUNTS_SECTION");

				normMap.put("D_CODE", div(sub(salesRevenue, sub(
						accountsSectionQM, accountsSectionQC)), salesRevenue));
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("担保比率".equals(normMap.get("NORM_NAME") + "")) {
			// 年末未清担保余额／所有者权益*１００％ 百分比

			List<Map<String, Object>> lDept = (List<Map<String, Object>>) cmap
					.get("map_dept");
			if (lDept != null && lDept.size() > 0) {

				// 年末未清担保余额 TODO

				// 所有者权益
				Object owerTotal = lDept.get(lDept.size() - 1)
						.get("OWER_TOTAL");

				normMap.put("D_CODE", 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("应收帐款周转速度".equals(normMap.get("NORM_NAME") + "")) {
			// 全年销售收入／［（期初应收帐款余额原值＋期末应收帐款余额原值）／２］*１００％ 次数
			List<Map<String, Object>> lProfitdis = (List<Map<String, Object>>) cmap
					.get("map_profitdis");
			List<Map<String, Object>> lDept = (List<Map<String, Object>>) cmap
					.get("map_profitdis");
			if (lProfitdis != null && lProfitdis.size() > 0 && lDept != null
					&& lDept.size() > 0) {
				// 全年销售收入
				Object salesRevenue = sub(lProfitdis.get(lProfitdis.size() - 1)
						.get("MAIN_INCOME"), lProfitdis.get(
						lProfitdis.size() - 1).get("MAIN_COST"));

				// 期末应收帐款余额原值
				Object accountsSectionQM = lDept.size() >= 2 ? lDept.get(
						lDept.size() - 2).get("ACCOUNTS_SECTION") : 0d;

				// 期初应收帐款余额原值
				Object accountsSectionQC = lDept.get(lDept.size() - 1).get(
						"ACCOUNTS_SECTION");

				normMap.put("D_CODE", div(salesRevenue, div(add(
						accountsSectionQC, accountsSectionQM), 2d)));
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("存货周转速度".equals(normMap.get("NORM_NAME") + "")) {
			// 销售成本／［（期初存货＋期末存货）／２］*１００％ 次数
			List<Map<String, Object>> lDept = (List<Map<String, Object>>) cmap
					.get("map_dept");
			List<Map<String, Object>> lProfitdis = (List<Map<String, Object>>) cmap
					.get("map_profitdis");
			if (lDept != null && lDept.size() > 0 && lProfitdis != null
					&& lProfitdis.size() > 0) {

				// 销售成本
				Object mainCost = lProfitdis.get(lProfitdis.size() - 1).get(
						"MAIN_COST");

				// 期初存货 取自[负债表] 存货字段
				Object stock_QC = lDept.size() >= 2 ? lDept.get(
						lDept.size() - 2).get("STOCK") : 0d;

				// 期末存货 取自[负债表] 存货字段
				Object stock_QM = lDept.get(lDept.size() - 1).get("STOCK");

				normMap.put("D_CODE", div(mainCost, div(
						add(stock_QC, stock_QM), 2d)));
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("总资产周转速度".equals(normMap.get("NORM_NAME") + "")) {
			// 全年销售收入／［（期初资产总额＋期末资产总额）／２］*１００％ 次数
			List<Map<String, Object>> lProfitdis = (List<Map<String, Object>>) cmap
					.get("map_profitdis");
			List<Map<String, Object>> lDept = (List<Map<String, Object>>) cmap
					.get("map_dept");
			if (lProfitdis != null && lProfitdis.size() > 0 && lDept != null
					&& lDept.size() > 0) {

				// 全年销售收入
				Object salesRevenue = sub(lProfitdis.get(lProfitdis.size() - 1)
						.get("MAIN_INCOME"), lProfitdis.get(
						lProfitdis.size() - 1).get("MAIN_COST"));

				// 资产总额 取自[资产负债表] 表字段
				Object NET_TOTAL_QC = lDept.size() >= 2 ? lDept.get(
						lDept.size() - 2).get("NET_TOTAL") : 0d;

				// 期末资产总额
				Object NET_TOTAL_QM = lDept.get(lDept.size() - 1).get(
						"NET_TOTAL");

				normMap.put("D_CODE", div(salesRevenue, div(add(NET_TOTAL_QC,
						NET_TOTAL_QM), 2d)));
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("毛利率".equals(normMap.get("NORM_NAME") + "")) {
			// 　（全年主营业务收入－主营业务成本）／全年主营业务收入*１００％ 百分比
			List<Map<String, Object>> lProfitdis = (List<Map<String, Object>>) cmap
					.get("map_profitdis");
			if (lProfitdis != null && lProfitdis.size() > 0) {

				// 全年主营业务收入
				Object mainIncome = lProfitdis.get(lProfitdis.size() - 1).get(
						"MAIN_INCOME");

				// 主营业务成本
				Object mainCost = lProfitdis.get(lProfitdis.size() - 1).get(
						"MAIN_COST");

				normMap.put("D_CODE",
						div(sub(mainIncome, mainCost), mainIncome));
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("营业利润率".equals(normMap.get("NORM_NAME") + "")) {
			// 　营业利润／全年销售收入*１００％ 百分比
			List<Map<String, Object>> lProfitdis = (List<Map<String, Object>>) cmap
					.get("map_profitdis");
			if (lProfitdis != null && lProfitdis.size() > 0) {
				// 营业利润 取自[利润及利润分配表] 表字段
				Object operProfit = lProfitdis.get(lProfitdis.size() - 1).get(
						"OPER_PROFIT");

				// 全年销售收入
				Object salesRevenue = sub(lProfitdis.get(lProfitdis.size() - 1)
						.get("MAIN_INCOME"), lProfitdis.get(
						lProfitdis.size() - 1).get("MAIN_COST"));

				normMap.put("D_CODE", div(operProfit, salesRevenue));
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("净资产收益率".equals(normMap.get("NORM_NAME") + "")) {
			// 净资产收益率=净利润/平均净资产

			List<Map<String, Object>> lProfitdis = (List<Map<String, Object>>) cmap
					.get("map_profitdis");
			List<Map<String, Object>> lDept = (List<Map<String, Object>>) cmap
					.get("map_dept");
			if (lDept != null && lDept.size() > 0 && lProfitdis != null
					&& lProfitdis.size() > 0) {

				// 净利润 取自[损益表] 表字段
				Object profitNet = lProfitdis.get(lProfitdis.size() - 1).get(
						"PROFIT_NET");

				// 平均净资产 取自[负债表] 所有者权益合计字段
				Object owerTotal = lDept.get(lDept.size() - 1).get("OWERTOTAL");
				normMap.put("D_CODE", div(profitNet, owerTotal));
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("总资产收益率".equals(normMap.get("NORM_NAME") + "")) {
			List<Map<String, Object>> lAnalyze = (List<Map<String, Object>>) cmap
					.get("map_analyze");
			if (lAnalyze != null && lAnalyze.size() > 0) {
				// CASE WHEN NET_TOTAL!=0 THEN
				// ROUND(NVL(((PROFIT_NET/NET_TOTAL)*100),0),2) ELSE 0 END
				// JZCSYL 净资产收益率=净利润/平均净资产 取自[资信财报-财务分析]表字段 总资产收益率(%)

				normMap.put("D_CODE", lAnalyze.get(lAnalyze.size() - 1).get(
						"ACCET_RATE_OF_RETURN") != null ? lAnalyze.get(
						lAnalyze.size() - 1).get("ACCET_RATE_OF_RETURN") : 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("平均存货周转天数".equals(normMap.get("NORM_NAME") + "")) {
			// 平均存货周转天数=360*（(期初存货+期末存货)÷2)/主营业务成本 CHZHZH_DAYS 取自[资信财报-财务分析]表字段
			List<Map<String, Object>> lAnalyze = (List<Map<String, Object>>) cmap
					.get("map_analyze");
			if (lAnalyze != null && lAnalyze.size() > 0) {
				normMap.put("D_CODE", lAnalyze.get(lAnalyze.size() - 1).get(
						"CHZHZH_DAYS") != null ? lAnalyze.get(
						lAnalyze.size() - 1).get("CHZHZH_DAYS") : 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}

		} else if ("平均应收账款周转天数".equals(normMap.get("NORM_NAME") + "")) {
			// 平均应收账款周转天数 平均应收账款周转天数=((期初应收账款+期末应收账款)/2)×360天/销售收入 YSZKZE_DAYS
			// 取自[资信财报-财务分析]表字段
			List<Map<String, Object>> lAnalyze = (List<Map<String, Object>>) cmap
					.get("map_analyze");
			if (lAnalyze != null && lAnalyze.size() > 0) {

				normMap.put("D_CODE", lAnalyze.get(lAnalyze.size() - 1).get(
						"YSZKZE_DAYS") != null ? lAnalyze.get(
						lAnalyze.size() - 1).get("YSZKZE_DAYS") : 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}

		} else if ("销售净利润率".equals(normMap.get("NORM_NAME") + "")) {
			// 销售利润率=净利润/主营业务收入*100% SELL_NET_PROFIT 取自[资信财报-财务分析]表字段
			List<Map<String, Object>> lAnalyze = (List<Map<String, Object>>) cmap
					.get("map_analyze");
			if (lAnalyze != null && lAnalyze.size() > 0) {

				normMap.put("D_CODE", lAnalyze.get(lAnalyze.size() - 1).get(
						"SELL_NET_PROFIT") != null ? lAnalyze.get(
						lAnalyze.size() - 1).get("SELL_NET_PROFIT") : 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}

		} else if ("资产负债率".equals(normMap.get("NORM_NAME") + "")) {
			// 资产负债率 资产负债率=负债合计/总资产*100% ASSET_LIABILITIES_RATE 取自[资信财报-财务分析]表字段
			List<Map<String, Object>> lAnalyze = (List<Map<String, Object>>) cmap
					.get("map_analyze");
			if (lAnalyze != null && lAnalyze.size() > 0) {

				normMap.put("D_CODE", lAnalyze.get(lAnalyze.size() - 1).get(
						"ASSET_LIABILITIES_RATE") != null ? lAnalyze.get(
						lAnalyze.size() - 1).get("ASSET_LIABILITIES_RATE") : 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}

		} else if ("总资产".equals(normMap.get("NORM_NAME") + "")) {
			// 总资产 资产负债表最后的“合计” NET_TOTAL 取自[资产负债表] 表字段
			List<Map<String, Object>> lDept = (List<Map<String, Object>>) cmap
					.get("map_dept");
			if (lDept != null && lDept.size() > 0) {
				normMap.put("D_CODE", lDept.get(lDept.size() - 1).get(
						"NET_TOTAL") != null ? lDept.get(lDept.size() - 1).get(
						"NET_TOTAL") : 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}

		} else if ("总资产报酬率".equals(normMap.get("NORM_NAME") + "")) {
			// 总资产报酬率 总资产收益率=净利润/总资产*100% 取自[资信财报-财务分析]表字段
			List<Map<String, Object>> lAnalyze = (List<Map<String, Object>>) cmap
					.get("map_analyze");
			if (lAnalyze != null && lAnalyze.size() > 0) {

				normMap.put("D_CODE", lAnalyze.get(lAnalyze.size() - 1).get(
						"ACCET_RATE_OF_RETURN") != null ? lAnalyze.get(
						lAnalyze.size() - 1).get("ACCET_RATE_OF_RETURN") : 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("销售毛利率".equals(normMap.get("NORM_NAME") + "")) {
			// 取自[资信财报-财务分析]表字段
			List<Map<String, Object>> lAnalyze = (List<Map<String, Object>>) cmap
					.get("map_analyze");
			if (lAnalyze != null && lAnalyze.size() > 0) {

				normMap.put("D_CODE", lAnalyze.get(lAnalyze.size() - 1).get(
						"SELL_GROSS_PROFIT") != null ? lAnalyze.get(
						lAnalyze.size() - 1).get("SELL_GROSS_PROFIT") : 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("杠杆率".equals(normMap.get("NORM_NAME") + "")) {
			// 取自[资信财报-财务分析]表字段
			List<Map<String, Object>> lAnalyze = (List<Map<String, Object>>) cmap
					.get("map_analyze");
			if (lAnalyze != null && lAnalyze.size() > 0) {

				normMap.put("D_CODE", lAnalyze.get(lAnalyze.size() - 1).get(
						"LEVER_RATE") != null ? lAnalyze.get(
						lAnalyze.size() - 1).get("LEVER_RATE") : 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}

		} else if ("销售收入增长率".equals(normMap.get("NORM_NAME") + "")) {
			// 取自[资信财报-财务分析]表字段
			List<Map<String, Object>> lAnalyze = (List<Map<String, Object>>) cmap
					.get("map_analyze");
			if (lAnalyze != null && lAnalyze.size() > 0) {

				normMap.put("D_CODE", lAnalyze.get(lAnalyze.size() - 1).get(
						"SELL_INCOME_INCREASE") != null ? lAnalyze.get(
						lAnalyze.size() - 1).get("SELL_INCOME_INCREASE") : 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("资产增长率".equals(normMap.get("NORM_NAME") + "")) {
			// 取自[资信财报-财务分析]表字段
			List<Map<String, Object>> lAnalyze = (List<Map<String, Object>>) cmap
					.get("map_analyze");
			if (lAnalyze != null && lAnalyze.size() > 0) {

				normMap.put("D_CODE", lAnalyze.get(lAnalyze.size() - 1).get(
						"ASSET_INCOME_INCREASE") != null ? lAnalyze.get(
						lAnalyze.size() - 1).get("ASSET_INCOME_INCREASE") : 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("总资产周转天数".equals(normMap.get("NORM_NAME") + "")) {
			// 取自[资信财报-财务分析]表字段
			List<Map<String, Object>> lAnalyze = (List<Map<String, Object>>) cmap
					.get("map_analyze");
			if (lAnalyze != null && lAnalyze.size() > 0) {

				normMap.put("D_CODE", lAnalyze.get(lAnalyze.size() - 1).get(
						"TOTAL_ASSET_DAYS") != null ? lAnalyze.get(
						lAnalyze.size() - 1).get("TOTAL_ASSET_DAYS") : 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("现金流动负债比率".equals(normMap.get("NORM_NAME") + "")) {
			// 取自[资信财报-财务分析]表字段
			List<Map<String, Object>> lAnalyze = (List<Map<String, Object>>) cmap
					.get("map_analyze");
			if (lAnalyze != null && lAnalyze.size() > 0) {

				normMap.put("D_CODE", lAnalyze.get(lAnalyze.size() - 1).get(
						"CASH_FLOW_DEBT") != null ? lAnalyze.get(
						lAnalyze.size() - 1).get("CASH_FLOW_DEBT") : 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("现金债务总额比率".equals(normMap.get("NORM_NAME") + "")) {
			// 取自[资信财报-财务分析]表字段
			List<Map<String, Object>> lAnalyze = (List<Map<String, Object>>) cmap
					.get("map_analyze");
			if (lAnalyze != null && lAnalyze.size() > 0) {

				normMap.put("D_CODE", lAnalyze.get(lAnalyze.size() - 1).get(
						"XJFZZE_RATE") != null ? lAnalyze.get(
						lAnalyze.size() - 1).get("XJFZZE_RATE") : 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("销售现金比率".equals(normMap.get("NORM_NAME") + "")) {
			// 取自[资信财报-财务分析]表字段
			List<Map<String, Object>> lAnalyze = (List<Map<String, Object>>) cmap
					.get("map_analyze");
			if (lAnalyze != null && lAnalyze.size() > 0) {

				normMap.put("D_CODE", lAnalyze.get(lAnalyze.size() - 1).get(
						"SELL_CASH_RATE") != null ? lAnalyze.get(
						lAnalyze.size() - 1).get("SELL_CASH_RATE") : 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("营业现金回笼率".equals(normMap.get("NORM_NAME") + "")) {
			// 取自[资信财报-财务分析]表字段
			List<Map<String, Object>> lAnalyze = (List<Map<String, Object>>) cmap
					.get("map_analyze");
			if (lAnalyze != null && lAnalyze.size() > 0) {

				normMap.put("D_CODE", lAnalyze.get(lAnalyze.size() - 1).get(
						"YYXJHL_RATE") != null ? lAnalyze.get(
						lAnalyze.size() - 1).get("YYXJHL_RATE") : 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		} else if ("EBITDA".equals(normMap.get("NORM_NAME") + "")) {
			// 取自[资信财报-财务分析]表字段
			List<Map<String, Object>> lAnalyze = (List<Map<String, Object>>) cmap
					.get("map_analyze");
			if (lAnalyze != null && lAnalyze.size() > 0) {

				normMap.put("D_CODE", lAnalyze.get(lAnalyze.size() - 1).get(
						"EBITDA") != null ? lAnalyze.get(lAnalyze.size() - 1)
						.get("EBITDA") : 0);
				switch (describe) {
				case 1:
					score = queryScoreIntervalByCode(normMap);
					break;
				case 2:
					score = queryScoreByExpression(normMap);
					break;

				default:
					break;
				}
			}
		}

		// 未统计出分值返回0
		if (!"".equals(score)) {
			return score;
		} else {
			return "0";
		}
	}

	/**
	 * 获取选项类定量打分项分值
	 * 
	 * @date 2014-4-3 上午09:25:16
	 * @author 作者 吴剑东
	 */
	public String queryScoreByCode() {
		return Dao.selectInt(path + "queryScoreByCode", map) + "";
	}

	/**
	 * 获取区间类定量打分项分值
	 * 
	 * @date 2014-4-3 上午09:25:16
	 * @author 作者 吴剑东
	 */
	public String queryScoreIntervalByCode(Map<String, Object> param) {
		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("D_CODE", param.get("D_CODE"));
		refer.put("D_TYPE", param.get("NORM_NAME"));
		refer.put("TRADE_TYPE", param.get("TRADE_TYPE"));
		return Dao.selectInt(path + "queryScoreIntervalByCode", refer) + "";
	}

	public String queryScoreByExpression(Map<String, Object> param) {

		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("D_TYPE", param.get("NORM_NAME"));
		refer.put("TRADE_TYPE", param.get("TRADE_TYPE"));
		List<Map<String, Object>> listMap = Dao.selectList(path
				+ "queryScoreExpress", refer);
		for (Map<String, Object> map : listMap) {
			String expression = map.get("EXPRESSION").toString();
			System.out
					.println("---------------expression=" + expression
							+ "---------map=" + map + "-----------------param="
							+ param);
			if (checkExpression(expression, param.get("D_CODE").toString())) {
				String score = map.get("FLAG_INTNA").toString();
				return getScore(score, param.get("D_CODE").toString(), 2);
			}
		}
		return "0";
	}

	/**
	 * 验证表达式结果
	 * 
	 * @param @param expression
	 * @param @param value
	 * @param @return
	 * @return boolean
	 * @throws
	 * @date 2014年7月14日 上午11:17:57
	 * @author WuYanFei
	 */
	public static boolean checkExpression(String expression, String value) {
		Evaluator e = new Evaluator();
		
		expression = formatExpression(expression);		

		// 把未知数替换为实际值
		expression = expression.replace("x", value);
		boolean flag = false;
		try {
			flag = e.getBooleanResult(expression);
		} catch (EvaluationException e1) {
			e1.printStackTrace();
		}
		return flag;
	}

	public static String getScore(String expression, String value, int bit) {
		Evaluator e = new Evaluator();
		expression = formatExpression(expression).replace("x",
				value);
		String score = "0";
		try {
			System.out.println("--------------------" + expression);
			score = e.evaluate(expression);
			System.out.println("--score--" + score);

			BigDecimal big = new BigDecimal(score);
			score = (big.setScale(bit, RoundingMode.HALF_UP).toString());
		} catch (EvaluationException e1) {
			// 发生计算错误直接给0，防止系统崩溃
			score = "0";
			e1.printStackTrace();
		}
		return score;
	}

	public static boolean isNumber(String str) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern
				.compile("[0-9]*");
		java.util.regex.Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			System.out.println(str+"aaaaa");
			return false;
		} else {
			System.out.println(str+"dddddd");
			return true;
		}
	}

	public static String formatExpression(String expression) {
		//去除空格
		expression = expression.toLowerCase().replace(" ", "") ;
		
		// 把表达式中包含逻辑字符'and' 'or'替换为虚拟机识别的 '&&' ,'||'
		if (expression.indexOf("and") > 0) {
			expression = expression.replace("and", "&&");
		}
		if (expression.indexOf("or") > 0) {
			expression = expression.replace("or", "||");
		}
		while (expression.contains("%")) {
			expression = getReplaceString(expression) ;
		}
		return expression;
	}

	public static String getReplaceString(String expression) {
		int start = expression.indexOf("%") - 1;
		int end = expression.indexOf("%");
		String subStr = expression.substring(start, end);
		try{
			while(isNumber(subStr)){
				--start;
				subStr=expression.substring(start, end);	
			}
		}catch (Exception e) {
			//防止向前推对比的串下表越界 如替换  10%为 （10/100）
		}
		
		String replaceStr = expression.substring(start + 1, end+1);
		String value=expression.toLowerCase().replace(" ", "").replace(replaceStr,
				"(" + expression.substring(start + 1, end) + "/100)");
		System.out.println(expression+"==="+value);
		return value;
	}

	// 减法
	public Double sub(Object obj1, Object obj2) {
		BigDecimal v1 = new BigDecimal(0);
		BigDecimal v2 = new BigDecimal(0);
		if (StringUtils.isNotBlank(obj1))
			v1 = new BigDecimal(obj1.toString());
		if (StringUtils.isNotBlank(obj2))
			v2 = new BigDecimal(obj2.toString());
		BigDecimal v3 = v1.subtract(v2);
		System.out.println("sub------------" + v3.doubleValue());
		return v3.doubleValue();
	}

	// 除法 (百分比)
	public Double div(Object obj1, Object obj2) {
		BigDecimal v1 = new BigDecimal(0);
		BigDecimal v2 = new BigDecimal(1);
		if (StringUtils.isNotBlank(obj1)){
			v1 = new BigDecimal(obj1.toString());
		}
			
		if (StringUtils.isNotBlank(obj2)){
			
			v2 = new BigDecimal(obj2.toString());
		}
		System.out.println("-------------------v1="+v1+"----------------v2="+v2);
		if(v2.doubleValue()==0d){
			return 0d;
		}
		BigDecimal v3 = v1.divide(v2, 3, RoundingMode.HALF_UP);
		return v3.doubleValue();
	}

	// 乘法
	public Double mul(Object obj1, Object obj2) {
		BigDecimal v1 = new BigDecimal(0);
		BigDecimal v2 = new BigDecimal(0);
		if (StringUtils.isNotBlank(obj1))
			v1 = new BigDecimal(obj1.toString());
		if (StringUtils.isNotBlank(obj2))
			v2 = new BigDecimal(obj2.toString());
		BigDecimal v3 = v1.multiply(v2);
		return v3.doubleValue();
	}

	// 加法
	public Double add(Object obj1, Object obj2) {
		BigDecimal v1 = new BigDecimal(0);
		BigDecimal v2 = new BigDecimal(0);
		if (StringUtils.isNotBlank(obj1))
			v1 = new BigDecimal(obj1.toString());
		if (StringUtils.isNotBlank(obj2))
			v2 = new BigDecimal(obj2.toString());
		BigDecimal v3 = v1.add(v2);
		return v3.doubleValue();
	}

	public static void main(String[] args) {
		String str = getReplaceString("10*20/80%");
		BigDecimal v1 = new BigDecimal("111111111111111111111111");
		BigDecimal v2 = new BigDecimal("111111111111111111110000");
		System.out.println(v1.subtract(v2).doubleValue());
		System.out.println("aaaa%".replace("%", ""));
		System.out.println("qaaaa%".replace("%", "/100"));
		Evaluator ev = new Evaluator();
		try {

			System.out.println(ev.evaluate("100.0*2/50"));
			ev.evaluate("1<#{x}<7");
			ev.getVariables();
			System.out.println();
			System.out.println(ev.getBooleanResult("1<5<7"));
		} catch (Exception e) {
			System.out.println();
		}
	}
}
