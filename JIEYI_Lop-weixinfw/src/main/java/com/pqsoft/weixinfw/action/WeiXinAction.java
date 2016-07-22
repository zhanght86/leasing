package com.pqsoft.weixinfw.action;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.pqsoft.skyeye.Log;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.weixinfw.service.AuthService;
import com.pqsoft.weixinfw.service.IOMessageService;
import com.pqsoft.weixinfw.service.MenuService;
import com.pqsoft.weixinfw.utils.IMethodReturn;
import com.pqsoft.weixinfw.utils.weixin.WeiXinMsgCryptUtils;


/**
 * 服务号(微信)入口类
 * @author LUYANFENG @ 2015年5月26日
 */
public class WeiXinAction extends AbstractWeiXinResponseAction{
	
	private AuthService authServ = new AuthService();
	private IOMessageService msgServ = new IOMessageService();
	
	/**
	 * 一切生于此
	 * @author : LUYANFENG @ 2015年6月12日
	 */
	@aAuth(type=aAuthType.ALL)
	@aDev(code="170025", email="luyf@pqsoft.cn", name="luyanfeng")
	public Reply  checkSignature(){
		HttpServletRequest request = SkyEye.getRequest();
		String msg_signature 		= request.getParameter("msg_signature");
		String signature 			= request.getParameter("signature");
		String timestamp 			= request.getParameter("timestamp");
		String nonce 				= request.getParameter("nonce");
		String echostr 			= request.getParameter("echostr");
		Log.debug(_getParameters());
		boolean isok = false; // 开发者认证
		WeiXinMsgCryptUtils wxbt = null;
		try {
			wxbt = this.authServ.checkSignature(signature, timestamp, nonce , msg_signature ,request.getInputStream());
			isok =  wxbt.verifyUrl(signature, timestamp, nonce);
			Log.debug(" -- 开发者认证结果 : " + isok);
			MenuService.initThreadMenuList(true); 
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		if(isok){
			if(StringUtils.isBlank(echostr)){
				IMethodReturn userResult = this.authServ.getCurrentUser(wxbt);
				if( !userResult.isOK() ){
					String msg = userResult.pollErrorMsg();
					echostr = this.msgServ.response( msg ,wxbt);
				}else{
					String newuser = (String) userResult.getMsg().get("newuser");
					if(StringUtils.isNotBlank(newuser)){
						echostr = this.msgServ.response( "hi，欢迎新用户，\n请输入【bind 身份证或营业执照】来绑定您的微信号与我们的系统。\n如不清楚请联系客服。", wxbt);
					}else{
						echostr = this.msgServ.response(wxbt );
					}
					// send message
//					this.msgServ.sendMsg("你好", wxbt);
				}
			}
			return new ReplyHtml(echostr);
		}
		return new ReplyHtml("");
	}
	
	
}
