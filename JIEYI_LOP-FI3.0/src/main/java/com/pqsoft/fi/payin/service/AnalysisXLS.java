package com.pqsoft.fi.payin.service;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

class AnalysisXLS extends AbstractAnalysisExcel {

	public AnalysisXLS(String f) {
		super(f);
	}

	@Override
	public Workbook getWorkbook() throws Exception {
		return new HSSFWorkbook(new FileInputStream(new File(filename)));
	}

}
