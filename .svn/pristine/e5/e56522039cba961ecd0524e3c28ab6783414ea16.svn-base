package com.pqsoft.weixin.utils;

import java.util.UUID;

import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.util.StringUtils;

public final class AppUtils {
	private AppUtils() {
	}

	
	
	public static final String MYTOKEN = "__MYTOKEN";
	
	/**
	 * 生成令牌
	 * @author LUYANFENG @ 2015年5月14日
	 */
	public static String getNewToken(){
		String string = UUID.randomUUID().toString();
		SkyEye.getRequest().setAttribute(AppUtils.MYTOKEN, string);
		return string;
	}
	
	/**
	 * 比较访问令牌是否相等
	 * @author LUYANFENG @ 2015年5月14日
	 */
	public static boolean equalsNowToken(String nowToken){
		String token = (String) SkyEye.getRequest().getSession().getAttribute(AppUtils.MYTOKEN);
		return StringUtils.isBlank(nowToken)|| StringUtils.isBlank(token) ? false :  nowToken.equals(token);
	}
	
	
	
	
	
	
	
	
	/**
	 * 资料阶段名
	 */
	public static enum Phase {
		放款前
		,放款后
		,立项
	}
	
	/**
	 * 客户类型
	 * @author LUYANFENG @ 2015年4月1日
	 */
	public static enum ClientType {
		LP,NP
	}
	
	/**
	 * 费用计入方式
	 * @author LUYANFENG @ 2015年5月9日
	 *
	 */
	public static enum Calculate {
		JRSQK	// 首期
		,JRRZE	// 融资额
		,JRWK	// 尾款
	}

	public static void generateToken() {
		String newToken = getNewToken();
		SkyEye.getRequest().getSession().setAttribute(MYTOKEN, newToken);
	}
}
