package com.pqsoft.reportexcel.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class ReportExcelCreaditServices {

	 private final String xmlPath = "reportexcel.";

	    // 查询按钮
	    public Page queryCreaditData(Map<String, Object> m) {
	        Object Curr = m.get("PAGE_CURR");
	        Object count = m.get("PAGE_COUNT");
	        int pageCurr = Integer.parseInt(Curr == null ? "1" : Curr.toString());
	        int pageCount = Integer.parseInt(count == null ? "20" : count.toString());

	        m.put("PAGE_BEGIN", pageCount * (pageCurr - 1) + 1);
	        m.put("PAGE_END", pageCount * pageCurr);

	        return PageUtil.getPage(m, xmlPath + "query", xmlPath + "queryCount");
	    }
	    
	    public List  exportData(Map<String, Object> m){
	    	return Dao.selectList(xmlPath + "exportData", m);
	    	
	    }
	    /**
	     * excel 导出
	     * @param params
	     * @return
	     */
	    public Excel exprotExcel(Map<String, Object> params) {
	        List<Map<String, Object>> dataList = (List<Map<String, Object>>) params.get("dataList");

	        Excel excel = new Excel();
	        excel.setAutoColumn(25);
	        excel.addSheetName("信审报表");
	        excel.addData(dataList);
	        LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
	        title.put("FLAG", "业务类型");
	        title.put("JJBH", "进件编号");
            title.put("JJSJ", "进件时间");
            title.put("MD", "门店");
            title.put("NAME", "客户姓名");
            title.put("ID_CARD_NO", "客户身份证");
            title.put("SHORTNAME", "客户渠道");
            title.put("SCHEME_NAME", "方案名称");
            title.put("FIRSTPAYALL", "首付款");
            title.put("FINANCE_TOPRIC", "融资额");
            title.put("LEASE_TERM", "期数");
            title.put("ITEM_MONEY", "月还");
            title.put("STATUS_NEW", "当前状态");
            title.put("CSJL", "初审结论");
            title.put("ZSJL", "终审结论");
            title.put("CSRQ", "初审日期");
            title.put("ZSRQ", "终审日期");
            title.put("XSGKRY", "信审挂靠人员");
            title.put("CSJJYY", "初审拒绝原因");
            title.put("ZSJJYY", "终审拒绝原因");
            title.put("ZZRW", "截止任务");
            title.put("RWDDSJ", "任务到达时间");
	        
	        excel.addTitle(title);
	        // excel.hasRownum("编号");
	        excel.setHasTitle(true);
	        return excel;
	    }
}
