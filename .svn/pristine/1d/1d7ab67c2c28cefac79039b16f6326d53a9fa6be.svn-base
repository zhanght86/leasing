package com.pqsoft.cashBzjQmth.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;

public class CashBzjQmthService {
	private String xmlPath="cashDepositK.";
	
	public Page getBzj_Qmth_All(Map<String, Object> map) {
		Map<String,Object> map_tem = Dao.selectOne(xmlPath+"getBzj_Qmth_Status", null);
		//判断是否需要结清 1是  直接去还款主表取值  2否  判断应收和逾期表是否已还款完毕
		if("1".equals(map_tem.get("CODE"))){
			return PageUtil.getPage(map, xmlPath+"getBzj_Qmth_List_One", xmlPath+"getBzj_Qmth_One_Count");
		}else if("2".equals(map_tem.get("CODE"))){
			return PageUtil.getPage(map, xmlPath+"getBzj_Qmth_List_Two", xmlPath+"getBzj_Qmth_Two_Count");
		}else {
			return null;
		}
	}

	public Map<String, Object> getBzj_Qmth_DataById(Map<String, Object> map) {
		return Dao.selectOne("cashDepositK.selectTHById", map);
	}

	public List<Map<String, Object>> getFi_R_Beginning_ListById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return Dao.selectList("cashDepositK.getFi_R_Beginning_List", map);
	}

	public int checkApplay_QMTH(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return Dao.selectInt(xmlPath+"checkApplay_Status", map);
	}

	public Map<String, Object> selectTHById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return Dao.selectOne(xmlPath+"selectTHInfoById", map);
	}

	public Page getBankData(Map<String, Object> param) {
		if(param == null){
			param=new HashMap();
		}
		param.put("PLATFORMID", Security.getUser().getOrg().getPlatformId());
		Map<String,Object> map=Dao.selectOne("fi.fund.getPTInfo", param);
		param.put("ID", map.get("ID"));
		param.put("FA_BANK_TYPE", "1");//账号类型 0：收款账号 1：付款账号  
		return PageUtil.getPage(param, xmlPath+"getBankData", xmlPath+"getBankDataCount");
	}

	public List<Map<String, Object>> getFRBListByRemarkId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return Dao.selectList("cashDepositK.getFRBListByRemarkId", map);
	}

}
