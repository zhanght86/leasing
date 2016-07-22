package com.pqsoft.white.action;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExportExcelUtils {

	/**
	 * @Title: exportExcel
	 * @Description: 导出Excel的方法
	 * @author: evan @ 2014-01-09 
	 * @param workbook 
	 * @param sheetNum (sheet的位置，0表示第一个表格中的第一个sheet)
	 * @param sheetTitle  （sheet的名称）
	 * @param headers    （表格的标题）
	 * @param result   （表格的数据）
	 * @param out  （输出流）
	 * @throws Exception
	 */
	public void exportHSSFExcel(HSSFWorkbook workbook, int sheetNum, String sheetTitle, String[] headers, 
			List<List<String>> result, OutputStream out) throws Exception {
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(sheetNum, sheetTitle);
		// 设置表格默认列宽度为20个字节
		sheet.setDefaultColumnWidth(20);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle(); // 生成标题样式
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index); // 设置单元格背景颜色
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 
//		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 边框线
//		style.setBorderLeft(HSSFCellStyle.BORDER_THIN); // 边框线
//		style.setBorderRight(HSSFCellStyle.BORDER_THIN); // 边框线
//		style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 边框线
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 设置水平居中
		// 生成一个字体
		HSSFFont font = workbook.createFont(); // 创建字体样式
		font.setColor(HSSFColor.BLACK.index); // 字体颜色
		font.setFontHeightInPoints((short) 12); // 设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
		// 把字体应用到当前的样式
		style.setFont(font);

		// 指定当单元格内容显示不下时自动换行
		style.setWrapText(true);

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style); // 把样式设置给单元格
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text.toString()); // 单元格设置数据值
		}
		// 遍历集合数据，产生数据行
		if (result != null) {
			int index = 1;
			for (List<String> m : result) {
				row = sheet.createRow(index);
				int cellIndex = 0;
				for (String str : m) {
					HSSFCell cell = row.createCell(cellIndex);
					cell.setCellValue(str.toString());
					cellIndex++;
				}
				index++;
			}
		}
	}
	
	
	/**
	 * 
	 * @param workbook
	 * @param sheetNum
	 * @param sheetTitle
	 * @param headers
	 * @param result
	 * @param out
	 * @throws Exception
	 */
	public void exportSXSSFExcelSheetRunnable(SXSSFWorkbook workbook , List<String> headers, 
			List<List<Map<String, Object>>> outList, List<String> keyList, OutputStream out) throws Exception {
		
		ExecutorService threadPool = Executors.newFixedThreadPool(2);
		ExportTask[] myTask = new ExportTask[outList.size()];
		
		//循环存入数据
		int sIndex = 0;
		for (List<Map<String, Object>> outMapList : outList) {
			
			// 生成一个表格
			SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet();
			workbook.setSheetName(sIndex, String.valueOf(sIndex));
			// 设置表格默认列宽度为20个字节
			sheet.setDefaultColumnWidth(20);
			myTask[sIndex] = new ExportTask(sheet, headers ,outMapList ,keyList);
	    	threadPool.execute(myTask[sIndex]);
	    	sIndex++;
		}
		
        threadPool.shutdown();
        boolean flag = false;
        while (!flag) {
        	Thread.sleep(1000); // 1秒
			flag = threadPool.isTerminated();
			if (flag) {
				workbook.write(out);
				out.close();
			}
		}
	}
	
	/**
	 * 
	 * @param workbook
	 * @param sheetNum
	 * @param sheetTitle
	 * @param headers
	 * @param result
	 * @param out
	 * @throws Exception
	 */
	public void exportSXSSFExcelSheet(SXSSFWorkbook workbook , List<String> headers, 
			List<List<Map<String, Object>>> outList, List<String> keyList, OutputStream out) throws Exception {
		
		//循环存入数据
		int sIndex = 0;
		for (List<Map<String, Object>> outMapList : outList) {
			
			// 生成一个表格
			SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet();
			workbook.setSheetName(sIndex, String.valueOf(sIndex));
			// 设置表格默认列宽度为20个字节
			sheet.setDefaultColumnWidth(20);
			
			// 产生表格标题行
    		SXSSFCell cell = null;
    		SXSSFRow row = (SXSSFRow) sheet.createRow(0);
    		int headIndex = 0;
    		for (String s : headers) {
    			cell = (SXSSFCell) row.createCell(headIndex);
    			cell.setCellValue(s); // 单元格设置数据值
    			headIndex++;
    		}
    		
    		// 遍历集合数据，产生数据行
    		int index = 1;
			for (Map<String, Object> mapData : outMapList) {
				String dataMap = "";
				row = (SXSSFRow) sheet.createRow(index);
				int cellIndex = 0;
				for (int i = 0; i < keyList.size(); i++) {
					String key = keyList.get(i);
					if (!StringUtils.isEmpty(key)) {
						Object ov = mapData.get(key);
						if (ov != null) {
							dataMap = ov.toString();
						}
					} else {
						dataMap = "";
					}
					cell = (SXSSFCell) row.createCell(cellIndex);
					cell.setCellValue(dataMap);
					cellIndex++;
				}
				index++;
			}
	    	sIndex++;
		}
		
		workbook.write(out);
		out.close();
	}
	
	
	/**
	 * 
	 * @param workbook
	 * @param sheetNum
	 * @param sheetTitle
	 * @param headers
	 * @param result
	 * @param out
	 * @throws Exception
	 */
	public void exportSXSSFExcel(SXSSFWorkbook workbook, int sheetNum , List<String> headers, 
			List<Map<String, Object>> mapList, List<String> keyList, OutputStream out) throws Exception {
		
		// 生成一个表格
		SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet();
		workbook.setSheetName(sheetNum, String.valueOf(sheetNum));
		// 设置表格默认列宽度为20个字节
		sheet.setDefaultColumnWidth(20);
		
		// 产生表格标题行
		SXSSFCell cell = null;
		SXSSFRow row = (SXSSFRow) sheet.createRow(0);
		int headIndex = 0;
		for (String s : headers) {
			cell = (SXSSFCell) row.createCell(headIndex);
			cell.setCellValue(s); // 单元格设置数据值
			headIndex++;
		}
		
		// 遍历集合数据，产生数据行
		int index = 1;
		//循环存入数据
		if(mapList != null) {
		for (Map<String, Object> mapData : mapList) {
			String dataMap = null;
			row = (SXSSFRow) sheet.createRow(index);
			int cellIndex = 0;
			for (int i = 0; i < keyList.size(); i++) {
				String key = keyList.get(i);
				if (!StringUtils.isEmpty(key)) {
					Object ov = mapData.get(key);
					if (ov != null) {
						dataMap = ov.toString();
					}
				} else {
					dataMap = "";
				}
				cell = (SXSSFCell) row.createCell(cellIndex);
				cell.setCellValue(dataMap);
				cellIndex++;
			}
			index++;
		}
		}
		
//		workbook.write(out);
		//out.close();
	}
	
	
	public void exportSXSSFExcel(SXSSFWorkbook workbook, int sheetNum , List<String> headers, 
			List<Map<String, Object>> mapList, List<String> keyList) throws Exception {
		
		// 生成一个表格
		SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet();
		workbook.setSheetName(sheetNum, String.valueOf(sheetNum));
		// 设置表格默认列宽度为20个字节
		sheet.setDefaultColumnWidth(20);
		
		// 产生表格标题行
		SXSSFCell cell = null;
		SXSSFRow row = (SXSSFRow) sheet.createRow(0);
		int headIndex = 0;
		for (String s : headers) {
			cell = (SXSSFCell) row.createCell(headIndex);
			cell.setCellValue(s); // 单元格设置数据值
			headIndex++;
		}
		
		// 遍历集合数据，产生数据行
		int index = 1;
		//循环存入数据
		if(mapList != null) {
		for (Map<String, Object> mapData : mapList) {
			String dataMap = null;
			row = (SXSSFRow) sheet.createRow(index);
			int cellIndex = 0;
			for (int i = 0; i < keyList.size(); i++) {
				String key = keyList.get(i);
				if (!StringUtils.isEmpty(key)) {
					Object ov = mapData.get(key);
					if (ov != null) {
						dataMap = ov.toString();
					}
				} else {
					dataMap = "";
				}
				cell = (SXSSFCell) row.createCell(cellIndex);
				cell.setCellValue(dataMap);
				cellIndex++;
			}
			index++;
		}
		}
		
//		workbook.write(out);
		//out.close();
	}
	
	
	// 内部类
    class ExportTask implements Runnable {

    	private SXSSFSheet sheet;
    	private List<String> headers;
    	private List<Map<String, Object>> outMapList;
    	private List<String> keyList;
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    	
    	public ExportTask(SXSSFSheet sheet, List<String> headers,
    			List<Map<String, Object>> outMapList, List<String> keyList) {
    		this.sheet = sheet;
    		this.headers = headers;
    		this.outMapList = outMapList;
    		this.keyList = keyList;
    	}

    	public void run() {
    		// 产生表格标题行
    		SXSSFCell cell = null;
    		SXSSFRow row = (SXSSFRow) sheet.createRow(0);
    		int headIndex = 0;
    		for (String s : headers) {
    			cell = (SXSSFCell) row.createCell(headIndex);
    			cell.setCellValue(s); // 单元格设置数据值
    			headIndex++;
    		}
    		
    		// 遍历集合数据，产生数据行
    		int index = 1;
			for (Map<String, Object> mapData : outMapList) {
				String dataMap = "";
				row = (SXSSFRow) sheet.createRow(index);
				int cellIndex = 0;
				for (int i = 0; i < keyList.size(); i++) {
					String key = keyList.get(i);
					if (!StringUtils.isEmpty(key)) {
						Object ov = mapData.get(key);
						if (ov != null) {
							dataMap = ov.toString();
						}
					} else {
						dataMap = "";
					}
					cell = (SXSSFCell) row.createCell(cellIndex);
					cell.setCellValue(dataMap);
					cellIndex++;
				}
				index++;
			}
    	}
    }
}
