package com.pqsoft.fi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;

public class FundTreeService {

	public JSONArray getDetail(Map<String, Object> param) {
		JSONArray array = JSONArray.fromObject(Dao.selectList("fi.fundtree.getDetail", param));
		array.add(JSONObject.fromObject(Dao.selectOne("fi.fundtree.getDfj", param)));
		return array;
	}

	public void getDetail(String fundId, List<Object> list) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("FUND_ID", fundId);
		List<Object> array = Dao.selectList("fi.fundtree.getDetail", param);
		Map<String, Object> dfj = Dao.selectOne("fi.fundtree.getDfj", param);
		if (dfj != null) array.add(dfj);
		list.add(array);
		if (dfj == null || "0".equals(dfj.get("FUND_STATUS") + "")) { return; }
		getDetail(dfj.get("FUND_ID") + "", list);
	}

	public Object getTree(Map<String, Object> param) {
		List<Object> list = new ArrayList<Object>();
		getDetail(param.get("FUND_ID") + "", list);
		return list;
	}

}
