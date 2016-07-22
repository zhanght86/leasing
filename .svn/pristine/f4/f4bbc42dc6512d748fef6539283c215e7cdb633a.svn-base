package com.pqsoft.util;

import java.math.BigDecimal;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.pqsoft.skyeye.api.Dao;

/**
 * 支付表工具类
 * 
 * @author Administrator
 * 
 */
public class Util extends PaymentRate {
	final private static Logger logger = Logger.getLogger(Util.class);
	public static List<Object> adminIdList;
	public static final long GB = 1024 * 1024 * 1024;
	public static final long MB = 1024 * 1024;
	public static final long KB = 1024;
	public static final int HALF_YEAR = 6;
	public static final int ONE_YEAR = 12;
	public static final int THREE_YEAR = 36;
	public static final int FIVE_YEAR = 60;
	/**
	 * 数据字典工具
	 */
	public static final DataDictionaryUtil DICTAG=new DataDictionaryUtil(); 
	/** 半年对应的基准利率. */
	public double HALF_YEAR_BASE_RATE;
	/** 一年对应的基准利率. */
	public double ONE_YEAR_BASE_RATE;
	/** 三年对应的基准利率. */
	public double THREE_YEAR_BASE_RATE;
	/** 五年对应的基准利率. */
	public double FIVE_YEAR_BASE_RATE;
	/** 多于五年对应的基准利率. */
	public double MORE_YEAR_BASE_RATE;
	public static SimpleDateFormat dsf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 平台自定义添加字段工具
	 */
	public static final ModuleUtil MODULE=new ModuleUtil();

	/**
	 * 减法（支付表计算用到）十位
	 */
	public static Double subDouble(Double d1, Double d2) {
		Format format = new java.text.DecimalFormat("#0.0000000000");
		BigDecimal b1 = new BigDecimal(format.format(d1));
		BigDecimal b2 = new BigDecimal(format.format(d2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 加法（支付表计算用到）
	 */
	public static Double addDouble(Double d1, Double d2) {
		Format format = new java.text.DecimalFormat("#0.00000000000");
		BigDecimal b1 = new BigDecimal(format.format(d1));
		BigDecimal b2 = new BigDecimal(format.format(d2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 格式化数字（支付表计算用到）10位
	 */
	public static Double formatDouble10(Double d) {
		d = round(d, 10);
		Format format = new java.text.DecimalFormat("#0.0000000000");
		return new BigDecimal(format.format(d)).doubleValue();
	}

	/**
	 * 格式化数字（支付表计算用到）2位
	 */
	public static Double formatDouble2(Double d) {
		d = round(d, 2);
		Format format = new java.text.DecimalFormat("#0.00");
		return new BigDecimal(format.format(d)).doubleValue();
	}

	/**
	 * 格式化数字（支付表计算用到）0位
	 */
	public static Double formatDouble0(Double d) {
		d = round(d, 2);
		Format format = new java.text.DecimalFormat("#0");
		return new BigDecimal(format.format(d)).doubleValue();
	}
	/**
	 * 格式化数字（支付表计算用到）0位
	 */
	public static Double formatDouble00(Double d) {
		d = round(d, 0);
		Format format = new java.text.DecimalFormat("#0");
		return new BigDecimal(format.format(d)).doubleValue();
	}

	/**
	 * 格式化数字（支付表计算用到）6位
	 */
	public static Double formatDouble6(Double d) {
		d = round(d, 10);
		Format format = new java.text.DecimalFormat("#0.000000");
		return new BigDecimal(format.format(d)).doubleValue();
	}

	/**
	 * 格式化数字（支付表计算用到）8位
	 */
	public static Double formatDouble8(Double d) {
		d = round(d, 10);
		Format format = new java.text.DecimalFormat("#0.00000000");
		return new BigDecimal(format.format(d)).doubleValue();
	}

	/**
	 * 
	 * @param value
	 * @param scale
	 * @return
	 */
	public static double round(double value, int scale) {
		BigDecimal bigDecimal = new BigDecimal(value);
		return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 格式化数字（支付表计算用到）2位
	 */
	public static Double formatDouble4(Double d) {
		d = round(d, 4);
		Format format = new java.text.DecimalFormat("#0.0000");
		return new BigDecimal(format.format(d)).doubleValue();
	}

	/**
	 * 月份计算（支付表计算用到）
	 */
	public static Date calePayDate(Date startDate, int periodNum, int payCountOfYear, boolean isFirst) {
		if (startDate == null)
			return null;
		Calendar t = Calendar.getInstance();
		t.setTime(startDate);
		t.add(Calendar.MONTH, 12 / payCountOfYear * (periodNum - (isFirst ? 1 : 0)));
		return t.getTime();
	}

	/**
	 * 天份计算（支付表计算用到）
	 */
	public static Date calePayDateDay(Date startDate, int periodNum, int payCountOfYear, boolean isFirst) {
		if (startDate == null)
			return null;
		Calendar t = Calendar.getInstance();
		t.setTime(startDate);
		t.add(Calendar.DAY_OF_YEAR, periodNum);
		return t.getTime();
	}

	/**
	 * 月份计算（支付表计算用到）
	 */
	static Date IrrcalePayDate(Date startDate, int periodNum, int payCountOfYear, boolean isFirst) {
		if (startDate == null)
			return null;
		Calendar t = Calendar.getInstance();
		t.setTime(startDate);
		int type = 1;
		if (isFirst) {
			type = periodNum - 1;
		} else {
			type = (1 == periodNum || 2 == periodNum ? 1 : periodNum - 1) - 1;
		}
		t.add(Calendar.MONTH, 12 / payCountOfYear * type);
		return t.getTime();
	}

	/**
	 * 减法（普通方法）
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Double sub(Double d1, Double d2) {
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		if (v2 == 0) {
			throw new IllegalArgumentException("The divisor must be a non-zero number");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_EVEN).doubleValue();
	}

	/**
	 * 乘法（普通方法）
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double mul(double v1, double v2) {
		// if (v2 == 0) {
		// throw new IllegalArgumentException(
		// "The divisor must be a non-zero number");
		// }
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 乘法（普通方法）
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double mul1(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 乘法（多参数）
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double mul(double... v) {
		double result = 1.0;
		for (double d : v) {
			result = mul(result, d);
		}
		return result;
	}

	public static String format(Double d) {
		Format format = new java.text.DecimalFormat("###0.00");
		return format.format(d);
	}

	// 初始化利率
	@SuppressWarnings("unchecked")
	public void paymentRate() throws Exception {
		Map<String, Object> m = (Map) Dao.selectOne("PayTask.queryNewRate");
		logger.debug(m.toString());
		HALF_YEAR_BASE_RATE = Double.valueOf(m.get("SIX_MONTHS").toString());
		ONE_YEAR_BASE_RATE = Double.valueOf(m.get("ONE_YEAR").toString());
		THREE_YEAR_BASE_RATE = Double.valueOf(m.get("ONE_THREE_YEARS").toString());
		FIVE_YEAR_BASE_RATE = Double.valueOf(m.get("THREE_FIVE_YEARS").toString());
		MORE_YEAR_BASE_RATE = Double.valueOf(m.get("OVER_FIVE_YEARS").toString());
	}

	/**
	 * 计算年利率
	 * 
	 * @param totalMonth
	 * @param upRate
	 * @return
	 */
	public double YearRate(int totalMonth, double upRate) throws Exception {
		this.paymentRate();
		double yearRate = Util.div(100 + upRate, 100, 6);
		logger.debug("totalMonth : " + totalMonth + "=====" + yearRate);
		if (totalMonth <= HALF_YEAR) {
			return Util.mul1(HALF_YEAR_BASE_RATE, yearRate);
		} else if (totalMonth <= ONE_YEAR) {
			return Util.mul1(ONE_YEAR_BASE_RATE, yearRate);
		} else if (totalMonth <= THREE_YEAR) {
			return Util.mul1(THREE_YEAR_BASE_RATE, yearRate);
		} else if (totalMonth <= FIVE_YEAR) {
			return Util.mul1(FIVE_YEAR_BASE_RATE, yearRate);
		} else {
			return Util.mul1(MORE_YEAR_BASE_RATE, yearRate);
		}
	}

	/**
	 * 反推上浮比例
	 * 
	 * @param totalMonth
	 * @param upRate
	 * @return
	 */
	public double FloatRate(int totalMonth, double year) throws Exception {
		this.paymentRate();
		logger.debug("totalMonth : " + totalMonth + "=====" + year);
		double yearInster = 0;
		if (totalMonth <= HALF_YEAR) {
			yearInster = HALF_YEAR_BASE_RATE;
		} else if (totalMonth <= ONE_YEAR) {
			yearInster = ONE_YEAR_BASE_RATE;
		} else if (totalMonth <= THREE_YEAR) {
			yearInster = THREE_YEAR_BASE_RATE;
		} else if (totalMonth <= FIVE_YEAR) {
			yearInster = FIVE_YEAR_BASE_RATE;
		} else {
			yearInster = MORE_YEAR_BASE_RATE;
		}
		double lv = Util.div(year, yearInster, 5);
		double a = sub(lv, Double.valueOf(1));
		double returnValue = formatDouble6(a * 100);
		logger.debug("=============" + returnValue);
		return formatDouble6(returnValue);
	}

	/**
	 * 获得分页信息，当前页数、和每页显示多少行
	 * 
	 * @author likun
	 * @return 返回一个数组，分别为0：当期第几页，1：当前页面有多少条数据
	 * @param m
	 * @throws Exception
	 */
	public static int[] getPageInfo(Map<String, Object> m) throws Exception {
		int pageCurr = m.get("PAGE_CURR") == null ? 1 : Integer.parseInt(m.get("PAGE_CURR").toString());
		int pageCount = 10;
		if (m.get("PAGE_COUNT") == "" || m.get("PAGE_COUNT") == null) {
			pageCount = 10;
		} else {
			pageCount = Integer.parseInt(m.get("PAGE_COUNT").toString());
		}
		m.put("PAGE_BEGIN", pageCount * (pageCurr - 1) + 1);
		m.put("PAGE_END", pageCount * pageCurr);
		int[] page = { pageCurr, pageCount };
		return page;
	}

	/**
	 * @Description: 日期做差求天数
	 * @author 吴国伟
	 * @return
	 * @throws Exception
	 * @date 2011-11-21下午03:26:47
	 */
	public static int TimeDay(Map<String, Object> m) throws Exception {
		SimpleDateFormat dsf1 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar d1 = Calendar.getInstance();
		// 开始时间
		d1.setTime(dsf1.parse(m.get("START_DATE").toString()));
		// 结束时间
		Calendar d2 = Calendar.getInstance();
		d2.setTime(dsf1.parse(m.get("END_DATE").toString()));
		// 相差天数
		int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		d1 = (Calendar) d1.clone();
		logger.debug(d1.get(Calendar.YEAR) + " ==" + d2.get(Calendar.YEAR));
		// 不是同一年的情况下
		while (d1.get(Calendar.YEAR) < d2.get(Calendar.YEAR)) {
			days = 365 * (d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR)) + days;
			d1.add(Calendar.YEAR, d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR));
		}
		return days;
	}

	/**
	 * @Description: 日期做差求月数
	 * @author 吴国伟
	 * @param m
	 * @return
	 * @throws Exception
	 * @date 2011-11-21下午03:34:07
	 */
	public int TimeMonth(Map<String, Object> m) throws Exception {
		SimpleDateFormat dsf1 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar d1 = Calendar.getInstance();
		// 开始时间
		d1.setTime(dsf1.parse(m.get("StartDate").toString()));
		// 结束时间
		Calendar d2 = Calendar.getInstance();
		d2.setTime(dsf1.parse(m.get("EndDate").toString()));
		// 相差天数
		int days = d2.get(Calendar.MONTH) - d1.get(Calendar.MONTH);
		d1 = (Calendar) d1.clone();
		logger.debug(d1.get(Calendar.YEAR) + " ==" + d2.get(Calendar.YEAR));
		// 不是同一年的情况下
		while (d1.get(Calendar.YEAR) < d2.get(Calendar.YEAR)) {
			days = 365 * (d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR)) + days;
			d1.add(Calendar.YEAR, d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR));
		}
		return days;
	}

	/**
	 * @Description: 时间格式化
	 * @author 吴国伟
	 * @param startDate
	 * @return
	 * @throws Exception
	 * @date 2012-4-25下午05:14:08
	 */
	public static Date ProcessStartDate(Date startDate) throws Exception {
		if (startDate != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(startDate);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			startDate = c.getTime();
		}
		return null == startDate ? startDate : new java.sql.Date(dsf.parse(dsf.format(startDate)).getTime());
	}

	/**
	 * @Description: 组装IRR信息
	 * @author 吴国伟
	 * @param topricSubfirent
	 * @param quarter_equal_fee
	 * @param leaseTerm
	 * @return
	 * @date 2011-10-27上午11:56:49
	 */
	public double[] SimpleData(double topricSubfirent, double[] quarter_equal_fee, int leaseTerm, double[] IRRData) {
		IRRData[0] = topricSubfirent > 0 ? -topricSubfirent : topricSubfirent;
		System.out.println(0+"============IRRData==="+IRRData[0]);
		for (int i = 1; i <= leaseTerm; i++) {
			IRRData[i] = quarter_equal_fee[i-1];
			System.out.println(i+"==========IRRData====="+IRRData[i]);
		}
		return IRRData;
	}

	/*
	 * 得到下一个月的日期，返回字符串
	 */
	public static String getNextMonth(String date_) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar curr = Calendar.getInstance();
		try {
			curr.setTime(df.parse(date_));
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("格式化日期出错");
		}
		curr.add(Calendar.MONTH, 1);
		Date date = curr.getTime();
		return df.format(date);
	}

	/*
	 * 得到上n个月或者下n个月的日期，返回字符串 如果需要得到上n个月则num的值为负值，如：-1就是得到上个月的日期
	 */
	public static String getMonth(String date_, int num) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar curr = Calendar.getInstance();
		try {
			curr.setTime(df.parse(date_));
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("格式化日期出错");
		}
		curr.add(Calendar.MONTH, num);
		Date date = curr.getTime();
		return df.format(date);
	}

//	/*
//	 * 山重还款日计算 firstDate起租日期 lease_interval 如果月付1，双月2，季付3 返回值为
//	 */
//	public static String getPayDateByFirstDate(String firstDate, String lease_interval) {
//		// lease_interval="1";
//		String first_day = firstDate.substring(8, 10);
//		String secondDate = "";
//		if (Integer.parseInt(first_day) >= 1 && Integer.parseInt(first_day) <= 5) {
//			secondDate = getDateAdd(firstDate, Integer.parseInt(lease_interval), "mm");
//			secondDate = secondDate.substring(0, 7) + "-05";
//		} else if (Integer.parseInt(first_day) > 5 && Integer.parseInt(first_day) <= 20) {
//			secondDate = getDateAdd(firstDate, Integer.parseInt(lease_interval), "mm");
//			secondDate = secondDate.substring(0, 7) + "-20";
//		} else {
//			secondDate = getDateAdd(firstDate, Integer.parseInt(lease_interval) + 1, "mm");
//			secondDate = secondDate.substring(0, 7) + "-05";
//		}
//		return secondDate;
//	}
	
	/*
	 * 平台还款日计算 firstDate起租日,Date1还款日期（主要约定多少号） lease_interval 如果月付1，双月2，季付3 返回值为
	 */
	public static String getPayDateByFirstDatePT(String firstDate,String Date1, String lease_interval) {
		// lease_interval="1";
		String first_day = firstDate.substring(8, 10);
		String secondDate = "";
		int day_=Integer.parseInt(Date1.substring(8, 10));
		if (Integer.parseInt(first_day) <= day_ ) {
			secondDate = getDateAdd(firstDate, Integer.parseInt(lease_interval), "mm");
			secondDate = secondDate.substring(0, 7) +"-"+ Integer.parseInt(Date1.substring(8, 10));
		} else {
			secondDate = getDateAdd(firstDate, Integer.parseInt(lease_interval) + 1, "mm");
			secondDate = secondDate.substring(0, 7) +"-"+ Integer.parseInt(Date1.substring(8, 10));
		}
		return secondDate;
	}
//	/*
//	 * 山重还款日计算 firstDate起租日期 lease_interval 如果月付1，双月2，季付3 返回值为
//	 */
//	public static String getPayDateByFirstDate2(String firstDate, String lease_interval) {
//		// lease_interval="1";
//		String first_day = firstDate.substring(8, 10);
//		String secondDate = "";
//		if (Integer.parseInt(first_day) >= 1 && Integer.parseInt(first_day) <= 5) {
//			secondDate = getDateAdd(firstDate, Integer.parseInt(lease_interval), "mm");
//			secondDate = secondDate.substring(0, 7) + "-05";
//		} else if (Integer.parseInt(first_day) > 5 && Integer.parseInt(first_day) <= 15) {
//			secondDate = getDateAdd(firstDate, Integer.parseInt(lease_interval), "mm");
//			secondDate = secondDate.substring(0, 7) + "-15";
//		} else if (Integer.parseInt(first_day) > 15 && Integer.parseInt(first_day) <= 25) {
//			secondDate = getDateAdd(firstDate, Integer.parseInt(lease_interval), "mm");
//			secondDate = secondDate.substring(0, 7) + "-25";
//		}else {
//			secondDate = getDateAdd(firstDate, Integer.parseInt(lease_interval) + 1, "mm");
//			secondDate = secondDate.substring(0, 7) + "-05";
//		}
//		return secondDate;
//	}

//	/**
//	 * 山重特殊付款日计算 ---针对供应商是否陕重汽或陕西通力 陕重汽 ADMR-8586BY，陕西通力 ADMR-8NL5NS
//	 * 
//	 * @param payDate
//	 * @return
//	 * @author:King 2013-10-19
//	 */
//	@SuppressWarnings("static-access")
//	public static String NEXT_WEDNESDAY(String payDate) {
//		Calendar curr = Calendar.getInstance();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		try {
//			curr.setTime(sdf.parse(payDate));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		switch (curr.get(Calendar.DAY_OF_WEEK)) {
//		case 1:
//			curr.add(curr.DAY_OF_WEEK, 4);
////			curr.add(curr.DAY_OF_WEEK, 3);
//			break;
//		case 2:
//			curr.add(curr.DAY_OF_WEEK, 3);
////			curr.add(curr.DAY_OF_WEEK, 2);
//			break;
//		case 3:
//			curr.add(curr.DAY_OF_WEEK, 7);
////			curr.add(curr.DAY_OF_WEEK, 1);
//			break;
//		case 4:
//			curr.add(curr.DAY_OF_WEEK, 6);
////			curr.add(curr.DAY_OF_WEEK, 7);
//			break;
//		case 5:
//			curr.add(curr.DAY_OF_WEEK, 5);
////			curr.add(curr.DAY_OF_WEEK, 6);
//			break;
//		case 6:
//			curr.add(curr.DAY_OF_WEEK, 6);
////			curr.add(curr.DAY_OF_WEEK, 5);
//			break;
//		case 7:
//			curr.add(curr.DAY_OF_WEEK, 5);
////			curr.add(curr.DAY_OF_WEEK, 4);
//			break;
//		}
//		return sdf.format(curr.getTime());
//	}
//
//	/**
//	 * 山重特殊付款日计算---建机
//	 * @param payDate
//	 * @return
//	 * @author:King 2013-10-19
//	 */
//	@SuppressWarnings("static-access")
//	public static String NEXT_WEDNESDAY_jj(String payDate) {
//		Calendar curr = Calendar.getInstance();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		try {
//			curr.setTime(sdf.parse(payDate));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		switch (curr.get(Calendar.DAY_OF_WEEK)) {
//		case 1:
////			curr.add(curr.DAY_OF_WEEK, 10);
//			curr.add(curr.DAY_OF_WEEK, 9);
//			break;
//		case 2:
//			curr.add(curr.DAY_OF_WEEK, 8);
////			curr.add(curr.DAY_OF_WEEK, 9);
//			break;
//		case 3:
////			curr.add(curr.DAY_OF_WEEK, 8);
//			curr.add(curr.DAY_OF_WEEK, 7);
//			break;
//		case 4:
//			curr.add(curr.DAY_OF_WEEK, 6);
////			curr.add(curr.DAY_OF_WEEK, 7);
//			break;
//		case 5:
//			curr.add(curr.DAY_OF_WEEK, 12);
////			curr.add(curr.DAY_OF_WEEK, 6);
//			break;
//		case 6:
//			curr.add(curr.DAY_OF_WEEK, 11);
////			curr.add(curr.DAY_OF_WEEK, 12);
//			break;
//		case 7:
//			curr.add(curr.DAY_OF_WEEK, 10);
////			curr.add(curr.DAY_OF_WEEK, 11);
//			break;
//		}
//		return sdf.format(curr.getTime());
//	}


	public static String getDateAdd(String strDate, int leng, String type) {
		Date addDate = null;
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(strDate);
		} catch (Exception e) {
			System.out.println(e);
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (type.equals("yy")) {
			cal.add(Calendar.YEAR, leng);
		} else if (type.equals("mm")) {
			cal.add(Calendar.MONTH, leng);
		} else if (type.equals("we")) {
			cal.add(Calendar.WEEK_OF_YEAR, leng);
		} else if (type.equals("dd")) {
			cal.add(Calendar.DAY_OF_YEAR, leng);
		} else if (type.equals("hh")) {
			cal.add(Calendar.HOUR_OF_DAY, leng);
		} else if (type.equals("mi")) {
			cal.add(Calendar.MINUTE, leng);
		} else if (type.equals("ss")) {

		}
		addDate = cal.getTime();
		return sdf.format(addDate);
	}

	public static void main(String[] args) throws ParseException {
//		System.out.println(Util.NEXT_WEDNESDAY("2013-10-12"));
//		System.out.println(Util.NEXT_WEDNESDAY_jj("2013-10-12"));
		// System.out.println(getPayDateByFirstDate("2013-09-21","1"));
		// System.out.println(Integer.parseInt("2013-09-02".substring(8,10));
//		System.out.println(getPayDateByFirstDate2("2013-09-21","1"));
	}
}
