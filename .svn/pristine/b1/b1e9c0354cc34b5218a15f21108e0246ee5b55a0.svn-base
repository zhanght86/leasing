package com.pqsoft.wx.bs.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.wx.base.WeixinBaseAction;
import com.pqsoft.wx.base.tools.WeixinConfig;
import com.pqsoft.wx.base.tools.WeixinTools;

import jsp.weixin.media.util.MUDload;
/**
 * Jieyi测试
 * @author liangds
 *
 */
public class JieyiAction extends WeixinBaseAction{
	@Override
	@aDev(code = "62", email = "liangds@163.com", name = "liangds")
	@aAuth(type = aAuthType.ALL)
	public Reply execute() {
		return null;
	}
	//工作台
	@aDev(code = "62", email = "liangds@163.com", name = "liangds")
	@aAuth(type = aAuthType.ALL)
	public Reply index() {
		if(getWeixinLoginInfo().length()>0){
			return new ReplyHtml(getWeixinLoginInfo());
		}
		//SendMsg.sendTxtToUser("admin","14", "您有新任务,请及时处理");
		//SendMsg.sendTxtTotag("1", "14", "发送消息给标签");
		
		VelocityContext context = new VelocityContext();
		context.put("userName", Security.getUser().getName());

		WeixinTools.getSign(WeixinTools.getUrl(SkyEye.getRequest()),getAgentId(),context);
		
		return new ReplyHtml(VM.html("wx/jieyi/myTask.vm",context));
	}
	
	//详细信息
	@aDev(code = "62", email = "liangds@163.com", name = "liangds")
	@aAuth(type = aAuthType.ALL)
	public Reply caseBaseInfo() {
		if(getWeixinLoginInfo().length()>0){
			return new ReplyHtml(getWeixinLoginInfo());
		}
		VelocityContext context = new VelocityContext();
		context.put("userName", Security.getUser().getName());

		WeixinTools.getSign(WeixinTools.getUrl(SkyEye.getRequest()),getAgentId(),context);
			
		return new ReplyHtml(VM.html("wx/jieyi/myTask2.vm",context));
	}
	
	//下载图片
		@aDev(code = "62", email = "liangds@163.com", name = "liangds")
		@aAuth(type = aAuthType.ALL)
		public Reply downImage() {
			Map<String, Object> param = _getParameters();
			String serverId = (String) param.get("serverId");
			System.out.println(serverId);
			
			String customerPath = SkyEye.getConfig("file.path") + File.separator + "fileUploadDir" 
					+ File.separator +(Security.getUser()==null ? "god" : Security.getUser().getCode())
					+ File.separator ;
			String dir = customerPath
					+ new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
			this.createDirectory(dir);
			
			String physicalPath = MUDload.downloadMedia(WeixinConfig.getAccessToken("14"), serverId.trim() , dir );
			
			//physicalPath=WeixinTools.getPrjectUrl()+"/leeds/cust_info_input/CustInfoInput!readPic.action?path="+physicalPath;

			System.out.println(physicalPath);
			return new ReplyAjax(true ,physicalPath);
		}
		@aDev(code = "62", email = "liangds@163.com", name = "liangds")
		@aAuth(type = aAuthType.ALL)
		private boolean createDirectory(String path) {
			boolean flag = true;
			try {
				File wf = new File(path);
				if (!wf.exists()) {
					wf.mkdirs();
				}
			} catch (Exception e) {
				flag = false;
			}
			return flag;
		}

}
