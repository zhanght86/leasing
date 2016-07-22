package com.pqsoft.customers.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.pqsoft.skyeye.Log;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.util.StringUtils;

import net.sf.json.JSONObject;

public class RZBService {
	private final String xmlPath = "customers.";
	
	/**
	 * 根据身份号验证身份证是否“验证通过”
	 * @return
	 */
	public Map<String,Object> isCheckCardNo(String ID_CARD_NO,String name){
		Map<String,Object> mRs=new HashMap<String, Object>();
		Map<String,String> mCheck=new HashMap<String,String>();
		mCheck.put("Account", "jylh_admin");   //账号：jylh_admin 
		mCheck.put("Pwd", "hdk1E84NCt");	  //密码：hdk1E84NCt
		mCheck.put("ID_CARD_NO", ID_CARD_NO);  //110101198010103365
		mCheck.put("Name", name);
		String rs="否"; //身份证未验证过
		List<Map<String,Object>> list =Dao.selectList(xmlPath+"queryIdCardCheck", mCheck);//根据身份证Id:ID_CARD_NO查询身份证是否存在
		if(StringUtils.isBlank(list)&&!list.isEmpty()&&list.size()>=1){
			for (Map<String,Object> m : list) {
				if("是".equals(m.get("IDCARD_CHECK"))){
					rs= "是";//身份证已验证过
				}
			}
		}else{
			//调用RZB认证接口
			String jsonStr=simpleCheck(mCheck);
			
			//解析接口返回数据
			JSONObject jsonObject = JSONObject.fromObject( jsonStr );
			String strResponseCode=jsonObject.get("ResponseCode").toString();
			String strIdentifier=jsonObject.get("Identifier").toString();
			JSONObject jsonObjectIdentifier = JSONObject.fromObject( strIdentifier );
			if("100".equals(strResponseCode)&&!strResponseCode.isEmpty()){
				String strResult=jsonObjectIdentifier.getString("Result").toString();
				if("一致".equals(strResult)&&!strResult.isEmpty()){
					rs="一致";
					Log.info("一致：身份证号和姓名相匹配");
				}else if("不一致".equals(strResult)&&!strResult.isEmpty()){
					rs="不一致";
					Log.info("不一致：身份证号和姓名不匹配");
				}else{
					rs="库中无此号";
					Log.info("库中无此号：身份证号码错误");
				}
			}else{
				Log.info("RZB认证接口调用不成功"+strResponseCode);
				throw new ActionException("RZB认证接口调用不成功"+strResponseCode);
			}
			
		}
		mRs.put("RS", rs);
		return mRs;
	}
	
	/**
	 * 验证CARD_ID与姓名是否一致
	 * @param map
	 * @return
	 */
	static String simpleCheck(Map<String,String>map){
		Map<String,String> mapParam=new HashMap<String,String>();
		mapParam.put("Account", map.get("Account"));
		mapParam.put("Pwd", map.get("Pwd"));
		mapParam.put("IDNumber", map.get("ID_CARD_NO"));
		mapParam.put("Name", map.get("Name"));
		mapParam.put("Sign", md5(md5(map.get("ID_CARD_NO") +  map.get("Account")) + map.get("Pwd")));
		String url;
		url = "https://service.sfxxrz.com/simpleCheck.ashx";
		String json=send(url,mapParam);
		return json;
	}
	
	static String md5(String text) {
		byte[] bts;
		try {
			bts = text.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bts_hash = md.digest(bts);
			StringBuffer buf = new StringBuffer();
			for (byte b : bts_hash) {
				buf.append(String.format("%02X", b & 0xff));
			}
			return buf.toString();
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		} catch (java.security.NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	static  String send(String url, Map<String, String> map) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String name = it.next();
			params.add(new BasicNameValuePair(name, map.get(name)));
		}
		try {
			httppost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			return EntityUtils.toString(entity, StandardCharsets.UTF_8);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
}
