/**
 * TODO 
 * @author LUYANFENG @ 2015年7月7日
 */
package com.pqsoft.weixinfw.utils;

import java.util.UUID;

/**
 * TODO 
 * @author LUYANFENG @ 2015年7月7日
 *
 */
public final class PQsoftStringUtil {
	
	private PQsoftStringUtil() {
	}
	
	public static String verify_code(){
		return UUID.randomUUID().toString().replace("-", "").substring(0,6);
	}
	
	
	public enum Variable{
		/**
		 * 微信用户id
		 */
		__openid__, 
		/**
		 * 微信用户信息
		 */
		__userinfo__
	}


	/**
	 * @return
	 * @author LUYANFENG @ 2015年8月11日
	 */
	public static String UUID() {
		return UUID.randomUUID().toString();
	}

}
