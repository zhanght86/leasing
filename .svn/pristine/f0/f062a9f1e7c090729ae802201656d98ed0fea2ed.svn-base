package com.pqsoft.call.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.pqsoft.dataDictionary.action.DataDictionaryAction;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.rbac.api.Security;

public class CallUtil {
	
	private static Logger logger = Logger.getLogger(DataDictionaryAction.class);
	
	/**
	 * 将传进来的参数进行md5加密
	 * @param
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-10-11  下午03:22:06
	 */
	public static String getPassWord(String oldPassWord){
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(oldPassWord.getBytes(), 0, oldPassWord.length());
			BigInteger bi = new BigInteger(1, md.digest());
			return bi.toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取呼叫专员信息
	 */
	public static Map<String,Object> getCallInformationCommissioner(Map<String,Object> param){
		return Dao.selectOne("callPersonnelConfiguration.selectPersonnelConfigurationDetails1", param);
	}

	/**
	 * 向指定手机号拨打电话
	 * 
	 * @param mobile
	 * @param message
	 * @return
	 */
	public static String callOut(String customerNumber) {
		URL url;
		URLConnection conn;
		InputStream input =null;
		StringBuffer hc = new StringBuffer();
		try {
			//获取当前登录人编号
			String userCode = Security.getUser().getCode();
			//获取呼叫专员配置信息
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("USER_CODE", userCode);
			Map<String,Object> map = CallUtil.getCallInformationCommissioner(param);
			if(map == null || map.isEmpty() || map.size() <= 0){
				return "您不属于呼叫专员！";
			}
			String password = "";//呼叫专员登录密码
			if(map.containsKey("USER_PASSWORD")){
				password = map.get("USER_PASSWORD").toString();
			}
			password = getPassWord(password);
			String cno = "";//呼叫专员坐席号
			if(map.containsKey("SEAT_NUMBER")){
				cno = map.get("SEAT_NUMBER").toString();
			}
			url = new URL("http://www2.pbx010.com/interface/PreviewOutcall?enterpriseId=2100077&hotline=&cno="+cno+"&pwd="+password+"&customerNumber="+customerNumber);
			conn = url.openConnection();
			input = conn.getInputStream();
			BufferedReader b = new BufferedReader(new InputStreamReader(input,"gb2312"));
			String li = null;
			while ((li = b.readLine()) != null) {
				hc.append(li);
			}
			logger.info("呼出电话返回值："+hc);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
				try {
					if(input!=null) input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		JSONObject json = JSONObject.fromObject(hc.toString());
		int result = Integer.parseInt(json.get("res").toString());
		switch(result) { 
			 case 0: return "座席已接听";
			 case 1: return "呼叫座席失败";
			 case 2: return "参数不正确";
			 case 3: return "用户验证没有通过";
			 case 4: return "账号被停用";
			 case 5: return "资费不足";
			 case 6: return "指定的业务尚未开通";
			 case 7: return "电话号码不正确";
			 case 8: return "座席工号（cno）不存在";
			 case 9: return "座席状态不为空闲，可能未登录或忙";
			case 10: return "其他错误";
			case 11: return "电话号码为黑名单";
			case 12: return "座席不在线";
			case 13: return "座席正在通话/呼叫中";
		} 
		return "呼叫失败";
	}
	
}
