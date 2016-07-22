package com.pqsoft.weixinfw.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.pqsoft.skyeye.Log;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.weixinfw.dao.AccountDao;
import com.pqsoft.weixinfw.utils.DefaultMethodReturn;
import com.pqsoft.weixinfw.utils.IMethodReturn;
import com.pqsoft.weixinfw.utils.PQsoftStringUtil;
import com.pqsoft.weixinfw.utils.weixin.AesException;
import com.pqsoft.weixinfw.utils.weixin.HTTPUtils;
import com.pqsoft.weixinfw.utils.weixin.WeiXinMsgCryptUtils;


/**
 * 微信认证类
 */
public class AuthService {
	
	private AccountDao accDao = new AccountDao();
	
	
	/**1 获取access token,
	 * 有效期目前为2个小时
	 * @author LUYANFENG @ 2015年5月20日
	 */
	public static String getAccess_token(boolean getNew ){
		String text = null;
		Map<String, Object> map = HTTPUtils.getAccess_token(WeiXinMsgCryptUtils.API_Access_token , getNew);
		text = (String) map.get("access_token");
		if(StringUtils.isBlank(text)){
			text = (String) map.get("errmsg");
		}
		return text;
	}
	
	
	/**2 获取微信服务器IP地址
	 * @author LUYANFENG @ 2015年5月20日
	 */
	public boolean getCallbackip(){
		//TODO 
		return false;
	}
	
	/**3 证认
	 * @author LUYANFENG @ 2015年5月20日
	 * @param nonce 
	 * @param timestamp 
	 * @param signature 
	 * @param msg_signature 
	 * @param servletInputStream 
	 */
	public WeiXinMsgCryptUtils checkSignature(String signature, String timestamp, String nonce,String msg_signature, InputStream servletInputStream){
		try {
			return  WeiXinMsgCryptUtils.build(signature,timestamp ,nonce ,msg_signature, servletInputStream);
		} catch (Exception e) {
			throw new ActionException(e.getMessage());
		}
		
	}
	
	
	/**
	 * 获取当前用户信息
	 * @return
	 * @author LUYANFENG @ 2015年8月7日
	 */
	public IMethodReturn getCurrentUser(WeiXinMsgCryptUtils wxbt) {
		IMethodReturn result = new DefaultMethodReturn();
		if(wxbt == null){
			//TODO 
		}
		
		try {
			Map<String, Object> respMap = wxbt.decryptXMLMsg();
			String OpenID = respMap.get("FromUserName").toString(); // OpenID
			
			String respstr = HTTPUtils.doGet(String.format(WeiXinMsgCryptUtils.API_userInfo, getAccess_token( false ), OpenID ));
			Map<String,Object> userInfoMap = JSONObject.fromObject(respstr );
			
			if(StringUtils.isNotBlank( (String)userInfoMap.get("errmsg") )){
				return result.addErrorMsg("errmsg","访问可能已过期,请重新打开！"+ userInfoMap.get("errcode")+" "+userInfoMap.get("errmsg") );
			}
			String subscribe = userInfoMap.get("subscribe").toString();
			if("0".equals(subscribe)){
				return result.addErrorMsg("errmsg", "您还没有关注该公众号！");
			}
			Log.debug("----------- user info --------------");
			for(Map.Entry<String, Object> m : userInfoMap.entrySet()){
				Log.debug(String.format("%1$20s : %2$s",m.getKey() , m.getValue().toString()));
			}
			Log.debug("------------------------------------");
		
			String subscribe_time = userInfoMap.get("subscribe_time").toString(); 
			Object unionid = userInfoMap.get("unionid");// 公众号下唯一
			
			Map<String, Object> sysUserMap = this.accDao.findWXUser( OpenID ,unionid ,subscribe_time);
			if(sysUserMap != null && !sysUserMap.isEmpty()){
				return result.clearError().addMsg(PQsoftStringUtil.Variable.__userinfo__.toString(), sysUserMap);
			}
			
			boolean ok = this.accDao.addWXUser(userInfoMap);
			if( !ok){
				return result.addErrorMsg("errmsg", "用户还没有关联系统账号！");
			}
//			Log.debug("========= 添加新用户！！：");
			result.clearError().addMsg(PQsoftStringUtil.Variable.__userinfo__.toString(), userInfoMap).addMsg("newuser", "1");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
