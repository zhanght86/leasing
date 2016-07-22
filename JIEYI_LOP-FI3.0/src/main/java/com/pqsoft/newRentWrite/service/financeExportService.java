package com.pqsoft.newRentWrite.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;

public class financeExportService {

    private final String xmlPath = "financeExport.";

    // 查询按钮
    public Page query(Map<String, Object> m) {
        Object Curr = m.get("PAGE_CURR");
        Object count = m.get("PAGE_COUNT");
        int pageCurr = Integer.parseInt(Curr == null ? "1" : Curr.toString());
        int pageCount = Integer.parseInt(count == null ? "10" : count.toString());

        m.put("PAGE_BEGIN", pageCount * (pageCurr - 1) + 1);
        m.put("PAGE_END", pageCount * pageCurr);

        return PageUtil.getPage(m, xmlPath + "query", xmlPath + "queryCount");
    }
    
    

    // 未锁定导出所有的数据
    public List excelAll(Map map) {
        map.put("USER_CODE", Security.getUser().getCode());
        map.put("USER_NAME", Security.getUser().getName());
        List list = Dao.selectList(xmlPath + "excelALL", map);// 山重
        return list;
    }

}
