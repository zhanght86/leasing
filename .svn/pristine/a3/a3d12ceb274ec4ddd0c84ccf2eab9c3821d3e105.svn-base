package com.pqsoft.dateConfig.service;

import java.util.Map;

import jxl.common.BaseUnit;

import org.apache.log4j.Logger;

import com.pqsoft.dataDictionary.service.DataDictionaryService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.PageUtil;

public class DateConfigService {
	private static Logger logger = Logger
			.getLogger(DataDictionaryService.class);

	public DateConfigService() {
	}

	public int insertDateConfig(Map m) {
		return Dao.update("DateDictionary.insertDateConfig", m);
	}

	public int deleteDateConfig(Map m) {
		return Dao.delete("DateDictionary.deleteDateConfig", m);
	}
	
	public Map getDateConfigData(Map m) {
		return Dao.selectOne("DateDictionary.getDateConfigData", m);
	}

	public Page getDateSiteData(Map<String, Object> m) {
		return PageUtil.getPage(m, "DateDictionary.getDateSiteData","DateDictionary.getDateSiteDataConut");
	}

	public int addDateSite(Map<String, Object> m) {
		return Dao.insert("DateDictionary.addDateSite", m);
	}
	public int deleteDate(Map<String, Object> m) {
		return Dao.delete("DateDictionary.deleteDate", m);
	}
	public int modifyDate(Map<String, Object> m) {
		return Dao.update("DateDictionary.modifyDate", m);
	}

}
