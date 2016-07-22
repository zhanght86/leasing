package com.pqsoft.creditData.service;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * 读取配置文件的业务处理
 *
 * @className CreditDataService
 * @author 钟林俊
 * @version V1.0 2016年4月21日 下午4:04:32
 */
public abstract class ConfigService {

    public ConfigService(){
        reload();
    }

    protected abstract void reload();

    /**
     * 从配置中加载数据
     *
     * @param configuration 配置对象
     * @param prefix key名的前缀，以"."来区分
     * @return key-value链接映射
     */
    protected Map<String, String> load(Configuration configuration, String prefix){
        try{
            Map<String, String> map = new LinkedHashMap<>();
            Iterator<String> keys = configuration.getKeys();
            while (keys.hasNext()) {
                String key = keys.next();
                //符合前缀要求的key才会放入容器
                if(prefix.equals(StringUtils.substringBefore(key, "."))){
                    map.put(StringUtils.substringAfter(key, "."), configuration.getString(key));
                }
            }
            return map;
        } catch (NullPointerException e){
            throw new RuntimeException("配置文件加载失败！");
        }
    }
}
