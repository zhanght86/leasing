package com.pqsoft.SynchronizeData.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.util.ajax.JSON;

import com.alibaba.druid.support.json.JSONUtils;
import com.pqsoft.skyeye.api.Dao;

/**
 * 与车采数据同步接口
 * @author 王华英  2016年7月10日18:02:33
 */
public class SynchronizeDataService {
	private String xmlPath = "SynchronizeDataMapper.";
	
	//同步数据
	public  void synchronizeData(Map<String,String> mapData){
		//如果处理的是品牌数据，则需要处理厂商数据
		if("PRODUCT".equals(mapData.get("flag"))){
			mapData.put("flag", "COMPANY");
			dealCompany(mapData);
			mapData.put("flag", "PRODUCT");
		}
		
		//1.检查数据是否存在
		boolean success=checkExistId(mapData);
		//2.更新或修改数据
		updateOrAddData(success,mapData);
		
		//如果处理的是车型数据，则需要处理车型参数数据
		if("TYPE".equals(mapData.get("flag"))){
			dealParameter(mapData);
		}
		
	}
	
	//检查数据是否存在
	private boolean checkExistId(Map<String,String> mapData){
    	boolean success=false;
    	String tableStr=mapData.get("flag");
    	List<Map<String,String>> listMap=Dao.selectList(xmlPath+"checkExist"+captureName(tableStr),mapData);
    	if(listMap.size()>0){
			success=true;
		}else{
			success=false;
		}
		return success;
	}
	 
	//添加或更新数据
	private void updateOrAddData(boolean success,Map<String,String> mapData){
		//获取表名
		String tableStr=mapData.get("flag");
			if(success){
				//更新数据
				String strUpdateParam="update"+captureName(tableStr);
				Dao.update(xmlPath+strUpdateParam,mapData);
			}else{
				//添加数据
				String strAddParam="add"+captureName(tableStr);
				Dao.insert(xmlPath+strAddParam,mapData);
			}
	}
	
	//更新或添加厂商数据
	private void dealCompany(Map<String,String> mapData){
		//1.检查厂商数据是否存在
		boolean success=checkExistId(mapData);
		//2.更新或修改厂商数据
		updateOrAddData(success,mapData);
	}
	
	//更新或添加车型参数数据
	private void dealParameter(Map<String,String> mapData){
		//查询车型数据
		List<Map<String,Object>> listTypeMap= Dao.selectList(xmlPath+"checkExistType",mapData);
		//获取车采传送过来的车型参数数据，并转成map
		@SuppressWarnings("unchecked")
		Map<String,String> parameterMap=(Map<String, String>)JSON.parse(JSONUtils.toJSONString(mapData.get("PARAMETER")));
		
		if(listTypeMap.size()>0){
			//获取车型数据ID
			String type_id=listTypeMap.get(0).get("ID").toString();
			Map<String,Object> paramMap=new HashMap<String,Object>();
			paramMap.put("TYPE_ID",type_id);
			List<Map<String,String>> listParameterMap = null;
			for(String key:parameterMap.keySet()){
				paramMap.put("NAME_CN", convertCN(key));
				paramMap.put("NAME_EN", convertEN(key));
				paramMap.put("TYPE_PARAM", parameterMap.get(key));
				//检车车型参数是否存在
				listParameterMap=Dao.selectList(xmlPath+"checkExistParameter",paramMap);
				//更新或添加车型参数数据
				if(listParameterMap.size()>0){
					Dao.update(xmlPath+"updateParameter",paramMap);
				}else{
					Dao.insert(xmlPath+"addParameter",paramMap);
				}
			}
		}
	}
	
	//首字母大写
    private String captureName(String name) {
        name = name.substring(0, 1) + name.substring(1).toLowerCase();
       return  name;
    }
    
    //车型参数转换
    private String convertCN(String key){
    	String result="";
    	switch (key){
    		case "MODEL_GEARBOX_NAME":
    			result="变速箱";
    			return result;
    		case "MODEL_STYLE_NAME":	
    			result="款式";
    			return result;
    		case "MODEL_SEATS_NAME":
    			result="座位数";
    			return result;
    		case "MODEL_CAR_TYPE":
    			result="车型";
    			return result;
    		case "MODEL_DISPLACEMENT_NAME":
    			result="排气量";
    			return result;
    		default :
    			return result;
    	}
    	
    }
    
    //车型参数转换
    private String convertEN(String key){
    	String result="";
    	switch (key){
    		case "MODEL_GEARBOX_NAME":
    			result="Stand Way";
    			return result;
    		case "MODEL_STYLE_NAME":	
    			result="Model Year";
    			return result;
    		case "MODEL_SEATS_NAME":
    			result="Seats";
    			return result;
    		case "MODEL_CAR_TYPE":
    			result="Wheelbase";
    			return result;
    		case "MODEL_DISPLACEMENT_NAME":
    			result="Displacement";
    			return result;
    		default :
    			return result;
    	}
    	
    }
}
