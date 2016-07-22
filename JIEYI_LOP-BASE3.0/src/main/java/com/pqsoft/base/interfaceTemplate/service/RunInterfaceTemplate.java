package com.pqsoft.base.interfaceTemplate.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.pqsoft.bpm.service.SystemMail;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.sms.service.SmsService;
import com.pqsoft.sms.service.WeixinService;

public class RunInterfaceTemplate {  

	String xmlPath = "runInterfaceTemplate.";// xml路径
	
	public void sendJbpmMessage(String NODE_NAME, String PROJECT_ID){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("PROJECT_ID", PROJECT_ID);
		sendJbpmMessage(NODE_NAME, param,null);
	}
	
	// 流程节点类
	public void sendJbpmMessage(String NODE_NAME, Map<String,Object> param,String TYPE) {
		RunInterfaceTemplateThread  run = new RunInterfaceTemplateThread(); 
		run.NODE_NAME = NODE_NAME;
		run.param = param;
		run.TYPE = TYPE;
		run.xmlPath = xmlPath;
		run.start();
	}

	// 定时批量类
	public void runTimingBatch() {
		List<Map<String, Object>> itListMap = Dao.selectList(xmlPath + "getTimingBatch");
		if (itListMap != null && itListMap.size() > 0) {
			for (int i = 0; i < itListMap.size(); i++) {
				try {
					Map<String, Object> itMap = itListMap.get(i);
					if (itMap.get("SEND_TIME").toString().trim().equals("****-**-****:**")) {
						send(itMap);
					} else {
						String[] SEND_TIME = itMap.get("SEND_TIME").toString().split(" ");
						String[] SEND_TIME1 = SEND_TIME[0].split("-");
						String[] SEND_TIME2 = SEND_TIME[1].split(":");

						Calendar c = Calendar.getInstance();
						int year = c.get(Calendar.YEAR);
						int month = c.get(Calendar.MONTH) + 1;
						int date = c.get(Calendar.DATE);
						int hour = c.get(Calendar.HOUR_OF_DAY);
						int minute = c.get(Calendar.MINUTE);

						String year1 = SEND_TIME1[0].toString().trim();
						String month1 = SEND_TIME1[1].toString().trim();
						String date1 = SEND_TIME1[2].toString().trim();
						String hour1 = SEND_TIME2[0].toString().trim();
						String minute1 = SEND_TIME2[1].toString().trim();

						boolean flag = false;
						boolean flag1 = true;
						if (!year1.equals("****")) {
							flag = year == Integer.parseInt(year1);
							if (!flag) {
								flag1 = false;
							}
						}
						if (!month1.equals("**")) {
							flag = month == Integer.parseInt(month1);
							if (!flag) {
								flag1 = false;
							}
						}
						if (!date1.equals("**")) {
							flag = date == Integer.parseInt(date1);
							if (!flag) {
								flag1 = false;
							}
						}
						if (!hour1.equals("**")) {
							flag = hour == Integer.parseInt(hour1);
							if (!flag) {
								flag1 = false;
							}
						}
						if (!minute1.equals("**")) {
							flag = minute == Integer.parseInt(minute1);
							if (!flag) {
								flag1 = false;
							}
						}
						if (flag1) {
							send(itMap);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void send(Map<String, Object> itMap) {
		String sqlNote = itMap.get("SQL_NOTE").toString();
		String content1 = itMap.get("CONTENT").toString();
		sqlNote = sqlNote.substring(1, sqlNote.length() - 1).trim();
		String[] sqlNA = sqlNote.split("】【");
		List<Map<String, Object>> sqlValue = Dao.selectList(xmlPath + "getSqlValue", itMap);
		if (sqlValue != null && sqlValue.size() > 0) {
			try {
				for (int j = 0; j < sqlValue.size(); j++) {
					String content = content1;
					Map<String, Object> valueMap = sqlValue.get(j);
					for (int k = 0; k < sqlNA.length; k++) {
						String value = valueMap.get(sqlNA[k].toString()).toString();
						content = content.replace("【" + sqlNA[k] + "】", value);
					}
					if (itMap.get("SMS").equals("1")) {
						try {
							String phone = valueMap.get("电话").toString();
							Map<String, Object> param = new HashMap<String, Object>();
							param.put("PHONE", phone);
							param.put("CONTENT", content);
							new SmsService().sendSms(param);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (itMap.get("WXQ").equals("1")) {
						try {
							String wxq = valueMap.get("微信企业号").toString();
							WeixinService.sendTextMsg(wxq, content, "app5");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (itMap.get("WXF").equals("1")) {
//						try {
//							String wxq = valueMap.get("微信服务号").toString();
//							WeixinService.sendTextMsg(wxq, content, "app5");
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
					}
					if (itMap.get("EMAIL").equals("1")) {
						try {
							String address = valueMap.get("邮件地址").toString();
							String title = valueMap.get("邮件标题").toString();
							SystemMail.sendMail(address, title, content);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
