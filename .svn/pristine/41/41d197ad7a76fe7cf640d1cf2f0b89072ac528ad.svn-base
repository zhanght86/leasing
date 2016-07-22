package com.pqsoft.base.stock.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;


import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.StringUtils;

public class StockManagerService {
	private String basePath = "Stock.";
	
	public Page getStockManager(Map<String,Object> param){
	    Page page = new Page(param);
	    JSONArray array = JSONArray.fromObject(Dao.selectList(basePath+"getStockList",param));
	    page.addDate(array, Dao.selectInt(basePath+"getStockCount", param));
	    return page;
	}
	
	public int addStockMess(Map<String,Object> param){
	    return Dao.insert(basePath+"addStockMess", param);
	}
	
	public int updateStockMess(Map<String,Object> param){
	    return Dao.update(basePath+"updateStockMess", param);
	}
	
	public Map<String,Object> getOneStock(Map<String,Object> param ){
	    return Dao.selectOne(basePath+"getOneStockMess", param);
	}
	
	public List<Object> getStockOtherTerm(Map<String,Object> param ){
	    return Dao.selectList(basePath+"getOneStockMess", param); 
	}
	
	public int delStock(Map<String,Object> param){
	    return Dao.delete(basePath+"delStock", param);
	}
	
	public List<Object> getSupByCompanyId(Map<String,Object> param){
	    return Dao.selectList(basePath+"getSupByCompanyId", param);
	}
	
	//立项根据业务类型插入库存信息
	public void addStockFromProject(String PROJECT_ID){
	    //通过项目id查询对应的立项设备信息
	    List<Object> eqMess = Dao.selectList(basePath+"getEqMessByProjectId", PROJECT_ID);
	    for (Object equipment : eqMess) {
		Map<String,Object> eq = (Map<String,Object>)equipment;
		if(eq.containsKey("PLATFORM_TYPE") && eq.get("PLATFORM_TYPE") !=null && "".equals(StringUtils.nullToString(eq.get("PLATFORM_TYPE")))){
		    Dao.insert(basePath+"addStockMess", eq);
		}
	        
	    }
	}

}
