package com.pqsoft.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentType {

	public static final String MONTH = "租金";
	public static final String MONTHTYPE = "monthPrice";
	public static final String MONTHPMT = "PMT租金";
	public static final String MONTHPMTTYPE = "monthPricePMT";
	public static final String OWN = "本金";
	public static final String OWNTYPE = "ownPrice";
	public static final String REN = "利息";
	public static final String RENTYPE = "renPrice";
	public static final String LASTOWN = "剩余本金";
	public static final String LASTOWNTYPE = "lastPrice";
	
	
	public List<Map<String,Object>> CreatePaymentType(){
		List<Map<String,Object>> paymentList = new ArrayList<Map<String,Object>>();
		Map<String,Object> mapMonth =null;
		Map<String,Object> mapMonthPMT =null;
		Map<String,Object> mapOwn =null;
		Map<String,Object> mapRen =null;
		Map<String,Object> mapLast =null;
		mapMonth = new HashMap<String,Object>();
		mapMonth.put("TYPE",MONTH);
		mapMonth.put("PAYTYPE",MONTHTYPE);
		paymentList.add(mapMonth);
		mapOwn = new HashMap<String,Object>();
		mapOwn.put("TYPE",OWN);
		mapOwn.put("PAYTYPE",OWNTYPE);
		paymentList.add(mapOwn);
		mapRen = new HashMap<String,Object>();
		mapRen.put("TYPE",REN);
		mapRen.put("PAYTYPE",RENTYPE);
		paymentList.add(mapRen);
		mapLast = new HashMap<String,Object>();
		mapLast.put("TYPE",LASTOWN);
		mapLast.put("PAYTYPE",LASTOWNTYPE);
		paymentList.add(mapLast);
		mapMonthPMT = new HashMap<String,Object>();
		mapMonthPMT.put("TYPE",MONTHPMT);
		mapMonthPMT.put("PAYTYPE",MONTHPMTTYPE);
		paymentList.add(mapMonthPMT);
		return paymentList;
	}
}
