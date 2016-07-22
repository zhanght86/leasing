package com.pqsoft.rentRemind.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.PageUtil;

public class RentRemindService {

	
	public Page query_Remind_Price_Mg_AJAX(Map<String, Object> m) {
		Map SUP_MAP = BaseUtil.getSup();
		if (SUP_MAP != null) {
			m.put("SUP_ID", SUP_MAP.get("SUP_ID"));
		}

		Map COM_MAP = BaseUtil.getCom();
		if (COM_MAP != null) {
			m.put("COMPANY_ID", COM_MAP.get("COMPANY_ID"));
		}
		return PageUtil.getPage(m, "fi.Remind.query_Supp_Remind_Page", "fi.Remind.query_Supp_Remind_Page_count");

	}
	public Object getOverdueCollectRecordByPayCode(Map<String, Object> map) {
		return Dao.selectList("fi.Remind.getOverdueCollectRecordByRenterCode", map);
	}
	public int doAddPressDunLog(Map<String, Object> map) {
		return Dao.insert("fi.Remind.doAddPressDunLog", map);
	}
	public int selectDunLogPreId() {
		Map<String, Object> str = (Map<String, Object>) Dao.selectOne("fi.Remind.selectDunLogPreId", null);
		int i = Integer.parseInt(str.get("CURRVAL").toString());
		return i;
	}
	public int doAddCollectionRecord(Map<String, Object> map) {
		return Dao.insert("fi.Remind.doAddCollectionRecord", map);
	}
	public int doDeletePressDunLog(String ID) {
		return Dao.delete("fi.Remind.doDeletePressDunLog", ID);
	}
	public List DunLog_More(Map<String, Object> map) {
		return Dao.selectList("fi.Remind.DunLog_more", map);
	}
}
