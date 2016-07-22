package com.pqsoft.wx.base.tools;

public class TokenRunnable implements Runnable{
	@Override
	public void run() {
		WeixinConfig.init(Parameters.initId);
	}
}
