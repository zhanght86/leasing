/**
 *
*/
package com.pqsoft.rePayment.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.holiday.service.HolidayMainService;
import com.pqsoft.skyeye.api.Dao;


public class RePaymentDetailService {
	
	private String namespace="paymentDetail.";
	
	/**
	 * 保存还款计划书
	 * addPayMentDetailService
	 * @date 2013-10-24 下午12:16:25
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int addPayMentDetailService(JSONArray array,JSONObject jsonMainPayment){
		//主要用来判断是否保存成功
		HolidayMainService ha = new HolidayMainService();
		int count2 = 1;
		double lastPrice = 0;
		double loanAmount = Float.parseFloat(jsonMainPayment.get("LOAN_AMOUNT").toString());
		if (array.size() > 0) {
			for (int i = 0; i < array.size(); i++) {
				JSONObject objRELATION = array.getJSONObject(i);
				String isHoliday="3";//默认为不是节假日
				String ADJUST_PAY_DATE = (String)objRELATION.get("REN_PAY_TIME");//调整后的本金还款日
				String ADJUST_REN_PAY_TIME = objRELATION.get("PAY_DATE").toString();//调整后的利息还款日
				if(i==0){
					lastPrice = loanAmount;
				}
				else{
					lastPrice = lastPrice-(Double.parseDouble(objRELATION.get("OWN_PRICE").toString()));
				}
				int j = ha.isHolidayByDate((String)objRELATION.get("REN_PAY_TIME"));
				int k = ha.isHolidayByDate(objRELATION.get("PAY_DATE").toString());
				//判断利息还款日和本金还款日是否都是节假日
				if(j==1&&k==1){
					isHoliday = "4";
					ADJUST_PAY_DATE = this.getAdjustDay(ADJUST_PAY_DATE, jsonMainPayment.get("holiday_pay_type").toString(), ha);//获取调整后的本金还款日
					if(jsonMainPayment.get("PAY_TYPE").toString().equals(4)){
						ADJUST_REN_PAY_TIME = this.getAdjustDay(ADJUST_REN_PAY_TIME, jsonMainPayment.get("holiday_pay_type").toString(), ha);//获取调整后的利息还款日
					}
				}
				if(j==1&&k!=1){
					isHoliday = "2";
					if(jsonMainPayment.get("PAY_TYPE").toString().equals(4)){
						ADJUST_REN_PAY_TIME = this.getAdjustDay(ADJUST_REN_PAY_TIME, jsonMainPayment.get("holiday_pay_type").toString(), ha);//获取调整后的利息还款日
					}
				}
				if(j!=1&&k==1){
					isHoliday = "1";
					ADJUST_PAY_DATE = this.getAdjustDay(ADJUST_PAY_DATE, jsonMainPayment.get("holiday_pay_type").toString(), ha);//获取调整后的本金还款日
				}
				objRELATION.put("ADJUST_PAY_DATE", ADJUST_PAY_DATE);
				objRELATION.put("ADJUST_REN_PAY_TIME", ADJUST_REN_PAY_TIME);
				objRELATION.put("ISHOLIDAY", isHoliday);
				objRELATION.put("LAST_PRICE", lastPrice);
				objRELATION.put("PAY_ID", jsonMainPayment.get("PAY_ID"));
				objRELATION.put("PROJECT_ID", jsonMainPayment.get("PROJECT_ID"));
				System.out.println(objRELATION);
				count2 = Dao.insert(namespace+"addPayMentDetail", objRELATION);
				if(count2==0){
					return -1;
				}
			}
		}
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("STATUS", "3");
		m.put("PAY_ID", jsonMainPayment.get("PAY_ID"));
		if(this.updatePaymentPlanStatus(m)<=0){
			return -1;
		}
		return count2;		
	}
	
	/**
	 * 修改还款计划书
	 * updatePayMentDetailService
	 * @date 2013-10-25 下午12:11:10
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int updatePayMentDetailService(JSONArray array,JSONObject jsonMainPayment){
		int count2 = 1;
		double lastPrice = 0;
		double loanAmount = Float.parseFloat(jsonMainPayment.get("LOAN_AMOUNT").toString());
		if (array.size() > 0) {
			for (int i = 0; i < array.size(); i++) {
				JSONObject objRELATION = array.getJSONObject(i);
				String ADJUST_PAY_DATE = (String)objRELATION.get("REN_PAY_TIME");//调整后的本金还款日
				String ADJUST_REN_PAY_TIME = objRELATION.get("PAY_DATE").toString();//调整后的利息还款日
				if(i==0){
					lastPrice = loanAmount;
				}
				else if(i>=array.size()-1){
					lastPrice = Double.parseDouble(objRELATION.get("OWN_PRICE").toString());
				}else{
					lastPrice = lastPrice-(Double.parseDouble(objRELATION.get("OWN_PRICE").toString()));
				}

				objRELATION.put("ADJUST_PAY_DATE", ADJUST_PAY_DATE);
				objRELATION.put("ADJUST_REN_PAY_TIME", ADJUST_REN_PAY_TIME);
				objRELATION.put("ISHOLIDAY", "3");
				objRELATION.put("LAST_PRICE", lastPrice);
				objRELATION.put("PAY_ID", jsonMainPayment.get("PAY_ID"));
				objRELATION.put("PROJECT_ID", jsonMainPayment.get("PROJECT_ID"));
				System.out.println(objRELATION);
				count2 = Dao.update(namespace+"updatePayMentDetail", objRELATION);
				if(count2==0){
					return -1;
				}
			}
		}
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("STATUS", "3");
		m.put("PAY_ID", jsonMainPayment.get("PAY_ID"));
		
		//手动调整的还款计划表 调息时需要特殊处理 king 2012-08-29
		m.put("RATE_TYPE", "2");
		if(this.updatePaymentPlanStatus(m)<=0){
			return -1;
		}
		return count2;		
	}
	
//	public int systemCreatePaymentDetail(Map<String,Object> map) throws Exception{
//		int per = 1;
//		String pay_type="";
//		List<PaymentLine>   payList = new ArrayList<PaymentLine>();
//		PaymentTableBusiness payTable = new PaymentTableBusiness();
//		SimpleDateFormat  dsf = new SimpleDateFormat("yyyy-MM-dd");
//		double loan_amount = Double.parseDouble(map.get("LOAN_AMOUNT").toString());//还款金额
//		int term = Integer.parseInt(map.get("TERM").toString());//期数
//		int period = Integer.parseInt(map.get("PERIOD").toString());//周期
//		double interest = Double.parseDouble(map.get("INTEREST").toString());//利率
//		int paytype = Integer.parseInt(map.get("PAY_TYPE").toString());//还款计划书生成规则
//		String paydate = map.get("PAY_DATE").toString();//开始还款日
//		if(period==1){
//			per = 1;
//		}
//		else if(period==2){
//			per = 4;
//		}
//		else{
//			per = 12;
//		}
//		if(paytype==1){
//			pay_type="期初等额本息支付";
//		}
//		else if(paytype==2){
//			pay_type="期初等额本金支付";
//		}
//		else if(paytype==3){
//			pay_type="期初不等额支付";
//		}
//		else{
//			pay_type="";
//		}
//		//系统调用算法生成还款计划书
//		payTable.setTopricSubfirent(loan_amount);//还款金额
//		payTable.setChangeTopricSubfirent(loan_amount);
//		payTable.setLeaseTerm(term);
//		payTable.setLeasePeriod(per);
//		payTable.setYearInterest(interest);
//		payTable.setPayWay(pay_type);
//		payTable.setStartDate(dsf.parse(paydate));
//		payList = payTable.print(payTable.magic());
//		return this.addPaymentDetailBySystem(payList, map);
//	}
	
	/**
	 * 修改方案信息
	 */
	public int updatePaymentPlanStatus(Map<String,Object> map){
		return Dao.update(namespace+"updatePaymentPlanStatus", map);
	}
	
	/** 
	* 获取调整后的还款日
	* @param specifiedDay 
	* @return 
	 * @throws Exception 
	* @throws Exception 
	*/ 
	@SuppressWarnings("static-access")
	public  String getAdjustDay(String specifiedDay,String type,HolidayMainService ha){
		String adjustDate = specifiedDay;
		int k = 0;
		do {
			if(type.equals("1")){
				adjustDate = this.getSpecifiedDayBefore(adjustDate);
			}
			else{
				adjustDate = this.getSpecifiedDayAfter(adjustDate);
			}
			k = ha.isHolidayByDate(adjustDate);
			System.out.println("k:  "+k+"specifiedDay "+specifiedDay+"  adjustDate "+adjustDate);
		}while(k==1);
		return adjustDate; 
	} 
	
	/** 
	* 获得指定日期的前一天 
	* @param specifiedDay 
	* @return 
	* @throws Exception 
	*/ 
	public static String getSpecifiedDayBefore(String specifiedDay){ 
		Calendar c = Calendar.getInstance(); 
		Date date=null; 
		try { 
		date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay); 
		} catch (Exception e) { 
		e.printStackTrace(); 
		} 
		c.setTime(date); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day-1); 
	
		String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
		return dayBefore; 
	} 
	
	/** 
	* 获得指定日期的后一天 
	* @param specifiedDay 
	* @return 
	*/ 
	public static String getSpecifiedDayAfter(String specifiedDay){ 
		Calendar c = Calendar.getInstance(); 
		Date date=null; 
		try { 
		date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay); 
		} catch (Exception e) { 
		e.printStackTrace(); 
		} 
		c.setTime(date); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day+1); 
	
		String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
		return dayAfter; 
	} 
}
