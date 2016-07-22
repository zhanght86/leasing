package com.pqsoft.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class PoiExcelUtil {

	public static void excelTemplateExcle(InputStream is,OutputStream os,Map<String,Object> modelData) throws Exception{
		 HSSFWorkbook wb = new HSSFWorkbook(is);
		 doExcelTemplateMerge(wb, modelData);
		 deleteEmptyRow(wb, modelData);
		 wb.write(os);
		 os.flush();
		 os.close();
		 is.close();
	}
	

	private static void deleteEmptyRow(HSSFWorkbook wb,Map<String, Object> modelData) {
		HSSFSheet sheet = wb.getSheetAt(0);
		for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			HSSFRow row = sheet.getRow(rowIndex);
			if (null == row)
				continue;
			boolean flag = false;// 定义标志位，如果查询一行的所有单元格都为空的则为false
			for (int colIndex = 0; colIndex < row.getLastCellNum(); colIndex++) {
				HSSFCell cell = row.getCell(colIndex);
				//String value = cell.getStringCellValue();
				if (cell!=null&&!"".equals(cell.getStringCellValue().trim())) {
					flag = true;
					break;
				}
			}
			if (!flag) {// 如果一行内的所有单元格都循环结束未找到有内容的则删除当前行
				sheet.removeRow(row);
			}
		}

	}


	public static HSSFWorkbook doExcelTemplateMerge(HSSFWorkbook wb,Map<String,Object> modelData) throws Exception
	 {
		 Map<String,Object> modelData1 = new HashMap<String, Object>();
		   for(String key:modelData.keySet())
		   {
			   modelData1.put(key.toLowerCase(), modelData.get(key));
		   }
		   modelData.putAll(modelData1);
		   for(int sheetIndex=0;sheetIndex<wb.getNumberOfSheets();sheetIndex++)
		   {
			   HSSFSheet sheet = wb.getSheetAt(sheetIndex);
			  // System.out.println("getPhysicalNumberOfRows:   "+sheet.getPhysicalNumberOfRows());
			  // System.out.println("getLastRowNum:  "+sheet.getLastRowNum());
			   //System.out.println("getNumMergedRegions:  "+sheet.getNumMergedRegions());
			   
			   for(int rowIndex=0;rowIndex<=sheet.getLastRowNum();rowIndex++)
			   {
				  HSSFRow row =  sheet.getRow(rowIndex);
				//  System.out.println(row+":"+rowIndex);
				  if(null==row)continue;
				//  System.out.println(row.getRowNum());
				  for(int colIndex=0;colIndex<row.getLastCellNum();colIndex++)
				  {
					  HSSFCell cell = row.getCell(colIndex);
					  if(null==cell)continue;
				//	  System.out.println("hello:"+":row:"+row.getRowNum()+":col:"+cell.getCellNum()+":value:"+cell.getStringCellValue());
					  switch(cell.getCellType())
					  {
					      case HSSFCell.CELL_TYPE_STRING:
					      {
					    	  String cellValue = StringUtils.nullToString(cell.getStringCellValue()).trim().replaceAll("\\s", "");
					    	  if(!cellValue.isEmpty())
					    	  {	  
					    		  //只有一个参数
					    		  if((cellValue.matches("^\\{.+\\}$"))||((cellValue.matches("^\\{.+\\}%$"))))
					    		  {
						    		  changeValue(cell,cellValue,modelData);
					    		  }
					    		//考虑一个excel中多个参数情况，此种情况，所有的的标记都用{string：key}并且cell格式都为string
					    		  /*else if(cellValue.indexOf("{string:")>-1){
					    			  String [] argArr = cellValue.split("\\{string:");
					    				
					    			  String afterStr =argArr[0].trim();
					    			  for(int i = 1;i<argArr.length;i++){
					    				  String temStr=argArr[i].trim();
					    				  String tem1=temStr.substring(0,temStr.indexOf("}"));
					    				  String tem2=temStr.substring(temStr.indexOf("}")+1,temStr.length());
					    				  afterStr+=StringUtil.nullToString(modelData.get(tem1.trim())).toString()+tem2;
					    			  }
					    			  cell.setCellValue(afterStr);
					    		  }*/
					    		  else
					    		  {
					    			  cell.setCellValue(new TokenReplaceTextManipulator().manipulate(new StringBuffer(cell.getStringCellValue().replaceAll("string:","")), modelData).toString());
					    		  }
					    	  }
					    	  break;
					      }
					      default:break;
					  }
				  }
			   }
			   sheet.setForceFormulaRecalculation(true);
		   }
		   return wb;
	   }
	 /**
	  * 替换原cell中对应的参数
	  * @param cell
	  * @param cellValue
	 * @throws Exception 
	  */
	 private static void changeValue(HSSFCell cell, String cellValue,Map<String,Object> modelData) throws Exception {
		 
		 
		
			  boolean isPercentValue = false;
			  if((cellValue.matches("^\\{.+\\}%$")))
			  {
				  isPercentValue = true;
				  cellValue = cellValue.substring(0,cellValue.length()-1);
			  }
			  cellValue = cellValue.replaceAll("：",":").toLowerCase();
			  String []cellValueArr = cellValue.split(":");
			  String typeName =  cellValueArr[0].replaceAll("（","\\(").replaceAll("）","\\)");
			  String fieldName = cellValueArr[1].substring(0,cellValueArr[1].length()-1);
   		  if(typeName.startsWith("{string"))//{string:map对应key}
   		  {
   			  cell.setCellValue(StringUtils.nullToString(modelData.get(fieldName)));
   		  }
   		  else if(typeName.startsWith("{int"))//{int:map对应key}
   		  {
   			  String value = StringUtils.nullToString(modelData.get(fieldName));
   			  if(isPercentValue)
   			  {
   				  cell.setCellValue(value+"%");
   			  }
   			  else
   			  {
	    			  if(value.isEmpty())
	    			  {
	    				  cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
	    			  }
	    			  else
	    			  {
	    				  cell.setCellValue(StringUtils.parseDecimal(value,0));
	    			  }
   			  }
   		  }
   		  else if(typeName.startsWith("{double")){//{double:map对应key}
   			  int prevIndex = typeName.indexOf("{double(");
   			  int scale = Integer.parseInt(typeName.substring(prevIndex+8,typeName.length()-1));
   			  String value = StringUtils.nullToString(modelData.get(fieldName));
   			  if(isPercentValue)
   			  {
	    			  if(value.isEmpty())
	    			  {
	    				  cell.setCellValue("%");
	    			  }
	    			  else
	    			  {
	    				  cell.setCellValue(StringUtils.parseDecimal(value, scale)+"%");
	    			  }
   			  }
   			  else
   			  {
	    			  if(value.isEmpty())
	    			  {
	    				  cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
	    			  }
	    			  else
	    			  {
	    				  cell.setCellValue(StringUtils.parseDecimal(value, scale));
	    			  }
   			  }
   		 }
   		 else if(typeName.startsWith("{list"))//{list(list对应mapkey@@_@@行号'从0开始'@@字段类型):对应map字段}
   		 {
   			  String contentStr = typeName.substring(typeName.indexOf("{list(")+6,typeName.length()-1);
   			  String []keyArr = contentStr.split("@@_@@");
   			  String listKey  = keyArr[0];
   			  int currentIndex  = Integer.parseInt(keyArr[1]);
   			  String currentTypeName  = "{"+keyArr[2];
   			  String realCellValue="";
   			  
   			  if(null!=modelData.get(listKey))
   			  {
   				  List<Map<String,Object>> l = (List<Map<String,Object>>)modelData.get(listKey);
   				  if(l.size()>currentIndex)
   				  {
   					  realCellValue = StringUtils.nullToString((l.get(currentIndex)).get(fieldName));
   				  }
   			  }
   			  if(realCellValue.isEmpty())
   			  {
   				  if(isPercentValue)
   				  {
   					  cell.setCellValue("%");
   				  }
   				  else
   				  {
   					  cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
   					  //cell.getRow().getSheet().removeRow(cell.getRow());
   				  }
   			  }
   			  else
   			  {
		    		  if(currentTypeName.startsWith("{string"))//{string:map对应key}
		    		  {
		    			  if(isPercentValue)
		    			  {
		    				  cell.setCellValue(realCellValue+"%");
		    			  }
		    			  else
		    			  {
		    				  cell.setCellValue(realCellValue);
		    			  }
		    		  }
		    		  else if(currentTypeName.startsWith("{int"))//{int:map对应key}
		    		  {
		    			  if(isPercentValue)
		    			  {
		    				  cell.setCellValue(realCellValue+"%");
		    			  }
		    			  else
		    			  {
			    			  cell.setCellValue(StringUtils.parseDecimal(realCellValue,0));
		    			  }
		    		  }
		    		  else if(currentTypeName.startsWith("{double")){//{double:map对应key}
		    			  int prevIndex = currentTypeName.indexOf("{double(");
		    			  int scale = Integer.parseInt(currentTypeName.substring(prevIndex+8,currentTypeName.length()-1));
		    			  if(isPercentValue)
		    			  {
		    				  cell.setCellValue(StringUtils.parseDecimal(realCellValue, scale)+"%");
		    			  }
		    			  else
		    			  {
		    				  cell.setCellValue(StringUtils.parseDecimal(realCellValue, scale));
		    			  }
		    		 }
   		 }
		  }		
	}
//	PoiExcelUtil.excelTemplateMerge(new FileInputStream(WebUtil.getWebContextPath()+StringUtil.getFilePathString("\\WEB-INF\\templates\\excels\\模板-A16起租说明书.xls")), os, modelData);

}
