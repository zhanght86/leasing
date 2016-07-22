package com.pqsoft.irrReport.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.PaymentIRRUtil;
import com.pqsoft.util.Util;

public class RealIRRService {
	/******* 实际收益率管理****@auth: king 2014年9月1日 *************************/
	private String NAMESPACE = "realIRR.";

	/**
	 * 查询收益率列表数据
	 * 
	 * @param map
	 * @return
	 * @author King 2014年9月2日
	 */
	public Page doShowRealIRRMg(Map<String, Object> map) {
		return PageUtil.getPage(map, NAMESPACE + "queryIRRList", NAMESPACE
				+ "queryIRRListCount");
	}

	/**
	 * 查询横坐标时间
	 * 
	 * @param map
	 * @return
	 * @author King 2014年9月2日
	 */
	public List<String> queryIRRXValue(Map<String, Object> map) {
		return Dao.selectList(NAMESPACE + "queryIRRXValue", map);
	}

	/**
	 * 查询图标数据
	 * 
	 * @param map
	 * @return
	 * @author King 2014年9月2日
	 */
	public List<Map<String, Object>> queryIRRDATA_TYPE(Map<String, Object> map) {
		return Dao.selectList(NAMESPACE + "queryIRRDATA_TYPE", map);
	}

	/**
	 * 查询折线图纵轴数据
	 * 
	 * @param map
	 * @return
	 * @author King 2014年9月2日
	 */
	public List<String> queryIRRDATA_VALUE(Map<String, Object> map) {
		return Dao.selectList(NAMESPACE + "queryIRRDATA_VALUE", map);
	}

	/**
	 * 组装图标数据
	 * 
	 * @param map
	 * @return
	 * @author King 2014年9月2日
	 */
	public List<Map<String, Object>> queryIRRDataList(Map<String, Object> map) {
		List<Map<String, Object>> list = this.queryIRRDATA_TYPE(map);
		for (Map<String, Object> map2 : list) {
			map.put("DATA_TYPE", map2.get("DATA_TYPE"));
			map2.put("DATA_VALUE", this.queryIRRDATA_VALUE(map));
		}
		return list;
	}
	
	/**
	 * 刷收益率数据
	 * @param NOW_DAY
	 * @author King 2014年9月2日
	 */
	public void refreshIRRForProject(String NOW_DAY){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("NOW_DAY", NOW_DAY);
		List<Map<String,Object>> projectList=Dao.selectList(NAMESPACE+"queryProjectInfo", map);
		for (Map<String, Object> map2 : projectList) {
			try{
			List<Map<String,Object>> rstList=Dao.selectList(NAMESPACE+"queryIRRCashFlow", map2);
			double []cashFlows= new double [rstList.size()];
			Date []date=new Date[rstList.size()];
			for (int i = 0; i < rstList.size(); i++) {
				cashFlows[i]=Double.parseDouble(rstList.get(i).get("MONEY")+"");
				date[i]=(Date) rstList.get(i).get("PAY_DATE");
			}
			double real_irr=Util.formatDouble6(PaymentIRRUtil.getIRR(cashFlows, date, Double.NaN,  Integer.parseInt(map2.get("LEASE_PERIOD")+"")));
			real_irr*=100;
			map2.put("REAL_IRR", real_irr);
			Dao.insert(NAMESPACE+"addProjectIRR", map2);
			}catch(Exception e){
				System.out.println("测算失败"+e.getMessage());
			}
		}
	}
	
	/**
	 * 查询成本核算合计
	 * @param map
	 * @return
	 * @author King 2014年11月3日
	 */
	public List<Map<String,Object>> querycbgsSum(Map<String,Object> map){
		return Dao.selectList(NAMESPACE+"querycbgsSum", map);
	}
	
	/**
	 * 查询成本核算现金流明细
	 * @param map
	 * @return
	 * @author King 2014年11月3日
	 */
	public List<Map<String,Object>> querycbgsDetail(Map<String,Object> map){
		return Dao.selectList(NAMESPACE+"querycbgsDetail", map);
	}
	
	/**
	 * 查询数据字典配置时间类型
	 * @return
	 * @author King 2014年11月21日
	 */
	public List<Map<String,Object>> queryDateList(){
		return Dao.selectList(NAMESPACE+"queryDateList");
	}
	
	/**
	 * 查询数据
	 * @param map
	 * @return
	 * @author King 2014年11月21日
	 */
	public Page queryIRRPage(Map<String,Object> map){
		return PageUtil.getPage(map, NAMESPACE+"showIRRList", NAMESPACE+"showIRRList_count");
	}
}
