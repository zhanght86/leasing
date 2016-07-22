package com.pqsoft.sms.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import org.springframework.util.FileCopyUtils;

import com.pqsoft.skyeye.api.Dao;

public class SmsService {
	
	String xmlPath = "sms.";//xml路径
	
	public Map<String,Object> getConfigDetail(){
		return Dao.selectOne(xmlPath + "getConfigDetail");
	}

	//返回值包含FLAG结果（true成功/false失败），STATE发送状态
	public Map<String,Object> doManualSendMessage(Map<String,Object> param){
		String result = sendSms(param);
		if(result == null || result.equals("")){
			param.put("FLAG", false);
			param.put("STATE", "发送失败");
			return param;
		}else{
			String[] resA = result.split(",");
			if(!result.equals("failure")){
				int res = Integer.parseInt(resA[0]);
				if(res > 0){
					param.put("FLAG", true);
				}else{
					param.put("FLAG", false);
				}
				param.put("STATE", switchSMSresult(resA));
			}else{
				param.put("FLAG", false);
				param.put("STATE", "发送失败");
			}
			return param;
		}
	}
	
	static Map<String,Object> urlMap = null;
	public SmsService(){
		if(urlMap == null){
			urlMap = Dao.selectOne("sms.getConfigDetail");
		}
	}
	
	/**
	 * 带返回黑字典发送方法 SendSms 支持群发
	 * @param
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2014-9-17  下午03:16:50
	 */
	public String sendSms(Map<String,Object> param){
		try{
			String phones = param.get("PHONE").toString().replace(" ", "").replace("，", ",");
			StringBuilder urlstr = new StringBuilder();
		    urlstr.append(urlMap.get("ADDRESS"));
		    urlstr.append("?Corpid="+urlMap.get("USERNAME"));
		    urlstr.append("&Pwd="+urlMap.get("PASSWORD"));
		    urlstr.append("&Phones="+phones);
//		    urlstr.append("&Phones=15010090847,15010282376");//可以群发
		    urlstr.append("&sContent=" + URLEncoder.encode(param.get("CONTENT").toString(), "GBK"));
		    urlstr.append("&ExtCode=");
		    urlstr.append("&SendTime=");
		    System.out.println(urlstr.toString());
		    URL url = new URL(urlstr.toString());
		    URLConnection conn = url.openConnection();
		    return FileCopyUtils.copyToString(new BufferedReader(new InputStreamReader(conn.getInputStream())));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "failure";
	}
	
	public final String switchSMSresult(String[] resA) {
		int result = Integer.parseInt(resA[0]);
		String backMean = "";
		if (result >= 0)
			backMean = "发送短信成功";
		else {
			switch (result) {
				case -1:
					backMean = "账号或密码错误";
					break;
				case -2:
					backMean = "系统错误";
					break;
				case -3:
					backMean = "账号未注册";
					break;
				case -5:
					backMean = "余额不足，请先充值";
					break;
				case -6:
					backMean = "定时发送时间不是有效的时间格式";
					break;
				case -7:
					backMean = "没有加签名";
					break;
				case -8:
					backMean = "发送内容需在1到500字之间";
					break;
				case -9:	
					backMean = "发送号码为空";
					break;
				case -10:
					backMean = "定时时间不能小于当前系统时间";
					break;
				case -11:
					backMean = "其他错误";
					break;
				case -12:
//				try {
//					backMean =  new String(resA[1].getBytes("UTF-8"), "ISO8859-1");
//				} catch (UnsupportedEncodingException e) {
					backMean = "短信内容存在黑词";
//				}
					break;
				case -100:
					backMean = "限制此IP访问";
					break;
				case -101:
					backMean = "调用接口速度太快";
			}
		}
		return backMean;
	}
	
	public int updateSms(Map<String,Object> param){
		return Dao.update("sms.updateSms",param);
	}

}
