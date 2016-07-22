package com.pqsoft.util;
import com.pqsoft.skyeye.api.Dao;

public class ModuleUtil{
	
	/**
	 * 查询业务模块是否有自定义的字段
	 * @param TYPE
	 * @return
	 * @author:King 2014-3-28
	 */
	public boolean getModuleName(String moduleName){
		if(StringUtils.isBlank(moduleName)){
			return false;
		}
		else{
			int num=Dao.selectInt("customModule.queryIsBooleanByName", moduleName);
			if(num>0){
				return true;
			}
			else{
				return false;
			}
		}
			
	}

}
