package com.pqsoft.analysisBySynthesis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.customers.service.FinancialStatisticsService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class IndustryService {
	
	/**
	 * 行业简介管理
	 * toMgIndustry
	 * @date 2014-1-13 下午09:38:40
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Page toMgIndustry(Map<String,Object> m){
		return PageUtil.getPage(m, "industry.toMgIndustry","industry.toMgIndustryCount");
	}
	
	/**
	 * 查看行业简介
	 * toShowIndustry
	 * @date 2014-1-13 下午09:37:54
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object toShowIndustry(Map<String,Object> map){
		//System.out.println("查看行业简介 所传参数：：："+map);
		return Dao.selectOne("industry.toShowIndustry", map);
	}
	
	/**
	 * 行业主要数据
	 * toShowIndustryChild
	 * @date 2014-1-13 下午09:39:45
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object toShowIndustryChild(Map<String,Object> map){
		return Dao.selectList("industry.toShowIndustryChild", map);
	}
	
	/**
	 * 行业分析
	 * toShowIndustryANALYZE
	 * @date 2014-3-19 上午11:35:51
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object toShowIndustryANALYZE(Map<String,Object> map){
		return Dao.selectList("industry.toShowIndustryANALYZE", map);
	}
	
	/**
	 * 添加行业简介
	 * doInertIndustry
	 * @date 2014-1-13 下午09:41:03
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doInertIndustry(Map<String,Object> map){ 
		return Dao.insert("industry.doInertIndustry", map);
	}
	
	/**
	 * 添加行业主要数据
	 * doInertIndustryChild
	 * @date 2014-1-13 下午09:42:42
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doInertIndustryChild(JSONObject m){
		List<Map<String, Object>> dataList = m.getJSONArray("CHILD_DATA");
		String  hy_id=m.get("HY_ID").toString();
		String  USER_ID=m.get("USER_ID").toString();
		int i = 0;
		
		System.out.println("=========="+dataList.size());
		
		for(Map<String, Object> map:dataList){
			String CHILD_ID = Dao.getSequence("SEQ_FIL_INDUSTRY_CHILD");
			map.put("CHILD_ID", CHILD_ID);
	    	map.put("INDUSTRY_ID", hy_id);
	    	map.put("USERID", USER_ID);
	    	i=Dao.insert("industry.doInertIndustryChild", map);
   	    }
		return i;
	}
	
	/**
	 * 添加行业分析
	 * doInertIndustryChild
	 * @date 2014-3-19 上午11:09:29
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doInertIndustryANALYZE(JSONObject m){
		List<Map<String, Object>> dataList = m.getJSONArray("ANALYZE");
		String  hy_id=m.get("HY_ID").toString();
		String  USER_ID=m.get("USER_ID").toString();
		int i = 0;
		for(Map<String, Object> map:dataList){
			String ANALYZE_ID = Dao.getSequence("SEQ_INDUSTRY_ANALYZE");
			map.put("ANALYZE_ID", ANALYZE_ID);
	    	map.put("INDUSTRY_ID", hy_id);
	    	map.put("USERID", USER_ID);
	    	i = Dao.insert("industry.doInertIndustryANALYZE", map);
   	    }
		return i;
	}
	
	/**
	 * 修改行业简介
	 * doUpdateINDUSTRY
	 * @date 2014-1-13 下午09:51:28
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doUpdateINDUSTRY(Map<String,Object> map){
		return Dao.update("industry.doUpdateINDUSTRY", map);
	}
	
	/**
	 * 修改行业主要数据
	 * doUpdateINDUSTRYChild
	 * @date 2014-1-13 下午09:52:12
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doUpdateINDUSTRYChild(JSONObject m){
		List<Map<String, Object>> dataList = m.getJSONArray("CHILD_DATA");
		String  hy_id=m.get("HY_ID").toString();
		String  USER_ID=m.get("USER_ID").toString();
		for(Map<String, Object> map:dataList){
			if(null==map.get("CHILD_ID") || "".equals(map.get("CHILD_ID"))){
				String CHILD_ID = Dao.getSequence("SEQ_FIL_INDUSTRY_CHILD");
				map.put("CHILD_ID", CHILD_ID);
		    	map.put("INDUSTRY_ID", hy_id);
		    	map.put("USERID", USER_ID);
		    	Dao.insert("industry.doInertIndustryChild", map);
	   	    }else{
		    	map.put("HY_ID", hy_id);
		    	map.put("USER_ID", USER_ID);
		    	Dao.update("industry.doUpdateINDUSTRYChild", map);
	   	    }
   	    }
		return 1;
	}
	
	/**
	 * 修改行业分析
	 * doUpdateANALYZE
	 * @date 2014-3-19 上午11:14:23
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int doUpdateANALYZE(JSONObject m){
		List<Map<String, Object>> dataList = m.getJSONArray("ANALYZE");
		String  hy_id=m.get("HY_ID").toString();
		String  USER_ID=m.get("USER_ID").toString();
		int i = 0;
		for(Map<String, Object> map:dataList){
			if(null==map.get("ANALYZE_ID") || "".equals(map.get("ANALYZE_ID"))){
				String ANALYZE_ID = Dao.getSequence("SEQ_FIL_INDUSTRY_CHILD");
				map.put("ANALYZE_ID", ANALYZE_ID);
		    	map.put("INDUSTRY_ID", hy_id);
		    	map.put("USERID", USER_ID);
		    	i = Dao.insert("industry.doInertIndustryANALYZE", map);
	   	    }else{
		    	map.put("INDUSTRY_ID", hy_id);
		    	map.put("USERID", USER_ID);
		    	i = Dao.update("industry.doUpdateANALYZE", map);
	   	    }
   	    }
		return i;
	}
	
	/**
	 * 删除
	 * delINDUSTRY
	 * @date 2014-1-14 上午09:23:23
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int delINDUSTRY(Map<String,Object> map){
		Dao.delete("industry.delINDUSTRYANALYZE", map);
		Dao.delete("industry.delINDUSTRYChild", map);
		return Dao.delete("industry.delINDUSTRY", map);
	}
	
	/**
	 * 公用方法， 用于功能中调用行业类型中的选择项目
	 * @author yx
	 * @date 2015-03-04
	 * @return
	 */
	public Object toFindIndustryMemcache(){
		return Dao.selectList("industry.toFindIndustryMemcache");
	}
	
	
	/********************************************************************************************************************
	 ********************************************************************************************************************  
	 ***************************以下为财务报表的相关内容  @author 杨雪   @date 2015-02-27*************************************
	 ********************************************************************************************************************
	 *******************************************************************************************************************/
	public Object toMgFinancialReport(Map<String,Object> map){
		return Dao.selectList("industry.manageFinancial", map);
	}
	
	///财务报表测算
	public void testCalculate(Map<String, Object> map, VelocityContext context,
			boolean isEdit) {
		FinancialStatisticsService service = new FinancialStatisticsService();
		context.put("param", map);
		if (isEdit) {
			Map<String, Object> mapHead = new HashMap<String, Object>();
			Map<String, Object> refer = new HashMap<String, Object>();
			refer.put("INDUSTRY_ID", map.get("INDUSTRY_ID"));
			refer.put("MANAGEID", map.get("MANAGEID"));
			mapHead = Dao.selectOne("industry.queryDebtHead", refer);
			context.put("mapHead", mapHead);
		}

		// 查询测算时间
		Map<String, Object> financeManager = service.findFinanceManageInfoById(map);
		if (financeManager == null || financeManager.size() < 0)
			return;

		map.put("IN_DATE", financeManager.get("IN_DATE1"));
		map.put("IN_DATE1", financeManager.get("IN_DATE2"));
		map.put("IN_DATE2", financeManager.get("IN_DATE3"));

		Map<String, Map<String, Object>> temp = new HashMap<String, Map<String, Object>>();

		// 盈利能力
		List<Map<String, Object>> profitability = service.profitability(map);
		for (int i = 0; i < profitability.size(); i++) {
			String key = "map_" + i;
			if (temp.get(key) != null)
				temp.get(key).putAll(profitability.get(i));
			else
				temp.put(key, profitability.get(i));
		}

		// 偿债能力
		List<Map<String, Object>> debtAbility =  service.debtAbility(map);

		for (int i = 0; i < debtAbility.size(); i++) {
			String key = "map_" + i;
			if (temp.get(key) != null)
				temp.get(key).putAll(debtAbility.get(i));
			else
				temp.put(key, debtAbility.get(i));
		}

		// EBITDA
		List<Map<String, Object>> EBITDA =  service.statisticsEBITDA(map);

		for (int i = 0; i < EBITDA.size(); i++) {
			String key = "map_" + i;
			if (temp.get(key) != null)
				temp.get(key).putAll(EBITDA.get(i));
			else
				temp.put(key, EBITDA.get(i));
		}

		// 经营现金流分析
		List<Map<String, Object>> jyxjFlowAnalyze = service.jyxjFlowAnalyze(map);

		for (int i = 0; i < jyxjFlowAnalyze.size(); i++) {
			String key = "map_" + i;
			if (temp.get(key) != null)
				temp.get(key).putAll(jyxjFlowAnalyze.get(i));
			else
				temp.put(key, jyxjFlowAnalyze.get(i));
		}

		// 销售收入增长率
		List<Map<String, Object>> salesGrowthRate = service.salesGrowthRate(map);

		for (int i = 0; i < salesGrowthRate.size(); i++) {
			String key = "map_" + i;
			if (temp.get(key) != null)
				temp.get(key).putAll(salesGrowthRate.get(i));
			else
				temp.put(key, salesGrowthRate.get(i));
		}

		// 总资产增长率
		List<Map<String, Object>> AssetsGrowthRate = service.AssetsGrowthRate(map);
		for (int i = 0; i < AssetsGrowthRate.size(); i++) {
			String key = "map_" + i;
			if (temp.get(key) != null)
				temp.get(key).putAll(AssetsGrowthRate.get(i));
			else
				temp.put(key, AssetsGrowthRate.get(i));
		}

		// 运营能力
		List<Map<String, Object>> servicePower = service.servicePower(map);
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
}
