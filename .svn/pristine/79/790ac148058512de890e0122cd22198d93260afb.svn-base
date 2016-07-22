package com.pqsoft.holiday.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
public class ValidateTime {
	// 定义校验格式
	private static String pattern = "yyyy-mm-dd";
	protected static final Logger logger = Logger.getLogger(ValidateTime.class);

	/**
	 * 根据传入的字符串校验日期
	 * 
	 * @author 杨雪
	 * @createDate 2013-10-18<br>
	 * @return
	 */
	public static boolean validate(String time) {
		boolean flag = true;
		String time2 = formatDate(constructDate(time));
		flag = time.equals(time2);
		return flag;
	}

	/**
	 * 格式化日期
	 * 
	 * @author 杨雪
	 * @createDate 2013-10-18<br>
	 * @return
	 */
	private static Date constructDate(String strDate) {

		Date exitDate = null;

		if (strDate != null) {

			try {

				DateFormat df = new SimpleDateFormat(pattern);

				exitDate = df.parse(strDate);

			} catch (ParseException pe) {
				System.out.println("日期格式不正确");
				logger.info("不是指定格式的字符串日期");
			}

		}
		return exitDate;

	}

	/**
	 * 根据定义的格式格式化日期
	 * 
	 * @author 杨雪
	 * @createDate 2013-10-18<br>
	 * @return
	 */
	private static String formatDate(Date exitDate) {

		String s = null;

		if (exitDate != null) {

			DateFormat df = new SimpleDateFormat(pattern);

			s = df.format(exitDate);

		}

		return s;

	}
//	public static void main(String args[]) throws ParseException {
//		float a = Float.parseFloat("900000.091");
//		double b = 12.12;
//		System.out.println(a);
//	}
}
