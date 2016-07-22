package com.pqsoft.analysisBySynthesis.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class RepaymentSourceService {
 
	/**
	 * 添加
	 * doAddForProfit
	 * @date 2014-3-29 下午05:35:31
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int doAddForProfit(Map<String,Object> map){
		String id = Dao.getSequence("SEQ_CREDIT_FORPROFIT");
		map.put("ID", id);
		return Dao.insert("forProfit.doInertForProfit",map);
	}
	/**
	 * 
	* @Title: queryForProfit 查询
	* @autor wuguowei_jing@163.com 2014-5-18 上午11:01:10
	* @param map
	* Map<String,Object>    
	 */
	public Map<String,Object> queryForProfit(Map<String,Object> map){
		Map<String,Object> dataMap =  Dao.selectOne("forProfit.queryForProfit", map);
//		Set<String> dataSet = dataMap.keySet();
//		Map<String,Object> returnMap = new HashMap<String,Object>();
//		Map<String,Object> childMap;
//		JSONObject  jsonObject;
// 		for(String key:dataSet){
// 			
// 			if(key==null || key.equals("ID") ||key.equals("CREATE_ID") ||key.equals("CREATE_TIME") 
// 					||key.equals("CLIENT_ID") ||key.equals("CREDIT_ID")){
// 				continue;
// 			}
// 			childMap = new HashMap<String,Object>();
// 			jsonObject  = new JSONObject();
// 			
// 			Map m1 = jsonObject.fromObject(dataMap.get(key));
// 			System.out.println(key+"==========="+m1.toString());
// 			childMap.put(key, m1);
// 			returnMap.put(key, childMap);
//		}
 		
 		return dataMap;
	}
	
	public int doInertCONSUMPTIONAIM(Map<String,Object> map){
		System.out.println("-----------------------"+map);
		String id = Dao.getSequence("SEQ_CREDIT_CONSUMPTIONAIM");
		map.put("ID", id);
		return Dao.insert("forProfit.doInertCONSUMPTIONAIM",map);
	}
	
	public  Map<String,Object> QueryForExpend(Map<String,Object> map){
		return Dao.selectOne("forProfit.queryForExpend",map);
		
	}

}
