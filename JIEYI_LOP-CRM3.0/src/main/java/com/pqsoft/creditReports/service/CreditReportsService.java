package com.pqsoft.creditReports.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;

public class CreditReportsService {

	private String basePath = "creditReports.";
	
	public Page getPageData(Map<String,Object> param){
		return PageUtil.getPage(param, basePath+"getPageData", basePath+"getPageDataCount");
	}
	
	public Page getFileData(Map<String,Object> param){
		return PageUtil.getPage(param, basePath+"getFileData", basePath+"getPageDataCount");
	}
	
	public int addCreditReports(Map<String,Object> param){
		return Dao.insert(basePath+"addCreditReports",param);
	}
	
	public Map<String,Object> findFileById(Map<String,Object> param){
		return Dao.selectOne(basePath+"findFileById", param);
	}
	
	public int deleteFile(Map<String,Object> param){
		return Dao.delete(basePath+"deleteFile",param);
	}
	
	public HSSFWorkbook getDataList(Map<String,Object> param){
		List<Map<String,Object>> dataList = Dao.selectList(basePath+"getDataList", param);
		
		String[] excelHeader = { "ID", "文件状态 ", "客户名称 ", "身份证号码/组织机构代码号", "创建时间 ", "流程节点名称", "状态"
				, "业务类型", "项目编号 "};   
		// 单元格列宽  
		int[] excelHeaderWidth = { 100, 100, 150, 220, 100, 100, 100, 100, 120};  


	    HSSFWorkbook wb = new HSSFWorkbook();    
	    HSSFSheet sheet = wb.createSheet("征信报告");    
	    HSSFRow row = sheet.createRow((int) 0);    
	    HSSFCellStyle style = wb.createCellStyle();    
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    Font font = wb.createFont();  
	    font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体 
	    font.setFontName("黑体");
	    font.setFontHeightInPoints((short) 10);//设置字体大小
	    style.setFont(font);
	    for (int i = 0; i < excelHeader.length; i++) {    
	         HSSFCell cell = row.createCell(i);    
	         cell.setCellValue(excelHeader[i]);    
	         cell.setCellStyle(style);    
	         sheet.autoSizeColumn(i);
	         sheet.setColumnWidth(i, 32 * excelHeaderWidth[i]); 
	    }    
	 
	    for (int i = 0; i < dataList.size(); i++) {    
	         row = sheet.createRow(i + 1);    
	         Map<String,Object> map = dataList.get(i); 
	         if(map!=null){
	        	 if(map.get("ID")!=null&&!"".equals(map.get("ID").toString())){
	        		 row.createCell(0).setCellValue(map.get("ID").toString());    
	        	 }
	        	 if(map.get("FCR_ID")!=null&&!"".equals(map.get("FCR_ID").toString())){
	        		 row.createCell(1).setCellValue(map.get("FCR_ID").toString());
	        	 }
	        	 if(map.get("CUST_NAME")!=null&&!"".equals(map.get("CUST_NAME").toString())){
	        		 row.createCell(2).setCellValue(map.get("CUST_NAME").toString()); 
	        	 }
	        	 if(map.get("ID_CARD_NO")!=null&&!"".equals(map.get("ID_CARD_NO").toString())){
	        		 row.createCell(3).setCellValue(map.get("ID_CARD_NO").toString());
	        	 }
	        	 if(map.get("CREATE_TIME")!=null&&!"".equals(map.get("CREATE_TIME").toString())){
	        		 row.createCell(4).setCellValue(map.get("CREATE_TIME").toString());
	        	 }
	        	 if(map.get("LCNAME")!=null&&!"".equals(map.get("LCNAME").toString())){
	        		 row.createCell(5).setCellValue(map.get("LCNAME").toString());
	        	 }
	        	 if(map.get("STATUS_NAME")!=null&&!"".equals(map.get("STATUS_NAME").toString())){
	        		 row.createCell(6).setCellValue(map.get("STATUS_NAME").toString());
	        	 }
	        	 if(map.get("PLATFORM_NAME")!=null&&!"".equals(map.get("PLATFORM_NAME").toString())){
	        		 row.createCell(7).setCellValue(map.get("PLATFORM_NAME").toString());
	        	 }
	        	 if(map.get("PRO_CODE")!=null&&!"".equals(map.get("PRO_CODE").toString())){
	        		 row.createCell(8).setCellValue(map.get("PRO_CODE").toString());
	        	 }
	         }
	    }    
	    return wb;    
	}
}
