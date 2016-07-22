package com.pqsoft.server.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;

public class Id5Service {
	
	public List<Map<String,Object>> getZLGS(){
		return Dao.selectList("id5.getZLGS");
	}
	
	public int getResult(Map<String,Object> param){
		if(param.containsKey("NAME") && !param.get("NAME").equals("")){
			Map<String,Object> map = Dao.selectOne("id5.getZLGS",param);
			param.putAll(map);
			if(map != null){
				if(map.get("TYPE").equals("IP")){
					return Dao.selectInt("id5.getResultByIp",param);
				}else if(map.get("TYPE").equals("AUTH_CODE")){
					return Dao.selectInt("id5.getResultByCode",param);
				}else{
					return 0;
				}
			}else{
				return 0;
			}
		}else{
			return 0;
		}
	}

}
