package com.pqsoft.bpm.service;

import com.google.common.base.Strings;
import com.pqsoft.skyeye.api.Dao;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 读取配置文件
 *
 * @className ConfigService
 * @author 钟林俊
 * @version V1.0 2016年4月21日 下午4:04:32
 */
public class ConfigService {
    private static final Logger log = Logger.getLogger(ConfigService.class);

    private ConcurrentHashMap<String, String> config;

    public ConfigService() throws ConfigurationException {
        reload();
    }

    public String get(String key) {
        return config.get(key);
    }

    public void reload() throws ConfigurationException {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        loadProperties("jbpm/bpm.properties", map);
        config = map;
        if(!isDev()){
            String carmsgHost = Dao.selectOne("sys.selectCarmsgHost");
            config.put("host", carmsgHost);
        }
    }

    private void loadProperties(String filename, ConcurrentHashMap<String, String> map) throws ConfigurationException {
        try {
            Configuration configuration = new PropertiesConfiguration(filename);
            Iterator<String> keys = configuration.getKeys();
            while (keys.hasNext()) {
                String key = keys.next();
                map.put(key, configuration.getString(key));
            }
        } catch (ConfigurationException e) {
            log.error(String.format("load %s config fail", filename));
            throw e;
        }
    }

    public boolean isDev() {
        String isDev = config.get("isDev");
        return !Strings.isNullOrEmpty(isDev) && isDev.equals("true");
    }
}
