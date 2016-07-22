package com.pqsoft.wx.base.tools.msg;
/**
 * 发送文本消息
 * @author liangds
 *
 */
public class TextMsg extends BaseMsg{
	private Text text;
	
	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}
	
	public TextMsg(String touser,String toparty,String totag,String msgtype,String agentid,String txt){
		setTouser(touser);
		setToparty(toparty);
		setTotag(totag);
		setMsgtype(msgtype);
		setAgentid(agentid);
		setText(new Text(txt));
	}
}
