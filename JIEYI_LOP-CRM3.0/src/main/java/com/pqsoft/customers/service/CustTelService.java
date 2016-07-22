package com.pqsoft.customers.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.base.organization.service.ManageService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.PageUtil;


public class CustTelService {

	// TODO NOWPROJECT
	
	private final String xmlPath = "custTel.";
	
	public Page findCustomers(Map<String, Object> m) {
		
		ManageService mgService = new ManageService();
		Map map = (Map) mgService.getSupByUserId(Security.getUser().getId());
		
		if (map != null && map.get("SUP_ID") != null) {
			m.put("SUPP_ID", map.get("SUP_ID"));
		}
		BaseUtil.getDataAllAuth(m);
		return PageUtil.getPage(m , xmlPath + "findCustomers", xmlPath + "findCustomersCount");
	}
	//插入联系人信息
		public boolean doAddEmergencyInfo(Map<String, Object> m) {
			
			  int i = Dao.insert(xmlPath + "doAddEmergencyInfo", m) ;
			  if(i > 0){
				  return true;
			  }
			  return false;
		}
		
		/**
		 * 保存电调内容
		 * @param m
		 * @return
		 */
		public boolean saveTelContext(Map<String, Object> m) {
			try {
				int i = Dao.update( xmlPath + "saveTelContext", m) ;
				if(i > 0){
					  return true;
				  }
				 return false;
			} catch (Exception e) {
				return false;
			}
		}
		
		public void saveTelContext2(Map<String, Object> m,String type) {
			// 保存客户电调内容
			if("1".equals(type)){
				
				Dao.update(xmlPath + "saveTelContextNomal", m) ;
			}
			// 保存联系人电调内容
			else if("2".equals(type)){
				
				Dao.update(xmlPath + "saveTelContextNomal2", m) ;
			}
		}
		
		/**
		 * 保存电调信息
		 * 
		 * @param mList
		 */
		public void saveTelContextList(List<Map<String, Object>> mList) {
			
			for (Map<String, Object> map : mList) {
				String num = map.get("num").toString();
				if("0".equals(num)) {
					Dao.update(xmlPath + "insertTelCallContextNomal", map) ;
				} else {
					Dao.update(xmlPath + "updateTelCallContextNomal", map) ;
				}
			}
		}
		
		/**
		 * 根据ID删除追加人信息
		 * @param m
		 * @return
		 */
		public boolean delByID(Map<String, Object> m){
			try {
				int i = Dao.delete(xmlPath + "delByID", m);
				if(i > 0){
					return true;
				}
				return false;
			} catch (Exception e) {
				return false;
			}
		}
}
