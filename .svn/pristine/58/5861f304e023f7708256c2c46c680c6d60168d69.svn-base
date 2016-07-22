package com.pqsoft.refund.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class RefundDBHeXiaoService {
    private String basePath ="refundSflc.";
    
    /**
     * 获取核销列表数据
     * @param param
     * @return
     */
    public Page getHeXiaoPageData(Map<String,Object> param){
    	param.put("DIC_RE_STATUS","退款单状态");
    	Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getRefundDbHeXiaoList",param));
		page.addDate(array, Dao.selectInt(basePath+"getRefundDbHeXiaoCount", param));
		return page;
    }
    
    /**
     * 跟新退款单退款信息
     * @param param
     * @return
     */
    public int updateRefundDan(Map<String,Object> param){
    	return Dao.update(basePath+"updateRefundDanMess", param);
    }

    public List<Object> refundDanDetail(Map<String,Object> param){
    	param.put("DB_STATUS", "资金池状态");
    	return Dao.selectList(basePath+"getCanRefundList", param);
    }
    
    public List<Object> getRefundDBMessByRe_id(Map<String,Object> param ){
    	return Dao.selectList(basePath+"getRefundDBbyReId", param);
    }
    
    public int updatePoolStatusByRe_id(Map<String,Object> param){
    	return Dao.selectInt(basePath+"updatePoolStatusByRe_id", param);
    }
}
