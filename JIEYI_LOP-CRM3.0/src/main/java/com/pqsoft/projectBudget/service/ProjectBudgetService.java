package com.pqsoft.projectBudget.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.RX_PaymentInternalReturnCalculate;
import com.pqsoft.util.RX_PaymentInternalReturnLine;
import com.pqsoft.util.StringUtils;

public class ProjectBudgetService {
	/***********@auth: king 2014年8月14日 项目测算跟进服务端*************************/
	
	private final String NAMESPACE="projectBudget.";
	
	/**
	 * 查询项目测算列表数据
	 * @param param
	 * @return
	 * @author King 2014年8月14日
	 */
	public Page doMgProjectBudget(Map<String,Object> param){
		return PageUtil.getPage(param, NAMESPACE+"queryBudgetProjectList", NAMESPACE+"queryBudgetProjectListCount");
	}
	
	/**
	 * 查询项目测算数据
	 * @param PROJECT_ID 项目ID
	 * @param PROJECT_SCHEME_ID 项目方案ID
	 * @return
	 * @author King 2014年8月18日
	 */
	public Map<String,Object> queryShowEstimates(Object PROJECT_ID,Object PROJECT_SCHEME_ID){
		if(StringUtils.isNotBlank(PROJECT_ID)&&StringUtils.isNotBlank(PROJECT_SCHEME_ID)){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("PROJECT_ID", PROJECT_ID);
			map.put("PROJECT_SCHEME_ID", PROJECT_SCHEME_ID);
			Map<String,Object> mm=Dao.selectOne(NAMESPACE+"queryShowEstimates", map);
			Dao.getClobTypeInfo2(mm, "SCHEME_CLOB");
			map.putAll(mm);
			return map;
		}else
			return new HashMap<String,Object>();
	}
	
	public List<RX_PaymentInternalReturnLine> RX_RentEstimates(Map<String,Object> m)throws Exception{
		List<RX_PaymentInternalReturnLine> list = new ArrayList<RX_PaymentInternalReturnLine>();
		try {
				RX_PaymentInternalReturnCalculate payTable = new RX_PaymentInternalReturnCalculate();
				payTable.setTopricSubfirent_bdw(Double.valueOf(m.get("LEASE_TOPRIC").toString()));
				payTable.setLeaseTerm(Integer.valueOf(m.get("LEASE_TERM").toString()));
				payTable.setLeasePeriod(Integer.valueOf(m.get("LEASE_PERIOD").toString()));
				payTable.setSfje(Double.valueOf(m.get("START_MONEY").toString()));//首付金额
				payTable.setSfjebl(Double.valueOf(m.get("START_PERCENT").toString()));//首付比例
				payTable.setRzje(Double.valueOf(m.get("FINANCE_TOPRIC").toString()));
				payTable.setRzjebl(Double.valueOf(m.get("RZJEBL").toString()));//融资比例
				payTable.setBzjje(Double.valueOf(m.get("DEPOSIT_MONEY").toString()));
				payTable.setBzjbl(Double.valueOf(m.get("DEPOSIT_PERCENT").toString()));
				if(m.containsKey("GLF_PRICE"))
					payTable.setGlfje(Double.valueOf(m.get("GLF_PRICE").toString()));
				if(m.containsKey("GLF_PERCENT"))
					payTable.setGlfbl(Double.valueOf(m.get("GLF_PERCENT").toString()));//管理费比例
				payTable.setDlsbzj(Double.valueOf(m.get("DB_BAIL_MONEY").toString()));
				payTable.setDlsbzjbl(Double.valueOf(m.get("DB_BAIL_PERCENT").toString()));
				payTable.setSfzjzb(Double.valueOf(m.get("SFZJZB").toString()));//首付资金占比
//				payTable.setBzjlx(Double.valueOf(m.get("BZJLX").toString()));//保证金利息
				payTable.setZlnbcl(Double.valueOf(m.get("YEAR_RATE").toString()));
//				payTable.setZlxs(Double.valueOf(m.get("ZLXS").toString()));//租赁系数
				payTable.setBxf(Double.valueOf(m.get("INSURANCE_MONEY").toString()));
//				payTable.setBxffl(Double.valueOf(m.get("INSURANCE_PERCENT").toString()));
				payTable.setKpzb(Double.valueOf(m.get("KPZB").toString()));
				payTable.setXjzfzb(Double.valueOf(m.get("XJZFZB").toString()));
				payTable.setKpbzjbl(Double.valueOf(m.get("KPBZJBL").toString()));
				payTable.setDkzb(Double.valueOf(m.get("DKZB").toString()));
				payTable.setKpsxfl(Double.valueOf(m.get("KPSXFL").toString()));
				payTable.setDkll(Double.valueOf(m.get("DKLL").toString()));
				payTable.setCkfl(Double.valueOf(m.get("CKFL").toString()));
				payTable.setDkys(Double.valueOf(m.get("DKYS").toString()));
				payTable.setBzjll(Double.valueOf(m.get("BZJLL").toString()));
				payTable.setPjtxll(Double.valueOf(m.get("PJTXLL").toString()));
				payTable.setYyssj(Double.valueOf(m.get("PJTXLL").toString()));
				payTable.setYyssl(Double.valueOf(m.get("PJTXLL").toString()));
				payTable.setYhsl(Double.valueOf(m.get("YYSJFJSL").toString()));
				payTable.setYhsj(Double.valueOf(m.get("YYSJFJSJ").toString()));
				payTable.setFhhtsl(Double.valueOf(m.get("FHHDFSL").toString()));
				payTable.setFhhtsj(Double.valueOf(m.get("FHHDFSJ").toString()));
				payTable.setPayWay(m.get("PAY_WAY").toString());
				payTable.setCzhgj(Double.valueOf(m.get("CZHGJ").toString()));//残值回购价
				payTable.setKpqk(Integer.valueOf(m.get("KPQK").toString()));
				list = payTable.print(payTable.ExecuteCommand());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
