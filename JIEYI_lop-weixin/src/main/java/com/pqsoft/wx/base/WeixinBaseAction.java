package com.pqsoft.wx.base;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.wx.base.tools.WeixinConfig;
import com.pqsoft.wx.base.tools.WeixinTools;
import jsp.weixin.oauth2.util.GOauth2Core;

/**
 * weixi bas action 
 * 所有weixi请求的action都要继承它
 * @author liangds
 *
 */
public abstract class WeixinBaseAction extends Action {
	private String weixinLoginInfo="";
	private String agentId="0";//应用ID
	public String getWeixinLoginInfo() {
		return weixinLoginInfo;
	}
	public void setWeixinLoginInfo(String weixinLoginInfo) {
		this.weixinLoginInfo = weixinLoginInfo;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	/**
	 * 初始化是处理weixin请求
	 * 所有的请求都需要带上 state=?
	 */
	public WeixinBaseAction() {
		logger.info("WeixinBaseAction ini");
		logger.info(WeixinTools.getUrl(SkyEye.getRequest()));
		String result="";
		if (Security.isWeixin()) {
			Map<String, Object> param = _getParameters();
			
			String agentId=(String) param.get("state");
			logger.info("state>>:"+agentId);
			if(agentId==null || "".equals(agentId)){
				result="未知的应用ID！";
				setWeixinLoginInfo(result);
				return;
			}else{
				setAgentId(agentId);
			}
			logger.info("agentId>>:"+agentId);
			
			String code = (String) param.get("code");
			logger.info("code>>:"+code);
			//code区分是从wx进来的 还是内部跳转
			//内部跳转
			if(code==null){
				//
			}
			//从wx菜单进来
			else{
				code = code.trim();
				if ("authdeny".equals(code)) {
					result="该应用未授权!";
					setWeixinLoginInfo(result);
					return;
				}
				String UserID = GOauth2Core.GetUserID(WeixinConfig.getAccessToken(agentId), code, agentId);
				if (UserID == null || "".equals(UserID)) {
					result="获取weixin用户信息失败！";
					setWeixinLoginInfo(result);
					return;
				}
				logger.info("login userId>>:"+UserID);
				try{
					//转换登录
					Security.login(WeixinTools.getLoginUserCode(UserID,agentId));
				}catch(Exception e){
					e.printStackTrace();
					result="未授权的用户！";
					setWeixinLoginInfo(result);
				}
			}
		}else{
			result="获取用户信息失败！";
			setWeixinLoginInfo(result);
		}
		logger.info("result>>:"+result);
	}
	
	public ReplyHtml errorMsg(String msg,VelocityContext context){
		context.put("errorMsg", msg);
		return new ReplyHtml(VM.html("wx/pub/message.vm",context)); 
	}
}
