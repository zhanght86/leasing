package com.pqsoft.action;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jsp.weixin.ParamesAPI.util.ParamesAPI;
import jsp.weixin.ParamesAPI.util.WeixinUtil;
import jsp.weixin.encryption.util.AesException;
import jsp.weixin.encryption.util.WXBizMsgCrypt;
import jsp.weixin.msg.Util.SMessage;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jfree.util.Log;
import org.springframework.util.FileCopyUtils;

import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.sms.service.WeixinService;

public class WeixinAction extends Action {

	private WeixinService wxServ = new WeixinService();
	@Override
	@aDev(code = "170043", email = "lichaohn@163.com", name = "lichao")
	@aAuth(type = aAuthType.ALL)
	public Reply execute() {
		try {
			Map<String, Object> param = _getParameters();
			for (Entry<String, Object> entry : param.entrySet()) {
				logger.info(entry.getKey() + "\t:\t" + entry.getValue());
			}
			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(ParamesAPI.token, ParamesAPI.encodingAESKey, ParamesAPI.corpId);
			String timestamp = SkyEye.getRequest().getParameter("timestamp");
			String nonce = SkyEye.getRequest().getParameter("nonce");
			String msg_signature = SkyEye.getRequest().getParameter("msg_signature");
			{
				String echostr = SkyEye.getRequest().getParameter("echostr");
				// 验证URL函数
				if (echostr != null) { return new ReplyHtml(wxcpt.VerifyURL(msg_signature, timestamp, nonce, echostr)); }
			}
			try {
				String xml = FileCopyUtils.copyToString(SkyEye.getRequest().getReader());
				Log.debug("==============================================WX xml test start=====================================");
				Log.debug(xml);
				Log.debug("==============================================WX xml test end =====================================");
				String res = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, xml);
				Map<String, String> m = parseXml(res);
				
				this.wxServ.FKres4Text(m);
				
				
				
				
				// String sRespData = respData(m.get("FromUserName"),
				// ParamesAPI.corpId, "测试一下谁点击了：" + m.get("FromUserName"));
				// String sEncryptMsg = wxcpt.EncryptMsg(sRespData, timestamp,
				// nonce);
				// msg(m.get("FromUserName"));
				// return new ReplyHtml(sEncryptMsg);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (AesException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public void msg(String user) {
		String PostData = SMessage.STextMsg("lichao", "", "", "9", user + ",访问了一次服务器");
		WeixinUtil.PostMessage(ParamesAPI.access_token, "POST", SMessage.POST_URL, PostData);
	}

	public String respData(String user, String sCorpID, String text) {
		String data = "<xml><ToUserName><![CDATA[";
		data += user;
		data += "]]></ToUserName><FromUserName><![CDATA[";
		data += sCorpID;
		data += "]]></FromUserName><CreateTime>";
		data += System.currentTimeMillis();
		data += "</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[";
		data += text;
		data += "]]></Content><MsgId>1234567890123456</MsgId><AgentID>128</AgentID></xml>";
		return data;
	}

	public Map<String, String> parseXml(String xml) throws Exception {
		// 解析结果存储在HashMap
		Map<String, String> map = new HashMap<String, String>();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(new StringReader(xml));
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();
		// 遍历所有子节点
		logger.info("------------------------ xml ----------------------");
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
			logger.info(e.getName() + "\t:\t" + e.getText());
			if("AgentID".equals(e.getName())){
				SkyEye.setConfig("AgentID", e.getText());
			}
		}
		return map;
	}

}
