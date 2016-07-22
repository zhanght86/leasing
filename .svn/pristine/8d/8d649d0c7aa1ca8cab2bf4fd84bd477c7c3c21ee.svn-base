package com.pqsoft.base.grantCredit.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.ReplyFile;

public class CreditLimitStatisticService {
    private String basePath ="Credit.";
    
    public Page getCreditLimitPageData(Map<String,Object> param){
    	Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getCredtTongJiList",param));
		page.addDate(array, Dao.selectInt(basePath+"getCreditTongJiCount", param));
		return page;
    }
    
    public List<Object> getLimitMessList(Map<String,Object> param){
    	return Dao.selectList(basePath+"getAmountMapList", param);
    }
    
    public Excel exportLimitRateMess(Map<String,Object> param ){
    	List<Map<String,Object>>  pageList = Dao.selectList(basePath+"getExportLimitRateList", param);
		//表头
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
		title.put("SUP_SHORTNAME", "供应商");
		title.put("KEY_WORD_ZN", "特征属性类型");
		title.put("KEY_WORD_VALUE", "特征描述");
		title.put("TOTAL_MONEY", "已使用额度总额");
		title.put("SINGLE_TOTAL_MONEY", "一单一议已使用额度");
		//封装excel
		Excel excel = new Excel();
		excel.addData(pageList);
		excel.addTitle(title);
		return excel;
    }
}
