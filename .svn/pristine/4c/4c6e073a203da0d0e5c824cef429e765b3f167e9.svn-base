package com.pqsoft.project.finance_Project.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import com.pqsoft.skyeye.api.Dao;

public class FinanceMainService {

	
	/**
	 * 
	*@author zhanghengyu
	*功能：查询资信负债表数据
	*@param creditId
	*@return
	 */
	public Map<String,Object> initDebt(String creditId)
	{
		Map map = this.queryDebt(creditId);
		JSONObject json=JSONObject.fromObject(map);
		Iterator<String> keys=json.keySet().iterator();
		String key="";
		String value="";
		Map<String,Object> m=new HashMap<String,Object>();
		while(keys.hasNext())
		{
			key=keys.next();
			value=map.get(key).toString();
			if (value.indexOf("{")>=0)
			{
				m.put(key, JSONObject.fromObject(value));
			}else{
				m.put(key, value);
			}
		}
		
		return m;
	}
	
	
	//查询财报信息
	public Map getFinnaceCridit(Map map)
	{
		Map mapInfo=new HashMap();
		 //查询资金负债表的未分配利润
        Map mapDebt=(Map)this.getFinnaceDebtRisk(map);
        if(mapDebt!=null)
        {
        	Set<String> key = mapDebt.keySet();
	        for (Iterator it = key.iterator(); it.hasNext();) {
	            String s = (String) it.next();
	            Map map2=JSONObject.fromObject(mapDebt.get(s).toString());
	            mapInfo.put(s, map2);
	        }
        }
        
        //查询利润及利润分配表的净利润
        Map mapDis=(Map)this.getFinnaceProfitDisRisk(map);
        if(mapDis!=null)
        {
        	Set<String> key = mapDis.keySet();
	        for (Iterator it = key.iterator(); it.hasNext();) {
	            String s = (String) it.next();
	            Map map2=JSONObject.fromObject(mapDis.get(s).toString());
	            mapInfo.put(s, map2);
	        }
        }	
        
		
		return mapInfo;
	}
	
	public Object getFinnaceDebtRisk(Map map)
	{
		
		return Dao.selectOne("finance.getFinnaceDebtRisk",map);
	}
	
	public Object getFinnaceProfitDisRisk(Map map)
	{
		return Dao.selectOne("finance.getFinnaceProfitDisRisk",map);
	}
	
	
	private Map queryDebt(String creditId) {

		return this.selectDebt(creditId);
	}
	
	//齐姜龙
	//查询出利润及利润分配表数据
	public Object getFinnaceProfitDistriButtion(Map map)
	{
		return Dao.selectOne("finance.getFinnaceProfitDistriButtion",map);
	}
	
	//齐姜龙
	//查询出利润及利润分配表数据
	public Object getFinnaceProfitDistriButtionByMap(Map map)
	{
		return Dao.selectOne("finance.getFinnaceProfitDistriButtionByMap",map);
	}
	
	public Object getFinnaceDebt(Map map)
	{
		return Dao.selectOne("finance.getFinnaceDebt",map);
	}
	
	//齐姜龙
	//查询出利润及利润分配表数据
	public Object getFinnaceCash(Map map)
	{
		return Dao.selectOne("finance.getFinnaceCash",map);
	}
	
	
	//齐姜龙
	//查询出利润及利润分配表数据
	public Object getFinnaceCashByMap(Map map)
	{
		return Dao.selectOne("finance.getFinnaceCashByMap",map);
	}
	
	
	public Object getFinnaceDis(Map map)
	{
		return Dao.selectOne("finance.getFinnaceDis",map);
	}
	
	
	public Map selectDebt(String creditId)
	{
		
		return Dao.selectOne("finance.selectDebt", creditId);
	}
	
	/**
	 * 
	*@author zhanghengyu
	*功能：保存资产负债表数据
	*@param map
	*@return
	 */
	public boolean insertDebt(Map<String,Object> map)
	{
		//删除老的数据
		Dao.delete("finance.deleteDebt", map);
		return Dao.insert("finance.insertDebt", map)>0;
	}
	
	//齐姜龙
	//查询出现金流量表数据保存
	public int saveFinnaceCash(Map<String,Object> map)
	{
		Dao.delete("finance.deleteFinnaceCash", map);
		return Dao.insert("finance.saveFinnaceCash",map);
	}
	
	//齐姜龙
	//查询出利润及利润分配表数据保存
	public int saveFinnaceProfitDistriButtion(Map<String,Object> map)
	{
		Dao.delete("finance.deleteFinnaceProfitDistriButtion", map);
		return Dao.insert("finance.saveFinnaceProfitDistriButtion",map);
	}
}
