package com.pqsoft.fi.payin.service;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

class AnalysisXLSX extends AbstractAnalysisExcel {

	public AnalysisXLSX(String f) {
		super(f);
	}

	@Override
	public Workbook getWorkbook() throws Exception {
		return new XSSFWorkbook(new FileInputStream(new File(filename)));
	}

}
