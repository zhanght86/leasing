package com.pqsoft.wx.base.tools;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import com.pqsoft.skyeye.api.Dao;
import jsp.weixin.ParamesAPI.util.WeixinUtil;
import net.sf.json.JSONObject;

/**
 * 微信配置 相关
 * @author liangds
 *
 */
public class WeixinConfig {
	static Logger logger=Logger.getLogger(WeixinConfig.class);
	private static long RUN_SECONDS=7000;//定时 秒数
	private WeixinConfig(){}
	public static Map<String, Object> config=new ConcurrentHashMap<String, Object>();//存放配置相关的参数
	static{
		init(Parameters.initId);
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new TokenRunnable(), RUN_SECONDS, RUN_SECONDS, TimeUnit.SECONDS);
	}
	/**
	 * 初始化微信 配置表
	 * id=t_weixin_main.id
	 * @param id
	 */
	public static void init(int id){
		try{
			config.clear();
			//查询主表
			Map<String, Object> mainMap=Dao.selectOne("weixin.getWxMainInfo",id);
			config.put(Parameters.ID, mainMap.get("ID"));
			config.put(Parameters.CORP_ID, mainMap.get("CORP_ID"));
			config.put(Parameters.URL_PREFIX, mainMap.get("PROJECT_URL"));
			config.put(Parameters.PROJECT_NAME, mainMap.get("PROJECT_NAME"));
			logger.info("weixin main_id>>:"+mainMap.get("ID"));
			logger.info("weixin corp_id>>:"+mainMap.get("CORP_ID"));
			initToken();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 初始化token
	 */
	private static void initToken(){
		try{
			//查询应用
			List<Map<String,Object>> agents=Dao.selectList("weixin.getWxAgents", Integer.parseInt(getId()));
			if(agents!=null && agents.size()>0){
				for(int i=0;i<agents.size();i++){
					Map<String,Object> map=agents.get(i);
					String agentId=agents.get(i).get("AGENT_ID").toString();
					String access_token=getAccessTokenForRes(agents.get(i).get(Parameters.SECRET).toString());
					String ticket=getAccessTicketForRes(access_token);
					//存到内存中
					map.put(Parameters.ACCECC_TOKEN, access_token);
					map.put(Parameters.ACCESS_TICKET, ticket);
					config.put(Parameters.AGENT+"_"+agentId, map);
					//存到库中
					WeixinTools.saveTokenToDB(map.get("APPS_ID").toString(),access_token,ticket);
				}
				logger.info(config.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取token
	 * @return
	 */
	private static String getAccessTokenForRes(String secret){
		String result="-1";
		JSONObject jsonobject = WeixinUtil.HttpRequest(Parameters.TOKEN_URL+"?corpid="+getCorpId()+"&corpsecret="+secret, "GET", null);
		System.out.println("getToken>>:"+jsonobject.toString());
		if(jsonobject.get("errcode")==null){
			result=jsonobject.getString("access_token");
		}
		return result;
	}
	/**
	 * config 为空则重新加载
	 */
	private static void reload(){
		if(config==null || config.isEmpty() || config.get(Parameters.CORP_ID)==null || "".equals(config.get(Parameters.CORP_ID))){
			init(Parameters.initId);
		}
	}
	
	/**
	 * 获取js ticet
	 * @return
	 */
	private static String getAccessTicketForRes(String token){
		String result="-1";
		JSONObject jsonobject = WeixinUtil.HttpRequest(Parameters.JSAPI_TICKET_URL+"?access_token="+token, "GET", null);
		System.out.println("getTicket>>:"+jsonobject.toString());
		if("0".equals(jsonobject.getString("errcode"))){
			result=jsonobject.getString("ticket");
		}
		return result;
	}
	
	//以下提供相应方法获取各种参数  corpId token encodingAesKey secret accessToken accessTicket
	private static Map<String,Object> getAgentMap(String agentId){
		reload();
		@SuppressWarnings("unchecked")
		Map<String,Object> map= (Map<String, Object>) config.get(Parameters.AGENT+"_"+agentId);
		return map;
	}
	
	/**
	 * corpId
	 * @return
	 */
	public static String getCorpId(){
		reload();
		return config.get(Parameters.CORP_ID).toString();
	}
	/**
	 * id
	 * @return
	 */
	public static String getId(){
		reload();
		return config.get(Parameters.ID).toString();
	}
	
	/**
	 * TOKEN
	 * @param agentId
	 * @return
	 */
	public static String getToken(String agentId){
		return getAgentMap(agentId).get(Parameters.TOKEN).toString();
	}
	/**
	 * ENCODING_AES_KEY
	 * @param agentId
	 * @return
	 */
	public static String getEncodingAesKey(String agentId){
		return getAgentMap(agentId).get(Parameters.ENCODING_AES_KEY).toString();
	}
	/**
	 * SECRET
	 * @param agentId
	 * @return
	 */
	public static String getSecret(String agentId){
		return getAgentMap(agentId).get(Parameters.SECRET).toString();
	}
	/**
	 * ACCECC_TOKEN
	 * @param agentId
	 * @return
	 */
	public static String getAccessToken(String agentId){
		return getAgentMap(agentId).get(Parameters.ACCECC_TOKEN).toString();
	}
	/**
	 * ACCESS_TICKET
	 * @param agentId
	 * @return
	 */
	public static String getAccessTicket(String agentId){
		return getAgentMap(agentId).get(Parameters.ACCESS_TICKET).toString();
	}
}
