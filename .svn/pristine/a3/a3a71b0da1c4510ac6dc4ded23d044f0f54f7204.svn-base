package com.pqsoft.base.interfaceTemplate.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.pqsoft.bpm.service.SystemMail;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.sms.service.SmsService;
import com.pqsoft.sms.service.WeixinService;
import com.pqsoft.util.StringUtils;

public class RunInterfaceTemplateThread extends Thread {  

	String xmlPath;// xml路径
	String NODE_NAME;
	Map<String,Object> param;
	String TYPE;
	
	public void run(){
		try {
			List<Map<String, Object>> itListMap = Dao.selectList(xmlPath + "getNodeTemplate", NODE_NAME);
			for (int i = 0; i < itListMap.size(); i++) {// 循环对象
				Map<String, Object> itMap = itListMap.get(i);
				String content = itMap.get("CONTENT").toString();
				List<String> contentReplaceStart = new ArrayList<String>();
				List<String> contentReplaceEnd = new ArrayList<String>();
				int index = 0;
				String subStr = "【";
				for (int j = 0; (index = content.indexOf(subStr, index)) != -1; j++) {
					contentReplaceStart.add(Integer.toString(index));
					index += subStr.length();
				}
				index = 0;
				subStr = "】";
				for (int j = 0; (index = content.indexOf(subStr, index)) != -1; j++) {
					contentReplaceEnd.add(Integer.toString(index));
					index += subStr.length();
				}
				String newContent = content;
				for (int j = 0; j < contentReplaceStart.size(); j++) {
					int start = Integer.parseInt(contentReplaceStart.get(j)) + 1;
					int end = Integer.parseInt(contentReplaceEnd.get(j));
					String SQL_NAME = content.substring(start, end);
					if(SQL_NAME.equals("通知"))
					{
						continue; 
					}
					String SQL = Dao.selectOne(xmlPath + "getSQL", SQL_NAME);
					for (Entry<String, Object> newParam : param.entrySet()) {
						SQL = SQL.replace("#{" + newParam.getKey() + "}", "'" + newParam.getValue().toString() + "'");
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("SQL", SQL);
						if (SQL.indexOf("#{") == -1) {
							String sqlValue = Dao.selectOne(xmlPath + "getSqlValueS", map);
							if (StringUtils.isBlank(sqlValue)) {
								sqlValue = "";
							}
							newContent = newContent.replace("【" + SQL_NAME + "】", sqlValue);
						}
					}
				}
				String SQL_ID = itMap.get("SQL_ID").toString();
				String SQL_TEXT = Dao.selectOne(xmlPath + "getSqlForID", SQL_ID);
				for(Entry<String, Object> newParam: param.entrySet()){
					if(newParam.getKey().equals("CODE")){
					SQL_TEXT = SQL_TEXT.replace("#{"+newParam.getKey()+"}", newParam.getValue().toString());
					}
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("SQL_TEXT", SQL_TEXT);
				List<Map<String, Object>> dxList = Dao.selectList(xmlPath + "getSqlValue", map);
				for (int j = 0; j < dxList.size(); j++) {
					Map<String, Object> dxMap = dxList.get(j);
					if(TYPE != null){
						if(TYPE.equals("EMAIL")){
							try {
								String address = dxMap.get("邮件地址").toString();
//								address = "438473135@qq.com";
								String title = dxMap.get("邮件标题").toString();
								SystemMail.sendMail(address, title, newContent);
								Map<String,Object> mapLog = new HashMap<String,Object>();
								mapLog.put("ACCOUNT_NUMBER", address);
								mapLog.put("CONTENT", newContent);
								mapLog.put("TYPE", "邮件");
								Dao.insert(xmlPath + "addLog", mapLog);
							} catch (Exception e) {
								e.printStackTrace();
								Dao.rollback();
							}
						}
						if(TYPE.equals("WX")){
							try {
								System.out.println("微信企业号");
								String wxq = dxMap.get("微信企业号").toString();
								WeixinService.sendTextMsg(wxq, newContent, "app5");
								Map<String,Object> mapLog = new HashMap<String,Object>();
								mapLog.put("ACCOUNT_NUMBER", wxq);
								mapLog.put("CONTENT", newContent);
								mapLog.put("TYPE", "微信");
								Dao.insert(xmlPath + "addLog", mapLog);
							} catch (Exception e) {
								e.printStackTrace();
								Dao.rollback();
							}
						}
						if(TYPE.equals("SMS")){
							try {
								String phone = dxMap.get("电话").toString();
								Map<String, Object> param1 = new HashMap<String, Object>();
								param1.put("PHONE", phone);
								param1.put("CONTENT", newContent);
								new SmsService().sendSms(param1);
								Map<String,Object> mapLog = new HashMap<String,Object>();
								mapLog.put("ACCOUNT_NUMBER", phone);
								mapLog.put("CONTENT", newContent);
								mapLog.put("TYPE", "短信");
								Dao.insert(xmlPath + "addLog", mapLog);
							} catch (Exception e) {
								e.printStackTrace();
								Dao.rollback();
							}
						}
					}else{
						if (itMap.get("SMS").equals("1")) {
							// System.out.println("#########################11111111111111111111111"+dxMap.get("电话"));
							try {
								String phone = dxMap.get("电话").toString();
								Map<String, Object> param1 = new HashMap<String, Object>();
								param1.put("PHONE", phone);
								param1.put("CONTENT", newContent);
								new SmsService().sendSms(param1);
								Map<String,Object> mapLog = new HashMap<String,Object>();
								mapLog.put("ACCOUNT_NUMBER", phone);
								mapLog.put("CONTENT", newContent);
								mapLog.put("TYPE", "短信");
								Dao.insert(xmlPath + "addLog", mapLog);
							} catch (Exception e) {
								e.printStackTrace();
								Dao.rollback();
							}
						}
						if (itMap.get("WXQ").equals("1")) {
							// System.out.println("#########################22222222222222222222222222"+dxMap.get("微信企业号"));
							try {
								String wxq = dxMap.get("微信企业号").toString();
								WeixinService.sendTextMsg(wxq, newContent, "app5");
								Map<String,Object> mapLog = new HashMap<String,Object>();
								mapLog.put("ACCOUNT_NUMBER", wxq);
								mapLog.put("CONTENT", newContent);
								mapLog.put("TYPE", "微信");
								Dao.insert(xmlPath + "addLog", mapLog);
							} catch (Exception e) {
								e.printStackTrace();
								Dao.rollback();
							}
						}
						if (itMap.get("WXF").equals("1")) {
							try {
								// String wxq = dxMap.get("微信服务号").toString();
								// WeixinService.sendTextMsg(wxq, newContent,
								// "app5");
							} catch (Exception e) {
								e.printStackTrace();
								Dao.rollback();
							}
						}
						if (itMap.get("EMAIL").equals("1")) {
							try {
								String address = dxMap.get("邮件地址").toString();
								String title = dxMap.get("邮件标题").toString();
								SystemMail.sendMail(address, title, newContent);
								Map<String,Object> mapLog = new HashMap<String,Object>();
								mapLog.put("ACCOUNT_NUMBER", address);
								mapLog.put("CONTENT", newContent);
								mapLog.put("TYPE", "邮件");
								Dao.insert(xmlPath + "addLog", mapLog);
							} catch (Exception e) {
								e.printStackTrace();
								Dao.rollback();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Dao.rollback();
		}
		Dao.commit();
		Dao.close();
	}

}
