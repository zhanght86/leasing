package com.pqsoft.fiReport.service;

import java.util.Map;

import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.PageUtil;

public class FiReportService {
	
	public Page getFiStartPageAjax(Map<String, Object> m) {
		return PageUtil.getPage(m, "fiReport.getFiStartData", "fiReport.getFiStartData_count");
	}

}
