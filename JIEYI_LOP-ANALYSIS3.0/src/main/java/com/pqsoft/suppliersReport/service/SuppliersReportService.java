package com.pqsoft.suppliersReport.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class SuppliersReportService {

	private String xmlPath = "suppliersReport.";
	
	public Page pageData(Map<String,Object> map){
		return PageUtil.getPage(map, xmlPath+"pageData", xmlPath+"pageDataCount");
	}
}
