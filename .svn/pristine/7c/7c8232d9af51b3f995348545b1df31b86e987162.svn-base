package com.pqsoft.leeds.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class StringUtil extends StringUtils{
	/**
	 * @desc
	 * 1、o为null，判为空<br/>
	 *	2、若为字符串，trim后，为空串，判为空
	 * @author leeds
	 * @time 2015年1月15日-下午3:37:06
	 */
	public static boolean isBlank(Object o) {
		if(o == null) return true;
		if(o instanceof String) {
			String str = (String)o;
			if(str.trim().equals("")) return true;
		}
		return false;
	}
	
	public static boolean isNotBlank(Object o) {
		if(!isBlank(o)) return true;
		return false;
	}
	/**
	 * @desc
	 * 从字符串input中，找出所有匹配正则表达式regex的子串，并以String[]形式返回
	 *
	 * @author leeds
	 * @time 2015年1月31日-上午1:07:34
	 */
	public static String[] matchedSubStrs(String regex, String input) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		List<String> results = new ArrayList<String>();
		while(m.find()) {
			results.add(m.group());
		}
		return StringUtil.toStringArray(results);
	}
}
