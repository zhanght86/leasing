package com.pqsoft.util;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.filter.ResMime;

/**
 * <p>
 * Title: POI导出excle 报表数据列表导出
 * </p>
 * <p>
 * Description: 报表导出公用
 * </p>
 * <p>
 * Company: 平强软件
 * </p>
 * 
 * @author 吴剑东 wujd@pqsoft.cn
 * @version 1.0
 */
public class POIExcelUtil {

	public void expDemo(Map<String, Object> params) {
		long a = System.currentTimeMillis();
		try {
			String[] str1 = new String[] { "测试1", "测试2", "测试3", "测试4", "测试5",
					"测试6", "测试7", "测试8", "测试9" };
			List<Map<String, Object>> dataList = Dao.selectList(
					"superTable.querySuperTableData", params);
			expExcel2007(null, "测试.xlsx", "测试", str1, null, dataList);

			System.out.println("\r执行耗时 : " + (System.currentTimeMillis() - a)
					/ 1000f + " 秒 ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出报表列表数据(停用的功能)
	 * 
	 * @Function: com.pqsoft.util.POIExcelUtil.expExcel2007
	 * @param fileName
	 *            导出文件名称
	 * @param sheetName
	 *            sheet名称
	 * @param titlesName
	 *            列标题数组
	 * @param sqlColumns sql字段
	 * @param dataList
	 *            导出数据
	 * 
	 * @author: 于奇
	 * 
	 * 
	 */
	public void expExcel2007(Map<String, Object> map, String fileName,
			String sheetName, String[] titlesName,int[] titlesIndex,
			List<Map<String, Object>> dataList) {
		//TODO 报表导出
	}
	
	/**
	 * 导出报表列表数据
	 * 
	 * @Function: com.pqsoft.util.POIExcelUtil.expExcel2007
	 * @param fileName
	 *            导出文件名称
	 * @param sheetName
	 *            sheet名称
	 * @param titlesName
	 *            列标题数组
	 * @param sqlColumns sql字段
	 * @param dataList
	 *            导出数据
	 * 
	 * @author: 于奇
	 * 
	 * 
	 */
	public void expExcel_2007(Map<String, Object> map, String fileName,
			String sheetName, String[] titlesName, String[] sqlColumns,
			List<Map<String, Object>> dataList) {
		OutputStream os;
		try {
			os = SkyEye.getResponse().getOutputStream();
			XSSFWorkbook xssf_w_book = new XSSFWorkbook();
			XSSFSheet xssf_w_sheet = xssf_w_book.createSheet(sheetName);
			XSSFRow xssf_w_row = null;// 创建一行
			XSSFCell xssf_w_cell = null;// 创建每个单元格
			int col_count = 0;
			int row_count = 0;
			row_count = dataList.size();
			col_count = titlesName.length;
			
			XSSFDataFormat format = xssf_w_book.createDataFormat();

			// 创建标题行样式
			XSSFCellStyle head_cellStyle = xssf_w_book.createCellStyle();
			XSSFFont head_font = xssf_w_book.createFont();
			head_font.setFontName("宋体");// 设置头部字体为宋体
			head_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			head_font.setFontHeightInPoints((short) 10);
			head_font.setColor(XSSFFont.COLOR_RED);
			

			head_cellStyle.setFont(head_font);// 单元格样式使用字体
			head_cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			head_cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			head_cellStyle.setDataFormat(format.getFormat("@"));

			// 普通文本
			XSSFCellStyle cellStyle_CN = xssf_w_book.createCellStyle();

			cellStyle_CN.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_CN.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_CN.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setDataFormat(format.getFormat("@"));
			
			// 日期
			XSSFCellStyle cellStyle_date = xssf_w_book.createCellStyle();

			cellStyle_date.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中 
			cellStyle_date.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_date.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderTop(XSSFCellStyle.BORDER_THIN);
//			cellStyle_date.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy/mm/dd"));
			cellStyle_date.setDataFormat(format.getFormat("yyyy/MM/dd"));
			
			// 数字
			XSSFCellStyle cellStyle_num = xssf_w_book.createCellStyle();

			cellStyle_num.setAlignment(CellStyle.ALIGN_RIGHT);// 右对齐
			cellStyle_num.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_num.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setDataFormat(format.getFormat("#,##0.######"));
			

			int titleRows = 0; // 标题占据的行数
			xssf_w_row = xssf_w_sheet.createRow(0 + titleRows);// 第一行写入标题行
			for (int i = 0; i < col_count; i++) {
				xssf_w_cell = xssf_w_row.createCell((short) i);			
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(titlesName[i]);
				xssf_w_cell.setCellStyle(head_cellStyle);
				xssf_w_sheet.autoSizeColumn((short) i);

			}

			Map<String, Object> rowMap = new HashMap<String, Object>();
			++titleRows;

			for (int i = 0; i < row_count; i++) {
				xssf_w_row = xssf_w_sheet.createRow(i + titleRows);
				rowMap = dataList.get(row_count != dataList.size() ? 1 : i);
				for (int j = 0; j < col_count; j++) {
					xssf_w_cell = xssf_w_row.createCell((short) j);
					Object value = rowMap.get(sqlColumns[j]);
					if(StringUtils.isNotBlank(value)){
					if (value instanceof java.math.BigDecimal) {
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						xssf_w_cell.setCellStyle(cellStyle_num);
						xssf_w_cell.setCellValue(((java.math.BigDecimal) value)
								.doubleValue());
					} else if (value instanceof java.sql.Timestamp) {
						
						try {
							xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
							xssf_w_cell.setCellStyle(cellStyle_date);
							xssf_w_cell.setCellValue((Date) value);
						} catch (Exception e) {
							xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
							xssf_w_cell.setCellStyle(cellStyle_CN);
							xssf_w_cell.setCellValue(value.toString());
						}

					} else if (value.toString().matches(
							"[1-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]")) {	
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						

						try {
							xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
							xssf_w_cell.setCellStyle(cellStyle_date);
							xssf_w_cell.setCellValue(sdf.parse(value.toString()));	
							
							
						} catch (Exception e) {
							xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
							xssf_w_cell.setCellStyle(cellStyle_CN);
							xssf_w_cell.setCellValue(value.toString());
						}
						
					} else {
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell.setCellStyle(cellStyle_CN);						
						xssf_w_cell.setCellValue(value.toString());
					}
					}
				}

			}

			SkyEye.getResponse().reset();
			SkyEye.getResponse().setCharacterEncoding("UTF-8");
			SkyEye.getResponse().setHeader(
					"Content-Disposition",
					new StringBuilder("attachment;filename=").append(
							new String(fileName.getBytes("GB2312"),
									"ISO-8859-1")).toString());
			SkyEye.getResponse().setContentType(ResMime.get("xlsx"));
			xssf_w_book.write(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void T306expExcel2007(Map<String, Object> map, String fileName,
			String sheetName, String[] titlesName, int[] titlesIndex,
			List<Map<String, Object>> dataList) {
		OutputStream os;
		try {
			os = SkyEye.getResponse().getOutputStream();
			XSSFWorkbook xssf_w_book = new XSSFWorkbook();
			XSSFSheet xssf_w_sheet = xssf_w_book.createSheet(sheetName);
			XSSFRow xssf_w_row = null;// 创建一行
			XSSFCell xssf_w_cell = null;// 创建每个单元格
			int col_count = 0;
			int row_count = 0;
			row_count = dataList.size();
			col_count = titlesName.length;
			
			XSSFDataFormat format = xssf_w_book.createDataFormat();

			// 创建标题行样式
			XSSFCellStyle head_cellStyle = xssf_w_book.createCellStyle();
			XSSFFont head_font = xssf_w_book.createFont();
			head_font.setFontName("宋体");// 设置头部字体为宋体
			head_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			head_font.setFontHeightInPoints((short) 10);
			head_font.setColor(XSSFFont.COLOR_RED);
			

			head_cellStyle.setFont(head_font);// 单元格样式使用字体
			head_cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			head_cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			head_cellStyle.setDataFormat(format.getFormat("@"));

			// 普通文本
			XSSFCellStyle cellStyle_CN = xssf_w_book.createCellStyle();

			cellStyle_CN.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_CN.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_CN.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setDataFormat(format.getFormat("@"));
			
			
			// 普通文本红色
			XSSFCellStyle cellStyle_red = xssf_w_book.createCellStyle();

			cellStyle_red.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_red.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_red.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setDataFormat(format.getFormat("@"));
			
			XSSFFont red_font = xssf_w_book.createFont();
			red_font.setColor((short)0xa);
			red_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			cellStyle_red.setFont(red_font);
			
			// 普通文本蓝色
			XSSFCellStyle cellStyle_blue = xssf_w_book.createCellStyle();

			cellStyle_blue.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_blue.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_blue.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setDataFormat(format.getFormat("@"));
			
			XSSFFont blue_font = xssf_w_book.createFont();
			blue_font.setColor((short)30F);
			blue_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			cellStyle_blue.setFont(blue_font);
			
			// 日期
			XSSFCellStyle cellStyle_date = xssf_w_book.createCellStyle();

			cellStyle_date.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中 
			cellStyle_date.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_date.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderTop(XSSFCellStyle.BORDER_THIN);
//			cellStyle_date.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy/mm/dd"));
			cellStyle_date.setDataFormat(format.getFormat("yyyy/MM/dd"));
			
			// 数字
			XSSFCellStyle cellStyle_num = xssf_w_book.createCellStyle();

			cellStyle_num.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_num.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_num.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setDataFormat(format.getFormat("#,##0.######"));
			

			int titleRows = 0; // 标题占据的行数
			xssf_w_row = xssf_w_sheet.createRow(0 + titleRows);// 第一行写入标题行
			for (int i = 0; i < col_count; i++) {
				xssf_w_cell = xssf_w_row.createCell((short) i);			
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(titlesName[i]);
				xssf_w_cell.setCellStyle(head_cellStyle);
				xssf_w_sheet.autoSizeColumn((short) i);

			}

			Map<String, Object> rowMap = new HashMap<String, Object>();
			++titleRows;

			for (int i = 0; i < row_count; i++) {
				xssf_w_row = xssf_w_sheet.createRow(i + titleRows);
				rowMap = dataList.get(row_count != dataList.size() ? 1 : i);
				for (int j = 0; j < col_count; j++) {
					xssf_w_cell = xssf_w_row.createCell((short) j);
					Object value = rowMap.get("COLUMN" + (titlesIndex[j])) == null ? ""
							: rowMap.get("COLUMN" + (titlesIndex[j]));
					if(titlesIndex[j] >= 7 && titlesIndex[j] <= 54){
						if("0".equals(value.toString())){
							xssf_w_cell.setCellStyle(cellStyle_red);
							xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
							xssf_w_cell.setCellValue("◇");
						}else if("1".equals(value.toString())){
							xssf_w_cell.setCellStyle(cellStyle_blue);
							xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
							xssf_w_cell.setCellValue("◇");
						}else{
							xssf_w_cell.setCellStyle(cellStyle_red);
							xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
							xssf_w_cell.setCellValue("");
						}
						continue;
					}
					if (value instanceof java.math.BigDecimal) {
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						xssf_w_cell.setCellStyle(cellStyle_num);
						xssf_w_cell.setCellValue(((java.math.BigDecimal) value)
								.doubleValue());
					} else if (value instanceof java.sql.Timestamp) {
						
						try {
							xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
							xssf_w_cell.setCellStyle(cellStyle_date);
							xssf_w_cell.setCellValue((Date) value);
						} catch (Exception e) {
							xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
							xssf_w_cell.setCellStyle(cellStyle_CN);
							xssf_w_cell.setCellValue(value.toString());
						}

					} else if (value.toString().matches(
							"[1-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]")) {	
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						

						try {
							xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
							xssf_w_cell.setCellStyle(cellStyle_date);
							xssf_w_cell.setCellValue(sdf.parse(value.toString()));	
							
							
						} catch (Exception e) {
							xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
							xssf_w_cell.setCellStyle(cellStyle_CN);
							xssf_w_cell.setCellValue(value.toString());
						}
						
					} else {
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell.setCellStyle(cellStyle_CN);						
						xssf_w_cell.setCellValue(value.toString());
					}

				}

			}

			SkyEye.getResponse().reset();
			SkyEye.getResponse().setCharacterEncoding("UTF-8");
			SkyEye.getResponse().setHeader(
					"Content-Disposition",
					new StringBuilder("attachment;filename=").append(
							new String(fileName.getBytes("GB2312"),
									"ISO-8859-1")).toString());
			SkyEye.getResponse().setContentType(ResMime.get("xlsx"));
			xssf_w_book.write(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
