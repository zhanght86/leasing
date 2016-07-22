package com.pqsoft.weixinfw.utils.weixin;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;

import com.pqsoft.skyeye.Log;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.util.StringUtils;
import com.pqsoft.weixinfw.service.AuthService;
import com.pqsoft.weixinfw.utils.APICodeUtils;
import com.pqsoft.weixinfw.utils.AbstractThreadJobBean;
import com.pqsoft.weixinfw.utils.PQsoftThreadUtils;

/**
 * 微信api专用
 * @author LUYANFENG @ 2015年8月11日
 */
public final class HTTPUtils {
	
	private static final int readTimeOut = 10 * 1000;
	private static final int connectTimeout = 10 * 1000;
	
	private static URL url;
	private static Map<String,Object> access_tokenMap = new ConcurrentHashMap<String,Object>();
	private static PQsoftThreadUtils pqThreadUtils = PQsoftThreadUtils.newInstance(20);
	
	private HTTPUtils() {	}
	
	/**
	 * 异步请求GET
	 * @param urlAddress
	 * @author LUYANFENG @ 2015年7月9日
	 */
	public static void doThreadGet(final String urlAddress){
		pqThreadUtils.addNewThreadJob(new AbstractThreadJobBean() {
			@Override
			public void autoRun() {
				String doGet = doGet(urlAddress);
				Log.debug("## 异步执行doGet："+doGet);
			}
		});
	}
	
	/**
	 * 异步请求POST
	 * @param urlAddress
	 * @param params
	 * @author LUYANFENG @ 2015年7月9日
	 */
	public static void doThreadPost(final String urlAddress, final Map<String,Object>... params){
		pqThreadUtils.addNewThreadJob(new AbstractThreadJobBean() {
			@Override
			public void autoRun() {
				String doPost = doPost(urlAddress , params);
				Log.debug("## 异步执行doPost："+doPost);
			}
		});
	}
	
	/**
	 * <pre>
	 * 异步请求POST json
	 * <pre>
	 * @see  doThreadJsonPost(final String urlAddress, final String jsonstr ,final IHandler handler)
	 * @param urlAddress 请求地址
	 * @param jsonstr	请求的参数JSON string.
	 * @author LUYANFENG @ 2015年8月14日
	 */
	public static void doThreadJsonPost(final String urlAddress, final String jsonstr){
		doThreadJsonPost(urlAddress, jsonstr, null);
	}
	
	/**
	 * 异步请求POST json
	 * @param urlAddress 请求地址
	 * @param jsonstr	请求的参数JSON string.
	 * @author LUYANFENG @ 2015年7月9日
	 */
	public static void doThreadJsonPost(final String urlAddress, final String jsonstr , final IHandler handler){
		pqThreadUtils.addNewThreadJob(new AbstractThreadJobBean() {
			@Override
			public void autoRun() {
				Log.debug("## 有新线程请求");
				String respJsonStr = doJsonPost(urlAddress , jsonstr);
				Log.debug("## 异步执行doJsonPost："+respJsonStr);
				if(handler != null){
					handler.doWhat(respJsonStr);
				}
			}
		});
	}

	
	// 向服务器发送get请求
	public static String doGet(String urlAddress) {
		try {
			Log.debug("url : "+urlAddress);
			url = new URL(urlAddress);
			HttpURLConnection uRLConnection = (HttpURLConnection) url.openConnection();
			uRLConnection.setRequestMethod("GET");
			uRLConnection.setReadTimeout( readTimeOut);
			uRLConnection.setConnectTimeout( connectTimeout);
			return getResponse(uRLConnection);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 向服务器发送post请求
	public static String doPost(String urlAddress, Map<String,Object>... params) {
		try {
			Log.debug("url : "+urlAddress);
			url = new URL(urlAddress);
			HttpURLConnection uRLConnection = (HttpURLConnection) url.openConnection();
			uRLConnection.setDoInput(true);
			uRLConnection.setDoOutput(true);
			uRLConnection.setRequestMethod("POST");
			uRLConnection.setUseCaches(false);
			uRLConnection.setInstanceFollowRedirects(false);
			uRLConnection.setReadTimeout( readTimeOut);
			uRLConnection.setConnectTimeout( connectTimeout);
			uRLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			uRLConnection.connect();

			DataOutputStream out = new DataOutputStream(uRLConnection.getOutputStream());
			final StringBuilder content = new StringBuilder();
			if(params != null && params.length > 0){
				for(Map<String,Object> map : params){
					for(Map.Entry<String, Object> m : map.entrySet()){
						content.append("&").append(m.getKey()).append("=").append(m.getValue());
					}
				}
			}
			out.write(content.toString().getBytes("utf-8") );
			out.flush();
			out.close();

			return getResponse(uRLConnection);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 向服务器发送post请求
	public static String doJsonPost(String urlAddress, String params) {
		try {
			Log.debug("url : "+urlAddress);
			url = new URL(urlAddress);
			HttpURLConnection uRLConnection = (HttpURLConnection) url.openConnection();
			uRLConnection.setDoInput(true);
			uRLConnection.setDoOutput(true);
			uRLConnection.setRequestMethod("POST");
			uRLConnection.setUseCaches(false);
			uRLConnection.setInstanceFollowRedirects(false);
			uRLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			uRLConnection.setReadTimeout( readTimeOut);
			uRLConnection.setConnectTimeout( connectTimeout);
			
			if(StringUtils.isNotBlank(params)){
				Log.debug("------------ params -----------");
				Log.debug(params);
				Log.debug("-------------------------------");
				Map<String, List<String>> requestProperties = uRLConnection.getRequestProperties();
				for(Map.Entry<?, ?> m :requestProperties.entrySet()){
					Log.debug(String.format("%1$20s : %2$s", m.getKey(),m.getValue()));
				}
				Log.debug(" http url :"+uRLConnection.getURL());
				
				uRLConnection.connect();
				
				DataOutputStream out = new DataOutputStream(uRLConnection.getOutputStream());
				out.write(params.getBytes("UTF-8") );
				out.close();
				
			}

			return getResponse(uRLConnection);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getResponse(HttpURLConnection uRLConnection) throws IOException {
		InputStream is = uRLConnection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
		String response = "";
		String readLine = null;
		while ((readLine = br.readLine()) != null) {
			response = response + readLine;
		}
		uRLConnection.disconnect();
		is.close();
		br.close();
		
		Map<String,Object> respJSON = JSONObject.fromObject(response );
		HTTPUtils.verifyAccess_token(respJSON);
		
		return response;
	}


	/**
	 * @param accessTokenUrl
	 * @author : LUYANFENG @ 2015年5月27日
	 * access_token的有效期目前为2个小时 。
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getAccess_token(final String accessTokenUrl ,boolean getNew) {
		final String jobName =  "t_申请新的access_token";
		synchronized(Map.class){
			if(getNew){
				access_tokenMap.clear();
				pqThreadUtils.shutdown(jobName);
			}
			
			if(!access_tokenMap.isEmpty()){
				Log.debug("access_token = "+ access_tokenMap.get("access_token"));
				return Collections.unmodifiableMap(access_tokenMap);
			}
			
			try {
				String text = doGet(accessTokenUrl);
				Map<String,Object> readValue = JSONObject.fromObject(text);
				Object expires_in = readValue.get("expires_in");
				if(expires_in == null){
					throw new ActionException("获取access_token 失败。"+ readValue.get("errmsg")+"("+ readValue.get("errcode") +")");
				}
				final long expires_in_long = Long.valueOf(expires_in.toString());
				Log.debug("-- 申请到新的token："+ readValue.get("access_token") );
				access_tokenMap.putAll(readValue);
				pqThreadUtils.addNewThreadJob(new AbstractThreadJobBean() {
					
					@Override
					public void autoRun() {
						String text = doGet(accessTokenUrl);
						Map<String,Object> readValue = JSONObject.fromObject(text);
						access_tokenMap.clear();
						access_tokenMap.putAll(readValue);
						Log.debug("-- 申请到新的token："+ access_tokenMap.get("access_token") );
					}

					@Override
					public long sleep() {
						return (expires_in_long - 300) * 1000;
					}

					@Override
					public String getJobName() {
						return jobName;
					}
					
				});
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			getNew = getNew? false : false;
			return Collections.unmodifiableMap(access_tokenMap);
		}
	}

	/**
	 * 确认access_token是否有效，否则获取新的
	 * @param respJSON
	 * @author LUYANFENG @ 2015年8月11日
	 */
	public static void verifyAccess_token(Map<String, Object> respJSON) {
		String errcode = respJSON.get("errcode")+"";
		if( !APICodeUtils.verifyAccess_token(errcode)){
			Log.debug("  主动申请access_token!!");
			AuthService.getAccess_token(true);
		}
	}
}
