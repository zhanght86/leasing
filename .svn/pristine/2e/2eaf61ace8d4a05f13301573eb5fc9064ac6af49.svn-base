package com.pqsoft.wx.agent.action;

import java.util.Map;
import java.util.Map.Entry;
import org.springframework.util.FileCopyUtils;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.wx.base.tools.WeixinConfig;
import com.pqsoft.wx.base.tools.WeixinTools;
import jsp.weixin.encryption.util.AesException;
import jsp.weixin.encryption.util.WXBizMsgCrypt;
/**
 * 用于应用回调
 * @author liangds
 *
 */
public class JieZhongAgentAction extends Action {
	String agentId="11";
	@Override
	@aDev(code = "62", email = "liangds@163.com", name = "liangds")
	@aAuth(type = aAuthType.ALL)
	public Reply execute() {
		try {
			Map<String, Object> param = _getParameters();
			//1.url验证
			for (Entry<String, Object> entry : param.entrySet()) {
				logger.info(entry.getKey() + "\t:\t" + entry.getValue());
			}
			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(WeixinConfig.getToken(agentId), WeixinConfig.getEncodingAesKey(agentId), WeixinConfig.getCorpId());
			String timestamp = SkyEye.getRequest().getParameter("timestamp");
			String nonce = SkyEye.getRequest().getParameter("nonce");
			String msg_signature = SkyEye.getRequest().getParameter("msg_signature");
			if(SkyEye.getRequest().getParameter("echostr")!=null){
				String echostr = SkyEye.getRequest().getParameter("echostr");
				String sEchoStr = wxcpt.VerifyURL(msg_signature, timestamp,nonce, echostr);
				if (echostr != null) { return new ReplyHtml(sEchoStr); }
			}
			//2.事件接收
			try {
				String xml = FileCopyUtils.copyToString(SkyEye.getRequest().getReader());
				String res = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, xml);
				WeixinTools.parseXml(res);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (AesException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	
	
}
