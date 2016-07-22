package com.pqsoft.shouldPaid.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class shouldPaidService{
	
	//设备放款管理页
	public Page shouldPaid_Mg_AJAX(Map<String,Object> m)
	{
		return PageUtil.getPage(m, "shouldPaid.shouldPaid_Mg", "shouldPaid.shouldPaid_Mg_count");
	}
	
	public Map shouldPaidHj(){
		return Dao.selectOne("shouldPaid.shouldPaid_HJ");
	}

}
