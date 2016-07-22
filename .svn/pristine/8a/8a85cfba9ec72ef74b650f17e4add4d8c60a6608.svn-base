package com.pqsoft.refund.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class RefundProcessService { 
    private String basePath = "refundProcess.";
    
    public Page getProcesConfigPageData(Map<String,Object> param){
    	Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getDbApplyList",param));
		page.addDate(array, Dao.selectInt(basePath+"getDbApplyCount", param));
		return page;
    }
}
