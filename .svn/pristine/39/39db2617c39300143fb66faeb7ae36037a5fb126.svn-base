package com.pqsoft.rePayment.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class RePaymentService {

	private final String xmlPath = "rePayment.";
	
	/**
	 * 融资回款管理
	 * toMgRePayment
	 * @date 2013-10-23 下午12:16:14
	 * @author 杨雪
	 * @return 返回值类型
	 * @
	 */
	public Page toMgRePayment(Map<String,Object> map){
		Page page = new Page();
		map.put("type", "再融资放款类型");
		List<Map<String,Object>> list = Dao.selectList(xmlPath+"toMgPayment", map);
		JSONArray array = new JSONArray();
		for(int i=0; i<list.size(); i++){
			Map<String,Object> m = list.get(i);
			JSONObject obj = new JSONObject();
			obj.put("ID", m.get("ID"));
			obj.put("PROJECT_NAME", m.get("PROJECT_NAME"));
			obj.put("PROJECT_CODE", m.get("PROJECT_CODE"));
			obj.put("PROJECT_STATUS", m.get("PROJECT_STATUS").toString()==null?"":m.get("PROJECT_STATUS").toString());			
			obj.put("EXECUTION_STATE", m.get("EXECUTION_STATE"));
			obj.put("REMARK", m.get("REMARK"));
			obj.put("CREATE_CODE", m.get("CREATE_CODE"));
			obj.put("CREATE_TIME", m.get("CREATE_TIME"));
			obj.put("PAY_TOTAL", m.get("PAY_TOTAL"));
			obj.put("BANK_TOTAL", m.get("BANK_TOTAL"));			
			obj.put("BAILOUTWAY_ID", m.get("BAILOUTWAY_ID"));			
			obj.put("BID", m.get("BID"));
			obj.put("BAILOUTWAY_NAME", m.get("BAILOUTWAY_NAME"));
			obj.put("ORGAN_NAME", m.get("ORGAN_NAME"));
			obj.put("CREDIT_END_DEADLINE", m.get("CREDIT_END_DEADLINE"));
			obj.put("REPAYMENT_MAN", m.get("REPAYMENT_MAN"));
			obj.put("BAIL_DEPOSITOR", m.get("BAIL_DEPOSITOR"));
			obj.put("LOAN_AMOUNT", m.get("LOAN_AMOUNT"));
			obj.put("DEDUCT_ACCOUNT", m.get("DEDUCT_ACCOUNT"));
			obj.put("LOAN_TIME", m.get("LOAN_TIME"));	
			obj.put("STATUS", m.get("STATUS"));
			obj.put("PAY_ID", m.get("PAY_ID"));
			obj.put("INTEREST", m.get("INTEREST"));
			obj.put("TERM", m.get("TERM"));
			obj.put("PERIOD", m.get("PERIOD"));
			obj.put("PAY_TYPE", m.get("PAY_TYPE"));
			obj.put("PAY_DATE", m.get("PAY_DATE"));
			obj.put("operate",  m.get("STATUS"));
			array.add(obj);
		}
		page.addDate(array, Dao.selectInt(xmlPath+"toMgPaymentCount", map));
		return page;
	}
	
	/**
	 * 方法说明：根据项目ID获取该项目下的方案信息
	 * queryT_REFUND_PAYMENTPLAN
	 * @date 2013-10-23 下午07:22:08
	 * @author 杨雪
	 * @return 返回值类型
	 * @
	 */
	public Object queryT_REFUND_PAYMENTPLAN(Map<String,Object> map) {
		return Dao.selectOne(xmlPath+"queryPAYMENTPLAN",map);		
	}
	
	/**
	 * 方法说明：根据融资方式ID获取该融资方式下的方案信息
	 * queryT_REFUND_BAILOUTWAY
	 * @date 2013-10-23 下午07:22:46
	 * @author 杨雪
	 * @return 返回值类型
	 * @
	 */
	public Object queryT_REFUND_BAILOUTWAY(Map<String,Object> map) {
		return Dao.selectOne(xmlPath+"queryT_REFUND_BAILOUTWAY",map);		
	}
	
	/**
	 * 根据各种比例计算对应的值
	 * getSchemeByProportion
	 * @date 2013-10-23 下午07:23:07
	 * @author 杨雪
	 * @return 返回值类型
	 * @
	 */
	public Map<String,Object> getSchemeByProportion(Map<String,Object> map,String loan){
		double loan_amount = Double.parseDouble(loan);//放款金额
		double BAILOUTWAY1 = loan_amount*Double.parseDouble(map.get("BAILOUTWAY_NSFR").toString())/100;//根据融资比例计算融资额
		double MARGIN1 = loan_amount*Double.parseDouble(map.get("MARGIN_SCALE").toString())/100;//根据保证金比例计算保证金
		double PREMIUM1 = loan_amount*Double.parseDouble(map.get("PREMIUM_SCALE").toString())/100;//根据保险费比例计算保险费
		double POUNDAGE1 = loan_amount*Double.parseDouble(map.get("POUNDAGE_SCALE").toString())/100;//根据手续费比例计算手续费
		double REST1 = loan_amount*Double.parseDouble(map.get("REST_SCALE").toString())/100;//根据其他费用比例计算融其他费用
		double MANAGER_COST1 = loan_amount*Double.parseDouble(map.get("REST_SCALE").toString())/100;//根据管理费比例计算管理费
		map.put("BAILOUTWAY1", BAILOUTWAY1);
		map.put("MARGIN1", MARGIN1);
		map.put("PREMIUM1", PREMIUM1);
		map.put("POUNDAGE1", POUNDAGE1);
		map.put("REST1", REST1);
		map.put("MANAGER_COST1", MANAGER_COST1);
		return map;
	}
	
	/**************************************************************************************************************************************
	 *                                                                                                                                    *
	 *                                                                                                                                    *
	 *                                                 以下为融资方案录入与确认方法                                                                                                                                                       *
	 *                                                                                                                                    *
	 *                                                                                                                                    *
	 *************************************************************************************************************************************/
	
	/**
	 * 获取全部基准利率
	 */
	public Map<String,Object> getInterest(){
		return (Map<String,Object> )Dao.selectOne(xmlPath+"getInterest");		
	}
	
	/**
	 * 保存方案信息
	 */
	public int savePaymentPlan(Map<String,Object> map){
		map.put("PAY_ID",Dao.getSequence("SEQ_RE_PAYMENT_HEAD"));
		return Dao.insert(xmlPath+"savePaymentPlan", map);
	}
	
	/**
	 * 修改方案信息
	 */
	public int updatePaymentPlan(Map<String,Object> map){
		return Dao.update(xmlPath+"updatePaymentPlan", map);
	}
	
	
	/**
	 * 根据周期和期次获取利率
	 */
	public String getInterestByPeriod(Map<String,Object> map,Map<String,Object> interestMap){
		String interest = "";
		int period = 0;
		if(map.get("PERIOD").equals("2")){
			period = 4;
		}
		else if(map.get("PERIOD").equals("3")){
			period = 12;
		}
		else{
			period = 1;
		}
		int term = Integer.parseInt(map.get("TERM").toString());//获取期数
		int months = period * term;
		if(months<=6){
			interest = interestMap.get("SIX_MONTHS").toString();
		}
		else if(months<=12){
			interest = interestMap.get("ONE_YEAR").toString();
		}
		else if(months<=36){
			interest = interestMap.get("ONE_THREE_YEARS").toString();
		}
		else if(months<=60){
			interest = interestMap.get("THREE_FIVE_YEARS").toString();
		}
		else{
			interest = interestMap.get("OVER_FIVE_YEARS").toString();
		}
		return interest;		
	}
	
	
	
	/**************************************************************************************************************************************
	 *                                                                                                                                    *
	 *                                                                                                                                    *
	 *                                                 以下为系统生成支付表方法                                                                                                                                                                 *
	 *                                                                                                                                    *
	 *                                                                                                                                    *
	 *************************************************************************************************************************************/
	
	/**
	 * 
	 * @memo  方法说明：根据项目ID获取该项目下的放款信息
	 *
	 */
	public Object queryT_REFUND_LOAN(Map<String,Object> map){
		return Dao.selectOne(xmlPath+"queryLOAN",map);		
	}
	
	/**
	 * 
	 * @memo  方法说明：根据项目ID获取对应的还款计划书
	 *
	 */
	public Object getPaymentDetailByPid(Map<String,Object> map){
		return Dao.selectList(xmlPath+"getPaymentDetailByPid",map);		
	}
	
	/**
	 * 修改方状态
	 */
	public int updatePaymentPlanStatus(Map<String,Object> map){
		return Dao.update(xmlPath+"updatePaymentPlanStatus", map);
	}
	
}
