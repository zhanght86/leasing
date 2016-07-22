package com.pqsoft.bpm.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.Log;
import com.pqsoft.skyeye.osi.api.Mail;

public class SystemMail {

	public static void sendMail(final String address, final String title, final String text) throws Exception {

		String stmp = null;
		String username = null;
		String password = null;
		try {
			List<Object> list = new SysDictionaryMemcached().get("系统邮箱");
			// List<Object> list = new DataDictionaryMemcached().get("系统邮箱");
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					Map<?, ?> m = (Map<?, ?>) list.get(i);
					if (m.get("FLAG").toString().equalsIgnoreCase("stmp")) {
						stmp = m.get("CODE").toString();
					}
					if (m.get("FLAG").toString().equalsIgnoreCase("password")) {
						password = m.get("CODE").toString();
					}
					if (m.get("FLAG").toString().equalsIgnoreCase("username")) {
						username = m.get("CODE").toString();
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("读取邮箱配置失败", e);
		}
		if (stmp == null || username == null || password == null) throw new Exception("邮箱配置错误");

		if (address == null || title == null || text == null) throw new Exception("邮件格式错误");

		try {
			final String s1 = stmp;
			final String s2 = username;
			final String s3 = password;
			new Thread() {
				public void run() {
					try {
						new Mail(s1, s2, s3).sendHtml(address, title, text);
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			}.start();
		} catch (Exception e) {
			Log.error("stmp : " + stmp);
			Log.error("username : " + username);
			Log.error("address : " + address);
			throw new Exception("发送邮件失败:" + e.getMessage(), e);
		}
	}
}
