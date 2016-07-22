package com.pqsoft.weixinfw.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.weixinfw.utils.weixin.INewsDataHandler;
import com.pqsoft.weixinfw.utils.weixin.bean.NewsBean;

import net.sf.json.JSONObject;

/**
 * 客服管理类
 * @author LUYANFENG @ 2015年5月20日
 *
 */
public class KeFuService {
	
	private Map<String,Object> getJSONComm(String openid, KF_msgtype msgtype ){
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("touser", openid);
		jsonMap.put("msgtype", msgtype.toString());
		return jsonMap;
	}
	
	/**
	 * 
	 * @param openid
	 * @param msgtype
	 * @param content
	 * @return
	 * @author LUYANFENG @ 2015年7月9日
	 */
	public String getJSON4Text(String openid, String content){
		Map<String, Object> jsonMap = this.getJSONComm(openid, KF_msgtype.text);
		Map<String,Object> sub = new HashMap<String,Object>();
		sub.put("content", content);
		jsonMap.put(KF_msgtype.text.toString(), sub);
		return JSONObject.fromObject(jsonMap).toString(0);
	}
	
	/**
	 * 
	 * @param openid
	 * @param msgtype
	 * @param media_id
	 * @return
	 * @author LUYANFENG @ 2015年7月9日
	 */
	public String getJSON4Image(String openid, String media_id){
		Map<String, Object> jsonMap = this.getJSONComm(openid, KF_msgtype.image);
		Map<String,Object> sub = new HashMap<String,Object>();
		sub.put("media_id", media_id);
		jsonMap.put(KF_msgtype.image.toString(), sub);
		return JSONObject.fromObject(jsonMap).toString(0);
	}
	
	/**
	 * 
	 * @param openid
	 * @param msgtype
	 * @param media_id
	 * @param thumb_media_id
	 * @param title
	 * @param description
	 * @return
	 * @author LUYANFENG @ 2015年7月9日
	 */
	public String getJSON4Video(String openid, String media_id , String thumb_media_id, String title, String description){
		Map<String, Object> jsonMap = this.getJSONComm(openid, KF_msgtype.video);
		Map<String,Object> sub = new HashMap<String,Object>();
		sub.put("media_id", media_id);
		sub.put("thumb_media_id", thumb_media_id);
		sub.put("title", title);
		sub.put("description", description);
		jsonMap.put(KF_msgtype.video.toString(), sub);
		return JSONObject.fromObject(jsonMap).toString(0);
	}
	
	/**
	 * 
	 * @param openid
	 * @param msgtype
	 * @param musicurl
	 * @param hqmusicurl
	 * @param thumb_media_id
	 * @param title
	 * @param description
	 * @return
	 * @author LUYANFENG @ 2015年7月9日
	 */
	public String getJSON4Music(String openid, String musicurl ,String hqmusicurl, String thumb_media_id, String title, String description){
		Map<String, Object> jsonMap = this.getJSONComm(openid, KF_msgtype.music);
		Map<String,Object> sub = new HashMap<String,Object>();
		sub.put("musicurl", musicurl);
		sub.put("thumb_media_id", thumb_media_id);
		sub.put("hqmusicurl", hqmusicurl);
		sub.put("title", title);
		sub.put("description", description);
		jsonMap.put(KF_msgtype.music.toString(), sub);
		return JSONObject.fromObject(jsonMap).toString(0);
	}
	/**
	 * 
	 * @param openid
	 * @param msgtype
	 * @param articles [{"title":"Happy Day","description":"Is Really A Happy Day","url":"URL","picurl":"PIC_URL"}]
	 * @return List<Map<String,Object>> articles
	 * @author LUYANFENG @ 2015年7月9日
	 * @throws Exception 
	 */
	public String getJSON4News(String openid, INewsDataHandler newsDataHandler) throws Exception{
		Map<String, Object> jsonMap = this.getJSONComm(openid, KF_msgtype.news);
		Map<String,Object> sub = new HashMap<String,Object>();
		List<NewsBean> articles = newsDataHandler.handler();
		sub.put("articles", articles);
		jsonMap.put(KF_msgtype.news.toString(), sub);
		return JSONObject.fromObject(jsonMap).toString(0);
	}
	
	
	
	/**
	 * 
	 * @param openid
	 * @param msgtype
	 * @param card_id
	 * @param code
	 * @param timestamp
	 * @param signature
	 * @return
	 * @author LUYANFENG @ 2015年7月9日
	 */
	public String getJSON4Wxcard(String openid, KF_msgtype msgtype ,String card_id, 
			String code,String timestamp, String signature){
		Map<String, Object> jsonMap = this.getJSONComm(openid, msgtype);
		Map<String,Object> sub = new HashMap<String,Object>();
		
		
		Map<String,Object> card_ext = new HashMap<String,Object>();
		{
			card_ext.put("code", code);
			card_ext.put("openid", openid);
			card_ext.put("timestamp", timestamp);
			card_ext.put("signature", signature);
		}
		sub.put("card_ext", card_ext);
		sub.put("card_id", card_id);
		
		
		jsonMap.put(msgtype.toString(), sub);
		return JSONObject.fromObject(jsonMap).toString(0);
	}
	
	
	
	/**
	 * 消息类型，文本为text，图片为image，语音为voice，视频消息为video，音乐消息为music，图文消息为news，卡券为wxcard
	 * @author LUYANFENG @ 2015年7月9日
	 */
	public enum KF_msgtype {
		text,
		image,
		voice,
		video,
		music,
		news,
		wxcard
	}

}
