package com.pqsoft.fi.payin.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

interface IAnalysisXLS {
	final static Logger logger = Logger.getLogger(IAnalysisXLS.class);

	List<Map<String, Object>> read() throws Exception;

}
