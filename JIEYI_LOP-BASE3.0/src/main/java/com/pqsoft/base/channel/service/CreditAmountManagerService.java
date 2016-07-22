package com.pqsoft.base.channel.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.StringUtils;

public class CreditAmountManagerService {
    private String basePath = "CreditAmount.";
    
//    /**
//     * 授信额度管理
//     * @param param
//     * @return
//     */
//    public Page getCreditAmountPageData(Map<String,Object> param){
//    	Page page = new Page(param);
////    	Map<String,Object> SUP_MAP = BaseUtil.getSup();
////		if(SUP_MAP!=null){
////			param.put("SUP_ID",SUP_MAP.get("SUP_ID"));
////		}
//		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getCreditAmountList",param));
//		page.addDate(array, Dao.selectInt(basePath+"getCreditAmountCount", param));
//		return page;
//    }
//    
    public int updateCreditAmount(Map<String,Object> param){
    	return Dao.update(basePath+"updateCreditAmount", param);
    }
    
//    public Page getApplyDanPageData(Map<String,Object> param ){
//    	Page page = new Page(param);
//		JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getApplyDanList",param));
//		page.addDate(array, Dao.selectInt(basePath+"getApplyDanCount", param));
//		return page;
//    }
    
//    //授信统计金额（一）
//    public List<Object> getSupCreditOne(Map<String,Object> param){
//    	//获取供应商可使用额度（每个项目的占用比例）
//    	return null;
//    }    
//    //授信统计金额（二）
	
    /**
     * 项目立项校验授信额度,并记录授信占用比例
     * param:PROJECT_ID,COMPANY_ID,SUP_ID,MONEYALL,CREDIT_TYPE(一单一议：1，不是：0)
     */
	public Boolean checkCreditMoney(Map<String,Object> param){
		Boolean result = true;
    	//立项校验授信额度
		String CREDIT_TYPE = param.containsKey("CREDIT_TYPE") && param.get("CREDIT_TYPE") !=null && "1".equals(param.get("CREDIT_TYPE").toString().trim())?"1":"0";
		param.put("CREDIT_TYPE", CREDIT_TYPE);
    	Map<String,Object> canUseMoney = Dao.selectOne(basePath+"getSupCreditCanUserMoney", param);
    	if(StringUtils.isBlank(canUseMoney))
    		return false;
    	Double lastPrice = Double.parseDouble(canUseMoney.get("CANUSE_MONEY").toString())- Double.parseDouble(param.get("MONEYALL").toString());
    	param.put("RENT_SUM", param.get("MONEYALL").toString());
    	if(lastPrice < 0){
    		result = false ; 
    	}else{
    		result = true ; 
    		//记录授信占用
    		Map<String,Object> projectScale = Dao.selectOne(basePath+"getProjectStartScale", param);
    		param.put("OCCUPT_SCALE", projectScale.get("LIMIT_RATE").toString());
    		List<Object> projectItem = Dao.selectList(basePath+"getProjectItemMess", param);
    		if(projectItem.size() == 0 ){
    	        Dao.insert(basePath+"addCreditProjectItem", param);
    		}
    	}
        return result;
    }
	
	/**
	 * 更新授信额度占用
	 * @param param
	 * @author:King 2014-3-5
	 */
	public void updateCreditMoney(Map<String,Object> param){
		Dao.update(basePath+"updateCreditMoney",param);
	}
	
	public int addCreditMess(Map<String,Object> param ){
		return Dao.insert(basePath+"addCreditAmountMess", param);
	}
}
