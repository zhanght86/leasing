package com.pqsoft.base.grantCredit.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class CompanyRefundService {
	//厂商保证金退换审批页面
	public Map<String, Object> showRefundById(Map<String, Object> map) {
		Map<String,Object> map_tem = Dao.selectOne("companyCredit.selectShowCsDate", map);
		System.out.println(map_tem+"---jdd----");
		return map_tem;
	}

	public List<Map<String, Object>> selectRefundList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = Dao.selectList("companyCredit.selectShowCsList", map);
		System.out.println("SupplierRefundService.selectRefundList()-----------");
		return list;
	}

}
