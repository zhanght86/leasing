package com.pqsoft.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;

/**
 * 
 * @ClassName: DateUtil 
 * @author 程龙达
 * @date 2013-9-5 上午10:46:22 
 *
 */
public class DateUtil {
	
	public static String getSysDate(){
        return (new SimpleDateFormat("yyyyMMdd")).format(new Date());
	}
	
	public static String getSysDate(String pattern){
		if(pattern == null)
			return (new SimpleDateFormat("yyyyMMdd")).format(new Date());
        else
            return (new SimpleDateFormat(pattern)).format(new Date());
	}
	
	/**
	 * 根据最后一期约定还款日及还款方式计算租赁物到期日 --山重
	 * @param PROJECT_ID 项目ID
	 * @param last_pay_date  最后一期约定还款日
	 * @param pay_way 还款方式
	 * @param LEASE_PERIOD 租赁周期
	 * @return
	 * @author:King 2013-10-23
	 */
	public static String getPAYLIST_END_DATE(String PROJECT_ID,String last_pay_date,String pay_way,int LEASE_PERIOD){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar payDate=Calendar.getInstance();
		try {
			payDate.setTime(sdf.parse(last_pay_date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(pay_way.contains("期初")){
			payDate.add(Calendar.MONTH, LEASE_PERIOD);
//			payDate.add(Calendar.DATE, 1);
		}else{
//			payDate.add(Calendar.DATE, 1);
		}
		
		Map<String,Object> dateMap=new HashMap<String,Object>();
		dateMap.put("END_DATE", sdf.format(payDate.getTime()));
		dateMap.put("PROJECT_ID", PROJECT_ID);
		Dao.update("project.updatePRO_END_DATE",dateMap);
		
		Object PAY_CODE=Dao.update("project.showPaylistCodeByProId",dateMap)+"";
		if(StringUtils.isBlank(PAY_CODE)){
			return sdf.format(payDate.getTime());
		}else{
			dateMap.put("PAY_CODE", PAY_CODE);
			Dao.update("PayTask.updatePayEndDate",dateMap);
			return sdf.format(payDate.getTime());
		}
	}
	
	/**
	 * 根据最后一期约定还款日及还款方式计算租赁物到期日 --山重
	 * @param PROJECT_ID 项目ID
	 * @param last_pay_date  最后一期约定还款日
	 * @param pay_way 还款方式
	 * @param LEASE_PERIOD 租赁周期
	 * @return
	 * @author:King 2013-10-23
	 */
	public static String getPAYLIST_END_DATE_SCHEME(String PROJECT_ID,String last_pay_date,String pay_way,int LEASE_PERIOD){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar payDate=Calendar.getInstance();
		try {
			payDate.setTime(sdf.parse(last_pay_date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(pay_way.contains("期初")){
			payDate.add(Calendar.MONTH, LEASE_PERIOD);
//			payDate.add(Calendar.DATE, 1);
		}else{
//			payDate.add(Calendar.DATE, 1);
		}
		
		Map<String,Object> dateMap=new HashMap<String,Object>();
		dateMap.put("END_DATE", sdf.format(payDate.getTime()));
		dateMap.put("PROJECT_ID", PROJECT_ID);
		Dao.update("project.updatePRO_END_DATE",dateMap);
		
		Object PAY_CODE=Dao.update("project.showPaylistCodeByProId",dateMap)+"";
		if(StringUtils.isBlank(PAY_CODE)){
			return sdf.format(payDate.getTime());
		}else{
			dateMap.put("PAY_CODE", PAY_CODE);
			Dao.update("PayTask.updatePayEndDate",dateMap);
			return sdf.format(payDate.getTime());
		}
	}
//	public static void main(String[] args) {
//		System.out.println(DateUtil.getPAYLIST_END_DATE("2013-10-31", "期初等额", 1));
//	}
	
	/**
	 * 格式化日期
	 * 
	 * @param dateStr 字符型日期
	 * @param format 格式
	 * @return 返回日期
	 */
	public static java.util.Date parseDate(String dateStr, String format) {
		java.util.Date date = null;
		try {
			java.text.DateFormat df = new java.text.SimpleDateFormat(format);
			String dt = dateStr.replaceAll("-", "/");
			if ((!dt.equals("")) && (dt.length() < format.length())) {
				dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]",
						"0");
			}
			date = (java.util.Date) df.parse(dt);
		} catch (Exception e) {
		}
		return date;
	}

	public static java.util.Date parseDate(String dateStr) {
		return parseDate(dateStr, "yyyy/MM/dd");
	}

	public static java.util.Date parseDate(java.sql.Date date) {
		return date;
	}

	public static java.sql.Date parseSqlDate(java.util.Date date) {
		if (date != null)
			return new java.sql.Date(date.getTime());
		else
			return null;
	}

	public static java.sql.Date parseSqlDate(String dateStr, String format) {
		java.util.Date date = parseDate(dateStr, format);
		return parseSqlDate(date);
	}

	public static java.sql.Date parseSqlDate(String dateStr) {
		return parseSqlDate(dateStr, "yyyy/MM/dd");
	}

	public static java.sql.Timestamp parseTimestamp(String dateStr,
			String format) {
		java.util.Date date = parseDate(dateStr, format);
		if (date != null) {
			long t = date.getTime();
			return new java.sql.Timestamp(t);
		} else
			return null;
	}

	public static java.sql.Timestamp parseTimestamp(String dateStr) {
		return parseTimestamp(dateStr, "yyyy/MM/dd  HH:mm:ss");
	}

	/**
	 * 格式化输出日期
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式
	 * @return 返回字符型日期
	 */
	public static String format(java.util.Date date, String format) {
		String result = "";
		try {
			if (date != null) {
				java.text.DateFormat df = new java.text.SimpleDateFormat(format);
				result = df.format(date);
			}
		} catch (Exception e) {
		}
		return result;
	}

	public static String format(java.util.Date date) {
		return format(date, "yyyy/MM/dd");
	}

	/**
	 * 返回年份
	 * 
	 * @param date
	 *            日期
	 * @return 返回年份
	 */
	public static int getYear(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.YEAR);
	}

	/**
	 * 返回月份
	 * 
	 * @param date
	 *            日期
	 * @return 返回月份
	 */
	public static int getMonth(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MONTH) + 1;
	}

	/**
	 * 返回日份
	 * 
	 * @param date
	 *            日期
	 * @return 返回日份
	 */
	public static int getDay(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回小时
	 * 
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHour(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.HOUR_OF_DAY);
	}

	/**
	 * 返回分钟
	 * 
	 * @param date
	 *            日期
	 * @return 返回分钟
	 */
	public static int getMinute(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MINUTE);
	}

	/**
	 * 返回秒钟
	 * 
	 * @param date
	 *            日期
	 * @return 返回秒钟
	 */
	public static int getSecond(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.SECOND);
	}

	/**
	 * 返回毫秒
	 * 
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * 返回字符型日期
	 * 
	 * @param date
	 *            日期
	 * @return 返回字符型日期
	 */
	public static String getDate(java.util.Date date) {
		return format(date, "yyyy/MM/dd");
	}

	/**
	 * 返回字符型时间
	 * 
	 * @param date
	 *            日期
	 * @return 返回字符型时间
	 */
	public static String getTime(java.util.Date date) {
		return format(date, "HH:mm:ss");
	}

	/**
	 * 返回字符型日期时间
	 * 
	 * @param date
	 *            日期
	 * @return 返回字符型日期时间
	 */
	public static String getDateTime(java.util.Date date) {
		return format(date, "yyyy/MM/dd  HH:mm:ss");
	}

	/**
	 * 日期相加
	 * 
	 * @param date
	 *            日期
	 * @param day
	 *            天数
	 * @return 返回相加后的日期
	 */
	public static java.util.Date addDate(java.util.Date date, int day) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}

	/**
	 * 日期相减
	 * 
	 * @param date
	 *            日期
	 * @param date1
	 *            日期
	 * @return 返回相减后的日期
	 */
	public static int diffDate(java.util.Date date, java.util.Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}

	/**
	 * 获取两个日期之间的月份差
	 *
	 * @author 钟林俊
	 * @param date1 日期参数1
	 * @param date2 日期参数2
     * @return 两个日期间的月份差
     */
	public static int getMonthDifference(Date date1, Date date2){
		if(date1 == null || date2 == null){
			throw new ActionException("空日期之间无法获取月份差！");
		}

		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);

		int year1 = calendar1.get(Calendar.YEAR);
		int month1 = calendar1.get(Calendar.MONTH);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);

		int year2 = calendar2.get(Calendar.YEAR);
		int month2 = calendar2.get(Calendar.MONTH);

		return (year1 - year2) * 12 + month1 - month2;
	}
}
