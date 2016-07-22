/**
 * <pre>
 * TODO
 * <pre>
 * 北京平强软件有限公司
 * @author LUYANFENG @ 2015年8月14日
 */
package com.pqsoft.weixinfw.utils.weixin.oauth2;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

import com.pqsoft.skyeye.Log;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.weixinfw.utils.DefaultMethodReturn;
import com.pqsoft.weixinfw.utils.IMethodReturn;
import com.pqsoft.weixinfw.utils.weixin.HTTPUtils;
import com.pqsoft.weixinfw.utils.weixin.WeiXinMsgCryptUtils;

/**
 * <pre>
 * 如果用户在微信客户端中访问第三方网页，公众号可以通过微信网页授权机制，来获取用户基本信息，进而实现业务逻辑。
 * <pre>
 * @author LUYANFENG @ 2015年8月14日
 */
public class OAuth2 {
	/**
	 * <pre>
	 * 获取当前来访者微信身份 oauth2
	 * TODO 引导页面
	 * <pre>
	 * @param url 能返回 code 的action url.
	 * @author LUYANFENG @ 2015年8月14日
	 */
	public String getOauth2Code(String url){
		try {
			url = URLEncoder.encode(url, "utf-8");
			String code = HTTPUtils.doGet(String.format(WeiXinMsgCryptUtils.API_oauth2_authorize, url));
			
			try {
				JSONObject fromObject = JSONObject.fromObject(code);
				Log.debug("-------OAuth2 authorize error---------");
				Log.error(code);
				Log.debug("--------------------------------------");
				throw new ActionException("OAuth2 认证失败：\n1、确认您的服务器已备案 \n2、服务器出错");
			} catch (Exception e) {
			}
			
			
			return code;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * <pre>
	 * 获取oauth2 下的 access_token 
	 * <pre>
	 * @param code com.pqsoft.weixinfw.utils.weixin.oauth2.OAuth2.getOA2User() 方法获取的
	 * @author LUYANFENG @ 2015年8月14日
	 */
	public IMethodReturn getAccessToken(String code ){
		IMethodReturn result = new DefaultMethodReturn();
		String jsonstr = HTTPUtils.doGet(String.format(WeiXinMsgCryptUtils.API_oauth2_access_token, code) );
		JSONObject jsonObj = JSONObject.fromObject(jsonstr);

		if(jsonObj.containsKey("errcode")){
			int errcode = jsonObj.getInt("errcode");
			return result.addErrorMsg("errcode", "获取页面授权access_token失败："+errcode);
		}
		
		/*String access_token = jsonObj.getString("access_token");
		String expires_in = jsonObj.getString("expires_in");
		String refresh_token = jsonObj.getString("refresh_token");
		String openid = jsonObj.getString("openid");
		String scope = jsonObj.getString("scope");
		String unionid = jsonObj.getString("unionid");*/
		result.clearError().addMsg("data", jsonObj);
		return result;
	}
}
