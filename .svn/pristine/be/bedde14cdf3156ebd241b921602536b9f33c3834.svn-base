package com.pqsoft.insure.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 日期操作
 */
public class InsureStatus {
	public static Map<String, String> status = new HashMap<String, String>();
	public static InsureStatus insureStatus = new InsureStatus();
	static {
		status.put("1", "提醒");
		status.put("20", "供应商操作");
		status.put("30", "待确认");
		status.put("40", "审核通过");
		status.put("50", "审核失败");
	}

	public static InsureStatus getInstence() {
		return insureStatus;
	}

	public static String getStatus(String s) {
		if (s == null) return null;
		return status.get(s);
	}
}