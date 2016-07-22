package com.pqsoft.fi.payin.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class FundBillService {

	public Page getPage(Map<String, Object> param) {
		return PageUtil.getPage(param, "fi.funddec.getBillPageList", "fi.funddec.getBillPageCount");
	}
	
	public int TransferredCredit(Map<String, Object> param)
	{
		return Dao.update("fi.funddec.TransferredCredit",param);
	}
}
