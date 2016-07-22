package com.pqsoft.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.pqsoft.skyeye.math.Finance;

/**
 * IRR计算年年利率
 * 
 * @author 吴国伟
 */
public class PaymentIRRUtil {

	/**
	 * 收益率XIRR
	 * 
	 * @param cashFlows
	 *            现金流
	 * @param date
	 *            现金流对应的收支时间 没有时间的配置参数为null
	 * @param estimatedResult
	 *            初始值 Double.NaN
	 * @param period
	 *            租赁周期 月为 1 双月2 季度 3 半年6 一年12
	 * @return 年租赁收益率
	 * @author:King 2013-1-10
	 */
	public static double getIRR(final double[] cashFlows, final Date[] date,
			final double estimatedResult, final int period) {
		double result = Double.NaN;
		boolean isDate = false;

		if (StringUtils.isNotBlank(date)) {
			Calendar firstDate = Calendar.getInstance();
			// 判断是否需要根据实际时间计算收益率 false为不根据时间
			try {
				firstDate.setTime(date[0]);
				isDate = true;
				if (cashFlows.length != date.length) {
					isDate = false;
				} else if (!PaymentIRRUtil.getDateTerm(date)) {
					isDate = false;
				}
			} catch (Exception e) {
			}
			if (isDate == false
					|| (cashFlows != null && cashFlows.length > 0 && cashFlows.length == date.length)) {

				if (cashFlows[0] != 0.0) {

					final double noOfCashFlows = cashFlows.length;

					double sumCashFlows = 0.0;

					int noOfNegativeCashFlows = 0;

					int noOfPositiveCashFlows = 0;
					for (int i = 0; i < noOfCashFlows; i++) {

						sumCashFlows += cashFlows[i];

						if (cashFlows[i] > 0) {

							noOfPositiveCashFlows++;

						} else if (cashFlows[i] < 0) {

							noOfNegativeCashFlows++;
						}

					}
					if (noOfNegativeCashFlows > 0 && noOfPositiveCashFlows > 0) {

						double irrGuess = 0.1; // default: 10%
						if (!Double.isNaN(estimatedResult)) {
							irrGuess = estimatedResult;
							if (irrGuess <= 0.0) {

								irrGuess = 0.5;

							}

						}

						double irr = 0.0;

						if (sumCashFlows < 0) {

							irr = -irrGuess;

						} else {

							irr = irrGuess;

						}

						final double minDistance = 1E-15;

						final double cashFlowStart = cashFlows[0];

						final int maxIteration = 1000;

						boolean wasHi = false;

						double cashValue = 0.0;

						for (int i = 0; i <= maxIteration; i++) {

							cashValue = cashFlowStart;
							Calendar cal = Calendar.getInstance();
							for (int j = 1; j < noOfCashFlows; j++) {
								if (isDate) {
									// 改变j为日期
									cal.setTime(date[j]);
									int day = getIntervalDays(firstDate, cal);
									cashValue += cashFlows[j]
											/ Math.pow(1.0 + irr, day / 365.0);
								} else {
									cashValue += cashFlows[j]
											/ Math.pow(1.0 + irr, j);
								}
							}

							if (Math.abs(cashValue) < 0.001) {

								result = irr;

								break;

							}
							if (cashValue > 0.0) {

								if (wasHi) {

									irrGuess /= 2;

								}

								irr += irrGuess;

								if (wasHi) {

									irrGuess -= minDistance;

									wasHi = false;

								}

							} else {

								irrGuess /= 2;

								irr -= irrGuess;

								wasHi = true;

							}

							if (irrGuess <= minDistance) {

								result = irr;

								break;

							}

						}

					}

				}
			}
		}
		if (isDate == false) {
			result = Finance.getIRR(cashFlows, 0.1);
			if (isDate == false) {
				result = result * 12 / period;
			}
		}
		return result;

	}

	// 计算两日期之间天数
	public static int getIntervalDays(Calendar startday, Calendar endday) {
		if (startday.after(endday)) {
			Calendar cal = startday;
			startday = endday;
			endday = cal;
		}
		long sl = startday.getTimeInMillis();
		long el = endday.getTimeInMillis();
		long ei = el - sl;
		return (int) (ei / (1000 * 60 * 60 * 24));
	}

	/**
	 * 确定时间间隔是否一致
	 * 
	 * @param date
	 * @return
	 * @author:King 2013-1-10
	 */
	public static boolean getDateTerm(Date[] date) {
		Calendar nowCalendar = Calendar.getInstance();
		Calendar afterCalendar = Calendar.getInstance();
		nowCalendar.setTime(date[0]);
		boolean flag = false;
		long distance = 0;
		for (int i = 1; i < date.length; i++) {
			afterCalendar.setTime(date[i]);
			long sl = nowCalendar.getTimeInMillis();
			long el = afterCalendar.getTimeInMillis();
			if (i == 1) {
				distance = (el - sl) / (1000 * 60 * 60 * 24);
			} else {
				long dis = (el - sl) / (1000 * 60 * 60 * 24);
				if (Math.abs(distance - dis) > 1) {
					flag = true;
					break;
				}
			}
			nowCalendar.setTime(date[i]);
		}
		return flag;
	}

	public static void main(String[] args) {

		// double[] cashFlows =
		// {-230000,10768.1,11776.43552,11784.46659,11792.59923,11800.83474,11809.17442,11817.61959,11826.17157,11834.83173,11843.60142,11852.48204,11861.47498,11870.58168,11879.80356,11889.14209,11898.59873,11908.17499,11917.87238,11927.69242,11937.63668,11947.70671,11957.90412,11968.23051,11978.68752,10.72321204};
		// double[] cashFlows = { -70000, 12000, 15000, 18000, 21000, 26000 };
		// double []cashFlows=new double[20];
		// // double[] cashFlows = new double[13];
		// cashFlows[0] = - 2780566.17d;
		// for (int i=1; i<=19;i++) {
		// cashFlows[i] = 156215.23;
		// }
		double[] cash03 = { -1000000.00, 95900.34, 95900.34, 95900.34,
				95900.34, 95900.34, 95900.34, 95900.34, 95900.34, 95900.34,
				95900.34, 95900.34, 95900.34 };

		double[] cash01 = { -870000.00, 31762.51, 31762.51, 31762.51, 31762.51,
				31762.51, 31762.51, 31762.51, 31762.51, 31762.51, 31762.51,
				31762.51, 31762.51, 31762.51, 31762.51, 31762.51, 31762.51,
				31762.51, 31762.51, 31762.51, 31762.51, 31762.51, 31762.51,
				31762.51, 31762.51, 31762.51, 31762.51, 31762.51, 31762.51,
				31762.51, 31762.51, 31762.51, 31762.51, 27050.05, 0.00, 0.00,
				0.00 };
		double[] cash02 = { -1000000.00, 31762.51, 31762.51, 31762.51,
				31762.51, 31762.51, 31762.51, 31762.51, 31762.51, 31762.51,
				31762.51, 31762.51, 31762.51, 31762.51, 31762.51, 31762.51,
				31762.51, 31762.51, 31762.51, 31762.51, 31762.51, 31762.51,
				31762.51, 31762.51, 31762.51, 31762.51, 31762.51, 31762.51,
				31762.51, 31762.51, 31762.51, 31762.51, 31762.51, 31762.51,
				31762.51, 31762.51, 31762.51 };
		double[] cash04 = { -870000.00, 95900.34, 95900.34, 95900.34, 95900.34,
				95900.34, 95900.34, 95900.34, 95900.34, 95900.34, 95900.34,
				91800.68, 0.00 };
		double[] cash05 = { -1615000.00, 74256.00, 74256.00, 74256.00,
				74256.00, 74256.00, 74256.00, 74256.00, 74256.00, 74256.00,
				189221.73, 189221.73, 189221.73, 189221.73, 189221.73,
				189221.73 };
		double[] cash06 = { -241788.2652, 9586.984542, 9586.984542,
				9586.984542, 9586.984542, 9586.984542, 9586.984542,
				9586.984542, 9586.984542, 9586.984542, 9586.984542,
				9586.984542, 9586.984542, 9586.984542, 9586.984542,
				9586.984542, 9586.984542, 9586.984542, 9586.984542,
				9586.984542, 9586.984542, 9586.984542, 9586.984542,
				9586.984542, 9586.984542, 9586.984542, 9586.984542,
				9586.984542, 9586.984542, 9586.984542, 9586.984542,
				9586.984542, 9586.984542, 9586.984542, 9586.984542,
				9586.984542, -4292.976783 };
		String[] dateStr = { "	2012-8-1	", "	2012-9-1	", "	2012-10-1	",
				"	2012-11-1	", "	2012-12-1	", "	2013-1-1	", "	2013-2-1	",
				"	2013-3-1	", "	2013-4-1	", "	2013-5-1	", "	2013-6-1	",
				"	2013-7-1	", "	2013-8-1	", "	2013-9-1	", "	2013-10-1	",
				"	2013-11-1	", "	2013-12-1	", "	2014-1-1	", "	2014-2-1	",
				"	2014-3-1	", "	2014-4-1	", "	2014-5-1	", "	2014-6-1	",
				"	2014-7-1	", "	2014-8-1	", "	2014-9-1	", "	2014-10-1	",
				"	2014-11-1	", "	2014-12-1	", "	2015-1-1	", "	2015-2-1	",
				"	2015-3-1	", "	2015-4-1	", "	2015-5-1	", "	2015-6-1	",
				"	2015-7-1	", "	2015-8-1	" };
		Date[] date = new Date[dateStr.length];
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < date.length; i++) {
			try {
				date[i] = sf.parse(dateStr[i]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		double Xirr = PaymentIRRUtil.getIRR(cash06, null, Double.NaN, 1);
		System.out.println("XIRR:" + Xirr);
		// double irr = PaymentIRRUtil.getIRR(cash05, Double.NaN);
		// System.out.println(irr);
		// System.out.println(irr * 100 * 12);
	}

}
