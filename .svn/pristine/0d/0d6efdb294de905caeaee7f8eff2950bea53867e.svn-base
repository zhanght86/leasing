package com.pqsoft.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

public class JsonUtil {

	/**
	 * 将json对象转换成Map
     * @param jsonObject json对象
     * @return Map对象
	 * @author 付玉龙  fuyulong47@foxmail.com
	 * @date 2013-8-30  下午09:56:19
	 */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(JSONObject jsonObject)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        Iterator<String> iterator = jsonObject.keys();
        String key = null;
        String value = null;
        while (iterator.hasNext())
        {
            key = iterator.next();
            value = jsonObject.getString(key);
            result.put(key, value);
        }
        return result;
    }
    
    /**
     * 将对象转化成JSON对象
    * @param @param obj
    * @param @return
    * @return JSONObject
    * @throws
    * @date 2014年7月2日 上午9:41:13 
    * @author WuYanFei
     */
    public static JSONObject toJson(Object obj){
    	return JSONObject.fromObject(obj) ;
    } 
	
}
