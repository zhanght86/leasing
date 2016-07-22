package com.pqsoft.base.grantCredit.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class SupplierRefundService {
	//经销商保证金退换审批页面
	public Map<String, Object> showRefundById(Map<String, Object> map) {
		Map<String,Object> map_tem = Dao.selectOne("SupplierCredit.selectShowJxsDate", map);
		return map_tem;
	}

	public List<Map<String, Object>> selectRefundList(Map<String, Object> map) {
		List<Map<String, Object>> list = Dao.selectList("SupplierCredit.selectShowJxsList", map);
		return list;
	}

}
