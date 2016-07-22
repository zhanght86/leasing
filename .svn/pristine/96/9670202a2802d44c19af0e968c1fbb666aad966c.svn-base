package com.pqsoft.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

import com.google.common.base.Strings;
import com.pqsoft.skyeye.exception.ActionException;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @ClassName: StringUtils
 * @Description: TODO(字符串处理工具类)
 * @author 程龙达
 * @date 2013-9-5 上午10:46:07
 */
public class StringUtils {

	public static double parseDecimal(String numberStr, int scale) throws Exception {
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setRoundingMode(RoundingMode.HALF_UP);
		nf.setMaximumFractionDigits(scale);
		return new Double(decimal(new Double(numberStr).doubleValue(), 2)).doubleValue();
	}

	public static String decimal(double number, int scale) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(scale);
		nf.setRoundingMode(RoundingMode.HALF_UP);
		return nf.format(number).replaceAll(",", "");
	}

	public static String nullToString(Object str) {
		if (str == null) return "";
		return str.toString().trim();
	}

	public static String nullToOther(Object str, Object other) {
		if (isBlank(str)) return nullToString(other);
		return nullToString(str);
	}

	public static String notBankToOther(Object str, Object other) {
		if (isNotBlank(str)) return nullToString(other);
		return nullToString(str);
	}

	public static boolean isNull(Object o) {
		if (o == null) { return true; }
		return false;
	}

	public static boolean isNotNull(Object o) {
		return !isNull(o);
	}
	
	public static boolean isBlank(Object o){
		if(o == null || "".equals(o.toString().trim())||"null".equals(o)||"undefined".equals(o)){
			return true;
		}
		return false;
	}

	public static boolean isNotBlank(Object o) {
		return !isBlank(o);
	}

	public static String formatNumberDoubleTwo(String str) {
		try {
			String temp_num = str;
			if ((temp_num == null) || (temp_num.equals(""))) {
				temp_num = "0.00";
			} else {
				java.text.DecimalFormat ft = new java.text.DecimalFormat("###0.00");
				// java.text.DecimalFormat ft = new
				// java.text.DecimalFormat(style);
				BigDecimal bd = new BigDecimal(temp_num);
				temp_num = ft.format(bd);

			}
			return temp_num;
		} catch (Exception e) {}
		return "";
	}

	public static String formatNumberDoubleTwo(double str) {
		try {
			String temp_num = String.valueOf(str);
			if ((temp_num == null) || (temp_num.equals(""))) {
				temp_num = "0.00";
			} else {
				java.text.DecimalFormat ft = new java.text.DecimalFormat("###0.00");
				// java.text.DecimalFormat ft = new
				// java.text.DecimalFormat(style);
				BigDecimal bd = new BigDecimal(temp_num);
				temp_num = ft.format(bd);
			}
			return temp_num;
		} catch (Exception e) {}
		return "";
	}
	
	
	/**
	 * 取串中所有汉字的全拼
	 * @return
	 */
	public static String getHanYuPingYin(String str , boolean isUpper){
		HanyuPinyinOutputFormat hy = new HanyuPinyinOutputFormat();
		hy.setCaseType(isUpper? HanyuPinyinCaseType.UPPERCASE :HanyuPinyinCaseType.LOWERCASE);
		hy.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		char[] charArray = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i <charArray.length ;  i ++){
				try {
					String[] hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(charArray[i], hy);
					if(hanyuPinyinStringArray!= null){
						sb.append(hanyuPinyinStringArray[0]);
					}else{
						sb.append(charArray[i]);
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			
		}
		return sb.toString();
	}
	/**
	 * 取串首字母
	 * @param str
	 * @return
	 */
	public static char getHanYuFirstPingYin(String str, boolean isUpper){
		return  getHanYuPingYin(str , isUpper).charAt(0);
	}

	/**
	 * 驼峰格式转换全大写下划线
	 *
	 * @author 钟林俊
	 * @param camelStr 驼峰格式字符串
	 * @return 下划线格式字符串
     */
	public static String camelToUnderLine(String camelStr){
		String src = camelStr;
		StringBuilder sb = new StringBuilder();
		for(int i = 1, j = 0; i < camelStr.length(); i++){
			Character c = camelStr.charAt(i);
			if(Character.isUpperCase(c)){
				sb.append(camelStr.substring(j, i).toUpperCase()).append("_");
				j = i;
				src = camelStr.substring(j);
			}
		}
		sb.append(src.toUpperCase());
		return sb.toString();
	}

	/**
	 * 下划线转驼峰
	 *
	 * @param str 需要转换的字符串
	 * @param isBig 是否大驼峰的标记位
     * @return 驼峰格式的字符串
     */
	public static String underlineToCamel(String str, boolean isBig){
		if(Strings.isNullOrEmpty(str)){
			throw new ActionException("空字符串！");
		}

		String[] strings = str.split("_");
		StringBuilder builder = new StringBuilder(str.length());

		if(isBig){
			builder.append(org.apache.commons.lang.StringUtils.capitalize(strings[0].toLowerCase()));
		}else{
			builder.append(strings[0].toLowerCase());
		}

		if(strings.length > 1){
			for(int i = 1; i < strings.length; i++){
				builder.append(org.apache.commons.lang.StringUtils.capitalize(strings[i].toLowerCase()));
			}
		}

		return builder.toString();
	}
	
}
