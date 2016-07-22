package com.pqsoft.wx.base.tools.msg;

import org.apache.log4j.Logger;

import com.pqsoft.wx.base.tools.WeixinConfig;
import jsp.weixin.ParamesAPI.util.WeixinUtil;
import net.sf.json.JSONObject;
/**
 * 主动发送消息
 * @author liangds
 *
 */
public class SendMsg {
	static Logger logger=Logger.getLogger(SendMsg.class);
	private static String SEND_URL="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";
	/**
	 *  发送text文本 给指定用户
	 *  多个用户可以用|隔开 如admin|csfl04
	 *  @all 全员发送
	 * @param agentId
	 * @param text
	 */
	public static void sendTxtToUser(String usercode,String agentId,String text){
		TextMsg msg=new TextMsg(usercode, "", "", "text", agentId,text);
		String json=JSONObject.fromObject(msg).toString();
		logger.info("post json>>:"+json);
		logger.info("result>>:"+WeixinUtil.PostMessage(WeixinConfig.getAccessToken(agentId), "POST", SEND_URL, json).toString());
	}
	/**
	 * 发送text文本 给指定部门
	 * 多个部门可以用|隔开 如 4|1
	 * @param toparty
	 * @param agentId
	 * @param text
	 */
	public static void sendTxtToparty(String toparty,String agentId,String text){
		TextMsg msg=new TextMsg("", toparty, "", "text", agentId,text);
		String json=JSONObject.fromObject(msg).toString();
		logger.info("post json>>:"+json);
		logger.info("result>>:"+WeixinUtil.PostMessage(WeixinConfig.getAccessToken(agentId), "POST", SEND_URL, json).toString());
	}
	/**
	 * 发送text文本 给指定标签
	 * @param totag
	 * @param agentId
	 * @param text
	 */
	public static void sendTxtTotag(String totag,String agentId,String text){
		TextMsg msg=new TextMsg("", "", totag, "text", agentId,text);
		String json=JSONObject.fromObject(msg).toString();
		logger.info("post json>>:"+json);
		logger.info("result>>:"+WeixinUtil.PostMessage(WeixinConfig.getAccessToken(agentId), "POST", SEND_URL, json).toString());
	
	}
}
