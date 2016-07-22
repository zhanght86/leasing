/**
 * TODO 
 * @author LUYANFENG @ 2015年5月28日
 */
package com.pqsoft.weixin.action;

import java.util.Map;

import jsp.weixin.ParamesAPI.util.ParamesAPI;
import jsp.weixin.ParamesAPI.util.WeixinUtil;
import jsp.weixin.msg.Util.SMessage;
import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.sms.service.WeixinService;

/**
 * @category 用于测试
 * @author LUYANFENG @ 2015年5月28日
 *
 */
public class TestWeiXinAction extends Action{

	@aAuth(type=aAuthType.ALL)
	@aDev(code="170025",email="luyanfeng@163.com" ,name="luyanfeng")
	@Override
	public Reply execute() {
		Map<String, Object> _getParameters = _getParameters();
		String msg = (String) _getParameters.get("msg");
		String appid = (String) _getParameters.get("appid");
		String userCode = (String) _getParameters.get("userCode");
		System.out.println("---------------test-------------------");
		System.out.println("     msg: "+msg);
		System.out.println("   appid: "+appid);
		System.out.println("userCode: "+userCode);
		
		System.out.println("---------------test-------------------");
		WeixinService.sendTextMsg(userCode, msg, appid);
		return null;
	}
	@aAuth(type=aAuthType.ALL)
	@aDev(code="170025",email="luyanfeng@163.com" ,name="luyanfeng")
	public Reply sendMail() {
		Map<String, Object> _getParameters = _getParameters();
		String c = (String) _getParameters.get("c");
		String t = (String) _getParameters.get("t");
		String mail = (String) _getParameters.get("mail");
		
		CertificateAction cAction = new CertificateAction();
		try {
			cAction.sendMail1(new String[]{mail}, t, c, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	

}
