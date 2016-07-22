package com.pqsoft.util;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Preconditions;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.poi.hssf.usermodel.*;


/**
 * excel操作工具类
 * excel -->  map   ReadExcel
 * map   -->  excel exportExcel
 * 
 * sql   -->  excel exportExcel
 * 
 * @author chenglongda
 *
 */
public class ExcelUtil {
	
	public static String sql = "";

	public static void main(String[] args) throws SQLException {
		//String sql = "select khbh 客户编号,zzdm 厂商,khmc 供应商全称,EMS_TO_NAME 收件人 ,EMS_TO_PHONE 电话,EMS_TO_DW 单位,EMS_TO_ADDR 地址, EMS_TO_CITY 城市,EMS_TO_CODE 邮编  from dl_mpxx order by khbh";
//		sql = "";
//		String fileName = "E:\\供应商邮寄信息导出.xls";
//		(new ExcelUtil()).exportExcel(sql, fileName);
		//Date data = new Date();
//		Map<Integer,List<List<String>>> map = (new ExcelUtil()).ReadExcel("现有保单以及发票档案2011.xls");
//		System.out.println(map);
		ReflectionUtil.printSetters(HSSFCellStyle.class, "destCellStyle");
	}
	
	
	/**
	 * 读取excel 进入map
	 * @param fileName excel路径
	 */
	public static Map<Integer,List<List<String>>> ReadExcel(String fileName){
		
		Map<Integer,List<List<String>>> map = new HashMap<Integer,List<List<String>>>();//每个excel
		List<List<String>> lists = null;//每个sheet
		List<String> list = null;//每行
			
		try {
			Workbook book = Workbook.getWorkbook(new File(fileName));
			Sheet[] sheets = book.getSheets();
			Sheet sheet = null;
			Cell cell = null;
			for(int i = 0; i < sheets.length ; i++){
				lists = new ArrayList<List<String>>();//excel每个sheet
				sheet = sheets[i];
				for(int j = 0 ; j < sheet.getRows() ; j++){//行
					list = new ArrayList<String>();//sheet中每行
					for(int k = 0 ; k < sheet.getColumns() ; k++){//列
						cell = sheet.getCell(k, j);//k列j行
						list.add(cell.getContents().trim());
					}
					lists.add(list);
				}
				map.put(i, lists);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 导出excel sql 到excel
	 * @param filename
	 * @param sql
	 */
	/*public static  void exportExcel(String sql,String fileName){
		try {
			
			WritableWorkbook book = Workbook.createWorkbook(new File(fileName));
			WritableSheet sheet = book.createSheet("sss",0);
			Label l = null;
			
			WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK); 
			WritableCellFormat detFormat = new WritableCellFormat (detFont);
			detFormat.setAlignment(Alignment.CENTRE);
			detFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			detFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			detFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			detFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			
			ResultSet rs = DBUtil.query(sql);

			ResultSetMetaData md = rs.getMetaData();
			int count = md.getColumnCount();
			//标题
			for(int i = 1 ; i <= count ; i++){
				sheet.addCell(new Label(i-1,0,md.getColumnName(i), detFormat));
			}
			
			int j = 1;
			//内容
			while(rs.next()){
				for(int i = 1 ; i <= count ; i++){
					l = new Label(i-1,j,rs.getString(i), detFormat);
					sheet.addCell(l);
				}
				j++;
			}
		
			book.write();
			book.close();
			System.out.println("请到E盘查找");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	/**
	 * 导出excel map到excel
	 * @param fileName
	 * @param map
	 */
	public  static void exportExcel(Map<Integer,List<List<String>>> map,String fileName){
		try {
			WritableWorkbook book = Workbook.createWorkbook(new File(fileName));
			WritableSheet sheet = null;
			
			WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK); 
			WritableCellFormat detFormat = new WritableCellFormat (detFont);
			detFormat.setAlignment(Alignment.CENTRE);
			detFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			detFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			detFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			detFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			
			Label l = null;
			for(Entry<Integer, List<List<String>>> entry : map.entrySet()){
				sheet = book.createSheet("sheet1", entry.getKey());
				List<List<String>> lists = entry.getValue();
				for(int i = 0 ; i < lists.size() ; i++){
					for(int j = 0 ; j < lists.get(i).size() ; j++){
						l = new Label(j,i,lists.get(i).get(j), detFormat);
						sheet.addCell(l);
					}
				}
			}
			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 执行查询 sql 到map
	 * @param sql
	 */
	/*public static  Map<Integer,List<List<String>>> Query(String sql){
		try {
			//返回结果集
			Map<Integer,List<List<String>>> map = new HashMap<Integer,List<List<String>>>();//每个excel
			List<List<String>> lists = new ArrayList<List<String>>();//每个sheet这里只有一个sheet
			List<String> list = null;//每行
			
			//数据源
			ResultSet rs = DBUtil.query(sql);
			ResultSetMetaData md = rs.getMetaData();
			int count = md.getColumnCount();
			
			//标题
			for(int i = 1 ; i <= count ; i++){
				list = new ArrayList<String>();//sheet中标题
				list.add(md.getColumnName(i));
			}
			lists.add(list);
			//内容
			while(rs.next()){
				list = new ArrayList<String>();//sheet中每行
				for(int i = 1 ; i <= count ; i++){
					list.add(rs.getString(i));
				}
				lists.add(list);
			}
			map.put(0,lists);
			return map ;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}*/

	/**
	 * 将源workbook中的数据合并到目的workbook中，忽略指定的行开头数及列开头数（即从开头数之后的行、列开始复制）
	 *
	 * @param destWorkbook 数据复制目的
	 * @param srcWorkbook 数据复制源
	 * @param rowOffset 行偏移量
	 * @param columnOffset 列偏移量
     * @return sheet页名称对行号变更的映射
     */
	public static Map<String, Map<Integer, Integer>> merge(HSSFWorkbook destWorkbook, HSSFWorkbook srcWorkbook, int rowOffset, int columnOffset){
		Preconditions.checkNotNull(destWorkbook, "目的工作簿对象为空！");
		Preconditions.checkNotNull(srcWorkbook, "源工作簿对象为空！");
		HSSFSheet destSheet;
		HSSFSheet srcSheet;
		Map<String, Map<Integer, Integer>> sheetRowNoMap = new HashMap<>();
		for(int i = 0; i < srcWorkbook.getNumberOfSheets(); i++){
			srcSheet = srcWorkbook.getSheetAt(i);
			destSheet = destWorkbook.getSheet(srcSheet.getSheetName());
			sheetRowNoMap.put(destSheet.getSheetName(), mergeSheet(destSheet, srcSheet, rowOffset, columnOffset));
		}
		return sheetRowNoMap;
	}

	/**
	 * 将源sheet中的数据合并到目的sheet中，忽略指定的行偏移量及列偏移量（即从开头数之后的行、列开始复制）
	 *
	 * @param destSheet 数据复制目的
	 * @param srcSheet 数据复制源
	 * @param rowOffset 行偏移量
	 * @param columnOffset 列偏移量
     * @return 被合并的数据的行号对合并后的数据的新行号的映射
     */
	private static Map<Integer, Integer> mergeSheet(HSSFSheet destSheet, HSSFSheet srcSheet, int rowOffset, int columnOffset) {
		// 总列数
		int columns;
		HSSFRow srcRow;
		HSSFRow destRow;
		HSSFCell srcCell;
		HSSFCell destCell;
		Map<Integer, Integer> rowNoMap = new HashMap<>();
		if(destSheet != null && srcSheet != null){
			// 原工作表从行偏移量后开始，目的工作表从最大行后开始
			for(int i = rowOffset, j = destSheet.getLastRowNum() + 1; i <= srcSheet.getLastRowNum(); i++, j++){
				srcRow = srcSheet.getRow(i);
				destRow = destSheet.createRow(j);
				rowNoMap.put(i + 1, j + 1);
				// 初始化总列数
				columns = srcRow.getLastCellNum();
				// 从列偏移量后开始获取数据
				for(int k = columnOffset; k < columns; k++){
					srcCell = srcRow.getCell(k);
					if(srcCell == null){
						continue;
					}
					destCell = destRow.createCell(k);
					copyCell(destCell, srcCell);
				}
			}
		}
		return rowNoMap;
	}

	/**
	 * 将原单元格复制到目标单元格中，包括格式，批注，数据
	 *
	 * @param destCell 目的单元格
	 * @param srcCell 源单元格
     */
	private static void copyCell(HSSFCell destCell, HSSFCell srcCell) {
		// 复制格式
		destCell.getCellStyle().cloneStyleFrom(srcCell.getCellStyle());
		// 复制批注
		HSSFComment cellComment = srcCell.getCellComment();
		if(cellComment != null){
			destCell.setCellComment(cellComment);
		}
		// 复制数据类型
		Integer cellType = srcCell.getCellType();
		destCell.setCellType(cellType);
		// 复制数据
		switch (cellType){
			case HSSFCell.CELL_TYPE_BOOLEAN:
				destCell.setCellValue(srcCell.getBooleanCellValue());
			case HSSFCell.CELL_TYPE_ERROR:
				destCell.setCellErrorValue(srcCell.getErrorCellValue());
			case HSSFCell.CELL_TYPE_FORMULA:
				destCell.setCellFormula(srcCell.getCellFormula());
			case HSSFCell.CELL_TYPE_NUMERIC:
				if(HSSFDateUtil.isCellDateFormatted(srcCell)){
					destCell.setCellValue(srcCell.getDateCellValue());
				}else{
					destCell.setCellValue(srcCell.getNumericCellValue());
				}
				break;
			case HSSFCell.CELL_TYPE_STRING:
				destCell.setCellValue(srcCell.getRichStringCellValue());
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				destCell.setCellValue("");
		}
	}

}
