//package com.pqsoft.util;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
///**
// * 数字工具类
// * 
// * @author King 2013-10-13
// */
//public class UtilNumber {
//
//	public static void main(String[] args) {
//		System.out.println(UtilNumber.digitUppercase(571300));
//	}
//
//	/**
//	 * 数字转大写
//	 * 
//	 * @param n
//	 * @return
//	 * @author:King 2012-3-13
//	 */
//	public static String digitUppercase(double n) {
//		String fraction[] = { "角", "分" };
//		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
//		String unit[][] = { { "元", "万", "亿" }, { "", "拾", "佰", "仟" } };
//
//		String head = n < 0 ? "负" : "";
//		n = Math.abs(n);
//
//		String s = "";
//		for (int i = 0; i < fraction.length; i++) {
//			s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i])
//					.replaceAll("(零.)+", "");
//		}
//		if (s.length() < 1) {
//			s = "整";
//		}
//		int integerPart = (int) Math.floor(n);
//
//		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
//			String p = "";
//			for (int j = 0; j < unit[1].length && n > 0; j++) {
//				p = digit[integerPart % 10] + unit[1][j] + p;
//				integerPart = integerPart / 10;
//			}
//			s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i]
//					+ s;
//		}
//		return head
//				+ s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "")
//						.replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
//	}
//
//	/**
//	 * 小写转换为大写 参数名将变为：将原小写数字的参数名转为大写，然后再其后面添加"_D"
//	 * 
//	 * @param map
//	 * @return
//	 * @author:King 2012-4-6
//	 */
//	public static HashMap<String,Object> numToUpCase(Map<String, Object> map) {
//		Set<String> set = map.keySet();
//		HashMap<String,Object> mm=new HashMap<String,Object>();
//		for (String key : set) {
//			if (key == null || key.length() <= 0) {
//				continue;
//			} else {
//				String str = null == map.get(key) ? "" : map.get(key)
//						.toString();
//				str = str.trim();
//				if (isDouble(str)) {
//					String st = new BigDecimal(str).toPlainString();
//					double str_d = Double.parseDouble(str);
//					String rst = UtilNumber.digitUppercase(str_d);
//					mm.put(key, st);
//					mm.put(key.toUpperCase() + "_D", rst);
//					mm.put(key.toUpperCase()+"大写值", rst);
//				}else{
//					mm.put(key, str);
//				}
//			}
//		}
//		return mm;
//	}
//
//	/**
//	 * 判断是否是double类型数字
//	 * @param str
//	 * @return
//	 * @author:King 2013-10-25
//	 */
//	private static boolean isDouble(String str) {
//		try {
//			Double.parseDouble(str);
//			return true;
//		} catch (NumberFormatException ex) {
//			return false;
//		}
//
//	}
//
//
//	/**
//	 * 判断是一个字符串是否是数字
//	 * 
//	 * @param str
//	 * @return
//	 * @author:King 2013-10-13
//	 */
//	public static boolean isNum(String str) {
//		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
//	}
//	
//	/**
//	 * 判断是一个字符串是否是数字
//	 * 
//	 * @param str
//	 * @return
//	 * @author:King 2014-3-31
//	 */
//	public double getDoubled(String doubleStr){
//		try{
//			return Double.parseDouble(doubleStr);
//		}catch(Exception e){
//			return 0;
//		}
//	}
//}
