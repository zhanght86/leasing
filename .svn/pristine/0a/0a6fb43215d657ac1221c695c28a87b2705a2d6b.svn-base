package com.pqsoft.sms.service;

import java.util.List;
import java.util.Map;

import jsp.weixin.ParamesAPI.util.ParamesAPI;
import jsp.weixin.ParamesAPI.util.WeixinUtil;
import jsp.weixin.msg.Util.SMessage;
import net.sf.json.JSONObject;

import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.Log;

public class WeixinService {

	public static String getAppId(String code) {
		if (code == null) return null;
		List<Map<String, Object>> list = (List<Map<String, Object>>) SysDictionaryMemcached.getList("企业微信平台");
		for (Map<String, Object> map : list) {
			if (code.equals(map.get("FLAG"))) { return (String) map.get("CODE"); }
		}
		return null;
	}

	/**
	 * 
	 * @param userCode 系统账号
	 * @param msg 
	 * @param program 如 app5, 系统设置中有
	 */
	public static void sendTextMsg(String userCode, String msg, String program) {
		String PostData = SMessage.STextMsg(userCode, "", "", getAppId(program), msg);
		JSONObject result = WeixinUtil.PostMessage(ParamesAPI.access_token, "POST", SMessage.POST_URL, PostData);
		System.out.println(result);
	}

	/**
	 * 客服
	 * 
	 * @param  AgentID :应用id 要对应微信平台上设置的
	 * @author : LUYANFENG @ 2015年6月8日
	 */
	public String FKres4Text(Map<String, String> m) {
		String AgentID = m.get("AgentID");
		String agentID = "7";
		if(agentID.equals(AgentID)){ // 客服
			//TODO 查出数据
			String msg = "测试回复你。"; 
			String PostData = SMessage.STextMsg(m.get("FromUserName"), "", "", getAppId("app4" ), msg);
			JSONObject result = WeixinUtil.PostMessage(ParamesAPI.access_token, "POST", SMessage.POST_URL, PostData);
			Log.debug(result);
		}
		return agentID;
	}
	
	

}
