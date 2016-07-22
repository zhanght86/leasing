package com.pqsoft.portal.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.BaseUtil;

public class FuncTaskSumService {

	public int getProjectCount(Map<String, Object> m) {
		m.put("tags1", "业务类型");
		m.put("tags2", "项目状态位");
		m.put("tags3", "客户类型");
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			m.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}

		Map COM_MAP = BaseUtil.getCom();
		if (COM_MAP != null) {
			m.put("COMPANY_ID", COM_MAP.get("COMPANY_ID"));
		}
		return Dao.selectInt("project.getAllProject_count", m);
	}

	public int getStartCount(Map<String, Object> m) {
		return Dao.selectInt("leaseApplication.toStartProject_count", m);
	}

	public int getFundCount(Map<String, Object> m) {
		return Dao.selectInt("fi.funddec.getPageCount", m);
	}

	public int getPaymentCount(Map<String, Object> m) {
		return Dao.selectInt("payment.pay_Head_Eq_Mg_count", m);
	}
	
	public int getRemindCount(Map<String, Object> m) {
		return Dao.selectInt("fi.Remind.query_Supp_Remind_Page_count", m);
	}
	
	public int getOverdueCount(Map<String, Object> m) {
		return Dao.selectInt("fi.overdue.query_overdue_Price_MG_count", m);
	}

}
