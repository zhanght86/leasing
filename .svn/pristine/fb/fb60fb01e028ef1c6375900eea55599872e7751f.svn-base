package com.pqsoft.weixinfw.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.skyeye.Log;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.weixinfw.utils.weixin.HTTPUtils;
import com.pqsoft.weixinfw.utils.weixin.IHandler;
import com.pqsoft.weixinfw.utils.weixin.WeiXinMsgCryptUtils;

/**
 * 菜单管理
 * 所有的菜单在这里生成
 * @author LUYANFENG @ 2015年5月20日
 */
@SuppressWarnings("unchecked")
public final class MenuService {
	private final static String Menu_create_URL = String.format(WeiXinMsgCryptUtils.API_menu_create, AuthService.getAccess_token(false));
	private final static String Menu_delete_URL = String.format(WeiXinMsgCryptUtils.API_menu_delete, AuthService.getAccess_token(false));
	
	private static boolean cache_flag = false;	// 是否缓存菜单
	
	private final static Map<String,Object> baseMenu = new HashMap<String,Object>();
	
	
	private static String serverName = SkyEye.getRequest().getServerName();
//	private static int serverPort = SkyEye.getRequest().getServerPort();
	
	
	private MenuService() {}
	
	
	/**
	 * 异步生成菜单
	 * @param cache 正式上线设置为true
	 */
	public static void initThreadMenuList(boolean cache) throws IOException{
		synchronized (Map.class) {
			if(cache && cache_flag){
				Log.debug(" 请注意：微信菜单已缓存！！！");
				return ;
			}
			if(!deleteMenu()){
				Log.debug(" ERROR : 服务号菜单生成失败！！！");
				return ;
			}
			
			if(baseMenu.isEmpty()){
				List<Map<String,?>> menuLMap = new ArrayList<Map<String,?>>(); 
				menuLMap.add(leftMenu());
				menuLMap.add(middleMenu());
				menuLMap.add(rightMenu());
				baseMenu.put("button", menuLMap);
			}
			
			final String menuStr = JSONObject.fromObject(baseMenu).toString(0);
			HTTPUtils.doThreadJsonPost(Menu_create_URL, menuStr , new IHandler() {
				@Override
				public void doWhat(String respJsonStr) {
					Map<String,Object> readValue = JSONObject.fromObject(respJsonStr);
					boolean ok = readValue.get("errcode").equals(0);
					if(ok){
						Map<String,Object> params = new HashMap<String,Object>();
						params.put("info", menuStr);
						params.put("appid", WeiXinMsgCryptUtils.getAppId());
						Dao.insert("fil_wxfw_menu.saveMenu", params);
						Dao.commit();
					}
				}
			});
			cache_flag = true;
		}
	}
	
	/**
	 * 删除菜单 
	 * @author LUYANFENG @ 2015年8月7日
	 */
	public static boolean deleteMenu(){
		synchronized (Map.class) {
			boolean isDeleted = false;
			if(isDeleted){
				return true;
			}
			String doJsonPost = HTTPUtils.doJsonPost(Menu_delete_URL, null);
			Map<String,Object> readValue = JSONObject.fromObject(doJsonPost);
			Log.debug("-------deleteMenu-------");
			Log.debug(readValue);
			boolean ok = readValue.get("errcode").equals(0);
			if(ok){
				baseMenu.clear();
				Dao.delete("fil_wxfw_menu.delMenu", WeiXinMsgCryptUtils.getAppId());
				Log.debug(" 请注意：微信菜单已被删除！");
				isDeleted = true;
			}
			return ok;
		}
		
	}
	
	/**
	 * 同步生成菜单
	 */
	public static boolean initMenuList(boolean cache) throws IOException{
		synchronized (Map.class) {
			if(cache && cache_flag){
				return true;
			}
			if(!deleteMenu()){
				Log.debug(" ERROR : 服务号菜单生成失败！！！");
				return false;
			}
			
			if(baseMenu.isEmpty()){
				List<Map<String,?>> menuLMap = new ArrayList<Map<String,?>>(); 
				menuLMap.add(leftMenu());
				menuLMap.add(middleMenu());
				menuLMap.add(rightMenu());
				baseMenu.put("button", menuLMap);
			}
			
			String menuStr = JSONArray.fromObject(baseMenu).toString(0);
			String doJsonPost = HTTPUtils.doJsonPost(Menu_create_URL, menuStr);
			Log.debug("################# 生成菜单响应："+doJsonPost);
			Map<String,Object> readValue = JSONObject.fromObject(doJsonPost);
			boolean ok = readValue.get("errcode").equals(0);
			if(ok){
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("info", menuStr);
				params.put("appid", WeiXinMsgCryptUtils.getAppId());
				Dao.insert("fil_wxfw_menu.saveMenu", params);
			}
			return ok;
		}
		
	}
	

	/**
	 * 只读方式获取菜单
	 * @author LUYANFENG @ 2015年8月7日
	 */
	public static Map<String,?> getMenuList(){
		return Collections.unmodifiableMap(baseMenu);
	}
	
	
	/**
	 * <pre>
	 * 左侧菜单
	 * </pre>
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private static Map<String,?> leftMenu() throws UnsupportedEncodingException{
		Map<String,Object> left_ = new HashMap<String,Object>();
		List<Map<String,?>> sub2 = new ArrayList<Map<String,?>>();
		left_.put("name", "我的借贷");
		left_.put("sub_button", sub2);
		
		{
			Map<String,Object> sub2_1 = new HashMap<String,Object>();
			sub2_1.put("type", "view");
			sub2_1.put("name", "还款情况");
			sub2_1.put("key", sub2_1.get("name"));
			String url = "http://"+ serverName +"/weixinfw/Biz!myPaymentInfo.action";
			url = URLEncoder.encode(url, "utf-8");
			sub2_1.put("url", String.format(WeiXinMsgCryptUtils.API_oauth2_authorize, url) );
			sub2.add(sub2_1);
		}
		{
			Map<String,Object> sub2_1 = new HashMap<String,Object>();
			sub2_1.put("type", "view");
			sub2_1.put("name", "进行中的");
			sub2_1.put("key", sub2_1.get("name"));
			String url = "http://"+ serverName +"/weixinfw/Biz!myPaying.action";
			url = URLEncoder.encode(url, "utf-8");
			sub2_1.put("url", String.format(WeiXinMsgCryptUtils.API_oauth2_authorize, url) );
			sub2.add(sub2_1);
		}
		{
			Map<String,Object> sub2_1 = new HashMap<String,Object>();
			sub2_1.put("type", "view");
			sub2_1.put("name", "历史借贷");
			sub2_1.put("key", sub2_1.get("name"));
			String url = "http://"+ serverName +"/weixinfw/Biz!myHistoryCreditList.action";
			url = URLEncoder.encode(url, "utf-8");
			sub2_1.put("url", String.format(WeiXinMsgCryptUtils.API_oauth2_authorize, url) );
			sub2.add(sub2_1);
		}
		
		
		return left_;
		
	}
	
	
	/**
	 * <pre>
	 * 中间菜单
	 * </pre>
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private static Map<String,?> middleMenu() throws UnsupportedEncodingException{
		Map<String,Object > middle_ = new HashMap<String,Object>();
//		List<Map<String,?>> sub2 = new ArrayList<Map<String,?>>();
		middle_.put("name", "我的对账单");
		middle_.put("type", "view");
		middle_.put("key", middle_.get("name"));
		String url = "http://"+ serverName +"/weixinfw/Biz!myBills.action";
		url = URLEncoder.encode(url, "utf-8");
		middle_.put("url", String.format(WeiXinMsgCryptUtils.API_oauth2_authorize, url) );
		/*middle_.put("sub_button", sub2);
		{
			Map<String,Object> sub2_1 = new HashMap<String,Object>();
			sub2_1.put("type", "click");
			sub2_1.put("name", "导出表单域无数据");
			sub2_1.put("key", sub2_1.get("name"));
			sub2.add(sub2_1);
		}
		
		{
			Map<String,Object> sub2_2 = new HashMap<String,Object>();
			sub2_2.put("type", "click");
			sub2_2.put("name", "罚息金额如何收取");
			sub2_2.put("key", sub2_2.get("name"));
			sub2.add(sub2_2);
		}
		
		{
			Map<String,Object> sub2_3 = new HashMap<String,Object>();
			sub2_3.put("type", "click");
			sub2_3.put("name", "支付表修改/变更");
			sub2_3.put("key", sub2_3.get("name"));
			sub2.add(sub2_3);
		}
		
		{
			Map<String,Object> sub2_4 = new HashMap<String,Object>();
			sub2_4.put("type", "click");
			sub2_4.put("name", "如何果看评审进度");
			sub2_4.put("key", sub2_4.get("name"));
			sub2.add(sub2_4);
		}
		
		{
			Map<String,Object> sub2_5 = new HashMap<String,Object>();
			sub2_5.put("type", "click");
			sub2_5.put("name", "如何将任务移交");
			sub2_5.put("key", sub2_5.get("name"));
			sub2.add(sub2_5);
		}
		*/
		return middle_;
		
	}
	
	
	/**
	 * <pre>
	 * 右侧菜单
	 * </pre>
	 * @return
	 */
	private static Map<String,?> rightMenu()  throws UnsupportedEncodingException{
		Map<String,Object > right_ = new HashMap<String,Object>();
		List<Map<String,?>> sub2 = new ArrayList<Map<String,?>>();
		right_.put("name", "走进平强");
		right_.put("sub_button", sub2);
		{
			Map<String,Object> sub2_1 = new HashMap<String,Object>();
			sub2_1.put("type", "click");
			sub2_1.put("name", "企业简介");
			sub2_1.put("key", sub2_1.get("name"));
			sub2.add(sub2_1);
		}
		{
			Map<String,Object> sub2_2 = new HashMap<String,Object>();
			sub2_2.put("type", "click");
			sub2_2.put("name", "平强软件");
			sub2_2.put("key", sub2_2.get("name"));
			sub2.add(sub2_2);
		}
		
		return right_;
		
	}
	
	
}
