package com.pqsoft.newRentWrite.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;

public class superTableService {

	 private final String xmlPath = "superTable.";

	 
	    // 查询按钮
	    public Page query(Map<String, Object> m) {
	        Object Curr = m.get("page");
	        Object count = m.get("rows");
	        int pageCurr = Integer.parseInt(Curr == null ? "1" : Curr.toString());
	        int pageCount = Integer.parseInt(count == null ? "10" : count.toString());

	        m.put("PAGE_BEGIN1", pageCount * (pageCurr - 1) + 1);
	        m.put("PAGE_END1", pageCount * pageCurr);
	        return  PageUtil.getPage(m, xmlPath + "query", xmlPath + "queryCount");
	    }

	    // 未锁定导出所有的数据
	    public List excelAll(Map map) {
	        map.put("USER_CODE", Security.getUser().getCode());
	        map.put("USER_NAME", Security.getUser().getName());
	        List list = Dao.selectList(xmlPath + "excelALL", map);// 山重
	        return list;
	    }
}
