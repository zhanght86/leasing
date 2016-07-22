package com.pqsoft.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

public class RentBeforeInterest {

	private  Logger logger = Logger.getLogger(getClass());
	SimpleDateFormat dsf = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 
	* @Title: PayShrinkPMT租前息计算
	* @autor wuguowei_jing@163.com 2014-5-12 下午5:30:34
	* @param m
	* List<Map<String,Object>>    
	* @throws
	 */
    public  JSONObject PayShrinkPMT(Map<String, Object> m)  {
		
    	SimpleDateFormat dsf = new SimpleDateFormat("yyyy-MM-dd");
    	Map<String, Object> tm = new HashMap<String, Object>();
    	Map<String,Object> map = null ;
		long payDays=0;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 支付表计算参数
		double LEASE_SUBFIRENT = Double.valueOf(m.get("LEASE_TOPRIC").toString());
		// 周期天、月、双月、季度、半年、年 
		int LEASE_PERIOD = Integer.valueOf(m.get("LEASE_PERIOD").toString());
		// 日利率
		double YEAR_INTEREST = Double.valueOf(m.get("TIAN_YEAR_INTEREST").toString());
		logger.debug("----------------------------YEAR_INTEREST=" + m.get("YEAR_INTEREST"));
		logger.debug("----------------------------LEASE_PERIOD=" + m.get("LEASE_PERIOD"));
		//开始时间
		String START_DATE = null;//开始时间
		String END_DATE = null;//结束时间
		String H_START_DATE = null;//还租日
		int LEASE_TERM =1; //支付次数(期数)默认一期
		Calendar c_begin = Calendar.getInstance(); //开始计算时间
		Calendar c_middle = Calendar.getInstance();//每期的计算时间-中间变量
		Calendar c_end_end = Calendar.getInstance();//每期还租时间
		Calendar c_end = Calendar.getInstance();//还租结束计算时间
		logger.debug("----------------------------START_DATE=" + m.get("START_DATE"));
		logger.debug("----------------------------END_DATE=" + m.get("END_DATE"));
		if ((m.get("START_DATE") != null && !m.get("START_DATE").equals("")) || (m.get("END_DATE") != null && !m.get("END_DATE").equals(""))) {
			START_DATE = m.get("START_DATE").toString();
			END_DATE = m.get("END_DATE").toString();
			H_START_DATE = m.get("H_START_DATE").toString();
			H_START_DATE = this.countMonth(START_DATE, H_START_DATE,LEASE_PERIOD);//计算第一期还款日
			logger.debug("----------------------------H_START_DATE=" + m.get("H_START_DATE"));
			try {
				c_end.setTime(dsf.parse(END_DATE));
				c_begin.setTime(dsf.parse(START_DATE));
				c_middle.setTime(dsf.parse(H_START_DATE));
				c_end_end.setTime(dsf.parse(H_START_DATE));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//结束时间（预估起租日）
			  
		}
		   /**
		    * // LEASE_PERIOD  周期月1、双月2、季度3、半年6、年12 
		    */
            while(c_end.getTime().getTime()>=c_end_end.getTime().getTime()){//结束时间大于还租日
            	
				    map = new HashMap<String,Object>();
					if(LEASE_TERM==1){//第一期
					  	//需要支付的天数，算利息需要 还款日-开始时间
					  	payDays=(c_end_end.getTime().getTime()-c_begin.getTime().getTime())/(1000*60*60*24);
					    logger.debug(LEASE_TERM+"=="+dsf.format(c_end_end.getTime())+"=="+dsf.format(c_begin.getTime()));
					}else{//中间期次
							payDays=(c_end_end.getTime().getTime()-c_middle.getTime().getTime())/(1000*60*60*24);
							logger.debug(LEASE_TERM+"=="+dsf.format(c_end_end.getTime())+"=="+dsf.format(c_middle.getTime()));
							//c_end_end.add(Calendar.MONTH, 1);
							c_middle.add(Calendar.MONTH, LEASE_PERIOD);
							
					}
					map.put("zj", Util.formatDouble2(YEAR_INTEREST*payDays*LEASE_SUBFIRENT));
					map.put("bj", 0);
					map.put("lx", Util.formatDouble2(YEAR_INTEREST*payDays*LEASE_SUBFIRENT));
					map.put("sybj", LEASE_SUBFIRENT);
					map.put("PAY_DATE", dsf.format(c_end_end.getTime()));
					map.put("qc", LEASE_TERM);
					list.add(map);
					LEASE_TERM = LEASE_TERM+1;
					c_end_end.add(Calendar.MONTH, LEASE_PERIOD);
		   }
           //最后一期处理
            if(c_end_end.getTime().getTime() > c_end.getTime().getTime() && LEASE_TERM>1){
            	 map = new HashMap<String,Object>();
			     payDays=(c_end.getTime().getTime()-c_middle.getTime().getTime())/(1000*60*60*24);
			     logger.debug(LEASE_TERM+"=="+dsf.format(c_end.getTime())+"=="+dsf.format(c_middle.getTime()));
			     map.put("zj", Util.formatDouble2(YEAR_INTEREST*payDays*LEASE_SUBFIRENT));
				 map.put("bj", 0);
				 map.put("lx", Util.formatDouble2(YEAR_INTEREST*payDays*LEASE_SUBFIRENT));
				 map.put("sybj", LEASE_SUBFIRENT);
				 map.put("PAY_DATE", dsf.format(c_end_end.getTime()));
				 map.put("qc", LEASE_TERM);
				 if(payDays>0){
					 list.add(map);
				 }else{
					 LEASE_TERM = LEASE_TERM-1;
				 }
				 
            }else{
           	 	map = new HashMap<String,Object>();
			     payDays=(c_end.getTime().getTime()-c_begin.getTime().getTime())/(1000*60*60*24);
			     map.put("zj", Util.formatDouble2(YEAR_INTEREST*payDays*LEASE_SUBFIRENT));
				 map.put("bj", 0);
				 map.put("lx", Util.formatDouble2(YEAR_INTEREST*payDays*LEASE_SUBFIRENT));
				 map.put("sybj", LEASE_SUBFIRENT);
				 map.put("PAY_DATE", dsf.format(c_end_end.getTime()));
				 map.put("qc", LEASE_TERM);
				 if(payDays>0){
					 list.add(map);
				 }else{
					 LEASE_TERM = LEASE_TERM-1;
				 }
            	
            }
            tm.put("ln", list);
    		tm.put("TIAN_YEAR_INTEREST", m.get("TIAN_YEAR_INTEREST"));
    		tm.put("LEASE_TERM", LEASE_TERM);
	
		return JSONObject.fromObject(tm);
	}
    /**
     * 计算还款日
    * @Title: countMonth
    * @autor wuguowei_jing@163.com 2014-5-13 下午2:33:31
    * @Description: TODO
    * @param START_DATE 起租日
    * @param H_START_DATE  还租日 取天
    * @param LEASE_PERIOD  还款周期
     */
	
	public  String countMonth(String START_DATE,String H_START_DATE,int LEASE_PERIOD){
		Calendar c_begin = Calendar.getInstance(); 
		Calendar c_end = Calendar.getInstance();
		  
		try {
			c_begin.setTime(dsf.parse(START_DATE));
			c_end.setTime(dsf.parse(H_START_DATE));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c_begin.add(Calendar.MONTH, LEASE_PERIOD);//起租日加周期（按月）
		int START_YEAR = c_begin.get(Calendar.YEAR); //第一期开始时间的年份
		int START_MONTH = c_begin.get(Calendar.MONTH)+1; //第一期开始时间的月份
		int START_DAY = c_end.get(Calendar.DATE);
		int END_DAY = c_end.get(Calendar.DATE);//还款日-每月还款是哪一天
		if(START_DAY < END_DAY){//是否是起租日当月还款
			START_MONTH = START_MONTH-1;
		}
		
		String newHZR = START_YEAR+"-"+START_MONTH+"-"+END_DAY;
		
		return newHZR;
	}
}
