package com.pqsoft.delivery.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class InvoiceApplicationService {

	public Object getInvoiceApplicationNextVal() {
		return Dao.selectOne("invoiceApplication.getInvoiceApplicationNextVal");
	}
	
	public Object getEMSInfoNextVal() {
		return Dao.selectOne("invoiceApplication.getEMSInfoNextVal");
	}
	
	public Object getClientIdByProjectId(Map<String,Object> param){
		return Dao.selectOne("invoiceApplication.getClientIdByProjectId", param) ;
	}
	
	public Map<String, Object> searchCustInfo(Map<String, Object> param) {
		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("PAY_ID", param.get("PAY_ID"));
		return Dao.selectOne("invoiceApplication.searchCustInfo", refer);
	}

	public int insertInvoiceApplication(Map<String, Object> param) {
		
		param.put("INVOICE_APPLICATION_ID", getInvoiceApplicationNextVal());
		param.put("EMS_ID", getEMSInfoNextVal()) ;
		return Dao.insert("invoiceApplication.insertInvoiceApplication", param);
	}

	public int updateInvoiceApplication(Map<String, Object> param) {
		return Dao.update("invoiceApplication.updateInvoiceApplication", param);
	}

	public Map<String, Object> getInvoiceApplicationById(
			Map<String, Object> param) {
		Map<String, Object> refer = new HashMap<String, Object>();
		refer.put("ID", param.get("ID"));
		return Dao.selectOne("invoiceApplication.getInvoiceApplicationById",
				refer);
	}

	public Page getInvoiceApplicationPage(Map<String, Object> param) {
		Page page = new Page(param);
		List<Map<String, Object>> list = Dao.selectList(
				"invoiceApplication.getInvoiceApplicationList", param);
		String TYPE = "开票方式";
		List<Map<String, Object>> typeList = (List<Map<String, Object>>) new DataDictionaryMemcached()
				.get(TYPE);

		for (int i = 0; i < list.size(); i++) {
			for (Map<String, Object> map2 : typeList) {
				if (list.get(i).get("INVOICEPATTERN") == null
						|| "".equals(list.get(i).get("INVOICEPATTERN")
								.toString()))
					continue;
				String invoicePattern = list.get(i).get("INVOICEPATTERN")
						.toString();
				String code = map2.get("CODE").toString();
				if (invoicePattern.equals(code)) {
					list.get(i).put("INVOICEPATTERN", map2.get("FLAG"));
				}

			}
		}

		JSONArray array = JSONArray.fromObject(list);
		page.addDate(array, Dao.selectInt(
				"invoiceApplication.getInvoiceApplicationCount", param));
		return page;
	}

}
