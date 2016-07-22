package com.pqsoft.base.suppliersInfo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.Util;

public class FinancialStatisticsService {
	private final String xmlPath = "suppFinancialStatistics.";

	/**
	 * 添加行业资产负债
	 * 
	 * @date 2014-3-6 下午05:00:22
	 * @author WuYanFei
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public boolean insertDebt(Map<String, Object> map) {
		JSONObject json = JSONObject.fromObject(map.get("ChangeViewData"));
		List<Map<String, Object>> list = json.getJSONArray("FORMDATA");
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("CREATE_NAME", Security.getUser().getName());
		data.put("CREATE_CODE", Security.getUser().getCode());
		data.put("CREATE_ID", Security.getUser().getId());
		data.put("UNIT_NAME", map.get("UNIT_NAME"));
		data.put("CHECK_PEOPLE", map.get("CHECK_PEOPLE"));
		data.put("CHECK_DATE", map.get("CHECK_DATE"));
		data.put("INDEX_CODE", map.get("INDEX_CODE"));
		data.put("BELONG_DATE", map.get("BELONG_DATE"));
		data.put("REVIEW_PEOPLE", map.get("REVIEW_PEOPLE"));
		data.put("REVIEW_DATE", map.get("REVIEW_DATE"));
		//data.put("FIXED_ASSENT_ORIGINAL", map.get("FIXED_ASSENT_ORIGINAL"));
		data.put("CLIENT_ID", map.get("CLIENT_ID"));
		data.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));
		data.put("IDENTIFIERS", map.get("MANAGEID"));
		int k = 0;

		for (Map<String, Object> refer : list) {
			refer.putAll(data);
			k = Dao.insert(xmlPath + "insertDebt", refer);
		}

		if (k > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 行业损益表
	 * 
	 * @date 2014-3-7 下午03:39:15
	 * @author WuYanFei
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int insertProfitDistri(Map<String, Object> map) {
		JSONObject json = JSONObject.fromObject(map.get("ChangeViewData"));
		List<Map<String, Object>> list = json.getJSONArray("FORMDATA");

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("UNIT_NAME", json.get("UNIT_NAME"));
		data.put("CHECK_PEOPLE", json.get("CHECK_PEOPLE"));
		data.put("CHECK_DATE", json.get("CHECK_DATE"));
		data.put("INDEX_CODE", json.get("INDEX_CODE"));
		data.put("BELONG_DATE", json.get("BELONG_DATE"));
		data.put("REVIEW_PEOPLE", json.get("REVIEW_PEOPLE"));
		data.put("REVIEW_DATE", json.get("REVIEW_DATE"));
		data.put("MANAGEID", map.get("MANAGEID"));
		data.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));
		System.out.println("----------uu"+data);
		data.put("PEOPLE_ID", json.get("CLIENT_ID"));
		data.put("CREADE_CODE", map.get("CREADE_CODE"));
		int k = 0;
		for (Map<String, Object> refer : list) {
			refer.putAll(data);
			k = Dao.insert(xmlPath + "saveFinnaceProfitDistriButtion", refer);
		}
		return k;
	}

	/**
	 * 现金流量表 saveFinnaceCash
	 * 
	 * @date 2014-3-26 下午04:02:18
	 * @author WuYanFei
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int insertCash(Map<String, Object> map) {
		JSONObject json = JSONObject.fromObject(map.get("ChangeViewData"));
		List<Map<String, Object>> list = json.getJSONArray("FORMDATA");

		Map<String, Object> data = new HashMap<String, Object>();

		data.put("UNIT_NAME", json.get("UNIT_NAME"));
		data.put("CHECK_PEOPLE", json.get("CHECK_PEOPLE"));
		data.put("CHECK_DATE", json.get("CHECK_DATE"));
		data.put("INDEX_CODE", json.get("INDEX_CODE"));
		data.put("BELONG_DATE", json.get("BELONG_DATE"));
		data.put("REVIEW_PEOPLE", json.get("REVIEW_PEOPLE"));
		data.put("REVIEW_DATE", json.get("REVIEW_DATE"));

		data.put("CREATE_NAME", Security.getUser().getName());
		data.put("CREATE_CODE", Security.getUser().getCode());
		data.put("CLIENT_ID", map.get("PEOPLE_ID"));
		data.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));
		data.put("MANAGEID", map.get("MANAGEID"));

		data.put("UNKNOWN_ONE", json.get("UNKNOWN_ONE"));
		data.put("UNKNOWN_TWO", json.get("UNKNOWN_TWO"));
		data.put("UNKNOWN_THREE", json.get("UNKNOWN_THREE"));
		data.put("UNKNOWN_FOUR", json.get("UNKNOWN_FOUR"));
		data.put("UNKNOWN_FIVE", json.get("UNKNOWN_FIVE"));
		data.put("UNKNOWN_SIX", json.get("UNKNOWN_SIX"));
		data.put("UNKNOWN_SEVEN", json.get("UNKNOWN_SEVEN"));
		data.put("UNKNOWN_EIGHT", json.get("UNKNOWN_EIGHT"));

		int k = 0;
		for (Map<String, Object> refer : list) {
			refer.putAll(data);
			k = Dao.insert(xmlPath + "saveFinnaceCash", refer);
		}
		return k;
	}

	/**
	 * 财务指标 financialIndex
	 * 
	 * @date 2014-3-26 下午03:49:44
	 * @author WuYanFei
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int insertIndex(Map<String, Object> map) {
		JSONObject json = JSONObject.fromObject(map.get("ChangeViewData"));
		List<Map<String, Object>> list = json.getJSONArray("FORMDATA");
		Map<String, Object> data = new HashMap<String, Object>();

		data.put("UNIT_NAME", json.get("UNIT_NAME"));
		data.put("CHECK_PEOPLE", json.get("CHECK_PEOPLE"));
		data.put("CHECK_DATE", json.get("CHECK_DATE"));
		data.put("INDEX_CODE", json.get("INDEX_CODE"));
		data.put("BELONG_DATE", json.get("BELONG_DATE"));
		data.put("REVIEW_PEOPLE", json.get("REVIEW_PEOPLE"));
		data.put("REVIEW_DATE", json.get("REVIEW_DATE"));
		data.put("MANAGEID", map.get("MANAGEID"));
		data.put("CLIENT_ID", map.get("PEOPLE_ID"));//客户财务指标
		data.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));//行业财务指标
		data.put("CREATE_ID", map.get("CREATE_ID"));
		int k = 0;
		
		for (Map<String, Object> refer : list) {//循环便利list， 逐条插入财务指标数据
			refer.putAll(data);
			k = Dao.insert(xmlPath + "financialIndex", refer);
		}
		return k;
	}

	/**
	 * 盈利能力 profitability
	 * 
	 * @date 2014-3-24 下午08:49:07
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public List<Map<String, Object>> profitability(Map<String, Object> map) {
		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("CLIENT_ID", map.get("CUST_ID"));//客户财报
		refer.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));//行业财报
		refer.put("IN_DATE", map.get("IN_DATE"));
		refer.put("IN_DATE1", map.get("IN_DATE1"));
		refer.put("IN_DATE2", map.get("IN_DATE2"));
		return Dao.selectList(xmlPath + "profitability", refer);
	}
	
	
	/**
	 * 获取净资产收益率
	* @param @param map
	* @param @return
	* @return List<Map<String,Object>>
	* @throws
	* @date 2014-5-23 下午01:37:51 
	* @author YuanYe
	 */
	public List<Map<String, Object>> getROE(Map<String, Object> map) {
		System.out.println("===========" + map);
		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("CLIENT_ID", map.get("CUST_ID"));//客户财报测算
		refer.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));//行业财报测算
		refer.put("IN_DATE", map.get("IN_DATE"));
		refer.put("IN_DATE1", map.get("IN_DATE1"));
		refer.put("IN_DATE2", map.get("IN_DATE2"));
		return Dao.selectList(xmlPath + "getROE", refer);
	}
	

	/**
	 * 偿债能力 debtAbility
	 * 
	 * @date 2014-3-24 下午08:50:37
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public List<Map<String, Object>> debtAbility(Map<String, Object> map) {

		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("CLIENT_ID", map.get("CUST_ID"));//客户财报测算
		refer.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));//行业财报测算
		refer.put("IN_DATE", map.get("IN_DATE"));
		refer.put("IN_DATE1", map.get("IN_DATE1"));
		refer.put("IN_DATE2", map.get("IN_DATE2"));
		return Dao.selectList(xmlPath + "debtAbility", refer);
	}

	/**
	 * 经营现金流分析 jyxjFlowAnalyze
	 * 
	 * @date 2014-3-25 下午04:44:39
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public List<Map<String, Object>> jyxjFlowAnalyze(Map<String, Object> map) {

		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("CLIENT_ID", map.get("CUST_ID"));//客户财报测算
		refer.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));//行业财报测算
		refer.put("IN_DATE", map.get("IN_DATE"));
		refer.put("IN_DATE1", map.get("IN_DATE1"));
		refer.put("IN_DATE2", map.get("IN_DATE2"));
		return Dao.selectList(xmlPath + "jyxjFlowAnalyze", refer);
	}

	/**
	 * 销售收入增长率 developmentAbility
	 * 
	 * @date 2014-3-25 下午05:23:55
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> salesGrowthRate(Map<String, Object> map) {

		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();

		double sellincomeRate = 0.00;

		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("CLIENT_ID", map.get("CUST_ID"));//客户财报测算
		refer.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));//行业财报测算
		refer.put("IN_DATE", map.get("IN_DATE"));
		refer.put("IN_DATE1", map.get("IN_DATE1"));
		refer.put("IN_DATE2", map.get("IN_DATE2"));
		List<Map<String, Object>> ability = Dao.selectList(xmlPath
				+ "debtGrowthRate", refer);

		for (int i = 0; i < ability.size(); i++) {
			Map<String, Object> m = (Map<String, Object>) ability.get(i);
			if (m.get("IN_DATE_YUE") != null
					&& "12".equals(m.get("IN_DATE_YUE"))) {

				m.put("IN_DATE_", m.get("IN_DATE"));
				m.put("CLIENT_ID", map.get("CLIENT_ID"));
				Map<String, Object> data = (Map<String, Object>) Dao.selectOne(
						xmlPath + "debtGrowthRateData", m);
				if ("".equals(m.get("MAIN_INCOME")) && data != null) {
					sellincomeRate = Util.div((Double.parseDouble(m.get(
							"MAIN_INCOME").toString()) - Double
							.parseDouble(data.get("MAIN_INCOME1").toString())),
							Double.parseDouble(data.get("MAIN_INCOME1")
									.toString()), 4) * 100;
				}
			}
			m.put("sellincomeRate", sellincomeRate);
			l.add(m);
		}
		return l;
	}

	/**
	 * 总资产增长率 AssetsGrowthRate
	 * 
	 * @date 2014-3-25 下午08:53:47
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> AssetsGrowthRate(Map<String, Object> map) {
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		double assetRate = 0.00;

		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("CLIENT_ID", map.get("CUST_ID"));//客户财报测算
		refer.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));//行业财报测算
		refer.put("IN_DATE", map.get("IN_DATE"));
		refer.put("IN_DATE1", map.get("IN_DATE1"));
		refer.put("IN_DATE2", map.get("IN_DATE2"));

		List<Map<String, Object>> access = Dao.selectList(xmlPath
				+ "debtAccessQC", refer);
		for (int k = 0; k < access.size(); k++) {
			Map<String, Object> m1 = (Map<String, Object>) access.get(k);
			if (m1.get("IN_DATE_YUE") != null
					&& "12".equals(m1.get("IN_DATE_YUE"))) {
				m1.put("IN_DATE", m1.get("IN_DATE"));
				m1.put("CLIENT_ID", map.get("CUST_ID"));

				System.out.println("-----------ddd-------------" + m1);
				Map<String, Object> data = (Map<String, Object>) Dao.selectOne(
						xmlPath + "debtAccessQM", m1);
				if ("".equals(m1.get("NET_TOTAL")) && data != null) {
					assetRate = Util.div((Double.parseDouble(m1
							.get("NET_TOTAL").toString()) - Double
							.parseDouble(data.get("NET_TOTAL1").toString())),
							Double.parseDouble(data.get("NET_TOTAL1")
									.toString()), 4) * 100;
				}
			}
			m1.put("assetRate", assetRate);
			l.add(m1);
		}
		return l;
	}

	/**
	 * 运营能力 servicePower
	 * 
	 * @date 2014-3-25 下午09:49:55
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public List<Map<String, Object>> servicePower(Map<String, Object> map) {
		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("CLIENT_ID", map.get("CUST_ID"));//客户财报测算
		refer.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));//行业财报测算
		refer.put("IN_DATE", map.get("IN_DATE"));
		refer.put("IN_DATE1", map.get("IN_DATE1"));
		refer.put("IN_DATE2", map.get("IN_DATE2"));
		return Dao.selectList(xmlPath + "debtYSZKQM", refer);
	}

	/**
	 * EBITDA statisticsEBITDA
	 * 
	 * @date 2014-3-24 下午08:51:43
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public List<Map<String, Object>> statisticsEBITDA(Map<String, Object> map) {

		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("CLIENT_ID", map.get("CUST_ID"));//客户财报测算
		refer.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));//行业财报测算
		refer.put("IN_DATE", map.get("IN_DATE"));
		refer.put("IN_DATE1", map.get("IN_DATE1"));
		refer.put("IN_DATE2", map.get("IN_DATE2"));
		return Dao.selectList(xmlPath + "statisticsEBITDA", refer);
	}

	/**
	 * 查看融资能力分析 toMgAnalazy
	 * 
	 * @date 2014-3-28 下午11:22:26
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object toMgAnalazy(Map<String, Object> map) {
		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("CLIENT_ID", map.get("CLIENT_ID"));
		return Dao.selectList(xmlPath + "toMgAnalazy", refer);
	}

	/**
	 * 根据客户id查找该客户的资产负债表
	 * 
	 * @author YuanYe
	 */
	public void queryDebtByClientid(Map<String, Object> map,
			VelocityContext context) {
		Map<String, Object> mapHead = new HashMap<String, Object>();

		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("CUST_ID", map.get("CUST_ID"));//客户
		refer.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));//行业
		refer.put("MANAGEID", map.get("MANAGEID"));
		mapHead = Dao.selectOne(xmlPath + "queryDebtHead", refer);
		List<Map<String, Object>> list = Dao.selectList(xmlPath
				+ "queryDebtByClientid", refer);
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> map3 = new HashMap<String, Object>();
		if(list!=null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				if (i == 0) {
					map1 = list.get(i);
				} else if (i == 1) {
					map2 = list.get(i);
				} else if (i == 2) {
					map3 = list.get(i);
				}
			}
		}else{
			Map<String,Object> p = new HashMap<String,Object>() ;
			p.put("ID",  map.get("MANAGEID")) ;
			p.put("PEOPLE_ID", map.get("CUST_ID")) ;
			p.put("INDUSTRY_ID", map.get("INDUSTRY_ID")) ;
			Map<String,Object> object = Dao.selectOne(xmlPath + "findFinanceManageInfoById",p) ;
			map1.put("IN_DATE", object.get("IN_DATE1")) ;
			map2.put("IN_DATE", object.get("IN_DATE2")) ;
			map3.put("IN_DATE", object.get("IN_DATE3")) ;
		}
		
		context.put("mapHead", mapHead);
		context.put("map1", map1);
		context.put("map2", map2);
		context.put("map3", map3);
	}

	/**
	 * 查询客户的资产负债表是否已存在
	 * 
	 * @author YuanYe
	 * @param map
	 * @return
	 */
	public int existDebt(Map<String, Object> map) {

		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("CLIENT_ID", map.get("CLIENT_ID"));
		return Dao.selectInt(xmlPath + "countdebtByClientID", refer);
	}

	/**
	 * 删除已存在资产负债表记录
	 * 
	 * @param map
	 * @return
	 * @author YuanYe
	 */
	public int deleteDebt(Map<String, Object> map) {
		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("CLIENT_ID", map.get("CLIENT_ID"));
		refer.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));
		refer.put("MANAGEID", map.get("MANAGEID"));
		return Dao.delete(xmlPath + "deleteDebtbyClientid", refer);
	}

	/**
	 * 查询资产损益表信息
	 * 
	 * @param map
	 * @param context
	 * @author YuanYe
	 */
	public void queryProfitByID(Map<String, Object> map, VelocityContext context) {
		Map<String, Object> mapHead = new HashMap<String, Object>();

		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("CUST_ID", map.get("CUST_ID"));//客户
		refer.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));//行业
		refer.put("MANAGEID", map.get("MANAGEID"));
		mapHead = Dao.selectOne(xmlPath + "ProfitHead", refer);
		List<Map<String, Object>> list = Dao.selectList(xmlPath
				+ "queryProfitByPeopleId", refer);
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> map3 = new HashMap<String, Object>();
		if(list!=null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				if (i == 0) {
					map1 = list.get(i);
				} else if (i == 1) {
					map2 = list.get(i);
				} else if (i == 2) {
					map3 = list.get(i);
				}
			}
		}else{
			Map<String,Object> p = new HashMap<String,Object>() ;
			p.put("ID",  map.get("MANAGEID")) ;
			p.put("PEOPLE_ID", map.get("CUST_ID")) ;
			p.put("INDUSTRY_ID", map.get("INDUSTRY_ID")) ;
			Map<String,Object> object = Dao.selectOne(xmlPath + "findFinanceManageInfoById",p) ;
			map1.put("IN_DATE", object.get("IN_DATE1")) ;
			map2.put("IN_DATE", object.get("IN_DATE2")) ;
			map3.put("IN_DATE", object.get("IN_DATE3")) ;
		}
		
		context.put("mapHead", mapHead);
		context.put("map1", map1);
		context.put("map2", map2);
		context.put("map3", map3);
	}

	/**
	 * 删除资产损益表信息
	 * 
	 * @return
	 * @author YuanYe
	 */
	public int deleteProfitDistri(Map<String, Object> map) {

		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("PEOPLE_ID", map.get("PEOPLE_ID"));
		refer.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));
		refer.put("MANAGEID", map.get("MANAGEID"));
		return Dao.delete(xmlPath + "deleteProfitDistri", refer);
	}

	/**
	 * 查询损益表中是否已存在记录数 大于0怎表示存在 等于或小于表示不存在
	 * 
	 * @param map
	 * @return
	 * @author YuanYe
	 */
	public int countProfitDistri(Map<String, Object> map) {

		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("PEOPLE_ID", map.get("PEOPLE_ID"));
		return Dao.selectInt(xmlPath + "countProfitDistri", refer);

	}

	/**
	 * 查询现金流量表记录回显
	 * 
	 * @author YuanYe
	 */

	public void queryCashById(Map<String, Object> map, VelocityContext context) {

		Map<String, Object> mapHead = new HashMap<String, Object>();
		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("CUST_ID", map.get("CUST_ID"));//客户
		refer.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));//行业
		refer.put("MANAGEID", map.get("MANAGEID"));
		mapHead = Dao.selectOne(xmlPath + "queryCashHead", refer);
		List<Map<String, Object>> list = Dao.selectList(xmlPath
				+ "queryCashById", refer);
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> map3 = new HashMap<String, Object>();
		if(list!=null&& list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				if (i == 0) {
					map1 = list.get(i);
				} else if (i == 1) {
					map2 = list.get(i);
				} else if (i == 2) {
					map3 = list.get(i);
				}
			}
		}else{
			Map<String,Object> p = new HashMap<String,Object>() ;
			p.put("ID",  map.get("MANAGEID")) ;
			p.put("PEOPLE_ID", map.get("CUST_ID")) ;
			p.put("INDUSTRY_ID", map.get("INDUSTRY_ID")) ;
			Map<String,Object> object = Dao.selectOne(xmlPath + "findFinanceManageInfoById",p) ;
			map1.put("IN_DATE", object.get("IN_DATE1")) ;
			map2.put("IN_DATE", object.get("IN_DATE2")) ;
			map3.put("IN_DATE", object.get("IN_DATE3")) ;
		}
		
		context.put("mapHead", mapHead);
		context.put("map1", map1);
		context.put("map2", map2);
		context.put("map3", map3);
	}

	/**
	 * 查询现金流量表中是否已存在记录数 大于0怎表示存在 等于或小于表示不存在
	 * 
	 * @param map
	 * @return
	 * @author YuanYe
	 */
	public int countCashRecodByID(Map<String, Object> map) {

		Map<String, Object> refer = new HashMap<String, Object>();

		refer.put("PEOPLE_ID", map.get("PEOPLE_ID"));
		refer.put("MANAGEID", map.get("MANAGEID"));
		return Dao.selectInt(xmlPath + "countCashRecodByID", refer);
	}

	/**
	 *删除已存在的现金流量记录
	 * 
	 * @param map
	 * @return
	 * @author YuanYe
	 */
	public int deleteCashByID(Map<String, Object> map) {
		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("PEOPLE_ID", map.get("PEOPLE_ID"));
		refer.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));
		refer.put("MANAGEID", map.get("MANAGEID"));
		return Dao.delete(xmlPath + "deleteCashByID", refer);

	}

	/**
	 * 查询客户的财务分析指标
	 * 
	 * @param map
	 * @param context
	 * @author YuanYe
	 */
	public void queryFinanceAnalyzeById(Map<String, Object> map,
			VelocityContext context) {

		Map<String, Object> mapHead = new HashMap<String, Object>();

		Map<String, Object> refer = new HashMap<String, Object>();

		refer.put("CUST_ID", map.get("CUST_ID"));
		refer.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));
		refer.put("MANAGEID", map.get("MANAGEID"));

		mapHead = Dao.selectOne(xmlPath + "queryFinanceAnalyzeHead", refer);

		List<Map<String, Object>> list = Dao.selectList(xmlPath
				+ "queryFinanceAnalyzeById", refer);

		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> map3 = new HashMap<String, Object>();
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				map1 = list.get(i);
			} else if (i == 1) {
				map2 = list.get(i);
			} else if (i == 2) {
				map3 = list.get(i);
			}
		}
		context.put("mapHead", mapHead);
		context.put("map1", map1);
		context.put("map2", map2);
		context.put("map3", map3);

	}

	public void testCalculate(Map<String, Object> map, VelocityContext context,
			boolean isEdit) {
		context.put("param", map);
		if (isEdit) {
			Map<String, Object> mapHead = new HashMap<String, Object>();
			Map<String, Object> refer = new HashMap<String, Object>();
			refer.put("CUST_ID", map.get("CUST_ID"));
			refer.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));
			refer.put("MANAGEID", map.get("MANAGEID"));
			mapHead = Dao.selectOne(xmlPath + "queryDebtHead", refer);
			context.put("mapHead", mapHead);
		}

		// 查询测算时间
		Map<String, Object> financeManager = findFinanceManageInfoById(map);

		if (financeManager == null || financeManager.size() < 0)
			return;

		map.put("IN_DATE", financeManager.get("IN_DATE1"));
		map.put("IN_DATE1", financeManager.get("IN_DATE2"));
		map.put("IN_DATE2", financeManager.get("IN_DATE3"));

		Map<String, Map<String, Object>> temp = new HashMap<String, Map<String, Object>>();

		// 盈利能力
		List<Map<String, Object>> profitability = profitability(map);
		for (int i = 0; i < profitability.size(); i++) {
			String key = "map_" + i;
			if (temp.get(key) != null)
				temp.get(key).putAll(profitability.get(i));
			else
				temp.put(key, profitability.get(i));
		}

		// 偿债能力
		List<Map<String, Object>> debtAbility = debtAbility(map);

		for (int i = 0; i < debtAbility.size(); i++) {
			String key = "map_" + i;
			if (temp.get(key) != null)
				temp.get(key).putAll(debtAbility.get(i));
			else
				temp.put(key, debtAbility.get(i));
		}

		// EBITDA
		List<Map<String, Object>> EBITDA = statisticsEBITDA(map);

		for (int i = 0; i < EBITDA.size(); i++) {
			String key = "map_" + i;
			if (temp.get(key) != null)
				temp.get(key).putAll(EBITDA.get(i));
			else
				temp.put(key, EBITDA.get(i));
		}

		// 经营现金流分析
		List<Map<String, Object>> jyxjFlowAnalyze = jyxjFlowAnalyze(map);

		for (int i = 0; i < jyxjFlowAnalyze.size(); i++) {
			String key = "map_" + i;
			if (temp.get(key) != null)
				temp.get(key).putAll(jyxjFlowAnalyze.get(i));
			else
				temp.put(key, jyxjFlowAnalyze.get(i));
		}

		// 销售收入增长率
		List<Map<String, Object>> salesGrowthRate = salesGrowthRate(map);

		for (int i = 0; i < salesGrowthRate.size(); i++) {
			String key = "map_" + i;
			if (temp.get(key) != null)
				temp.get(key).putAll(salesGrowthRate.get(i));
			else
				temp.put(key, salesGrowthRate.get(i));
		}

		// 总资产增长率
		List<Map<String, Object>> AssetsGrowthRate = AssetsGrowthRate(map);
		for (int i = 0; i < AssetsGrowthRate.size(); i++) {
			String key = "map_" + i;
			if (temp.get(key) != null)
				temp.get(key).putAll(AssetsGrowthRate.get(i));
			else
				temp.put(key, AssetsGrowthRate.get(i));
		}

		// 运营能力
		List<Map<String, Object>> servicePower = servicePower(map);
		for (int i = 0; i < servicePower.size(); i++) {
			String key = "map_" + i;
			if (temp.get(key) != null)
				temp.get(key).putAll(servicePower.get(i));
			else
				temp.put(key, servicePower.get(i));
		}

		for (int i = 0; i < temp.size(); i++) {
			String key = "map_" + i;
			context.put(key, temp.get(key));
		}

	}

	/**
	 * 查询客户在是否已存在财务分析记录
	 * 
	 * @param map
	 * @return
	 * @author YuanYe
	 */
	public int countFinanceAnalyzeRecord(Map<String, Object> map) {
		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("PEOPLE_ID", map.get("PEOPLE_ID"));
		refer.put("MANAGEID", map.get("MANAGEID"));
		return Dao.selectInt(xmlPath + "countFinanceAnalyzeRecord", refer);
	}

	/**
	 * 删除客户已存在财务分析记录
	 * 
	 * @param map
	 * @return
	 * @author YuanYe
	 */
	public int deleteFinanceAnalyzeRecord(Map<String, Object> map) {
		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("PEOPLE_ID", map.get("PEOPLE_ID"));
		refer.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));
		refer.put("MANAGEID", map.get("MANAGEID"));
		return Dao.delete(xmlPath + "deleteFinanceAnalyzeRecord", refer);

	}

	/**
	 * 财务报表管理记录
	 * 
	 * @param map
	 * @return
	 * @return Object
	 * @throws
	 * @date 2014-5-5 上午11:27:52
	 * @author YuanYe
	 */
	public Object manageFinancial(Map<String, Object> map) {
		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("PEOPLE_ID", map.get("CUST_ID"));
		return Dao.selectList(xmlPath + "manageFinancial", refer);
	}

	/**
	 * 获取财报管理表序列
	 * 
	 * @param
	 * @return String
	 * @throws
	 * @date 2014-5-6 下午03:34:16
	 * @author YuanYe
	 */
	public String getFinancialSeq() {
		return Dao.getSequence("SEQ_FIL_CREDIT_FINANCE_MANAGE");
	}

	/**
	 *插入财务报表管理表记录
	 * 
	 * @param map
	 * @return int
	 * @throws
	 * @date 2014-5-6 下午03:34:51
	 * @author YuanYe
	 */
	public int insertFinanceManage(Map<String, Object> map) {
		Map<String, Object> refer = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(map.get("ChangeViewData"));
		List<Map<String, Object>> list = json.getJSONArray("FORMDATA");
		for (int i = 0; i < list.size(); i++) {
			String key = "IN_DATE" + (i + 1);
			refer.put(key, list.get(i).get("IN_DATE"));
		}
		refer.put("ID", map.get("MANAGEID"));
		refer.put("PEOPLE_ID", map.get("PEOPLE_ID"));//客户
		refer.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));//行业
		refer.put("CREATE_NAME", Security.getUser().getName());
		refer.put("CREATE_CODE", Security.getUser().getCode());
		refer.put("OPERATOR_ID", Security.getUser().getId());// 登陆用户编号
		refer.put("OPERATOR_NAME", Security.getUser().getName());// 登陆用户姓名
		refer.put("OPERATOR_CODE", Security.getUser().getCode());// 登陆用户工号
		return Dao.insert(xmlPath + "insertFinanceManage", refer);
	}

	/**
	 * 查看序列是否已在表中
	 * 
	 * @param map
	 * @return
	 * @return int
	 * @throws
	 * @date 2014-5-6 下午03:35:40
	 * @author YuanYe
	 */
	public int findFinanceManageById(Map<String, Object> map) {
		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("ID", map.get("MANAGEID"));
		refer.put("PEOPLE_ID", map.get("PEOPLE_ID"));//客户财报信息
		refer.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));//行业财报信息
		return Dao.selectInt(xmlPath + "findFinanceManageById", refer);
	}

	public int deleteFinancilManage(Map<String, Object> map) {
		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("ID", map.get("MANAGEID"));
		refer.put("PEOPLE_ID", map.get("PEOPLE_ID"));
		return Dao.delete(xmlPath + "deleteFinancilManage", refer);
	}

	public int updateFinanceManageById(Map<String, Object> map) {
		Map<String, Object> refer = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(map.get("ChangeViewData"));
		List<Map<String, Object>> list = json.getJSONArray("FORMDATA");
		refer.put("ID", map.get("MANAGEID"));
		refer.put("PEOPLE_ID", map.get("PEOPLE_ID"));
		refer.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));
		for (int i = 0; i < list.size(); i++) {
			String key = "IN_DATE" + (i + 1);
			refer.put(key, list.get(i).get("IN_DATE"));
		}
		return Dao.update(xmlPath + "updateFinanceManageById", refer);
	}

	public Map<String, Object> findFinanceManageInfoById(Map<String, Object> map) {
		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("ID", map.get("MANAGEID"));
		refer.put("PEOPLE_ID", map.get("CUST_ID"));//关联客户财报
		refer.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));//与行业财报进行关联
		return Dao.selectOne(xmlPath + "findFinanceManageInfoById", refer);
	}

	public int selectCountForDebtByManagerId(Map<String, Object> map) {
		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("MANAGEID", map.get("MANAGEID"));
		return Dao.selectInt(xmlPath + "selectCountForDebtByManagerId", refer);
	}

	public int selectCountForCashByManagerId(Map<String, Object> map) {
		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("MANAGEID", map.get("MANAGEID"));
		return Dao.selectInt(xmlPath + "selectCountForCashByManagerId", refer);
	}

	public int selectCountForProfitByManagerId(Map<String, Object> map) {
		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("MANAGEID", map.get("MANAGEID"));
		return Dao.selectInt(xmlPath + "selectCountForProfitByManagerId", refer);
	}

	public int updateDebtInDateByManagerId(Map<String, Object> map) {
		JSONObject json = JSONObject.fromObject(map.get("ChangeViewData"));
		List<Map<String, Object>> list = json.getJSONArray("FORMDATA");

		int k = 0;
		int i = 1;
		for (Map<String, Object> refer : list) {
			refer.put("MANAGEID", map.get("MANAGEID"));
			refer.put("ROWNUM", i);
			k = Dao.update(xmlPath + "updateDebtInDateByManagerId", refer);
			i++;
		}

		return k;
	}

	public int updateCashInDateByManagerId(Map<String, Object> map) {

		JSONObject json = JSONObject.fromObject(map.get("ChangeViewData"));
		List<Map<String, Object>> list = json.getJSONArray("FORMDATA");

		int k = 0;
		int i = 1;
		for (Map<String, Object> refer : list) {
			refer.put("MANAGEID", map.get("MANAGEID"));
			refer.put("ROWNUM", i);
			k = Dao.update(xmlPath + "updateCashInDateByManagerId", refer);
			i++;
		}
		return k;
	}

	public int updateProfitInDateByManagerId(Map<String, Object> map) {

		JSONObject json = JSONObject.fromObject(map.get("ChangeViewData"));
		List<Map<String, Object>> list = json.getJSONArray("FORMDATA");

		int k = 0;
		int i = 1;
		for (Map<String, Object> refer : list) {
			refer.put("MANAGEID", map.get("MANAGEID"));
			refer.put("ROWNUM", i);
			k = Dao.update(xmlPath + "updateProfitInDateByManagerId", refer);
			i++;
		}
		return k;
	}

}
