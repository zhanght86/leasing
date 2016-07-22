package com.pqsoft.leeds.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.junit.Test;


import com.pqsoft.skyeye.api.SkyEye;

/**
 * 
 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
 * @time 2014年7月16日 上午9:54:05
 */
@SuppressWarnings({"rawtypes"})
public class ExcelUtil {

	private static final Log logger = LogFactory.getLog(ExcelUtil.class);
	public static final int COLUMN_WIDTH = 20;//列宽
	private static String[] SUFFIXS = { ".xls", ".xlsx" };

	/**
	 * @desc 
	 * 		1、读取多个sheet页。不存在合并行列。<br>
	 * 		2、每页第一行为标题行。标题行，必须设置字段注释。<br>
	 * 		3、第一列为非空列，若为空，则判定该行无数据。本sheet页读取完毕。<br>
	 * 		4、主要用于批量导入数据.
	 * @param
	 *  	filepath Excel文件路径
	 * @return 
	 * 		1、返回一个Map<String,Object>构成的list。<br>
	 *     2、list.get(0)返回标题行信息。<br>
	 *     3、list.get(1)返回字段映射信息。要求模板标题行的注释必须为相应字段<br>
	 *     4、其他依次为各行所读取的数据。
	 *     5、Map的key为cell【columnIndex】，例如第一列为cell0
	 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
	 * @time 2014年7月16日 下午12:35:42
	 * 
	 */
	public List<Map<String, Object>> importXls(String filepath)
			throws Exception {
		File file = new File(filepath);
		String fileName = file.getName();
		String fileSuffix = fileName.substring(fileName.lastIndexOf("."),
				fileName.length());
		InputStream is = new FileInputStream(file);
		
		return this.importXls(is, fileSuffix);
	}
	/**
	 *@requestFunction
	 * importXls(String filepath)方法的另一种形式。可以接受文件流
	 * 
	 * @param is 读取的Excel文件流
	 * @param fileSuffix 可取值为：.xls、.xlsx
	 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
	 * @time 2014年10月22日 下午1:28:32
	 */
	public List<Map<String, Object>> importXls(InputStream is, String fileSuffix) 
		throws Exception{
		List<Map<String, Object>> results = null;
		Workbook workbook = null;
		if (fileSuffix.equalsIgnoreCase(SUFFIXS[0])) {
			workbook = new HSSFWorkbook(is);
		}
		else if (fileSuffix.equalsIgnoreCase(SUFFIXS[1])) {
			workbook = new XSSFWorkbook(is);
		} else throw new Exception("导入的文件不是Excel格式");
		results = this.readData(workbook);
		return results;
	}
	/**
	 * @desc
	 * 分sheet页，读取一组关联性数据。sheet页有前后顺序。在前的先导入<br>
	 * @param
	 * 		每个sheet页构成一个List，依次放入一个总List中返回。每个sheet页读取规则同importXls
	 * @author leeds</a>
	 * @time 2014年11月20日 下午1:35:51
	 */
	public List<List<Map<String, Object>>> importXlsBySheets(String filepath) 
		throws Exception{
		File file = new File(filepath);
		String fileName = file.getName();
		String fileSuffix = fileName.substring(fileName.lastIndexOf("."),
				fileName.length());
		InputStream is = new FileInputStream(file);
		return this.importXlsBySheets(is, fileSuffix);
	}
	public List<List<Map<String, Object>>> importXlsBySheets(InputStream is, String fileSuffix) 
			throws Exception{
		List<List<Map<String, Object>>> results = new ArrayList<List<Map<String, Object>>>();
		Workbook workbook = null;
		if (fileSuffix.equalsIgnoreCase(SUFFIXS[0])) {
			workbook = new HSSFWorkbook(is);
		}
		else if (fileSuffix.equalsIgnoreCase(SUFFIXS[1])) {
			workbook = new XSSFWorkbook(is);
		} else {
			is.close();
			throw new Exception("导入的文件不是Excel格式");
		}
		
		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
			Sheet sheet = workbook.getSheetAt(numSheet);
			results.add(this.readData(sheet, true));
		}
		return results;
	}
	
	/**
	 *@requestFunction
	 * 提供一个查询出来的数据集合data，
	 * 一个指定Map中的field和导出的Excel中字段对应关系的headers，
	 * 一个Excel文件名title，
	 * 将对应的列表，导出成不存在合并行列、不分sheet页的Excel。<br>
	 * headers，格式如：{{"name", "姓名"},{"phone", "电话"},...}<br>
	 * List本身的index，决定相应字段的排列顺序。
	 * @return
	 * 生成一个Excel，并直接提供下载。
	 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
	 * @time 2014年10月11日 上午11:36:10
	 */
	public void exportXls(List<Map> data, String[][] headers, String title) {
		try {
			title = new String(title.getBytes("gb2312"), "iso8859-1");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// 声明一个工作薄
	    HSSFWorkbook workbook = new HSSFWorkbook();
	    workbook = this.genSheet(workbook, data, headers, "sheet0");
	    //提供下载
	    HttpServletResponse response = SkyEye.getResponse();
	    response.setContentType("octets/stream");
		response.addHeader("Content-Disposition", "attachment;filename="+title+".xls");
	    try {
	    	workbook.write(response.getOutputStream());
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	public void exportXlsSheets(List<List<Map>> datas, String[][][] headerss, String title, String[] sheetTitles) {
		try {
			title = new String(title.getBytes("gb2312"), "iso8859-1");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		for(int i=0; i<datas.size(); i++) {
			workbook = this.genSheet(workbook, datas.get(i), headerss[i], sheetTitles[i]);
			
		}
		//提供下载
		HttpServletResponse response = SkyEye.getResponse();
		response.setContentType("octets/stream");
		response.addHeader("Content-Disposition", "attachment;filename="+title+".xls");
		try {
			workbook.write(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private HSSFWorkbook genSheet(HSSFWorkbook wb, List<Map> data, String[][] headers, String sheetTitle){
		// 生成一个表格
		HSSFSheet sheet = wb.createSheet(sheetTitle);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(COLUMN_WIDTH);
		
		// 生成一个标题行样式
		HSSFCellStyle titleStyle = wb.createCellStyle();
		// 设置这些样式
		titleStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成标题字体
		HSSFFont titleFont = wb.createFont();
		titleFont.setColor(HSSFColor.VIOLET.index);
		titleFont.setFontHeightInPoints((short) 12);
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		titleStyle.setFont(titleFont);
		// 生成并设置普通行样式
		HSSFCellStyle normalStyle = wb.createCellStyle();
		normalStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		normalStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		normalStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		normalStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		normalStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		normalStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		normalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成普通行字体
		HSSFFont normalFont = wb.createFont();
		normalFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		normalStyle.setFont(normalFont);
		//产生表格标题行
		int index = 0;
		HSSFRow row = sheet.createRow(index);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(titleStyle);
			HSSFRichTextString text = new HSSFRichTextString(headers[i][1]);
			cell.setCellValue(text);
		}
		//遍历集合数据，产生数据行
		Iterator<Map> it = data.iterator();
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			Map dataOne = (Map) it.next();
			for (int i = 0; i < headers.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(normalStyle);
				String cellValue = "";
				Object o = dataOne.get(headers[i][0]);
				if(o != null) {
					cellValue = o.toString(); 
				}
				HSSFRichTextString text = new HSSFRichTextString(cellValue);
				cell.setCellValue(text);
			}
		}
		sheet.createFreezePane(0, 1);//锁定首行标题行。
		return wb;
	}
	
	public void exportXls(List<Map> data, JSONArray headers, String title) {
		String[][] headers1 = new String[headers.size()][];
		for(int i=0; i<headers.size(); i++) {
			JSONArray ja1 = headers.getJSONArray(i);
			headers1[i] = (String[])JSONArray.toArray(ja1, String.class);
		}
		this.exportXls(data, headers1, title);
	}
	
	
	private int getColCount(Sheet sheet) {
		int colCount = 0;
		if (this.getRowCount(sheet) == 0) {// 0行
			return colCount;
		} else {
			Row row = sheet.getRow(0);
			for (Iterator<Cell> it = row.iterator(); it.hasNext();) {
				Cell cell = it.next();
				if (this.isCellNull(cell))
					return colCount;
				else
					colCount++;
			}
		}
		return colCount;
	}
	
	

	/**
	 * @requestFunction 提供一个sheet对象，返回该sheet页中有效行数<br>
	 *                  计算法则：若某行第一列Cell为空（null或trim后是""），则判定该行为结束行。计算行数
	 * 
	 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
	 * @time 2014年7月16日 下午3:03:28
	 */
	private int getRowCount(Sheet sheet) {
		int rowCount = 0;
		for (Iterator<Row> it = sheet.iterator(); it.hasNext();) {
			Row row = it.next();
			Cell cell = row.getCell(0);
			if (this.isCellNull(cell)) {
				break;
			} else
				rowCount++;
		}
		return rowCount;
	}

	/**
	 * @requestFunction 提供一个Cell对象，当Cell为null或者Cell原始内容trim之后是""，返回true。即该Cell为空
	 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
	 * @time 2014年7月16日 下午3:34:28
	 */
	private boolean isCellNull(Cell cell) {
		if (cell == null
				|| (cell.getCellType() == Cell.CELL_TYPE_STRING && cell
						.getStringCellValue().trim().equals("")))
			return true;
		else
			return false;
	}

	/**
	 * @desc
	 * 读取一个workbook对象中的数据
	 * 
	 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
	 * @time 2014年7月16日 下午7:08:12
	 */
	private List<Map<String, Object>> readData(Workbook workbook)
			throws IOException {
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
			Sheet sheet = workbook.getSheetAt(numSheet);
			if(numSheet == 0) {//不读取非第一页的标题行
				datas.addAll(this.readData(sheet, true));
			} else {
				datas.addAll(this.readData(sheet, false));
			}
		}
		return datas;
	}
	
	/**
	 * @desc
	 *读取一个sheet页的数据。
	 * @param
	 * 		flag 是否读取第一行（标题行）。true，为读取。
	 * @author leeds</a>
	 * @time 2014年11月20日 下午3:22:54
	 */
	private List<Map<String, Object>> readData(Sheet sheet, boolean flag)
			throws IOException {
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		int colCount = this.getColCount(sheet);// 模板列数，以标题行为准。
		
		int rowCount = this.getRowCount(sheet);// 模板每页的行数
		// 循环行Row
		for (int numRow = 0; numRow < rowCount; numRow++) {
			Row row = sheet.getRow(numRow);// 当row中所有cell都为null时，该row为null
			Map<String, Object> rowData = new HashMap<String, Object>();
			// 循环列
			for (int colNum = 0; colNum < colCount; colNum++) {
				Cell cell = row.getCell(colNum);
				rowData.put("cell" + colNum, this.getValue(cell));
			}
			if(flag) {
				datas.add(rowData);
				//解析标题行注释，生成字段映射，加入list.get(1)
				if(numRow == 0) {
					Map<String, Object> filedsMap = new HashMap<String, Object>();
					// 循环列
					for (int colNum = 0; colNum < colCount; colNum++) {
						Cell cell = row.getCell(colNum);
						filedsMap.put("cell" + colNum, cell.getCellComment().getString().getString().trim());
					}
					datas.add(filedsMap);
				}
			} else if(numRow != 0) {
				datas.add(rowData);
			}
		}
		return datas;
	}

//	@Test
	public void  testImportXls() {
		String filepath = "F:\\pqsoft\\开发的功能点\\山重\\导入模板\\员工档案.xls";
		
		try {
			List<List<Map<String, Object>>> datas = this.importXlsBySheets(filepath);
			
			int i = 0;
			int a = i + 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * @requestFunction 提供一个Cell对象，拿到其中的内容。<br>
	 *                  如果该Cell为空（null或trim后为""），则返回null
	 * 
	 * @author <a href='mailto:leedsjung@126.com'>leeds</a>
	 * @time 2014年7月16日 下午4:01:03
	 */
	private String getValue(Cell cell) {
		if (this.isCellNull(cell))
			return null;
		else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC&&DateUtil.isCellDateFormatted(cell)) {
			Date d = cell.getDateCellValue();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.format(d);
		} else {
			cell.setCellType(Cell.CELL_TYPE_STRING);
			return cell.getStringCellValue().trim();
		}
	}
}
