package com.pqsoft.firstPayRemind.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class FirstPayRemindService {
	private String path = "fi.FirstPayRemind.";
	public Page getFirstPayMoneyData(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return PageUtil.getPage(map, path+"getFirstPayMoneyDataList", path+"getFirstPayMoneyDataCount");
	}
	public Object getOverdueCollectRecordByPayCode(Map<String, Object> param) {
		return Dao.selectList("fi.Remind.getOverdueCollectRecordByRenterCode", param);
	}
	public int doAdd_FIL_DUN_LOGRecord(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return Dao.insert("fi.Remind.doAddPressDunLog", map);
	}
	public void doAddCollectionRecord(Map<String, Object> map) {
		Dao.insert("fi.Remind.doAddCollectionRecord", map);
	}
	public int doDeletePressDunLog(String ID) {
		// TODO Auto-generated method stub
		return Dao.delete("fi.Remind.doDeletePressDunLog", ID);
	}
	public List DunLog_More(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return Dao.selectList("fi.Remind.DunLog_more", param);
	}

}
