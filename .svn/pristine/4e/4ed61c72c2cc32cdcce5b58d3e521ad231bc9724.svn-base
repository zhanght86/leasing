package com.pqsoft.cashBzjQmdk.service;

import java.util.HashMap;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;

public class CashBzjQmdkService {
	private String xmlPath = "cashDepositK.";
	public Page getBzjQmdkData(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return PageUtil.getPage(map, xmlPath+"getBzjQmdkDataList", xmlPath+"getBzjQmdkDataCount");
	}
	public Map<String, Object> getCustNameBankInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return Dao.selectOne(xmlPath+"getCustNameBankInfo", map);
	}

}
