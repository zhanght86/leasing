package com.pqsoft.newRentWrite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;

public class whiteListService {

	private final String xmlPath = "whiteList.";

	// 查询按钮
	public Page query(Map<String, Object> m) {
		Object Curr = m.get("page");
		Object count = m.get("rows");
		int pageCurr = Integer.parseInt(Curr == null ? "1" : Curr.toString());
		int pageCount = Integer.parseInt(count == null ? "10" : count.toString());

		m.put("PAGE_BEGIN1", pageCount * (pageCurr - 1) + 1);
		m.put("PAGE_END1", pageCount * pageCurr);
		return PageUtil.getPage(m, xmlPath + "query", xmlPath + "queryCount");
	}

	// 未锁定导出所有的数据
	public List excelAll(Map map) {
		map.put("USER_CODE", Security.getUser().getCode());
		map.put("USER_NAME", Security.getUser().getName());
		List list = Dao.selectList(xmlPath + "excelALL", map);// 山重
		return list;
	}

	// 下载
	public Map<String,String> down(Map map) {

		List<Object> downObj = Dao.selectList(xmlPath + "downAll", map);

		Map m = new HashMap();
       
		for (int i = 0; i < downObj.size(); i++) {
			String k = "";
			Object modelObj = downObj.get(i);

			if (modelObj != null && modelObj != "") {

				Map modelMap = (Map) modelObj;
			
				String path = "";
				for (Object key : modelMap.keySet()) {

					if (key.equals("ID")) {

						Object BANK_CUSTNAMEOBJ = modelMap.get("ID");

						if (BANK_CUSTNAMEOBJ == null || BANK_CUSTNAMEOBJ.equals("")) {

						} else {
							k+= String.valueOf(BANK_CUSTNAMEOBJ);
						}
					}
					
					if (key.equals("BANK_CUSTNAME")) {

						Object BANK_CUSTNAMEOBJ = modelMap.get("BANK_CUSTNAME");

						if (BANK_CUSTNAMEOBJ == null || BANK_CUSTNAMEOBJ.equals("")) {

						} else {
							k+= String.valueOf(BANK_CUSTNAMEOBJ);
						}
					}

					if (key.equals("BANK_ACCOUNT")) {

						Object BANK_ACCOUNTOBJ = modelMap.get("BANK_ACCOUNT");

						if (BANK_ACCOUNTOBJ == null || BANK_ACCOUNTOBJ.equals("")) {

						} else {
							k += String.valueOf(BANK_ACCOUNTOBJ);
						}
					}
					
					if (key.equals("PDF_PATH")) {

						Object PDF_PATHOBJ = modelMap.get("PDF_PATH");

						if (PDF_PATHOBJ == null || PDF_PATHOBJ.equals("")) {

						} else {
							path= String.valueOf(PDF_PATHOBJ);
						}
					}
					
				}
				System.out.println("liuli test->"+k);
				m.put(k, path);
			}
			
		}
		return m;
	}
}
