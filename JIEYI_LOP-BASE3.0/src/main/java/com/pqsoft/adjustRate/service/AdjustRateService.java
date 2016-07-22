package com.pqsoft.adjustRate.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.math.Finance;
import com.pqsoft.util.JsonUtil;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;
import com.pqsoft.util.Util;

public class AdjustRateService {
	/********** 调息*@auth: king 2014年9月10日 *************************/

	private String NAMESPACE = "adjustRate.";

	// 调息结果描述
	private String RESULT_MSG = "";
	// 0 调息正常 1调息异常
	private int TYPE_ = 0;

	/**
	 * 获取最新的利率配置时间
	 * 
	 * @return
	 * @author King 2014年9月11日
	 */
	public String queryNewBaseRate() {
		return Dao.selectOne(NAMESPACE + "queryNewBaseRate");
	}

	/**
	 * 获取调息的批次
	 * 
	 * @return
	 * @author King 2014年9月11日
	 */
	public String getBatch() {
		return Dao.getSequence("SEQ_UPDATE_INTEREST_BATCH");
	}

	/**
	 * 查询调息结果列表
	 * 
	 * @param map
	 * @return
	 * @author King 2014年9月11日
	 */
	public Page doShowAdjustRateMg(Map<String, Object> map) {
		return PageUtil.getPage(map, NAMESPACE + "queryAdjustRateList",
				NAMESPACE + "queryAdjustRateCount");
	}

	/**
	 * 查询调息明细
	 * 
	 * @param map
	 * @return
	 * @author King 2014年9月11日
	 */
	public List<Map<String, Object>> showAdjustDetail(Map<String, Object> map) {
		return Dao.selectList(NAMESPACE + "showAdjustDetail", map);
	}

	/**
	 * 根据条件查询需要调息的还款计划
	 * 
	 * @param map
	 * @return
	 * @author King 2014年9月10日
	 */
	public List<Map<String, Object>> showRentPlanHead(Map<String, Object> map) {
		map.put("ROWNUM_", "10");
		return Dao.selectList(NAMESPACE + "showRentPlanHead", map);
	}

	/**
	 * 检验是否符合调息的条件
	 * 
	 * @param map
	 * @return
	 * @author King 2014年9月11日
	 */
	public boolean isCheckFactor(Map<String, Object> map) {
		RESULT_MSG = "";
		TYPE_ = 0;
		// 正常、缩期、展期、不等额、提前还款、B计划
		if (!"0".equals(map.get("STATUS").toString().trim())
				&& !"4".equals(map.get("STATUS").toString().trim())
				&& !"7".equals(map.get("STATUS").toString().trim())
				&& !"8".equals(map.get("STATUS").toString().trim())
				&& !"9".equals(map.get("STATUS").toString().trim())
				&& !"300".equals(map.get("STATUS").toString().trim())) {
			RESULT_MSG = map.get("STATUS_NAME") + "";
			TYPE_ = 1;
		} else if (StringUtils.isBlank(map.get("START_DATE"))) {
			RESULT_MSG = "未起租";
			TYPE_ = 1;
		} else if ("0".equals(map.get("YEAR_TYPE"))) {
			RESULT_MSG = "固定利率";
			TYPE_ = 1;
		} else if (StringUtils.isBlank(map.get("YEAR_TYPE"))) {
			RESULT_MSG = "利率类型未标清(固定/非固定)：" + map.get("YEAR_TYPE");
			TYPE_ = 1;
		} else if (!"PMT".equals(map.get("CALCULATE_WAY"))) {
			RESULT_MSG = "计算公式不支持:" + map.get("CALCULATE_WAY");
			TYPE_ = 1;
		}

		if (TYPE_ == 0)
			return getStartTerm(map);
		else {
			map.put("TYPE_", TYPE_);
			map.put("RESULT_MSG", RESULT_MSG);
			return false;
		}
	}

	/**
	 * 获取最新两次利率浮动
	 * 
	 * @return
	 * @author King 2014年9月10日
	 */
	public Map<String, Object> getRateAdjust() {
		// 查询央行调整利息（查询最近两次的利息）
		return Dao.selectOne(NAMESPACE + "get_rate");
	}

	/**
	 * 获取新利率
	 * 
	 * @param map
	 * @param rate
	 *            最新两次利率变动差
	 * @return
	 * @author King 2014年9月10日
	 */
	public double getNewYEARINTEREST(Map<String, Object> map,
			Map<String, Object> rate) {
		double new_rate = 0;
		double OLD_RATE = Double.parseDouble(map.get("YEAR_INTEREST") + "");
		double SIX_MONTHS = Double.parseDouble(rate.get("SIX_MONTHS") + "");
		double ONE_YEAR = Double.parseDouble(rate.get("ONE_YEAR") + "");
		double ONE_THREE_YEARS = Double.parseDouble(rate.get("ONE_THREE_YEARS")
				+ "");
		double THREE_FIVE_YEARS = Double.parseDouble(rate
				.get("THREE_FIVE_YEARS") + "");
		double OVER_FIVE_YEARS = Double.parseDouble(rate.get("OVER_FIVE_YEARS")
				+ "");
		int TERM = (int) Util.mul(Integer.parseInt(map.get("LEASE_TERM") + ""),
				Integer.parseInt(map.get("LEASE_PERIOD") + ""));
		if (TERM <= 6) {
			new_rate = Util.addDouble(OLD_RATE, SIX_MONTHS);
		} else if (6 < TERM && TERM <= 12) {
			new_rate = Util.addDouble(OLD_RATE, ONE_YEAR);
		} else if (12 < TERM && TERM <= 36) {
			new_rate = Util.addDouble(OLD_RATE, ONE_THREE_YEARS);
		} else if (36 < TERM && TERM <= 60) {
			new_rate = Util.addDouble(OLD_RATE, THREE_FIVE_YEARS);
		} else if (60 < TERM) {
			new_rate = Util.addDouble(OLD_RATE, OVER_FIVE_YEARS);
		}
		return new_rate;
	}

	/**
	 * 获取调息调试期次
	 * 
	 * @param map
	 * @author King 2014年9月10日
	 */
	public boolean getStartTerm(Map<String, Object> map) {
		Map<String, Object> temp = Dao.selectOne(NAMESPACE
				+ "binn_start_term_syzj", map);
		if (temp != null && !temp.isEmpty()) {
			map.put("start_term", temp.get("START_TERM"));// 开始期次
			map.put("syzj", temp.get("SYZJ"));// 剩余租金

			/*************** 用于记录调息结果展示 ************************/
			map.put("START_TERM", temp.get("START_TERM"));// 开始期次
			map.put("SYZJ", temp.get("SYZJ"));// 剩余租金
			/*************** 用于记录调息结果展示 ************************/

			map.put("total_term", temp.get("TOTAL_TERM"));// 剩余期次（调息期次数量）
			map.put("date_", temp.get("DATE_"));// 开始调息期次应收日期
			return true;
		} else {
			map.put("TYPE_", "1");
			map.put("RESULT_MGS", "没有找到匹配的调息期次");
			return false;
		}
	}

	/**
	 * 行转列
	 * 
	 * @param pay_id
	 * @return
	 * @author King 2014年9月10日
	 */
	public List<Map<String, String>> rowDenormaliser(String pay_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ID", pay_id);
		List<Map<String, Object>> list = Dao.selectList(
				NAMESPACE + "payDetail", map);
		List<Map<String, String>> lw = new ArrayList<Map<String, String>>();
		if (list.isEmpty() || list.size() < 1) {
			RESULT_MSG = "未索引到还款明细";
			TYPE_ = 1;
			return lw;
		}
		Map<String, String> mw = null;
		int num = Integer.parseInt(list.get(list.size() - 1).get("PERIOD_NUM")
				+ "");
		// 开始期次
		int t_num = Integer.parseInt(list.get(0).get("PERIOD_NUM") + "");
		double zj = 0d;
		double bj = 0d;
		double lx = 0d;
		double sybj = 0d;
		for (int i = t_num; i <= num; i++) {
			mw = new HashMap<String, String>();
			Set<Object> set = new HashSet<Object>();
			for (Map<String, Object> m : list) {
				if (StringUtils.isBlank(m.get("PAY_DATE"))) {
					RESULT_MSG = "己起租,但明细项无还款日期";
					TYPE_ = 1;
					break;
				}

				if ((i + "").equals(m.get("PERIOD_NUM") + "")
						&& "租金".equals(m.get("ITEM_NAME"))) {
					mw.put("zj", m.get("ITEM_MONEY") + "");
					set = new HashSet<Object>();
					set.add(m.get("PAY_DATE"));
					zj = Double.parseDouble(m.get("ITEM_MONEY") + "");

				} else if ((i + "").equals(m.get("PERIOD_NUM") + "")
						&& "本金".equals(m.get("ITEM_NAME"))) {
					mw.put("bj", m.get("ITEM_MONEY") + "");
					set = new HashSet<Object>();
					set.add(m.get("PAY_DATE"));
					bj = Double.parseDouble(m.get("ITEM_MONEY") + "");
				} else if ((i + "").equals(m.get("PERIOD_NUM") + "")
						&& "利息".equals(m.get("ITEM_NAME"))) {
					mw.put("lx", m.get("ITEM_MONEY") + "");
					set = new HashSet<Object>();
					set.add(m.get("PAY_DATE"));
					lx = Double.parseDouble(m.get("ITEM_MONEY") + "");
				} else if ((i + "").equals(m.get("PERIOD_NUM") + "")
						&& "剩余本金".equals(m.get("ITEM_NAME"))) {
					mw.put("sybj", m.get("ITEM_MONEY") + "");
					set = new HashSet<Object>();
					set.add(m.get("PAY_DATE"));
					sybj = Double.parseDouble(m.get("ITEM_MONEY") + "");
				}
			}

			if (Util.sub(zj, Util.addDouble(bj, lx)) != 0) {
				RESULT_MSG = "租金不等于本息合计.期次" + i + ",误差："
						+ Math.abs(Util.sub(zj, Util.addDouble(bj, lx)));
				TYPE_ = 1;
				break;
			} else if (zj < 0 || bj < 0 || lx < 0 || sybj < 0) {
				String name = 0 > zj ? "租金" : bj < 0 ? "本金" : lx < 0 ? "利息"
						: "剩余本金";
				RESULT_MSG = "金额出现负数.期次" + i + name;
				TYPE_ = 1;
				break;
			}
			if (set.size() > 0) {
				Iterator<Object> iterator = set.iterator();
				mw.put("PAY_DATE", iterator.next() + "");
				mw.put("qc", i + "");
			}
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
	 * 测算新的还款计划
	 * 
	 * @param m
	 * @return
	 * @author King 2014年9月10日
	 */
	@SuppressWarnings("rawtypes")
	public JSONArray quoteCalculateTest(Map<String, Object> m) {
		double annualRate = Double.parseDouble(m.get("annualRate") + "");
		int _leaseTerm =Integer.parseInt(m.get("_leaseTerm") + "");
		double residualPrincipal = Double.parseDouble(m
				.get("residualPrincipal") + "");
		int _payCountOfYear = Integer.parseInt(m.get("_payCountOfYear") + "");
		int start_term = Integer.parseInt(m.get("start_term") + "");
		String PAY_WAY = m.get("PAY_WAY") + "";
		String date = m.get("date_") + "";
		date = Util.getMonth(date, -12 / _payCountOfYear * 1);
		List<Map<String, String>> averagePrincipalInterest = null;
		Map<String, Object> tm = new HashMap<String, Object>();
		if (PAY_WAY.contains("不等额") || "2".equals(PAY_WAY)
				|| "3".equals(PAY_WAY)) {
			// 如果是第一次进来m.get("EditRows")为空，就按照正常的计算
			if (m.get("EditRows") == null || "".equals(m.get("EditRows"))
					|| "[]".equals(m.get("EditRows"))
					|| ((List) m.get("EditRows")).isEmpty()) {
				averagePrincipalInterest = AdjustRateService
						.averagePrincipalInterest(annualRate, _leaseTerm,
								residualPrincipal, _payCountOfYear, false,
								date, start_term,0);
				// averagePrincipalInterest =
				// AdjustRateService.averagePrincipalInterest(
				// annualRate, _leaseTerm, residualPrincipal,
				// _payCountOfYear, PAY_WAY.contains("期初"), date,
				// start_term);
				tm.put("code", 8);
			} else {
				JSONArray anomalyData = JSONArray.fromObject(m.get("EditRows"));
				// 不等额的期次数据
				averagePrincipalInterest = this.averagePrincipalInterest3(
						anomalyData, annualRate, _leaseTerm, residualPrincipal,
						_payCountOfYear, false, date, start_term);
				// averagePrincipalInterest = this.averagePrincipalInterest3(
				// anomalyData, annualRate, _leaseTerm, residualPrincipal,
				// _payCountOfYear, PAY_WAY.contains("期初"), date,
				// start_term);
				tm.put("code", 8);
			}
		} else if (PAY_WAY.contains("等额本息") || "1".equals(PAY_WAY)
				|| "4".equals(PAY_WAY)) {
			averagePrincipalInterest = AdjustRateService
					.averagePrincipalInterest(annualRate, _leaseTerm,
							residualPrincipal, _payCountOfYear,
							PAY_WAY.contains("期初"), date, start_term,0);
		}
		averagePrincipalInterest = AdjustRateService
				.manageLastIssue(averagePrincipalInterest);
		return JSONArray.fromObject(averagePrincipalInterest);
	}

	/**
	 * 保存
	 * 
	 * @param m
	 * @author King 2014年9月10日
	 */
	public void save(JSONObject m) {
		m.put("ID", m.get("PAY_ID"));
		// 得到租金计划编号
		m.put("PAYLIST_CODE", m.get("PAY_CODE"));
		m.put("NEWID", Dao.getSequence("SEQ_FIL_RENT_PLAN_HEAD"));
		// 插入明细表并删除未付款的租金信息
		
		 List<Map<String, String>> list =
		 AdjustRateService.getParsePayList((JSONArray)m.get("data_"));
		 double money_all=0d;
		 for (Map<String, String> map : list) {
			 map.put("PAY_ID", m.get("NEWID") + "");
			 map.put("ITEM_FLAG", "2");
			 if("租金".equals(map.get("ITEM_NAME")))
				 money_all+=Double.parseDouble(map.get("ITEM_MONEY"));
			 Dao.insert(NAMESPACE+"payplan_detail", map);
		 }
		 m.put("MONEY_ALL", money_all+Double.parseDouble(m.get("START_MONEY")+""));
		 // 插入主表queryPayByID
		 Dao.insert(NAMESPACE+"upgrade_payplan", m);
		 
		 Dao.insert(NAMESPACE+"insertOtherMoney",m);
		 
		 synchronizationBeginning(m);
	}

	/**
	 * 同步期初应收表
	 * 
	 * @param m
	 * @return
	 * @author King 2014年9月17日
	 */
	public int synchronizationBeginning(Map<String, Object> m) {
		// 删除财务表数据
		Dao.delete(NAMESPACE + "deleteBeginning", m);

		// 同步财务数据
		Map<String, String> temp1 = new HashMap<String, String>();
		temp1.put("PAY_ID", m.get("NEWID") + "");
		temp1.put("PAYLIST_CODE", m.get("PAYLIST_CODE") + "");
		temp1.put("STARTDATE", m.get("STARTDATE") + "");
		temp1.put("PMT", "PMT租金");
		temp1.put("ZJ", "租金");
		temp1.put("SYBJ", "剩余本金");
		temp1.put("D", "第");
		temp1.put("QI", "期");
		return Dao.insert(NAMESPACE + "synchronizationBeginning", temp1);
	}

	/**
	 * 等额本息调息
	 * 
	 * @param map
	 * @param rate
	 * @param start_term
	 *            调息开始期次
	 * @return
	 * @author King 2014年9月10日
	 */
	public void adjustRateByPmt(Map<String, Object> map,
			Map<String, Object> rate, int start_term) {
		// 剩余租金
		double syzj = 0;
		// 新支付表期次
		int total_term = 0;
		// 标志位，用于表示调息成功或失败 0成功，1失败
		try {
			// 浮动之后利率
			double NEW_YEAR_INTEREST = this.getNewYEARINTEREST(map, rate);

			syzj = Double.parseDouble(map.get("syzj") + "");
			total_term = Integer.parseInt(map.get("total_term") + "");
			// 获取到旧的支付表和计算出新的支付表
			List<Map<String, String>> oldPlan = rowDenormaliser(map
					.get("PAY_ID") + "");
			if (!oldPlan.isEmpty() && oldPlan.size() >= 1 && TYPE_ == 0) {
				map.put("annualRate", NEW_YEAR_INTEREST);
				map.put("_leaseTerm", total_term);
				map.put("residualPrincipal", syzj);
				map.put("_payCountOfYear", (int) (Util.div(12,
						Integer.parseInt(map.get("LEASE_PERIOD") + ""), 0)));

				/************ 调息当期租金变化 *******************/
				boolean flag = this.AccurateTerm(
						Integer.parseInt(map.get("LEASE_PERIOD") + ""),
						map.get("STARTDATE") + "", NEW_YEAR_INTEREST,
						Double.parseDouble(map.get("YEAR_INTEREST") + ""),
						oldPlan, start_term, map);
				/*******************************/

				JSONArray newPlan = this.quoteCalculateTest(map);
				// 截取出还款和抵扣后的期次后，合并新算出的
				JSONArray data_ = new JSONArray();
				double oldzjhj=0d;
				if (flag) {
					for (int j = 0; j < oldPlan.size(); j++) {
						if (j < start_term) {
							data_.add(oldPlan.get(j));
							oldzjhj+=Double.parseDouble(oldPlan.get(j).get("zj"));
						} else {
							oldzjhj+=Double.parseDouble(oldPlan.get(j).get("zj"));
						}
					}
				} else {
					for (int j = 0; j < oldPlan.size(); j++) {
						if (j < start_term - 1) {
							data_.add(oldPlan.get(j));
							oldzjhj+=Double.parseDouble(oldPlan.get(j).get("zj"));
						} else {
							oldzjhj+=Double.parseDouble(oldPlan.get(j).get("zj"));
						}
					}
				}
				data_.addAll(newPlan);
				data_ = JSONArray.fromObject(AdjustRateService.manageLastIssue(data_));
				JSONObject json = new JSONObject();
				json.put("data_", data_);
				json.put("PAY_CODE", map.get("PAYLIST_CODE"));
				json.put("PAY_ID", map.get("PAY_ID"));
				json.put("STARTDATE", map.get("STARTDATE"));
				json.put("YEAR_INTEREST", NEW_YEAR_INTEREST);
				json.put("START_MONEY", Double.parseDouble(map.get("OLDZJHJ")+"")-oldzjhj);
				this.save(json);
				RESULT_MSG = "调息成功";
			}
		} catch (Exception e) {
			e.printStackTrace();
			RESULT_MSG = "调息失败";
			TYPE_ = 1;
		}
		map.put("TYPE_", TYPE_);
		map.put("RESULT_MSG", RESULT_MSG);
	}

	/**
	 * 不等额调息
	 * 
	 * @param map
	 * @param rate
	 * @param start_term
	 * @author King 2014年9月10日
	 */
	@SuppressWarnings("unchecked")
	public void adjustRateByBDE(Map<String, Object> map,
			Map<String, Object> rate, int start_term) {
		// 剩余租金
		double syzj = 0;
		// 新支付表期次
		int total_term = 0;
		// 标志位，用于表示调息成功或失败 0成功，1失败
		int TYPE_ = 0;
		try {
			// 得到利率
			double NEW_YEAR_INTEREST = getNewYEARINTEREST(map, rate);
			syzj = Double.parseDouble(map.get("syzj") + "");
			total_term = Integer.parseInt(map.get("total_term") + "");
			// 获取到旧的支付表和计算出新的支付表
			List<Map<String, String>> oldPlan = rowDenormaliser(map
					.get("PAY_ID") + "");
			map.put("annualRate", NEW_YEAR_INTEREST);
			map.put("_leaseTerm", total_term);
			map.put("residualPrincipal", syzj);
			map.put("_payCountOfYear",
					(int) (Util.div(12,
							Integer.parseInt(map.get("LEASE_PERIOD") + ""), 0)));
			map.put("pay_way", "期末不等额");
			// map.put("date", map.get("date_"));
			// 不规则期次
			List<Map<String, Object>> period_list = Dao.selectList(NAMESPACE
					+ "anomaly_period_num", map);
			List<Map<String, String>> EditRows = new ArrayList<Map<String, String>>();
			if (period_list != null && !period_list.isEmpty()) {
				for (int j = 0; j < period_list.size(); j++) {
					Map<String, Object> period_map = (Map<String, Object>) period_list
							.get(j);
					String period = period_map.get("PERIOD_NUM") + "";
					for (int k = 0; k < oldPlan.size(); k++) {
						Map<String, String> tm = oldPlan.get(k);
						if (period.equals(tm.get("qc"))) {
							tm.put("lock", "yes");
							EditRows.add(tm);
							break;
						}
					}
				}
			}
			if (!EditRows.isEmpty()) {
				map.put("EditRows", EditRows);
			}

			/************ 调息当期租金变化 *******************/
			boolean flag = this.AccurateTerm(
					Integer.parseInt(map.get("LEASE_PERIOD") + ""),
					map.get("STARTDATE") + "", NEW_YEAR_INTEREST,
					Double.parseDouble(map.get("YEAR_INTEREST") + ""), oldPlan,
					start_term, map);
			/*******************************/

			JSONArray newPlan = this.quoteCalculateTest(map);
			// 截取出还款和抵扣后的期次后，合并新算出的
			JSONArray data_ = new JSONArray();
			double oldzjhj=0d;
			if (flag) {
				for (int j = 0; j < oldPlan.size(); j++) {
					if (j < start_term) {
						data_.add(oldPlan.get(j));
					} else {
						oldzjhj+=Double.parseDouble(oldPlan.get(j).get("zj"));
					}
				}
			} else {
				for (int j = 0; j < oldPlan.size(); j++) {
					if (j < start_term - 1) {
						data_.add(oldPlan.get(j));
					} else {
						oldzjhj+=Double.parseDouble(oldPlan.get(j).get("zj"));
					}
				}
			}
			data_.addAll(newPlan);
			// 处理最后一期
			data_ = JSONArray.fromObject(AdjustRateService
					.manageLastIssue(data_));
			// 把新的支付表保存并升级版本和同步应收表
			JSONObject json = new JSONObject();
			json.put("data_", data_);
			json.put("PAY_CODE", map.get("PAYLIST_CODE"));
			json.put("PAY_ID", map.get("PAY_ID"));
			json.put("YEAR_INTEREST", NEW_YEAR_INTEREST);
			json.put("STARTDATE", map.get("STARTDATE"));
			json.put("START_MONEY", Double.parseDouble(map.get("OLDZJHJ")+"")-oldzjhj);
			this.save(json);
			RESULT_MSG = "调息成功";
		} catch (Exception e) {
			e.printStackTrace();
			TYPE_ = 1;
			RESULT_MSG = "调息失败";
		}
		map.put("TYPE_", TYPE_);
		map.put("RESULT_MSG", RESULT_MSG);
	}

	/**
	 * 创建调息记录
	 * 
	 * @param tm
	 * @author King 2014年9月10日
	 */
	public void addFIL_RENT_UPDATE_INTEREST(Map<String, Object> tm) {
		Dao.insert(NAMESPACE + "FIL_RENT_UPDATE_INTEREST", tm);
	}

	/*
	 * 计算等额本息租金方法 annualRate 年利率 _leaseTerm 总期次 residualPrincipal 融资额（剩余本金）
	 * _payCountOfYear 每年支付次数 whetherBeginningPeriod 是否期初（期初true/期末false） date
	 * 第一期日期 changeIssue 从第几期开始
	 */
	public static List<Map<String, String>> averagePrincipalInterest(
			double annualRate, int _leaseTerm, double residualPrincipal,
			int _payCountOfYear, boolean whetherBeginningPeriod, String date,
			int changeIssue,double last_money) {
		List<Map<String, String>> lt = new ArrayList<Map<String, String>>();
//		double residualPrincipal_ = residualPrincipal;
		double zj = Finance.pmt(annualRate/_payCountOfYear, _leaseTerm, -residualPrincipal, last_money, whetherBeginningPeriod?1:0);
//		double zj = FinancialAlgLib.pmt(residualPrincipal_, annualRate,
//				_leaseTerm, _payCountOfYear, whetherBeginningPeriod);
		zj = Util.formatDouble2(zj);
		for (int i = 1; i <= _leaseTerm; i++) {
			LinkedHashMap<String, String> mw = new LinkedHashMap<String, String>();
			// double bj = Finance.ppmt(annualRate/_payCountOfYear, i,
			// (int)_leaseTerm, -residualPrincipal_);
			// double lx = Util.subDouble(zj, bj);
			// double sybj = Util.subDouble(residualPrincipal,bj);
			double lx = 0;
			if (!(whetherBeginningPeriod && changeIssue == 1 && i == 1)) {// 如果为期初（并且开始期次为1）的时候第一期利息为0
				// lx = residualPrincipal * annualRate / _payCountOfYear;
				lx = Util.div(Util.mul(residualPrincipal, annualRate),
						_payCountOfYear, 2);
			}
			lx = Util.formatDouble2(lx);
			double bj = Util.subDouble(zj, lx);
			bj = Util.formatDouble2(bj);
			double sybj = Util.subDouble(residualPrincipal, bj);
			sybj = Util.formatDouble2(sybj);
			// double lx =
			// Double.parseDouble(formatNumberDoubleTwo(String.valueOf(dremainprincipal*nyear_rate/100/income_number)));

			residualPrincipal = sybj;
			if (!"".equals(date)) {
				date = Util.getMonth(date, 12 / _payCountOfYear).toString();
			}
			mw.put("zj", zj + "");
			mw.put("bj", bj + "");
			mw.put("lx", lx + "");
			mw.put("sybj", sybj + "");
			mw.put("PMTzj", zj + "");
			mw.put("qc", changeIssue + i - 1 + "");
			mw.put("PAY_DATE", date + "");
			lt.add(mw);
		}
		return lt;
	}

	/*
	 * 计算不等额支付的
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String, String>> averagePrincipalInterest3(
			JSONArray anomalyData, double annualRate, int _leaseTerm,
			double _topricSubfirent, int _payCountOfYear,
			boolean whetherBeginningPeriod, String date, int changeIssue) {
		// 2.开始配平，内差法
		// 得到不等额的期次
		// JSONObject json = (JSONObject)anomalyData.get(0);
		// Map anomalyMap = JsonUtil.toMap(json);
		List<Map<String, String>> tmpList = averagePrincipalInterest(
				annualRate, _leaseTerm, _topricSubfirent, _payCountOfYear,
				whetherBeginningPeriod, date, changeIssue,0);
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
				if (whetherBeginningPeriod && changeIssue == 1 && i == 0)
					lx = 0;
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
				double zj = Util.formatDouble00(Double.parseDouble(map
						.get("zj")));
				// zj = Util.formatDouble0(zj);
				double lx = 0;
				if (!(whetherBeginningPeriod && changeIssue == 1 && i == 0)) {// 如果为期初（并且开始期次为1）的时候第一期利息为0
					// lx = _topricSubfirent * annualRate / _payCountOfYear;
					lx = Util.div(Util.mul(_topricSubfirent, annualRate),
							_payCountOfYear, 2);
				}
				lx = Util.formatDouble2(lx);
				double bj = Util.subDouble(zj, lx);
				bj = Util.formatDouble2(bj);
				double sybj = Util.subDouble(_topricSubfirent, bj);
				sybj = Util.formatDouble2(sybj);
				_topricSubfirent = sybj;
				map.put("zj", zj + "");
				map.put("PMTzj", zj + "");
				map.put("bj", bj + "");
				map.put("lx", lx + "");
				map.put("sybj", sybj + "");
				map.put("PAY_DATE", date1);
				map.put("qc", qc + i + "");
				date1 = Util.getMonth(date1, 12 / _payCountOfYear).toString();
			} else {
				// double bj = Double.parseDouble(map.get("bj"));
				// bj = Util.formatDouble2(bj);
				// double sybj = Util.subDouble(_topricSubfirent, bj);
				// sybj = Util.formatDouble2(sybj);
				// _topricSubfirent = sybj;
				// map.put("sybj", sybj + "");
				// map.put("PAY_DATE", date1);
				// map.put("qc", qc+i+"");
				// date1 = Util.getMonth(date1, 12 /
				// _payCountOfYear).toString();
				// double lx = Double.parseDouble(map.get("lx"));
				double lx = Util.div(Util.mul(_topricSubfirent, annualRate),
						_payCountOfYear, 2);
				lx = Util.formatDouble2(lx);
				double zj = Double.parseDouble(map.get("zj"));
				// zj = Util.formatDouble2(zj);
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
				date1 = Util.getMonth(date1, 12 / _payCountOfYear).toString();
			}
		}
		return list;
	}

	/*
	 * 计算的还款计划处理最后一期后返回
	 */
	public static List<Map<String, String>> manageLastIssue(
			List<Map<String, String>> list) {
		System.out.println(list.size() + "=======");
		if (list.size() >= 2) {
			Map<String, String> map1 = list.get(list.size() - 2);
			Map<String, String> map = list.get(list.size() - 1);
			double bj = Double.parseDouble(map1.get("sybj"));
			double zj = Double.parseDouble(map.get("zj"));
			double lx = Util.subDouble(zj, bj);
			map.put("bj", bj + "");
			map.put("lx", lx + "");
			map.put("sybj", "0");
		}
		return list;
	}

	/*
	 * 按照期次分组后显示的数据再次转换为一个资金项目为一条数据
	 */
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

	/**
	 * 检查是否可以还原 返回值大于0 不能还原
	 * 
	 * @param map
	 * @return
	 * @author King 2014年9月17日
	 */
	public int checkPay(Map<String, Object> map) {
		return Dao.selectInt(NAMESPACE + "checkPay", map);
	}

	/**
	 * 根据还款计划ID删除还款计划明细
	 * 
	 * @param pay_id
	 * @return
	 * @author King 2014年9月17日
	 */
	public int doDelRentDetail(Object pay_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PAY_ID", pay_id);
		return Dao.delete(NAMESPACE + "doDelRentDetail", map);
	}

	/**
	 * 根据还款计划ID删除还款计划表头
	 * 
	 * @param pay_id
	 * @return
	 * @author King 2014年9月17日
	 */
	public int doDelRentHead(Object pay_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PAY_ID", pay_id);
		return Dao.delete(NAMESPACE + "doDelRentHead", map);
	}

	/**
	 * 变更调息结果记录状态
	 * 
	 * @param map
	 * @return
	 * @author King 2014年9月17日
	 */
	public int doUpdateRentStatus(Map<String, Object> map) {
		return Dao.update(NAMESPACE + "doUpdateRentStatus", map);
	}

	/**
	 * 查询需要恢复的还款计划
	 * 
	 * @param map
	 * @return
	 * @author King 2014年9月24日
	 */
	public List<Map<String, Object>> queryhfPayList(Map<String, Object> map) {
		return Dao.selectList(NAMESPACE + "queryhfPayList", map);
	}

	/**
	 * 精确调息当期的调整
	 * 
	 * @param term_period
	 * @param adjust_date
	 * @param new_Year_Rate
	 *            扩大100倍的结果
	 * @param old_Year_Rate
	 *            扩大100倍的结果
	 * @param start_term
	 *            开始期次
	 * @param map
	 * @return
	 * @author King 2014年11月14日
	 */
	public boolean AccurateTerm(int term_period, String adjust_date,
			double new_Year_Rate, double old_Year_Rate,
			List<Map<String, String>> oldPlan, int start_term,
			Map<String, Object> mm) {
		List alist = DataDictionaryMemcached.getList("调息模式");
		String adjustModel = null;
		if (StringUtils.isNotBlank(alist) && !alist.isEmpty() ) {
			Object obj = alist.get(0);
			Map<String, Object> amap = (Map<String, Object>) obj;
			adjustModel = amap.get("CODE") + "";
		}
		boolean flag = false;
		double sybj = 0d;
		for (Map<String, String> map : oldPlan) {
			int qc = Integer.parseInt(map.get("qc"));
			if (qc == start_term - 1) {
				sybj = Double.parseDouble(map.get("sybj") + "");
			}
			if (qc == start_term) {
				double bj = Double.parseDouble(map.get("bj") + "");
				if (Math.round(sybj) <= 1) {
					sybj = Double.parseDouble(mm.get("syzj") + "");
				}
				if ("1".equals(adjustModel)) {
					//精确调息，即调息日期所处的期次以开始调息日期为转折点，之前的按老利率计算，之后的按新利率计算
					Calendar NowTermPayDate_c = Calendar.getInstance();
					Calendar adjustPayDate_c = Calendar.getInstance();
					Calendar nextPayDate_c = Calendar.getInstance();

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					long oldDay = 0;
					long newDay = 0;

					try {
						NowTermPayDate_c
								.setTime(sdf.parse(map.get("PAY_DATE")));
						nextPayDate_c.setTime(sdf.parse(map.get("PAY_DATE")));
						adjustPayDate_c.setTime(sdf.parse(adjust_date));
						NowTermPayDate_c.add(Calendar.MONTH, -term_period);
						oldDay = (adjustPayDate_c.getTime().getTime() - NowTermPayDate_c
								.getTime().getTime()) / (1000 * 3600 * 24);
						newDay = (nextPayDate_c.getTime().getTime() - adjustPayDate_c
								.getTime().getTime()) / (1000 * 3600 * 24);
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (NowTermPayDate_c.equals(adjustPayDate_c)
							|| nextPayDate_c.equals(adjustPayDate_c)) {
						mm.put("residualPrincipal", sybj);
						break;
					}
					/*************** 天利率按360天计算 **********************************/
					double lx = Util.formatDouble2(sybj
							* (old_Year_Rate * oldDay + new_Year_Rate * newDay)
							/ 360);
					/*************************************************/
					double zj = lx + bj;
					map.put("zj", zj + "");
					map.put("lx", lx + "");
					mm.put("residualPrincipal", sybj - bj);
					mm.put("_leaseTerm",
							Integer.parseInt(mm.get("_leaseTerm") + "") - 1);
					mm.put("date_", sdf.format(nextPayDate_c.getTime()));
					mm.put("start_term",
							Integer.parseInt(mm.get("start_term") + "") + 1);
					flag = true;
					break;
				} else {
					mm.put("residualPrincipal", sybj);
				}
			}
		}
		return flag;
	}
}
