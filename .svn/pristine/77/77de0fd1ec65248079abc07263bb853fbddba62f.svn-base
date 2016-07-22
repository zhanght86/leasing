package com.pqsoft.payment.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class payMentFLService {
	
	//联合租赁放款管理页
	public Page pay_FL_Eq_Mg(Map<String,Object> m)
	{
		return PageUtil.getPage(m, "paymentFl.pay_FL_Mg", "paymentFl.pay_FL_Mg_count");

	}
	
	public Page pay_FL_COM_Eq_Mg(Map<String,Object> m)
	{
		return PageUtil.getPage(m, "paymentFl.pay_FL_COM_Mg", "paymentFl.pay_FL_COM_Mg_count");

	}
	
	//联合租赁放款管理页
	public Page pay_FL_JL_Mg(Map<String,Object> m)
	{
		return PageUtil.getPage(m, "paymentFl.pay_FL_JL_Mg", "paymentFl.pay_FL_JL_Mg_count");

	}
	
	public int updatePayMentFL(Map map){
		Dao.update("paymentFl.updatePayMentFLPayListCode",map);
		return Dao.update("paymentFl.updatePayMentFLDate",map);
	}
	
	//修改状态
	public void fl_PayHead_Status(Map map){
		Dao.update("paymentFl.updateFlStatus",map);
	}
}
