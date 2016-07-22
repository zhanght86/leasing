package com.pqsoft.bpm.status;

import java.util.HashMap;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.rbac.api.Security;

public class Cust_Bzj_THStatus {
	private String namespace = "bpm.bzj_qmcd.";
	/*
	 * 客户保证金期末退回通过
	 */
	public void cust_Bzj_TH_Pass(int ID){
		double bzj_sy_money =  Dao.selectDouble(namespace+"get_Bzj_sy_moneyById", ID);
		double refund_money = Dao.selectDouble(namespace+"get_refund_moneyById", ID);
		Map<String,Object> map = Dao.selectOne(namespace+"selectStatusAndId", ID);
		String FUND_ID_BZJ=Dao.selectOne("fi.fund.getFundId");
		String FUND_CODE_BZJ=Dao.selectOne("fi.fund.getFundCode");
		map.put("BEGIN_BZJ", bzj_sy_money);
		map.put("SY_BZJ", bzj_sy_money-refund_money);
		map.put("REFUND_MONEY", refund_money);
		map.put("FUND_ID", FUND_ID_BZJ);
		map.put("FUND_CODE", FUND_CODE_BZJ);
		map.put("REMARK", "期末退回，使用金额"+refund_money);
		map.put("OPERATOR_MAN", Security.getUser().getName());
		map.put("TYPE", "期末退回");
		//添加保证金使用记录明细
		Dao.insert(namespace+"insertFi_Bzj_Record", map);
		//修改fil_rent_plan_head表剩余保证金和还款计划表状态
		Dao.update(namespace+"update_Status_Deposit_sy", map);
	}
	/*
	 * 客户保证金期末退回不通过
	 */
	public void cust_Bzj_TH_NoPass(int ID){
		Map<String,Object> map = new HashMap<String, Object>();
		System.out.println(map+"------------");
		map.put("ID", ID);
		Map<String,Object> map_tem = Dao.selectOne(namespace+"selectStatusAndId", map);
		Dao.update(namespace+"updateFRPH_Status_ById", map_tem);
	}
}
