package com.pqsoft.weixinfw.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.Log;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.util.StringUtils;
import com.pqsoft.weixinfw.dao.AccountDao;
import com.pqsoft.weixinfw.dao.BusinessDao;
import com.pqsoft.weixinfw.utils.IMethodReturn;
import com.pqsoft.weixinfw.utils.weixin.AesException;
import com.pqsoft.weixinfw.utils.weixin.HTTPUtils;
import com.pqsoft.weixinfw.utils.weixin.INewsDataHandler;
import com.pqsoft.weixinfw.utils.weixin.SentenceAnalyze;
import com.pqsoft.weixinfw.utils.weixin.SentenceAnalyze.Opt_Finally_Type;
import com.pqsoft.weixinfw.utils.weixin.WeiXinMsgCryptUtils;
import com.pqsoft.weixinfw.utils.weixin.WeiXinMsgCryptUtils.MsgType;
import com.pqsoft.weixinfw.utils.weixin.bean.NewsBean;


/**
 * 订阅号，未
 * 接收 发送 微信的信息
 * @author LUYANFENG @ 2015年5月20日
 *
 */
public class IOMessageService {
	
	private KeFuService kfServ = new KeFuService();
	private BusinessDao bizDao = new BusinessDao();
	private AccountDao accDao = new AccountDao();
	
	
	
	/**
	 * 接收image信息
	 * @author LUYANFENG @ 2015年5月20日
	 */
	private String getImage(){
		//TODO 
		return "";
	}
	
	/**
	 * 接收text信息
	 * @author LUYANFENG @ 2015年5月20日
	 * @param breader 
	 */
	private String getText(Map<String,Object> respMap){
		return respMap.get("Content").toString();
	}
	
	/**
	 * 接收 click | view
	 * 
	 * @author LUYANFENG @ 2015年5月20日
	 * @param breader 
	 */
	private String getClickEvent(Map<String,Object> respMap){
		String result = "";
		String eventType = respMap.get("Event").toString();
		if(eventType.equalsIgnoreCase("VIEW")){
//			String openid = respMap.get("FromUserName").toString();
//			String json4News = this.kfServ.getJSON4Text(openid, "跳转到页面："+respMap.get("EventKey").toString() );
//			HTTPUtils.doThreadJsonPost(this.send_URL, json4News);
//			this.authService.getCurrentUser(true);
		}else{
			result = respMap.get("EventKey").toString();
		}
		return result;
	}
	
	/**
	 * 接收voice信息
	 * @author LUYANFENG @ 2015年5月20日
	 */
	private String getVoice(){
		//TODO 
		return "";
	}
	
	/**
	 * 接收video信息
	 * @author LUYANFENG @ 2015年5月20日
	 */
	private String getVideo(){
		//TODO 
		return "";
	}
	
	
	/**
	 * 接收location信息
	 * @author LUYANFENG @ 2015年5月20日
	 */
	private String getLocation(){
		//TODO 
		return "";
	}
	
	
	/**
	 * 接收link信息
	 * @author LUYANFENG @ 2015年5月20日
	 */
	private String getLink(){
		//TODO 
		return "";
	}
	
	
	//--------------------------------------------------up 接收信息-----------------------------------------------------
	//--------------------------------------------------down 发送信息-----------------------------------------------------
	
	/**
	 * 发送text信息
	 * myID : 原始ID
	 * @author LUYANFENG @ 2015年5月20日
	 */
	private String respXML4Text(String myID, String touser,String msg){
		StringBuilder sb =new StringBuilder();
		sb.append("<xml>")
		  .append("<ToUserName><![CDATA[").append(touser).append("]]></ToUserName>")
		  .append("<FromUserName><![CDATA[").append(myID).append("]]></FromUserName>")
		  .append("<CreateTime>").append(Calendar.getInstance().getTime().getTime()).append("</CreateTime>")
		  .append("<MsgType><![CDATA[text]]></MsgType>")
		  .append("<Content><![CDATA[").append(msg).append("]]></Content>")
		  .append("</xml>")
		;
		return sb.toString();
	}
	
	/**
	 * 发送image信息
	 * @author LUYANFENG @ 2015年5月20日
	 */
	private String respXML4Image(String touser){
		return "";
	}
	
	/**
	 * 发送voice信息
	 * @author LUYANFENG @ 2015年5月20日
	 */
	private String respXML4Voice(String touser){
		return "";
	}
	
	/**
	 * 发送video信息
	 * @author LUYANFENG @ 2015年5月20日
	 */
	private String respXML4Video(String touser){
		return "";
	}
	
	/**
	 * 发送news信息
	 * @author LUYANFENG @ 2015年5月20日
	 */
	private String respXML4News(String touser){
		return "";
	}
	
	//=========================================对外方法================================================
	
	/**
	 * 被动式响应入口
	 * @param msg
	 * @return
	 * @author : LUYANFENG @ 2015年6月26日
	 */
	public String response(String msg, WeiXinMsgCryptUtils build){
		try {
			Map<String, Object> respMap = build.decryptXMLMsg();
			String result = this.respXML4Text(respMap.get("ToUserName").toString(), respMap.get("FromUserName").toString(), msg);
			result = build.encryptMsg(result, null , null);
			return result;
		} catch (AesException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 被动式响应入口 ： 关键词处理
	 * @param reader
	 * @author : LUYANFENG @ 2015年6月8日
	 */
	public String response(WeiXinMsgCryptUtils build) {
		String result = null;
		try {
			Map<String, Object> respMap = build.decryptXMLMsg();
			String msgType = respMap.get("MsgType").toString();
			
			// 基本权限过滤 ： 没有绑定的用户要先绑定下
			
			
			switch(MsgType.valueOf(msgType)){
			case text:
				Map<String, Object> analyzeResult = SentenceAnalyze.sentenceAnalyze(respMap.get("Content").toString());
				String optVal = analyzeResult.get("optVal").toString();
				Opt_Finally_Type opt_Finally_Type = null;
				if(StringUtils.isNotBlank(optVal)){
					opt_Finally_Type = SentenceAnalyze.crud(optVal);
				}
				String keyword = (String) analyzeResult.get("keyword");
				String openID = respMap.get("FromUserName").toString();
				result = this.processOptMsg(opt_Finally_Type ,openID, keyword);
				break;
			case event:
				result = this.getClickEvent(respMap);
				
				break;
			case image:
			case link:
			case location:
			case shortvideo:
			case video:
			case voice:
				default:
					result = "暂不支持的请求["+msgType+"]";
			}
			if(StringUtils.isBlank(result)){
				return "";
			}
			
			result = this.respXML4Text(respMap.get("ToUserName").toString(), respMap.get("FromUserName").toString(),result);
			result = build.encryptMsg(result, null , null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 处理用户动作指令
	 * @param opt_Finally_Type
	 * @param keyword
	 * @return
	 * @author : LUYANFENG @ 2015年7月6日
	 * @param keyword 
	 */
	@SuppressWarnings("unchecked")
	private String processOptMsg(Opt_Finally_Type opt_Finally_Type, String openid, String keyword) {
		String result = "";
		if(opt_Finally_Type == null){
			opt_Finally_Type = Opt_Finally_Type.R;
		}
		Log.debug("客户指令为 : "+opt_Finally_Type.toString(1));
		Log.debug("客户内容："+keyword);
		if(StringUtils.isNotBlank(keyword)){
			IMethodReturn hasBind = this.accDao.hasBind(openid);
			String unBind = "此操作需绑定后才可操作。\n请输入【bind/bd 身份证或营业执照】进行绑定。";
			switch(opt_Finally_Type){
			case C:
				// 操作前要判断是否已绑定
				if(!hasBind.isOK()){
					result += unBind;
					break;
				}
				break;
			case R:
				// 操作前要判断是否已绑定
				if(!hasBind.isOK()){
					result += unBind;
					break;
				}
				try {
					result += this.doQuery( openid, keyword);
				} catch (Exception e) {
					result += e.getMessage();
				}
				break;
			case U:
				// 操作前要判断是否已绑定
				if(!hasBind.isOK()){
					result += unBind;
					break;
				}
				break;
			case D:
				// 操作前要判断是否已绑定
				if(!hasBind.isOK()){
					result += unBind;
					break;
				}
				break;
			case B:
				if(hasBind.isOK()){
					result += hasBind.getMsg().get("info");
					break;
				}
				
				IMethodReturn methodResult = this.accDao.bindAccount(openid, keyword);
				if(methodResult.isOK()){
					Map<String, ?> msgMap = methodResult.getMsg();
					result += msgMap.get("info");
				}else{
					String msg = methodResult.pollErrorMsg();
					result += msg;
				}
				
				
				break;
			case RB:
				IMethodReturn mr = this.accDao.rebindAccount(openid, keyword);
				if(mr.isOK()){
					Map<String, ?> msgMap = mr.getMsg();
					Map<String,Object> userMap = (Map<String, Object>) msgMap.get("userMap");
					result += "恭喜！已重新绑定客户【"+ userMap.get("NAME") +"】";
				}else{
					String msg = mr.pollErrorMsg();
					result += msg;
				}
			case VC:
				if(hasBind.isOK()){
					result += hasBind.getMsg().get("info");
					break;
				}
				mr = this.accDao.checkVerifyCode(openid, keyword);
				if(mr.isOK()){
					result += "绑定成功！";
				}else{
					result += mr.pollErrorMsg();
				}
				break;
				
			case N:
			default:
				break;
					
			}
		}
		
		if(StringUtils.isBlank(result)){
			result = "悟空，你又调皮。";
		}
		return result;
	}

	/**
	 * 
	 * @return
	 * @author LUYANFENG @ 2015年7月9日
	 * @throws Exception 
	 */
	private String doQuery(final   String openid , final String keyword) throws Exception {
		String result = "";
		final List<Map<String, Object>> myCreditList = this.bizDao.getMyCreditList(openid,keyword);
		if(myCreditList != null && !myCreditList.isEmpty() ){
			String json4News = this.kfServ.getJSON4News(openid, new INewsDataHandler() {
				@Override
				public List<NewsBean> handler() throws Exception {
					List<NewsBean> list = new ArrayList<NewsBean>();
						NewsBean newsBean = new NewsBean();
						newsBean.setDescription("系统查询出"+myCreditList.size()+"条数据，点击文图查看详情。");
						String serverName = SkyEye.getRequest().getServerName();
						int serverPort = SkyEye.getRequest().getServerPort();
						newsBean.setPicurl( "http://"+ serverName+":"+serverPort +"/images/wx_pro_list_head.jpg");
						newsBean.setTitle("查询【"+keyword+"】的结果");
						//FIXME
						String url = "http://"+ serverName+"/weixinfw/Biz!myCreditList.action?k="+keyword;
						url = URLEncoder.encode(url, "utf-8");
						url = String.format(WeiXinMsgCryptUtils.API_oauth2_authorize, url);
						newsBean.setUrl(url);
						
						list.add(newsBean);
					return list;
				}
			});
			HTTPUtils.doThreadJsonPost( String.format(WeiXinMsgCryptUtils.API_send, AuthService.getAccess_token(false)), json4News);
			result = "数据加载中...";
		}else{
			result = "没有查询到数据";
		}
		return  result;
	}

	/**
	 * 发送Text信息
	 * @param strmsg
	 * @author : LUYANFENG @ 2015年6月26日
	 */
	public boolean sendMsg( String strmsg,WeiXinMsgCryptUtils build){
		Map<String, Object> respMap = build.decryptXMLMsg();
		String openid = respMap.get("FromUserName").toString();
		String jsonstr = this.kfServ.getJSON4Text(openid, strmsg);
		HTTPUtils.doThreadJsonPost(String.format(WeiXinMsgCryptUtils.API_send, AuthService.getAccess_token(false)), jsonstr );
		return true;
	}

}
