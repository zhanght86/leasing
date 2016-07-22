package com.pqsoft.util;

import java.text.NumberFormat;
import java.util.Locale;

import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.generic.NumberTool;

@DefaultKey("number")
public class MyNumberTool extends NumberTool {

	
	public String currency(Object obj, int minimumFractionDigits) {
		return format("currency", obj ,minimumFractionDigits ,null, getLocale());
	}

	public String number(Object obj ,int minimumFractionDigits) {
		return format("number", obj , minimumFractionDigits,null, getLocale());
	}
	public String number(Object obj ,int minimumFractionDigits , int maximumFractionDigits) {
		return format("number", obj , minimumFractionDigits,maximumFractionDigits, getLocale());
	}

	public String percent(Object obj ,int minimumFractionDigits) {
		return format("percent", obj,minimumFractionDigits, null,getLocale());
	}
	
	
	public String getCurrency(Object obj ,Object dividend,int minimumFractionDigits) {
		Double newVal = Double.valueOf(obj.toString()) / Double.valueOf(dividend.toString());
		return format("currency", newVal,minimumFractionDigits, null,getLocale());
	}
	
	
	
	
	
	
	
	
	
	public String format(String format, Object obj,Integer minimumFraction,Integer maximumFraction, Locale locale) {
		Number number = toNumber(obj);
		NumberFormat nf = getNumberFormat(format, locale);
		if(minimumFraction != null ) nf.setMinimumFractionDigits(minimumFraction);
		if(maximumFraction != null ) nf.setMaximumFractionDigits(maximumFraction);
		
		if ((number == null) || (nf == null)) {
			return null;
		}
		return nf.format(number);
	}
}
