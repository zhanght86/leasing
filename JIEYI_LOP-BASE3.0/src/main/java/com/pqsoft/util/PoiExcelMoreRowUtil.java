package com.pqsoft.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class PoiExcelMoreRowUtil {
	
	public static void excelTemplateExcle(InputStream is,OutputStream os,Map<String,Object> modelData) throws Exception{
		 HSSFWorkbook wb = new HSSFWorkbook(is);
		 doExcelTemplateMerge(wb, modelData);
		 wb.write(os);
		 os.flush();
		 os.close();
		 is.close();
	}
	
	
	public static HSSFWorkbook doExcelTemplateMerge(HSSFWorkbook wb,Map<String,Object> modelData) throws Exception
	 {
		List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
		
		if(modelData !=null && modelData.get("listData")!=null && !modelData.get("listData").equals("")){
			listData=(List<Map<String,Object>>)modelData.get("listData");
		}
		
		String[] tilteCell=(String[])modelData.get("tilteCell");
		
		int rowNum=Integer.parseInt(modelData.get("rowNum").toString());
		
		
		 for(int sheetIndex=0;sheetIndex<wb.getNumberOfSheets();sheetIndex++)
		 {
			 HSSFSheet sheet = wb.getSheetAt(sheetIndex);
			 
//			
			 
			 int listNum=listData.size();
			 
			 sheet.shiftRows(rowNum, sheet.getLastRowNum(), listNum,true,true);

			 sheet.createRow(rowNum);
			 
			 HSSFRow targetRow = sheet.getRow(rowNum-1);
			 
			 addSyteml(wb, sheet, rowNum, rowNum+listNum-1, targetRow.getFirstCellNum(), targetRow.getLastCellNum()-1);
			 
			 System.out.println("----------------listNum="+listNum+"------------------tilteCell="+tilteCell.length);
			 //写数据
			 for(int rowIndex=0;rowIndex<=(listNum-1);rowIndex++){
				 HSSFRow row =  sheet.getRow(rowIndex+rowNum);
				 Map<String,Object> mapd=listData.get(rowIndex);
//				 try {
					 System.out.println("----------------targetRow.getLastCellNum()="+targetRow.getLastCellNum());
					 for(int colIndex=0;colIndex<(targetRow.getLastCellNum()-1);colIndex++){
						 System.out.println("------------colIndex="+colIndex);
						 HSSFCell cell = row.getCell(colIndex);
						 if(tilteCell[colIndex] !=null && !tilteCell[colIndex].equals("")){
							 String FILED_NAME=tilteCell[colIndex]+"";
							 cell.setCellValue(StringUtils.nullToString(mapd.get(FILED_NAME)));
						 }
						 
					 }
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			 }
			 
			 //处理excel中固定列值
			 if(modelData !=null && modelData.get("GD") !=null){
				 List listGD=(List)modelData.get("GD");
				 for(int aa=0;aa<listGD.size();aa++){
					 Map mapGD=(Map)listGD.get(aa);
					 if(mapGD !=null){
						 int ROWCOL=Integer.parseInt(mapGD.get("ROWCOL")+"");
						 int CELLCOL=Integer.parseInt(mapGD.get("CELLCOL")+"");
						 String CELLVALUE=mapGD.get("CELLVALUE")+"";
						 
						 HSSFRow row =  sheet.getRow(ROWCOL);
						 HSSFCell cell = row.getCell(CELLCOL);
						 cell.setCellValue(CELLVALUE);
					 }
				 }
			 }
			 
			 
		 }
		
		   return wb;
	   }
	
	
	/**
	* 设置动态数据的样式
	*
	*/
	private static void addSyteml(HSSFWorkbook workbook,HSSFSheet aSheet,int rowFrom,int rowTo,int columnFrom,int columnTo){
	HSSFCellStyle cloneStyle = workbook.createCellStyle();
	cloneStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	cloneStyle.setBorderLeft((short)0);
	cloneStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	cloneStyle.setBorderBottom((short)0);
	cloneStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        for(int i=rowFrom;i<=rowTo;i++){
//	        	try {
	        		HSSFRow row_temp = aSheet.getRow(i);
	        		if(row_temp == null) row_temp= aSheet.createRow(i);
	        		for(int j=columnFrom;j<=columnTo;j++){
	        			HSSFCell cell_temp = row_temp.getCell(j);
	        			if(cell_temp ==null ){
	        				cell_temp = row_temp.createCell(j);
	        			}
	        			cell_temp.setCellStyle(cloneStyle);
	        		}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
	        }
	} 

}
